package me.nao.events.mg;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
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
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.entity.WaterMob;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import com.google.common.base.Strings;

import me.nao.cosmetics.mg.RankPlayer;
import me.nao.enums.mg.GameInteractions;
import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.Items;
import me.nao.enums.mg.PlayerGameStatus;
import me.nao.enums.mg.ReviveStatus;
import me.nao.flareactions.mg.Flare;
import me.nao.generalinfo.mg.CuboidZone;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.manager.mg.GameIntoMap;
import me.nao.mobs.mg.MobsActions;
import me.nao.revive.mg.RevivePlayer;
import me.nao.shop.mg.MinigameShop1;
import me.nao.utils.mg.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public class EventRandoms implements Listener{
	
	private Minegame plugin;
	
	public EventRandoms(Minegame plugin) {
		this.plugin = plugin;
	}
	
	

//		@EventHandler(priority = EventPriority.LOWEST)
//		public void mgMenu(InventoryCloseEvent e) {
//		   
//			Player player = (Player) e.getPlayer();
//			if(plugin.getPlayersLookingMgMenu().remove(player.getName()));
//	   }
//	

	
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
			Entity ent = (Entity) player.getPassengers().get(0);
			player.removePassenger(ent);
			if(ent.getType() == EntityType.TNT || ent.getType() == EntityType.FIREBALL) {
				Location loc = player.getLocation();
				ent.setVelocity(loc.getDirection().multiply(3).setY(1));
			}
			return;
		}
		
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void mgGameMode(PlayerGameModeChangeEvent e) {
		Player player = (Player) e.getPlayer();
		GameConditions gc = new GameConditions(plugin);
		
		if(gc.isPlayerinGame(player)) {
			if(e.getNewGameMode() == GameMode.SPECTATOR) {
				player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
				if(!player.getPassengers().isEmpty()) {
					Entity ent = (Entity) player.getPassengers().get(0);
					player.removePassenger(ent);
					
				}
			}
		}
	}
	
	
	
	
	//TODO INTERACT
	@EventHandler(priority = EventPriority.LOWEST)
	public void ClickEntity(PlayerInteractAtEntityEvent e) {
		Player player = (Player) e.getPlayer();
		GameConditions gc = new GameConditions(plugin);
		
		if(gc.isPlayerinGame(player)) {
			if(e.getHand() == EquipmentSlot.OFF_HAND)return;
			
			PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pi.getMapName());
			if(gi.getGameStatus() == GameStatus.JUGANDO || gi.getGameStatus() == GameStatus.PAUSE || gi.getGameStatus() == GameStatus.FREEZE) {

				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					if(ga.getDeadPlayers().contains(player.getName()) || ga.getSpectators().contains(player.getName())) {
						e.setCancelled(true);
						return;
					}
				}
				
				Entity ent = e.getRightClicked();
				
				if(ent instanceof ArmorStand) {
					
					ArmorStand as = (ArmorStand) ent;
					if(as.getCustomName() != null) {
						String name = ChatColor.stripColor(as.getCustomName());
						 
						if(name.startsWith("REVIVIR CON (SHIFT + CLICK DERECHO) A: ")){
							e.setCancelled(true);
							
							Player target = Bukkit.getPlayer(name.replace("REVIVIR CON (SHIFT + CLICK DERECHO) A: ","").replace(" ",""));
							if(gc.isPlayerKnocked(target)) {
								
								if(target.getName().equals(player.getName()) && player.getGameMode() == GameMode.SPECTATOR){
									
									if(player.getInventory().containsAtLeast(Items.REVIVEP.getValue(), 1)){
										
										RevivePlayer pr = plugin.getKnockedPlayer().get(player);
										int value = pr.getValue();
										if(value != 100) {
											pr.setValue(pr.getValue()+1);
											player.sendTitle(""+ChatColor.WHITE+ChatColor.BOLD+"REVIVIENDO"+ChatColor.GREEN+ChatColor.BOLD+" + ", ""+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(value,100, 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]", 0, 20, 0);
											pr.setReviveStatus(ReviveStatus.HEALING);
											pr.getArmorStand().getWorld().spawnParticle(Particle.HAPPY_VILLAGER, pr.getArmorStand().getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */5, 1, 1, 1, /* velocidad */0, null, true);

										}else {
											if(pr.getReviveStatus() == ReviveStatus.SELFREVIVED) return;
											pr.setReviveStatus(ReviveStatus.SELFREVIVED);
											player.sendTitle(""+ChatColor.GREEN+ChatColor.BOLD+"REVIVIDO" ,""+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(100,100, 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]", 20, 20, 20);
											player.sendMessage(ChatColor.GOLD+"Te has autorevivido ");
											gc.sendMessageToUsersOfSameMapLessPlayer(player,""+ChatColor.GREEN+ChatColor.BOLD+player.getName()+" consiguio Autorevivirse.");
											//removeItemstackCustom(player,Items.REVIVEP.getValue());
										}
									}
									
									return;
								}else{
									if(player.isSneaking()) {
										RevivePlayer pr = plugin.getKnockedPlayer().get(target);
										int value = pr.getValue();
										if(value != 100) {
											pr.setValue(pr.getValue()+1);
											target.sendTitle(""+ChatColor.WHITE+ChatColor.BOLD+"REVIVIENDO"+ChatColor.GREEN+ChatColor.BOLD+" + ", ""+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(value,100, 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]", 0, 20, 0);
											player.sendTitle(""+ChatColor.WHITE+ChatColor.BOLD+"REVIVIENDO"+ChatColor.GREEN+ChatColor.BOLD+" + ", ""+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(value,100, 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]", 0, 20, 0);
											pr.setReviveStatus(ReviveStatus.HEALING);
											pr.getArmorStand().getWorld().spawnParticle(Particle.HAPPY_VILLAGER, pr.getArmorStand().getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */5, 1, 1, 1, /* velocidad */0, null, true);

										}else {
											if(pr.getReviveStatus() == ReviveStatus.REVIVED) return;
												pr.setReviveStatus(ReviveStatus.REVIVED);
												target.sendTitle(""+ChatColor.GREEN+ChatColor.BOLD+"REVIVIDO", ""+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(100,100, 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]", 20, 20, 20);
												player.sendTitle(""+ChatColor.GREEN+ChatColor.BOLD+"REVIVIDO", ""+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(100,100, 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]", 20, 20, 20);
											
												player.sendMessage(ChatColor.GOLD+"Ayudaste a levantar a "+ChatColor.GREEN+target.getName());
												target.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.GOLD+" te ayudo a levantarte.");
												gc.sendMessageToUsersOfSameMapLessTwoPlayers(player, target,""+ChatColor.GREEN+ChatColor.BOLD+"+ "+ChatColor.GOLD+player.getName()+ChatColor.AQUA+" ayudo a "+ChatColor.GREEN+target.getName()+ChatColor.AQUA+" a Levantarse.");
												PlayerInfo targetrevive = plugin.getPlayerInfoPoo().get(player);
												targetrevive.getGamePoints().setHelpRevive(targetrevive.getGamePoints().getHelpRevive()+1);
												
												PlayerInfo targetrevive2 = plugin.getPlayerInfoPoo().get(target);
												targetrevive2.getGamePoints().setRevive(targetrevive2.getGamePoints().getRevive()+1);
												
											
											
										}
										
										return;
									}else {
										if(player.getPassengers().isEmpty()) {
											player.addPassenger(as);
										}
										
										return;
									}
								}
							}
						}
					}
				}
				
				
				

				if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
					
					if(gi instanceof GameAdventure) {
						GameAdventure ga = (GameAdventure) gi;
						if(ga.getDeadPlayers().contains(player.getName()) || ga.getSpectators().contains(player.getName())) {
							e.setCancelled(true);
							return;
						}
					}
					
					
					if(!player.getPassengers().isEmpty()) {
						
							 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""+ChatColor.RED+ChatColor.BOLD+"YA TIENES ENCIMA A UNA ENTIDAD"));
						
						return;
					}else if(ent.getType() == EntityType.PLAYER) {
						Player target = (Player) ent;
						if(gc.isPlayerKnocked(player)) return;
						
						
						if(player.getGameMode() == GameMode.SPECTATOR) {
							e.setCancelled(true);
							
							  player.sendMessage(ChatColor.RED+"No puedes interactuar con Entidades siendo Espectador...");
							
							return;
						}
						
						if(target.getGameMode() == GameMode.SPECTATOR) {
							e.setCancelled(true);
							
								player.sendMessage(ChatColor.RED+"No puedes interactuar con Espectadores.");
							
							
							return;
						}
					
							if(player.isSneaking() && ent.getPassengers().isEmpty()) {
								target.addPassenger(player);
							}else {
								player.addPassenger(target);
							}
							
							
						
					}else if(ent.getType() == EntityType.CHEST_MINECART) {
						
						StorageMinecart mc = (StorageMinecart) ent;
						if(mc.getCustomName() != null && ChatColor.stripColor(mc.getCustomName()).equals("PAQUETE DE AYUDA")) {
							return;
						}else {
							player.addPassenger(ent);	
						}
						
					}else {
						if(player.getGameMode() == GameMode.SPECTATOR) {
							e.setCancelled(true);
							
								player.sendMessage(ChatColor.RED+"No puedes interactuar con Entidades siendo Espectador.");

							
							return;
						}else if(player.isSneaking() && ent.getPassengers().isEmpty()) {
							ent.addPassenger(player);
						}else {
							 player.addPassenger(ent); 
						}
						
					}
				}
				
				return;
			}
		}
		
	} 
	 
	 
	//TODO TROW ENTITY
	@EventHandler(priority = EventPriority.LOWEST)
	public void Playerheld(PlayerItemHeldEvent e) {
		Player player = (Player) e.getPlayer();
		
		GameConditions gc = new GameConditions(plugin);
		if(gc.isPlayerinGame(player)) {
			
			if(e.getNewSlot() >= 0 || e.getNewSlot() <= 8) {
				if(!player.getPassengers().isEmpty() && player.isSneaking()) {
					Entity ent2 = (Entity) player.getPassengers().get(0);
					player.removePassenger(ent2);
					Location loc = player.getLocation();
					
					if(ent2 instanceof Player) {
						Player target = (Player) ent2;
						target.setVelocity(loc.getDirection().multiply(3).setY(1));
						System.out.println("THROW PLAYER");
					}else if(ent2 instanceof Mob) {
						ent2.setVelocity(loc.getDirection().multiply(3).setY(1));
						
					} 
					
				
					return;
				}
			}
			
		}
	}
	
	//@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerSwapItem(PlayerSwapHandItemsEvent e) {
		Player player = (Player) e.getPlayer();
		
		GameConditions gc = new GameConditions(plugin);
		if(gc.isPlayerinGame(player)) {
			if(!player.getPassengers().isEmpty() && player.isSneaking()) {
				Entity ent2 = (Entity) player.getPassengers().get(0);
				player.removePassenger(ent2);
				Location loc = player.getLocation();
				ent2.setVelocity(loc.getDirection().multiply(3).setY(1));
			
				return;
			}
		}
	}
	
	//TODO INTERACCION

	@SuppressWarnings("removal")
	@EventHandler(priority = EventPriority.LOWEST)
	public void alinteractuar(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		
		if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().isSimilar(Items.BENGALAROJA.getValue()) || player.getInventory().getItemInMainHand().isSimilar(Items.BENGALAVERDE.getValue())) {
			new Flare(player, player.getInventory().getItemInMainHand(),player.getEyeLocation(),plugin);
			return;
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (e.getItem() != null) {
				if(e.getItem().isSimilar(Items.SPAWNZOMBI.getValue())) {
					MobsActions ma = new MobsActions(plugin);
					ma.spawnManualZombi(player.getLocation());
					return;
				}if(e.getItem().isSimilar(Items.SPAWNBABYZOMBI.getValue())) {
					MobsActions ma = new MobsActions(plugin);
					ma.spawnManualBabyZombi(player.getLocation());
					return;
				}if(e.getItem().isSimilar(Items.SPAWNELITEZOMBI.getValue())) {
					MobsActions ma = new MobsActions(plugin);
					ma.spawnEliteZombi(player.getLocation());
					return;
				}if(e.getItem().isSimilar(Items.SPAWNHORDEZOMBI.getValue())) {
					MobsActions ma = new MobsActions(plugin);
					ma.spawnManualZombi(player.getLocation());
					ma.spawnManualBabyZombi(player.getLocation());
					ma.spawnManualZombi(player.getLocation());
					return;
				}
			}
		}
		
		
		GameConditions gc = new GameConditions(plugin);
		if(gc.isPlayerinGame(player)) {
			 
				
				
				if(e.getAction() == Action.PHYSICAL) {
					if(e.getClickedBlock().getType() == Material.TRIPWIRE || e.getClickedBlock().getType() == Material.STONE_PRESSURE_PLATE) {
						
						detectDispenser(player,player.getLocation(),plugin.getGameInfoPoo().get(plugin.getPlayerInfoPoo().get(player).getMapName()));
						//Bukkit.broadcastMessage("1");
						
						
					}
					
					if(!gc.isPlayerKnocked(player)) {
						
						if(e.getClickedBlock().getType() == Material.OAK_PRESSURE_PLATE) {
							DetectChestAndJump(player);
						}
						 
						if(e.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE || e.getClickedBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
							landMine(player);
							
						}
						return;
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
						Block checkpoint = b3.getRelative(0, -2, 0);
					 if(b3.getType() == Material.STONE_BUTTON ) {
						 
						 	YouNeedYourFriends(player,b3);
							DetectChestAndDrop(player,b3);
							DetectChestAndReadBook(player, b3);
							DetectChestAndPotion(player, b3);
							DetectChestAndCommand(player.getLocation());
							//DetectChestAndArmorSoulStand(player);
							//DetectChestAndArmorNexoStand(player);
							
					 }
						
					 if(e.getHand() == EquipmentSlot.OFF_HAND)return;
					 if(b3.getType() == Material.LIME_BANNER && checkpoint.getType() == Material.STRUCTURE_BLOCK) {
						 PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
						 if(pl.getCheckPointMarker() != null) {
							 if(pl.getCheckPointMarker().equals(b3.getLocation())) {
								 player.sendMessage(ChatColor.RED+"La Ubicacion ya esta Guardada, encuentra otro sitio para Sobreescribir los Datos.");
							 }else {
								 player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, b3.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */25, 0.5, 1, 0.5, /* velocidad */0, null, true);
								 player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
								 player.sendMessage(ChatColor.GREEN+"La Ubicacion se Guardado. (Ubicacion Sobreescrita)");
								 pl.setCheckpointLocationMg(b3.getLocation());
							 }
						 }else {
							 player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, b3.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */25, 0.5, 1, 0.5, /* velocidad */0, null, true);

							 player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
							 player.sendMessage(ChatColor.GREEN+"La Ubicacion se Guardado.");
							 pl.setCheckpointLocationMg(b3.getLocation());
						 }
						 
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
												inv.StoreCreate(player);
												
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
								spawnLootEnchantedTable(player,t.getBlock().getLocation());
								
							}else {
								 e.setCancelled(true);
								player.sendMessage(ChatColor.RED+"No puedes interactuar con eso.");
							}
							
					  }
				 }
					  
			
						
					
				}
				
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
					if (e.getItem() != null) {
						
						
//						if(e.getItem().isSimilar(Items.TEST.getValue())) {
//							
//							FallingBlock f =  player.getWorld().spawnFallingBlock(player.getEyeLocation().add(0.5, 0, 0.5) ,Material.GREEN_STAINED_GLASS.createBlockData());
//							f.setGravity(true);
//							f.setInvulnerable(true);
//							f.setMaxDamage(20);
//							f.setDropItem(false);
//							f.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
//				       }
						
						if(!e.getItem().isSimilar(new ItemStack(Material.AIR))) {
							 gc.turret(player);
						}
					 
						if(e.getItem().isSimilar( Items.JEDIP.getValue())) {
							player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20.0F, 1F);
							for(Entity e1 : getNearbyEntities(player.getLocation(),20)) {
								if(!(e1.getType() == EntityType.PLAYER)) {
									e1.setVelocity(e1.getLocation().getDirection().multiply(-3).setY(1));
								}
							}
							
							removeItemstackCustom(player,e.getItem());
						}
						 
						if(e.getItem().isSimilar(Items.CHECKPOINTP.getValue())) {
							plugin.getCheckPoint().put(player, player.getLocation());
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
							player.sendMessage(ChatColor.GREEN+"CheckPoint Marcado.");
							removeItemstackCustom(player,e.getItem());
						}
						
						
						if(e.getItem().isSimilar(Items.DASHP.getValue())) {
							
							if(!player.hasCooldown(Items.DASHP.getValue())) {
								player.setCooldown(Items.DASHP.getValue(), 10 * 20);
								player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 20.0F, 1F);
								player.setVelocity(player.getLocation().getDirection().multiply(5).setY(1));
								
							}
							
							//player.launchProjectile(org.bukkit.entity.EnderPearl.class);
			               
						}
						
						
						if(e.getItem().isSimilar(Items.RAINARROWP.getValue())) {
							player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
							
							//player.launchProjectile(org.bukkit.entity.EnderPearl.class);
			               	for(int i = 0;i<20;i++) {
									SpawnArrows(player, RandomPosOrNeg(10),RandomPosOrNeg(5));
								}
			               	removeItemstackCustom(player,e.getItem());
						}
						 
				
						if(e.getItem().isSimilar(Items.STOREXPRESSP.getValue())) {
									MinigameShop1 inv = new MinigameShop1(plugin);
									inv.StoreCreate(player);
									removeItemstackCustom(player,e.getItem());
						}
						
					
						
						if (e.getItem().isSimilar(Items.OBJETIVOSP.getValue())) {
							MinigameShop1 ms = new MinigameShop1(plugin);
							ms.ShowObjetives(player);
						}

						
						if(e.getItem().isSimilar( Items.REFUERZOSP.getValue())) {
					
							Location loc = player.getLocation();
							IronGolem ih = (IronGolem) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.IRON_GOLEM);
							ih.setCustomName(ChatColor.AQUA+"GUARDIA DE: "+ChatColor.GOLD+player.getName());
							removeItemstackCustom(player,e.getItem());
						 }
					
						if(e.getItem().isSimilar(Items.REFUERZOS2P.getValue())) {
							Location loc = player.getLocation();
							ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
							SkullMeta meta = (SkullMeta) head.getItemMeta();
							meta.setOwningPlayer(player);
							head.setItemMeta(meta);

							
							for(int i = 0 ; i < 4;i++) {
								Snowman ih = (Snowman) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.SNOW_GOLEM);
								ih.getEquipment().setHelmet(head);
								ih.setCustomName(ChatColor.GREEN+"GUARDIA DE: "+ChatColor.GOLD+player.getName());
							}
							
						
							
							removeItemstackCustom(player,e.getItem());

						
						}
					if (e.getItem().isSimilar(Items.BAZUKAP.getValue())) {
						if(player.getInventory().containsAtLeast( Items.COHETEP.getValue(),1)) {
							Location loc = player.getLocation();
							player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 20.0F, 1F);
							Fireball f = (Fireball) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.FIREBALL);
							f.setCustomName(ChatColor.GOLD+"Cohete");
							f.setYield(10);
							f.setVelocity(loc.getDirection().multiply(3));
							f.setShooter(player);
							
							removeItemstackCustom(player, Items.COHETEP.getValue());
						}else {
							player.sendMessage(ChatColor.RED+"No tienes mas municion de Bazooka");

						}
					}
					if (e.getItem().isSimilar(Items.ARROWLP.getValue())) {
							if(player.getInventory().containsAtLeast( new ItemStack(Material.ARROW),2)) {
									Location loc = player.getLocation();
									Location loc2 = player.getLocation();
									player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
			
								
								
									Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
									Arrow aw2 = (Arrow) loc2.getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.ARROW);
									aw.setCritical(true);
									aw.setKnockbackStrength(1);
									aw.setFireTicks(1200);
									aw.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
									
									aw2.setCritical(true);
									aw2.setKnockbackStrength(1);
									aw2.setFireTicks(1200);
									aw2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
			
									aw.setShooter(player);
									aw2.setShooter(player);
									
									player.getInventory().removeItem(new ItemStack(Material.ARROW,2));
								}else {
									player.sendMessage(ChatColor.RED+"Necesitas tener 2 Flechas minimo para Disparar");
								}
					}	
					
						if(e.getItem().isSimilar(Items.BENGALAROJAP.getValue()) || e.getItem().isSimilar(Items.BENGALAVERDEP.getValue())) {
							new Flare(player, player.getInventory().getItemInMainHand(),player.getEyeLocation(),plugin);
							removeItemstackCustom(player,e.getItem());
						}
						
						if(e.getItem().isSimilar(Items.BENGALAMARCADORAP.getValue())) {
							new Flare(player, player.getInventory().getItemInMainHand(),player.getEyeLocation(),plugin);
							removeItemstackCustom(player,e.getItem());
						}
					
					
						
						if(e.getItem().isSimilar(Items.MEDICOP.getValue())) {
					
							Location loc = player.getLocation();
							Villager v = (Villager) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.VILLAGER);
							v.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"Medico "+ChatColor.RED+ChatColor.BOLD+"+");
							v.setProfession(Profession.NONE);
							v.setTicksLived(1200);
							removeItemstackCustom(player,e.getItem());
							
							gc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.AQUA+"El Jugador "+ChatColor.GREEN+player.getName()+ChatColor.AQUA+" a un Medico");
							
							
							
				        }if(e.getItem().isSimilar(Items.TNTP.getValue())) {
				        	TNTPrimed ptnt = (TNTPrimed) player.getWorld().spawnEntity(player.getLocation().add(0.5,1,0.5),EntityType.TNT);
							ptnt.setFuseTicks(30);
							ptnt.setVelocity(player.getLocation().getDirection().multiply(2.5));
							ptnt.setCustomName(ChatColor.DARK_PURPLE+"Super TNT");
							ptnt.setSource(player);
							ptnt.setYield(5);
							removeItemstackCustom(player,e.getItem());
						}
					
						
					}}
			
		}
		


		
	}
	
	@EventHandler
	public void dropItemMg(PlayerDropItemEvent e) {
		Player player = (Player) e.getPlayer();
		
		
		if(e.getItemDrop().getItemStack().isSimilar(Items.DASHP.getValue())) {
			if(player.isSneaking()) {
				
			}else {
				e.setCancelled(true);
				if(!player.hasCooldown(Items.DASHP.getValue())) {
					player.setCooldown(Items.DASHP.getValue(), 10 * 20);
					player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 20.0F, 1F);
					player.setVelocity(player.getLocation().getDirection().multiply(5).setY(1));
					
				}
			}
		}
		
				
				
				
				//player.launchProjectile(org.bukkit.entity.EnderPearl.class);
               
			
		
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
			gc.LeaveMapConexionIlegal(player);
			
			
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
	 		GameConditions gc = new GameConditions(plugin);
	 		if(!gc.isPlayerinGame(player)) return;
	 		
	 		if(player.getGameMode() == GameMode.ADVENTURE) {
	 			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
	 			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
	 			
	 			List<CuboidZone> zones = gi.getCuboidZones();
	 			System.out.println(pl.getMapName());
	 			if(zones.isEmpty())return;
	 			
	 			
	 			for(CuboidZone zone : zones) {
	 				if(zone.getGameInteractionType() == GameInteractions.CANMODIFY || zone.getGameInteractionType() == GameInteractions.CANBREAK) {
	 				
	 					if(zone.isinsideOfCuboidZone(e.getBlock().getLocation(), zone.getLocation1(), zone.getLocation2())) return;
		 				
	 				}
	 				
	 			}
	 				e.setCancelled(true);
					player.sendMessage(ChatColor.RED+"No puedes Romper Bloques en esa Zona.");
					return;
	 		}
	 		
	 		
	 		
