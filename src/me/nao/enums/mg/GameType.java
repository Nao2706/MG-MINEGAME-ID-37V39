package me.nao.enums.mg;


public enum GameType {
	
	
	ADVENTURE("Aventura"),
	FREEFORALL("Todos Contra Todos"),
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
