package me.nao.generalinfo.mg;

import java.util.ArrayList;
import java.util.List;

public class GameObjetivesMG {

	/**
	 * Lista de Objetivos : Contiene Objetivos que cada mapa contenga.o
	 * 
	 * 
	 * 
	 */

	private List<ObjetivesMG> objetives;
	private boolean hasobjetives,isNecessaryObjetivePrimary,isNecessaryObjetiveSedondary, iscopmpleteobjetivesprimary, iscompleteobjetivessecondary;
	private List<List<String>> CompleteAllPrimaryObjetiveForReward , CompleteAllSecondaryObjetiveForReward;
	
	
	public GameObjetivesMG() {
		this.objetives = new ArrayList<>();
		this.isNecessaryObjetivePrimary = false;
		this.isNecessaryObjetiveSedondary = false;
		this.iscopmpleteobjetivesprimary = false;
		this.iscompleteobjetivessecondary = false;
		this.CompleteAllPrimaryObjetiveForReward = new ArrayList<>();
		this.CompleteAllSecondaryObjetiveForReward = new ArrayList<>();
	}

	/**
	 * Lista de Objetivos
	 * 
	 * @return Lista de Objetivos del Mapa
	 * 
	 */
	public List<ObjetivesMG> getObjetives() {
		return objetives;
	}
	
	public void setObjetives(List<ObjetivesMG> objetives) {
		this.objetives = objetives;
	}
	
	public boolean hasMapObjetives() {
		return hasobjetives;
	}
	
	public boolean isNecessaryObjetivePrimary() {
		return isNecessaryObjetivePrimary;
	}
	
	public boolean isNecessaryObjetiveSedondary() {
		return isNecessaryObjetiveSedondary;
	}
	
	public boolean isObjetivesPrimaryComplete() {
		return iscopmpleteobjetivesprimary;
	}
	
	public boolean isObjetivesSecondaryComplete() {
		return iscompleteobjetivessecondary;
	}
	
	public List<ObjetivesMG> getObjetivesPrimary(){
		List<ObjetivesMG> l = new ArrayList<>();
		for(ObjetivesMG obj : getObjetives()) {
			if(obj.getPriority() != 1) continue;
			l.add(obj);
		}
		return l;
	}
	
	public List<ObjetivesMG> getObjetivesSecondary(){
		List<ObjetivesMG> l = new ArrayList<>();
		for(ObjetivesMG obj : getObjetives()) {
			if(obj.getPriority() <= 1) continue;
			l.add(obj);
		}
		return l;
	}
	
	public List<ObjetivesMG> getObjetivesHostile(){
		List<ObjetivesMG> l = new ArrayList<>();
		for(ObjetivesMG obj : getObjetives()) {
			if(obj.getPriority() >= 1) continue;
			l.add(obj);
		}
		return l;
	}
	
	public List<List<String>> getCompleteAllPrimaryObjetiveForReward(){
		return CompleteAllPrimaryObjetiveForReward;
	}
	
	public List<List<String>> getCompleteAllSecondaryObjetiveForReward(){
		return CompleteAllSecondaryObjetiveForReward;
	}
	
	public void setCompleteAllPrimaryObjetiveForReward(List<List<String>> CompleteAllPrimaryObjetiveForReward) {
		this.CompleteAllPrimaryObjetiveForReward = CompleteAllPrimaryObjetiveForReward;
	}
	
	public void setCompleteAllSecondaryObjetiveForReward(List<List<String>> CompleteAllSecondaryObjetiveForReward) {
		this.CompleteAllSecondaryObjetiveForReward = CompleteAllSecondaryObjetiveForReward;
	}
	
	public void setMapObjetives(boolean objetives) {
		hasobjetives = objetives;
	}

	public void setObjetivesPrimaryComplete(boolean objetivesprimary) {
		this.iscopmpleteobjetivesprimary = objetivesprimary;
	}

	public void setObjetivesSecondaryComplete(boolean objetivessecondary) {
		this.iscompleteobjetivessecondary = objetivessecondary;
	}
	
	public void setNecessaryObjetivesPrimaryCompletes(boolean necessaryobjetivesprimary) {
		this.isNecessaryObjetivePrimary = necessaryobjetivesprimary;
	}

	public void setNecessaryObjetivesSecondaryCompletes(boolean necessaryobjetivessecondary) {
		this.isNecessaryObjetiveSedondary = necessaryobjetivessecondary;
	}
	

}
