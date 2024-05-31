package me.nao.general.info;

public class GamePoints {

	
	
	private int kills , deads , revive, asisrevive, da�o;
	
	//falta el da�o
	public GamePoints(int kills , int deads ,int revive ,int asisrevive,int da�o) {
		this.kills = kills;
		this.deads = deads;
		this.revive = revive;
		this.asisrevive = asisrevive;
		this.da�o = da�o;
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
		return da�o;
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
	
	public void setDamage(int da�o) {
		this.da�o = da�o;
	}
	
	
}
