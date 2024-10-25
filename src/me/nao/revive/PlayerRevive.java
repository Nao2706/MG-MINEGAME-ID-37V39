package me.nao.revive;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Minegame;
import me.nao.manager.MapIntoGame;
import me.nao.shop.Items;

public class PlayerRevive{

	private Player player;
	private int value ;
	private int time;
	private Entity e;
	private DamageCause cause;
	private Minegame plugin;
	private ReviveStatus rv;
	private int taskID;
	private List<Location> loc ;
	
	public PlayerRevive(Player player, int value, int time,ReviveStatus rv, Entity e,DamageCause cause,List<Location> loc,Minegame plugin) {
		this.player = player;
		this.value = value;
		this.time = time;
		this.e = e;
		this.cause = cause;
		this.plugin = plugin;
		this.rv = rv;
		this.loc = loc;
		//runTaskTimer(JavaPlugin.getPlugin(Minegame.class),0,3);

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
	
	public List<Location> getLocationsChange() {
		return loc;
	}

	public void setReviveStatus(ReviveStatus rv) {
		this.rv = rv;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	public void addLocation(Location l) {
		List<Location> list = getLocationsChange();
		
		if(!list.contains(l)) {
			list.add(l);
		}
	}
	
	public void Restore() {
		List<Location> list = getLocationsChange();
		
		for(Location locs : list) {
			player.sendBlockChange(locs, locs.getBlock().getBlockData());
		}
	}
	
	public boolean isAllKnocked() {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			if(ga.getVivo().size() == ga.getKnockedPlayers().size() && !hasPlayersAutoreviveItem()) {
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
		PotionEffect lent = new PotionEffect(PotionEffectType.JUMP,/*duration*/ 999 * 20,/*amplifier:*/150, true ,true,true );
		PotionEffect jump = new PotionEffect(PotionEffectType.SLOW,/*duration*/ 999 * 20,/*amplifier:*/20, true ,true,true );
		
		player.addPotionEffect(lent);
		player.addPotionEffect(jump);
		addToRevive();
		System.out.println("TODOS NOQUEADOS: "+isAllKnocked()+ " TIENE ITEM DE REVIVIR: "+hasPlayersAutoreviveItem());
		Start();
		player.sendMessage("");
		player.sendMessage(ChatColor.YELLOW+"Has sido Derribado.");
		player.sendMessage("");
	}
	
	public void StandUp() {
		
		Block b = player.getLocation().getBlock();
		Block r = b.getRelative(0, 1, 0);
		
		player.sendBlockChange(r.getLocation(),r.getLocation().getBlock().getBlockData());
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
		removeToRevive();
		
	}
	
	
	
	public void addToRevive() {
	
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				if(!ga.getKnockedPlayers().contains(player.getName())) {
					ga.getKnockedPlayers().add(player.getName());
				} 
				
				System.out.println("L1: "+ga.getKnockedPlayers());
			}
		
	}
	
	public void removeToRevive() {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			Restore();
			if(ga.getKnockedPlayers().remove(player.getName()));
			System.out.println("L2: "+ga.getKnockedPlayers());
			plugin.getPlayerKnocked().remove(player);
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
	
	public boolean hasPlayersAutoreviveItem() {
		
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		List<Player> hasitem = new ArrayList<>();
		
		
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			GameConditions gc = new GameConditions(plugin);
			
			List<Player> pr = gc.ConvertStringToPlayer(ga.getKnockedPlayers());
		
			if(!pr.isEmpty()) {
				for(Player targets  : pr) {
					if(targets.getInventory().containsAtLeast(Items.REVIVE.getValue(),1)) {
						hasitem.add(targets);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	
	public boolean reviveStoped() {
		
		if(getReviveStatus() != ReviveStatus.REVIVED) {
			 if(time == 0) {
					System.out.println("TIEMPO 0");
					return true;
				}else if(isAllKnocked()) {
					System.out.println("TODOS NOQUEADOS");
					return true;
				}else if(isDangerZone()) {
					System.out.println("ZONA PELIGROSA");
					return true;
				}
		}
	
		return false;
	}
	
	public void Start() {
		
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
	    
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			@Override
			public void run() {
				
				
				if(getReviveStatus() == ReviveStatus.REVIVED) {
					Bukkit.getScheduler().cancelTask(taskID);	
					StandUp();
				}
				
			   if(reviveStoped() || player == null || !player.isOnline()) {
					Bukkit.getScheduler().cancelTask(taskID);	
					Dead(e, cause);
				}else if(getReviveStatus() == ReviveStatus.BLEEDING) {
					time--;
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"SANGRANDO",""+ChatColor.YELLOW+ChatColor.BOLD+"Moriras en "+ChatColor.RED+ChatColor.BOLD+time, 0, 20, 0);
					player.playEffect(player.getLocation().add(0,0,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK); 
				}else if(getReviveStatus() == ReviveStatus.HEALING) {
					setReviveStatus(ReviveStatus.BLEEDING);
				}
				
				
				
			
				
			}
		},0L,20);
	}
	

	

	
	
	
}
