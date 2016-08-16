package models.dao;

import java.util.List;

import models.SpeciesType;
import models.ThreatType;
import enums.SpeciesEnum;


public interface LookupDao {

    public List<SpeciesType> getSpeciestypes();
    public List<ThreatType> getThreatTypes();

}
