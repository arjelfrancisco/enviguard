package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.PatrolLocation;
import models.dao.impl.LocationDaoImpl;
import controllers.DatabaseConnection.DatabaseUtilities;
import play.mvc.*;
import play.libs.Json;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	
	@Inject
	public LocationDaoImpl locationDao;
	 
	
    public Result index() {
    	
        return ok(index.render("map"));
    }
    
    @Inject
	private DatabaseUtilities databaseUtilities;
    
   
    
	//PlayerDAO myPlayerDAO = new PlayerDAOImpl();
	
	public Result getAllStudent() {
		
		List<String> lst = new ArrayList<String>();
		
		Connection connection = databaseUtilities.getConnection();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM patrol_locations";
			
			ResultSet rs = stmt.executeQuery(query);
			
			int id =0;
			int patrolId =0;
			double lat =0;
			double lng =0;
			
			
			while(rs.next()){
				id = rs.getInt("id");
				patrolId = rs.getInt("patrol_id");
				lat = rs.getDouble("latitude");
				lng = rs.getDouble("longtitude");
				//String timestamp = rs.getString("timestamp");
				
				
				//lst.add(timestamp);
			}
			
			return ok(""+id + patrolId + lat + lng);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return badRequest("There are no Students in the Database");
    }

}
