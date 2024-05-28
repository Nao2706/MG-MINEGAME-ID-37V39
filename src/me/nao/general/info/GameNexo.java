package me.nao.general.info;




import java.util.List;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;

import me.nao.manager.EstadoPartida;
import me.nao.manager.StopMotivo;


public class GameNexo extends GameInfo{

	

	
	private List<String> participantes;
	private List<String> t1;
	private List<String> t2;
	private List<String> spectator;
	
	private Location n1;
	private Location n2;
	private int point1;
	private int point2;





	public GameNexo(String name,int maxplayers,int minplayers, GameType mision, EstadoPartida estpart, StopMotivo motivo, BossBar boss,
			String time,GameObjetivesMG objetives,List<String> participantes, List<String> spectator, List<String> t1, List<String> t2, Location n1,
			Location n2, int point1, int point2) {
		super(name,maxplayers,minplayers,mision,estpart,motivo,boss,time,objetives,participantes,spectator);
		this.participantes = participantes;
		this.t1 = t1;
		this.t2 = t2;
		this.spectator = spectator;
		this.n1 = n1;
		this.n2 = n2;
		this.point1 = point1;
		this.point2 = point2;
	}

	public Location getBlueNexoLocation() {
		return n1;
	}
	
	public Location getRedNexoLocation() {
		return n2;
	}
	
	public int getBlueLifeNexo() {
		return point1;
	}
	
	public int getRedLifeNexo() {
		return point2;
	}
	
	public void SetBlueLifeNexo(int point) {
		this.point1 = point;
	}
	
	public void SetRedLifeNexo(int point) {
		this.point2 = point;
	}
	

	public List<String> getParticipantes() {
		return participantes;
	}


	public List<String> getBlueTeamMg() {
		return t1;
	}


	public List<String> getRedTeamMg() {
		return t2;
	}


	public List<String> getSpectator() {
		return spectator;
	}

	




	
}
