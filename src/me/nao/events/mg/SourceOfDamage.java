package me.nao.events.mg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.EnchantingTable;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import me.nao.enums.mg.Items;
import me.nao.enums.mg.Posion;
import me.nao.enums.mg.ReviveStatus;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.manager.mg.GameIntoMap;
import me.nao.mobs.mg.MobsActions;
import me.nao.revive.mg.RevivePlayer;


@SuppressWarnings("deprecation")
public class SourceOfDamage implements Listener{

	private Minegame plugin;
	
	public SourceOfDamage(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void PlayerTelport(PlayerTeleportEvent e) {
		Player player = (Player) e.getPlayer();
		Location l = e.getTo();
		
		GameConditions gc = new GameConditions(plugin);
		if(gc.isPlayerinGame(player)) {
			gc.forceGameModePlayerRol(player);
			
		}
		
		if(player.getGameMode() == GameMode.SPECTATOR) {
			if(e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
			
				if(gc.isPlayerinGame(player)) {
					
					if(!player.getLocation().getWorld().getName().equals(l.getWorld().getName())) {
						player.sendMessage(ChatColor.RED+"- Ese Jugador no esta en tu Juego.");
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 20.0F, 1F);
						e.setCancelled(true);
						return;
					}else {
						List<Entity> ent = getNearbyEntites(l, 2);
						for(Entity et : ent) {
							if(et.getType() == EntityType.PLAYER) {
								Player target = (Player) et;
								
								if(!gc.isPlayerinGame(target)) {
									player.sendMessage(ChatColor.RED+"Ese Jugador no esta Jugando....");
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 20.0F, 1F);
									e.setCancelled(true);
									return;
								}
								
								PlayerInfo tg = plugin.getPlayerInfoPoo().get(target);
								PlayerInfo user = plugin.getPlayerInfoPoo().get(player);
								
								if(user.getMapName().equals(tg.getMapName()) && target.getGameMode() == GameMode.ADVENTURE) {
									player.sendMessage(ChatColor.GREEN+"Te teletransportaste a "+ChatColor.GOLD+target.getName());
									player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
								}else {
									player.sendMessage(ChatColor.RED+"Ese Jugador no esta en tu Juego...");
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 20.0F, 1F);
									e.setCancelled(true);
								}
									
								
							}
							
								
								break;
							}
						}
					}
				}
			}
		}
		
	
	
	
	@EventHandler
	public void Command1(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String message = e.getMessage();
		GameConditions gc = new GameConditions(plugin);
		
		if(gc.isPlayerinGame(player)) {
			if(!player.isOp()) {
				if(message.contains("mg")) {
					 
					e.setCancelled(false);
				}else {
					player.sendMessage(ChatColor.RED+"No puedes ejecutar comandos mientras estas en una Partida");
					player.sendMessage(ChatColor.AQUA+"Si deseas salir usa: "+ChatColor.DARK_RED+"/mg leave");
					e.setCancelled(true);
				}
			}
		}
			
		
		
	}
	
	@EventHandler
	public void inmob(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		Entity entidad = e.getRightClicked();
		
		if(entidad.getType() == EntityType.VILLAGER && entidad.getCustomName() != null && entidad.getCustomName().contains(""+ChatColor.GREEN+ChatColor.BOLD+"Medico "+ChatColor.RED+ChatColor.BOLD+"+")) {
			//player.setHealth(20);
			PotionEffect inst = new PotionEffect(PotionEffectType.INSTANT_HEALTH,/*duration*/ 5*20,/*amplifier:*/2, false ,false,true );
			PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION,/*duration*/ 10*20,/*amplifier:*/3, false ,false,true );
			Villager vi = (Villager) entidad;
			vi.addPotionEffect(inst);
			vi.addPotionEffect(regen);
			if(player.getHealth() < 20) {
				
				Random r = new Random();
				List<ItemStack> l = new ArrayList<>();
				l.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE,1));
				l.add(new ItemStack(Material.GOLDEN_APPLE,1));
				l.add(Posion.ABSORP.getValue());
				l.add(Posion.REGENERP.getValue());
				l.add(Posion.HEALTHP.getValue());
				vi.getWorld().dropItem(vi.getLocation().add(0, 1, 0),l.get(r.nextInt(l.size())));
				
				player.setFoodLevel(20);
				player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(1, 1, 1),
						/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
				
				
				player.addPotionEffect(inst);
				player.addPotionEffect(regen);
				player.sendMessage(vi.getCustomName()+ChatColor.GREEN+" Has sido curado ten un Item para tu Lucha.");
			}else {
				player.sendMessage(vi.getCustomName()+ChatColor.RED+" Tu vida esta llena.");
			}
			
			
			
		}
		
		
	}
	
	
	
	@EventHandler(priority = EventPriority.LOWEST)  //METODO
    public void mgworld(PlayerChangedWorldEvent e){
		Player player = e.getPlayer();

		GameConditions gc = new GameConditions(plugin);
		if(gc.isPlayerinGame(player)) {
			gc.forceGameModePlayerRol(player);
			
		}
	
	}
	
	@EventHandler
	public void toxicCloud(AreaEffectCloudApplyEvent e) {
		
		AreaEffectCloud cloud = (AreaEffectCloud) e.getEntity();	
		if(cloud.getCustomName() != null && ChatColor.stripColor(cloud.getCustomName()).equals("GAS TOXICO")) {
			List<LivingEntity> l = e.getAffectedEntities();
			
			for(LivingEntity ent : l) {
				if(ent instanceof Player) {
					
					Player player = (Player) ent;
					if(player.getInventory().getHelmet() != null && isAntiGasMask(player, player.getInventory().getHelmet())) {
						
						e.setCancelled(true);
					}
				}else if(ent instanceof Monster) {
					   e.setCancelled(true);
				}
			}
		}		
				
	}
	
	
	   @EventHandler(priority = EventPriority.LOWEST)
		public void mgPotionEffect(EntityPotionEffectEvent e) {
		
			Entity ent = e.getEntity();
			Cause cause = e.getCause();
			
			if(cause == Cause.AREA_EFFECT_CLOUD) {
				
				if(ent instanceof Player) {
					Player player = (Player) ent;
					if(player.getInventory().getHelmet() != null && isAntiGasMask(player, player.getInventory().getHelmet())){
						e.setCancelled(true);
					}
					return;
				}else if(ent instanceof Monster) {
					e.setCancelled(true);
				}
			}
		
			
		
			
		}
	
	
	//TODO MOVE EVNT
	@EventHandler  //METODO
    public void moving(PlayerMoveEvent e){
		   
			Player player = e.getPlayer();
			 
			GameConditions gc = new GameConditions(plugin);
			if(gc.isPlayerinGame(player)) {
				gc.blockPotion(player);
				
				gc.TryToScapeInSpectatorMode(player);
				GameIntoMap ci = new GameIntoMap(plugin);
				ci.GamePlayerFallMap(player , null);
				ci.GamePlayerWin(player);
//				ci.PlayerFallMap(player);	
//				ci.PlayerWin(player);
				
				if(plugin.CreditKill().containsKey(player)) {
					Entity mob = plugin.CreditKill().get(player);
					if(mob.isDead()) {
						plugin.CreditKill().remove(player);
					}else if(player.getLocation().distance(mob.getLocation()) >= 50) {
						plugin.CreditKill().remove(player);
					}
				}
				
				//if(player.hasPermission("mg.toxiczone")) {
				Block block = player.getLocation().getBlock();
				Block c1 = block.getRelative(0, 1, 0);
				Block c2 = block.getRelative(0, -2, 0);
				
				 if(c1.getType() == Material.LIME_BANNER && c2.getType() == Material.STRUCTURE_BLOCK) {
					 PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
					 if(!player.getInventory().containsAtLeast(Items.CHECKPOINTFLAG.getValue(),1)) {
						 player.getInventory().addItem(Items.CHECKPOINTFLAG.getValue());
					 }
					 if(pl.getCheckPointMarker() != null) {
						 if(pl.getCheckPointMarker().equals(block.getLocation())) {
							 player.sendMessage(ChatColor.RED+"La Ubicacion ya esta Guardada, encuentra otro sitio para Sobreescribir los Datos.");
							 return;
						 }else {
			        		 player.sendTitle(""+ChatColor.BLUE+ChatColor.BOLD+">>> "+ChatColor.GREEN+ChatColor.BOLD+"CHECKPOINT GUARDADO"+ChatColor.BLUE+ChatColor.BOLD+"  <<<",ChatColor.YELLOW+"Punto de Control", 20, 40, 20);

							 player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, block.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */25, 0.5, 1, 0.5, /* velocidad */0, null, true);
							 player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
							 player.sendMessage(ChatColor.GREEN+"La Ubicacion se Guardado. (Ubicacion Sobreescrita) , Utiliza el Item de CheckPoint de Banderas para regresar.\"");
							 pl.setCheckpointLocationMg(block.getLocation());
					
						 }
					 }else {
		        		 player.sendTitle(""+ChatColor.BLUE+ChatColor.BOLD+">>> "+ChatColor.GREEN+ChatColor.BOLD+"CHECKPOINT GUARDADO"+ChatColor.BLUE+ChatColor.BOLD+"  <<<",ChatColor.YELLOW+"Punto de Control", 20, 40, 20);
 
			
						 player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, block.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */25, 0.5, 1, 0.5, /* velocidad */0, null, true);

						 player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
						 player.sendMessage(ChatColor.GREEN+"La Ubicacion se Guardado, Utiliza el Item de CheckPoint de Banderas para regresar.");
						 pl.setCheckpointLocationMg(block.getLocation());
					 }
					 
				 }
				
				
				if(player.getScoreboardTags().contains("Toxic") && player.getGameMode() == GameMode.ADVENTURE) {
					if(player.isInsideVehicle() && player.getVehicle() instanceof Boat) {
						Boat boat = (Boat) player.getVehicle();
						if(boat.getStatus() == Boat.Status.IN_WATER) {
							return;
						}
					}
					if(player.getInventory().getHelmet() != null && isAntiGasMask(player, player.getInventory().getHelmet())) return;
					if(player.hasPotionEffect(PotionEffectType.POISON)) return;
			
					
					
					Block b1 = block.getRelative(0, 0, 0);
					Block b2 = block.getRelative(0, 1, 0);
				
					
					//if(!b1.isLiquid() || !b2.isLiquid()) return;
					
				
						
						if(b1.getType() == Material.WATER || b2.getType() == Material.WATER) {
							player.sendTitle(ChatColor.RED+"!!!PELIGRO!!!", ChatColor.GREEN+"Estas en"+ChatColor.BLUE+" Agua Toxica "+ChatColor.GREEN+"corre", 20, 40, 20);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
							player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,/*duration*/ 5 * 20,/*amplifier:*/10, false ,false,true));
							//player.addPotionEffect(lento);
							player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,/*duration*/ 10 * 20,/*amplifier:*/100, false ,false,true));
							//player.addPotionEffect(ciego);
							player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,/*duration*/ 10 * 20,/*amplifier:*/20, false ,false,true));
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
							player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 1, 1),
									/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
						}else if(b1.getType() == Material.LAVA || b2.getType() == Material.LAVA) {
							player.sendTitle(ChatColor.RED+"!!!PELIGRO!!!", ChatColor.GREEN+"Estas en"+ChatColor.YELLOW+" Lava Toxica "+ChatColor.GREEN+"corre", 20, 40, 20);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
							player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,/*duration*/ 5 * 20,/*amplifier:*/10, false ,false,true));
							//player.addPotionEffect(lento);
							player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,/*duration*/ 10 * 20,/*amplifier:*/100, false ,false,true));
							//player.addPotionEffect(ciego);
							player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,/*duration*/ 10 * 20,/*amplifier:*/20, false ,false,true));
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
							player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 1, 1),
									/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
							player.removePotionEffect(PotionEffectType.RESISTANCE);
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.REGENERATION);
						}
						
				}else if(player.getScoreboardTags().contains("ToxicZone") && player.getGameMode() == GameMode.ADVENTURE) {
					if(player.isInsideVehicle() && player.getVehicle() instanceof Boat) {
						Boat boat = (Boat) player.getVehicle();
						if(boat.getStatus() == Boat.Status.IN_WATER) {
							return;
						}
					}
					if(player.getInventory().getHelmet() != null && isAntiGasMask(player, player.getInventory().getHelmet())) return;
					if(player.hasPotionEffect(PotionEffectType.POISON)) return;
					
					
					Block b1 = block.getRelative(0, 0, 0);
					Block b2 = block.getRelative(0, 1, 0);
					
					if(b1.getType() != Material.AIR)return;
				
					PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
					GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
					
					
				
					int rango = gi.getToxicZoneRange();
					
					List<Location> l = gi.getMobsGenerators();
					
					if(!l.isEmpty()) {
						for(Location loc : l) {
							
							if(player.getLocation().distance(loc) > rango) continue;
							Block a = loc.getBlock();
							Block b = a.getRelative(0,-1, 0);
							
							if(a.getType() == Material.COMMAND_BLOCK && b.getType() == Material.BEDROCK) {
								player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 0, 1),
										/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
								 
								player.sendTitle(ChatColor.RED+"!!!PELIGRO!!!", ChatColor.GREEN+"Estas en una"+ChatColor.RED+" Zona Toxica "+ChatColor.GREEN+"corre", 20, 40, 20);
								player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,/*duration*/ 10 * 20,/*amplifier:*/20, false ,false,true));
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,/*duration*/ 10 * 20,/*amplifier:*/100, false ,false,true));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
								player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,/*duration*/ 10 * 20,/*amplifier:*/20, false ,false,true));
								player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
								player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 1, 1),
										/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
								break;
							}else if(a.getType() == Material.REPEATING_COMMAND_BLOCK && b.getType() == Material.BEDROCK) {
								if(b1.getType() == Material.LAVA || b2.getType() == Material.LAVA) {
									player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 0, 1),
											/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
									
									player.sendTitle(ChatColor.RED+"!!!PELIGRO!!!", ChatColor.GREEN+"Estas en"+ChatColor.YELLOW+" Zona de Lava Toxica "+ChatColor.GREEN+"corre", 20, 40, 20);
									player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
									player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,/*duration*/ 5 * 20,/*amplifier:*/10, false ,false,true));
									//player.addPotionEffect(lento);
									player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,/*duration*/ 10 * 20,/*amplifier:*/100, false ,false,true));
									//player.addPotionEffect(ciego);
									player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,/*duration*/ 10 * 20,/*amplifier:*/20, false ,false,true));
									player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
									player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 1, 1),
											/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
									player.removePotionEffect(PotionEffectType.RESISTANCE);
									player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.REGENERATION);
								}
								
								break;
							}else if(a.getType() == Material.CHAIN_COMMAND_BLOCK && b.getType() == Material.BEDROCK) {
								if(b1.getType() == Material.WATER || b2.getType() == Material.WATER) {
									player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 0, 1),
											/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
									
									player.sendTitle(ChatColor.RED+"!!!PELIGRO!!!", ChatColor.GREEN+"Estas en"+ChatColor.BLUE+" Zona Agua Toxica "+ChatColor.GREEN+"corre", 20, 40, 20);
									player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
									player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,/*duration*/ 5 * 20,/*amplifier:*/10, false ,false,true));
									//player.addPotionEffect(lento);
									player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,/*duration*/ 10 * 20,/*amplifier:*/100, false ,false,true));
									//player.addPotionEffect(ciego);
									player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,/*duration*/ 10 * 20,/*amplifier:*/20, false ,false,true));
									player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 10 * 20,/*amplifier:*/50, false ,false,true));
									player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(1, 1, 1),
											/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
								}
								
								break;
							}
							
						}}
				}
				
				
				
