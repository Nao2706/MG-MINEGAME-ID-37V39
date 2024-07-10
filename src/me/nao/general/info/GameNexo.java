package me.nao.general.info;




import java.util.List;


import org.bukkit.boss.BossBar;

import me.nao.manager.EstadoPartida;
import me.nao.manager.StopMotivo;


public class GameNexo extends GameInfo{

	

	
	private List<String> participantes;
	private List<List<TeamsMg>> teams;
	private List<String> spectator;
	




	//O1 Y O2 son los objetivos si es que se completaron para evitar doble reclamo o recompensa
	public GameNexo(String name,int maxplayers,int minplayers, GameType mision, EstadoPartida estpart, StopMotivo motivo, BossBar boss,
			String time,GameObjetivesMG objetives,List<String> participantes, List<String> spectator,boolean o1,boolean o2,List<List<TeamsMg>> teams) {
		super(name,maxplayers,minplayers,mision,estpart,motivo,boss,time,objetives,participantes,spectator,o1,o2);
		this.participantes = participantes;
		this.teams = teams;
		this.spectator = spectator;
	}
	
	public List<String> getParticipantes() {
		return participantes;
	}


	public List<List<TeamsMg>> getTeams() {
		return teams;
	}

	public List<String> getSpectator() {
		return spectator;
	}

	




	
}
