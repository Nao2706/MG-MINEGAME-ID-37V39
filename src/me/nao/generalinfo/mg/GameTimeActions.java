package me.nao.generalinfo.mg;

import java.util.List;

public class GameTimeActions {

	private String time;
	private List<String> actions;
	private boolean iscomplete;
	
	public GameTimeActions(String time , List<String> actions) {
		this.time = time;
		this.actions = actions;
		this.iscomplete = false;
	}


	public String getDisplayTime() {
		return time;
	}

	public List<String> getTimeActions() {
		return actions;
	}
	
	public boolean isActionExecuted() {
		return this.iscomplete;
	}
	
	public void setDisplayTime(String time) {
		this.time = time;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}
	
	public void setActionExecuted(boolean bol) {
		this.iscomplete = bol;
	}
	
}
