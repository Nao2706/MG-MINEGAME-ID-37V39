package me.nao.general.info;

import java.util.List;

import org.bukkit.boss.BossBar;

import me.nao.enums.GameStatus;
import me.nao.enums.GameType;
import me.nao.enums.StopMotivo;



public class GameAdventure extends GameInfo{

	
	private List<String> vivo;
	private List<String> muerto;
	private List<String> arrivo;
	private List<String> knocked;
	
	
	//O1 Y O2 son los objetivos si es que se completaron para evitar doble reclamo o recompensa
	public GameAdventure(String name,int maxplayers,int minplayers, GameType mision, GameStatus estpart, StopMotivo motivo, BossBar boss,
			String time,GameObjetivesMG objetives,List<String> participantes,List<String> spectator, List<String> vivo, List<String> muerto, List<String> arrivo,List<String> knocked,boolean objetiveprimary ,boolean objetivesecondary) {
		
		super(name,maxplayers,minplayers,mision,estpart,motivo,boss,time,objetives,participantes,spectator,objetiveprimary,objetivesecondary);
		this.vivo = vivo;
		this.muerto = muerto;
		this.arrivo = arrivo;
		this.knocked = knocked;
	}

	public List<String> getAlivePlayers() {
		return vivo;
	}
	
	public List<String> getDeadPlayers() {
		return muerto;
	}
	
	public List<String> getArrivePlayers() {
		return arrivo;
	}
	
	public List<String> getKnockedPlayers() {
		return knocked;
	}

	
	
}
