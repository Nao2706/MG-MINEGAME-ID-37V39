package me.nao.cosmetics.fireworks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.main.mg.Minegame;
import me.nao.utils.Utils;
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
		
			
				if(lvl <= 99) {
					return rank = ""+ChatColor.GREEN+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 100 && lvl <= 199) {
					return rank =  ""+ChatColor.AQUA+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 200 && lvl <= 299) {
					return rank =  ""+ChatColor.RED+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 300 && lvl <= 399) {
					return rank =  ""+ChatColor.DARK_PURPLE+ChatColor.BOLD+lvl+".LVL ";
					
				}else if(lvl >= 400 && lvl <= 499) {
					 rank =  ""+ChatColor.GOLD+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 500 && lvl <= 599) {
					 rank =  ""+ChatColor.DARK_GREEN+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 600 && lvl <= 699) {
					 rank =  ""+ChatColor.DARK_RED+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 700 && lvl <= 799) {
					 rank =  ""+ChatColor.GRAY+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 800 && lvl <= 899) {
					 rank =  ""+ChatColor.DARK_PURPLE+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl >= 900 && lvl <= 999) {
					 rank =  ""+ChatColor.BLACK+ChatColor.BOLD+lvl+".LVL ";
					 
				}else if(lvl == 1000) {
					 rank =  ""+ChatColor.GOLD+ChatColor.BOLD+lvl+".LVL ";
					 
				}
		
			return rank;
	}
	
	public String getRankPrestigeColor(int lvl) {
		
		String rank = "";
		
			
				if(lvl == 0) {
					return rank = Utils.colorText("&7&l[&8&lH&7&l] ");
					
				}else if(lvl == 1) {
					return rank =  Utils.colorText("&7&l[&f&lG&7&l] ");
					
				}else if(lvl == 2) {
					return rank =  Utils.colorText("&7&l[&e&lF&7&l] ");
					
				}else if(lvl == 3) {
					return rank =  Utils.colorText("&7&l[&c&lE&7&l] ");
					
				}else if(lvl == 4) {
					 rank =  Utils.colorText("&7&l[&4&lD&7&l] ");
					 
				}else if(lvl == 5) {
					 rank =  Utils.colorText("&7&l[&a&lC&7&l] ");
					 
				}else if(lvl == 6) {
					 rank =  Utils.colorText("&7&l[&2&lB&7&l] ");
					 
				}else if(lvl == 7) {
					 rank =  Utils.colorText("&7&l[&b&lA&7&l] ");
					 
				}else if(lvl == 7) {
					 rank =  Utils.colorText("&7&l[&5&l&nS+&7&l] ");
					 
				}else if(lvl == 9) {
					 rank =  Utils.colorText("&7&l[&1&l&nSS+&7&l] ");
					 
				}else if(lvl == 10) {
					 rank = Utils.colorText("&7&l[&6&l&nSSS+&7&l] ");
					 
				}
		
			return rank;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
