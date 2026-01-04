
package me.nao.manager.mg;


import java.util.HashMap;
import java.util.Map;

//import org.bukkit.Bukkit;

import org.bukkit.Location;
import org.bukkit.block.Block;

import org.bukkit.inventory.ItemStack;


import me.nao.main.mg.Minegame;

public class GeneratorManager {

		private Map<Location,Generador> gens;
	    private Minegame plugin;
	    
	    
	    public GeneratorManager(Minegame plugin) {
	        gens = new HashMap<>();
	        this.plugin = plugin;
	    }
	    
	    
	    
	    public void addGenerator(Block b,ItemStack item,float time) {
	        if(existe(b.getLocation()))return;
	        
	        Generador gen = new Generador(b, item, time ,plugin);
	        gens.put(b.getLocation(), gen);
	        
	        gen.runTaskTimer(plugin, 0,Math.round(time * 20));
	       
	    }
	    
	    
	    public boolean existe(Location loc){
	    	return gens.containsKey(loc);
	    }
	  
	    
	public void removeGenerador(Location loc){
	
		if(gens.containsKey(loc)) {
			gens.get(loc).cancel();
			gens.remove(loc);
		}
	
	}


	
	
	
	
	
}
