package me.nao.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class Utils {

	
	public static String colorText(String text) {
		return ChatColor.translateAlternateColorCodes('&',text) ;
	}
	
	public static String colorTextChatColor(String text) {
		return org.bukkit.ChatColor.translateAlternateColorCodes('&',text) ;
	}
	
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
		
		//FORMATO DE 1K 1M 1B 1T ETC
		public static String formatValue(float value) {
		    String arr[] = {"", "K", "M", "B", "T", "P", "E"};
		    int index = 0;
		    while ((value / 1000) >= 1) {
		        value = value / 1000;
		        index++;
		    }
		    DecimalFormat decimalFormat = new DecimalFormat("#.##");
		    //SI SE DA ESPACIO AL %s %s al mostrar saldra asi 1 K juntos sale 1K
		    return String.format("%s%s", decimalFormat.format(value), arr[index]);
		}
		
		@SuppressWarnings("deprecation")
		public static TextComponent sendTextComponentShow(String text,String showtext,ChatColor color) {
			TextComponent m1 = new TextComponent();
			m1.setText(text);
			m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(showtext).color(color).bold(true).create()));
		    //m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg join "));
		    return m1;
		}
		
		@SuppressWarnings("deprecation")
		public static TextComponent sendTextComponentRunCommand(String text,String showtext,String runcommand ,ChatColor color) {
			TextComponent m1 = new TextComponent();
			m1.setText(text);
			m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(showtext).color(color).bold(true).create()));
		    m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/"+runcommand));
		    return m1;
		}
		
		@SuppressWarnings("deprecation")
		public static TextComponent sendTextComponentSuggestCommand(String text,String showtext,String suggestcommand ,ChatColor color) {
			TextComponent m1 = new TextComponent();
			m1.setText(text);
			m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(showtext).color(color).bold(true).create()));
		    m1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/"+suggestcommand));
		    return m1;
		}
		
		public static TextComponent sendTextComponent(String text) {
			TextComponent m1 = new TextComponent();
			m1.setText(text);
		    //m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg join "));
		    return m1;
		}
		
		
		public static BaseComponent sendTextComponentfromBaseComponent(TextComponent... textcomponent) {
			ComponentBuilder cb = new ComponentBuilder();
			for(TextComponent component : textcomponent) {
				cb.append(component);
			}
		    //m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg join "));
		    return cb.build();
		}
		
		
		
}
