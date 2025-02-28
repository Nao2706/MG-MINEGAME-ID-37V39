package me.nao.generalinfo.mg;

import org.bukkit.Location;

import me.nao.enums.mg.GameInteractions;

public class CuboidZone {

	private Location location1;
	private Location location2;
	private GameInteractions gameinteractions;
	
	public CuboidZone(Location location1, Location location2,GameInteractions gameinteractions) {
		this.location1 = location1;
		this.location2 = location2;
		this.gameinteractions = gameinteractions;
	}

	public Location getLocation1() {
		return location1;
	}

	public Location getLocation2() {
		return location2;
	}
	
	public GameInteractions getGameInteractionType() {
		return gameinteractions;
	}

	public void setLocation1(Location location1) {
		this.location1 = location1;
	}

	public void setLocation2(Location location2) {
		this.location2 = location2;
	}
	
	public void setGameInteraction(GameInteractions gameinteractions) {
		this.gameinteractions = gameinteractions;
	}
	
	//BLOCK VERSION
	public boolean isinsideOfCuboidZone(Location location , Location point1 , Location point2) {
		
		//if(!(getLocation1().getWorld().getName().equals(getLocation2().getWorld().getName()))) return false;
		
			double minX = Math.min(getLocation1().getX(),getLocation2().getX());
			double minY = Math.min(getLocation1().getY(),getLocation2().getY());
			double minZ = Math.min(getLocation1().getZ(),getLocation2().getZ());
			
			double maxX = Math.max(getLocation1().getX(), getLocation2().getX());//quiete +1 deteccion de mas
			double maxY = Math.max(getLocation1().getY(), getLocation2().getY());//quite un +1 
			double maxZ = Math.max(getLocation1().getZ(), getLocation2().getZ());//quiete +1 deteccion de mas

			return (maxX >= location.getX() && location.getX() >= minX) &&
				   (maxY >= location.getY() && location.getY() >= minY) &&
				   (maxZ >= location.getZ() && location.getZ() >= minZ);
		
	}
	
}
