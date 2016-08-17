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
import models.Patroller;
import models.SpeciesType;
import models.ThreatType;
import models.dao.impl.LocationDaoImpl;
import models.dao.impl.LookupDaoImpl;
import models.dao.impl.ObservationDaoImpl;
import models.dao.impl.PatrolDaoImpl;
import models.dao.impl.PatrollerDaoImpl;
import controllers.DatabaseConnection.DatabaseUtilities;
import enums.SpeciesEnum;
import play.mvc.*;
import play.libs.Json;
import views.html.*;

public class AdminController extends Controller {
	
	@Inject
	public LocationDaoImpl locationDao;
	
	@Inject
	public PatrolDaoImpl patrolDao;
	
	@Inject
	public PatrollerDaoImpl patrollerDao;
	
	@Inject
	public ObservationDaoImpl observationDao;
	
	@Inject
	public LookupDaoImpl lookupDao;

	
  public Result index() {
    	
        return ok(index.render("admin"));
    }
	  
  public Result getPatrollers(){
	  
	  List<Patroller> patrollers = patrollerDao.getPatrollers();
	  
	  return ok(Json.toJson(patrollers));
  }
  
  public Result addPatrollers(){
	  JsonNode newPatroller = request().body().asJson();
	  String name = newPatroller.get("name").asText();
	  
	  if(patrollerDao.getByName(name) == null){
		  patrollerDao.addPatroller(name);
		  return ok("Patroller Successfully Added!");
		  
	  }
	  else{
		  
		  return badRequest("Patroller Already Exists!");
	  }
  }
  
  public Result updatePatroller(int id){
	  JsonNode patrollerData = request().body().asJson();
	  String name = patrollerData.get("name").asText();
	 
	  
	  
	  Patroller patroller = new Patroller(id, name);
	  
	  patrollerDao.updatePatroller(patroller);
	  
	  return ok();
  }
  
  public Result deletePatroller(int id){
	 
	  
	  if(patrollerDao.deletePatroller(id)){
		  
		  return ok("Patroller Successfully Deleted!");
	  }
	  else{
		  return badRequest("Patroller not found!");
	  }
	  
	  
  }
  
  public Result getThreatTypes(){
	  
	  List<ThreatType> threatType = lookupDao.getThreatTypes();
	  
	  return ok(Json.toJson(threatType));
  }
  
  public Result addThreatType(){
	  JsonNode newThreat = request().body().asJson();
	  String name = newThreat.get("name").asText();
	  
	  if(lookupDao.getThreatByName(name) == null){
		  lookupDao.addThreatType(name);
		  return ok("Threat Successfully Added!");
		  
	  }
	  else{
		  
		  return badRequest("Threat Already Exists!");
	  }
	  
  }
  
  public Result updateThreat(long id){
	  JsonNode threatData = request().body().asJson();
	  String name = threatData.get("name").asText();
	 
	  
	  
	  ThreatType threatType = new ThreatType();
	  threatType.setId(id);
	  threatType.setName(name);
	  
	  lookupDao.updateThreatType(threatType);
	  
	  return ok();
  }
  
  public Result deleteThreat(long id){
	 
	  
	  if(lookupDao.deleteThreatType(id)){
		  
		  return ok("Threat Successfully Deleted!");
	  }
	  else{
		  return badRequest("Threat cannot be deleted!");
	  }
  }
  
 public Result getSpeciesTypes(){
	  
	  List<SpeciesType> speciesType = lookupDao.getSpeciestypes();
	  
	  return ok(Json.toJson(speciesType));
  }
 
 public Result addSpeciesType(){
	  JsonNode newSpecies = request().body().asJson();
	  String name = newSpecies.get("name").asText();
	  String species = newSpecies.get("species").asText();
	  SpeciesEnum speciesEnum = SpeciesEnum.valueOf(species);
	  
	  SpeciesType speciesType = new SpeciesType();
	  speciesType.setName(name);
	  speciesType.setSpecies(speciesEnum);
	  
	  if(lookupDao.getSpeciesByName(speciesType) == null){
		  lookupDao.addSpeciesType(speciesType);
		  return ok("Species Successfully Added!");
		  
	  }
	  else{
		  
		  return badRequest("Species Already Exists!");
	  }
	  
 }
 
 public Result updateSpecies(){
	  JsonNode speciesData = request().body().asJson();
	  String name = speciesData.get("name").asText();
	  String species = speciesData.get("species").asText();
	  long id = speciesData.get("id").asLong();
	  SpeciesEnum speciesEnum = SpeciesEnum.valueOf(species.trim());
	  
	  SpeciesType speciesType = new SpeciesType();
	  speciesType.setId(id);
	  speciesType.setName(name);
	  speciesType.setSpecies(speciesEnum);
	  
	  lookupDao.updateSpeciesType(speciesType);
	  
	  return ok();
 }
 
 public Result deleteSpecies(long id){
	 
	  
	  if(lookupDao.deleteSpeciesType(id)){
		  
		  return ok("Species Successfully Deleted!");
	  }
	  else{
		  return badRequest("Species cannot be deleted!");
	  }
 }
  
  

  
	  
}
