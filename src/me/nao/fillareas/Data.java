package me.nao.fillareas;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.main.game.Main;




public class Data {
	
	private Main plugin;
	
	public Data(Main plugin) {
		this.plugin = plugin;
	}
	
	public void createGroup(String group,  Player player) {
		
	
		FileConfiguration ym = plugin.getCommandsMg();
		
	
		
		if(!ym.contains("Commands."+group)) {
			
			
			List<String> action = ym.getStringList("Commands."+group+".Start-Actions");
			ym.set("Commands."+group+".Start-Actions",action);
			action.add("give %player% apple %points%");
			
			List<String> actione = ym.getStringList("Commands."+group+".End-Actions");
			ym.set("Commands."+group+".End-Actions",actione);
			actione.add("give %player% apple %points%");
			
			plugin.getCommandsMg().save();
			plugin.getCommandsMg().reload();
			
			
			
			if(player != null) {
				player.sendMessage(ChatColor.GREEN+"Los commandos "+ChatColor.GOLD+group+ChatColor.GREEN+" fue creado.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Los commandos "+ChatColor.GOLD+group+ChatColor.GREEN+" fue creado.");
		}else {
			if(player != null) {
				player.sendMessage(ChatColor.GREEN+"Los commandos "+ChatColor.GOLD+group+ChatColor.GREEN+" ya existe.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Los commandos "+ChatColor.GOLD+group+ChatColor.GREEN+" ya existe.");
		}
		
		
		
		
	}
	
	
	public void deleteGroup(String group,  Player player) {
			
			FileConfiguration config = plugin.getConfig();
			FileConfiguration ym = plugin.getCommandsMg();
			
			List<String> g2 = config.getStringList("All-Groups.List");
			
			if(g2.contains(group)) {
				
				config.set("All-Groups.List", g2);
				g2.remove(group);
				List<String> g = ym.getStringList("Commands-Groups."+group);
				ym.set("Commands-Groups."+null, g);
				plugin.getCommandsMg().save();
				plugin.getCommandsMg().reload();
				
				plugin.getConfig().save();
				plugin.getConfig().reload();
				
				if(player != null) {
					player.sendMessage(ChatColor.GREEN+"El grupo "+ChatColor.GOLD+group+ChatColor.GREEN+" fue eliminado.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El grupo "+ChatColor.GOLD+group+ChatColor.GREEN+" fue eliminado.");
			}else {
				if(player != null) {
					player.sendMessage(ChatColor.GREEN+"El grupo "+ChatColor.GOLD+group+ChatColor.GREEN+" no existe.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El grupo "+ChatColor.GOLD+group+ChatColor.GREEN+" no existe.");
			}
			
	}
	
	
	
	
	
	
	
	public void StartActionsGroup(String name, Player player,Player sp) {
		
		
		 FileConfiguration cg = plugin.getCommandsMg();
		 FileConfiguration config = plugin.getConfig();
		 
		  List <String> list = config.getStringList("Worlds-Permission.List"); 
		  
		  String worldp = player.getWorld().getName();
		  
		  if(!list.contains(worldp)){
			  Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Este mundo no permite usar RandomCommand."); 
			  if(sp != null) {
				  if(sp.isOp()) {
						sp.sendMessage(ChatColor.RED+"Este mundo no permite usar RandomCommand.");
				  }
			  }
				  
			  return;
		  }
		 
		 
		 if(!cg.contains("Commands-Groups."+name)) {
			 Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Ese grupo no existe");
			  if(sp != null) {
				 if(sp.isOp())
						sp.sendMessage(ChatColor.RED+"Ese grupo no existe");
			  }
					return;
		 }
		 
		  if(sp != null) {
				 if(sp.isOp())
						sp.sendMessage(ChatColor.GREEN+"El jugador "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" recibio el grupo: "+ChatColor.RED+name);
			  }
		 
		for (String key : cg.getConfigurationSection("Commands-Groups").getKeys(false)) {
				if(name.equals(key)) {
					

					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					
					List<String> action = cg.getStringList("Commands-Groups."+key+".Actions");
					
					if(cg.getBoolean("Commands-Groups."+name+ ".Random-Reward")) {
						Random r = new Random();
						
						String t = action.get(r.nextInt(action.size()));
						
						
						Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', t.replaceAll("%player%",player.getName())));
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El jugador "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" recibio el grupo: "+ChatColor.RED+name);
						
					}else {
						
						for(int i = 0 ; i < action.size(); i++) {
							
							String texto = action.get(i);
							Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName())));
							
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El jugador "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" recibio el grupo: "+ChatColor.RED+name);
					}
					
					
					
					return;
			    }

			}
		}
	
	
	
	public void getGrouptest(String group,Player player) {
		
		FileConfiguration ym = plugin.getCommandsMg();
		if(ym.contains("Commands-Groups."+group)) {
			List<String> g = ym.getStringList("Commands-Groups."+group);
			Random r = new Random();
			
			String t = g.get(r.nextInt(g.size()));
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El jugador "+ChatColor.GOLD+player.getName()+ChatColor.GREEN+" recibio el grupo: "+ChatColor.RED+group);
			Bukkit.dispatchCommand(console, t.replaceAll("%player%",player.getName()));
		}else {
			if(player.isOp())
			player.sendMessage(ChatColor.RED+"Ese grupo no existe");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Ese grupo no existe");
		}
		
	}
	
	
	
	
	
	

}