//				if(plugin.getPlayerKnocked().containsKey(player)) {
//					
//					Block b = player.getLocation().getBlock();
//					Block r = b.getRelative(0, 1, 0);
//					if(b.getType() == Material.LADDER) return;
//				    player.sendBlockChange(r.getLocation(), Material.BARRIER.createBlockData());
//					RevivePlayer pr = plugin.getPlayerKnocked().get(player);
//					pr.addLocation(r.getLocation());
//					System.out.println("tamaño "+pr.getLocationsChange().size());	
//					
//					
//				}
				
			}
			
			
			
			
			
			Block block = player.getLocation().getBlock();
			Block b = block.getRelative(0, -2, 0);	
			Block b3 = block.getRelative(0, -4, 0);
			
		
			
			
			
			//Block bx = block.getRelative(0, 0, 0);
			
		
//			if(plugin.PlayerisArena(player)) {
//				
//					//DetectDoor(player);
//				
//					if(player.getGameMode() != GameMode.SPECTATOR && !player.isOp()) {
//						if(plugin.getSpectators().get(plugin.getArenaPlayerInfo().get(player)).contains(player.getName())) {
//
//							player.setGameMode(GameMode.SPECTATOR);
//						}
//					}
//					if(player.getGameMode() != GameMode.ADVENTURE && !player.isOp()) {
//						if(plugin.getAlive().get(plugin.getArenaPlayerInfo().get(player)).contains(player.getName()) && !plugin.getArrive().get(plugin.getArenaPlayerInfo().get(player)).contains(player.getName())) {
//
//							player.setGameMode(GameMode.ADVENTURE);
//						}
//					}
//				 
//				if(b.getType() == Material.REDSTONE_BLOCK) {
//					if(plugin.getCheckPoint().containsKey(player)) {
//						plugin.getCheckPoint().remove(player);
//						player.sendMessage(ChatColor.RED+"Tu Checkpoint a sido borrado.");
//					}
//				}
//				
//				if(bx.getType() == Material.BARRIER && player.getGameMode() == GameMode.SPECTATOR) {
//				
//							String arena = plugin.getArenaPlayerInfo().get(player);
//							
//							ClassArena c = new ClassArena(plugin);
//							c.DeathTptoSpawn(player, arena);
//							player.sendMessage(ChatColor.RED+"No puedes salir de la arena.");	
//						
//						
//				}
//				
//				if(b1.getType() == Material.SLIME_BLOCK) {
//					PotionEffect salto = new PotionEffect(PotionEffectType.JUMP,/*duration*/ 2 * 20,/*amplifier:*/10, true ,true,true );
//					if(!player.hasPotionEffect(PotionEffectType.JUMP)) {
//						player.addPotionEffect(salto);
//					}
//					
//				}
//				
//			}
//			
//			//DetectDispenser(player);
//			
//			if(b1.getType() == Material.LODESTONE && b.getType() == Material.BEDROCK) {
//				if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.CREATIVE) {
//					player.setGameMode(GameMode.ADVENTURE);
//				}
//			}
//			
//			if(b.getType() == Material.LODESTONE && b2.getType() == Material.BEDROCK) {
//				if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.ADVENTURE)) {
//					player.setGameMode(GameMode.SPECTATOR);
//				}
//			}
//			
//			if(b.getType() == Material.RED_CONCRETE && b2.getType() == Material.BARRIER) {
//				if(plugin.PlayerisArena(player)) {
//					player.setHealth(20);
//					player.setFoodLevel(20);
//				}
//			}
			
			if(b3.getType() == Material.CHEST){
				  Chest chest = (Chest) b3.getState();
				  if(chest.getCustomName() != null) {
					  
					  FileConfiguration config = plugin.getConfig();
					  List<String> ac = config.getStringList("Maps-Created.List");
					  
					  if(ac.contains(chest.getCustomName())) {
						 // String arenaName = chest.getCustomName();
						 // ClassArena c = new ClassArena(plugin);
						  if(!plugin.getPlayerGround().contains(player.getName())) {
						   // c.JoinPlayerArena(player, arenaName);
						  }
						  
					  }
					  
			
					  
					  
					  
				  }
			} 
			
			if(b.getType() == Material.CHEST){
				  Chest chest = (Chest) b.getState();
				  if(chest.getCustomName() != null) {
					  
					  
					  FileConfiguration config = plugin.getConfig();
					  List<String> ac = config.getStringList("Maps-Created.List");
					  
					  if(ac.contains(chest.getCustomName())) {
						 // String arenaName = chest.getCustomName();
						 // ClassArena c = new ClassArena(plugin);
						  //la lista esta para evitar spam de chat
						  if(!plugin.getPlayerGround().contains(player.getName())) {
							   // c.JoinPlayerArena(player, arenaName);
						  }
						  
					  }
							  if(gc.isPlayerinGame(player)) {
								  
								  if(player.getGameMode() == GameMode.ADVENTURE) {
									   if(chest.getCustomName().equals("TELEPORT")) {
										     if(!chest.getInventory().isEmpty()) {
													for(ItemStack itemStack : chest.getInventory().getContents()) {
															if(itemStack == null) continue;
														
															     if(!itemStack.hasItemMeta()) {
																		return;
																	}
																	
																	ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
															
																	if(!bm.hasDisplayName()) {
																		return;
																	}
																	   String n = bm.getDisplayName();
																	   String[] nn = n.split(" ");
																	    //world 345 67 89 0 1
																		 
																		  String world = nn[0];
																		  double x = Double.valueOf( nn[1]);
																		  double y = Double.valueOf(nn[2]);
																		  double z = Double.valueOf(nn[3]);
																		  float pitch = Float.valueOf(nn[4]);
																		  float yaw = Float.valueOf(nn[5]);
																		  
																		  Location l = new Location(Bukkit.getWorld(world),x,y,z,pitch,yaw);
																		  player.teleport(l);	
																		  player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
										              }
										          }
								      }
								  }
								  
						
						
								   
								   
						    } 
//					  
//			   
//			
			
			
			if(gc.isPlayerinGame(player)) {
				
				getNearbyBlocksParticle(player);
				
				for(Entity e1 : getNearbyEntites(player.getLocation(),150)) {
					
					Block blocke = e1.getLocation().getBlock();
					Block be = blocke.getRelative(0, -1, 0);
					if(be.getType() == Material.BARRIER) {
						if(e1.getType() == EntityType.ITEM) {
							e1.remove();
						}
					}
				}	
			}
	
		}}
			
	}
	

	//TODO MUERTES DE MOBS
	@EventHandler  //METODO
    public void killmobs(EntityDeathEvent e){
		 
	
		GameConditions gc = new GameConditions(plugin);
		
		
		Entity ent = e.getEntity().getKiller();
		EntityType mob = e.getEntityType();
		

		if(ent instanceof Player) {
			Player player = (Player) ent;
			
			if(gc.isPlayerinGame(player)) {
				
				
				if(gc.isGuardian(e.getEntity())) return;
				 GameIntoMap c = new GameIntoMap(plugin);
				
				
				if(player != null && mob != EntityType.ITEM_FRAME && mob != EntityType.ITEM) {
					Entity m = e.getEntity();
					

					Random random = new Random();
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
					
					int n = random.nextInt(5);
					if(n == 1) {
						m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.DIAMOND,5)));
					}
					else if(n == 2) {
						m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.EMERALD,10)));
					}
					else if(n == 3) {
						m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.GOLD_INGOT,15)));		
									}
					else if(n == 4) {
						m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.IRON_INGOT,20)));
					}
					else if(n == 5) {
						m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.NETHERITE_INGOT,2)));
					}
					
					if(m instanceof Zombie) {
					     MobsActions ma = new MobsActions(plugin);
						 ma.onDead(player, m);
					}
					//SoulsArmor(player);
					
					
					 c.gamePlayerAddPoints(player);
					 
					 //iba el otor
				
				}
			}
		}else if(e.getEntity() instanceof Monster) {
			
			
			if(!plugin.getGuardianCredit().containsKey(e.getEntity())) return;
				
				GameIntoMap c = new GameIntoMap(plugin);
				 Player target = gc.ConvertStringToPlayerAlone(plugin.getGuardianCredit().get(e.getEntity()));
				 if(!gc.isPlayerinGame(target)) return;
				 c.gamePlayerAddPoints(target);
				 target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
					
			 
			
		}
		

		
		
	return;
		
		
	}
	
	//TODO SOULS
