package models;

import java.util.Date;

import enums.ObservationTypeEnum;

public class Observation {
	
	private Long id;
    private Long patrolId;
    private ObservationTypeEnum observationType;
    private Date startDate;
    private Date endDate;
    private Long obsId;
    
    public Long getObsId() {
		return obsId;
	}
	public void setObsId(Long obsId) {
		this.obsId = obsId;
	}
	// List of Images
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
	public ObservationTypeEnum getObservationType() {
		return observationType;
	}
	public void setObservationType(ObservationTypeEnum observationType) {
		this.observationType = observationType;
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
	
	
    
	
    

}
