package me.nao.utils.mg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
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
		
		public static void iteminfo(ItemStack it) {
			//List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
			
			System.out.println("NAME"+it.getItemMeta().getDisplayName());
			
			List<String> lore = it.getItemMeta().getLore();
			for(String t : lore) {
				System.out.println("Lore:"+t);
			}
		
			List<Map.Entry<Attribute,AttributeModifier>> list = new ArrayList<>(it.getItemMeta().getAttributeModifiers().entries());
			for(Map.Entry<Attribute,AttributeModifier> e : list) {
				
				System.out.println("Atr"+e.getKey().getKeyOrThrow()+" value:"+ e.getValue().getAmount()+" slot:"+e.getValue().getSlotGroup() );
			}
			
			List<Map.Entry<Enchantment,Integer>> list2 = new ArrayList<>(it.getItemMeta().getEnchants().entrySet());
			for(Map.Entry<Enchantment,Integer> e : list2) {
				System.out.println("encan"+e.getKey()+" lvl:"+ e.getValue());
			}
		}
		
			@SuppressWarnings("deprecation")
			public static void iteminfo2(Player player ,ItemStack it) {
			
				TextComponent cLink = new TextComponent("Hola mi item es: ");
				
				
				
				ItemTag itemtag = ItemTag.ofNbt(it.getItemMeta().toString());	
				Item item = new Item(it.getType().getKey().toString(),it.getAmount(),itemtag);
				cLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, item));
				
				ComponentBuilder cb = new ComponentBuilder(cLink);
				BaseComponent[] bc = cb.create();
				
				player.spigot().sendMessage(bc);
				
		   }
		
		@SuppressWarnings("deprecation")
		public static TextComponent sendTextComponentItem(Player player ,String text ,ItemStack is) {
			
			
		   // String output = "Whatever you want the line of text you'll hover over to be.";
		    TextComponent cLink = new TextComponent(text+" "+is.getItemMeta().getDisplayName());
		    TextComponent cLink2 = new TextComponent();
		    TextComponent cLink3 = new TextComponent();
		            //this is the magic line, gets us the item meta of an existing item stack so we can send it to the hoverEvent
		            //ItemTag itemTag = ItemTag.ofNbt(is.getItemMeta() == null ? null : is.getItemMeta().getAsString());
		    
		    		String t1 = is.getItemMeta().toString();
		    		String t2 = is.getItemMeta().getAsComponentString();
		    		String t3 = is.getItemMeta().getAsString();// SUPUESTAMENTE HASTA LE VERSION 1.20.4 FUNCIONABA CON ESTO
//		    		String components = is.getItemMeta().getAsComponentString(); // example: "[minecraft:damage=53]"
//		    		 String itemTypeKey = is.getType().getKey().toString(); // example: "minecraft:diamond_sword"
//		    		 String itemAsString = itemTypeKey + components; // results in: "minecraft:diamond_sword[minecraft:damage=53]"
//
//		    		 ItemStack recreatedItemStack = Bukkit.getItemFactory().createItemStack(itemAsString);
//		    		 assert is.isSimilar(recreatedItemStack); // Should be true*
		    		ItemTag itemtag = ItemTag.ofNbt(t1);
		    		ItemTag itemtag2 = ItemTag.ofNbt(t2);
		    		ItemTag itemtag3 = ItemTag.ofNbt(t3);
		    		System.out.println("T1: "+t1);// ELIMINADO EN PRUEBA POR MENCION DE UNSPECIFIC_META 
		    		System.out.println("T2: "+t2);
		    		System.out.println("T3: "+t3);
//		    		Map<String,Object> seri = is.serialize();
//		    		System.out.println("Seri: "+seri.toString());
//		            //ItemTag itemtag = ItemTag.ofNbt(is.getItemMeta() == null ? null : seri.toString());
//		     //****NOTE**** if you are trying to do this on 1.17 you must replace .getAsString() with .toString()
//		        	System.out.println("toString: "+is.getItemMeta().toString());
//		         	//DynamicOps<JsonElement> dyna = CraftRegistry.getMinecraftR
//		    		System.out.println("ItemTag: "+itemtag);
//		    		
//		    		System.out.println("desSeri: "+ItemStack.deserialize(seri).toString());
//		    		 ItemTag itemtag2 = ItemTag.ofNbt(is.getItemMeta() == null ? null : ItemStack.deserialize(seri).toString());
		    		Item it = new Item(is.getType().getKey().toString(),is.getAmount(),itemtag);
		    		Item it2 = new Item(is.getType().getKey().toString(),is.getAmount(),itemtag2);
		    		Item it3 = new Item(is.getType().getKey().toString(),is.getAmount(),itemtag3);
		            cLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, it));
		            cLink2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, it2));
		            cLink3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, it3));
		            cLink.setText(ChatColor.translateAlternateColorCodes('&', cLink.getText()));
		            cLink2.setText("Hola");
		            cLink3.setText("Hola3");
		            //then simply send it to the player
		          player.spigot().sendMessage(cLink);
		          player.spigot().sendMessage(cLink2);
		          player.spigot().sendMessage(cLink3);
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
		
		public static void rand() {
		
			
		
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
