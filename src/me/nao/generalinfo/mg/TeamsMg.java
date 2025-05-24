package me.nao.generalinfo.mg;

import java.util.ArrayList;
import java.util.List;

public class TeamsMg {
	
	private List<TeamMg> teamsingame;

	public TeamsMg() {
		this.teamsingame = new ArrayList<>();
		
	}


	public List<TeamMg> getTeamsingame() {
		return teamsingame;
	}
	
	public void setTeamsingame(List<TeamMg> teamsingame) {
		this.teamsingame = teamsingame;
	}

	
	public int getTeamsAlive() {
		
		List<TeamMg> teams = getTeamsingame();
		List<TeamMg> alive = new ArrayList<>();
		
		for(TeamMg t : teams) {
			if(t.isObliterated()) continue;
			alive.add(t);
		}
		
		return alive.size();
	}
	
	public int getTeamsObliterated() {
		
		List<TeamMg> teams = getTeamsingame();
		List<TeamMg> eliminated = new ArrayList<>();
		
		for(TeamMg t : teams) {
			if(!t.isObliterated()) continue;
			eliminated.add(t);
		}
		
		return eliminated.size();
	}
	
	

	
}
