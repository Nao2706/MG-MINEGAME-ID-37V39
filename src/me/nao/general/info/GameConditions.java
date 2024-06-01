package me.nao.general.info;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import me.nao.cooldown.CooldownMG2;
import me.nao.gamemode.DestroyNexo;
import me.nao.main.game.Main;
import me.nao.manager.ClassArena;
import me.nao.manager.EstadoPartida;
import me.nao.manager.StopMotivo;
import me.nao.teamsmg.MgTeams;
import me.nao.timers.DialogRun;
import me.nao.timers.InfectedTemp;
import me.nao.timers.NexoTemp;
import me.nao.timers.AdventureTemp;
import me.nao.timers.ResistenceTemp;
import me.nao.yamlfile.game.YamlFilePlus;

public class GameConditions {
	
	private Main plugin;
	
	public GameConditions(Main plugin) {
		this.plugin = plugin;
		
	}
	
	
	//TODO JOIN
	
	public void JoinToTheGames(Player player,String map) {
		LoadDataMap(map);
		
		if(CanJoinToTheMision(player,map)){
			ClassArena cl = new ClassArena(plugin);
			if(ExistProblemBetweenInventorys(map)) {
				player.sendMessage(ChatColor.RED+"Error conflicto de inventarios llama a un Administrador.");
				return;
			}
				//Salva al Jugador checa si debe setearle un inv
				SetAndSavePlayer(player, map);
				AddPlayerToGame(player,map);
				cl.TptoPreLobbyArena(player, map);
				CanStartTheGame(player,map);
				return;
		}
		
	}
	
	
	//TODO LEAVE
	public void LeaveOfTheGame(Player player) {
		 
		if(!isPlayerinGame(player)) {
			player.sendMessage(ChatColor.RED+"No estas en Ningun Juego.");
			return;
		}
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		FileConfiguration mision = getGameConfig(pl.getMapName());
		
		GameInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		
		
		
		EstadoPartida part = ms.getEstopartida();
		
		if(ms instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) ms;
			List<String> spectador = ga.getSpectator();
			List<String> join = ga.getParticipantes();
			
			
			if(part == EstadoPartida.ESPERANDO || part == EstadoPartida.COMENZANDO) {
				if(join.size() < getMinPlayerMap(pl.getMapName())) {
					
					 
					 int min1 =  getMinPlayerMap(pl.getMapName());
					 
					 min1 = min1 - join.size();
					  
					 if(min1 == 1) {
						 SendMessageToUsersOfSameGame(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
					 }else {
						 SendMessageToUsersOfSameGame(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");

						
					 }
					
				}
			}
			
			
			if(spectador.contains(player.getName())) {
				SendMessageToUsersOfSameGame(player, ChatColor.WHITE+"El jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" salio del Modo Espectador."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+(spectador.size() - 1)+ChatColor.RED+"]");
			}else {
				 SendMessageToUsersOfSameGame(player,
							ChatColor.YELLOW+"A Salido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+(ga.getParticipantes().size()-1)+ChatColor.YELLOW+"/"+ChatColor.GOLD+getMaxPlayerMap(pl.getMapName())+ChatColor.RED+")");			
			}
			
			 String mt = mision.getString("Start.Tittle-of-Mision"); 
			 player.sendMessage(ChatColor.GREEN+"Has salido del Mapa "+ChatColor.translateAlternateColorCodes('&',mt.replaceAll("%player%",player.getName())));
			RestorePlayer(player);
			
		}else if(ms instanceof GameNexo) {
			GameNexo gn = (GameNexo) ms;
			List<String> spectador = gn.getSpectator();
			List<String> join = gn.getParticipantes();
			if(part == EstadoPartida.ESPERANDO || part == EstadoPartida.COMENZANDO) {
				if(join.size() < getMinPlayerMap(pl.getMapName())) {
					
					 
					 int min1 =  getMinPlayerMap(pl.getMapName());
					 
					 min1 = min1 - join.size();
					  
					 if(min1 == 1) {
						 SendMessageToUsersOfSameGame(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
					 }else {
						 SendMessageToUsersOfSameGame(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");

						
					 }
					
				}
			}
			
			if(spectador.contains(player.getName())) {
				SendMessageToUsersOfSameGame(player, ChatColor.WHITE+"El jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" salio del Modo Espectador."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+(spectador.size() - 1)+ChatColor.RED+"]");
			}else {
				 SendMessageToUsersOfSameGame(player,
							ChatColor.YELLOW+"A Salido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+(gn.getParticipantes().size()-1)+ChatColor.YELLOW+"/"+ChatColor.GOLD+getMaxPlayerMap(pl.getMapName())+ChatColor.RED+")");			
			}
			 String mt = mision.getString("Start.Tittle-of-Mision"); 
			 player.sendMessage(ChatColor.GREEN+"Has salido del Mapa "+ChatColor.translateAlternateColorCodes('&',mt.replaceAll("%player%",player.getName())));
			RestorePlayer(player);
		}
		
	
		//player.sendMessage("Saliste.");
		
	}
	
	
	public void EndTheGame(String  name) {
		GameInfo gi = plugin.getGameInfoPoo().get(name);
		
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			List<Player> player = ConvertStringToPlayer(ga.getParticipantes());

			for(Player target : player) {
				RestorePlayer(target);
			}
			
			List<Player> spec = ConvertStringToPlayer(ga.getSpectator());
			
			for(Player target : spec) {
				RestorePlayer(target);
			}
				plugin.getGameInfoPoo().remove(name);
				System.out.println("DESPUES MISION OBJECT BORRADO: "+plugin.getGameInfoPoo().toString());

		}
		
	}
	
	

	
	public void ForceGameModePlayerRol(Player player) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo game = plugin.getGameInfoPoo().get(mapa);
		if(game instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) game;
			
			List<String> vivo = ga.getVivo();
			List<String> arrivo = ga.getArrivo();
			List<String> spectador = ga.getSpectator();
			
			
			
			if(vivo.contains(player.getName()) && !arrivo.contains(player.getName())) {
				if(player.getGameMode() != GameMode.ADVENTURE && !player.isOp()) {
					player.setGameMode(GameMode.ADVENTURE);
				}
			}
			if(spectador.contains(player.getName())) {
				if(player.getGameMode() != GameMode.SPECTATOR && !player.isOp()) {
					player.setGameMode(GameMode.SPECTATOR);
				}
			}
			
		}
		
	
	}
	
	public void TryToScapeInSpectatorMode(Player player) {
		PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
		Block block = player.getLocation().getBlock();
		Block b = block.getRelative(0, 0, 0);
		
		if(b.getType() == Material.BARRIER && player.getGameMode() == GameMode.SPECTATOR) {
			ClassArena c = new ClassArena(plugin);
			c.TptoSpawnSpectator(player, pi.getMapName());
			
			Random r = new Random();
			
			List<String> l = new ArrayList<>();
			l.add(ChatColor.RED+"Oh una Barrera no puedes salir del Mapa...");
			l.add(ChatColor.RED+"Creo que no deberias ir por alli...");
			l.add(ChatColor.RED+"Y si pruebas siguiendo a un Jugador , bueno si es que no estas solo...");
			l.add(ChatColor.RED+"Vaya parece que han colocado una Barrera por algo...");
			
			player.sendMessage(l.get(r.nextInt(l.size())));
		}
		
		
		
	}
	

	
	
	
	
