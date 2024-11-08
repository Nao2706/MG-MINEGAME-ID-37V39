package me.nao.general.info;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.boss.BossBar;

import me.nao.enums.GameStatus;
import me.nao.enums.GameType;
import me.nao.enums.StopMotivo;

public class GameInfo {
	
	
	private String name , time;
	private int maxplayers,minplayers;
	private List<String> participants,spectators;
	private GameType type;
	private GameStatus estpart;
	private StopMotivo motivo;
	private BossBar boss;
	private GameObjetivesMG objetives;
	private boolean hasobjetives,isNecessaryObjetivePrimary,isNecessaryObjetiveSedondary, iscopmpleteobjetivesprimary, iscompleteobjetivessecondary;
	private List<GameTimeActions> gametimeactions;

	
	// @param GameInfo Sirve para modo aventura y resistencia
	/**
	 * Constructor Base para futuros tipos de Juegos 
	 * 
	 */
	public GameInfo() {
		this.name = "DESCONOCIDO";
		this.maxplayers = 10;
		this.minplayers = 1;
		this.type = null;
		this.estpart = GameStatus.ESPERANDO;
		this.motivo = StopMotivo.NINGUNO;
		this.boss = null;
		this.time = "0";
		this.objetives = null;
		this.participants = new ArrayList<>();
		this.spectators = new ArrayList<>();
		this.hasobjetives = false;
		this.isNecessaryObjetivePrimary = false;
		this.isNecessaryObjetiveSedondary = false;
		this.iscopmpleteobjetivesprimary = false;
		this.iscompleteobjetivessecondary = false;
		this.gametimeactions = new ArrayList<>();
	}
	
	
	public String getMapName() {
		return name;
	}

	public int getMaxPlayers() {
		return maxplayers;
	}
	
	public int getMinPlayers() {
		return minplayers;
	}
	
	public GameType getGameType() {
		return type;
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
	
	public List<GameTimeActions> getGameTimeActionsMg() {
		return gametimeactions;
	}
	
	public GameObjetivesMG getGameObjetivesMg() {
		return objetives;
	}

	public List<String> getParticipants(){
		return participants;
	}
	
	public List<String> getSpectators(){
		return spectators;
	}
	
	public boolean hasMapObjetives() {
		return hasobjetives;
	}
	
	public boolean isNecessaryObjetivePrimary() {
		return isNecessaryObjetivePrimary;
	}
	
	public boolean isNecessaryObjetiveSedondary() {
		return isNecessaryObjetiveSedondary;
	}
	
	public boolean isObjetivesPrimaryComplete() {
		return iscopmpleteobjetivesprimary;
	}
	
	public boolean isObjetivesSecondaryComplete() {
		return iscompleteobjetivessecondary;
	}
	
	public void setMapName(String name) {
		this.name = name;
	}
	
	public void setMaxPlayersinMap(int maxplayers) {
		this.maxplayers = maxplayers;
	}
	
	public void setMinPlayersinMap(int minplayers) {
		this.minplayers = minplayers;
	}
	
	public void setBossbar(BossBar boss) {
		this.boss = boss;
	}
	
	public void setTimeMg(String time) {
		this.time = time;
	}
	
	public void setGameStatus(GameStatus estpart) {
		this.estpart = estpart;
	}

	public void setMotivo(StopMotivo motivo) {
		this.motivo = motivo;
	}
	
	public void setGameType(GameType type) {
		this.type = type;
	}
	
	public void setGameTimeActions(List<GameTimeActions> gametimeactions) {
		this.gametimeactions = gametimeactions;
	}
	
	public void setObjetivesMg(GameObjetivesMG objetives) {
		this.objetives = objetives;
	}
	
	public void setMapObjetives(boolean objetives) {
		hasobjetives = objetives;
	}

	public void setObjetivesPrimaryComplete(boolean objetivesprimary) {
		this.iscopmpleteobjetivesprimary = objetivesprimary;
	}

	public void setObjetivesSecondaryComplete(boolean objetivessecondary) {
		this.iscompleteobjetivessecondary = objetivessecondary;
	}
	
	public void setNecessaryObjetivesPrimaryCompletes(boolean necessaryobjetivesprimary) {
		this.isNecessaryObjetivePrimary = necessaryobjetivesprimary;
	}

	public void setNecessaryObjetivesSecondaryCompletes(boolean necessaryobjetivessecondary) {
		this.isNecessaryObjetiveSedondary = necessaryobjetivessecondary;
	}

	public String ShowGame() {
		return getMapName()+" "+getMaxPlayers()+" "+getMinPlayers()+" "+getGameType().toString()+" "+getGameStatus().toString()+" "+
	getMotivo().toString()+" "+getBoss().getTitle()+" "+getTimeMg();
	}
	

}