//	public void SoulsArmor(Player player) {
//		List<Entity> l = getNearbyEntites(player.getLocation(),30);
//		
//		for(Entity e1 : l) {
//			// 
//			if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
//				// FALTAN  10 / 30
//				//ALMAS 10 / 10
//				String name = ChatColor.stripColor(e1.getCustomName());
//				
//				if(name.contains("FALTAN")) {
//					String[] sp = name.split(" ");
//					int nm = Integer.valueOf(ChatColor.stripColor(sp[1]));
//					int nm1 = Integer.valueOf(ChatColor.stripColor(sp[3]));
//					nm = nm + 1;
//					
//					if(nm >= nm1) {
//						
//						DetectChestAndCommandArmorStand(player);
//						
//						List<Entity> l2 = getNearbyEntites(player.getLocation(),30);
//						
//						for(Entity e2 : l2) {
//							if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null ) {
//								
//								String name2 = ChatColor.stripColor(e2.getCustomName());
//								if(name2.contains("RECIPIENTE DE ALMAS") || name2.contains("FALTAN")) {
//									e2.getWorld().spawnParticle(Particle.CLOUD, e2.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */10, 3, 1, 3, /* velocidad */0, null, true);
//									e2.remove();
//								}
//							
//								
//							}
//							
//						
//						}
//						
//						player.sendMessage(ChatColor.GREEN+"Se ha terminado de llenar las almas.");
//						 String arena = plugin.getArenaPlayerInfo().get(player);
//						 for(Player players : Bukkit.getOnlinePlayers()) {
//							 if(players.getName().equals(player.getName())) continue;
//							 String arena2 = plugin.getArenaPlayerInfo().get(players);
//							 if(arena2 != null && arena2.equals(arena)) {
//								
//								 players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Termino de llenar las almas.");							 
//								 
//							 }
//							 
//						 }
//						
//					}else {
//						
//						e1.setCustomName(""+ChatColor.AQUA+ChatColor.BOLD+"FALTAN "+ChatColor.GREEN+nm+ChatColor.RED+" / "+ChatColor.GREEN+nm1);	
//						e1.getWorld().spawnParticle(Particle.FLAME, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
//						e1.getWorld().spawnParticle(Particle.FLAME, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
//					}
//				}
//				
//				
//				
//			
//			
//				return;
//			}
//		}
//	}
	
	//TODO COMANDOS DETECT
	@EventHandler 
	public void CommandEvent(ServerCommandEvent e) {
		
		
		
		if(e.getSender() instanceof BlockCommandSender) {
			GameConditions gc = new GameConditions(plugin);
			String comando = e.getCommand();
			
			
			if(comando.startsWith("kill")) {
				String[] a = comando.split(" ");
				String name = a[1];
				if(name != null) {
					Player target = Bukkit.getServer().getPlayerExact(name);
				
					
					if(target != null && gc.isPlayerinGame(target)){
						//PlayerInfo pi = plugin.getPlayerInfoPoo().get(target);
						e.setCancelled(true);
						 GameIntoMap cs = new GameIntoMap(plugin);
						 cs.GamePlayerDeadInMap(target);
						 target.setHealth(20);
						 target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"INSTAKILL DE AREA", 40, 80, 40);
						 target.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Instakill de Area.");
											 
					    gc.sendMessageToUsersOfSameMapLessPlayer(target,ChatColor.GOLD+target.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"InstaKill de Area");
						 
						 
						 /*
						 List<Map.Entry<Player, String>> list2 = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
							for (Map.Entry<Player,String> datos : list2) {
								 for(Player players : Bukkit.getOnlinePlayers()) {
									 if(players.getName().equals(target.getName())) continue;
									 String ar = plugin.getArenaPlayerInfo().get(players);
										
									 if(ar != null && ar.equals(arena)) {
								     		 players.sendMessage(ChatColor.GOLD+target.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"InstaKill de Area");
								     	}
								     		 //esto lo veran los que estan en arena
								 }
							}*/
						
						 
					}
				}
				
			}
			
			
			//Bukkit.getConsoleSender().sendMessage("commandblocksay: "+comando);
			//Player target = Bukkit.getServer().getPlayerExact(args[0]);
		}
		if(e.getSender() instanceof CommandSender) {
			GameConditions gc = new GameConditions(plugin);
			//Player target = Bukkit.getServer().getPlayerExact(args[0]);
			String comando = e.getCommand();
			//Bukkit.getConsoleSender().sendMessage("consolesay: "+comando);
			if(comando.startsWith("kill")) {
				String[] a = comando.split(" ");
				String name = a[1];
				if(name != null) {
					Player target = Bukkit.getServer().getPlayerExact(name);
					if(target != null && gc.isPlayerinGame(target)){
						//PlayerInfo pi = plugin.getPlayerInfoPoo().get(target);
						e.setCancelled(true);
						 GameIntoMap cs = new GameIntoMap(plugin);
						 cs.GamePlayerDeadInMap(target);
						 target.setHealth(20);
						 target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"INSTAKILL POR COMANDO", 40, 80, 40);
						 target.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Instakill por Comando.");
						 
					  gc.sendMessageToUsersOfSameMapLessPlayer(target,ChatColor.GOLD+target.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"InstaKill por Comando.");
								     	
								     		 //esto lo veran los que estan en arena
								 
							
					}}		
						 
					
				
				
			}
			
			
		}
		
	}
	
	
	
	
	@SuppressWarnings("removal")
	@EventHandler
	public void shootm(ProjectileLaunchEvent e) {
		Entity m = (Entity) e.getEntity().getShooter();
		Random r = new Random();

		int rand = r.nextInt(6);
		
		if(m instanceof Snowman) {
			Snowman s = (Snowman) m;
			
			
			if(s.getCustomName() != null && ChatColor.stripColor(s.getCustomName()).startsWith("GUARDIA DE: ")) {
				Location loc = s.getLocation();
				
				e.setCancelled(true);
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				aw.getLocation().setDirection(s.getLocation().getDirection());
				aw.setCritical(true);
				aw.setKnockbackStrength(2);
				aw.setFireTicks(1200);
				aw.setPierceLevel(127);
				aw.setVelocity(loc.getDirection().multiply(6));
				((Arrow) aw).setShooter(s);
				
			}
		
		}
		
		if(m instanceof EnderDragon) {
			
			EnderDragon d = (EnderDragon) m;
			
			Location loc = d.getLocation();
			Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 10, 0), EntityType.FIREBALL);
			Fireball f =(Fireball) h1;
			f.setYield(5);
			f.setDirection(new Vector(0,-3,0));
			f.setVelocity(f.getDirection().multiply(3));
			((Fireball) h1).setShooter(d);
			
			
		}
		
		if(m instanceof Blaze) {
			Blaze b = (Blaze) m;
			
			for(int i = 0 ;i < 7 ;i++) {
			
			Location loc = b.getLocation();
			Location loc2 = b.getLocation();

			
			
			Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.SMALL_FIREBALL);
			Entity h2 = loc2.getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.SMALL_FIREBALL);
			h1.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
			h2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
			SmallFireball f = (SmallFireball) h1;
			SmallFireball f2 = (SmallFireball) h2;
			f.setIsIncendiary(true);
			f.setYield(2);
			f2.setIsIncendiary(true);
			f2.setYield(2);
			
			((SmallFireball) h1).setShooter(b);
			((SmallFireball) h2).setShooter(b);
			
			}
		}
		if(m instanceof Skeleton) {
			
			Skeleton s = (Skeleton)m;
			
			if(s.getCustomName() != null && ChatColor.stripColor(s.getCustomName()).contains("ARQUERO")) {
				if(rand == 0) {
					Location loc = s.getLocation();
					
					Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
					aw.setCritical(true);
					aw.setKnockbackStrength(2);
					aw.setFireTicks(1200);
					aw.setVelocity(loc.getDirection().multiply(6));
					((Arrow) aw).setShooter(s);
				}
			}
			
		
	
			
		}
		if(m instanceof Pillager) {
			
			Pillager p = (Pillager) m;
			
			
			ItemStack b = new ItemStack(Material.CROSSBOW,1);
			ItemMeta meta = b.getItemMeta();
			meta.addEnchant(Enchantment.QUICK_CHARGE, 5, true);
			meta.addEnchant(Enchantment.MULTISHOT, 1, true);
			b.setItemMeta(meta);
			
			p.getEquipment().setItemInMainHand(b);
			
			
			if(p.isPatrolLeader()) {
				
				if(rand == 0) {
					PotionEffect damage = new PotionEffect(PotionEffectType.STRENGTH,/*duration*/ 100,/*amplifier:*/1, true ,true,true );
					Location loc = p.getLocation();
					Location loc2 = p.getLocation();
		
					
					
					Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
					Entity h2 = loc2.getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.ARROW);
					h1.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
					h2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
					Arrow aw = (Arrow) h1;
					Arrow aw2 = (Arrow) h2;
					aw.addCustomEffect(damage,true);
					aw.setCritical(true);
					aw.setKnockbackStrength(1);
					aw.setFireTicks(1200);
					aw2.addCustomEffect(damage,true);
					aw2.setCritical(true);
					aw2.setKnockbackStrength(1);
					aw2.setFireTicks(1200);
		
					((Arrow) h1).setShooter(p);
					((Arrow) h2).setShooter(p);
				}
				if(rand == 1) {
					
					for(int i = 0;i<20;i++) {
						SpawnArrowsFireMob(p, RandomPosOrNeg(10),RandomPosOrNeg(5));
					}
				}
				
			}else {
				if(rand == 1) {
				Location loc = p.getLocation();
				Location loc2 = p.getLocation();
	
				
				
				Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				Entity h2 = loc2.getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.ARROW);
				h1.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
				h2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
				Arrow aw = (Arrow) h1;
				Arrow aw2 = (Arrow) h2;
				aw.setCritical(true);
				aw.setKnockbackStrength(1);
				aw.setFireTicks(1200);
				aw2.setCritical(true);
				aw2.setKnockbackStrength(1);
				aw2.setFireTicks(1200);
	
				((Arrow) h1).setShooter(p);
				((Arrow) h2).setShooter(p);
				}
			}
			
		}
		
	}
	

	@SuppressWarnings("removal")
	public void SpawnArrowsFireMob(Entity e,float addy ,float addp ) {
		
		Location loc = e.getLocation();
		loc.setYaw(loc.getYaw()+addy);
		loc.setPitch(loc.getPitch()+addp);
		Vector v = loc.getDirection();
		
		
		Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
		aw.setCritical(true);
		aw.setKnockbackStrength(2);
		aw.setFireTicks(1200);
		aw.setVelocity(v.multiply(5));
		aw.setShooter((ProjectileSource) e);
	}
	
	@EventHandler  //TODO MUERTES METODO
    public void damageInGame(EntityDamageEvent e){
		
		if(e.getEntity().getType() != EntityType.ITEM_FRAME || e.getEntity().getType() != EntityType.GLOW_ITEM_FRAME || e.getEntity().getType() != EntityType.ITEM) {
			
			//LINEA DE SANGRE XD
			
			e.getEntity().getWorld().playEffect(e.getEntity().getLocation().add(0,1,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK); 
			
			Block block = e.getEntity().getLocation().getBlock();
			Block b = block.getRelative(0, -1, 0);
			if(b.getType() == Material.BARRIER && e.getEntity().getType() != EntityType.PLAYER){
				
				if(e instanceof Mob) {
					((Mob) e).setHealth(0);
				}
			}
	
		} 
		
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			GameIntoMap ci = new GameIntoMap(plugin);
			GameConditions gc = new GameConditions(plugin);
		
			if(!gc.isPlayerinGame(player)) return;
			PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
			
			GameInfo gi = plugin.getGameInfoPoo().get(pi.getMapName());
			
			
			if(gi.getSpectators().contains(player.getName())) {
				e.setCancelled(true);
				player.setGameMode(GameMode.SPECTATOR);
				return;
			}
		
			
				Block block = player.getLocation().getBlock();
				Block b1 = block.getRelative(0, 0, 0);
				Block b2 = block.getRelative(0, 1, 0);
				
				if(player.getGameMode() == GameMode.ADVENTURE) {
					
					if(player.getScoreboardTags().contains("Toxic") || player.getScoreboardTags().contains("ToxicZone")) {
						if(b1.getType() == Material.WATER || b2.getType() == Material.WATER) {
							if(player.getHealth() <= 10) {
								spawnToxicCloud(player,ChatColor.BLUE+"Agua Toxica");
							}
							
						}else if(b1.getType() == Material.LAVA || b2.getType() == Material.LAVA) {
							if(player.getHealth() <= 10) {
							   spawnToxicCloud(player,ChatColor.YELLOW+"Lava Toxica");
							}
						}else if(b1.getType() == Material.AIR) {
							if(player.getHealth() <= 3 && !isAntiGasMask(player, player.getInventory().getHelmet())) {
								spawnToxicCloud(player,ChatColor.DARK_GREEN+"Aire Toxico");
							}
						}
					}
				}
			
			
			if(e instanceof EntityDamageByEntityEvent && isAntiGasMask(player, player.getInventory().getHelmet())) {
				breakAntiGasMask(player, player.getInventory().getHelmet());
			}
			// CUANDO CAE ENCIMA DEL BARRIER RECIBE DAÑO PERO ESTE NO MUERE PERO SE PUEDE MOVER UNOS SEGS
			if(player.getHealth() > e.getFinalDamage() && e.getCause() == DamageCause.FALL) {
				ci.GamePlayerFallMap(player,null);
			}if(e.getCause() == DamageCause.VOID) {
				e.setCancelled(true);
				ci.GamePlayerFallMap(player,DamageCause.VOID);
			}else if(e.getFinalDamage() >= player.getHealth()) {
				
				if(player.getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING)) || player.getInventory().getItemInOffHand().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING)))return;
				
				e.setCancelled(true);
				if(e instanceof EntityDamageByEntityEvent){
							
							
							Entity damager = ((EntityDamageByEntityEvent)e).getDamager();
					
							if(gc.isEnabledReviveSystem(pi.getMapName())) {
								ArmorStand armor = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
								RevivePlayer pr = new RevivePlayer(player,0,60,ReviveStatus.BLEEDING,damager,null,armor ,plugin);
								pr.Knocked();
								plugin.getKnockedPlayer().put(player, pr);
								
							}else {
								ci.GameMobDamagerCauses(player, damager);
							}
							return;
					
				}else{
					//bloque de daño por causas externas
							if(gc.isEnabledReviveSystem(pi.getMapName())) {
								ArmorStand armor = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
								RevivePlayer pr = new RevivePlayer(player,0,60,ReviveStatus.BLEEDING,null,e.getCause(),armor ,plugin);
								pr.Knocked();
								plugin.getKnockedPlayer().put(player, pr);
								
							}else {
								ci.GameDamageCauses(player, e.getCause());
							}
							return;
				}
					
			}
			
		}
		

		
		
	}
	 
	
	public boolean isAntiGasMask(Player player,ItemStack it) {
		if(it != null) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && ChatColor.stripColor(it.getItemMeta().getDisplayName()).contains("MASCARA ANTIGAS")) {
		
					return true;
			}
		}

		return false;
	}
	
	public void breakAntiGasMask(Player player,ItemStack it) {
			
			//Durabilidad 5/5
		
			if(it.hasItemMeta() && it.getItemMeta().hasLore()) {
				if(it.getItemMeta().getLore().size() > 1 && !ChatColor.stripColor(it.getItemMeta().getLore().get(2)).startsWith("Durabilidad:")) {
					
					return;
				}
				
			}
		
			List<String> lore = it.getItemMeta().getLore();
			ItemMeta meta = it.getItemMeta();
			String info = ChatColor.stripColor(lore.get(2));
			String[] letra = info.split(" ");
			String numero = letra[1];
			String[] num = numero.split("/");
			int dur = Integer.valueOf(num[0]);
			int limit = Integer.valueOf(num[1]);
			dur = dur -1;
			if(dur == 0) {
				player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 50.0F, 0F);
				player.sendMessage(ChatColor.RED+"Mascara Anti Gas rota.");
				if(player.getInventory().getHelmet() != null) {
					player.getInventory().setHelmet(new ItemStack(Material.AIR));
				}
				
			  return ;
			}
			if(dur >= 20 && dur <= 30) {
				lore.set(2,ChatColor.GOLD+"Durabilidad: "+ChatColor.GREEN+dur+ChatColor.RED+"/"+ChatColor.GREEN+limit);
			}else if(dur >= 10 && dur <= 19) {
				lore.set(2,ChatColor.GOLD+"Durabilidad: "+ChatColor.YELLOW+dur+ChatColor.RED+"/"+ChatColor.GREEN+limit);
			}else if(dur >= 1 && dur <= 9) {
				lore.set(2,ChatColor.GOLD+"Durabilidad: "+ChatColor.RED+dur+ChatColor.RED+"/"+ChatColor.GREEN+limit);
			}
			
			player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 50.0F, 0F);
			meta.setLore(lore);
			it.setItemMeta(meta);
			player.getInventory().setHelmet(it);
			
			return ;
		}
	
	
	public void breakMgItem(Player player,ItemStack it,String itembreakmessage) {
		
		//Durabilidad 5/5
		String name = it.getItemMeta().hasCustomName() ? it.getItemMeta().getDisplayName() : "-.No hay Display.-";
		if(player.getInventory().getContents().length >= 1) {
			for (ItemStack itemStack : player.getInventory().getContents()) {
				if(itemStack == null) continue;
			
			
				if(itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().equals(name))
				if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
					if(itemStack.getItemMeta().getLore().size() > 1 && !ChatColor.stripColor(itemStack.getItemMeta().getLore().get(2)).startsWith("Durabilidad:")) continue;
					
				}
			
				List<String> lore = it.getItemMeta().getLore();
				ItemMeta meta = it.getItemMeta();
				String info = ChatColor.stripColor(lore.get(2));
				String[] letra = info.split(" ");
				String numero = letra[1];
				String[] num = numero.split("/");
				int dur = Integer.valueOf(num[0]);
				int limit = Integer.valueOf(num[1]);
				dur = dur -1;
				if(dur == 0) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 50.0F, 0F);
					player.sendMessage(ChatColor.RED+itembreakmessage);
					if(player.getInventory().getHelmet() != null) {
						player.getInventory().setHelmet(new ItemStack(Material.AIR));
					}
					
				  return ;
				}
				if(dur >= 20 && dur <= 30) {
					lore.set(2,ChatColor.GOLD+"Durabilidad: "+ChatColor.GREEN+dur+ChatColor.RED+"/"+ChatColor.GREEN+limit);
				}else if(dur >= 10 && dur <= 19) {
					lore.set(2,ChatColor.GOLD+"Durabilidad: "+ChatColor.YELLOW+dur+ChatColor.RED+"/"+ChatColor.GREEN+limit);
				}else if(dur >= 1 && dur <= 9) {
					lore.set(2,ChatColor.GOLD+"Durabilidad: "+ChatColor.RED+dur+ChatColor.RED+"/"+ChatColor.GREEN+limit);
				}
				
				player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 50.0F, 0F);
				meta.setLore(lore);
				it.setItemMeta(meta);
				player.getInventory().setHelmet(it);
			}}
		

		
		return ;
	}
	
