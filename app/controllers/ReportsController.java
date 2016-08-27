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

import com.fasterxml.jackson.databind.JsonNode;

import models.ForestConditionObservation;
import models.ForestConditionReport;
import models.Observation;
import models.PatrolLocation;
import models.Patrol;
import models.Patroller;
import models.Reports;
import models.ReportsParameter;
import models.ThreatReport;
import models.dao.impl.LocationDaoImpl;
import models.dao.impl.ObservationDaoImpl;
import models.dao.impl.PatrolDaoImpl;
import models.dao.impl.PatrollerDaoImpl;
import models.dao.impl.ReportsDaoImpl;
import models.dao.impl.ThreatReportDaoImpl;
import controllers.DatabaseConnection.DatabaseUtilities;
import enums.ObservationTypeEnum;
import play.mvc.*;
import play.libs.Json;
import views.html.*;


public class ReportsController extends Controller {
	

	@Inject
	public ObservationDaoImpl observationDao;
	
	@Inject
	public ReportsDaoImpl reportsDao;
	
	@Inject
	public ThreatReportDaoImpl threatReportDao;
	
	 public Result index() {
		  if(session("username") == null){
		  		return ok(login.render());
		  	}
		  	else{
		      return ok(index.render("report",session("name"),session("username")));
		  
		  	}
	    }
	
	public Result getReports(String observation, String type){
		
		List<Reports> reports = observationDao.getReports(observation, type);
		
		return ok(Json.toJson(reports));
	}
	
	public Result getForestConditionReports(){
		try{
		JsonNode jsonRequest = request().body().asJson();
		ReportsParameter reportsParameter = new ReportsParameter();
		
		String filterType = jsonRequest.get("filterType").asText();
		reportsParameter.setFilterType(filterType);
		
		
		if(filterType == "region"){
			
			String region = jsonRequest.get("region").asText();
			reportsParameter.setRegions(region);
			
		}
		else if(filterType == "date"){
			String startDate = jsonRequest.get("startDate").asText();
			String endDate = jsonRequest.get("endDate").asText();
			reportsParameter.setStartDate(startDate);
			reportsParameter.setEndDate(endDate);

		}
		else{
			//view all
			
		}
		
		List<ForestConditionReport> forestConditionReports = reportsDao.getForestConditionTableReports(reportsParameter);
		
		return ok(Json.toJson(forestConditionReports));
		}catch(Exception ex){
			
			return badRequest(""+ex);
		}
	}
	
	public Result getThreatReport(){
		try{
		JsonNode jsonRequest = request().body().asJson();
		ReportsParameter reportsParameter = new ReportsParameter();
		
		String filterType = jsonRequest.get("filterType").asText();
		reportsParameter.setFilterType(filterType);
		
		
		if(filterType == "region"){
			
			String region = jsonRequest.get("region").asText();
			reportsParameter.setRegions(region);
			
		}
		else if(filterType == "date"){
			String startDate = jsonRequest.get("startDate").asText();
			String endDate = jsonRequest.get("endDate").asText();
			reportsParameter.setStartDate(startDate);
			reportsParameter.setEndDate(endDate);

		}
		else{
			//view all
			
		}
		
		List<ThreatReport> forestConditionReports = reportsDao.getThreatReport(reportsParameter);
		
		return ok(Json.toJson(forestConditionReports));
		}catch(Exception ex){
			
			return badRequest(""+ex);
		}
	}
	
	public Result getThreatReports(){
		
		try{
			JsonNode jsonRequest = request().body().asJson();
			ReportsParameter reportsParameter = new ReportsParameter();
			
			String filterType = jsonRequest.get("filterType").asText();
			reportsParameter.setFilterType(filterType);
			
			
			if(filterType.equals("region")){
				
				String region = jsonRequest.get("region").asText();
				reportsParameter.setRegions(region);
				
			}
			else if(filterType == "date"){
				String startDate = jsonRequest.get("startDate").asText();
				String endDate = jsonRequest.get("endDate").asText();
				reportsParameter.setStartDate(startDate);
				reportsParameter.setEndDate(endDate);

			}
			else{
				//view all
				
			}
			
			List<Reports> forestConditionReports = reportsDao.getThreatReports(reportsParameter);
			
			
			
			
			return ok(Json.toJson(forestConditionReports));
			}catch(Exception ex){
				
				return badRequest(""+ex);
			}
	}
	
	
	public Result getThreat(){
		return ok(Json.toJson(reportsDao.getThreatDatatable()));
	}
	
	public Result getWildlifeDatatable(){
		
		
		return ok(Json.toJson(reportsDao.getWildlifeDatatable()));
	}
	
	public Result getForestConditionDatatable(){
		
		return ok(Json.toJson(reportsDao.getForestConditionDatatable()));
	}
	
}

