package models.dao;

import java.util.List;

import models.Patroller;

public interface PatrollerDao {

	public List<Patroller> getPatrollersByPatrolId(int id);
	public List<Patroller> getPatrollers();
}
