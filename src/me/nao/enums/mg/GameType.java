package me.nao.enums.mg;


public enum GameType {
	
	
	ADVENTURE("Aventura"),
	POINTHUNT("Caceria de Puntos"),
	RESISTENCE("Resistencia");
//	PVP("PVP"),
//	NEXO("Nexo"),
//	INFECTED("Infectado");

	
	 private String description;
	
	 GameType(String description) {
		this.description = description;
	 } 
	
	public String getValue() {
		return this.description ;
	}
	
	public static GameType matchMode(String texto) {
		
		for(GameType val : values()) {
			if(val.name().equals(texto.toUpperCase())) {
				return val;
			}
		}
		
		return null;
	}
}
