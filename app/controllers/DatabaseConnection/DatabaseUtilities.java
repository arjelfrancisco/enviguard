package controllers.DatabaseConnection;

import java.sql.Connection;

import javax.inject.Inject;

import play.db.Database;
import play.mvc.Controller;

public class DatabaseUtilities extends Controller {

	private Connection connection;
	
    @Inject
    public DatabaseUtilities(Database db) {
        this.connection = db.getConnection();
    }

    public Connection getConnection() {
    	return this.connection;
    }
	
}
