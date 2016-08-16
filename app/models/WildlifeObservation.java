package models;

import java.util.Date;

import enums.SpeciesEnum;
import enums.WildlifeObservationTypeEnum;

public class WildlifeObservation extends Observation {

	 private WildlifeObservationTypeEnum wildlifeObservationType;
	 private SpeciesEnum species;
	 private String speciesType;
	public WildlifeObservationTypeEnum getWildlifeObservationType() {
		return wildlifeObservationType;
	}
	public void setWildlifeObservationType(
			WildlifeObservationTypeEnum wildlifeObservationType) {
		this.wildlifeObservationType = wildlifeObservationType;
	}
	public SpeciesEnum getSpecies() {
		return species;
	}
	public void setSpecies(SpeciesEnum species) {
		this.species = species;
	}
	public String getSpeciesType() {
		return speciesType;
	}
	public void setSpeciesType(String speciesType) {
		this.speciesType = speciesType;
	}
	
	
	
	
	 
	 
	 
}