//	public void breakAntiGasMask(Player player,ItemStack it,int slot) {
//		
//		//Durabilidad 5/5
//	
//		if(it.hasItemMeta() && it.getItemMeta().hasLore()) {
//			if(it.getItemMeta().getLore().size() > 1 && !ChatColor.stripColor(it.getItemMeta().getLore().get(2)).startsWith("Durabilidad:")) {
//				return;
//			}
//			
//		}
//	
//		List<String> lore = it.getItemMeta().getLore();
//		ItemMeta meta = it.getItemMeta();
//		String info = ChatColor.stripColor(lore.get(2));
//		//player.sendMessage("info eh "+ info);
//		String[] letra = info.split(" ");
//		String numero = letra[1];
//		String[] num = numero.split("/");
//		int dur = Integer.valueOf(num[0]);
//		int limit = Integer.valueOf(num[1]);
//		if(dur == 0) {
//			player.sendMessage(ChatColor.RED+"Mascara Anti Gas rota.");
//			if(player.getInventory().getHelmet() != null) {
//				player.getInventory().setHelmet(new ItemStack(Material.AIR));
//			}
//			
//		  return ;
//		}
//		dur = dur -1;
//		lore.set(2,ChatColor.GOLD+"Durabilidad: "+ChatColor.GREEN+dur+ChatColor.RED+"/"+ChatColor.GREEN+limit);
//	
//		meta.setLore(lore);
//		it.setItemMeta(meta);
//		player.getInventory().setItem(slot, it);
//		return ;
//	}
	
	
	public void spawnToxicCloud(Player player,String name) {
		PotionEffect effect = new PotionEffect(PotionEffectType.INSTANT_DAMAGE,/*duration*/ 15*20,/*amplifier:*/25, false ,false,true );
		AreaEffectCloud aec = (AreaEffectCloud) player.getWorld().spawnEntity(player.getLocation(),  EntityType.AREA_EFFECT_CLOUD);
		aec.addCustomEffect(effect, true);
		aec.setCustomName(name);
		aec.setColor(Color.GREEN);
		aec.setDuration(10*20);
		aec.setRadius(1);
		aec.setReapplicationDelay(5*20);
		return;
	}
	
	
	
	
	public List<Entity> getNearbyEntites(Location l , int size){
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : l.getWorld().getEntities()) {
			if(l.distance(e.getLocation()) <= size) {
				entities.add(e);
			}
		}
		return entities;
		
		
	}
	
	//TODO CHEST
	public void getNearbyBlocksParticle(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Chest-Particle-Range") ;
		
		if (r.getType().equals(Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

				
						Block a = r.getRelative(x, y, z);
						
						// setea bloques en esos puntos
						
						if(player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SPECTATOR ) {
								if(a.getType() == Material.CHEST) {
									  Chest chest = (Chest) a.getState();
									  if(chest.getCustomName() != null) {
										
										  if(chest.getCustomName().contains("TIENDA")) {
												chest.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, chest.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
												
										  }
										  
										  if(chest.getCustomName().contains("REVIVIR")) {
											  chest.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, chest.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
												
										  }
										  
								
									  }
								}
								Block a1 = r.getRelative(x, y-1, z);
								if(a.getType() == Material.ENCHANTING_TABLE && a1.getType() == Material.BEDROCK) {
									EnchantingTable t = (EnchantingTable) a.getState();
									t.getWorld().spawnParticle(Particle.ENCHANT, t.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
								}
								
								
								//player.sendMessage("Hay un bloque de diamante en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
							}

						

					

					}
					;
				}
				;
			}
			;


		}
	}
	

	