	public void AddPlayerToGame(Player player ,String mision) {
	
			GameInfo mis = plugin.getGameInfoPoo().get(mision);
			if(mis instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) mis;
				if(!ga.getParticipantes().contains(player.getName())) {
					ga.getParticipantes().add(player.getName());
					ga.getVivo().add(player.getName());
				}
			}
			
	}
	
	//TODO SIN USO
	public void RevivePlayerToGame(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getVivo().contains(player.getName())) {
				ga.getVivo().add(player.getName());
			}
			if(ga.getMuerto().remove(player.getName()));
		}
		
	
	}
	
	//TODO SIN USO
	public void DeadPlayerToGame(Player player ,String mision) {
		
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getMuerto().contains(player.getName())) {
				ga.getMuerto().add(player.getName());
			}
			if(ga.getVivo().remove(player.getName()));
		}
		
	
		
	}
	
	public void SpectatorAddToGame(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getSpectator().contains(player.getName())) {
				ga.getSpectator().add(player.getName());
			}
		}
		
		
	}
	
	public void PlayerArriveToTheWin(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getArrivo().contains(player.getName())) {
				ga.getArrivo().add(player.getName());
			}
		}
		
		
	}
	
	 
	public void RemoveAllPlayerToGame(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(mis.getGameType() == GameType.ADVENTURE || mis.getGameType() == GameType.RESISTENCE ) {
				MgTeams t = new MgTeams(plugin);
				plugin.CreditKill().remove(player);
				if(ga.getParticipantes().remove(player.getName()));
				if(ga.getVivo().remove(player.getName()));
				if(ga.getMuerto().remove(player.getName()));
				if(ga.getArrivo().remove(player.getName()));
				if(ga.getSpectator().remove(player.getName()));
				plugin.getPlayerInfoPoo().remove(player);
				plugin.getCheckPoint().remove(player);
				t.RemoveAllPlayer(player);
			}
		}else if(mis instanceof GameNexo) {
			GameNexo ga = (GameNexo) mis;
			if(ga.getGameType() == GameType.NEXO ) {
				if(ga.getParticipantes().remove(player.getName()));
				if(ga.getSpectator().remove(player.getName()));
				if(ga.getBlueTeamMg().remove(player.getName()))
				if(ga.getRedTeamMg().remove(player.getName()))
				plugin.getPlayerInfoPoo().remove(player);
			}
		}
	
		
	
	}
	
	public void PlayerWinnerReward(Player player) {
		
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String name = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(name);
		
		if(ms instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) ms;
			if(ms.getGameType() == GameType.ADVENTURE) {
				List<String> arrivo = ga.getArrivo();
				List<String> spectador = ga.getSpectator();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(!arrivo.contains(player.getName())) {
					return;
				}
			}
			
			if(ms.getGameType() == GameType.RESISTENCE) {
				List<String> vivo = ga.getVivo();
				List<String> spectador = ga.getSpectator();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(!vivo.contains(player.getName())) {
					return;
				}
			}
		}	
		
		
		
		
	
		int puntaje = pl.getGamePoints().getKills();
		FileConfiguration mision = getGameConfig(name);

		List<String> winreward = mision.getStringList("Win-Rewards.Commands");
		List<String> win = mision.getStringList("Win.Chat-Message-Win");
		
		
	       String[] wint = mision.getString("Win.Tittle-Time").split("-");
           int aw = Integer.valueOf(wint[0]);
	       int aw2 = Integer.valueOf(wint[1]);
	       int aw3 = Integer.valueOf(wint[2]);
		   String[] sts = mision.getString("Win.Sound-of-Win").split(";");
		
		   try {
	    	   Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
			   Float volumen = Float.valueOf(sts[1]);
			   Float grade = Float.valueOf(sts[2]);
				player.playSound(player.getLocation(), soundtype, volumen,grade);
	       }catch(IllegalArgumentException e1) {
	    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Win Sound-of-Mision-Win en el Mapa: "+ChatColor.GOLD+name);
	       }
		   
		   if(!win.isEmpty()) {
				 for(int i = 0;i < win.size();i++ ) {
					   
					    player.sendMessage(ChatColor.translateAlternateColorCodes('&',win.get(i).replace("%player%",player.getName())));
					 
		   }}
		   
	     player.sendTitle(ChatColor.translateAlternateColorCodes('&',mision.getString("Win.Tittle-of-Win").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',mision.getString("Win.SubTittle-of-Win").replaceAll("%player%", player.getName())), aw, aw2, aw3);

		  if(mision.getBoolean("Win.Reward-Position-Top")) {
			  TopForReward(player, name);
		  }else {
			  if(!winreward.isEmpty()) {
					for(int i = 0 ; i < winreward.size(); i++) {
						String texto = winreward.get(i);

						Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(RewardPointsForItems(puntaje)))));
						
				      }
				}
		  }
	  
		  return;
		   
		
		
	}
	
	public void PlayerLoserReward(Player player) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String name = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(name);
		
		
		if(ms instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) ms;
			if(ms.getGameType() == GameType.ADVENTURE) {
				List<String> arrivo = ga.getArrivo();
				List<String> spectador = ga.getSpectator();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(arrivo.contains(player.getName())) {
					return;
				}
			}
			
			
			if(ms.getGameType() == GameType.RESISTENCE) {
				List<String> vivo = ga.getVivo();
				List<String> spectador = ga.getSpectator();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(vivo.contains(player.getName())) {
					return;
				}
			}
		
		}
		
	
	
	
		FileConfiguration mision = getGameConfig(name);
		List<String> lostreward = mision.getStringList("Lost-Rewards.Commands");
		List<String> lost = mision.getStringList("Lost.Chat-Message-Lost");
		int puntaje = pl.getGamePoints().getKills();
		
		
		  String[] lostt = mision.getString("Lost.Tittle-Time").split("-");
	       int al = Integer.valueOf(lostt[0]);
	       int al2 = Integer.valueOf(lostt[1]);
	       int al3 = Integer.valueOf(lostt[2]);
	       
		   String[] sts = mision.getString("Lost.Sound-of-Lost").split(";");
	       try {
	    	   Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
			   Float volumen = Float.valueOf(sts[1]);
			   Float grade = Float.valueOf(sts[2]);
				player.playSound(player.getLocation(), soundtype, volumen,grade);
	       }catch(IllegalArgumentException e1) {
	    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Lost Sound-of-Mision-Lost en el Mapa: "+ChatColor.GOLD+name);
	       }
	
	       if(!lost.isEmpty()) {
	    	   for(int i = 0;i < lost.size();i++ ) {
		    	   
					 player.sendMessage(ChatColor.translateAlternateColorCodes('&',lost.get(i).replace("%player%",player.getName())));
					 
				}
	       }
	      
	       
		   player.sendTitle(ChatColor.translateAlternateColorCodes('&',mision.getString("Lost.Tittle-of-Lost").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',mision.getString("Lost.SubTittle-of-Lost").replaceAll("%player%", player.getName())), al,al2,al3);
		   if(!lostreward.isEmpty()) {
			   for(int i = 0 ; i < lostreward.size(); i++) {
					String texto = lostreward.get(i);
					Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(RewardPointsForItems(puntaje)))));

			}}
		
		
	}
	
	public void StartGameActions(String map) {
		
		
		FileConfiguration mision = getGameConfig(map);
		
		List<String> start = mision.getStringList("Start-Console.Commands");
		if(!start.isEmpty()) {
			ExecuteMultipleCommandsInConsoleOnly(start);
		}
	
	}
	
	public void EndGameActions(String map) {
	
		
		FileConfiguration mision = getGameConfig(map);
		
		List<String> end = mision.getStringList("End-Console.Commands");
		if(!end.isEmpty()) {
			ExecuteMultipleCommandsInConsoleOnly(end);
		}
		
	}
	
	
	
	
	
	public Location GetLocationOfLobby() {
	    FileConfiguration config = plugin.getConfig();
	    Location l = null;
	    
		for (String key : config.getConfigurationSection("Lobby-Spawn").getKeys(false)) {
			
			String world1 = key;
			double x = Double.valueOf(config.getString("Lobby-Spawn." + key + ".X"));
			double y = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Y"));
			double z = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Z"));
			float yaw = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Yaw"));
			float pitch = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Pitch"));

			l = new Location(Bukkit.getWorld(world1), x, y, z, yaw, pitch);
			
	  }
		return l;
	}
	
	public void ReturnToLobby(Player player) {
		    FileConfiguration config = plugin.getConfig();
			for (String key : config.getConfigurationSection("Lobby-Spawn").getKeys(false)) {
				
				String world1 = key;
				double x = Double.valueOf(config.getString("Lobby-Spawn." + key + ".X"));
				double y = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Y"));
				double z = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Z"));
				float yaw = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Yaw"));
				float pitch = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Pitch"));
	
				Location l = new Location(Bukkit.getWorld(world1), x, y, z, yaw, pitch);
				player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0, 1, 0),
						/* NUMERO DE PARTICULAS */20, 10, 10, 10, /* velocidad */0, null, true);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
				player.teleport(l);
				return;
			
	
		}
	}
	
	
	 
	public void SetAndSavePlayer(Player player,String map) {
		 if(CanJoinWithYourInventory(map) && !CanUseKit(map)) {
				//NO SALVAS SU INVENTARIO
				
				
				if(ExistLobbyMg()) {
					PlayerInfo pl = new PlayerInfo(plugin,false,player, player.getGameMode(), player.isFlying(), GetLocationOfLobby(), true, map,new GamePoints(0,0,0,0,0));
					plugin.getPlayerInfoPoo().put(player, pl);
					pl.ClearGamemodePlayerMg();
				
					
				}else {
					PlayerInfo pl = new PlayerInfo(plugin,false,player, player.getGameMode(), player.isFlying(), player.getLocation(), true, map,new GamePoints(0,0,0,0,0));
					plugin.getPlayerInfoPoo().put(player, pl);
					pl.ClearGamemodePlayerMg();
					
					
				}
			}else{
				//LO SALVAS
				
				if(ExistLobbyMg()) {
					PlayerInfo pl = new PlayerInfo(plugin,true,player,player.getActivePotionEffects(), player.getInventory().getContents(), player.getGameMode(), player.isFlying(),player.getHealth(), player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), player.getFoodLevel(), player.getLevel(),player.getExp(), GetLocationOfLobby(), true, map,new GamePoints(0,0,0,0,0));
					plugin.getPlayerInfoPoo().put(player, pl);
					pl.ClearAllPlayerMg();
					
				}else {
				
					PlayerInfo pl = new PlayerInfo(plugin,true,player,player.getActivePotionEffects(), player.getInventory().getContents(), player.getGameMode(), player.isFlying(),player.getHealth(), player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), player.getFoodLevel(), player.getLevel(),player.getExp(), player.getLocation(), true, map,new GamePoints(0,0,0,0,0));
					plugin.getPlayerInfoPoo().put(player, pl);
					pl.ClearAllPlayerMg();
				}
		
				
			}
	}
	
	//TODO LOAD MAP
	public void LoadDataMap(String map) {
		
		
		if(!plugin.getGameInfoPoo().containsKey(map)) {
			try {
				FileConfiguration mision = getGameConfig(map);
				String time = mision.getString("Timer-H-M-S");
				GameType misiontype = GameType.valueOf(mision.getString("Type-Mission").toUpperCase());
				int maxplayers = mision.getInt("Max-Player");
				int minplayers = mision.getInt("Min-Player");
				
				BossBar boss = null ;
				if(misiontype == GameType.ADVENTURE) {
					boss = Bukkit.createBossBar(""+ChatColor.GREEN+ChatColor.BOLD+"Bienvenido al Mapa "+ChatColor.GOLD+ChatColor.BOLD+map+ChatColor.RED+ChatColor.BOLD+" Modo: "+ChatColor.GREEN+ChatColor.BOLD+"Aventura",BarColor.GREEN, BarStyle.SOLID,  null ,null);
				}else if(misiontype == GameType.RESISTENCE) {
					boss = Bukkit.createBossBar(""+ChatColor.GREEN+ChatColor.BOLD+"Bienvenido al Mapa "+ChatColor.GOLD+ChatColor.BOLD+map+ChatColor.RED+ChatColor.BOLD+" Modo: "+ChatColor.DARK_RED+ChatColor.BOLD+"Resistencia",BarColor.GREEN, BarStyle.SOLID,  null ,null);
				}
				
			
			    boss.setVisible(true);
			    List<String> participantes = new ArrayList<>();
			    List<String> espectador = new ArrayList<>();
			   
			    if(misiontype == GameType.ADVENTURE || misiontype == GameType.RESISTENCE) {
			    	  //pasar solo una lista para los 4 espacios ojo
				  
			    		List<String> vivos = new ArrayList<>();
					    List<String> muertos = new ArrayList<>();
					    List<String> arrivo = new ArrayList<>();
					
			    	GameAdventure mis = new GameAdventure(map ,maxplayers,minplayers,misiontype ,EstadoPartida.ESPERANDO,StopMotivo.NINGUNO,boss,time,LoadObjetivesOfGames(map),participantes,espectador,vivos,muertos,arrivo);
					System.out.println("MISION: "+mis.ShowGame());
					
					plugin.getGameInfoPoo().put(map, mis);
			    }else if(misiontype == GameType.NEXO) {
			    	List<String> t1 = new ArrayList<>();
				    List<String> t2 = new ArrayList<>();
			    	
			    	
			    	
			    	Location bl = BlueNexo(map);
			    	Location rd = RedNexo(map);
			    	
			    	if(bl == null || rd == null) {
			    		
			    		System.out.println("NEXO: Error no uno de los Location del Nexo Rojo o Azul no existe mapa: "+map);
			    		return;
			    	}
			    	
					GameNexo gn = new GameNexo(map ,maxplayers,minplayers,misiontype ,EstadoPartida.ESPERANDO,StopMotivo.NINGUNO,boss,time,LoadObjetivesOfGames(map),participantes,espectador,t1,t2,bl,rd,100,100);
				
					System.out.println("NEXO: "+gn.ShowGame());
				
					plugin.getGameInfoPoo().put(map, gn);
			    }
			    
			  
			}catch(NullPointerException e) {
				e.printStackTrace();
				System.out.println("Error en el Mapa "+map);
			}
		}
		return;
	}
	

	
	
	public boolean CanStartTheGame(Player player ,String map) {
		 FileConfiguration config = plugin.getConfig();
		 GameInfo ms = plugin.getGameInfoPoo().get(map);
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				 System.out.println("MISION: "+ms.ShowGame());
				 player.sendMessage(ChatColor.GREEN+"Has Entrado en el Mapa "+ChatColor.translateAlternateColorCodes('&',getNameOfTheMap(map).replace("%player%",player.getName())));

				 player.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Te has unido"+ChatColor.RED+" ("+ChatColor.GOLD+ga.getParticipantes().size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+ getMaxPlayerMap(map)+ChatColor.RED+")");

				 SendMessageToUsersOfSameGame(player,
				ChatColor.YELLOW+"Se a unido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+ga.getParticipantes().size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+getMaxPlayerMap(map)+ChatColor.RED+")");
				
				 if(getMinPlayerMap(map) > ga.getParticipantes().size()) {
					 
					 int min1 = getMinPlayerMap(map);
					 min1 = min1 - ga.getParticipantes().size();
					 //TEXTO 
					 if(min1 == 1) {
						 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
						 SendMessageToUsersOfSameGame(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
					 }else {
						 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");
						 SendMessageToUsersOfSameGame(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");

					 } 
			   }
				 
			if(getMinPlayerMap(map) == ga.getParticipantes().size()){
				ms.setEstadopartida(EstadoPartida.COMENZANDO);
				if(ms.getEstopartida() == EstadoPartida.COMENZANDO) {
				     int  segundo = config.getInt("CountDownPreLobby");
					 player.sendMessage(ChatColor.GREEN+"Se a alcanzado el minimo de Jugadores necesarios, la partida Comenzara en "+ChatColor.RED+segundo+ChatColor.GREEN+" segundos.");
					 
					 SendMessageToUsersOfSameGame(player, ChatColor.GREEN+"Se a alcanzado el minimo de Jugadores necesarios, la partida Comenzara en "+ChatColor.RED+segundo+ChatColor.GREEN+" segundos.");
					// TempInGame2 t = new TempInGame2(plugin);
			     	// t.Inicio(map);
				}
				
				if(ms.getGameType() == GameType.ADVENTURE) {
					AdventureTemp t = new AdventureTemp(plugin);
					t.Inicio(map);
				}else if(ms.getGameType() == GameType.RESISTENCE) {
					ResistenceTemp t = new ResistenceTemp(plugin);
					t.Inicio(map);
				}else if(ms.getGameType() == GameType.INFECTED) {
					InfectedTemp t = new InfectedTemp(plugin);
					t.Inicio(map);
				}else if(ms.getGameType() == GameType.NEXO) {
					 
				
					DestroyNexo dn = new DestroyNexo(plugin);
					dn.RandomTeam(map);
					
					NexoTemp t = new NexoTemp(plugin);
					t.Inicio(map);
				}
				
			}
		 
		 
		 }
	
 
		return true;
	}
	
	public boolean isPlayerinGame(Player player) {
		return plugin.getPlayerInfoPoo().containsKey(player);
	}
	
	public boolean isMapinGame(String map) {
		return plugin.getGameInfoPoo().containsKey(map);
	}
	
	
	public boolean hasAntiVoid(Player player ) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		FileConfiguration game = getGameConfig(pl.getMapName());
		return game.getBoolean("Anti-Void");
	}
	
	public void SetHeartsInGame(Player player , String map) {
		FileConfiguration game = getGameConfig(map);
		int vida = game.getInt("Set-Hearts");
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vida);
		return;
	}
	
	public void SetDefaultHeartsInGame(Player player) {
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		return;
	}
	
	public boolean CanJoinWithYourInventory(String map) {
		 FileConfiguration game = getGameConfig(map);
		 return game.getBoolean("Allow-Inventory");
	}
	
	public boolean isPvPAllowed(String map) {
		 FileConfiguration game = getGameConfig(map);
		 return game.getBoolean("Allow-PVP");
	}
	
	public boolean CanUseKit(String map) {
		 FileConfiguration game = getGameConfig(map);
		 return game.getBoolean("Has-Kit");
	}
	
	public boolean ExistKit(String map) {
		 FileConfiguration game = getGameConfig(map);
		 FileConfiguration invt = plugin.getInventorysYaml();
		
		 String kit = game.getString("Start-Kit");
		
		return invt.contains("Inventory."+kit);
	}
	
	public boolean ExistProblemBetweenInventorys(String map) {
		 if(CanUseKit(map) && CanJoinWithYourInventory(map)) {
			 return true;
		 }
		return false;
	}
	
	public boolean isLocketTheMap(String map) {
		FileConfiguration config = plugin.getConfig();
		 List<String> ac = config.getStringList("Arenas-Locked.List");
		 return ac.contains(map);
	}
	
	public boolean ExistLobbyMg() {
		 FileConfiguration config = plugin.getConfig();
		 if(config.contains("Lobby-Active")) {
			 return config.getBoolean("Lobby-Active");
		 }
		return false;
	}
	
	public boolean HasMaintenance() {
		 FileConfiguration config = plugin.getConfig();	 
	     return config.getBoolean("Maintenance");
		
	}
	
	
	public boolean ExistMap(String map) {
		FileConfiguration config = plugin.getConfig();
		 
		 List<String> ac = config.getStringList("Maps-Created.List");
		  return ac.contains(map);
		
	}
	
	public boolean ExistMapDialog(String map) {
		FileConfiguration config = plugin.getConfig();
		 
		 List<String> ac = config.getStringList("Maps-Dialogs.List");
		  return ac.contains(map);
		
	}
	
	public boolean HasObjetives(String map) {
		 FileConfiguration game = getGameConfig(map);
	     return game.getBoolean("Has-Objetives");
		
	}
	
	public boolean isNecesaryObjetivePrimary(String map) {
		 FileConfiguration game = getGameConfig(map);
	     return game.getBoolean("Primary-Objetive-Opcional");
		
	}
	
	public boolean isNecesaryObjetiveSedondary(String map) {
		 FileConfiguration game = getGameConfig(map);
	     return game.getBoolean("Secondary-Objetive-Opcional");
		
	}
	
	public String getNameOfTheMap(String map) {
		FileConfiguration mision = getGameConfig(map);
		return mision.getString("Start.Tittle-of-Mision");
	}
	
	public Integer getMaxPlayerMap(String map) {
		FileConfiguration mision = getGameConfig(map);
		return mision.getInt("Max-Player");
	}
	
	/**
	 * Obten el minimo de Juagadores 
	 * @param String , el Mapa
	 */
	public Integer getMinPlayerMap(String map) {
		FileConfiguration mision = getGameConfig(map);
		return mision.getInt("Min-Player");
	}
	
	//TODO TIME 
	public void HasTimePath(String time ,String map) {
		 FileConfiguration game = getGameConfig(map);
		 
		 if(game.contains("Time-Actions."+time)) {
			 ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				
				List<String> actions = game.getStringList("Time-Actions."+time+".List");
				if(!actions.isEmpty()) {
					
					for(int i = 0 ;i<actions.size();i++) {
						String texto = actions.get(i);
						if(!isMessageFromActions(map,texto)) {
							Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto));	
						} 
						
					
						
		 }}}		
		 
		 return;
	}
	
	public boolean isMessageFromActions(String map,String message) {
		
		String keyword = "(P)";
		if(message.endsWith(keyword)) {
			GameInfo ms = plugin.getGameInfoPoo().get(map);
			
			 if(ms instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) ms;
					List<String> play = ga.getParticipantes();
					List<String> vivos = ga.getVivo();
					
					
						for(String target : play) {
							Player user = Bukkit.getServer().getPlayerExact(target);
							   user.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%players%",vivos.toString()).replace(keyword,"").replace("%player%",user.getName())));
						
						return true;
					}
			 
			 }
			//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
			
		
			
			
		}
		return false;
	}
	

	
	public FileConfiguration getGameConfig(String name) {
		YamlFilePlus file = new YamlFilePlus(plugin);
		FileConfiguration config = file.getSpecificYamlFile("Maps",name);
		//u.saveSpecificl(name);
	    return config;
	}
	
	public FileConfiguration getDialogueConfig(String name) {
		YamlFilePlus file = new YamlFilePlus(plugin);
		FileConfiguration config = file.getSpecificYamlFile("Maps-Dialogues",name);
		//u.saveSpecificl(name);
	    return config;
	}

	
	public Long RewardPointsForItems(int points) {
		
		  int puntaje = points;
		  double resultado = puntaje / 5;
		  long pr = 0;
  		  pr = Math.round(resultado);
  		  return pr;
		
	}
	
	

	
	public boolean CanJoinToTheMision(Player player ,String map) {
		 if(isPlayerinGame(player)) {
			 player.sendMessage(ChatColor.RED+"Ya estas en un Juego...");
			 return false;
		 }if(!ExistMap(map)) {
			 player.sendMessage(ChatColor.RED+"Ese Mapa no Existe...");
			 return false;
		 }if(ConditionsToStartAdventureGame(player,map)) {
			return true;//visto bueno para entrar
		 }
	    return false;
	}
	
	
	public boolean ConditionsToStartAdventureGame(Player player,String map) {
		if(HasMaintenance()) {
			if(!player.isOp()) {
				player.sendMessage(ChatColor.RED+"Fuera de Servicio Por Mantenimiento contacta con un Administrador.");
				return false;
			}else {
				player.sendMessage(ChatColor.RED+"MineGame esta Fuera de Servicio Por Mantenimiento, Accediendo como Op.");
			}
		}if(isLocketTheMap(map)) {
			if(!player.isOp()) {
				player.sendMessage(ChatColor.RED+"El Mapa "+map+" esta Deshabilitado.");
				return false;
			}else {
				player.sendMessage(ChatColor.RED+"El Mapa "+map+" esta Deshabilitado, Accediendo como Op.");
			}
		}
		
		 
		 
		 FileConfiguration mision = getGameConfig(map);
		 GameInfo minfo = plugin.getGameInfoPoo().get(map);
		 
		 if(minfo instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) minfo;
				 BossBar boss = minfo.getBoss();
				 GameType misiontype = minfo.getGameType();
				 int maxplayers = mision.getInt("Max-Player");
				 CooldownMG2 cooldown = new CooldownMG2(plugin) ;
				 
				if(misiontype == GameType.ADVENTURE) {
					
					if(cooldown.HasSancionPlayer(player)) {
						// System.out.println("Bloqueado");
						 return false;
					 }
					if(!mision.contains("Pre-Lobby")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el PreLobby");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("Spawn")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("Spawn-Spectator")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 
					 if(minfo.getEstopartida() == EstadoPartida.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipantes().size() == maxplayers && minfo.getEstopartida() == EstadoPartida.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getEstopartida() == EstadoPartida.JUGANDO && !isPlayerinGame(player)) {
						 if(cooldown.HasSancionPlayer(player)) {
							 //MODO ESPECTADOR no te uniras como jugador
							 player.sendMessage(ChatColor.YELLOW+"Estas Baneado o tienes un TempBan pero puedes Observar.");
						 }
							 //MODO ESPECTADOR no te uniras como jugador
						 JoinSpectator(player,map);
						  
						 return false;
					 }if(mision.getBoolean("Requires-Permission")) {
			 				String perm = mision.getString("Permission-To-Play");
			 				if(!player.hasPermission(perm)) {
			 					List<String> perml = mision.getStringList("Requires-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						
			 						
			 					}else {
			 						player.sendMessage(ChatColor.RED+"Mapa Bloqueado: Necesitas un Permiso para Acceder.");
			 					}
			 					
			 					return false;
			 				}
			 		 }if(mision.getBoolean("Has-Time")) {
						 String time = mision.getString("Time");
					    	
					        if(!PassedTimeMg(player,time)) return false;
					 }
			 		 
			 		boss.addPlayer(player);
			 		return true;
				}else if(misiontype == GameType.RESISTENCE) {
					
					if(cooldown.HasSancionPlayer(player)) {
						// System.out.println("Bloqueado");
						 return false;
					 }
					if(!mision.contains("Pre-Lobby")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el PreLobby");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("Spawn")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("Spawn-Spectator")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 
					 if(!mision.contains("Spawn-End")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 
					 
					 if(minfo.getEstopartida() == EstadoPartida.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipantes().size() == maxplayers && minfo.getEstopartida() == EstadoPartida.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getEstopartida() == EstadoPartida.JUGANDO && !isPlayerinGame(player)) {
						 if(cooldown.HasSancionPlayer(player)) {
							 //MODO ESPECTADOR no te uniras como jugador
							 player.sendMessage(ChatColor.YELLOW+"Estas Baneado o tienes un TempBan pero puedes Observar.");
						 }
							 //MODO ESPECTADOR no te uniras como jugador
						 JoinSpectator(player,map);
						
						 return false;
					 }if(mision.getBoolean("Requires-Permission")) {
			 				String perm = mision.getString("Permission-To-Play");
			 				if(!player.hasPermission(perm)) {
			 					List<String> perml = mision.getStringList("Requires-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						
			 						
			 					}else {
			 						player.sendMessage(ChatColor.RED+"Mapa Bloqueado: Necesitas un Permiso para Acceder.");
			 					}
			 					
			 					return false;
			 				}
			 		 }if(mision.getBoolean("Has-Time")) {
						 String time = mision.getString("Time");
					    	
					        if(!PassedTimeMg(player,time)) return false;
					 }
			 		 
			 		boss.addPlayer(player);
			 		return true;
				}else if(misiontype == GameType.INFECTED) {
					
					if(cooldown.HasSancionPlayer(player)) {
						// System.out.println("Bloqueado");
						 return false;
					 }
					if(!mision.contains("Pre-Lobby")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el PreLobby");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("Spawn")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("Spawn-Spectator")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 
					 if(!mision.contains("Spawn-End")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 
					 
					 if(minfo.getEstopartida() == EstadoPartida.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipantes().size() == maxplayers && minfo.getEstopartida() == EstadoPartida.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getEstopartida() == EstadoPartida.JUGANDO && !isPlayerinGame(player)) {
						 if(cooldown.HasSancionPlayer(player)) {
							 //MODO ESPECTADOR no te uniras como jugador
							 player.sendMessage(ChatColor.YELLOW+"Estas Baneado o tienes un TempBan pero puedes Observar.");
						 }
						 JoinSpectator(player,map);
						
						 return false;
					 }if(mision.getBoolean("Requires-Permission")) {
			 				String perm = mision.getString("Permission-To-Play");
			 				if(!player.hasPermission(perm)) {
			 					List<String> perml = mision.getStringList("Requires-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						
			 						
			 					}else {
			 						player.sendMessage(ChatColor.RED+"Mapa Bloqueado: Necesitas un Permiso para Acceder.");
			 					}
			 					
			 					return false;
			 				}
			 		 }if(mision.getBoolean("Has-Time")) {
						 String time = mision.getString("Time");
					    	
					        if(!PassedTimeMg(player,time)) return false;
					 }
			 		 
			 		boss.addPlayer(player);
			 		return true;
				}
		 }else if(minfo instanceof GameNexo) {
			 
			 GameNexo ga = (GameNexo) minfo;
			 BossBar boss = minfo.getBoss();
			 GameType misiontype = minfo.getGameType();
			 int maxplayers = mision.getInt("Max-Player");
			 CooldownMG2 cooldown = new CooldownMG2(plugin) ;
			 
			 if(misiontype == GameType.NEXO) {
					
					if(cooldown.HasSancionPlayer(player)) {
						// System.out.println("Bloqueado");
						 return false;
					 }
					if(!mision.contains("Pre-Lobby")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el PreLobby");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("SpawnBlue")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el NexoBlue");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("SpawnRed")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el NexoRed");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("NexoBlue")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el NexoBlue");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("NexoRed")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el NexoRed");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(!mision.contains("Spawn-Spectator")) {
						 if(player.isOp()) {
							 player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no tiene seteado el Spawn-Spectator");
						 }
						 else {
							 player.sendMessage(ChatColor.RED+"Error en el Mapa: "+map);
						 }
						 return false;
					 }
					 if(minfo.getEstopartida() == EstadoPartida.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipantes().size() == maxplayers && minfo.getEstopartida() == EstadoPartida.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getEstopartida() == EstadoPartida.JUGANDO && !isPlayerinGame(player)) {
						 if(cooldown.HasSancionPlayer(player)) {
							 //MODO ESPECTADOR no te uniras como jugador
							 player.sendMessage(ChatColor.YELLOW+"Estas Baneado o tienes un TempBan pero puedes Observar.");
						 }
						 JoinSpectator(player,map);
						
						 return false;
					 }if(mision.getBoolean("Requires-Permission")) {
			 				String perm = mision.getString("Permission-To-Play");
			 				if(!player.hasPermission(perm)) {
			 					List<String> perml = mision.getStringList("Requires-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						
			 						
			 					}else {
			 						player.sendMessage(ChatColor.RED+"Mapa Bloqueado: Necesitas un Permiso para Acceder.");
			 					}
			 					
			 					return false;
			 				}
			 		 }if(mision.getBoolean("Has-Time")) {
						 String time = mision.getString("Time");
					    	
					        if(!PassedTimeMg(player,time)) return false;
					 }
			 		 
			 		boss.addPlayer(player);
			 		return true;
				}
		 }
		 
		
		
		
		
		return false;
	}

	
	public boolean hasPlayerACheckPoint(Player player) {
		
		if(plugin.getCheckPoint().containsKey(player)) {
			player.setNoDamageTicks(20*5);
		    PotionEffect vid = new PotionEffect(PotionEffectType.HEALTH_BOOST,/*duration*/ 10 * 20,/*amplifier:*/5, true ,true,true );
		    PotionEffect reg = new PotionEffect(PotionEffectType.REGENERATION,/*duration*/ 10 * 20,/*amplifier:*/5, true ,true,true );
			PotionEffect fir = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,/*duration*/ 10 * 20,/*amplifier:*/5, true ,true,true );
			PotionEffect resis = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,/*duration*/ 10 * 20,/*amplifier:*/5, true ,true,true );
			player.addPotionEffect(vid);
			player.addPotionEffect(fir);
			player.addPotionEffect(reg);
			player.addPotionEffect(resis);
			player.setFireTicks(0);
		    player.sendMessage(ChatColor.GREEN+"Has usado tu checkpoint..");
		    player.sendMessage(ChatColor.YELLOW+"(coloca otro si puedes para evitar morir.)");
			player.teleport(plugin.getCheckPoint().get(player));
			plugin.getCheckPoint().remove(player);
			return true;
		}
		
		return false;
	}
	
	
	//TODO TP
	public void TpAllPlayersOfGameToLocation(Player player) {
		if(isPlayerinGame(player)) {
			PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				List<Player> targets = ConvertStringToPlayer(ga.getVivo());
				for(Player players : targets) {
					players.teleport(player.getLocation());
				}
				SendMessageToUserAndConsole(player, ChatColor.GREEN+"Los Jugadores fueron Teletransportado a "+player.getName());
			}
		

		}
	}
	
	public void TpPlayerOfGameToLocation(Player player,String name) {
		if(isPlayerinGame(player)) {
			PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
			 if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					List<Player> targets = ConvertStringToPlayer(ga.getVivo());
					for(Player players : targets) {
						if(players.getName().equals(name)) {
							players.teleport(player.getLocation());
							break;
						}
					}
					SendMessageToUserAndConsole(player, ChatColor.GREEN+name+" fue Teletransportado a "+player.getName());
			 }
			
		
			
		}
	}
	
	public void TpAllPlayersOfGameToLocationSpecific(Player player,String map,String world,double x,double y,double z,float yaw,float pitch) {
		
			if(isMapinGame(map)) {
				GameInfo gi = plugin.getGameInfoPoo().get(map);
				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
					List<Player> targets = ConvertStringToPlayer(ga.getVivo());
					for(Player players : targets) {
						players.teleport(l);
					}
					SendMessageToUserAndConsole(player, ChatColor.GREEN+"Los Jugadores fueron Teletransportados a "+l.toString());
				}
				

				
			}
			
			
		
		
	}
	
	public void TpAllPlayersOfGameToLocationSpecific(Player player,String map,String world,double x,double y,double z) {
		
		if(isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				Location l = new Location(Bukkit.getWorld(world), x, y, z);
				List<Player> targets = ConvertStringToPlayer(ga.getVivo());
				for(Player players : targets) {
					l.setYaw(players.getLocation().getYaw());
					l.setPitch(players.getLocation().getPitch());
					players.teleport(l);
				}
				
				SendMessageToUserAndConsole(player, ChatColor.GREEN+"Los Jugadores fueron Teletransportados a "+l.toString());
			
			}
			
	

			
		}
		
		
	
	
	}
	
	
	public void TpPlayerOfGameToLocationSpecific(Player player,String target,String map,String world,double x,double y,double z,float yaw,float pitch) {
		
		if(isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				
				Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
				List<Player> targets = ConvertStringToPlayer(ga.getVivo());
				for(Player players : targets) {
					if(players.getName().equals(target)) {
						players.teleport(l);
						break;
					}
					
				}
				SendMessageToUserAndConsole(player, ChatColor.GREEN+target+" fue Teletransportado a "+l.toString());
			}
			
		

			
		}
	
	}
	
	public void TpPlayerOfGameToLocationSpecific(Player player,String target,String map,String world,double x,double y,double z) {
		
		if(isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				
				
				Location l = new Location(Bukkit.getWorld(world), x, y, z);
				List<Player> targets = ConvertStringToPlayer(ga.getVivo());
				for(Player players : targets) {
					if(players.getName().equals(target)) {
						l.setYaw(players.getLocation().getYaw());
						l.setPitch(players.getLocation().getPitch());
						players.teleport(l);
						break;
					}
					
				}
				
				SendMessageToUserAndConsole(player, ChatColor.GREEN+target+" fue Teletransportado a "+l.toString());
				
			}
			
			
		}
	
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
	
	
	
	public LocalDateTime AddOrRemoveMg() {
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
	//				0				  1  2	3	4   5 total 6 	
	//MG (BAN, TEMPBAM, KICK , WARN) NAO 5h 10m 10s No sabe Jugar en equipo
	//TODO TIME
	public boolean PassedTimeMg(Player player ,String time) {
		
	LocalDateTime lt = AddOrRemoveMg();
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
					player.sendMessage(ChatColor.GREEN+TimeDiferenceMg(lt, t));
					player.sendMessage(ChatColor.AQUA+"Para que pueda estar Disponible.");
					player.sendMessage(ChatColor.GREEN+"================================================");
					//isJoinRunning(player);
					return false;
				}
				if(lt.isAfter(t2)) {
					player.sendMessage(ChatColor.RED+"================================================");
					player.sendMessage(""+ChatColor.YELLOW+ChatColor.BOLD+"                     [CERRADO] ");
					player.sendMessage(ChatColor.YELLOW+"Me temo que el Tiempo para Ingresar ya paso.");
					player.sendMessage(""+ChatColor.YELLOW+ChatColor.BOLD+"                [A Trasncurrido] ");
					player.sendMessage(ChatColor.RED+TimeDiferenceMg(t2, lt));
					player.sendMessage(ChatColor.YELLOW+"Desde que ha terminado.");
					player.sendMessage(ChatColor.RED+"================================================");
					//despues de pasar la fecha
					//isJoinRunning(player);
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
							player.sendMessage(ChatColor.GREEN+TimeDiferenceMg(lt, t));
							player.sendMessage(ChatColor.AQUA+"Para que pueda estar Disponible.");
							player.sendMessage(ChatColor.GREEN+"================================================");
							//isJoinRunning(player);
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
							//isJoinRunning(player);
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
					//isJoinRunning(player);
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
					//isJoinRunning(player);
					return false;
				}
				
				
			}
	   }
	
		
		return false;
	}
	
	
	public String TimeDiferenceMg(LocalDateTime fechactual, LocalDateTime fin) {
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

               return  years+ " Años " +
               months+" Meses " + 
                days + " Dias " +
                hours + " Horas " +
                minutes + " Minutos " +
                seconds + " Segundos.";
	}
	
	
	public void JoinSpectator(Player player ,String map) {
		 //MODO ESPECTADOR no te uniras como jugador
		 SetAndSavePlayer(player, map);
		 SpectatorAddToGame(player, map);
		 GameInfo ms = plugin.getGameInfoPoo().get(map);
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				 List<String> spectador = ga.getSpectator();
				 player.sendMessage(ChatColor.GREEN+"Estas como Espectador en el Mapa: "+ChatColor.GOLD+map);
				 SendMessageToUsersOfSameGame(player, ChatColor.WHITE+"El Jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" se Unio como Espectador."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+(spectador.size())+ChatColor.RED+"]");
				 ClassArena ca = new ClassArena(plugin);
				 ca.TptoSpawnSpectator(player, map);
		 }
		 
		
		
	}
	
	//TODO CHAT
	public void SendMessageToAllUsersOfSameMission(Player player ,String text) {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String arenaName = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(arenaName);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				
				
				List<Player> play = ConvertStringToPlayer(ga.getParticipantes());
				List<Player> spect = ConvertStringToPlayer(ga.getSpectator());
				
				for(Player target : play) {
					
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
				}
				
				if(!spect.isEmpty()) {
					for(Player target : spect) {
						
						target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				}
				 Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+arenaName.toUpperCase()+": "+text);
		 }
	

	}
	
	
	//TODO CHAT
	public void SendMessageToAllUsersOfSameMissionCommand(Player player, String map ,String text) {
		
		if(!isMapinGame(map)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe.");
			}
			
			 Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe.");
			return;
		}
		GameInfo ms = plugin.getGameInfoPoo().get(map);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		
		if(player != null) {
			player.sendMessage(ChatColor.WHITE+"Mensaje en Mapa: "+ChatColor.GOLD+map+" "+ChatColor.translateAlternateColorCodes('&', text));
		}
		 Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE+"Mensaje en Mapa: "+ChatColor.GOLD+map+" "+ChatColor.translateAlternateColorCodes('&', text));
		
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				
				
				List<Player> play = ConvertStringToPlayer(ga.getParticipantes());
				List<Player> spect = ConvertStringToPlayer(ga.getSpectator());
				
				for(Player target : play) {
					
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
				}
				
				if(!spect.isEmpty()) {
					for(Player target : spect) {
						
						target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				}
		 }
	

	}
	
	//mg message Tutorial hola que hacen ???
	//mg tittle Tutorial hola que hacen ??? ; que mas
	public void SendTittleToAllUsersOfSameMissionCommand(Player player, String map ,String text) {
		
		if(!isMapinGame(map)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe.");
			}
			
			 Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe.");
			return;
		}
		GameInfo ms = plugin.getGameInfoPoo().get(map);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		
		if(player != null) {
			player.sendMessage(ChatColor.WHITE+"Titulo en Mapa: "+ChatColor.GOLD+map+" "+ChatColor.translateAlternateColorCodes('&', text));
		}
		 Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE+"Titulo en Mapa: "+ChatColor.GOLD+map+" "+ChatColor.translateAlternateColorCodes('&', text));
		
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				
				String message1 = "";
				String message2 = "";
				
				if(text.contains(";")) {
					String[] split = text.split(";");
					message1 = split[0];
					message2 = split[1];
				}else {
					message1 = text;
					message2 = ""; 
				}
				
				List<Player> play = ConvertStringToPlayer(ga.getParticipantes());
				List<Player> spect = ConvertStringToPlayer(ga.getSpectator());
				
				
				for(Player target : play) {
					
					target.sendTitle(ChatColor.translateAlternateColorCodes('&', message1), ChatColor.translateAlternateColorCodes('&', message2), 20, 40, 20);
				}
				
				if(!spect.isEmpty()) {
					for(Player target : spect) {
						
						target.sendTitle(ChatColor.translateAlternateColorCodes('&', message1), ChatColor.translateAlternateColorCodes('&', message2), 20, 40, 20);
					}
				}
		 }
	

	}
	
	
	public void SendMessageToUsersOfSameGame(Player player ,String text) {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String arenaName = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(arenaName);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				List<Player> play = ConvertStringToPlayer(ga.getParticipantes());
				
					for(Player target : play) {
						
						   if(target.getName().equals(player.getName())) continue;
						   target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				
		 } 
		
		
	}
	
	public void SendMessageToAllPlayersInGame(String map,String text) {
		
		
		GameInfo ms = plugin.getGameInfoPoo().get(map);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				List<String> play = ga.getParticipantes();
				
				if(!play.isEmpty()) {
					for(String target : play) {
						Player user = Bukkit.getServer().getPlayerExact(target);
						
						user.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				}	
		 }
	

		
	}
	
	//TODO CONSOLE AND PLAYER FOR OP OR DEBUG
	public void SendMessageToUserAndConsole(Player player ,String text) {
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',text));
		if(player != null) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
		}
		
	}
	
	//TODO RESTORE
	public void RestorePlayer(Player player) {
	
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		
		 if(ms instanceof GameAdventure) {
				//GameAdventure ga = (GameAdventure) ms;
				SetDefaultHeartsInGame(player);
				BossBar boss = ms.getBoss();
				boss.removePlayer(player);
				
				System.out.println("ANTES MISION: "+ms.ShowGame());
				
					//SE SECCIONA POR QUE HAY QUE VER SI SE SALVO O NO SU INVENTARIO
					if(pl.hasPlayerMoreInfo()) {
						player.teleport(pl.getLocationMG());
						pl.RestoreAllPlayerMg(player);
						if(ms.getEstopartida() == EstadoPartida.TERMINANDO) {
							PlayerWinnerReward(player);
							PlayerLoserReward(player);
						}
						
						RemoveAllPlayerToGame(player, pl.getMapName());
					
					}else {
						player.teleport(pl.getLocationMG());
						pl.RestoreGamemodePlayerMg(player);
						if(ms.getEstopartida() == EstadoPartida.TERMINANDO) {
							PlayerWinnerReward(player);
							PlayerLoserReward(player);
						}
						RemoveAllPlayerToGame(player, pl.getMapName());
					}
				
		 }else if(ms instanceof GameNexo) {
			// GameNexo gn = (GameNexo) ms;
			    SetDefaultHeartsInGame(player);
				BossBar boss = ms.getBoss();
				boss.removePlayer(player);
				System.out.println("ANTES NEXO: "+ms.ShowGame());
				
					player.teleport(pl.getLocationMG());
					pl.RestoreAllPlayerMg(player);
				
				RemoveAllPlayerToGame(player, pl.getMapName());
				
		 }
		
	
		
	}
	
	public void setKitMg(Player player) {
		FileConfiguration invt = plugin.getInventorysYaml();
		String map = plugin.getPlayerInfoPoo().get(player).getMapName();
		FileConfiguration mision = getGameConfig(map);
		
			//name =
		  if(!CanUseKit(map)) {
			return;  
		  }
		  
		  
		  if(!ExistKit(map)) {
				 
			  if(player.isOp()) {
				    String kit = mision.getString("Start-Kit");
					player.sendMessage(ChatColor.RED+"Error ese el Kit "+kit+" no existe.");
				}
			  return;
		   }

		      String kit = mision.getString("Start-Kit");
			for (String key : invt.getConfigurationSection("Inventory").getKeys(false)) {
				if(key.equals(kit)) {
					@SuppressWarnings("unchecked")
					ItemStack[] content = ((List<ItemStack>) invt.get("Inventory."+ key)).toArray(new ItemStack[0]);
					player.getInventory().setContents(content);
				}
				
			}
	}
	
	
	   //TODO TOP
   	public void Top(Player player ,String arenaName) {
   					FileConfiguration message = plugin.getMessage();
   		
		

		// PRIMERA PARTE
					HashMap<String, Integer> scores = new HashMap<>();

						GameInfo ms = plugin.getGameInfoPoo().get(arenaName);
						 if(ms instanceof GameAdventure) {
								GameAdventure ga = (GameAdventure) ms;
								List<Player> joins = ConvertStringToPlayer(ga.getParticipantes());
								List<Player> spectador = ConvertStringToPlayer(ga.getSpectator());
								for(Player user : joins) {
									PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
									
									if(spectador.contains(user)) continue;
									 scores.put(user.getName(), pl.getGamePoints().getKills());	
								}
						 }
					
						
					
						
							
						
			// SE GUARDAN LOS DATOS EN EL HASH MAP
			

		

		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});

		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
	
		
				System.out.println("-------TOP--------");
				
				
					if (message.getBoolean("Message.message-top")) {
						List<String> messagep1 = message.getStringList("Message.message-top-decoracion1");
						for (int j1 = 0; j1 < messagep1.size(); j1++) {
							String texto2 = messagep1.get(j1);
							 
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto2));
					  }}			
							
					
					int i = 0;
					for (Map.Entry<String, Integer> e : list) {

					
						if (i <= message.getInt("Top-Amount")) {
							i++;
							// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

							if (message.getBoolean("Message.message-top")) {
								List<String> messagep = message.getStringList("Message.message-top-texto");
								for (int j = 0; j < messagep.size(); j++) {
									String texto = messagep.get(j);
								
								 		
										// String time = plugin.getPlayerCronomet().get(e.getKey());
										 player.sendMessage(ChatColor.translateAlternateColorCodes('&',texto
												 .replace("%player%", e.getKey())
												 .replace("%place%", Integer.toString(i))
												 .replace("%pointuser%", Integer.toString(e.getValue()))
												 .replace("%reward%", Long.toString(RewardPointsForItems(e.getValue())))
												 .replace("%revive%", Integer.toString(getReviveInfo(e.getKey())))
												 .replace("%asisrevive%", Integer.toString(getReviveAsistenceInfo(e.getKey())))
												 .replace("%deads%", Integer.toString(getDeadsInfo(e.getKey())))
												 .replace("%damage%", Integer.toString(getDamageInfo(e.getKey())))
												 //.replace("%cronomet%", time)
												
												 ));
										
										 
									
								}

							}

						}
					}
					
					 if (message.getBoolean("Message.message-top")) {
							List<String> messagep3 = message.getStringList("Message.message-top-decoracion2");
							for (int j3 = 0; j3 < messagep3.size(); j3++) {
								String texto3 = messagep3.get(j3);
							
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto3));
					  }}
					
					
				
	
			


		
   		
   	}
   	
   
   	public void TopConsole(String map) {
	FileConfiguration message = plugin.getMessage();
   		
		

		// PRIMERA PARTE
					HashMap<String, Integer> scores = new HashMap<>();

						GameInfo ms = plugin.getGameInfoPoo().get(map);
						 if(ms instanceof GameAdventure) {
								GameAdventure ga = (GameAdventure) ms;
								List<Player> joins = ConvertStringToPlayer(ga.getParticipantes());
								
								for(Player user : joins) {
									PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
									
								
									 scores.put(user.getName(), pl.getGamePoints().getKills());	
								}
						 }
					
						
					
						
							
						
			// SE GUARDAN LOS DATOS EN EL HASH MAP
			

		

		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});

		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
		
		
				System.out.println("-------TOP--------");
				
				
					if (message.getBoolean("Message.message-top")) {
						List<String> messagep1 = message.getStringList("Message.message-top-decoracion1");
						for (int j1 = 0; j1 < messagep1.size(); j1++) {
							String texto2 = messagep1.get(j1);
							 
							SendMessageToUserAndConsole(null, texto2);
					  }}			
							
					
					int i = 0;
					for (Map.Entry<String, Integer> e : list) {

					
						if (i <= message.getInt("Top-Amount")) {
							i++;
							// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

							if (message.getBoolean("Message.message-top")) {
								List<String> messagep = message.getStringList("Message.message-top-texto");
								for (int j = 0; j < messagep.size(); j++) {
									String texto = messagep.get(j);
								
								 		
										// String time = plugin.getPlayerCronomet().get(e.getKey());
									SendMessageToUserAndConsole(null,texto
												 .replace("%player%", e.getKey())
												 .replace("%place%", Integer.toString(i))
												 .replace("%pointuser%", Integer.toString(e.getValue()))
												 .replace("%reward%", Long.toString(RewardPointsForItems(e.getValue())))
												 .replace("%revive%", Integer.toString(getReviveInfo(e.getKey())))
												 .replace("%asisrevive%", Integer.toString(getReviveAsistenceInfo(e.getKey())))
												 .replace("%deads%", Integer.toString(getDeadsInfo(e.getKey())))
												 .replace("%damage%", Integer.toString(getDamageInfo(e.getKey())))
												 //.replace("%cronomet%", time)
												
												 );
										
										 
									
								}

							}

						}
					}
					
					 if (message.getBoolean("Message.message-top")) {
							List<String> messagep3 = message.getStringList("Message.message-top-decoracion2");
							for (int j3 = 0; j3 < messagep3.size(); j3++) {
								String texto3 = messagep3.get(j3);
							
								SendMessageToUserAndConsole(null,texto3);
					  }}
   	}
   	 
   	
   	public int getReviveInfo(String name) {
   	 return plugin.getPlayerInfoPoo().get(ConvertStringToPlayer(name)).getGamePoints().getRevive();
   	}
   	
 	public int getDeadsInfo(String name) {
 	   	 return plugin.getPlayerInfoPoo().get(ConvertStringToPlayer(name)).getGamePoints().getDeads();
 	}
 	public int getReviveAsistenceInfo(String name) {
 	   	 return plugin.getPlayerInfoPoo().get(ConvertStringToPlayer(name)).getGamePoints().getReviveAsistence();
 	}
 	
 	public int getDamageInfo(String name) {
	   	 return TransformPosOrNeg(plugin.getPlayerInfoPoo().get(ConvertStringToPlayer(name)).getGamePoints().getDamage());
	}
 	
	public int TransformPosOrNeg(int i) {
		return i =  (~(i -1));
	}
   	
	   //TODO TOP
	public void TopForReward(Player player ,String arenaName) {
				
					FileConfiguration mision = getGameConfig(arenaName);
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

		// PRIMERA PARTE
					HashMap<String, Integer> scores = new HashMap<>();
					

					
						GameInfo ms = plugin.getGameInfoPoo().get(arenaName);
						if(ms instanceof GameAdventure) {
							GameAdventure ga = (GameAdventure) ms;
							List<Player> joins = ConvertStringToPlayer(ga.getParticipantes());
							List<Player> spectador = ConvertStringToPlayer(ga.getSpectator());
							List<String> winreward = mision.getStringList("Win-Rewards.Commands");
							for(Player user : joins) {
								PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
								
								if(spectador.contains(user)) continue;
								 scores.put(user.getName(), pl.getGamePoints().getKills());	
							}
						
							
							// SE GUARDAN LOS DATOS EN EL HASH MAP
							

						

						// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
						List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

						
						Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
							public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
								return e2.getValue() - e1.getValue();
							}
						});

						// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
					
						
								System.out.println("-------TOP--------");
								
									int i = 0;
									for (Map.Entry<String, Integer> e : list) {

									
										if(i < 3) {
											
											
											
											if(e.getKey().equals(player.getName())) {
												if(!winreward.isEmpty()) {
													
													if(i < winreward.size()) {
														String texto = winreward.get(i);

														Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(RewardPointsForItems(e.getValue())))));
														return;
													}
												
												      
												}
											}
											i++;
											// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

											

										}
									}
							
						}
		
	}	
   	
   	//TODO OBJETIVOS
 	public GameObjetivesMG LoadObjetivesOfGames(String map) {
   		FileConfiguration game = getGameConfig(map);
   		
   		List<ObjetivesMG> l = new ArrayList<>();   		
   		if(HasObjetives(map)) {
   			
   			ObjetivesMG ghost = new ObjetivesMG("Mapa Con Objetivos Borrados",1,0,0,"Habilitaron los Objetivos pero no hay ninguno en la Config del Mapa.",ObjetiveType.WAITING);
   			l.add(ghost);
   			if(game.contains("Game-Objetives")) {
   	   			for (String key : game.getConfigurationSection("Game-Objetives").getKeys(false)) {
   	   				int priority = game.getInt("Game-Objetives."+key+".Priority");
   	   				String status = game.getString("Game-Objetives."+key+".Status");
   	   				int value = game.getInt("Game-Objetives."+key+".Value-Limit");
   	   				String description = game.getString("Game-Objetives."+key+".Description");
   	   				ObjetivesMG gob = new ObjetivesMG(key,priority,0,value,description,ObjetiveType.valueOf(status));
   	   				l.add(gob);
   	   				
   	   		}}
   			
   			if(l.size() > 1) {
   				if(l.stream().filter(o -> o.getNombre().equals("Mapa Con Objetivos Borrados")).findFirst().isPresent()) {
   					l.remove(ghost);
   				}
   		     }
   		}
   		
   		GameObjetivesMG go = new GameObjetivesMG(l);
   		
   		return go;
   	}
 	
 	
	public void LoadObjetivesOfGameDebug(String map) {
   		FileConfiguration game = getGameConfig(map);
   		
   		List<ObjetivesMG> l = new ArrayList<>();
   		
   		if(HasObjetives(map)) {
   			ObjetivesMG ghost = new ObjetivesMG("Mapa Con Objetivos Borrados",1,0,0,"Habilitaron los Objetivos pero no hay ninguno en la Config del Mapa.",ObjetiveType.WAITING);
   			l.add(ghost);
	   		if(game.contains("Game-Objetives")) {
	   			for (String key : game.getConfigurationSection("Game-Objetives").getKeys(false)) {
	   				int priority = game.getInt("Game-Objetives."+key+".Priority");
	   				String descripcion = game.getString("Game-Objetives."+key+".Description");
	   				String status = game.getString("Game-Objetives."+key+".Status");
	   				int value = game.getInt("Game-Objetives."+key+".Value-Limit");
	   				
	   				List<String> objetivesmg = game.getStringList("Game-Objetives."+key+".ObjetiveCompleteMessage");
	   				List<String> objetivesaction = game.getStringList("Game-Objetives."+key+".ObjetiveCompleteActions");
	   				List<String> objetives2mg = game.getStringList("Game-Objetives."+key+".ObjetiveIncompleteMessage");
	   				List<String> objetivesaction2 = game.getStringList("Game-Objetives."+key+".ObjetiveIncompleteActions");
	   				
	   				System.out.println(key);
	   				System.out.println(descripcion);
	   				System.out.println(priority);
	   				System.out.println(objetivesmg.toString());
	   				System.out.println(objetivesaction.toString());
	   				System.out.println(objetives2mg.toString());
	   				System.out.println(objetivesaction2.toString());
	   				
	   				ObjetivesMG omg = new ObjetivesMG(key,priority,0,value,descripcion,ObjetiveType.valueOf(status.toUpperCase()));
	   				l.add(omg);
	   		}}
	   		
	   		if(l.size() > 1) {
   				if(l.stream().filter(o -> o.getNombre().equals("Mapa Con Objetivos Borrados")).findFirst().isPresent()) {
   					l.remove(ghost);
   				}
   		     }
	   		
   		}
   		
   		
   		GameObjetivesMG go = new GameObjetivesMG(l);
   		for(ObjetivesMG o : go.getObjetives()) {
   			System.out.println(o.getNombre()+" "+o.getPriority()+" "+o.getValueinitial()+" "+o.getValuereferencial()+" "+o.isStatus().toString());
   		}
   	
   		
   		return;
   	}
 	
 	public void isCompleteAllPrimaryObjetiveForReward(String map) {
 		GameInfo gi = plugin.getGameInfoPoo().get(map);
   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);
   				if(obj.getPriority() == 1 && obj.isStatus() != ObjetiveType.COMPLETE) {
   					p.add(obj);
   				}
   				
   			}
   		}
   		
   		if(!p.isEmpty()) {
   			return;
   		}
   		
			FileConfiguration game = getGameConfig(map);
			List<String> rewardpm = game.getStringList("Complete-All-Objetives-Primary.Message");
		  	List<String> rewardp = game.getStringList("Complete-All-Objetives-Primary.Rewards");
		  	
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
				
				
				for(Player player : players) {
					if(!rewardpm.isEmpty()) {
					   for(String text : rewardpm) {
	   	   				player.sendMessage(ChatColor.translateAlternateColorCodes('&',text.replace("%player%", player.getName())));
					}}
	   				
					ExecuteMultipleCommandsInConsole(player, rewardp);
				}
			}
			
		  	
   		
   		return;
 	}
 	
 	public void isCompleteAllSecondaryObjetiveForReward(String map) {
 		GameInfo gi = plugin.getGameInfoPoo().get(map);
   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);
   				if(obj.getPriority() >= 2 && obj.isStatus() != ObjetiveType.COMPLETE) {
   					p.add(obj);
   				}
   				
   			}
   		}
   		
   		if(!p.isEmpty()) {
   			return;
   		}
   		
   		
			FileConfiguration game = getGameConfig(map);
			List<String> rewardpm = game.getStringList("Complete-All-Objetives-Secondary.Message");
		  	List<String> rewardp = game.getStringList("Complete-All-Objetives-Secondary.Rewards");
		  	
		  	
		  	if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
				
				for(Player player : players) {
					if(!rewardpm.isEmpty()) {
					   for(String text : rewardpm) {
	   	   				player.sendMessage(ChatColor.translateAlternateColorCodes('&',text.replace("%player%", player.getName())));
					}}
	   				
					ExecuteMultipleCommandsInConsole(player, rewardp);
				}
		  	}
			
					
					
				
			
		  
   		
   		return;
 	}
 	public boolean isAllPrimaryObjetivesComplete(Player player ,String map) {
   		GameInfo gi = plugin.getGameInfoPoo().get(map);
   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);
   				if(obj.getPriority() == 1 && obj.isStatus() != ObjetiveType.COMPLETE) {
   					p.add(obj);
   				}
   				
   			}
   		}
   		
   		if(!p.isEmpty()) {
   			for(int i = 0 ; i < p.size();i++) {
   				player.sendMessage(ChatColor.RED+"El Objetivo Primario "+p.get(i).getNombre()+" no esta Completado "+p.get(i).getValueinitial()+"/"+p.get(i).getValuereferencial());
   			}
   			return false;
   		}
   		
 		
 		return true;
 	}
 	
 	
	public boolean isAllSecondaryObjetivesComplete(Player player ,String map) {
   		GameInfo gi = plugin.getGameInfoPoo().get(map);
   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);
   				if(obj.getPriority() >= 2 && obj.isStatus() != ObjetiveType.COMPLETE) {
   					p.add(obj);
   				}
   				
   			}
   		}
   		
   		if(!p.isEmpty()) {
   			for(int i = 0 ; i < p.size();i++) {
   				player.sendMessage(ChatColor.RED+"El Objetivo Secundario "+p.get(i).getNombre()+" no esta Completado "+p.get(i).getValueinitial()+"/"+p.get(i).getValuereferencial());
   			}
   			return false;
   		}
   		
 		
 		return true;
 	}
   	
	public void CompletePrimaryObjetive(String map, String name ,int value) {
	
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Primario "+name+" no Existe");
			return;
		}
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() == ObjetiveType.COMPLETE) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Primario "+name+" esta Completo.");
				return;
			}
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() != ObjetiveType.COMPLETE) {
				int val =  l.get(i).getValueinitial();
				int valr = l.get(i).getValuereferencial();
				int total = val + value;
				
				if(total >= valr) {
					
					SendMessageToAllPlayersInGame(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+l.get(i).getNombre()+ChatColor.GREEN+" a sido Completado "+ChatColor.GOLD+valr+"/"+valr);

					
					FileConfiguration game = getGameConfig(map);
					l.get(i).setStatus(ObjetiveType.COMPLETE);
					l.get(i).setValueinitial(valr);
					
			   		List<String> objetivesmg = game.getStringList("Game-Objetives."+name+".ObjetiveCompleteMessage");
			   		List<String> objetivesaction = game.getStringList("Game-Objetives."+name+".ObjetiveCompleteActions");
			   		
			   		
			   		if(gi instanceof GameAdventure) {
						GameAdventure ga = (GameAdventure) gi;
						List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
						for(Player p : players) {
				   			if(!objetivesmg.isEmpty()) {
				   				p.sendMessage(ChatColor.GREEN+"Objetivo Primario "+ChatColor.GOLD+name+ChatColor.GREEN+" fue Completado.");
				   				for(String text : objetivesmg) {
				   	   				p.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
				   				}
				   			}
				   			
				   			ExecuteMultipleCommandsInConsole(p,objetivesaction);
				   			
				   		}
						
			   		}
					
					isCompleteAllPrimaryObjetiveForReward(map);
				
					return ;
				}else {
					
					l.get(i).setValueinitial(total);
					SendMessageToAllPlayersInGame(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+l.get(i).getNombre()+ChatColor.GREEN+" a Aumentado "+ChatColor.GOLD+total+"/"+l.get(i).getValuereferencial());

					return ;
				}
				
			}
		}
	
		return ;	
	}
	
	
	public void CompleteSecondaryObjetive(String map, String name ,int value) {
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Secundario "+name+" no Existe");
			return;
		}
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() == ObjetiveType.COMPLETE) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Secundario "+name+" esta Completo.");
				return;
			}
			
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() != ObjetiveType.COMPLETE) {
				int val =  l.get(i).getValueinitial();
				int valr = l.get(i).getValuereferencial();
				int total = val + value;
				
				if(total >= valr) {
					
					SendMessageToAllPlayersInGame(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+l.get(i).getNombre()+ChatColor.GREEN+" a sido Completado "+ChatColor.GOLD+valr+"/"+valr);

					FileConfiguration game = getGameConfig(map);
					l.get(i).setStatus(ObjetiveType.COMPLETE);
					l.get(i).setValueinitial(valr);
					
			   		List<String> objetivesmg = game.getStringList("Game-Objetives."+name+".ObjetiveCompleteMessage");
			   		List<String> objetivesaction = game.getStringList("Game-Objetives."+name+".ObjetiveCompleteActions");
			   		
			   		
			   		if(gi instanceof GameAdventure) {
						GameAdventure ga = (GameAdventure) gi;
						List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
						
						for(Player p : players) {
				   			if(!objetivesmg.isEmpty()) {
				   				p.sendMessage(ChatColor.GREEN+"Objetivo Secundario "+ChatColor.GOLD+name+ChatColor.GREEN+" fue Completado.");
				   				for(String text : objetivesmg) {
				   	   				p.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
				   				}
				   			}
				   			
				   			ExecuteMultipleCommandsInConsole(p,objetivesaction);
				   			
				   		}
			   		}
					
					isCompleteAllSecondaryObjetiveForReward(map);
					
					
					return;
				}else {
				
					l.get(i).setValueinitial(total);
					SendMessageToAllPlayersInGame(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+l.get(i).getNombre()+ChatColor.GREEN+" a Aumentado "+ChatColor.GOLD+total+"/"+l.get(i).getValuereferencial());
					return ;
			    }
			 
			
			}
		}
			return ;
	}
	
	public void IncompletePrimaryObjetive(String map, String name) {
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Primario "+name+" Incomplete no Existe");
			return;
		}
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() == ObjetiveType.INCOMPLETE) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Primario "+name+" ya esta Incompleto.");
				return;
			}
			
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() != ObjetiveType.INCOMPLETE) {
				FileConfiguration game = getGameConfig(map);
				l.get(i).setStatus(ObjetiveType.INCOMPLETE);
				
					
			   		List<String> objetives2mg = game.getStringList("Game-Objetives."+name+".ObjetiveIncompleteMessage");
					List<String> objetivesaction2 = game.getStringList("Game-Objetives."+name+".ObjetiveIncompleteActions");
			   		
					if(gi instanceof GameAdventure) {
						GameAdventure ga = (GameAdventure) gi;
						List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
				 		for(Player p : players) {
				   			if(!objetives2mg.isEmpty()) {
				   				p.sendMessage(ChatColor.RED+"Objetivo Primario "+ChatColor.GOLD+name+ChatColor.RED+" esta Incompleto.");
				   				for(String texto : objetives2mg) {
				   	   				p.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
				   	   			}
				   			}
				   			
				   			ExecuteMultipleCommandsInConsole(p,objetivesaction2);
				   		}
					}
					
			  
	
				return;
			}
		}
	
			
	}
	
	public void IncompleteSecondaryObjetive(String map, String name) {
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Secundario "+name+" Incomplete no Existe");
			return;
		}
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() == ObjetiveType.INCOMPLETE) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Secundario "+name+" ya esta Incompleto.");
				return;
			}
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() != ObjetiveType.INCOMPLETE) {
				
				FileConfiguration game = getGameConfig(map);
				l.get(i).setStatus(ObjetiveType.INCOMPLETE);
		   		List<String> objetives2mg = game.getStringList("Game-Objetives."+name+".ObjetiveIncompleteMessage");
		   		List<String> objetivesaction2 = game.getStringList("Game-Objetives."+name+".ObjetiveIncompleteActions");
		   		
		   		
		   		if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
					for(Player p : players) {
			   			if(!objetives2mg.isEmpty()) {
			   				p.sendMessage(ChatColor.RED+"Objetivo Secundario "+ChatColor.GOLD+name+ChatColor.RED+" esta Incompleto.");
			   				for(String texto : objetives2mg) {
			   	   				p.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
			   	   			}
			   			}
			   			
			   			ExecuteMultipleCommandsInConsole(p,objetivesaction2);
			   		}
		   		
		   		}
		   		
		   		
		   		
		   		
		   		
		   		
				
				return;
			}
		}
	
			
	}
	
	
	public void WaitingPrimaryObjetive(String map, String name) {
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Primario "+name+" Wait no Existe");
			return;
		}
		
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() == ObjetiveType.WAITING) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Primario "+name+" ya esta Esperando.");
				return;
			}
			
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() != ObjetiveType.WAITING) {
			
				l.get(i).setStatus(ObjetiveType.WAITING);
				
				
				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					 List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
					  for(Player p : players) {
						  p.sendMessage(ChatColor.GREEN+"Estatus de Objetivo "+name+" Actualizado a Esperando.");
					  }
				}
				
				
	
				return;
			}
		}
	
			
	}
	
	public void WaitingSecondaryObjetive(String map, String name) {
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Secundario "+name+" Wait no Existe");
			return;
		}
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() == ObjetiveType.WAITING) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Secundario "+name+" ya esta Esperando.");
				return;
			}
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() != ObjetiveType.WAITING) {
				
				l.get(i).setStatus(ObjetiveType.WAITING);
				
				
				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
					  for(Player p : players) {
						  p.sendMessage(ChatColor.GREEN+"Estatus de Objetivo "+name+" Actualizado a Esperando.");
					  }
				}
			
		
				return;
			}
		}
	
			
	}
	
	public void UnknowPrimaryObjetive(String map, String name) {
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Primario "+name+" Unknow no Existe");
			return;
		}
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() == ObjetiveType.UNKNOW) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Primario "+name+" ya esta Unknow.");
				return;
			}
			
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() == 1 && l.get(i).isStatus() != ObjetiveType.UNKNOW) {
			
				l.get(i).setStatus(ObjetiveType.UNKNOW);
				
				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
					  for(Player p : players) {
						  p.sendMessage(ChatColor.GREEN+"Estatus de Objetivo "+name+" Actualizado a Unknow.");
					  }
				}
				
				
				
	
				return;
			}
		}
	
			
	}
	
	public void UnknowSecondaryObjetive(String map, String name) {
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		
		if(!l.stream().filter(o -> o.getNombre().equals(name)).findFirst().isPresent()) {
			SendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo Secundario "+name+" Unknow no Existe");
			return;
		}
		
		for(int i = 0; i<l.size();i++) {
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() == ObjetiveType.UNKNOW) {
				SendMessageToUserAndConsole(null,ChatColor.GREEN+"El Objetivo Secundario "+name+" ya esta Unknow.");
				return;
			}
			
			if(l.get(i).getNombre().equals(name) && l.get(i).getPriority() >= 2 && l.get(i).isStatus() != ObjetiveType.UNKNOW) {
				
				l.get(i).setStatus(ObjetiveType.UNKNOW);
				
				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					List<Player> players = ConvertStringToPlayer(ga.getParticipantes());
					  for(Player p : players) {
						  p.sendMessage(ChatColor.GREEN+"Estatus de Objetivo "+name+" Actualizado a Unknow.");
					  }
				}
				
			
		
				return;
			}
		}
	
			
	}
	
	
   
   	//mg objetive-primary complete 1
    //mg objetive-secondary complete 1
	//mg objetive-primary incomplete 1
    //mg objetive-secondary incomplete 1
	//X ! >⨉  ?  ✔

