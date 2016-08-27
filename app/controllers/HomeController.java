package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import models.PatrolLocation;
import models.User;
import models.dao.impl.LocationDaoImpl;
import models.dao.impl.UserDaoImpl;
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
	public UserDaoImpl userDao;
	 
	
    public Result index() {
    	
    	
    	
    	if(session("username") == null){
    		return ok(login.render());
    	}
    	else{
        return ok(index.render("home",session("name"),session("username")));
    
    	}
    }
    
    public Result logIn(){
	
		JsonNode user = request().body().asJson();
		String username = user.get("username").asText();
		String password = user.get("password").asText();
		 
		User thisUser = new User();
		thisUser.setUsername(username);
	 	thisUser.setPassword(password);
	 
	 	//User usr = userDao.checkUser(thisUser);
	 	
	 	if(userDao.checkUser(thisUser) != null){
	 		User usr = userDao.checkUser(thisUser);
	 		
	 		session("username",usr.getUsername());
	 		session("name",usr.getName());
	 		
	 		return ok(index.render("home",session("name"),session("username")));
	 		//return ok(session("name"));
	 	}
	 	return badRequest("Invalid Username/Password");
    	
    }
    
    public Result logOut(){
    	session().clear();
    	
    	
    	return ok(login.render());
    }
    
    
    
   
}
