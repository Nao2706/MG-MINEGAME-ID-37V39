package me.nao.revive;




import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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

import me.nao.enums.GameStatus;
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

	public int getRemainingTimeLife() {
		return time;
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
	
	public void Dead(Entity e, DamageCause cause) {
		GameIntoMap mig = new GameIntoMap(plugin);
		
		if(e != null) {
			mig.GameMobDamagerCauses(player, e);
		}else {
			mig.GameDamageCauses(player,cause);
		}
		removeToRevive();
		return;
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
		
		PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING,/*duration*/ 300 * 20,/*amplifier:*/10, true ,true,true );

		ArmorStand as = getArmorStand();
		
		as.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"CADAVER DE "+ChatColor.GREEN+ChatColor.BOLD+player.getName());
		as.setCustomNameVisible(true);
		as.setBasePlate(false);
		as.setArms(true);
		as.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
		//as.setBodyPose(new EulerAngle(360,0,0));
		as.setHeadPose(new EulerAngle(Math.toRadians(32f),Math.toRadians(0f),Math.toRadians(0f)));
		as.setLeftArmPose(new EulerAngle(Math.toRadians(295f),Math.toRadians(360f),Math.toRadians(0f)));
		as.setRightArmPose(new EulerAngle(Math.toRadians(299f),Math.toRadians(360f),Math.toRadians(0f)));
		as.setLeftLegPose(new EulerAngle(Math.toRadians(279f),Math.toRadians(346f),Math.toRadians(0f)));
		as.setRightLegPose(new EulerAngle(Math.toRadians(281f),Math.toRadians(23f),Math.toRadians(0f)));
		
		as.getEquipment().setHelmet(head);
		as.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		as.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		as.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		as.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		as.setGravity(true);
		as.setInvulnerable(true);
		
		if(player.getInventory().containsAtLeast(Items.REVIVEP.getValue(),1) || player.getInventory().getItemInOffHand().isSimilar(Items.REVIVEP.getValue())) {
			player.sendMessage(ChatColor.AQUA+"AutoRevive: "+ChatColor.GREEN+"Manten pulsado el Click Derecho sobre tu Cadaver para Autorevivirte o espera a un Compañero.");
			as.getEquipment().setItemInOffHand(Items.REVIVEP.getValue());
		}else {
			player.sendMessage(ChatColor.RED+"Nota: "+ChatColor.YELLOW+"Debes esperar a que un Compañero tuyo te reviva.");
			as.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
		}
		as.addPotionEffect(glow);
		
		
		player.setGameMode(GameMode.SPECTATOR);
		addToRevive();
		//System.out.println("TODOS NOQUEADOS: "+isAllKnocked()+ " TIENE ITEM DE REVIVIR: "+hasPlayersAutoreviveItem());
		Start();
		gc.sendMessageToUsersOfSameMapLessPlayer(player,""+ChatColor.RED+ChatColor.BOLD+ player.getName()+ChatColor.YELLOW+" fue Derribado. (Ayudalo a levantarse tiene solo %time%)".replace("%time%",time+"s"));
		
		player.sendMessage("");
		if(e != null) {
			if(e instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) e;
				if(le.getCustomName() != null) {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado por: "+ChatColor.GOLD+le.getCustomName());
					gc.SendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+le.getCustomName());
					
				}else {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado por: "+ChatColor.GOLD+le.getType());
					gc.SendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+le.getType());
					
				}
			}else if(e instanceof Projectile) {
				
				Projectile p =(Projectile) e;
				if(p.getCustomName() != null) {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado por: "+ChatColor.GOLD+p.getCustomName());
					gc.SendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+p.getCustomName());
					
				}else {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado por: "+ChatColor.GOLD+p.getType());
					gc.SendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+p.getType());
			
				}
				
			}

		}else{
			if(cause != null) {
				player.sendMessage(ChatColor.YELLOW+"Has sido Derribado por: "+ChatColor.GOLD+causeToSpanish(cause));
				gc.SendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+causeToSpanish(cause));
				
			}
		}

		
	
		player.sendMessage("");

	}
	
	public void StandUp() {
		
		player.setGameMode(GameMode.ADVENTURE);
		player.teleport(getArmorStand().getLocation());
	
		removeToRevive();
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
		
		
	}
	
	public String causeToSpanish(DamageCause cause) {
		String causes = "";
		if(cause == DamageCause.BLOCK_EXPLOSION) {
			causes = "POR UNA EXPLOSION DE BLOQUE";
		}else if(cause == DamageCause.CONTACT) {
			causes = "POR CONTACTO";
		}else if(cause == DamageCause.CRAMMING) {
			causes = "POR ASFIXIA ENTRE MUCHAS ENTIDADES";
		}else if(cause == DamageCause.CUSTOM) {
			causes = "POR DAÑO CUSTOM";
		}else if(cause == DamageCause.DROWNING) {
			causes = "POR AHOGARSE";
		}else if(cause == DamageCause.FALL) {
			causes = "POR UNA CAIDA";
		}else if(cause == DamageCause.FIRE) {
			causes = "POR FUEGO";
		}else if(cause == DamageCause.FLY_INTO_WALL) {
			causes = "POR VOLAR CONTRA UNA PARED";
		}else if(cause == DamageCause.FREEZE) {
			causes = "POR CONGELAMIENTO";
		}else if(cause == DamageCause.HOT_FLOOR) {
			causes = "POR UN PISO CALIENTE";
		}else if(cause == DamageCause.KILL) {
			causes = "POR COMANDO /KILL";
		}else if(cause == DamageCause.LAVA) {
			causes = "POR LAVA";
		}else if(cause == DamageCause.LIGHTNING) {
			causes = "POR UN RAYO";
		}else if(cause == DamageCause.MAGIC) {
			causes = "POR MAGIA";
		}else if(cause == DamageCause.STARVATION) {
			causes = "POR HAMBRE";
		}else if(cause == DamageCause.SUFFOCATION) {
			causes = "POR SOFOCACION";
		}else {
			causes = "ALGO QUE NO ESTA EN LISTA "+cause.toString()+" (SI VES ESTO REPORTALO)";
		}
		return causes;
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
			getArmorStand().remove();
			System.out.println("REMOVIDO "+player.getName());
			plugin.getKnockedPlayer().remove(player);
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
	

	
	
	
	public boolean reviveStoped() {
		
		if(getReviveStatus() != ReviveStatus.REVIVED) {
			 if(time == 0) {
					System.out.println("TIEMPO 0");
					return true;
				}
//			 	else if(isAllKnocked()) {
//					System.out.println("TODOS NOQUEADOS");
//					return true;
//				}
				else if(isDangerZone()) {
					System.out.println("ZONA PELIGROSA");
					return true;
				}
		}
	
		return false;
	}
	
	public void Start() {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
	    
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			@Override
			public void run() {
				//setColor(getArmorStand());
				
				if(getReviveStatus() == ReviveStatus.REVIVED) {
					
					StandUp();
					Bukkit.getScheduler().cancelTask(taskID);	
				}
				if(ms.getGameStatus() == GameStatus.TERMINANDO) {
					
				    Dead(e, cause);
				    Bukkit.getScheduler().cancelTask(taskID);	
				   
				}else if(reviveStoped() || player == null || !player.isOnline()) {
					Dead(e, cause);
					Bukkit.getScheduler().cancelTask(taskID);	

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
	
	// METODO SIN USAR PERO DE EJEMPLO , SI TIENES SETEANDO OTRO SCOREBOARD ESTE NO SE VERA BIEN O LO COLOCAS DENTRO DEL MISMO
	// SCOREBOARD O LE DAS UN DELAY PARA QUE SE PUEDA APRECIAR
	public void setColor(Entity e) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		scoreboard.registerNewTeam("yellow");
		Team team = scoreboard.getTeam("yellow");
		team.setColor(ChatColor.YELLOW);
		team.addEntry(e.getUniqueId().toString());
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.setScoreboard(scoreboard);
		}
	
		return;
	}
	


	
	
	
}
