package models.dao;

import java.util.List;

import models.Patroller;
import models.SpeciesType;
import models.ThreatType;
import enums.SpeciesEnum;


public interface LookupDao {

    public List<SpeciesType> getSpeciestypes();
    public List<ThreatType> getThreatTypes();
    public void addThreatType(String name);
    public ThreatType getThreatByName(String name);
    public void updateThreatType(ThreatType threatType);
    public boolean deleteThreatType(long id);
    public void addSpeciesType (SpeciesType speciesType);
    public SpeciesType getSpeciesByName(SpeciesType speciesType);
    public void updateSpeciesType(SpeciesType speciesType);
    public boolean deleteSpeciesType(long id);
}
