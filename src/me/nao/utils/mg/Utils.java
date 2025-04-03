package me.nao.utils.mg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;


public class Utils {

	public static String warningLineMessage(int amount) {
		String text = "/";
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < amount ; i++) {
			if(i % 2 == 0) {
				sb.append(""+ChatColor.YELLOW+ChatColor.BOLD+text);
			}else {
				sb.append(""+ChatColor.BLACK+ChatColor.BOLD+text);
			}
		}
		return sb.toString();
	}
	
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
		
		@SuppressWarnings("deprecation")
		public static TextComponent sendTextComponentItem(Player player ,String text ,ItemStack is) {
		   // String output = "Whatever you want the line of text you'll hover over to be.";
		    TextComponent cLink = new TextComponent(text+" "+is.getItemMeta().getDisplayName());
		         
		            //this is the magic line, gets us the item meta of an existing item stack so we can send it to the hoverEvent
		            //ItemTag itemTag = ItemTag.ofNbt(is.getItemMeta() == null ? null : is.getItemMeta().getAsString());
		            ItemTag itemTag = ItemTag.ofNbt(is.getItemMeta() == null ? null : is.getItemMeta().toString());
		     //****NOTE**** if you are trying to do this on 1.17 you must replace .getAsString() with .toString()
		         	//DynamicOps<JsonElement> dyna = CraftRegistry.getMinecraftR
		    		System.out.println("TEXT: "+itemTag);
		            cLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item(is.getType().getKey().toString(), is.getAmount(),ItemTag.ofNbt(itemTag.getNbt()))));

		            cLink.setText(ChatColor.translateAlternateColorCodes('&', cLink.getText()));

		            //then simply send it to the player
		          player.spigot().sendMessage(cLink);
		      return cLink;
		}
		
		
		
		public static BaseComponent sendTextComponentfromBaseComponent(TextComponent... textcomponent) {
			ComponentBuilder cb = new ComponentBuilder();
			for(TextComponent component : textcomponent) {
				cb.append(component);
			}
		    //m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg join "));
		    return cb.build();
		}
		
		
		public static String pingLevel(int ping) {
			String text = "";
			if(ping <= 30) {
				text = colorTextChatColor("&a"+ping+"ms &fes &aMuy Bueno");
			}else if(ping >= 31 && ping <= 60) {
				text = colorTextChatColor("&b"+ping+"ms &fes &bBueno");
			}else if(ping >= 61 && ping <= 120) {
				text = colorTextChatColor("&e"+ping+"ms &fes &eMedio");
			}else if(ping >= 121 && ping <= 250) {
				text = colorTextChatColor("&6"+ping+"ms &fes &6Malo [Posible Desconexion]");
			}else if(ping >= 251) {
				text = colorTextChatColor("&c"+ping+"ms &fes &cMuy Malo [Desconexion por Lag Inminente]");
			}
			
			return text;
		}
		
		public static String chooseAmPm(String text) {
			int hour = Integer.valueOf(text.split(":")[0]);
			
			switch(hour) {
				case 00:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
					text = text + " AM";
				case 12:
				case 13:
				case 14:
				case 15:
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
				case 21:
				case 22:
				case 23:
					text = text + " PM";
			}
			return text;
		} 
}
