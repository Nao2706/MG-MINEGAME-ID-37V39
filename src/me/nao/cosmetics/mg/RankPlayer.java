package me.nao.cosmetics.mg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.main.mg.Minegame;
import me.nao.utils.mg.Utils;
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
					return rank = ""+ChatColor.GREEN+ChatColor.BOLD+"LVL."+lvl+" ";
					
				}else if(lvl >= 100 && lvl <= 199) {
					return rank =  ""+ChatColor.AQUA+ChatColor.BOLD+"LVL."+lvl+" ";
					
				}else if(lvl >= 200 && lvl <= 299) {
					return rank =  ""+ChatColor.RED+ChatColor.BOLD+"LVL."+lvl+" ";
					
				}else if(lvl >= 300 && lvl <= 399) {
					return rank =  ""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"LVL."+lvl+" ";
					
				}else if(lvl >= 400 && lvl <= 499) {
					 rank =  ""+ChatColor.GOLD+ChatColor.BOLD+"LVL."+lvl+" ";
					 
				}else if(lvl >= 500 && lvl <= 599) {
					 rank =  ""+ChatColor.DARK_GREEN+ChatColor.BOLD+"LVL."+lvl+" ";
					 
				}else if(lvl >= 600 && lvl <= 699) {
					 rank =  ""+ChatColor.DARK_RED+ChatColor.BOLD+"LVL."+lvl+" ";
					 
				}else if(lvl >= 700 && lvl <= 799) {
					 rank =  ""+ChatColor.GRAY+ChatColor.BOLD+"LVL."+lvl+" ";
					 
				}else if(lvl >= 800 && lvl <= 899) {
					 rank =  ""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"LVL."+lvl+" ";
					 
				}else if(lvl >= 900 && lvl <= 999) {
					 rank =  ""+ChatColor.BLACK+ChatColor.BOLD+"LVL."+lvl+" ";
					 
				}else if(lvl == 1000) {
					 rank =  ""+ChatColor.GOLD+ChatColor.BOLD+"LVL."+lvl+" ";
					 
				}
		
			return rank;
	}
	
	public String getRankPrestigeColor(int lvl) {
		
		String rank = "";
		
			
				if(lvl == 0) {
					return rank = Utils.colorText("&7&l[&8&lNOVATO&7&l] ");
					
				}else if(lvl == 1) {
					return rank =  Utils.colorText("&7&l[&f&lCABALLERO&7&l] ");
					
				}else if(lvl == 2) {
					return rank =  Utils.colorText("&7&l[&e&lGUARDIAN&7&l] ");
					
				}else if(lvl == 3) {
					return rank =  Utils.colorText("&7&l[&c&lCENTINELA&7&l] ");
					
				}else if(lvl == 4) {
					 rank =  Utils.colorText("&7&l[&4&lCAMPEON&7&l] ");
					 
				}else if(lvl == 5) {
					 rank =  Utils.colorText("&7&l[&a&lCONQUISTADOR&7&l] ");
					 
				}else if(lvl == 6) {
					 rank =  Utils.colorText("&7&l[&2&lTITAN&7&l] ");
					 
				}else if(lvl == 7) {
					 rank =  Utils.colorText("&7&l[&b&lEMPERADOR&7&l] ");
					 
				}else if(lvl == 7) {
					 rank =  Utils.colorText("&7&l[&5&l&nREY&7&l] ");
					 
				}else if(lvl == 9) {
					 rank =  Utils.colorText("&7&l[&1&l&nMAESTRO&7&l] ");
					 
				}else if(lvl == 10) {
					 rank = Utils.colorText("&7&l[&6&l&nCELESTIAL&7&l] ");
					 
				}
		
			return rank;
	}
	
	
	public String getRankPrestigePlaceHolder(int lvl) {
		
		String rank = "";
		
			
				if(lvl == 0) {
					return rank = "NOVATO";
					
				}else if(lvl == 1) {
					return rank =  "CABALLERO";
					
				}else if(lvl == 2) {
					return rank = "GUARDIAN";
					
				}else if(lvl == 3) {
					return rank = "CENTINELA";
					
				}else if(lvl == 4) {
					 rank =  "CAMPEON";
					 
				}else if(lvl == 5) {
					 rank = "CONQUISTADOR";
					 
				}else if(lvl == 6) {
					 rank = "TITAN";
					 
				}else if(lvl == 7) {
					 rank =  "EMPERADOR";
					 
				}else if(lvl == 7) {
					 rank =  "REY";
					 
				}else if(lvl == 9) {
					 rank =  "MAESTRO";
					 
				}else if(lvl == 10) {
					 rank = "CELESTIAL";
					 
				}
		
			return rank;
	}
	
	public void showPrestiges(Player player) {
		
		
		List<String> l = new ArrayList<>();
		l.add("");
		l.add("&c- &7[&8&lNOVATO&7]");
		l.add("&c- &7[&f&lCABALLERO&7]");
		l.add("&c- &7[&e&lGUARDIAN&7]");
		l.add("&c- &7[&c&lCENTINELA&7]");
		l.add("&c- &7[&4&lCAMPEON&7]");
		l.add("&c- &7[&a&lCONQUISTADOR&7]");
		l.add("&c- &7[&2&lTITAN&7]");
		l.add("&c- &7[&b&lEMPERADOR&7]");
		l.add("&c- &7[&5&l&nREY&7]");
		l.add("&c- &7[&1&l&nMAESTRO&7]");
		l.add("&c- &7[&6&l&nCELESTIAL&7]");

		
		if(player != null) {
			player.sendMessage(Utils.colorText("&c&l&nPrestigios Disponibles"));
			
			for(String text : l) {
				player.sendMessage(Utils.colorText(text));
			}
		
		}
		Bukkit.getConsoleSender().sendMessage(Utils.colorText("&c&l&nPrestigios Disponibles"));
		for(String text : l) {
			Bukkit.getConsoleSender().sendMessage(Utils.colorText(text));
		}
	
	
	}
	
	
	

}
