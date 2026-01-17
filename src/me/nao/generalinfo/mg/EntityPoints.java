package me.nao.generalinfo.mg;

import org.bukkit.entity.EntityType;

public class EntityPoints {
	
	private EntityType type;
	private String name;
	private int point;
	
	
	public EntityPoints(EntityType type, String name, int point) {
		this.type = type;
		this.name = name;
		this.point = point;
	}
	
	
	public EntityType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPoint() {
		return point;
	}
	
	public void setType(EntityType type) {
		this.type = type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	

	
	
}
