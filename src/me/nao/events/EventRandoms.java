package me.nao.events;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.EnchantingTable;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Dispenser;
//import org.bukkit.block.sign.SignSide;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.nao.cosmetics.fireworks.RankPlayer;
import me.nao.general.info.GameInfo;
import me.nao.general.info.GameNexo;
import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameConditions;
import me.nao.general.info.PlayerInfo;
import me.nao.items.actions.Flare;
import me.nao.main.game.Main;
import me.nao.manager.EstadoPartida;
import me.nao.shop.Items;
import me.nao.shop.MinigameShop1;


public class EventRandoms implements Listener{
	
	private Main plugin;
	
	public EventRandoms(Main plugin) {
		this.plugin = plugin;
	}
	
	//@EventHandler(priority = EventPriority.LOWEST)
	public void runev(PlayerToggleSprintEvent e) {
	
		Player player = (Player) e.getPlayer();
		int t = 0;
		if(player.isSprinting()) {
			player.sendMessage("Tiempo corriendo "+(t+1));
		}else {
			player.sendMessage("stop");
		}
		
	}
	
	

	@EventHandler(priority = EventPriority.LOWEST)
	public void shifting(PlayerToggleSneakEvent e) {
		Player player = (Player) e.getPlayer();
		if(!player.getPassengers().isEmpty() && player.isSneaking()) {
			player.removePassenger(player.getPassengers().get(0));
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void ClickEntity(PlayerInteractAtEntityEvent e) {
		Player player = (Player) e.getPlayer();
		GameConditions gc = new GameConditions(plugin);
		
		if(gc.isPlayerinGame(player)) {
			if(!player.getPassengers().isEmpty() ) {
				player.sendMessage(ChatColor.RED+"Ya tienes una Entidad encima.");
				return;
			}
			PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pi.getMapName());
			if(gi.getEstopartida() == EstadoPartida.JUGANDO) {
				
				Entity ent = e.getRightClicked();
				if(ent.getType() == EntityType.PLAYER) {
					
					Player target = (Player) ent;
					if(target.getGameMode() != GameMode.SPECTATOR) {
							player.addPassenger(target);
					}
				}else {
					player.addPassenger(ent);
				}
				return;
			}
		}
		
	}
	
	
	//TODO INTERACCION
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void alinteractuar(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		
		if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().isSimilar(Items.BENGALAROJA.getValue()) || player.getInventory().getItemInMainHand().isSimilar(Items.BENGALAVERDE.getValue())) {
			new Flare(player, player.getInventory().getItemInMainHand(),player.getEyeLocation());
		
			return;
		}
		
		
		GameConditions gc = new GameConditions(plugin);
		if(gc.isPlayerinGame(player)) {
			
				if(e.getAction() == Action.PHYSICAL) {
					if(e.getClickedBlock().getType() == Material.TRIPWIRE) {
						
						DetectDispenser(player.getLocation());
						//Bukkit.broadcastMessage("1");
						
						
					}
					if(e.getClickedBlock().getType() == Material.STONE_PRESSURE_PLATE) {
						//Bukkit.broadcastMessage("3");
						
						DetectDispenser(player.getLocation());
						
					}
					if(e.getClickedBlock().getType() == Material.OAK_PRESSURE_PLATE) {
						//Bukkit.broadcastMessage("3");
						DetectChestAndJump(player);
						
					}
					
					if(e.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
						//Bukkit.broadcastMessage("3");
						//locacion , poder de explosion , genera fuego? , rompe bloques?
						player.getWorld().createExplosion(player.getLocation(), 5, false, false);
						
					}
					
					if(e.getClickedBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
						//Bukkit.broadcastMessage("3");
						//locacion , poder de explosion , genera fuego? , rompe bloques?
						player.getWorld().createExplosion(player.getLocation(), 10, false, false);
						
					}
					
			
					
				}
				
				//QUEDA PENDIENTE EL DE LOS PUNTOS
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK ) {
					
					
					
					
					 Block b2 = player.getTargetBlock((Set<Material>) null, 5);
					  if(b2.getType() == Material.OAK_SIGN || b2.getType() == Material.OAK_WALL_SIGN || b2.getType() == Material.BIRCH_WALL_SIGN || b2.getType() == Material.BIRCH_SIGN) {
						  Sign sign = (Sign) b2.getState();
						  if(!sign.getLine(0).isEmpty() && ChatColor.stripColor(sign.getLine(0)).equals("Kit-MG")) {
							  if(!sign.getLine(1).isEmpty()) {
								  String name = ChatColor.stripColor(sign.getLine(1));
								  gc.getInventorySing(name, player);
							  }
						  }
						  
					  }
					
					
						Block b3 = e.getClickedBlock();
						Block b1 = b3.getRelative(0, -1, 0);
					 if(b3.getType() == Material.STONE_BUTTON ) {
						 
						 	YouNeedYourFriends(player,b3);
							DetectChestAndDrop(player,b3);
							DetectChestAndReadBook(player, b3);
							DetectChestAndPotion(player, b3);
							DetectChestAndCommand(player.getLocation());
							DetectChestAndArmorSoulStand(player);
							DetectChestAndArmorNexoStand(player);
							
					 }
						
						
					  if(b3.getType() == Material.CHEST ) {
						  
						  if(player.getGameMode() == GameMode.SPECTATOR) {
							  e.setCancelled(true);
							  player.sendMessage(ChatColor.RED+"No puedes interactuar con eso.");
						  }
						  
						  Chest chest = (Chest) b3.getState();
						  
						  /*CODIGO PARA SABER SI ES COFRE DOBLE
						  Inventory invc = chest.getInventory();
						  if(invc instanceof DoubleChestInventory) {
							  DoubleChestInventory dc = (DoubleChestInventory) invc;
							 
							  
						  }
						  */
						  if(player.getGameMode() == GameMode.ADVENTURE) {
								  if(chest.getCustomName() != null) {
										  if(chest.getCustomName().contains("TIENDA")) {
											  	e.setCancelled(true);
											  	plugin.getPags().put(player, 1);
											    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 20.0F, 1F);
											  	MinigameShop1 inv = new MinigameShop1(plugin);
												inv.createInv(player);
												
										  }
										  if(chest.getCustomName().contains("TIENDA TEST")) {
											
												if(chest.getInventory().isEmpty()) {
													MinigameShop1 inv = new MinigameShop1(plugin);
													inv.CreateInvChest(player, chest);
												}
											
											  
												
										  }
										  if(chest.getCustomName().contains("TRAMPA1")) {
											  e.setCancelled(true);
											  player.setVelocity(player.getLocation().getDirection().multiply(-3));
											  player.sendMessage(ChatColor.RED+"Cofre trampa activado.");
											  
												
										  }
										  if(chest.getCustomName().contains("REVIVIR")) {
											  e.setCancelled(true);
											  plugin.getPags().put(player, 1);
											  player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 20.0F, 1F);
											  	MinigameShop1 inv = new MinigameShop1(plugin);
												inv.GameReviveInv(player);											
										  }
										  
									 
								  }
							  }
						 
					  }
					  if(player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SPECTATOR) {
						  if(!player.isOp() && b3.getType() == Material.DISPENSER) {
								 
								 e.setCancelled(true);
								 player.sendMessage(ChatColor.RED+"No puedes interactuar con eso.");
							
					  }
					  if(b3.getType() == Material.ENDER_CHEST) {
							if(player.getGameMode() == GameMode.ADVENTURE) {
								 e.setCancelled(true);
								 }
					  }
					  
					  if(b3.getType() == Material.ENCHANTING_TABLE && b1.getType() == Material.BEDROCK) {
//							EnderChest enderchest = (EnderChest) b3.getState();
							EnchantingTable t = (EnchantingTable) b3.getState();
							if(player.getGameMode() == GameMode.ADVENTURE) {
								 e.setCancelled(true);
						 
								 player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 20.0F, 1F);
								//enderchest.getWorld().dropItem(enderchest.getLocation().add(0.5,1,0.5), arg1);
								spawnLootEnchantedTable(player,t.getLocation());
								
							}else {
								 e.setCancelled(true);
								player.sendMessage(ChatColor.RED+"No puedes interactuar con eso.");
							}
							
					  }
				 }
					  
			
						
					
				}
				
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
					if (e.getItem() != null) {
						
						
						if (e.getItem().isSimilar(Items.JEDIP.getValue())) {
				
							player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20.0F, 1F);
							for(Entity e1 : getNearbyEntites(player.getLocation(),20)) {
								if(!(e1.getType() == EntityType.PLAYER)) {
									e1.setVelocity(e1.getLocation().getDirection().multiply(-3).setY(1));
								}
							}
							removeItemstackCustom(player, Items.JEDIP.getValue());
				       }
						
						if (e.getItem().isSimilar(Items.CHECKPOINTP.getValue())) {
							
							
							plugin.getCheckPoint().put(player, player.getLocation());
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
							player.sendMessage(ChatColor.GREEN+"CheckPoint Marcado.");
							removeItemstackCustom(player, Items.CHECKPOINTP.getValue());
						}
						 
						
					if (e.getItem().isSimilar(Items.RAINARROW.getValue())) {
							player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
						
						//player.launchProjectile(org.bukkit.entity.EnderPearl.class);
						
						
							LaunchRandomArrow(player, 0, 0);
							LaunchRandomArrow(player, 5, 5);
							LaunchRandomArrow(player, -5, 5);
							LaunchRandomArrow(player, 5, -5);
							LaunchRandomArrow(player, -5, -5);
						}
						
					if (e.getItem().isSimilar(Items.STOREXPRESSP.getValue())) {
																		MinigameShop1 inv = new MinigameShop1(plugin);
									inv.createInv(player);
									removeItemstackCustom(player, Items.STOREXPRESSP.getValue());		
						}
					if (e.getItem().isSimilar(Items.OBJETIVOSP.getValue())) {
						MinigameShop1 ms = new MinigameShop1(plugin);
						ms.ShowObjetives(player);
					}

						
					if (e.getItem().isSimilar(Items.REFUERZOSP.getValue())) {
						Location loc = player.getLocation();
						Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.IRON_GOLEM);
						IronGolem ih = (IronGolem) h1;
						ih.setCustomName(ChatColor.GOLD+player.getName());
						removeItemstackCustom(player, Items.REFUERZOSP.getValue());
						
						
			          }
					
					if (e.getItem().isSimilar(Items.REFUERZOS2P.getValue())) {
						Location loc = player.getLocation();
						Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.SNOWMAN);
						Snowman ih = (Snowman) h1;
						ih.setCustomName(ChatColor.GOLD+player.getName());
						removeItemstackCustom(player, Items.REFUERZOS2P.getValue());
						
						
			         }
					if (e.getItem().isSimilar(Items.BAZUKAP.getValue())) {
						if(player.getInventory().containsAtLeast(Items.COHETEP.getValue(), 1)) {
							removeItemstackCustom(player, Items.COHETEP.getValue());
							Location loc = player.getLocation();
							Entity fb = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.FIREBALL);
							fb.setVelocity(loc.getDirection().multiply(3));
							player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 20.0F, 1F);
							Fireball f = (Fireball) fb;
							f.setYield(10);
							((Fireball) fb).setShooter(player);
						}else {
							player.sendMessage(ChatColor.RED+"No tienes mas municion de Bazooka");

						}
					}
					if (e.getItem().isSimilar(Items.ARROWLP.getValue())) {
							if(player.getInventory().containsAtLeast(new ItemStack(Material.ARROW), 2)) {
									Location loc = player.getLocation();
									Location loc2 = player.getLocation();
									player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
			
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
			
									((Arrow) h1).setShooter(player);
									((Arrow) h2).setShooter(player);
									
									player.getInventory().removeItem(new ItemStack(Material.ARROW, 2));
								}else {
									player.sendMessage(ChatColor.RED+"Necesitas tener 2 Flechas minimo para Disparar");
								}
					}
					
					
						if (e.getItem().isSimilar(Items.MEDICOP.getValue())) {
							Location loc = player.getLocation();
							Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.VILLAGER);
							Villager v = (Villager) h1;
							v.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"Medico "+ChatColor.RED+ChatColor.BOLD+"+");
							v.setProfession(Profession.CLERIC);
							v.setTicksLived(1200);
							removeItemstackCustom(player, Items.MEDICOP.getValue());
							
							gc.SendMessageToUsersOfSameMap(player, ChatColor.AQUA+"El Jugador "+ChatColor.GREEN+player.getName()+ChatColor.AQUA+" a un Medico");
							
							
							
				        }
					
						
					}}
			
		}
		


		
	}
	
	@EventHandler
	public void EnchantedItemMg(EnchantItemEvent e) {
		
	}
	
	
	//TODO JOIN
	@EventHandler
	public void JoinServer(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		

		
		
		
		
		if(player.isOp()) {
			
			//e.setJoinMessage(ChatColor.GRAY+"["+ChatColor.RED+"!"+ChatColor.GRAY+"]"+ChatColor.LIGHT_PURPLE+" Entro al server de Test un Admin "+ChatColor.GOLD+player.getName());
		}else {
		//  e.setJoinMessage(ChatColor.GRAY+"["+ChatColor.RED+"!"+ChatColor.GRAY+"]"+ChatColor.AQUA+" Entro al server de Test "+ChatColor.GREEN+player.getName());
		}
	
		
		
	}
	 @EventHandler
	public void LeavePlayerServer_Arena(PlayerQuitEvent e) {
		    Player player = (Player) e.getPlayer();
		
		    GameConditions gc = new GameConditions(plugin);
			gc.IlegalLeaveMapConexion(player);
		    //gc.LeaveOfTheGame(player);
		if(player.isOp()) {
		//	e.setQuitMessage(ChatColor.GRAY+"["+ChatColor.RED+"!"+ChatColor.GRAY+"]"+ChatColor.RED+" El Admin Salio al server de Test "+ChatColor.GOLD+player.getName());
		}else {
		//	e.setQuitMessage(ChatColor.GRAY+"["+ChatColor.RED+"!"+ChatColor.GRAY+"]"+ChatColor.RED+" Salio al server de Test "+ChatColor.GREEN+player.getName());
		}
	
		return;	
		
	}
	
	 
	 	@EventHandler(priority = EventPriority.LOWEST)
		public void PlayerBreakMG(BlockBreakEvent e) {
	 		Player player = e.getPlayer();
	 		GameConditions gm = new GameConditions(plugin);
	 		if(gm.isPlayerinGame(player)) {
	 			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
	 			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
	 			if(gi instanceof GameNexo) {
	 				GameNexo gn = (GameNexo) gi;
	 				if(gn.getBlueTeamMg().contains(player.getName()) && gn.getRedNexoLocation() == e.getBlock().getLocation()){
	 					List<Player> redt = gm.ConvertStringToPlayer(gn.getRedTeamMg());
	 					if(gn.getRedLifeNexo() != 0) {
	 						
	 						
	 						gn.SetRedLifeNexo(gn.getRedLifeNexo()-1);
	 						for(Player rd : redt) {
	 							rd.sendMessage(ChatColor.BLUE+player.getName()+" "+ChatColor.GOLD+" esta Atacando el Nexo: "+ChatColor.GREEN+gn.getRedLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
	 						}
	 						
		 					e.setCancelled(true);
	 					}else if(gn.getRedLifeNexo() == 0)  {
	 						
	 						for(Player rd : redt) {
	 							rd.sendMessage(ChatColor.BLUE+player.getName()+" "+ChatColor.GOLD+" Destruyo el Nexo: "+ChatColor.GREEN+gn.getRedLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
	 						}
	 						
	 						gn.setEstadopartida(EstadoPartida.TERMINANDO);
	 						e.setCancelled(true);
	 					}
	 				
	 				}else if(gn.getRedTeamMg().contains(player.getName()) && gn.getBlueNexoLocation() == e.getBlock().getLocation()){
	 					List<Player> bluet = gm.ConvertStringToPlayer(gn.getBlueTeamMg());
	 					if(gn.getBlueLifeNexo() != 0) {
	 						

	 						gn.SetBlueLifeNexo(gn.getBlueLifeNexo()-1);
	 						
	 						for(Player bl : bluet) {
	 							bl.sendMessage(ChatColor.RED+player.getName()+" "+ChatColor.GOLD+" esta Atacando el Nexo: "+ChatColor.GREEN+gn.getBlueLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
	 						}
		 					e.setCancelled(true);
	 					}else if(gn.getBlueLifeNexo() == 0){
	 						
	 						for(Player bl : bluet) {
	 							bl.sendMessage(ChatColor.RED+player.getName()+" "+ChatColor.GOLD+" Destruyo el Nexo: "+ChatColor.GREEN+gn.getBlueLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
	 						}
	 						
	 						gn.setEstadopartida(EstadoPartida.TERMINANDO);
	 						e.setCancelled(true);

	 					}
	 					
	 				}
	 			}
	 			
	 		}
	 		
	 		
		}
	 
	

	 @EventHandler
	 public void PlayerPlace(BlockPlaceEvent event) {
		 
	//	 if(event.getBlock().getLocation().subtract(0,1,0).getBlock().getType() != Material.AIR) 
		//	 return;
			// event.getBlock().getWorld().spawnFallingBlock(event.getBlock().getLocation(),event.getBlock().getType() ,event.getBlock().getData());
			 //event.getBlock().setType(Material.AIR);
			 
		
		 
		 
	 }
	 
	//TODO NEXO
		public void NexoArmor(Player player) {
//			
//			List<Entity> list = getNearbyEntites(player.getLocation(), 200);
//  			
//  			for(Entity e : list) {
//  				if(e.getType() != EntityType.PLAYER && e instanceof Creature) {
//  					if(e.getType() != EntityType.IRON_GOLEM || e.getType() != EntityType.SNOWMAN ) continue;
//  						Creature ent = (Creature) e;
//  	  					ent.setTarget(player);
//  					
//  				}
//  			}
//			 
//			
//  			List<Entity> l = getNearbyEntites(player.getLocation(),30);
//			for(Entity e1 : l) {
//				// 
//				if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
//					// VIDA  10
//					//ALMAS 10 / 10
//					String name = ChatColor.stripColor(e1.getCustomName());
//					
//					if(name.contains("VIDA")) {
//						String[] sp = name.split(" ");
//						int nm = Integer.valueOf(ChatColor.stripColor(sp[1]));
//					    nm = nm - 1;
//						if(nm <= 0) {
//							SourceOfDamage s = new SourceOfDamage(plugin);
//							s.DetectChestAndCommandArmorStand(player);
//							
//							List<Entity> l2 = getNearbyEntites(player.getLocation(),30);
//							
//							for(Entity e2 : l2) {
//								if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null ) {
//									
//									String name2 = ChatColor.stripColor(e2.getCustomName());
//									if(name2.contains("NEXO") || name2.contains("VIDA")) {
//										e2.getWorld().spawnParticle(Particle.CLOUD, e2.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */10, 3, 1, 3, /* velocidad */0, null, true);
//										e2.remove();
//									}
//								
//									
//								}
//								
//							
//							}
//							
//							player.sendMessage(ChatColor.GREEN+"El Nexo a sido Destruido.");
//							 String arena = plugin.getArenaPlayerInfo().get(player);
//							 for(Player players : Bukkit.getOnlinePlayers()) {
//								 if(players.getName().equals(player.getName())) continue;
//								 String arena2 = plugin.getArenaPlayerInfo().get(players);
//								 if(arena2 != null && arena2.equals(arena)) {
//									
//									 players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Termino de Destruir el Nexo.");							 
//									 
//								 }
//								 
//							 }
//						}else {
//							
//							 	e1.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"VIDA "+ChatColor.GREEN+nm);	
//								e1.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
//								e1.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
//						}
//						
//					
//						
//						
//					}
//					
//					
//					
//				
//				
//					
//				}
//			}
//			
		}
	
	
	
		
	
	 @EventHandler 
	 public void onDamage(EntityDamageByEntityEvent e) {
		 
		 
		 Entity entidad = e.getDamager();
		 Entity entidadAtacada = e.getEntity();
		 
		 
	
		  if(entidad instanceof Player) {
				Player player = (Player) entidad;
		  
				GameConditions gc = new GameConditions(plugin);
				
				 
				if(!gc.isPlayerinGame(player)) {
					return;
				}
					
					
					PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
					
					 if(entidadAtacada instanceof LivingEntity) {
						 LivingEntity mob = (LivingEntity) entidadAtacada;
						  	pl.getGamePoints().setDamage(pl.getGamePoints().getDamage()+ConvertDoubleToInt(mob.getHealth()-mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
					 }
					
					  if(entidadAtacada instanceof Player || entidadAtacada instanceof Villager) {
						  String arenaName = pl.getMapName();
							
				 			if(!gc.isPvPAllowed(arenaName)) {
				 				e.setCancelled(true);
				 				player.sendMessage(ChatColor.RED+"El PVP en el Mapa "+ChatColor.GOLD+arenaName+ChatColor.RED+" no esta Habilitado");
				 			}
							
							
							return;
					 }
					
					
					
					
				
				
			
			  
		  }else if(entidad instanceof Monster) {
			  if(entidadAtacada instanceof Player) {
				    Player player = (Player) entidadAtacada;
					GameConditions gc = new GameConditions(plugin);
					if(gc.isPlayerinGame(player)) {
						plugin.CreditKill().put(player, entidad);
					}
			  }
		  }
				
		 
		 
//		 
//		 FileConfiguration config = plugin.getConfig();
//		 List<String> hw = config.getStringList("HeadShoot-World.List");
//		 
//		 		
//		 	Entity entidad = event.getDamager();
//			Entity entidadAtacada = event.getEntity();
//		   
//			
//			
//			
//			if(entidad.getType() == EntityType.PLAYER && entidadAtacada.getType() == EntityType.PLAYER) {
						
//						
//						
//						
//						
//			 		return;
//			 	}
//		 		
//		 	
//		 		
//		 	
//		 
//	        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
//	            return;
//	        }
//	        
//	        if(event instanceof Projectile) {
//	        	  Projectile projectile = (Projectile)event.getDamager();
//	  	          Entity shooter1 = (Entity) projectile.getShooter(); 
//	  	          if(shooter1 instanceof Player) {
//	  	        	PlayerInfo pl = plugin.getPlayerInfoPoo().get(shooter1);
//	  	        	 Entity damaged = event.getEntity();
//					if(pl.isPlayerInMision()) {
//						YamlFilePlus u = new YamlFilePlus(plugin);
//						FileConfiguration ym = u.getSpecificYamlFile("Arenas",pl.getMapName());
//	        			//si no esta en true
//						
//						if() {
//							
//						}
//						
//		        			if(!ym.getBoolean("Allow-PVP")) {
//		        				event.setCancelled(true);
//		        				shooter.sendMessage(ChatColor.RED+"El PVP en la arena "+ChatColor.GOLD+pl.getMapName()+ChatColor.RED+" no esta Habilitado2");
//		        			}
//					}
//	  	          }
//	  	          
//	        
//	        }
//	        
//	  try { 
//		    Projectile projectile = (Projectile)event.getDamager();
//	        Entity shooter1 = (Entity) projectile.getShooter(); 
//	        if (shooter1 instanceof Player) {
//	        	
//	        	Player shooter = (Player) shooter1;
//	            Entity damaged = event.getEntity();
//	            
//	            double projectileY = projectile.getLocation().getY();
//	            double damagedY = damaged.getLocation().getY();
//	            
//	            if (damaged instanceof Zombie && (projectileY - damagedY >= 1.60D) ) {// originalmente estaba en 1.35D
//	            	
//	            	Zombie z = (Zombie) damaged;
//	            	if(projectile.getType() == EntityType.ARROW) {
//	            		Arrow aw = (Arrow) projectile;
//	            		if(aw.isCritical()) {
//	            			if(z.isAdult()) {
//						               	
//							   if(z.getEquipment().getHelmet().isSimilar((new ItemStack(Material.AIR))) || z.getEquipment().getHelmet() == null){
//								   if(plugin.PlayerisArena(shooter)) {
//									   ClassIntoGame c = new ClassIntoGame(plugin);
//										//c.PlayerAddPoints(shooter);
//										z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.DIAMOND,5));
//										z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.NETHERITE_INGOT,2));
//						            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.EMERALD,5));
//						            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.GOLD_INGOT,5));
//						            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.IRON_INGOT,5));
//								   }
//							           	shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadShoot "+ChatColor.RED+z.getName());
//								        z.setHealth(0);
//								        
//								        z.getWorld().playEffect(z.getLocation().add(0,1.60,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//								        shooter.playSound(shooter.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
//							      }else {
//							            z.getEquipment().setHelmet(new ItemStack(Material.AIR));
//								         shooter.sendMessage(ChatColor.GREEN+z.getName()+" traia un casco dispara de nuevo");
//							           }
//			            		}
//	            			}
//	            		else {
//	            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//	            		}	
//	          
//	            	}
//	            	
//	            	
//	            	if(projectile.getType() == EntityType.SPECTRAL_ARROW) {
//	            		SpectralArrow aw = (SpectralArrow) projectile;
//	            		if(aw.isCritical()) {
//	            			if(z.isAdult()) {
//						               	
//							   if(z.getEquipment().getHelmet().isSimilar((new ItemStack(Material.AIR))) || z.getEquipment().getHelmet() == null){
//								   if(plugin.PlayerisArena(shooter)) {
//									   ClassIntoGame c = new ClassIntoGame(plugin);
//										//c.PlayerAddPoints(shooter);
//										z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.DIAMOND,5));
//										z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.NETHERITE_INGOT,2));
//						            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.EMERALD,5));
//						            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.GOLD_INGOT,5));
//						            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.IRON_INGOT,5));
//								   }
//							           	shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadShoot "+ChatColor.RED+z.getName());
//								        z.setHealth(0);
//								        
//								        z.getWorld().playEffect(z.getLocation().add(0,1.60,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//								        shooter.playSound(shooter.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
//							      }else {
//							            z.getEquipment().setHelmet(new ItemStack(Material.AIR));
//								         shooter.sendMessage(ChatColor.GREEN+z.getName()+" traia un casco dispara de nuevo");
//							           }
//			            		}
//	            			}
//	            		else {
//	            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//	            		}	
//	          
//	            	}
//	            
//	            
//	               
//	            }
//	        else if ((damaged instanceof Zombie) && (projectileY - damagedY >= 0.60D) ) {
//	          	Zombie z = (Zombie) damaged;
//	            	if(projectile.getType() == EntityType.ARROW) {
//	            		Arrow aw = (Arrow) projectile;
//	            		if(aw.isCritical()) {
//	            			if(!z.isAdult()) {
//			             
//						               	
//						        if(z.getEquipment().getHelmet().isSimilar((new ItemStack(Material.AIR))) || z.getEquipment().getHelmet() == null){
//						        	if(plugin.PlayerisArena(shooter)) {
//						        		 ClassIntoGame c = new ClassIntoGame(plugin);
//										//	c.PlayerAddPoints(shooter);
//											z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.DIAMOND,5));
//											z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.NETHERITE_INGOT,2));
//							            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.EMERALD,5));
//							            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.GOLD_INGOT,5));
//							            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.IRON_INGOT,5));
//									    }
//						            		shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadShoot "+ChatColor.RED+z.getName());
//							            	z.setHealth(0);
//							            	z.getWorld().playEffect(z.getLocation().add(0,0.60,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//							            	shooter.playSound(shooter.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
//						            	}else {
//
//							                z.getEquipment().setHelmet(new ItemStack(Material.AIR));
//							                shooter.sendMessage(ChatColor.GREEN+z.getName()+" traia un casco dispara de nuevo");
//						            	}
//			            		}
//	            			}
//	            		else {
//	            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//	            		}	
//	          
//	            	}
//	            	
//	               	if(projectile.getType() == EntityType.SPECTRAL_ARROW) {
//	               		SpectralArrow aw = (SpectralArrow) projectile;
//	            		if(aw.isCritical()) {
//	            			if(!z.isAdult()) {
//			             
//						               	
//						        if(z.getEquipment().getHelmet().isSimilar((new ItemStack(Material.AIR))) || z.getEquipment().getHelmet() == null){
//						        	if(plugin.PlayerisArena(shooter)) {
//						        		 ClassIntoGame c = new ClassIntoGame(plugin);
//										//	c.PlayerAddPoints(shooter);
//											z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.DIAMOND,5));
//											z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.NETHERITE_INGOT,2));
//							            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.EMERALD,5));
//							            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.GOLD_INGOT,5));
//							            	z.getWorld().dropItem(z.getLocation(), new ItemStack(Material.IRON_INGOT,5));
//									    }
//						            		shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadShoot "+ChatColor.RED+z.getName());
//							            	z.setHealth(0);
//							            	z.getWorld().playEffect(z.getLocation().add(0,1.60,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//							            	shooter.playSound(shooter.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
//						            	}else {
//
//							                z.getEquipment().setHelmet(new ItemStack(Material.AIR));
//							                shooter.sendMessage(ChatColor.GREEN+z.getName()+" traia un casco dispara de nuevo");
//						            	}
//			            		}
//	            			}
//	            		else {
//	            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//	            		}	
//	          
//	            	}
//	            	
//	            }
//
//	            
//	        else if (damaged instanceof Player && (projectileY - damagedY >= 1.60D) ) {// originalmente estaba en 1.35D
//        		Player p = (Player) damaged;
//        		
//        		if(plugin.PlayerisArena(shooter)) {
//        			String arenaName = plugin.getArenaPlayerInfo().get(shooter);
//        			YamlFilePlus u = new YamlFilePlus(plugin);
//					FileConfiguration ym = u.getSpecificYamlFile("Arenas",arenaName);
//        			//si no esta en true
//	        			if(!ym.getBoolean("Allow-PVP")) {
//	        				event.setCancelled(true);
//	        				shooter.sendMessage(ChatColor.RED+"El PVP en la arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no esta Habilitado2");
//	        			}else {
//	        	         	if(p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL)) {
//	    	            		if(projectile.getType() == EntityType.ARROW) {
//	    		            		Arrow aw = (Arrow) projectile;
//	    		            		if(aw.isCritical()) {
//	    				                	
//	    							               	
//	    							      if(p.getInventory().getHelmet() == null){
//	    							            	
//	    							            		shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadSHoot "+ChatColor.RED+p.getName());
//	    								            	p.setHealth(0);
//	    								            	p.getWorld().playEffect(p.getLocation().add(0.5,1,0.5), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//	    								            	
//	    							        }else {
//
//	    								                p.getEquipment().setHelmet(new ItemStack(Material.AIR));
//	    								                shooter.sendMessage(ChatColor.GREEN+p.getName()+" traia un casco dispara de nuevo");
//	    							            	}
//	    				            		
//	    		            			}else {
//	    		            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//	    		            		   }	
//	    		          
//	    		            	}
//	    	            		
//	    	            		if(projectile.getType() == EntityType.SPECTRAL_ARROW) {
//	    	            			SpectralArrow aw = (SpectralArrow) projectile;
//	    		            		if(aw.isCritical()) {
//	    				                	
//	    							               	
//	    							      if(p.getInventory().getHelmet() == null){
//	    							            	
//	    							            		shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadSHoot "+ChatColor.RED+p.getName());
//	    								            	p.setHealth(0);
//	    								            	p.getWorld().playEffect(p.getLocation().add(0.5,1,0.5), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//	    								            	
//	    							        }else {
//
//	    								                p.getEquipment().setHelmet(new ItemStack(Material.AIR));
//	    								                shooter.sendMessage(ChatColor.GREEN+p.getName()+" traia un casco dispara de nuevo");
//	    							            	}
//	    				            		
//	    		            			}else {
//	    		            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//	    		            		   }	
//	    		          
//	    		            	}
//	    	            	}
//	        			}
//        		}else {
//        			
//        			String world = shooter.getWorld().getName();
//        			if(hw.contains(world)) {
//        				if(p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL)) {
//    	            		if(projectile.getType() == EntityType.ARROW) {
//    		            		Arrow aw = (Arrow) projectile;
//    		            		if(aw.isCritical()) {
//    				                	
//    							               	
//    							      if(p.getInventory().getHelmet() == null){
//    							            	
//    							            		shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadSHoot "+ChatColor.RED+p.getName());
//    								            	p.setHealth(0);
//    								            	p.getWorld().playEffect(p.getLocation().add(0.5,1,0.5), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//    								            	
//    							        }else {
//
//    								                p.getEquipment().setHelmet(new ItemStack(Material.AIR));
//    								                shooter.sendMessage(ChatColor.GREEN+p.getName()+" traia un casco dispara de nuevo");
//    							            	}
//    				            		
//    		            			}else {
//    		            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//    		            		   }	
//    		          
//    		            	}
//    	            		
//    	            		if(projectile.getType() == EntityType.SPECTRAL_ARROW) {
//    		            		SpectralArrow aw = (SpectralArrow) projectile;
//    		            		if(aw.isCritical()) {
//    				                	
//    							               	
//    							      if(p.getInventory().getHelmet() == null){
//    							            	
//    							            		shooter.sendMessage(ChatColor.GREEN+shooter.getName()+ChatColor.GOLD+" le dio un HeadSHoot "+ChatColor.RED+p.getName());
//    								            	p.setHealth(0);
//    								            	p.getWorld().playEffect(p.getLocation().add(0.5,1,0.5), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
//    								            	
//    							        }else {
//
//    								                p.getEquipment().setHelmet(new ItemStack(Material.AIR));
//    								                shooter.sendMessage(ChatColor.GREEN+p.getName()+" traia un casco dispara de nuevo");
//    							            	}
//    				            		
//    		            			}else {
//    		            			  shooter.sendMessage(ChatColor.GREEN+" Para dar un headshot debes tenzar todo el arco");
//    		            		   }	
//    		          
//    		            	}
//    	            	}
//        			}
//        		  
//        		}
//        		
//        		
//   
//          
//            
//       
//         }
//	            
//	        else if ((damaged instanceof Player)) {
//					//Player damage = (Player) hit;
//	        		
//	        		if(projectile.getType() == EntityType.ARROW || projectile.getType() == EntityType.TRIDENT || 
//	        				projectile.getType() == EntityType.SMALL_FIREBALL || projectile.getType() == EntityType.FIREBALL 
//	        				|| projectile.getType() == EntityType.SNOWBALL || projectile.getType() == EntityType.EGG
//	        				|| projectile.getType() == EntityType.SPECTRAL_ARROW
//	        				) {
//	        			
//	        			 if(plugin.PlayerisArena(shooter)) {
//				    			String arenaName = plugin.getArenaPlayerInfo().get(shooter1);
//				    			YamlFilePlus u = new YamlFilePlus(plugin);
//								FileConfiguration ym = u.getSpecificYamlFile("Arenas",arenaName);
//								//si no esta en true
//				        			if(!ym.getBoolean("Allow-PVP")) {
//				        				event.setCancelled(true);
//				        				shooter.sendMessage(ChatColor.RED+"El PVP en la arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no esta Habilitado");
//				        			}
//				        			
//							}
//	        		}
//					
//				}
//	            
//	 
//	           
//	            
//	           
//	      }
//		  }catch(ClassCastException e) {
//			  return;
//		  }     
	      
	    }
	 
	 
	 //TODO HEADSHOOT revisar
	 
	 public boolean isaHeadShot(Player player, Entity damage, Projectile projecil) {
		 
		 
         double projectileY = projecil.getLocation().getY();
         double damagedY = damage.getLocation().getY();
         
         
         if ((projectileY - damagedY >= 1.60D) ) {// originalmente estaba en 1.35D
		 
		 
         }
		 
		 return false;
	 }
	 
	 //TODO CHAT
	 @EventHandler(priority = EventPriority.LOWEST)
	 public void chatplayer(AsyncPlayerChatEvent e) {
		 
		 Player player = e.getPlayer();
		 String message = e.getMessage();
		 RankPlayer ra = new RankPlayer(plugin);
		 
		 GameConditions gc = new GameConditions(plugin);
		 Set<Player> c1 = e.getRecipients();
		 c1.removeIf(t -> plugin.getPlayerInfoPoo().containsKey(t));
		 if(gc.isPlayerinGame(player)) {
			 
			 GameConditions cm = new GameConditions(plugin);
			 PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			 String mapa = pl.getMapName();
			 GameInfo gi = plugin.getGameInfoPoo().get(mapa);
				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					
					List<String> alive1 = ga.getVivo();
					List<String> deads1 = ga.getMuerto();
					List<String> spec = ga.getSpectator();
				 
					
					if(cm.isPlayerinGame(player)) {
						
						if(alive1.contains(player.getName())) {
		
							cm.SendMessageToAllUsersOfSameMap(player, ""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.GREEN+ChatColor.BOLD+"VIVO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ra.getRank(player)+ChatColor.WHITE+player.getName()+": "+ChatColor.GREEN+message);
						}else if(deads1.contains(player.getName())) {
		
							cm.SendMessageToAllUsersOfSameMap(player, ""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ra.getRank(player)+ChatColor.WHITE+player.getName()+": "+ChatColor.YELLOW+message);
		
						}else if(spec.contains(player.getName())) {
		
							cm.SendMessageToAllUsersOfSameMap(player, ""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.WHITE+ChatColor.BOLD+"ESPECTADOR"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ra.getRank(player)+ChatColor.WHITE+player.getName()+": "+ChatColor.GRAY+message);
						}

					 e.setCancelled(true);
					}
				}
		
		 }
		 
		


	
			// e.setFormat(ChatColor.translateAlternateColorCodes('&',"&2&lADMIN TESTER "+"&a"+player.getName()+"&8|| "+"&6"+message));
	
			// e.setFormat(ChatColor.translateAlternateColorCodes('&',"&7&lTESTER "+"&a"+player.getName()+"&8|| "+"&b"+message));
		 
		 
	 }
	 
	 @EventHandler
	 public void lifeplayer(EntityRegainHealthEvent e) {
		 
		 		
		 		Entity enti = e.getEntity();
			
	
				if(enti.getType() == EntityType.PLAYER) {
					Player player = (Player) enti;
					
					GameConditions gc = new GameConditions(plugin);
					if(gc.isPlayerinGame(player)) {
						if(e.getRegainReason() == RegainReason.MAGIC_REGEN || e.getRegainReason() == RegainReason.MAGIC || e.getRegainReason() == RegainReason.CUSTOM ) {
							 
							e.setCancelled(false);
						 }
						else {
							e.setCancelled(true);
						 }
					}
			  }
			
	}
			
		
		
	 
	 
	 @EventHandler
	 public void eatplayer(PlayerItemConsumeEvent e) {
		 Player player = (Player) e.getPlayer();
		 ItemStack food = e.getItem();
		 
			GameConditions gc = new GameConditions(plugin);
			if(gc.isPlayerinGame(player)) {
				 if(food.isSimilar(new ItemStack(Material.APPLE))) {
					 player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
				 }
				 
				 if(food.isSimilar(new ItemStack(Material.GOLDEN_APPLE))) {
					 player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
				 }	
			}
		 
	
		 
	 }
	 
	// @EventHandler SIN UN USO EFICIENTE 
	 public void arrowplayer(PlayerPickupArrowEvent e) {
		//Player p = (Player) e.getPlayer();
		if(e.getArrow().getType() == EntityType.ARROW) {
			Arrow a = (Arrow) e.getArrow(); 
			 if(a.isOnGround()) {
				
					 Random r = new Random();
					 int n = r.nextInt(20);
					 if(n == 20) {
						// p.sendMessage(ChatColor.GREEN+" No olvides revisar cuantas flechas te quedan");
					 }
					 
					 if(n == 5) {
						// p.sendMessage(ChatColor.GREEN+" La vida no se regenera asi que ten cuidado");
					 }
				 
				 
				
			 }
		}
	
	 }
	 
	 
		@EventHandler
		public void Nofire(EntityCombustEvent e) {
			     
			   Entity entidad = e.getEntity();
			
			if(entidad.getType() == EntityType.ZOMBIE) {
				if(e instanceof EntityCombustByEntityEvent || e instanceof EntityCombustByBlockEvent ) {
					return;
				}else {
					e.setCancelled(true);
				}
			}
			if(entidad.getType() == EntityType.SKELETON) {
				if(e instanceof EntityCombustByEntityEvent || e instanceof EntityCombustByBlockEvent ) {
					return;
				}else {
					e.setCancelled(true);
				}
			}
			if(entidad.getType() == EntityType.DROWNED) {
				if(e instanceof EntityCombustByEntityEvent || e instanceof EntityCombustByBlockEvent ) {
					return;
				}else {
					e.setCancelled(true);
				}
			}
			if(entidad.getType() == EntityType.ZOMBIE_VILLAGER) {
				if(e instanceof EntityCombustByEntityEvent || e instanceof EntityCombustByBlockEvent ) {
					return;
				}else {
					e.setCancelled(true);
				}
			}
			if(entidad.getType() == EntityType.PHANTOM) {
				if(e instanceof EntityCombustByEntityEvent || e instanceof EntityCombustByBlockEvent ) {
					return;
				}else {
					e.setCancelled(true);
				}
			}
				
		}
		
		
		
		@EventHandler  //METODO
	    public void tab(TabCompleteEvent e){
			String tab = e.getBuffer();
			Player player = (Player) e.getSender();
		
			if(player.isOp()) {
				e.setCancelled(false);
			}else {
				if(tab.contains("wpp")) {
					e.setCancelled(true);
				}
			}
			
		}
		
		

	
	
		
		//TODO EXPLOSION
	
		@EventHandler
		public void EntityChangeBlockMg(EntityChangeBlockEvent e) {
			FileConfiguration config = plugin.getConfig();
			
			List<String> w = config.getStringList("FallingBlock-Remove-If-Touch-Ground.List");
			
			if(w.contains(e.getEntity().getWorld().getName())) {
				if(e.getEntity() instanceof FallingBlock) {
					FallingBlock f = (FallingBlock) e.getEntity();
					if(f.getCustomName() != null) {
						if(f.getCustomName().equals("Animation")) {
							f.remove();
						}
					}
				}
			}
			
			
		}
		
		@EventHandler
		public void onExplodeEntity(EntityExplodeEvent e) {
				FileConfiguration config = plugin.getConfig();
			
				List<String> mundos = config.getStringList("Regen-Explosion.List");

				String mundo = e.getEntity().getWorld().getName();

				if (mundos.contains(mundo)) {
					List<Block> blocks = e.blockList();
					new RegenRun(blocks).runTaskTimer(plugin, 1, 1);
					e.setYield(0);
				}

				List<String> w = config.getStringList("Worlds-Animation.List");
				String world1 = e.getLocation().getWorld().getName();
				 if(w.contains(world1))
				 
				 for(Block b : e.blockList()) {
			            int max = config.getInt("Max-Animation");
			            int min = config.getInt("Min-Animation");
			            double x = Math.random() * (max - min) + min;
			            double y = Math.random() * (max - min) + min;
			            double z = Math.random() * (max - min) + min;
			          //  FallingBlock block = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
			            FallingBlock block = b.getWorld().spawnFallingBlock(b.getLocation(),b.getBlockData());
			            block.setInvulnerable(false);
			            block.setCustomName("Animation");
			            block.setVelocity(new Vector(x,y,z));
			            b.setType(Material.AIR);
			        }
			 
				
			

		}

		@EventHandler
		public void onExplodeBlock(BlockExplodeEvent e) {
			FileConfiguration config = plugin.getConfig();
			
				List<String> mundos = config.getStringList("Regen-Explosion.List");

				String mundo = e.getBlock().getWorld().getName();

				if (mundos.contains(mundo)) {
					
					List<Block> blocks = e.blockList();
					new RegenRun(blocks).runTaskTimer(plugin, 1, 1);
					e.setYield(0);
					
				}

				
			

		}

		@EventHandler
		public void Grapping(PlayerFishEvent e) {
			Player player =  e.getPlayer();
			
			GameConditions gc = new GameConditions(plugin);
			if(gc.isPlayerinGame(player)) {
				ItemStack item = player.getInventory().getItemInMainHand();
				ItemMeta meta = item.getItemMeta();
				String name = ChatColor.stripColor(meta.getDisplayName());
				if(name.contains("GANCHO")) {
					if(e.getState() == PlayerFishEvent.State.IN_GROUND) {
						Location pl = player.getLocation();
						Location hookl = e.getHook().getLocation();
						Location ch = hookl.subtract(pl);
						
						player.setVelocity(ch.toVector().multiply(0.3).setY(1));
						
					}
					
				}
				
				if(name.contains("GANCHO DE MANIOBRAS")) {
					if(e.getState() == PlayerFishEvent.State.REEL_IN) {
					
						if(!player.getInventory().containsAtLeast(new ItemStack(Material.LAPIS_LAZULI),5)) {
							player.sendMessage(ChatColor.RED+"Necesitas tener 5 de LapisLazuli.");
							return;
						}
						
						player.getInventory().removeItem(new ItemStack(Material.LAPIS_LAZULI,5));
						Location pl = player.getLocation();
						Location hookl = e.getHook().getLocation();
						Location ch = hookl.subtract(pl);
						
						player.setVelocity(ch.toVector().multiply(0.3).setY(1));
						
					}
					
				}
			}
			
	
	
			 
			
		}
		
		public void spawnLootEnchantedTable(Player player,Location l) {
				FileConfiguration config = plugin.getConfig();
				Random ra = new Random();
				GameConditions gc = new GameConditions(plugin);
				PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
				String map = pl.getMapName();
				// hierro oro diamante esmeralda netherite creeper zombi
				int r = ra.nextInt(7);
				int low = 20;
				int hig = 30;
				int r2 = ra.nextInt(hig-low+1) + low;
				 
				List<Entity> list = getNearbyEntitesItems(l, 5);
				//System.out.println("Lista 2 "+list.size());
				if(list.size() >= config.getInt("Loot-Table-Limit")) {
					gc.SendMessageToAllPlayersInMap(map,ChatColor.GREEN+player.getName()+ChatColor.RED+" Tu ambicion sera tu perdicion.\nAnti-Looter-2 y Suicida Invocados");
					l.getWorld().spawnParticle(Particle.FLAME, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
					player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					LivingEntity entidad = (LivingEntity) l.getWorld().spawnEntity(l.add(0.5,1,0.5), EntityType.ZOMBIE);
					Zombie zombi = (Zombie) entidad;
					zombi.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					zombi.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(50);
					zombi.setCustomName(""+ChatColor.GOLD+ChatColor.BOLD+"Anti-Looter-2");
					zombi.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					zombi.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi.getEquipment().setItemInMainHand(Items.ZOMBIPALO.getValue());
					zombi.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
					
					
					PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 120 * 20,/*amplifier:*/2, true ,true,true );
					PotionEffect salto= new PotionEffect(PotionEffectType.JUMP,/*duration*/ 120 * 20,/*amplifier:*/5, true ,true,true );
	
					
				    zombi.addPotionEffect(rapido);
					zombi.addPotionEffect(salto);
					
					LivingEntity entidadc = (LivingEntity) l.getWorld().spawnEntity(l.add(0.5,1,0.5), EntityType.CREEPER);
					Creeper c = (Creeper) entidadc;
					c.setExplosionRadius(15);
					c.setMaxFuseTicks(1);
					c.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					c.setCustomName(""+ChatColor.AQUA+ChatColor.BOLD+"SUICIDA");
					zombi.addPassenger(c);
					
				}
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 100)) {
					
					gc.SendMessageToAllPlayersInMap(map,ChatColor.GREEN+player.getName()+ChatColor.RED+" recibio un Castigo por tener muchos Diamantes.\nAnti-Looter y Guardia del Loot Invocados");
					l.getWorld().spawnParticle(Particle.FLAME, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
					player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					LivingEntity entidad = (LivingEntity) l.getWorld().spawnEntity(l.add(0.5,1,0.5), EntityType.ZOMBIE);
					Zombie zombi = (Zombie) entidad;
					zombi.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					zombi.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(50);
					zombi.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Anti-Looter");
					zombi.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					zombi.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi.getEquipment().setItemInMainHand(Items.ZOMBIPALO.getValue());
					zombi.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
					
				
					PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 60 * 20,/*amplifier:*/2, true ,true,true );
					PotionEffect salto= new PotionEffect(PotionEffectType.JUMP,/*duration*/ 60 * 20,/*amplifier:*/5, true ,true,true );
	
					
				    zombi.addPotionEffect(rapido);
					zombi.addPotionEffect(salto);
					
					LivingEntity entidad1 = (LivingEntity) l.getWorld().spawnEntity(l.add(0.5,1,0.5), EntityType.WITCH);
					Witch c1 = (Witch) entidad1;
					c1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					c1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"GUARDIA DEL LOOT");	
				}
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 100)) {
					
					gc.SendMessageToAllPlayersInMap(map,ChatColor.GREEN+player.getName()+ChatColor.RED+" recibio un Castigo por tener mucho Oro.\nGuardias del Loot Invocados");

					l.getWorld().spawnParticle(Particle.FLAME, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */5, 0.5, 1, 0.5, /* velocidad */0, null, true);

					player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					LivingEntity entidad = (LivingEntity) l.getWorld().spawnEntity(l.add(0.5,1,0.5), EntityType.CREEPER);
					Creeper c = (Creeper) entidad;
					c.setExplosionRadius(5);
					c.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					c.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"GUARDIA DEL LOOT");
					
					//l.getWorld().spawnParticle(Particle.FLAME, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */5, 0.5, 1, 0.5, /* velocidad */0, null, true);

					//player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					LivingEntity entidad1 = (LivingEntity) l.getWorld().spawnEntity(l.add(0.5,1,0.5), EntityType.WITCH);
					Witch c1 = (Witch) entidad1;
					c1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					c1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"GUARDIA DEL LOOT");		
				
				}
				
				
				
				
				if(r == 0) {
					
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.LAPIS_LAZULI,r2));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
					
				}if(r == 1) {
				
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.EXPERIENCE_BOTTLE,r2));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
					
				}if(r == 2) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.DIAMOND,r2));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
				}if(r == 3) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.IRON_INGOT,r2));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
				}if(r == 4) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.EMERALD,r2));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
				}if(r == 5) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.GOLD_INGOT,r2));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
				}if(r == 6) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.NETHERITE_INGOT,1));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
				}if(r == 7) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					l.getWorld().dropItem(l.add(0.5,1,0.5),new ItemStack(Material.WOODEN_SWORD,1));
					l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
				}
			
			
		}
		
		
		//TODO PROYECTILE
		 @EventHandler
		 public void onProjectileEvent(ProjectileHitEvent e) {
			 
		
			  Projectile projectile = e.getEntity();
			  GameConditions gc = new GameConditions(plugin);
			 
			  if(projectile.getShooter() instanceof Player) {
				 
				  Player player = (Player)projectile.getShooter();
				  Entity entidadhit = e.getHitEntity();
				  
				
				 
				  if(entidadhit != null) {
					  if(!gc.isPlayerinGame(player)) {
						  return;
					  }
					  	PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
					  	if(entidadhit instanceof LivingEntity) {
							 LivingEntity mob = (LivingEntity) entidadhit;
							  	pl.getGamePoints().setDamage(pl.getGamePoints().getDamage()+ConvertDoubleToInt(mob.getHealth()-mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
						 }
					  	
					  	
						if(entidadhit instanceof Player || entidadhit instanceof Villager) {
								String arenaName = pl.getMapName();
				
					 			if(!gc.isPvPAllowed(arenaName)) {
					 				e.setCancelled(true);
					 				player.sendMessage(ChatColor.RED+"El PVP en el Mapa "+ChatColor.GOLD+arenaName+ChatColor.RED+" no esta Habilitado");
					 			}
					 			
					 		
								
							}
							return;
					 }
				  
				  
				  if(e.getHitBlock() != null) {
					  Block b = e.getHitBlock();
						 // Player player = (Player) projectile.getShooter();
						  if(b.getType() == Material.TARGET) {
							  DetectDispenser(b.getLocation());
							  DetectChestAndCommand(b.getLocation());
						  }
				  }
			
				  
				  //si un mob te dispara
			  }else if (projectile.getShooter() instanceof Monster) {
				  Entity damager =  (Entity) projectile.getShooter();
				  Entity entidadhit = e.getHitEntity();
				  
				  
				  if(entidadhit instanceof Player) {
					  Player player = (Player) entidadhit;
						if(gc.isPlayerinGame(player)) {
							 plugin.CreditKill().put(player, damager);
						}
					 
				  }
				 
			  }
			  
			  
		 }
		
		//TODO REDUCE
		public void removeItemstackCustom(Player player,ItemStack it) {
			Inventory inv = player.getInventory();
			int slot = inv.first(it);

			@SuppressWarnings("unused")
			boolean hasAmmo = false;
			for (slot = 0; slot < inv.getSize(); slot++) {

				ItemStack item = inv.getItem(slot);// 3
				if (item != null && item.isSimilar(it)) {
					hasAmmo = true;
					
					int amount = item.getAmount() - 1;
					if (amount <= 0) {
						item = new ItemStack(Material.AIR);
					} else {
						item.setAmount(amount);
					}
					inv.setItem(slot, item);
					break;
				}
			}
			
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
	
		public List<Entity> getNearbyEntitesItems(Location l , int size){
			
			List<Entity> entities = new ArrayList<Entity>();
			for(Entity e : l.getWorld().getEntities()) {
				
				if(l.distance(e.getLocation()) <= size) {
					//if(e.getType() == EntityType.ARMOR_STAND && e.getCustomName() != null && e.getCustomName().contains(ChatColor.stripColor("RECIPIENTE DE ALMAS"))) {
						if(e.getType() == EntityType.DROPPED_ITEM) {
							entities.add(e);
							//System.out.println("Lista de entidad "+entities.size());
						}
						
					//	break;
				//	}
					
				}
			}
			return entities;
			
			
		}
		
		
	public List<Entity> getNearbyEntitesPlayers(Location l , int size){
			
			List<Entity> entities = new ArrayList<Entity>();
			for(Entity e : l.getWorld().getEntities()) {
				if(l.distance(e.getLocation()) <= size) {
					//if(e.getType() == EntityType.ARMOR_STAND && e.getCustomName() != null && e.getCustomName().contains(ChatColor.stripColor("RECIPIENTE DE ALMAS"))) {
						entities.add(e);
					//	break;
				//	}
					
				}
			}
			return entities;
			
			
		}
		
		public void DetectDispenser(Location l) {
			FileConfiguration config = plugin.getConfig();
			Block block = l.getBlock();
			Block r = block.getRelative(0, 0, 0);
			int rango = config.getInt("Dispenser-Range") ;
			
		//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
				
			//	Powerable pw = (Powerable) r.getBlockData();
			//	if(pw.isPowered()) {

					for (int x = -rango; x < rango+1; x++) {
						for (int y = -rango; y < rango+1; y++) {
							for (int z = -rango; z < rango+1; z++) {
		
								Block a = r.getRelative(x, y, z);
		
								// setea bloques en esos puntos
								
		
									if(a.getType() == Material.DISPENSER) {
										
										Dispenser d = (Dispenser) a.getBlockData();
										Location loc = a.getLocation();
										Inventory i = (((InventoryHolder)a.getState()).getInventory());
										//FLECHAS
										
										if(i.containsAtLeast(new ItemStack(Material.ARROW), 1 )) {
											ProyectileShootType(d, EntityType.ARROW, 1, loc);
										}
										if(i.containsAtLeast(new ItemStack(Material.SPECTRAL_ARROW), 1)) {
											ProyectileShootType(d, EntityType.ARROW, 2, loc);									
										}
									
										//TODO FIRE
										if(i.containsAtLeast(new ItemStack(Material.FIRE_CHARGE), 1)) {
											ProyectileShootType(d, EntityType.SMALL_FIREBALL, 1, loc);
										}
									
									
										//SMALL FIREBALLS
										//d.getFacing().getDirection();
									
										
										//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
									}
		
			
							};
						};
					};
			//	}	
				//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

			//}
		}
		
		
		public void DetectChestAndJump(Player player) {
			
			Block block = player.getLocation().getBlock();
			Block r = block.getRelative(0, 0, 0);
			int rango = 5 ;
			
		//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
				
			//	Powerable pw = (Powerable) r.getBlockData();
			//	if(pw.isPowered()) {

					for (int x = -rango; x < rango+1; x++) {
						for (int y = -rango; y < rango+1; y++) {
							for (int z = -rango; z < rango+1; z++) {
		
								Block a = r.getRelative(x, y, z);
		
								// setea bloques en esos puntos
								
		
									if(a.getType() == Material.CHEST) {
										
										  Chest chest = (Chest) a.getState();
										   if(chest.getCustomName()!= null && chest.getCustomName().equals("JUMP")) {
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
																		    //5 1
																		
																			  int power = Integer.valueOf(nn[0]);
																			  int y1 = Integer.valueOf(nn[1]);
																			player.setVelocity(player.getLocation().getDirection().multiply(power).setY(y1));
															
											              }
											          }
									      }  
									
										//SMALL FIREBALLS
										//d.getFacing().getDirection();
									
										
										//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
									}
		
			
							};
						};
					};
			//	}	
				//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

			//}
		}
		
		public void LaunchRandomArrow(Player player , float addYaw, float addPitch) {
			
			final Location loc = player.getLocation();
			loc.setYaw(loc.getYaw()+addYaw);
			loc.setPitch(loc.getPitch()+addPitch);
			final Vector mot = loc.getDirection();
			
			    Entity h1 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
	    		Entity h2 = loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
	    		
	    		h1.setVelocity(mot);
	    		h2.setVelocity(mot);
	    		
	    		Arrow aw = (Arrow) h1;
	    		Arrow aw2 = (Arrow) h2;
	    		aw.setCritical(true);
	    		aw.setKnockbackStrength(2);
	    		aw.setFireTicks(1200);
	    		aw2.setCritical(true);
	    		aw2.setKnockbackStrength(2);
	    		aw2.setFireTicks(1200);

	    		((Arrow) h1).setShooter(player);
	    		((Arrow) h2).setShooter(player);
			
		}
		
		public void ProyectileShootType(Dispenser d, EntityType type , int option ,Location loc ) {
					Location loc2 = loc;
			
					if(d.getFacing() == BlockFace.NORTH) {
						
						if(type == EntityType.ARROW) {
							
								if(option == 1) {
									Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,-1), EntityType.ARROW);
									h1.setVelocity(d.getFacing().getDirection().multiply(6));
									Arrow aw = (Arrow) h1;
									aw.setCritical(true);
									aw.setKnockbackStrength(10);
									aw.setTicksLived(20);
									aw.setCustomName("Flecha Trampa");
									aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
								}
								if(option == 2) {
									Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,-1), EntityType.ARROW);
									h1.setVelocity(d.getFacing().getDirection().multiply(6));
									Arrow aw = (Arrow) h1;
									aw.setCritical(true);
									aw.setKnockbackStrength(10);
									aw.setTicksLived(20);
									aw.setFireTicks(1200);
									aw.setCustomName("Flecha Trampa");
									aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
								}
								// dos flechas
						
						}
						if(type == EntityType.SMALL_FIREBALL) {
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,-1), EntityType.SMALL_FIREBALL);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								SmallFireball sf = (SmallFireball) h1;
								sf.setCustomName("Lanzallama Trampa");
								sf.setTicksLived(1200);
							}
						
							if(option == 2) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,-1), EntityType.SMALL_FIREBALL);
								h1.setVelocity(d.getFacing().getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
								SmallFireball sf = (SmallFireball) h1;
								sf.setCustomName("Lanzallama Trampa");
								sf.setTicksLived(1200);
								
								Entity h2 = loc2.getWorld().spawnEntity(loc2.add(0.5,0.5,-1), EntityType.SMALL_FIREBALL);
								h2.setVelocity(d.getFacing().getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
								SmallFireball sf2 = (SmallFireball) h2;
								sf2.setCustomName("Lanzallama Trampa");
								sf2.setTicksLived(1200);
								
							}
						
						}
					}
					if(d.getFacing() == BlockFace.SOUTH) {
								if(type == EntityType.ARROW) {
									
									if(option == 1) {
										Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,1), EntityType.ARROW);
										h1.setVelocity(d.getFacing().getDirection().multiply(6));
										Arrow aw = (Arrow) h1;
										aw.setCritical(true);
										aw.setKnockbackStrength(10);
										aw.setTicksLived(20);
										aw.setCustomName("Flecha Trampa");
										aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
									}
									if(option == 2) {
										Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,1), EntityType.ARROW);
										h1.setVelocity(d.getFacing().getDirection().multiply(6));
										Arrow aw = (Arrow) h1;
										aw.setCritical(true);
										aw.setKnockbackStrength(10);
										aw.setTicksLived(20);
										aw.setFireTicks(1200);
										aw.setCustomName("Flecha Trampa");
										aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
									}
									// dos flechas
							
							}
							if(type == EntityType.SMALL_FIREBALL) {
									if(option == 1) {
										Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,1), EntityType.SMALL_FIREBALL);
										h1.setVelocity(d.getFacing().getDirection().multiply(6));
										SmallFireball sf = (SmallFireball) h1;
										sf.setCustomName("Lanzallama Trampa");
										sf.setTicksLived(1200);
									}
								
									if(option == 2) {
										Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,1), EntityType.SMALL_FIREBALL);
										h1.setVelocity(d.getFacing().getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
										SmallFireball sf = (SmallFireball) h1;
										sf.setCustomName("Lanzallama Trampa");
										sf.setTicksLived(1200);
										
										Entity h2 = loc2.getWorld().spawnEntity(loc2.add(0.5,0.5,1), EntityType.SMALL_FIREBALL);
										h2.setVelocity(d.getFacing().getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
										SmallFireball sf2 = (SmallFireball) h2;
										sf2.setCustomName("Lanzallama Trampa");
										sf2.setTicksLived(1200);
										
									}
								
							}
					}
					if(d.getFacing() == BlockFace.EAST) {
						if(type == EntityType.ARROW) {
							
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(1,0.5,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(1,0.5,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							// dos flechas
		
					    }
							if(type == EntityType.SMALL_FIREBALL) {
								if(option == 1) {
									Entity h1 = loc.getWorld().spawnEntity(loc.add(1,0.5,0.5), EntityType.SMALL_FIREBALL);
									h1.setVelocity(d.getFacing().getDirection().multiply(6));
									SmallFireball sf = (SmallFireball) h1;
									sf.setCustomName("Lanzallama Trampa");
									sf.setTicksLived(1200);
								}
							
						}
					}
					if(d.getFacing() == BlockFace.WEST) {
						if(type == EntityType.ARROW) {
							
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(-1,0.5,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(-1,0.5,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							// dos flechas

					    }
						if(type == EntityType.SMALL_FIREBALL) {
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(-1,0.5,0.5), EntityType.SMALL_FIREBALL);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								SmallFireball sf = (SmallFireball) h1;
								sf.setCustomName("Lanzallama Trampa");
								sf.setTicksLived(1200);
							}
						
					    }
					}
					if(d.getFacing() == BlockFace.UP) {
						if(type == EntityType.ARROW) {
							
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,1,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,1,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							// dos flechas

					    }
						
						if(type == EntityType.SMALL_FIREBALL) {
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,1,0.5), EntityType.SMALL_FIREBALL);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								SmallFireball sf = (SmallFireball) h1;
								sf.setCustomName("Lanzallama Trampa");
								sf.setTicksLived(1200);
							}
						
					    }
					}
					if(d.getFacing() == BlockFace.DOWN) {
						if(type == EntityType.ARROW) {
							
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,-1,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,-1,0.5), EntityType.ARROW);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								Arrow aw = (Arrow) h1;
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							// dos flechas

					    }
						
						if(type == EntityType.SMALL_FIREBALL) {
							if(option == 1) {
								Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,-1,0.5), EntityType.SMALL_FIREBALL);
								h1.setVelocity(d.getFacing().getDirection().multiply(6));
								SmallFireball sf = (SmallFireball) h1;
								sf.setCustomName("Lanzallama Trampa");
								sf.setTicksLived(1200);
							}
						
					    }
					}
			
				
			
		}
		
		public void DetectChestAndDrop(Player player,Block b) {
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
											  if(chest.getCustomName().contains("DROP")) {
												  if(!chest.getInventory().isEmpty()) {
														for (ItemStack itemStack : chest.getInventory().getContents()) {
															if(itemStack == null) continue;
																b.getWorld().dropItem(b.getLocation().add(0.5,1,0.5), itemStack);
																player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 2F);
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
		
		
		public void DetectChestAndPotion(Player player,Block b) {
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
											  if(chest.getCustomName().contains("POSION")) {
												  if(!chest.getInventory().isEmpty()) {
														for (ItemStack itemStack : chest.getInventory().getContents()) {
															if(itemStack == null) continue;
															
															if(!itemStack.hasItemMeta()) continue;
															
															ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
															
															if(!bm.hasDisplayName()) continue;
															
															
															//EFFECT DURA POTENCIA
															//
															   String n = bm.getDisplayName();
															   String[] nn = n.split(" ");
															    //5 1
															
															   	 String type = nn[0];
																  int dura = Integer.valueOf(nn[1]);
																  int power = Integer.valueOf(nn[2]);
															
															@SuppressWarnings("deprecation")
															PotionEffect posion = new PotionEffect(PotionEffectType.getByName(type),/*duration*/ dura * 20,/*amplifier:*/power, true ,true,true );
															player.addPotionEffect(posion);
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
		
		public void DetectChestAndCommand(Location l ) {
			//FileConfiguration config = plugin.getConfig();
			
			
			
			
			Block block = l.getBlock();
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
											  if(chest.getCustomName().contains("COMMANDS")) {
												  if(!chest.getInventory().isEmpty()) {
														for (ItemStack itemStack : chest.getInventory().getContents()) {
																if(itemStack == null) continue;
																if(!itemStack.hasItemMeta()) continue;
																	
																		
																		
																		ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
																		
																		
																	if(!bm.hasDisplayName()) continue;
																		//EFFECT DURA POTENCIA
																		//
																	  String n = bm.getDisplayName().replace("%exe1%", "execute at @e[type=villager,name=CB] run ");
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
		
		public void DetectChestAndReadBook(Player player,Block b) {
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
											  if(chest.getCustomName().contains("MESSAGE")) {
												  if(!chest.getInventory().isEmpty()) {
														for (ItemStack itemStack : chest.getInventory().getContents()) {
																if(itemStack == null) continue;
															
																	
																	if(itemStack.getType() == Material.WRITTEN_BOOK || itemStack.getType() == Material.WRITABLE_BOOK ) {
																	
																		
																		if(!itemStack.hasItemMeta()) continue;
																		
																		BookMeta bm = (BookMeta) itemStack.getItemMeta();
																		
																		/*
																		if(bm.hasTitle()) {
																			String t = bm.getTitle();
																			player.sendMessage("TITULO: "+t);
																			
																		}else {
																			player.sendMessage("NO HAY TITULO");
																		}
																		
																		if(bm.hasAuthor()) {
																			player.sendMessage("HAY AUTOR ES "+bm.getAuthor());
																		}else {
																			player.sendMessage("NO HAY AUTOR");
																		}
																		*/
																		
																		if(bm.hasPages()) {
																			
																			//player.sendMessage("PAGINAS INFO");
																			List<String> lp = bm.getPages();
																			StringBuilder sb = new StringBuilder();
																			for(int i = 0;i< lp.size();i++) {
																				sb.append( lp.get(i).replace("%player%", player.getName()).replace("%jl%","\n"));
									
																			}
																			String sbb = sb.toString();
																			player.sendMessage(ChatColor.translateAlternateColorCodes('&',sbb));
																			
																		//	player.sendMessage("PAGINAS EN TOTAL "+bm.getPageCount());
																		//	player.sendMessage("PAGINA");
																			//player.sendMessage(bm.getPage(1));
																		}
																	
																 }
																
															
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
		
		
		
		
		
		
		//==============0
		public void DetectChestAndArmorSoulStand(Player player) {
			//FileConfiguration config = plugin.getConfig();
//			
//			List<Entity> e = getNearbyEntites(player.getLocation(), 30);
//			
//			for(Entity e1 : e) {
//				if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
//					// VIDA  10
//					//ALMAS 10 / 10
//					String name = ChatColor.stripColor(e1.getCustomName());
//					if(name.equals("RECIPIENTE DE ALMAS")) {
//						player.sendMessage(ChatColor.RED+"Ya hay recipiente de almas que debes llenar.");
//						return;
//					}
//					
//				}
//			}
//			
//			
//			
//				// 
//					//if(e.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null && e1.getCustomName().contains(ChatColor.stripColor("RECIPIENTE DE ALMAS"))) {
//			
//			Block block = player.getLocation().getBlock();
//			Block r = block.getRelative(0, 0, 0);
//			
//		//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
//				int rango = 10;
//			//	Powerable pw = (Powerable) r.getBlockData();
//			//	if(pw.isPowered()) {
//
//					for (int x = -rango; x < rango+1; x++) {
//						for (int y = -rango; y < rango+1; y++) {
//							for (int z = -rango; z < rango+1; z++) {
//		
//								Block a = r.getRelative(x, y, z);
//		
//								// setea bloques en esos puntos
//								
//		
//									if(a.getType() == Material.CHEST) {
//										
//										  Chest chest = (Chest) a.getState();
//										  if(chest.getCustomName() != null) {
//											  if(chest.getCustomName().contains("ALMAS")) {
//												  
//												  player.sendMessage(ChatColor.RED+"Debes llenar el recipiente de almas matando mobs cerca.");
//												  
//												  String arena = plugin.getArenaPlayerInfo().get(player);
//													 for(Player players : Bukkit.getOnlinePlayers()) {
//														 if(players.getName().equals(player.getName())) continue;
//														 String arena2 = plugin.getArenaPlayerInfo().get(players);
//														 if(arena2 != null && arena2.equals(arena)) {
//															
//															 players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.RED+" se encontro con un recipiente de almas llenalo matando mobs cerca.");						 
//															 
//														 }
//														 
//													 }
//												  
//												  if(!chest.getInventory().isEmpty()) {
//														for (ItemStack itemStack : chest.getInventory().getContents()) {
//																if(itemStack == null) continue;
//																if(!itemStack.hasItemMeta()) {
//																	return;
//																}
//																ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
//																
//																if(!bm.hasDisplayName()) {
//																	return;
//																}
//																   String n = bm.getDisplayName();
//																   
//																	LivingEntity entidad1 = (LivingEntity) chest.getWorld().spawnEntity(chest.getLocation().add(0.5,4,0.5), EntityType.ARMOR_STAND);
//																	LivingEntity entidad2 = (LivingEntity) chest.getWorld().spawnEntity(chest.getLocation().add(0.5,4.4,0.5), EntityType.ARMOR_STAND);
//																	ArmorStand ar = (ArmorStand) entidad1;
//																	ArmorStand ar2 = (ArmorStand) entidad2;
//																	
//																	//ar.setTicksLived(2000);
//																	
//																	ar2.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"RECIPIENTE DE ALMAS");
//																	ar2.setCustomNameVisible(true);
//																	ar2.setCollidable(false);
//																	ar2.setInvisible(true);
//																	ar2.setInvulnerable(true);
//																	ar2.setGravity(false);
//																	
//																	ar.setCustomName(""+ChatColor.AQUA+ChatColor.BOLD+"FALTAN "+ChatColor.GREEN+0+ChatColor.RED+" / "+ChatColor.GREEN+n);
//																	ar.setCustomNameVisible(true);
//																	ar.setCollidable(false);
//																	ar.setInvisible(true);
//																	ar.setInvulnerable(true);
//																	ar.setGravity(false);
//																	//ar.addPassenger(ar2);
//																	
//																	return;
//																
//																
//														}
//													}
//												 
//												
//												  
//												  
//											  }
//								          }
//										  
//										 
//									
//										//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
//									}
//		
//			
//							};
//						};
//					};
//			//	}	
//				//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");
//
//			//}
		}
		
		public void DetectChestAndArmorNexoStand(Player player) {
			//FileConfiguration config = plugin.getConfig();
//			
//			List<Entity> e = getNearbyEntites(player.getLocation(), 30);
//			
//			
//			for(Entity e1 : e) {
//				if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
//					// VIDA  10
//					//ALMAS 10 / 10
//					String name = ChatColor.stripColor(e1.getCustomName());
//					if(name.equals("NEXO")) {
//						player.sendMessage(ChatColor.RED+"Ya hay un Nexo que hay que Destruir.");
//						return;
//					}
//					
//				}
//			}
//				// 
//					//if(e.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null && e1.getCustomName().contains(ChatColor.stripColor("RECIPIENTE DE ALMAS"))) {
//			
//			Block block = player.getLocation().getBlock();
//			Block r = block.getRelative(0, 0, 0);
//			
//		//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
//				int rango = 10;
//			//	Powerable pw = (Powerable) r.getBlockData();
//			//	if(pw.isPowered()) {
//
//					for (int x = -rango; x < rango+1; x++) {
//						for (int y = -rango; y < rango+1; y++) {
//							for (int z = -rango; z < rango+1; z++) {
//		
//								Block a = r.getRelative(x, y, z);
//		
//								// setea bloques en esos puntos
//								
//		
//									if(a.getType() == Material.CHEST) {
//										
//										  Chest chest = (Chest) a.getState();
//										  if(chest.getCustomName() != null) {
//											  if(chest.getCustomName().contains("NEXO")) {
//												  
//												  player.sendMessage(ChatColor.RED+"Debes Destruir el Nexo");
//												  
//												  String arena = plugin.getArenaPlayerInfo().get(player);
//													 for(Player players : Bukkit.getOnlinePlayers()) {
//														 if(players.getName().equals(player.getName())) continue;
//														 String arena2 = plugin.getArenaPlayerInfo().get(players);
//														 if(arena2 != null && arena2.equals(arena)) {
//															
//															 players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.RED+" se encontro con un Nexo ayudalo a Destruirlo.");						 
//															 
//														 }
//														 
//													 }
//												  
//												  if(!chest.getInventory().isEmpty()) {
//														for (ItemStack itemStack : chest.getInventory().getContents()) {
//																if(itemStack == null) continue;
//																if(!itemStack.hasItemMeta()) {
//																	return;
//																}
//																ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
//																
//																if(!bm.hasDisplayName()) {
//																	return;
//																}
//																   String n = bm.getDisplayName();
//																   
//																	LivingEntity entidad1 = (LivingEntity) chest.getWorld().spawnEntity(chest.getLocation().add(0.5,4,0.5), EntityType.ARMOR_STAND);
//																	LivingEntity entidad2 = (LivingEntity) chest.getWorld().spawnEntity(chest.getLocation().add(0.5,4.4,0.5), EntityType.ARMOR_STAND);
//																	ArmorStand ar = (ArmorStand) entidad1;
//																	ArmorStand ar2 = (ArmorStand) entidad2;
//																	
//																	//ar.setTicksLived(2000);
//																	
//																	ar2.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"NEXO");
//																	ar2.setCustomNameVisible(true);
//																	ar2.setCollidable(false);
//																	ar2.setInvisible(true);
//																	ar2.setInvulnerable(true);
//																	ar2.setGravity(false);
//																	
//																	ar.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"VIDA "+ChatColor.GREEN+n);
//																	ar.setCustomNameVisible(true);
//																	ar.setCollidable(false);
//																	ar.setInvisible(true);
//																	ar.setInvulnerable(true);
//																	ar.setGravity(false);
//																	//ar.addPassenger(ar2);
//																	
//																	return;
//																
//																
//														}
//													}
//												 
//												
//												  
//												  
//											  }
//								          }
//										  
//										 
//									
//										//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
//									}
//		
//			
//							};
//						};
//					};
//			//	}	
//				//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");
//
//			//}
		}
		
		
		public void YouNeedYourFriends(Player player,Block b) {
			PlayerInfo name = plugin.getPlayerInfoPoo().get(player);
			GameAdventure ga = (GameAdventure) plugin.getGameInfoPoo().get(name.getMapName());
			GameConditions gc = new GameConditions(plugin);
			
			List<Entity> e = getNearbyEntitesPlayers(b.getLocation(), 10);
			List<Entity> e3 = new ArrayList<Entity>();
			
			
		
			
			
			List<Player> alive1 = gc.ConvertStringToPlayer(ga.getVivo());
			
			for(Entity e2 : e) {
				if(e2.getType() == EntityType.PLAYER) {
					Player player1 = (Player) e2;
					if(gc.isPlayerinGame(player1) && player1.getGameMode() == GameMode.ADVENTURE) {
						e3.add(e2);
					}
					
				}
			}
			
			
			
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
											  if(chest.getCustomName().contains("FRIENDS")) {
												  
												  if(e3.size() != alive1.size()) {
														player.sendMessage(ChatColor.YELLOW+"Necesitas que todos los jugadores vivos esten cerca para avanzar.");
														
														
													gc.SendMessageToUsersOfSameMap(player, ChatColor.GREEN+player.getName()+ChatColor.RED+" Solicita una reunion para poder activar un Evento.");
															
															 }
												  
												  
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
																		   Bukkit.dispatchCommand(console, n.replace("%player%", player.getName()));
											              }
												  }
												  
												  
												  
														return;
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
					
					
	//TODO DOUBLE TO INT
					
					public int ConvertDoubleToInt(double damage) {
						NumberFormat nf = NumberFormat.getInstance();
						nf.setMaximumFractionDigits(0);
						
						return Integer.parseInt(nf.format(damage));
					}
					
					
					
					
					
					
					
					
					
		}
		

		

