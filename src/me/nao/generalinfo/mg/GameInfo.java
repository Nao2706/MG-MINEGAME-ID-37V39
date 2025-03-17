package me.nao.generalinfo.mg;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;

import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.GameType;
import me.nao.enums.mg.StopMotive;
import me.nao.utils.mg.Utils;

public class GameInfo {
	
	
	private String name , time , stopreason;
	private int maxplayers,minplayers, loottablemax , itemspawnrange , spawnmobrange , countdownstart, toxiczonerange, dispenserrange;
	private int pointsperkills, pointsperdeads , pointsperrevive ,pointsperhelprevive , pointsbonus;
	private List<String> participants,spectators;
	private GameType type;
	private GameStatus estpart;
	private StopMotive motivo;
	private BossBar boss;
	private GameObjetivesMG objetives;
	private List<GameTimeActions> gametimeactions;
	private List<CuboidZone> cuboidzones;	
	private List<Location> generators,mobsgenerators;	
	private boolean pvp,barriers,allowinventory;
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
		this.motivo = StopMotive.NINGUNO;
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
		this.barriers = true;
		this.allowinventory = false;
		this.gt = null;
		this.countdownstart = 0;
		this.toxiczonerange = 0;
		this.pointsperkills = 25;
		this.pointsperdeads = 30;
		this.pointsperrevive = 10;
		this.pointsperhelprevive = 15;
		this.stopreason = StopMotive.NINGUNO.getValue();
		this.dispenserrange = 0;
		
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
	
	public int getDispenserRange() {
		return dispenserrange;
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

	public StopMotive getStopMotive() {
		return motivo;
	}
	
	public String getStopReason() {
		return Utils.colorTextChatColor(stopreason);
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
	
	public boolean hasBarriersinMap() {
		return barriers;
	}
	
	public boolean isAllowedJoinWithOwnInventory() {
		return allowinventory;
	}
	
	public GameTime getGameTime() {
		return gt;
	}
	
	public int getCountDownStart() {
		return countdownstart;
	}
	
	public int getToxicZoneRange() {
		return toxiczonerange;
	}
	
	public int getPointsPerKills() {
		return pointsperkills;
	}
	
	public int getPointsPerDeads() {
		return pointsperdeads;
	}
	
	public int getPointsPerRevive() {
		return pointsperrevive;
	}
	
	public int getPointsPerHelpRevive() {
		return pointsperhelprevive;
	}
	
	public int getPointsBonus() {
		return pointsbonus;
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
	
	public void setDispenserRange(int dispenserrange) {
		this.dispenserrange = dispenserrange;
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

	public void setStopMotive(StopMotive motivo) {
		this.motivo = motivo;
	}
	
	public void setStopReason(String stopreason) {
		this.stopreason = stopreason;
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
	
	public void setBarriersinMap(boolean barriers) {
		this.barriers = barriers;
	}
	
	public void setAllowedJoinWithOwnInventory(boolean allowinventory) {
		this.allowinventory = allowinventory;
	}
	
	public void setGameTime(GameTime gt) {
		this.gt = gt;
	}
	
	public void setCountDownStart(int countdownstart) {
		this.countdownstart = countdownstart;
	}
	
	public void setToxicZoneRange(int toxiczonerange) {
		this.toxiczonerange = toxiczonerange;
	}
	
	public void setPointsPerKills(int pointsperkills) {
		this.pointsperkills = pointsperkills;
	}
	
	public void setPointsPerDeads(int pointsperdeads) {
		this.pointsperdeads = pointsperdeads;
	}
	
	public void setPointsPerRevive(int pointsperrevive) {
		this.pointsperrevive = pointsperrevive;
	}
	
	public void setPointsPerHelpRevive(int pointsperhelprevive) {
		this.pointsperhelprevive = pointsperhelprevive;
	}
	
	public void setPointsBonus(int pointsbonus) {
		this.pointsbonus = pointsbonus;
	}
	
	public String ShowGame() {
		return getMapName()+" "+getMaxPlayers()+" "+getMinPlayers()+" "+getGameType().toString()+" "+getGameStatus().toString()+" "+
	getStopMotive().toString()+" "+getBossbar().getTitle()+" "+getTimeMg();
	}
	

}
