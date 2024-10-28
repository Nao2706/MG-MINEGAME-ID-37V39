package me.nao.general.info;

public class GamePoints {

	
	
	private int kills , deads , revive, helprevive, damage;
	
	
	/**
	 * Clase destinada a almacenar datos de Jugador Kills Deads Revive HelpRevive Damage
	 * 
	 * 
	 */
	
	public GamePoints(int kills , int deads ,int revive ,int helprevive,int damage) {
		this.kills = kills;
		this.deads = deads;
		this.revive = revive;
		this.helprevive = helprevive;
		this.damage = damage;
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
	
	public int getDamage() {
		return damage;
	}
	
	public int getHelpRevive() {
		return helprevive;
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
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
}
