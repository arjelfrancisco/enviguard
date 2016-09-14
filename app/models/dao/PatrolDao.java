package models.dao;

import java.util.List;

import models.Patrol;

public interface PatrolDao {

	public Patrol getPatrolById(long id);
	public Long addPatrol(Patrol patrol);
	public List<Patrol> getPatrols();
}
