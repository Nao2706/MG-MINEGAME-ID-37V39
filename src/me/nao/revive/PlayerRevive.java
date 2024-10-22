package me.nao.revive;



import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameInfo;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Minegame;
import me.nao.manager.MapIntoGame;

public class PlayerRevive extends BukkitRunnable{

	private Player player;
	private int value ;
	private int time;
	private Entity e;
	private DamageCause cause;
	private Minegame plugin;
	private ReviveStatus rv;
	
	public PlayerRevive(Player player, int value, int time,ReviveStatus rv, Entity e, DamageCause cause,Minegame plugin) {
		this.player = player;
		this.value = value;
		this.time = time;
		this.e = e;
		this.cause = cause;
		this.plugin = plugin;
		this.rv = rv;
		runTaskTimer(JavaPlugin.getPlugin(Minegame.class),0,3);

	}

	public Player getPlayer() {
		return player;
	}

	public int getValue() {
		return value;
	}

	public Entity getEntity() {
		return e;
	}

	public DamageCause getCause() {
		return cause;
	}
	
	public ReviveStatus getReviveStatus() {
		return rv;
	}

	public void setReviveStatus(ReviveStatus rv) {
		this.rv = rv;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	public boolean isAllKnocked() {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			if(ga.getVivo().size() == ga.getKnockedPlayers().size()) {
				return true;
			}
		}
		return false;
	}
	
	public void Dead(Entity e, DamageCause cause) {
		MapIntoGame mig = new MapIntoGame(plugin);
		if(e != null) {
			mig.GameMobDamagerCauses(player, e);
			return;
		}else {
			mig.GameDamageCauses(player,cause);
		}
		plugin.getPlayerKnocked().remove(player);
		if(player.hasPotionEffect(PotionEffectType.JUMP)) {
			player.removePotionEffect(PotionEffectType.SLOW);
			player.removePotionEffect(PotionEffectType.JUMP);
		}
		removeToRevive();
	}
	
	public void Knocked() {
		Block b = player.getLocation().getBlock();
		Block r = b.getRelative(0, 1, 0);
		
		player.sendBlockChange(r.getLocation(), Material.BARRIER.createBlockData());
		player.setInvulnerable(true);
		PotionEffect lent = new PotionEffect(PotionEffectType.JUMP,/*duration*/ 10 * 20,/*amplifier:*/150, true ,true,true );
		PotionEffect jump = new PotionEffect(PotionEffectType.SLOW,/*duration*/ 10 * 20,/*amplifier:*/100, true ,true,true );
		player.addPotionEffect(lent);
		player.addPotionEffect(jump);
		addToRevive();
	}
	
	public void StandUp() {
		Block b = player.getLocation().getBlock();
		Block r = b.getRelative(0, 1, 0);
		
		player.sendBlockChange(r.getLocation(), Material.AIR.createBlockData());
		player.setInvulnerable(false);
		PotionEffect vid = new PotionEffect(PotionEffectType.REGENERATION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		PotionEffect comida = new PotionEffect(PotionEffectType.SATURATION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		PotionEffect abso = new PotionEffect(PotionEffectType.ABSORPTION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		player.addPotionEffect(vid);
		player.addPotionEffect(comida);
		player.addPotionEffect(abso);
		
		if(player.hasPotionEffect(PotionEffectType.JUMP)) {
			player.removePotionEffect(PotionEffectType.SLOW);
			player.removePotionEffect(PotionEffectType.JUMP);
		}
		
		plugin.getPlayerKnocked().remove(player);
	}
	
	
	public void addToRevive() {
	
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				if(ga.getKnockedPlayers().contains(player.getName())) return;
				ga.getKnockedPlayers().add(player.getName());
			}
		
	}
	
	public void removeToRevive() {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			if(ga.getKnockedPlayers().remove(player.getName())) return;
		
		}
	
}
	
	public boolean isDangerZone() {
		Block b = player.getLocation().getBlock();
		Block r = b.getRelative(0, 0, 0);
		Block r2 = b.getRelative(0, -1, 0);
		
		if(r.getType() == Material.WATER || r.getType() == Material.LAVA || r2.getType() == Material.BARRIER) {
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		
		if(value == 100) {
			this.cancel();
			StandUp();
		}else if(time == 0 || isAllKnocked() || player == null || !player.isOnline() || isDangerZone()) {
			this.cancel();
			Dead(e, cause);
		}else if(getReviveStatus() == ReviveStatus.BLEEDING) {
			time--;
			player.sendTitle(ChatColor.YELLOW+"Moriras en ",""+ChatColor.RED+time, 20, 20, 20);
			player.playEffect(player.getLocation().add(0,0,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK); 
		}else if(getReviveStatus() == ReviveStatus.HEALING) {
			setReviveStatus(ReviveStatus.BLEEDING);
		}
		
		
		
	
		
	}
	
	
	
}
