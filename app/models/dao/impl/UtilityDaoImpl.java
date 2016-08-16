package models.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.inject.Singleton;

import controllers.DatabaseConnection.DatabaseUtilities;
import models.dao.UtilityDao;

@Singleton
public class UtilityDaoImpl implements UtilityDao {

private Connection connection;
	
	@Inject
	public UtilityDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}
	
	@Override
	public void clearPatrolData() {
		deleteTableData("forest_condition_observations");
		deleteTableData("other_observations");
		deleteTableData("patrol_locations");
		deleteTableData("patrol_observation_images");
		deleteTableData("patrol_observations");
		deleteTableData("patrol_patrollers");
		deleteTableData("patrols");
		deleteTableData("threat_observations");
		deleteTableData("wildlife_observations");		
	}
	
	private void deleteTableData(String tableName) {
		try {
			Statement stmt = connection.createStatement();
			String sql = "DELETE FROM " + tableName;
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
