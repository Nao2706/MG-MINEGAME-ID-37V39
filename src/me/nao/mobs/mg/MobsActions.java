package me.nao.mobs.mg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Witch;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.manager.mg.GameIntoMap;

@SuppressWarnings("deprecation")
public class MobsActions {

	private Minegame plugin;
	
	public MobsActions(Minegame plugin) {
		this.plugin = plugin;
	}
	
	//LANZA UNA FLECHA EN DIRECCION AL JUGADOR UNA VEZ SPAWNEE EN CUALQUIER SITIO
	public void detectBlockAndShoot(Player player,Location l ,int range) {
		
		
		Block a = l.getBlock();
		Block b = a.getRelative(0, -1, 0);
		
		
		if(a.getType() == Material.DISPENSER && b.getType() == Material.LODESTONE) {
			Dispenser d = (Dispenser) a.getBlockData();
			
			if(d.getFacing() == BlockFace.UP) {
				if(player.getLocation().getBlockY() > a.getLocation().getBlockY()) {
					Location pl = player.getLocation();
					Location sl = new Location(Bukkit.getWorld(pl.getWorld().getName()), pl.getX(), pl.getY()+1, pl.getZ());
				
				
					
					Arrow aw = (Arrow) a.getLocation().getWorld().spawnEntity(a.getLocation().add(0.5,1,0.5), EntityType.ARROW);
					
					Vector v = sl.toVector().subtract(aw.getLocation().toVector());
					Location en = aw.getLocation();
					en.setDirection(v);
					aw.teleport(en);
					aw.setVelocity(aw.getLocation().getDirection().multiply(6));
					aw.setCritical(true);
					aw.setFireTicks(1200);
					//aw.setKnockbackStrength(10);
					aw.setCustomName(ChatColor.RED+"Torreta Anti Aerea");
					aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
				}
			
			}
			
		}else if(a.getType() == Material.LODESTONE && b.getType() == Material.DISPENSER) {
			Dispenser d = (Dispenser) b.getBlockData();
			
			if(d.getFacing() == BlockFace.DOWN) {
				
				if(player.getLocation().getBlockY() < b.getLocation().getBlockY()) {
					Location pl = player.getLocation();
					Location sl = new Location(Bukkit.getWorld(pl.getWorld().getName()), pl.getX(), pl.getY()-1, pl.getZ());
				
					Arrow aw = (Arrow) b.getLocation().getWorld().spawnEntity(b.getLocation().add(0.5,1,0.5), EntityType.ARROW);

					Vector v = sl.toVector().subtract(aw.getLocation().toVector());
					Location en = aw.getLocation();
					en.setDirection(v);
					aw.teleport(en);
					aw.setVelocity(aw.getLocation().getDirection().multiply(6));
					aw.setCritical(true);
					aw.setFireTicks(1200);
					//aw.setKnockbackStrength(10);
					aw.setCustomName(ChatColor.RED+"Torreta Anti Aerea");
					aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
				}

			}
		}if(a.getType() == Material.DISPENSER && b.getType() == Material.BLAST_FURNACE) {
			
			
			
			Dispenser d = (Dispenser) a.getBlockData();
		

			if(d.getFacing() == BlockFace.UP) {
				
				if(player.getLocation().getBlockY() > a.getLocation().getBlockY()) {
					
					Location pl = player.getLocation();
					Location sl = new Location(Bukkit.getWorld(pl.getWorld().getName()), pl.getX(), pl.getY()+1, pl.getZ());
				
					
					Fireball fb = (Fireball) a.getLocation().getWorld().spawnEntity(a.getLocation().add(0.5,1,0.5),  EntityType.FIREBALL);
					Vector v = sl.toVector().subtract(fb.getLocation().toVector());
					Location en = fb.getLocation();
					en.setDirection(v);
					fb.teleport(en);
					fb.setCustomName("Anti Aereo Explosivo");
					fb.setVelocity(fb.getLocation().getDirection().multiply(7));
					fb.setYield(10);
					fb.setFallDistance(60);
					//fb.setBounce(false);
				
				}
				

			}
			

			//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
		}if(a.getType() == Material.DISPENSER && b.getType() == Material.BEACON) {
			
			
				
			Dispenser d = (Dispenser) a.getBlockData();
		

			if(d.getFacing() == BlockFace.UP) {
				if(player.getLocation().getBlockY() > a.getLocation().getBlockY()) {
					
					Location pl = player.getLocation();
					Location sl = new Location(Bukkit.getWorld(pl.getWorld().getName()), pl.getX(), pl.getY()+1, pl.getZ());
				
					Arrow aw = (Arrow) a.getLocation().getWorld().spawnEntity(a.getLocation().add(0.5,1,0.5), EntityType.ARROW);
					
					Vector v = sl.toVector().subtract(aw.getLocation().toVector());
					Location en = aw.getLocation();
					en.setDirection(v);
					aw.teleport(en);
					aw.setVelocity(aw.getLocation().getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
					aw.setCritical(true);
					aw.setFireTicks(1200);
					
					aw.setCustomName("Torreta Anti Aerea");
					aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
					
				
					Arrow aw2 = (Arrow) a.getLocation().getWorld().spawnEntity(a.getLocation().add(0.5,1,0.5), EntityType.ARROW);
					Vector v2 = sl.toVector().subtract(aw2.getLocation().toVector());
					Location en2 = aw2.getLocation();
					en2.setDirection(v2);
					aw2.teleport(en2);
					aw2.setVelocity(aw2.getLocation().getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
					aw2.setCritical(true);
					aw2.setFireTicks(1200);
				
					aw2.setCustomName("Torreta Anti Aerea");
					aw2.setPickupStatus(PickupStatus.CREATIVE_ONLY);
				
				
				}
		

			}
			

			//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
		}if(a.getType() == Material.BLAST_FURNACE && b.getType() == Material.DISPENSER) {
			Dispenser d = (Dispenser) b.getBlockData();
			if(d.getFacing() == BlockFace.DOWN) {
				if(player.getLocation().getBlockY() < b.getLocation().getBlockY()) {
					
					Location pl = player.getLocation();
					Location sl = new Location(Bukkit.getWorld(pl.getWorld().getName()), pl.getX(), pl.getY()-1, pl.getZ());
				
					Fireball fb = (Fireball) b.getLocation().getWorld().spawnEntity(b.getLocation().add(0.5,-1,0.5),  EntityType.FIREBALL);
					Vector v = sl.toVector().subtract(fb.getLocation().toVector());
					Location en = fb.getLocation();
					en.setDirection(v);
					fb.teleport(en);
					fb.setCustomName("Anti Aereo Explosivo");
					fb.setVelocity(fb.getLocation().getDirection().multiply(7));
					fb.setYield(10);
					//fb.setBounce(false);
					
				}
			}
		}
		if(a.getType() == Material.BEACON && b.getType() == Material.DISPENSER) {
			Dispenser d = (Dispenser) b.getBlockData();
			if(d.getFacing() == BlockFace.DOWN) {
				if(player.getLocation().getBlockY() < b.getLocation().getBlockY()) {
					
					
					Location pl = player.getLocation();
					Location sl = new Location(Bukkit.getWorld(pl.getWorld().getName()), pl.getX(), pl.getY(), pl.getZ());
					
					Arrow aw = (Arrow) b.getLocation().getWorld().spawnEntity(b.getLocation().add(0.5,-1,0.5), EntityType.ARROW);
					
					
					Vector v = sl.toVector().subtract(aw.getLocation().toVector());
					Location en = aw.getLocation();
					en.setDirection(v);
					aw.teleport(en);
					aw.setVelocity(aw.getLocation().getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
					aw.setCritical(true);
					aw.setFireTicks(1200);
					
					aw.setCustomName("Torreta Anti Aerea");
					aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
					
					
				
					
					
					Arrow aw2 = (Arrow) b.getLocation().getWorld().spawnEntity(b.getLocation().add(0.5,-1,0.5), EntityType.ARROW);
					Vector v2 = sl.toVector().subtract(aw2.getLocation().toVector());
					Location en2 = aw2.getLocation();
					en2.setDirection(v2);
					aw2.teleport(en2);
					aw2.setVelocity(aw2.getLocation().getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
					aw2.setCritical(true);
					aw2.setFireTicks(1200);
			
					aw2.setCustomName("Torreta Anti Aerea");
					aw2.setPickupStatus(PickupStatus.CREATIVE_ONLY);
				
				
					
					
				}
			}
		}
		
		
		
		
		
//		if(player.getInventory().containsAtLeast(new ItemStack(Material.COMMAND_BLOCK), 1)) {
//			return;
//		}
		
//		//FileConfiguration config = plugin.getConfig();
//		Block block = player.getLocation().getBlock();
//		Block r = block.getRelative(0, 0, 0);
//		
//	//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
//			int rango = range;
//		//	Powerable pw = (Powerable) r.getBlockData();
//		//	if(pw.isPowered()) {
//
//				for (int x = -rango; x < rango+1; x++) {
//					for (int y = -rango; y < rango+1; y++) {
//						for (int z = -rango; z < rango+1; z++) {
//	
//							Block a = r.getRelative(x, y, z);
//							if(a.getType() == Material.AIR) continue;
//						
//							Block b = r.getRelative(x, y-1, z);
//	
//							// setea bloques en esos puntos
//							
//	
//					
//		
//						};
//					};
//				};
//		//	}	
//			//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

		//}
	}
	
	
	

	//CUANDO ATACAS
	@SuppressWarnings("removal")
	public void getAttackedZombie(Entity atacante ,Entity atacada) {
		
		
		  if(atacante instanceof Player && atacada instanceof Zombie) {
			  Player target = (Player) atacante;
			  Zombie z = (Zombie) atacada;
			  
			  if(z.getCustomName() == null) return;
			  String mobname = ChatColor.stripColor(z.getCustomName());
			  
			  	PotionEffect jump = new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/5, false ,false,true );
				PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/5, false ,false,true );
			  if(mobname.contains("Screamer")) {
					
				    target.playSound(target.getLocation(), Sound.ENTITY_GHAST_SCREAM, 50.0F, 0F);
					List<Entity> list = getNearbyEntities(z.getLocation(),50);
					for(Entity ent : list) {
          				if(ent.getType() != EntityType.PLAYER && ent instanceof Monster) {
          					Monster cre = (Monster) ent;
          					//if(cre.getUniqueId().equals(z.getUniqueId())) continue;
          					cre.setTarget(target);
          				}
          			}
			   
				}if(mobname.contains("Leader")) {
				
					
					List<Entity> list = getNearbyEntities(z.getLocation(),50);
					
					for(Entity ent : list) {
						if(ent instanceof Player) {
							
							target.playSound(target.getLocation(), Sound.ENTITY_GHAST_HURT, 2, 0);
						}
						
        				if(ent.getType() != EntityType.PLAYER && ent instanceof Monster) {
        					Monster cre = (Monster) ent;
        					//if(cre.getUniqueId().equals(z.getUniqueId())) continue;
        					cre.setTarget(target);
        					cre.addPotionEffect(speed);
        					cre.addPotionEffect(jump);
        				}
        			}
			   
				}if(mobname.contains("Zombi Puas")) {
			    	
				  for(int i = 0;i<10;i++) {
						Location loc = z.getLocation();
						
						Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
						aw.setVelocity(loc.getDirection().add(new Vector(RandomV(),0.3,RandomV())).multiply(3));
						aw.setCritical(true);
						aw.setKnockbackStrength(2);
						aw.setFireTicks(1200);
						aw.setShooter(z);
						//((Arrow) h1).setShooter(player);
					}
			}if(mobname.contains(ChatColor.RED+"Summon")) {
					for(int i =0 ; i< 10;i++) {
						Location loc = z.getLocation();
						Zombie z1 = (Zombie) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ZOMBIE);
						z1.addPotionEffect(speed);
						z1.addPotionEffect(jump);
						
					}
					
					
					Random r = new Random();
				
					int rand = r.nextInt(10);
					if(rand == 0) {
						target.sendMessage(ChatColor.RED+" A ver si esto es de tu talla");
					}
					
					if(rand == 1) {
						target.sendMessage(ChatColor.RED+" A correr "+target.getName());
					}
					
					if(rand == 2) {
						target.sendMessage(ChatColor.RED+" Yo llamo refuerzos y tu?? ");
					}
					
					if(rand == 3) {
						target.sendMessage(ChatColor.RED+" Alguieen va a morirr ");
					}
					
					if(rand == 4) {
						target.sendMessage(ChatColor.RED+" Momento horda ");
					}
					
					if(rand == 5) {
						target.sendMessage(ChatColor.RED+" Nuke Moment no creo que escapes");
						//player.getInventory().removeItem(new ItemStack(Material.TNT,1));
						Location lm = new Location(target.getWorld(),target.getLocation().getBlockX(),256,target.getLocation().getBlockZ());
						Location l = z.getLocation();
						
						//fb.setCustomName("Mortero");
						
						Fireball f =  (Fireball) l.getWorld().spawnEntity(lm.add(0.5, 0, 0.5), EntityType.FIREBALL);
						f.setYield(10);
						f.setDirection(new Vector (0,-3,0));
						f.setVelocity(f.getDirection().multiply(3));
						f.setShooter(z);
						
					}
					if(rand == 7) {
						target.sendMessage(ChatColor.RED+" Jutsu Clon de Sombra ");
						
							for(int i = 0 ; i < 3;i++) {
								Location loc = z.getLocation();
							
							
								Zombie z1 = (Zombie) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ZOMBIE);
								z1.setCustomName(ChatColor.RED+"Summon");
								z1.addPotionEffect(speed);
								z1.addPotionEffect(jump);
							}
					}if(rand == 8) {
						target.sendMessage(ChatColor.RED+" Que tu copia te mate ");
						
						for(int i = 0 ; i < 3;i++) {
							
								ItemStack head = new ItemStack(Material.PLAYER_HEAD);
								SkullMeta meta = (SkullMeta) head.getItemMeta();
								meta.setOwningPlayer(target);
								Location loc = z.getLocation();
								
								Zombie z1 = (Zombie) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ZOMBIE);
								z1.setCustomName(ChatColor.GREEN+"Copia de: "+ChatColor.GOLD+target.getName());
								z1.addPotionEffect(speed);
								z1.addPotionEffect(jump);
								 ItemStack[] inv = target.getInventory().getArmorContents();
							        //acciones
							        z1.getEquipment().setArmorContents(inv);
							        z1.getEquipment().setHelmet(head);
							       //z.setCustomName(c("&aMob equipado por "+player.getName()));
							        z1.getEquipment().setItemInMainHand(target.getInventory().getItemInMainHand());
						}
							
					}if(rand == 9) {
						target.sendMessage(ChatColor.RED+" JAJAJAJA Cuidado te matas por donde vas ");
						PotionEffect jump1 = new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 25*20,/*amplifier:*/50, true ,true,false );
						PotionEffect speed1 = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 30*20,/*amplifier:*/50, true ,true,false );
						PotionEffect deb = new PotionEffect(PotionEffectType.WEAKNESS,/*duration*/ 35*20,/*amplifier:*/50, true ,true,false );
						PotionEffect cieg = new PotionEffect(PotionEffectType.BLINDNESS,/*duration*/ 22*20,/*amplifier:*/50, true ,true,false );
						target.addPotionEffect(speed1); target.addPotionEffect(cieg);
						target.addPotionEffect(jump1); 	target.addPotionEffect(deb);
					}
			  }
		  }
		
	}
	
	@SuppressWarnings("removal")
	public void onDead(Entity atacante ,Entity atacada) {
		  if(atacante instanceof Entity && atacada instanceof Zombie) {
			  Zombie z = (Zombie) atacada;
			  
			    if(z.getCustomName() == null) return;
			    String mobname = ChatColor.stripColor(z.getCustomName());
			    if(mobname.contains("VIRUS")) {
				  AreaPotion(z,z.getLocation(),PotionEffectType.POISON,"GREEN",15,20,20,5);
				  AreaPotion(z,z.getLocation(),PotionEffectType.INSTANT_DAMAGE,"RED",15,20,20,2);
					
				  
				  
			    }if(mobname.contains("HARDCORE VIRUS")) {
			    	
			    	  AreaPotion(z,z.getLocation(),PotionEffectType.HUNGER,"GREEN",30,10,20,5);
					  AreaPotion(z,z.getLocation(),PotionEffectType.POISON,"GREEN",30,10,20,50);
					  AreaPotion(z,z.getLocation(),PotionEffectType.INSTANT_DAMAGE,"RED",25,20,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.INSTANT_DAMAGE,"RED",10,20,30,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.SLOWNESS,"PURPLE",35,15,20,3);
					 
					  
				}if(mobname.contains("Zombi Super Suicida")) {
					
					for(int i = 0; i<4;i++) {
					  	TNTPrimed ptnt = (TNTPrimed) z.getWorld().spawnEntity(z.getLocation().add(0.5,0,0.5),EntityType.TNT);
						ptnt.setFuseTicks(0);
						ptnt.setSource(z);
						ptnt.setCustomName(ChatColor.DARK_GREEN+"Explosivo Zombi");
						ptnt.setYield(7);
					}

					
			  
			  }if(mobname.contains("Puas")) {
			    	
				  for(int i = 0;i<10;i++) {
						Location loc = z.getLocation();
						
						
						
						Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
						aw.setVelocity(loc.getDirection().add(new Vector(RandomV(),0.3,RandomV())).multiply(3));
						aw.setCritical(true);
						aw.setKnockbackStrength(2);
						aw.setFireTicks(1200);
						aw.setShooter(z);
						//((Arrow) h1).setShooter(player);
					}
			}
		  }
	}
	 
	//CUANDO EL ZOMBI ATACA
	@SuppressWarnings("removal")
	public void getZombiettack(Entity atacante ,Entity atacada) {
		 if(atacante instanceof Zombie && atacada instanceof Player) {
			 Player player = (Player) atacada;
			 Zombie z = (Zombie) atacante; 
			 if(z.getCustomName() == null) return;
			  
			 String mobname = ChatColor.stripColor(z.getCustomName());
			  
			  if(mobname.contains("Goliath") || mobname.contains("Tank")) {
				  player.setVelocity(player.getLocation().getDirection().multiply(-3).setY(2));
			  
			  }if(mobname.contains("VIRUS")) {
				  	PotionEffect poison = new PotionEffect(PotionEffectType.POISON,/*duration*/ 30*20,/*amplifier:*/10, false ,false,true );

				  player.addPotionEffect(poison);
			  }if(mobname.contains("HARDCORE VIRUS")) {
				  	PotionEffect poison = new PotionEffect(PotionEffectType.POISON,/*duration*/ 25*20,/*amplifier:*/50, false ,false,true );
				  	PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 15*20,/*amplifier:*/50, false ,false,true );

				    player.addPotionEffect(poison);
				    player.addPotionEffect(hunger);
			  } if(mobname.contains("DROPPER")) {
				  GameIntoMap gim = new GameIntoMap(plugin);
				  gim.PlayerDropAllItems(player);
				  player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Dropper", ChatColor.YELLOW+"Un Dropper te a tocado.", 20, 40, 20);
				  player.sendMessage(ChatColor.RED+"");
				  player.setVelocity(player.getLocation().getDirection().multiply(-1).setY(2));
			  
			  }if(mobname.contains("Zombi Super Suicida")) {
					
					for(int i = 0; i<4;i++) {
					  	TNTPrimed ptnt = (TNTPrimed) z.getWorld().spawnEntity(z.getLocation().add(0.5,0,0.5),EntityType.TNT);
						ptnt.setFuseTicks(0);
						//ptnt.setSource(z);
						ptnt.setCustomName(ChatColor.DARK_GREEN+"Explosivo Zombi");
						ptnt.setYield(7);
					}

			  
			  }if(mobname.contains("Zombi Puas")) {
			    	
				  for(int i = 0;i<10;i++) {
						Location loc = z.getLocation();
						
						
						
						Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
						aw.setVelocity(loc.getDirection().add(new Vector(RandomV(),0.3,RandomV())).multiply(3));
						aw.setCritical(true);
						aw.setKnockbackStrength(2);
						aw.setFireTicks(1200);
						aw.setShooter(z);
						//((Arrow) h1).setShooter(player);
					}
			}
					
			 
		 }
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
	
	
 	public void AreaPotion(Entity e,Location l,PotionEffectType type,String color,int radius ,int duration,int effectduration, int amplifier) {
		//System.out.println("TYPE: "+type.toString());
 		PotionEffect effect = new PotionEffect(type,/*duration*/ effectduration*20,/*amplifier:*/amplifier, false ,false,true );
 		Map<String,Color> typecolor = new HashMap<>();
 		typecolor.put("RED", Color.RED);
 		typecolor.put("AQUA", Color.AQUA);
 		typecolor.put("BLACK", Color.BLACK);
 		typecolor.put("BLUE", Color.BLUE);
 		typecolor.put("FUCHSIA", Color.FUCHSIA);
 		typecolor.put("GRAY", Color.GRAY);
 		typecolor.put("GREEN", Color.GREEN);
 		typecolor.put("LIME", Color.LIME);
 		typecolor.put("MAROON", Color.MAROON);
 		typecolor.put("NAVY", Color.NAVY);
 		typecolor.put("OLIVE", Color.OLIVE);
 		typecolor.put("ORANGE", Color.ORANGE);
 		typecolor.put("PURPLE", Color.PURPLE);
 		typecolor.put("SILVER", Color.SILVER);
 		typecolor.put("TEAL", Color.TEAL);
 		typecolor.put("WHITE", Color.WHITE);
 		typecolor.put("YELLOW", Color.YELLOW);
 	
 		
		AreaEffectCloud aec = (AreaEffectCloud) l.getWorld().spawnEntity(l,  EntityType.AREA_EFFECT_CLOUD);
		aec.addCustomEffect(effect, true);
		//aec.setBasePotionType(type);
		aec.setCustomName(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"GAS TOXICO");
		aec.setColor(typecolor.get(color));
		aec.setDuration(duration*20);
		aec.setRadius(radius);
		aec.setReapplicationDelay(5*20);
//		aec.setDurationOnUse(1);
//		aec.setRadiusOnUse(0.1f);
		//aec.setRadiusPerTick(aec.getRadiusPerTick()-(15*20));
		
		//aec.setParticle(Particle.SPELL);
		
		
				
	}
	
 	//en limbo
	public void ShootAgainstEntity(Entity attacker ,Entity target) {
		
		
		Location pl = target.getLocation();
		Location sl = new Location(Bukkit.getWorld(pl.getWorld().getName()), pl.getX(), pl.getY()+1, pl.getZ());
	
	
		
		Arrow aw = (Arrow)  attacker.getLocation().getWorld().spawnEntity(attacker.getLocation().add(0,1,0), EntityType.ARROW);
		
		
		//LINE BETWEEN TO ENTITYS 
		Vector v = sl.toVector().subtract(aw.getLocation().toVector());
		Location en = aw.getLocation();
		en.setDirection(v);
		aw.teleport(en);
		
		aw.setVelocity(aw.getLocation().getDirection().multiply(6));
		aw.setCritical(true);
		aw.setFireTicks(1200);
		//aw.setKnockbackStrength(10);
		aw.setCustomName("Torreta Anti Aerea");
		aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
	}
	
	
	
	public void spawnManualBabyZombi(Location loc) {
		
		Location l = loc.add(0.5,1,0.5);
		
		PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );
		PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );

		Attribute attribute = Attribute.FOLLOW_RANGE;
		
	
		ZombieVillager zv1 = (ZombieVillager) l.getWorld().spawnEntity(l, EntityType.ZOMBIE_VILLAGER);
		zv1.setBaby();
	
		zv1.getAttribute(attribute).setBaseValue(150);
		
		
		
		Zombie zv2 = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		zv2.setBaby();
		
		zv2.getAttribute(attribute).setBaseValue(150);
		
		
		Drowned zv3 = (Drowned) l.getWorld().spawnEntity(l, EntityType.DROWNED);
		zv3.setBaby();
		
		zv3.getAttribute(attribute).setBaseValue(150);
		
		
		
		Husk husk = (Husk) l.getWorld().spawnEntity(l, EntityType.HUSK);	
		husk.setBaby();
		
		husk.getAttribute(attribute).setBaseValue(150);
		
	
		
			zv1.addPotionEffect(rapido);
			zv1.addPotionEffect(salto);
			zv2.addPotionEffect(rapido);
			zv2.addPotionEffect(salto);
			zv3.addPotionEffect(rapido);
			zv3.addPotionEffect(salto);
			husk.addPotionEffect(rapido);
			husk.addPotionEffect(salto);
		
		
		
		
		
	}
	
	public void spawnManualZombi(Location loc) {
		
		Location l = loc.add(0.5,1,0.5);
		
		
		PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );
		PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );

		Attribute attribute = Attribute.FOLLOW_RANGE;
		
	
		ZombieVillager zv1 = (ZombieVillager) l.getWorld().spawnEntity(l, EntityType.ZOMBIE_VILLAGER);
	
		
		zv1.getAttribute(attribute).setBaseValue(150);
		
		
		Zombie zv2 = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		
		zv2.getAttribute(attribute).setBaseValue(150);
		
		
		Drowned zv3 = (Drowned) l.getWorld().spawnEntity(l, EntityType.DROWNED);
		
		zv3.getAttribute(attribute).setBaseValue(150);
		
		
		
		Husk husk = (Husk) l.getWorld().spawnEntity(l, EntityType.HUSK);	
		
		husk.getAttribute(attribute).setBaseValue(150);
		
		
		
			zv1.addPotionEffect(rapido);
			zv1.addPotionEffect(salto);
			zv2.addPotionEffect(rapido);
			zv2.addPotionEffect(salto);
			zv3.addPotionEffect(rapido);
			zv3.addPotionEffect(salto);
			husk.addPotionEffect(rapido);
			husk.addPotionEffect(salto);
		
		
	

	}
	
	public void spawnEliteZombi(Location loc) {
		
		Location l = loc.add(0.5,1,0.5);
		Attribute attribute = Attribute.FOLLOW_RANGE;

		Zombie zombi1 = (Zombie)  l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		Zombie zombi2 = (Zombie)  l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		Zombie zombi3 = (Zombie)  l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		Zombie zombi4 = (Zombie)  l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		
		zombi1.setCustomName(""+ChatColor.DARK_RED+ChatColor.BOLD+"Tank Infectado");
		zombi2.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Soldado Infectado");
		zombi3.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Elite Infectado");
		zombi4.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"Superviviente Infectado");
		zombi1.setCustomNameVisible(true);
		zombi2.setCustomNameVisible(true);
		zombi3.setCustomNameVisible(true);
		zombi4.setCustomNameVisible(true);
	
		
		
		
		PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );
		PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );

		
		zombi1.getAttribute(attribute).setBaseValue(150);
		zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		
		
		
		
		
		zombi2.getAttribute(attribute).setBaseValue(150);
		zombi2.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
		zombi2.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
		zombi2.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		zombi2.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		zombi2.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
		
		
		
		zombi3.getAttribute(attribute).setBaseValue(150);
		zombi3.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
		zombi3.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
		zombi3.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
		zombi3.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
		zombi3.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
		
		
		
		zombi4.getAttribute(attribute).setBaseValue(150);
		zombi4.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
		zombi4.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		zombi4.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		zombi4.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
		zombi4.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
		
		
			zombi1.addPotionEffect(rapido);
			zombi1.addPotionEffect(salto);
			zombi2.addPotionEffect(rapido);
			zombi2.addPotionEffect(salto);
			zombi3.addPotionEffect(rapido);
			zombi3.addPotionEffect(salto);
			zombi4.addPotionEffect(rapido);
			zombi4.addPotionEffect(salto);	
		

		
	}
	
	//TODO ZOMBI
    public void zombisSpecials(Location l) {
    	
    	
    		
				Random random = new Random();
				Attribute attribute = Attribute.FOLLOW_RANGE;
				Attribute attributesp = Attribute.SPAWN_REINFORCEMENTS;

				
				int n = random.nextInt(50);
				
				Location l2 = l.add(0.5,1,0.5);
				World world = l2.getWorld();
			
				Zombie zombi = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);

				zombi.getAttribute(attribute).setBaseValue(150);
				zombi.getAttribute(attributesp).setBaseValue(50);
				
				
				PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );
				PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );

				
			    zombi.addPotionEffect(rapido);
				zombi.addPotionEffect(salto);
				
	
				
			     	
				if(n == 0) {
					ZombieVillager zv = (ZombieVillager)  world.spawnEntity(l2, EntityType.ZOMBIE_VILLAGER);
					zv.getAttribute(attribute).setBaseValue(150);
				
					
					
					
					Drowned zombi8 = (Drowned) world.spawnEntity(l2, EntityType.DROWNED);
					
					
					zv.addPotionEffect(salto);
					zv.addPotionEffect(rapido);
					
					
				    zombi8.addPotionEffect(rapido);
				   
					zombi8.addPotionEffect(salto);
					zombi8.getAttribute(attribute).setBaseValue(150);
					
					Husk husk = (Husk) world.spawnEntity(l2, EntityType.HUSK);
					
					
					husk.addPotionEffect(rapido);
				
					husk.addPotionEffect(rapido);
				
					husk.getAttribute(attribute).setBaseValue(150);
					
					

				}
				
				if(n == 1) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);

				
					zombi1.getAttribute(attribute).setBaseValue(150);
					
	  				zombi1.addPotionEffect(rapido);
	  				zombi1.addPotionEffect(salto);
	  				
	  				
	  				
				}else if(n == 2) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);

					zombi1.setCustomName(""+ChatColor.DARK_RED+ChatColor.BOLD+"Tank");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
					zombi1.getAttribute(Attribute.MAX_HEALTH).setBaseValue(200);
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
					
				}else if(n == 3) {
				
					
					Zombie zombi4 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);


	  			
	  			    zombi4.addPotionEffect(rapido);
	  			    zombi4.addPotionEffect(salto);
	  				zombi4.getAttribute(attribute).setBaseValue(150);
	  			
	  				
				}else if(n == 4) {
					
					
		  		    
					Zombie zombi6 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
	  				
	  				zombi6.getAttribute(attribute).setBaseValue(150);
	  			    zombi6.addPotionEffect(rapido);
	  				zombi6.addPotionEffect(salto);
	  				zombi6.setBaby();
	  			
	  				LivingEntity entidad7 = (LivingEntity) world.spawnEntity(l2, EntityType.SKELETON);
	  				Skeleton zombi7 = (Skeleton) entidad7;
	  				
	  			    zombi7.addPotionEffect(rapido);
	  				zombi7.addPotionEffect(salto);
	  				zombi6.addPassenger(entidad7);
	  				zombi7.getAttribute(attribute).setBaseValue(150);
	  			
	  				
	  				
	  			
				}else if(n == 5) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Goliath");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
					
				}else if(n == 6) {
					
					for(int i = 0 ; i< 5;i++) {
						Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
						
							zombi1.getAttribute(attribute).setBaseValue(150);
							
			  				zombi1.addPotionEffect(rapido);
			  				zombi1.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}
				else if(n == 7) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					
						zombi1.getAttribute(attribute).setBaseValue(150);
						
		  				zombi1.addPotionEffect(rapido);
		  				zombi1.addPotionEffect(salto);
					for(int i = 0 ; i< 10;i++) {
						Zombie zombi2 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
						
							zombi2.getAttribute(attribute).setBaseValue(150);
							
			  				zombi2.addPotionEffect(rapido);
			  				zombi2.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}else if(n == 8) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Armado");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
					
				}else if(n == 9) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Artillero");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getAttribute(Attribute.MAX_HEALTH).setBaseValue(50);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.CROSSBOW));
					
				}else if(n == 10) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Super Suicida");
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.TNT));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.TNT));
					
					
					
					Creeper s = (Creeper) world.spawnEntity(l2, EntityType.CREEPER);
					s.setExplosionRadius(10);
					s.setMaxFuseTicks(1);
					s.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Zombi Super Suicida");
					s.getAttribute(attribute).setBaseValue(150);
					zombi1.addPassenger(s);
				}else if(n == 11) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));
					
					
					
					Blaze s = (Blaze) world.spawnEntity(l2, EntityType.BLAZE);
					s.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
					s.getAttribute(attribute).setBaseValue(150);
					zombi1.addPassenger(s);
				}else if(n == 12) {
					
					for(int i = 0 ; i< 15;i++) {
						Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
						
							zombi1.getAttribute(attribute).setBaseValue(150);
							zombi1.setBaby();
			  				zombi1.addPotionEffect(rapido);
			  				zombi1.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}else if(n == 13) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"Speed");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
			
				}else if(n == 14) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"Summon");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
			
				}else if(n == 15) {
				
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"Screamer");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
				
					
				}else if(n == 16) {
					
					
					Zombie zombi1 = (Zombie) world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.GOLD+"NEMESIS");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
					
			
				}else if(n == 17) {
				
					Zombie zombi1 = (Zombie) world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"VIRUS");
					zombi1.getEquipment().setHelmet(new ItemStack(Material.GREEN_CONCRETE));
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);

					
			
				}else if(n == 17) {
				
					Zombie zombi1 = (Zombie) world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"HARDCORE VIRUS");
					zombi1.getEquipment().setHelmet(new ItemStack(Material.RED_CONCRETE));
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);

					
			
				}else if(n == 18) {
					
				
					
					Zombie zombi1 = (Zombie) world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"DROPPER");
					zombi1.getEquipment().setHelmet(new ItemStack(Material.DROPPER));
					zombi1.getEquipment().setHelmet(new ItemStack(Material.REDSTONE_BLOCK));
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
			
				}
				
				/*
				 else if(n == 7) {
					
					
		  		    
					 WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
						CustomZombi giant = new CustomZombi(player.getLocation());
						
						world.addEntity(giant);
	  			
				}	
				*/
				
				
					
		
    	return;
    }
    
    
	//TODO Creeper
    public void Blaze(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		Blaze s = (Blaze) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.BLAZE);
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	
	//TODO Creeper
    public void Creeper(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		
		Creeper s = (Creeper) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.CREEPER);
		s.setExplosionRadius(10);
		s.setMaxFuseTicks(3);
		s.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"SUICIDA");
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
    }

	//TODO evoker
    public void villagerz(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		
		ZombieVillager s = (ZombieVillager) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE_VILLAGER);
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void drowned(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		Drowned s = (Drowned) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.DROWNED);
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void husk(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		
		Husk s = (Husk) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.HUSK);
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void evoker(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		Evoker s = (Evoker) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.EVOKER);
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	
	//TODO pillager
    public void Pillager(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		
		Pillager s = (Pillager) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.PILLAGER);
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		ItemStack b = new ItemStack(Material.CROSSBOW,1);
		ItemMeta meta = b.getItemMeta();
		meta.addEnchant(Enchantment.QUICK_CHARGE, 5, true);
		meta.addEnchant(Enchantment.MULTISHOT, 1, true);
		b.setItemMeta(meta);
		
		s.getEquipment().setItemInMainHand(b);
		
		
    }
    
  
	
	//TODO vindicator
    public void vindicador(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		
		Vindicator s = (Vindicator) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.VINDICATOR);
		
		Random random = new Random();

		int n = random.nextInt(50);
		
		if(n == 0) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		}
		
		if(n == 5) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
		}
		
		if(n == 10) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
		}
		
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		s.setCanPickupItems(true);
		
		
    }
	
	//TODO SkeletomDARK
    public void skeletonDark(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
	
		WitherSkeleton s = (WitherSkeleton) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.WITHER_SKELETON);
		s.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		s.setCanPickupItems(true);
		
		
    }
	
	//TODO Skeletom
    public void skeleton(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
    	Random random = new Random();

		int n = random.nextInt(50);

		Skeleton s = (Skeleton) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
		s.setCustomName(ChatColor.DARK_PURPLE+"ARQUERO");
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		s.setCanPickupItems(true);
		
		
		if(n == 0) {
			Skeleton s1 = (Skeleton) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
			s1.setCustomName(ChatColor.DARK_PURPLE+"ARQUERO");
			s1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
			s1.setCanPickupItems(true);
			for(int i = 0;i<5;i++) {
				Skeleton s2 = (Skeleton) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
				s2.setCustomName(ChatColor.DARK_PURPLE+"ARQUERO");
				s2.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
				s2.setCanPickupItems(true);
				s1.addPassenger(s2);
			}
		}
		
		
		
		if(n == 10) {
			Skeleton s1 = (Skeleton) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
			s1.setCustomName(ChatColor.DARK_PURPLE+"ARQUERO");
			s1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		}
		
    }
	
  //TODO Bruja
    public void Bruja(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
	
		Witch s = (Witch) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.WITCH);
		s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO ZOMBI
    public void zombis(World world , int x , int y , int z) {
    	
    	
    		
				Random random = new Random();
			
				
				int n = random.nextInt(50);
				
				
				 Attribute attribute = Attribute.FOLLOW_RANGE;
				 Attribute attributer = Attribute.SPAWN_REINFORCEMENTS;

				Location l2 = new Location(world, x, y+2, z); 			
			
				Zombie zombi = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);

				zombi.getAttribute(attribute).setBaseValue(150);
				zombi.getAttribute(attributer).setBaseValue(50);
				
				
				PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/4, false ,false,true );
				PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/5, false ,false,true );

				
			    zombi.addPotionEffect(rapido);
				zombi.addPotionEffect(salto);
				
	
				
			     	
				if(n == 0) {
					ZombieVillager zv = (ZombieVillager)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE_VILLAGER);
					zv.getAttribute(attribute).setBaseValue(150);
				
					
					
					
					Drowned zombi8 = (Drowned) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.DROWNED);
					
					
					zv.addPotionEffect(salto);
					zv.addPotionEffect(rapido);
					
					
				    zombi8.addPotionEffect(rapido);
				   
					zombi8.addPotionEffect(salto);
					zombi8.getAttribute(attribute).setBaseValue(150);
					
					Husk husk = (Husk) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.HUSK);
					
					
					husk.addPotionEffect(rapido);
				
					husk.addPotionEffect(rapido);
				
					husk.getAttribute(attribute).setBaseValue(150);
					
					

				}
				
				if(n == 1) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);

				
					zombi1.getAttribute(attribute).setBaseValue(150);
					
	  				zombi1.addPotionEffect(rapido);
	  				zombi1.addPotionEffect(salto);
	  				
	  				
	  				
				}else if(n == 2) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);

					zombi1.setCustomName(""+ChatColor.DARK_RED+ChatColor.BOLD+"Tank");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
					
				}else if(n == 3) {
				
					
					Zombie zombi4 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);


	  			
	  			    zombi4.addPotionEffect(rapido);
	  			    zombi4.addPotionEffect(salto);
	  				zombi4.getAttribute(attribute).setBaseValue(150);
	  			
	  				
				}else if(n == 4) {
					
					
		  		    
					Zombie zombi6 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
	  				
	  				zombi6.getAttribute(attribute).setBaseValue(150);
	  			    zombi6.addPotionEffect(rapido);
	  				zombi6.addPotionEffect(salto);
	  				zombi6.setBaby();
	  			
	  				LivingEntity entidad7 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
	  				Skeleton zombi7 = (Skeleton) entidad7;
	  				
	  			    zombi7.addPotionEffect(rapido);
	  				zombi7.addPotionEffect(salto);
	  				zombi6.addPassenger(entidad7);
	  				zombi7.getAttribute(attribute).setBaseValue(150);
	  			
	  				
	  				
	  			
				}else if(n == 5) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Goliath");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getAttribute(Attribute.MAX_HEALTH).setBaseValue(100);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
					
				}else if(n == 6) {
					
					for(int i = 0 ; i< 5;i++) {
						Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
						
							zombi1.getAttribute(attribute).setBaseValue(150);
							
			  				zombi1.addPotionEffect(rapido);
			  				zombi1.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}
				else if(n == 7) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					
						zombi1.getAttribute(attribute).setBaseValue(150);
						
		  				zombi1.addPotionEffect(rapido);
		  				zombi1.addPotionEffect(salto);
					for(int i = 0 ; i< 10;i++) {
						Zombie zombi2 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
						
							zombi2.getAttribute(attribute).setBaseValue(150);
							
			  				zombi2.addPotionEffect(rapido);
			  				zombi2.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}else if(n == 8) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Armado");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
					
				}else if(n == 9) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Artillero");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.CROSSBOW));
					  
				}else if(n == 10) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Super Suicida");
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.TNT));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.TNT));
					
					
					
					Creeper s = (Creeper) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.CREEPER);
					s.setExplosionRadius(10);
					s.setMaxFuseTicks(0);
					s.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Zombi Super Suicida");
					s.getAttribute(attribute).setBaseValue(150);
					zombi1.addPassenger(s);
				}else if(n == 11) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));
					
					
					for(int i = 0; i<5;i++) {
						Blaze s = (Blaze) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.BLAZE);
						s.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
						s.getAttribute(attribute).setBaseValue(150);
						zombi1.addPassenger(s);
					}
					
				}else if(n == 12) {
					
					for(int i = 0 ; i< 15;i++) {
						Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
						
							zombi1.getAttribute(attribute).setBaseValue(150);
							zombi1.setBaby();
			  				zombi1.addPotionEffect(rapido);
			  				zombi1.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}else if(n == 13) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"Speed");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
			
				}else if(n == 14) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"Summon");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
			
				}else if(n == 15) {
				
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"Screamer");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
				
					
				}else if(n == 15) {
				
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"Screamer");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
				
					
				}else if(n == 16) {
					
					PotionEffect rapido2 = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/6, false ,false,true );
					PotionEffect salto2= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/6, false ,false,true );
					
					Zombie zombi1 = (Zombie) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.GOLD+"NEMESIS");
					
					
					zombi1.addPotionEffect(rapido2);
					zombi1.addPotionEffect(salto2);
					zombi1.getAttribute(Attribute.MAX_HEALTH).setBaseValue(200);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
					
			
				}else if(n == 17) {
				
					Zombie zombi1 = (Zombie) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"VIRUS");
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);

					
			
				}else if(n == 18) {
					
				
					
					Zombie zombi1 = (Zombie) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"DROPPER");
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
			
				}else if(n == 19) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Zombi Puas");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.CROSSBOW));
					
				}else if(n == 19) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Leader");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(attribute).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.CROSSBOW));
					
				}
				
				/*
				 else if(n == 7) {
					
					
		  		    
					 WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
						CustomZombi giant = new CustomZombi(player.getLocation());
						
						world.addEntity(giant);
	  			
				}	
				*/
				
				
					
		
    	return;
    }


    
    
	//TODO NERBYBLOCK
	public void spawnMobs(Player player) {
		
		if(player.getGameMode() != GameMode.ADVENTURE) return;
		
	
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		int rango = gi.getSpawnMobRange();
		
		List<Location> l = gi.getMobsGenerators();
		
		if(!l.isEmpty()) {
			for(Location loc : l) {
				
				if(player.getLocation().distance(loc) <= rango) {
				
				Block a = loc.getBlock();
				Block b = a.getRelative(0,-1, 0);
				
				if(a.getType() == Material.GREEN_CONCRETE && b.getType() == Material.BEDROCK) {
					
					zombis(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.BLUE_CONCRETE && b.getType() == Material.BEDROCK) {
					
					drowned(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.ORANGE_CONCRETE && b.getType() == Material.BEDROCK) {
					
					husk(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.LIGHT_GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
					
					villagerz(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.WHITE_CONCRETE && b.getType() == Material.BEDROCK) {
					
					skeleton(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.BLACK_CONCRETE && b.getType() == Material.BEDROCK) {
					skeletonDark(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
					vindicador(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.RED_CONCRETE && b.getType() == Material.BEDROCK) {
					Pillager(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.PURPLE_CONCRETE && b.getType() == Material.BEDROCK) {
					evoker(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.LIME_CONCRETE && b.getType() == Material.BEDROCK) {
					Creeper(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				
				if(a.getType() == Material.MAGENTA_CONCRETE && b.getType() == Material.BEDROCK) {
					Bruja(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
				}
				detectBlockAndShoot(player,loc,gi.getDispenserRange());
			  }	
			}
		}
	}
	
	//TODO NERBYBLOCK
		public void spawnOres(Player player) {
			
			if(player.getGameMode() != GameMode.ADVENTURE) return;
			
		
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
			int rango = gi.getSpawnItemRange();
			
			List<Location> l = gi.getGenerators();
			
			if(!l.isEmpty()) {
				for(Location loc : l) {
					
					if(player.getLocation().distance(loc) <= rango) {
					
					Block a = loc.getBlock();
					Block b = a.getRelative(0,-1, 0);
					
					
					if(a.getType() == Material.NETHERITE_BLOCK && b.getType() == Material.BEDROCK) {
						
						
						 player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,(float) Math.max(0, 20 - player.getLocation().distance(loc) + 1), 1F  );
						 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_INGOT));
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_BLOCK,1));
						 
					}
					
					if(a.getType() == Material.DIAMOND_BLOCK && b.getType() == Material.BEDROCK) {
						 player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,(float) Math.max(0, 20 - player.getLocation().distance(loc) + 1), 1F  );
						 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
					     a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND));
					     a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND_BLOCK,1));
					}
					
					if(a.getType() == Material.EMERALD_BLOCK && b.getType() == Material.BEDROCK) {
						 player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,(float) Math.max(0, 20 - player.getLocation().distance(loc) + 1), 1F  );
						 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD));
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD_BLOCK,1));
					}
					
					if(a.getType() == Material.IRON_BLOCK && b.getType() == Material.BEDROCK) {
						 player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,(float) Math.max(0, 20 - player.getLocation().distance(loc) + 1), 1F  );
						 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_INGOT));
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_BLOCK,1));
					}
					
					if(a.getType() == Material.GOLD_BLOCK && b.getType() == Material.BEDROCK) {
						 player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,(float) Math.max(0, 20 - player.getLocation().distance(loc) + 1), 1F  );
						 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_INGOT));
						 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_BLOCK,1));
					}
					
				  }	
				}
			}
		}
	
		
		 
		    @SuppressWarnings("removal")
			public void shootEntityToPlayer(Player player) {
		    	 
		    	List<Entity> l = getNearbyEntities(player.getLocation(),150);
		    	
		    	if(!l.isEmpty()) {
		    		for(Entity e : l) {
		    			if(e instanceof Zombie) {
		    				Zombie z = (Zombie) e;
		    				
		    				if(z.getTarget() != null) {
		    					if(z.getTarget() instanceof Player) {
		    						Player pla = (Player) z.getTarget();
		    						if(!pla.getName().equals(player.getName())) {
		    							return;
		    						}
		    					}
		    				}
		    				
		    				
		    				RayTraceResult rt = z.getWorld().rayTraceEntities(z.getEyeLocation().add(z.getLocation().getDirection()),z.getLocation().getDirection() , 100.0D);
		              		if(rt != null && rt.getHitEntity() != null) {
		              			
		              			if(z.getCustomName() == null) {
		              				return;
		              			}
		              			
		              			if(ChatColor.stripColor(z.getCustomName()).equals("Zombi Artillero")) {
		              				if(rt.getHitEntity().getType() == EntityType.PLAYER) {
		                  				
		              					Location loc = z.getLocation();
		            					Location loc2 = z.getLocation();
		        						
		        						Arrow aw = (Arrow) z.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
		        						Arrow aw2 = (Arrow) z.getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.ARROW);
		        						aw.setCritical(true);
		        						aw.setKnockbackStrength(2);
		        						aw.setFireTicks(1200);
		        						aw.setShooter(z);
		        						aw.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
		        						aw2.setCritical(true);
		        						aw2.setKnockbackStrength(2);
		        						aw2.setFireTicks(1200);
		        						aw2.setShooter(z);
		        						aw2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
		                  			}
		              			}
		              			if(ChatColor.stripColor(z.getCustomName()).equals("NEMESIS")) {
		              				if(rt.getHitEntity().getType() == EntityType.PLAYER) {
		                  				for(int i = 0 ; i < 10;i++) {
		                  					SpawnArrowsFireMob(e,randomPosOrNeg(10),randomPosOrNeg(5));
		                  				}
		              					
		                  			}
		              			}
		              			
		              		}
		              		
		    			}
		    			
		    			
		    		}
		    	}
		    
		    	
		    }
		    
		    public void moblandmine(Player player) {
		    	List<Entity> l = getNearbyEntities(player.getLocation(),150);
		    	
		    	if(!l.isEmpty()) {
		    		for(Entity e : l) {
		    			if(e.getType() == EntityType.ARMOR_STAND) continue;
		    			if(e instanceof LivingEntity && !(e instanceof Player)) {
		    				Block block = e.getLocation().getBlock();
		    				Block r = block.getRelative(0, 0, 0);
		    				if(r.getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE || r.getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
		    					TNTPrimed ptnt = (TNTPrimed) player.getWorld().spawnEntity(e.getLocation().add(0.5,0,0.5),EntityType.TNT);
		    					ptnt.setFuseTicks(0);
		    					ptnt.setCustomName(ChatColor.DARK_PURPLE+"Mina Explosiva");
		    					ptnt.setYield(5);
		    					ptnt.setIsIncendiary(true);
		    				}
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
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setKnockbackStrength(2);
				aw.setFireTicks(1200);
				aw.setShooter((ProjectileSource) e);
			}
		    
		    
			  public void mobLocation(Player player) {
				  	GameConditions gc = new GameConditions(plugin);
			    	List<Entity> entities = getNearbyEntities(player.getLocation(), 50);
			    	for(int i = 0;i< entities.size();i++) {
			    		if(!(entities.get(i) instanceof LivingEntity))continue;
			    		
			    		LivingEntity lve = (LivingEntity) entities.get(i);
			    		Block block = lve.getLocation().getBlock();
			    		Block r = block.getRelative(0, 0, 0);
			    		
			    		if(lve.getType() == EntityType.PLAYER) continue;
			    		gc.turret(lve);
			    		gc.blockPotion(lve);
			    		if(r.getType() == Material.OAK_PRESSURE_PLATE) {
			    			lve.setVelocity(lve.getLocation().getDirection().multiply(3).setY(2));
			    		}
			    		if(r.getType() == Material.STONE_PRESSURE_PLATE) {
			    			lve.setVelocity(lve.getLocation().getDirection().multiply(3).setY(1));
			    		}
			    		
			    	}
			    }
			  
		 public void removeTrapEntitys(Player player) {
			    	List<Entity> entities = getNearbyEntities(player.getLocation(), 50);
			    	List<Entity> drops = new ArrayList<>();
			    	
			    		for(Entity i : entities) {
			    			if(i.getType() == EntityType.SNOW_GOLEM || i.getType() == EntityType.IRON_GOLEM) {
			     			   Block block = i.getLocation().getBlock();
			     			   Block r = block.getRelative(0, -1, 0);
			     			   if(r.getType() == Material.BARRIER) {
			     				   i.remove();
			 					}
			    			}else if(i.getType() == EntityType.ITEM) {
			    				drops.add(i);
			    			}
			    		}
			    		
			    	if(drops.size() >= 50) {
			    		for(Entity i : drops) {
			    			i.remove();
			    		}
			    	}
			    	
			    	
			    }
			    
			    
		public void removeEntitysInBarrier(Player player) {
			    	
			    	List<Entity> l = getNearbyEntities(player.getLocation(),150);
			    	
			    	if(!l.isEmpty()) {
			    		for(Entity e : l) {
			    	    	
			        		Block b = e.getLocation().getBlock();
			        		Block block = b.getRelative(0,-1,0);
			        		
			        		if(e.getType() != EntityType.PLAYER && block.getType() == Material.BARRIER) {
			        			if(e instanceof Mob) {
			        				((Mob) e).setHealth(0);
			        			}else {
			        				e.remove();
			        			}
			        			
			        		}
			        	}
			    	}
			    
			    	
			    }
			    
		 public void removeEntitysAfterGame(Player player) {
			    	
			    	List<Entity> l = getNearbyEntities(player.getLocation(),150);
			    	
			    	if(!l.isEmpty()) {
			    		for(Entity e : l) {
			        		
			        		if(e instanceof Monster || e instanceof Villager || e instanceof Golem || e instanceof Item) {
			        			if(e.getType() == EntityType.ITEM) {
			        				Item ite = (Item) e;
			        				if(ite.getOwner() != null) continue;
			        					e.remove();
			        			    }else {
			        			    	if(e.getType() != EntityType.ARMOR_STAND) {
			        			    		e.remove();
			        			    	}
			        			    }
			        			
			        			
			        		}
			        	} 
			    	}
			    
			    	
		}
    
		 
	 public double RandomV() {
				return Math.random() * 2 - 1;
	}
		 
	public int randomPosOrNeg(int i){
				Random r = new Random();
				int v = r.nextInt(i+1);
				//genera aleatoriedad si es 0 devuelve el valor como tal si es 1 devuelve un valor positivo o negativo
				int r2 = r.nextInt(1+1);
				if(r2 == 1){
					return v;
				}
				return transformPosOrNeg(v);
	}
			
	public int transformPosOrNeg(int i){
				return i =  (~(i -1));
	}	 
		 
	public int randomBetweenValue(int min ,int max) {
		
		try {
				Random r = new Random();
				int value = r.nextInt(max-min+1) + min;
				return value;
			}catch(IllegalArgumentException e) {
				Logger logger = Logger.getLogger(MobsActions.class.getName());
		       	 logger.log(Level.SEVERE,"Coloca primero un valor menor y despues un mayor. "+max+"/"+min);
			}
			System.out.println("F");
			return -1;
			
		}
    
	
}
