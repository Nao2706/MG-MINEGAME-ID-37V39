package me.top.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.cooldown.Cooldown;
import me.nao.cosmetics.fireworks.Fireworks;
import me.nao.main.game.Main;

public class PointsManager {

	private Main plugin;
	
	public PointsManager(Main plugin) {
		this.plugin = plugin;
	}
	
	
		//AGREGAR PUNTOS SI EL JUGADOR LLEGA A LA META 
		public void addPoints(Player player, int punto) {
	
				FileConfiguration points = plugin.getPoints();
				
				if(points.contains("Players."+player.getName()+".Kills")) {
					int point = points.getInt("Players."+player.getName()+".Kills");
					points.set("Players."+player.getName()+".Kills", point+punto);
					saveAll();
					
				}else {
					
					points.set("Players."+player.getName()+".Kills",punto);
					saveAll();
				}
			
			
		}
		
		
		public void isInTop(Player player) {
			
			
			
			FileConfiguration points = plugin.getPoints();
			
			
			if(points.contains("Players."+player.getName()+".Kills")) {
			// PRIMERA PARTE
			HashMap<String, Integer> scores = new HashMap<>();

			for (String key : points.getConfigurationSection("Players").getKeys(false)) {

				int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
				// SE GUARDAN LOS DATOS EN EL HASH MAP
				scores.put(key, puntaje);

			}

			// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
			List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
					return e2.getValue() - e1.getValue();
				}
			});

			// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR

			int i = 0;
			for (Map.Entry<String, Integer> e : list) {
				
				i++;
				if(i <= 10) {
						if(player.getName().equals(e.getKey())) {
							ClaimReward(player,i);
							break;
						}
	
				}else{
					player.sendMessage(ChatColor.RED+"No estas en el Top 10 de Puntaje de Misiones.");
					break;
				}
			}
			
		}else {
			player.sendMessage(ChatColor.RED+"No tienes ni un Punto para participar en esto.");
			player.sendMessage(ChatColor.RED+"Consigue puntos en las Misiones.");
		}
			
			
		}
		
		
		
	
		
		
		
	public void ClaimReward(Player player,int pos) {
		FileConfiguration cool = plugin.getCooldown();
		FileConfiguration config = plugin.getConfig();
		 List<String> rand = config.getStringList("Random-Reward.List");
		 Random r = new Random();
		
		int tiempo = config.getInt("Time-Reward");// 3 horas 3600
		
		Cooldown c = new Cooldown (plugin, tiempo); 
		String pathtime = "Players."+player.getUniqueId()+".Cooldown-Recompensa";  
		String cooldown = c.getCooldown(player);
	
		if(cooldown.equals("-1")) {
			long millis = System.currentTimeMillis();   
			cool.set(pathtime, millis);
		    plugin.getCooldown().save();
		    
		    Fireworks f = new Fireworks(player);
		    for(int i = 0 ; i < 5; i++) {
		    	 f.spawnMetodoAyi();
		    }
		   
		    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		    console.sendMessage(ChatColor.WHITE+"El Jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" en la Posicion "+ChatColor.GREEN+pos+"#"+ChatColor.WHITE+" del Top Reclamo su Recompensa Diaria.");
			for(int i = 0 ; i < (11 - pos) ;i++) {
		    	
				 String commando = rand.get(r.nextInt(rand.size()));
				 Bukkit.dispatchCommand(console, commando.replaceAll("%player%",player.getName()));
				 console.sendMessage(""+ChatColor.RED+(i+1)+ChatColor.GOLD+" Premio: "+ChatColor.GREEN+commando);
		    }
			
			 player.sendMessage(ChatColor.GOLD+"[Recompensa]: "+ChatColor.GREEN+"Has reclamado tu recompensa felicidades Diaria.");
			 player.sendMessage("Como tu posicion en el Top es: "+ChatColor.GREEN+pos+"# \n"+ChatColor.GOLD+" Recibiras "+ChatColor.GREEN+(11 - pos)+ChatColor.GOLD+" Recompensas.");
			 
			 
		}
		
		// se activa despues de reclamar
		else {
			
			player.sendMessage(ChatColor.GOLD+"[Recompensa]: "+ChatColor.RED+"Tu Proxima Recompensa sera en: "+ChatColor.GREEN+cooldown);
			
		 }
	}
	
	public String TopArmorStand() {
		
		StringBuilder f = new StringBuilder();
		FileConfiguration message = plugin.getMessage();
    	FileConfiguration points = plugin.getPoints();
    	// PRIMERA PARTE
		HashMap<String, Integer> scores = new HashMap<>();

		for (String key : points.getConfigurationSection("Players").getKeys(false)) {

			int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
			// SE GUARDAN LOS DATOS EN EL HASH MAP
			scores.put(key, puntaje);

		}

		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});

		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR

		int i = 0;
		for (Map.Entry<String, Integer> e : list) {

			i++;
			if (i <= message.getInt("Top-Amount-Global")) {
				// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

				if (message.getBoolean("Top.message-top-global")) {
					String texto = message.getString("Top.message-top-global-text");
				
					
					
					f.append( texto.replaceAll("%player%",e.getKey())
							.replace("%pointuser%", e.getValue().toString())
							.replaceAll("%place%", Integer.toString(i))+"\n");
					
					
					
				}

			}
		}
		
		String top = f.toString();
		
		 return ChatColor.translateAlternateColorCodes('&',top);
	}
	
	
	public String PositionArmorStand(int posicion) {
		
		try {
		
		StringBuilder f  = new StringBuilder();
		FileConfiguration message = plugin.getMessage();
    	FileConfiguration points = plugin.getPoints();
    	// PRIMERA PARTE
		HashMap<String, Integer> scores = new HashMap<>();

		for (String key : points.getConfigurationSection("Players").getKeys(false)) {

			int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
			// SE GUARDAN LOS DATOS EN EL HASH MAP
			scores.put(key, puntaje);

		}

		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});

		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
		
		  Entry<String, Integer> p = list.get(posicion);
	
		
				String texto = message.getString("Top.message-top-global-text");
				f.append( texto.replaceAll("%player%",p.getKey())
						.replace("%pointuser%", p.getValue().toString())
						.replaceAll("%place%", Integer.toString(posicion+1)));
			
		
		
		
		
		
		
		String top = f.toString();
		
		 return ChatColor.translateAlternateColorCodes('&',top);
	}catch(IndexOutOfBoundsException e) {
		return ChatColor.RED+"Vacio";
	}
		 
	}
	
	
	
	public void saveAll() {
		plugin.getPoints().save();
		plugin.getPoints().reload();
	}
	
	
}
