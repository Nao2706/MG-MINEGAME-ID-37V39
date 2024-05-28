package me.nao.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;//checar si vale
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import org.bukkit.scheduler.BukkitRunnable;

import me.nao.main.game.Main;



public class RegenRun extends BukkitRunnable{
	
    @SuppressWarnings("unused")
	private Main plugin;
	
	public RegenRun(Main plugin) {
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
		    	if(!updateBlockList.get(max).getType().equals(Material.TNT)) {
		    		updateBlockList.get(max).update(true,false);
		    	}
		    	updateBlockList.remove(max);
		    }
		    else {
		    	this.cancel();
		    }
		
			
		
		
	}
	
	
	

}
