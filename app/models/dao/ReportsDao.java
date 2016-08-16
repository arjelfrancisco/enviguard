package models.dao;

import java.util.List;

import models.Reports;

public interface ReportsDao {

	public List<Reports> getReport(String observation, String type);
}
