package me.nao.mobs;

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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.nao.main.game.Minegame;
import me.nao.manager.GameIntoMap;


public class MobsActions {

	private Minegame plugin;
	
	public MobsActions(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	public void getAttackedZombie(Entity atacante ,Entity atacada) {
		
		
		  if(atacante instanceof Player && atacada instanceof Zombie) {
			  Player target = (Player) atacante;
			  Zombie z = (Zombie) atacada;
			  
			  if(z.getCustomName() == null) {
				  return;
			  }
			  
			  
			  	PotionEffect JUMP_BOOST = new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/5, false ,false,true );
				PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/5, false ,false,true );
			  if(z.getCustomName().contains(ChatColor.RED+"Screamer")) {
					
					
					List<Entity> list = getNearbyEntities(z.getLocation(),50);
					for(Entity ent : list) {
          				if(ent.getType() != EntityType.PLAYER && ent instanceof Monster) {
          					Monster cre = (Monster) ent;
          					//if(cre.getUniqueId().equals(z.getUniqueId())) continue;
          					cre.setTarget(target);
          				}
          			}
			   
				}if(z.getCustomName().contains(ChatColor.RED+"Speed")) {
				
					
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
        					cre.addPotionEffect(JUMP_BOOST);
        				}
        			}
			   
				}if(z.getCustomName().contains(ChatColor.RED+"Summon")) {
					for(int i =0 ; i< 10;i++) {
						Location loc = z.getLocation();
						Zombie z1 = (Zombie) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ZOMBIE);
						z1.addPotionEffect(speed);
						z1.addPotionEffect(JUMP_BOOST);
						
					}
					
					
					Random r = new Random();
				
					int rand = r.nextInt(10);
