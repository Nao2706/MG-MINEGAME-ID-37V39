package me.nao.enums.mg;

import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public enum MapStatus {

	
	COMPLETE(""+ChatColor.GREEN+ChatColor.BOLD+"COMPLETADO"),
	UNKNOW(""+ChatColor.GRAY+ChatColor.BOLD+"DESCONOCIDO"),
	INCOMPLETE(""+ChatColor.RED+ChatColor.BOLD+"FRACASARON");
	
	 private String description;
		
	 MapStatus(String description) {
		this.description = description; 
	 } 
	
	public String getValue() {
		return this.description ;
	}
}
