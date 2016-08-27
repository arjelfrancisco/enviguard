package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.AnimalDirectWildlifeObservation;
import models.Column;
import models.DatatableEntry;
import models.FloralDirectWildlifeObservation;
import models.ForestConditionReport;
import models.IndirectWildlifeObservation;
import models.Observation;
import models.PatrolLocation;
import models.ReportsParameter;
import models.SpeciesType;
import models.ThreatReport;
import models.ThreatType;
import models.WildlifeObservation;
import models.dao.LocationDao;
import play.data.format.Formats.DateTime;
import play.libs.Json;
import utils.CyberTrackerUtilities;
import controllers.DatabaseConnection.DatabaseUtilities;
import enums.ActionTakenEnum;
import enums.ForestConditionTypeEnum;
import enums.SpeciesEnum;
import enums.WildlifeObservationTypeEnum;
import models.Reports;
import models.dao.ReportsDao;

public class ReportsDaoImpl implements ReportsDao {

	Connection connection;
	@Inject
	public ReportsDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}

	@Inject
	LocationDaoImpl locationDao;
	
	@Inject
	LookupDaoImpl lookupDao;
	
	@Override
	public List<Reports> getReport(String observation, String type) {

		List<Reports> reports = new ArrayList<Reports>();
		try {
		
			Statement stmt = connection.createStatement();
			
			String query = "SELECT " + type + " as name COUNT(*) as value " +
							"FROM " + observation +
							"GROUP BY " + type;
						
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			String name = "";
			int value = -1;
			
			
			while(rs.next()){
				Reports report = new Reports();
				value = rs.getInt("value");
				name = rs.getString("name");
				
				report.setLabel(name);
				report.setValue(value);
				
				reports.add(report);
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reports;
	}


		@Override
		public List<Reports> getWildlifeReport(String type, String species, String speciesType) {
		List<Reports> reports = new ArrayList<Reports>();
		try {
		
			
			
			Statement stmt = connection.createStatement();
			
			String query = "SELECT " + type + " as name COUNT(*) as value " +
							"FROM " + speciesType +
							"GROUP BY " + type;
						
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			String name = "";
			int value = -1;
			
			
			while(rs.next()){
				Reports report = new Reports();
				value = rs.getInt("value");
				name = rs.getString("name");
				
				report.setLabel(name);
				report.setValue(value);
				
				reports.add(report);
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reports;
	}


	@Override
	public List<ForestConditionReport> getForestConditionTableReports(ReportsParameter reportsParameter) {
		List<ForestConditionReport> forestConditionReports = new ArrayList<ForestConditionReport>();
		try {
			
			Statement stmt = connection.createStatement();
			String where = "";
			
			if(reportsParameter.getFilterType() == "region"){
				
				where = "WHERE (SELECT region from patrol_locations  where region IS NOT NULL  limit 1) = '" + 
							reportsParameter.getRegions() + "'";
			}
			else if(reportsParameter.getFilterType() == "date"){
				where = "WHERE patrols.start_date between '" + reportsParameter.getStartDate() 
						+ "' and '" + reportsParameter.getEndDate() + "'" 
						+ "and patrols.end_date between '" + reportsParameter.getStartDate() + "' and '" 
						+ reportsParameter.getEndDate() + "'" ;  
			}
			
			String query = "SELECT "				 

					+ "forest.forest_condition_type,"
					+ "forest.presence_of_regenerants,"
					+ "forest.density_of_regenerants,"

					+ "obs.start_date,"
					+ "obs.end_date,"

					+ "patrols.patrol_name"

					+ " FROM forest_condition_observations forest"

					+ " LEFT JOIN patrol_observations obs"
					+ " on forest.forest_condition_observation_id = obs.observation_id"
					+ " AND obs.observation_type = 'FOREST_CONDITION'"

					+ " LEFT JOIN patrols patrols"
					+ " ON obs.patrol_id = patrols.patrol_id "
					+ where;
						
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				ForestConditionReport forestConditionReport = new ForestConditionReport();
				forestConditionReport.setPatrolName(rs.getString("patrol_name"));
				forestConditionReport.setForestConditionType(ForestConditionTypeEnum.valueOf(rs.getString("forest_condition_type")).getLabel());
				forestConditionReport.setPresenceOfRegenerants(rs.getBoolean("presence_of_regenerants"));
				forestConditionReport.setDensityOfRegenerants(rs.getString("density_of_regenerants"));
				forestConditionReport.setStartDate(rs.getDate("start_date"));
				forestConditionReport.setEndDate(rs.getDate("end_date"));
				forestConditionReports.add(forestConditionReport);
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forestConditionReports;
	}


	@Override
	public List<ThreatReport> getThreatReport(ReportsParameter reportsParameter) {
		List<ThreatReport> threatReports = new ArrayList<ThreatReport>();
		try {
			
			Statement stmt = connection.createStatement();
			String where = "";
			
			if(reportsParameter.getFilterType() == "region"){
				
				where = "WHERE (SELECT region from patrol_locations  where region IS NOT NULL  limit 1) = '" + 
							reportsParameter.getRegions() + "'";
			}
			else if(reportsParameter.getFilterType() == "region"){
				where = "WHERE patrols.start_date between '" + reportsParameter.getStartDate() 
						+ "' and '" + reportsParameter.getEndDate() + "'" 
						+ "and patrols.end_date between '" + reportsParameter.getStartDate() + "' and '" 
						+ reportsParameter.getEndDate() + "'" ;  
			}
			
			String query = "SELECT "				 

					+ "threat.threat_type,"
					+ "threat.distance_of_threat_from_waypoint,"
					+ "threat.bearing_of_threat_from_waypoint,"
					+ "threat.response_to_threat,"
					+ "threat.note,"
					
					+ "(SELECT region from patrol_locations  where region IS NOT NULL  and patrol_id = patrols.patrol_id limit 1) as region,"

					+ "obs.start_date,"
					+ "obs.end_date,"

					+ "patrols.patrol_name"

					+ " FROM cybertrackerdb.threat_observations threat"

					+ " LEFT JOIN patrol_observations obs"
					+ " on threat.threat_observation_id = obs.observation_id"
					+ " AND obs.observation_type = 'THREATS'"

					+ " LEFT JOIN patrols patrols"
					+ " ON obs.patrol_id = patrols.patrol_id "
					+ where;
						
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				
				ThreatReport threatReport = new ThreatReport();
				threatReport.setPatrolName(rs.getString("patrol_name"));
				threatReport.setThreatType(rs.getString("threat_type"));
				threatReport.setDistance(rs.getInt("distance_of_threat_from_waypoint"));
				threatReport.setBearing(rs.getInt("bearing_of_threat_from_waypoint"));
				threatReport.setResponse(rs.getString("response_to_threat"));
				threatReport.setNote(rs.getString("note"));
				threatReport.setStartDate(rs.getDate("start_date"));
				threatReport.setEndDate(rs.getDate("end_date"));
				threatReport.setRegion(rs.getString("region"));
				threatReports.add(threatReport);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return threatReports;
	}


	@Override
	public List<WildlifeObservation> getAnimalDirectWildlifeReport(ReportsParameter reportsParameter) {
		
		return null;
	}


	@Override
	public List<Reports> getThreatReports(ReportsParameter reportsParameter) {
		List<Reports> threatReports = new ArrayList<Reports>();
		try {
			
			Statement stmt = connection.createStatement();
			String where = "";
			
			
			if(reportsParameter.getFilterType().equals("region")){
				
				where = "WHERE (SELECT region from patrol_locations  where region IS NOT NULL "
						+ " AND patrolObs.patrol_id = patrol_locations.patrol_id  limit 1) = '" + 
							reportsParameter.getRegions() + "'";
			}
			else if(reportsParameter.getFilterType().equals("date")){
				where = "WHERE patrols.start_date between '" + reportsParameter.getStartDate() 
						+ "' and '" + reportsParameter.getEndDate() + "'" 
						+ "and patrols.end_date between '" + reportsParameter.getStartDate() + "' and '" 
						+ reportsParameter.getEndDate() + "'" ;  
			}
			
			String query = "SELECT "				 

					+ "threat.threat_type as threat_type,"
					+ "COUNT(*) as threat_count"
					
					+ " FROM threat_observations threat"

					+ " INNER JOIN patrol_observations patrolObs"
					+ " ON patrolObs.observation_id = threat_observation_id"
					+ " AND patrolObs.observation_type = 'THREATS'"

					+ " LEFT JOIN patrols patrols"
					+ " ON patrolObs.patrol_id = patrols.patrol_id "
					
					+ where
					
					+ " GROUP BY threat.threat_type ";
						
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				
				Reports threatReport = new Reports();
				
				threatReport.setLabel(rs.getString("threat_type"));
				threatReport.setValue(rs.getInt("threat_count"));

				
				threatReports.add(threatReport);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return threatReports;
	}


	@Override
	public DatatableEntry getWildlifeDatatable() {
		
		List<String> regions = locationDao.getDistinctRegions();
		List<SpeciesType> speciesTypes = lookupDao.getSpeciestypes();
		List<List<String>> datasets = new ArrayList<>();
		
		List<Column> columns = new ArrayList<>();
		Column column = new Column();
		column.setTitle("Regions");
		columns.add(column);
		
		for(SpeciesType speciesType: speciesTypes){
			Column columnRegion = new Column();
			columnRegion.setTitle(speciesType.getName());
			
			columns.add(columnRegion);
		}
		
		for(String region: regions){
			List<String> dataset = new ArrayList<>();
			dataset.add(region);
			//datasets.add("");
			for(SpeciesType speciesType: speciesTypes){
				//call sql get threat count per region and threat type
				
				try {
					
					Statement stmt = connection.createStatement();
					
					String query = "SELECT "				 

							+ "wildlife.species_type as species_type,"
							+ "COUNT(*) as species_count"
							
							+ " FROM wildlife_observations wildlife"

							+ " INNER JOIN patrol_observations patrolObs"
							+ " ON patrolObs.observation_id = wildlife_observation_id"
							+ " AND patrolObs.observation_type = 'WILDLIFE'"

							+ " LEFT JOIN patrols patrols"
							+ " ON patrolObs.patrol_id = patrols.patrol_id "
							
							+ " WHERE (SELECT region from patrol_locations  where region IS NOT NULL "
							+ " AND patrolObs.patrol_id = patrol_locations.patrol_id  limit 1) = '" + region + "' "
							+ "  AND species_type = '" + speciesType.getName() + "'"
 
							
							
							+ " GROUP BY wildlife.species_type ";
								
					
						
					ResultSet rs = stmt.executeQuery(query);
					
					int count = 0;
					
					while(rs.next()){
						
						count = rs.getInt("species_count");
					}
					dataset.add(Integer.toString(count));
					//dataset.add(threatType);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				
				
				
			}
			
			datasets.add(dataset);
		}
		
		DatatableEntry datatableEntry = new DatatableEntry();
		datatableEntry.setDataset(datasets);
		datatableEntry.setColumns(columns);
		
		return datatableEntry;
	}
	
	public DatatableEntry getForestConditionDatatable() {
		
		List<String> regions = locationDao.getDistinctRegions();
		//List<SpeciesType> speciesTypes = lookupDao.getSpeciestypes();
		List<String> cols = new ArrayList<String>();
		for(ForestConditionTypeEnum fcType : ForestConditionTypeEnum.values()){
			
			cols.add(fcType.getLabel());
		}
		
		
		List<List<String>> datasets = new ArrayList<>();
		
		List<Column> columns = new ArrayList<>();
		Column column = new Column();
		column.setTitle("Regions");
		columns.add(column);
		
		for(String forestConditionType: cols){
			Column columnRegion = new Column();
			columnRegion.setTitle(forestConditionType);
			
			columns.add(columnRegion);
		}
		
		List<String> forestConditionTypes = new ArrayList<String>();
		for(ForestConditionTypeEnum fcType : ForestConditionTypeEnum.values()){
			
			forestConditionTypes.add(fcType.name());
		}
		
		for(String region: regions){
			List<String> dataset = new ArrayList<>();
			dataset.add(region);
			//datasets.add("");
			for(String forestConditionType: forestConditionTypes){
				//call sql get threat count per region and threat type
				
				try {
					
					Statement stmt = connection.createStatement();
					
					String query = "SELECT "				 

							+ "fcObs.forest_condition_type as forest_condition_type,"
							+ "COUNT(*) as forest_condition_count"
							
							+ " FROM forest_condition_observations fcObs"

							+ " INNER JOIN patrol_observations patrolObs"
							+ " ON patrolObs.observation_id = forest_condition_observation_id"
							+ " AND patrolObs.observation_type = 'FOREST_CONDITION'"

							+ " LEFT JOIN patrols patrols"
							+ " ON patrolObs.patrol_id = patrols.patrol_id "
							
							+ " WHERE (SELECT region from patrol_locations  where region IS NOT NULL "
							+ " AND patrolObs.patrol_id = patrol_locations.patrol_id  limit 1) = '" + region + "' "
							+ "  AND forest_condition_type = '" + forestConditionType + "'"
 
							
							
							+ " GROUP BY fcObs.forest_condition_type ";
								
					
						
					ResultSet rs = stmt.executeQuery(query);
					
					int count = 0;
					
					while(rs.next()){
						
						count = rs.getInt("forest_condition_count");
					}
					dataset.add(Integer.toString(count));
					//dataset.add(threatType);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				
				
				
			}
			
			datasets.add(dataset);
		}
		
		DatatableEntry datatableEntry = new DatatableEntry();
		datatableEntry.setDataset(datasets);
		datatableEntry.setColumns(columns);
		
		return datatableEntry;
	}


	@Override
	public DatatableEntry getThreatDatatable() {
		List<String> regions = locationDao.getDistinctRegions();
		List<ThreatType> threatTypes = lookupDao.getThreatTypes();
		List<List<String>> datasets = new ArrayList<>();
		
		List<Column> columns = new ArrayList<>();
		Column column = new Column();
		column.setTitle("Regions");
		columns.add(column);
		
		for(ThreatType threatType: threatTypes){
			Column columnRegion = new Column();
			columnRegion.setTitle(threatType.getName());
			
			columns.add(columnRegion);
		}
		
		for(String region: regions){
			List<String> dataset = new ArrayList<>();
			dataset.add(region);
			//datasets.add("");
			for(ThreatType threatType: threatTypes){
				//call sql get threat count per region and threat type
				
				try {
					
					Statement stmt = connection.createStatement();
					
					String query = "SELECT "				 

							+ "threat.threat_type as threat_type,"
							+ "COUNT(*) as threat_count"
							
							+ " FROM threat_observations threat"

							+ " INNER JOIN patrol_observations patrolObs"
							+ " ON patrolObs.observation_id = threat_observation_id"
							+ " AND patrolObs.observation_type = 'THREATS'"

							+ " LEFT JOIN patrols patrols"
							+ " ON patrolObs.patrol_id = patrols.patrol_id "
							
							+ " WHERE (SELECT region from patrol_locations  where region IS NOT NULL "
							+ " AND patrolObs.patrol_id = patrol_locations.patrol_id  limit 1) = '" + region + "' "
							+ "  AND threat_type = '" + threatType.getName() + "'"
 
							
							
							+ " GROUP BY threat.threat_type ";
								
					
						
					ResultSet rs = stmt.executeQuery(query);
					
					int threatCount = 0;
					
					while(rs.next()){
						
						threatCount = rs.getInt("threat_count");
					}
					dataset.add(Integer.toString(threatCount));
					//dataset.add(threatType);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				
				
				
			}
			
			datasets.add(dataset);
		}
		
		DatatableEntry datatableEntry = new DatatableEntry();
		datatableEntry.setDataset(datasets);
		datatableEntry.setColumns(columns);
		
		return datatableEntry;
		
	}
	
	
}
