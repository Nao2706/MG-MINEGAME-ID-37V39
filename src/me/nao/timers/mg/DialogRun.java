package me.nao.timers.mg;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.enums.mg.GameStatus;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameDialogs;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.main.mg.Minegame;




public class DialogRun {

	
	
	private int taskID;
    private Minegame plugin;
    private GameDialogs dialog;
    

 
    
    
    
    
    
    //cuenta atras
	public DialogRun(Minegame plugin,GameDialogs g) {
		this.plugin = plugin;
		this.dialog = g;
	
		
		
	}
	
	
	
	public void StartDialog() {
		 BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
		 taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
		 List<String> l = dialog.getList();
		 GameInfo gi = plugin.getGameInfoPoo().get(dialog.getMap());
		 
		 int start = 0;
//		 int repeat = dialog.getTiempo();
//		 int value = 0;
			//se ejecuta siempre 
			public void run() {
				
//				if(value != repeat) {
				
				if(start == l.size() || gi.getGameStatus() != GameStatus.JUGANDO) {
					Bukkit.getScheduler().cancelTask(taskID);//se cancela
					System.out.println("Dialogo detenido");
				}	
				if(start < l.size()) {
					String text = l.get(start);
					System.out.println("Dialogo "+start);
					isForPlayersOrCommand(dialog.getMap(),text);
				}
				
				
				
				
					
			

					
				
						
					
					 start++;
						
			}
					
	
		},0L,20 * dialog.getTiempo());
	
	}
	
	
	public void StartDialogDebug(Player player) {
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
		 List<String> l = dialog.getList();
		 int start = 0;
//		 int repeat = dialog.getTiempo();
//		 int value = 0;
			//se ejecuta siempre 
			public void run() {
				
				
					String text = l.get(start);
				
					isForPlayersOrCommandDebug(player, text);
					if(start == l.size()) {
						Bukkit.getScheduler().cancelTask(taskID);//se cancela
					}	
				
						
					
					start++;
						
			}
					
	
		},0L,20);
	
	}
	
	public void isForPlayersOrCommand(String map,String text) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if(text.contains("<action>")) {
			System.out.println("ACCION "+text);
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			GameConditions gc = new GameConditions(plugin);
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				
				List<Player> l = gc.ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player player : l) {
					Bukkit.dispatchCommand(console,text.replace("<action>", "").replace("%player%", player.getName()));
				}
			}
			
			
			
		
		}else if(text.contains("<server>")) {
			System.out.println("SERVER "+text);
			Bukkit.dispatchCommand(console,text.replace("<server>", ""));
		
		}else{
			System.out.println("PLAYER "+text);
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			GameConditions gc = new GameConditions(plugin);
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				
				List<Player> l = gc.ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player player : l) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',text.replace("%player%", player.getName())));
				}
			}
		
		}
		return;
	}
	
	
	
	public void isForPlayersOrCommandDebug(Player player,String text) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if(text.contains("<action>")) {
			
		
			Bukkit.dispatchCommand(console,text.replace("<action>", "").replace("%player%", player.getName()));
			
			
		
		}else if(text.contains("<server>")) {
		
			Bukkit.dispatchCommand(console,text.replace("<server>", ""));
		
		}else{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',text.replace("%player%", player.getName())));
			
		}
		return;
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

