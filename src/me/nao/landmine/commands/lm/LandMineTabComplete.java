package me.nao.landmine.commands.lm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class LandMineTabComplete implements TabCompleter{

	
	List<String> arguments = new ArrayList<String>();
	
	
	public List <String> onTabComplete(CommandSender sender, Command cmd,String label,String[]args){
		
		if(arguments.isEmpty()) {
			arguments.add("createLandMine");
			arguments.add("reload");
			arguments.add("playerslandmines");
			arguments.add("playerlandmines");
			arguments.add("worldslandmines");
		}
		
		
		if(sender instanceof Player) {
			if(args.length >= 1) {
				
			}
			
			
			
		}
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
		
		
		return null;
	}
	
	
	
	
	
	
	
	
}
