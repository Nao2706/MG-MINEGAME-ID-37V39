package me.nao.manager;

import java.text.NumberFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.nao.enums.ObjetiveStatusType;
import me.nao.general.info.GameConditions;
import me.nao.main.mg.Minegame;
import me.nao.timers.Countdown;




public class MapSettings {

		
	    private Minegame plugin;
	   
		
		public MapSettings(Minegame plugin) {
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
			ym.set("Type-Map","Adventure");
			ym.set("Revive-System",false);
			ym.set("Max-Player", 5);
			ym.set("Min-Player", 2);
			ym.set("Game-Timer-H-M-S", "1,5,10");
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
			ym.set("Usage-Time","Lunes Viernes");
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
			
			
			List<String> zones = ym.getStringList("Cuboid-Zones.List");
			ym.set("Cuboid-Zones.List",zones);
			zones.add("world,123,34,56;world,124,34,58;CANPLACE");
			zones.add("world,123,34,56;world,124,34,58;CANBREAK");
			
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
	   
	   
	   for(int i = 0 ;i < 5;i++) {
		   	ym.set("Game-Objetives."+objetive+i+".Priority",i);
		 	ym.set("Game-Objetives."+objetive+i+".Status",ObjetiveStatusType.WAITING.toString());
		 	ym.set("Game-Objetives."+objetive+i+".Start-Value",0);
			ym.set("Game-Objetives."+objetive+i+".Complete-Value",10);
			ym.set("Game-Objetives."+objetive+i+".Incomplete-Value",0);
			List<String> description = ym.getStringList("Game-Objetives."+objetive+i+".Description");
			ym.set("Game-Objetives."+objetive+i+".Description",description);
			description.add("Busca una palanca o algo");
			//ym.set("Game-Objetives."+objetive+i+".ObjetiveScope",ObjetiveScope.GLOBAL.toString());
			
			List<String> objetivesmg = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveCompleteMessage");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveCompleteMessage",objetivesmg);
			objetivesmg.add("Vaya completaste el Objetivo Felicidades");
			
			List<String> objetivesaction = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveCompleteActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveCompleteActions",objetivesaction);
			objetivesaction.add("say felicidades :) console2 accion");
			
			List<String> objetivesplayeraction = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveCompletePlayerActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveCompletePlayerActions",objetivesplayeraction);
			objetivesplayeraction.add("say felicidades :) player accion");
			
			
			
		//=====================================================================================	
			List<String> objetives2mg = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveIncompleteMessage");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveIncompleteMessage",objetives2mg);
			objetives2mg.add("Vaya parece que Fracasaste");
			
			List<String> objetivesaction2 = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveIncompleteActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveIncompleteActions",objetivesaction2);
			objetivesaction2.add("say F console Accion");
			
