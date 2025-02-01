package me.nao.cosmetics.fireworks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.main.mg.Minegame;
import net.md_5.bungee.api.ChatColor;

public class RankPlayer {
	
	private Minegame plugin;
	
    public RankPlayer (Minegame plugin) {
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
			if(points.contains("Players."+player.getName()+".Level")) {
				int point = points.getInt("Players."+player.getName()+".Level");
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
	
	
	public String getRankLevelColor(int lvl) {
		
		String rank = "";
		
			
				if(lvl >= 10) {
					return rank = ""+ChatColor.YELLOW+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 30) {
					return rank =  ""+ChatColor.AQUA+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 40) {
					return rank =  ""+ChatColor.RED+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 60) {
					return rank =  ""+ChatColor.DARK_PURPLE+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 70) {
					 rank =  ""+ChatColor.GOLD+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 80) {
					 rank =  ""+ChatColor.DARK_GREEN+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 90) {
					 rank =  ""+ChatColor.DARK_RED+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 100) {
					 rank =  ""+ChatColor.DARK_PURPLE+ChatColor.BOLD+lvl+".LVL ";
					 
				}else {
					rank = ""+ChatColor.GREEN+ChatColor.BOLD+lvl+".LVL ";
				}
				
			
		
			return rank;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