//	 		Player player = e.getPlayer();
//	 		GameConditions gm = new GameConditions(plugin);
//	 		if(gm.isPlayerinGame(player)) {
//	 			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
//	 			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
//	 			if(gi instanceof GameNexo) {
//	 				GameNexo gn = (GameNexo) gi;
//	 				if(gn.getBlueTeamMg().contains(player.getName()) && gn.getRedNexoLocation() == e.getBlock().getLocation()){
//	 					List<Player> redt = gm.ConvertStringToPlayer(gn.getRedTeamMg());
//	 					if(gn.getRedLifeNexo() != 0) {
//	 						
//	 						
//	 						gn.SetRedLifeNexo(gn.getRedLifeNexo()-1);
//	 						for(Player rd : redt) {
//	 							rd.sendMessage(ChatColor.BLUE+player.getName()+" "+ChatColor.GOLD+" esta Atacando el Nexo: "+ChatColor.GREEN+gn.getRedLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
//	 						}
//	 						
//		 					e.setCancelled(true);
//	 					}else if(gn.getRedLifeNexo() == 0)  {
//	 						
//	 						for(Player rd : redt) {
//	 							rd.sendMessage(ChatColor.BLUE+player.getName()+" "+ChatColor.GOLD+" Destruyo el Nexo: "+ChatColor.GREEN+gn.getRedLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
//	 						}
//	 						
//	 						gn.setEstadopartida(EstadoPartida.TERMINANDO);
//	 						e.setCancelled(true);
//	 					}
//	 				
//	 				}else if(gn.getRedTeamMg().contains(player.getName()) && gn.getBlueNexoLocation() == e.getBlock().getLocation()){
//	 					List<Player> bluet = gm.ConvertStringToPlayer(gn.getBlueTeamMg());
//	 					if(gn.getBlueLifeNexo() != 0) {
//	 						
//
//	 						gn.SetBlueLifeNexo(gn.getBlueLifeNexo()-1);
//	 						
//	 						for(Player bl : bluet) {
//	 							bl.sendMessage(ChatColor.RED+player.getName()+" "+ChatColor.GOLD+" esta Atacando el Nexo: "+ChatColor.GREEN+gn.getBlueLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
//	 						}
//		 					e.setCancelled(true);
//	 					}else if(gn.getBlueLifeNexo() == 0){
//	 						
//	 						for(Player bl : bluet) {
//	 							bl.sendMessage(ChatColor.RED+player.getName()+" "+ChatColor.GOLD+" Destruyo el Nexo: "+ChatColor.GREEN+gn.getBlueLifeNexo()+ChatColor.GOLD+"/"+ChatColor.GREEN+"100");
//	 						}
//	 						
//	 						gn.setEstadopartida(EstadoPartida.TERMINANDO);
//	 						e.setCancelled(true);
//
//	 					}
//	 					
//	 				}
//	 			}
//	 			
//	 		}
	 		
	 		
		}
	 
	

	 @EventHandler(priority = EventPriority.LOWEST)
	 public void PlayerPlace(BlockPlaceEvent e) {
		 
			Player player = e.getPlayer();
	 		GameConditions gc = new GameConditions(plugin);
	 		if(!gc.isPlayerinGame(player)) return;
	 		
	 		if(player.getGameMode() == GameMode.ADVENTURE) {
	 			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
	 			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
	 			System.out.println(pl.getMapName());
	 			List<CuboidZone> zones = gi.getCuboidZones();
	 			
	 			if(zones.isEmpty()) return;
	 			
	 			for(CuboidZone zone : zones) {
	 				
	 				if(zone.getGameInteractionType() == GameInteractions.CANMODIFY || zone.getGameInteractionType() == GameInteractions.CANPLACE) {
	 					
	 					if(zone.isinsideOfCuboidZone(e.getBlockPlaced().getLocation(), zone.getLocation1(), zone.getLocation2())) return;
		 				
	 				}
	 				
	 			}
	 				e.setCancelled(true);
					player.sendMessage(ChatColor.RED+"No puedes Colocar Bloques en esa Zona.");
					return;
	 		}
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
//							 	e1.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"VIDA "+ChatColor.GREEN+nm);	
//								e1.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
//								e1.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
//						}
//						
//					}
//					
//				}
//			}
//			
		}
	
	
	
		
	
	 @EventHandler 
	 public void onDamage(EntityDamageByEntityEvent e) {
		 
		 
		 Entity atacante = e.getDamager();
		 Entity entidadAtacada = e.getEntity();
		 
		 MobsActions ma = new MobsActions(plugin);
		 ma.getAttackedZombie(atacante, entidadAtacada);
		 ma.getZombiettack(atacante, entidadAtacada);
		 
		
		  if(atacante instanceof Player) {
				Player player = (Player) atacante;
				 GameConditions gc = new GameConditions(plugin);
				
				
				 
				if(!gc.isPlayerinGame(player)) {
					return;
				}
				
					PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
					 if(entidadAtacada instanceof LivingEntity) {
						 LivingEntity mob = (LivingEntity) entidadAtacada;
						  	pl.getGamePoints().setDamage(pl.getGamePoints().getDamage()+ConvertDoubleToInt(mob.getHealth()-mob.getAttribute(Attribute.MAX_HEALTH).getBaseValue()));
					 }
					 
					  if(entidadAtacada instanceof Player || entidadAtacada instanceof Villager) {
						  String map = pl.getMapName();
				 			if(!gc.isPvPAllowed(map)) {
				 				e.setCancelled(true);
				 				player.sendTitle("",ChatColor.RED+"El PVP en el Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no esta Habilitado",20,40,20);
				 			}
							return;
					 }
			
			  
		  }else if(atacante instanceof Monster) {
			  
			  
			  if(entidadAtacada instanceof Player) {
				    Player player = (Player) entidadAtacada;
					GameConditions gc = new GameConditions(plugin);
					if(gc.isPlayerinGame(player)) {
						plugin.CreditKill().put(player, atacante);
					}
			  }
		  }else if(atacante instanceof LivingEntity) {
			
				LivingEntity le = (LivingEntity) atacante; 
				LivingEntity victim = (LivingEntity) entidadAtacada; 

				
				 GameConditions gc = new GameConditions(plugin);
				// GameIntoMap c = new GameIntoMap(plugin);
			
		
				if(le.getType() == EntityType.IRON_GOLEM) {
					 
					if(victim != null && le.getCustomName() != null) {
			
						String name = ChatColor.stripColor(le.getCustomName());
				
						if(name.startsWith("GUARDIA DE: ")) {
						
							Player target = Bukkit.getPlayer(name.replaceAll("GUARDIA DE: ","").replaceAll(" ",""));
						
							if(!gc.isPlayerinGame(target)) return;
							plugin.getGuardianCredit().put(victim, target.getName());
							//c.gamePlayerAddPoints(target);
							//target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
						}
					}
				}
			} 
			
	      
	   }
	 
