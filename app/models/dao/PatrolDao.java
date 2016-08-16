package models.dao;

import models.Patrol;

public interface PatrolDao {

	public Patrol getPatrolById(long id);
	public Long addPatrol(Patrol patrol);
}
