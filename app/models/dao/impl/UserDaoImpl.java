package models.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.inject.Inject;

import controllers.DatabaseConnection.DatabaseUtilities;
import models.User;
import models.dao.UserDao;

public class UserDaoImpl implements UserDao {

	Connection connection;
	@Inject
	public UserDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}
	
	
	@Override
	public User checkUser(User user) {
		User thisUser = null;
		try {
			
			Statement stmt = connection.createStatement();
			
			String query = "SELECT "				 

					+ "username,"
					+ "password,"
					+ "name"
					
					+ " FROM users"

					+ " WHERE username = '" + user.getUsername() + "'"
					+ " AND password = '" + user.getPassword() + "'";
				
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()){
				thisUser = new User();
				thisUser.setName(rs.getString("name"));
				thisUser.setUsername(rs.getString("username"));
				thisUser.setPassword(rs.getString("password"));
				
				
				return thisUser;
			}
			return thisUser;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return thisUser;
		}
		
		
		
	}

	
}
