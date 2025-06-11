package me.nao.generalinfo.mg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;

public class TeamMg {

	private String teamname;
	private List<String> members,alivemembers,deadsmembers;
	private Color color;
	private Location spawn;
	private Location nexo;
	private int lifeNexo,woolpoints ,maxmembers;
	private boolean isobliterated;
	
	
	public TeamMg() {
		this.teamname = "DESCONOCIDO"; 
		this.members = new ArrayList<>();
		this.alivemembers = new ArrayList<>();
		this.deadsmembers = new ArrayList<>();
		this.color = null;
		this.spawn = null;
		this.nexo = null;
		this.lifeNexo = 0;
		this.isobliterated = false;
	}

	
	public String getTeamName() {
		return teamname;
	}

	public List<String> getMembers() {
		return members;
	}
	
	public List<String> getAlivemembers() {
		return alivemembers;
	}

	public List<String> getDeadsmembers() {
		return deadsmembers;
	}

	public Color getColor() {
		return color;
	}

	public Location getSpawn() {
		return spawn;
	}

	public Location getNexo() {
		return nexo;
	}

	public int getLifeNexo() {
		return this.lifeNexo;
	}
	
	public int getWoolpoints() {
		return woolpoints;
	}

	public int getMaxmembers() {
		return maxmembers;
	}
	
	public boolean isNexoDestroyed() {
		return this.lifeNexo <= 0 ? true : false;
	}
	
	public boolean isAllMembersDead() {
		return this.members.isEmpty();
	}

	public void setTeamName(String name) {
		this.teamname = name;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public void setNexo(Location nexo) {
		this.nexo = nexo;
	}

	public void setLifeNexo(int lifeNexo) {
		this.lifeNexo = lifeNexo;
	}

	public void setWoolpoints(int woolpoints) {
		this.woolpoints = woolpoints;
	}

	public void setObliterated(boolean isobliterated) {
		this.isobliterated = isobliterated;
	}

	public void setMaxmembers(int maxmembers) {
		this.maxmembers = maxmembers;
	}		
	
	public void addMember(String name) {
		if(!this.members.contains(name)) {
			this.members.add(name);
		}
	}
	
	public void removeMember(String name) {
		if(this.members.remove(name));
	}

	public void addAliveMember(String name) {
		if(!this.alivemembers.contains(name)) {
			alivemembers.add(name);
		}
	}

	public void removeAliveMember(String name) {
		if(this.alivemembers.remove(name));
	}

	public void addDeadMember(String name) {
		if(!this.deadsmembers.contains(name)) {
			deadsmembers.add(name);
		}
	}

	public void removeDeadMember(String name) {
		if(this.deadsmembers.remove(name));
	}
	
	public void changeOfLifetoDead(String name) {
		removeAliveMember(name);
		addDeadMember(name);
	}
	
	public void changeOfDeadtoLife(String name) {
		removeDeadMember(name);
		addAliveMember(name);
	}
	
	public void removeAll(String name) {
		removeMember(name);
		removeAliveMember(name);
		removeDeadMember(name);
	}
	
	public boolean isMemberAlive(String name) {
		return this.alivemembers.contains(name);
	}
	
	public boolean isMemberDead(String name) {
		return this.deadsmembers.contains(name);
	}

	public boolean isObliterated() {
		return isobliterated;
	}
	



	
	
	
}
