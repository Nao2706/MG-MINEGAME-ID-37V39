package me.nao.enums.mg;


public enum GameType {
	
	
	ADVENTURE("Aventura"),
	PREADVENTURE("Preaventura"),
	RESISTENCE("Resistencia"),
	PVP("PVP"),
	NEXO("Nexo"),
	INFECTED("Infectado");

	
	 private String description;
	
	 GameType(String description) {
		this.description = description;
	 } 
	
	public String getValue() {
		return this.description ;
	}
	
	
}
