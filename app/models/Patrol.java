package models;

import java.util.Date;

public class Patrol {
	
	private long id;
	private String patrolName;
	private String patrollerName;
	private String patrolStatus;
	private Date startDate;
	private Date endDate;
	
	
	public String getPatrollerName() {
		return patrollerName;
	}
	public void setPatrollerName(String patrollerName) {
		this.patrollerName = patrollerName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPatrolName() {
		return patrolName;
	}
	public void setPatrolName(String patrolName) {
		this.patrolName = patrolName;
	}
	public String getPatrolStatus() {
		return patrolStatus;
	}
	public void setPatrolStatus(String patrolStatus) {
		this.patrolStatus = patrolStatus;
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
	/*
	public Patrol(long id, String patrolName, String patrolStatus,
			String startDate, String endDate) {
		super();
		this.id = id;
		this.patrolName = patrolName;
		this.patrolStatus = patrolStatus;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	*/
	

}
