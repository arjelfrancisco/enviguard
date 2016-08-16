package models.dao.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;

import models.PatrolObservationImage;
import models.dao.ImageDao;
import play.Configuration;
import utils.CyberTrackerUtilities;
import controllers.DatabaseConnection.DatabaseUtilities;

@Singleton
public class ImageDaoImpl implements ImageDao {

	private Connection connection;
	
	private static String IMAGE_DEFAULT_DIRECTORY; 
	private static final String IMAGE_PREFIX = "obsimg";
	
	@Inject
	public ImageDaoImpl(DatabaseUtilities databaseUtilities, Configuration configuration) {
		IMAGE_DEFAULT_DIRECTORY = configuration.getString("image.base.directory");		
		connection = databaseUtilities.getConnection();
		System.out.println("Image Directory: " + IMAGE_DEFAULT_DIRECTORY);
	}

	@Override
	public Long addImage(PatrolObservationImage patrolObservationImage) {
		try {
			String imageName = IMAGE_PREFIX + System.currentTimeMillis() + ".jpg"; 
			String imageLocation = IMAGE_DEFAULT_DIRECTORY + "\\" + imageName;
			
			BufferedImage image = ImageIO.read(patrolObservationImage.getImage());
			ImageIO.write(image, "jpg", new File(imageLocation));
			
			System.out.println("Written to File Location: " + imageLocation);
			
			String insertSql = "INSERT INTO patrol_observation_images (observation_id, image_location, longitude, latitude, date) VALUES(?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, patrolObservationImage.getObservationId());
			stmt.setString(2, imageLocation);
			stmt.setString(3, patrolObservationImage.getLongitude());
			stmt.setString(4, patrolObservationImage.getLatitude());
			stmt.setTimestamp(5, CyberTrackerUtilities.convertToTimestampDate(patrolObservationImage.getTimestamp()));
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			
			return rs.getLong(1);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
