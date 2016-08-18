package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import models.AnimalDirectWildlifeObservation;
import models.FloralDirectWildlifeObservation;
import models.ForestConditionObservation;
import models.IndirectWildlifeObservation;
import models.OtherObservation;
import models.Patrol;
import models.PatrolLocation;
import models.PatrolObservationImage;
import models.SendPatrolResponse;
import models.ThreatObservation;
import models.dao.UtilityDao;
import models.dao.impl.ImageDaoImpl;
import models.dao.impl.LocationDaoImpl;
import models.dao.impl.LookupDaoImpl;
import models.dao.impl.ObservationDaoImpl;
import models.dao.impl.PatrolDaoImpl;
import models.dao.impl.PatrollerDaoImpl;
import models.dao.impl.UtilityDaoImpl;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import utils.CyberTrackerUtilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import enums.ActionTakenEnum;
import enums.DensityOfRegenerantsEnum;
import enums.ForestConditionTypeEnum;
import enums.ObservationTypeEnum;
import enums.ResponseToThreatEnum;
import enums.SpeciesEnum;
import enums.WildlifeObservationTypeEnum;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class WSController extends Controller {
	
	@Inject
	private PatrolDaoImpl patrolDao;
	
	@Inject
	private LocationDaoImpl locationDao;
	
	@Inject
	private ObservationDaoImpl observationDao;
	
	@Inject
	private ImageDaoImpl imageDao;
	
	@Inject 
	private LookupDaoImpl lookupDao;
	
	@Inject
	private PatrollerDaoImpl patrollerDao;
	
	@Inject
	private UtilityDaoImpl utilityDao;
	
    public Result index() {
        return ok("Cybertracker DB is working...");
    }
    
    public Result addObservationImage(Long observationId) {
    	try {
    		MultipartFormData<File> body = request().body().asMultipartFormData();
            FilePart<File> picture = body.getFile("image");
            File file = picture.getFile();
            
            Map<String, String[]> formBody = body.asFormUrlEncoded();
            String longitude = formBody.get("longitude")[0];
            String latitude = formBody.get("latitude")[0];
            String timestamp = formBody.get("timestamp")[0];
            
            PatrolObservationImage patrolObservationImage = new PatrolObservationImage();
            patrolObservationImage.setImage(file);
            patrolObservationImage.setLatitude(latitude);
            patrolObservationImage.setLongitude(longitude);
            patrolObservationImage.setObservationId(observationId);
            patrolObservationImage.setTimestamp(new Date(Long.valueOf(timestamp)));
            
            imageDao.addImage(patrolObservationImage);
            
            System.out.println("|ADD OBSERVATION IMAGE| Add Observation Image Successful | Observation ID: " + observationId);
            return ok("Image Added Successfully.");
    	} catch(RuntimeException e) {
    		// Log Error
    		System.out.println("|ADD OBSERVATION IMAGE| Runtime Exception: " + e);
    		return internalServerError("Error with message: " + e.getMessage());
    	}
    }
    
      public Result addPatrol() {
    	try {
    		JsonNode jsonRequest = request().body().asJson();
        	String name = jsonRequest.get("name").asText();
        	String patrollerName = jsonRequest.get("patrollerName").asText();
        	Long startDate = jsonRequest.get("startDate").asLong();
        	Long endDate = jsonRequest.get("endDate").asLong();
        	
        	Patrol patrol = new Patrol();
        	patrol.setPatrolName(name);
        	patrol.setPatrollerName(patrollerName);
        	patrol.setStartDate(new Date(startDate));
        	patrol.setEndDate(new Date(endDate));
        	Long patrolId = patrolDao.addPatrol(patrol);
        	
    		ArrayNode locationsArrNode = (ArrayNode) jsonRequest.get("locations");
    		for(JsonNode location : locationsArrNode) {
    			double longitude = location.get("longitude").asDouble();
    			double latitude = location.get("latitude").asDouble();
    			String region = location.get("region").asText();
    			Long timestamp = location.get("timestamp").asLong();
    			
    			PatrolLocation patrolLocation = new PatrolLocation();
    			patrolLocation.setPatrolId(patrolId);
    			patrolLocation.setLng(longitude);
    			patrolLocation.setLat(latitude);
    			patrolLocation.setRegion(region);
    			patrolLocation.setTimestamp(new Date(timestamp));
    			
    			locationDao.addLocation(patrolLocation);
    		}
    		
    		// Observations
    		
    		List<SendPatrolResponse> sendPatrolResponses = new ArrayList<>();
    		
    		ArrayNode observationsArrNode = (ArrayNode) jsonRequest.get("observations");
    		for(JsonNode observation : observationsArrNode) {
    			
    			Long observationId = observation.get("observationId").asLong();
    			Long webPatrolObsId = null;
    			ObservationTypeEnum observationType = ObservationTypeEnum.valueOf(observation.get("observationType").asText());
    			Date obsStartDate = CyberTrackerUtilities.retrieveDate(observation.get("startDate").asLong());
    			Date obsEndDate = CyberTrackerUtilities.retrieveDate(observation.get("endDate").asLong());
    			
    			if(observationType == ObservationTypeEnum.FOREST_CONDITION) {
    				ForestConditionObservation forestConditionObservation = new ForestConditionObservation();
    				forestConditionObservation.setPatrolId(patrolId);
    				forestConditionObservation.setObservationType(observationType);
    				forestConditionObservation.setStartDate(obsStartDate);
    				forestConditionObservation.setEndDate(obsEndDate);
    				
    				forestConditionObservation.setForestConditionType(ForestConditionTypeEnum.valueOf(observation.get("forestConditionType").asText()));
    				forestConditionObservation.setPresenceOfRegenerants(observation.get("presenceOfRegenerants").asBoolean());
    				forestConditionObservation.setDensityOfRegenerants(DensityOfRegenerantsEnum.valueOf(observation.get("densityOfRegenerants").asText()));
    				
    				webPatrolObsId = observationDao.addObservation(forestConditionObservation);
    			} else if(observationType == ObservationTypeEnum.WILDLIFE) {
    				WildlifeObservationTypeEnum wildlifeObservationType = WildlifeObservationTypeEnum.valueOf(observation.get("wildlifeObservationType").asText());
    				SpeciesEnum species = SpeciesEnum.valueOf(observation.get("species").asText());
    				String speciesType = observation.get("speciesType").asText();
    				
    				if(wildlifeObservationType == WildlifeObservationTypeEnum.DIRECT) {
    					if(species == SpeciesEnum.FLORA) {
    						FloralDirectWildlifeObservation floralDirectWildlifeObservation = new FloralDirectWildlifeObservation();
    						
    						floralDirectWildlifeObservation.setPatrolId(patrolId);
    						floralDirectWildlifeObservation.setObservationType(observationType);
    						floralDirectWildlifeObservation.setStartDate(obsStartDate);
    						floralDirectWildlifeObservation.setEndDate(obsEndDate);							
    						
    						floralDirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
    						floralDirectWildlifeObservation.setSpecies(species);
    						floralDirectWildlifeObservation.setSpeciesType(speciesType);
    						
    						floralDirectWildlifeObservation.setDiameter(observation.get("diameter").asInt());
    						floralDirectWildlifeObservation.setNoOfTrees(observation.get("noOfTrees").asInt());
    						floralDirectWildlifeObservation.setObservedThrougGathering(observation.get("observedThroughGathering").asBoolean());
    						floralDirectWildlifeObservation.setOtherTreeSpeciedObserved(observation.get("otherTreeSpeciedObserved").asText());
    						
    						webPatrolObsId = observationDao.addObservation(floralDirectWildlifeObservation);
    					} else {
    						AnimalDirectWildlifeObservation animalDirectWildlifeObservation = new AnimalDirectWildlifeObservation();
    						animalDirectWildlifeObservation.setPatrolId(patrolId);
    						animalDirectWildlifeObservation.setObservationType(observationType);
    						animalDirectWildlifeObservation.setStartDate(obsStartDate);
    						animalDirectWildlifeObservation.setEndDate(obsEndDate);	
    						
    						animalDirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
    						animalDirectWildlifeObservation.setSpecies(species);
    						animalDirectWildlifeObservation.setSpeciesType(speciesType);
    						
    						animalDirectWildlifeObservation.setNoOfMaleAdults(observation.get("noOfMaleAdults").asInt());
    						animalDirectWildlifeObservation.setNoOfFemaleAdults(observation.get("noOfFemaleAdults").asInt());
    						animalDirectWildlifeObservation.setNoOfYoung(observation.get("noOfYoung").asInt());
    						animalDirectWildlifeObservation.setNoOfUndetermined(observation.get("undetermined").asInt());
    						animalDirectWildlifeObservation.setActionTaken(ActionTakenEnum.valueOf(observation.get("actionTaken").asText()));
    						animalDirectWildlifeObservation.setObservedThroughHunting(observation.get("observedThroughHunting").asBoolean());
    						
    						webPatrolObsId = observationDao.addObservation(animalDirectWildlifeObservation);
    					}
    				} else {
    					IndirectWildlifeObservation indirectWildlifeObservation = new IndirectWildlifeObservation();
    					indirectWildlifeObservation.setPatrolId(patrolId);
    					indirectWildlifeObservation.setObservationType(observationType);
    					indirectWildlifeObservation.setStartDate(obsStartDate);
    					indirectWildlifeObservation.setEndDate(obsEndDate);	
    					
    					indirectWildlifeObservation.setWildlifeObservationType(wildlifeObservationType);
    					indirectWildlifeObservation.setSpecies(species);
    					indirectWildlifeObservation.setSpeciesType(speciesType);
    					
    					indirectWildlifeObservation.setEvidences(observation.get("evidences").asText());
    					
    					webPatrolObsId = observationDao.addObservation(indirectWildlifeObservation);
    				}					
    			} else if(observationType == ObservationTypeEnum.THREATS) {
    				ThreatObservation threatObservation = new ThreatObservation();
    				
    				threatObservation.setPatrolId(patrolId);
    				threatObservation.setObservationType(observationType);
    				threatObservation.setStartDate(obsStartDate);
    				threatObservation.setEndDate(obsEndDate);
    				
    				threatObservation.setThreatType(observation.get("threatType").asText());
    				threatObservation.setDistanceOfThreatFromWaypoint(observation.get("distanceOfThreatFromWaypoint").asInt());
    				threatObservation.setBearingOfThreatFromWaypoint(observation.get("bearingOfThreatFromWaypoint").asInt());
    				threatObservation.setResponseToThreat(ResponseToThreatEnum.valueOf(observation.get("responseToThreat").asText()));
    				threatObservation.setNote(observation.get("note").asText());
    				
    				webPatrolObsId = observationDao.addObservation(threatObservation);
    			} else {
    				OtherObservation otherObservation = new OtherObservation();
    				
    				otherObservation.setPatrolId(patrolId);
    				otherObservation.setObservationType(observationType);
    				otherObservation.setStartDate(obsStartDate);
    				otherObservation.setEndDate(obsEndDate);
    				
    				otherObservation.setNote(observation.get("note").asText());
    				
    				webPatrolObsId = observationDao.addObservation(otherObservation);
    			}
    			
    			
    			sendPatrolResponses.add(new SendPatrolResponse(webPatrolObsId, observationId, observationType));
    		}
    	
    		Logger.info("|ADD PATROL| Add Patrol Successful | Patrol Name: " + name);
    		return ok(Json.toJson(sendPatrolResponses));
    	} catch(RuntimeException e) {
    		Logger.error("|ADD PATROL| Error with Message: {}", e.getMessage(), e);
    		return internalServerError("Error with message: " + e.getMessage());
    	} 
    }
    
    public Result synchronizeMobileData() {
    	ObjectNode responseNode = Json.newObject();
    	responseNode.put("speciesTypes", Json.toJson(lookupDao.getSpeciestypes()));
    	responseNode.put("threatTypes", Json.toJson(lookupDao.getThreatTypes()));
    	responseNode.put("patrollers", Json.toJson(patrollerDao.getPatrollers()));
    	
    	return ok(responseNode);
    }
    
    public Result clearPatrolData() {
    	utilityDao.clearPatrolData();
    	return ok("Successful Clearing Patrol Data.");
    }

}
