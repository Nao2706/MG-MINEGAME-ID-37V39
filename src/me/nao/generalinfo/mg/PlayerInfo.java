package me.nao.generalinfo.mg;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.nao.main.mg.Minegame;

public class PlayerInfo {
	
	private Minegame plugin;
	private boolean isinventorysave;
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
	
	/** 
	 *Crear Objeto PlayerInfo para poder definir si en un juego este entrara sin sus cosas(inventario , vida , posiones , exp ) variante que guarda su inventario y otra informacion.
	 *
	 *@param Minegame plugin ,boolean isinventorysave,Player player, Collection<PotionEffect> potions, ItemStack[] inv, GameMode gamemode, boolean fly, double vida, double maxvida,int comida, int lvlxp, float xp,Location l,String nameMision,GamePoints gp
	 *
	 * */
	
	public PlayerInfo(Minegame plugin ,boolean isinventorysave,Player player,Location l,String map,GamePoints gp) {
	
		if(isinventorysave) {
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
		}else {
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
		}
		
		
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
	
	public String getMapName() {
		return map;
	}
	
	public GamePoints getGamePoints() {
		return gp;
	}
	
	public String getTeamName() {
		return teamname;
	}
	
	public int getMgPlayerLvl() {
		return mglvl;
	}
	
	public int getMgPlayerPrestige() {
		return mgprestige;
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
	
	public void setMisionName(String map) {
		this.map = map;
	}
	
	public void setTeamName(String teamname) {
		this.teamname = teamname;
	}
	
	public void setMgPlayerLvl(int value) {
		this.mglvl = value;
	}
	
	public void setMgPlayerprestige(int value) {
		this.mgprestige = value;
	}
	
	public void ClearAllPlayerMg() {
		
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
	
	public void ClearGamemodePlayerMg() {
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
		player.setFlying(false);
		player.setGameMode(GameMode.ADVENTURE);
	}
	
	
	public void RestoreAllPlayerMg(Player playerMg) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(playerMg);
		
		 player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		 
		 player.setGameMode(pl.getGamemodeMG());
		 player.setFlying(pl.isFlyMG());
		 player.getInventory().setContents(pl.getInventoryMG());
		 player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(pl.getMaxLifeMG());
		 player.getAttribute(Attribute.SCALE).setBaseValue(pl.getScaleMG());
		 player.setFoodLevel(pl.getFoodMG());
		 player.addPotionEffects(pl.getPlayerPottionsMg());
		 player.setExp(pl.getXpMG());
		 player.setLevel(pl.getLvlXpMG());
		 player.setHealth(pl.getLifeMG());
	}
	
	public void RestoreGamemodePlayerMg(Player playermg) {
		 PlayerInfo pl = plugin.getPlayerInfoPoo().get(playermg);
		 player.setGameMode(pl.getGamemodeMG());
		 player.getAttribute(Attribute.SCALE).setBaseValue(pl.getScaleMG());
		 player.setFlying(pl.isFlyMG());
		 
		
	}
	

}
