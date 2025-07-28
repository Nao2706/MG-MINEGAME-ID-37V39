package me.nao.command.mg;



import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.type.Piston;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Arrow;
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

import me.nao.cosmetics.mg.RankPlayer;
import me.nao.database.mg.BukkitSerialization;
import me.nao.database.mg.SQLInfo;
import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.Items;
import me.nao.enums.mg.ObjetiveStatusType;
import me.nao.enums.mg.StopMotive;
import me.nao.events.mg.ItemNBT;
import me.nao.fillareas.mg.Data;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.GameTime;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.generalinfo.mg.ReportsManager;
import me.nao.generalinfo.mg.SystemOfLevels;
import me.nao.main.mg.Minegame;
import me.nao.manager.mg.GameIntoMap;
import me.nao.manager.mg.InfectedGame;
import me.nao.manager.mg.MapSettings;
import me.nao.mobs.mg.MobsActions;
import me.nao.shop.mg.MinigameShop1;
import me.nao.timers.mg.Countdown2;
import me.nao.topusers.mg.PointsManager;
import me.nao.utils.mg.Utils;
import me.nao.yamlfile.mg.YamlFilePlus;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;







@SuppressWarnings("deprecation")
public class Comandsmg implements CommandExecutor{

	
	
	
	private Minegame plugin;
	
