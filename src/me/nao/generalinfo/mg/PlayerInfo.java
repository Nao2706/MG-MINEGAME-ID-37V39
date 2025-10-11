package me.nao.generalinfo.mg;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.nao.enums.mg.PlayerGameStatus;
import me.nao.main.mg.Minegame;

public class PlayerInfo {
	
	private Minegame plugin;
	private boolean isinventorysave;
	private PlayerGameStatus pgs;
	private Player player;
	private Collection<PotionEffect> potions;
	private ItemStack[] inv;
	private GameMode gamemode;
	private boolean fly;
	private double vida;
	private double maxvida,scale;
	private int comida;
	private int lvlxp , mglvl, mgprestige;
	private float xp;
	private Location l;
	private String map;
	private GamePoints gp;
	private String teamname;
	private Set<String> tags;
	private SystemOfLevels sl;
	private Location checkpoint,itemcheckpoint,respawn;
	private RespawnLife respawnlife;
	private MapRecords tr;
	private Entity creditkill;

	
	/** 
	 *Crear Objeto PlayerInfo para poder definir si en un juego este entrara sin sus cosas(inventario , vida , posiones , exp ) variante que guarda su inventario y otra informacion.
	 *
	 *@param Minegame plugin ,boolean isinventorysave,Player player, Collection<PotionEffect> potions, ItemStack[] inv, GameMode gamemode, boolean fly, double vida, double maxvida,int comida, int lvlxp, float xp,Location l,String nameMision,GamePoints gp
	 *
	 * */
	
	public PlayerInfo(Minegame plugin ,boolean isinventorysave,Player player,Location l,String map,GamePoints gp,PlayerGameStatus pgs) {
	
		if(isinventorysave) {
			this.pgs = pgs;
			this.plugin = plugin;
			this.isinventorysave = isinventorysave;
			this.player = player;
			this.gamemode = player.getGameMode();
			this.scale = player.getAttribute(Attribute.SCALE).getValue();
			this.fly = player.isFlying();
			this.l = l;
			this.map = map;
			this.gp = gp;
			this.teamname = "NINGUNO";
			this.mglvl = 0;
			this.tags = player.getScoreboardTags();
		
		}else{
			this.pgs = pgs;
			this.plugin = plugin;
			this.isinventorysave = isinventorysave;
			this.player = player;
			this.potions = player.getActivePotionEffects();
			this.inv = player.getInventory().getContents();
			this.gamemode = player.getGameMode();
			this.fly = player.isFlying();
			this.vida = player.getHealth();
			this.maxvida = player.getAttribute(Attribute.MAX_HEALTH).getValue();
			this.scale = player.getAttribute(Attribute.SCALE).getValue();
			this.comida = player.getFoodLevel();
			this.lvlxp = player.getLevel();
			this.xp = player.getExp();
			this.l = l;
			this.map = map;
			this.gp = gp;
			this.teamname = "NINGUNO";
			this.mglvl = 0;
			this.mgprestige = 0;
			this.tags = player.getScoreboardTags();
	
		}
		
		this.checkpoint = null;
		this.itemcheckpoint = null;
		this.respawn = null;
		this.respawnlife = null;
		this.creditkill = null;
		tr = null;
		sl = null;
		
	}
	
	
	public PlayerGameStatus getPlayerGameStatus() {
		return pgs;
	}
	
	public boolean isInventoryAllowedForTheMap() {
		return isinventorysave;
	}

	public Player getPlayerMG() {
		return player;
	}
	
	public Collection<PotionEffect> getPlayerPottionsMg() {
		return potions;
	}

	public ItemStack[] getInventoryMG() {
		return inv;
	}

	public GameMode getGamemodeMG() {
		return gamemode;
	}

	public boolean isFlyMG() {
		return fly;
	}

	public double getLifeMG() {
		return vida;
	}

	public double getMaxLifeMG() {
		return maxvida;
	}
	
	public double getScaleMG() {
		return scale;
	}

	public int getFoodMG() {
		return comida;
	}

	public int getLvlXpMG() {
		return lvlxp;
	}

	public float getXpMG() {
		return xp;
	}
	
	public Location getLocationMG() {
		return l;
	}
	
	public Location getCheckPointMarker() {
		return checkpoint;
	}
	
	public Location getCheckPointMarkerPosition() {
		if(this.checkpoint != null) {
		  return new Location(Bukkit.getWorld(checkpoint.getWorld().getName()),checkpoint.getBlockX(),checkpoint.getBlockY(),checkpoint.getBlockZ());
		}
		return null;
	}
	
	public Location getCheckPointItem() {
		return itemcheckpoint;
	}
	
	public Location getRespawn() {
		
		return respawn;
	}
	
	public Location getRespawnPosition() {
		
		if(this.respawn != null) {
			return new Location(Bukkit.getWorld(respawn.getWorld().getName()),respawn.getBlockX(),respawn.getBlockY(),respawn.getBlockZ());
		}
		return null;
	}
	
	public RespawnLife getRespawnLife() {
		
		return respawnlife;
	}
	
	public String getMapName() {
		return map;
	}
	
	public GamePoints getGamePoints() {
		return gp;
	}
	
	public String getTeamName() {
		return teamname;
	}
	
	public MapRecords getPlayerCronomet() {
		return tr;
	}
	
