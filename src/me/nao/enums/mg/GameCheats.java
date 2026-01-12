package me.nao.enums.mg;

public enum GameCheats {

	ILEGAL_GOD("ILEGAL-GOD"),
	ILEGAL_FLY("ILEGAL-FLY"),
	HACKS("HACKS"),
	DISRESPECT("DISRESPECT"),
	ILEGAL_SPEED("ILEGAL-SPEED"),
	ILEGAL_ANTIKNOCKBACK("ILEGAL-ANTIKNOCKBACK"),
	SPAM("SPAM"),
	X_RAY("X-RAY"),
	BULLYING("BULLYING"),
	KILLAURA("KILLAURA"),
	AUTO_CLICK("AUTOCLICK"),
	OTHERS("OTHERS");
	
	private String description;
	
	GameCheats(String description) {
		this.description = description;
	 } 
	
	public String getValue() {
		return this.description ;
	}
	
	
	
}
