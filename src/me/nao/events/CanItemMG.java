package me.nao.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class CanItemMG {

	
	
	
	public  void FlagsItem(Player player , Material item , String status , String data ) {
		 List<String> blocks = new ArrayList<String>();
	        
	        
		  String[] datos = data.split(",");
		  for(int i = 0 ; i< datos.length ;i ++) {
			  
			  blocks.add("minecraft:"+datos[i]);
			 
		  }
		  
		  String give = "minecraft:give "+player.getName()+" minecraft:"+item;
		  
		  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		  Bukkit.dispatchCommand(console, give);
		  
		//  NBTTagList idsTag = new NBTTagList();
		//  NBTItem nbti = new NBTItem(new ItemStack(item));
		//  NBTListCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
		  
	}
	
	
	
	
	
	
	
	
}
