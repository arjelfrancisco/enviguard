package models.dao;

import java.util.List;

import models.Patroller;

public interface PatrollerDao {

	public List<Patroller> getPatrollersByPatrolId(int id);
	public List<Patroller> getPatrollers();
	public boolean addPatroller(String name);
	public Patroller getByName(String name);
	public void updatePatroller(Patroller patroller);
	public boolean deletePatroller(int id);
}
