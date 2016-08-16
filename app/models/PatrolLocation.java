package models;

import java.util.Date;

import play.data.format.Formats.DateTime;

public class PatrolLocation {
	
	//patrol locations
	
	private Long id;
	private Long patrolId;
	private double lat;
	private double lng; 
	private Date timestamp;
	private String region;
	
	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPatrolId() {
		return patrolId;
	}
	public void setPatrolId(Long patrolId) {
		this.patrolId = patrolId;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "PatrolLocation [id=" + id + ", patrolId=" + patrolId + ", lat="
				+ lat + ", lng=" + lng + ", timestamp=" + timestamp
				+ ", region=" + region + "]";
	}
	
	/*
	public PatrolLocation(int id, int patrolId, double lat, double lng, String timestamp) {
		super();
		this.id = id;
		this.patrolId = patrolId;
		this.lat = lat;
		this.lng = lng;
		this.timestamp = timestamp;
	}
	*/

}
