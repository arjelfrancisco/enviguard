package controllers.DatabaseConnection;

import java.sql.Connection;

import javax.inject.Inject;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Controller;

public class DBConnnection extends Controller {

	@Inject
	private DatabaseUtilities databaseUtilities;
	
	public Result testConnection(){
		Connection connection = databaseUtilities.getConnection();
		if(connection != null){	
			return ok("connected");
		}else{
			return badRequest("No connection");
		}
	}
	
}
