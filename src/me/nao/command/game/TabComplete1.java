package me.nao.command.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import me.nao.main.game.Minegame;



public class TabComplete1 implements TabCompleter{
	
	
	private Minegame plugin;
	
	public TabComplete1(Minegame plugin) {
		this.plugin = plugin;
	}
	
	List<String> arguments = new ArrayList<String>();
	
	public List <String> onTabComplete(CommandSender sender, Command cmd,String label,String[]args){
		
		if(arguments.isEmpty()) {
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
			arguments.add("setspawn-spectator");
			arguments.add("enabled");
			arguments.add("disabled");
			arguments.add("set-points");
			arguments.add("set-life");
			arguments.add("top");
			arguments.add("check");
			arguments.add("my-points");
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
			arguments.add("maintenance");
			arguments.add("objetives");
			arguments.add("objetive");
			arguments.add("tp");
			arguments.add("tpall");
			arguments.add("tpall-to-player");
			arguments.add("tp-to-player");
			arguments.add("misions");
			arguments.add("message");
			arguments.add("tittle");
			arguments.add("generator");
			arguments.add("delgenerator");
			arguments.add("mobgenerator");
			arguments.add("delmobgenerator");
			arguments.add("points");
			arguments.add("timegame");
			arguments.add("pause");
			
			
		}
		
		FileConfiguration config = plugin.getConfig();
		List<String> arguments2 = config.getStringList("Maps-Created.List");
	
			if(arguments2.isEmpty()) {
				for(int i = 0; i < arguments2.size();i++) {
					arguments2.add(arguments2.get(i));
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
		List<String> result2 = new ArrayList<String>();
		if(args.length == 2) {
			for(String a : arguments2 ) {
				//report NAO
				//inicio de autocompletado args[0,1,2]
				if(a.toLowerCase().startsWith(args[1].toLowerCase())) 
					result2.add(a);
					
				
				
			}
			return result2;
		}
		
		
		
		
		return null;
	}	
	
	
	
	

}
