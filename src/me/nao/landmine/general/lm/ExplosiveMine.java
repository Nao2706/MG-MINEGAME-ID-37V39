package me.nao.landmine.general.lm;

import org.bukkit.Location;
import org.bukkit.Material;

import me.nao.landmine.enums.lm.DetectionType;
import me.nao.landmine.enums.lm.LandMineType;
import me.nao.landmine.enums.lm.Position;

public class ExplosiveMine {

	
	private String owner;
	private Location loc;
	private DetectionType dt;
	private LandMineType lt;
	private Material material;
	private Position pos;
	
	public ExplosiveMine(String owner, Location loc ,DetectionType dt, LandMineType lt, Material material) {
		this.owner = owner;
		this.loc = loc;
		this.dt = dt;
		this.lt = lt;
		this.material = material;
		this.pos = Position.NONE;
	}

	public String getOwnerName() {
		return owner;
	}
	
	public Location getLocationLandMine() {
		return loc;
	}

	public DetectionType getDetectionType() {
		return dt;
	}

	public LandMineType getLandMineType() {
		return lt;
	}

	public Material getLandMineMaterial() {
		return material;
	}

	public Position getLandMinePosition() {
		return pos;
	}
	
	public void setOwnerName(String owner) {
		this.owner = owner;
	}
	
	public void setLocationLandMine(Location loc) {
		this.loc = loc;
	}

	public void setDetectionType(DetectionType dt) {
		this.dt = dt;
	}

	public void setLandMineType(LandMineType lt) {
		this.lt = lt;
	}

	public void setLandMineMaterial(Material material) {
		this.material = material;
	}
	
	public void setLandMinePosition(Position pos) {
		this.pos = pos;
	}
	
	public String toData() {
		return loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+";"+getOwnerName()+","+getLandMineMaterial()+","+getDetectionType().toString()+","+getLandMineType();
	}
	
	
}
