package me.nao.general.info;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;

import me.nao.enums.GameStatus;
import me.nao.enums.GameType;
import me.nao.enums.StopMotivo;

public class GameInfo {
	
	
	private String name , time;
	private int maxplayers,minplayers, loottablemax , itemspawnrange , spawnmobrange , countdownstart;
	private List<String> participants,spectators;
	private GameType type;
	private GameStatus estpart;
	private StopMotivo motivo;
	private BossBar boss;
	private GameObjetivesMG objetives;
	private List<GameTimeActions> gametimeactions;
	private List<CuboidZone> cuboidzones;	
	private List<Location> generators,mobsgenerators;	
	private boolean pvp;
	private GameTime gt;
	// @param GameInfo Sirve para modo aventura y resistencia
	/**
	 * Constructor Base para futuros tipos de Juegos 
	 * 
	 */
	public GameInfo() {
		this.name = "DESCONOCIDO";
		this.spawnmobrange = 0;
		this.itemspawnrange = 0;
		this.loottablemax = 0;
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
		this.gametimeactions = new ArrayList<>();
		this.cuboidzones = new ArrayList<>();
		this.generators = new ArrayList<>();
		this.mobsgenerators = new ArrayList<>();
		this.pvp = false;
		this.gt = null;
		this.countdownstart = 0;
	}
	
	
	public String getMapName() {
		return name;
	}

	public int getSpawnMobRange() {
		return spawnmobrange;
	}
	
	public int getSpawnItemRange() {
		return itemspawnrange;
	}
	
	public int getLootTableLimit() {
		return loottablemax;
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

	public BossBar getBossbar() {
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
	
	public List<CuboidZone> getCuboidZones(){
		return cuboidzones;
	}
	
	public List<Location> getGenerators(){
		return generators;
	}
	
	public List<Location> getMobsGenerators(){
		return mobsgenerators;
	}
	
	public boolean isPvpinMap() {
		return pvp;
	}
	
	public GameTime getGameTime() {
		return gt;
	}
	
	public int getCountDownStart() {
		return countdownstart;
	}
	
	public void setMapName(String name) {
		this.name = name;
	}
	
	public void setSpawnMobRange(int spawnmobrange) {
		this.spawnmobrange = spawnmobrange ;
	}
	
	public void setSpawnItemRange(int itemspawnrange) {
		this.itemspawnrange = itemspawnrange ;
	}
	
	public void setLootTableLimit(int amount) {
		this.loottablemax = amount;
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
	
	public void setCuboidZones(List<CuboidZone> cuboidzones) {
		this.cuboidzones = cuboidzones;
	}
	
	public void setGenerators(List<Location> generators) {
		this.generators = generators;
	}
	
	public void setMobsGenerators(List<Location> mobsgenerators) {
		this.mobsgenerators = mobsgenerators;
	}
	
	public void setGameTimeActions(List<GameTimeActions> gametimeactions) {
		this.gametimeactions = gametimeactions;
	}
	
	public void setObjetivesMg(GameObjetivesMG objetives) {
		this.objetives = objetives;
	}
	
	public void setPvpinMap(boolean pvp) {
		this.pvp = pvp;
	}
	
	public void setGameTime(GameTime gt) {
		this.gt = gt;
	}
	
	public void setCountDownStart(int countdownstart) {
		this.countdownstart = countdownstart;
	}
	
	public String ShowGame() {
		return getMapName()+" "+getMaxPlayers()+" "+getMinPlayers()+" "+getGameType().toString()+" "+getGameStatus().toString()+" "+
	getMotivo().toString()+" "+getBossbar().getTitle()+" "+getTimeMg();
	}
	

}
