package me.nao.manager;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.GameType;
import me.nao.general.info.ObjetiveType;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Main;

import me.nao.timers.Countdown;




public class ClassArena {

		
	    private Main plugin;
	   
		
		public ClassArena(Main plugin) {
			this.plugin = plugin;
		}
		
		//PENDIENTE MAS ACCIONES
	public void CreateDialog(String name,String path ,Player player) {
		GameConditions gc = new GameConditions(plugin);
 		if(!gc.ExistMapDialog(name)) {
 			plugin.NewYmlDialog(name, player);
 			FileConfiguration dialog = plugin.getCacheSpecificDialogsYML(name);
 			dialog.set(path+".Time", 1);
 			dialog.set(path+".isRepetitive",false);
 			List<String> perm = dialog.getStringList(path+".Message-Actions-Server");
 			dialog.set(path+".Message-Actions-Server", perm);
			perm.add("Hola %player% como estas.");
			perm.add("give %player% stone <action>");
			perm.add("say que mas ve <server>");
		
			
			
			plugin.getCacheSpecificDialogsYML(name).save();
			plugin.getCacheSpecificDialogsYML(name).reload();
 		}
	}	
		
	
		//TODO DEFAULT INFO PLAYER
	public void CreateNewGame(String name, Player player) {
		GameConditions gc = new GameConditions(plugin);
 		if(!gc.ExistMap(name)) {
			plugin.NewYml(name, player);
			FileConfiguration ym = plugin.getCacheSpecificYML(name);
			ym.set("Type-Mission","Adventure");
			ym.set("Max-Player", 5);
			ym.set("Min-Player", 2);
			ym.set("Timer-H-M-S", "1,5,10");
			ym.set("Has-Objetives",false); 
			ym.set("Primary-Objetive-Opcional",false); 
			ym.set("Secondary-Objetive-Opcional",false); 
			ym.set("Set-Hearts",20); 
			ym.set("Anti-Void",false); 
			ym.set("Allow-Inventory",false);
			ym.set("Allow-PVP",false);
			ym.set("Has-Kit",false);
			ym.set("Start-Kit","example");
			ym.set("Has-Time",false);
			ym.set("Time","Lunes Viernes");
			ym.set("Requires-Permission",false);
			ym.set("Permission-To-Play","my.permission.use");
			List<String> perm = ym.getStringList("How-Get-Permission.Message");
			ym.set("How-Get-Permission.Message", perm);
			perm.add("Hola %player% necesitas un permiso para entrar a este Mapa.");
			
			ym.set("Start.Tittle-of-Mision", "Mision %player%");
			ym.set("Start.Tittle-Time", "20-40-20");
			ym.set("Start.SubTittle-of-Mision", "hola %player%");
			List<String> startc = ym.getStringList("Start.Chat-Message");
			ym.set("Start.Chat-Message", startc);
			startc.add("Holax2 %player%");
			ym.set("Start.Sound-of-Mision", "entity_ender_dragon_ambient;20.0;1");
			List<String> start = ym.getStringList("Start.Actions");
			ym.set("Start.Actions", start);
			start.add("execute at %player% run summon minecraft:item ~ ~1 ~ {Item:{id:diamond,Count:1}}");// summon minecraft:item ~ ~1 ~ {Item:{id:diamond,Count:1}}
			
			ym.set("Win.Tittle-of-Win", "Felicidades %player%");
			ym.set("Win.Tittle-Time", "20-40-20");
			ym.set("Win.SubTittle-of-Win", "ganaste %player%");
			ym.set("Win.Reward-Position-Top", false);
			List<String> win = ym.getStringList("Win.Chat-Message-Win");
			ym.set("Win.Chat-Message-Win", win);
			startc.add("Ganaste %player%");
			ym.set("Win.Sound-of-Win", "ui_toast_challenge_complete;20.0;1");
			List<String> winr = ym.getStringList("Win-Rewards.Commands");
			ym.set("Win-Rewards.Commands", winr);
			winr.add("summon minecraft:item ~ ~1 ~ {Item:{id:diamond,Count:1}}");
			
			ym.set("Lost.Tittle-of-Lost", "Fail %player%");
			ym.set("Lost.Tittle-Time", "20-40-20");
			ym.set("Lost.SubTittle-of-Lost", "F %player%");
			List<String> lost = ym.getStringList("Lost.Chat-Message-Lost");
			ym.set("Lost.Chat-Message-Lost", lost);
			startc.add("Perdiste %player%");
			ym.set("Lost.Sound-of-Lost", "entity_witch_celebrate;20.0;1");
			List<String> lostr = ym.getStringList("Lost-Rewards.Commands");
			ym.set("Lost-Rewards.Commands", lostr);
			lostr.add("summon minecraft:item ~ ~1 ~ {Item:{id:apple,Count:1}}");
			
			List<String> startco = ym.getStringList("Start-Console.Commands");
			ym.set("Start-Console.Commands",startco);
			startco.add("minecraft:weather thunder");
			
			List<String> end = ym.getStringList("End-Console.Commands");
			ym.set("End-Console.Commands",end);
			end.add("minecraft:weather clear");
			
			List<String> time = ym.getStringList("Time-Actions.0_1_0.List");
			ym.set("Time-Actions.Time-1-0-1.List",time);
			time.add("say hola");
			
			
		
			SetObjetiveInfoDefault(ym,"Objetivo");
			SetInfoItemOfMision(name);
			
			plugin.getCacheSpecificYML(name).save();
			plugin.getCacheSpecificYML(name).reload();
			
		
			
			
		}else {
			
			if(player != null) {
				player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" ya existe");
			}
			
			 Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"El Mapa  "+ChatColor.GREEN+name+ChatColor.YELLOW+" ya existe");
		}
		
	
       
	}
	
   
   
   public void SetInfoItemOfMision(String name) {
	   
	   FileConfiguration itemm = plugin.getMenuItems();
	   
	   itemm.set(name+".Is-Working",true);
	   itemm.set(name+".Material","bedrock");
	   itemm.set(name+".Display-Name",name);
	   List<String> lore = itemm.getStringList(name+".Lore-Item");
	   itemm.set(name+".Lore-Item",lore);
	   lore.add("Descripcion: Be carefull");
	   
	   plugin.getMenuItems().save();
	   plugin.getMenuItems().reload();
	   
   }
   
   public void SetObjetiveInfoDefault(FileConfiguration ym, String objetive) {
	   
	   
	   for(int i = 0 ;i<5;i++) {
		   	ym.set("Game-Objetives."+objetive+i+".Priority",i);
		 	ym.set("Game-Objetives."+objetive+i+".Status",ObjetiveType.WAITING.toString());
		 	ym.set("Game-Objetives."+objetive+i+".Start-Value",0);
			ym.set("Game-Objetives."+objetive+i+".Complete-Value",10);
			ym.set("Game-Objetives."+objetive+i+".Incomplete-Value",0);
			ym.set("Game-Objetives."+objetive+i+".Description","Busca una palanca");
			
			List<String> objetivesmg = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveCompleteMessage");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveCompleteMessage",objetivesmg);
			objetivesmg.add("Vaya completaste el Objetivo Felicidades");
			
			List<String> objetivesplayeraction = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveCompletePlayerActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveCompletePlayerActions",objetivesplayeraction);
			objetivesplayeraction.add("say felicidades :)");
			
			List<String> objetivesaction = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveCompleteActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveCompleteActions",objetivesaction);
			objetivesaction.add("say felicidades :) console2");
			
		//=====================================================================================	
			List<String> objetives2mg = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveIncompleteMessage");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveIncompleteMessage",objetives2mg);
			objetives2mg.add("Vaya parece que Fracasaste");
			
			List<String> objetivesplayeraction2 = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveIncompletePlayerActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveIncompletePlayerActions",objetivesplayeraction2);
			objetivesplayeraction2.add("say infelicidades :)");
			
			List<String> objetivesaction2 = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveIncompleteActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveIncompleteActions",objetivesaction2);
			objetivesaction2.add("say F console2");
	   }
		
	  	List<String> rewardpm = ym.getStringList("Complete-All-Objetives-Primary.Message");
		ym.set("Complete-All-Objetives-Primary.Message",rewardpm);
		rewardpm.add("Han completado todos los objetivos primarios Felicidades 1");
		
	   	List<String> rewardp = ym.getStringList("Complete-All-Objetives-Primary.Actions");
		ym.set("Complete-All-Objetives-Primary.Actions",rewardp);
		rewardp.add("say felicidades 1");
		
		List<String> rewardpl = ym.getStringList("Complete-All-Objetives-Primary.PlayerActions");
		ym.set("Complete-All-Objetives-Primary.PlayerActions",rewardpl);
		rewardpl.add("say felicidades 11");
		
		
		List<String> rewardsm = ym.getStringList("Complete-All-Objetives-Secondary.Message");
		ym.set("Complete-All-Objetives-Secondary.Message",rewardsm);
		rewardsm.add("Han completado todos los objetivos secundarios Felicidades 2");
		
		List<String> rewards = ym.getStringList("Complete-All-Objetives-Secondary.Actions");
		ym.set("Complete-All-Objetives-Secondary.Actions",rewards);
		rewards.add("say felicidades 2");
		
		List<String> rewardspl = ym.getStringList("Complete-All-Objetives-Secondary.PlayerActions");
		ym.set("Complete-All-Objetives-Secondary.PlayerActions",rewardspl);
		rewardspl.add("say felicidades 22");
	   
   }
   
   
   //TODO STOP
   public void StopGames(Player player , String name,StopMotivo motivo) {
	   
	   GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
			GameInfo ms = plugin.getGameInfoPoo().get(name);
			if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				EstadoPartida estadoPartida = ms.getEstopartida();
				if(estadoPartida == EstadoPartida.JUGANDO) {
					ClassIntoGame ci = new ClassIntoGame(plugin);
					
					ms.setMotivo(motivo);
					List<String> vivos = ga.getVivo();
					List<Player> players = gc.ConvertStringToPlayer(vivos);
					if(motivo == StopMotivo.WIN) {
						
						if(vivos.isEmpty()) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+ms.getGameType().toString()+ChatColor.GREEN+" Ganaron en la Arena: "+ChatColor.GOLD+name+ChatColor.GREEN+" por Condiciones varias , Terminando. Pero no hay ningun jugador vivo.");

							return;
						}
						
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+ms.getGameType().toString()+ChatColor.GREEN+"AVENTURA: Ganaron en la Arena: "+ChatColor.GOLD+name+ChatColor.GREEN+" por Condiciones varias , Terminando.");
						
						
						if(ms.getGameType() == GameType.ADVENTURE) {
							for(Player target : players) {
								target.sendMessage(ChatColor.GREEN+"Victoria todos los que quedaron Vivos han Ganado...");
								ci.GamePlayerWin(target);
							}
							return;
						}else if(ms.getGameType() == GameType.RESISTENCE) {
							
							ClassArena c = new ClassArena(plugin);
							for(Player target : players) {
								target.sendMessage(ChatColor.GREEN+"Victoria todos los que quedaron Vivos han Ganado...");
								c.EndTptoSpawn(target, name);
							}
							return;
						
						}
					
					}else if(motivo == StopMotivo.LOSE) {
						
						if(vivos.isEmpty()) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ms.getGameType().toString()+ChatColor.GREEN+"Perdieron en la Arena: "+ChatColor.GOLD+name+ChatColor.GREEN+" por Condiciones varias , Terminando. Pero no hay ningun jugador vivo.");

							return;
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ms.getGameType().toString()+ChatColor.RED+"Perdieron en la Arena: "+ChatColor.GREEN+name+ChatColor.RED+" por Condiciones varias , Terminando.");
					
						
						if(ms.getGameType() == GameType.ADVENTURE) {
							for(Player target : players) {
								target.sendMessage(ChatColor.RED+"Todos los Jugadores con Vida han Perdido...");
								ci.GamePlayerLost(target);
							}
							return;
						}else if(ms.getGameType() == GameType.RESISTENCE) {
							for(Player target : players) {
								target.sendMessage(ChatColor.RED+"Todos los Jugadores con Vida han Perdido...");
								ci.GamePlayerLost(target);
							}
							return;
						}
						
						
						
						
					}else if(motivo == StopMotivo.ERROR) {
						
						
						if(vivos.isEmpty()) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ms.getGameType().toString()+ChatColor.GREEN+"Hubo un error en el Mapa: "+ChatColor.GOLD+name+ChatColor.GREEN+" por Condiciones varias , Terminando. Pero no hay ningun jugador vivo.");

							return;
						}
						
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ms.getGameType().toString()+ChatColor.RED+"Hubo un error en el Mapa: "+ChatColor.GREEN+name+ChatColor.RED+" Terminando.");
						
						if(ms.getGameType() == GameType.ADVENTURE) {
							for(Player target : players) {
								target.sendMessage(ChatColor.RED+"Hubo un error en el Mapa: "+ChatColor.GREEN+name+ChatColor.RED+" Terminando.");
								ci.GamePlayerLost(target);
							}
							return;
						}else if(ms.getGameType() == GameType.RESISTENCE) {
							for(Player target : players) {
								target.sendMessage(ChatColor.RED+"Hubo un error en el Mapa: "+ChatColor.GREEN+name+ChatColor.RED+" Terminando.");
								ci.GamePlayerLost(target);
							}
							return;
						}
						
					
						
						
						
						
					}else if(motivo == StopMotivo.FORCE) {
						
						
						if(vivos.isEmpty()) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ms.getGameType().toString()+ChatColor.GREEN+"Perdieron en el Mapa: "+ChatColor.GOLD+name+ChatColor.GREEN+" por Condiciones varias , Terminando. Pero no hay ningun jugador vivo.");

							return;
						}
						
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ms.getGameType().toString()+ChatColor.RED+"El Mapa "+ChatColor.GREEN+name+ChatColor.RED+" fue Forzada a terminar.");
						
						if(ms.getGameType() == GameType.ADVENTURE) {
							for(Player target : players) {
								target.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GREEN+name+ChatColor.RED+" fue Forzada a terminar.");
								ci.GamePlayerLost(target);
							}
							return;
						}else if(ms.getGameType() == GameType.RESISTENCE) {
							for(Player target : players) {
								target.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GREEN+name+ChatColor.RED+" fue Forzada a terminar.");
								ci.GamePlayerLost(target);
							}
							return;
						}
						
						
						
					}
				
	    			
	    		}else {
	    			//no puedes detener una partida que no esta jugando
	    			if(player != null) {
						player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GREEN+name+ChatColor.RED+" no esta en estado Jugando.");
					}
					
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GREEN+name+ChatColor.RED+" no esta en estado Jugando.");
	    		}
				
			}else {
				player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
			}
			
			}
			
			
   }
   
   public void SetObjetiveInfoDefault(Player player , String map, String objetive) {
	   GameConditions gc = new GameConditions(plugin);
	   if(gc.ExistMap(map)) {
			
			
			plugin.ChargedYml(map, player);
			FileConfiguration ym = plugin.getCacheSpecificYML(map);	
			ym.set("Game-Objetives."+objetive+".Priority",1);
			ym.set("Game-Objetives."+objetive+".Description","Busca una palanca");
			List<String> objetivesmg = ym.getStringList("Game-Objetives."+objetive+".ObjetiveCompleteMessage");
			ym.set("Game-Objetives."+objetive+".ObjetiveCompleteMessage",objetivesmg);
			objetivesmg.add("Vaya completaste el Objetivo Felicidades");
			List<String> objetivesaction = ym.getStringList("Game-Objetives."+objetive+".ObjetiveCompleteActions");
			ym.set("Game-Objetives."+objetive+".ObjetiveCompleteActions",objetivesaction);
			objetivesaction.add("say felicidades :)");
			
			List<String> objetives2mg = ym.getStringList("Game-Objetives."+objetive+".ObjetiveIncompleteMessage");
			ym.set("Game-Objetives."+objetive+".ObjetiveCompleteMessage",objetives2mg);
			objetives2mg.add("Vaya parece que Fracasaste");
			List<String> objetivesaction2 = ym.getStringList("Game-Objetives."+objetive+".ObjetiveIncompleteMessage");
			ym.set("Game-Objetives."+objetive+".ObjetiveCompleteActions",objetivesaction2);
			objetivesaction2.add("say F ");
			
			
			plugin.getCacheSpecificYML(map).save();
			plugin.getCacheSpecificYML(map).reload();
			player.sendMessage(ChatColor.GREEN+"Objetivo nuevo seteado en el Mapa "+map);
	   }
		
  }
   
   
   //TODO setArenaLobby
   public void setServerLobby(Player player) {
		FileConfiguration config = plugin.getConfig();

		Location l = player.getLocation();

		config.set("Lobby-Active",true);
		config.set("Lobby-Spawn." + l.getWorld().getName() + ".X", l.getX());
		config.set("Lobby-Spawn." + l.getWorld().getName() + ".Y", l.getY());
		config.set("Lobby-Spawn." + l.getWorld().getName() + ".Z", l.getZ());
		config.set("Lobby-Spawn." + l.getWorld().getName() + ".Yaw", l.getYaw());
		config.set("Lobby-Spawn." + l.getWorld().getName() + ".Pitch", l.getPitch());
		
		plugin.saveConfig();
		player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha seteado el Lobby correctamente ");
   }
   
   //TODO setArenaPrelobby
   public void setMapPreLobby(String name, Player player) {
	   GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
			
	
			plugin.ChargedYml(name, player);
			FileConfiguration ym = plugin.getCacheSpecificYML(name);	
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
			
			
		
			
				ym.set("Pre-Lobby",player.getLocation().getWorld().getName()+"/"+nf.format(player.getLocation().getX())+"/"+nf.format(player.getLocation().getY())+"/"+nf.format(player.getLocation().getZ())+"/"+nf.format(player.getLocation().getYaw())+"/"+nf.format(player.getLocation().getPitch()));
				plugin.getCacheSpecificYML(name).save();
				plugin.getCacheSpecificYML(name).reload();
			
		
					
				//u.saveSpecificPlayer(name);
				//u.reloadSpecificYml(name);
				player.sendMessage(ChatColor.GREEN+"Se a seteado el Pre-Lobby correctamente en el Mapa: "+ChatColor.GOLD+name);	
			
		
		}else {
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		}
		
	
      
	}
   
   
   //TODO setSpwanArena
   public void setMapSpawn(String name, Player player) {
	   GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
			plugin.ChargedYml(name, player);
			FileConfiguration ym = plugin.getCacheSpecificYML(name);	
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
				ym.set("Spawn",player.getLocation().getWorld().getName()+"/"+nf.format(player.getLocation().getX())+"/"+nf.format(player.getLocation().getY())+"/"+nf.format(player.getLocation().getZ())+"/"+nf.format(player.getLocation().getYaw())+"/"+nf.format(player.getLocation().getPitch()));
				
				
			
				player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn correctamente en el Mapa: "+ChatColor.GOLD+name);
				plugin.getCacheSpecificYML(name).save();
				plugin.getCacheSpecificYML(name).reload();
				
				
		}else {
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		}
		
	
     
	}
   
  
   
   
   
   
   //TODO setSpawnSpectator
   public void setMapSpawnSpectator(String name, Player player) {
	   GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
			plugin.ChargedYml(name, player);
			FileConfiguration ym = plugin.getCacheSpecificYML(name);	
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
				ym.set("Spawn-Spectator",player.getLocation().getWorld().getName()+"/"+nf.format(player.getLocation().getX())+"/"+nf.format(player.getLocation().getY())+"/"+nf.format(player.getLocation().getZ())+"/"+nf.format(player.getLocation().getYaw())+"/"+nf.format(player.getLocation().getPitch()));


				plugin.getCacheSpecificYML(name).save();
				plugin.getCacheSpecificYML(name).reload();
			
			
				player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn-Spectator correctamente en el Mapa: "+ChatColor.GOLD+name);	
			
		
		}else {
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		}
		
	
      
	}
   
   //TODO END SPAWN
   public void setMapSpawnEnd(String name, Player player) {
		GameConditions gc = new GameConditions(plugin);
 		if(gc.ExistMap(name)) {
 			plugin.ChargedYml(name, player);
 			FileConfiguration ym = plugin.getCacheSpecificYML(name);	
 			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
 				ym.set("Spawn-End",player.getLocation().getWorld().getName()+"/"+nf.format(player.getLocation().getX())+"/"+nf.format(player.getLocation().getY())+"/"+nf.format(player.getLocation().getZ())+"/"+nf.format(player.getLocation().getYaw())+"/"+nf.format(player.getLocation().getPitch()));


 				plugin.getCacheSpecificYML(name).save();
 				plugin.getCacheSpecificYML(name).reload();
 			
 			
 				player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn-End correctamente en el Mapa: "+ChatColor.GOLD+name);	
 			
 		
 		}else {
 			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
 		}
 		
 	
       
 	}
   
   
	  public void setMapSpawnRed(String name, Player player) {
		  GameConditions gc = new GameConditions(plugin);
		  if(gc.ExistMap(name)) {
					plugin.ChargedYml(name, player);
					FileConfiguration ym = plugin.getCacheSpecificYML(name);	
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(0);
		 				ym.set("Spawn-Red",player.getLocation().getWorld().getName()+"/"+nf.format(player.getLocation().getX())+"/"+nf.format(player.getLocation().getY())+"/"+nf.format(player.getLocation().getZ())+"/"+nf.format(player.getLocation().getYaw())+"/"+nf.format(player.getLocation().getPitch()));

					player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn-Red correctamente en el Mapa: "+ChatColor.GOLD+name);
					plugin.getCacheSpecificYML(name).save();
					plugin.getCacheSpecificYML(name).reload();
					//plugin.getTempYml().clear();
					
			
		}else{
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
				
		}
				
			
			
		
	     
		}
	
	  public void setMapSpawnBlue(String name, Player player) {
		
		  
		  GameConditions gc = new GameConditions(plugin);
		  if(gc.ExistMap(name)) {

					plugin.ChargedYml(name, player);
					FileConfiguration ym = plugin.getCacheSpecificYML(name);	
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(0);
		 				ym.set("Spawn-Blue",player.getLocation().getWorld().getName()+"/"+nf.format(player.getLocation().getX())+"/"+nf.format(player.getLocation().getY())+"/"+nf.format(player.getLocation().getZ())+"/"+nf.format(player.getLocation().getYaw())+"/"+nf.format(player.getLocation().getPitch()));

					player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn-Blue correctamente en el Mapa: "+ChatColor.GOLD+name);
					plugin.getCacheSpecificYML(name).save();
					plugin.getCacheSpecificYML(name).reload();
					//plugin.getTempYml().clear();
		  }else {	
			
				player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		  }
			
		
	     
		}
   
   
	  public void setMapBlueNexo(String name, Player player) {
		  GameConditions gc = new GameConditions(plugin);
		  if(gc.ExistMap(name)) {
			plugin.ChargedYml(name, player);
			FileConfiguration ym = plugin.getCacheSpecificYML(name);	
			
			 Block b = player.getTargetBlock((Set<Material>) null, 3);
             if(!b.getType().isSolid()) {
                    player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque] para setearlo como Nexo.");
                     return;
               }
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
				ym.set("Nexo-Blue",b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()));

			player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn-Blue correctamente en el Mapa: "+ChatColor.GOLD+name);
			plugin.getCacheSpecificYML(name).save();
			plugin.getCacheSpecificYML(name).reload();
			//plugin.getTempYml().clear();
		  }else {
			  player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe"); 
		  }
	
		
	
	


	  }
	  
	  
	  public void setMapRedNexo(String name, Player player) {
		  GameConditions gc = new GameConditions(plugin);
		  if(gc.ExistMap(name)) {
			plugin.ChargedYml(name, player);
			FileConfiguration ym = plugin.getCacheSpecificYML(name);	
			
			 Block b = player.getTargetBlock((Set<Material>) null, 3);
             if(!b.getType().isSolid()) {
                    player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque] para setearlo como Nexo.");
                     return;
               }
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
				ym.set("Nexo-Blue",b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()));

			player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn-Red correctamente en el Mapa: "+ChatColor.GOLD+name);
			plugin.getCacheSpecificYML(name).save();
			plugin.getCacheSpecificYML(name).reload();
			//plugin.getTempYml().clear();
		  }else {
			  player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe"); 
		  }
	
		
	
	


	  }
	  
   //TODO TpEndSpawn
   public void EndTptoSpawn(Player player ,String arenaName){
	   
	   GameConditions gc = new GameConditions(plugin);
	   FileConfiguration ym = gc.getGameConfig(arenaName);
	   if(ym.contains("Spawn-End")) {
		    String[] coords = ym.getString("Spawn-End").split("/");
		    String world = coords[0];
		    Double x = Double.valueOf(coords[1]);
		    Double y = Double.valueOf(coords[2]);
		    Double z = Double.valueOf(coords[3]);
		    Float yaw = Float.valueOf(coords[4]);
		    Float pitch = Float.valueOf(coords[5]);
		   
		    player.setInvulnerable(false);
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
			Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			Random r = new Random();
			
			List<String> l1 = new ArrayList<>();
			l1.add(ChatColor.GREEN+"Por poco y no la cuentas menos mal saliste en una Pieza.");
			l1.add(ChatColor.GREEN+"Sobreviviste felicidades por tu Victoria");
			l1.add(ChatColor.GREEN+"Sobreviviste un dia mas para Luchar Felicidades");
			l1.add(ChatColor.GREEN+"Puede que seas de los pocos con Vida sigue asi.");
			
			player.sendMessage(l1.get(r.nextInt(l1.size())));
			player.teleport(l);
		
				return;
			  
		} 
   }
   
   
   //TODO TpDeathSpawn
   public void DeathTptoSpawn(Player player ,String arenaName){
	   GameConditions gc = new GameConditions(plugin);
	   FileConfiguration ym = gc.getGameConfig(arenaName);
	   if(ym.contains("Spawn-Spectator")) {
		    String[] coords = ym.getString("Spawn-Spectator").split("/");
		    String world = coords[0];
		    Double x = Double.valueOf(coords[1]);
		    Double y = Double.valueOf(coords[2]);
		    Double z = Double.valueOf(coords[3]);
		    Float yaw = Float.valueOf(coords[4]);
		    Float pitch = Float.valueOf(coords[5]);
		   
		    player.setInvulnerable(false);
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
			Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			player.teleport(l);
		
				return;
			  
		} 
   }
   
    
   public void TptoSpawnArenaSimple(Player player){
	   GameConditions gc = new GameConditions(plugin);
	   PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
	   FileConfiguration ym = gc.getGameConfig(pi.getMapName());
	   if(ym.contains("Spawn")) {
		   

		   String[] coords = ym.getString("Spawn").split("/");
		    String world = coords[0];
		    Double x = Double.valueOf(coords[1]);
		    Double y = Double.valueOf(coords[2]);
		    Double z = Double.valueOf(coords[3]);
		    Float yaw = Float.valueOf(coords[4]);
		    Float pitch = Float.valueOf(coords[5]);
			Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			player.teleport(l);
			return;
	   }
   }
   
   //TODO TP AL SPAWN DE LA ARENA
   public void TptoSpawnArena(Player player ,String map){
	   GameConditions gc = new GameConditions(plugin);
	   FileConfiguration ym = gc.getGameConfig(map);
	   if(ym.contains("Spawn")) {
		   
		   
		   GameInfo gm = plugin.getGameInfoPoo().get(map);
		   plugin.getFirstLogMg().put(map, gm.ShowGame());
		   
		   List<String> startc = ym.getStringList("Start.Chat-Message");
		   List<String> start = ym.getStringList("Start.Actions");
		   
		   String[] t = ym.getString("Start.Tittle-Time").split("-");
		   int a = Integer.valueOf(t[0]);
		   int b = Integer.valueOf(t[1]);
		   int c = Integer.valueOf(t[2]);
			//ym.set("Start.Actions", start);
		    String[] coords = ym.getString("Spawn").split("/");
		    String world = coords[0];
		    Double x = Double.valueOf(coords[1]);
		    Double y = Double.valueOf(coords[2]);
		    Double z = Double.valueOf(coords[3]);
		    Float yaw = Float.valueOf(coords[4]);
		    Float pitch = Float.valueOf(coords[5]);
		    
		    
		      
		        String[] sts = ym.getString("Start.Sound-of-Mision").split(";");
		        try {
		    	    Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
				    Float volumen = Float.valueOf(sts[1]);
				    Float grade = Float.valueOf(sts[2]);
					player.playSound(player.getLocation(), soundtype, volumen,grade);
		       }catch(IllegalArgumentException e) {
		    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Start Sound of Mision en la arena: "+ChatColor.GOLD+map);
		       }
		
		    player.setInvulnerable(false);
			Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			player.teleport(l);
			gc.SetHeartsInGame(player, map);
			//este send message es un separador de chat contra la cuenta atras xd
			player.sendMessage(" ");
			
			if(!startc.isEmpty()) {
				for(int i = 0 ; i< startc.size();i++) { 
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',DifficultyMission(startc.get(i)).replaceAll("%player%", player.getName())));
				}
			}
			
			
			player.sendTitle(ChatColor.translateAlternateColorCodes('&',ym.getString("Start.Tittle-of-Mision").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',DifficultyMission(ym.getString("Start.SubTittle-of-Mision")).replaceAll("%player%", player.getName())), a,b,c);
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			if(!start.isEmpty()) {
				for(int i = 0 ; i < start.size(); i++) {
					String texto = start.get(i);

					Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName())));
				}
			}
			
		     	GameConditions cm = new GameConditions(plugin);
				cm.setKitMg(player);
			//getInventoryY(player);
			
				return;
			  
		} 
   }
  

   //TP SPAWN SPECTATOR
   public void TptoSpawnSpectator(Player player ,String arenaName){
	   GameConditions gc = new GameConditions(plugin);
	   FileConfiguration ym = gc.getGameConfig(arenaName);
	   if(ym.contains("Spawn-Spectator")) {
		    String[] coords = ym.getString("Spawn-Spectator").split("/");
		    String world = coords[0];
		    Double x = Double.valueOf(coords[1]);
		    Double y = Double.valueOf(coords[2]);
		    Double z = Double.valueOf(coords[3]);
		    Float yaw = Float.valueOf(coords[4]);
		    Float pitch = Float.valueOf(coords[5]);
		   
		    player.setInvulnerable(false);
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
			Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			player.teleport(l);
			//player.sendMessage(ChatColor.translateAlternateColorCodes('&',ym.getString("Chat-Message")));
			//player.sendTitle(ChatColor.translateAlternateColorCodes('&',ym.getString("Tittle-of-Mision")), ChatColor.translateAlternateColorCodes('&',ym.getString("SubTittle-of-Mision")), 20, 40, 20);
				return;
			  
		} 
   }
   
   
 //TODO TP AL PRELOBBY DE LA ARENA
   public void TptoPreLobbyArena(Player player ,String arenaName){
	   GameConditions gc = new GameConditions(plugin);
	   FileConfiguration ym = gc.getGameConfig(arenaName);
	   if(ym.contains("Pre-Lobby")) {
		    String[] coords = ym.getString("Pre-Lobby").split("/");
		    String world = coords[0];
		    Double x = Double.valueOf(coords[1]);
		    Double y = Double.valueOf(coords[2]);
		    Double z = Double.valueOf(coords[3]);
		    Float yaw = Float.valueOf(coords[4]);
		    Float pitch = Float.valueOf(coords[5]);

		    
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
			Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			player.setInvulnerable(true);
			player.teleport(l);
				return;
			  
		} 
   }
   
   // VER SI EXISTE LA ARENA
   // PRE
   // SPWN
   //VER SI EL JUGADOR ESTA EN ARE
   
  
	// metodo que se ejecuta  al iniciar una partida
   //TODO Spectator Player
