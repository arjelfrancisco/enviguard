package models;

import java.util.Date;

public class ThreatReport {

	private String patrolName;
	private String threatType;
	private int distance;
	private int bearing;
	private String response;
	private String note;
	private Date startDate;
	private Date endDate;
	private String region;
	
	public String getPatrolName() {
		return patrolName;
	}
	public void setPatrolName(String patrolName) {
		this.patrolName = patrolName;
	}
	public String getThreatType() {
		return threatType;
	}
	public void setThreatType(String threatType) {
		this.threatType = threatType;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getBearing() {
		return bearing;
	}
	public void setBearing(int bearing) {
		this.bearing = bearing;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