//	 //@EventHandler(priority = EventPriority.LOWEST)
//	 public void mginput(PlayerInputEvent e) {
//		 Bukkit.getConsoleSender().sendMessage("Test: "+e.getPlayer().getCurrentInput());
//		 Bukkit.getConsoleSender().sendMessage("Test2: "+e.getInput());
//	 }

	 
	 //TODO CHAT
	 @EventHandler(priority = EventPriority.LOWEST)
	 public void chatplayer(AsyncPlayerChatEvent e) {
		 
		 Player player = e.getPlayer();
		 String message = e.getMessage();
	
		 
		 GameConditions cm = new GameConditions(plugin);
		 //ELIMINAR AL JUGADOR PARA QUE NO VEA MENSAJE DE OTROS JUGADORES
		 Set<Player> c1 = e.getRecipients();
		 c1.removeIf(t -> plugin.getPlayerInfoPoo().containsKey(t));
		 if(cm.isPlayerinGame(player)) {
			 
			 PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			 RankPlayer ra = new RankPlayer(plugin);

				 
						if(plugin.getKnockedPlayer().containsKey(player)) {
							RevivePlayer rp = plugin.getKnockedPlayer().get(player);
							int timelife = rp.getRemainingTimeLife();
							
							if(timelife >= 41 && timelife <= 60) {
								cm.sendMessageToAllUsersOfSameMap(player,ra.getRankPrestigeColor(pl.getMgPlayerPrestige())+"&8&l[&a&lDERRIBADO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&7"+player.getName()+": &a"+message+" \n&6(Le queda &c"+rp.getRemainingTimeLife()+"secs &6de vida.)");

							}else if(timelife >= 21 && timelife <= 40) {
								cm.sendMessageToAllUsersOfSameMap(player,ra.getRankPrestigeColor(pl.getMgPlayerPrestige())+"&8&l[&e&lDERRIBADO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&7"+player.getName()+": &a"+message+" \n&6(Le queda &c"+rp.getRemainingTimeLife()+"secs &6de vida.)");

							}else if(timelife >= 1 && timelife <= 20) {
								cm.sendMessageToAllUsersOfSameMap(player,ra.getRankPrestigeColor(pl.getMgPlayerPrestige())+"&8&l[&c&lDERRIBADO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&7"+player.getName()+": &a"+message+" \n&6(Le queda &c"+rp.getRemainingTimeLife()+"secs &6de vida.)");

							}
							
 
						}else if(pl.getPlayerGameStatus() == PlayerGameStatus.ALIVE) {
							cm.sendMessageToAllUsersOfSameMap(player,ra.getRankPrestigeColor(pl.getMgPlayerPrestige())+"&8&l[&a&lVIVO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&f"+player.getName()+": &a"+message);
							
						}else if(pl.getPlayerGameStatus() == PlayerGameStatus.DEAD) {
							cm.sendMessageToAllUsersOfSameMap(player,ra.getRankPrestigeColor(pl.getMgPlayerPrestige())+"&8&l[&c&lMUERTO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&f"+player.getName()+": &e"+message);
		
						}else if(pl.getPlayerGameStatus() == PlayerGameStatus.SPECTATOR) {
							cm.sendMessageToAllUsersOfSameMap(player,ra.getRankPrestigeColor(pl.getMgPlayerPrestige())+"&8&l[&f&lESPECTADOR&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&f"+player.getName()+": &7"+message);
							
						}
						
						//USA TEXT COMPONENT
//						if(plugin.getKnockedPlayer().containsKey(player)) {
//							RevivePlayer rp = plugin.getKnockedPlayer().get(player);
//							int timelife = rp.getRemainingTimeLife();
//							
//							if(timelife >= 41 && timelife <= 60) {
//								cm.SendMessageTextComponentToAllUsersOfSameMap(player,Utils.colorText("&8&l[&a&lDERRIBADO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&7"+player.getName()+": &a"+message+" &6(Le queda &c"+rp.getRemainingTimeLife()+"secs &6de vida.)"));
//
//							
//							}else if(timelife >= 21 && timelife <= 40) {
//								cm.SendMessageTextComponentToAllUsersOfSameMap(player,Utils.colorText("&8&l[&e&lDERRIBADO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&7"+player.getName()+": &a"+message+" &6(Le queda &c"+rp.getRemainingTimeLife()+"secs &6de vida.)"));
//
//								
//							}else if(timelife >= 1 && timelife <= 20) {
//								cm.SendMessageTextComponentToAllUsersOfSameMap(player,Utils.colorText("&8&l[&c&lDERRIBADO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&7"+player.getName()+": &a"+message+" &6(Le queda &c"+rp.getRemainingTimeLife()+"secs &6de vida.)"));
//
//								
//							}
//							
// 
//						}else if(alive1.contains(player.getName())) {
//		
//							cm.SendMessageTextComponentToAllUsersOfSameMap(player,Utils.colorText("&8&l[&a&lVIVO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&f"+player.getName()+": &a"+message));
//						}else if(deads1.contains(player.getName())) {
//		
//							cm.SendMessageTextComponentToAllUsersOfSameMap(player,Utils.colorText("&8&l[&c&lMUERTO&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&f"+player.getName()+": &e"+message));
//		
//						}else if(spec.contains(player.getName())) {
//		
//							cm.SendMessageTextComponentToAllUsersOfSameMap(player,Utils.colorText("&8&l[&f&lESPECTADOR&8&l] "+ra.getRankLevelColor(pl.getMgPlayerLvl())+"&f"+player.getName()+": &7"+message));
//						}

					 e.setCancelled(true);
					
				
		
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
					 player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, player.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
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
		
		
		
		//@EventHandler  //METODO
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
			
				// 
				Entity ent = e.getEntity();
				 
				if(ent.getCustomName() != null){
					String mobname = ChatColor.stripColor(ent.getCustomName());
					
					
					
					List<String> names = new ArrayList<>();
					names.add("Mina Explosiva");
					names.add("Ataque Aereo");
					names.add("Anti Aereo Explosivo");
					names.add("Suicida");
					names.add("Cohete");
					names.add("Super TNT");
					
					
					//names.stream().filter(o -> o.contains(ChatColor.stripColor(ent.getCustomName()))).findFirst().isPresent() || 
					if(names.stream().filter(o -> o.startsWith(mobname)).findFirst().isPresent()) {
						
						List<Material> mat = new ArrayList<>();
						mat.add(Material.INFESTED_CHISELED_STONE_BRICKS);mat.add(Material.INFESTED_COBBLESTONE);mat.add(Material.INFESTED_CRACKED_STONE_BRICKS);
						mat.add(Material.INFESTED_DEEPSLATE);mat.add(Material.INFESTED_MOSSY_STONE_BRICKS);mat.add(Material.INFESTED_STONE);
						mat.add(Material.INFESTED_STONE_BRICKS);
						for(Block b : e.blockList()) {
							if(mat.contains(b.getType())) {
								//System.out.println("SI");
								b.getWorld().playEffect(b.getLocation().add(0.5,0,0.5), Effect.STEP_SOUND,b.getType()); 
								b.setType(Material.AIR);
							}
						}
						
					   e.blockList().clear();
					  return;
					}
				}
				
				 
			
				FileConfiguration config = plugin.getConfig();
			
				List<String> mundos = config.getStringList("Regen-Explosion.List");

				String mundo = e.getEntity().getWorld().getName();

				if (mundos.contains(mundo)) {
				
					List<Block> blocks = e.blockList();
					new RegenRun(blocks).runTaskTimer(plugin, 1, 1);
					
					//e.setYield(0);
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
					//e.setYield(0);
					
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
						
						if(player.getFacing() == BlockFace.UP) {
							
							player.setVelocity(ch.toVector().setY(2.5).multiply(0.3));
							
						}else {
							player.setVelocity(ch.toVector().setY(1).multiply(0.3));
						}
					}
					
				}
				
				if(name.contains("GANCHO DE MANIOBRAS")) {
					if(e.getState() == PlayerFishEvent.State.REEL_IN) {
					
						if(!player.getInventory().containsAtLeast(new ItemStack(Material.LAPIS_LAZULI),10)) {
							player.sendMessage(ChatColor.RED+"Necesitas tener 10 de LapisLazuli.");
							return;
						}
						
						player.getInventory().removeItem(new ItemStack(Material.LAPIS_LAZULI,10));
						Location pl = player.getLocation();
						Location hookl = e.getHook().getLocation();
						Location ch = hookl.subtract(pl);
						
						player.setVelocity(ch.toVector().multiply(0.3).setY(1));
						
					}
					
				}
			}
			
	
	
			 
			
		}
		
		public void spawnLootEnchantedTable(Player player,Location l) {
			
				
				Random ra = new Random();
				GameConditions gc = new GameConditions(plugin);
				
				if(gc.hasPlayerTempCooldown(player, 30)) return;
		
				 Attribute attribute = Attribute.FOLLOW_RANGE; 
				 Attribute attributer = Attribute.SPAWN_REINFORCEMENTS; 


				PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
				String map = pl.getMapName();
				GameInfo gi = plugin.getGameInfoPoo().get(map);
				// hierro oro diamante esmeralda netherite creeper zombi
				int r = ra.nextInt(7+1);
				int low = 20;
				int hig = 30;
				int r2 = ra.nextInt(hig-low+1) + low;
				Location l1 = l;
				
				l1 = l1.add(0.5,1,0.5);
				 
				List<Entity> list = getNearbyEntitesItems(l1, 5);
				//System.out.println("Lista 2 "+list.size());
				if(list.size() >= gi.getLootTableLimit()) {
					gc.sendMessageToAllPlayersInMap(map,ChatColor.GREEN+player.getName()+Utils.colorTextChatColor("\n&cTu ambicion sera tu Perdicion.\n&5Anti-Looter-2 &cy &5Suicida Invocados \n&e(&8Dejo tirados muchos Items Cerca de la Mesa&e)"));
					l1.getWorld().spawnParticle(Particle.FLAME, l1,	/* N DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
					player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					
					Zombie zombi = (Zombie) l1.getWorld().spawnEntity(l1, EntityType.ZOMBIE);
					zombi.getAttribute(attribute).setBaseValue(150);
					zombi.getAttribute(attributer).setBaseValue(50);
					zombi.setCustomName(""+ChatColor.GOLD+ChatColor.BOLD+"Anti-Looter-2");
					zombi.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					zombi.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi.getEquipment().setItemInMainHand(Items.ZOMBIPALO.getValue());
					zombi.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
					zombi.setTarget(player);
					
					
					PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 120 * 20,/*amplifier:*/2, true ,true,true );
					PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 120 * 20,/*amplifier:*/5, true ,true,true );
	
					
				    zombi.addPotionEffect(rapido);
					zombi.addPotionEffect(salto);
					
					
					Creeper c = (Creeper) l1.getWorld().spawnEntity(l1, EntityType.CREEPER);
					c.setExplosionRadius(15);
					c.setMaxFuseTicks(1);
					c.getAttribute(attribute).setBaseValue(150);
					c.setCustomName(""+ChatColor.AQUA+ChatColor.BOLD+"SUICIDA");
					c.setTarget(player);
					zombi.addPassenger(c);
					gc.setPlayerTempCooldown(player);
				}else if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 100)) {
					
					gc.sendMessageToAllPlayersInMap(map,ChatColor.GREEN+player.getName()+Utils.colorTextChatColor(" \n&crecibio un Castigo por tener muchos &bDiamantes. \n&e(&8Tiene mas de 100 de &bDiamantes&e)\n&5Anti-Looter &cy &5Guardia del Loot Invocados"));
					l1.getWorld().spawnParticle(Particle.FLAME, l1,	/* N DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
					player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					
					Zombie zombi = (Zombie) l1.getWorld().spawnEntity(l1, EntityType.ZOMBIE);
					zombi.getAttribute(attribute).setBaseValue(150);
					zombi.getAttribute(attributer).setBaseValue(50);
					zombi.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Anti-Looter");
					zombi.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					zombi.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi.getEquipment().setItemInMainHand(Items.ZOMBIPALO.getValue());
					zombi.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
					
				
					PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 60 * 20,/*amplifier:*/2, true ,true,true );
					PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 60 * 20,/*amplifier:*/5, true ,true,true );
	
					
				    zombi.addPotionEffect(rapido);
					zombi.addPotionEffect(salto);
					zombi.setTarget(player);
					
					Witch c1 = (Witch) l1.getWorld().spawnEntity(l1, EntityType.WITCH);
					c1.getAttribute(attribute).setBaseValue(150);
					c1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"GUARDIA DEL LOOT");
					c1.setTarget(player);
					gc.setPlayerTempCooldown(player);
				}else if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 100)) {
					
					gc.sendMessageToAllPlayersInMap(map,ChatColor.GREEN+player.getName()+Utils.colorTextChatColor(" \n&crecibio un Castigo por tener mucho &6Oro. \n&e(&8Tiene mas de 100 de Oro&e)\n&5Guardias del Loot Invocados"));

					l1.getWorld().spawnParticle(Particle.FLAME, l1.add(0.5, 1, 0.5),	/* N DE PARTICULAS */5, 0.5, 1, 0.5, /* velocidad */0, null, true);

					player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					
					Creeper c = (Creeper) l1.getWorld().spawnEntity(l1, EntityType.CREEPER);
					c.setExplosionRadius(5);
					c.getAttribute(attribute).setBaseValue(150);
					c.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"GUARDIA DEL LOOT");
					c.setTarget(player);
					
					//l.getWorld().spawnParticle(Particle.FLAME, l.add(0.5, 1, 0.5),	/* N DE PARTICULAS */5, 0.5, 1, 0.5, /* velocidad */0, null, true);

					//player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 20.0F, 0F);
					
					Witch c1 = (Witch) l1.getWorld().spawnEntity(l1, EntityType.WITCH);
					c1.getAttribute(attribute).setBaseValue(150);
					c1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"GUARDIA DEL LOOT");		
					c1.setTarget(player);
					gc.setPlayerTempCooldown(player);
				}else {
					
					l1.getWorld().spawnParticle(Particle.FIREWORK, l1,	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					if(r == 0) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.LAPIS_LAZULI,5));
					}if(r == 1) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.EXPERIENCE_BOTTLE,r2));
					}if(r == 2) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.DIAMOND,r2));
					}if(r == 3) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.IRON_INGOT,r2));
					}if(r == 4) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.EMERALD,r2));
					}if(r == 5) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.GOLD_INGOT,r2));
					}if(r == 6) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.NETHERITE_INGOT,2));
					}if(r == 7) {
						l1.getWorld().dropItem(l1,new ItemStack(Material.WOODEN_SWORD,1));
					}
				}
			
			
		}
		
		
		//TODO PROYECTILE
		 @EventHandler
		 public void onProjectileEvent(ProjectileHitEvent e) {
			 
		  
			  Projectile projectile = e.getEntity();
			  GameConditions gc = new GameConditions(plugin);
			  
			  
			  if(projectile.getShooter() instanceof Snowman) {
				  
				  Entity entidadhit = e.getHitEntity();
				  
				  
				  if(entidadhit instanceof Player ||  entidadhit instanceof Snowman  || entidadhit instanceof IronGolem) {
					  e.setCancelled(true);
				  }
				  
				  
			  }
			 
			  if(projectile.getShooter() instanceof Player) {
				 
				  Player player = (Player) projectile.getShooter();
				  Entity entidadhit = e.getHitEntity();
				 
				  if(entidadhit != null) {
					    if(!gc.isPlayerinGame(player)) return;
					    
					    
					     MobsActions ma = new MobsActions(plugin);
						 ma.getAttackedZombie(player, entidadhit);
					    //AUMENTO DE PUNTO POR ELIMINAR MOBS VIVOS , 
					  	PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
					  	if(entidadhit instanceof LivingEntity) {
							    LivingEntity mob = (LivingEntity) entidadhit;
							 	 
							  	pl.getGamePoints().setDamage(pl.getGamePoints().getDamage()+ConvertDoubleToInt(mob.getHealth()-mob.getAttribute(Attribute.MAX_HEALTH).getBaseValue()));
								//isaHeadShot(player, mob, projectile);
								if(gc.isGuardian(mob)) return;
							  	headShoot(player, mob, projectile);
						 }
					  	
					  	
						if(entidadhit instanceof Player || entidadhit instanceof Villager) {
								String map = pl.getMapName();
				
								if(entidadhit == player)return;
								 
					 			if(!gc.isPvPAllowed(map)) {
					 				e.setCancelled(true);
					 				player.sendTitle("",ChatColor.RED+"El PVP en el Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no esta Habilitado",20,40,20);
					 	}}
							return;
					 }
				   
				  if(e.getHitBlock() != null) {
					  Block b = e.getHitBlock();
					  if(projectile instanceof AbstractArrow) {
						  AbstractArrow arrow = (AbstractArrow) projectile;
						  if(arrow.getCustomName() != null) {
							  if(arrow.getCustomName().equals("Flecha Trampa") || arrow.getCustomName().equals("Flecha Trampa Mejorada")) {
								arrow.remove();
							  }else if(arrow.getCustomName().startsWith("Torreta")) {
								arrow.remove();
							  }
							  
						  }else{
							  if(b.getType() == Material.BARRIER) {
								  arrow.remove();
							  }
						  }
					  } 
						 // Player player = (Player) projectile.getShooter();
						  if(b.getType() == Material.TARGET) {
							   if(!gc.isPlayerinGame(player)) return;
							  detectDispenser(player,b.getLocation(),plugin.getGameInfoPoo().get(plugin.getPlayerInfoPoo().get(player).getMapName()));
							  DetectChestAndCommand(b.getLocation());
						  }if(b.getType() == Material.STRUCTURE_BLOCK) {
							if(projectile instanceof AbstractArrow) {
								AbstractArrow arrow = (AbstractArrow) projectile;
								
							
								for(int i = 1 ; i < 6; i++) {
									Vector v = arrow.getVelocity().normalize().multiply(-i);
									Block newblock = b.getLocation().add(v).getBlock();
									if(newblock.getType() != Material.AIR) continue;	
									newblock.setType(Material.DIAMOND_BLOCK);
								}
								
							}
							  
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
				  
				
				  
				 
			  }else if (projectile.getShooter() instanceof LivingEntity) {
				  Entity damager =  (Entity) projectile.getShooter();
				  Entity entidadhit = e.getHitEntity();
		
				  if(damager.getType() == EntityType.SNOW_GOLEM) {
						LivingEntity le = (LivingEntity) damager;
						LivingEntity victim = (LivingEntity) entidadhit;
						
						if(victim != null && le.getCustomName() != null) {
							
							String name = ChatColor.stripColor(le.getCustomName());
							
							if(name.startsWith("GUARDIA DE: ")) {
							

								Player target = Bukkit.getPlayer(name.replaceAll("GUARDIA DE: ","").replaceAll(" ",""));
								if(!gc.isPlayerinGame(target)) return;
								plugin.getGuardianCredit().put(victim, target.getName());
							}
						}
				  }
			  }else if(e.getHitBlock() != null) {
				  Block b = e.getHitBlock();
				  if(projectile instanceof AbstractArrow) {
					  AbstractArrow arrow = (AbstractArrow) projectile;
					  if(arrow.getCustomName() != null) {
						  if(arrow.getCustomName().equals("Flecha Trampa") || arrow.getCustomName().equals("Flecha Trampa Mejorada")) {
							arrow.remove();
						  }else if(arrow.getCustomName().startsWith("Torreta")) {
							arrow.remove();
						  }
						  
					  }else{
						  if(b.getType() == Material.BARRIER) {
							  arrow.remove();
						  }
					  }
				  }
			  }
			  
			   
		 }
		
		//TODO REDUCE
		public boolean removeItemstackCustom(Player player,ItemStack it,int cant) {
			
			
			Inventory inv = player.getInventory();
			
			if(player.getInventory().getItemInMainHand().isSimilar(it)) {
				if(inv.containsAtLeast(it, cant)) {
					
					
					int slot = inv.first(it);
					@SuppressWarnings("unused")
					boolean hasAmmo = false;
					for (slot = 0; slot < inv.getSize(); slot++) {

						ItemStack item = inv.getItem(slot);// 3
						if (item != null && item.isSimilar(it)) {
							hasAmmo = true;
							
							int amount = item.getAmount() - cant;
							if (amount <= 0) {
								item = new ItemStack(Material.AIR);
							} else {
								item.setAmount(amount);
							}
							inv.setItem(slot, item);
							//break;
							return true;
						}
					}
					
				}
			}
			
		
			
			
			return false;
		}
		
		//ORIGINAL
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
			
			
			return ;
		}
		
		
		public List<Entity> getNearbyEntities(Location l , int size){
			
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
						if(e.getType() == EntityType.ITEM) {
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
		
		public void detectDispenser(Player player ,Location l,GameInfo gi) {
			
			Block block = l.getBlock();
			Block r = block.getRelative(0, 0, 0);
			int rango = gi.getDispenserRange();;
			MobsActions ma = new MobsActions(plugin);
			
		//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
				
			//	Powerable pw = (Powerable) r.getBlockData();
			//	if(pw.isPowered()) {
 
					for (int x = -rango; x < rango+1; x++) {
						for (int y = -rango; y < rango+1; y++) {
							for (int z = -rango; z < rango+1; z++) {
		
								Block a = r.getRelative(x, y, z);
		
								// setea bloques en esos puntos
								
		
									if(a.getType() == Material.DISPENSER) {
										
										ma.detectBlockAndShoot(player, a.getLocation(), rango);
										
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
											
										}if(i.containsAtLeast(Items.ARROWDIS.getValue(),1)) {
											ProyectileShootType(d, EntityType.ARROW, 3, loc);
											
										}if(i.containsAtLeast(Items.ARROWDIS2.getValue(),1)) {
											ProyectileShootType(d, EntityType.ARROW, 4, loc);
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
		
	
		
		
		@SuppressWarnings("removal")
		public void ProyectileShootType(Dispenser d, EntityType type , int option ,Location loc ) {
					Location loc2 = loc;
			
					if(d.getFacing() == BlockFace.NORTH) {
						
						if(type == EntityType.ARROW) {
						
						
							Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0.5,0.5,-1), EntityType.ARROW);
								if(option == 1) {
									
									aw.setVelocity(d.getFacing().getDirection().multiply(6));
									aw.setCritical(true);
									aw.setKnockbackStrength(10);
									aw.setTicksLived(20);
									aw.setCustomName("Flecha Trampa");
									aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
								}
								if(option == 2) {
									aw.setVelocity(d.getFacing().getDirection().multiply(6));
									aw.setCritical(true);
									aw.setKnockbackStrength(10);
									aw.setTicksLived(20);
									aw.setFireTicks(1200);
									aw.setCustomName("Flecha Trampa");
									aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
								}if(option == 3) {
									
								 	for(int i = 0;i<20;i++) {
										SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),false);
									}
									
								}if(option == 4) {
									
								 	for(int i = 0;i<20;i++) {
										SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),true);
									}
								
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
									
									
									Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0.5,0.5,1), EntityType.ARROW);
									if(option == 1) {
										aw.setVelocity(d.getFacing().getDirection().multiply(6));
										aw.setCritical(true);
										aw.setKnockbackStrength(10);
										aw.setTicksLived(20);
										aw.setCustomName("Flecha Trampa");
										aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
									}
									if(option == 2) {
										aw.setVelocity(d.getFacing().getDirection().multiply(6));
										aw.setCritical(true);
										aw.setKnockbackStrength(10);
										aw.setTicksLived(20);
										aw.setFireTicks(1200);
										aw.setCustomName("Flecha Trampa");
										aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
									}if(option == 3) {
										
									 	for(int i = 0;i<20;i++) {
											SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),false);
										}
										
									}if(option == 3) {
										
									 	for(int i = 0;i<20;i++) {
											SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),true);
										}
										
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
						
							Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(1,0.5,0.5), EntityType.ARROW);
							if(option == 1) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}if(option == 3) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),false);
								}
								
							}if(option == 3) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),true);
								}
								
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
							
							
							Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(-1,0.5,0.5), EntityType.ARROW);
							if(option == 1) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}if(option == 3) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),false);
								}
								
							}if(option == 4) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),true);
								}
								
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
							
							
							
							Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0.5,1,0.5), EntityType.ARROW);
							
							if(option == 1) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}if(option == 3) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),false);
								}
								
							}if(option == 4) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),true);
								}
								
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
						
							Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0.5,-1,0.5), EntityType.ARROW);
							
							if(option == 1) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}
							if(option == 2) {
								aw.setVelocity(d.getFacing().getDirection().multiply(6));
								aw.setCritical(true);
								aw.setKnockbackStrength(10);
								aw.setTicksLived(20);
								aw.setFireTicks(1200);
								aw.setCustomName("Flecha Trampa");
								aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
							}if(option == 3) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),false);
								}
								
							}if(option == 4) {
								
							 	for(int i = 0;i<20;i++) {
									SpawnArrowsDispenser(aw.getLocation(),d.getFacing(), RandomPosOrNeg(10),RandomPosOrNeg(5),true);
								}
								
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
			List<Player> alive1 = gc.ConvertStringToPlayer(ga.getAlivePlayers());
			
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
														
														
														gc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GREEN+player.getName()+ChatColor.RED+" Solicita una Reunion para poder activar un Evento.");
														return;
												   }else if(!chest.getInventory().isEmpty()) {
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
														return;
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
					
					
	//TODO DOUBLE TO INT
					
		public int ConvertDoubleToInt(double damage) {
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
			nf.setGroupingUsed(false);
			return TransformPosOrNeg(Integer.parseInt(nf.format(damage)));
		}
		
					
					
					 //TODO HEADSHOOT revisar

					
					
		 public void headShoot(Player player, LivingEntity damage, Projectile projectil) {
						 
			 			 GameIntoMap c = new GameIntoMap(plugin);
				         double projectileY = projectil.getLocation().getY();
				         double damagedY = damage.getLocation().getY();
				         int headshoot = 50;
				         int headshotarmor = 25;
				         double alture = 0;
				         //ADULTO
				         
				         if(projectil instanceof AbstractArrow) {
				        	 AbstractArrow arrow = (AbstractArrow) projectil;
				        	 if(arrow.isCritical()) {
				        		 if(damage instanceof Ageable) {
				        			 Ageable entity = (Ageable) damage;
				        			 if(entity.isAdult()) {
				        				 alture = 1.57D;
				        			 }else {
				        				 alture = 0.57D;
				        			 }
				        		 }else {
				        			 alture = 1.57D; 
				        		 }
				        		 
				        		 if(damage instanceof Snowman || damage instanceof IronGolem) return;
				        		 if(damage instanceof Animals || damage instanceof AnimalTamer || damage instanceof Spider || damage instanceof CaveSpider || damage instanceof Ambient || damage instanceof WaterMob){
				        			return; 
				        		 }
				        		 
		                		 if((projectileY - damagedY >= alture)) {//ADULT OR KID originalmente estaba en 1.35D
		                			 
		                			 if(damage.getEquipment().getHelmet().isSimilar(new ItemStack(Material.AIR)) || damage.getEquipment().getHelmet() == null){
		                				 
		                				 if(damage.getHealth() > headshoot){
		                					 
		 								       damage.getWorld().spawnParticle(Particle.DRIPPING_LAVA, damage.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
		                					   damage.setHealth((damage.getHealth() - headshoot));
		                					   player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
		                					 
		                				 }else{
		                					damage.getWorld().dropItem(damage.getLocation(), new ItemStack(Material.DIAMOND,5));
		         							damage.getWorld().dropItem(damage.getLocation(), new ItemStack(Material.NETHERITE_INGOT,2));
		         			            	damage.getWorld().dropItem(damage.getLocation(), new ItemStack(Material.EMERALD,5));
		         			            	damage.getWorld().dropItem(damage.getLocation(), new ItemStack(Material.GOLD_INGOT,5));
		         			            	damage.getWorld().dropItem(damage.getLocation(), new ItemStack(Material.IRON_INGOT,5));
		         			            	damage.setHealth(0);
		         			            	projectil.remove();
		         			            	player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
		         			            	
		         							 c.gamePlayerAddPoints(player);
		         			            	 
		                				 }
		                				 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""+ChatColor.RED+ChatColor.BOLD+"CRITICAL HEADSHOT"));
		                				   
		                		       }else{
		                		    	   		 ItemStack it = damage.getEquipment().getHelmet();
		                		    	   	
		                		    	   		 
		    									 if(it.getType() == Material.NETHERITE_HELMET || it.getType() == Material.DIAMOND_HELMET || it.getType() == Material.GOLDEN_HELMET ||
		    									    it.getType() == Material.IRON_HELMET || it.getType() == Material.CHAINMAIL_HELMET || it.getType() == Material.LEATHER_HELMET ||
		    									    it.getType() == Material.TURTLE_HELMET){ 	
		    										 
		    										 ItemMeta im = it.getItemMeta();
		    										 Damageable dm  = (Damageable) im;
		    							
		    										 	 int itemdamage = dm.getDamage();
		        										 int damagetotal = (itemdamage + headshotarmor);
		        									
		        										 dm.setDamage(damagetotal);// REPRESENTA EL DAÑO MAS EL PLUS DE DAÑO OSEA 1 + 25 DESPUES 26 +25
		    											 it.setItemMeta(im); 
		        										 damage.getEquipment().setHelmet(it);
		    											
		    											 int vidaitem = (it.getType().getMaxDurability() - dm.getDamage());
		    										
		    											 player.getInventory().setHelmet(it);
		    											 if(vidaitem <= 0) {
		    												 damage.getEquipment().setHelmet(new ItemStack(Material.AIR));
		    												 damage.getWorld().spawnParticle(Particle.DRIPPING_LAVA, damage.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */25, 0.5, 1, 0.5, /* velocidad */0, null, true);

		        											 player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 20.0F, 1F);
		        											 if(damage.getCustomName() != null) {
		           		           			   		            // player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED+">>> "+ChatColor.GRAY+damage.getName()+ChatColor.RED+" <<<"));
		        		        	   			                	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED+">>> "+ChatColor.GRAY+damage.getName()+ChatColor.GRAY+" A ROTO SU CASCO "+ChatColor.RED+" <<<"));

		        		        	   			                }else {
		        		        	   			                	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED+">>> "+ChatColor.GRAY+damage.getType()+ChatColor.GRAY+" CASCO ROTO "+ChatColor.RED+" <<<"));
		           		           			   		             //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED+">>> "+ChatColor.GRAY+damage.getType()+ChatColor.RED+" <<<"));

		        		        	   			                	
		        		        	   			                }
		        											
		    											 }else {
		    												 damage.getWorld().spawnParticle(Particle.DRIPPING_LAVA, damage.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */25, 0.5, 1, 0.5, /* velocidad */0, null, true);
		        		    								 player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 20.0F, 1F); 
		        		    								 if(damage.getCustomName() != null) {
		           		           			   		             //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW+"!!! "+ChatColor.GRAY+damage.getName()+ChatColor.YELLOW+" !!!"));
		        		        	   			                	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW+"!!! "+ChatColor.RED+damage.getName()+ChatColor.GRAY+" TIENE CASCO "+vidaitem+"/"+it.getType().getMaxDurability()+ChatColor.YELLOW+" !!!"));

		        		    									 
		        		    									 
		        		        	   			                }else {
		           		           			   		            // player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW+"!!! "+ChatColor.GRAY+damage.getType()+ChatColor.YELLOW+" !!!"));
		        		        	   			                	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW+"!!! "+ChatColor.RED+damage.getType()+ChatColor.GRAY+" TIENE CASCO "+vidaitem+"/"+it.getType().getMaxDurability()+ChatColor.YELLOW+" !!!"));
		        		        	   			                	
		        		        	   			                }
		    											 }			
		    									}else{
		    											 damage.getEquipment().setHelmet(new ItemStack(Material.AIR));
					    								// damage.getWorld().spawnParticle(Particle.BLOCK_CRACK, damage.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 0.5, 1, 0.5, /* velocidad */0, null, true);
														 player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 20.0F, 1F);
														 return;
		    										
		    									}
		        		            	}
		                			 
		                			 return;
		                         }
				        	 }
				         }
				         

					 }
		 
		 /// TODO ARROWS
		 
		 

		 
			public int RandomPosOrNeg(int i){
				Random r = new Random();
				int v = r.nextInt(i+1);
				//genera aleatoriedad si es 0 devuelve el valor como tal si es 1 devuelve un valor positivo o negativo
				int r2 = r.nextInt(1+1);
				if(r2 == 1){
					return v;
				}
				return TransformPosOrNeg(v);
			}
			
			public int TransformPosOrNeg(int i){
				return i =  (~(i -1));
			}
			
			
			
			
			
			@SuppressWarnings("removal")
			public void SpawnArrows(Player player,float addy ,float addp ) {
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
				Location loc = player.getLocation();
				loc.setYaw(loc.getYaw()+addy);
				loc.setPitch(loc.getPitch()+addp);
				Vector v = loc.getDirection();
				
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setKnockbackStrength(2);
				//aw.setFireTicks(1200);
				aw.setShooter(player);
				//((Arrow) h1).setShooter(player);
			}
			
			@SuppressWarnings("removal")
			public void SpawnArrowsFire(Player player,float addy ,float addp ) {
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
				Location loc = player.getLocation();
				loc.setYaw(loc.getYaw()+addy);
				loc.setPitch(loc.getPitch()+addp);
				Vector v = loc.getDirection();
				
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setKnockbackStrength(2);
				aw.setFireTicks(1200);
				aw.setShooter(player);
			}
			
			@SuppressWarnings("removal")
			public void SpawnArrowsPoison(Player player,float addy ,float addp ) {
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
				PotionEffect poison = new PotionEffect(PotionEffectType.POISON,/*duration*/ 20*20,/*amplifier:*/50, false ,false,true );
				Location loc = player.getLocation();
				loc.setYaw(loc.getYaw()+addy);
				loc.setPitch(loc.getPitch()+addp);
				Vector v = loc.getDirection();
				
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setColor(Color.GREEN);
				aw.setKnockbackStrength(2);
				aw.addCustomEffect(poison, false);
				aw.setShooter(player);
				//((Arrow) h1).setShooter(player);
			}
			
			
			@SuppressWarnings("removal")
			public void SpawnArrowsDamage(Player player,float addy ,float addp ) {
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
				PotionEffect damage = new PotionEffect(PotionEffectType.INSTANT_DAMAGE,/*duration*/ 20*20,/*amplifier:*/50, false ,false,true );

				Location loc = player.getLocation();
				loc.setYaw(loc.getYaw()+addy);
				loc.setPitch(loc.getPitch()+addp);
				Vector v = loc.getDirection();
				
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setColor(Color.RED);
				aw.setKnockbackStrength(2);
				aw.addCustomEffect(damage, false);
				aw.setShooter(player);
				
				//((Arrow) h1).setShooter(player);
			}
			
		
			@SuppressWarnings("removal")
			public void SpawnArrowsExplosive(Player player,float addy ,float addp ) {
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
				Location loc = player.getLocation();
				loc.setYaw(loc.getYaw()+addy);
				loc.setPitch(loc.getPitch()+addp);
				Vector v = loc.getDirection();
		
				
				
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setKnockbackStrength(2);
				aw.setCustomName("ArrowTNT");
				aw.setPierceLevel(2);
				aw.setShooter(player);
			
				
				//((Arrow) h1).setShooter(player);
			}
			
		
			@SuppressWarnings("removal")
			public void SpawnArrowsFireMob(Entity e,float addy ,float addp ) {
			
				Location loc = e.getLocation();
				loc.setYaw(loc.getYaw()+addy);
				loc.setPitch(loc.getPitch()+addp);
				Vector v = loc.getDirection();
			
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setKnockbackStrength(2);
				aw.setFireTicks(1200);
				aw.setShooter((ProjectileSource) e);
			}
		 
		 
		
			@SuppressWarnings("removal")
			public void SpawnArrowsDispenser(Location l,BlockFace bf,float addy ,float addp , boolean iswithfire) {
				Location loc = l;
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc, EntityType.ARROW);
				
				
				loc.setYaw(loc.getYaw()+addy);
				loc.setPitch(loc.getPitch()+addp);
				Vector v = bf.getDirection();
				
				aw.setCritical(true);
				aw.setCustomName("Flecha Trampa Mejorada");
				aw.setKnockbackStrength(2);
				aw.setVelocity(v.multiply(6));;
				if(iswithfire) {
					aw.setFireTicks(1200);
				}
				
				//aw.setFireTicks(1200);
				aw.setShooter(null);
				//((Arrow) h1).setShooter(player);
			}
			
			
			public void landMine(Player player) {
				 
				Block b = player.getLocation().getBlock();
				Block r = b.getRelative(0, -2, 0);
				Block r2 = b.getRelative(0, 1, 0);
				
				if(r.getType() == Material.GREEN_CONCRETE) {
						
					AreaPotion(player.getLocation(),Color.GREEN,PotionType.LONG_POISON);;
					return;
				}else if(r.getType() == Material.RED_CONCRETE) {
					AreaPotion(player.getLocation(),Color.RED,PotionType.HARMING);
					return;

				}else if(r.getType() == Material.GRAY_CONCRETE) {
					AreaPotion(player.getLocation(),Color.BLACK,PotionType.WEAKNESS);
					return;

				}else if(r.getType() == Material.AIR) {
					//System.out.println("BOOM");
					//player.getWorld().createExplosion(r2.getLocation(), 15, false, false);
					TNTPrimed ptnt = (TNTPrimed) player.getWorld().spawnEntity(r2.getLocation().add(0.5,0,0.5),EntityType.TNT);
					ptnt.setFuseTicks(0);
					ptnt.setCustomName(ChatColor.DARK_PURPLE+"Mina Explosiva");
					ptnt.setYield(5);
					//ptnt.setIsIncendiary(true);
				
				
					
		 			return;
				}
			 	
			}
			
			public void AreaPotion(Location l,Color color,PotionType type) {
				System.out.println("TYPE: "+type.toString());
				AreaEffectCloud aec = (AreaEffectCloud) l.getWorld().spawnEntity(l,  EntityType.AREA_EFFECT_CLOUD);
				aec.setBasePotionType(type);
				aec.setColor(color);
				aec.setDuration(30*20);
				aec.setRadius(10);
				aec.setReapplicationDelay(20);
				aec.setDurationOnUse(20);
				//aec.setParticle(Particle.SPELL);
				
				//aec.setRadiusOnUse(10);
				//aec.setRadiusPerTick(30*20);
						
			}
		 
		 
			public String Porcentage(int current , int max ) {
				float percent = (float) current/max*100;
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(0);
				return nf.format(percent)+"%";
			}
		 
		 
			public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,ChatColor notCompletedColor) {
		        float percent = (float) current/max;
		        int progressBars = (int) (totalBars * percent);
		 
		        return Strings.repeat(""+ completedColor +ChatColor.BOLD + symbol, progressBars) + Strings.repeat("" + notCompletedColor +ChatColor.BOLD+ symbol, totalBars - progressBars);
		   }
		 
					
					
		}
		

		