//   public void SpectatorPlayerArena(Player player, String arenaName) {
//	   
//	   
//	   if(!plugin.PlayerisArena(player)) {
//		   
//		     BossBar boss = plugin.getBossBarTime().get(arenaName);
//			 boss.addPlayer(player);
// 			
//			 player.sendMessage(ChatColor.YELLOW+"La partida esta en progreso entraras en modo espectador");
//			 plugin.addPlayerArena(player, arenaName);
//			 plugin.Join(player);
//			
//			 TptoSpawnSpectator(player, arenaName);
//			 List<String> spect = plugin.getSpectators().get(arenaName);
//			 spect.add(player.getName());
//			 player.setGameMode(GameMode.SPECTATOR);
//			 
//			MgTeams t = new MgTeams(plugin);
//			 t.JoinTeamSpectator(player);
//			 for(Player players : Bukkit.getOnlinePlayers()) {
//				 if(players.getName().equals(player.getName())) continue;
//				 String ar = plugin.getArenaPlayerInfo().get(players);
//					
//				 if(ar != null && ar.equals(arenaName)) {
//				     	 players.sendMessage(ChatColor.WHITE+"El jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" se unio en modo espectador."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+spect.size()+ChatColor.RED+"]");
//				     }
//			 }
//
//			 
//			    player.sendMessage(ChatColor.GREEN+"Te has unido a la arena: "+ChatColor.RED+arenaName+ChatColor.GREEN+" en modo espectador");
//				player.sendMessage(ChatColor.RED+"\nUsa la hotbar para ver a otros jugadores. "+ChatColor.YELLOW+"\n!!!Solo podras ver a los que estan en tu partida");
//		}else {
//			 player.sendMessage(ChatColor.RED+"Ya estas en una arena");
//			 Bukkit.getConsoleSender().sendMessage("Ya estas en una arena");
//		 }
//	   
//   }
   
   
//  
//   //TODO TOP
//   	public void Top(String arenaName) {
//   		FileConfiguration message = plugin.getMessage();
//   		
//		if (message.getBoolean("Message.message-top")) {
//			List<String> messagep = message.getStringList("Message.message-top-decoracion1");
//			for (int j = 0; j < messagep.size(); j++) {
//				String texto = messagep.get(j);
//				 List<Map.Entry<Player, String>> lista = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
//					for (Map.Entry<Player,String> e1 : lista) {
//						if(e1.getValue().equals(arenaName)) {
//							e1.getKey().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
//						}
//						
//					}
//				
//			}
//		}
//
//		// PRIMERA PARTE
//		HashMap<String, Integer> scores = new HashMap<>();
//
//						
//		
//				 List<Map.Entry<Player, Integer>> lista2 = new ArrayList<>(plugin.getEspecificPlayerPoints().entrySet());
//					for (Map.Entry<Player,Integer> e2 : lista2) {
//						String p = plugin.getArenaPlayerInfo().get(e2.getKey());
//								if(p.equals(arenaName)) {
//									Player key2 = e2.getKey();
//									int puntaje = e2.getValue();
//									scores.put(key2.getName(), puntaje);
//								}
//								
//	
//					}
//				
//				
//  
//			
//			// SE GUARDAN LOS DATOS EN EL HASH MAP
//			
//
//		
//
//		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
//		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
//
//		
//		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
//				return e2.getValue() - e1.getValue();
//			}
//		});
//
//		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
//		 List<Map.Entry<Player, String>> lista3 = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
//			for (Map.Entry<Player,String> e1 : lista3) {
//				
//				int i = 0;
//				for (Map.Entry<String, Integer> e : list) {
//
//				
//					if (i <= message.getInt("Top-Amount")) {
//						// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());
//
//						if (message.getBoolean("Message.message-top")) {
//							List<String> messagep = message.getStringList("Message.message-top-texto");
//							for (int j = 0; j < messagep.size(); j++) {
//								String texto = messagep.get(j);
//								
//								//if(e1.getValue().equals(arenaName) && e.getKey().equals(e1.getKey().getName())) {
//								if(e1.getValue().equals(arenaName)) {
//											i++;
//											int puntaje = e.getValue();
//											double resultado = puntaje / 5;
//											long pr = 0;
//									  		pr = Math.round(resultado);
//									  		String time = plugin.getPlayerCronomet().get(e.getKey());
//											e1.getKey().sendMessage(ChatColor.translateAlternateColorCodes('&',texto.replaceAll("%player%", e.getKey()).replace("%pointuser%", e.getValue().toString()).replaceAll("%place%", Integer.toString(i)).replaceAll("%reward%", Long.toString(pr)).replaceAll("%cronomet%", time) ));
//											
//								}
//		
//							}
//
//						}
//
//					} else {
//						break;
//					}
//
//				}
//			}
//
//
//		if (message.getBoolean("Message.message-top")) {
//			List<String> messagep = message.getStringList("Message.message-top-decoracion2");
//			for (int j = 0; j < messagep.size(); j++) {
//				String texto = messagep.get(j);
//				List<Map.Entry<Player, String>> lista1 = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
//				for (Map.Entry<Player,String> e1 : lista1) {
//					if(e1.getValue().equals(arenaName)) {
//						e1.getKey().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
//					}
//				}
//			}
//		}
//   		
//   	}	
//   	  
//   	
//   	
//  //TODO TOP
//   	public void Top2(String arenaName) {
//   		FileConfiguration message = plugin.getMessage();
//   		
//		if (message.getBoolean("Message.message-top")) {
//			List<String> messagep = message.getStringList("Message.message-top-decoracion1");
//			for (int j = 0; j < messagep.size(); j++) {
//				String texto = messagep.get(j);
//				 List<Map.Entry<Player, String>> lista = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
//					for (Map.Entry<Player,String> e1 : lista) {
//						if(e1.getValue().equals(arenaName)) {
//							e1.getKey().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
//						}
//						
//					}
//				
//			}
//		}
//
//		// PRIMERA PARTE
//		HashMap<String, Integer> scores = new HashMap<>();
//
//						
//		
//				 List<Map.Entry<Player, Integer>> lista2 = new ArrayList<>(plugin.getEspecificPlayerPoints().entrySet());
//					for (Map.Entry<Player,Integer> e2 : lista2) {
//						String p = plugin.getArenaPlayerInfo().get(e2.getKey());
//								if(p.equals(arenaName)) {
//									Player key2 = e2.getKey();
//									int puntaje = e2.getValue();
//									scores.put(key2.getName(), puntaje);
//								}
//								
//	
//					}
//				
//				
//  
//			
//			// SE GUARDAN LOS DATOS EN EL HASH MAP
//			
//
//		
//
//		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
//		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
//
//		
//		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
//				return e2.getValue() - e1.getValue();
//			}
//		});
//
//		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
//		 List<Map.Entry<Player, String>> lista3 = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
//			for (Map.Entry<Player,String> e1 : lista3) {
//				
//				int i = 0;
//				for (Map.Entry<String, Integer> e : list) {
//
//				
//					if (i <= message.getInt("Top-Amount")) {
//						// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());
//
//						if (message.getBoolean("Message.message-top")) {
//							List<String> messagep = message.getStringList("Message.message-top-texto");
//							for (int j = 0; j < messagep.size(); j++) {
//								String texto = messagep.get(j);
//								
//								//if(e1.getValue().equals(arenaName) && e.getKey().equals(e1.getKey().getName())) {
//								if(e1.getValue().equals(arenaName)) {
//											i++;
//											int puntaje = e.getValue();
//											double resultado = puntaje / 5;
//											long pr = 0;
//									  		pr = Math.round(resultado);
//											e1.getKey().sendMessage(ChatColor.translateAlternateColorCodes('&',texto.replaceAll("%player%", e.getKey()).replace("%pointuser%", e.getValue().toString()).replaceAll("%place%", Integer.toString(i)).replaceAll("%reward%", Long.toString(pr)) ));
//											
//								}
//		
//							}
//
//						}
//
//					} else {
//						break;
//					}
//
//				}
//			}
//
//
//		if (message.getBoolean("Message.message-top")) {
//			List<String> messagep = message.getStringList("Message.message-top-decoracion2");
//			for (int j = 0; j < messagep.size(); j++) {
//				String texto = messagep.get(j);
//				List<Map.Entry<Player, String>> lista1 = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
//				for (Map.Entry<Player,String> e1 : lista1) {
//					if(e1.getValue().equals(arenaName)) {
//						e1.getKey().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
//					}
//				}
//			}
//		}
//   		
//   	}	
//   	
//   	
//   	
//  
   	
   	
   //TODO [JOIN PLAYER]
