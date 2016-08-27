package models.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.google.inject.Inject;

import controllers.DatabaseConnection.DatabaseUtilities;
import models.Column;
import models.DatatableEntry;
import models.Reports;
import models.ThreatType;
import models.dao.ThreatReportDao;

@Singleton
public class ThreatReportDaoImpl implements ThreatReportDao {
	
	Connection connection;
	@Inject
	public ThreatReportDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}

	@Inject
	LocationDaoImpl locationDao;
	
	@Inject
	LookupDaoImpl lookupDao;
	
	@Override
	public DatatableEntry getDatatableEntry(){
		
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
