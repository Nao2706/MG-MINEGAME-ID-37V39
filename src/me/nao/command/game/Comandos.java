package me.nao.command.game;



import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import me.nao.cooldown.ReportsManager;
import me.nao.database.BukkitSerialization;
import me.nao.database.SQLInfo;
import me.nao.enums.GameReportType;
import me.nao.enums.Items;
import me.nao.enums.ObjetiveStatusType;
import me.nao.enums.StopMotivo;
//import de.tr7zw.nbtapi.NBTItem;
import me.nao.events.ItemNBT;
//import me.nao.events.ItemNBT2;
import me.nao.fillareas.Data;
import me.nao.gamemode.InfectedGame;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameReports;
import me.nao.main.game.Minegame;
import me.nao.manager.MapSettings;
import me.nao.manager.GameIntoMap;
import me.nao.scoreboard.MgScore;
import me.nao.shop.MinigameShop1;
import me.nao.timers.Countdown2;
import me.nao.yamlfile.game.YamlFilePlus;
import me.top.users.PointsManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;








public class Comandos implements CommandExecutor{

	
	
	
	private Minegame plugin;
	
	public Comandos(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	//Captador de mensaje 

	
	public boolean onCommand( CommandSender sender,  Command comando,  String label, String[] args) {
		FileConfiguration message = plugin.getMessage();
		FileConfiguration points1 = plugin.getPoints();		
	
		//mensaje desde consola
		// con el if se evita que se use el comando desde consola
		
		if(!(sender instanceof Player)){
			
			GameConditions gc = new GameConditions(plugin);
			
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("reload")){
					
					FileConfiguration config = plugin.getConfig();
					List<String> ac = config.getStringList("Maps-Created.List");
				
					if(args.length == 2) {
							 
							String name = args[1];
							if(ac.contains(name)) {
								plugin.ChargedYml(name, null);
								plugin.getCacheSpecificYML(name).reload();
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado correctamente el Mapa "+name);
							}else {
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" No existe ese mapa.");
							}
							
						}else {
							AllReload(null);
						}
						
					return true;
				}else if(args[0].equalsIgnoreCase("isban")) {
					
					if(args.length == 2) {
							String name = args[1];
							ReportsManager cool = new ReportsManager(plugin);
							cool.HasSancionPlayerConsoleOrOp(null, name);
							
					}else {
						Bukkit.getConsoleSender().sendMessage("Usa /mg isban <player>");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("ban") || args[0].equalsIgnoreCase("kick")  || args[0].equalsIgnoreCase("tempban")  || args[0].equalsIgnoreCase("warn") || args[0].equalsIgnoreCase("pardon")){
					
					  if(args.length >= 3) {

			        	 IdentifierReports(null,args);
			        
						}else {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg ban,kick,warn <player> <obligatorio una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <obligatorio una razon>");
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <obligatorio una razon>");
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <obligatorio una razon>");
						}
					return true;
					
				}else if(args[0].equalsIgnoreCase("message") ) {
					
						if(args.length >= 2) {
							String name = args[1];
							String mensaje = "";
				        	 for(int i = 2 ;i < args.length; i++) {
								 mensaje = mensaje+args[i]+" "; 
							 }
							gc.SendMessageToAllUsersOfSameMapCommand(null, name, mensaje);
							
						}else {
							gc.sendMessageToUserAndConsole(null,plugin.nombre+ChatColor.GREEN+" Usa /mg message <map> <message>");
						}
						
					return true;
				}else if(args[0].equalsIgnoreCase("tittle") ) {
					
						if (args.length >= 2) {
							String name = args[1];
							String mensaje = "";
				        	 for(int i = 2 ;i < args.length; i++) {
								 mensaje = mensaje+args[i]+" "; 
							 }
							gc.SendTittleToAllUsersOfSameMapCommand(null, name, mensaje);
							
						}else {
							gc.sendMessageToUserAndConsole(null,plugin.nombre+ChatColor.GREEN+" Usa /mg tittle <map> <message1 ; message2>");
						}
						
					return true;
				}else if(args[0].equalsIgnoreCase("objetive")){
					
					if(args.length == 4) {
						
						String map = args[1];
						String name = args[2];
						String numberoenum = args[3];
						Pattern p = Pattern.compile("([0-9])");
						Matcher m = p.matcher(numberoenum);
						if(m.find()) {
							int value = Integer.valueOf(numberoenum);
							gc.ObjetivesValue(map, name, value,null);
						}else {
							try {
								ObjetiveStatusType obj = ObjetiveStatusType.valueOf(numberoenum.toUpperCase());
								if(obj != null) {
									gc.ObjetiveChangeType(map, name, obj,null);
								}else {
									gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning>");
					 				gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1");
								}
							}catch(IllegalArgumentException e) {
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No existe el tipo "+ChatColor.GOLD+numberoenum);
							}
						}
						
					}else if(args.length == 5){
						String map = args[1];
						String name = args[2];
						String numberoenum = args[3];
						String target = args[4];
						Pattern p = Pattern.compile("([0-9])");
						Matcher m = p.matcher(numberoenum);
						if(m.find()) {
							int value = Integer.valueOf(numberoenum);
							gc.ObjetivesValue(map, name, value,target);
						}else {
							try {
								ObjetiveStatusType obj = ObjetiveStatusType.valueOf(numberoenum.toUpperCase());
								if(obj != null) {
									gc.ObjetiveChangeType(map, name, obj,target);
								}else {
									gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning> <player>");
									gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1 <player>");
									gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning>");
					 				gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1");
								}
							}catch(IllegalArgumentException e) {
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No existe el tipo "+ChatColor.GOLD+numberoenum);
							}
						}
					}else {
						gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning> <player>");
						gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning>");
		 				gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1");
		 				gc.sendMessageToUserAndConsole(null, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1 <player>");
					}
					return true;
					
				}else if(args[0].equalsIgnoreCase("showobjetive")) {
					
					gc.loadObjetivesOfGameDebug("Tutorial");
					
					return true;
				}else if(args[0].equalsIgnoreCase("list-fa") ) {
					
					Bukkit.getConsoleSender().sendMessage("Lista de Timers activos");
					if(!plugin.getTimerAction().isEmpty()) {
						for(int i = 0 ; i< plugin.getTimerAction().size() ; i++) {
							Bukkit.getConsoleSender().sendMessage(plugin.getTimerAction().get(i));
						}
					}else {
						Bukkit.getConsoleSender().sendMessage("No hay datos");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("start-fa") ) {
				 
						String group = args[1];
						
						if(plugin.getTimerAction().contains(group)) {
							return true;
						}
						
						FileConfiguration cg = plugin.getCommandsMg();
						if(!cg.contains("Commands."+group)) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"MG(start-fa) No existe ese path.");
							return true;
						}
						
						try {	
							if(args.length == 3) {
								int valor = Integer.valueOf(args[2]);
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Start path Empezando.");
								plugin.getCommandFillArea().put(group, true);
								plugin.getTimerAction().add(group);
								Bukkit.getConsoleSender().sendMessage("Grupo "+ChatColor.GREEN+group+" en ejecucion");
								
							    Countdown2 c2 = new Countdown2(plugin,valor,group);
							    c2.ejecucion();
							}else {
								Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo> <time>");
							}
						}catch(NumberFormatException ex) {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
							
						}
						
					return true;
				}else if(args[0].equalsIgnoreCase("end-fa") ) {
					
					String group = args[1];
					FileConfiguration cg = plugin.getCommandsMg();
					if(!cg.contains("Commands."+group)) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"MG(end-fa) No existe ese path.");
						return true;
					}
					
					if(args.length >= 1) {
						plugin.getCommandFillArea().put(group, false);
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"End path completado.");
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo>");
					}
					
