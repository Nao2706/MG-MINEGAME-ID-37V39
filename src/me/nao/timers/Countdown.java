package me.nao.timers;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.main.game.Main;



public class Countdown {

	
	
	int taskID;
	
	
    private Main plugin;
	int tiempo;
	private Player player;
 
    
    
    
    
    
    //cuenta atras
	public Countdown(Main plugin,int tiempo,Player player) {
		this.plugin = plugin;
		this.tiempo = tiempo;
		this.player = player;
		
		
	}
	
	
	public void ejecucion() {
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			
			//se ejecuta siempre 
			public void run() {
				
				
			
						if(tiempo == 0) {
							Bukkit.getScheduler().cancelTask(taskID);//se cancela
						
							
							if(plugin.getPlayerGround().remove(player.getName()));
							return;
						}
					
						
							
			
						 
					tiempo--;	
						
			}
					
	
		},0L,20);
	
	}
	
	

	
	
	
}




/*package me.nao.eventos;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import me.nao.ItemManager;


public class Invent implements Listener{

	

	
	@SuppressWarnings("null")
	@Nullable
	HumanEntity getHolder(Inventory I) {
		 Player player = null ;
		
		if(player.getInventory().contains(ItemManager.vara)) {
			
			
			
			
		}
		
		
		
		
		
		return null;
	}
	
	
	
	
}

*/