	public Comandsmg(Minegame plugin) {
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
								if(gc.isMapinGame(name)) {
									gc.reloadInfoTheGame(name);
								}
								
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado correctamente el Mapa "+name);
							}else {
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" No existe ese mapa.");
							}
							
						}else {
							AllReload(null);
						}
						
					return true;
					
					
				}else if(args[0].equalsIgnoreCase("time")) {
        			FileConfiguration config = plugin.getConfig();
        			String country = config.getString("Country-Time","Unknow");
              		LocalDateTime lt = LocalDateTime.now();
              		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
              		
              		Bukkit.getConsoleSender().sendMessage("");
              		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"La Fecha es del Pais: "+ChatColor.GRAY+country);
              		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Fecha Actual del Servidor: "+ChatColor.GREEN+lt.format(formatter));
              		Bukkit.getConsoleSender().sendMessage("");
          			
          			return true;
          			
          		}else if(args[0].equalsIgnoreCase("mapsingame")) {
          			
          			  
          			List<Map.Entry<String, GameInfo>> list = new ArrayList<>(plugin.getGameInfoPoo().entrySet());
          			
          			if(list.isEmpty()) {
          				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay ningun Juego en Progreso.");
          				return true;
          			}
          			
          			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Mapas Activos:");		
          			List<String> l = new ArrayList<>();
          			for (Map.Entry<String, GameInfo> e : list) {
          				if(e.getValue().getGameStatus() == GameStatus.ESPERANDO) {
          					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.WHITE+e.getValue().getGameStatus().toString());
          				}else if(e.getValue().getGameStatus() == GameStatus.COMENZANDO) {
          					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.GREEN+e.getValue().getGameStatus().toString());
          				}else if(e.getValue().getGameStatus() == GameStatus.JUGANDO) {
          					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.YELLOW+e.getValue().getGameStatus().toString());
          				}else if(e.getValue().getGameStatus() == GameStatus.TERMINANDO) {
          					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.RED+e.getValue().getGameStatus().toString());
          				}else if(e.getValue().getGameStatus() == GameStatus.PAUSE) {
          					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.GOLD+e.getValue().getGameStatus().toString());
          				}else if(e.getValue().getGameStatus() == GameStatus.FREEZE) {
          					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.AQUA+e.getValue().getGameStatus().toString());
          				}
          			}
          			
        			if(args.length == 1) {
    					
          				gc.pagsSystem(null, l, 1, 10);
          				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Un Total de: "+ChatColor.RED+list.size()+" Mapas Activos.");		
    				}else if(args.length == 2){
    					
    					int pag = Integer.valueOf(args[1]);
    					gc.pagsSystem(null, l, pag, 10);
    					Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Un Total de: "+ChatColor.RED+list.size()+" Mapas Activos.");		
    				}else {
    					Bukkit.getConsoleSender().sendMessage("Usa /mg mapsingame ");
    					Bukkit.getConsoleSender().sendMessage("Usa /mg mapsingame <pag>");
    				}
          			
         
          			
          			
          			return true;
          		}else if(args[0].equalsIgnoreCase("reportlogs")) {
					 //mg reportlogs nao 1
					
					if(args.length == 2) {
							String name = args[1];
							ReportsManager cool = new ReportsManager(plugin);
							cool.sendReportLogs(null, name, 1);
							
					}else if(args.length == 3){
						String name = args[1];
						int pag = Integer.valueOf(args[2]);
						ReportsManager cool = new ReportsManager(plugin);
						cool.sendReportLogs(null, name, pag);
						
					}else {
						Bukkit.getConsoleSender().sendMessage("Usa /mg reportlogs <player>");
						Bukkit.getConsoleSender().sendMessage("Usa /mg reportlogs <player> <pag>");
					}
					
					return true;
				}else if (args[0].equalsIgnoreCase("info")) {
					if(args.length == 1) {
						
						infomg(null,1);
					
					}else if(args.length == 2){
						
						int pag = Integer.valueOf(args[1]);
						infomg(null,pag);
					}

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
						gc.TpPlayerOfGameToLocationSpecific(target, map, world, x, y, z, yaw, pitch);
					}else if(args.length == 7) {
						String target = args[1];
						String map = args[2];
						String world = args[3];
						double x = Double.valueOf(args[4]);
						double y = Double.valueOf(args[5]);
						double z = Double.valueOf(args[6]);
						gc.TpPlayerOfGameToLocationSpecific(target, map, world, x, y, z);
						
					}else {
					 gc.sendMessageToUserAndConsole(null, ChatColor.GREEN+"1) Usa /mg tp <target> <map> <world> <x> <y> <z> <yaw> <pitch>");
					 gc.sendMessageToUserAndConsole(null,ChatColor.GREEN+"2) Usa /mg tp <target> <map> <world> <x> <y> <z>");
					}
					
					
					//mg tp-all Tutorial World 122 343 34 35 45 - 9
					//mg tp-all Tutorial World 122 343 34 - 7
					//mg tp Tutorial NAO World 122 343 34 35 45 - 9
					//mg tp Tutorial NAO World 122 343 34 - 7
					
					return true;
				}else if(args[0].equalsIgnoreCase("tpall")){
					
					if(args.length == 8) {
						
						String map = args[1];
						String world = args[2];
						double x = Double.valueOf(args[3]);
						double y = Double.valueOf(args[4]);
						double z = Double.valueOf(args[5]);
						float yaw = Float.valueOf(args[6]);
						float pitch = Float.valueOf(args[7]);
						gc.TpAllPlayersOfGameToLocationSpecific(map, world, x, y, z, yaw, pitch);
					}else if(args.length == 6) {
					
						String map = args[1];
						String world = args[2];
						double x = Double.valueOf(args[3]);
						double y = Double.valueOf(args[4]);
						double z = Double.valueOf(args[5]);
						gc.TpAllPlayersOfGameToLocationSpecific(map, world, x, y, z);

						
					}else {
					 gc.sendMessageToUserAndConsole(null, ChatColor.GREEN+"1) Usa /mg tpall <map> <world> <x> <y> <z> <yaw> <pitch>");
					 gc.sendMessageToUserAndConsole(null,ChatColor.GREEN+"2) Usa /mg tpall <map> <world> <x> <y> <z>");
					}
					
					
					//mg tp all Tutorial World 122 343 34 35 45 - 9
					//mg tp all Tutorial World 122 343 34 - 7
					//mg tp Tutorial NAO World 122 343 34 35 45 - 9
					//mg tp Tutorial NAO World 122 343 34 - 7
					
					return true;
				}else if(args[0].equalsIgnoreCase("tpall-to-player")){
					if(args.length == 2) {
						String target = args[1];
						Player player = Bukkit.getPlayerExact(target);
						if(player == null) {
							 gc.sendMessageToUserAndConsole(null,ChatColor.RED+"Ese jugador no esta en linea o esta mal escrito.");
							return true;
						}
						
						gc.TpAllPlayersOfGameToLocation(player);
						
					 }else {
						 gc.sendMessageToUserAndConsole(null,ChatColor.RED+"/mg tp-all-to-player <target>");
					 }
					
					return true;
				}else if(args[0].equalsIgnoreCase("tp-to-player")){
					if(args.length == 3) {
						String target1 = args[1];
						String target2 = args[2];
						Player player1 = Bukkit.getPlayerExact(target1);
						if(player1 == null && !gc.isPlayerinGame(player1)) {
							 gc.sendMessageToUserAndConsole(null,ChatColor.RED+"Ese jugador no esta en linea o esta mal escrito o no esta en juego.");
							return true;
						}
						
						gc.TpPlayerOfGameToLocation(player1, target2);
					}else {
						 gc.sendMessageToUserAndConsole(null,ChatColor.RED+"/mg tp-to-player <target1> <target2>");
					}
					return true;
				}else if(args[0].equalsIgnoreCase("addtag")) {
					
					if (args.length == 3) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					    String valor = args[2];
					  
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							if(!target.getScoreboardTags().contains(valor)) {
								target.addScoreboardTag(valor);
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue añadida con la Tag correctamente. ");
							}else {
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" ya tiene la Tag "+ChatColor.RED+valor);

							}
							
							
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg addtag <nombre> <texto>");
					}
					return true;
				}else if(args[0].equalsIgnoreCase("removetag")) {
					
					if (args.length == 3) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					    String valor = args[2];
					  
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							if(target.getScoreboardTags().contains(valor)) {
								target.removeScoreboardTag(valor);
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" le fue removida la Tag correctamente. ");
							}else {
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" ya no tiene la Tag "+ChatColor.RED+valor);

							}
							
							
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg removetag <nombre> <texto>");
					}
					return true;
				}else if(args[0].equalsIgnoreCase("showtags")) {
					
				
					if (args.length == 2) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					    
					  
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							if(!target.getScoreboardTags().isEmpty()) {
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" Tiene las Siguientes Tags. ");

								for(String t : target.getScoreboardTags()) {
									Bukkit.getConsoleSender().sendMessage(t);
								}
							}else {
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" no tiene la Tags ");

							}
							
							
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg showtags <nombre> ");
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
						  
						  	ReportsManager rm = new ReportsManager(plugin);
				        	 rm.IdentifierReports(null,args);
			        
						}else {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg ban,kick,warn <player> <obligatorio una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <obligatorio una razon>");
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <obligatorio una razon>");
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <obligatorio una razon>");
						}
					return true;
					
				}else if(args[0].equalsIgnoreCase("message") ) {
					
					//mg message map gola xd
						if(args.length >= 2) {
							String name = args[1];
							String mensaje = "";
				        	 for(int i = 2 ;i < args.length; i++) {
								 mensaje = mensaje+args[i]+" "; 
							 }
							gc.sendMessageToAllUsersOfSameMapCommand(null, name, mensaje);
							
						}else {
							gc.sendMessageToUserAndConsole(null,plugin.nombre+ChatColor.GREEN+" Usa /mg message <map> <message>");
						}
						
					return true;
				}else if(args[0].equalsIgnoreCase("title") ) {
					
						if (args.length >= 2) {
							String name = args[1];
							String mensaje = "";
				        	 for(int i = 2 ;i < args.length; i++) {
								 mensaje = mensaje+args[i]+" "; 
							 }
							gc.sendTittleToAllUsersOfSameMapCommand(null, name, mensaje);
							
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
							gc.ObjetivesValue(map, name.replaceAll("-"," ").replaceAll("_"," "), value,null);
						}else {
							try { 
								ObjetiveStatusType obj = ObjetiveStatusType.valueOf(numberoenum.toUpperCase());
								if(obj != null) {
									gc.ObjetiveChangeType(map, name.replaceAll("-"," ").replaceAll("_"," "), obj,null);
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
							gc.ObjetivesValue(map, name.replaceAll("-"," ").replaceAll("_"," "), value,target);
						}else {
							try {
								ObjetiveStatusType obj = convertStringToObjetiveStatusType(null,numberoenum);
								if(obj != null) {
									gc.ObjetiveChangeType(map, name.replaceAll("-"," ").replaceAll("_"," "), obj,target);
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
				}else if(args[0].equalsIgnoreCase("god")) {
					if (args.length == 2) {
						// /c add n p
						// mg set nao 10
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
						if(target != null) {
							target.setInvulnerable(true);
							Bukkit.getConsoleSender().sendMessage(target.getName()+" modo Invulnerable On");
						}
						
					}
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("ungod")) {
					if (args.length == 2) {
						// /c add n p
						// mg set nao 10
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
						if(target != null) {
							target.setInvulnerable(false);
							Bukkit.getConsoleSender().sendMessage(target.getName()+" modo Invulnerable Off");

						}
					}
					
					
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
		    	}else if(args[0].equalsIgnoreCase("spawnzombi")) {
			 		//H Z BZ EZ
					//mg spawnzombi wolrd,12,23,34 50 H
			 		 if(args.length == 4) {
			 			 String location = args[1];
			 			 String amount  = args[2];
			 			 String type = args[3].toUpperCase();
			 			 
			 			 String[] split = location.split(",");
			 			 
			 			 World world = Bukkit.getWorld(split[0]);
			 			 if(world == null) {
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mundo "+ChatColor.GOLD+split[0]+ChatColor.RED+" no existe.");
			 				 return true;
			 			 }
			 			
			 			 
			 			 int cant = Integer.valueOf(amount);
			 			 
			 			 if(cant == 0) {
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No puedes dejar en 0 el monto.");
			 				 return true;
			 			 }
			 			 MobsActions ma = new MobsActions(plugin);
			 			 
			 			 for(int i = 0; i < cant; i++) {
			 				 if(type.equals("H")) {
			 					 ma.spawnEliteZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
			 					 ma.spawnManualBabyZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
			 					 ma.spawnManualZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
			 				 }if(type.equals("ZB")) {
			 					 ma.spawnManualBabyZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
			 				 }if(type.equals("ZE")) {
			 					 ma.spawnEliteZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
			 				 }if(type.equals("Z")) {
			 					 ma.spawnManualZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
			 				 }if(type.equals("S")) {
			 					 ma.zombisSpecials(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
			 				 }
			 			 }
			 			 
			 		 }else{
			 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa mg spawnzombi <world,x,y,z> <amount> <H: Horde of all zombies, ZE:zombie elite, ZB:zombie baby, Z:zombie , S:Especial>");
			 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Ejemplo mg spawnzombi world,12,23,34 50 H");
			 		 }
			 		  
					return true;
				
				  }else if(args[0].equalsIgnoreCase("version")){
					Bukkit.getConsoleSender().sendMessage("");
					Bukkit.getConsoleSender().sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"MiniGame");
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Autor: "+ChatColor.AQUA+plugin.autor.get(0));		
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Version: "+ChatColor.RED+plugin.version);					
					Bukkit.getConsoleSender().sendMessage("");
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
				}else if(args[0].equalsIgnoreCase("tagsingame")) {
					if(args.length == 1) {
						gc.showTagsInGame(null,1);
					}else if(args.length == 2) {
						int pag = Integer.valueOf(args[1]);
						
						gc.showTagsInGame(null,pag);
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("dropplayer")){
					GameIntoMap ci = new GameIntoMap(plugin);
					
					if (args.length == 2) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					   
						if(target != null) {
							
							ci.PlayerDropAllItems(target);
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Se dropeo el Inventario de "+ChatColor.GOLD+target.getName());

						}else {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Ese Jugador no esta en linea o esta mal escrito.");	
						}
						
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg dropplayer <nombre>");	
					
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
					gc.loadItemMenu();
					return true;
		    	}else if(args[0].equalsIgnoreCase("pause")) {
			 		  //mg pause map
			 		 if(args.length != 2) {
			 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"/mg pause <map>");
			 			 return true;
			 		 }
			 		 
			 		 String map = args[1];
			 		 if(!gc.existMap(map)) {
			 			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+map+" no existe.");
		 				 return true;
		 			 }else if(!gc.isMapinGame(map)) {
		 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+map+" no esta en Juego.");
		 				 return true;
		 			 }
			 		 GameInfo gi = plugin.getGameInfoPoo().get(map);
			 		 
			 		 if(gi.getGameStatus() == GameStatus.JUGANDO) {
			 			 gi.setGameStatus(GameStatus.PAUSE);
			 		 }else if(gi.getGameStatus() == GameStatus.PAUSE){
			 			 gi.setGameStatus(GameStatus.JUGANDO);
			 		 }
			 		 
			 		 return true;
				  }else if(args[0].equalsIgnoreCase("formats")) {
	              	  CommandsMessage c1 = new CommandsMessage(plugin);
	              	  c1.formatsMessage(null);
          			
          			return true;
          			
          		}else if(args[0].equalsIgnoreCase("invite")) {
          			
          			//mg invite Tutorial
          			
          			if(args.length == 2) {
          				CommandsMessage c1 = new CommandsMessage(plugin);
          				c1.inviteToPlay(null, args[1]);
          			}else {
          				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa /mg invite <Mapa>");
          				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Ejemplo /mg invite Tutorial");
          			}
	              	  
        			
        			return true;
        			
        		}else if(args[0].equalsIgnoreCase("prestiges")) {
        			RankPlayer rk= new RankPlayer(plugin);
        			
        			rk.showPrestiges(null);
        			return true;
        		}else if(args[0].equalsIgnoreCase("deletecheckpoint")) {
					
					if (args.length == 2) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
						
						if(!gc.isPlayerinGame(target)) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no esta en Juego. ");

							return true;
						}
					 
					    
						if(target != null) {
							
							PlayerInfo pl = plugin.getPlayerInfoPoo().get(target);
							
							if(pl.getCheckPointMarker() != null) {
								target.sendMessage(ChatColor.RED+"- Tu CheckPoint por Bandera fue Eliminado.");
								Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+" El CheckPoint por Bandera de "+ChatColor.GOLD+args[1]+ChatColor.GREEN+" fue Eliminado");

								pl.setCheckpointLocationMg(null);
							}
							
							
							if(pl.getRespawn() != null) {
								target.sendMessage(ChatColor.RED+"- Tu Respawn fue Eliminado.");
								Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+" El Respawn de "+ChatColor.GOLD+args[1]+ChatColor.GREEN+" fue Eliminado");

								pl.setRespawnLocationMg(null);
							}
							
							
							if(pl.getCheckPointItem() != null) {
								pl.setCheckpointLocationMg(null);
								
								target.sendMessage(ChatColor.RED+"- Tu CheckPoint fue Eliminado.");
								Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+" El CheckPoint de "+ChatColor.GOLD+args[1]+ChatColor.GREEN+" fue Eliminado");

							}else {
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no tiene CheckPoints Guardados. ");

							}
							
						}else {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg deletecheckpoint <nombre>");
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("xp")) {
					
					//mg xp NAO prestige,level,points set,add,remove 10
					if(args.length == 5) {
						 String name = args[1];
						 String type  = args[2];
						 String option  = args[3];
						PointsManager pm = new PointsManager(plugin);
						 
						 if(type.equals("prestige")) {
							 int value = Integer.valueOf(args[4]);
							 
							 if(option.equals("set")) {
								 pm.setPlayerPrestigeMg(null, name, value);
								 gc.sendMessageToUserAndConsole(null,"&aSe seteo "+value+" de Nivel de Prestigio a &6"+name);
							 }else if(option.equals("add")) {
								 pm.addOrRemovePlayerPrestigeMg(null, name, Math.abs(value));
								 gc.sendMessageToUserAndConsole(null,"&aSe añadio "+Math.abs(value)+" de Nivel de Prestigio a &6"+name);
							 }else if(option.equals("remove")) {
								 pm.addOrRemovePlayerPrestigeMg(null, name, -Math.abs(value));
								 gc.sendMessageToUserAndConsole(null,"&aSe removio "+Math.abs(value)+" de Nivel de Prestigio a &6"+name);
							 }
							 
						 }else if(type.equals("level")) {
							 int value = Integer.valueOf(args[4]);
							 
							 if(option.equals("set")) {
								 pm.setPlayerLevelMg(null, name, value);
								 gc.sendMessageToUserAndConsole(null,"&aSe seteo "+value+" de Nivel de XP a &6"+name);
							 }else if(option.equals("add")) {
								 pm.addOrRemovePlayerLevelMg(null, name, Math.abs(value));
								 gc.sendMessageToUserAndConsole(null,"&aSe añadio "+Math.abs(value)+" de Nivel de XP a &6"+name);
							 }else if(option.equals("remove")) {
								 pm.addOrRemovePlayerLevelMg(null, name, -Math.abs(value));
								 gc.sendMessageToUserAndConsole(null,"&aSe removio "+-Math.abs(value)+" de Nivel de XP a &6"+name);
							 }
							 
						 }else if(type.equals("points")) {
							 long value = Long.valueOf(args[4]);
							 
							 if(option.equals("set")) {
								 	pm.calcReferenceExpAddOrRemove(null, name, value, true);
								 	gc.sendMessageToUserAndConsole(null,"&aSe seteo "+value+" de XP a &6"+name);
							 }else if(option.equals("add")) {
									pm.calcReferenceExpAddOrRemove(null, name, Math.abs(value), false);
									gc.sendMessageToUserAndConsole(null,"&aSe añadio "+Math.abs(value)+" de XP a &6"+name);
							 }else if(option.equals("remove")) {
									pm.calcReferenceExpAddOrRemove(null, name, -Math.abs(value), false);
									gc.sendMessageToUserAndConsole(null,"&aSe removio "+-Math.abs(value)+" de XP a &6"+name);
							 }
						 }
						 
						 
						 
					
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" /mg xp <name> <prestige,level,points> <set,add,remove> <value>");
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+"Example: /mg xp NAO2706 points add 10");
						//gc.checkPlayerInfo(player,player.getName());
					}
				
					return true;
					
				}else if(args[0].equalsIgnoreCase("timegame")) {
						//mg time Tutorial set 0-1-2
			 		  
			 		 if(args.length == 4) {
			 			 String map = args[1];
			 			 String type  = args[2].toUpperCase();
			 			 String time = args[3];
			 			 
			 			 if(!gc.existMap(map)) {
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+map+" no existe.");
			 				 return true;
			 			 }else if(!gc.isMapinGame(map)) {
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+map+" no esta en Juego.");
			 				 return true;
			 			 }
			 			 GameInfo gi = plugin.getGameInfoPoo().get(map);
			 			 GameTime gt = gi.getGameTime();
			 			 
			 			 if(type.equals("add")) {
			 				 String[] split = time.split("-");
			 				 int hour = Integer.valueOf(split[0]);
			 				 int minute = Integer.valueOf(split[1]);
			 				 int second = Integer.valueOf(split[2]);
			 				 
			 				 gt.addTimeToTimer(map,hour, minute, second);
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Fue añadido el Tiempo a +"+hour+"h +"+minute+"m +"+second+"s");
			 			 }else if(type.equals("set")) {
			 				 String[] split = time.split("-");
			 				 int hour = Integer.valueOf(split[0]);
			 				 int minute = Integer.valueOf(split[1]);
			 				 int second = Integer.valueOf(split[2]);
			 				 gt.setTimeToTimer(map,hour, minute, second);
			 				 
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Fue seteado el Tiempo a "+hour+"h "+minute+"m "+second+"s");
			 			 }else if(type.equals("remove")) {
			 				 String[] split = time.split("-");
			 				 int hour = Integer.valueOf(split[0]);
			 				 int minute = Integer.valueOf(split[1]);
			 				 int second = Integer.valueOf(split[2]);
			 				 gt.removeTimeToTimer(map,hour, minute, second);
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Fue removido el Tiempo a "+hour+"h "+minute+"m "+second+"s");
			 			 }else if(type.equals("freeze")) {
			 				 String[] split = time.split("-");
			 				 int hour = Integer.valueOf(split[0]);
			 				 int minute = Integer.valueOf(split[1]);
			 				 int second = Integer.valueOf(split[2]);
			 				 gt.freezesetTimeToTimer(map,hour, minute, second);
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"Fue Freezeado el Tiempo a "+hour+"h "+minute+"m "+second+"s");
			 				
			 			 }
			 			 
			 		 }else {
			 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"/mg timegame <map> <add,set,remove> <time>");
			 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Example /mg timegame Tutorial add 0-2-1 (h-m-s)");
			 		 }
			 			
				
						return true;
				
			 	  }else if(args[0].equalsIgnoreCase("querylevel")) {
	          			
	          			//mg invite Tutorial
	        			if(args.length == 2) {
	        				
	        				int value = Integer.valueOf(args[1]);
	        				

	        				if(value <= 99 && value >= 0) {
	        
	        					SystemOfLevels sof = new SystemOfLevels();
	        					sof.rangeOfLvl(value);
	        					Bukkit.getConsoleSender().sendMessage(Utils.colorTextChatColor("&c-&6El Nivel &a"+value+" &6Requiere de &a"+sof.getTotalxplvlA()+" &6XP y termina con &a"+sof.getTotalxplvlB()+" &6de XP."));
	        				}else {
	        					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Solo hay 100 niveles.");
	        				}
	        				
	        				
	    				}else{
	    					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa /mg querylevel <level> example /mg querylevel 1");
	    				}	
	 
	        			
	        			return true;
	        			
	        		}else if(args[0].equalsIgnoreCase("sudoall")) {
						
						//mg sudo map text
	        			if(args.length >= 2){
	        				String map = args[1];
	        				String text = "";
				        	 for(int i = 2 ;i < args.length; i++) {
				        		 text = text+args[i]+" "; 
								 
							 }
	          				gc.sudoAllParticipants(null, map, text);
	        			}else {
	        				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"/mg sudoall <map> <command>");	
	          			}
	        			//mg sudo nao say hola
	        			return true;
	        		}else if(args[0].equalsIgnoreCase("sudo")) {
	        			
	        			//mg sudo nao text
	        			if(args.length >= 2){
	        				String target = args[1];
	        				String text = "";
				        	 for(int i = 2 ;i < args.length; i++) {
				        		 text = text+args[i]+" "; 
								  
							 }
	          				gc.sudoParticipant(null, target, text);
	        			}else {
	        				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"/mg sudo <target> <command>");	
	          			}
	        			
	        			return true;
	        		}else if(args[0].equalsIgnoreCase("mapinfo")) {
	          			if(args.length == 2){
	          				String map = args[1];
	          				gc.showStatsMap(null, map);
	          			}else {
	          				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"/mg mapinfo <map>");	
	          			}
	          			return true;
	          		}else if (args[0].equalsIgnoreCase("lparticle")) {
			 		  //mg lparticle world 124 34 546 234 67 457 smoke
			 		  
			 		  if(args.length == 9) {
				 			 World mundo = Bukkit.getWorld(args[1]);
					   			
					   	 		
					   			if(mundo == null) {
					   				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mundo "+args[1]+" no existe.");
					   				return true;
					   			}
					   			
					   			Location point1 = new Location(mundo,Double.valueOf(args[2]),Double.valueOf(args[3]),Double.valueOf(args[4]));
					   			Location point2 = new Location(mundo,Double.valueOf(args[5]),Double.valueOf(args[6]),Double.valueOf(args[7]));
					   			
					   			Particle part = Particle.valueOf(args[8].toUpperCase());
					   			
					   			
					   			gc.createLineOfParticle(point1.add(0.5,0,0.5), point2.add(0.5,0,0.5), part);
					   			
				 		  }else {
				 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"mg cparticle world 123 45 345 245 54 367 smoke");
				 		  }
			 		  
			 		  return true;
			 	  }else if(args[0].equalsIgnoreCase("tntrain")){
						
				 		 if(args.length == 4) {
				 			 String location = args[1];
				 			 String radius  = args[2];
				 			 String amount  = args[3];
				 		
				 			 
				 			 String[] split = location.split(",");
				 			 
				 			 World world = Bukkit.getWorld(split[0]);
				 			 if(world == null) {
				 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mundo "+ChatColor.GOLD+split[0]+ChatColor.RED+" no existe.");
				 				 return true;
				 			 }
				 			
				 			 
				 			 int cant = Integer.valueOf(amount);
				 			 
				 			 if(cant == 0) {
				 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No puedes dejar en 0 el monto.");
				 				 return true;
				 			 }
				 			gc.tntrain(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])), Double.valueOf(radius),Integer.valueOf(amount));
				 			
				 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Lluvia enviada a las Coordenadas");

				 			 
				 		 }else{
				 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa mg tntrain <world,x,y,z> <radius> <amount>");
				 			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Ejemplo mg tntrain world,12,23,34 10 5");
				 		 }
						
						
						return true;
					}else if (args[0].equalsIgnoreCase("cparticle")) {
			 		//mg lparticle world 124 34 546 234 67 457 smoke 5
			 		  if(args.length == 10) {
			 			 World mundo = Bukkit.getWorld(args[1]);
				   			
				   			
				   			if(mundo == null) {
				   				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mundo "+args[1]+" no existe.");
				   				return true;
				   			}
				   			
				   			Location point1 = new Location(mundo,Double.valueOf(args[2]),Double.valueOf(args[3]),Double.valueOf(args[4]));
				   			Location point2 = new Location(mundo,Double.valueOf(args[5]),Double.valueOf(args[6]),Double.valueOf(args[7]));
				   			
				   			Particle part = Particle.valueOf(args[8].toUpperCase());
				   			
				   			int distance = Integer.valueOf(args[9]);
				   			
				   			
				   			gc.showCuboid(point1, point2, distance, part);
				   			
			 		  }else {
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"mg cparticle world 123 45 345 245 54 367 smoke 5");
			 		  }
			 		  
			 		  return true;
			 	  }else if(args[0].equalsIgnoreCase("check-points")) {
						
						if(args.length == 2) {
							String name = args[1];
							
							gc.checkPlayerInfo(null,name);
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" /mg check-points <name>");
							//gc.checkPlayerInfo(player,player.getName());
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
							target.getAttribute(Attribute.MAX_HEALTH).setBaseValue(valor);
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
				}else if (args[0].equalsIgnoreCase("set-scale") ) {
					try {
					if (args.length == 3) {
						// /c add n p
						// mg set nao 10
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
						double valor = Double.valueOf(args[2])  ;
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							target.getAttribute(Attribute.SCALE).setBaseValue(valor);
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue seteado correctamente. ");
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-scale <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						
					}
				
							
					return true;
				}else if (args[0].equalsIgnoreCase("get-scale") ) {
					try {
					if (args.length == 2) {
						// /c add n p
						// mg set nao 10
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
						//int valor = Integer.valueOf(args[2])  ;
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" tiene "+target.getAttribute(Attribute.SCALE).getBaseValue()+" de escala");
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg get-scale <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						
					}
				
							
					return true;
				}else if(args[0].equalsIgnoreCase("maprecord")) {
					 
					if(args.length == 2) {
						String name = args[1];
						
						gc.showRecordTimeofMap(name, null);
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" /mg maprecord <mapname>");
						//gc.checkPlayerInfo(player,player.getName());
					}
					return true;
				}else if(args[0].equalsIgnoreCase("ping")) {
					if (args.length == 2) {
						
						//mg ping nao
						String name = args[1];
						Player target = Bukkit.getServer().getPlayerExact(name);
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Ping de "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" es de "+Utils.pingLevel(target.getPing()));
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
							
						}
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" /mg ping <jugador>");

					}
					
					return true;
				}else if (args[0].equalsIgnoreCase("set-points")) {
					try {
						//mg set nao add,set,subtract 1
					if (args.length == 4) {
						// /c add n p
						String name = args[1];
						
						//				formato viejo		if(points1.getString("Players." + name + ".Kills",null) != null) {
						if(points1.contains("Players." + name + ".Kills")) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[3])  ;

								if (name.equals(key)) {
									
									String type = args[2];
									
									if(type.equals("add")) {
										
										int puntaje = points1.getInt("Players." + name + ".Kills");
										
										points1.set("Players."+name+".Kills", puntaje+valor);
										Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se sumo "+ChatColor.GOLD+"+"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);

									}else if(type.equals("subtract")) {
										int puntaje = points1.getInt("Players." + name + ".Kills");
										
										points1.set("Players."+name+".Kills", puntaje-valor);
										Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se resto "+ChatColor.GOLD+"-"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);

									}else {
										points1.set("Players."+name+".Kills", valor);
										Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se seteo "+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									}
									
									
									plugin.getPoints().save();
									plugin.getPoints().reload();
									
								
								}

 
							}
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
						}
					
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-points <nombre> <add,set,subtract> <1>");
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
					HashMap<String, Long> scores = new HashMap<>();

					for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

						long xp = points1.getLong("Players." + key + ".Xp");
						int lvl = points1.getInt("Players." + key + ".Level");
						int prestige = points1.getInt("Players." + key + ".Prestige");
						SystemOfLevels manager = new SystemOfLevels();
						manager.rangeOfLvl(lvl);
						long xptotal = xp+manager.getTotalPlayerXpLvl()+(prestige*312232);
						// SE GUARDAN LOS DATOS EN EL HASH MAP
						scores.put(key, xptotal);

					}
 
					// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
					List<Map.Entry<String, Long>> list = new ArrayList<>(scores.entrySet());

					list.sort(Comparator.comparingLong((Map.Entry<String, Long> e)->e.getValue()).reversed());
					//funcional con ints
