package me.nao.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class TextFormats {
	
	
	
	
	
	
	public static String getMessageColor(String texto) {
		
		
		List <String> vers = new ArrayList<String>();
		vers.add("1.16");
		vers.add("1.17");
		vers.add("1.18");
		vers.add("1.19");
		
		if(vers.contains(Bukkit.getVersion())) {
		   Pattern pat = Pattern.compile("#[a-fA-F0-9]{6}");
		   Matcher match = pat.matcher(texto);
		   while(match.find()) {
			   String color = texto.substring(match.start(),match.end());
			   texto = texto.replace(color,ChatColor.of(color)+"");
			   
			   match = pat.matcher(texto);
		   }
		
		}
		
		return ChatColor.translateAlternateColorCodes('&', texto);
		
	}
	
	
	
	
	
	
	
	
	

}
