package models.dao;

import java.util.List;

import models.AnimalDirectWildlifeObservation;
import models.DatatableEntry;
import models.ForestConditionReport;
import models.Observation;
import models.Reports;
import models.ReportsParameter;
import models.ThreatReport;
import models.WildlifeObservation;

public interface ReportsDao {

	public List<Reports> getReport(String observation, String type);
	public List<Reports> getWildlifeReport(String type, String species, String speciesType);
	public List<ForestConditionReport> getForestConditionTableReports(ReportsParameter reportsParameter);
	public List<ThreatReport> getThreatReport(ReportsParameter reportsParameter);
	public List<WildlifeObservation> getAnimalDirectWildlifeReport(ReportsParameter reportsParameter);
	
	public List<Reports> getThreatReports(ReportsParameter reportsParameter);
	
	public DatatableEntry getWildlifeDatatable();
	public DatatableEntry getForestConditionDatatable();
	public DatatableEntry getThreatDatatable();
}