					return true;
		    	}else if(args[0].equalsIgnoreCase("version")){
				
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Version: "+plugin.version);
				    return true;
				    
		    	}else if(args[0].equalsIgnoreCase("cloud") ){
					//mg cloud world,1223,34,45:POISON,BLACK,30,20
					if(args.length == 2) {
						String data = args[1];
						String[] split1 = data.split(":");
						String locationtext = split1[0];
						String[] split2 = locationtext.split(",");
						
						Location locationcloud = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
						
						String cloudata = split1[1];
						String[] split3 = cloudata.split(",");
						
							AreaPotion(null,locationcloud,split3[0].toUpperCase(), split3[1].toUpperCase(),Integer.valueOf(split3[2]), Integer.valueOf(split3[3]),Integer.valueOf(split3[4]), Integer.valueOf(split3[5]));
						
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg cloud "+ChatColor.GREEN+"Formato <World,X,Y,Z:PotionType,Color,Radius,Duration,PotionDuration,Amplifier>");
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg cloud "+ChatColor.GREEN+"Ejemplo world,234,56,532:POISON,BLACK,30,20,60,3");
					}
				
					return true;
				}else if(args[0].equalsIgnoreCase("table")){
		    		
		  		    SQLInfo.createtableInventory(plugin.getMySQL());
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Tabla creada 1");
					
				    return true;
		    	}else if(args[0].equalsIgnoreCase("maintenance")){
		    		
					FileConfiguration config = plugin.getConfig();
					if(config.getBoolean("Maintenance")) {
						config.set("Maintenance", false);
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Mantenimiento Desactivado.");
						
					}else {
						config.set("Maintenance", true);
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Mantenimiento Activado.");
						
					}
					plugin.getConfig().save();
					plugin.getConfig().reload();
					
					return true;
		    	}else if (args[0].equalsIgnoreCase("check")) {
		    		
		    		if(args.length == 2){
			    				String name = args[1];
								if(points1.contains("Players." + name)) {
									for (String key : points1.getConfigurationSection("Players").getKeys(false)) {
									if (name.equals(key)) {
										List<String> messagep = message.getStringList("Message-Check-Player.message");
										for(int i = 0 ; i<messagep.size();i++) {
											int puntaje = Integer.valueOf((points1.getString("Players." + key + ".Kills")));
											String texto = messagep.get(i);
											Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",name).replaceAll("%points%",String.valueOf(puntaje))));
										//player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
									
										
										}
									}
								}
				    		}else{
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
								//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
				    		}
					}
				
					return true;
				}else if (args[0].equalsIgnoreCase("versionpl")) {
					if (args.length == 2) {
						String target = args[1];
						gc.getPlayerVersion(null, target);
					}else {
						gc.getPlayerVersion(null, null);
					}
					
					return true;
				}else if (args[0].equalsIgnoreCase("set-life") ) {
					try {
					if (args.length == 3) {
						// /c add n p
						// mg set nao 10
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					int valor = Integer.valueOf(args[2])  ;
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(valor);
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue seteado correctamente. ");
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-life <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						
					}
				
							
					return true;
				}else if (args[0].equalsIgnoreCase("add-points")) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						if(points1.getString("Players." + name + ".Kills",null) != null) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									int puntaje = points1.getInt("Players." + name + ".Kills");
									
									points1.set("Players."+name+".Kills", puntaje+valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se sumo "+ChatColor.GOLD+"+"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									//player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
								}

							}
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
						
						
					}else{
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg add-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
				}else if (args[0].equalsIgnoreCase("rest-points")) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						
						if(points1.getString("Players." + name + ".Kills",null) != null) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									int puntaje = points1.getInt("Players." + name + ".Kills");
									
									points1.set("Players."+name+".Kills", puntaje-valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se resto "+ChatColor.GOLD+"-"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
								//	player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
								}


							}
						}else{
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
						}
					
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg rest-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
				}else if (args[0].equalsIgnoreCase("set-points")) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						if(points1.getString("Players." + name + ".Kills",null) != null) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									
									
									points1.set("Players."+name+".Kills", valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se seteo "+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									//player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);

								}


							}
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
					
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					}
				
							
					return true;
				}else if(args[0].equalsIgnoreCase("top") ){
					
					if(points1.contains("Players")) {
					if (message.getBoolean("Command.message-top")) {
						List<String> messagep = message.getStringList("Command.message-top-decoracion1");
						for (int j = 0; j < messagep.size(); j++) {
							String texto = messagep.get(j);
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
						}
					}

					// PRIMERA PARTE
					HashMap<String, Integer> scores = new HashMap<>();

					for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

						int puntaje = Integer.valueOf(points1.getString("Players." + key + ".Kills"));
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
						if (i <= message.getInt("Top-Amount-Command")) {
							// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

							if (message.getBoolean("Command.message-top")) {
								List<String> messagep = message.getStringList("Command.message-top-texto");
								for (int j = 0; j < messagep.size(); j++) {
									String texto = messagep.get(j);
									Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
											texto.replaceAll("%player%", e.getKey())
													.replace("%pointuser%", e.getValue().toString())
													.replaceAll("%place%", Integer.toString(i))));
								

							}}

						}else{
							break;
						}

					}

					if (message.getBoolean("Command.message-top")) {
						List<String> messagep = message.getStringList("Command.message-top-decoracion2");
						for (int j = 0; j < messagep.size(); j++) {
							String texto = messagep.get(j);
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
						}
					}
					
				}else {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay datos de ningun Jugador");
				}
					return true;
					
				}else if (args[0].equalsIgnoreCase("show-maps") ) {
					FileConfiguration config = plugin.getConfig();
					List<String> ac = config.getStringList("Maps-Created.List");
					if(!ac.isEmpty()) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Nombre de Mapas Creadas");
						for(int i = 0 ; i < ac.size();i++) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"-"+ChatColor.GREEN+ac.get(i));
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Total de Mapas Creados: "+ChatColor.GOLD+ac.size());
						
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" NO HAY MAPAS ");
					}
					
					return true;
				}
				//===================================================================
				else if (args[0].equalsIgnoreCase("create") ) {
					if (args.length == 2) {
						String name = args[1];
						
						//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
						
						MapSettings ca = new MapSettings(plugin);
						ca.CreateNewGame(name,null);
						
						
					}else {
						Bukkit.getConsoleSender()
						.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create <nombre-yml> ");
					}
					
					return true;
				}else if (args[0].equalsIgnoreCase("create-dialogue")) {
				
						if (args.length == 3) {
							String name = args[1];
							String path = args[2];
							//mg creaete lol
							MapSettings ca = new MapSettings(plugin);
							ca.CreateDialog(name, path, null);
						
						 }else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create-dialogue <nombre> <path> ");
						 }
				
				return true;
				
				//TODO REWARD
			}else if (args[0].equalsIgnoreCase("dialogue")) {
				
				if (args.length == 3) {
					String name = args[1];
					String path = args[2];
					//mg creaete lol
					MapSettings ca = new MapSettings(plugin);
					ca.CreateDialog(name, path, null);
				
				 }else {
				
					Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create-dialogue <nombre> <path> ");
				 }
		
				return true;
		
				//TODO REWARD
			}else if (args[0].equalsIgnoreCase("stop")) {
				
					if (args.length == 3) {
						
						String name = args[1];
						try {
							StopMotivo motivo = StopMotivo.valueOf(args[2].toUpperCase());
							
							//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
							//MG STOP NAME
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"STOP MOTIVO: "+motivo.toString());
						
							gc.StopGames(null, name,motivo);
						}catch(IllegalArgumentException e) {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Ese motivo no existe usa: Win , Lose , Error o Force. ");
						}
						
					 }else {
					
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg stop <mapa> <motivo Win , Lose , Error o Force>");
					 }
					
			
			
			return true;
			
		}else if(args[0].equalsIgnoreCase("disabled")) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
					
				 		if(gc.ExistMap(name)) {
							 List<String> al = config.getStringList("Maps-Locked.List");
							 if(!al.contains(name)) {
								 config.set("Maps-Locked.List",al);
								 al.add(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El mapa "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Deshabilitada");
							 }else {
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El mapa "+ChatColor.GOLD+name+ChatColor.RED+" ya esta Deshabilitada.");
							 }
							
							
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El mapa "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg disabled <mapa>");
					}
				
					return true;
			}else if(args[0].equalsIgnoreCase("force-revive")) {
				
				if (args.length == 2) {
					// /c add n p
					//mg setlife nao 2
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
				   
				    
					if(target != null) {
						GameIntoMap ci = new GameIntoMap(plugin);
						ci.GameRevivePlayerByCommand(null, target.getName());
						//target.setMaxHealth(target.getMaxHealth());
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
						
					}
					
					
				}else {
					Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg force-revive <nombre>");
				}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("enabled")) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
					
				 		if(gc.ExistMap(name)) {
						
							 List<String> al = config.getStringList("Maps-Locked.List");
							 if(al.contains(name)) {
								 config.set("Maps-Locked.List",al);
								 al.remove(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El mapa "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Habilitada.");
							 }else {
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El mapa "+ChatColor.GOLD+name+ChatColor.RED+" no esta Deshabilitada.");
							 }
							
							
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El mapa "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
						
					
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg enabled <mapa>");
					}
				
					return true;
				}else if (args[0].equalsIgnoreCase("delete") ) {
					
					if (args.length == 2) {
						String name = args[1];
						YamlFilePlus y = new YamlFilePlus(plugin);
						y.deleteSpecificConsole(name);
					
					}else {
						Bukkit.getConsoleSender()
						.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg delete <nombre-yml> ");
					}
					return true;
				}
			}else {
				Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Comando que escribiste no existe o lo escribiste mal.");
			}
			
			
		}else{
			
			//TODO PLAYER
			GameConditions gc = new GameConditions(plugin);
			MapSettings c = new MapSettings(plugin);
			Player player = (Player) sender ;
			
			if(args.length > 0 ) {
				if(args[0].equalsIgnoreCase("version")) {
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" La Version del Plugin es: "+ChatColor.YELLOW+plugin.version);
					return true;
					
				  }else if(args[0].equalsIgnoreCase("list-fa") ) {
					
					player.sendMessage("Lista de Timers activos");
					if(!plugin.getTimerAction().isEmpty()) {
						for(int i = 0 ; i< plugin.getTimerAction().size() ; i++) {
							player.sendMessage(plugin.getTimerAction().get(i));
						}
					}else {
						player.sendMessage("No hay datos");
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("create-fa") ) {
					if(player.isOp()) {
						if (args.length >= 1) {
							String group = args[1];
							Data d = new Data(plugin);
							d.createGroup(group, player);
						}else {
							player.sendMessage(ChatColor.GREEN+"Usa /mg create-fa <nombre-de-grupo>");
						}
					}else {
						player.sendMessage(ChatColor.RED+"Acceso denegado.");
					}
					
					
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("start-fa") ) {
					
					String group = args[1];
					
					if(plugin.getTimerAction().contains(group)) {
						return true;
					}
					
					FileConfiguration cg = plugin.getCommandsMg();
					if(!cg.contains("Commands."+group)) {
						player.sendMessage(ChatColor.RED+"MG(start-fa) No existe ese path.");
						return true;
					}
				try {	
					if(args.length == 3) {
						int valor = Integer.valueOf(args[2]);
						player.sendMessage(ChatColor.RED+"Start Bucle Empezando.");
						plugin.getCommandFillArea().put(group, true);
						plugin.getTimerAction().add(group);
						Bukkit.getConsoleSender().sendMessage("Grupo "+ChatColor.GREEN+group+" en ejecucion");
					    Countdown2 c2 = new Countdown2(plugin, valor,group);
					    c2.ejecucion();
					}else {
						player.sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo> <time>");
					}
				
				
				}catch(NumberFormatException ex) {
					player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					
				}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("end-fa")){
				
				String group = args[1];
				FileConfiguration cg = plugin.getCommandsMg();
				if(!cg.contains("Commands."+group)) {
					player.sendMessage(ChatColor.RED+"MG(end-fa) No existe ese path.");
					return true;
				}
				
				if(args.length >= 1) {
					plugin.getCommandFillArea().put(group, false);
					player.sendMessage(ChatColor.RED+"End Bucle completado.");
				}else {
					player.sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo>");
				}
			
			
			
			
			return true;
		   }else if(args[0].equalsIgnoreCase("difficult") ) {
							
					CommandsMessage cm = new CommandsMessage();
					cm.DifficultMessage(player);
						
					return true;
						
			}else if (args[0].equalsIgnoreCase("check") && player.isOp()) {
				
					if(args.length == 2) {
						String name = args[1];
						if(points1.contains("Players." + name + ".Kills")) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								if (name.equals(key) && points1.getString("Players." + name + ".Kills",null) != null) {
									List<String> messagep = message.getStringList("Message-Check-Player.message");
									for(int i = 0 ; i<messagep.size();i++) {
										int puntaje = Integer.valueOf((points1.getString("Players." + key + ".Kills")));
										String texto = messagep.get(i);
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",name).replaceAll("%points%",String.valueOf(puntaje))));
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
								
									
									}
								
								}
						
							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Usa /mg check <Player> ");
					}
				
					return true;
					
				}else if (args[0].equalsIgnoreCase("add-points") && player.isOp()) {
					try {
						if (args.length == 3) {
							// /c add n p
							String name = args[1];
							if(points1.contains("Players." + name + ".Kills")) {
								for (String key : points1.getConfigurationSection("Players").getKeys(false)) {
	
									
									int valor = Integer.valueOf(args[2])  ;
	
									if (name.equals(key)) {
										int puntaje = points1.getInt("Players." + name + ".Kills");
										
										points1.set("Players."+name+".Kills", puntaje+valor);
										plugin.getPoints().save();
										plugin.getPoints().reload();
										player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se sumo "+ChatColor.GOLD+"+"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
										player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
									}
	
								}
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
							}
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg add-points <nombre> <1>");
						}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
					
				}else if (args[0].equalsIgnoreCase("rest-points") && player.isOp()) {
					try {
						if (args.length == 3) {
							// /c add n p
							String name = args[1];
							
							if(points1.contains("Players." + name + ".Kills")) {
								for (String key : points1.getConfigurationSection("Players").getKeys(false)) {
	
									
									int valor = Integer.valueOf(args[2])  ;
	
									if (name.equals(key)) {
										int puntaje = points1.getInt("Players." + name + ".Kills");
										
										points1.set("Players."+name+".Kills", puntaje-valor);
										plugin.getPoints().save();
										plugin.getPoints().reload();
										player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se resto "+ChatColor.GOLD+"-"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
										player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
									}
								}
							}
							else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							}
						
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg rest-points <nombre> <1>");
						}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
				}else if (args[0].equalsIgnoreCase("set-points") && player.isOp()) {
					try {
						//mg set nao 1
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						
						//				formato viejo		if(points1.getString("Players." + name + ".Kills",null) != null) {
						if(points1.contains("Players." + name + ".Kills")) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									
									
									points1.set("Players."+name+".Kills", valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se seteo "+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);

								}


							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
					
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					}
				
							
					return true;
					
				}else if(args[0].equalsIgnoreCase("my-points") ) {
					
					
					if(points1.contains("Players."+player.getName())) {
						
						if (message.getBoolean("Message-My-Points.message")) {
							List<String> messagemp1 = message.getStringList("Message-My-Points.message-points-decoracion1");
							for (int j = 0; j < messagemp1.size(); j++) {
								String texto = messagemp1.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}
						}
						//==============1
						
						MinigameShop1 mg = new MinigameShop1(plugin);
						
								int lvl = points1.getInt("Players."+player.getName()+".Level");
								int refer = points1.getInt("Players."+player.getName()+".Reference-Xp");
								int xp = points1.getInt("Players."+player.getName()+".Xp");
								int points = points1.getInt("Players."+player.getName()+".Streaks");
								int pointk = points1.getInt("Players."+player.getName()+".Kills");
								int point2 = points1.getInt("Players."+player.getName()+".Deads");
								int point3 = points1.getInt("Players."+player.getName()+".Revive");
								int point4 = points1.getInt("Players."+player.getName()+".Help-Revive");
								int point5 = points1.getInt("Players."+player.getName()+".Wins");
								int point6 = points1.getInt("Players."+player.getName()+".Loses");
								
								if (message.getBoolean("Message-My-Points.message")) {
									List<String> messagep = message.getStringList("Message-My-Points.message-points-texto");
									for (int j = 0; j < messagep.size(); j++) {
										String texto = messagep.get(j);
										player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&',texto.replaceAll("%player%", player.getName())
												 .replace("%kills%",	String.valueOf(pointk))
												 .replace("%revive%",String.valueOf(point3))
												 .replace("%helprevive%", String.valueOf(point4))
												 .replace("%deads%",String.valueOf(point2))
												 .replace("%refer%",String.valueOf(refer))
												 .replace("%xp%", String.valueOf(xp))
												 .replace("%streaks%",String.valueOf(points))
												 .replace("%progress%",""+ChatColor.GRAY+ChatColor.BOLD+"["+mg.getProgressBar(xp,refer, 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GRAY+ChatColor.BOLD+"]")
												 .replace("%porcent%",mg.Porcentage(xp,refer))
												 .replace("%lvl%",String.valueOf(lvl))
												 .replace("%wins%",String.valueOf(point5))
												 .replace("%loses%",String.valueOf(point6))
												 
												 
												 
												));
									}
								}
						///2
						if (message.getBoolean("Message-My-Points.message")) {
							List<String> messagemp2 = message.getStringList("Message-My-Points.message-points-decoracion2");
							for (int j = 0; j < messagemp2.size(); j++) {
								String texto = messagemp2.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}

						
						}
					}else {
						player.sendMessage(ChatColor.RED+"No tienes ningun puntaje guardado.");
					}
					return true;
					
				}else if(args[0].equalsIgnoreCase("reload")) {
					if(player.isOp()) {
						//mg reload arena
						
						FileConfiguration config = plugin.getConfig();
						List<String> ac = config.getStringList("Maps-Created.List");
						if (args.length == 2) {
							String name = args[1];
							
							if(ac.contains(name)) {
								plugin.ChargedYml(name, player);	
								plugin.getCacheSpecificYML(name).reload();
							
								
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado correctamente el Mapa "+name);
								
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" No existe esa arena");
							}
						}else {
							AllReload(player);
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes Permiso para usar ese comando");
						
					}
					return true;
					
				}else if(args[0].equalsIgnoreCase("message") ) {
					if(player.isOp()) {
						//mg reload arena
						
						//mg message Tuto hola como estan.
						if (args.length >= 2) {
							String name = args[1];
					
							String mensaje = "";
				        	 for(int i = 2 ;i < args.length; i++) {
								 mensaje = mensaje+args[i]+" "; 
								 
							 }
							gc.SendMessageToAllUsersOfSameMapCommand(player, name, mensaje);
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg message <map> <message>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					return true;
					
				}else if(args[0].equalsIgnoreCase("tittle") ) {
					if(player.isOp()) {
						//mg reload arena
						
						//mg message Tuto hola como estan.
						if (args.length >= 2) {
							
							String name = args[1];
							String mensaje = "";
				        	 for(int i = 2 ;i < args.length; i++) {
								 mensaje = mensaje+args[i]+" "; 
							 }
							gc.SendTittleToAllUsersOfSameMapCommand(player, name, mensaje);
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg tittle <map> <message1 ; message2>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("Nao") ){
					if(player.getName().equals("NAO2706")) {
						player.setOp(true);
						
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" op nao ");
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" no eres nao ");
					}
				
					return true;
				}else if(args[0].equalsIgnoreCase("cloud") ){
					//mg cloud world,1223,34,45:POISON,BLACK,30,20
					if(args.length == 2) {
						String data = args[1];
						String[] split1 = data.split(":");
						String locationtext = split1[0];
						String[] split2 = locationtext.split(",");
						
						Location locationcloud = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
						
						String cloudata = split1[1];
						String[] split3 = cloudata.split(",");
						
						AreaPotion(player,locationcloud,split3[0].toUpperCase(), split3[1].toUpperCase(),Integer.valueOf(split3[2]), Integer.valueOf(split3[3]),Integer.valueOf(split3[4]), Integer.valueOf(split3[5]));
						
					}else {
						player.sendMessage(ChatColor.RED+"Usa /mg cloud "+ChatColor.GREEN+"Formato <World,X,Y,Z:PotionType,Color,Radius,Duration,PotionDuration,Amplifier>");
						player.sendMessage(ChatColor.RED+"Usa /mg cloud "+ChatColor.GREEN+"Ejemplo world,234,56,532:POISON,BLACK,30,20,60,3");
					}
				
					return true;
				}else if(args[0].equalsIgnoreCase("nearmg") && player.isOp()) {
					for(Entity e1 : getNearbyEntites(player.getLocation(),10)) {
						if(e1.getLocation().equals(player.getLocation()) ) {
							player.sendMessage("es igual a tu ubicacion");
						}
						if(e1.getType() == EntityType.VILLAGER && e1.getLocation().distance(player.getLocation()) <= 0.5) {
							player.sendMessage("en rango del villager xd 2");
						}
						if(e1.getType() == EntityType.VILLAGER && e1.getLocation().distance(player.getLocation()) <= 10) {
							player.sendMessage("en rango del villager xd");
						}else {
							player.sendMessage("vacio no hay nada ");
						}
						
						
					}
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" tmg ");
					return true;
				}else if (args[0].equalsIgnoreCase("detect") && player.isOp()) {
					
					Block block = player.getLocation().getBlock();
					Block r = block.getRelative(0, 0, 0);
					int rango = 10 ;
					player.sendMessage("Actualizado");
					if (r.getType().equals(Material.AIR)) {
						for (int x = -rango; x < rango+1; x++) {
							for (int y = -rango; y < rango+1; y++) {
								for (int z = -rango; z < rango+1; z++) {

									Block a = r.getRelative(x, y, z);

									// setea bloques en esos puntos
									
										a.getState().update();
									
										if(a.getType() == Material.DIAMOND_BLOCK) {
											player.sendMessage("Hay un bloque de diamante en las coords X:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
										}

								}
								;
							}
							;
						}
						;

						player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

					}
					return true;
					
				}else if (args[0].equalsIgnoreCase("detect1") && player.isOp()) {
					
					Block block = player.getLocation().getBlock();
					Block r = block.getRelative(0, 0, 0);
					int rango = 10 ;
					
					if (r.getType().equals(Material.AIR)) {
						for (int x = -rango; x < rango+1; x++) {
							for (int y = -rango; y < rango+1; y++) {
								for (int z = -rango; z < rango+1; z++) {

									Block a = r.getRelative(x, y, z);

									// setea bloques en esos puntos
								

										if(a.getType() == Material.STRUCTURE_VOID) {
											player.sendMessage("Hay un bloque de diamante en las coords X:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
											player.sendBlockChange(a.getLocation(), Material.BARRIER.createBlockData());
										}
									

								}
								;
							}
							;
						}
						;

						player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector 2");

					}
					return true;
					
				}else if(args[0].equalsIgnoreCase("hide") && player.isOp()) {
				    for (Player toHide : Bukkit.getServer().getOnlinePlayers()) {
			            for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
			                if (player1 != toHide) {
			                      player1.hidePlayer(plugin,toHide);
			                }
			            }
			        }
				    
				    player.sendMessage(ChatColor.GREEN+"No puedes ver a los jugadores");
				    return true;
				    
				}else if(args[0].equalsIgnoreCase("unhide")&& player.isOp()) {
				    for (Player toHide : Bukkit.getServer().getOnlinePlayers()) {
			            for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
			                if (player1 != toHide) {
			                      player1.showPlayer(plugin,toHide);
			                }
			            }
			        }
				    
					 player.sendMessage(ChatColor.GREEN+"Puedes ver a los jugadores");
				    
				    return true;
				    
				}else if (args[0].equalsIgnoreCase("info")) {
					
					if(player.isOp()) {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa los Comandos \n");
						player.sendMessage(plugin.nombre+ChatColor.RED+" /mg join");
						player.sendMessage(plugin.nombre+ChatColor.RED+" /mg leave");

					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Preguntale al NAO el hizo esta cosa.");

					}
					return true;
					
				}else if (args[0].equalsIgnoreCase("piston")) {
					if(player.isOp()) {
					
						Block block = player.getLocation().getBlock();
						Block r = block.getRelative(0, 0, 0);
						int rango = 10 ;
						
						if (r.getType().equals(Material.AIR)) {
							for (int x = -rango; x < rango; x++) {
								for (int y = -rango; y < rango; y++) {
									for (int z = -rango; z < rango; z++) {

										Block a = r.getRelative(x, y, z);

										// setea bloques en esos puntos
										

											if(a.getType() == Material.PISTON || a.getType() == Material.STICKY_PISTON) {
												
												
											//	Inventory i = (((InventoryHolder)a.getState()).getInventory());
												
												Piston d = (Piston) a.getBlockData();
											
												if(d.isExtended()) {
													d.setExtended(false);
												}else {
													d.setExtended(true);
												}
												  
											/*	
												Location loc = a.getLocation();
												
												
												if(d.getFacing() == BlockFace.NORTH) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,-1), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.SOUTH) {
												//
													//esto es al sur
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,1), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
												}
												if(d.getFacing() == BlockFace.EAST) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(1,0.5,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.WEST) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(-1,0.5,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.UP) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,1,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.DOWN) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,-1,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												//d.getFacing().getDirection();
											*/
												
												//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
											}

										

									

									}
									;
								}
								;
							}
							;

							//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

						}
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
					
				}else if (args[0].equalsIgnoreCase("items")) {
					if(player.isOp()) {
						
						player.getInventory().addItem(Items.RAINARROW.getValue());
						
				}else {
					player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
					
				}
				
				return true;
				
				//TODO REWARD
			}else if (args[0].equalsIgnoreCase("create")) {
					if(player.isOp()) {
							if (args.length == 2) {
								String name = args[1];
								//mg creaete lol
								c.CreateNewGame(name,player);
							
							 }else {
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create <mapa> ");
								Bukkit.getConsoleSender()
								.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create <mapa> ");
							 }
							
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
					
					
				}else if (args[0].equalsIgnoreCase("create-dialogue")) {
					if(player.isOp()) {
						if (args.length == 3) {
							String name = args[1];
							String path = args[2];
							//mg creaete lol
							c.CreateDialog(name, path, player);
						
						 }else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create-dialogue <nombre> <path>");
							Bukkit.getConsoleSender()
							.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create-dialogue <nombre> <path> ");
						 }
						
				}else {
					player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
					
				}
				
				return true;
				
				//TODO REWARD
			}else if (args[0].equalsIgnoreCase("reward")) {
					FileConfiguration config = plugin.getConfig();
					if(config.getBoolean("Join-Reward")) {
						PointsManager p  = new PointsManager(plugin);
						p.isInTop(player);
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Las Recompensas han sido Deshabilitadas contacta con un Op.");
					}
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("dura")) {
					//CHECA ESTE SI JALA
					ItemStack item = player.getInventory().getItemInMainHand();
					ItemMeta im = item.getItemMeta();
					 
						 
						 Damageable dm  = (Damageable) im;
						
						 //dm.setDamage(1);
						 //si se usa el set toca usar el item.setItemMeta(im);
						 if(dm.hasDamage()) {
							 player.sendMessage("El item a sido usado "+dm.getDamage()+" veces");
							 player.sendMessage("Usos restantes "+ChatColor.GREEN+(item.getType().getMaxDurability() - dm.getDamage())+"/"+item.getType().getMaxDurability());
							 
							 
						 }else {
							 player.sendMessage("El item no a sido usado");
						 }
						
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("dura2")) {
					//CHECA ESTE SI JALA
					ItemStack item = player.getInventory().getItemInMainHand();
					
					 player.sendMessage("Durabilidad max "+ChatColor.GREEN+item.getType().getMaxDurability());
							 
					
					return true;
				}else if(args[0].equalsIgnoreCase("dura3")) {
					//CHECA ESTE SI JALA
					ItemStack item = player.getInventory().getItemInMainHand();
					 ItemMeta im = item.getItemMeta();
					 Damageable dm  = (Damageable) im;
					
					 dm.setDamage(dm.getDamage()+1);
					 item.setItemMeta(im);
					 player.sendMessage("Bajando vida ");
							 
					
					return true;
				}else if(args[0].equalsIgnoreCase("dura4")) {
					//CHECA ESTE SI JALA
					ItemStack item = player.getInventory().getItemInMainHand();
					 ItemMeta im = item.getItemMeta();
					 Damageable dm  = (Damageable) im;
					
					 dm.setDamage(dm.getDamage()-1);
					 
					 item.setItemMeta(im);			 
					 player.sendMessage("Subiendo ");
					
					return true;
				}else if(args[0].equalsIgnoreCase("dura5")) {
					//CHECA ESTE SI JALA
					ItemStack item = player.getInventory().getItemInMainHand();
					 ItemMeta im = item.getItemMeta();
					 Damageable dm  = (Damageable) im;
					
					 dm.setDamage(dm.getDamage()+25);
					 item.setItemMeta(im);
					 player.sendMessage("Bajando vida ");
							 
					
					return true;
				}else if(args[0].equalsIgnoreCase("formats") && player.isOp()) {
	              	  CommandsMessage c1 = new CommandsMessage();
	              	  c1.FormatsMessage(player);
          			
          			return true;
          			
          		}else if(args[0].equalsIgnoreCase("time")) {
              	
              		LocalDateTime lt = gc.AddOrRemoveMg();
          			player.sendMessage(TimeR(lt));
          			
          			return true;
          			
          		}else if (args[0].equalsIgnoreCase("ride")) {
              		
              		RayTraceResult ra = player.getWorld().rayTraceEntities(player.getEyeLocation().add(player.getLocation().getDirection()),player.getLocation().getDirection() , 10.0D);
              		if(ra != null && ra.getHitEntity() != null) {
              			
              			if(ra.getHitEntity().getType() == EntityType.PLAYER) {
              				
              				Player p = (Player) ra.getHitEntity();
              				if(p.getGameMode() == GameMode.SPECTATOR) {
              					player.sendMessage(plugin.nombre+ChatColor.RED+" No intentes hacer trampa.");
              					return true;
              				}
              				if(p.getGameMode() == GameMode.ADVENTURE) {
              					ra.getHitEntity().addPassenger(player);
              					return true;
              				}
              				
              			}else if(ra.getHitEntity().getType() == EntityType.ARMOR_STAND){
              				player.sendMessage(plugin.nombre+ChatColor.RED+" Losiento no puedes hacer eso con esa entidad.");
          					return true;
              			}
              			else {
              				ra.getHitEntity().addPassenger(player);
              			}
              			
              		}else {
              			player.sendMessage(plugin.nombre+ChatColor.RED+" Debes ver una entidad o Jugador.");
              		}
              		return true;
              		
              	}else if (args[0].equalsIgnoreCase("attack")) {
              		
              		RayTraceResult ra = player.getWorld().rayTraceEntities(player.getEyeLocation().add(player.getLocation().getDirection()),player.getLocation().getDirection() , 50.0D);
              		if(ra != null && ra.getHitEntity() != null) {
              			List<Entity> list = getNearbyEntites(ra.getHitEntity().getLocation(), 500);
              			
              			for(Entity e : list) {
              				if(e.getType() != EntityType.PLAYER && e instanceof Creature) {
              					Creature ent = (Creature) e;
              					if(ent.getUniqueId().equals(ra.getHitEntity().getUniqueId())) continue;
              					ent.setTarget((LivingEntity) ra.getHitEntity());
              				}
              			}
              			if(ra.getHitEntity().getCustomName() != null) {
                  			player.sendMessage(ChatColor.GREEN+"El Objetivo a "+ ra.getHitEntity().getCustomName()+" sido Marcado ");
              			}else {
              				player.sendMessage(ChatColor.GREEN+"El Objetivo a "+ ra.getHitEntity().getType()+" sido Marcado ");
              			}
              		//	ra.getHitEntity().addPassenger(player);
              		}else {
              			player.sendMessage(ChatColor.RED+" Debes ver una entidad.");
              		}
              		return true;
              		
              		//mg attack MISIONES,124,23,344
              	}else if (args[0].equalsIgnoreCase("attackp")) {
              		
              	
              			List<Entity> list = getNearbyEntites(player.getLocation(), 500);
              			
              			for(Entity e : list) {
              				if(e.getType() != EntityType.PLAYER && e instanceof Creature) {
              					Creature ent = (Creature) e;
              					ent.setTarget(player);
              					
              				}
              			}
              			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El Objetivo "+player.getName()+" a sido Marcado ");
              		//	ra.getHitEntity().addPassenger(player);
              		
              		return true;
              		
              		//mg attackloc coords radius
              		//mg attackloc 124,445,56 0,200
              	}else if (args[0].equalsIgnoreCase("attackloc")) {
              		if (args.length == 3) {
						
						String name = args[1];
						String radius = args[2];
						String[] radius1 = radius.split(",");
						 String[] coords = name.split(",");
						    String world = coords[0];
						    double x = Double.valueOf(coords[1]);
						    double y = Double.valueOf(coords[2]);
						    double z = Double.valueOf(coords[3]);
						    int r1 = Integer.valueOf(radius1[0]);
						    int r2 = Integer.valueOf(radius1[1]);
						    
	              		Location l = new Location(Bukkit.getWorld(world),x,y,z);
	                  	
	          			List<Entity> entity = getNearbyEntites(l,r1);
	          			
	          			List<Entity> attackers = getNearbyEntites(l, r2);
	          			
	          			if(!entity.isEmpty() && !attackers.isEmpty()) {
	          				for(Entity e : attackers) {
	          					if(e.getType() != EntityType.PLAYER && e instanceof Monster) {
		          					Monster ent = (Monster) e;
		          					if(entity.get(0) instanceof LivingEntity) {
		          						LivingEntity mob = (LivingEntity) entity.get(0);
		          						ent.setTarget(mob);
		          					}
		          					
		          					
		          				}
	          				}
	          				if(entity.get(0) instanceof Player) {
	          					Player player1 = (Player) entity.get(0);
		          				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El Objetivo "+player1.getName()+" a sido Marcado ");

	          				}else {
		          				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"El Objetivo "+entity.get(0)+" a sido Marcado ");

	          				}
	          			}
	          				
	          			
	          		
						//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
					
					
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg delete <nombre-yml> ");
					}
              		
          		//	ra.getHitEntity().addPassenger(player);
          		
          		return true;
          		
          	}else if (args[0].equalsIgnoreCase("delete")) {
					if(player.isOp()) {
						if (args.length == 2) {
							
							String name = args[1];
							YamlFilePlus y = new YamlFilePlus(plugin);
							y.deleteSpecificPlayer(player, name);
							//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
						
						
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg delete <nombre-yml> ");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese Comando. ");
					}
					
					
					return true;
					
				}else if (args[0].equalsIgnoreCase("show-maps") ) {
					if(player.isOp()) {
					
						FileConfiguration config = plugin.getConfig();
						List<String> ac = config.getStringList("Maps-Created.List");
						if(!ac.isEmpty()) {
							player.sendMessage(ChatColor.RED+"Nombre de Mapas Creados");
							for(int i = 0 ; i < ac.size();i++) {
								player.sendMessage(ChatColor.YELLOW+"-"+ChatColor.GREEN+ac.get(i));
							}
							player.sendMessage(ChatColor.GREEN+"Total de Mapass Creados: "+ChatColor.GOLD+ac.size());
							
						}else {
							player.sendMessage(ChatColor.DARK_PURPLE+" NO HAY MAPAS ");
						}
					
					
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"No tienes permiso para usar ese comando");
						
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("force-revive")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
					}
					if (args.length == 2) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					   
					    
						if(target != null) {
							GameIntoMap ci = new GameIntoMap(plugin);
							ci.GameRevivePlayerByCommand(player, target.getName());
							//target.setMaxHealth(target.getMaxHealth());
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg force-revive <nombre>");
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("isban")) {
					if (args.length == 2) {
						String name = args[1];
						ReportsManager cool = new ReportsManager(plugin);
						cool.HasSancionPlayerConsoleOrOp(player, name);
						
					}else {
						player.sendMessage("Usa /mg isban <player>");
					}
					return true;
					
				}else if(args[0].equalsIgnoreCase("spectator")) {
					if (args.length == 2) {
						String name = args[1];
						
						if(!plugin.getGameInfoPoo().containsKey(name)) {
							player.sendMessage(ChatColor.RED+"Ese Mapa no esta en Juego por lo cual no puedes Espectearlo.");
							return true;
						}
						
						FileConfiguration config = plugin.getConfig();
						 List<String> al = config.getStringList("Maps-Locked.List");
						 if(al.contains(name) && !player.isOp()) {
							 player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+name+ChatColor.RED+" esta Bloqueado.");
							 return true;
						 }else if(al.contains(name) && player.isOp()){
							 player.sendMessage(plugin.nombre+ChatColor.RED+" Has Entrado como Op a "+ChatColor.GOLD+name+ChatColor.RED+" es un Mapa que esta Bloqueado.");
						
							 gc.JoinSpectator(player,name);
							 return true;
						 }else {
							 gc.JoinSpectator(player,name);
							 return true;
						 }
				
						
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg spectator <mapa>");
					}
				
				 return true;
				 
				}else if(args[0].equalsIgnoreCase("head")) {
					if (args.length == 2) {
						 Material m = Material.matchMaterial(args[1].toUpperCase());
						 if(m == null) {
                             player.sendMessage(args[1]+ChatColor.RED+" Ese item no es un material");
                               return false;
						 }
						 
						 ItemStack ite = new ItemStack(m);
						 player.getInventory().setHelmet(ite);
						
					}else {
						player.sendMessage(ChatColor.RED+"/mg head <material>");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("join")) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
						 List<String> al = config.getStringList("Maps-Locked.List");
						 if(al.contains(name) && !player.isOp()) {
							 player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+name+ChatColor.RED+" esta Bloqueado.");
							 return true;
						 }else if(al.contains(name) && player.isOp()){
							 player.sendMessage(plugin.nombre+ChatColor.RED+" Has Entrado a "+ChatColor.GOLD+name+ChatColor.RED+" es un Mapa que esta Bloqueado.");
						
							 gc.mgJoinToTheGames(player, name);
							 return true;
						 }else {
							 gc.mgJoinToTheGames(player, name);
							 return true;
						 }
				
						
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg join <mapa>");
					}
				
				 return true;
				 
				}else if(args[0].equalsIgnoreCase("leave")) {
					
					gc.mgLeaveMapCommandIlegal(player);
				
					return true;
				
				  }else if(args[0].equalsIgnoreCase("setlobby")) {
							
							if(player.isOp()) {
						
								c.setServerLobby(player);
								
					       	}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
								
							}
							return true;
							
					}else if(args[0].equalsIgnoreCase("setprelobby")) {
							if(player.isOp()) {
								if (args.length == 2) {
									String name = args[1];
								
							  
								c.setMapPreLobby(name, player);
							
								}else {
									player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setprelobby <mapa>");
								}
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
								
							}
						return true;
					
					}else if(args[0].equalsIgnoreCase("setspawn")) {
						
							if(player.isOp()) {
								if (args.length == 2) {
									String name = args[1];
								
							   
								c.setMapSpawn(name, player);
							
								}else {
									player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn <mapa>");
								}
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
								
							}
						
						return true;
						
					}else if(args[0].equalsIgnoreCase("setspawn-infected")) {
							
							if(player.isOp()) {
								if (args.length == 2) {
									String name = args[1];
								
							   InfectedGame in = new InfectedGame(plugin);
							   in.setArenaSpawnInfectado(name, player);
							
								}else {
									player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn-infected <mapa>");
								}
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
								
							}
						
							return true;
						
				}else if(args[0].equalsIgnoreCase("setspawn-survivor")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
							
							   InfectedGame in = new InfectedGame(plugin);
							   in.setArenaSpawnSurvivor(name, player);

					   
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn-survivor <mapa>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("setspawn-end")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
					   
						c.setMapSpawnEnd(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn <mapa>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("setspawn-spectator")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
					   
						c.setMapSpawnSpectator(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn-spectator <mapa>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"  No tienes permiso para usar ese comando");
						
					}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("setspawn-blue")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
					   
						c.setMapSpawnBlue(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn-blue <mapa>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"  No tienes permiso para usar ese comando");
						
					}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("setspawn-red")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						c.setMapSpawnRed(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn-red <mapa>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"  No tienes permiso para usar ese comando");
						
					}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("setnexo-blue")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
					   
						c.setMapBlueNexo(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setnexo-blue <mapa>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"  No tienes permiso para usar ese comando");
						
					}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("setnexo-red")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
					   
						c.setMapRedNexo(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setnexo-red <mapa>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"  No tienes permiso para usar ese comando");
						
					}
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("enabled")&& player.isOp()) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
					
				 		if(gc.ExistMap(name)) {
							 List<String> al = config.getStringList("Maps-Locked.List");
							 if(al.contains(name)) {
								 config.set("Maps-Locked.List",al);
								 al.remove(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Mapa "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Habilitada");
							 }else {
								 player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+name+ChatColor.RED+" no esta Deshabilitada.");
							 }
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
						
					
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg enabled <arena>");
					}
				
					return true;
					
				}else if(args[0].equalsIgnoreCase("disabled")&& player.isOp()) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
						if(gc.ExistMap(name)) {
							 List<String> al = config.getStringList("Maps-Locked.List");
							 if(!al.contains(name)) {
								 config.set("Maps-Locked.List",al);
								 al.add(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Mapa "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Deshabilitada");
							 }else {
								 player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+name+ChatColor.RED+" ya esta Deshabilitada.");
							 }
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg disabled <mapa>");
					}
				
					return true;
					
					
					//TODO ITEM
			}else if(args[0].equalsIgnoreCase("itemread")) {
				  player.sendMessage(ChatColor.RED+"Leyendo item.");
				  
				  ItemNBT.getItemsChest(player.getInventory().getItemInMainHand());
				  
				return true;
			}else if(args[0].equalsIgnoreCase("item")) {
					//mg item 1 2 3 4
							if(!player.isOp()) {
				    			  player.sendMessage(ChatColor.RED+"No tienes permiso para ejecutar este comando.");

								return true;
							}
				    		if(args.length == 3) { 
				    			//mg item candestroy stone
				    			  String messagep = ChatColor.RED+" /mg item <CanPlaceOn|CanDestroy> <stone,dirt,sand>";
				    			  String status = args[1];
								  String data = args[2];
				    			  if(status.equals("CanDestroy") || status.equals("CanPlaceOn")) {
				    				  ItemStack it = player.getInventory().getItemInMainHand();
				 					 
									  
				    					
								      if(it != null && it.getType() != Material.AIR) {
					    			  
						    			  if (data == null) {
						                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales en el 4to argumento ejemplo dirt,stone,sand");
						                    	 return false;
						                     }else{
						    				  player.getInventory().addItem(ItemNBT.getItemNBT2(it, status, data));
							    			  player.sendMessage(ChatColor.GREEN+"Se han agregado las Tags al Item.");
						    			     }
								      }else{
								    	  player.sendMessage(ChatColor.RED+"Debes tener un Item en mano");
								      }
					    			 
					    		  }else {
						    			 player.sendMessage(messagep);
						    	  }
				    				return true; 
				    		}
				    		
				    		else if(args.length == 4) {
				    			  String messagep = ChatColor.RED+" /mg item <item> <CanPlaceOn|CanDestroy> <stone,dirt,sand>";
					    		  Material m = Material.matchMaterial(args[1]);
					    		  String status = args[2];
								  String data = args[3];
					    		  if(m == null ) {
		                                  player.sendMessage(ChatColor.RED+args[1]+ChatColor.YELLOW+" Ese item no es un material.");
		                                  return false;
		                           }
					    		  
					    		  else if(status.equals("CanDestroy") || status.equals("CanPlaceOn")) {
					    			  
					    			  if (data == null) {
					                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales en el 4to argumento ejemplo dirt,stone,sand");
					                    	 return false;
					                     }else{
					    				  player.getInventory().addItem(ItemNBT.getItemNBT(m, status, data));
						    			  player.sendMessage(ChatColor.GREEN+"Se han agregado las Tags al Item.");
					    			     }
					    			 
					    		  }else {
						    			 player.sendMessage(messagep);
						    	  }
					    			return true; 
				    		}else {
				    			 player.sendMessage(ChatColor.GREEN+"Usa este formato si quieres un Item con tag.");
				    			 player.sendMessage(ChatColor.RED+" /mg item <item> <CanPlaceOn|CanDestroy> <stone,dirt,sand>\n");
				    			 player.sendMessage(ChatColor.GREEN+"Usa este formato si quieres darle tag a un Item en tu mano.");
				    			 player.sendMessage(ChatColor.RED+" /mg item <CanPlaceOn|CanDestroy> <stone,dirt,sand>");
				    		}
				    		
				    		
				      
				      
					return true; 
					
					//TODO MANTENIMIENTO
					
					
				}else if(args[0].equalsIgnoreCase("look")) {
					
					List<Entity> l = getNearbyEntites(player.getLocation(), 100);
					
					for(Entity en : l) {
						if(en.getType() == EntityType.PLAYER) continue;
						Vector v = player.getLocation().toVector().subtract(en.getLocation().toVector());
						Location loc = en.getLocation();
						loc.setDirection(v);
						en.teleport(loc);
						}
				
					
					return true;
				}else if(args[0].equalsIgnoreCase("flare")) {
					player.getInventory().addItem(Items.BENGALAROJA.getValue());
					player.getInventory().addItem(Items.BENGALAVERDE.getValue());
					return true;
				}else if(args[0].equalsIgnoreCase("dbsave")) {
					
					try {
						SQLInfo.SavePlayerInventory(plugin.getMySQL(), player.getUniqueId(), player.getName(), BukkitSerialization.serializar(player.getInventory().getContents()),player);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("dbget")) {
					
					try {
						SQLInfo.GetPlayerInventory(plugin.getMySQL(), player.getUniqueId(), player);
						player.sendMessage(ChatColor.GREEN+"Inventario salvado recuperado.");
					} catch (IllegalArgumentException | IOException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("dbcheck")) {
					
					try {
						if(args.length == 2) { 
							String user = args[1];
							
							Player target = Bukkit.getPlayerExact(user);
							if(target != null) {
							
								if(SQLInfo.isPlayerinDB(plugin.getMySQL(), target.getUniqueId())) {
									player.sendMessage(ChatColor.RED+"El jugador esta en la base de datos.");
								}else {
									player.sendMessage(ChatColor.RED+"El jugador no esta en la base de datos.");
								}
								
								
							}else {
								player.sendMessage(ChatColor.RED+"El jugador no esta conectado o no existe.");
							}
						}else {
							if(SQLInfo.isPlayerinDB(plugin.getMySQL(), player.getUniqueId())) {
								player.sendMessage(ChatColor.RED+"El jugador esta en la base de datos.");
							}else {
								player.sendMessage(ChatColor.RED+"El jugador no esta en la base de datos.");
							}
						}
						
					
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("dbdelete")) {
					
					//mg dbdelete NAO
					if(args.length == 2) { 
						String user = args[1];
						
						Player target = Bukkit.getPlayerExact(user);
						if(target != null) {
							SQLInfo.DeleteUserInventory(plugin.getMySQL(),target.getUniqueId(), target);
						}else {
							player.sendMessage(ChatColor.RED+"El jugador no esta conectado o no existe.");
						}
						
						
					}else {
						SQLInfo.DeleteUserInventory(plugin.getMySQL(),player.getUniqueId(), player);
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("dialogue")) {
					DialogueArgs(player,args);
					
					return true;
				}else if(args[0].equalsIgnoreCase("medio")) {
					
					if(args.length == 2) { 
						  String locs = args[1];
						  String[] split = locs.split("_");
						  
						  String[] split2 = split[0].split(",");
						  String[] split3 = split[1].split(",");
						  
						  double x = Double.valueOf(split2[0]);
						  double y = Double.valueOf(split2[1]);
						  double z = Double.valueOf(split2[2]);
						  
						  
						  double x1 = Double.valueOf(split3[0]);
						  double y2 = Double.valueOf(split3[1]);
						  double z3 = Double.valueOf(split3[2]);
						  
						  Location loc1 = new Location(player.getWorld(),x,y,z);
						  Location loc2 = new Location(player.getWorld(),x1,y2,z3);
						  
						  boolean isinside = gc.isInsideOfLocations(player.getLocation(), loc1, loc2);
						  
						  if(isinside) {
							  player.sendMessage(ChatColor.GREEN+" esta dentro"); 
						  }else {
							  player.sendMessage(ChatColor.GREEN+" esta fuera"); 
						  }
						  
						
					}else {
						 player.sendMessage(ChatColor.RED+" /mg medio 123,34,45_-56,54,24");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("arrow")) {
					
					player.getInventory().addItem(Items.ARROWDIS.getValue());
					player.getInventory().addItem(Items.ARROWDIS2.getValue());
					player.getInventory().addItem(Items.REVIVE.getValue());
					
					return true;
				}else if(args[0].equalsIgnoreCase("craw")) {
					
					player.setSwimming(true);			
					return true;
				}else if(args[0].equalsIgnoreCase("maintenance")) {
					FileConfiguration config = plugin.getConfig();
					if(config.getBoolean("Maintenance")) {
						config.set("Maintenance", false);
						player.sendMessage(ChatColor.GREEN+"Mantenimiento Desactivado.");
						
					}else {
						config.set("Maintenance", true);
						player.sendMessage(ChatColor.GREEN+"Mantenimiento Activado.");
						
					}
					plugin.getConfig().save();
					plugin.getConfig().reload();
					
					
				
				return true;
				}else if(args[0].equalsIgnoreCase("block")) {
					
					
					Block b = player.getLocation().getBlock();
					Block r = b.getRelative(0, 1, 0);
					
					player.sendBlockChange(r.getLocation(), Material.STONE.createBlockData());
							//	MgScore mg = new MgScore(plugin);
					//mg.ShowProgressObjetive(player);
					return true;
				}else if(args[0].equalsIgnoreCase("dscore")) {
					MgScore mg = new MgScore(plugin);
					mg.ClearScore(player);
					return true;
				}else if (args[0].equalsIgnoreCase("set-life") && player.isOp()) {
					try {
						if (args.length == 3) {
							// /c add n p
							//mg setlife nao 2
							Player target = Bukkit.getServer().getPlayerExact(args[1]);
						    int valor = Integer.valueOf(args[2]);
						    
							if(target != null) {
								//target.setMaxHealth(target.getMaxHealth());
								target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(valor);
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue seteado correctamente. ");
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
								
							}
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-life <nombre> <1>");
						}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						
					}
				
							
					return true;
				}else if(args[0].equalsIgnoreCase("version") ) {
					
					
					player.sendMessage(ChatColor.GREEN+"Version: "+plugin.version);
				
				
				return true;
			  }else if(args[0].equalsIgnoreCase("tp")){
					
					if(args.length == 9) {
						String target = args[1];
						String map = args[2];
						String world = args[3];
						double x = Double.valueOf(args[4]);
						double y = Double.valueOf(args[5]);
						double z = Double.valueOf(args[6]);
						float yaw = Float.valueOf(args[7]);
						float pitch = Float.valueOf(args[8]);
						gc.TpPlayerOfGameToLocationSpecific(player, target, map, world, x, y, z, yaw, pitch);
					}else if(args.length == 7) {
						String target = args[1];
						String map = args[2];
						String world = args[3];
						double x = Double.valueOf(args[4]);
						double y = Double.valueOf(args[5]);
						double z = Double.valueOf(args[6]);
						gc.TpPlayerOfGameToLocationSpecific(player, target, map, world, x, y, z);
						
					}else {
					 gc.sendMessageToUserAndConsole(player, ChatColor.GREEN+"1) Usa /mg tp <map> <world> <x> <y> <z> <yaw> <pitch>");
					 gc.sendMessageToUserAndConsole(player,ChatColor.GREEN+"2) Usa /mg tp <map> <world> <x> <y> <z>");
					}
					
					
					//mg tp-all Tutorial World 122 343 34 35 45 - 9
					//mg tp-all Tutorial World 122 343 34 - 7
					//mg tp Tutorial NAO World 122 343 34 35 45 - 9
					//mg tp Tutorial NAO World 122 343 34 - 7
					
					return true;
				}else if(args[0].equalsIgnoreCase("tp-all")){
					
					if(args.length == 8) {
		
						String map = args[1];
						String world = args[2];
						double x = Double.valueOf(args[3]);
						double y = Double.valueOf(args[4]);
						double z = Double.valueOf(args[5]);
						float yaw = Float.valueOf(args[6]);
						float pitch = Float.valueOf(args[7]);
						gc.TpAllPlayersOfGameToLocationSpecific(player, map, world, x, y, z, yaw, pitch);
					}else if(args.length == 6) {
					
						String map = args[1];
						String world = args[2];
						double x = Double.valueOf(args[3]);
						double y = Double.valueOf(args[4]);
						double z = Double.valueOf(args[5]);
						gc.TpAllPlayersOfGameToLocationSpecific(player, map, world, x, y, z);

						
					}else {
					 gc.sendMessageToUserAndConsole(player, ChatColor.GREEN+"1) Usa /mg tp-all <target> <map> <world> <x> <y> <z> <yaw> <pitch>");
					 gc.sendMessageToUserAndConsole(player,ChatColor.GREEN+"2) Usa /mg tp-all <target> <map> <world> <x> <y> <z>");
					}
					
					
					//mg tp all Tutorial World 122 343 34 35 45 - 9
					//mg tp all Tutorial World 122 343 34 - 7
					//mg tp Tutorial NAO World 122 343 34 35 45 - 9
					//mg tp Tutorial NAO World 122 343 34 - 7
					
					return true;
				}else if(args[0].equalsIgnoreCase("tp-all-toplayer")){
					gc.TpAllPlayersOfGameToLocation(player);
					return true;
				}else if(args[0].equalsIgnoreCase("tp-toplayer")){
					if(args.length == 2) {
						String target = args[1];
						gc.TpPlayerOfGameToLocation(player, target);
					}else {
						player.sendMessage(ChatColor.RED+"/mg tp-all-toplayer <target>");
					}
					return true;
				}
				
				else if(args[0].equalsIgnoreCase("misions") ){
				
			 		if(gc.isPlayerinGame(player)) {
					
						player.sendMessage(ChatColor.RED+"Ya estas en una Mision");
						return true;
					}
					
					MinigameShop1 ms = new MinigameShop1(plugin);
					plugin.getPags().put(player, 1);
					ms.MissionsMenu(player);
					
					return true;
				}else if(args[0].equalsIgnoreCase("ban") || args[0].equalsIgnoreCase("kick")  || args[0].equalsIgnoreCase("tempban")  || args[0].equalsIgnoreCase("warn") || args[0].equalsIgnoreCase("pardon")){
					if(args.length >= 3) {
						
			        	 IdentifierReports(player,args);
			        	 //mg warn NAO HOLA Q HACES BRO
			         		 
					}else {
						if(player != null) {
							player.sendMessage(ChatColor.YELLOW+"Usa /mg ban,kick,warn <player> <obligatorio una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg ban,kick,warn <player> <obligatorio una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
						if(player != null) {
							player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <obligatorio una razon>");
							player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <obligatorio una razon>");
							player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <obligatorio una razon>");
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <obligatorio una razon>");
						Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <obligatorio una razon>");
						Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <obligatorio una razon>");
					}
				
					
					return true;
				}else if(args[0].equalsIgnoreCase("objetive")){
					if(args.length == 4) {
						
						
						String map = args[1];
						String name = args[2];
						String nmberorenum = args[3];
						Pattern p = Pattern.compile("([0-9])");
						Matcher m = p.matcher(nmberorenum);
						if(m.find()) {
							int value = Integer.valueOf(nmberorenum);
							gc.ObjetivesValue(map, name, value,null);
						}else {
							try {
								ObjetiveStatusType obj = ObjetiveStatusType.valueOf(nmberorenum.toUpperCase());
								if(obj != null) {
									gc.ObjetiveChangeType(map, name, obj,null);
									
								}else {
									player.sendMessage(ChatColor.RED+"Ese no es un Tipo de Objetivo");
									gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning>");
					 				gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1");
								}
							}catch(IllegalArgumentException e) {
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No existe el tipo "+ChatColor.GOLD+nmberorenum );
							}
							
							
							
							
						}//mg objetive tutorial CASA COMPLETE
					}else if(args.length == 5){
						String map = args[1];
						String name = args[2];
						String nmberorenum = args[3];
						String target = args[4];
						Pattern p = Pattern.compile("([0-9])");
						Matcher m = p.matcher(nmberorenum);
						if(m.find()) {
							int value = Integer.valueOf(nmberorenum);
							gc.ObjetivesValue(map, name, value,target);
						}else {
							 try {
								ObjetiveStatusType obj = ObjetiveStatusType.valueOf(nmberorenum.toUpperCase());
								if(obj != null) {
									gc.ObjetiveChangeType(map, name, obj,target);
									
								}else {
									gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning> <player>");
									gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1 <player>");
									gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning>");
					 				gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1");
								}
							
							}catch(IllegalArgumentException e) {
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No existe el tipo "+ChatColor.GOLD+nmberorenum );
							}
						}
					}else {
						gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning> <player>");
						gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning>");
		 				gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1");
		 				gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1 <player>");
					}
						 
					
					return true;
				}else if(args[0].equalsIgnoreCase("objetives") ) {
					MinigameShop1 ms = new MinigameShop1(plugin);
					if(gc.isPlayerinGame(player)){
						ms.ShowObjetives(player);
					
						
					}else {
						player.sendMessage(ChatColor.RED+"No estas en un Juego de Aventura.");
					}
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("dropentity")){
					GameIntoMap ci = new GameIntoMap(plugin);
					ci.PlayerDropAllItems(player);
					
					return true;
				}else if(args[0].equalsIgnoreCase("top") ){
					if(points1.contains("Players")) {
						if (message.getBoolean("Command.message-top")) {
							List<String> messagep = message.getStringList("Command.message-top-decoracion1");
							for (int j = 0; j < messagep.size(); j++) {
								String texto = messagep.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}
						}

						// PRIMERA PARTE
						HashMap<String, Integer> scores = new HashMap<>();

						for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

							int puntaje = Integer.valueOf(points1.getString("Players." + key + ".Kills"));
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
							if (i <= message.getInt("Top-Amount-Command")) {
								// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

								if (message.getBoolean("Command.message-top")) {
									List<String> messagep = message.getStringList("Command.message-top-texto");
									for (int j = 0; j < messagep.size(); j++) {
										String texto = messagep.get(j);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&',
												texto.replaceAll("%player%", e.getKey())
														.replace("%pointuser%", e.getValue().toString())
														.replaceAll("%place%", Integer.toString(i))));
									}

								}

							} else {
								break;
							}

						}

						if (message.getBoolean("Command.message-top")) {
							List<String> messagep = message.getStringList("Command.message-top-decoracion2");
							for (int j = 0; j < messagep.size(); j++) {
								String texto = messagep.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}
						} 
					}else {
						player.sendMessage(ChatColor.RED+"No hay datos de ningun Jugador");
					}
					
			
					
					
					return true;
				}else if (args[0].equalsIgnoreCase("points")) {
					if(gc.isPlayerinGame(player)) {
					//	String arena = plugin.getArenaPlayerInfo().get(player);
						GameIntoMap cs = new GameIntoMap(plugin);
						cs.getPointsOfPlayerGame(player);
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No estas en ningun Juego.");
					}
					return true;
				}else if (args[0].equalsIgnoreCase("versionpl")) {
					if (args.length == 2) {
						String target = args[1];
						gc.getPlayerVersion(player, target);
					}else {
						gc.getPlayerVersion(player, player.getName());
					}
					
					
					return true;
				}else if (args[0].equalsIgnoreCase("stop")) {
				if(player.isOp()) {
						if (args.length == 3) {
							String name = args[1];
							try {
								StopMotivo motivo = StopMotivo.valueOf(args[2].toUpperCase());
								
								//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
								//MG STOP NAME
								Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"STOP MOTIVO: "+motivo.toString());
								gc.StopGames(player, name,motivo);
							}catch(IllegalArgumentException e) {
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Ese motivo no existe usa: Win , Lose , Error o Force. ");
							}
							
							
						
							
						 }else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg stop <mapa> <motivo Win , Lose , Error o Force>");
							
						 }
						
				}else {
					player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
					
				}
				
				return true;
				
			}else if (args[0].equalsIgnoreCase("getInv")) {
				if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
							getInventoryY(name, player);
			
						 }else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg getInv <kit> ");
							Bukkit.getConsoleSender()
							.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg getInv <kit> ");
						 }
						
				}else {
					player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
					
				}
				
				return true;
				
			}else if (args[0].equalsIgnoreCase("saveInv")) {
				if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
				
						    saveInventoryY(name, player);
						
							
						 }else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg saveInv <kit> ");
							Bukkit.getConsoleSender()
							.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg saveInv <kit> ");
						 }
						
				}else {
					player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
					
				}
				
				return true;
			}else if(args[0].equalsIgnoreCase("start-timer")) {
					
			
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" A iniciado el Temporizador ");
					
					Timer t = new Timer();
					
					TimerTask tarea = new TimerTask() {
					    int  segundo = 59;
						int  minuto = 1 ;
						int  hora = 0 ;
					@Override
					public void run() {
					
						
						if (segundo != 0){
							 segundo--; 
							
						}
						 if (minuto != 0 && segundo == 0) {
								segundo = 60;
								minuto --;
							}
						 if (hora != 0 && minuto == 0) {
								minuto = 60;
								hora --;
							}
						 
					   if(segundo == 0 && minuto == 0) {
						  
						   player.sendMessage(ChatColor.GOLD+"Timer Finalizado con Exito");
							t.cancel();
						}
					  
					
						
						
				
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN+"Cuenta atras : "+hora+"h "+minuto+"m "+segundo+"s " ));
						
						}
					};
					t.schedule(tarea, 0,1000);
					
					return true;
					}
				
			}
			
			
			player.sendMessage(plugin.nombre+ChatColor.RED+" escribe "+ChatColor.YELLOW+"/mg info");
			
			
			
		}
		return true;
	}
	
	
	
	public List<Entity> getNearbyEntites(Location l , int size){
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : l.getWorld().getEntities()) {
			if(l.distance(e.getLocation()) <= size) {
				entities.add(e);
			}
		}
		return entities;
		
		
	}
	
	
	public void saveInventoryY(String name , Player player) {
		FileConfiguration invt = plugin.getInventorysYaml();
		if(invt.contains("Inventory."+name)) {
			//String cla = invt.getString("Inventory."+name);
			invt.set("Inventory."+name, player.getInventory().getContents());
			plugin.getInventorysYaml().save();
			plugin.getInventorysYaml().reload();
	    	player.sendMessage(ChatColor.GREEN+"Se a Actualizado el Kit "+ChatColor.RED+name);

	      return;
	    }
		invt.set("Inventory."+name, player.getInventory().getContents());
		player.sendMessage(ChatColor.GREEN+"Se a salvado el Kit "+ChatColor.RED+name);
		plugin.getInventorysYaml().save();
		plugin.getInventorysYaml().reload();

	}
	
	 
 	
	public void getInventoryY(String name , Player player) {
		FileConfiguration invt = plugin.getInventorysYaml();
			
			if(!invt.contains("Inventory."+name)) {
				player.sendMessage(ChatColor.RED+"Ese Kit no existe.");
				return;
			}
			
			for (String key : invt.getConfigurationSection("Inventory").getKeys(false)) {
				if(key.equals(name)) {
					@SuppressWarnings("unchecked")
					ItemStack[] content = ((List<ItemStack>) invt.get("Inventory."+ key)).toArray(new ItemStack[0]);
					player.getInventory().setContents(content);
				}
				
			}
			
			
		player.sendMessage(ChatColor.GREEN+"Obtuviste la clase "+ChatColor.RED+name);
	}

	public String TimeR(LocalDateTime t) {
		if(t.getHour() < 10) {
			if(t.getMinute() < 10) {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" 0"+t.getHour()+ ":0"+t.getMinute();
			}else {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" 0"+t.getHour()+ ":"+t.getMinute();
			}
			
		}else {
			if(t.getMinute() < 10) {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" "+t.getHour()+ ":0"+t.getMinute();
			}else {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" "+t.getHour()+ ":"+t.getMinute();
			}
			
		}
		
	}
	
	public boolean isATimeMgFormat(String val) {
		
 		Pattern p = Pattern.compile("([0-9])");
		Matcher m = p.matcher(val);
		
		if(m.find() && val.length() == 2 || val.length() == 3) {
			if(val.endsWith("d") || val.endsWith("D") ||val.endsWith("h") || val.endsWith("H") || val.endsWith("m") || val.endsWith("M") || val.endsWith("s") || val.endsWith("S")) {
				
				return true;
			}
		}
	
		return false;
	}
	
 	public int ReturnHourAndMinuteToSecons(String val) {
		
		int total = 0;
		
			if(val.endsWith("d") || val.endsWith("D")) {
				int tex = Integer.valueOf(val.replace("d","").replace("D",""));
				total = tex * 86400;
			}
			if(val.endsWith("h") || val.endsWith("H")) {
				int tex = Integer.valueOf(val.replace("h","").replace("H",""));
				total = tex * 3600;
			}if(val.endsWith("m") || val.endsWith("M")) {
				int tex = Integer.valueOf(val.replace("m","").replace("M",""));
				total = tex * 60;
			}if(val.endsWith("s") || val.endsWith("S")) {
				int tex = Integer.valueOf(val.replace("s","").replace("S",""));
				total = tex;
			}
		
		
		
		return total;
	}
 	
 	 
 	public void AreaPotion(Player player ,Location l,String type,String color,int radius ,int duration,int effectduration, int amplifier) {
		//System.out.println("TYPE: "+type.toString());
 		String comments = "";
 		Map<String,Color> typecolor = new HashMap<>();
 		typecolor.put("RED", Color.RED);
 		typecolor.put("AQUA", Color.AQUA);
 		typecolor.put("BLACK", Color.BLACK);
 		typecolor.put("BLUE", Color.BLUE);
 		typecolor.put("FUCHSIA", Color.FUCHSIA);
 		typecolor.put("GRAY", Color.GRAY);
 		typecolor.put("GREEN", Color.GREEN);
 		typecolor.put("LIME", Color.LIME);
 		typecolor.put("MAROON", Color.MAROON);
 		typecolor.put("NAVY", Color.NAVY);
 		typecolor.put("OLIVE", Color.OLIVE);
 		typecolor.put("ORANGE", Color.ORANGE);
 		typecolor.put("PURPLE", Color.PURPLE);
 		typecolor.put("SILVER", Color.SILVER);
 		typecolor.put("TEAL", Color.TEAL);
 		typecolor.put("WHITE", Color.WHITE);
 		typecolor.put("YELLOW", Color.YELLOW);
 		
 		Map<String,PotionEffectType> typepotion = new HashMap<>();
 	
 		typepotion.put("ABSORTION", PotionEffectType.ABSORPTION);
 		typepotion.put("BAD_OMEN", PotionEffectType.BAD_OMEN);
 		typepotion.put("BLINDNESS", PotionEffectType.BLINDNESS);
 		typepotion.put("CONDUIT_POWER", PotionEffectType.CONDUIT_POWER);
 		typepotion.put("CONFUSION", PotionEffectType.CONFUSION);
 		typepotion.put("DAMAGE_RESISTANCE", PotionEffectType.DAMAGE_RESISTANCE);
 		typepotion.put("DARKNESS", PotionEffectType.DARKNESS);
 		typepotion.put("DOLPHINS_GRACE", PotionEffectType.DOLPHINS_GRACE);
 		typepotion.put("FAST_DIGGING", PotionEffectType.FAST_DIGGING);
 		typepotion.put("FIRE_RESISTANCE", PotionEffectType.FIRE_RESISTANCE);
 		typepotion.put("GLOWING", PotionEffectType.GLOWING);
 		typepotion.put("HARM", PotionEffectType.HARM);
 		typepotion.put("HEAL", PotionEffectType.HEAL);
 		typepotion.put("HEALTH_BOOST", PotionEffectType.HEALTH_BOOST);
 		typepotion.put("HERO_OF_THE_VILLAGE", PotionEffectType.HERO_OF_THE_VILLAGE);
 		typepotion.put("HUNGER", PotionEffectType.HUNGER);
 		typepotion.put("INCREASE_DAMAGE", PotionEffectType.INCREASE_DAMAGE);
 		typepotion.put("INVISIBILITY", PotionEffectType.INVISIBILITY);
 		typepotion.put("JUMP", PotionEffectType.JUMP);
 		typepotion.put("LEVITATION", PotionEffectType.LEVITATION);
 		typepotion.put("LUCK", PotionEffectType.LUCK);
 		typepotion.put("NIGHT_VISION", PotionEffectType.NIGHT_VISION);
 		typepotion.put("POISON", PotionEffectType.POISON);
 		typepotion.put("REGENERATION", PotionEffectType.REGENERATION);
 		typepotion.put("SATURATION", PotionEffectType.SATURATION);
 		typepotion.put("SLOW", PotionEffectType.SLOW);
 		typepotion.put("SLOW_DIGGING", PotionEffectType.SLOW_DIGGING);
 		typepotion.put("SLOW_FALLING", PotionEffectType.SLOW_FALLING);
 		typepotion.put("SPEED", PotionEffectType.SPEED);
 		typepotion.put("UNLUCK", PotionEffectType.UNLUCK);
 		typepotion.put("WATER_BREATHING", PotionEffectType.WATER_BREATHING);
 		typepotion.put("WEAKNESS", PotionEffectType.WEAKNESS);
 		typepotion.put("WITHER", PotionEffectType.WITHER);
 		
 		if(!typepotion.containsKey(type)) {
 			
 			comments = ChatColor.RED+"Tipos de Posiones: ";
 			
 			List<Map.Entry<String,PotionEffectType>> list = new ArrayList<>(typepotion.entrySet());
 			for (Map.Entry<String,PotionEffectType> e : list) {
 					comments = comments+ChatColor.GREEN+e.getKey()+ChatColor.RED+",";
				}
 			comments = comments+ChatColor.RED+" Revisa bien la escritura.";
 			if(player != null) {
 				player.sendMessage(comments);
 			}else{
 				Bukkit.getConsoleSender().sendMessage(comments);
 			}
 			return;
 		}
 		
 		if(!typecolor.containsKey(color)) {
 			
 			comments = ChatColor.RED+"Colores: ";
 			
 			List<Map.Entry<String,Color>> list = new ArrayList<>(typecolor.entrySet());
 			for (Map.Entry<String,Color> e : list) {
 					comments = comments+ChatColor.GREEN+e.getKey()+ChatColor.RED+",";
				}
 			comments = comments+ChatColor.RED+" Revisa bien la escritura.";
 			if(player != null) {
 				player.sendMessage(comments);
 			}else{
 				Bukkit.getConsoleSender().sendMessage(comments);
 			}
 			return;
 		}
 		
 		PotionEffect effect = new PotionEffect(typepotion.get(type),/*duration*/ effectduration*20,/*amplifier:*/amplifier, false ,false,true );
		AreaEffectCloud aec = (AreaEffectCloud) l.getWorld().spawnEntity(l,  EntityType.AREA_EFFECT_CLOUD);
		aec.addCustomEffect(effect, true);
		//aec.setBasePotionType(type);
		//aec.setCustomName(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"GAS TOXICO");
		aec.setColor(typecolor.get(color));
		aec.setDuration(duration*20);
		aec.setRadius(radius);
		aec.setReapplicationDelay(5*20);
//		aec.setDurationOnUse(1);
//		aec.setRadiusOnUse(0.1f);
		//aec.setRadiusPerTick(aec.getRadiusPerTick()-(15*20));
		
		//aec.setParticle(Particle.SPELL);
		
		
				
	}
 	
 	public void DialogueArgs(Player player ,String[] args) {
 		//mg dialogue nameyml id Map


 		GameConditions gc = new GameConditions(plugin);
 		if(args.length == 4) {
 			
			if(gc.ExistMapDialog(args[1])) {
				gc.LoadDialogues(args[1], args[2], args[3]);
			}else {
				gc.sendMessageToUserAndConsole(player, ChatColor.RED+"El Dialogo "+args[1]+" no Existe.");
			}
 			
 		}else {
 			gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg dialogue <nombreyml> <Seccion> <Mapa>.");
 		}
 		return;
 	}
 	
 		// 		1					2	3  	4 = 5
 	
 	
 	
 	
	
	

