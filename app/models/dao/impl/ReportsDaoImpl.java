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
import models.Reports;
import models.dao.ReportsDao;

public class ReportsDaoImpl implements ReportsDao {

	Connection connection;
	@Inject
	public ReportsDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}
	
	
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
	

}
