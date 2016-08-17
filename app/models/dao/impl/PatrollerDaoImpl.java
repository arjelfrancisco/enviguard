package models.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Patroller;
import models.dao.PatrollerDao;
import controllers.DatabaseConnection.DatabaseUtilities;

@Singleton
public class PatrollerDaoImpl implements PatrollerDao {

	Connection connection;
	@Inject
	public PatrollerDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}
	
	
	@Override
	public List<Patroller> getPatrollersByPatrolId(int id) {
		List<Patroller> patrollers = new ArrayList<Patroller>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT patrollers.patroller_id, patrollers.patroller_name "
					+ "FROM patrol_patrollers "
					+ "INNER JOIN patrollers "
					+ "ON patrollers.patroller_id = patrol_patrollers.patroller_id "
					+ "WHERE patrol_patrollers.patrol_id = " + id;
				
			ResultSet rs = stmt.executeQuery(query);
			
			int patrollerId = 0;
			String patrollerName = "";
			
			
			while(rs.next()){
				
				patrollerId = rs.getInt("patroller_id");
				patrollerName = rs.getString("patroller_name");
				
				Patroller patroller = new Patroller(patrollerId,patrollerName);
				patrollers.add(patroller);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return patrollers;
	}


	@Override
	public List<Patroller> getPatrollers() {
		try {
			List<Patroller> patrollers = new ArrayList<>();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM patrollers");
			
			while(rs.next()) {
				
				int id = rs.getInt("patroller_id");
				String name = rs.getString("patroller_name");
				Patroller patroller = new Patroller(id,name);
				patrollers.add(patroller);
			}
			
			return patrollers;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public boolean addPatroller(String name) {
		try {
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO patrollers (patroller_name) VALUES ('" + name + "')");
			return true;
			
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
		
		
	}


	@Override
	public Patroller getByName(String name) {
		try {
			Patroller patroller = null;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM patrollers where patroller_name ='" + name + "'");
			
			while(rs.next()) {
				
				int id = rs.getInt("patroller_id");
				patroller = new Patroller(id,name);
				
				
				
			}
			
			return patroller;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void updatePatroller(Patroller patroller) {
		try {
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE patrollers SET patroller_name = '" + patroller.getName() +
					"'WHERE patroller_id = " + patroller.getId());
			
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}

	}


	@Override
	public boolean deletePatroller(int id) {
		try {
				
				Statement stmt = connection.createStatement();
				int rows = stmt.executeUpdate("DELETE FROM patrollers WHERE patroller_id = " + id);
			
				if(rows == 1){
					
					return true;
				}else{
					
					return false;
				}
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
		
	}
	
	

}
