package me.nao.general.info;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.nao.enums.ObjetiveStatusType;

public class ObjetivesMG {
	
	
	private String name ,description;
	private int priority ,valuestart, valueinitial, valuecomplete,valueincomplete;
	private ObjetiveStatusType status;
	private HashMap<Player,Integer> playerinteractions;
	
	/**
	 * Crear Objetivo 
	 * 
	 * @param String name,  int priority, int valuestart, int valueinitial, int valuecomplete,int valueincomplete,String description, ObjetiveStatusType status,HashMap<Player,Integer> playerinteractions
	 * 
	 */
	
	public ObjetivesMG(String name,  int priority, int valuestart, int valueinitial, int valuecomplete,int valueincomplete,String description, ObjetiveStatusType status,HashMap<Player,Integer> playerinteractions) {
		this.name = name;
		this.valuestart = valuestart;
		this.valueinitial = valueinitial;
		this.valuecomplete = valuecomplete;
		this.valueincomplete = valueincomplete;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.playerinteractions = playerinteractions;
	}

	public String getName() {
		return name;
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
		return playerinteractions;
	}

	public void setValue(int valueinitial) {
		this.valueinitial = valueinitial;
	}

	public void setObjetiveType(ObjetiveStatusType status) {
		this.status = status;
	}
	
	
	
	
	

}