	public int getMgPlayerLvl() {
		return mglvl;
	}
	
	public int getMgPlayerPrestige() {
		return mgprestige;
	}
	
	public Set<String> getBackAllTagsMg(){
		return tags;
	}
	
	public SystemOfLevels getSl() {
		return sl;
	}

	public Entity getCreditKillMob() {
		return creditkill;
	}
	
	public void setPlayerMG(Player player) {
		this.player = player;
	}

	public void setInvMG(ItemStack[] inv) {
		this.inv = inv;
	}

	public void setGamemodeMG(GameMode gamemode) {
		this.gamemode = gamemode;
	}
	
	public void setPlayerGameStatus(PlayerGameStatus status) {
		this.pgs = status;
	}

	public void setFlyMG(boolean fly) {
		this.fly = fly;
	}

	public void setVidaMG(double vida) {
		this.vida = vida;
	}

	public void setMaxvidaMG(double maxvida) {
		this.maxvida = maxvida;
	}
	
	public void setScaleMG(double scale) {
		this.scale = scale;
	}

	public void setComidaMG(int comida) {
		this.comida = comida;
	}

	public void setLvlxpMG(int lvlxp) {
		this.lvlxp = lvlxp;
	}

	public void setXpMG(float xp) {
		this.xp = xp;
	}
	
	public void setLocationMg(Location lo) {
		this.l = lo;
	}
	
	public void setCheckpointLocationMg(Location checkpoint) {
		this.checkpoint = checkpoint;
	}
	
	public void setCheckpointItemLocationMg(Location itemcheckpoint) {
		this.itemcheckpoint = itemcheckpoint;
	}
	
	public void setRespawnLocationMg(Location respawn) {
		this.respawn = respawn;
	}
	
	public void setRespawnLifeLocationMg(RespawnLife respawnlife) {
		this.respawnlife = respawnlife;
	}
	
	public void setMisionName(String map) {
		this.map = map;
	}
	
	public void setTeamName(String teamname) {
		this.teamname = teamname;
	}
	
	public void setPlayerCronomet(MapRecords tr) {
		this.tr = tr;
	}
	
	public void setMgPlayerLvl(int value) {
		this.mglvl = value;
	}
	
	public void setMgPlayerprestige(int value) {
		this.mgprestige = value;
	}
	
	public void setSl(SystemOfLevels sl) {
		this.sl = sl;
	}
	
	public void setCreditKillMob(Entity creditkill) {
		 this.creditkill = creditkill;
	}


	public void clearAllPlayerMg() {
		
		int lvl = 0 ;
		int prestigee = 0 ;
		FileConfiguration points = plugin.getPoints();
		if(points.contains("Players."+player.getName())) {
			int lvlplayer = points.getInt("Players."+player.getName()+".Level");
			int prestigeplayer = points.getInt("Players."+player.getName()+".Prestige");
			lvl = lvlplayer;
			prestigee = prestigeplayer;
		}
		
		setMgPlayerLvl(lvl);
		setMgPlayerprestige(prestigee);
		player.getScoreboardTags().clear();
		player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
		player.getAttribute(Attribute.SCALE).setBaseValue(1);
		player.setExp(0);
		player.setLevel(0);
		player.getInventory().clear();
		player.setFoodLevel(20);
		player.setFlying(false);
		player.setHealth(20);
		player.setGameMode(GameMode.ADVENTURE);
		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		
	}
	
	public void clearGamemodePlayerMg() {
		int lvl = 0 ;
		int prestigee = 0 ;
		FileConfiguration points = plugin.getPoints();
		if(points.contains("Players."+player.getName())) {
			int lvlplayer = points.getInt("Players."+player.getName()+".Level");
			int prestigeplayer = points.getInt("Players."+player.getName()+".Prestige");
			lvl = lvlplayer;
			prestigee = prestigeplayer;
		}
		
		setMgPlayerLvl(lvl);
		setMgPlayerprestige(prestigee);
		player.getScoreboardTags().clear();
		player.setFlying(false);
		player.setGameMode(GameMode.ADVENTURE);
	}
	
	
	public void restoreAllPlayerMg() {
		//PlayerInfo pl = plugin.getPlayerInfoPoo().get(playerMg);
		
		 player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		 
		 player.setGameMode(getGamemodeMG());
		 player.setFlying(isFlyMG());
		 player.getInventory().setContents(getInventoryMG());
		 player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(getMaxLifeMG());
		 player.getAttribute(Attribute.SCALE).setBaseValue(getScaleMG());
		 player.setFoodLevel(getFoodMG());
		 player.addPotionEffects(getPlayerPottionsMg());
		 player.setExp(getXpMG());
		 player.setLevel(getLvlXpMG());
		 player.setHealth(getLifeMG());
		 player.getScoreboardTags().addAll(getBackAllTagsMg());
	}
	
	public void restoreGamemodePlayerMg() {
		 //PlayerInfo pl = plugin.getPlayerInfoPoo().get(playermg);
		 player.setGameMode(getGamemodeMG());
		 player.getAttribute(Attribute.SCALE).setBaseValue(getScaleMG());
		 player.setFlying(isFlyMG());
		 player.getScoreboardTags().addAll(getBackAllTagsMg());
		
	}
	

}
