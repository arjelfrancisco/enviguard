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

import utils.CyberTrackerUtilities;
import controllers.DatabaseConnection.DatabaseUtilities;
import enums.ActionTakenEnum;
import enums.DensityOfRegenerantsEnum;
import enums.ForestConditionTypeEnum;
import enums.ObservationTypeEnum;
import enums.ResponseToThreatEnum;
import enums.SpeciesEnum;
import enums.WildlifeObservationTypeEnum;
import models.AnimalDirectWildlifeObservation;
import models.FloralDirectWildlifeObservation;
import models.ForestConditionObservation;
import models.IndirectWildlifeObservation;
import models.Observation;
import models.OtherObservation;
import models.Patrol;
import models.PatrolObservationImage;
import models.Reports;
import models.ThreatObservation;
import models.ThreatType;
import models.WildlifeObservation;
import models.dao.ObservationDao;

@Singleton
public class ObservationDaoImpl implements ObservationDao {



	Connection connection;
	@Inject
	public ObservationDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}

	@Override
	public Long addObservation(Observation observation) {
		
		Long observationId = null;
		
		try {
			ObservationTypeEnum observationType = observation.getObservationType();
			if(observationType == ObservationTypeEnum.FOREST_CONDITION) {
				ForestConditionObservation forestConditionObservation = (ForestConditionObservation) observation;
				String insertSql = "INSERT INTO forest_condition_observations (forest_condition_type, presence_of_regenerants, density_of_regenerants) VALUES(?, ?, ?)";
				
				PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, forestConditionObservation.getForestConditionType().name());
				stmt.setBoolean(2, forestConditionObservation.isPresenceOfRegenerants());
				stmt.setString(3, forestConditionObservation.getDensityOfRegenerants().name());
				stmt.executeUpdate();
				
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				
				observationId = rs.getLong(1);
			} else if(observationType == ObservationTypeEnum.WILDLIFE) {
				WildlifeObservation wildlifeObservation = (WildlifeObservation) observation;
				WildlifeObservationTypeEnum wildlifeObservationType = wildlifeObservation.getWildlifeObservationType();
				SpeciesEnum species = wildlifeObservation.getSpecies();
				String speciesType = wildlifeObservation.getSpeciesType();
				
				if(wildlifeObservationType == WildlifeObservationTypeEnum.DIRECT) {
					if(species == SpeciesEnum.FLORA) {
						FloralDirectWildlifeObservation floralDirectWildlifeObservation = (FloralDirectWildlifeObservation) observation;
						
						String insertSql = "INSERT INTO wildlife_observations (wildlife_observation_type, species, species_type, diameter, "
								+ "no_of_trees, observed_through_gathering, other_tree_species_observed) VALUES (?, ?, ?, ?, ?, ?, ?)";
						
						PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
						stmt.setString(1, wildlifeObservationType.name());
						stmt.setString(2, species.name());
						stmt.setString(3, speciesType);
						stmt.setInt(4, floralDirectWildlifeObservation.getDiameter());
						stmt.setInt(5, floralDirectWildlifeObservation.getNoOfTrees());
						stmt.setBoolean(6, floralDirectWildlifeObservation.getObservedThrougGathering());
						stmt.setString(7, floralDirectWildlifeObservation.getOtherTreeSpeciedObserved());
						stmt.executeUpdate();
						
						ResultSet rs = stmt.getGeneratedKeys();
						rs.next();
						
						observationId = rs.getLong(1);
					} else {
						AnimalDirectWildlifeObservation animalDirectWildlifeObservation = (AnimalDirectWildlifeObservation) observation;
						
						String insertSql = "INSERT INTO wildlife_observations (wildlife_observation_type, species, species_type, no_of_male_adults, "
								+ "no_of_female_adults, no_of_young, no_of_undetermined, action_taken, observed_through_hunting) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
						PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
						stmt.setString(1, wildlifeObservationType.name());
						stmt.setString(2, species.name());
						stmt.setString(3, speciesType);
						stmt.setInt(4, animalDirectWildlifeObservation.getNoOfMaleAdults());
						stmt.setInt(5, animalDirectWildlifeObservation.getNoOfFemaleAdults());
						stmt.setInt(6, animalDirectWildlifeObservation.getNoOfYoung());
						stmt.setInt(7, animalDirectWildlifeObservation.getNoOfUndetermined());
						stmt.setString(8, animalDirectWildlifeObservation.getActionTaken().name());
						stmt.setBoolean(9, animalDirectWildlifeObservation.getObservedThroughHunting());
						stmt.executeUpdate();
						
						ResultSet rs = stmt.getGeneratedKeys();
						rs.next();
						
						observationId = rs.getLong(1);
					}
				} else {
					IndirectWildlifeObservation indirectWildlifeObservation = (IndirectWildlifeObservation) observation;
					
					String insertSql = "INSERT INTO wildlife_observations (wildlife_observation_type, species, species_type, evidences) VALUES (?, ?, ?, ?)";
					
					PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, wildlifeObservationType.name());
					stmt.setString(2, species.name());
					stmt.setString(3, speciesType);
					stmt.setString(4, indirectWildlifeObservation.getEvidences());
					
					stmt.executeUpdate();
					
					ResultSet rs = stmt.getGeneratedKeys();
					rs.next();
					
					observationId = rs.getLong(1);
				}
			} else if(observationType == ObservationTypeEnum.THREATS) {
				ThreatObservation threatObservation = (ThreatObservation) observation;
				
				String insertSql = "INSERT INTO threat_observations (threat_type, distance_of_threat_from_waypoint, bearing_of_threat_from_waypoint, response_to_threat, note) VALUES (?, ?, ?, ?, ?)";
				
				PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, threatObservation.getThreatType());
				stmt.setInt(2, threatObservation.getDistanceOfThreatFromWaypoint());
				stmt.setInt(3, threatObservation.getBearingOfThreatFromWaypoint());
				stmt.setString(4, threatObservation.getResponseToThreat().name());
				stmt.setString(5, threatObservation.getNote());
				
				stmt.executeUpdate();
				
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				
				observationId = rs.getLong(1);
			} else {
				OtherObservation otherObservation = (OtherObservation) observation;
				
				String insertSql = "INSERT INTO other_observations (note) VALUES (?)";
				
				PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, otherObservation.getNote());
				
				stmt.executeUpdate();
				
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				
				observationId = rs.getLong(1);
			}
			
			String insertSql = "INSERT INTO patrol_observations (patrol_id, observation_id, observation_type, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, observation.getPatrolId());
			stmt.setLong(2, observationId);
			stmt.setString(3, observation.getObservationType().name());
			stmt.setTimestamp(4, CyberTrackerUtilities.convertToTimestampDate(observation.getStartDate()));
			stmt.setTimestamp(5, CyberTrackerUtilities.convertToTimestampDate(observation.getEndDate()));
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			
			return rs.getLong(1);
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Observation> getObservationByPatrolId(Long patrolId) {
		List<Observation> observations = new ArrayList<Observation>();
		
		
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM patrol_observations where patrol_id = " + patrolId;
			
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				Observation observation = new Observation();
				//patrolId = rs.getLong("patrol_id");	
				
				
				long observationId = rs.getLong("observation_id");
				Date startDate = rs.getDate("start_date");
				Date endDate = rs.getDate("end_date");
				ObservationTypeEnum observationType = ObservationTypeEnum.valueOf(rs.getString("observation_type"));
				
				observation.setId(rs.getLong("patrol_observation_id"));
				observation.setPatrolId(patrolId);
				observation.setObsId(observationId);
				observation.setStartDate(startDate);
				observation.setEndDate(endDate);
				observation.setObservationType(observationType);
				
				observations.add(observation);
				/*
				
				if(observationType == ObservationTypeEnum.FOREST_CONDITION){
					ForestConditionObservation forestConditionObservation = new ForestConditionObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM forest_condition_observations where forest_condition_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){
					
							forestConditionObservation.setForestConditionType(ForestConditionTypeEnum.valueOf(rs1.getString("forest_condition_type")));
							forestConditionObservation.setPresenceOfRegenerants(rs1.getBoolean("presence_of_regenerants"));
							forestConditionObservation.setDensityOfRegenerants(DensityOfRegenerantsEnum.valueOf(rs1.getString("density_of_regenerants")));
							
							forestConditionObservation.setId(observationId);
							forestConditionObservation.setPatrolId(patrolId);
							forestConditionObservation.setForestConditionObservationId(rs1.getLong("forest_condition_observation_id"));
							forestConditionObservation.setStartDate(startDate);
							forestConditionObservation.setEndDate(endDate);
							forestConditionObservation.setObservationType(observationType);
							
							observations.add(forestConditionObservation);
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if(observationType == ObservationTypeEnum.WILDLIFE){
					WildlifeObservation wildlifeConditionObservation = new WildlifeObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM wildlife_observations where wildlife_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){
							
							WildlifeObservationTypeEnum wildlifeObservationType = WildlifeObservationTypeEnum.valueOf(rs1.getString("wildlife_observation_type"));
							SpeciesEnum species = SpeciesEnum.valueOf(rs1.getString("species"));
							String speciesType = rs1.getString("species_type");
							
							if(wildlifeObservationType == WildlifeObservationTypeEnum.DIRECT){
								if(species == SpeciesEnum.FLORA){
									FloralDirectWildlifeObservation floralDirectWildlifeObservation = new FloralDirectWildlifeObservation();

									floralDirectWildlifeObservation.setDiameter(rs1.getInt("diameter"));
									floralDirectWildlifeObservation.setNoOfTrees(rs1.getInt("no_of_trees"));
									floralDirectWildlifeObservation.setObservedThrougGathering(rs1.getBoolean("observed_through_gathering"));
									floralDirectWildlifeObservation.setOtherTreeSpeciedObserved(rs1.getString("other_tree_species_observed"));
									
									floralDirectWildlifeObservation.setSpecies(species);
									floralDirectWildlifeObservation.setSpeciesType(speciesType);
									floralDirectWildlifeObservation.setPatrolId(patrolId);
									floralDirectWildlifeObservation.setId(observationId);
									floralDirectWildlifeObservation.setStartDate(startDate);
									floralDirectWildlifeObservation.setEndDate(endDate);
									floralDirectWildlifeObservation.setObservationType(observationType);
									floralDirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
									
									observations.add(floralDirectWildlifeObservation);
									
									
								}else{
									AnimalDirectWildlifeObservation animalDirectWildlifeObservation = new AnimalDirectWildlifeObservation();
									
									animalDirectWildlifeObservation.setActionTaken(ActionTakenEnum.valueOf(rs1.getString("action_taken")));
									animalDirectWildlifeObservation.setNoOfFemaleAdults(rs1.getInt("no_of_female_adults"));
									animalDirectWildlifeObservation.setNoOfMaleAdults(rs1.getInt("no_of_male_adults"));
									animalDirectWildlifeObservation.setNoOfUndetermined(rs1.getInt("no_of_undetermined"));
									animalDirectWildlifeObservation.setNoOfYoung(rs1.getInt("no_of_young"));
									animalDirectWildlifeObservation.setObservedThroughHunting(rs1.getBoolean("observed_through_hunting"));
									
									animalDirectWildlifeObservation.setSpecies(species);
									animalDirectWildlifeObservation.setSpeciesType(speciesType);
									animalDirectWildlifeObservation.setPatrolId(patrolId);
									animalDirectWildlifeObservation.setId(observationId);
									animalDirectWildlifeObservation.setStartDate(startDate);
									animalDirectWildlifeObservation.setEndDate(endDate);
									animalDirectWildlifeObservation.setObservationType(observationType);
									animalDirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
									
									observations.add(animalDirectWildlifeObservation);
								}
								
							}
							else{
									IndirectWildlifeObservation indirectWildlifeObservation = new IndirectWildlifeObservation();
									
									indirectWildlifeObservation.setEvidences(rs1.getString("evidences"));
									
									indirectWildlifeObservation.setSpecies(species);
									indirectWildlifeObservation.setSpeciesType(speciesType);
									indirectWildlifeObservation.setId(observationId);
									indirectWildlifeObservation.setPatrolId(patrolId);
									indirectWildlifeObservation.setStartDate(startDate);
									indirectWildlifeObservation.setEndDate(endDate);
									indirectWildlifeObservation.setObservationType(observationType);
									indirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
									
									observations.add(indirectWildlifeObservation);
								
							}
							//wildlifeConditionObservation.setWildlifeObservationType();
							
							
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				else if(observationType == ObservationTypeEnum.THREATS){
					ThreatObservation threatObservation = new ThreatObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM threat_observations where threat_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){
					
							
							threatObservation.setThreatType(rs1.getString("threat_type"));
							threatObservation.setDistanceOfThreatFromWaypoint(rs1.getInt("distance_of_threat_from_waypoint"));
							threatObservation.setBearingOfThreatFromWaypoint(rs1.getInt("bearing_of_threat_from_waypoint"));
							threatObservation.setResponseToThreat(ResponseToThreatEnum.valueOf(rs1.getString("response_to_threat")));
							threatObservation.setNote(rs1.getString("note"));
							
							threatObservation.setId(observationId);
							threatObservation.setPatrolId(patrolId);
							threatObservation.setStartDate(startDate);
							threatObservation.setEndDate(endDate);
							threatObservation.setObservationType(observationType);
							
							//observation =  forestConditionObservation;
							observations.add(threatObservation);
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
				}
				
				else if(observationType == ObservationTypeEnum.OTHER_OBSERVATIONS){
					OtherObservation otherObservation = new OtherObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM other_observations where other_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){	
							otherObservation.setNote(rs1.getString("note"));
							
							otherObservation.setId(observationId);
							otherObservation.setPatrolId(patrolId);
							otherObservation.setStartDate(startDate);
							otherObservation.setEndDate(endDate);
							otherObservation.setObservationType(observationType);
							
							observations.add(otherObservation);
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			
				*/
				
				
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return observations;
	}

	@Override
	public Observation getObservationByObsId(Long id) {
		//List<Observation> observations = new ArrayList<Observation>();
		
		
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM patrol_observations where patrol_observation_id = " + id;
			
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				Observation observation = new Observation();
				//patrolId = rs.getLong("patrol_id");	
				
				
				long obsId = rs.getLong("patrol_observation_id");
				long observationId = rs.getLong("observation_id");
				long patrolId = rs.getLong("patrol_id");
				Date startDate = rs.getDate("start_date");
				Date endDate = rs.getDate("end_date");
				ObservationTypeEnum observationType = ObservationTypeEnum.valueOf(rs.getString("observation_type"));
				
				observation.setId(id);
				observation.setPatrolId(patrolId);
				observation.setObsId(observationId);
				observation.setStartDate(startDate);
				observation.setEndDate(endDate);
				observation.setObservationType(observationType);
							
				if(observationType == ObservationTypeEnum.FOREST_CONDITION){
					ForestConditionObservation forestConditionObservation = new ForestConditionObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM forest_condition_observations where forest_condition_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){
					
							forestConditionObservation.setForestConditionType(ForestConditionTypeEnum.valueOf(rs1.getString("forest_condition_type")));
							forestConditionObservation.setPresenceOfRegenerants(rs1.getBoolean("presence_of_regenerants"));
							forestConditionObservation.setDensityOfRegenerants(DensityOfRegenerantsEnum.valueOf(rs1.getString("density_of_regenerants")));
							
							forestConditionObservation.setId(observationId);
							forestConditionObservation.setPatrolId(patrolId);
							forestConditionObservation.setForestConditionObservationId(rs1.getLong("forest_condition_observation_id"));
							forestConditionObservation.setStartDate(startDate);
							forestConditionObservation.setEndDate(endDate);
							forestConditionObservation.setObservationType(observationType);
							forestConditionObservation.setObsId(obsId);
							//observations.add(forestConditionObservation);
							return forestConditionObservation;
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if(observationType == ObservationTypeEnum.WILDLIFE){
					WildlifeObservation wildlifeConditionObservation = new WildlifeObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM wildlife_observations where wildlife_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){
							
							WildlifeObservationTypeEnum wildlifeObservationType = WildlifeObservationTypeEnum.valueOf(rs1.getString("wildlife_observation_type"));
							SpeciesEnum species = SpeciesEnum.valueOf(rs1.getString("species"));
							String speciesType = rs1.getString("species_type");
							
							if(wildlifeObservationType == WildlifeObservationTypeEnum.DIRECT){
								if(species == SpeciesEnum.FLORA){
									FloralDirectWildlifeObservation floralDirectWildlifeObservation = new FloralDirectWildlifeObservation();

									floralDirectWildlifeObservation.setDiameter(rs1.getInt("diameter"));
									floralDirectWildlifeObservation.setNoOfTrees(rs1.getInt("no_of_trees"));
									floralDirectWildlifeObservation.setObservedThrougGathering(rs1.getBoolean("observed_through_gathering"));
									floralDirectWildlifeObservation.setOtherTreeSpeciedObserved(rs1.getString("other_tree_species_observed"));
									
									floralDirectWildlifeObservation.setSpecies(species);
									floralDirectWildlifeObservation.setSpeciesType(speciesType);
									floralDirectWildlifeObservation.setPatrolId(patrolId);
									floralDirectWildlifeObservation.setId(observationId);
									floralDirectWildlifeObservation.setStartDate(startDate);
									floralDirectWildlifeObservation.setEndDate(endDate);
									floralDirectWildlifeObservation.setObservationType(observationType);
									floralDirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
									floralDirectWildlifeObservation.setObsId(obsId);
									//observations.add(floralDirectWildlifeObservation);
									return floralDirectWildlifeObservation;
									
									
								}else{
									AnimalDirectWildlifeObservation animalDirectWildlifeObservation = new AnimalDirectWildlifeObservation();
									
									animalDirectWildlifeObservation.setActionTaken(ActionTakenEnum.valueOf(rs1.getString("action_taken")));
									animalDirectWildlifeObservation.setNoOfFemaleAdults(rs1.getInt("no_of_female_adults"));
									animalDirectWildlifeObservation.setNoOfMaleAdults(rs1.getInt("no_of_male_adults"));
									animalDirectWildlifeObservation.setNoOfUndetermined(rs1.getInt("no_of_undetermined"));
									animalDirectWildlifeObservation.setNoOfYoung(rs1.getInt("no_of_young"));
									animalDirectWildlifeObservation.setObservedThroughHunting(rs1.getBoolean("observed_through_hunting"));
									
									animalDirectWildlifeObservation.setSpecies(species);
									animalDirectWildlifeObservation.setSpeciesType(speciesType);
									animalDirectWildlifeObservation.setPatrolId(patrolId);
									animalDirectWildlifeObservation.setId(observationId);
									animalDirectWildlifeObservation.setStartDate(startDate);
									animalDirectWildlifeObservation.setEndDate(endDate);
									animalDirectWildlifeObservation.setObservationType(observationType);
									animalDirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
									animalDirectWildlifeObservation.setObsId(obsId);
									//observations.add(animalDirectWildlifeObservation);
									return animalDirectWildlifeObservation;
								}
								
							}
							else{
									IndirectWildlifeObservation indirectWildlifeObservation = new IndirectWildlifeObservation();
									
									indirectWildlifeObservation.setEvidences(rs1.getString("evidences"));
									
									indirectWildlifeObservation.setSpecies(species);
									indirectWildlifeObservation.setSpeciesType(speciesType);
									indirectWildlifeObservation.setId(observationId);
									indirectWildlifeObservation.setPatrolId(patrolId);
									indirectWildlifeObservation.setStartDate(startDate);
									indirectWildlifeObservation.setEndDate(endDate);
									indirectWildlifeObservation.setObservationType(observationType);
									indirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
									indirectWildlifeObservation.setObsId(obsId);
									//observations.add(indirectWildlifeObservation);
									return indirectWildlifeObservation;
								
							}
							//wildlifeConditionObservation.setWildlifeObservationType();
							
							
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				else if(observationType == ObservationTypeEnum.THREATS){
					ThreatObservation threatObservation = new ThreatObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM threat_observations where threat_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){
					
							
							threatObservation.setThreatType(rs1.getString("threat_type"));
							threatObservation.setDistanceOfThreatFromWaypoint(rs1.getInt("distance_of_threat_from_waypoint"));
							threatObservation.setBearingOfThreatFromWaypoint(rs1.getInt("bearing_of_threat_from_waypoint"));
							threatObservation.setResponseToThreat(ResponseToThreatEnum.valueOf(rs1.getString("response_to_threat")));
							threatObservation.setNote(rs1.getString("note"));
							
							threatObservation.setId(observationId);
							threatObservation.setPatrolId(patrolId);
							threatObservation.setStartDate(startDate);
							threatObservation.setEndDate(endDate);
							threatObservation.setObservationType(observationType);
							threatObservation.setObsId(obsId);
							//observation =  forestConditionObservation;
							//observations.add(threatObservation);
							return threatObservation;
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
				}
				
				else if(observationType == ObservationTypeEnum.OTHER_OBSERVATIONS){
					OtherObservation otherObservation = new OtherObservation() ;
					
					try{
						Statement stmt1 = connection.createStatement();
						String query1 = "SELECT * FROM other_observations where other_observation_id = " + observationId;
						
						ResultSet rs1 = stmt1.executeQuery(query1);
						
						while(rs1.next()){	
							otherObservation.setNote(rs1.getString("note"));
							
							otherObservation.setId(observationId);
							otherObservation.setPatrolId(patrolId);
							otherObservation.setStartDate(startDate);
							otherObservation.setEndDate(endDate);
							otherObservation.setObservationType(observationType);
							otherObservation.setObsId(obsId);
							//observations.add(otherObservation);
							return otherObservation;
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				
			
				
				
				
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//return observations;
		
		
		
		return null;
	}

	

	@Override
	public List<Reports> getReports(String observation, String type) {
		List<Reports> reports = new ArrayList<Reports>();
		try {
			
			Statement stmt = connection.createStatement();
			
			String query = "SELECT " + type + " as label, COUNT(*) as value " +
							" FROM " + observation +
							" GROUP BY " + type;
						
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				
				
				String label = rs.getString("label");
				int value = rs.getInt("value");
				
				Reports report = new Reports();
				report.setLabel(label);
				report.setValue(value);
				
				reports.add(report);
			}

			return reports;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PatrolObservationImage> getImages(Long obsId) {
		List<PatrolObservationImage> images = new ArrayList<PatrolObservationImage>();
		
		try {
			
			Statement stmt = connection.createStatement();
			
			String query = "SELECT patrol_observation_image_id,latitude,longitude,date,"
					//+ "REPLACE(image_location,'\\','/\') as image_location"
					+ "image_location"
					+ " FROM patrol_observation_images where observation_id = " + obsId ;
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				
				PatrolObservationImage image = new PatrolObservationImage();
				
				Long id = rs.getLong("patrol_observation_image_id");
				String imageLocation = rs.getString("image_location");
				String latitude = rs.getString("latitude");
				String longitude = rs.getString("longitude");
				Date date = rs.getDate("date");
						
				image.setId(id);
				image.setObservationId(obsId);
				image.setImageLocation(imageLocation);
				image.setLatitude(latitude);
				image.setLongitude(longitude);
				image.setTimestamp(date);
				
				
				images.add(image);
			}

			return images;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public PatrolObservationImage getImage(Long id) {
			try {
			
			Statement stmt = connection.createStatement();
			
			String query = "SELECT patrol_observation_image_id,latitude,longitude,date,observation_id,"
					//+ "REPLACE(image_location,'\\','/\') as image_location"
					+ "image_location"
					+ " FROM patrol_observation_images where patrol_observation_image_id = " + id ;
			
				
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				
				PatrolObservationImage image = new PatrolObservationImage();
				
				//Long id = rs.getLong("patrol_observation_image_id");
				long obsId = rs.getLong("observation_id");
				String imageLocation = rs.getString("image_location");
				String latitude = rs.getString("latitude");
				String longitude = rs.getString("longitude");
				Date date = rs.getDate("date");
						
				image.setId(id);
				image.setObservationId(obsId);
				image.setImageLocation(imageLocation);
				image.setLatitude(latitude);
				image.setLongitude(longitude);
				image.setTimestamp(date);
				
				return image;
				
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



	
}
