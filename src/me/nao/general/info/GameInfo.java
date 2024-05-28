package me.nao.general.info;


import java.util.List;

import org.bukkit.boss.BossBar;

import me.nao.manager.EstadoPartida;
import me.nao.manager.StopMotivo;

public class GameInfo {
	
	
	private String name;
	private int maxplayers;
	private int minplayers;
	
	
	private List<String> participantes;
	private List<String> spectator;
	private GameType mision;
	private EstadoPartida estpart;
	private StopMotivo motivo;
	private BossBar boss;
	private String time;
	private GameObjetivesMG objetives;



	
	// @param GameInfo Sirve para modo aventura y resistencia
	/**
	 * Este metodo sirve siempre que no uses nexo
	 
	 */
	public GameInfo(String name,int maxplayers,int minplayers, GameType mision, EstadoPartida estpart, StopMotivo motivo, BossBar boss,
			String time,GameObjetivesMG objetives,List<String> participantes,List<String> spectator) {
	
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

	public EstadoPartida getEstopartida() {
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

	public List<String> getParticipantes(){
		return participantes;
	}
	
	public List<String> getSpectator(){
		return spectator;
	}
	
	public void setEstadopartida(EstadoPartida estpart) {
		this.estpart = estpart;
	}

	public void setMotivo(StopMotivo motivo) {
		this.motivo = motivo;
	}


	public String ShowGame() {
		return getName()+" "+getMaxPlayers()+" "+getMinPlayers()+" "+getGameType().toString()+" "+getEstopartida().toString()+" "+
	getMotivo().toString()+" "+getBoss().getTitle()+" "+getTimeMg();
	}
	

}
