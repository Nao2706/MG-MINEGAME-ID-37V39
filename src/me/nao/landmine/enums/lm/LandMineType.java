package me.nao.landmine.enums.lm;

import me.nao.landmine.utils.lm.Utils;

public enum LandMineType {

	
	EXPLOSION(Utils.formatChatColor("Explosive")),
	LAVA(Utils.formatChatColor("Lava")),
	PORTAL(Utils.formatChatColor("Portal")),
	WATER(Utils.formatChatColor("Water")),
	JUMP(Utils.formatChatColor("Jump")),
	INMOVIL(Utils.formatChatColor("Inmovil")),
	FIRE(Utils.formatChatColor("Fire")),
	VOID(Utils.formatChatColor("Void")),
	ZOMBIES1(Utils.formatChatColor("Zombies-V1"));
	
	public String description;
	
	LandMineType(String description){
		this.description = description;
	}
	
	public String getValue() {
		return description;
	}
	
	public static LandMineType matchLandMine(String texto) {
		
		for(LandMineType val : values()) {
			if(val.name().equals(texto.toUpperCase())) {
				return val;
			}
		}
		
		return null;
	}
	
	
}
