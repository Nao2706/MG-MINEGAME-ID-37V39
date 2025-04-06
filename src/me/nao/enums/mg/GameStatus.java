package me.nao.enums.mg;

import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public enum GameStatus {


	ESPERANDO(""+ChatColor.WHITE+ChatColor.BOLD+"ESPERANDO"),
	COMENZANDO(""+ChatColor.GOLD+ChatColor.BOLD+"COMENZANDO"),
	PAUSE(""+ChatColor.YELLOW+ChatColor.BOLD+"PAUSE"),
	FREEZE(""+ChatColor.AQUA+ChatColor.BOLD+"FREEZE"),
	JUGANDO(""+ChatColor.GREEN+ChatColor.BOLD+"JUGANDO"),
	TERMINANDO(""+ChatColor.RED+ChatColor.BOLD+"TERMINANDO"),
	DESACTIVADA(""+ChatColor.GRAY+ChatColor.BOLD+"DESACTIVADA");
	
	
	
	 private String description;
	
	GameStatus(String description) {
		this.description = description; 
	 } 
	
	public String getValue() {
		return this.description ;
	}
	
}
