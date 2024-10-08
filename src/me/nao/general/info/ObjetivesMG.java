package me.nao.general.info;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class ObjetivesMG {
	
	
	private String nombre ,description;
	private int priority ,valuestart, valueinitial, valuecomplete,valueincomplete;
	private ObjetiveStatusType status;
	private HashMap<Player,Integer> value;
	
	public ObjetivesMG(String nombre,  int priority, int valuestart, int valueinitial, int valuecomplete,int valueincomplete,String description, ObjetiveStatusType status,HashMap<Player,Integer> value) {
		this.nombre = nombre;
		this.valuestart = valuestart;
		this.valueinitial = valueinitial;
		this.valuecomplete = valuecomplete;
		this.valueincomplete = valueincomplete;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.value = value;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPriority() {
		return priority;
	}
	
	public int getStartValue() {
		return valuestart;
	}
	
	public int getValue() {
		return valueinitial;
	}
	
	public int getCompleteValue() {
		return valuecomplete;
	}
	
	public int getIncompleteValue() {
		return valueincomplete;
	}
	
	public String getDescription() {
		return description;
	}

	public ObjetiveStatusType getObjetiveType() {
		return status;
	}
	
	public HashMap<Player,Integer> getParticipants(){
		return value;
	}

	public void setValue(int valueinitial) {
		this.valueinitial = valueinitial;
	}

	public void setObjetiveType(ObjetiveStatusType status) {
		this.status = status;
	}
	
	
	
	
	

}
