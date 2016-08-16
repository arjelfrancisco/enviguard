package models.dao;

import java.util.List;

import models.PatrolLocation;



public interface LocationDao {

	public List<PatrolLocation> getAllRoutes();
	public List<PatrolLocation> getRoutesById(long id);
	public List<Integer> distinctPatrols();
	public List<PatrolLocation> getRoutesByPatrollerName(String name);
	public List<PatrolLocation> getRoutesByDate(String start, String end);
	public List<PatrolLocation> getRoutesByStatus(String status);
	public Long addLocation(PatrolLocation patrolLocation);
	public List<String> getDistinctRegions();
	public List<PatrolLocation> getRoutesByRegion(String region);
	public List<PatrolLocation> getRoutesByPatrolName(String name);
	
	
}
