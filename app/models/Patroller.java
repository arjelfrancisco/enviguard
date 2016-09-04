package models;

public class Patroller {
	
	private int id;
	private String name;
	private boolean active;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Patroller(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	

	
}
