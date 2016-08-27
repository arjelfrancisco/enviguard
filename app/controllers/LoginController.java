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
public class LoginController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	
	@Inject
	public LocationDaoImpl locationDao;
	 
	
    public Result index() {
    	
        return ok(index.render("home",session("name"),session("username")));
    }
    
    @Inject
	private DatabaseUtilities databaseUtilities;
    
   
    
	
}