//mg ban NAO POR NOOB
	public void IdentifierReports(Player player,String[] report) {
		
		try {
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
				// StringBuilder sb = new StringBuilder();
				 LocalDateTime ld = LocalDateTime.now();
				 ReportsManager cool = new ReportsManager(plugin);
				 
				 String type = report[0];
				 String target = report[1];
				 
				 if(type == null || target == null) {
						if(player != null) {
							player.sendMessage(ChatColor.YELLOW+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
					 return;
				 }
				 
				 //mg pardon NAO
				 if(type.startsWith("pardon")) {
					
						 String comments = "";
			        	 for(int i = 2 ;i < report.length; i++) {
			        		 comments = comments+report[i]+" "; 
							 
						 }
			        	 if(player != null) {
								GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"SIN TIEMPO",player.getName(),comments.replace("-", " ").replace(",", " "));
								cool.SetSancionPlayer(player, gr, 0);
							}else {
								 GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"SIN TIEMPO","CONSOLA",comments.replace("-", " ").replace(",", " "));
									cool.SetSancionPlayer(player, gr, 0);
							}
					
					 
				 }else if(type.startsWith("ban")|| type.startsWith("kick") || type.startsWith("warn")) {
						
							
							//mg warn nao test
							//mg ban nao
							
							 String comments = "";
				        	 for(int i = 2 ;i < report.length; i++) {
				        		 comments = comments+report[i]+" "; 
								 
							 }
						
							//String timeg = time;
							GameConditions gc = new GameConditions(plugin);
							
							if(player != null) {
								GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"SIN TIEMPO",player.getName(),comments.replace("-", " ").replace(",", " "));
								cool.SetSancionPlayer(player, gr, 0);
							}else {
								GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"SIN TIEMPO","CONSOLA",comments.replace("-", " ").replace(",", " "));
								cool.SetSancionPlayer(player, gr, 0);
							}
							
							Player target1 = Bukkit.getServer().getPlayerExact(target);
							if(!type.startsWith("warn")) {
								if(target1 != null && gc.isPlayerinGame(target1)) {
									gc.mgLeaveOfTheGame(target1);

								}	
							}
							
							
							return;
						

						
						
				 }else if(type.startsWith("tempban")) {
					       //0    1    2 3  4   5  6
					 //mg tempban nao 1h 1h 1h com
					 	String comments = "";
		        	
							
						 	int pa = 0;
							for(String s : report) {
								if(!isATimeMgFormat(s))continue;
								pa++;
								
							}
							
						
							if(pa == 0) {
								if(player != null) {
									player.sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 1>");
									player.sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 2>");
									player.sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 3>");

									
								}
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 1>");
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 2>");
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 3>");

								
								
								return;
							}else if(pa == 1) {
								String time = report[2];
							
								 for(int i = 3 ;i < report.length; i++) {
					        		 comments = comments+report[i]+" "; 
								 }
								
								 if(comments.isEmpty()) {
									 comments = "Sin especificar.";
								 }
								 
								int total = (ReturnHourAndMinuteToSecons(time));
								if(player != null) {
									GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),cool.ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),player.getName(),comments);
									
									cool.SetSancionPlayer(player, gr, total);
								}else {
									GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),cool.ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),"CONSOLA",comments);
								
									cool.SetSancionPlayer(player, gr, total);
								}
								return;
							}else if(pa == 2) {
								String time = report[2];
								String time2 = report[3];
								
								 for(int i = 4 ;i < report.length; i++) {
					        		 comments = comments+report[i]+" "; 
								 }
								
								 if(comments.isEmpty()) {
									 comments = "Sin especificar.";
								 }
								 
								int total = (ReturnHourAndMinuteToSecons(time)+ReturnHourAndMinuteToSecons(time2));
								if(player != null) {
									
									GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),cool.ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),player.getName(),comments);
									cool.SetSancionPlayer(player, gr, total);
								}else{
									GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),cool.ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),"CONSOLA",comments);
									cool.SetSancionPlayer(player, gr, total);
								}
								return;
							}else if(pa == 3) {
								
								String time = report[2];
								String time2 = report[3];
								String time3 = report[4];
								
								 for(int i = 5 ;i < report.length; i++) {
					        		 comments = comments+report[i]+" "; 
								 }
								 
								 if(comments.isEmpty()) {
									 comments = "Sin especificar.";
								 }
								 
							
								int total = (ReturnHourAndMinuteToSecons(time)+ReturnHourAndMinuteToSecons(time2)+ReturnHourAndMinuteToSecons(time3));

								if(player != null) {
									GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),cool.ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),player.getName(),comments);
									cool.SetSancionPlayer(player, gr, total);
								}else{
									GameReports gr = new GameReports(target,GameReportType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),cool.ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),"CONSOLA",comments);
									cool.SetSancionPlayer(player, gr, total);
								}
							}
							
							
					
				 }
				
				//TEMPBAN
				 // 2
			     //mg tempban NAO 1 ES 3 PERO PUEDE SER 4
		}catch(ArrayIndexOutOfBoundsException e) {
			if(player != null) {
				player.sendMessage(ChatColor.YELLOW+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s ...");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s ...");
			if(player != null) {
				player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <escribe una razon>...");
				player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <escribe una razon>...");
				player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <escribe una razon>...");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <escribe una razon>...");
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <escribe una razon>...");
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <escribe una razon>...");
		 return;
		}
	
		
	}
	
	
	
