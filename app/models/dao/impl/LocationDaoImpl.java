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

import models.PatrolLocation;
import models.dao.LocationDao;
import play.data.format.Formats.DateTime;
import play.libs.Json;
import utils.CyberTrackerUtilities;
import controllers.DatabaseConnection.DatabaseUtilities;

@Singleton
public class LocationDaoImpl implements LocationDao {

	List<PatrolLocation> locations = new ArrayList<PatrolLocation>();

	Connection connection;
	@Inject
	public LocationDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}

	
	@Override
	public Long addLocation(PatrolLocation patrolLocation) {
		try {
			String insertSql = "INSERT INTO patrol_locations (patrol_id, latitude, longitude, date, region) VALUES(?, ?, ?, ?,?)";
			PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, patrolLocation.getPatrolId());
			stmt.setDouble(2, patrolLocation.getLat());
			stmt.setDouble(3, patrolLocation.getLng());
			stmt.setTimestamp(4, CyberTrackerUtilities.convertToTimestampDate(patrolLocation.getTimestamp()));
			stmt.setString(5, patrolLocation.getRegion());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<PatrolLocation> getAllRoutes() {
		locations = new ArrayList<PatrolLocation>();
		
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM patrol_locations";
			
			ResultSet rs = stmt.executeQuery(query);
			
	
			while(rs.next()){
				
				Long id = rs.getLong("patrol_location_id");
				Long patrolId = rs.getLong("patrol_id");
				double lat = rs.getDouble("latitude");
				double lng = rs.getDouble("longitude");
				Date timestamp = rs.getDate("date");
				String region = rs.getString("region");
				
				PatrolLocation pLoc = new PatrolLocation();
				pLoc.setId(id);
				pLoc.setPatrolId(patrolId);
				pLoc.setLat(lat);
				pLoc.setLng(lng);
				pLoc.setTimestamp(timestamp);
				pLoc.setRegion(region);
				
				locations.add(pLoc);
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		return locations;
		
	}



	@Override
	public List<PatrolLocation> getRoutesById(long patrolId) {
		List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM patrol_locations where patrol_id = " + patrolId;
			
			ResultSet rs = stmt.executeQuery(query);
			
			
			
			
			while(rs.next()){
				
				Long id = rs.getLong("patrol_location_id");				
				//Long patrolId = rs.getLong("patrol_id");
				double lat = rs.getDouble("latitude");
				double lng = rs.getDouble("longitude");
				Date timestamp = rs.getDate("date");
				String region = rs.getString("region");
				
				PatrolLocation pLoc = new PatrolLocation();
				pLoc.setId(id);
				pLoc.setPatrolId(patrolId);
				pLoc.setLat(lat);
				pLoc.setLng(lng);
				pLoc.setTimestamp(timestamp);
				pLoc.setRegion(region);
				
				locations.add(pLoc);
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}



	@Override
	public List<Integer> distinctPatrols() {
		List<Integer> lstIds = new ArrayList<Integer>();
		int id = -1;
		try {
			Statement stmt = connection.createStatement();
			String query = "select distinct patrol_id from cybertrackerdb.patrol_locations";
			
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				
				id = rs.getInt("patrol_id");
				lstIds.add(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lstIds;
	}



	@Override
	public List<PatrolLocation> getRoutesByPatrollerName(String name) {
		
		List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT patrol_locations.patrol_location_id, "
					+ "patrol_locations.patrol_id, patrol_locations.latitude, "
					+ "patrol_locations.longitude, patrol_locations.date, patrol_locations.region "
					+ "FROM patrol_locations "
					+ "INNER JOIN patrol_patrollers "
					+ "ON patrol_patrollers.patrol_id =  patrol_locations.patrol_id "
					+ "INNER JOIN patrollers "
					+ "ON patrollers.patroller_id = patrol_patrollers.patroller_id "
					+ "WHERE patrollers.patroller_name = '" + name +"'";
				
			ResultSet rs = stmt.executeQuery(query);
			
			
			
			while(rs.next()){
				
				Long id = rs.getLong("patrol_location_id");
				Long patrolId = rs.getLong("patrol_id");
				double lat = rs.getDouble("latitude");
				double lng = rs.getDouble("longitude");
				Date timestamp = rs.getDate("date");

				String region = rs.getString("region");
				
				PatrolLocation pLoc = new PatrolLocation();
				pLoc.setId(id);
				pLoc.setPatrolId(patrolId);
				pLoc.setLat(lat);
				pLoc.setLng(lng);
				pLoc.setTimestamp(timestamp);
				pLoc.setRegion(region);
				locations.add(pLoc);
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}



	@Override
	public List<PatrolLocation> getRoutesByDate(String start, String end) {
			
		List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT pLoc.patrol_location_id, "
					+ "pLoc.patrol_id, pLoc.latitude, "
					+ "pLoc.longitude, pLoc.date, pLoc.region "
					+ "FROM patrol_locations pLoc "
					+ "INNER JOIN patrols patrols "
					+ "on pLoc.patrol_id = patrols.patrol_id "
					+ "where patrols.start_date between '" + start + "' and '" + end + "'" 
					+ "and patrols.end_date between '" + start + "' and '" + end + "'" ;
				
			ResultSet rs = stmt.executeQuery(query);
			 
			
			
			while(rs.next()){
				
				Long id = rs.getLong("patrol_location_id");
				Long patrolId = rs.getLong("patrol_id");
				double lat = rs.getDouble("latitude");
				double lng = rs.getDouble("longitude");
				Date timestamp = rs.getDate("date");

				String region = rs.getString("region");
				
				PatrolLocation pLoc = new PatrolLocation();
				pLoc.setId(id);
				pLoc.setPatrolId(patrolId);
				pLoc.setLat(lat);
				pLoc.setLng(lng);
				pLoc.setTimestamp(timestamp);
				pLoc.setRegion(region);
				locations.add(pLoc);
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}



	@Override
	public List<PatrolLocation> getRoutesByStatus(String status) {
		List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT patrol_locations.patrol_location_id, "
					+ "patrol_locations.patrol_id, patrol_locations.latitude, "
					+ "patrol_locations.longitude, patrol_locations.date "
					+ "FROM patrol_locations "
					+ "INNER JOIN patrols "
					+ "ON patrol_locations.patrol_id = patrols.patrol_id "
					+ "where patrols.patrol_status = '" + status + "' ";
				
			ResultSet rs = stmt.executeQuery(query);
			
			
			
			
			while(rs.next()){
				
				Long id = rs.getLong("patrol_location_id");
				Long patrolId = rs.getLong("patrol_id");
				double lat = rs.getDouble("latitude");
				double lng = rs.getDouble("longitude");
				Date timestamp = rs.getDate("date");

				String region = rs.getString("region");
				
				PatrolLocation pLoc = new PatrolLocation();
				pLoc.setId(id);
				pLoc.setPatrolId(patrolId);
				pLoc.setLat(lat);
				pLoc.setLng(lng);
				pLoc.setTimestamp(timestamp);
				pLoc.setRegion(region);
				locations.add(pLoc);
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}


	@Override
	public List<String> getDistinctRegions() {
		List<String> regions = new ArrayList<String>();
		String region = "";
		try {
			Statement stmt = connection.createStatement();
			String query = "select distinct region from cybertrackerdb.patrol_locations";
			
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				
				region = rs.getString("region");
				regions.add(region);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return regions;
	}


	@Override
	public List<PatrolLocation> getRoutesByRegion(String region) {
		locations = new ArrayList<PatrolLocation>();
		
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM patrol_locations where region = '" + region + "'";
			
			ResultSet rs = stmt.executeQuery(query);
			
	
			while(rs.next()){
				
				Long id = rs.getLong("patrol_location_id");
				Long patrolId = rs.getLong("patrol_id");
				double lat = rs.getDouble("latitude");
				double lng = rs.getDouble("longitude");
				Date timestamp = rs.getDate("date");
				//String region = rs.getString("region");
				
				PatrolLocation pLoc = new PatrolLocation();
				pLoc.setId(id);
				pLoc.setPatrolId(patrolId);
				pLoc.setLat(lat);
				pLoc.setLng(lng);
				pLoc.setTimestamp(timestamp);
				pLoc.setRegion(region);
				
				locations.add(pLoc);
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		return locations;
	}


	@Override
	public List<PatrolLocation> getRoutesByPatrolName(String name) {
		
		List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT pLoc.patrol_location_id, "
					+ "pLoc.patrol_id, pLoc.latitude, "
					+ "pLoc.longitude, pLoc.date, pLoc.region "
					+ "FROM patrol_locations pLoc "
					+ "INNER JOIN patrols patrols "
					+ "on pLoc.patrol_id = patrols.patrol_id "
					+ "where patrols.patrol_name = '" + name + "'" ;
				
			ResultSet rs = stmt.executeQuery(query);
			 
			
			
			while(rs.next()){
				
				Long id = rs.getLong("patrol_location_id");
				Long patrolId = rs.getLong("patrol_id");
				double lat = rs.getDouble("latitude");
				double lng = rs.getDouble("longitude");
				Date timestamp = rs.getDate("date");

				String region = rs.getString("region");
				
				PatrolLocation pLoc = new PatrolLocation();
				pLoc.setId(id);
				pLoc.setPatrolId(patrolId);
				pLoc.setLat(lat);
				pLoc.setLng(lng);
				pLoc.setTimestamp(timestamp);
				pLoc.setRegion(region);
				locations.add(pLoc);
			}
			
			//return routes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}
	
	


}
