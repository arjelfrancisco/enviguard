package models.dao;

import java.util.List;

import models.AnimalDirectWildlifeObservation;
import models.FloralDirectWildlifeObservation;
import models.ForestConditionObservation;
import models.IndirectWildlifeObservation;
import models.Observation;
import models.OtherObservation;
import models.PatrolObservationImage;
import models.Reports;
import models.ThreatObservation;


public interface ObservationDao {


    public List<Observation> getObservationByPatrolId(Long patrolId);
    public Long addObservation(Observation observation);
    public Observation getObservationByObsId(Long id);
    public List<Reports> getReports(String observation, String type);
    public List<PatrolObservationImage> getImages(Long obsId);
    public PatrolObservationImage getImage(Long id);
    
}

