package me.nao.landmine.enums.lm;


public enum DetectionType {

	GENERAL,
	BLOCKBREAK,
	REDSTONE,
	INTERACT,
	MOVE;
	
	public static DetectionType matchType(String texto) {
		
		for(DetectionType val : values()) {
			if(val.name().equals(texto.toUpperCase())) {
				return val;
			}
		}
		
		return null;
	}
	
	
}
