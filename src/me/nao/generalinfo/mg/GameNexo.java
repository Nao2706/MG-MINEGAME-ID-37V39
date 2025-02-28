package me.nao.generalinfo.mg;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class GameNexo extends GameInfo{

	private Map<String,TeamsMg> teams;
	


	public GameNexo() {
		this.teams = new HashMap<>();
	}


	public Map<String, TeamsMg> getTeams() {
		return teams;
	}


	public void setTeams(Map<String, TeamsMg> teams) {
		this.teams = teams;
	}
	
	
	public boolean existOnlyOneTeam() {
		
		List<Map.Entry<String, TeamsMg>> list = new ArrayList<>(this.teams.entrySet());
		List<String> l = new ArrayList<>();
		
		for(Map.Entry<String, TeamsMg> tm : list) {
			if(!tm.getValue().isAllMembersDead()) {
				l.add(tm.getKey());
			}
		}
		
		if(l.size() == 1) {
			return true;
		}
		return false;
	}

	


	
}
