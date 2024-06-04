package me.nao.general.info;

import java.util.List;

import org.bukkit.boss.BossBar;

import me.nao.manager.EstadoPartida;
import me.nao.manager.StopMotivo;



public class GameAdventure extends GameInfo{

	
	private List<String> vivo;
	private List<String> muerto;
	private List<String> arrivo;
	private List<String> spectator;
	
	
	//O1 Y O2 son los objetivos si es que se completaron para evitar doble reclamo o recompensa
	public GameAdventure(String name,int maxplayers,int minplayers, GameType mision, EstadoPartida estpart, StopMotivo motivo, BossBar boss,
			String time,GameObjetivesMG objetives,List<String> participantes,List<String> spectator, List<String> vivo, List<String> muerto, List<String> arrivo,boolean o1 ,boolean o2) {
		
		super(name,maxplayers,minplayers,mision,estpart,motivo,boss,time,objetives,participantes,spectator,o1,o2);
		this.vivo = vivo;
		this.muerto = muerto;
		this.arrivo = arrivo;
		this.spectator = spectator;
	}

	public List<String> getVivo() {
		return vivo;
	}
	public List<String> getMuerto() {
		return muerto;
	}
	public List<String> getArrivo() {
		return arrivo;
	}
	public List<String> getSpectator() {
		return spectator;
	}

	
	
}
