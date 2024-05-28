package me.nao.timers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.main.game.Main;



public class Countdown2 {

	
	
	int taskID;
	
	
    private Main plugin;
    private String group;
    private int time ;
    
    
    
    
    //cuenta atras
	public Countdown2(Main plugin,int time ,String group) {
		this.plugin = plugin;
		this.time = time;
		this.group = group;
	}
	
	
	public void ejecucion() {
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
	
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();	
			
			int seg = time;	
			int i = 0;
			int i2 = 0;
		
			//se ejecuta siempre 
			public void run() {
				FileConfiguration c = plugin.getCommandsMg();
				List<String> action = c.getStringList("Commands."+group+".Start-Actions");
				List<String> actione = c.getStringList("Commands."+group+".End-Actions");
				
				//console.sendMessage("TIME "+seg);
				
				if(!plugin.getCommandFillArea().get(group)) {
					Bukkit.getScheduler().cancelTask(taskID);
					plugin.getTimerAction().remove(group);
					console.sendMessage("REP CANCEL "+group);
					for(int i3 = 0 ; i3 < actione.size();i3++) {
						Bukkit.dispatchCommand(console, actione.get(i3));
					}
				
				}
				
				if(seg == 0) {
					
					if(i != action.size()) {
						//console.sendMessage("Time Spam Test del NAO :v lista 1 "+i);
						String command = action.get(i);
						console.sendMessage("Comando ejecutado: "+command);
						Bukkit.dispatchCommand(console, command);
						seg = time;
						i++;
					}else {
						
						if(i == action.size()) {
							//console.sendMessage("Time Spam Test del NAO :v lista 2 "+i2);
							String command = actione.get(i2);
							console.sendMessage("Comando ejecutado2: "+command);
							Bukkit.dispatchCommand(console, command);
							seg = time;
							i2++;
							if(i2 == actione.size()){
								i = 0;
								i2 = 0;
								//console.sendMessage("Time Spam Test del NAO :v lista reset1 "+i);
								//console.sendMessage("Time Spam Test del NAO :v lista reset2 "+i2);
							}
						}
						
					}
					
					
				}
			//console.sendMessage("Time Spam Test del NAO :v "+seg);
			
					seg--;	
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

