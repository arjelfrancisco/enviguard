package models;

import enums.DensityOfRegenerantsEnum;
import enums.ForestConditionTypeEnum;



public class ForestConditionObservation extends Observation {

	private Long forestConditionObservationId;
    private ForestConditionTypeEnum forestConditionType;
    private boolean presenceOfRegenerants;
    private DensityOfRegenerantsEnum densityOfRegenerants;
	public Long getForestConditionObservationId() {
		return forestConditionObservationId;
	}
	public void setForestConditionObservationId(Long forestConditionObservationId) {
		this.forestConditionObservationId = forestConditionObservationId;
	}
	public ForestConditionTypeEnum getForestConditionType() {
		return forestConditionType;
	}
	public void setForestConditionType(ForestConditionTypeEnum forestConditionType) {
		this.forestConditionType = forestConditionType;
	}
	public boolean isPresenceOfRegenerants() {
		return presenceOfRegenerants;
	}
	public void setPresenceOfRegenerants(boolean presenceOfRegenerants) {
		this.presenceOfRegenerants = presenceOfRegenerants;
	}
	public DensityOfRegenerantsEnum getDensityOfRegenerants() {
		return densityOfRegenerants;
	}
	public void setDensityOfRegenerants(
			DensityOfRegenerantsEnum densityOfRegenerants) {
		this.densityOfRegenerants = densityOfRegenerants;
	}
    
    
	
	
}
