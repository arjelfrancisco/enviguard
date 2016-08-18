package models;

import enums.ObservationTypeEnum;

public class SendPatrolResponse {

    private Long webPatrolObsId;
    private Long observationId;
    private ObservationTypeEnum observationType;
    
    public SendPatrolResponse() {
    	
    }
    
    public SendPatrolResponse(Long webPatrolObsId, Long observationId,
			ObservationTypeEnum observationType) {
		super();
		this.webPatrolObsId = webPatrolObsId;
		this.observationId = observationId;
		this.observationType = observationType;
	}

	public Long getWebPatrolObsId() {
        return webPatrolObsId;
    }

    public void setWebPatrolObsId(Long webPatrolObsId) {
        this.webPatrolObsId = webPatrolObsId;
    }

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
    }

    public ObservationTypeEnum getObservationType() {
        return observationType;
    }

    public void setObservationType(ObservationTypeEnum observationType) {
        this.observationType = observationType;
    }
}