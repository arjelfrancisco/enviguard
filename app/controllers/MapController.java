package controllers;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import models.ForestConditionObservation;
import models.Observation;
import models.PatrolLocation;
import models.Patrol;
import models.PatrolObservationImage;
import models.Patroller;
import models.dao.impl.LocationDaoImpl;
import models.dao.impl.ObservationDaoImpl;
import models.dao.impl.PatrolDaoImpl;
import models.dao.impl.PatrollerDaoImpl;
import controllers.DatabaseConnection.DatabaseUtilities;
import enums.ObservationTypeEnum;
import play.mvc.*;
import play.libs.Json;
import views.html.*;


public class MapController extends Controller {
	
	@Inject
	public LocationDaoImpl locationDao;
	
	@Inject
	public PatrolDaoImpl patrolDao;
	
	@Inject
	public PatrollerDaoImpl patrollerDao;
	
	@Inject
	public ObservationDaoImpl observationDao;
	
	 public Result index() {
		  if(session("username") == null){
		  		return ok(login.render());
		  	}
		  	else{
		      return ok(index.render("map",session("name"),session("username")));
		  
		  	}
	    	
	    }
	 
	 public Result getImageLists(Long obsId){
		 List<PatrolObservationImage> images = observationDao.getImages(obsId);
		 
		 
		 //return ok(new File("C:/cybertracker-images/obsimg1470840600252.jpg"));
		 //return ok(new File(images.get(0).getImageLocation()));
		return ok(Json.toJson(images));
	 }
	 
	 public Result showImage(long id){
		 //String file = filename.replaceAll("\\", "/");
		 
		 PatrolObservationImage image = observationDao.getImage(id);
		 
		 return ok(new File(image.getImageLocation()));
		 
	 }
	 
	 public Result getAllRoutes(){
		List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
		
		locations = locationDao.getAllRoutes();
		 
		 
		 return ok(Json.toJson(locations));
		 
		 
	 }
	 
	 public Result getDistinctPatrols(){
		 List<Integer> lstPatrolIds = new ArrayList<Integer>();
		 
		 
		 lstPatrolIds = locationDao.distinctPatrols();
		 
		 return ok(Json.toJson(lstPatrolIds));
	 }
	 
	 public Result getDistinctRegions(){
		 List<String> regions = new ArrayList<String>();
		 
		 
		 regions = locationDao.getDistinctRegions();
		 
		 return ok(Json.toJson(regions));
	 }
	 
	 
	 public Result getRoutesById(long id){
		
		 List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
			
		 locations = locationDao.getRoutesById(id);
			 
			 
		 return ok(Json.toJson(locations));
	 }
	 
	 public Result getRoutesByPatrollerName(String name){
		 
		 List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
			
		 locations = locationDao.getRoutesByPatrollerName(name);
			 
			 
		 return ok(Json.toJson(locations));
	 }
	 
	 public Result getRoutesByPatrolName(String name){
		 
		 List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
			
		 locations = locationDao.getRoutesByPatrolName(name);
			 
			 
		 return ok(Json.toJson(locations));
	 }
	 
	 public Result getRoutesByDate(String start, String end){
		
		 List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
			
		 locations = locationDao.getRoutesByDate(start,end);
			 
			 
		 return ok(Json.toJson(locations));
	 
	 }
	 
	 public Result getRoutesByStatus(String status){
			
		 List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
			
		 locations = locationDao.getRoutesByStatus(status);
			 
			 
		 return ok(Json.toJson(locations));
	 
	 }
	 
	 public Result getRoutesByRegion(String region, String city, String street){
		 
		 List<PatrolLocation> locations = new ArrayList<PatrolLocation>();
			
		 locations = locationDao.getRoutesByRegion(region, city, street);
			 
			 
		 return ok(Json.toJson(locations));
	 }
	 
	 public Result getPatrolById(int id){
		 
		 Patrol patrol = patrolDao.getPatrolById(id);
		 			 
		 List<PatrolLocation> locations = locationDao.getRoutesById((long) id);
		 
		 double totalDistance = 0;
		 for(int i=0; i<locations.size()-1; i++){
			 
			 totalDistance += distance(locations.get(i).getLat(), locations.get(i).getLng(),locations.get(i+1).getLat(), locations.get(i+1).getLng(), "M");
		 }
		 //totalDistance = totalDistance * 1000;
		 
		 DecimalFormat twoDec = new DecimalFormat("#.00");
		 
		 double totalDistanceD = Double.valueOf(twoDec.format(totalDistance));
		 
		 patrol.setDistance(totalDistanceD);
		 return ok(Json.toJson(patrol));
	 
	 }
	 
	 //for distance
	 private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
			double theta = lon1 - lon2;
			double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
			dist = Math.acos(dist);
			dist = rad2deg(dist);
			
			dist = dist * 60 * 1.1515;
			if (unit == "K") {
				dist = dist * 1.609344;
			} else if (unit == "N") {
				dist = dist * 0.8684;
			}

			return (dist);
	}
	
	 private static double deg2rad(double deg) {
			return (deg * Math.PI / 180.0);
	}
	 
	 private static double rad2deg(double rad) {
			return (rad * 180 / Math.PI);
	}
	 //for distance
	 
	 public Result getPatrollersByPatrolId(int id){
		 
		 List<Patroller> patrollers = new ArrayList<Patroller>();
			
		 patrollers = patrollerDao.getPatrollersByPatrolId(id);
			 
			 
		 return ok(Json.toJson(patrollers));
	 
	 }
	 
	 public Result getPatrols(){
		 
		 return ok(Json.toJson(patrolDao.getPatrols()));
	 }
	 
	 public Result getCity(String region){
		 
		 
		 return ok(Json.toJson(locationDao.getCity(region)));
	 }
	
	 public Result getStreet(String region, String city){
		 return ok(Json.toJson(locationDao.getStreet(region, city)));
	 }
	 
	 public Result getObservationsByPatrolId(long patrolId){
		 List<Observation> observations = new ArrayList<Observation>();
		 
		 observations = observationDao.getObservationByPatrolId(patrolId);
		 
		 
		 /*
		 for(int i=0; i<observations.size(); i++){
			 Observation observation = observations.get(i);
			 //ObservationTypeEnum observationType = ObservationTypeEnum.valueOf(observation.getObservationType());
			 
			 
			 
			 if(observation.getObservationType() == ObservationTypeEnum.FOREST_CONDITION){
				 List<ForestConditionObservation> forestConditionObservations = new ArrayList<ForestConditionObservation>();
				 ForestConditionObservation forestConditionObservation = (ForestConditionObservation) observations.get(i);
				 
				 
				 return ok(Json.toJson(forestConditionObservation));
			 }
			 
		 }
		 */
		 
		 return ok(Json.toJson(observations));
	 }
	 
	 public Result getObservationByObsId(long obsId){
		 Observation observation = new Observation();
		 observation = observationDao.getObservationByObsId(obsId);
		 
		 
		 return ok(Json.toJson(observation));
	 
	 }


	 public Result getImage(String filePath){
		 File file = new File(filePath);
		 return ok(file,true);
	 }

}