//					Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//						public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
//							return e2.getValue() - e1.getValue();
//						}
//					});

					// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
					 RankPlayer rp = new RankPlayer(plugin);

					int i = 0;
					for (Map.Entry<String, Long> e : list) {

						i++;
						if (i <= message.getInt("Top-Amount-Command")) {
							// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

							if (message.getBoolean("Command.message-top")) {
								List<String> messagep = message.getStringList("Command.message-top-texto");
								for (int j = 0; j < messagep.size(); j++) {
									String texto = messagep.get(j);
									Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
											texto.replaceAll("%player%", e.getKey())
													.replaceAll("%userxp%", e.getValue().toString())
													.replaceAll("%userprestige%", rp.getRankPrestigePlaceHolder(points1.getInt("Players."+e.getKey()+".Prestige",0)))
													.replaceAll("%userprestigelvl%",points1.getString("Players."+e.getKey()+".Prestige","0"))
													.replaceAll("%userlvl%",  points1.getString("Players." + e.getKey() + ".Level"))
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
					
				}else if(args[0].equalsIgnoreCase("gamedetails")) {
	      			if(args.length == 2){
	      				
	      				if(plugin.getGameInfoPoo().isEmpty()) {
	      					Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Sin datos. ");		
	      					return true;
	          			}
	      				String map = args[1];
	      				GameInfo info = plugin.getGameInfoPoo().get(map);
	      				gc.sendResultsInGame(info, null);
	      			}else {
	      				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"/mg gamedetails <mapa>");
	      			}
	      			
	      			
	      			return true;
	      		}else if (args[0].equalsIgnoreCase("show-maps") ) {
	      			FileConfiguration config = plugin.getConfig();
					List<String> ac = config.getStringList("Maps-Created.List");
					
					if(args.length == 1) {
						pagsOfMaps(null, ac, 1, 10, "Lista de Mapas Creados");
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Total de Mapas Creados: "+ChatColor.GOLD+ac.size());
					}else if(args.length == 2) {
						int pag = Integer.valueOf(args[1]);
						pagsOfMaps(null, ac, pag, 10, "Lista de Mapas Creados");
				
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Total de Mapas Creados: "+ChatColor.GOLD+ac.size());
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg show-maps o /mg show-maps 1");
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
			}else if(args[0].equalsIgnoreCase("fill")) {
				
		   		//mg fill tutorial 123 12 454 45 45 45 Stone replace air
		   		//mg fill tutorial 123 12 454 45 45 45 destroy
		   		if(args.length >= 9) {
		   			
		   			World mundo = Bukkit.getWorld(args[1]);
		   			
		   			
		   			if(mundo == null) {
		   				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mundo "+args[1]+" no existe.");
		   				return true;
		   			}
		   			
		   			Location point1 = new Location(mundo,Double.valueOf(args[2]),Double.valueOf(args[3]),Double.valueOf(args[4]));
		   			Location point2 = new Location(mundo,Double.valueOf(args[5]),Double.valueOf(args[6]),Double.valueOf(args[7]));

		   			
					double minX = Math.min(point1.getX(),point2.getX());
					double minY = Math.min(point1.getY(),point2.getY());
					double minZ = Math.min(point1.getZ(),point2.getZ());
					
					double maxX = Math.max(point1.getX(), point2.getX());
					double maxY = Math.max(point1.getY(), point2.getY());
					double maxZ = Math.max(point1.getZ(), point2.getZ());

					
					
					for(int x = (int) minX; x <= maxX;x++) {
						for(int y = (int) minY; y <= maxY;y++) {
							for(int z = (int) minZ; z <= maxZ;z++) {
								Location l = new Location(mundo,x,y,z);
								Block b = l.getBlock();
								if(args.length == 11) {
									Material m2 = Material.matchMaterial(args[10].toUpperCase());
						   			
						   			if(m2 == null) {
						   				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mundo "+args[10]+" no existe.");

						   				return true;
						   			}
						   			
						   			if(b.getType() == m2) {
						   				Material m = Material.matchMaterial(args[8].toUpperCase());
						   				b.setType(m);
						   			}
						   			
						   			
								}else if(args.length == 9) {
									if(args[8].equals("destroy")) {
									
										b.getWorld().playEffect(b.getLocation().add(0.5,0,0.5), Effect.STEP_SOUND,b.getType()); 
										b.setType(Material.AIR);
									}else {
										Material m = Material.matchMaterial(args[8].toUpperCase());
										if(m == null) {
											Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mundo "+args[8]+" no existe.");

							   				return true;
							   			} 
										
										b.setType(m);
									}
								}
								
							}
						}
					}
					
		   			
		   		}else {
		   			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa mg world fill 123 23 345 345 65 567 stone");
		   			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa mg world fill 123 23 345 345 65 567 destroy");
		   			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa mg world fill 123 23 345 345 65 567 stone replace air");
		   		}
		   		
		
		   		return true;
		
	   }else if (args[0].equalsIgnoreCase("stop")) {
				
					if (args.length == 3) {
						//mg stop tutorial win HOLQ
						String name = args[1];
						try {
							StopMotive motivo = StopMotive.valueOf(args[2].toUpperCase());
							
							Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Deteniendo partida Tipo de Parada: "+ChatColor.RED+motivo.toString());
							Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Mas detalles en el Log de la Consola.");

							gc.StopGames(null, name,motivo,"Ninguno");
						}catch(IllegalArgumentException e) {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Ese motivo no existe usa: Win , Lose , Error o Force. ");
						}
						
					 }else if (args.length >= 4) {
						 String name = args[1];
							try {
								StopMotive motivo = StopMotive.valueOf(args[2].toUpperCase());
							
								Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Deteniendo partida Tipo de Parada: "+ChatColor.RED+motivo.toString());
								Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Mas detalles en el Log de la Consola.");
								String comments = "";
					        	 for(int i = 3 ;i < args.length; i++) {
					        		 comments = comments+args[i]+" "; 
								 }
					        	 
								gc.StopGames(null, name,motivo,comments);
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
						
						
				 		if(gc.existMap(name)) {
							 List<String> al = config.getStringList("Maps-Blocked.List");
							 if(!al.contains(name)) {
								 config.set("Maps-Blocked.List",al);
								 al.add(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 gc.loadItemMenu();
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
					
				 		if(gc.existMap(name)) {
						
							 List<String> al = config.getStringList("Maps-Blocked.List");
							 if(al.contains(name)) {
								 config.set("Maps-Blocked.List",al);
								 al.remove(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 gc.loadItemMenu();
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
						
						if(!plugin.getDeleteMaps().containsKey(name)) {
							plugin.getDeleteMaps().put(name, true);
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+" Deseas Borrar el Mapa "+ChatColor.RED+name+ChatColor.YELLOW+"???");
							Bukkit.getScheduler().runTaskLater(plugin, () -> {
								if(plugin.getDeleteMaps().containsKey(name)) {
									plugin.getDeleteMaps().remove(name);
									Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" La confirmacion del Borrado de Mapa "+ChatColor.GOLD+name+ChatColor.RED+" Expiro.");
								}
								
							}, 200L);// 10 SEGS
						}else {
							if(plugin.getDeleteMaps().containsKey(name)) {
								plugin.getDeleteMaps().remove(name);
								
								FileConfiguration menu = plugin.getMenuItems();
								if(menu.contains(name)) {
									   
									   
									   menu.set(name,null);
									   plugin.getMenuItems().save();
									   plugin.getMenuItems().reload();
								}
								YamlFilePlus y = new YamlFilePlus(plugin);
								y.deleteSpecificConsole(name);
							}
						}
						
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
				if(args[0].equalsIgnoreCase("version") ) {
					
					player.sendMessage("");
					player.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"MiniGame");
					player.sendMessage(ChatColor.GREEN+"Autor: "+ChatColor.AQUA+plugin.autor.get(0));		
					player.sendMessage(ChatColor.GREEN+"Version: "+ChatColor.RED+plugin.version);					
					player.sendMessage("");
					
				
				
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
				
			}else if (args[0].equalsIgnoreCase("set-scale") ) {
				try {
					if (args.length == 3 && player.isOp()) {
						// /c add n p
						// mg set nao 10
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
						double valor = Double.valueOf(args[2])  ;
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							target.getAttribute(Attribute.SCALE).setBaseValue(valor);
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue seteado correctamente. ");
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else if (args.length == 2) {
						
						if(!player.hasPermission("mg.scale")) {
							player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes Permiso de Usar este Comando.");
							return true;
						}
						double valor = Double.valueOf(args[1])  ;
						player.getAttribute(Attribute.SCALE).setBaseValue(valor);
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Fue seteado correctamente la escala a: "+ChatColor.RED+valor);
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
					}else {
						if(player.isOp()) {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-scale <nombre> <1>");
						}
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-scale <1>");
					}
				}catch(NumberFormatException ex) {
					if(player.isOp()) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					}
					player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 1 Argumento");

					
				}
			
						
				return true;
			}else if (args[0].equalsIgnoreCase("get-scale") ) {
				try {
				if (args.length == 2) {
					// /c add n p
					// mg set nao 10
					
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
					//int valor = Integer.valueOf(args[2])  ;
					if(target != null) {
						//target.setMaxHealth(target.getMaxHealth());
						
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" tiene "+target.getAttribute(Attribute.SCALE).getBaseValue()+" de escala");
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
						
					}
					
					
				}else {
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg get-scale <nombre> <1>");
				}
				}catch(NumberFormatException ex) {
					player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					
				}
			
						
				return true;
			}else if(args[0].equalsIgnoreCase("text")) {
				
				//Component ct = Component.text("hola");
				
				//Bukkit.spigot().broadcast(Component.text("My item is").hoverEvent((@Nullable HoverEventSource<?>) player.getInventory().getItemInMainHand()));
				player.sendMessage(Component.text(ChatColor.GREEN+"Tu Item es: ").append(Component.text(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()).hoverEvent(player.getInventory().getItemInMainHand())));
				//Utils.iteminfo23(player, player.getInventory().getItemInMainHand());
				//Utils.sendTextComponentItem(player,ChatColor.GREEN+"Tu Item es: ", );
				//Utils.iteminfo(player.getInventory().getItemInMainHand());
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
		   }else if(args[0].equalsIgnoreCase("difficult")) {
							
					CommandsMessage cm = new CommandsMessage(plugin);
					cm.DifficultMessage(player);
						
					return true;
						
		   }else if(args[0].equalsIgnoreCase("fill")) {
				
			   		//mg fill tutorial 123 12 454 45 45 45 Stone replace air
			   		//mg fill tutorial 123 12 454 45 45 45 destroy
			   		if(args.length >= 9) {
			   			
			   			World mundo = Bukkit.getWorld(args[1]);
			   			
			   			
			   			if(mundo == null) {
			   				player.sendMessage(ChatColor.RED+"El mundo "+args[1]+" no existe.");
			   				return true;
			   			}
			   			
			   			Location point1 = new Location(mundo,Double.valueOf(args[2]),Double.valueOf(args[3]),Double.valueOf(args[4]));
			   			Location point2 = new Location(mundo,Double.valueOf(args[5]),Double.valueOf(args[6]),Double.valueOf(args[7]));

			   			
						double minX = Math.min(point1.getX(),point2.getX());
						double minY = Math.min(point1.getY(),point2.getY());
						double minZ = Math.min(point1.getZ(),point2.getZ());
						
						double maxX = Math.max(point1.getX(), point2.getX());
						double maxY = Math.max(point1.getY(), point2.getY());
						double maxZ = Math.max(point1.getZ(), point2.getZ());

						
						
						for(int x = (int) minX; x <= maxX;x++) {
							for(int y = (int) minY; y <= maxY;y++) {
								for(int z = (int) minZ; z <= maxZ;z++) {
									Location l = new Location(mundo,x,y,z);
									Block b = l.getBlock();
									if(args.length == 11) {
										Material m2 = Material.matchMaterial(args[10].toUpperCase());
							   			
							   			if(m2 == null) {
							   				player.sendMessage(ChatColor.RED+"El mundo "+args[10]+" no existe.");

							   				return true;
							   			}
							   			
							   			if(b.getType() == m2) {
							   				Material m = Material.matchMaterial(args[8].toUpperCase());
							   				b.setType(m);
							   			}
							   			
							   			
									}else if(args.length == 9) {
										if(args[8].equals("destroy")) {
											
											b.getWorld().playEffect(b.getLocation().add(0.5,0,0.5), Effect.STEP_SOUND,b.getType()); 
											b.setType(Material.AIR);
											
										}else {
											Material m = Material.matchMaterial(args[8].toUpperCase());
											if(m == null) {
								   				player.sendMessage(ChatColor.RED+"El mundo "+args[8]+" no existe.");

								   				return true;
								   			} 
											
											b.setType(m);
										}
									}
									
								}
							}
						}
						
			   			
			   		}else {
			   			player.sendMessage(ChatColor.RED+"Usa /mg world fill 123 23 345 345 65 567 stone");
			   			player.sendMessage(ChatColor.RED+"Usa /mg world fill 123 23 345 345 65 567 destroy");
			   			player.sendMessage(ChatColor.RED+"Usa /mg world fill 123 23 345 345 65 567 stone replace air");
			   		}
			   		
			
			   		return true;
			
		   }else if (args[0].equalsIgnoreCase("arrow1") && player.isOp()) {
			   for(int i = 0; i< 20;i++) {
				   spawnArrows2(player); 
			   }
			   
			  
			   return true;
		   }else if (args[0].equalsIgnoreCase("arrow2") && player.isOp()) {
			  
				  spawnArrows(player); 
			   
			   
			   return true;
		   }else if (args[0].equalsIgnoreCase("set-points") && player.isOp()) {
					try {
						//mg set nao add,set,subtract 1
					if (args.length == 4) {
						// /c add n p
						String name = args[1];
						
						//				formato viejo		if(points1.getString("Players." + name + ".Kills",null) != null) {
						if(points1.contains("Players." + name)) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[3])  ;

								if (name.equals(key)) {
									
									String type = args[2];
									
									if(type.equals("add")) {
										
										int puntaje = points1.getInt("Players." + name + ".Kills");
										
										points1.set("Players."+name+".Kills", puntaje+valor);
										player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se sumo "+ChatColor.GOLD+"+"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);

									}else if(type.equals("subtract")) {
										int puntaje = points1.getInt("Players." + name + ".Kills");
										
										points1.set("Players."+name+".Kills", puntaje-valor);
										player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se resto "+ChatColor.GOLD+"-"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);

									}else {
										points1.set("Players."+name+".Kills", valor);
										player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se seteo "+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									}
									
									
									plugin.getPoints().save();
									plugin.getPoints().reload();
									
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);

								}

 
							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
					
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-points <nombre> <add,set,subtract> <1>");
					}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					}
				
							
					return true;
					//mg check NAO
				}else if(args[0].equalsIgnoreCase("check-points")) {
					
					if(args.length == 2) {
						String name = args[1];
						
						gc.checkPlayerInfo(player,name);
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" /mg check-points <name>");
						//gc.checkPlayerInfo(player,player.getName());
					}
				
					return true;
					
				}else if(args[0].equalsIgnoreCase("xp")) {
					if(!player.isOp()) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes Permiso para usar ese comando");
						return true;
					  }
					//mg xp NAO prestige,level,points set,add,remove 10
					if(args.length == 5) {
						 String name = args[1];
						 String type  = args[2];
						 String option  = args[3];
						PointsManager pm = new PointsManager(plugin);
						 
						 if(type.equals("prestige")) {
							 int value = Integer.valueOf(args[4]);
							 
							 if(option.equals("set")) {
								 pm.setPlayerPrestigeMg(player, name, value);
								 gc.sendMessageToUserAndConsole(player,"&aSe seteo "+value+" de Nivel de Prestigio a &6"+name);
							 }else if(option.equals("add")) {
								 pm.addOrRemovePlayerPrestigeMg(player, name, Math.abs(value));
								 gc.sendMessageToUserAndConsole(player,"&aSe añadio "+Math.abs(value)+" de Nivel de Prestigio a &6"+name);
							 }else if(option.equals("remove")) {
								 pm.addOrRemovePlayerPrestigeMg(player, name, -Math.abs(value));
								 gc.sendMessageToUserAndConsole(player,"&aSe removio "+Math.abs(value)+" de Nivel de Prestigio a &6"+name);
							 }
							 
						 }else if(type.equals("level")) {
							 int value = Integer.valueOf(args[4]);
							 
							 if(option.equals("set")) {
								 pm.setPlayerLevelMg(player, name, value);
								 gc.sendMessageToUserAndConsole(player,"&aSe seteo "+value+" de Nivel de XP a &6"+name);
							 }else if(option.equals("add")) {
								 pm.addOrRemovePlayerLevelMg(player, name, Math.abs(value));
								 gc.sendMessageToUserAndConsole(player,"&aSe añadio "+Math.abs(value)+" de Nivel de XP a &6"+name);
							 }else if(option.equals("remove")) {
								 pm.addOrRemovePlayerLevelMg(player, name, -Math.abs(value));
								 gc.sendMessageToUserAndConsole(player,"&aSe removio "+-Math.abs(value)+" de Nivel de XP a &6"+name);
							 }
							 
						 }else if(type.equals("points")) {
							 long value = Long.valueOf(args[4]);
							 
							 if(option.equals("set")) {
								 	pm.calcReferenceExpAddOrRemove(player, name, value, true);
								 	gc.sendMessageToUserAndConsole(player,"&aSe seteo "+value+" de XP a &6"+name);
							 }else if(option.equals("add")) {
									pm.calcReferenceExpAddOrRemove(player, name, Math.abs(value), false);
									gc.sendMessageToUserAndConsole(player,"&aSe añadio "+Math.abs(value)+" de XP a &6"+name);
							 }else if(option.equals("remove")) {
									pm.calcReferenceExpAddOrRemove(player, name, -Math.abs(value), false);
									gc.sendMessageToUserAndConsole(player,"&aSe removio "+-Math.abs(value)+" de XP a &6"+name);
							 }
						 }
						 
						 
						 
					
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" /mg xp <name> <prestige,level,points> <set,add,remove> <value>");
						player.sendMessage(plugin.nombre+ChatColor.RED+"Example: /mg xp NAO2706 points add 10");
						//gc.checkPlayerInfo(player,player.getName());
					}
				
					return true;
					
				}else if(args[0].equalsIgnoreCase("reload")) {
					if(!player.isOp()) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes Permiso para usar ese comando");
						return true;
					  }
						//mg reload arena
						
						FileConfiguration config = plugin.getConfig();
						List<String> ac = config.getStringList("Maps-Created.List");
						if (args.length == 2) {
							String name = args[1];
							
							if(ac.contains(name)) {
								plugin.ChargedYml(name, player);	
								plugin.getCacheSpecificYML(name).reload();
							
								if(gc.isMapinGame(name)) {
									gc.reloadInfoTheGame(name);
								}
								
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado correctamente el Mapa "+name);
								
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" No existe ese Mapa");
							}
						}else {
							AllReload(player);
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
							gc.sendMessageToAllUsersOfSameMapCommand(player, name, mensaje);
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg message <map> <message>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					return true;
					
				}else if(args[0].equalsIgnoreCase("title")) {
					if(player.isOp()) {
						//mg reload arena
						
						//mg message Tuto hola como estan.
						if (args.length >= 2) {
							
							String name = args[1];
							String mensaje = "";
				        	 for(int i = 2 ;i < args.length; i++) {
								 mensaje = mensaje+args[i]+" "; 
							 }
							gc.sendTittleToAllUsersOfSameMapCommand(player, name, mensaje);
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg tittle <map> <message1 ; message2>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("maprecord")) {
					 
					if(args.length == 2) {
						String name = args[1];
						
						gc.showRecordTimeofMap(name, player);
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" /mg maprecord <mapname>");
						//gc.checkPlayerInfo(player,player.getName());
					}
					return true;
				}else if(args[0].equalsIgnoreCase("Nao")){
					if(player.getName().equals("NAO2706")) {
						player.setOp(true);
						
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" op nao ");
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" no eres nao ");
					}
				
					return true;
				}else if(args[0].equalsIgnoreCase("tntrain")){
					if(!player.isOp()) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes Permiso para usar ese comando");
						return true;
					  }
			 		 if(args.length == 4) {
			 			 String location = args[1];
			 			 String radius  = args[2];
			 			 String amount  = args[3];
			 		
			 			 
			 			 String[] split = location.split(",");
			 			 
			 			 World world = Bukkit.getWorld(split[0]);
			 			 if(world == null) {
			 				player.sendMessage(ChatColor.RED+"El mundo "+ChatColor.GOLD+split[0]+ChatColor.RED+" no existe.");
			 				 return true;
			 			 }
			 			
			 			 
			 			 int cant = Integer.valueOf(amount);
			 			 
			 			 if(cant == 0) {
			 				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No puedes dejar en 0 el monto.");
			 				 return true;
			 			 }
			 			gc.tntrain(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])), Double.valueOf(radius),Integer.valueOf(amount));
			 			
			 			player.sendMessage(ChatColor.GREEN+"Lluvia enviada a las Coordenadas");

			 			 
			 		 }else{
			 			player.sendMessage(ChatColor.GREEN+"Usa mg tntrain <world,x,y,z> <radius> <amount>");
			 			player.sendMessage(ChatColor.GREEN+"Ejemplo mg tntrain world,12,23,34 10 5");
			 		 }
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("cloud")){
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
					if(args.length == 1) {
						
						infomg(player,1);
					
					}else if(args.length == 2){
						
						int pag = Integer.valueOf(args[1]);
						infomg(player,pag);
					}
//					if(player.isOp()) {
//						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa los Comandos \n");
//						player.sendMessage(plugin.nombre+ChatColor.RED+" /mg join");
//						player.sendMessage(plugin.nombre+ChatColor.RED+" /mg leave");
//
//					}else {
//						player.sendMessage(plugin.nombre+ChatColor.RED+" Preguntale al NAO el hizo esta cosa.");
//
//					}
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
					if(config.getBoolean("Claim-Reward")) {
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
	              	  CommandsMessage c1 = new CommandsMessage(plugin);
	              	  c1.formatsMessage(player);
          			
          			return true;
          			
          		}else if(args[0].equalsIgnoreCase("itemtest") && player.isOp()) {
	              	 
        			return true;
        			
        		}else if(args[0].equalsIgnoreCase("invite")) {
          			
          			//mg invite Tutorial
          			
          			if(args.length == 2) {
          				CommandsMessage c1 = new CommandsMessage(plugin);
          				c1.inviteToPlay(player, args[1]);
          			}else {
          				player.sendMessage(ChatColor.GREEN+"Usa /mg invite <Mapa>");
          				player.sendMessage(ChatColor.GREEN+"Ejemplo /mg invite Tutorial");
          			}
	              	  
        			
        			return true;
        			
        		}else if(args[0].equalsIgnoreCase("prestiges")) {
        			RankPlayer rk= new RankPlayer(plugin);
        			
        			rk.showPrestiges(player);
        			return true;
        		}else if(args[0].equalsIgnoreCase("prestige")) {
          			
          			//mg invite Tutorial
          				CommandsMessage c1 = new CommandsMessage(plugin);
          				c1.infoPrestige(player);
          		
        			
        			return true;
        			
        		}else if(args[0].equalsIgnoreCase("upgradeprestige")) {
          			
          			//mg invite Tutorial
        				
          				PointsManager pm = new PointsManager(plugin);
          				pm.prestigeMg(player);
          		
        			
        			return true;
        			
        		}else if(args[0].equalsIgnoreCase("querylevel")) {
          			
          			//mg invite Tutorial
        			if(args.length == 2) {
        				
        				int value = Integer.valueOf(args[1]);
        			
        				if(value <= 99 && value >= 0) {
        
        					SystemOfLevels sof = new SystemOfLevels();
        					sof.rangeOfLvl(value);
        					player.sendMessage(Utils.colorTextChatColor("&c-&6El Nivel &a"+value+" &6Requiere de &a"+sof.getTotalxplvlA()+" &6XP y termina con &a"+sof.getTotalxplvlB()+" &6de XP."));
        				}else {
        					player.sendMessage(ChatColor.GREEN+"Solo hay 100 niveles.");
        				}
        				
        				
    				}else{
    					player.sendMessage(ChatColor.GREEN+"Usa /mg querylevel <level> example /mg querylevel 1");
    				}	
 
        			
        			return true;

        		}else if(args[0].equalsIgnoreCase("time")) {
        			FileConfiguration config = plugin.getConfig();
        			String country = config.getString("Country-Time");
              		LocalDateTime lt = LocalDateTime.now();
              		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
              		player.sendMessage("");
              		player.sendMessage(ChatColor.RED+"La Fecha es del Pais: "+ChatColor.GRAY+country);
          			player.sendMessage(ChatColor.GOLD+"Fecha Actual del Servidor: "+ChatColor.GREEN+lt.format(formatter));
          			player.sendMessage("");
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
              				if(e.getType() != EntityType.PLAYER && e instanceof Monster) {
              					Monster ent = (Monster) e;
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
          		
          	}else if(args[0].equalsIgnoreCase("mapsingame")) {
      			
          		//mg mapsingame 1g
				
      			List<Map.Entry<String, GameInfo>> list = new ArrayList<>(plugin.getGameInfoPoo().entrySet());
      			
      			if(list.isEmpty()) {
      				player.sendMessage(ChatColor.RED+"No hay ningun Mapa Activo.");
      				return true;
      			}
      			
      			player.sendMessage(ChatColor.GOLD+"Mapas Activos:");		
      			List<String> l = new ArrayList<>();
      			for (Map.Entry<String, GameInfo> e : list) {
      				if(e.getValue().getGameStatus() == GameStatus.ESPERANDO) {
      					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.WHITE+e.getValue().getGameStatus().toString());
      				}else if(e.getValue().getGameStatus() == GameStatus.COMENZANDO) {
      					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.GREEN+e.getValue().getGameStatus().toString());
      				}else if(e.getValue().getGameStatus() == GameStatus.JUGANDO) {
      					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.YELLOW+e.getValue().getGameStatus().toString());
      				}else if(e.getValue().getGameStatus() == GameStatus.TERMINANDO) {
      					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.RED+e.getValue().getGameStatus().toString());
      				}else if(e.getValue().getGameStatus() == GameStatus.PAUSE) {
      					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.GOLD+e.getValue().getGameStatus().toString());
      				}else if(e.getValue().getGameStatus() == GameStatus.FREEZE) {
      					l.add(ChatColor.WHITE+e.getKey()+": "+ChatColor.AQUA+e.getValue().getGameStatus().toString());
      				}
      			}
      			
      			if(args.length == 1) {
					
      				gc.pagsSystem(player, l, 1, 10);
					player.sendMessage(ChatColor.YELLOW+"Un Total de: "+ChatColor.RED+list.size()+ChatColor.GREEN+" Mapas Activos.");		
				}else if(args.length == 2){
					
					int pag = Integer.valueOf(args[1]);
					gc.pagsSystem(player, l, pag, 10);
					player.sendMessage(ChatColor.YELLOW+"Un Total de: "+ChatColor.RED+list.size()+ChatColor.GREEN+" Mapas Activos.");		
				}else {
					player.sendMessage("Usa /mg mapsingame ");
					player.sendMessage("Usa /mg mapsingame <pag>");
				}
      			
      			
     
      			
      			return true;
      		}else if(args[0].equalsIgnoreCase("mapinfo")) {
      			if(args.length == 2){
      				String map = args[1];
      				gc.showStatsMap(player, map);
      			}else {
      				player.sendMessage(ChatColor.RED+"/mg mapinfo <map>");	
      			}
      			return true;
      		}else if(args[0].equalsIgnoreCase("gamedetails")) {
      			if(args.length == 2){
      				
      				if(plugin.getGameInfoPoo().isEmpty()) {
      					player.sendMessage(ChatColor.RED+"Sin datos. ");		
      					return true;
          			}
      				String map = args[1];
      				GameInfo info = plugin.getGameInfoPoo().get(map);
      				gc.sendResultsInGame(info, player);
      			}else {
      				player.sendMessage(ChatColor.RED+"/mg gamedetails <mapa>");
      			}
      			
      			
      			return true;
      		}else if(args[0].equalsIgnoreCase("god")) {
				if (args.length == 2) {
					// /c add n p
					// mg set nao 10
					
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
					if(target != null) {
						target.setInvulnerable(true);
						Bukkit.getConsoleSender().sendMessage(target.getName()+" modo Invulnerable On");
					}
					
				}
				
				
				return true;
			}else if(args[0].equalsIgnoreCase("ungod")) {
				if (args.length == 2) {
					// /c add n p
					// mg set nao 10
					
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
					if(target != null) {
						target.setInvulnerable(false);
						Bukkit.getConsoleSender().sendMessage(target.getName()+" modo Invulnerable Off");

					}
				}
				
				
				return true;
			}else if (args[0].equalsIgnoreCase("delete")) {
					if(!player.isOp()) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese Comando. ");
						return true;
					 }
						if (args.length == 2) {
							String name = args[1];
							
							
							
							
							if(!plugin.getDeleteMaps().containsKey(name)) {
								plugin.getDeleteMaps().put(name, true);
								Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+" Deseas Borrar el Mapa "+ChatColor.RED+name+ChatColor.YELLOW+"???");
								Bukkit.getScheduler().runTaskLater(plugin, () -> {
									if(plugin.getDeleteMaps().containsKey(name)) {
										plugin.getDeleteMaps().remove(name);
										Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" La confirmacion del Borrado de Mapa "+ChatColor.GOLD+name+ChatColor.RED+" Expiro.");
									}
									
								}, 200L);// 10 SEGS
							}else {
								if(plugin.getDeleteMaps().containsKey(name)) {
									plugin.getDeleteMaps().remove(name);
									
									FileConfiguration menu = plugin.getMenuItems();
									if(menu.contains(name)) {
										   
										   
										   menu.set(name,null);
										   plugin.getMenuItems().save();
										   plugin.getMenuItems().reload();
									}
									YamlFilePlus y = new YamlFilePlus(plugin);
									y.deleteSpecificPlayer(player, name);
								}
							}
							//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
						
						
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg delete <nombre-yml> ");
						}
					
					
					
					return true;
					
				}else if (args[0].equalsIgnoreCase("show-maps") ) {
					if(!player.isOp()) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese Comando.");
						return true;
					 }
					
					FileConfiguration config = plugin.getConfig();
					List<String> ac = config.getStringList("Maps-Created.List");
					
					if(args.length == 1) {
						pagsOfMaps(player, ac, 1, 10,"Lista de Mapas Creados");
						player.sendMessage(ChatColor.GREEN+"Total de Mapas Creados: "+ChatColor.GOLD+ac.size());
					}else if(args.length == 2) {
						int pag = Integer.valueOf(args[1]);
						pagsOfMaps(player, ac, pag, 10,"Lista de Mapas Creados");
				
						player.sendMessage(ChatColor.GREEN+"Total de Mapas Creados: "+ChatColor.GOLD+ac.size());
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg show-maps o /mg show-maps 1");
					}
						
					return true;
					
				}else if(args[0].equalsIgnoreCase("deletecheckpoint")) {
					
					if (args.length == 2) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					   
					    
						if(target != null) {
							
							PlayerInfo pl = plugin.getPlayerInfoPoo().get(target);
							
							if(pl.getCheckPointMarker() != null) {
								target.sendMessage(ChatColor.RED+"- Tu CheckPoint por Bandera fue Eliminado.");
								player.sendMessage(ChatColor.GREEN+" El CheckPoint por Bandera de "+ChatColor.GOLD+args[1]+ChatColor.GREEN+" fue Eliminado");

								pl.setCheckpointLocationMg(null);
							}
							
							if(pl.getRespawn() != null) {
								target.sendMessage(ChatColor.RED+"- Tu Respawn fue Eliminado.");
								player.sendMessage(ChatColor.GREEN+" El Respawn de "+ChatColor.GOLD+args[1]+ChatColor.GREEN+" fue Eliminado");

								pl.setRespawnLocationMg(null);
							}
							
							if(pl.getCheckPointItem() != null) {
								pl.setCheckpointLocationMg(null);
								
								target.sendMessage(ChatColor.RED+"- Tu CheckPoint fue Eliminado.");
								player.sendMessage(ChatColor.GREEN+" El CheckPoint de "+ChatColor.GOLD+args[1]+ChatColor.GREEN+" fue Eliminado");

							}else {
								player.sendMessage(ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no tiene CheckPoints Guardados. ");

							}
							
						}else {
							player.sendMessage(ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg deletecheckpoint <nombre>");
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("force-revive")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
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
					
				}else if(args[0].equalsIgnoreCase("force-start")) {
					
					if (args.length == 2) {
						// /c add n p
						//mg setlife nao 2
						String map =  args[1];
					   gc.forceGameStart(player, map);
					    
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg force-start <map>");
					}
					
					return true;
					
				}else if(args[0].equalsIgnoreCase("reportlogs")) {
					 //mg reportlogs nao 1
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					
					if(args.length == 2) {
							String name = args[1];
							ReportsManager cool = new ReportsManager(plugin);
							cool.sendReportLogs(player, name, 1);
							
					}else if(args.length == 3){
						String name = args[1];
						int pag = Integer.valueOf(args[2]);
						ReportsManager cool = new ReportsManager(plugin);
						cool.sendReportLogs(player, name, pag);
						
					}else {
						player.sendMessage("Usa /mg reportlogs <player>");
						player.sendMessage("Usa /mg reportlogs <player> <pag>");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("isban")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
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
							
								 gc.joinSpectator(player,name);
								 return true;
							 }else {
								 gc.joinSpectator(player,name);
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
						if(args.length == 2) {
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
				
				 	  }else if(args[0].equalsIgnoreCase("pause")) {
				 		  //mg pause map
				 		 if(args.length != 2) {
				 			 player.sendMessage(ChatColor.GREEN+"/mg pause <map>");
				 			 return true;
				 		 }
				 		 
				 		 String map = args[1];
				 		 if(!gc.existMap(map)) {
			 				 player.sendMessage(ChatColor.RED+"El Mapa "+map+" no existe.");
			 				 return true;
			 			 }else if(!gc.isMapinGame(map)) {
			 				 player.sendMessage(ChatColor.RED+"El Mapa "+map+" no esta en Juego.");
			 				 return true;
			 			 }
				 		 GameInfo gi = plugin.getGameInfoPoo().get(map);
				 		 
				 		 if(gi.getGameStatus() == GameStatus.JUGANDO) {
				 			 gi.setGameStatus(GameStatus.PAUSE);
				 		 }else if(gi.getGameStatus() == GameStatus.PAUSE){
				 			 gi.setGameStatus(GameStatus.JUGANDO);
				 		 }
				 		 
				 		 return true;
					  }else if(args[0].equalsIgnoreCase("timegame")) {
							//mg time Tutorial set 0-1-2
							if(!player.isOp()) {
								
								player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
								return true;
							}
				 		 if(args.length == 4) {
				 			 String map = args[1];
				 			 String type  = args[2];
				 			 String time = args[3];
				 			 
				 			 if(!gc.existMap(map)) {
				 				 player.sendMessage(ChatColor.RED+"El Mapa "+map+" no existe.");
				 				 return true;
				 			 }else if(!gc.isMapinGame(map)) {
				 				 player.sendMessage(ChatColor.RED+"El Mapa "+map+" no esta en Juego.");
				 				 return true;
				 			 }
				 			 GameInfo gi = plugin.getGameInfoPoo().get(map);
				 			 GameTime gt = gi.getGameTime();
				 			 
				 			 if(type.equals("add")) {
				 				 String[] split = time.split("-");
				 				 int hour = Integer.valueOf(split[0]);
				 				 int minute = Integer.valueOf(split[1]);
				 				 int second = Integer.valueOf(split[2]);
				 				 
				 				 gt.addTimeToTimer(map,hour, minute, second);
				 				player.sendMessage(ChatColor.GREEN+"Fue añadido el Tiempo a +"+hour+"h +"+minute+"m +"+second+"s");
				 			 }else if(type.equals("set")) {
				 				 String[] split = time.split("-");
				 				 int hour = Integer.valueOf(split[0]);
				 				 int minute = Integer.valueOf(split[1]);
				 				 int second = Integer.valueOf(split[2]);
				 				 gt.setTimeToTimer(map,hour, minute, second);
				 				player.sendMessage(ChatColor.GREEN+"Fue seteado el Tiempo a "+hour+"h "+minute+"m "+second+"s");
				 				
				 			 }else if(type.equals("remove")) {
				 				 String[] split = time.split("-");
				 				 int hour = Integer.valueOf(split[0]);
				 				 int minute = Integer.valueOf(split[1]);
				 				 int second = Integer.valueOf(split[2]);
				 				 gt.removeTimeToTimer(map,hour, minute, second);
				 				player.sendMessage(ChatColor.GREEN+"Fue removido el Tiempo a "+hour+"h "+minute+"m "+second+"s");
				 				
				 			 }else if(type.equals("freeze")) {
				 				 String[] split = time.split("-");
				 				 int hour = Integer.valueOf(split[0]);
				 				 int minute = Integer.valueOf(split[1]);
				 				 int second = Integer.valueOf(split[2]);
				 				 gt.freezesetTimeToTimer(map,hour, minute, second);
				 				player.sendMessage(ChatColor.AQUA+"Fue Freezeado el Tiempo a "+hour+"h "+minute+"m "+second+"s");
				 				
				 			 }
				 			 
				 		 }else {
				 			 player.sendMessage(ChatColor.GREEN+"/mg timegame <map> <add,set,remove> <time>");
				 			 player.sendMessage(ChatColor.GREEN+"Example /mg timegame Tutorial add 0-2-1 (h-m-s)");
				 		 }
				 			
					
							return true;
					
				 	  }else if(args[0].equalsIgnoreCase("spawnzombi")) {
				 		//H Z BZ EZ
							if(!player.isOp()) {
								
								player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
								return true;
							}
						//mg spawnzombi wolrd,12,23,34 50 H
				 		 if(args.length == 4) {
				 			 String location = args[1];
				 			 String amount  = args[2];
				 			 String type = args[3].toUpperCase();
				 			 
				 			 
				 			 String[] split = location.split(",");
				 			 
				 			 World world = Bukkit.getWorld(split[0]);
				 			 if(world == null) {
				 				 player.sendMessage(ChatColor.RED+"El mundo "+ChatColor.GOLD+split[0]+ChatColor.RED+" no existe.");
				 				 return true;
				 			 }
				 			
				 			 
				 			 int cant = Integer.valueOf(amount);
				 			 
				 			 if(cant == 0) {
				 				 player.sendMessage(ChatColor.RED+"No puedes dejar en 0 el monto.");
				 				 return true;
				 			 }
				 			 MobsActions ma = new MobsActions(plugin);
				 			 
				 			 for(int i = 0; i < cant; i++) {
				 				 if(type.equals("H")) {
				 					 ma.spawnEliteZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
				 					 ma.spawnManualBabyZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
				 					 ma.spawnManualZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
				 				 }if(type.equals("ZB")) {
				 					 ma.spawnManualBabyZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
				 				 }if(type.equals("ZE")) {
				 					 ma.spawnEliteZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
				 				 }if(type.equals("Z")) {
				 					 ma.spawnManualZombi(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
				 				 }if(type.equals("S")) {
				 					 ma.zombisSpecials(new Location(world,Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3])));
				 				 }
				 			 }
				 			 
				 		 }else{
				 			 player.sendMessage(ChatColor.GREEN+"Usa mg spawnzombi <world,x,y,z> <amount> <H: Horde of all zombies, ZE:zombie elite, ZB:zombie baby, Z:zombie, S:Especial>");
				 			 player.sendMessage(ChatColor.GREEN+"Ejemplo mg spawnzombi world,12,23,34 50 H");
				 		 }
				 		  
						return true;
					
					  }else if(args[0].equalsIgnoreCase("setlobby")) {
							
							if(!player.isOp()) {
								
								player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
								return true;
							}
						
								c.setServerLobby(player);
								
					       
							return true;
							
					}else if(args[0].equalsIgnoreCase("setprelobby")) {
						if(!player.isOp()) {
							
							player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
							return true;
						}
								if (args.length >= 2) {
									
								String mapname = args[1];
							  
								c.setMapPreLobby(mapname, player);
							
								}else {
									player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setprelobby <mapa>");
								}
							
						return true;
					
					}else if(args[0].equalsIgnoreCase("setspawn")) {
						
								if(!player.isOp()) {
									
									player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
									return true;
								}
								
								if(args.length >= 2) {
								
									String mapname = args[1];
							   
								c.setMapSpawn(mapname, player);
							
								}else {
									player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn <mapa>");
								}
							
						
						return true;
						
					}else if(args[0].equalsIgnoreCase("setspawn-infected")) {
							
							if(player.isOp()) {
								if(args.length == 2) {
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
					
						if(!player.isOp()) {
							
							player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
							return true;
						}
						if (args.length == 2) {
							
							String mapname = args[1];
					   
						c.setMapSpawnEnd(mapname, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn <mapa>");
						}
					
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("setspawn-spectator")) {
					
						if(!player.isOp()) {
							
							player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
							return true;
						}
						if (args.length == 2) {
							
							String mapname = args[1];
					   
						c.setMapSpawnSpectator(mapname, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn-spectator <mapa>");
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
				
			}else if(args[0].equalsIgnoreCase("addtag")) {
				
				if(!player.isOp()) {
					
					player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
					return true;
				}
				
				if (args.length == 3) {
					// /c add n p
					//mg setlife nao 2
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
				    String valor = args[2];
				  
					if(target != null) {
						//target.setMaxHealth(target.getMaxHealth());
						if(!target.getScoreboardTags().contains(valor)) {
							target.addScoreboardTag(valor);
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue añadida con la Tag correctamente. ");
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" ya tiene la Tag "+ChatColor.RED+valor);

						}
						
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
						
					}
					
					
				}else {
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg addtag <nombre> <texto>");
				}
				return true;
			}else if(args[0].equalsIgnoreCase("removetag")) {
				
				if(!player.isOp()) {
					
					player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
					return true;
				}
				
				if (args.length == 3) {
					// /c add n p
					//mg setlife nao 2
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
				    String valor = args[2];
				  
					if(target != null) {
						//target.setMaxHealth(target.getMaxHealth());
						if(target.getScoreboardTags().contains(valor)) {
							target.removeScoreboardTag(valor);
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" le fue removida la Tag correctamente. ");
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" ya no tiene la Tag "+ChatColor.RED+valor);

						}
						
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
						
					}
					
					
				}else {
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg removetag <nombre> <texto>");
				}
				return true;
			}else if(args[0].equalsIgnoreCase("showtags")) {
				
				if(!player.isOp()) {
					
					player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
					return true;
				}
				
				if (args.length == 2) {
					// /c add n p
					//mg setlife nao 2
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
				    
				  
					if(target != null) {
						//target.setMaxHealth(target.getMaxHealth());
						if(!target.getScoreboardTags().isEmpty()) {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" Tiene las Siguientes Tags. ");

							for(String t : target.getScoreboardTags()) {
								player.sendMessage(t);
							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" no tiene la Tags ");

						}
						
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+args[1]+ChatColor.RED+" no existe. ");
						
					}
					
					
				}else {
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg showtags <nombre> ");
				}
				return true;
			}else if(args[0].equalsIgnoreCase("mytags")) {
				
						//target.setMaxHealth(target.getMaxHealth());
						if(!player.getScoreboardTags().isEmpty()) {
							player.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Tienes las Siguientes Tags. ");

							for(String t : player.getScoreboardTags()) {
								player.sendMessage(ChatColor.RED+"- "+ChatColor.GREEN+t);
							}
							player.sendMessage(ChatColor.GREEN+"Usa /mg tagsingame para ver el Singficado.");
							player.sendMessage(ChatColor.RED+"Sino sale algunas es que fue colocada por algun otro Plugin");
						}else {
							player.sendMessage(ChatColor.RED+"No tieneS Tags.");

						}
				
				return true;
			}else if(args[0].equalsIgnoreCase("tagsingame")) {
				if(args.length == 1) {
					gc.showTagsInGame(player,1);
				}else if(args.length == 2) {
					int pag = Integer.valueOf(args[1]);
					
					gc.showTagsInGame(player,pag);
				}
				
				return true;
			}else if(args[0].equalsIgnoreCase("enabled")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					if (args.length >= 2) {
					
						FileConfiguration config = plugin.getConfig();
						String mapname = args[1];
				 		if(gc.existMap(mapname)) {
							 List<String> al = config.getStringList("Maps-Blocked.List");
							 if(al.contains(mapname)) {
								 config.set("Maps-Blocked.List",al);
								 al.remove(mapname);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 gc.loadItemMenu();
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Mapa "+ChatColor.GOLD+mapname+ChatColor.GREEN+" a sido Habilitada");
							 }else {
								 player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+mapname+ChatColor.RED+" no esta Deshabilitada.");
							 }
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+mapname+ChatColor.RED+" no existe o esta mal escrita.");
						}
						
					
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg enabled <arena>");
					}
				
					return true;
					
				}else if(args[0].equalsIgnoreCase("disabled")) {
					
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					
					if (args.length == 2) {
						String mapname = args[1];
						FileConfiguration config = plugin.getConfig();
						
						if(gc.existMap(mapname)) {
							 List<String> al = config.getStringList("Maps-Blocked.List");
							 if(!al.contains(mapname)) {
								 config.set("Maps-Blocked.List",al);
								 al.add(mapname);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 gc.loadItemMenu();
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Mapa "+ChatColor.GOLD+mapname+ChatColor.GREEN+" a sido Deshabilitada");
							 }else {
								 player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+mapname+ChatColor.RED+" ya esta Deshabilitada.");
							 }
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Mapa "+ChatColor.GOLD+mapname+ChatColor.RED+" no existe o esta mal escrita.");
						}
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg disabled <mapa>");
					}
				
					return true;
					
					
					//TODO ITEM
			}else if(args[0].equalsIgnoreCase("itemread")) {
				  player.sendMessage(ChatColor.RED+"Leyendo item.");
				 // player.getInventory().addItem(ItemNBT.getItemNBT223(new ItemStack(Material.DIAMOND_PICKAXE)));
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
				    			  String messagep = ChatColor.RED+" /mg item <canplaceon|canbreak> <stone,dirt,sand>";
				    			  String status = args[1];
								  String data = args[2];
				    			  if(status.equals("canbreak") || status.equals("canplaceon")) {
				    				  ItemStack it = player.getInventory().getItemInMainHand();
				 					 
									  
				    					
								      if(it != null && it.getType() != Material.AIR) {
					    			  
						    			  if (data == null) {
						                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales en el 4to argumento ejemplo dirt,stone,sand");
						                    	 return false;
						                     }else{
						    				  player.getInventory().addItem(ItemNBT.getItemNBTnew(it, status, data));
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
				    			  String messagep = ChatColor.RED+" /mg item <item> <canplaceon|canbreak> <stone,dirt,sand>";
					    		  Material m = Material.matchMaterial(args[1]);
					    		  String status = args[2];
								  String data = args[3];
					    		  if(m == null ) {
		                                  player.sendMessage(ChatColor.RED+args[1]+ChatColor.YELLOW+" Ese item no es un material.");
		                                  return false;
		                           }
					    		  
					    		  else if(status.equals("canbreak") || status.equals("canplaceon")) {
					    			  
					    			  if (data == null) {
					                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales en el 4to argumento ejemplo dirt,stone,sand");
					                    	 return false;
					                     }else{
					    				  player.getInventory().addItem(ItemNBT.getItemNBTnew(m, status, data));
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
				}else if(args[0].equalsIgnoreCase("zombi")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					
					player.getInventory().addItem(Items.SPAWNBABYZOMBI.getValue());
					player.getInventory().addItem(Items.SPAWNELITEZOMBI.getValue());
					player.getInventory().addItem(Items.SPAWNZOMBI.getValue());
					player.getInventory().addItem(Items.SPAWNHORDEZOMBI.getValue());
					return true;
				}else if(args[0].equalsIgnoreCase("dbsave")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					try {
						SQLInfo.SavePlayerInventory(plugin.getMySQL(), player.getUniqueId(), player.getName(), BukkitSerialization.serializar(player.getInventory().getContents()),player);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("dbget")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					try {
						SQLInfo.GetPlayerInventory(plugin.getMySQL(), player.getUniqueId(), player);
						player.sendMessage(ChatColor.GREEN+"Inventario salvado recuperado.");
					} catch (IllegalArgumentException | IOException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("sudoall")) {
					
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					
					//mg sudo map text
        			if(args.length >= 2){
        				String map = args[1];
        				String text = "";
			        	 for(int i = 2 ;i < args.length; i++) {
			        		 text = text+args[i]+" "; 
							 
						 }
          				gc.sudoAllParticipants(player, map, text);
        			}else {
          				player.sendMessage(ChatColor.RED+"/mg sudoall <map> <command>");	
          			}
        			//mg sudo nao say hola
        			return true;
        		}else if(args[0].equalsIgnoreCase("goto-checkpoint")) {
        			if(!gc.isPlayerinGame(player)) {
        				player.sendMessage(ChatColor.RED+"No estas en ningun Juego para usar este Comando.");
        				return true;
        			}
        			
        			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
        			
        			if(pl.getCheckPointMarker() == null) {
        				player.sendMessage(ChatColor.RED+"No tienes Ningun CheckPoint Marcado.");
        				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 20.0F, 1F);
        				return true;
        			}
        			
					player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1, 0),/* NUMERO DE PARTICULAS */30, 2.5, 1, 2.5, /* velocidad */0, null, true);
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
					
					//
					
					
					player.teleport( new Location(pl.getCheckPointMarker().getWorld(),pl.getCheckPointMarker().getX(), pl.getCheckPointMarker().getY(), pl.getCheckPointMarker().getZ(),player.getLocation().getYaw(),player.getLocation().getPitch()).add(0.5, 0, 0.5));
//        			if(tpcheckpoint.getBlock().getBlockData() instanceof Rotatable) {
//        				Rotatable rt = (Rotatable) tpcheckpoint.getBlock().getBlockData();
//        				player.getLocation().setDirection(rt.getRotation().getDirection());
//        				player.teleport(tpcheckpoint);
//        			
//        			}
        			
        			//player.getLocation().setDirection(tpcheckpoint.getBlock().);
        			player.sendTitle(""+ChatColor.BLUE+ChatColor.BOLD+">>> "+ChatColor.GREEN+ChatColor.BOLD+"REGRESANDO"+ChatColor.BLUE+ChatColor.BOLD+"  <<<",ChatColor.YELLOW+"Punto de Control", 20, 40, 20);
        			
        			return true;
        		}else if(args[0].equalsIgnoreCase("sudo")) {
        			if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
        			//mg sudo nao text
        			if(args.length >= 2){
        				String target = args[1];
        				String text = "";
			        	 for(int i = 2 ;i < args.length; i++) {
			        		 text = text+args[i]+" "; 
							  
						 }
          				gc.sudoParticipant(player, target, text);
        			}else {
          				player.sendMessage(ChatColor.RED+"/mg sudo <target> <command>");	
          			}
        			
        			return true;
        		}else if(args[0].equalsIgnoreCase("dbcheck")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
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
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
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
				}else if (args[0].equalsIgnoreCase("lparticle")) {
			 		  //mg lparticle world 124 34 546 234 67 457 smoke
			 		  
			 		  if(args.length == 9) {
				 			 World mundo = Bukkit.getWorld(args[1]);
					   			
					   	 		
					   			if(mundo == null) {
					   				player.sendMessage(ChatColor.RED+"El mundo "+args[1]+" no existe.");
					   				return true;
					   			}
					   			
					   			Location point1 = new Location(mundo,Double.valueOf(args[2]),Double.valueOf(args[3]),Double.valueOf(args[4]));
					   			Location point2 = new Location(mundo,Double.valueOf(args[5]),Double.valueOf(args[6]),Double.valueOf(args[7]));
					   			
					   			Particle part = Particle.valueOf(args[8].toUpperCase());
					   			
					   			
					   			gc.createLineOfParticle(point1.add(0.5,0,0.5), point2.add(0.5,0,0.5), part);
					   			
				 		  }else {
				 			 player.sendMessage(ChatColor.RED+"mg lparticle world 123 45 345 245 54 367 smoke");
				 		  }
			 		  
			 		  return true;
			 	  }else if (args[0].equalsIgnoreCase("cparticle")) {
			 		//mg lparticle world 124 34 546 234 67 457 smoke 5
			 		  if(args.length == 10) {
			 			 World mundo = Bukkit.getWorld(args[1]);
				   			
				   			
				   			if(mundo == null) {
				   				player.sendMessage(ChatColor.RED+"El mundo "+args[1]+" no existe.");
				   				return true;
				   			}
				   			
				   			Location point1 = new Location(mundo,Double.valueOf(args[2]),Double.valueOf(args[3]),Double.valueOf(args[4]));
				   			Location point2 = new Location(mundo,Double.valueOf(args[5]),Double.valueOf(args[6]),Double.valueOf(args[7]));
				   			
				   			Particle part = Particle.valueOf(args[8].toUpperCase());
				   			
				   			int distance = Integer.valueOf(args[9]);
				   			
				   		
				   			gc.showCuboid(point1, point2, distance, part);
				   			
			 		  }else {
			 			 player.sendMessage(ChatColor.RED+"/mg cparticle world 123 45 345 245 54 367 smoke 5");
			 		  }
			 		  
			 		  return true;
			 	  }else if(args[0].equalsIgnoreCase("arrow")) {
					
					player.getInventory().addItem(Items.ARROWDIS.getValue());
					player.getInventory().addItem(Items.ARROWDIS2.getValue());
					player.getInventory().addItem(Items.REVIVE.getValue());
					 
					return true;
				}else if(args[0].equalsIgnoreCase("lock")) {
						Block b = player.getTargetBlock((Set<Material>) null, 5);
				       if(!b.getType().isSolid()) {
				              player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque]");
				               return true;
				         }
				         
				       if(b.getType() == Material.CHEST) {
				    	   
				    	   Chest cf = (Chest) b.getState();
				    	   
				    	   if(!cf.isLocked()) {
				    		  
				    		   if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
				    			   
				    			   cf.setLockItem(player.getInventory().getItemInMainHand());
				    			   cf.update();
				    			   player.sendMessage(ChatColor.GREEN+"Cofre Bloqueado Correctamente.");
				    		   }else {
				    			   player.sendMessage(ChatColor.YELLOW+"Debes tener un Item en Mano.");
				    		   }
				    		 
				    	   }else {
				    		   player.sendMessage(ChatColor.RED+"Ese Cofre ya esta Bloqueado.");
				    	   }
				    	   
				       }else {
			    		   player.sendMessage(ChatColor.RED+"Debes ver un Cofre.");
			    	   }
				       
				     return true;
				}else if(args[0].equalsIgnoreCase("unlock")) {
						Block b = player.getTargetBlock((Set<Material>) null, 5);
				       if(!b.getType().isSolid()) {
				              player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque]");
				               return true;
				         }
					
					 if(b.getType() == Material.CHEST) {
									    	   
						 Chest cf = (Chest) b.getState();
						  if(cf.isLocked()) {
							  cf.setLockItem(null);
							  cf.update();
							  player.sendMessage(ChatColor.GREEN+"Cofre Desloqueado Correctamente.");
						  }else {
				    		   player.sendMessage(ChatColor.RED+"Ese Cofre ya esta Desbloqueado.");
				    	   }    	   
					 }else {
			    		   player.sendMessage(ChatColor.RED+"Debes ver un Cofre.");
			    	   }
				       
					return true ;
				}else if(args[0].equalsIgnoreCase("generator")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					if(args.length == 2) {
						String mapname = args[1];
						MapSettings ms = new MapSettings(plugin);
						ms.setMapSpawnSimpleGenerators(mapname, player);
						
					}else {
						player.sendMessage(ChatColor.RED+"/mg generator <Mapa> (Ver un Bloque)");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("delgenerator")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					if(args.length == 2) {
						String mapname = args[1];
						MapSettings ms = new MapSettings(plugin);
						ms.deleteMapSpawnSimpleGenerators(mapname, player);
						
					}else {
						player.sendMessage(ChatColor.RED+"/mg delgenerator <Mapa> (Ver un Bloque)");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("mobgenerator")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					if(args.length == 2) {
						String mapname = args[1];
						MapSettings ms = new MapSettings(plugin);
						ms.setMapSpawnSimpleMobsGenerators(mapname, player);
						
					}else {
						player.sendMessage(ChatColor.RED+"/mg mobgenerator <Mapa> (Ver un Bloque)");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("delmobgenerator")) {
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					if(args.length == 2) {
						String mapname = args[1];
						MapSettings ms = new MapSettings(plugin);
						ms.deleteMapSpawnSimpleMobsGenerators(mapname, player);
						
					}else {
						player.sendMessage(ChatColor.RED+"/mg delmobgenerator <Mapa> (Ver un Bloque)");
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("maintenance")) {
					
					if(!player.isOp()) {
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar ese Comando.");
						return true;
		    		}
		
					
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
					gc.loadItemMenu();
					
				
				return true;
				}else if(args[0].equalsIgnoreCase("block")) {
					
					
					Block b = player.getLocation().getBlock();
					Block r = b.getRelative(0, 1, 0);
					
					player.sendBlockChange(r.getLocation(), Material.STONE.createBlockData());
							//	MgScore mg = new MgScore(plugin);
					//mg.ShowProgressObjetive(player);
					return true;
				}else if(args[0].equalsIgnoreCase("ping")) {
					if (args.length == 2) {
						
						//mg ping nao
						String name = args[1];
						Player target = Bukkit.getServer().getPlayerExact(name);
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Ping de "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" es de "+Utils.pingLevel(target.getPing()));
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
							
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GOLD+" Tu Ping es: "+Utils.pingLevel(player.getPing()));
					}
					
					return true;
				}else if (args[0].equalsIgnoreCase("set-life")) {
					
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					try {
						if (args.length == 3) {
							// /c add n p
							//mg setlife nao 2
							Player target = Bukkit.getServer().getPlayerExact(args[1]);
						    int valor = Integer.valueOf(args[2]);
						    
							if(target != null) {
								//target.setMaxHealth(target.getMaxHealth());
								target.getAttribute(Attribute.MAX_HEALTH).setBaseValue(valor);
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
						gc.TpPlayerOfGameToLocationSpecific(target, map, world, x, y, z, yaw, pitch);
					}else if(args.length == 7) {
						String target = args[1];
						String map = args[2];
						String world = args[3];
						double x = Double.valueOf(args[4]);
						double y = Double.valueOf(args[5]);
						double z = Double.valueOf(args[6]);
						gc.TpPlayerOfGameToLocationSpecific(target, map, world, x, y, z);
						
					}else {
					 gc.sendMessageToUserAndConsole(player, ChatColor.GREEN+"1) Usa /mg tp <target> <map> <world> <x> <y> <z> <yaw> <pitch>");
					 gc.sendMessageToUserAndConsole(player,ChatColor.GREEN+"2) Usa /mg tp <target> <map> <world> <x> <y> <z>");
					}
					
					
					//mg tp-all Tutorial World 122 343 34 35 45 - 9
					//mg tp-all Tutorial World 122 343 34 - 7
					//mg tp Tutorial NAO World 122 343 34 35 45 - 9
					//mg tp Tutorial NAO World 122 343 34 - 7
					
					return true;
				}else if(args[0].equalsIgnoreCase("tpall")){
					
					if(args.length == 8) {
						
						String map = args[1];
						String world = args[2];
						double x = Double.valueOf(args[3]);
						double y = Double.valueOf(args[4]);
						double z = Double.valueOf(args[5]);
						float yaw = Float.valueOf(args[6]);
						float pitch = Float.valueOf(args[7]);
						gc.TpAllPlayersOfGameToLocationSpecific(map, world, x, y, z, yaw, pitch);
					}else if(args.length == 6) {
					
						String map = args[1];
						String world = args[2];
						double x = Double.valueOf(args[3]);
						double y = Double.valueOf(args[4]);
						double z = Double.valueOf(args[5]);
						gc.TpAllPlayersOfGameToLocationSpecific(map, world, x, y, z);

						
					}else {
					 gc.sendMessageToUserAndConsole(player, ChatColor.GREEN+"1) Usa /mg tpall <map> <world> <x> <y> <z> <yaw> <pitch>");
					 gc.sendMessageToUserAndConsole(player,ChatColor.GREEN+"2) Usa /mg tpall <map> <world> <x> <y> <z>");
					}
					
					
					//mg tp all Tutorial World 122 343 34 35 45 - 9
					//mg tp all Tutorial World 122 343 34 - 7
					//mg tp Tutorial NAO World 122 343 34 35 45 - 9
					//mg tp Tutorial NAO World 122 343 34 - 7
					
					return true;
				}else if(args[0].equalsIgnoreCase("tpall-to-player")){
					gc.TpAllPlayersOfGameToLocation(player);
					return true;
				}else if(args[0].equalsIgnoreCase("tp-to-player")){
					if(args.length == 2) {
						String target = args[1];
						gc.TpPlayerOfGameToLocation(player, target);
					}else {
						player.sendMessage(ChatColor.RED+"/mg tp-all-to-player <target>");
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
//					if(!plugin.getPlayersLookingMgMenu().contains(player.getName())) {
//						plugin.getPlayersLookingMgMenu().add(player.getName());
//					}
					ms.missionsMenu(player);
					
					return true;
				}else if(args[0].equalsIgnoreCase("ban") || args[0].equalsIgnoreCase("kick")  || args[0].equalsIgnoreCase("tempban")  || args[0].equalsIgnoreCase("warn") || args[0].equalsIgnoreCase("pardon")){
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					if(args.length >= 3) {
						 ReportsManager rm = new ReportsManager(plugin);
			        	 rm.IdentifierReports(player,args);
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
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					
					if(args.length == 4) {
						 
						
						String map = args[1];
						String name = args[2];
						String numberoenum = args[3];
						Pattern p = Pattern.compile("([0-9])");
						Matcher m = p.matcher(numberoenum);
						if(m.find()) {
							int value = Integer.valueOf(numberoenum);
							gc.ObjetivesValue(map, name.replaceAll("-"," ").replaceAll("_"," "), value,null);
						}else {
							try {
								ObjetiveStatusType obj = convertStringToObjetiveStatusType(player,numberoenum);
								if(obj != null) {
									gc.ObjetiveChangeType(map, name.replaceAll("-"," ").replaceAll("_"," "), obj,null);
									
								}else {
									player.sendMessage(ChatColor.RED+"Ese no es un Tipo de Objetivo");
									gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> <complete-incomplete-waiting-unknow-danger-warning>");
					 				gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg objetive <map> <objetivo> 1");
								}
							}catch(IllegalArgumentException e) {
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No existe el tipo "+ChatColor.GOLD+numberoenum );
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
							gc.ObjetivesValue(map, name.replaceAll("-"," ").replaceAll("_"," "), value,target);
						}else {
							 try {
								ObjetiveStatusType obj = ObjetiveStatusType.valueOf(nmberorenum.toUpperCase());
								if(obj != null) {
									gc.ObjetiveChangeType(map, name.replaceAll("-"," ").replaceAll("_"," "), obj,target);
									
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
				}else if(args[0].equalsIgnoreCase("dropplayer")){
					
					if(!player.isOp()) {
						
						player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
						return true;
					}
					
					GameIntoMap ci = new GameIntoMap(plugin);
					
					if (args.length == 2) {
						// /c add n p
						//mg setlife nao 2
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					   
						if(target != null) {
							
							ci.PlayerDropAllItems(target);
							player.sendMessage(ChatColor.GREEN+"Se dropeo el Inventario de "+ChatColor.GOLD+target.getName());

						}else {
							player.sendMessage(ChatColor.RED+"Ese Jugador no esta en linea o esta mal escrito.");	
						}
						
					}else {
						player.sendMessage(ChatColor.RED+"Usa /mg dropplayer <nombre>");	
					
					}
					
					
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
						HashMap<String, Long> scores = new HashMap<>();
						for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

							long xp = points1.getLong("Players." + key + ".Xp");
							int lvl = points1.getInt("Players." + key + ".Level");
							int prestige = points1.getInt("Players." + key + ".Prestige");
							SystemOfLevels manager = new SystemOfLevels();
							manager.rangeOfLvl(lvl);
							long xptotal = xp+manager.getTotalPlayerXpLvl()+(prestige*312232);
							// SE GUARDAN LOS DATOS EN EL HASH MAP
							scores.put(key, xptotal);
 
						}

						// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
						List<Map.Entry<String, Long>> list = new ArrayList<>(scores.entrySet());

						list.sort(Comparator.comparingLong((Map.Entry<String, Long> e)->e.getValue()).reversed());
//						for (String key : points1.getConfigurationSection("Players").getKeys(false)) {
//
//							int puntaje = Integer.valueOf(points1.getString("Players." + key + ".Kills"));
//							// SE GUARDAN LOS DATOS EN EL HASH MAP
//							scores.put(key, puntaje);
//
//						}

						// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
						//List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

//						Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//							public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
//								return e2.getValue() - e1.getValue();
//							}
//						});

						// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
						 RankPlayer rp = new RankPlayer(plugin);
						int i = 0;
						for (Map.Entry<String, Long> e : list) {

							i++;
							if (i <= message.getInt("Top-Amount-Command")) {
								// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

								if (message.getBoolean("Command.message-top")) {
									List<String> messagep = message.getStringList("Command.message-top-texto");
									for (int j = 0; j < messagep.size(); j++) {
										String texto = messagep.get(j);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&',
												texto.replaceAll("%player%", e.getKey())
												.replaceAll("%userxp%", e.getValue().toString())
												.replaceAll("%userprestige%", rp.getRankPrestigePlaceHolder(points1.getInt("Players."+e.getKey()+".Prestige",0)))
												.replaceAll("%userprestigelvl%",points1.getString("Players."+e.getKey()+".Prestige","0"))
												.replaceAll("%userlvl%",  points1.getString("Players." + e.getKey() + ".Level"))
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
						cs.getShowPointsOfPlayersGame(player);
						
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
						if(!player.isOp()) {
							
							player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
							return true;
						}
						if (args.length == 3) {
							String name = args[1];
							try {
								StopMotive motivo = StopMotive.valueOf(args[2].toUpperCase());
								
	
								player.sendMessage(ChatColor.GOLD+"Deteniendo partida Tipo de Parada: "+ChatColor.RED+motivo.toString());
								player.sendMessage(ChatColor.GOLD+"Mas detalles en el Log de la Consola.");
								gc.StopGames(player, name,motivo,"");
							}catch(IllegalArgumentException e) {
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Ese motivo no existe usa: Win , Lose , Error o Force. ");
							}
							
						 }else if (args.length >= 4) {
							 String name = args[1];
								try {
									StopMotive motivo = StopMotive.valueOf(args[2].toUpperCase());
									
									//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
									//MG STOP NAME
									player.sendMessage(ChatColor.GOLD+"Deteniendo partida Tipo de Parada: "+ChatColor.RED+motivo.toString());
									player.sendMessage(ChatColor.GOLD+"Mas detalles en el Log de la Consola.");
									String comments = "";
						        	 for(int i = 3 ;i < args.length; i++) {
						        		 comments = comments+args[i]+" "; 
									 }
						        	 
									gc.StopGames(player, name,motivo,comments);
								}catch(IllegalArgumentException e) {
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" Ese motivo no existe usa: Win , Lose , Error o Force. ");
								}
						 }else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg stop <mapa> <motivo Win , Lose , Error o Force>");
							
						 }
						
				
				return true;
				
			}else if (args[0].equalsIgnoreCase("getInv")) {
						if(!player.isOp()) {
							
							player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
							return true;
						}
						if (args.length == 2) {
							String name = args[1];
						
							getInventoryY(name, player);
			
						 }else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg getInv <kit> ");
							Bukkit.getConsoleSender()
							.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg getInv <kit> ");
						 }
						
				
				
				return true;
				
			}else if (args[0].equalsIgnoreCase("saveInv")) {
						if(!player.isOp()) {
							
							player.sendMessage(ChatColor.RED+"No tienes permiso para usar este comando.");
							return true;
						}
						if (args.length == 2) {
							String name = args[1];
				
						    saveInventoryY(name, player);
						
							
						 }else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg saveInv <kit> ");
							Bukkit.getConsoleSender()
							.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg saveInv <kit> ");
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
	
	
 
 	
 	public ObjetiveStatusType convertStringToObjetiveStatusType(Player player,String text) {
 		ObjetiveStatusType type = null;
 		
 		String comments = "";
 		Map<String,ObjetiveStatusType> objetivetype = new HashMap<>();
 		objetivetype.put("COMPLETE", ObjetiveStatusType.COMPLETE);
 		objetivetype.put("CANCELLED", ObjetiveStatusType.CANCELLED);
 		objetivetype.put("CONCLUDED", ObjetiveStatusType.CONCLUDED);
 		objetivetype.put("DANGER", ObjetiveStatusType.DANGER);
 		objetivetype.put("HIDE", ObjetiveStatusType.HIDE);
 		objetivetype.put("INCOMPLETE", ObjetiveStatusType.INCOMPLETE);
 		objetivetype.put("RESET", ObjetiveStatusType.RESET);
 		objetivetype.put("UNKNOW", ObjetiveStatusType.UNKNOW);
 		objetivetype.put("WAITING", ObjetiveStatusType.WAITING);
 		objetivetype.put("WARNING", ObjetiveStatusType.WARNING);
 	
 		if(!objetivetype.containsKey(text.toUpperCase())) {
 			 
 			comments = ChatColor.RED+"Estatus de Objetivos: ";
 			
 			List<Map.Entry<String,ObjetiveStatusType>> list = new ArrayList<>(objetivetype.entrySet());
 			for (Map.Entry<String,ObjetiveStatusType> e : list) {
 					comments = comments+ChatColor.GREEN+e.getKey()+ChatColor.RED+",";
				}
 			comments = comments+ChatColor.RED+" Revisa bien la escritura. Fue colocado por default WAITING.";
 			if(player != null) {
 				player.sendMessage(comments);
 			}else{
 				Bukkit.getConsoleSender().sendMessage(comments);
 			}
 			type = objetivetype.get("WAITING");
 			
 		}else {
 			type = objetivetype.get(text.toUpperCase());
 		}
 		
 		return type;
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
 		typepotion.put("NAUSEA", PotionEffectType.NAUSEA);
 		typepotion.put("RESISTANCE", PotionEffectType.RESISTANCE);
 		typepotion.put("DARKNESS", PotionEffectType.DARKNESS);
 		typepotion.put("DOLPHINS_GRACE", PotionEffectType.DOLPHINS_GRACE);
 		typepotion.put("HASTE", PotionEffectType.HASTE);
 		typepotion.put("FIRE_RESISTANCE", PotionEffectType.FIRE_RESISTANCE);
 		typepotion.put("GLOWING", PotionEffectType.GLOWING);
 		typepotion.put("INSTANT_DAMAGE", PotionEffectType.INSTANT_DAMAGE);
 		typepotion.put("INSTANT_HEALTH", PotionEffectType.INSTANT_HEALTH);
 		typepotion.put("HEALTH_BOOST", PotionEffectType.HEALTH_BOOST);
 		typepotion.put("HERO_OF_THE_VILLAGE", PotionEffectType.HERO_OF_THE_VILLAGE);
 		typepotion.put("HUNGER", PotionEffectType.HUNGER);
 		typepotion.put("INCREASE_DAMAGE", PotionEffectType.STRENGTH);
 		typepotion.put("INVISIBILITY", PotionEffectType.INVISIBILITY);
 		typepotion.put("JUMP_BOOST", PotionEffectType.JUMP_BOOST);
 		typepotion.put("LEVITATION", PotionEffectType.LEVITATION);
 		typepotion.put("LUCK", PotionEffectType.LUCK);
 		typepotion.put("NIGHT_VISION", PotionEffectType.NIGHT_VISION);
 		typepotion.put("POISON", PotionEffectType.POISON);
 		typepotion.put("REGENERATION", PotionEffectType.REGENERATION);
 		typepotion.put("SATURATION", PotionEffectType.SATURATION);
 		typepotion.put("SLOWNESS", PotionEffectType.SLOWNESS);
 		typepotion.put("MINING_FATIGUE", PotionEffectType.MINING_FATIGUE);
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
 	
 	
	 public void pagsOfMaps(Player player,List<String> l , int pag,int datosperpags,String title) {
	    	
	    	if(!l.isEmpty()) {
	    		int inicio = (pag -1) * datosperpags;
	    		int fin = inicio + datosperpags;
	    		
	    		int sizelista = l.size();
	    		int numerodepags = (int) Math.ceil((double) sizelista /datosperpags);
	    		
	    		if(pag > numerodepags) {
	    			if(player != null) {
		    			player.sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+ChatColor.GREEN+" Paginas en Total: "+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));

	    			}
	    			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+ChatColor.GREEN+" Paginas en Total: "+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    			return;
	    		}
	    		if(player != null) {
	    			player.sendMessage(title);
		    		player.sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    		}
	    		
	    		Bukkit.getConsoleSender().sendMessage(title);
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    	
	    		for(int i = inicio;i < fin && i < l.size();i++) {
	    			if(player != null) {
	    				player.sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," "));
	    			}
	    			
	    			
	    			Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," "));
	    					
	    		}
	    		
	    	}else {
	    		if(player != null) {
		    		player.sendMessage(ChatColor.RED+"No hay datos para mostrar.");

	    		}
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay datos para mostrar.");
	    	}
	    	return;
	    }
 	
 	 
 	public void DialogueArgs(Player player ,String[] args) {
 		//mg dialogue nameyml id Map


 		GameConditions gc = new GameConditions(plugin);
 		if(args.length == 4) {
 			
			if(gc.existMap(args[1])) {
				gc.LoadDialogues(args[1], args[2], args[3]);
			}else {
				gc.sendMessageToUserAndConsole(player, ChatColor.RED+"El Dialogo "+args[1]+" no Existe.");
			}
 			
 		}else {
 			gc.sendMessageToUserAndConsole(player, ChatColor.RED+"usa /mg dialogue <nombreyml> <Seccion> <Mapa>.");
 		}
 		return;
 	}
 	
 	
 	
 	
 	public void infomg(Player player,int pag) {
	 	List<String> l = new ArrayList<>();
	 		l.add(ChatColor.GREEN+"/mg join <map>"+ChatColor.AQUA+" Podras entrar a un Mapa.");
	 		l.add(ChatColor.GREEN+"/mg spectator <map>"+ChatColor.AQUA+" Entra como Espectador a un Mapa.");
	 		l.add(ChatColor.GREEN+"/mg leave"+ChatColor.AQUA+" Podras salir del Mapa si estas dentro.");
	 		l.add(ChatColor.GREEN+"/mg mapinfo <map>"+ChatColor.AQUA+" Podras ver informacion General del Mapa.");
	 		l.add(ChatColor.GREEN+"/mg check-points <player>"+ChatColor.AQUA+" Podras ver informacion de un Jugador.");
	 		l.add(ChatColor.GREEN+"/mg prestige"+ChatColor.AQUA+" Podras subir de Prestigio.");
			l.add(ChatColor.GREEN+"/mg querylevel <1>"+ChatColor.AQUA+" Consulta la XP del un Nivel.");
	 		l.add(ChatColor.GREEN+"/mg prestiges"+ChatColor.AQUA+" Lista de Prestigios.");
	 		l.add(ChatColor.GREEN+"/mg maprecord <map>"+ChatColor.AQUA+" Comprueba el Top 10 de Records del Mapa.");
	 		l.add(ChatColor.GREEN+"/mg invite <map>"+ChatColor.AQUA+" Manda una Invitacion a todos los Jugadores.");
	 		l.add(ChatColor.GREEN+"/mg mytags <map>"+ChatColor.AQUA+" Muestra las Tags que tienes.");
	 		l.add(ChatColor.GREEN+"/mg tagsingame <map>"+ChatColor.AQUA+" Muestra las Tags del Juego.");
	 		l.add(ChatColor.GREEN+"/mg top"+ChatColor.AQUA+" Muestra el Top General de los 10 Mejores Jugadores.");
	 		l.add(ChatColor.GREEN+"/mg reward"+ChatColor.AQUA+" Reclama la Recompensa si estas en el Top.");
	 		l.add(ChatColor.GREEN+"/mg misions"+ChatColor.AQUA+" Muestra el Menu de Misiones Publicas.");
	 		l.add(ChatColor.GREEN+"/mg time"+ChatColor.AQUA+" Muestra el Tiempo del Server (Util para ciertas Misiones).");
	 		l.add(ChatColor.GREEN+"/mg version"+ChatColor.AQUA+" Muestra la Version del Plugin.");
	 		l.add(ChatColor.GREEN+"/mg objetives"+ChatColor.AQUA+" Muestra los Objetivos de un Mapa.");
 		if(player != null && player.isOp() || player == null) {
 			l.add(ChatColor.GOLD+"/mg reload"+ChatColor.AQUA+" Recarga las Configuraciones.");
 			l.add(ChatColor.GOLD+"/mg reload <map>"+ChatColor.AQUA+" Recarga las Configuraciones de un Mapa especifico.");
 			l.add(ChatColor.GOLD+"/mg mapsingame"+ChatColor.AQUA+" Muestra los Mapas que estan en Juego.");
 			l.add(ChatColor.GOLD+"/mg gamedetails <map>"+ChatColor.AQUA+" Muestra los Detalles del Mapa.");
 	 		l.add(ChatColor.GOLD+"/mg stop"+ChatColor.AQUA+" Deten una partida (revisa las indicaciones del Comando).");
 	 		l.add(ChatColor.GOLD+"/mg maintenance"+ChatColor.AQUA+" Activa/Desactiva el Modo de Mantenimiento.");
 	 		l.add(ChatColor.GOLD+"/mg enabled <map>"+ChatColor.AQUA+" Habilita el Mapa en caso de estar Deshabilitado.");
 	 		l.add(ChatColor.GOLD+"/mg disabled <map>"+ChatColor.AQUA+" Deshabilita el Mapa en caso de estar Habilitado.");
 	 		l.add(ChatColor.GOLD+"/mg timegame <map>"+ChatColor.AQUA+" Establece el Tiempo de Duracion de un Mapa.");
 	 		l.add(ChatColor.GOLD+"/mg xp <user>"+ChatColor.AQUA+" Establece la Experiencia de un Jugador.");
 	 		l.add(ChatColor.GOLD+"/mg show-maps"+ChatColor.AQUA+" Muestra los Mapas Creados.");
 	 		l.add(ChatColor.GOLD+"/mg reportlogs <user>"+ChatColor.AQUA+" Muestra los Reportes de un Jugador.");
 	 		l.add(ChatColor.GOLD+"/mg warn,kick,tempan,ban,pardon"+ChatColor.AQUA+" Advierte , Kickea , da Tembans , Banea o Perdona a un Jugador.");
 	 		l.add(ChatColor.GOLD+"/mg isban"+ChatColor.AQUA+" Comprueba si un Jugador esta Baneado de los Juegos.");
 	 		l.add(ChatColor.GOLD+"/mg create <map>"+ChatColor.AQUA+" Crea un Mapa nuevo.");
 	 		l.add(ChatColor.GOLD+"/mg setlobby"+ChatColor.AQUA+" Establece el Lobby de Salida de los Mapas.");
	 		l.add(ChatColor.GOLD+"/mg setprelobby"+ChatColor.AQUA+" Establece el PreLobby de un Mapa.");
	 		l.add(ChatColor.GOLD+"/mg setspawn"+ChatColor.AQUA+" Establece el Spawn de un Mapa.");
	 		l.add(ChatColor.GOLD+"/mg setspawn-spectator"+ChatColor.AQUA+" Establece el Spawn de Espectadores de un Mapa.");
	 		l.add(ChatColor.GOLD+"/mg setspawn-end"+ChatColor.AQUA+" Establece el Spawn Final de un Mapa.(Para Modo Resistencia)");
 	 		l.add(ChatColor.GOLD+"/mg delete <map>"+ChatColor.AQUA+" Borra un Mapa.");
 	 		l.add(ChatColor.GOLD+"/mg objetive"+ChatColor.AQUA+" Cambia o Modifica un Objetivo.");
 	 		l.add(ChatColor.GOLD+"/mg generator"+ChatColor.AQUA+" Agrega un Generador de Ores al Mapa. (Usa Combinacion de Bloques)");
 	 		l.add(ChatColor.GOLD+"/mg delgenerator"+ChatColor.AQUA+" Borra un Generador de Ores del Mapa. (Usa Combinacion de Bloques)");
 	 		l.add(ChatColor.GOLD+"/mg mobgenerator"+ChatColor.AQUA+" Agrega un Generador de Mobs del Mapa. (Usa Combinacion de Bloques)");
 	 		l.add(ChatColor.GOLD+"/mg delmobgenerator"+ChatColor.AQUA+" Borra un Generador de Mobs del Mapa. (Usa Combinacion de Bloques)");
 	 		l.add(ChatColor.GOLD+"/mg god"+ChatColor.AQUA+" Activa el Modo Invulnerable.");
 	 		l.add(ChatColor.GOLD+"/mg ungod"+ChatColor.AQUA+" Desactiva el Modo Invulnerable.");
 	 		l.add(ChatColor.GOLD+"/mg addtag"+ChatColor.AQUA+" Añade una Tag a un Jugador.");
 	 		l.add(ChatColor.GOLD+"/mg removetag"+ChatColor.AQUA+" Remueve una Tag a un Jugador.");
 	 		l.add(ChatColor.GOLD+"/mg showtags"+ChatColor.AQUA+" Muestra las Tags de un Jugador.");
 			l.add(ChatColor.GOLD+"/mg force-revive"+ChatColor.AQUA+" Revive a la Fuerza a un Jugador.");
 			l.add(ChatColor.GOLD+"/mg set-life"+ChatColor.AQUA+" Establece los Corazones de un Jugador.");
 			l.add(ChatColor.GOLD+"/mg set-scale"+ChatColor.AQUA+" Establece los la Escala/Tamaño de un Jugador.");
 			l.add(ChatColor.GOLD+"/mg getInv"+ChatColor.AQUA+" Obten un Inventario Guardado.");
 			l.add(ChatColor.GOLD+"/mg saveInv"+ChatColor.AQUA+" Guarda un Inventario.");
 			l.add(ChatColor.GOLD+"/mg pause"+ChatColor.AQUA+" Pausa el tiempo de un Mapa.");
 			l.add(ChatColor.GOLD+"/mg formats"+ChatColor.AQUA+" Obten el Formato de tiempo para usarlo en los Mapas.");
 			l.add(ChatColor.GOLD+"/mg dropplayer <user>"+ChatColor.AQUA+" Dropeale el Inventario a un Jugador.");
 			l.add(ChatColor.GOLD+"/mg deletecheckpoint <user>"+ChatColor.AQUA+" Borra los Todos Checkpoints de un Jugador cuando esta en un Mapa.");
 			
 			
 			
 		}
 		
 		pagsOfMaps(player, l, pag, 10,""+ChatColor.RED+ChatColor.BOLD+"Informacion de Comandos.");
 		
 		
 	}
 		// 		1					2	3  	4 = 5
 	
 	
 	
 	
	
//	public String mapNamemg(String[] arg, int positionstart) {
//			String text = "";
// 			int comas = arg.split(",").lenght -1; 	
//	   	 for(int i = positionstart ;i < arg.length; i++) {
//	   		 text = text+arg[i]+" "; 
//				 
//			}
//	   	 
//	   	 return text;
//	}

	
 	
	
	
	
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
	
	 public double randomPosOrNeg(int i){
			Random r = new Random();
			double v = r.nextDouble() * i;
			//genera aleatoriedad si es 0 devuelve el valor como tal si es 1 devuelve un valor positivo o negativo
			int r2 = r.nextInt(1+1);
			if(r2 == 1){
				return v;
			}
			return transformPosOrNeg(v);
		}
	 
	 
	 public double transformPosOrNeg(double i){
			return -i;
		}
	 
	
		@SuppressWarnings("removal")
		public void spawnArrows2(Player player) {
			
			double rotatex = randomPosOrNeg(3);
			double rotatey = randomPosOrNeg(3);
			
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
			Location loc = player.getLocation();
			
			Vector v = loc.getDirection();
			v.rotateAroundX(Math.toRadians(rotatex));
			v.rotateAroundY(Math.toRadians(rotatey));
			Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
			aw.setVelocity(v.multiply(5));
			aw.setCritical(true);
			aw.setKnockbackStrength(2);
			//aw.setFireTicks(1200);
			aw.setShooter(player);
			//((Arrow) h1).setShooter(player);
		}
		
		@SuppressWarnings("removal")
		public void spawnArrows(Player player) {
			
			
			Random r = new Random();
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
			Location loc = player.getLocation();
			for(int i = 0 ; i < 100 ;i++) {
				Vector v = new Vector(
						r.nextDouble() * 2 - 1, 
						r.nextDouble() * 2 - 1,
						r.nextDouble() * 2 - 1
						).normalize().multiply(2);
				Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);// originamente loc.getEyelocation ,v , 2f 0f
				aw.setVelocity(v.multiply(5));
				aw.setCritical(true);
				aw.setKnockbackStrength(2);
				//aw.setFireTicks(1200);
				aw.setShooter(player);
			}
		
			
			
			
		
			
			//((Arrow) h1).setShooter(player);
		}
		
	
	public void AllReload(Player player) {
		
			plugin.getRecordTime().reload();
		    plugin.getMenuItems().reload();
			plugin.getConfig().reload();
			plugin.getMessage().reload();
			plugin.getPoints().reload();
			plugin.getCommandsMg().reload();
			plugin.getInventorysYaml().reload();
			plugin.getCooldown().reload();	
			plugin.getReportsYaml().reload();
			GameConditions gc = new GameConditions(plugin);
			gc.loadItemMenu();
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







