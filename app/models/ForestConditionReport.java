package models;

import java.sql.Timestamp;
import java.util.Date;

import play.data.format.Formats.DateTime;

public class ForestConditionReport {
	
	private String patrolName;
	private String forestConditionType;
	private boolean presenceOfRegenerants;
	private String densityOfRegenerants;
	private Date startDate;
	private Date endDate;
	private String accumulatedTime;
	public String getPatrolName() {
		return patrolName;
	}
	public void setPatrolName(String patrolName) {
		this.patrolName = patrolName;
	}
	public String getForestConditionType() {
		return forestConditionType;
	}
	public void setForestConditionType(String forestConditionType) {
		this.forestConditionType = forestConditionType;
	}
	public boolean getPresenceOfRegenerants() {
		return presenceOfRegenerants;
	}
	public void setPresenceOfRegenerants(boolean presenceOfRegenerants) {
		this.presenceOfRegenerants = presenceOfRegenerants;
	}
	public String getDensityOfRegenerants() {
		return densityOfRegenerants;
	}
	public void setDensityOfRegenerants(String densityOfRegenerants) {
		this.densityOfRegenerants = densityOfRegenerants;
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
	public String getAccumulatedTime() {
		return accumulatedTime;
	}
	public void setAccumulatedTime(String accumulatedTime) {
		this.accumulatedTime = accumulatedTime;
	}
	
	
	


}