//					if(rand == 0) {
//						p.sendMessage(ChatColor.RED+" A ver si esto es de tu talla");
//					}
//					
//					if(rand == 1) {
//						p.sendMessage(ChatColor.RED+" A correr "+p.getName());
//					}
//					
//					if(rand == 2) {
//						p.sendMessage(ChatColor.RED+" Yo llamo refuerzos y tu?? ");
//					}
//					
//					if(rand == 3) {
//						p.sendMessage(ChatColor.RED+" Alguieen va a morirr ");
//					}
//					
//					if(rand == 4) {
//						p.sendMessage(ChatColor.RED+" Momento horda ");
//					}
//					
//					if(rand == 5) {
//						p.sendMessage(ChatColor.RED+" Nuke Moment no creo que escapes");
//						//player.getInventory().removeItem(new ItemStack(Material.TNT,1));
//						Location lm = new Location(p.getWorld(),p.getLocation().getBlockX(),256,p.getLocation().getBlockZ());
//						Location l = z.getLocation();
//						Entity fb = l.getWorld().spawnEntity(lm.add(0.5, 0, 0.5), EntityType.FIREBALL);
//						//fb.setCustomName("Mortero");
//						
//						Fireball f = (Fireball) fb;
//						f.setYield(10);
//						f.setDirection(new Vector (0,-3,0));
//						f.setVelocity(f.getDirection().multiply(3));
//						((Fireball) fb).setShooter(z);
//					}
					if(rand == 7) {
						target.sendMessage(ChatColor.RED+" Jutsu Clon de Sombra ");
						
							for(int i = 0 ; i < 3;i++) {
								Location loc = z.getLocation();
							
							
								Zombie z1 = (Zombie) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ZOMBIE);
								z1.setCustomName(ChatColor.RED+"Summon");
								z1.addPotionEffect(speed);
								z1.addPotionEffect(JUMP_BOOST);
							}
					}if(rand == 8) {
						target.sendMessage(ChatColor.RED+" Que tu copia te mate ");
						
						for(int i = 0 ; i < 3;i++) {
							
								ItemStack head = new ItemStack(Material.PLAYER_HEAD);
								SkullMeta meta = (SkullMeta) head.getItemMeta();
								meta.setOwningPlayer(target);
								Location loc = z.getLocation();
								
								Zombie z1 = (Zombie) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ZOMBIE);
								z1.setCustomName(ChatColor.GOLD+target.getName()+ChatColor.GREEN+" soy su copia y lo mate xD");
								z1.addPotionEffect(speed);
								z1.addPotionEffect(JUMP_BOOST);
								 ItemStack[] inv = target.getInventory().getArmorContents();
							        
								 	
							        //acciones
							        z1.getEquipment().setArmorContents(inv);
							        z1.getEquipment().setHelmet(head);
							       //z.setCustomName(c("&aMob equipado por "+player.getName()));
							        z1.getEquipment().setItemInMainHand(target.getInventory().getItemInMainHand());
						}
							
					}
			  }
		  }
		
	}
	
	public void virus(Entity atacante ,Entity atacada) {
		  if(atacante instanceof Player && atacada instanceof Zombie) {
			  Zombie z = (Zombie) atacada;
			  
			    if(z.getCustomName() == null) {
				  return;
			    }
			    if(z.getCustomName().contains(ChatColor.RED+"VIRUS")) {
				  AreaPotion(z,z.getLocation(),PotionEffectType.POISON,"GREEN",15,20,20,2);
				  AreaPotion(z,z.getLocation(),PotionEffectType.INSTANT_DAMAGE,"RED",15,20,20,2);
				  
			    }if(z.getCustomName().contains(ChatColor.RED+"HARDCORE VIRUS")) {
			    	
			    	  AreaPotion(z,z.getLocation(),PotionEffectType.HUNGER,"ORANGE",30,10,20,5);
					  AreaPotion(z,z.getLocation(),PotionEffectType.POISON,"GREEN",30,10,20,50);
					  AreaPotion(z,z.getLocation(),PotionEffectType.INSTANT_DAMAGE,"RED",25,10,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.INSTANT_DAMAGE,"RED",5,10,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.SLOWNESS,"PURPLE",35,10,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.SLOWNESS,"PURPLE",35,10,20,2);
					  
				 }
		  }
	}
	
	public void getZombiettack(Entity atacante ,Entity atacada) {
		 if(atacante instanceof Zombie && atacada instanceof Player) {
			 Player player = (Player) atacada;
			 Zombie z = (Zombie) atacante; 
			 if(z.getCustomName() == null) {
				  return;
			  }
			  
			  
			  if(z.getCustomName().contains(ChatColor.RED+"Goliath") || z.getCustomName().contains(ChatColor.RED+"Tank")) {
				  player.setVelocity(player.getLocation().getDirection().multiply(-3).setY(2));
			  
			  }if(z.getCustomName().contains(ChatColor.RED+"VIRUS")) {
				  	PotionEffect poison = new PotionEffect(PotionEffectType.POISON,/*duration*/ 30*20,/*amplifier:*/10, false ,false,true );

				  player.addPotionEffect(poison);
			  }if(z.getCustomName().contains(ChatColor.RED+"HARDCORE VIRUS")) {
				  	PotionEffect poison = new PotionEffect(PotionEffectType.POISON,/*duration*/ 25*20,/*amplifier:*/50, false ,false,true );
				  	PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 15*20,/*amplifier:*/50, false ,false,true );

				    player.addPotionEffect(poison);
				    player.addPotionEffect(hunger);
			  } if(z.getCustomName().contains(ChatColor.RED+"DROPPER")) {
				  GameIntoMap gim = new GameIntoMap(plugin);
				  gim.PlayerDropAllItems(player);
				  player.setVelocity(player.getLocation().getDirection().multiply(-3).setY(2));
			  
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

		
		
	
		ZombieVillager zv1 = (ZombieVillager) l.getWorld().spawnEntity(l, EntityType.ZOMBIE_VILLAGER);
		zv1.setBaby();
	
		zv1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
		
		Zombie zv2 = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		zv2.setBaby();
		
		zv2.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
		Drowned zv3 = (Drowned) l.getWorld().spawnEntity(l, EntityType.DROWNED);
		zv3.setBaby();
		
		zv3.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
		
		Husk husk = (Husk) l.getWorld().spawnEntity(l, EntityType.HUSK);	
		husk.setBaby();
		
		husk.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
	
		
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

		
		
	
		ZombieVillager zv1 = (ZombieVillager) l.getWorld().spawnEntity(l, EntityType.ZOMBIE_VILLAGER);
	
		
		zv1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
		Zombie zv2 = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		
		zv2.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
		Drowned zv3 = (Drowned) l.getWorld().spawnEntity(l, EntityType.DROWNED);
		
		zv3.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
		
		Husk husk = (Husk) l.getWorld().spawnEntity(l, EntityType.HUSK);	
		
		husk.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		
		
		
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

		
		zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		
		
		
		
		
		zombi2.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		zombi2.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
		zombi2.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
		zombi2.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		zombi2.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		zombi2.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
		
		
		
		zombi3.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		zombi3.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
		zombi3.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
		zombi3.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
		zombi3.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
		zombi3.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
		
		
		
		zombi4.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
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
			
				
				int n = random.nextInt(50);
				
				Location l2 = l.add(0.5,1,0.5);
				World world = l2.getWorld();
			
				Zombie zombi = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);

				zombi.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
				zombi.getAttribute(Attribute.SPAWN_REINFORCEMENTS).setBaseValue(50);
				
				
				PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );
				PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/randomBetweenValue(1, 5), false ,false,true );

				
			    zombi.addPotionEffect(rapido);
				zombi.addPotionEffect(salto);
				
	
				
			     	
				if(n == 0) {
					ZombieVillager zv = (ZombieVillager)  world.spawnEntity(l2, EntityType.ZOMBIE_VILLAGER);
					zv.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
				
					
					
					
					Drowned zombi8 = (Drowned) world.spawnEntity(l2, EntityType.DROWNED);
					
					
					zv.addPotionEffect(salto);
					zv.addPotionEffect(rapido);
					
					
				    zombi8.addPotionEffect(rapido);
				   
					zombi8.addPotionEffect(salto);
					zombi8.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					
					Husk husk = (Husk) world.spawnEntity(l2, EntityType.HUSK);
					
					
					husk.addPotionEffect(rapido);
				
					husk.addPotionEffect(rapido);
				
					husk.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					
					

				}
				
				if(n == 1) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);

				
					zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					
	  				zombi1.addPotionEffect(rapido);
	  				zombi1.addPotionEffect(salto);
	  				
	  				
	  				
				}else if(n == 2) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);

					zombi1.setCustomName(""+ChatColor.DARK_RED+ChatColor.BOLD+"Tank");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
					
				}else if(n == 3) {
				
					
					Zombie zombi4 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);


	  			
	  			    zombi4.addPotionEffect(rapido);
	  			    zombi4.addPotionEffect(salto);
	  				zombi4.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
	  			
	  				
				}else if(n == 4) {
					
					
		  		    
					Zombie zombi6 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
	  				
	  				zombi6.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
	  			    zombi6.addPotionEffect(rapido);
	  				zombi6.addPotionEffect(salto);
	  				zombi6.setBaby();
	  			
	  				LivingEntity entidad7 = (LivingEntity) world.spawnEntity(l2, EntityType.SKELETON);
	  				Skeleton zombi7 = (Skeleton) entidad7;
	  				
	  			    zombi7.addPotionEffect(rapido);
	  				zombi7.addPotionEffect(salto);
	  				zombi6.addPassenger(entidad7);
	  				zombi7.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
	  			
	  				
	  				
	  			
				}else if(n == 5) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Goliath");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
					
				}else if(n == 6) {
					
					for(int i = 0 ; i< 5;i++) {
						Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
						
							zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
							
			  				zombi1.addPotionEffect(rapido);
			  				zombi1.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}
				else if(n == 7) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					
						zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
						
		  				zombi1.addPotionEffect(rapido);
		  				zombi1.addPotionEffect(salto);
					for(int i = 0 ; i< 10;i++) {
						Zombie zombi2 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
						
							zombi2.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
							
			  				zombi2.addPotionEffect(rapido);
			  				zombi2.addPotionEffect(salto);
					}
				   
	  				
	  				
	  				
				}else if(n == 8) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Armado");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
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
				
					zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
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
				
					zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.TNT));
					
					
					
					Creeper s = (Creeper) world.spawnEntity(l2, EntityType.CREEPER);
					s.setExplosionRadius(10);
					s.setMaxFuseTicks(1);
					s.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Zombi Super Suicida");
					s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					zombi1.addPassenger(s);
				}else if(n == 11) {
					Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
					
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));
					
					
					
					Blaze s = (Blaze) world.spawnEntity(l2, EntityType.BLAZE);
					s.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
					s.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
					zombi1.addPassenger(s);
				}else if(n == 12) {
					
					for(int i = 0 ; i< 15;i++) {
						Zombie zombi1 = (Zombie)  world.spawnEntity(l2, EntityType.ZOMBIE);
						
							zombi1.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
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
					
					zombi1.addPotionEffect(rapido);
					zombi1.addPotionEffect(salto);

					
			
				}else if(n == 18) {
					
				
					
					Zombie zombi1 = (Zombie) world.spawnEntity(l2, EntityType.ZOMBIE);
					zombi1.setCustomName(ChatColor.RED+"DROPPER");
					
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
