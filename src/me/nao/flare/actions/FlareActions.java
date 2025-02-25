package me.nao.flare.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.nao.enums.Items;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.main.mg.Minegame;



public class FlareActions {
	
	private Minegame plugin;
	
	public FlareActions(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	public void SendAirDrop(Player player,Location l ) {
		
		GameConditions gc = new GameConditions(plugin);
		String map = plugin.getPlayerInfoPoo().get(player).getMapName();
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		
		
		Location loc = l;
		
		Block b = isOutside(loc,gi).getBlock();
		//System.out.println("top es "+b.getType()+" loc: "+loc);
		
		if(gi.hasBarriersinMap()) {
			if(b.getType() == Material.BARRIER) {
				gc.sendMessageToAllPlayersInMap(map,""+ChatColor.RED+ChatColor.BOLD+"AC 130: "+ChatColor.GREEN+"Paquete de ayuda solicitado por "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" suerte.");

				StorageMinecart ent = (StorageMinecart) b.getWorld().spawnEntity(b.getLocation().add(0.5, -3, 0.5), EntityType.CHEST_MINECART);
				ent.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"PAQUETE DE AYUDA");
				ent.setCustomNameVisible(true);		
				ent.getLocation().setYaw(player.getLocation().getYaw());
				
				Vector v = player.getLocation().toVector().subtract(ent.getLocation().toVector());
				Location en = ent.getLocation();
				en.setDirection(v);
				ent.teleport(en);
				
				//for(int i = 0 ; i <27;i++) {
				 ItemStack[] items = lootItems().stream().toArray(ItemStack[] :: new);
				
				ent.getInventory().setContents(items);
				return;
				
			}else {
				player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"AC 130: "+ChatColor.YELLOW+"No podemos ver tu bengala lanza otra donde podamos verla.");
				l.getWorld().dropItem(l, Items.BENGALAVERDEP.getValue());
				//System.out.println("No hay Barriers");
				return;
			}
		}else {
			if(b.getType() == Material.AIR) {
				gc.sendMessageToAllPlayersInMap(map,""+ChatColor.RED+ChatColor.BOLD+"AC 130: "+ChatColor.GREEN+"Paquete de ayuda solicitado por "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" suerte.");

				StorageMinecart ent = (StorageMinecart) b.getWorld().spawnEntity(b.getLocation().add(0.5, -3, 0.5), EntityType.CHEST_MINECART);
				ent.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"PAQUETE DE AYUDA");
				ent.setCustomNameVisible(true);		
				ent.getLocation().setYaw(player.getLocation().getYaw());
				
				Vector v = player.getLocation().toVector().subtract(ent.getLocation().toVector());
				Location en = ent.getLocation();
				en.setDirection(v);
				ent.teleport(en);
				
				//for(int i = 0 ; i <27;i++) {
				 ItemStack[] items = lootItems().stream().toArray(ItemStack[] :: new);
				
				ent.getInventory().setContents(items);
				return;
				
			}else {
				player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"AC 130: "+ChatColor.YELLOW+"No podemos ver tu bengala lanza otra donde podamos verla.");
				l.getWorld().dropItem(l, Items.BENGALAVERDEP.getValue());
				//System.out.println("No hay Barriers");
				return;
			}
		}
		
	
		
	
		
	}
	
	public void SendAirStrike(Player player,Location l) {
		
		GameConditions gc = new GameConditions(plugin);
		String map = plugin.getPlayerInfoPoo().get(player).getMapName();
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		Location loc = l;
		
	
		Block b = isOutside(loc,gi).getBlock();
		//System.out.println("top es "+b.getType()+" loc: "+loc);
		
		if(gi.hasBarriersinMap()) {
			if(b.getType() == Material.BARRIER) {
				gc.sendMessageToAllPlayersInMap(map,""+ChatColor.RED+ChatColor.BOLD+"A10 WARTHOG: "+ChatColor.GREEN+"Ataque Aereo Solicitado por "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" en Camino.");

				Entity fb = b.getWorld().spawnEntity(b.getLocation().add(0.5, -1, 0.5), EntityType.FIREBALL);
				//fb.setCustomName("Mortero");
				
				Fireball f = (Fireball) fb;
				f.setYield(10);
				f.setShooter(null);
				f.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Ataque Aereo");
				f.setDirection(new Vector (0,-3,0));
				f.setVelocity(f.getAcceleration().multiply(3));
				return;
				//((Fireball) fb).setShooter(player);
			}else {
				player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"A10 WARTHOG: "+ChatColor.YELLOW+"No podemos ver tu bengala lanza otra donde podamos verla.");
				l.getWorld().dropItem(l, Items.BENGALAROJAP.getValue());
				return;
				//System.out.println("No hay Barriers2");
			}
		}else {
			if(b.getType() == Material.AIR) {
				gc.sendMessageToAllPlayersInMap(map,""+ChatColor.RED+ChatColor.BOLD+"A10 WARTHOG: "+ChatColor.GREEN+"Ataque Aereo Solicitado por "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" en Camino.");

				Entity fb = b.getWorld().spawnEntity(b.getLocation().add(0.5, -1, 0.5), EntityType.FIREBALL);
				//fb.setCustomName("Mortero");
				
				Fireball f = (Fireball) fb;
				f.setYield(10);
				f.setShooter(null);
				f.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Ataque Aereo");
				f.setDirection(new Vector (0,-3,0));
				f.setVelocity(f.getAcceleration().multiply(3));
				return;
			}else {
				player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"A10 WARTHOG: "+ChatColor.YELLOW+"No podemos ver tu bengala lanza otra donde podamos verla.");
				l.getWorld().dropItem(l, Items.BENGALAROJAP.getValue());
				return;
				//System.out.println("No hay Barriers2");
			}
		}
		
		
		
		
		
		
	}
	
	private List<ItemStack> lootItems() {
	
		List<ItemStack> l = new ArrayList<>();
		
		// 17 elementos
		l.add(ChanceItemStack(90,new ItemStack(Material.GOLDEN_APPLE,25)));
		l.add(ChanceItemStack(100,new ItemStack(Material.ARROW,64)));
		l.add(ChanceItemStack(70,new ItemStack(Material.ENCHANTED_GOLDEN_APPLE,13)));
		l.add(ChanceItemStack(50,new ItemStack(Material.DIAMOND,45)));
		l.add(ChanceItemStack(30,new ItemStack(Material.NETHERITE_INGOT,60)));
		l.add(ChanceItemStack(50,new ItemStack(Material.EMERALD,35)));
		l.add(ChanceItemStack(50,new ItemStack(Material.GOLD_INGOT,25)));
		l.add(ChanceItemStack(80,new ItemStack(Material.SPECTRAL_ARROW,55)));
		l.add(ChanceItemStack(95,new ItemStack(Material.BOW,1)));
		l.add(ChanceItemStack(100,new ItemStack(Material.DIAMOND_CHESTPLATE,1)));
		l.add(ChanceItemStack(100,new ItemStack(Material.NETHERITE_CHESTPLATE,1)));
	
		l.add(ChanceItemStack(45,Items.ESCU1P.getValue()));
		l.add(ChanceItemStack(45,Items.ESCU2P.getValue()));
		l.add(ChanceItemStack(45,Items.KATANAP.getValue()));
		l.add(ChanceItemStack(50,Items.PALOP.getValue()));
		l.add(ChanceItemStack(45,Items.BENGALAROJAP.getValue()));
		l.add(ChanceItemStack(45,Items.BENGALAVERDEP.getValue()));
		l.add(ChanceItemStack(45,Items.DEADBOWP.getValue()));
		l.add(ChanceItemStack(45,Items.BENGALAMARCADORAP.getValue()));
		l.add(ChanceItemStack(45,Items.ARCOENCAN1P.getValue()));
		Collections.shuffle(l);
		
		return l;
		
	}
	
	
	public void getEntitiesFromFlare(Player player , Location l) {
		
		String map = plugin.getPlayerInfoPoo().get(player).getMapName();
		List<Entity> list = new ArrayList<>(getNearbyEntities(l,50));
		List<Entity> list2 = plugin.getEntitiesFromFlare().get(map);
		player.sendMessage(ChatColor.YELLOW+"Marcando a todas las entidades en el area.");
		PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING,/*duration*/ 30 * 20,/*amplifier:*/10, false ,false,true );
		
		
		for(Entity e : list) {
			if(e instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) e;
				le.addPotionEffect(glow);
				if(!list2.contains(e)) {
					list2.add(e);
				}
			}
			
		}
		
	}
	
	
	private ItemStack ChanceItemStack(int probab,ItemStack m) {
		
		  Random r = new Random();
		  
		  int number = r.nextInt(100+1);
		  if(number <= probab) {
			  return m;
		  }else {
			  return new ItemStack(Material.AIR,1);
		  }
		
	}
	
