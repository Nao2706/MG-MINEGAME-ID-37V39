package me.nao.events.mg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import org.bukkit.scheduler.BukkitRunnable;

import me.nao.main.mg.Minegame;



public class RegenRun extends BukkitRunnable{
	
    @SuppressWarnings("unused")
	private Minegame plugin;
	
	public RegenRun(Minegame plugin) {
		this.plugin = plugin;
	}
	
	ArrayList<BlockState> updateBlockList = new ArrayList<>();
	//FileConfiguration config = plugin.getConfig();
	
	
	public RegenRun(List<Block> blocks) {
		for(Block b : blocks) {
			updateBlockList.add(b.getState());
		}
	}
	
	@Override
	public void run() {
		
			int max = updateBlockList.size() -1;
		    if(max > -1) {
		    	if(updateBlockList.get(max).getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE || updateBlockList.get(max).getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE || updateBlockList.get(max).getType() == Material.STONE_PRESSURE_PLATE) {	  
		    		updateBlockList.get(max).update(true,true);
		    		updateBlockList.get(max).setType(updateBlockList.get(max).getType());
		    	}else {
		    		updateBlockList.get(max).update(true,false);
		    	}
		    
		    	updateBlockList.remove(max);
		    }
		    else {
		    	this.cancel();
		    }
		
			
		
		
	}
	
	
	

}