			List<String> objetivesplayeraction2 = ym.getStringList("Game-Objetives."+objetive+i+".ObjetiveIncompletePlayerActions");
			ym.set("Game-Objetives."+objetive+i+".ObjetiveIncompletePlayerActions",objetivesplayeraction2);
			objetivesplayeraction2.add("say infelicidades :) player accion");
	   }
		
	  	List<String> rewardpm = ym.getStringList("Complete-All-Objetives-Primary.Message");
		ym.set("Complete-All-Objetives-Primary.Message",rewardpm);
		rewardpm.add("Han completado todos los objetivos primarios Felicidades 1");
		
	   	List<String> rewardp = ym.getStringList("Complete-All-Objetives-Primary.Actions");
		ym.set("Complete-All-Objetives-Primary.Actions",rewardp);
		rewardp.add("say felicidades 1 accion");
		
		List<String> rewardpl = ym.getStringList("Complete-All-Objetives-Primary.PlayerActions");
		ym.set("Complete-All-Objetives-Primary.PlayerActions",rewardpl);
		rewardpl.add("say felicidades 11 player accion");
		
		
		List<String> rewardsm = ym.getStringList("Complete-All-Objetives-Secondary.Message");
		ym.set("Complete-All-Objetives-Secondary.Message",rewardsm);
		rewardsm.add("Han completado todos los objetivos secundarios Felicidades 2");
		
		List<String> rewards = ym.getStringList("Complete-All-Objetives-Secondary.Actions");
		ym.set("Complete-All-Objetives-Secondary.Actions",rewards);
		rewards.add("say felicidades 2 accion ");
		
		List<String> rewardspl = ym.getStringList("Complete-All-Objetives-Secondary.PlayerActions");
		ym.set("Complete-All-Objetives-Secondary.PlayerActions",rewardspl);
		rewardspl.add("say felicidades 22 player accion");
	   
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
			nf.setGroupingUsed(false);
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
			nf.setGroupingUsed(false);
			nf.setMaximumFractionDigits(0);
				ym.set("Spawn",player.getLocation().getWorld().getName()+"/"+nf.format(player.getLocation().getX())+"/"+nf.format(player.getLocation().getY())+"/"+nf.format(player.getLocation().getZ())+"/"+nf.format(player.getLocation().getYaw())+"/"+nf.format(player.getLocation().getPitch()));
				
				
			
				player.sendMessage(ChatColor.GREEN+"Se a seteado el Spawn correctamente en el Mapa: "+ChatColor.GOLD+name);
				plugin.getCacheSpecificYML(name).save();
				plugin.getCacheSpecificYML(name).reload();
				
				
		}else {
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		}
     
	}
   
   public void setMapSpawnSimpleGenerators(String name, Player player) {
	   
		   Block b = player.getTargetBlock((Set<Material>) null, 5);
	       if(!b.getType().isSolid()) {
	              player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque]");
	               return;
	         }
		   
	   
	   	GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
				plugin.ChargedYml(name, player);
				FileConfiguration ym = plugin.getCacheSpecificYML(name);	
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				nf.setMaximumFractionDigits(0);
				
				List<String> generators = ym.getStringList("Generators.List");
				ym.set("Generators.List",generators);
				if(generators.contains(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()))){
					player.sendMessage(ChatColor.RED+"Esa Ubicacion ya esta Guardada.");

					return;
				}
				generators.add(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()));
				
				
			
				player.sendMessage(ChatColor.GREEN+"Se a seteado el Generador correctamente en el Mapa: "+ChatColor.GOLD+name);
				plugin.getCacheSpecificYML(name).save();
				plugin.getCacheSpecificYML(name).reload();
				
				
		}else {
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		}
     
	}
   
	   public void deleteMapSpawnSimpleGenerators(String name, Player player) {
		   
		   Block b = player.getTargetBlock((Set<Material>) null, 5);
	       if(!b.getType().isSolid()) {
	              player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque]");
	               return;
	         }
		   
	   
	   	GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
				plugin.ChargedYml(name, player);
				FileConfiguration ym = plugin.getCacheSpecificYML(name);	
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				nf.setMaximumFractionDigits(0);
				
				List<String> generators = ym.getStringList("Generators.List");
				ym.set("Generators.List",generators);
				if(!generators.contains(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()))){
					player.sendMessage(ChatColor.RED+"Esa Ubicacion no esta guardada...");
	
					return;
				}
				generators.remove(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()));
				
				
			
				player.sendMessage(ChatColor.GREEN+"Se a eliminado el Generador correctamente en el Mapa: "+ChatColor.GOLD+name);
				plugin.getCacheSpecificYML(name).save();
				plugin.getCacheSpecificYML(name).reload();
				
				
		}else {
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		}
	 
	   }
	   
	   
	   public void setMapSpawnSimpleMobsGenerators(String name, Player player) {
		   
		   Block b = player.getTargetBlock((Set<Material>) null, 5);
	       if(!b.getType().isSolid()) {
	              player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque]");
	               return;
	         }
		   
	   
	   	GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
				plugin.ChargedYml(name, player);
				FileConfiguration ym = plugin.getCacheSpecificYML(name);	
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				nf.setMaximumFractionDigits(0);
				
				List<String> generators = ym.getStringList("Mobs-Generators.List");
				ym.set("Mobs-Generators.List",generators);
				if(generators.contains(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()))){
					player.sendMessage(ChatColor.RED+"Esa Ubicacion ya esta Guardada.");

					return;
				}
				generators.add(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()));
				
				
			
				player.sendMessage(ChatColor.GREEN+"Se a seteado el Generador correctamente en el Mapa: "+ChatColor.GOLD+name);
				plugin.getCacheSpecificYML(name).save();
				plugin.getCacheSpecificYML(name).reload();
				
				
		}else {
			player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
		}
     
	}
   
	   
	   public void deleteMapSpawnSimpleMobsGenerators(String name, Player player) {
		   
		   Block b = player.getTargetBlock((Set<Material>) null, 5);
	       if(!b.getType().isSolid()) {
	              player.sendMessage(ChatColor.RED+"Debes mirar un material Solido [Un Bloque]");
	               return;
	         }
		   
	   
	   	GameConditions gc = new GameConditions(plugin);
		if(gc.ExistMap(name)) {
				plugin.ChargedYml(name, player);
				FileConfiguration ym = plugin.getCacheSpecificYML(name);	
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				nf.setMaximumFractionDigits(0);
				
				List<String> generators = ym.getStringList("Mobs-Generators.List");
				ym.set("Mobs-Generators.List",generators);
				if(!generators.contains(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()))){
					player.sendMessage(ChatColor.RED+"Esa Ubicacion no esta guardada.");
					
					return;
				}
				generators.remove(b.getLocation().getWorld().getName()+"/"+nf.format(b.getLocation().getX())+"/"+nf.format(b.getLocation().getY())+"/"+nf.format(b.getLocation().getZ()));
				
				
			
				player.sendMessage(ChatColor.GREEN+"Se a eliminado el Generador correctamente en el Mapa: "+ChatColor.GOLD+name);
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
			nf.setGroupingUsed(false);
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
 			nf.setGroupingUsed(false);
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
					nf.setGroupingUsed(false);
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
			nf.setGroupingUsed(false);
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
			nf.setGroupingUsed(false);
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

		public List<Entity> getNearbyEntites(Location l , int size){
			
			List<Entity> entities = new ArrayList<Entity>();
			for(Entity e : l.getWorld().getEntities()) {
				if(l.distance(e.getLocation()) <= size) {
					entities.add(e);
				}
			}
			return entities;
			
			
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
	
	
	
}