//	  public void Probability(int n) {
//		  Random r = new Random();
//		  int number = r.nextInt(100+1);
//		  if(number <= n) {
//			  System.out.println("Se cumplio la probabilidad :))))))"+n);
//		  }else {
//			  System.out.println("No se cumplio la probabilidad "+n);
//		  }
//		  System.out.println("Random probabilidad "+number);
//	  }
//	  
	  public Location isOutside(Location l,GameInfo gi) {
		  
		  Location loc = l;
		
		  
		  if(gi.hasBarriersinMap()) {
			  int y = loc.getBlockY();
			  while(y < 320) {
				  if(new Location(loc.getWorld(),loc.getX(),y,loc.getZ()).getBlock().getType() == Material.BARRIER) {
					  return new Location(loc.getWorld(),loc.getX(),y,loc.getZ());
					  
				  }//else if(new Location(loc.getWorld(),loc.getX(),y,loc.getZ()).getBlock().getType() != Material.AIR) {
				  else if(!(new Location(loc.getWorld(),loc.getX(),y,loc.getZ()).getBlock().isPassable())) {
						
					  return new Location(loc.getWorld(),loc.getX(),y,loc.getZ());
					  
				  }
				  y++;
			  }
		  }else {
			  int y = loc.getBlockY();
			  int start = 0;
			  while(start < 51) {
				  
				  if(start == 50) {
					  return new Location(loc.getWorld(),loc.getX(),y,loc.getZ()); 
				  }else if(!(new Location(loc.getWorld(),loc.getX(),y,loc.getZ()).getBlock().isPassable())) {
					
					  return new Location(loc.getWorld(),loc.getX(),y,loc.getZ());
					  
				  }
				  y++;
				  start++;
			  }
		  }
		  
		
		  return l;
		
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
	

}
