package me.nao.general.info;

public class GamePoints {

	
	
	private int kills , deads , revive, asisrevive, daño;
	
	//falta el daño
	public GamePoints(int kills , int deads ,int revive ,int asisrevive,int daño) {
		this.kills = kills;
		this.deads = deads;
		this.revive = revive;
		this.asisrevive = asisrevive;
		this.daño = daño;
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
		return daño;
	}

	
	public int getReviveAsistence() {
		return asisrevive;
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
	
	public void setReviveAsistence(int asisrevive) {
		this.asisrevive = asisrevive;
	}
	
	public void setDamage(int daño) {
		this.daño = daño;
	}
	
	
}
