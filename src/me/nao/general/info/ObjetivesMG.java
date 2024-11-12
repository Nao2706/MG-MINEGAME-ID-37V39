package me.nao.general.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.nao.enums.ObjetiveStatusType;

public class ObjetivesMG {
	
	
	private String name ;
	private int priority ,startvalue, currentvalue, valuecomplete,valueincomplete;
	private ObjetiveStatusType status;
	private HashMap<Player,Integer> playerinteractions;
	private List<String> description ,ObjetiveCompleteMessage,ObjetiveCompleteActions,ObjetiveCompletePlayerActions,ObjetiveIncompleteMessage,ObjetiveIncompleteActions,ObjetiveIncompletePlayerActions;
	
	/**
	 * Crear Objetivo 
	 * 
	 * @param String name,  int priority, int valuestart, int valueinitial, int valuecomplete,int valueincomplete,String description, ObjetiveStatusType status,HashMap<Player,Integer> playerinteractions
	 * 
	 */
	
	public ObjetivesMG() {
		this.name = "NINGUNO";
		this.startvalue = 0;
		this.currentvalue = 0;
		this.valuecomplete = 10;
		this.valueincomplete = 0;
		this.description =  new ArrayList<>();   
		this.status = ObjetiveStatusType.WAITING;
		this.priority = 0;
		this.playerinteractions = new HashMap<Player, Integer>();
		this.ObjetiveCompleteMessage =  new ArrayList<>();   
		this.ObjetiveCompleteActions =  new ArrayList<>();   
		this.ObjetiveCompletePlayerActions =  new ArrayList<>();   
		this.ObjetiveIncompleteMessage =  new ArrayList<>();   
		this.ObjetiveIncompleteActions =  new ArrayList<>();   
		this.ObjetiveIncompletePlayerActions =  new ArrayList<>();   
	}

	public String getObjetiveName() {
		return name;
	}

	public int getPriority() {
		return priority;
	}

	public int getStartValue() {
		return startvalue;
	}
	
	public int getCurrentValue() {
		return currentvalue;
	}

	public int getCompleteValue() {
		return valuecomplete;
	}

	public int getIncompleteValue() {
		return valueincomplete;
	}

	public ObjetiveStatusType getObjetiveStatusType() {
		return status;
	}

	public HashMap<Player, Integer> getPlayerInteractions() {
		return playerinteractions;
	}

	public List<String> getDescription() {
		return description;
	}

	public List<String> getObjetiveCompleteMessage() {
		return ObjetiveCompleteMessage;
	}

	public List<String> getObjetiveCompleteActions() {
		return ObjetiveCompleteActions;
	}

	public List<String> getObjetiveCompletePlayerActions() {
		return ObjetiveCompletePlayerActions;
	}

	public List<String> getObjetiveIncompleteMessage() {
		return ObjetiveIncompleteMessage;
	}

	public List<String> getObjetiveIncompleteActions() {
		return ObjetiveIncompleteActions;
	}

	public List<String> getObjetiveIncompletePlayerActions() {
		return ObjetiveIncompletePlayerActions;
	}

	public void setObjetiveName(String name) {
		this.name = name;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setStartValue(int startvalue) {
		this.startvalue = startvalue;
	}
	
	public void setCurrentValue(int currentvalue) {
		this.currentvalue = currentvalue;
	}

	public void setCompleteValue(int valuecomplete) {
		this.valuecomplete = valuecomplete;
	}

	public void setInCompleteValue(int valueincomplete) {
		this.valueincomplete = valueincomplete;
	}

	public void setObjetiveStatusType(ObjetiveStatusType status) {
		this.status = status;
	}

	public void setPlayerInteractions(HashMap<Player, Integer> playerinteractions) {
		this.playerinteractions = playerinteractions;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public void setObjetiveCompleteMessage(List<String> objetiveCompleteMessage) {
		ObjetiveCompleteMessage = objetiveCompleteMessage;
	}

	public void setObjetiveCompleteActions(List<String> objetiveCompleteActions) {
		ObjetiveCompleteActions = objetiveCompleteActions;
	}

	public void setObjetiveCompletePlayerActions(List<String> objetiveCompletePlayerActions) {
		ObjetiveCompletePlayerActions = objetiveCompletePlayerActions;
	}

	public void setObjetiveIncompleteMessage(List<String> objetiveIncompleteMessage) {
		ObjetiveIncompleteMessage = objetiveIncompleteMessage;
	}

	public void setObjetiveIncompleteActions(List<String> objetiveIncompleteActions) {
		ObjetiveIncompleteActions = objetiveIncompleteActions;
	}

	public void setObjetiveIncompletePlayerActions(List<String> objetiveIncompletePlayerActions) {
		ObjetiveIncompletePlayerActions = objetiveIncompletePlayerActions;
	}


	
	
	

}
