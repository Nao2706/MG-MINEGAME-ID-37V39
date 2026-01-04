package me.nao.manager.mg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
import org.bukkit.block.Block;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
//import org.bukkit.entity.EntityType;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.nao.main.mg.Minegame;



public class Generador extends BukkitRunnable{

	
    @SuppressWarnings("unused")
	private Minegame plugin;
	
	
	private Block b;
    private ItemStack item;
    private float time ;
    //timex antes era final ojito
    private final float timex;
    
    
    
    
    public Generador(Block b,ItemStack item,float time, Minegame plugin) {
        this.time = time;
        this.timex = time;
        this.b = b;
        this.item = item;
        this.plugin = plugin;
          
    }
    public Location getPos() {
        return b.getLocation();
    }
    
    @Override
    public void run() {
    	
    	
    			if(time <= 0) {
 		            time = timex;
 		            //b.getWorld().playSound(b.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
 		            //b.getWorld().spawnParticle(Particle.TOTEM,b.getLocation().add(0.5, 1.5, 0.5), 20);
 		            b.getWorld().dropItem(b.getLocation().add(0.5, 1, 0.5), item);
 		        }
 		        time = -0.05f;
    		
 		        
 		
    }
    
    
//    @Override
//    public void run() {
//    	FileConfiguration config = plugin.getConfig();
//    	for(Entity e : getNearbyEntites(b.getLocation(),config.getInt("Range-Spawn"))) {
//    		if(e.getType() == EntityType.PLAYER) {
//    			 if(time <= 0) {
// 		            time = timex;
// 		            e.getWorld().playSound(e.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
// 		            b.getWorld().spawnParticle(Particle.TOTEM,b.getLocation().add(0.5, 1.5, 0.5), 20);
// 		            b.getWorld().dropItem(b.getLocation().add(0.5, 1, 0.5), item);
// 		        }
// 		        time = -0.05f;
//    		}
//		       
//       }
//
//    }
	
    
    // TESTEAR PARA VER SI GENERA ITEMS AL ESTAR CERCA DEL GENERADOR 
	public List<Entity> getNearbyEntites(Location l , int size){
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : l.getWorld().getEntities()) {
			if(l.distance(e.getLocation()) <= size) {
				entities.add(e);
			}
		}
		return entities;
		
		
	}
	
	
}
