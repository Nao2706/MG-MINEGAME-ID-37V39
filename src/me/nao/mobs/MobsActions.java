package me.nao.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobsActions {

	
	
	
	public void getAttackedZombie(Entity atacante ,Entity atacada) {
		
		
		  if(atacante instanceof Player && atacada instanceof Zombie) {
			  Player target = (Player) atacante;
			  Zombie z = (Zombie) atacada;
			  
			  if(z.getCustomName() == null) {
				  return;
			  }
			  
			  
			  	PotionEffect jump = new PotionEffect(PotionEffectType.JUMP,/*duration*/ 99999,/*amplifier:*/5, false ,false,true );
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
        					cre.addPotionEffect(jump);
        				}
        			}
			   
				}if(z.getCustomName().contains(ChatColor.RED+"VIRUS")) {
				  AreaPotion(z,z.getLocation(),PotionEffectType.POISON,"GREEN",15,20,20,2);
				  AreaPotion(z,z.getLocation(),PotionEffectType.HARM,"RED",15,20,20,2);
				  
			    }if(z.getCustomName().contains(ChatColor.RED+"HARDCORE VIRUS")) {
			    	
					  AreaPotion(z,z.getLocation(),PotionEffectType.POISON,"GREEN",30,10,20,50);
					  AreaPotion(z,z.getLocation(),PotionEffectType.HARM,"RED",25,10,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.HARM,"RED",5,10,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.HARM,"RED",5,10,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.SLOW,"PURPLE",35,10,20,2);
					  AreaPotion(z,z.getLocation(),PotionEffectType.SLOW,"PURPLE",35,10,20,2);
					  
				 }if(z.getCustomName().contains(ChatColor.RED+"Summon")) {
					for(int i =0 ; i< 10;i++) {
						Location loc = z.getLocation();
		
						
						Zombie z1 = (Zombie) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ZOMBIE);
						z1.addPotionEffect(speed);
						z1.addPotionEffect(jump);
						
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
								z1.setCustomName(ChatColor.GOLD+target.getName()+ChatColor.GREEN+" soy su copia y lo mate xD");
								z1.addPotionEffect(speed);
								z1.addPotionEffect(jump);
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
	
	
	public void getZombiettack(Entity atacante ,Entity atacada) {
		 if(atacante instanceof Zombie && atacada instanceof Player) {
			 Player player = (Player) atacada;
			 Zombie z = (Zombie) atacante; 
			 if(z.getCustomName() == null) {
				  return;
			  }
			  
			  
			  if(z.getCustomName().contains(ChatColor.RED+"Goliath")) {
				  player.setVelocity(player.getLocation().getDirection().multiply(-3).setY(2));
			  
			  }if(z.getCustomName().contains(ChatColor.RED+"VIRUS")) {
				  	PotionEffect poison = new PotionEffect(PotionEffectType.POISON,/*duration*/ 30*20,/*amplifier:*/10, false ,false,true );

				  player.addPotionEffect(poison);
			  }if(z.getCustomName().contains(ChatColor.RED+"HARDCORE VIRUS")) {
				  	PotionEffect poison = new PotionEffect(PotionEffectType.POISON,/*duration*/ 25*20,/*amplifier:*/50, false ,false,true );
				  	PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 15*20,/*amplifier:*/50, false ,false,true );

				    player.addPotionEffect(poison);
				    player.addPotionEffect(hunger);
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
		aec.setDurationOnUse(20);
		aec.setRadiusOnUse(aec.getRadiusOnUse()-(15*20));
		//aec.setRadiusPerTick(aec.getRadiusPerTick()-(15*20));
		
		//aec.setParticle(Particle.SPELL);
		
		
				
	}
	
	
	
	
	
}
