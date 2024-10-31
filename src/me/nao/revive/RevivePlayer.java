package me.nao.revive;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.EulerAngle;

import me.nao.enums.Items;
import me.nao.enums.ReviveStatus;
import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Minegame;
import me.nao.manager.GameIntoMap;

public class RevivePlayer{

	private Player player;
	private int value ;
	private int time;
	private Entity e;
	private DamageCause cause;
	private Minegame plugin;
	private ReviveStatus rv;
	private int taskID;
	private ArmorStand armor ;
	
	public RevivePlayer(Player player, int value, int time,ReviveStatus rv, Entity e,DamageCause cause,ArmorStand armor,Minegame plugin) {
		this.player = player;
		this.value = value;
		this.time = time;
		this.e = e;
		this.cause = cause;
		this.plugin = plugin;
		this.rv = rv;
		this.armor = armor;
	

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
	
	public ArmorStand getArmorStand() {
		return armor;
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
			if(ga.getAlivePlayers().size() == ga.getKnockedPlayers().size() && !hasPlayersAutoreviveItem()) {
				return true;
			}
		}
		return false;
	}
	
	public void Dead(Entity e, DamageCause cause) {
		GameIntoMap mig = new GameIntoMap(plugin);
		
		if(e != null) {
			mig.GameMobDamagerCauses(player, e);
			return;
		}else {
			mig.GameDamageCauses(player,cause);
		}
		getArmorStand().remove();
		removeToRevive();
		
	}
	
	public void Knocked() {
		
		GameConditions gc = new GameConditions(plugin);
		//Block b = player.getLocation().getBlock();
		//Block r = b.getRelative(0, 1, 0);
		
	    //player.sendBlockChange(r.getLocation(), Material.BARRIER.createBlockData());
		
		ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwningPlayer(player);
		head.setItemMeta(meta);
		
		PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING,/*duration*/ 30 * 20,/*amplifier:*/10, true ,true,true );

		ArmorStand as = getArmorStand();
		as.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"CADAVER DE "+ChatColor.GREEN+ChatColor.BOLD+player.getName());
		as.setCustomNameVisible(true);
		as.setBasePlate(false);
		as.setArms(true);
		//as.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
		//as.setBodyPose(new EulerAngle(360,0,0));
		as.setHeadPose(new EulerAngle(90,0,0));
		as.setLeftArmPose(new EulerAngle(295,360,0));
		as.setRightArmPose(new EulerAngle(299,360,0));
		as.setLeftLegPose(new EulerAngle(279,346,0));
		as.setRightLegPose(new EulerAngle(281,23,0));
		as.getEquipment().setHelmet(head);
		as.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		as.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		as.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		as.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		as.setGravity(false);
		if(player.getInventory().containsAtLeast(Items.REVIVEP.getValue(),1) || player.getInventory().getItemInOffHand().isSimilar(Items.REVIVEP.getValue())) {
			player.sendMessage(ChatColor.AQUA+"AutoRevive: "+ChatColor.GREEN+"Manten pulsado el Click Derecho sobre tu Cadaver para Autorevivirte o espera a un Compañero.");
			as.getEquipment().setItemInOffHand(Items.REVIVEP.getValue());
		}else {
			player.sendMessage(ChatColor.RED+"Nota: "+ChatColor.YELLOW+"Debes esperar a que un Compañero tuyo te reviva.");
			as.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
		}
		as.addPotionEffect(glow);
		setColor(as);
		
		player.setGameMode(GameMode.SPECTATOR);
		addToRevive();
		System.out.println("TODOS NOQUEADOS: "+isAllKnocked()+ " TIENE ITEM DE REVIVIR: "+hasPlayersAutoreviveItem());
		Start();
		gc.SendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+ player.getName()+ChatColor.YELLOW+" fue Derribado. (Ayudalo a levantarse tiene solo %time%)".replace("%time%",time+"s"));
		player.sendMessage("");
		player.sendMessage(ChatColor.YELLOW+"Has sido Derribado.");
		player.sendMessage("");
	}
	
	public void StandUp() {
		
		//Block b = player.getLocation().getBlock();
		//Block r = b.getRelative(0, 1, 0);
		
		//player.sendBlockChange(r.getLocation(),r.getLocation().getBlock().getBlockData());
		player.setGameMode(GameMode.ADVENTURE);
		player.teleport(getArmorStand().getLocation());
		getArmorStand().remove();
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
		plugin.getPlayerKnocked().remove(player);
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			if(ga.getKnockedPlayers().remove(player.getName()));
			System.out.println("L2: "+ga.getKnockedPlayers());
			
		}
	
}
	
	public boolean isDangerZone() {
		Block b = getArmorStand().getLocation().getBlock();
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
					if(targets.getInventory().containsAtLeast(Items.REVIVEP.getValue(),1) || targets.getInventory().getItemInOffHand().isSimilar(Items.REVIVEP.getValue())) {
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
					player.playEffect(getArmorStand().getLocation().add(0,0,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK); 
				}else if(getReviveStatus() == ReviveStatus.HEALING) {
					setReviveStatus(ReviveStatus.BLEEDING);
				}
				
				
				
			
				
			}
		},0L,20);
	}
	

	public void setColor(Entity e) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		scoreboard.registerNewTeam("Example");
		Team team = scoreboard.getTeam("Example");
		team.setColor(ChatColor.YELLOW);
		team.addEntry(e.getUniqueId().toString());
	
		return;
	}
	


	
	
	
}
