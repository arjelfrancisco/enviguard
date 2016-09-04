package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Patrol;
import models.dao.PatrolDao;
import utils.CyberTrackerUtilities;
import controllers.DatabaseConnection.DatabaseUtilities;

@Singleton
public class PatrolDaoImpl implements PatrolDao {

	Connection connection;
	@Inject
	public PatrolDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}
	
	@Override
	public Long addPatrol(Patrol patrol) {
		try {
			String insertSql = "INSERT INTO patrols (patrol_name, start_date, end_date, patroller_name) VALUES(?, ?, ?, ?)";
			
			PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, patrol.getPatrolName());
			stmt.setTimestamp(2, CyberTrackerUtilities.convertToTimestampDate(patrol.getStartDate()));
			stmt.setTimestamp(3, CyberTrackerUtilities.convertToTimestampDate(patrol.getEndDate()));
			stmt.setString(4, patrol.getPatrollerName());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Patrol getPatrolById(long id) {
		Patrol patrol = new Patrol();
		
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM patrols where patrol_id = " + id;
			
			ResultSet rs = stmt.executeQuery(query);
			
			//int id =0;
			long patrolId =0;
			String patrolName = "";
			String patrolStatus = "";
			String patrollerName = "";
			Date startDate;
			Date endDate;
			
			
			while(rs.next()){
				
				
				patrolId = rs.getInt("patrol_id");
				patrolName = rs.getString("patrol_name");
				patrollerName = rs.getString("patroller_name");
				
				startDate = rs.getDate("start_date");
				endDate = rs.getDate("end_date");
				
				//patrol = new Patrol(patrolId, patrolName, patrolStatus, startDate, endDate);
				patrol.setId(patrolId);
				patrol.setPatrolName(patrolName);
				patrol.setPatrollerName(patrollerName);
				
				//patrol.setPatrolStatus(patrolStatus);
				patrol.setStartDate(startDate);
				patrol.setEndDate(endDate);
				
			}
			
			//return routes;
			return patrol;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patrol;
	}

}
