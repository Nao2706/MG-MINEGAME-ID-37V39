package me.nao.general.info;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;

public class TeamsMg {

	private List<String> members;
	private Color color;
	private Location spawn;
	private Location nexo;
	private int lifeNexo;
	
	
	public TeamsMg(List<String> members, Color color, Location spawn, Location nexo, int lifeNexo) {
		this.members = members;
		this.color = color;
		this.spawn = spawn;
		this.nexo = nexo;
		this.lifeNexo = lifeNexo;
	}


	public List<String> getMembers() {
		return members;
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
		return lifeNexo;
	}

	public void setLifeNexo(int lifeNexo) {
		this.lifeNexo = lifeNexo;
	}
	
	
	
	
	
	
}