//	public void dropItemsPlayer(Player player) {
//		if(player.getInventory().getContents().length >= 1) {
//			for (ItemStack itemStack : player.getInventory().getContents()) {
//				if(itemStack == null) continue;
//						
//					player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
//                    player.getInventory().removeItem(itemStack);
//              }
//				player.getInventory().clear(); 
//		}
//		
//	}
	
	
	
//	public void Dead(Player player) {
//		ClassIntoGame c = new ClassIntoGame(plugin);
//		c.PlayerDeadInArena(player);
//		player.setGameMode(GameMode.SPECTATOR);
//		
//	}
	
	
////	public void SpawnPlayerZombi(Player player) {
////		ClassArena ca = new ClassArena(plugin);
////		String arena = plugin.getArenaPlayerInfo().get(player);		 
////		 FileConfiguration config = ca.getConfigGame(arena);
////		if(config.getInt("Game-Mode") == 3) {
////			return;
////		}
//		
//		PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );   
//	    PotionEffect salto= new PotionEffect(PotionEffectType.JUMP,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );
//		
//		ItemStack item = new ItemStack(Material.PLAYER_HEAD,/*CANTIDAD*/1);
//		SkullMeta meta = (SkullMeta) item.getItemMeta();
//		meta.setOwningPlayer(player);
//		item.setItemMeta(meta);
//		
//	    
//		LivingEntity entidad = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
//		Zombie zombi = (Zombie) entidad;
//		zombi.getEquipment().setHelmet(item);
//		zombi.setCanPickupItems(true);
//		zombi.setCustomName(ChatColor.RED+player.getName());
//		zombi.setCustomNameVisible(true);
//		zombi.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
//		zombi.addPotionEffect(rapido);
//		zombi.addPotionEffect(salto);
//		
//		//plugin.getDeadmob().put(player, player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ());
//		
//		LivingEntity entidad1 = (LivingEntity) player.getWorld().spawnEntity(player.getLocation().add(0,-1,0), EntityType.ARMOR_STAND);
//		ArmorStand a = (ArmorStand) entidad1;
//		a.setCustomName(ChatColor.YELLOW+player.getName()+ChatColor.RED+" Murio aqui.");
//		a.setCustomNameVisible(true);
//		a.setCollidable(false);
//		a.setInvisible(true);
//		a.setInvulnerable(true);
//		a.setGravity(false);
//	}
//	
	
