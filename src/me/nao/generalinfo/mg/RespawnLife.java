package me.nao.generalinfo.mg;

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
