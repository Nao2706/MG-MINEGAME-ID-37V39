package me.nao.enums.mg;

public enum GameInfringements {

	ILEGAL_GOD("ILEGAL-GOD"),
	ILEGAL_FLY("ILEGAL-FLY"),
	HACKS("HACKS"),
	DISRESPECT("DISRESPECT"),
	ILEGAL_SPEED("ILEGAL-SPEED"),
	ILEGAL_ANTIKNOCKBACK("ILEGAL-ANTIKNOCKBACK"),
	ILEGAL_NODAMAGE("ILEGAL-NODAMAGE"),
	SPAM("SPAM"),
	X_RAY("X-RAY"),
	TROLL("TROLL"),
	BUG("BUG"),
	BULLYING("BULLYING"),
	KILLAURA("KILLAURA"),
	AUTO_CLICK("AUTOCLICK"),
	OTHERS("OTHERS");
	
	private String description;
	
	GameInfringements(String description) {
		this.description = description;
	 } 
	
	public String getValue() {
		return this.description ;
	}
	
	
	
}