//	public Block isYourBlockInRange(Location l ,Block b) {
//		Block block = Material.AIR;
//	}
	
	public void DetectChestAndCommandArmorStand(Player player) {
		//FileConfiguration config = plugin.getConfig();
		
		
		
		
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		
	//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
			int rango = 10;
		//	Powerable pw = (Powerable) r.getBlockData();
		//	if(pw.isPowered()) {

				for (int x = -rango; x < rango+1; x++) {
					for (int y = -rango; y < rango+1; y++) {
						for (int z = -rango; z < rango+1; z++) {
	
							Block a = r.getRelative(x, y, z);
	
							// setea bloques en esos puntos
							
	
								if(a.getType() == Material.CHEST) {
									
									  Chest chest = (Chest) a.getState();
									  if(chest.getCustomName() != null) {
										  if(chest.getCustomName().contains("C-A")) {
											  if(!chest.getInventory().isEmpty()) {
													for (ItemStack itemStack : chest.getInventory().getContents()) {
															if(itemStack == null) continue;
														
																
																	if(!itemStack.hasItemMeta()) continue;
																	
																	ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
																	
																	if(!bm.hasDisplayName()) continue;
																	
																	//EFFECT DURA POTENCIA
																	//
																	   String n = bm.getDisplayName();
																	  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																	  Bukkit.dispatchCommand(console, n);
																	   //player.performCommand(n);
															 
															
														
										              }
											  }
										  }
							          }
								
									//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
								}
	
		
						};
					};
				};
		//	}	
			//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

		//}
	}
	
	public void DetectDoor(Player player) {
		//FileConfiguration config = plugin.getConfig();
		
		
		
		
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		
	//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
			int rango = 5;
		//	Powerable pw = (Powerable) r.getBlockData();
		//	if(pw.isPowered()) {

				for (int x = -rango; x < rango+1; x++) {
					for (int y = -rango; y < rango+1; y++) {
						for (int z = -rango; z < rango+1; z++) {
	
							Block a = r.getRelative(x, y, z);
	
							// setea bloques en esos puntos
							
	
								if(a.getType() == Material.CHEST) {
									
									  Chest chest = (Chest) a.getState();
									  if(chest.getCustomName() != null) {
										  if(chest.getCustomName().contains("DOOR")) {
											  if(!chest.getInventory().isEmpty()) {
												  
												  /*if(chest.getInventory().getItem(2) != null) {
													  ItemStack it = chest.getInventory().getItem(2);
													  if(player.getInventory().containsAtLeast(it, it.getAmount())) {
														  if(player.getLocation().distance(chest.getLocation()) < 3) {
															  if(chest.getInventory().getItem(0) != null) {
																  ItemStack it2 = chest.getInventory().getItem(0);
																  
																  if(it2.hasItemMeta()) {
																	  ItemMeta bm = (ItemMeta) it2.getItemMeta();
																	  if(bm.hasDisplayName()) {
																		  String n = bm.getDisplayName().replace("%exe%", "execute at "+player.getName()+" run ");
																			  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																			  Bukkit.dispatchCommand(console, n);
																	  }
																  }
																  
															  }
														  }
														  if(player.getLocation().distance(chest.getLocation()) >= 4 || player.getLocation().distance(chest.getLocation()) <= 5) {
															  if(chest.getInventory().getItem(1) != null) {
																  ItemStack it2 = chest.getInventory().getItem(1);
																  if(it2.hasItemMeta()) {
																	  ItemMeta bm = (ItemMeta) it2.getItemMeta();
																	  if(bm.hasDisplayName()) {
																		     String n = bm.getDisplayName().replace("%exe%", "execute at "+player.getName()+" run ");
																			  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																			  Bukkit.dispatchCommand(console, n);
																	  }
																  }
																  
															  }
														  }
														  
													  }
													  
													  
												  }*/
												
											
												  
												  
												  
												
											  }
										  }
							          }
								
									//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
								}
	
		
						};
					};
				};
		//	}	
			//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

		//}
	}
	
	
	public int RandomPosOrNeg(int i) {
		Random r = new Random();
		int v = r.nextInt(i+1);
		int r2 = r.nextInt(1+1);
		if(r2 == 1) {
			return v;
		}
		return TransformPosOrNeg(v);
		
	}
	
	public int TransformPosOrNeg(int i) {
		return i =  (~(i -1));
	}
	
}
