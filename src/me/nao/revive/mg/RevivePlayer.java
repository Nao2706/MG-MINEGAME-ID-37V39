package me.nao.revive.mg;




import java.util.List;

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

import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.Items;
import me.nao.enums.mg.ReviveStatus;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.manager.mg.GameIntoMap;

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
	private ItemStack[] contents;
	
	public RevivePlayer(Player player, int value, int time,ReviveStatus rv, Entity e,DamageCause cause,ArmorStand armor,Minegame plugin) {
		this.player = player;
		this.value = value;
		this.time = time;
		this.e = e;
		this.cause = cause;
		this.plugin = plugin;
		this.rv = rv;
		this.armor = armor;
		this.contents = null;
	

	}

	public Player getPlayer() {
		return player;
	}

	public int getRemainingTimeLife() {
		return time;
	}
	
	public ItemStack[] restoreItemstoPlayer() {
		return contents;
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
	
	public void saveItemsPlayer() {
		this.contents = player.getInventory().getContents();
	}
	
	public void Dead(Entity e, DamageCause cause) {
		GameIntoMap mig = new GameIntoMap(plugin);
		player.getInventory().setContents(restoreItemstoPlayer());
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
		ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwningPlayer(player);
		head.setItemMeta(meta);
		
		PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING,/*duration*/ 300 * 20,/*amplifier:*/10, true ,true,true );

		ArmorStand as = getArmorStand();
		
		as.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"REVIVIR CON (SHIFT + CLICK DERECHO) A: "+ChatColor.GREEN+ChatColor.BOLD+player.getName());
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
		saveItemsPlayer();
		clearInventorymg();
		
		
		gc.sendMessageToUsersOfSameMapLessPlayer(player,""+ChatColor.RED+ChatColor.BOLD+ player.getName()+ChatColor.YELLOW+" fue Derribado/a. \n(Ayudalo/a a levantarse tiene solo %time%)".replace("%time%",time+"s"));
		
		player.sendMessage("");
		if(e != null) {
			
			if(e instanceof Projectile) {
				
				Projectile p =(Projectile) e;
				
				if(p.getShooter() != null) {
					Entity ent = (Entity) p.getShooter();
					
					if(ent.getCustomName() != null) {
						player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+ent.getCustomName()+"..");
						gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+ent.getCustomName()+"..");
						
					}else {
						player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+ent.getType()+"..");
						gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+ent.getType()+"..");
				
					}
					
				}else {
					if(p.getCustomName() != null) {
						player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+p.getCustomName()+"...");
						gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+p.getCustomName()+"...");
						
					}else {
						player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+p.getType());
						gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+p.getType()+"...");
				
					}
				}
				
				
				
			}else if(e instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) e;
				if(le.getCustomName() != null) {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+le.getCustomName());
					gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+le.getCustomName());
					
				}else {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+le.getType());
					gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+le.getType());
					
				}
			}else if(e instanceof Player) {
				
				Player otherplayer = (Player) e;
				if(player.getName().equals(otherplayer.getName())) {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+"Ti mismo (Suicidio o Accidente)");
					gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+"El/Ella mismo/a (Suicidio o Accidente)");
					
				}else {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+otherplayer.getName());
					gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+otherplayer.getName());
				}
				
			}else {
				if(e.getCustomName() != null) {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+e.getCustomName());
					gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+e.getCustomName());
					
				}else {
					player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+e.getType());
					gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+e.getType());
					
				}
			}

		}else{
			if(cause != null) {
				player.sendMessage(ChatColor.YELLOW+"Has sido Derribado/a por: "+ChatColor.GOLD+causeToSpanish(cause));
				gc.sendMessageToAllUsersOfSameMap(player,""+ChatColor.RED+ChatColor.BOLD+"RAZON: "+ChatColor.GOLD+causeToSpanish(cause));
				
			}
		}

		
	
		player.sendMessage("");
		addToRevive();
		Start();
	}
	
	public void StandUp() {
		
		player.getInventory().setContents(restoreItemstoPlayer());
		player.teleport(getArmorStand().getLocation());
		
		if(getReviveStatus() == ReviveStatus.SELFREVIVED) {
			if(player.getInventory().getContents().length >= 1) {
				for (ItemStack itemStack : player.getInventory().getContents()) {
					if(itemStack == null || !itemStack.isSimilar(Items.REVIVEP.getValue())) continue;
							
	                    player.getInventory().removeItem(itemStack);
	              }

			}
	 	}
		
		player.setGameMode(GameMode.ADVENTURE);
		removeToRevive();
		PotionEffect vid = new PotionEffect(PotionEffectType.REGENERATION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		PotionEffect comida = new PotionEffect(PotionEffectType.SATURATION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		PotionEffect abso = new PotionEffect(PotionEffectType.ABSORPTION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		player.addPotionEffect(vid);
		player.addPotionEffect(comida);
		player.addPotionEffect(abso);
		
		
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
		}else if(cause == DamageCause.FIRE_TICK) {
			causes = "POR ARDER EN LLAMAS";
		}else if(cause == DamageCause.LIGHTNING) {
			causes = "POR UN RAYO";
		}else if(cause == DamageCause.MAGIC) {
			causes = "POR MAGIA";
		}else if(cause == DamageCause.STARVATION) {
			causes = "POR HAMBRE";
		}else if(cause == DamageCause.SUFFOCATION) {
			causes = "POR SOFOCACION";
		}else if(cause == DamageCause.POISON) {
			causes = "POR ENVENENAMIENTO";
		}else if(cause == DamageCause.WITHER) {
			causes = "POR WITHER";
		}else {
			causes = "ALGO QUE NO ESTA EN LISTA >>>"+cause.toString()+"<<< (SI VES ESTO REPORTALO)";
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
				
			}
		
	}
	
	public void removeToRevive() {
		 
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
	
		if(gi instanceof GameAdventure) {
			getArmorStand().remove();
			plugin.getKnockedPlayer().remove(player);
			GameAdventure ga = (GameAdventure) gi;
			if(ga.getKnockedPlayers().remove(player.getName()));
			
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
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		if(gi instanceof GameAdventure) {
			if(getReviveStatus() != ReviveStatus.REVIVED || getReviveStatus() != ReviveStatus.SELFREVIVED) {
				 if(time == 0) {
						System.out.println("TIEMPO 0");
						return true;
					}
				  	
				 
				 	else if(allAlivePlayersKnocked() && !hasReviveItemAnyPlayer()) {
						System.out.println("TODOS NOQUEADOS");
						return true;
					}
					else if(isDangerZone()) {
						System.out.println("ZONA PELIGROSA");
						return true;
					}
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
				
				if(getReviveStatus() == ReviveStatus.REVIVED || getReviveStatus() == ReviveStatus.SELFREVIVED) {
					
					StandUp();
					Bukkit.getScheduler().cancelTask(taskID);	
				}else if(getReviveStatus() == ReviveStatus.HEALING) {
					setReviveStatus(ReviveStatus.BLEEDING);
				}else if(ms.getGameStatus() == GameStatus.TERMINANDO) {
					//System.out.println("Error");
				    Dead(e, cause);
				    Bukkit.getScheduler().cancelTask(taskID);	
				   
				}else if(reviveStoped() || player == null || !player.isOnline()) {
					Dead(e, cause);
					//System.out.println("No tienen item F");
					Bukkit.getScheduler().cancelTask(taskID);	

				}else if(getReviveStatus() == ReviveStatus.BLEEDING) {
					
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"SANGRANDO",""+ChatColor.YELLOW+ChatColor.BOLD+"Moriras en "+ChatColor.RED+ChatColor.BOLD+time, 0, 20, 0);
					player.playEffect(getArmorStand().getLocation().add(0,0,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK); 
					time--;
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
	

	public void clearInventorymg() {
		if(player.getInventory().getContents().length >= 1) {
			for (ItemStack itemStack : player.getInventory().getContents()) {
				if(itemStack == null || itemStack.isSimilar(Items.REVIVEP.getValue())) continue;
						
                    player.getInventory().removeItem(itemStack);
              }

		}
	}

	
	public boolean hasReviveItemAnyPlayer() {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			List<String> l = ga.getAlivePlayers();
			for(String player : l) {
				Player pla = Bukkit.getPlayerExact(player);
				if(pla.getInventory().contains(Items.REVIVEP.getValue(), 1) || pla.getInventory().getItemInOffHand().isSimilar(Items.REVIVEP.getValue())) {
					return true;
				}
			}
		
		}
		return false;
	}
	
	public boolean allAlivePlayersKnocked() {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			int alive = ga.getAlivePlayers().size();
			int knocked = ga.getKnockedPlayers().size();
			int arrive = ga.getArrivePlayers().size();
			 System.out.println("true "+(alive - arrive)+" == "+knocked);
			 if((alive - arrive) == knocked) {
				
				return true; 
			 }
				
				
		}
		return false;
	}

	
}
