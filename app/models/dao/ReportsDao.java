package models.dao;

import java.util.List;

import models.Reports;

public interface ReportsDao {

	public List<Reports> getReport(String observation, String type);
	public List<Reports> getWildlifeReport(String type, String species, String speciesType);
}
