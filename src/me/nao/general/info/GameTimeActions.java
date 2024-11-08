package me.nao.general.info;

import java.util.List;

public class GameTimeActions {

	private String time;
	private List<String> actions;
	
	
	public GameTimeActions(String time , List<String> actions) {
		this.time = time;
		this.actions = actions;
				
	}


	public String getDisplayTime() {
		return time;
	}

	public List<String> getActions() {
		return actions;
	}
	
	public void setDisplayTime(String time) {
		this.time = time;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}
	
}