//	
	public int getAmountOfObjetivesComplete(List<ObjetivesMG> l) {
		int actual = 0;
   		List<String> complete = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0;i<l.size();i++) {
   				if(l.get(i).isStatus() == ObjetiveType.COMPLETE) {
   					complete.add(l.get(i).getNombre());
   		  }}}
   		
   		actual = complete.size();
   		
   		return actual ;
	}
	
	//TODO STRING TO PLAYER
	public List<Player> ConvertStringToPlayer(List<String> l1){
		List<Player> l = new ArrayList<>();
		for(int i = 0;i<l1.size();i++) {
   			Player user = Bukkit.getServer().getPlayerExact(l1.get(i));
   			l.add(user);
   		}
		return l ;
	}
	
	public void ExecuteMultipleCommandsInConsole(Player player,List<String> l) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if(!l.isEmpty()) {
			for(int i = 0 ; i < l.size(); i++) {
				String texto = l.get(i);
				
				Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName())));
				
			}
		}
	}
	
	public void ExecuteMultipleCommandsInConsoleOnly(List<String> l) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if(!l.isEmpty()) {
			for(int i = 0 ; i < l.size(); i++) {
				String texto = l.get(i);
				
				Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto));
				
			}
		}
	}
	
	public void LoadDialogues(String nameyml ,String id,String  map) {
		FileConfiguration dialogue = getDialogueConfig(nameyml);
		if(dialogue.contains(id)) {
			int repeattime = dialogue.getInt(id+".Time");
			boolean iscomplete = dialogue.getBoolean(id+".isRepetitive");
			List<String> l = dialogue.getStringList(id+".Message-Actions-Server");
			GameDialogs gd = new GameDialogs(map,id,repeattime,iscomplete,l);
			DialogRun dr = new DialogRun(plugin,gd);
			dr.StartDialog();
		}else {
			System.out.println("No existe "+id);
		}
		
		
	}
   	
	public void LoadDialoguesDebug(Player player,String nameyml ,String id,String  map) {
		FileConfiguration dialogue = getDialogueConfig(nameyml);
		if(dialogue.contains(id)) {
			int repeattime = dialogue.getInt(id+".Repeat");
			boolean iscomplete = dialogue.getBoolean(id+".isRepetitive");
			List<String> l = dialogue.getStringList(id+".Message-Actions-Server");
			GameDialogs gd = new GameDialogs(map,id,repeattime,iscomplete,l);
			DialogRun dr = new DialogRun(plugin,gd);
			dr.StartDialogDebug(player);
		}
		
		
	}
	
	public void getInventorySing(String name , Player player) {
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
		player.sendMessage(ChatColor.GREEN+"Obtuviste el Kit "+ChatColor.GOLD+name+ChatColor.GREEN+" para avanzar en tu Aventura.");
//	player.sendMessage(ChatColor.GREEN+"Obtuviste la clase "+ChatColor.RED+name);
	}
	
	
	public Player ConvertStringToPlayer(String name) {
		
		return Bukkit.getPlayerExact(name);
	}
	
	//TODO NEXO
	
	public Location RedNexo(String name) {
		   FileConfiguration ym = getGameConfig(name);
		   if(!ym.contains("Red-Nexo")) {
			   
			return null;   
		   }
			    String[] coords = ym.getString("Red-Nexo").split("/");
			    String world = coords[0];
			    double x = Double.valueOf(coords[1]);
			    double y = Double.valueOf(coords[2]);
			    double z = Double.valueOf(coords[3]);
				Location l = new Location(Bukkit.getWorld(world), x, y, z);

					return l;
	}
	
	public Location BlueNexo(String name) {
		   FileConfiguration ym = getGameConfig(name);
		   if(!ym.contains("Blue-Nexo")) {
			   
			return null;   
		   }
			    String[] coords = ym.getString("Blue-Nexo").split("/");
			    String world = coords[0];
			    double x = Double.valueOf(coords[1]);
			    double y = Double.valueOf(coords[2]);
			    double z = Double.valueOf(coords[3]);
				Location l = new Location(Bukkit.getWorld(world), x, y, z);

			return l;
	}
	

	public void TptoSpawnTeam(Player player) {
		Team t = plugin.BlueNexo();
		if(t.hasEntry(player.getName())){
			
		}
	}
	


	
	public void Team1Win() {
		
	}
	
	public void Team2Win() {
		
	}
	
	public void TeamsDraw() {
		
	}
	
	
}