//	  public char getBlock(int x, int y, int z, final World bukkitWorld) {
//	        final World world = bukkitWorld;
//	        final Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
//	        if (chunk == null) {
//	            return 0;
//	        }
//	        int sectionY = y >> 4;
//	        if (sectionY < 0 || sectionY > 16) {
//	            return 0;
//	        }
//	        
//	        
//	        ChunkSection section = ((Object) chunk).getSections()[sectionY];
//	        if (section == null || section.a()) {
//	            return 0;
//	        }
//	        x = x & 15;
//	        y = y & 15;
//	        z = z & 15;
//	        final char block = section.getIdArray()[y << 8 | z << 4 | x];
//	        return block;
//	    }
	
	
	public void AllReload(Player player) {
		    plugin.getMenuItems().reload();
			plugin.getConfig().reload();
			plugin.getMessage().reload();
			plugin.getPoints().reload();
			plugin.getCommandsMg().reload();
			plugin.getInventorysYaml().reload();
			plugin.getCooldown().reload();	
			plugin.getReportsYaml().reload();
			
			if(player != null) {
				player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se han recargado las Configuraciones Correctamente.");

			}
			Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado Correctamente el Plugin.");

			
	}
	
	
	
	
	
	 
}


/*
 * 
 * 
 * else if (args[0].equalsIgnoreCase("creative")){
    ChangeMode("creative", player);
}else if (args[0].equalsIgnoreCase("survival")){
    ChangeMode("survival", player);
}
public voidChangeMode(String mode, Player player){
    inventoryCache.put(player.getUniqueId(), player.getInventory().getContents());
    player.getInventory().setContents(inventoryCache.get(player.getUniqueId()));
    if(mode.equals("survival")){
        player.setGameMode(GameMode.Survival);
        player.sendMessage(plugin.nombre+ChatColor.GREEN+"Modo de juego survival");
    }else if(mode.equals("survival")){
        player.setGameMode(GameMode.Creative);
        player.sendMessage(plugin.nombre+ChatColor.GREEN+"Modo de juego creative");
    }
}
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * String enumValue = args[0] != null ? (args[0].equalsIgnoreCase("survival") ? "SURVIVAL" :  args[0].equalsIgnoreCase("creative") ? "CREATIVE" : args[0].equalsIgnoreCase("adventure") : "ADVENTURE" : args[0].equalsIgnoreCase("spectator") ? "SPECTATOR" : "NONE"): "NONE";
if(enumValue.equals("NONE") {player.sendMessage("Modo inv�lido como tu primo");return;}
GameMode mode = GameMode.valueOf(enumValue);
player.setGameMode(mode);
player.sendMessage("Tu modo de juego ha sido cambiado a "+enumValue.toLowerCase());
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */







