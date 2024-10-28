package me.nao.general.info;


import java.util.List;

import org.bukkit.boss.BossBar;

import me.nao.enums.GameStatus;
import me.nao.enums.GameType;
import me.nao.enums.StopMotivo;

public class GameInfo {
	
	
	private String name;
	private int maxplayers;
	private int minplayers;
	
	
	private List<String> participantes;
	private List<String> spectator;
	private GameType mision;
	private GameStatus estpart;
	private StopMotivo motivo;
	private BossBar boss;
	private String time;
	private GameObjetivesMG objetives;
	private boolean objetivesprimary, objetivessecondary;


	
	// @param GameInfo Sirve para modo aventura y resistencia
	/**
	 * Constructor Base para futuros tipos de Juegos 
	 * 
	 */
	public GameInfo(String name,int maxplayers,int minplayers, GameType mision, GameStatus estpart, StopMotivo motivo, BossBar boss,
			String time,GameObjetivesMG objetives,List<String> participantes,List<String> spectator,boolean objetivesprimary , boolean objetivessecondary) {
	
		this.name = name;
		this.maxplayers = maxplayers;
		this.minplayers = minplayers;
		this.mision = mision;
		this.estpart = estpart;
		this.motivo = motivo;
		this.boss = boss;
		this.time = time;
		this.objetives = objetives;
		this.participantes = participantes;
		this.spectator = spectator;
		this.objetivesprimary = objetivesprimary;
		this.objetivessecondary = objetivessecondary;
	}
	
	
	public String getName() {
		return name;
	}

	public int getMaxPlayers() {
		return maxplayers;
	}
	
	public int getMinPlayers() {
		return minplayers;
	}
	
	public GameType getGameType() {
		return mision;
	}

	public GameStatus getGameStatus() {
		return estpart;
	}

	public StopMotivo getMotivo() {
		return motivo;
	}

	public BossBar getBoss() {
		return boss;
	}

	
	public String getTimeMg() {
		return time;
	}
	
	public GameObjetivesMG getGameObjetivesMg() {
		return objetives;
	}

	public List<String> getParticipants(){
		return participantes;
	}
	
	public List<String> getSpectators(){
		return spectator;
	}
	
	public boolean isObjetivesPrimaryComplete() {
		return objetivesprimary;
	}


	public boolean isObjetivesSecondaryComplete() {
		return objetivessecondary;
	}
	
	public void setGameStatus(GameStatus estpart) {
		this.estpart = estpart;
	}

	public void setMotivo(StopMotivo motivo) {
		this.motivo = motivo;
	}

	public void setObjetivesPrimaryComplete(boolean objetivesprimary) {
		this.objetivesprimary = objetivesprimary;
	}


	public void setObjetivesSecondaryComplete(boolean objetivessecondary) {
		this.objetivessecondary = objetivessecondary;
	}


	public String ShowGame() {
		return getName()+" "+getMaxPlayers()+" "+getMinPlayers()+" "+getGameType().toString()+" "+getGameStatus().toString()+" "+
	getMotivo().toString()+" "+getBoss().getTitle()+" "+getTimeMg();
	}
	

}
