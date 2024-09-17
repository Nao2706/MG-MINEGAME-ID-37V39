package me.nao.general.info;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.nao.main.game.Minegame;

public class PlayerInfo {
	
	private Minegame plugin;
	private boolean isinventorysave;
	private Player player ;
	private Collection<PotionEffect> potion ;
	private ItemStack[] inv;
	private GameMode gamemode;
	private boolean fly;
	private double vida;
	private double maxvida;
	private int comida;
	private int lvlxp;
	private float xp;
	private Location l;
	private boolean isMision;
	private String namemision;
	private GamePoints gp;
	
	public PlayerInfo(Minegame plugin ,boolean isinventorysave,Player player, Collection<PotionEffect> potion, ItemStack[] inv, GameMode gamemode, boolean fly, double vida, double maxvida,int comida, int lvlxp, float xp,Location l,boolean ismision,String nameMision,GamePoints gp) {
	
		this.plugin = plugin;
		this.isinventorysave = isinventorysave;
		this.player = player;
		this.potion = potion;
		this.inv = inv;
		this.gamemode = gamemode;
		this.fly = fly;
		this.vida = vida;
		this.maxvida = maxvida;
		this.comida = comida;
		this.lvlxp = lvlxp;
		this.xp = xp;
		this.l = l;
		this.isMision = ismision;
		this.namemision = nameMision;
		this.gp = gp;
		
	}
	
	public PlayerInfo(Minegame plugin ,boolean isinventorysave,Player player, GameMode gamemode, boolean fly,Location l,boolean ismision,String nameMision,GamePoints gp) {
		
		this.plugin = plugin;
		this.isinventorysave = isinventorysave;
		this.player = player;
		this.gamemode = gamemode;
		this.fly = fly;
		this.l = l;
		this.isMision = ismision;
		this.namemision = nameMision;
		this.gp = gp;
		
	}
	
	
	public boolean hasPlayerMoreInfo() {
		return isinventorysave;
	}

	public Player getPlayerMG() {
		return player;
	}
	
	public Collection<PotionEffect> getPlayerPottionsMg() {
		return potion;
	}

	public ItemStack[] getInvMG() {
		return inv;
	}

	public GameMode getGamemodeMG() {
		return gamemode;
	}

	public boolean isFlyMG() {
		return fly;
	}

	public double getVidaMG() {
		return vida;
	}

	public double getMaxvidaMG() {
		return maxvida;
	}

	public int getComidaMG() {
		return comida;
	}

	public int getLvlxpMG() {
		return lvlxp;
	}

	public float getXpMG() {
		return xp;
	}
	
	public Location getLocationMG() {
		return l;
	}
	
	public boolean isStillPlayerInMision() {
		return isMision;
	}
	
	public String getMapName() {
		return namemision;
	}
	
	public GamePoints getGamePoints() {
		return gp;
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
	
	public void setPlayerMision(boolean mision) {
		this.isMision = mision;
	}
	
	public void setMisionName(String nameMision) {
		this.namemision = nameMision;
	}
	
	
	
	public void ClearAllPlayerMg() {
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
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
		player.setFlying(false);
		player.setGameMode(GameMode.ADVENTURE);
	}
	
	
	public void RestoreAllPlayerMg(Player playerMg) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(playerMg);
		
		 player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		 
		 player.setGameMode(pl.getGamemodeMG());
		 player.setFlying(pl.isFlyMG());
		 player.getInventory().setContents(pl.getInvMG());
		 player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(pl.getMaxvidaMG());
		 player.setFoodLevel(pl.getComidaMG());
		 player.addPotionEffects(pl.getPlayerPottionsMg());
		 player.setExp(pl.getXpMG());
		 player.setLevel(pl.getLvlxpMG());
		 player.setHealth(pl.getVidaMG());
	}
	
	public void RestoreGamemodePlayerMg(Player playermg) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(playermg);
		 player.setGameMode(pl.getGamemodeMG());
		 
		 player.setFlying(pl.isFlyMG());
		 
		
	}
	

}
