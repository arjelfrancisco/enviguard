package controllers;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import models.ForestConditionObservation;
import models.Observation;
import models.PatrolLocation;
import models.Patrol;
import models.Patroller;
import models.Reports;
import models.dao.impl.LocationDaoImpl;
import models.dao.impl.ObservationDaoImpl;
import models.dao.impl.PatrolDaoImpl;
import models.dao.impl.PatrollerDaoImpl;
import models.dao.impl.ReportsDaoImpl;
import controllers.DatabaseConnection.DatabaseUtilities;
import enums.ObservationTypeEnum;
import play.mvc.*;
import play.libs.Json;
import views.html.*;


public class ReportsController extends Controller {
	

	@Inject
	public ObservationDaoImpl observationDao;
	
	 public Result index() {
	        return ok(index.render("report"));
	    }
	
	public Result getReports(String observation, String type){
		
		List<Reports> reports = observationDao.getReports(observation, type);
		
		return ok(Json.toJson(reports));
	}
	
	
	
}

