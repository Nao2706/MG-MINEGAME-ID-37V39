package me.nao.cosmetics.fireworks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.main.game.Main;
import net.md_5.bungee.api.ChatColor;

public class RankPlayer {
	
	private Main plugin;
	
    public RankPlayer (Main plugin) {
    	this.plugin = plugin;
    }
	
	
	//NOVATO
    //CORREDOR
    //SUPERVIVIENTE
    //EXPERTO
    //ELITE
    //LEYENDA
    
	public String getRank(Player player) {
		
		String rank = "";
		
			FileConfiguration points = plugin.getPoints();
			if(points.contains("Players."+player.getName()+".Kills")) {
				int point = points.getInt("Players."+player.getName()+".Kills");
				if(point >= 100) {
					return rank = ""+ChatColor.GREEN+ChatColor.BOLD+"CORREDOR ";
				}
				else if(point >= 500) {
					return rank =  ""+ChatColor.AQUA+ChatColor.BOLD+"SUPERVIVIENTE ";
				}
				
				else if(point >= 1000) {
					return rank =  ""+ChatColor.RED+ChatColor.BOLD+"EXPERTO ";
				}
				
				else if(point >= 1500) {
					return rank =  ""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"ELITE ";
				}
				
				else if(point >= 2000) {
					return rank =  ""+ChatColor.GOLD+ChatColor.BOLD+"LEYENDA ";
				}

				else {
					return rank = ""+ChatColor.WHITE+ChatColor.BOLD+"NOVATO ";
				}
				
			}else {
				rank = ""+ChatColor.WHITE+ChatColor.BOLD+"NOVATO ";
			}
		
			return rank;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
