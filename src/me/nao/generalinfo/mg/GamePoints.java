package me.nao.generalinfo.mg;

public class GamePoints {

	
	
	private int kills , deads , revive, helprevive;
	private long damage, time;
	
	/**
	 * Clase destinada a almacenar datos de Jugador Kills Deads Revive HelpRevive Damage
	 * 
	 * 
	 */
	
	public GamePoints() {
		this.kills = 0;
		this.deads = 0;
		this.revive = 0;
		this.helprevive = 0;
		this.damage = 0;
		this.time = 0;
	}


	public int getKills() {
		return kills;
	}

	public int getDeads() {
		return deads;
	}

	public int getRevive() {
		return revive;
	}
	
	public long getDamage() {		
		return damage;
	}
	
	public int getHelpRevive() {
		return helprevive;
	}

	public long getCronometSeconds() {
		return time;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setDeads(int deads) {
		this.deads = deads;
	}
	
	public void setRevive(int revive) {
		this.revive = revive;
	}
	
	public void setHelpRevive(int helprevive) {
		this.helprevive = helprevive;
	}
	
	public void setDamage(long damage) {
		this.damage = damage;
	}
	
	public void setCronometinSeconds(long time) {
		this.time = time;
	}	
	
}
