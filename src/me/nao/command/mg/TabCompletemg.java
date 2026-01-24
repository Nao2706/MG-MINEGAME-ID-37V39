package me.nao.command.mg;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.enums.mg.GameCheats;
import me.nao.main.mg.Minegame;



public class TabCompletemg implements TabCompleter{
	
	
	private Minegame plugin;
	
	public TabCompletemg(Minegame plugin) {
		this.plugin = plugin;
	}
	
	List<String> arguments = new ArrayList<String>();
	
	public List <String> onTabComplete(CommandSender sender, Command cmd,String label,String[]args){
		
		if(arguments.isEmpty()) {
			arguments.add("info");
			arguments.add("help");
			arguments.add("tntrain");
			arguments.add("entityrain");
			arguments.add("arrowrain");
			arguments.add("pardon");
			arguments.add("ban");
			arguments.add("kick");
			arguments.add("warn");
			arguments.add("tempban");
			arguments.add("version");
			arguments.add("reload");
			arguments.add("create");
			arguments.add("join");
			arguments.add("leave");
			arguments.add("delete");
			arguments.add("show-maps");
			arguments.add("setlobby");
			arguments.add("setprelobby");
			arguments.add("setspawn");
			arguments.add("setspawnffa");
			arguments.add("setspawn-spectator");
			arguments.add("spectator");			
			arguments.add("enabled");
			arguments.add("disabled");
			arguments.add("set-points");
			arguments.add("set-life");
			arguments.add("top");
			arguments.add("check-points");
			arguments.add("dropplayer");
			arguments.add("goto-checkpoint");
			arguments.add("deletecheckpoint");
			arguments.add("reward");
			arguments.add("setspawn-end");
			arguments.add("stop");
			arguments.add("time");
			arguments.add("formats");
			arguments.add("difficult");
			arguments.add("getInv");
			arguments.add("saveInv");
			arguments.add("item");
			arguments.add("ride");
			arguments.add("force-revive");
			arguments.add("force-start");
			arguments.add("maintenance");
			arguments.add("objetives");
			arguments.add("objetive");
			arguments.add("tp");
			arguments.add("tpall");
			arguments.add("tpall-to-player");
			arguments.add("tp-to-player");
			arguments.add("misions");
			arguments.add("message");
			arguments.add("title");
			arguments.add("generator");
			arguments.add("delgenerator");
			arguments.add("mobgenerator");
			arguments.add("delmobgenerator");
			arguments.add("points");
			arguments.add("timegame");
			arguments.add("pause");
			arguments.add("get-scale");
			arguments.add("set-scale");
			arguments.add("invite");
			arguments.add("addtag");
			arguments.add("removetag");
			arguments.add("showtags");
			arguments.add("tagsingame");
			arguments.add("mytags");
			arguments.add("prestige");
			arguments.add("querylevel");
			arguments.add("mapsingame");
			arguments.add("ping");
			arguments.add("gamedetails");
			arguments.add("mapinfo");
			arguments.add("sudo");
			arguments.add("sudoall");
			arguments.add("prestiges");
			arguments.add("xp");
			arguments.add("maprecord");
			arguments.add("delete-all-tempcooldown");
			arguments.add("delete-tempcooldown");
			arguments.add("getKit");
			arguments.add("saveKit");
			arguments.add("getItem");
			arguments.add("saveItem");
			arguments.add("save-item-db");
			arguments.add("get-item-db");
			arguments.add("check-item-db");
			arguments.add("delete-item-db");
			arguments.add("save-kit-db");
			arguments.add("get-kit-db");
			arguments.add("check-kit-db");
			arguments.add("delete-kit-db");
			arguments.add("report");
			arguments.add("reportlogs");
			arguments.add("modlogs");
			arguments.add("set-custom-generator");
			arguments.add("remove-custom-generator");
			arguments.add("bossbar");
			arguments.add("execute-timer");
			arguments.add("cancel-timer");
			arguments.add("signmarker");
			arguments.add("signreload");
			
		}
		
		
		if(sender instanceof Player) {
			//mg arg1-tutorial arg2-
			
			//Bukkit.getConsoleSender().sendMessage("TAB TEST: c:"+cmd+" label:"+label+" Args:"+String.join(" ", args)+" l:"+args.length);
			
			if(args.length >= 1) {
				
					if(args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("enabled") || 
							args[0].equalsIgnoreCase("disabled")|| args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("invite") 
							|| args[0].equalsIgnoreCase("setspawnffa") || args[0].equalsIgnoreCase("setspawn") || args[0].equalsIgnoreCase("setprelobby")
							|| args[0].equalsIgnoreCase("setspawn-spectator") || args[0].equalsIgnoreCase("setspawn-end")) {
						
						FileConfiguration config = plugin.getConfig();
						List<String> result = new ArrayList<String>();
						List<String> arguments2 = config.getStringList("Maps-Created.List");
						if(!arguments2.isEmpty()) {
							
							for(String a : arguments2) {
								if(args.length == 2) {
									if(a.toLowerCase().startsWith(args[1].toLowerCase())) 
										result.add(a);
								}
								
							}
						
						}//mg repor tot xd
						return result;
					} 
					
					if(args[0].equalsIgnoreCase("report")){
						//Player player = (Player) sender;
						if(args.length == 3) {
							
								//TOMA ENUMS Y LOS DA COMO LISTA
							   //List<String> result = Arrays.stream(GameCheats.values()).map(Object:: toString).collect(Collectors.toList());
							   List<String> result = Arrays.stream(GameCheats.values()).map(GameCheats::getValue).collect(Collectors.toList());

							
							
						       return result;
						}
				
				    }
					if(args[0].equalsIgnoreCase("coord")){
							Player player = (Player) sender;
							Block b = player.getTargetBlock((Set<Material>) null, 150);
							List<String> result = new ArrayList<String>();
					       if(!b.getType().isSolid()) {
					    	   result.add("Apunta a un Bloque Solido");
					       }else {
					    	   	NumberFormat nf = NumberFormat.getInstance();
								nf.setGroupingUsed(false);
								nf.setMaximumFractionDigits(0);
								
					    	   result.add(player.getWorld().getName()+";"+nf.format(player.getLocation().getX())+";"+nf.format(player.getLocation().getY())+";"+nf.format(player.getLocation().getZ()));
					       }
					       return result;
					}
					
					if(args[0].equalsIgnoreCase("getKit")) {
						FileConfiguration invs = plugin.getKitsYaml();
						List<String> result = new ArrayList<String>();
						if(invs.contains("Kits")) {
							for (String key : invs.getConfigurationSection("Kits").getKeys(false)) {
								if(args.length == 2) {
									if(key.toLowerCase().startsWith(args[1].toLowerCase())) 
										result.add(key);
								}
							}
						}
						
						if(result.isEmpty()) {
							result.add("No hay Ningun Kit");	
						}
						
		
						return result;
					} 
					
					if(args[0].equalsIgnoreCase("getItem")) {
						FileConfiguration items = plugin.getItemsYaml();
						List<String> result = new ArrayList<String>();
					
						
						if(items.contains("Items")) {
							for (String key : items.getConfigurationSection("Items").getKeys(false)) {
								if(args.length == 2) {
									if(key.toLowerCase().startsWith(args[1].toLowerCase())) 
										result.add(key);
								}
							}
						}
						
						if(result.isEmpty()) {
							result.add("No hay Ningun Item");	
						}
						
		
						return result;
					} 
				
			
			}
			
			//tab 1 comandos
			List<String> result = new ArrayList<String>();
			if(args.length == 1) {
				for(String a : arguments ) {
					//report NAO
					//inicio de autocompletado args[0,1,2]
					if(a.toLowerCase().startsWith(args[0].toLowerCase())) 
						result.add(a);
						
					
					
				}
				return result;
			}
			
			//checar todo
			//tab 2 arenas
//			List<String> result2 = new ArrayList<String>();
//			if(args.length == 2) {
//				for(String a : arguments2 ) {
//					//report NAO
//					//inicio de autocompletado args[0,1,2]
//					if(a.toLowerCase().startsWith(args[1].toLowerCase())) 
//						result2.add(a);
//						
//					
//					
//				}
//				return result2;
//			}
		}
		
		

			
			
//		FileConfiguration config = plugin.getConfig();
//		List<String> arguments2 = config.getStringList("Maps-Created.List");
//		if(args.length == 1) {
//			for(String a : arguments ) {
//				if(a.toLowerCase().startsWith("join") || a.toLowerCase().startsWith("delete") || a.toLowerCase().startsWith("enabled") || a.toLowerCase().startsWith("disabled")) {
//					
//				} 
//			}
//			if(arguments2.isEmpty()) {
//				for(int i = 0; i < arguments2.size();i++) {
//					arguments2.add(arguments2.get(i));
//				}
//			}
//			return arguments2;
//		}
			
			
		

		
		
		
		
		return null;
	}	
	
	
	
	

}
