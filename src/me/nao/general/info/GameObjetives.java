package me.nao.general.info;

import java.util.List;

public class GameObjetives {

	
	
	private List<String> primaryobjetives;
	private List<String> secondaryobjetives;
	
	public GameObjetives(List<String> primaryobjetives,List<String> secondaryobjetives) {
		this.primaryobjetives = primaryobjetives;
		this.secondaryobjetives = secondaryobjetives;
	}

	public List<String> getPrimaryObjetives() {
		return primaryobjetives;
	}

	public List<String> getSecondaryObjetives() {
		return secondaryobjetives;
	}
	
	
	
	
}
