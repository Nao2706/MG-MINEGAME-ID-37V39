package me.nao.enums.mg;

import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public enum StopMotive {
	
	ERROR(""+ChatColor.RED+ChatColor.BOLD+"ERROR DE MAPA"),
	NINGUNO(""+ChatColor.WHITE+ChatColor.BOLD+"PARTIDA NORMAL"),
	WIN(""+ChatColor.GREEN+ChatColor.BOLD+"VICTORIA FORZADA"),
	LOSE(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"DERROTA FORZADA"),
	FORCE(""+ChatColor.RED+ChatColor.BOLD+"INTERRUPCION FORZADA");

	 private String description;
	
	 StopMotive(String description) {
		this.description = description;
	 } 
	
	public String getValue() {
		return this.description ;
	}
	
}