//	 public void JoinPlayerArena(Player player, String arenaName) {
//		 //el primer list evalua si la arena existe 
//		 FileConfiguration config = plugin.getConfig();
//		 FileConfiguration ym = getConfigGame(arenaName);
//		 List<String> ac = config.getStringList("Arenas-Created.List");
//		 
//			
//		 
//		 if(ac.contains(arenaName)) {
//			 
//			
//			 //TODO MODO 1a
//			 if(ym.getInt("Game-Mode") == 1) {
//				 
//					 if(!ym.contains("Pre-Lobby")) {
//						 if(player.isOp()) {
//							 player.sendMessage(ChatColor.RED+"La arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no tiene seteado el PreLobby");
//						 }
//						 else {
//							 player.sendMessage(ChatColor.RED+"Error en la arena: "+arenaName);
//						 }
//						 return;
//					 }
//					 if(!ym.contains("Spawn")) {
//						 if(player.isOp()) {
//							 player.sendMessage(ChatColor.RED+"La arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no tiene seteado el Spawn");
//						 }
//						 else {
//							 player.sendMessage(ChatColor.RED+"Error en la arena: "+arenaName);
//						 }
//						 return;
//					 }
//					 if(!ym.contains("Spawn-Spectator")) {
//						 if(player.isOp()) {
//							 player.sendMessage(ChatColor.RED+"La arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
//						 }
//						 else {
//							 player.sendMessage(ChatColor.RED+"Error en la arena: "+arenaName);
//						 }
//						 return;
//					 }
//					 
//					 //DESPUES DE CHECK DE SPAWNS
//					 
//					 if(plugin.getArenaJoinPlayer().get(arenaName) != null && plugin.getEstatusArena().get(arenaName) == EstadoPartida.TERMINANDO) {
//							 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
//							 return;
//					 }
//					 
//					 if(plugin.getArenaJoinPlayer().get(arenaName) != null && plugin.getArenaJoinPlayer().get(arenaName).size() == ym.getInt("Max-Player") && plugin.getEstatusArena().get(arenaName) == EstadoPartida.COMENZANDO) {
//							player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como espectador."); 
//							return;
//					 }
//					 
//					 if(plugin.getEstatusArena().get(arenaName) == EstadoPartida.ESPERANDO || plugin.getEstatusArena().get(arenaName) == EstadoPartida.COMENZANDO) {
//							 if(plugin.PlayerisArena(player)) {
//								 player.sendMessage(ChatColor.RED+"Ya estas en una arena.");
//								 Bukkit.getConsoleSender().sendMessage("Ya estas en una arena");
//								 return; 
//							   }
//					 }else{
//									SpectatorPlayerArena(player, arenaName);
//									return;
//					 }
//					 
//					 //TODO PERMISSION TO USE
//					 			if(ym.getBoolean("Requires-Permission")) {
//					 				String perm = ym.getString("Permission-To-Play");
//					 				if(!player.hasPermission(perm)) {
//					 					player.sendMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+"BLOQUEADO");
//					 					player.sendMessage(ChatColor.RED+"Esta Mision requiere un permiso para Jugar en este Mapa.");
//					 					player.sendMessage(ChatColor.RED+"Termina la Mision Anterior para Desbloquear esta Mision.");
//					 					return;
//					 				}
//					 			}
//							 
//							     ///SI LA MISION TIENE TIEMPO PARA ENTRAR
//								 if(ym.getBoolean("Has-Time")) {
//									 String time = ym.getString("Time");
//							    	
//							        if(!PassedTime(player,time)) return;
//								  }
//								 
//								 //CREACION DE BOSSBAR EN CACHE SINO EXISTE
//								 if(!plugin.getBossBarTime().containsKey(arenaName)) {
//					 			 		BossBar boss = Bukkit.createBossBar(""+ChatColor.GREEN+ChatColor.BOLD+"Bienvenido",BarColor.GREEN, BarStyle.SOLID,  null ,null);
//					 			 		boss.setVisible(false);
//					 			 		plugin.getBossBarTime().put(arenaName, boss);
//					 			   }
//								 
//								 plugin.getStopMisionMotivo().put(arenaName, StopMotivo.NINGUNO);
//								 
//								 //GUARDA INVENTARIO TE AGREGA A LAS DISTINTAS LISTAS PARA JUGAR
//								 MisionMode(player, arenaName);
//								 //TP AL PRE-LOBBY
//								 TptoPreLobbyArena(player, arenaName);
//								
//								 //TE AÑADE PARA QUE VEAS LA BOSSBAR
//								 BossBar boss = plugin.getBossBarTime().get(arenaName);
//								 boss.addPlayer(player);
//								 
//								 
//								 //MENSAJE DE ENTRADA A MISION
//								 player.sendMessage(ChatColor.GREEN+"Has Entrado en la Mision "+ChatColor.translateAlternateColorCodes('&',ym.getString("Start.Tittle-of-Mision").replace("%player%", player.getName())));
//								 player.setInvulnerable(true);
//								 
//								 MgTeams t1 = new MgTeams(plugin);
//								 t1.JoinTeamLife(player);
//								 MgScore ms = new MgScore();
//								 ms.createLife(player);
//								 
//								 if(player.isOp()) {
//									 //MENSAJE A OP
//									 player.sendMessage(ChatColor.GREEN+"Te has unido a la arena: "+arenaName);
//								 }
//								 
//								 
//								 //LISTA DE JUGADORES QUE PARTICIPARAN
//								 List<String> join1 = plugin.getArenaJoinPlayer().get(arenaName);
//								 //MENSAJE DE ENTRADA
//								 player.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Te has unido"+ChatColor.RED+" ("+ChatColor.GOLD+join1.size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+ ym.getInt("Max-Player")+ChatColor.RED+")");
//
//								 //MENSAJE QUE INDICA JUGADORES NECESARIOS PARA EMPEZAR (LO VE EL JUGADOR QUE ENTRA)
//										 if(join1.size() < ym.getInt("Min-Player")) {
//											 
//												 int min1 = ym.getInt("Min-Player");
//												 min1 = min1 - join1.size();
//												 //TEXTO 
//												 if(min1 == 1) {
//													 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
//												 }else {
//													 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");
//												 } 
//										 }
//									 
//										 //MENSAJE PARA JUGADORES EN PARTIDA SOLO LO VEN LOS QUE ESTEN DENTRO
//										 for(Player players : Bukkit.getOnlinePlayers()) {
//											    if(players.getName().equals(player.getName())) continue;
//											
//												 if(plugin.PlayerisArena(players)) {
//													 String ar = plugin.getArenaPlayerInfo().get(players);
//														
//													 if(ar != null && ar.equals(arenaName)) {
//														 players.sendMessage(ChatColor.YELLOW+"Se a unido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+join1.size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+ym.getInt("Max-Player")+ChatColor.RED+")");
//														 
//															 if(join1.size() < ym.getInt("Min-Player")) {
//																 
//																 int min1 = ym.getInt("Min-Player");
//																 min1 = min1 - join1.size();
//																 
//																 if(min1 == 1) {
//																	 players.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
//																 }else {
//																	 players.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");
//																 } 
//															 }
//											}}}
//											     
//										 
//										 //=======================
//										 //INICIO DE PARTIDA CON JUGADORES MINIMOS
//										 if(join1.size() == ym.getInt("Min-Player")) {
//											 
//											 plugin.getEstatusArena().replace(arenaName,EstadoPartida.COMENZANDO);
//											 
//											 if(plugin.getEstatusArena().get(arenaName).equals(EstadoPartida.COMENZANDO)) {
//												 
//													 for(Player players : Bukkit.getOnlinePlayers()) {
//												
//															 if(plugin.PlayerisArena(players)) {
//																 String ar = plugin.getArenaPlayerInfo().get(players);
//																
//																 if(ar != null && ar.equals(arenaName)) {
//																	 int  segundo = config.getInt("CountDownPreLobby");
//																	 players.sendMessage(ChatColor.GREEN+"Se a alcanzado el minimo de Jugadores necesarios, la partida Comenzara en "+ChatColor.RED+segundo+ChatColor.GREEN+" segundos.");
//
//																	 
//													  }}}
//													
//													 TempInGame2 t = new TempInGame2(plugin);
//											     	 t.Inicio(arenaName);
//													
//											 }
//										 }
//										 Bukkit.getConsoleSender().sendMessage(player.getName()+" se unio a "+ arenaName);
//										 
//						 
//						 
//					 
//				 
//				 
//				 return;
//			 }
//			else if(ym.getInt("Game-Mode") == 2) {
//				 
//				 if(!ym.contains("Pre-Lobby")) {
//					 if(player.isOp()) {
//						 player.sendMessage(ChatColor.RED+"La arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no tiene seteado el PreLobby");
//					 }
//					 else {
//						 player.sendMessage(ChatColor.RED+"Error en la arena: "+arenaName);
//					 }
//					 return;
//				 }
//				 if(!ym.contains("Spawn")) {
//					 if(player.isOp()) {
//						 player.sendMessage(ChatColor.RED+"La arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no tiene seteado el Spawn");
//					 }
//					 else {
//						 player.sendMessage(ChatColor.RED+"Error en la arena: "+arenaName);
//					 }
//					 return;
//				 }
//				 if(!ym.contains("Spawn-Spectator")) {
//					 if(player.isOp()) {
//						 player.sendMessage(ChatColor.RED+"La arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
//					 }
//					 else {
//						 player.sendMessage(ChatColor.RED+"Error en la arena: "+arenaName);
//					 }
//					 return;
//				 }
//				 if(!ym.contains("Spawn-End")) {
//					 if(player.isOp()) {
//						 player.sendMessage(ChatColor.RED+"La arena "+ChatColor.GOLD+arenaName+ChatColor.RED+" no tiene seteado el Spawn-End");
//					 }
//					 else {
//						 player.sendMessage(ChatColor.RED+"Error en la arena: "+arenaName);
//					 }
//					 return;
//				 }
//				 
//				 if(plugin.getArenaJoinPlayer().get(arenaName) != null && plugin.getEstatusArena().get(arenaName) == EstadoPartida.TERMINANDO) {
//					 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
//					 return;
//				 }
//			 
//				 if(plugin.getArenaJoinPlayer().get(arenaName) != null && plugin.getArenaJoinPlayer().get(arenaName).size() == ym.getInt("Max-Player") && plugin.getEstatusArena().get(arenaName) == EstadoPartida.COMENZANDO) {
//					player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como espectador."); 
//					return;
//				 }
//			 
//				 if(plugin.getEstatusArena().get(arenaName) == EstadoPartida.ESPERANDO || plugin.getEstatusArena().get(arenaName) == EstadoPartida.COMENZANDO) {
//					 if(plugin.PlayerisArena(player)) {
//						 player.sendMessage(ChatColor.RED+"Ya estas en una arena.");
//						 Bukkit.getConsoleSender().sendMessage("Ya estas en una arena");
//						 return; 
//				}}else {
//						 SpectatorPlayerArena(player, arenaName);
//							return;
//				}
//				 
//					 if(ym.getBoolean("Has-Time")) {
//						 String time = ym.getString("Time");
//				    	
//				        if(!PassedTime(player,time)) return;
//					  }
//					 
//					 //CREACION DE BOSSBAR EN CACHE SINO EXISTE
//					 if(!plugin.getBossBarTime().containsKey(arenaName)) {
//		 			 		BossBar boss = Bukkit.createBossBar(""+ChatColor.GREEN+ChatColor.BOLD+"Bienvenido",BarColor.GREEN, BarStyle.SOLID,  null ,null);
//		 			 		boss.setVisible(false);
//		 			 		plugin.getBossBarTime().put(arenaName, boss);
//		 			   }
//					 
//					//GUARDA INVENTARIO TE AGREGA A LAS DISTINTAS LISTAS PARA JUGAR
//					 MisionMode(player, arenaName);
//					 //TP AL PRE-LOBBY
//					 TptoPreLobbyArena(player, arenaName);
//					
//					 //TE AÑADE PARA QUE VEAS LA BOSSBAR
//					 BossBar boss = plugin.getBossBarTime().get(arenaName);
//					 boss.addPlayer(player);
//					 
//					 //MENSAJE DE ENTRADA A MISION
//					 player.sendMessage(ChatColor.GREEN+"Has Entrado en la Mision "+ChatColor.translateAlternateColorCodes('&',ym.getString("Start.Tittle-of-Mision").replace("%player%", player.getName())));
//					
//					 
//					 MgTeams t1 = new MgTeams(plugin);
//					 t1.JoinTeamLife(player);
//					 MgScore ms = new MgScore();
//					 ms.createLife(player);
//					 
//					 if(player.isOp()) {
//						 //MENSAJE A OP
//						 player.sendMessage(ChatColor.GREEN+"Te has unido a la arena: "+arenaName);
//					 }
//					 
//					 //LISTA DE JUGADORES QUE PARTICIPARAN
//					 List<String> join1 = plugin.getArenaJoinPlayer().get(arenaName);
//					 //MENSAJE DE ENTRADA
//					 player.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Te has unido"+ChatColor.RED+" ("+ChatColor.GOLD+join1.size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+ ym.getInt("Max-Player")+ChatColor.RED+")");
//
//					 //MENSAJE QUE INDICA JUGADORES NECESARIOS PARA EMPEZAR (LO VE EL JUGADOR QUE ENTRA)
//							 if(join1.size() < ym.getInt("Min-Player")) {
//								 
//									 int min1 = ym.getInt("Min-Player");
//									 min1 = min1 - join1.size();
//									 //TEXTO 
//									 if(min1 == 1) {
//										 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
//									 }else {
//										 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");
//									 } 
//							 }
//						 
//							 //MENSAJE PARA JUGADORES EN PARTIDA SOLO LO VEN LOS QUE ESTEN DENTRO
//							 for(Player players : Bukkit.getOnlinePlayers()) {
//								    if(players.getName().equals(player.getName())) continue;
//								
//									 if(plugin.PlayerisArena(players)) {
//										 String ar = plugin.getArenaPlayerInfo().get(players);
//											
//										 if(ar != null && ar.equals(arenaName)) {
//											 players.sendMessage(ChatColor.YELLOW+"Se a unido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+join1.size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+ym.getInt("Max-Player")+ChatColor.RED+")");
//											 
//												 if(join1.size() < ym.getInt("Min-Player")) {
//													 
//													 int min1 = ym.getInt("Min-Player");
//													 min1 = min1 - join1.size();
//													 
//													 if(min1 == 1) {
//														 players.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
//													 }else {
//														 players.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");
//													 } 
//												 }
//								}}}
//							 
//							 
//							 
//							 //INICIO DE PARTIDA CON JUGADORES MINIMOS
//							 if(join1.size() == ym.getInt("Min-Player")) {
//								 
//								 plugin.getEstatusArena().replace(arenaName,EstadoPartida.COMENZANDO);
//								 
//								 if(plugin.getEstatusArena().get(arenaName).equals(EstadoPartida.COMENZANDO)) {
//									 
//										 for(Player players : Bukkit.getOnlinePlayers()) {
//									
//												 if(plugin.PlayerisArena(players)) {
//													 String ar = plugin.getArenaPlayerInfo().get(players);
//													
//													 if(ar != null && ar.equals(arenaName)) {
//														 int  segundo = config.getInt("CountDownPreLobby");
//														 players.sendMessage(ChatColor.GREEN+"Se a alcanzado el minimo de Jugadores necesarios, la partida Comenzara en "+ChatColor.RED+segundo+ChatColor.GREEN+" segundos.");
//
//														 
//										  }}}
//										
//										 TempInGame3 t = new TempInGame3(plugin);
//								     	 t.Inicio(arenaName);
//										
//										
//								 }
//							 }
//							 Bukkit.getConsoleSender().sendMessage(player.getName()+" se unio a "+ arenaName);
//							 
//							 
//				 return;
//			 }else {
//				 if(player.isOp()) {
//					 player.sendMessage(ChatColor.RED+"Error ese Modo de Juego no existe.");
//				 }else {
//					 player.sendMessage(ChatColor.RED+"Error.");
//				 }
//				
//				 return;
//			 }
//			 
//	
//			 
//		  
//		 }else {
//			 player.sendMessage(ChatColor.RED+"Esa arena no existe");
//		 }
//		 //la segunda lista es para agregarlo a su respectiva arena
//		
//		 
//	 }
//	 //===========================
//	 //TODO FORCE LEAVE SPECTATOR
//	 
//	 public void ForceLeave(Player player) {
//		 if(plugin.PlayerisArena(player)) {
//			 FileConfiguration config = plugin.getConfig();
//			 if(config.getBoolean("Lobby-Active")) {
//				 plugin.locationReturnLobbyPlayer(player);
//					
//				}else {
//					plugin.locationReturnPlayer(player);
//					
//				}
//			 String arenaName = plugin.getArenaPlayerInfo().get(player);
//			 plugin.ChargedYml(arenaName, player);
//			 FileConfiguration ym = plugin.getSpecificYamls(arenaName);	
//			 //TODO SI ESTA PERMITIDO RESTAURARA EL MODO DE JUEGO UNICAMENTE
//			 if(ym.getBoolean("Allow-Inventory")) {
//				 plugin.restoreGFPlayerArena(player);
//				
//			 }else{
//				 plugin.restoreInventoryPlayerArena(player);
//				 
//			
//			 }
//			 
//			//	player.getInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK,10));
//			//	player.sendMessage(ChatColor.GREEN+"TEST:recibiste 10 bloques de diamante :)");
//			 if(plugin.getEstatusArena().get(arenaName) == EstadoPartida.TERMINANDO) {
//					plugin.getEstatusArena().replace(arenaName,EstadoPartida.ESPERANDO);
//				}
//		 }
//		
//		 
//		 
//	 }
//	 
//	 public void ForceLeaveSpectator(String arenaName) {
//		 for(Player players : Bukkit.getOnlinePlayers()) {
//			 if(plugin.getArenaPlayerInfo().get(players).equals(arenaName)) {
//				
//					List<String> s = plugin.getSpectators().get(arenaName);
//		
//				
//				if(s.remove(players.getName()));
//				
//					
//					//players.setCustomName(ChatColor.WHITE+players.getName());
//	 
//			 }
//			 
//			 
//		 }
//	 }
//	 
//	 
//	//TODO FIN DE PARTIDA
//	 public void EndGame(Player player) {
//		  
//		 
//			if(plugin.PlayerisArena(player)) {
//					
//		
//							String name = plugin.getArenaPlayerInfo().get(player);
//
//							
//							FileConfiguration ym = getConfigGame(name);
//						
//						
//							 BossBar boss = plugin.getBossBarTime().get(name);
//							 boss.removePlayer(player);
//			
//							List<String> win1 = plugin.getArrive().get(name);
//							List<String> s = plugin.getSpectators().get(name);
//							
//							
//							
//							List<String> winreward = ym.getStringList("Win-Rewards.Commands");
//							List<String> lostreward = ym.getStringList("Lost-Rewards.Commands");
//							List<String> end = ym.getStringList("End.Commands");
//							
//							
//							
//							ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
//							
//							//sino esta permitido entrar con el inventario
//							
//							plugin.Leave(player);
//							if(plugin.getDeadmob().containsKey(player)) {
//								
//								plugin.getDeadmob().remove(player);
//							}
//							if(plugin.getCheckPoint().containsKey(player)) {
//								plugin.getCheckPoint().remove(player);
//							}
//							
//							
//								//player.setCustomName(ChatColor.WHITE+player.getName());
//							
//						
//							 player.setInvulnerable(false);
//							 List<String> win = ym.getStringList("Win.Chat-Message-Win");
//							 List<String> lost = ym.getStringList("Lost.Chat-Message-Lost");
//							 MgScore ms = new MgScore();
//							 ms.clearScore(player);
//							
//							 
//							 if(win1.contains(player.getName())) {
//									
//								          String[] wint = ym.getString("Win.Tittle-Time").split("-");
//								          int aw = Integer.valueOf(wint[0]);
//									       int aw2 = Integer.valueOf(wint[1]);
//									       int aw3 = Integer.valueOf(wint[2]);
//										  String[] sts = ym.getString("Win.Sound-of-Win").split(";");
//									       try {
//									    	   Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
//											   Float volumen = Float.valueOf(sts[1]);
//											   Float grade = Float.valueOf(sts[2]);
//												player.playSound(player.getLocation(), soundtype, volumen,grade);
//									       }catch(IllegalArgumentException e1) {
//									    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Win Sound-of-Mision-Win en la arena: "+ChatColor.GOLD+name);
//									       }
//										if(!win.isEmpty()) {
//											 for(int i = 0;i < win.size();i++ ) {
//												   
//												    player.sendMessage(ChatColor.translateAlternateColorCodes('&',win.get(i).replace("%player%",player.getName())));
//												 
//											      }
//										}
//									    
//									     
//									     player.sendTitle(ChatColor.translateAlternateColorCodes('&',ym.getString("Win.Tittle-of-Win").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',ym.getString("Win.SubTittle-of-Win").replaceAll("%player%", player.getName())), aw, aw2, aw3);
//										
//									//	String arena = plugin.getArenaPlayerInfo().get(player);
//										
//										  HashMap <Player,Integer> h = plugin.getEspecificPlayerPoints();
//										  int puntaje = h.get(player);
//										
//										  double resultado = puntaje / 5;
//										  long pr = 0;
//								  		  pr = Math.round(resultado);
//								  		
//								  	      //if(puntaje < 5) {
//								  		  //player.sendMessage("");
//								  		  // }
//										if(!winreward.isEmpty()) {
//											for(int i = 0 ; i < winreward.size(); i++) {
//												String texto = winreward.get(i);
//								
//												Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(pr))));
//												
//										      }
//										}
//										  
//									
//									
//								}
//							
//							
//							if(!win1.contains(player.getName()) && !s.contains(player.getName())) {
//								
//								       String[] lostt = ym.getString("Lost.Tittle-Time").split("-");
//								       int al = Integer.valueOf(lostt[0]);
//								       int al2 = Integer.valueOf(lostt[1]);
//								       int al3 = Integer.valueOf(lostt[2]);
//								       
//									   String[] sts = ym.getString("Lost.Sound-of-Lost").split(";");
//								       try {
//								    	   Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
//										   Float volumen = Float.valueOf(sts[1]);
//										   Float grade = Float.valueOf(sts[2]);
//											player.playSound(player.getLocation(), soundtype, volumen,grade);
//								       }catch(IllegalArgumentException e1) {
//								    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Lost Sound-of-Mision-Lost en la arena: "+ChatColor.GOLD+name);
//								       }
//								
//								       if(!lost.isEmpty()) {
//								    	   for(int i = 0;i < lost.size();i++ ) {
//									    	   
//												 player.sendMessage(ChatColor.translateAlternateColorCodes('&',lost.get(i).replace("%player%",player.getName())));
//												 
//											}
//								       }
//								      
//								       
//									   player.sendTitle(ChatColor.translateAlternateColorCodes('&',ym.getString("Lost.Tittle-of-Lost").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',ym.getString("Lost.SubTittle-of-Lost").replaceAll("%player%", player.getName())), al,al2,al3);
//									
//									   if(!lostreward.isEmpty()) {
//										   for(int i = 0 ; i < lostreward.size(); i++) {
//												String texto = lostreward.get(i);
//										       //String arena = plugin.getArenaPlayerInfo().get(player);
//												
//												
//												 HashMap <Player,Integer> h = plugin.getEspecificPlayerPoints();
//												 int puntaje = h.get(player);
//												 double resultado = puntaje / 5;
//												  long pr = 0;
//										  		  pr = Math.round(resultado);
//									
//												Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(pr))));
//											  }
//									   }
//									 
//						
//							 }
//								if(!end.isEmpty()) {
//									for(int i = 0 ; i < end.size(); i++) {
//										String texto = end.get(i);
//									
//										Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName())));
//										
//									}
//								}
//								
//							
//							
//							 String mt = ym.getString("Start.Tittle-of-Mision"); 
//							 player.sendMessage(ChatColor.GREEN+"Has salido de la Mision "+ChatColor.translateAlternateColorCodes('&',mt.replaceAll("%player%",player.getName())));
//							
//							
//								plugin.getPlayerCronomet().remove(player.getName());
//								//plugin.getArenaCronometer().remove(name);							
//						    	plugin.removePlayerArenaList(name, player.getName());
//							
//								plugin.resetPlayerPoints(name,player);
//								plugin.removePlayerArena(player);
//								MgTeams t = new MgTeams(plugin);
//								t.LeaveTeamLife(player);
//								t.LeaveTeamDead(player);
//								t.LeaveTeamSpectator(player);
//						
//							
//							if(player.isOp()) {
//								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se termino la partida de "+ name);
//							}	
//						
//					
//					
//				  }
//		 }
//	 
//	 
//	//TODO FIN DE PARTIDA 2
//		 public void EndGame2(Player player) {
//			 
//			 
//				if(plugin.getArenaPlayerInfo().containsKey(player)) {
//						
//			
//								
//								String name = plugin.getArenaPlayerInfo().get(player);
//								FileConfiguration ym = getConfigGame(name);
//								
//								
//								 BossBar boss = plugin.getBossBarTime().get(name);
//								 boss.removePlayer(player);
//				
//								List<String> win1 = plugin.getAlive().get(name);
//								List<String> s = plugin.getSpectators().get(name);
//								
//								
//								
//								List<String> winreward = ym.getStringList("Win-Rewards.Commands");
//								List<String> lostreward = ym.getStringList("Lost-Rewards.Commands");
//								List<String> end = ym.getStringList("End.Commands");
//								
//								
//								
//								ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
//								
//								//sino esta permitido entrar con el inventario
//								
//								plugin.Leave(player);
//								if(plugin.getDeadmob().containsKey(player)) {
//									
//									plugin.getDeadmob().remove(player);
//								}
//								if(plugin.getCheckPoint().containsKey(player)) {
//									plugin.getCheckPoint().remove(player);
//								}
//								
//								
//									//player.setCustomName(ChatColor.WHITE+player.getName());
//								
//							
//								 player.setInvulnerable(false);
//								 List<String> win = ym.getStringList("Win.Chat-Message-Win");
//								 List<String> lost = ym.getStringList("Lost.Chat-Message-Lost");
//								 MgScore ms = new MgScore();
//								 ms.clearScore(player);
//								
//								 
//								 if(win1.contains(player.getName())) {
//										
//									          String[] wint = ym.getString("Win.Tittle-Time").split("-");
//									          int aw = Integer.valueOf(wint[0]);
//										       int aw2 = Integer.valueOf(wint[1]);
//										       int aw3 = Integer.valueOf(wint[2]);
//											  String[] sts = ym.getString("Win.Sound-of-Win").split(";");
//										       try {
//										    	   Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
//												   Float volumen = Float.valueOf(sts[1]);
//												   Float grade = Float.valueOf(sts[2]);
//													player.playSound(player.getLocation(), soundtype, volumen,grade);
//										       }catch(IllegalArgumentException e1) {
//										    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Win Sound-of-Mision-Win en la arena: "+ChatColor.GOLD+name);
//										       }
//											if(!win.isEmpty()) {
//												 for(int i = 0;i < win.size();i++ ) {
//													   
//													    player.sendMessage(ChatColor.translateAlternateColorCodes('&',win.get(i).replace("%player%",player.getName())));
//													 
//												      }
//											}
//										    
//										     
//										     player.sendTitle(ChatColor.translateAlternateColorCodes('&',ym.getString("Win.Tittle-of-Win").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',ym.getString("Win.SubTittle-of-Win").replaceAll("%player%", player.getName())), aw, aw2, aw3);
//											
//										//	String arena = plugin.getArenaPlayerInfo().get(player);
//											
//											  HashMap <Player,Integer> h = plugin.getEspecificPlayerPoints();
//											  int puntaje = h.get(player);
//											
//											  double resultado = puntaje / 5;
//											  long pr = 0;
//									  		  pr = Math.round(resultado);
//									  		
//									  	      //if(puntaje < 5) {
//									  		  //player.sendMessage("");
//									  		  // }
//											if(!winreward.isEmpty()) {
//												for(int i = 0 ; i < winreward.size(); i++) {
//													String texto = winreward.get(i);
//									
//													Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(pr))));
//													
//											      }
//											}
//											  
//										
//										
//									}
//								
//								
//								if(!win1.contains(player.getName()) && !s.contains(player.getName())) {
//									
//									       String[] lostt = ym.getString("Lost.Tittle-Time").split("-");
//									       int al = Integer.valueOf(lostt[0]);
//									       int al2 = Integer.valueOf(lostt[1]);
//									       int al3 = Integer.valueOf(lostt[2]);
//									       
//										   String[] sts = ym.getString("Lost.Sound-of-Lost").split(";");
//									       try {
//									    	   Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
//											   Float volumen = Float.valueOf(sts[1]);
//											   Float grade = Float.valueOf(sts[2]);
//												player.playSound(player.getLocation(), soundtype, volumen,grade);
//									       }catch(IllegalArgumentException e1) {
//									    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Lost Sound-of-Mision-Lost en la arena: "+ChatColor.GOLD+name);
//									       }
//									
//									       if(!lost.isEmpty()) {
//									    	   for(int i = 0;i < lost.size();i++ ) {
//										    	   
//													 player.sendMessage(ChatColor.translateAlternateColorCodes('&',lost.get(i).replace("%player%",player.getName())));
//													 
//												}
//									       }
//									      
//									       
//										   player.sendTitle(ChatColor.translateAlternateColorCodes('&',ym.getString("Lost.Tittle-of-Lost").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',ym.getString("Lost.SubTittle-of-Lost").replaceAll("%player%", player.getName())), al,al2,al3);
//										
//										   if(!lostreward.isEmpty()) {
//											   for(int i = 0 ; i < lostreward.size(); i++) {
//													String texto = lostreward.get(i);
//											       //String arena = plugin.getArenaPlayerInfo().get(player);
//													
//													
//													 HashMap <Player,Integer> h = plugin.getEspecificPlayerPoints();
//													 int puntaje = h.get(player);
//													 double resultado = puntaje / 5;
//													  long pr = 0;
//											  		  pr = Math.round(resultado);
//										
//													Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(pr))));
//												  }
//										   }
//										 
//							
//								 }
//									if(!end.isEmpty()) {
//										for(int i = 0 ; i < end.size(); i++) {
//											String texto = end.get(i);
//										
//											Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName())));
//											
//										}
//									}
//								
//								
//								
//							   	plugin.removePlayerArenaList(name, player.getName());
//								
//								plugin.resetPlayerPoints(name,player);
//								plugin.removePlayerArena(player);
//								MgTeams t = new MgTeams(plugin);
//								t.LeaveTeamLife(player);
//								t.LeaveTeamDead(player);
//								t.LeaveTeamSpectator(player);
//									
//								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se termino la partida de "+ name);
//								
//					  }
//			 }
//	// metodo que se ejecuta  al abandonar una partida
//	 
//	 //TODO [Leave Player] SALIDA
//	 public void LeavePlayerArenas(Player player) {
//		 
//		
//		 
//			if(plugin.PlayerisArena(player)) {
//				
//						String name = plugin.getArenaPlayerInfo().get(player);
//
//						
//					
//						 FileConfiguration ym = getConfigGame(name);	
//						 
//						 Block block = player.getLocation().getBlock();
//						 Block b = block.getRelative(0, -2, 0);
//						 
//						 if(ym.getBoolean("Allow-Inventory")) {
//							 if(b.getType() != Material.EMERALD_BLOCK) {
//								  player.getInventory().clear();
//								  player.sendMessage(ChatColor.RED+"Tu Inventario fue Borrado por Abandonar fuera de una Zona Segura.");
//								  //Bukkit.getConsoleSender().sendMessage("1");
//							 }
//						 }
// 
//						 if(plugin.getCheckPoint().containsKey(player)) {
//								plugin.getCheckPoint().remove(player);
//							}
//						 
//						List<String> join1 = plugin.getArenaJoinPlayer().get(name);
//						List<String> s = plugin.getSpectators().get(name);
//					//	player.setCustomName(ChatColor.WHITE+player.getName());
//						
//						//sino esta permitido entrar con el inventario
//					//	 if(!ym.getBoolean("Allow-Inventory")) {
//						 plugin.Leave(player);
//									
//						 plugin.LeavePlayerPoint(name, player);
//						 MgTeams t = new MgTeams(plugin);
//						 MgScore ms = new MgScore();
//						 ms.clearScore(player);
//						 t.LeaveTeamLife(player);
//						 t.LeaveTeamDead(player);
//						 t.LeaveTeamSpectator(player);
//						 
//						 BossBar boss = plugin.getBossBarTime().get(name);
//						 boss.removePlayer(player);
//						 
//						 player.setInvulnerable(false);
//						
//						 
//						 if(plugin.getDeadmob().containsKey(player)) {
//							 String[] coords = plugin.getDeadmob().get(player).split("/");
//							    String world2 = coords[0];
//							    Double x2 = Double.valueOf(coords[1]);
//							    Double y2 = Double.valueOf(coords[2]);
//							    Double z2 = Double.valueOf(coords[3]);
//							    
//							    Location l1 = new Location(Bukkit.getWorld(world2),x2,y2,z2);
//							    for(Entity e2 : getNearbyEntites(l1,10)) {
//							    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null) {
//							    		String name2 = ChatColor.stripColor(e2.getCustomName());
//							    		
//							    		if(name2.contains(player.getName())) {
//							    			e2.remove();
//							    		}
//									
//									}
//							  
//								}
//							    plugin.getDeadmob().remove(player);
//							  
//						 }
//						
//						 if(join1.contains(player.getName())) {
//							// plugin.removePlayerArenaList(name, player.getName());
//							 
//							 for(Player players : Bukkit.getOnlinePlayers()) {
//								 if(players.getName().equals(player.getName())) continue;
//								 String ar = plugin.getArenaPlayerInfo().get(players);
//							
//								 if(ar != null && ar.equals(name)) {
//									 	
//									 
//								     	 players.sendMessage(ChatColor.YELLOW+"El jugador "+ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" salio."+ChatColor.RED+" ("+ChatColor.GOLD+(join1.size() - 1)+ChatColor.YELLOW+"/"+ChatColor.GOLD+ym.getInt("Max-Player")+ChatColor.RED+")");
//								     	 
//								     	if(plugin.getEstatusArena().get(name) == EstadoPartida.ESPERANDO || plugin.getEstatusArena().get(name) == EstadoPartida.COMENZANDO) {
//								     		 if(join1.size() < ym.getInt("Min-Player")) {
//												 
//												 int min1 = ym.getInt("Min-Player");
//												 
//												 min1 = min1 - join1.size();
//												  
//												 if(min1 == 1) {
//													 players.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
//												 }else {
//													 players.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");
//												 }
//												 
//											 }
//								     	}
//								     	 
//								    }
//							 }
//							 
//						 }
//						 
//						
//						 
//						 if(s.contains(player.getName())) {
//							
//							 
//							 for(Player players : Bukkit.getOnlinePlayers()) {
//								 if(players.getName().equals(player.getName())) continue;
//								 String ar = plugin.getArenaPlayerInfo().get(players);
//									
//								 if(ar != null && ar.equals(name)) {
//									 players.sendMessage(ChatColor.WHITE+"El jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" salio."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+(s.size() - 1)+ChatColor.RED+"]");
//								   }
//							 }
//							 
//						 }
//						 
//						 String mt = ym.getString("Start.Tittle-of-Mision"); 
//						 player.sendMessage(ChatColor.GREEN+"Has salido de la Mision "+ChatColor.translateAlternateColorCodes('&',mt.replaceAll("%player%",player.getName())));
//						 
//						plugin.removePlayerArenaList(name, player.getName()); 
//						plugin.getPlayerCronomet().remove(player.getName());
//						plugin.removePlayerArena(player);
//					
//						
//						//player.sendMessage(ChatColor.GREEN+"Has salido de la Mision");
//						
//						if(player.isOp()) {
//							player.sendMessage(ChatColor.GREEN+"Saliste de la partida: "+name);
//						}
//						
//						Bukkit.getConsoleSender().sendMessage(player.getName()+" a salido de "+name);
//						
//			  }
//		  	}
	 
	 
		public List<Entity> getNearbyEntites(Location l , int size){
			
			List<Entity> entities = new ArrayList<Entity>();
			for(Entity e : l.getWorld().getEntities()) {
				if(l.distance(e.getLocation()) <= size) {
					entities.add(e);
				}
			}
			return entities;
			
			
		}
		
		
		
	//TODO  TIEMPO PARA ABAJO	
		
		public List <DayOfWeek> SpanishToEnglish(String days) {
			String[] d = days.split(" ");
			List <DayOfWeek> l = new ArrayList<DayOfWeek>();
			try {
				for(int i = 0; i < d.length;i++) {
					//System.out.println(d[i]);
					
				//	System.out.println(d[i].toUpperCase().replace("Lunes","MONDAY").replace("Martes","TUESDAY").replace("Miercoles","WEDNESDAY").replace("Jueves","THURSDAY").replace("Viernes","FRIDAY").replace("Sabado","SATURDAY").replace("Domingo","SUNDAY"));
					l.add(DayOfWeek.valueOf(d[i].replace("Lunes","MONDAY").replace("Martes","TUESDAY").replace("Miercoles","WEDNESDAY").replace("Jueves","THURSDAY").replace("Viernes","FRIDAY").replace("Sabado","SATURDAY").replace("Domingo","SUNDAY").toUpperCase()));
				
						
				
				}
			}catch(IllegalArgumentException e) {
				System.out.println("El Formato 1 o 2 no es el correcto");
			}
			
			return l;
		}
		
		
		
		public LocalDateTime AddOrRemove() {
			FileConfiguration config = plugin.getConfig();
			LocalDateTime lt = LocalDateTime.now();
			
			String simbol = config.getString("Plus-Or-Remove");
			 
			int sld = config.getInt("Local-Time-Day");
			int slh = config.getInt("Local-Time-Hour");
			
			if(simbol.equals("+")) {
				lt = lt.plusDays(sld).plusHours(slh);
			}
			
			else if(simbol.equals("-")) {
				lt = lt.minusDays(sld).minusHours(slh);
			}
			
		    
			
			return lt;
		}
		

		//TODO TIME
		public boolean PassedTime(Player player ,String time) {
			
		LocalDateTime lt = AddOrRemove();
		if(time.contains("/")) {
				
			StringTokenizer st = new StringTokenizer(time);
			
			if(st.countTokens() == 5) {
				
				
				String[] cor = time.split("-");
				String a = cor[0];
				String b = cor[1];
				
			  
				 try {
					  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
					  LocalDateTime t = LocalDateTime.parse(a, formatter);
					  LocalDateTime t2 = LocalDateTime.parse(b, formatter);
				
			    
		
			    if(lt.isAfter(t) && lt.isBefore(t2)) {
			    	//player.sendMessage("estas en la fecha correcta");
					return true;
				}else {
					
					if(lt.isBefore(t)) {
						//antes de llegar a la fecha
						player.sendMessage(ChatColor.GREEN+"================================================");
						player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"                     [PROXIMO A ABRIR] ");
						player.sendMessage(ChatColor.AQUA+"Me temo que aun no es el Tiempo para Ingresar ");
						player.sendMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+"                [Tiempo Faltante] ");
						player.sendMessage(ChatColor.GREEN+TimeDiference(lt, t));
						player.sendMessage(ChatColor.AQUA+"Para que pueda estar Disponible.");
						player.sendMessage(ChatColor.GREEN+"================================================");
						isJoinRunning(player);
						return false;
					}
					if(lt.isAfter(t2)) {
						player.sendMessage(ChatColor.RED+"================================================");
						player.sendMessage(""+ChatColor.YELLOW+ChatColor.BOLD+"                     [CERRADO] ");
						player.sendMessage(ChatColor.YELLOW+"Me temo que el Tiempo para Ingresar ya paso.");
						player.sendMessage(""+ChatColor.YELLOW+ChatColor.BOLD+"                [A Trasncurrido] ");
						player.sendMessage(ChatColor.RED+TimeDiference(t2, lt));
						player.sendMessage(ChatColor.YELLOW+"Desde que ha terminado.");
						player.sendMessage(ChatColor.RED+"================================================");
						//despues de pasar la fecha
						isJoinRunning(player);
						return false;
					}
					
				}
			    
			 }catch(DateTimeParseException e) {
				 e.printStackTrace();
			 }
			    
			}else if (st.countTokens() == 3){
				String[] cor = time.split("-");
				String a = cor[0];
				
					try {
					    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
						 
					    LocalDateTime t = LocalDateTime.parse(a, formatter);
					 
				
					    if(lt.isAfter(t)) {
					    	//player.sendMessage("estas en la fecha correcta");
							return true;
						}else {
							
							if(lt.isBefore(t)) {
								//antes de llegar a la fecha
								player.sendMessage(ChatColor.GREEN+"================================================");
								player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"                     [PROXIMO A ABRIR] ");
								player.sendMessage(ChatColor.AQUA+"Me temo que aun no es el Tiempo para Ingresar ");
								player.sendMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+"                [Tiempo Faltante] ");
								player.sendMessage(ChatColor.GREEN+TimeDiference(lt, t));
								player.sendMessage(ChatColor.AQUA+"Para que pueda estar Disponible.");
								player.sendMessage(ChatColor.GREEN+"================================================");
								isJoinRunning(player);
								return false;
							}
							
							
						}
				    
				 }catch(DateTimeParseException e) {
					 e.printStackTrace();
				 }
			}
			
			    
			    
			}
			else if(!time.contains("/")){
				//REVISA SI TIENE NUMEROS EL STRING
				Pattern p = Pattern.compile("([0-9])");
				Matcher m = p.matcher(time);
				
				//si tiene un numero entra al if que es la 2 forma
				if(m.find()){
					
						List <DayOfWeek> lw = new ArrayList<DayOfWeek>();
						List <String> le = new ArrayList<String>();
						StringTokenizer st = new StringTokenizer(time);
						while(st.hasMoreTokens()) {
							
							String cad = st.nextToken();
							Matcher m1 = p.matcher(cad);
							
							if(m1.find()){
								le.add(cad);
							}else{
								lw.add(DayOfWeek.valueOf(cad.replace("Lunes","MONDAY").replace("Martes","TUESDAY").replace("Miercoles","WEDNESDAY").replace("Jueves","THURSDAY").replace("Viernes","FRIDAY").replace("Sabado","SATURDAY").replace("Domingo","SUNDAY").toUpperCase()));
							}
						
						}
					
						String horac = le.get(0);
					
						String[] c2 = horac.split("-");
						String t1 = c2[0];
		 				String t2 = c2[1];
						String[] c3 = t1.split(":");
						String[] c4 = t2.split(":");
					
					    int hora = Integer.valueOf(c3[0]);
					    int minuto = Integer.valueOf(c3[1]);
							
					    int hora2 = Integer.valueOf(c4[0]);
				    	int minuto2 = Integer.valueOf(c4[1]);
					
					if(lw.contains(lt.getDayOfWeek())) {
						
						    LocalDateTime tw4 = LocalDateTime.of(lt.getYear(), lt.getMonth(), lt.getDayOfMonth(), hora, minuto);
							LocalDateTime tw5 = LocalDateTime.of(lt.getYear(), lt.getMonth(), lt.getDayOfMonth(), hora2, minuto2);
							
							if(lt.isAfter(tw4) && lt.isBefore(tw5)) {
								//System.out.println("estas en el dia correctocon fecha");
								return true;
							}else {
								StringTokenizer st2 = new StringTokenizer(time);
								StringBuilder sb = new StringBuilder();
								
								int men = st2.countTokens();
								int i = 1;
								int tm = st2.countTokens()-1; 
								
					
								while(st2.hasMoreTokens()) {
								
									sb.append(ChatColor.GREEN+st2.nextToken()+" ");
									//SI TIENE MAS DIAS AGREGA EL TEXTO AL FINAL TM -1 ES REFERENTE AL TOKEN FINAL  LUNES VIERNERS ENTRE LAS HORAS 12 23
									if(men > 1) {
										
										if(i == tm) {
											sb.append(ChatColor.RED+"entre las horas ");
										}
										i++;
										
									}
									 
									
								}
							
								String dias = sb.toString();
								player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"Cerrado: "+ChatColor.GOLD+"Solo funciona los dias "+dias);
								isJoinRunning(player);
								return false;
							}
							
							
					}else {
						StringTokenizer st2 = new StringTokenizer(time);
						StringBuilder sb = new StringBuilder();
						
						int men = st2.countTokens();
						int i = 1;
						int tm = st2.countTokens()-1; 
						
			
						while(st2.hasMoreTokens()) {
						
							sb.append(ChatColor.GREEN+st2.nextToken()+" ");
							
							if(men > 1) {
								
								if(i == tm) {
									sb.append(ChatColor.RED+"entre las horas ");
								}
								i++;
								
							}
							 
							
						}
					
						String dias = sb.toString();
						player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"Cerrado: "+ChatColor.GOLD+"Solo funciona los dias "+dias);
						isJoinRunning(player);
						return false;
					}
					
					
				}else{
					//si no tiene numero es forma 1
					List <DayOfWeek> l = SpanishToEnglish(time);
					//System.out.println("forma 1");
					//si contiene ejecuta el codigo chido
					if(l.contains(lt.getDayOfWeek())) {
						//System.out.println("estas en el dia correcto1");
						return true;
					}else{
						//sino es muestra el cooldown
						StringTokenizer st = new StringTokenizer(time);
						StringBuilder sb = new StringBuilder();
						
						int men = st.countTokens();
						int i = 1;
						int tm = st.countTokens()-1; 
						
			
						while(st.hasMoreTokens()) {
						
							sb.append(ChatColor.GREEN+st.nextToken()+" ");
							
							if(men > 1) {
								
								if(i == tm) {
									sb.append(ChatColor.GOLD+"y ");
								}
								i++;
								
							}
							 
							
						}
					
						String dias = sb.toString();
						player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"Cerrado: "+ChatColor.GOLD+"Solo funciona los dias "+dias);
						isJoinRunning(player);
						return false;
					}
					
					
				}
		}
			
			

			
				
			
			return false;
		}
		
		
		public String TimeDiference(LocalDateTime fechactual, LocalDateTime fin) {
	        LocalDateTime tempDateTime = LocalDateTime.from(fechactual);

	        //no cuenta el dia finalejemplo   10 incio 15 final dias diferencia de dias son 4
	        long years = tempDateTime.until( fin, ChronoUnit.YEARS );
	        tempDateTime = tempDateTime.plusYears( years );

	        long months = tempDateTime.until( fin, ChronoUnit.MONTHS );
	        tempDateTime = tempDateTime.plusMonths( months );

	        long days = tempDateTime.until( fin, ChronoUnit.DAYS );
	        tempDateTime = tempDateTime.plusDays( days );

	        long hours = tempDateTime.until( fin, ChronoUnit.HOURS );
	        tempDateTime = tempDateTime.plusHours( hours );

	        long minutes = tempDateTime.until( fin, ChronoUnit.MINUTES );
	        tempDateTime = tempDateTime.plusMinutes( minutes );

	        long seconds = tempDateTime.until( fin, ChronoUnit.SECONDS );

	    /*    
	        System.out.println( years + " years " + 
	                months + " months " + 
	                days + " days " +
	                hours + " hours " +
	                minutes + " minutes " +
	                seconds + " seconds.");
	  */
	     
	               return  years+ " Años " +
	               months+" Meses " + 
	                days + " Dias " +
	                hours + " Horas " +
	                minutes + " Minutos " +
	                seconds + " Segundos.";
		}
		
		
		//por si el jugador quiere unirse corriendo a un bloque
	public void isJoinRunning(Player player) {
		Block block = player.getLocation().getBlock();
		Block b = block.getRelative(0, -2, 0);
		Block b3 = block.getRelative(0, -4, 0);
		
		
		
		
		if(b3.getType() == Material.CHEST){
			  Chest chest = (Chest) b3.getState();
			  if(chest.getCustomName() != null) {
				  FileConfiguration config = plugin.getConfig();
				  List<String> ac = config.getStringList("Maps-Created.List");
				
				  if(ac.contains(chest.getCustomName())) {
					  List<String> l = plugin.getPlayerGround();
					  if(!l.contains(player.getName())) {
						  l.add(player.getName());
						player.setVelocity(player.getLocation().getDirection().multiply(-2).setY(1));
						Countdown c = new Countdown(plugin, 2, player);
						c.ejecucion();
					  } 
					 
					  return;
				  }
				  
			  }
		} 
		 
		if(b.getType() == Material.CHEST){
			  Chest chest = (Chest) b.getState();
			  if(chest.getCustomName() != null) {
				  FileConfiguration config = plugin.getConfig();
				  List<String> ac = config.getStringList("Maps-Created.List");
				  
				  if(ac.contains(chest.getCustomName())) {
					  List<String> l = plugin.getPlayerGround();
					  if(!l.contains(player.getName())) {
						  l.add(player.getName());
						player.setVelocity(player.getLocation().getDirection().multiply(-2).setY(1));
						Countdown c = new Countdown(plugin, 2, player);
						c.ejecucion();
					  } 
				
					
					return;
				  }
				  
			  }
		}
		
	
		
	}	
		

	

	
	public String DifficultyMission(String text) {
		
		if(text.contains("%dific1%")) {
			return text.replace("%dific1%",""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.GREEN+ChatColor.BOLD+" FACIL"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		}
		if(text.contains("%dific2%")) {
			return text.replace("%dific2%",""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.YELLOW+ChatColor.BOLD+" MEDIA"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		}
		if(text.contains("%dific3%")) {
			return text.replace("%dific3%",""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.RED+ChatColor.BOLD+" DIFICIL"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		}
		if(text.contains("%dific4%")) {
			return text.replace("%dific4%",""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.DARK_RED+ChatColor.BOLD+" HARDCORE"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		}
		if(text.contains("%dific5%")) {
			return text.replace("%dific5%",""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.DARK_PURPLE+ChatColor.BOLD+" ELITE"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		}
		if(text.contains("%dific6%")) {
			return text.replace("%dific6%",""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.GOLD+ChatColor.BOLD+" LEYENDA"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		}
		if(text.contains("%resp1%")) {
			return text.replace("%resp1%",""+ChatColor.YELLOW+ChatColor.BOLD+"NOTA: "+ChatColor.GREEN+"Incluye Cofres para ser Revivido por otros Jugadores." );
		}
		if(text.contains("%resp2%")) {
			return text.replace("%resp2%",""+ChatColor.YELLOW+ChatColor.BOLD+"NOTA: "+ChatColor.RED+"No incluye Cofres para ser Revivido por otros Jugadores." );
		}
		
		return text;
	}
	 
	

	    //LISTAS EN LAS QUE EL JUGADOR ES AGREGADO PARA VERIFICAR SI EL JUGADOR ESTA EN PARTIDA Y AÑADIRLO EN LISTA DE VIDA ,PUNTOS ,SALVAR SU INV CON EL JOIN
//	public void MisionMode(Player player,String name) {
//		
//	
//		 plugin.addPlayerArenaList(name,player.getName());
//		 plugin.addPlayerArena(player, name);
//		 plugin.Join(player);
//		 plugin.JoinPlayerPoints(name, player);
//
//		 
//	}
//	
	
//	public void getInventoryY(Player player) {
//		FileConfiguration invt = plugin.getInventorysYaml();
//		String arena = plugin.getArenaPlayerInfo().get(player);
//		FileConfiguration  ym = getConfigGame(arena);
//		
//		  if(!ym.getBoolean("Has-Kit")) {
//			  return;
//		   }
//		  String kit = ym.getString("Start-Kit");
//			//name =
//			if(!invt.contains("Inventory."+kit)) {
//				if(player.isOp()) {
//					player.sendMessage(ChatColor.RED+"Error ese el Kit "+kit+" no existe.");
//				}
//				return;
//			}
//			
//			for (String key : invt.getConfigurationSection("Inventory").getKeys(false)) {
//				if(key.equals(kit)) {
//					ItemStack[] content = ((List<ItemStack>) invt.get("Inventory."+ key)).toArray(new ItemStack[0]);
//					player.getInventory().setContents(content);
//				}
//				
//			}
//	}
			
		//player.sendMessage(ChatColor.GREEN+"Obtuviste la clase "+ChatColor.RED+name);
	
	
	
}
