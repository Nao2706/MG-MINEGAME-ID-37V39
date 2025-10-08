package me.nao.generalinfo.mg;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class RespawnLife {
	
	
	private Location loc ;
	private int lifes;
	
	public RespawnLife(Location loc, int lifes) {
		this.loc = loc;
		this.lifes = lifes;
	}

	public Location getLocRespawnLife() {
		
		return loc;
	}
	
	public Location getLocRespawnLifePosition() {
		
		return new Location(Bukkit.getWorld(loc.getWorld().getName()),loc.getX(),loc.getY(),loc.getZ());
	}
	
	public int getLifes() {
		return lifes;
	}
	
	public void setLocRespawnLife(Location loc) {
		this.loc = loc;
	}
	
	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
	
	
	

}
