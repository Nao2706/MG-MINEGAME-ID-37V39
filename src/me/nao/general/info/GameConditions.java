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
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;

import me.nao.cooldown.mg.ReportsManager;
import me.nao.enums.GameInteractions;
import me.nao.enums.GameStatus;
import me.nao.enums.GameType;
import me.nao.enums.Items;
import me.nao.enums.ObjetiveStatusType;
import me.nao.enums.StopMotivo;
import me.nao.main.mg.Minegame;
import me.nao.manager.GameIntoMap;
import me.nao.scoreboard.MgScore;
import me.nao.teamsmg.MgTeams;
import me.nao.timers.DialogRun;
import me.nao.timers.InfectedTemp;
import me.nao.timers.NexoTemp;
import me.nao.timers.AdventureTemp;
import me.nao.timers.ResistenceTemp;
import me.nao.utils.Utils;
import me.nao.yamlfile.mg.YamlFilePlus;
import me.top.users.mg.PointsManager;


public class GameConditions {
	
	private Minegame plugin;
	
	public GameConditions(Minegame plugin) {
		this.plugin = plugin;
		
	}
	
	
	//TODO JOIN
	 
	public void mgJoinToTheGames(Player player,String map) {
		loadDataMap(map);
		
		if(CanJoinToTheMap(player,map)){
			
			if(ExistProblemBetweenInventorys(map)) {
				player.sendMessage(ChatColor.RED+"Error conflicto de inventarios llama a un Administrador.");
				return;
			}
				//Salva al Jugador checa si debe setearle un inv
				setAndSavePlayer(player, map);
				addPlayerToGame(player,map);
				tptoPreLobbyMap(player, map);
				canStartTheGame(player,map);
				return;
		}
		
	}
	 
	  
	//TODO LEAVE
	public void mgLeaveOfTheGame(Player player) {
		  
		if(!isPlayerinGame(player)) {
			player.sendMessage(ChatColor.RED+"No estas en Ningun Juego.");
			return;
		}
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		FileConfiguration mision = getGameConfig(pl.getMapName());
		GameInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		
		GameStatus part = ms.getGameStatus();
		MgScore sco = new MgScore(plugin);
		sco.ClearScore(player);
		
		if(ms instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) ms;
			List<String> spectador = ga.getSpectators();
			List<String> join = ga.getParticipants();
			
			
			if(part == GameStatus.ESPERANDO || part == GameStatus.COMENZANDO) {
				if(join.size() < getMinPlayerMap(pl.getMapName())) {
					
					 
					 int min1 =  getMinPlayerMap(pl.getMapName());
					 
					 min1 = min1 - join.size();
					  
					 if(min1 == 1) {
						 sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
					 }else {
						 sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");

						
			 }}}
			 
			
			if(spectador.contains(player.getName())) {
				sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.WHITE+"El jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" salio del Modo Espectador."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+(spectador.size() - 1)+ChatColor.RED+"]");
				ga.getBossbar().removePlayer(player);
			}else {
				sendMessageToUsersOfSameMapLessPlayer(player,
							ChatColor.YELLOW+"A Salido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+(ga.getParticipants().size()-1)+ChatColor.YELLOW+"/"+ChatColor.GOLD+getMaxPlayerMap(pl.getMapName())+ChatColor.RED+")");			
			}
			
			
			
			
			 String mt = mision.getString("Start.Tittle-of-Mision"); 
			 player.sendMessage(ChatColor.GREEN+"Has salido del Mapa "+ChatColor.translateAlternateColorCodes('&',mt.replaceAll("%player%",player.getName())));
			 RestorePlayer(player);
			
		}else if(ms instanceof GameNexo) {
			GameNexo gn = (GameNexo) ms;
			List<String> spectador = gn.getSpectators();
			List<String> join = gn.getParticipants();
			if(part == GameStatus.ESPERANDO || part == GameStatus.COMENZANDO) {
				if(join.size() < getMinPlayerMap(pl.getMapName())) {
					
					 
					 int min1 =  getMinPlayerMap(pl.getMapName());
					 
					 min1 = min1 - join.size();
					  
					 if(min1 == 1) {
						 sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
					 }else {
						 sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");

			}}}			
			
			
			
			
			if(spectador.contains(player.getName())) {
				sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.WHITE+"El jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" salio del Modo Espectador."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+(spectador.size() - 1)+ChatColor.RED+"]");
			
			}else {
				sendMessageToUsersOfSameMapLessPlayer(player,
							ChatColor.YELLOW+"A Salido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+(gn.getParticipants().size()-1)+ChatColor.YELLOW+"/"+ChatColor.GOLD+getMaxPlayerMap(pl.getMapName())+ChatColor.RED+")");			
			}
			 String mt = mision.getString("Start.Tittle-of-Mision"); 
			 player.sendMessage(ChatColor.GREEN+"Has salido del Mapa "+ChatColor.translateAlternateColorCodes('&',mt.replaceAll("%player%",player.getName())));
			 RestorePlayer(player);
		}
		
	
		//player.sendMessage("Saliste.");
		
	}
	
	
	public void mgLeaveMapCommandIlegal(Player player) {
		
		if(!isPlayerinGame(player)) return;
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameAdventure ga = (GameAdventure) plugin.getGameInfoPoo().get(pl.getMapName());
		if(CanJoinWithYourInventory(pl.getMapName()) && !ga.getSpectators().contains(player.getName())) {
			
			if(ga.getDeadPlayers().contains(player.getName())) {
				mgLeaveOfTheGame(player);
			}else{
				Block block = player.getLocation().getBlock();
				Block b = block.getRelative(0, -2, 0);
				if(b.getType() != Material.STRUCTURE_BLOCK) {
					 player.sendMessage(ChatColor.YELLOW+"Debes estar dentro de una Zona Segura para salirte.");
					 player.sendMessage(ChatColor.RED+"Si te Desconectas fuera de una Zona segura tu Inventario se Borrara.");
					 
				}else {
					mgLeaveOfTheGame(player);
				}
				return;
			}
		}else {
			mgLeaveOfTheGame(player);
		}
		return;
	}
	
	
	
	public void LeaveMapConexionIlegal(Player player) {
		
		if(!isPlayerinGame(player)) return;
	
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameAdventure ga = (GameAdventure) plugin.getGameInfoPoo().get(pl.getMapName());
		if(CanJoinWithYourInventory(pl.getMapName()) && !ga.getSpectators().contains(player.getName())) {
			
			if(ga.getDeadPlayers().contains(player.getName())) {
				mgLeaveOfTheGame(player);
			
			}else{
				Block block = player.getLocation().getBlock();
				Block b = block.getRelative(0, -2, 0);
				if(b.getType() != Material.STRUCTURE_BLOCK) {
					GameIntoMap ci = new GameIntoMap(plugin);
					ci.PlayerDropAllItems(player);
					mgLeaveOfTheGame(player);
				
					 return;
				}else{
					mgLeaveOfTheGame(player);
				}
				
			}
		}else{
			mgLeaveOfTheGame(player);
		}
	
		return;
	}
	
	
	
	public void mgEndTheGame(String  name) {
		GameInfo gi = plugin.getGameInfoPoo().get(name);
		
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			List<Player> player = ConvertStringToPlayer(ga.getParticipants());
			MgScore sco = new MgScore(plugin);
		
			for(Player target : player) {
				sco.ClearScore(target);
				RestorePlayer(target);
			}
			
			List<Player> spec = ConvertStringToPlayer(ga.getSpectators());
			
			for(Player target : spec) {
				sco.ClearScore(target);
				RestorePlayer(target);
			}
			
				checkGenerator(gi);
				System.out.println("LOG END GAME RESULT: "+plugin.getGameInfoPoo().get(name).ShowGame());
				System.out.println("ENTIDADES MARCADAS: "+plugin.getEntitiesFromFlare().size());
				plugin.getEntitiesFromFlare().remove(name);
				plugin.getGameInfoPoo().remove(name);
				System.out.println("LOG MAP OF GAMES: "+plugin.getGameInfoPoo().toString());

		}
		
	}
	
	
	 //TODO TP AL PRELOBBY DEL MAPA
	   public void tptoPreLobbyMap(Player player ,String map){
		   FileConfiguration ym = getGameConfig(map);
		   if(ym.contains("Pre-Lobby")) {
			    System.out.println("El jugador "+player.getName()+" fue hacia el prelobby con exito");
			    String[] coords = ym.getString("Pre-Lobby").split("/");
			    String world = coords[0];
			    double x = Double.valueOf(coords[1]);
			    double y = Double.valueOf(coords[2]);
			    double z = Double.valueOf(coords[3]);
			    float yaw = Float.valueOf(coords[4]);
			    float pitch = Float.valueOf(coords[5]);

				player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, player.getLocation().add(0, 1, 0),
						/* NUMERO DE PARTICULAS */20, 5, 5, 5, /* velocidad */0, null, true);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
				Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
				player.setInvulnerable(true);
				player.teleport(l);
				return;
			} 
	   }
	   
	   //TODO TpEndSpawn
	   public void EndTptoSpawn(Player player ,String arenaName){
		   
		   GameConditions gc = new GameConditions(plugin);
		   FileConfiguration ym = gc.getGameConfig(arenaName);
		   if(ym.contains("Spawn-End")) {
			    String[] coords = ym.getString("Spawn-End").split("/");
			    String world = coords[0];
			    double x = Double.valueOf(coords[1]);
			    double y = Double.valueOf(coords[2]);
			    double z = Double.valueOf(coords[3]);
			    float yaw = Float.valueOf(coords[4]);
			    float pitch = Float.valueOf(coords[5]);
			   
			    player.setInvulnerable(false);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
				Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
				Random r = new Random();
				
				List<String> point1 = new ArrayList<>();
				point1.add(ChatColor.GREEN+"Por poco y no la cuentas menos mal saliste en una Pieza.");
				point1.add(ChatColor.GREEN+"Sobreviviste felicidades por tu Victoria");
				point1.add(ChatColor.GREEN+"Sobreviviste un dia mas para Luchar Felicidades");
				point1.add(ChatColor.GREEN+"Puede que seas de los pocos con Vida sigue asi.");
				
				player.sendMessage(point1.get(r.nextInt(point1.size())));
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
	   
	    
	   public void TptoSpawnMapSimple(Player player){
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
	   public void TptoSpawnMap(Player player ,String map){
		   GameConditions gc = new GameConditions(plugin);
		   FileConfiguration ym = gc.getGameConfig(map);
		   if(ym.contains("Spawn")) {
			   
			   
			   GameInfo gm = plugin.getGameInfoPoo().get(map);
			   GameObjetivesMG gomg = gm.getGameObjetivesMg();
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
			    	  
			    	    @SuppressWarnings("deprecation")
						Sound soundtype = Sound.valueOf(sts[0].toUpperCase());
					    Float volumen = Float.valueOf(sts[1]);
					    Float grade = Float.valueOf(sts[2]);
						player.playSound(player.getLocation(), soundtype, volumen,grade);
			       }catch(IllegalArgumentException e) {
			    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error de Argumentos en Start Sound of Mision en el Mapa: "+ChatColor.GOLD+map);
			       }
			
			    player.setInvulnerable(false);
				Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
				player.teleport(l);
				gc.SetHeartsInGame(player, map);
				//este send message es un separador de chat contra la cuenta atras xd
				player.sendMessage(" ");
				
				if(!startc.isEmpty()) {
					for(int i = 0 ; i< startc.size();i++) { 
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',DifficultyMap(startc.get(i)).replaceAll("%player%", player.getName())));
					}
				}
				
				
				player.sendTitle(ChatColor.translateAlternateColorCodes('&',ym.getString("Start.Tittle-of-Mision").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',DifficultyMap(ym.getString("Start.SubTittle-of-Mision")).replaceAll("%player%", player.getName())), a,b,c);
				
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				if(!start.isEmpty()) {
					for(int i = 0 ; i < start.size(); i++) {
						String texto = start.get(i);
						if(!gc.hasPlayerPermissionByLuckPerms(player, texto)) continue;
						Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName())));
					}
				}
				
			     	 
					gc.setKitMg(player);
					if(gomg.hasMapObjetives()) {
						if(player.getInventory().getItemInMainHand() != null) {
							if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
								player.getInventory().setItemInMainHand(Items.OBJETIVOSP.getValue());
								
							}else {
								ItemStack item = player.getInventory().getItemInMainHand();
								player.getWorld().dropItem(player.getLocation(),item);
								player.getInventory().setItemInMainHand(Items.OBJETIVOSP.getValue());
							}
							
						}
					}
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
	
	public void ForceGameModePlayerRol(Player player) {
		
		if(isPlayerKnocked(player)) return;
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo game = plugin.getGameInfoPoo().get(mapa);
		if(game instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) game;
			
			List<String> vivo = ga.getAlivePlayers();
			List<String> arrivo = ga.getArrivePlayers();
			List<String> spectador = ga.getSpectators();
			List<String> deads = ga.getDeadPlayers();
			
			
			
				if(vivo.contains(player.getName()) && !arrivo.contains(player.getName())) {
					if(player.getGameMode() != GameMode.ADVENTURE) {
						player.setGameMode(GameMode.ADVENTURE);
					}
				}else if(deads.contains(player.getName())) {
					if(player.getGameMode() != GameMode.SPECTATOR) {
						player.setGameMode(GameMode.SPECTATOR);
					}
				}else if(spectador.contains(player.getName())) {
					if(player.getGameMode() != GameMode.SPECTATOR) {
						player.setGameMode(GameMode.SPECTATOR);
					}
				}
			
			
		
			
		}
		
	
	}
	
	public void forceGameStart(Player player,String map) {
		
		if(!ExistMap(map)) {
			player.sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe o esta mal escrito.");

		}
		
		if(!isMapinGame(map)) {
			player.sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" no esta en Juego.");
			return;
		}
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		
		if(gi.getGameStatus() == GameStatus.ESPERANDO) {
			 SendMessageToAllUsersOfSameMap(player, ChatColor.GREEN+"\nSe a Forzado a Comenzar el Juego.\n"+ChatColor.GOLD+"La partida Comenzara en: "+ChatColor.RED+gi.getCountDownStart()+ChatColor.GREEN+" segundos.\n ");
			 if(gi.getGameType() == GameType.ADVENTURE) {
					AdventureTemp t = new AdventureTemp(plugin);
					t.Inicio(gi.getMapName());
				}else if(gi.getGameType() == GameType.RESISTENCE) {
					ResistenceTemp t = new ResistenceTemp(plugin);
					t.Inicio(gi.getMapName());
				}
		}else {
			player.sendMessage(ChatColor.RED+"La partida debe estar en Estatus de Esperando para Forzar un Inicio.");
		}
		
	}
	
	public void TryToScapeInSpectatorMode(Player player) {
		PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
		Block block = player.getLocation().getBlock();
		Block b = block.getRelative(0, 0, 0);
		
		if(b.getType() == Material.BARRIER && player.getGameMode() == GameMode.SPECTATOR) {
			
			TptoSpawnSpectator(player, pi.getMapName());
			
			Random r = new Random();
			
			List<String> l = new ArrayList<>();
			l.add(ChatColor.RED+"Oh una Barrera no puedes salir del Mapa...");
			l.add(ChatColor.RED+"Creo que no deberias ir por alli...");
			l.add(ChatColor.RED+"Y si pruebas siguiendo a un Jugador , bueno si es que no estas solo...");
			l.add(ChatColor.RED+"Vaya parece que han colocado una Barrera por algo...");
			
			player.sendMessage(l.get(r.nextInt(l.size())));
		}
		
		
		
	}
	

	

	public String DifficultyMap(String text) {
		
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
	
	
	
	
	public void addPlayerToGame(Player player ,String mision) {
	
			GameInfo mis = plugin.getGameInfoPoo().get(mision);
			if(mis instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) mis;
				if(!ga.getParticipants().contains(player.getName())) {
					ga.getParticipants().add(player.getName());
					ga.getAlivePlayers().add(player.getName());
				}
			}
	}
	
	//TODO SIN USO
	public void revivePlayerToGame(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getAlivePlayers().contains(player.getName())) {
				ga.getAlivePlayers().add(player.getName());
			}
			if(ga.getDeadPlayers().remove(player.getName()));
		}
	}
	
	//TODO SIN USO
	public void deadPlayerToGame(Player player ,String mision) {
		
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getDeadPlayers().contains(player.getName())) {
				ga.getDeadPlayers().add(player.getName());
			}
			if(ga.getAlivePlayers().remove(player.getName()));
		}
	}
	
	public void spectatorAddToGame(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getSpectators().contains(player.getName())) {
				ga.getSpectators().add(player.getName());
			}
		}
	}
	
	public void playerArriveToTheWin(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(!ga.getArrivePlayers().contains(player.getName())) {
				ga.getArrivePlayers().add(player.getName());
			}
		}
	}
	
	 
	public void removeAllPlayerToGame(Player player ,String mision) {
		
		GameInfo mis = plugin.getGameInfoPoo().get(mision);
		if(mis instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) mis;
			if(mis.getGameType() == GameType.ADVENTURE || mis.getGameType() == GameType.RESISTENCE ) {
				MgTeams t = new MgTeams(plugin);
			
				if(ga.getParticipants().remove(player.getName()));
				if(ga.getAlivePlayers().remove(player.getName()));
				if(ga.getDeadPlayers().remove(player.getName()));
				if(ga.getArrivePlayers().remove(player.getName()));
				if(ga.getSpectators().remove(player.getName()));
				plugin.CreditKill().remove(player);
				plugin.getPlayerInfoPoo().remove(player);
				plugin.getCheckPoint().remove(player);
				t.RemoveAllPlayer(player);
			}
		}else if(mis instanceof GameNexo) {
			GameNexo ga = (GameNexo) mis;
			if(ga.getGameType() == GameType.NEXO ) {
				if(ga.getParticipants().remove(player.getName()));
				if(ga.getSpectators().remove(player.getName()));
//				if(ga.getBlueTeamMg().remove(player.getName()))
//				if(ga.getRedTeamMg().remove(player.getName()))
				plugin.getPlayerInfoPoo().remove(player);
			}
		}
	}
	
	public void playerWinnerReward(Player player) {
		
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String name = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(name);
		
		if(ms instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) ms;
			if(ms.getGameType() == GameType.ADVENTURE) {
				List<String> arrivo = ga.getArrivePlayers();
				List<String> spectador = ga.getSpectators();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(!arrivo.contains(player.getName())) {
					return;
				}
			}
			
			if(ms.getGameType() == GameType.RESISTENCE) {
				List<String> vivo = ga.getAlivePlayers();
				List<String> spectador = ga.getSpectators();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(!vivo.contains(player.getName())) {
					return;
				}
			}
		}	
		
		
		if(isMapRanked(name)) {
			PointsManager pm = new PointsManager(plugin) ;
			pm.WinGamePoints(player);
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
	    	   @SuppressWarnings("deprecation")
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
						if(!hasPlayerPermissionByLuckPerms(player,texto)) continue;
						Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(RewardPointsForItems(puntaje)))));
						
				      }
				}
		  }
	  
		  return;
		   
		
		
	}
	
	public void playerLoserReward(Player player) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String name = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(name);
		
		
		if(ms instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) ms;
			if(ms.getGameType() == GameType.ADVENTURE) {
				List<String> arrivo = ga.getArrivePlayers();
				List<String> spectador = ga.getSpectators();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(arrivo.contains(player.getName())) {
					return;
				}
			}
			
			
			if(ms.getGameType() == GameType.RESISTENCE) {
				List<String> vivo = ga.getAlivePlayers();
				List<String> spectador = ga.getSpectators();
				
				if(spectador.contains(player.getName())) {
					return;
				}
				if(vivo.contains(player.getName())) {
					return;
				}
			}
		
		}
		
	
		if(isMapRanked(name)) {
			PointsManager pm = new PointsManager(plugin) ;
			pm.LoseGamePoints(player);
		}
	
		FileConfiguration mision = getGameConfig(name);
		List<String> lostreward = mision.getStringList("Lost-Rewards.Commands");
		List<String> lost = mision.getStringList("Lost.Chat-Message-Lost");
		int puntaje = pl.getGamePoints().getKills();
		
		
		  String[] lostt = mision.getString("Lost.Tittle-Time").split("-");
	       int al = Integer.valueOf(lostt[0]);
	       int apoint2 = Integer.valueOf(lostt[1]);
	       int al3 = Integer.valueOf(lostt[2]);
	       
		   String[] sts = mision.getString("Lost.Sound-of-Lost").split(";");
	       try {
	    	  
			   @SuppressWarnings("deprecation")
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
	      
	       
		   player.sendTitle(ChatColor.translateAlternateColorCodes('&',mision.getString("Lost.Tittle-of-Lost").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',mision.getString("Lost.SubTittle-of-Lost").replaceAll("%player%", player.getName())), al,apoint2,al3);
		   if(!lostreward.isEmpty()) {
			   for(int i = 0 ; i < lostreward.size(); i++) {
					String texto = lostreward.get(i);
					if(!hasPlayerPermissionByLuckPerms(player,texto)) continue;
					Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName()).replaceAll("%points%",String.valueOf(RewardPointsForItems(puntaje)))));

			}}
	}
	
	public void startGameActions(String map) {
		FileConfiguration mision = getGameConfig(map);
		
		List<String> start = mision.getStringList("Start-Console.Commands");
		if(!start.isEmpty()) {
			ExecuteMultipleCommandsInConsole(null,start);
		}
	}
	
	public void endGameActions(String map) {
		FileConfiguration mision = getGameConfig(map);
		
		List<String> end = mision.getStringList("End-Console.Commands");
		if(!end.isEmpty()) {
			ExecuteMultipleCommandsInConsole(null,end);
		}
	}
	
	
	
	
	
	public Location getLocationOfLobby() {
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
	
	public void returnToLobby(Player player) {
		    FileConfiguration config = plugin.getConfig();
			for (String key : config.getConfigurationSection("Lobby-Spawn").getKeys(false)) {
				
				String world1 = key;
				double x = Double.valueOf(config.getString("Lobby-Spawn." + key + ".X"));
				double y = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Y"));
				double z = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Z"));
				float yaw = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Yaw"));
				float pitch = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Pitch"));
	
				Location l = new Location(Bukkit.getWorld(world1), x, y, z, yaw, pitch);
				player.getWorld().spawnParticle(Particle.LARGE_SMOKE, player.getLocation().add(0, 1, 0),
						/* NUMERO DE PARTICULAS */20, 10, 10, 10, /* velocidad */0, null, true);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
				player.teleport(l);
				return;
			
	
		}
	}
	
	
	 
	public void setAndSavePlayer(Player player,String map) {
		
		 PlayerInfo pl = null;
		 Attribute attribute = Attribute.MAX_HEALTH; 
		 if(CanJoinWithYourInventory(map) && !CanUseKit(map)) {
				//NO SALVAS SU INVENTARIO
			 	
				
				if(ExistLobbyMg()) {
				    pl = new PlayerInfo(plugin,false,player, player.getGameMode(), player.isFlying(), getLocationOfLobby(), map,new GamePoints());
				
				}else {
					pl = new PlayerInfo(plugin,false,player, player.getGameMode(), player.isFlying(), player.getLocation(), map,new GamePoints());
					
				}
			}else{
				//LO SALVAS
				
				if(ExistLobbyMg()) {
				    pl = new PlayerInfo(plugin,true,player,player.getActivePotionEffects(), player.getInventory().getContents(), player.getGameMode(), player.isFlying(),player.getHealth(), player.getAttribute(attribute).getValue(), player.getFoodLevel(), player.getLevel(),player.getExp(), getLocationOfLobby(), map,new GamePoints());
					
				}else {
					pl = new PlayerInfo(plugin,true,player,player.getActivePotionEffects(), player.getInventory().getContents(), player.getGameMode(), player.isFlying(),player.getHealth(), player.getAttribute(attribute).getValue(), player.getFoodLevel(), player.getLevel(),player.getExp(), player.getLocation(), map,new GamePoints());
	
				}
			}
		 
		 	plugin.getPlayerInfoPoo().put(player, pl);
			pl.ClearAllPlayerMg();
	}
	
	//TODO LOAD MAP
	public boolean loadDataMap(String map) {
		
		
		if(!plugin.getGameInfoPoo().containsKey(map)) {
			try {
				FileConfiguration game = getGameConfig(map);
				String time = game.getString("Game-Timer-H-M-S");
				GameType type = GameType.valueOf(game.getString("Type-Map").toUpperCase());
				int maxplayers = game.getInt("Max-Player");
				int minplayers = game.getInt("Min-Player");
				
				BossBar boss = null ;
				if(type == GameType.ADVENTURE) {
					boss = Bukkit.createBossBar(""+ChatColor.GREEN+ChatColor.BOLD+"Bienvenido al Mapa "+ChatColor.GOLD+ChatColor.BOLD+map+ChatColor.RED+ChatColor.BOLD+" Modo: "+ChatColor.GREEN+ChatColor.BOLD+"Aventura",BarColor.GREEN, BarStyle.SOLID,  null ,null);
				}else if(type == GameType.RESISTENCE) {
					boss = Bukkit.createBossBar(""+ChatColor.GREEN+ChatColor.BOLD+"Bienvenido al Mapa "+ChatColor.GOLD+ChatColor.BOLD+map+ChatColor.RED+ChatColor.BOLD+" Modo: "+ChatColor.DARK_RED+ChatColor.BOLD+"Resistencia",BarColor.GREEN, BarStyle.SOLID,  null ,null);
				}
			    boss.setVisible(true);
			   
			   
			    if(type == GameType.ADVENTURE || type == GameType.RESISTENCE) {
			    	  //pasar solo una lista para los 4 espacios ojo
				  
			
				    List<Entity> entities = new ArrayList<>();
					     
					    
			    	GameAdventure ga = new GameAdventure();
			    	ga.setMapName(map);
			    	ga.setTimeMg(time);
			    	ga.setGameType(type);
			    	ga.setMaxPlayersinMap(maxplayers);
			    	ga.setMinPlayersinMap(minplayers);
			    	ga.setBossbar(boss);	    	
			    	ga.setObjetivesMg(loadObjetivesOfGames(map));
			     
			    	ga.setGameTimeActions(loadGameTimeActions(map));
			    	ga.setCuboidZones(loadCuboidZones(map));
			    	ga.setLootTableLimit(getLootTableLimit());
			    	ga.setSpawnItemRange(getSpawnItemRange());
			    	ga.setSpawnMobRange(getSpawnMobRange());
			    	ga.setGenerators(loadMapGenerators(map));
			    	ga.setMobsGenerators(loadMapMobsGenerators(map));
			    	ga.setPvpinMap(isPvPAllowed(map));
			    	ga.setGameTime(loadMapGameTime(map, time)); 
			    	ga.setCountDownStart(loadCountdownMap());
			    	
			    	System.out.println("LOG-1 MISION: "+ga.ShowGame());
					
					plugin.getEntitiesFromFlare().put(map,entities);
					plugin.getGameInfoPoo().put(map, ga);
			    }else if(type == GameType.NEXO) {
			    	
			    	
			    	GameNexo gn = new GameNexo();
			    	gn.setMapName(map);
			    	gn.setTimeMg(time);
			    	gn.setGameType(type);
			    	gn.setMaxPlayersinMap(maxplayers);
			    	gn.setMinPlayersinMap(minplayers);
			    	gn.setBossbar(boss);	
			    	gn.setPvpinMap(isPvPAllowed(map));
//			    	List<String> t1 = new ArrayList<>();
//				    List<String> t2 = new ArrayList<>();
//			    	
//			    	
//			    	
//			    	Location bl = BlueNexo(map);
//			    	Location rd = RedNexo(map);
//			    	
//			    	if(bl == null || rd == null) {
//			    		
//			    		System.out.println("LOG-2 NEXO: Error no uno de los Location del Nexo Rojo o Azul no existe mapa: "+map);
//			    		return;
//			    	}
			    	
					//GameNexo gn = new GameNexo(map ,maxplayers,minplayers,misiontype ,EstadoPartida.ESPERANDO,StopMotivo.NINGUNO,boss,time,LoadObjetivesOfGames(map),participantes,espectador,false,false,t1,t2,bl,rd,100,100);
				
					//System.out.println("LOG-3 NEXO: "+gn.ShowGame());
				
					//plugin.getGameInfoPoo().put(map, gn);
			    }
			    
			  
			}catch(NullPointerException e) {
				e.printStackTrace();
				System.out.println("LOG-4 Error en el Mapa "+map);
			}
			return true;
		}
		return false;
	}
	


	
	public boolean canStartTheGame(Player player ,String map) {
		
		// NO ESTA INCLUIDO LA CONDICION DE SI AL EMPEZAR EL MAPA CON LOS JUGADORES MINIMOS Y UNO DURANTE LA FASE DE COMENZANDO SE SALE ESTA SE DETENGA
		 GameInfo ms = plugin.getGameInfoPoo().get(map);
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				 System.out.println("LOG-2 CANSTART MISION: "+ms.ShowGame());
				 player.sendMessage("");
				 player.sendMessage(ChatColor.GREEN+"Has Entrado en el Mapa "+ChatColor.translateAlternateColorCodes('&',getNameOfTheMap(map).replace("%player%",player.getName())));

				 player.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Te has unido"+ChatColor.RED+" ("+ChatColor.GOLD+ga.getParticipants().size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+ getMaxPlayerMap(map)+ChatColor.RED+")");

				 sendMessageToUsersOfSameMapLessPlayer(player,
				ChatColor.YELLOW+"Se a unido "+ChatColor.GREEN+player.getName()+ChatColor.RED+" ("+ChatColor.GOLD+ga.getParticipants().size()+ChatColor.YELLOW+"/"+ChatColor.GOLD+getMaxPlayerMap(map)+ChatColor.RED+")");
				MgScore sc = new MgScore(plugin);
				sc.LoadScore(player);
				
				 if(getMinPlayerMap(map) > ga.getParticipants().size()) {
					 
					 int min1 = getMinPlayerMap(map);
					 min1 = min1 - ga.getParticipants().size();
					 //TEXTO 
					 if(min1 == 1) {
						 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
						 sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugador para empezar.");
					 }else {
						 player.sendMessage(ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");
						 sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.YELLOW+"Faltan minimo "+ChatColor.RED+min1+ChatColor.YELLOW+" Jugadores para empezar.");

					 } 
			   }
				 
			if(getMinPlayerMap(map) == ga.getParticipants().size()){
				ms.setGameStatus(GameStatus.COMENZANDO);
				if(ms.getGameStatus() == GameStatus.COMENZANDO) {
				     int  segundo = ga.getCountDownStart();
				
					 SendMessageToAllUsersOfSameMap(player, ChatColor.GREEN+"\nSe a alcanzado el minimo de Jugadores necesarios.\n"+ChatColor.GOLD+"La partida Comenzara en: "+ChatColor.RED+segundo+ChatColor.GREEN+" segundos.\n ");
			 
				}
				
				if(ms.getGameType() == GameType.ADVENTURE) {
					AdventureTemp t = new AdventureTemp(plugin);
					t.Inicio(ms.getMapName());
				}else if(ms.getGameType() == GameType.RESISTENCE) {
					ResistenceTemp t = new ResistenceTemp(plugin);
					t.Inicio(ms.getMapName());
				}else if(ms.getGameType() == GameType.INFECTED) {
					InfectedTemp t = new InfectedTemp(plugin);
					t.Inicio(ms.getMapName());
				}else if(ms.getGameType() == GameType.NEXO) {
					//DestroyNexo dn = new DestroyNexo(plugin);
					//dn.RandomTeam(map);
					NexoTemp t = new NexoTemp(plugin);
					t.Inicio(ms.getMapName());
				}
			}
		 }
		return true;
	}
	
	public void reloadInfoTheGame(String map) {
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);
	 	gi.setGameTimeActions(loadGameTimeActions(map));
    	gi.setCuboidZones(loadCuboidZones(map));
    	gi.setLootTableLimit(getLootTableLimit());
    	gi.setSpawnItemRange(getSpawnItemRange());
    	gi.setSpawnMobRange(getSpawnMobRange());
    	gi.setGenerators(loadMapGenerators(map));
    	gi.setMobsGenerators(loadMapMobsGenerators(map));
    	gi.setPvpinMap(isPvPAllowed(map));
    	gi.setGameTime(loadMapGameTime(map, gi.getTimeMg())); 
	}
	
	public boolean isPlayerKnocked(Player player) {
		return plugin.getKnockedPlayer().containsKey(player);
	}
	
	//TODO BOOLEAN
	public boolean isPlayerinGame(Player player) {
		return plugin.getPlayerInfoPoo().containsKey(player);
	}
	
	public boolean isMapinGame(String map) {
		return plugin.getGameInfoPoo().containsKey(map);
	}
	
	public int getLootTableLimit() {
		FileConfiguration config = plugin.getConfig();
		return config.getInt("Loot-Table-Limit");
	}
	
	public int getSpawnMobRange() {
		FileConfiguration config = plugin.getConfig();
		return config.getInt("Mob-Spawn-Range");
	}
	
	public int getSpawnItemRange() {
		FileConfiguration config = plugin.getConfig();
		return config.getInt("Item-Spawn-Range");
	}
	
	public boolean hasAntiVoid(Player player ) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		FileConfiguration game = getGameConfig(pl.getMapName());
		return game.getBoolean("Anti-Void");
	}
	
	public void SetHeartsInGame(Player player , String map) {
		FileConfiguration game = getGameConfig(map);
		int vida = game.getInt("Set-Hearts");
		player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(vida);
		return;
	}
	
	public void SetDefaultHeartsInGame(Player player) {
		player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
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
	
	public boolean hasMapObjetives(String map) {
		 FileConfiguration game = getGameConfig(map);
		 return game.getBoolean("Has-Objetives");
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
	
	public boolean isBlockedTheMap(String map) {
		FileConfiguration config = plugin.getConfig();
		 List<String> ac = config.getStringList("Maps-Blocked.List");
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
	
	public boolean hasObjetives(String map) {
		FileConfiguration game = getGameConfig(map);
	    return game.getBoolean("Has-Objetives");
	}
	
	public boolean isNecessaryObjetivePrimary(String map) {
		FileConfiguration game = getGameConfig(map);
	    return game.getBoolean("Primary-Objetive-Opcional");
	}
	
	public boolean isNecessaryObjetiveSedondary(String map) {
		FileConfiguration game = getGameConfig(map);
	    return game.getBoolean("Secondary-Objetive-Opcional");
	}
	
	public String getNameOfTheMap(String map) {
		FileConfiguration mision = getGameConfig(map);
		return mision.getString("Start.Tittle-of-Mision");
	}
	
	public int getMaxPlayerMap(String map) {
		FileConfiguration mision = getGameConfig(map);
		return mision.getInt("Max-Player");
	}
	
	public boolean isEnabledReviveSystem(String map) {
		FileConfiguration mision = getGameConfig(map);
		return mision.getBoolean("Revive-System");
	}
	
	public boolean hasCuboidZones(String map) {
		FileConfiguration game = getGameConfig(map);
	    return game.contains("Cuboid-Zones.List");
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
				  
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<GameTimeActions> list = gi.getGameTimeActionsMg();
		if(!list.stream().filter(o -> o.getDisplayTime().equals(time)).findFirst().isPresent()) {
			return;
		}
		
		GameTimeActions gta = list.stream().filter(o -> o.getDisplayTime().equals(time)).findFirst().get();
		
		if(gta.isActionExecuted()) return;
		
			 	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				
				List<String> actions = gta.getTimeActions();
				if(!actions.isEmpty()) {
					for(int i = 0 ;i<actions.size();i++) {
						String texto = actions.get(i);
						if(!isMessageFromActions(map,texto)) {
							Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto));	
						} 
				}}		
			
				
				gta.setActionExecuted(true);
		 return;
	}
	 
	public boolean isMapRanked(String map) {
		List<String> are = plugin.getConfig().getStringList("Maps-Ranked.List");
		return are.contains(map);
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
	
	
	public boolean isMessageFromActions(String map,String message) {
		
		String keyword = "(P)";
		if(message.endsWith(keyword)) {
			GameInfo ms = plugin.getGameInfoPoo().get(map);
			
			 if(ms instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) ms;
					List<String> play = ga.getParticipants();
					List<String> vivos = ga.getAlivePlayers();
					
					
						for(String target : play) {
							Player user = Bukkit.getServer().getPlayerExact(target);
							   user.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%players%",vivos.toString()).replace(keyword,"").replace("%player%",user.getName())));
						
						return true;
				  }
			 }
		}
		return false;
	}

	
	public Long RewardPointsForItems(int points) {
		
		  int puntaje = points;
		  double resultado = puntaje / 5;
		  long pr = 0;
  		  pr = Math.round(resultado);
  		  return pr;
		
	}
	  
	public void JoinSpectator(Player player ,String map) {
		 //MODO ESPECTADOR no te uniras como jugador
		 setAndSavePlayer(player, map);
		 spectatorAddToGame(player, map);
		 GameInfo ms = plugin.getGameInfoPoo().get(map);
		 if(ms instanceof GameAdventure) {
			 	player.setGameMode(GameMode.SPECTATOR);
				GameAdventure ga = (GameAdventure) ms;
				 List<String> spectador = ga.getSpectators();
				 player.sendMessage(ChatColor.GREEN+"Estas como Espectador en el Mapa: "+ChatColor.GOLD+map);
				 ga.getBossbar().addPlayer(player);
				 sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.WHITE+"El Jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" se Unio como Espectador."+ChatColor.RED+"\n["+ChatColor.GREEN+"Total de Espectadores"+ChatColor.YELLOW+": "+ChatColor.DARK_PURPLE+(spectador.size())+ChatColor.RED+"]");
				 TptoSpawnSpectator(player, map);
		 }
	}

	
	public boolean CanJoinToTheMap(Player player ,String map) {
		 if(isPlayerinGame(player)) {
			 player.sendMessage(ChatColor.RED+" Ya estas en un Juego...");
			 return false;
		 }if(!ExistMap(map)) {
			 player.sendMessage(ChatColor.RED+" Ese Mapa no Existe...");
			 return false;
		 }if(ConditionsToStartGame(player,map)) {
			return true;//visto bueno para entrar
		 }
	    return false;
	}
	
	
	public boolean ConditionsToStartGame(Player player,String map) {
		if(HasMaintenance()) {
			if(!player.isOp()) {
				player.sendMessage(ChatColor.RED+"Fuera de Servicio Por Mantenimiento contacta con un Administrador.");
				return false;
			}else {
				player.sendMessage(ChatColor.RED+"MineGame esta Fuera de Servicio Por Mantenimiento, Accediendo como Op.");
			}
		}if(isBlockedTheMap(map)) {
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
				 BossBar boss = minfo.getBossbar();
				 GameType misiontype = minfo.getGameType();
				 int maxplayers = mision.getInt("Max-Player");
				 ReportsManager cooldown = new ReportsManager(plugin) ;
				 
				if(misiontype == GameType.ADVENTURE) {
					
					if(cooldown.HasSancionPlayer(player)) {
					
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
					 
					 if(minfo.getGameStatus() == GameStatus.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipants().size() == maxplayers && minfo.getGameStatus() == GameStatus.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getGameStatus() == GameStatus.JUGANDO && !isPlayerinGame(player)) {
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
			 					List<String> perml = mision.getStringList("How-Get-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						
			 						player.sendMessage("");
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						player.sendMessage("");			
			 						
			 						
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
					 
					 
					 if(minfo.getGameStatus() == GameStatus.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipants().size() == maxplayers && minfo.getGameStatus() == GameStatus.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getGameStatus() == GameStatus.JUGANDO || minfo.getGameStatus() == GameStatus.PAUSE && !isPlayerinGame(player)) {
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
			 					List<String> perml = mision.getStringList("How-Get-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						
			 						player.sendMessage("");
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						player.sendMessage("");			
			 						
			 						
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
					 
					 
					 if(minfo.getGameStatus() == GameStatus.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipants().size() == maxplayers && minfo.getGameStatus() == GameStatus.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getGameStatus() == GameStatus.JUGANDO  || minfo.getGameStatus() == GameStatus.PAUSE && !isPlayerinGame(player)) {
						 if(cooldown.HasSancionPlayer(player)) {
							 //MODO ESPECTADOR no te uniras como jugador
							 player.sendMessage(ChatColor.YELLOW+"Estas Baneado o tienes un TempBan pero puedes Observar.");
						 }
						 JoinSpectator(player,map);
						
						 return false;
					 }if(mision.getBoolean("Requires-Permission")) {
			 				String perm = mision.getString("Permission-To-Play");
			 				if(!player.hasPermission(perm)) {
			 					List<String> perml = mision.getStringList("How-Get-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						
			 						player.sendMessage("");
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						player.sendMessage("");			 						
			 						
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
			 BossBar boss = minfo.getBossbar();
			 GameType misiontype = minfo.getGameType();
			 int maxplayers = mision.getInt("Max-Player");
			 ReportsManager cooldown = new ReportsManager(plugin) ;
			 
			 if(misiontype == GameType.NEXO) {
					
					if(cooldown.HasSancionPlayer(player)) {
						
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
					 if(minfo.getGameStatus() == GameStatus.TERMINANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta terminando. ");
						 return false;
					 }
					 
					 if(ga.getParticipants().size() == maxplayers && minfo.getGameStatus() == GameStatus.COMENZANDO) {
						 player.sendMessage(ChatColor.RED+"La Partida esta llena espera un rato para entrar como Espectador."); 
						 return false;
					 }if(minfo.getGameStatus() == GameStatus.JUGANDO && !isPlayerinGame(player)) {
						 if(cooldown.HasSancionPlayer(player)) {
							 //MODO ESPECTADOR no te uniras como jugador
							 player.sendMessage(ChatColor.YELLOW+"Estas Baneado o tienes un TempBan pero puedes Observar.");
						 }
						 JoinSpectator(player,map);
						
						 return false;
					 }if(mision.getBoolean("Requires-Permission")) {
			 				String perm = mision.getString("Permission-To-Play");
			 				if(!player.hasPermission(perm)) {
			 					List<String> perml = mision.getStringList("How-Get-Permission.Message");
			 					if(!perml.isEmpty()) {
			 						perml.add("");
			 						for(int i =0;i< perml.size();i++) {
			 							player.sendMessage(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%", player.getName()));
			 						}
			 						perml.add("");
			 						
			 					}else {
			 						player.sendMessage(ChatColor.RED+"Mapa Bloqueado: Necesitas un Permiso para Acceder.");
			 					}
			 					
			 					return false;
			 				}
			 		 }if(mision.getBoolean("Has-Time")) {
						 String time = mision.getString("Usage-Time");
					    	
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
			PotionEffect resis = new PotionEffect(PotionEffectType.RESISTANCE,/*duration*/ 10 * 20,/*amplifier:*/5, true ,true,true );
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
				List<Player> targets = ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player players : targets) {
					players.teleport(player.getLocation());
				}
				sendMessageToConsole(ChatColor.GREEN+"Los Jugadores fueron Teletransportado a "+player.getName());
			}
		

		}
	}
	
	public void TpPlayerOfGameToLocation(Player player,String name) {
		if(isPlayerinGame(player)) {
			PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
			 if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					List<Player> targets = ConvertStringToPlayer(ga.getAlivePlayers());
					for(Player players : targets) {
						if(players.getName().equals(name)) {
							players.teleport(player.getLocation());
							break;
						}
					}
					sendMessageToConsole(ChatColor.GREEN+name+" fue Teletransportado a "+player.getName());
			 }
		}
	}
	
	public void TpAllPlayersOfGameToLocationSpecific(Player player,String map,String world,double x,double y,double z,float yaw,float pitch) {
		
			if(isMapinGame(map)) {
				GameInfo gi = plugin.getGameInfoPoo().get(map);
				if(gi instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) gi;
					Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
					List<Player> targets = ConvertStringToPlayer(ga.getAlivePlayers());
					for(Player players : targets) {
						players.teleport(l.add(0.5,0, 0.5));

					}
					sendMessageToConsole(ChatColor.GREEN+"Los Jugadores fueron Teletransportados a "+l.toString());
				}
			}else {
				sendMessageToConsole(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no esta en Juego");

			}
	}
	
	public void TpAllPlayersOfGameToLocationSpecific(Player player,String map,String world,double x,double y,double z) {
		
		if(isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				Location l = new Location(Bukkit.getWorld(world), x, y, z);
				List<Player> targets = ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player players : targets) {
					l.setYaw(players.getLocation().getYaw());
					l.setPitch(players.getLocation().getPitch());
					players.teleport(l.add(0.5,0, 0.5));

				}
				 
				sendMessageToConsole( ChatColor.GREEN+"Los Jugadores fueron Teletransportados a "+l.toString());
			
			}
		}else {
			sendMessageToConsole(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no esta en Juego");

		}
	}
	
	
	public void TpPlayerOfGameToLocationSpecific(Player player,String target,String map,String world,double x,double y,double z,float yaw,float pitch) {
		
		if(isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				
				Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
				List<Player> targets = ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player players : targets) {
					if(players.getName().equals(target)) {
						players.teleport(l.add(0.5,0, 0.5));
						break;
					}
					
				}
				sendMessageToConsole(ChatColor.GREEN+target+" fue Teletransportado a "+l.toString());
			}
		}else {
			sendMessageToConsole(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no esta en Juego");

		}
	
	}
	
	public void TpPlayerOfGameToLocationSpecific(Player player,String target,String map,String world,double x,double y,double z) {
		
		if(isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				
				
				Location l = new Location(Bukkit.getWorld(world), x, y, z);
				List<Player> targets = ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player players : targets) {
					if(players.getName().equals(target)) {
						l.setYaw(players.getLocation().getYaw());
						l.setPitch(players.getLocation().getPitch());
						players.teleport(l.add(0.5,0, 0.5));

						break;
					}
					
				}
				
				sendMessageToConsole(ChatColor.GREEN+target+" fue Teletransportado a "+l.toString());
				
			}
		}else {
			sendMessageToConsole(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" no esta en Juego");

		}
	}

	

	
	
	
	
	
	
	
	//TODO  TIEMPO PARA ABAJO	
	//LISTA QUE CONVIERTE DE ESPAÑOL A INGLES DE LA CONFIG
	
	public void mgformatsTime() {
		System.out.println("FORMATOS");
		System.out.println("1) Lunes Miercoles Viernes");
		System.out.println("2) Domingo Sabado Viernes 08:35-17:11");
		System.out.println("3) 10/01/2023 06:14:00 AM-");
		System.out.println("4) 10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM");
		System.out.println("Significado:");
		System.out.println("1) Dias: coloca los dias que deseas que funcione el mapa.");
		System.out.println("2) Dias y Hora: coloca los dias y una hora que deseas que funcione el mapa. (la hora va de menor a mayor ver el ejemplo)");
		System.out.println("3) Fecha Especifica 1: coloca la fecha en la que deseas que empiece a funcionar el mapa. (Guiarse con el ejemplo estrictamente)");
		System.out.println("4) Fecha Especifica 2: coloca la fecha en la que deseas que funcione el mapa y otra en la que cierre. (Guiarse con el ejemplo estrictamente)");

		
		return;
	}
	
	
	public List <DayOfWeek> SpanishToEnglish(String days) {
		String[] d = days.split(" ");
		List <DayOfWeek> l = new ArrayList<DayOfWeek>();
		try {
			
			for(int i = 0; i < d.length;i++) {
				
			l.add(DayOfWeek.valueOf(d[i].replace("Lunes","MONDAY").replace("Martes","TUESDAY").replace("Miercoles","WEDNESDAY").replace("Jueves","THURSDAY").replace("Viernes","FRIDAY").replace("Sabado","SATURDAY").replace("Domingo","SUNDAY").toUpperCase()));
			
			}
		}catch(IllegalArgumentException e) {
			System.out.println("LOG-1 SPANISH El Formato 1 o 2 no es el correcto ");
			 mgformatsTime();
			
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
			
		}else if(simbol.equals("-")) {
			
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
					
					//TIME A AND TIME B
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
							}else if(lt.isAfter(t2)) {
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
						 mgformatsTime();
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
						 mgformatsTime();
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
							}}
						
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
							
					}}
				
					String dias = sb.toString();
					player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"Cerrado: "+ChatColor.GOLD+"Solo funciona los dias "+dias);
					//isJoinRunning(player);
					return false;
				}
				
			}else{
				//si no tiene numero es forma 1
				List <DayOfWeek> l = SpanishToEnglish(time);
			
				//si contiene ejecuta el codigo chido
				if(l.contains(lt.getDayOfWeek())) {
				
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
							
					}}
				
					String dias = sb.toString();
					player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"Cerrado: "+ChatColor.GOLD+"Solo funciona los dias "+dias);
					//isJoinRunning(player);
					return false;
				}
			}
	   }
		return false;
	}
	
	
	public String TimeDiferenceMg(LocalDateTime inicio, LocalDateTime fin) {
        LocalDateTime tempDateTime = LocalDateTime.from(inicio);

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
	
	
	//TODO CHAT
	public void SendMessageToAllUsersOfSameMap(Player player ,String text) {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String map = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(map);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				
				
				List<Player> play = ConvertStringToPlayer(ga.getParticipants());
				List<Player> spect = ConvertStringToPlayer(ga.getSpectators());
				
				for(Player target : play) {
					
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
				}
				
				if(!spect.isEmpty()) {
					for(Player target : spect) {
						
						target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				}
				 Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+map.toUpperCase()+": "+text);
		 }
	}
	
	
	//TODO CHAT
	public void SendMessageToAllUsersOfSameMapCommand(Player player, String map ,String text) {
		
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
				
				
				List<Player> play = ConvertStringToPlayer(ga.getParticipants());
				List<Player> spect = ConvertStringToPlayer(ga.getSpectators());
				
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
	public void SendTittleToAllUsersOfSameMapCommand(Player player, String map ,String text) {
		
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
				
				List<Player> play = ConvertStringToPlayer(ga.getParticipants());
				List<Player> spect = ConvertStringToPlayer(ga.getSpectators());
				
				
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
	
	//TODO MESSAGE
	//LA DIFERENCIA ES QUE EN ESTE OMITE TU NOMBRE EN EL ALL PLAYERS ES UN MENSAJE GLOBAL PARA TODOS LOS JUGADORES INCLUYENDOTE
	
	/**
	 * Envia un mensaje a otros jugadores del mapa menos al Jugador inicial
	 * 
	 */
	 
	public void sendMessageToUsersOfSameMapLessPlayer(Player player ,String text) {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapname = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(mapname);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) ms;
					List<Player> play = ConvertStringToPlayer(ga.getParticipants());
					
					sendMessageToConsole(text);
					
					for(Player target : play) {
						   if(target.getName().equals(player.getName())) continue;
						   target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
					
					List<Player> spect = ConvertStringToPlayer(ga.getSpectators());
					if(!spect.isEmpty()) {
						for(Player target : spect) {
							
							target.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
						
					}}
		 } 
	}
	
	/**
	 * Envia un mensaje a todos los Jugadores menos a 2 que esten especificados
	 * Orientado a revivir 
	 * 
	 */
	public void sendMessageToUsersOfSameMapLessTwoPlayers(Player player,Player player2 ,String text) {
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapname = pl.getMapName();
		GameInfo ms = plugin.getGameInfoPoo().get(mapname);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) ms;
					List<Player> play = ConvertStringToPlayer(ga.getParticipants());
				
					sendMessageToConsole(text);
					
					for(Player target : play) {
						   if(target.getName().equals(player.getName()) || target.getName().equals(player2.getName())) continue;
						   target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
					
					List<Player> spect = ConvertStringToPlayer(ga.getSpectators());
					if(!spect.isEmpty()) {
						for(Player target : spect) {
							target.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
					}}
		 } 
	}
	 
	
	/**
	 * Envia un mensaje a todos los jugadores del mapa 
	 * 
	 */
	public void sendMessageToAllPlayersInMap(String map,String text) {
		
		
		GameInfo ms = plugin.getGameInfoPoo().get(map);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				List<String> play = ga.getParticipants();
				
				if(!play.isEmpty()) {
					for(String target : play) {
						Player user = Bukkit.getServer().getPlayerExact(target);
						
						user.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					
				}}	
		 }
	}
	
	
	/**
	 * Envia un mensaje a todos los jugadores Op del mapa 
	 * 
	 */
	
	public void sendMessageToAllPlayersOpInMap(String map,String text) {
		
		GameInfo ms = plugin.getGameInfoPoo().get(map);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				List<String> play = ga.getParticipants();
				
				if(!play.isEmpty()) {
					for(String target : play) {
						Player user = Bukkit.getServer().getPlayerExact(target);
						if(!user.isOp()) continue;
						user.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					
				}}	
		 }
	}
	
	/**
	 * Envia un titulo a todos los jugadores del mapa 
	 * 
	 */
	
	public void sendTittleToAllPlayersInMap(String map,String text,String text2) {
		
		GameInfo ms = plugin.getGameInfoPoo().get(map);
		//MisionInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		 if(ms instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) ms;
				List<String> play = ga.getParticipants();
				
				if(!play.isEmpty()) {
					for(String target : play) {
						Player user = Bukkit.getServer().getPlayerExact(target);
						
						user.sendTitle(ChatColor.translateAlternateColorCodes('&', text), ChatColor.translateAlternateColorCodes('&', text2), 20, 20, 20);
					
				}}	
		 }
	}
	
	
	//TODO CONSOLE AND PLAYER FOR OP OR DEBUG
	public void sendMessageToUserAndConsole(Player player ,String text) {
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',text));
		if(player != null) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',text.replace("%player%", player.getName())));
		}
		return;
	}
	
	public void sendMessageToConsole(String text) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',text));
		return;
	}
	
	public void sendResultsOfGame(GameInfo map,String cronomet,String timer ) {
		
		if(map instanceof GameAdventure) {
			
			GameAdventure ga = (GameAdventure) map;
			
			List<String> participants = ga.getParticipants(); 
			List<String> alive = ga.getAlivePlayers();  
			List<String> deads = ga.getDeadPlayers();
			List<String> spectator = ga.getSpectators();
			List<String> arrives = ga.getArrivePlayers();
			
			if(!HasMaintenance() || !isBlockedTheMap(map.getMapName())) {
				saveMapFrequencysmg(map);
			}
			
			
			LocalDateTime ldt = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
			
			sendMessageToConsole("");	
			sendMessageToConsole(ChatColor.GRAY+"=============================");	
			sendMessageToConsole(""+ChatColor.GRAY+"MAPA: "+ChatColor.WHITE+ga.getMapName());
			sendMessageToConsole(""+ChatColor.GRAY+"FECHA: "+ChatColor.WHITE+ldt.format(formatter));
			sendMessageToConsole(""+ChatColor.GRAY+"DEFAULT TIMER: "+ChatColor.GREEN+ga.getGameTime().getGameTimerDefaultForResult());
			sendMessageToConsole(""+ChatColor.GRAY+"DURACION: "+ChatColor.WHITE+cronomet);
			sendMessageToConsole(""+ChatColor.GRAY+"TIMER: "+ChatColor.WHITE+timer);
			sendMessageToConsole(""+ChatColor.GRAY+"MOTIVOS DE PARADA: "+ChatColor.WHITE+map.getMotivo().toString());

			if(participants.isEmpty()) {
				sendMessageToConsole(""+ChatColor.GRAY+ChatColor.BOLD+"PARTICIPANTES: "+ChatColor.WHITE+"SIN PARTICIPANTES");
			}else {
				String comments =  ""+ChatColor.GREEN+ChatColor.BOLD+"PARTICIPANTES: ";
				for(int i = 0 ; i < participants.size();i++) {
					comments = comments+ChatColor.GREEN+participants.get(i)+ChatColor.GOLD+",";
				}
				comments = comments+ChatColor.GRAY+ChatColor.BOLD+" PARTICIPARON: "+ChatColor.GREEN+participants.size();
				sendMessageToConsole(comments);
			}
			
			if(alive.isEmpty()) {
				sendMessageToConsole(""+ChatColor.GREEN+ChatColor.BOLD+"VIVOS: "+ChatColor.WHITE+"SIN SUPERVIVIENTES");
			}else {
				String comments =  ""+ChatColor.GREEN+ChatColor.BOLD+"VIVOS: ";
				for(int i = 0 ; i < alive.size();i++) {
					comments = comments+ChatColor.GREEN+alive.get(i)+ChatColor.GOLD+",";
				}
				comments = comments+ChatColor.GREEN+ChatColor.BOLD+" SOBREVIVIERON: "+ChatColor.GOLD+alive.size();
				sendMessageToConsole(comments);

			}
			
			if(deads.isEmpty()) {
				sendMessageToConsole(""+ChatColor.RED+ChatColor.BOLD+"MUERTOS: "+ChatColor.WHITE+"SIN MUERTOS");
			}else {
				String comments = ""+ChatColor.RED+ChatColor.BOLD+"MUERTOS: ";
				for(int i = 0 ; i < deads.size();i++) {
					comments = comments+ChatColor.YELLOW+deads.get(i)+ChatColor.GOLD+",";
				}
				comments = comments+ChatColor.RED+ChatColor.BOLD+" MURIERON: "+ChatColor.YELLOW+deads.size();
				sendMessageToConsole(comments);

			}
			
			if(spectator.isEmpty()) {
				sendMessageToConsole(""+ChatColor.AQUA+ChatColor.BOLD+"ESPECTADORES: "+ChatColor.WHITE+"SIN ESPECTADORES");
			}else {
				String comments = ""+ChatColor.AQUA+ChatColor.BOLD+"ESPECTADORES: ";
				for(int i = 0 ; i < spectator.size();i++) {
					comments = comments+ChatColor.WHITE+spectator.get(i)+ChatColor.GOLD+",";
				}
				comments = comments+ChatColor.AQUA+ChatColor.BOLD+" ESPECTADORES: "+ChatColor.WHITE+spectator.size();
				sendMessageToConsole(comments);

			}
			
			if(arrives.isEmpty()) {
				sendMessageToConsole(""+ChatColor.GOLD+ChatColor.BOLD+"GANADORES: "+ChatColor.WHITE+"SIN GANADORES");
			}else {
				String comments = ""+ChatColor.GOLD+ChatColor.BOLD+"GANADORES: ";
				for(int i = 0 ; i < arrives.size();i++) {
					comments = comments+ChatColor.DARK_PURPLE+arrives.get(i)+ChatColor.DARK_GREEN+",";
				}
				comments = comments+ChatColor.GOLD+ChatColor.BOLD+" GANADORES: "+ChatColor.WHITE+arrives.size();
				sendMessageToConsole(comments);

			}
			sendMessageToConsole(ChatColor.GRAY+"=============================");	
			sendMessageToConsole("");	
			
			
		}
		
	}
	
	//TODO RESTORE
	public void RestorePlayer(Player player) {
	
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo ms = plugin.getGameInfoPoo().get(pl.getMapName());
		
		 if(ms instanceof GameAdventure) {
				//GameAdventure ga = (GameAdventure) ms;
				SetDefaultHeartsInGame(player);
				BossBar boss = ms.getBossbar();
				boss.removePlayer(player);
				
				System.out.println("LOG-1 RESTORE ANTES MAP: "+ms.ShowGame());
				
					//SE SECCIONA POR QUE HAY QUE VER SI SE SALVO O NO SU INVENTARIO
					if(pl.hasPlayerMoreInfo()) {
						player.teleport(pl.getLocationMG());
						pl.RestoreAllPlayerMg(player);
						if(ms.getGameStatus() == GameStatus.TERMINANDO) {
							playerWinnerReward(player);
							playerLoserReward(player);
						}
						
						removeAllPlayerToGame(player, pl.getMapName());
					
					}else {
						player.teleport(pl.getLocationMG());
						pl.RestoreGamemodePlayerMg(player);
						if(ms.getGameStatus() == GameStatus.TERMINANDO) {
							playerWinnerReward(player);
							playerLoserReward(player);
						}
						removeAllPlayerToGame(player, pl.getMapName());
					}
				
		 }else if(ms instanceof GameNexo) {
			// GameNexo gn = (GameNexo) ms;
			    SetDefaultHeartsInGame(player);
				BossBar boss = ms.getBossbar();
				boss.removePlayer(player);
				System.out.println("LOG 2 RESTORE ANTES NEXO: "+ms.ShowGame());
				
					player.teleport(pl.getLocationMG());
					pl.RestoreAllPlayerMg(player);
				
				removeAllPlayerToGame(player, pl.getMapName());
				
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
   	public void Top(String map) {
   					FileConfiguration message = plugin.getMessage();
		// PRIMERA PARTE
					HashMap<String, Integer> scores = new HashMap<>();

						GameInfo ms = plugin.getGameInfoPoo().get(map);
						List<Player> joins = new ArrayList<>();
						List<Player> spectador = new ArrayList<>();
						
						 if(ms instanceof GameAdventure) {
								GameAdventure ga = (GameAdventure) ms;
								 joins = ConvertStringToPlayer(ga.getParticipants());
								 spectador = ConvertStringToPlayer(ga.getSpectators());
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
	
		
				System.out.println("LOG 1 -------TOP--------");
				
				
					
					for(Player player : joins) {

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
										if(i == 1) {
											 player.sendMessage(ChatColor.translateAlternateColorCodes('&',texto
													 .replace("%mvp%",""+ChatColor.GREEN+ChatColor.BOLD+"MVP")
													 .replace("%player%", e.getKey())
													 .replace("%place%", Integer.toString(i))
													 .replace("%pointuser%", Integer.toString(e.getValue()))
													 .replace("%reward%", Long.toString(RewardPointsForItems(e.getValue())))
													 .replace("%revive%", Integer.toString(getReviveInfo(e.getKey())))
													 .replace("%helprevive%", Integer.toString(getReviveAsistenceInfo(e.getKey())))
													 .replace("%deads%", Integer.toString(getDeadsInfo(e.getKey())))
													 .replace("%damage%", Integer.toString(getDamageInfo(e.getKey())))
													 //.replace("%cronomet%", time)
													
													 ));
										}else {
											 player.sendMessage(ChatColor.translateAlternateColorCodes('&',texto
													 .replace("%mvp%","")
													 .replace("%player%", e.getKey())
													 .replace("%place%", Integer.toString(i))
													 .replace("%pointuser%", Integer.toString(e.getValue()))
													 .replace("%reward%", Long.toString(RewardPointsForItems(e.getValue())))
													 .replace("%revive%", Integer.toString(getReviveInfo(e.getKey())))
													 .replace("%helprevive%", Integer.toString(getReviveAsistenceInfo(e.getKey())))
													 .replace("%deads%", Integer.toString(getDeadsInfo(e.getKey())))
													 .replace("%damage%", Integer.toString(getDamageInfo(e.getKey())))
													 //.replace("%cronomet%", time)
													
													 ));
										}
											
							}}}}				
						
						 if (message.getBoolean("Message.message-top")) {
								List<String> messagep3 = message.getStringList("Message.message-top-decoracion2");
								for (int j3 = 0; j3 < messagep3.size(); j3++) {
									String texto3 = messagep3.get(j3);
								
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto3));
						  }}
					}
   		
   	}
   	
   	
   	public List<Map.Entry<String, Integer>> getParticipants(){
   		HashMap<String, Integer> scores = new HashMap<>();
   		
   		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});
   		
   		return list;
   	}
   
   	public void TopConsole(String map) {
	FileConfiguration message = plugin.getMessage();
   		
		

		// PRIMERA PARTE
					HashMap<String, Integer> scores = new HashMap<>();

						GameInfo ms = plugin.getGameInfoPoo().get(map);
						 if(ms instanceof GameAdventure) {
								GameAdventure ga = (GameAdventure) ms;
								List<Player> joins = ConvertStringToPlayer(ga.getParticipants());
								
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
		
		
				System.out.println("LOG 1-------TOP-------- CONSOLE");
				
				
					if (message.getBoolean("Message.message-top")) {
						List<String> messagep1 = message.getStringList("Message.message-top-decoracion1");
						for (int j1 = 0; j1 < messagep1.size(); j1++) {
							String texto2 = messagep1.get(j1);
							 
							sendMessageToUserAndConsole(null, texto2);
					  }}			
							
					if(!list.isEmpty()) {
						
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
									
										if(i == 1) {
												sendMessageToUserAndConsole(null,texto
														 .replace("%mvp%",""+ChatColor.GREEN+ChatColor.BOLD+"MVP")
														 .replace("%player%", e.getKey())
														 .replace("%place%", Integer.toString(i))
														 .replace("%pointuser%", Integer.toString(e.getValue()))
														 .replace("%reward%", Long.toString(RewardPointsForItems(e.getValue())))
														 .replace("%revive%", Integer.toString(getReviveInfo(e.getKey())))
														 .replace("%helprevive%", Integer.toString(getReviveAsistenceInfo(e.getKey())))
														 .replace("%deads%", Integer.toString(getDeadsInfo(e.getKey())))
														 .replace("%damage%", Integer.toString(getDamageInfo(e.getKey())))
														 //.replace("%cronomet%", time)
														
														 );
										}else {
											sendMessageToUserAndConsole(null,texto
													 .replace("%mvp%","")
													 .replace("%player%", e.getKey())
													 .replace("%place%", Integer.toString(i))
													 .replace("%pointuser%", Integer.toString(e.getValue()))
													 .replace("%reward%", Long.toString(RewardPointsForItems(e.getValue())))
													 .replace("%revive%", Integer.toString(getReviveInfo(e.getKey())))
													 .replace("%helprevive%", Integer.toString(getReviveAsistenceInfo(e.getKey())))
													 .replace("%deads%", Integer.toString(getDeadsInfo(e.getKey())))
													 .replace("%damage%", Integer.toString(getDamageInfo(e.getKey())))
													 //.replace("%cronomet%", time)
													
													 );
										}
								}}}}
						
					}else {
						sendMessageToUserAndConsole(null, "Vacio");
					}
					
					
					 if (message.getBoolean("Message.message-top")) {
							List<String> messagep3 = message.getStringList("Message.message-top-decoracion2");
							for (int j3 = 0; j3 < messagep3.size(); j3++) {
								String texto3 = messagep3.get(j3);
							
								sendMessageToUserAndConsole(null,texto3);
					  }}
   	}
   	 
   	
   	public int getReviveInfo(String name) {
   	 return plugin.getPlayerInfoPoo().get(ConvertStringToPlayerAlone(name)).getGamePoints().getRevive();
   	}
   	
 	public int getDeadsInfo(String name) {
 	   	 return plugin.getPlayerInfoPoo().get(ConvertStringToPlayerAlone(name)).getGamePoints().getDeads();
 	}
 	public int getReviveAsistenceInfo(String name) {
 	   	 return plugin.getPlayerInfoPoo().get(ConvertStringToPlayerAlone(name)).getGamePoints().getHelpRevive();
 	}
 	
 	public int getDamageInfo(String name) {
	   	 return TransformPosOrNeg(plugin.getPlayerInfoPoo().get(ConvertStringToPlayerAlone(name)).getGamePoints().getDamage());
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
							List<Player> joins = ConvertStringToPlayer(ga.getParticipants());
							List<Player> spectador = ConvertStringToPlayer(ga.getSpectators());
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
					
						
								System.out.println("LOG 1 REWARD-------TOP--------");
								
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
									}}
							
						}
		
	}	
   	
	
	   //TODO STOP
	   public void StopGames(Player player , String name,StopMotivo motivo) {
		   
		   GameConditions gc = new GameConditions(plugin);
			if(gc.ExistMap(name)) {
				GameInfo ms = plugin.getGameInfoPoo().get(name);
				if(ms instanceof GameAdventure) {
					GameAdventure ga = (GameAdventure) ms;
					GameStatus estadoPartida = ms.getGameStatus();
					if(estadoPartida == GameStatus.JUGANDO) {
						GameIntoMap ci = new GameIntoMap(plugin);
						
						ms.setMotivo(motivo);
						List<String> vivos = ga.getAlivePlayers();
						List<Player> players = gc.ConvertStringToPlayer(vivos);
						if(motivo == StopMotivo.WIN) {
							
							if(vivos.isEmpty()) {
								Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+ms.getGameType().toString()+ChatColor.GREEN+" Ganaron en la Arena: "+ChatColor.GOLD+name+ChatColor.GREEN+" por Condiciones varias , Terminando. Pero no hay ningun jugador vivo.");

								return;
							}
							
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+ms.getGameType().toString()+ChatColor.GREEN+": Ganaron en la Arena: "+ChatColor.GOLD+name+ChatColor.GREEN+" por Condiciones varias , Terminando.");
							
							
							if(ms.getGameType() == GameType.ADVENTURE) {
								for(Player target : players) {
									target.sendMessage(ChatColor.GREEN+"Victoria todos los que quedaron Vivos han Ganado...");
									ci.GamePlayerWin(target);
								}
								return;
							}else if(ms.getGameType() == GameType.RESISTENCE) {
								
								
								for(Player target : players) {
									target.sendMessage(ChatColor.GREEN+"Victoria todos los que quedaron Vivos han Ganado...");
									gc.EndTptoSpawn(target, name);
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
					if(player != null) {
						player.sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
					}
					Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"El Mapa "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");

				}
				
			}
				
				
	   }
	  
	   
	   
	   //TODO LOAD
	   

   public GameTime loadMapGameTime(String map ,String time) {
	
	    String timer[] = time.split(",");
		int hora = Integer.valueOf(timer[0]);
		int minuto = Integer.valueOf(timer[1]);
		int segundo = Integer.valueOf(timer[2]);
	
		return new GameTime(plugin,map,hora,minuto,segundo);
   }	 
   
   
   public int loadCountdownMap() {
		
		FileConfiguration config = plugin.getConfig();
		return config.getInt("CountDownPreLobby");
  }	 
   
	   
   public List<Location> loadMapGenerators(String map) {
		FileConfiguration game = getGameConfig(map);
		List<Location> l = new ArrayList<>();
		if(game.contains("Generators.List")) {
			List<String> generators = game.getStringList("Generators.List");
			if(!generators.isEmpty()) {
				for(String loc : generators) {
					l.add(convertStringLocationToLocations(loc));
				}
			}
    	}
			
		return l;
	}      
   
   
   public List<Location> loadMapMobsGenerators(String map) {
		FileConfiguration game = getGameConfig(map);
		List<Location> l = new ArrayList<>();
		if(game.contains("Mobs-Generators.List")) {
			List<String> generators = game.getStringList("Mobs-Generators.List");
			if(!generators.isEmpty()) {
				for(String loc : generators) {
					l.add(convertStringLocationToLocations(loc));
				}
			}
    	}
			
		return l;
	}      
	     
	public List<GameTimeActions> loadGameTimeActions(String map) {
		FileConfiguration game = getGameConfig(map);
		List<GameTimeActions> list = new ArrayList<>();
			if(game.contains("Time-Actions")) {
				for (String key : game.getConfigurationSection("Time-Actions").getKeys(false)) {
					List<String> actions = game.getStringList("Time-Actions."+key+".List");
					list.add(new GameTimeActions(key,actions));
				}
			    
			}
		
		return list;
	}   
	
	public List<CuboidZone> loadCuboidZones(String map){
		FileConfiguration game = getGameConfig(map);
		List<CuboidZone> zones = new ArrayList<>();
		if(hasCuboidZones(map)) {
			List<String> list = game.getStringList("Cuboid-Zones.List");
			if(!list.isEmpty()) {
				for(String data : list) {
					String[] split = data.split(";");
					String coord1 = split[0];
					String coord2 = split[1];
					String status = split[2];
					 
					CuboidZone cz = new CuboidZone(convertStringLocationToLocations(coord1),convertStringLocationToLocations(coord2),convertStringToGameInteractions(status));
					zones.add(cz);
				}
			}
		}
		
		
	
		return zones;
	}
	  
   	//TODO OBJETIVOS
 	public GameObjetivesMG loadObjetivesOfGames(String map) {
   		FileConfiguration game = getGameConfig(map);
   		GameObjetivesMG go = new GameObjetivesMG();
   		
   		List<ObjetivesMG> l = new ArrayList<>();   		
   		if(hasObjetives(map)) {
   	   		List<String> des = new ArrayList<>();   		
   	   		des.add("Habilitaron los Objetivos pero no hay ninguno en la Config del Mapa.");
   	   		ObjetivesMG ghost = new ObjetivesMG();
			ghost.setObjetiveName("Mapa Con Objetivos Borrados");
			ghost.setPriority(1);
			ghost.setStartValue(1);
			ghost.setCurrentValue(0);
			ghost.setInCompleteValue(0);
			ghost.setInCompleteValue(0);
			ghost.setDescription(des);
			ghost.setObjetiveStatusType(ObjetiveStatusType.WAITING);
   			l.add(ghost);
   			
   			if(game.contains("Game-Objetives")) { 
   				
   	   			for (String key : game.getConfigurationSection("Game-Objetives").getKeys(false)) {
		   	   			int priority = game.getInt("Game-Objetives."+key+".Priority");
		   				List<String> description = game.getStringList("Game-Objetives."+key+".Description");
		   				String status = game.getString("Game-Objetives."+key+".Status");
		   				int startvalue = game.getInt("Game-Objetives."+key+".Start-Value");
		   				int currentvalue = startvalue;
		   				int completevalue = game.getInt("Game-Objetives."+key+".Complete-Value");
		   				int incompletevalue = game.getInt("Game-Objetives."+key+".Incomplete-Value");
		   	   				
		   				List<String> objetivecompletesmessage = game.getStringList("Game-Objetives."+key+".ObjetiveCompleteMessage");
		   				List<String> objetivecompleteactions = game.getStringList("Game-Objetives."+key+".ObjetiveCompleteActions");
		   		   		List<String> objetivecompleteplayeractions = game.getStringList("Game-Objetives."+key+".ObjetiveCompletePlayerActions");
		
		   				List<String> objetiveincompletesmessage = game.getStringList("Game-Objetives."+key+".ObjetiveIncompleteMessage");
		   				List<String> objetiveincompleteactions = game.getStringList("Game-Objetives."+key+".ObjetiveIncompleteActions");
		   		   		List<String> objetiveincompleteplayeractions = game.getStringList("Game-Objetives."+key+".ObjetiveIncompletePlayerActions");
		   		   		
		   		   		ObjetivesMG omg = new ObjetivesMG();
		
						omg.setObjetiveName(key.replaceAll("-"," ").replaceAll("_"," "));
						omg.setPriority(priority);
						omg.setStartValue(startvalue);
						omg.setCurrentValue(currentvalue);
						omg.setCompleteValue(completevalue);
						omg.setInCompleteValue(incompletevalue);
						omg.setDescription(description);
						omg.setObjetiveStatusType(ObjetiveStatusType.valueOf(status.toUpperCase()));
						omg.setObjetiveCompleteMessage(objetivecompletesmessage);
						omg.setObjetiveCompleteActions(objetivecompleteactions);
						omg.setObjetiveCompletePlayerActions(objetivecompleteplayeractions);
						omg.setObjetiveIncompleteMessage(objetiveincompletesmessage);
						omg.setObjetiveIncompleteActions(objetiveincompleteactions);
						omg.setObjetiveIncompletePlayerActions(objetiveincompleteplayeractions);
						
						l.add(omg);
   	   				}
		   	   		List<String> rewardcompleteprimarymessage = game.getStringList("Complete-All-Objetives-Primary.Message");
				 	List<String> rewardcompleteprimaryactions = game.getStringList("Complete-All-Objetives-Primary.Actions");
				 	List<String> rewardcompleteprimaryplayeractions = game.getStringList("Complete-All-Objetives-Primary.PlayerActions");
				  	 
				 	
				 	List<String> rewardcompletesecondarymessage = game.getStringList("Complete-All-Objetives-Secondary.Message");
					List<String> rewardcompletesecondaryactions = game.getStringList("Complete-All-Objetives-Secondary.Actions");
					List<String> rewardcompletesecondaryplayeractions = game.getStringList("Complete-All-Objetives-Secondary.PlayerActions");
		    
					List<List<String>> completeprimary = new ArrayList<>();
					List<List<String>> completesecondary = new ArrayList<>();
		
					completeprimary.add(rewardcompleteprimarymessage);
					completeprimary.add(rewardcompleteprimaryactions);
					completeprimary.add(rewardcompleteprimaryplayeractions);
					
					completesecondary.add(rewardcompletesecondarymessage);
					completesecondary.add(rewardcompletesecondaryactions);
					completesecondary.add(rewardcompletesecondaryplayeractions);
					
					go.setMapObjetives(hasObjetives(map));
					go.setNecessaryObjetivesPrimaryCompletes(isNecessaryObjetivePrimary(map));
					go.setNecessaryObjetivesSecondaryCompletes(isNecessaryObjetiveSedondary(map));
					go.setCompleteAllPrimaryObjetiveForReward(completeprimary);
					go.setCompleteAllSecondaryObjetiveForReward(completesecondary);
   	   			
   	   			}
   			
   			if(l.size() >= 1) {
   				if(l.stream().filter(o -> o.getObjetiveName().equals("Mapa Con Objetivos Borrados")).findFirst().isPresent()) {
   					l.remove(ghost);
   				}
   		     }
   		}
   		
   			
   		go.setObjetives(l);
			
   		return go;
   	}
 	
 	
	public void loadObjetivesOfGameDebug(String map) {
   		FileConfiguration game = getGameConfig(map);
   		
   		List<ObjetivesMG> l = new ArrayList<>();
   		
   		if(hasObjetives(map)) {
   			List<String> des = new ArrayList<>();   		
   	   		des.add("Habilitaron los Objetivos pero no hay ninguno en la Config del Mapa.");
   			ObjetivesMG ghost = new ObjetivesMG();
   			ghost.setObjetiveName("Mapa Con Objetivos Borrados");
   			ghost.setPriority(1);
   			ghost.setStartValue(1);
   			ghost.setCurrentValue(0);
   			ghost.setInCompleteValue(0);
   			ghost.setInCompleteValue(0);
   			ghost.setDescription(des);
   			ghost.setObjetiveStatusType(ObjetiveStatusType.WAITING);
   			l.add(ghost);
	   		if(game.contains("Game-Objetives")) {
	   			for (String key : game.getConfigurationSection("Game-Objetives").getKeys(false)) {
	   				int priority = game.getInt("Game-Objetives."+key+".Priority");
	   				List<String> description = game.getStringList("Game-Objetives."+key+".Description");
	   				String status = game.getString("Game-Objetives."+key+".Status");
	   				int startvalue = game.getInt("Game-Objetives."+key+".Start-Value");
	   				int currentvalue = startvalue;
	   				int completevalue = game.getInt("Game-Objetives."+key+".Complete-Value");
	   				int incompletevalue = game.getInt("Game-Objetives."+key+".Incomplete-Value");
	   				
	   				
	   				List<String> objetivecompletesmessage = game.getStringList("Game-Objetives."+key+".ObjetiveCompleteMessage");
	   				List<String> objetivecompleteactions = game.getStringList("Game-Objetives."+key+".ObjetiveCompleteActions");
	   		   		List<String> objetivecompleteplayeractions = game.getStringList("Game-Objetives."+key+".ObjetiveCompletePlayerActions");

	   				List<String> objetiveincompletesmessage = game.getStringList("Game-Objetives."+key+".ObjetiveIncompleteMessage");
	   				List<String> objetiveincompleteactions = game.getStringList("Game-Objetives."+key+".ObjetiveIncompleteActions");
	   		   		List<String> objetiveincompleteplayeractions = game.getStringList("Game-Objetives."+key+".ObjetiveIncompletePlayerActions");


	   				 
	   				System.out.println("LOG DE OBJETIVOS");
	   				System.out.println(key);
	   				System.out.println(description.toString());
	   				System.out.println(priority);
	   				System.out.println(objetivecompletesmessage.toString());
	   				System.out.println(objetivecompleteactions.toString());
	   				System.out.println(objetivecompleteplayeractions.toString());
	   				System.out.println(objetiveincompletesmessage.toString());
	   				System.out.println(objetiveincompleteactions.toString());
	   				System.out.println(objetiveincompleteplayeractions.toString());
	   				
	   				//ObjetivesMG omg = new ObjetivesMG(key.replaceAll("-"," ").replaceAll("_"," "),priority,values,valueinit,valuec,valuei,description, ObjetiveStatusType.valueOf(status.toUpperCase()),new HashMap<Player,Integer>());
	   				ObjetivesMG omg = new ObjetivesMG();

	   				omg.setObjetiveName(key.replaceAll("-"," ").replaceAll("_"," "));
	   				omg.setPriority(priority);
	   				omg.setStartValue(startvalue);
	   				omg.setCurrentValue(currentvalue);
	   				omg.setInCompleteValue(completevalue);
	   				omg.setInCompleteValue(incompletevalue);
	   				omg.setDescription(des);
	   				omg.setObjetiveStatusType(ObjetiveStatusType.valueOf(status.toUpperCase()));
	   				l.add(omg);
	   		}}
	   		
	   		if(l.size() > 1) {
   				if(l.stream().filter(o -> o.getObjetiveName().equals("Mapa Con Objetivos Borrados")).findFirst().isPresent()) {
   					l.remove(ghost);
   				}
   		     }
	   		
   		}
   		
   		
   		GameObjetivesMG go = new GameObjetivesMG();
   		for(ObjetivesMG o : go.getObjetives()) {
   			System.out.println("LOG DE OBJETIVOS LISTA "+o.getObjetiveName()+" "+o.getPriority()+" "+o.getCurrentValue()+" "+o.getCompleteValue()+" "+o.getObjetiveStatusType().toString());
   		}
   	
   		
   		return;
   	}
 	
	
	 
	//para reclamar recompensa y comprobar
 	public void isCompleteAllPrimaryObjetiveForReward(String map) {
 		GameInfo gi = plugin.getGameInfoPoo().get(map);
 		GameObjetivesMG gomg = gi.getGameObjetivesMg();
   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);
   				if(obj.getPriority() == 1 && obj.getObjetiveStatusType() != ObjetiveStatusType.COMPLETE) {
   					p.add(obj);
   			}}
   		}
   		
   		if(!p.isEmpty()) {
   			return;
   		}else {
   			if(!l.stream().filter(o -> o.getPriority() == 1).findFirst().isPresent()) {
   				return;
   			// condicional para evitar claimear doble 	
   			}else if(gomg.isObjetivesPrimaryComplete()) {
   			 return;	
   			}
   			 
   			gomg.setObjetivesPrimaryComplete(true);
   		
			List<String> rewardmessage = gomg.getCompleteAllPrimaryObjetiveForReward().get(0);
		 	List<String> rewardactions = gomg.getCompleteAllPrimaryObjetiveForReward().get(1);
		 	List<String> rewardplayer = gomg.getCompleteAllPrimaryObjetiveForReward().get(2);
		  	 
		 	
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				List<Player> players = ConvertStringToPlayer(ga.getAlivePlayers());
				
				
				for(Player player : players) {
					if(!rewardmessage.isEmpty()) {
					   for(String text : rewardmessage) {
	   	   				player.sendMessage(ChatColor.translateAlternateColorCodes('&',text.replace("%player%", player.getName())));
					}}
	   				
					ExecuteMultipleCommandsInConsole(player, rewardplayer);
				}
				ExecuteMultipleCommandsInConsole(null, rewardactions);
			}	
   		}
   		return;
 	}
 	
 	//para reclamar recompensa y comprobar
 	public void isCompleteAllSecondaryObjetiveForReward(String map) {
 		GameInfo gi = plugin.getGameInfoPoo().get(map);
 		
 		GameObjetivesMG gomg = gi.getGameObjetivesMg();

   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);
   				if(obj.getPriority() >= 2 && obj.getObjetiveStatusType() != ObjetiveStatusType.COMPLETE) {
   					p.add(obj);
   			}}
   		}
   		 
   		if(!p.isEmpty()) {
   			return;
   		}else{
   			if(!l.stream().filter(o -> o.getPriority() >= 2).findFirst().isPresent()) {
   				return;
   			}else if(gomg.isObjetivesSecondaryComplete()) {
      			 return;	
      		}

   			gomg.setObjetivesSecondaryComplete(true);
   			 
   			List<String> rewardmessage = gomg.getCompleteAllPrimaryObjetiveForReward().get(0);
		 	List<String> rewardactions = gomg.getCompleteAllPrimaryObjetiveForReward().get(1);
		 	List<String> rewardplayer = gomg.getCompleteAllPrimaryObjetiveForReward().get(2);
		  	
		  	
		  	if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				List<Player> players = ConvertStringToPlayer(ga.getParticipants());
				
				for(Player player : players) {
					if(!rewardmessage.isEmpty()) {
					   for(String text : rewardmessage) {
	   	   				player.sendMessage(ChatColor.translateAlternateColorCodes('&',text.replace("%player%", player.getName())));
					}}
	   				
					ExecuteMultipleCommandsInConsole(player, rewardplayer);
				}
				ExecuteMultipleCommandsInConsole(null, rewardactions);

		  	}
   		}
   		
   		return;
 	}
 	
 	//para mostrar a jugador
 	public boolean isAllPrimaryObjetivesComplete(Player player ,String map) {
   		GameInfo gi = plugin.getGameInfoPoo().get(map);
   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);
   				if(obj.getPriority() == 1 && obj.getObjetiveStatusType() != ObjetiveStatusType.COMPLETE) {
   					
   					p.add(obj);
   			}}
   		}
   		
   		if(!p.isEmpty()) {
   			for(int i = 0 ; i < p.size();i++) {
   				player.sendMessage(ChatColor.RED+"El Objetivo Primario "+p.get(i).getObjetiveName()+" no esta Completado "+p.get(i).getCurrentValue()+"/"+p.get(i).getCompleteValue());
   			}
   			return false;
   		}
 		return true;
 	}
 	
 	//para mostrar a jugador
	public boolean isAllSecondaryObjetivesComplete(Player player ,String map) {
   		GameInfo gi = plugin.getGameInfoPoo().get(map);
   		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
   		List<ObjetivesMG> p = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0 ; i < l.size();i++) {
   				ObjetivesMG obj = l.get(i);

   				if(obj.getPriority() >= 2 && obj.getObjetiveStatusType() != ObjetiveStatusType.COMPLETE) {
   					p.add(obj);
   				}}
   		}
   		
   		if(!p.isEmpty()) {
   			for(int i = 0 ; i < p.size();i++) {
   				player.sendMessage(ChatColor.RED+"El Objetivo Secundario "+p.get(i).getObjetiveName()+" no esta Completado "+p.get(i).getCurrentValue()+"/"+p.get(i).getCompleteValue());
   			}
   			return false;
   		}
 		return true;
 	}
   	
	//TODO OBJETIVES VALUE
	public void ObjetivesValue(String map, String name ,int value,String player) {
		
		if(!isMapinGame(map)) {
			sendMessageToUserAndConsole(null,"El Mapa "+map+" no esta en Juego no puedes editar Objetivos.");
			return;
		}
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		GameConditions gc = new GameConditions(plugin);
		
		if(!l.stream().filter(o -> o.getObjetiveName().equals(name)).findFirst().isPresent()) {
			sendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo  "+name+" no Existe");
			return;
		}else {
			
			ObjetivesMG obj = l.stream().filter(o -> o.getObjetiveName().equals(name)).findFirst().get();
			
			if(obj.getObjetiveStatusType() == ObjetiveStatusType.COMPLETE) {
				sendMessageToUserAndConsole(null,ChatColor.GOLD+map+" "+ChatColor.GREEN+"El Objetivo "+name+" alcanzo el Estado Completo.");
				return;
			}else if(obj.getObjetiveStatusType() == ObjetiveStatusType.INCOMPLETE) {
				sendMessageToUserAndConsole(null,ChatColor.GOLD+map+" "+ChatColor.GREEN+"El Objetivo "+name+" alcanzo el Estado Incompleto.");
				return;
			}else if(obj.getObjetiveStatusType() == ObjetiveStatusType.CONCLUDED) {
				sendMessageToUserAndConsole(null,ChatColor.GOLD+map+" "+ChatColor.GREEN+"El Objetivo "+name+" alcanzo el Estado Concluido.");
				return;
			}else if(obj.getObjetiveStatusType() == ObjetiveStatusType.CANCELLED) {
				sendMessageToUserAndConsole(null,ChatColor.GOLD+map+" "+ChatColor.GREEN+"El Objetivo "+name+" alcanzo el Estado Cancelado.");
				return;
			}else if(obj.getObjetiveStatusType() == ObjetiveStatusType.WAITING || obj.getObjetiveStatusType() == ObjetiveStatusType.WARNING || obj.getObjetiveStatusType() == ObjetiveStatusType.DANGER) {
				int vals =  obj.getStartValue();
				int val =  obj.getCurrentValue();
				int valc = obj.getCompleteValue();
				int vali = obj.getIncompleteValue();
					val = val + value;
				
				
					// VALOR START: 10  VALOR COMPLETE: 0
				if(vals > valc) {
					
					//SI ES MENOR O IGUAL A COMPLETO
					if(val <= valc) {
						
						//SI ES WAIT PASA A COMPLETO SI ES DANGER O WARNING A CONCLUDED
						if(obj.getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
							obj.setObjetiveStatusType(ObjetiveStatusType.COMPLETE);
							
						}else {
							obj.setObjetiveStatusType(ObjetiveStatusType.CONCLUDED);
						}
						obj.setCurrentValue(obj.getCompleteValue());
						ObjetiveGeneralActionsComplete(map,obj,gi);
						
						//SI ES NULL CAMBIA A NORMAL EL OBJETIVO SINO ES NULL LO CONVIERTE A PLAYER Y SE CHECA QUE ESTE EN JUEGO SI ES NULL O NO ESTA SIGUE SIENDO UN CAMBIO NORMAL
						if(player != null) {
							Player target = Bukkit.getPlayerExact(player);
							if(target != null && gc.isPlayerinGame(target)) {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Completado gracias a "+target.getName()+" "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+ChatColor.GREEN+" Completado por "+target.getName()+" "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");

								if(obj.getPlayerInteractions().containsKey(target)) {
									obj.getPlayerInteractions().put(target,obj.getPlayerInteractions().get(target)+value);
								}else {
									obj.getPlayerInteractions().put(target,value);
								}
							}else {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");
							}
						}else {
							//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");
							sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");

						}
						
					
					}else if(val >= vali) {
						if(obj.getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
							obj.setObjetiveStatusType(ObjetiveStatusType.INCOMPLETE);
						}else {
							obj.setObjetiveStatusType(ObjetiveStatusType.CONCLUDED);
						}
						obj.setCurrentValue(obj.getIncompleteValue());
						ObjetiveGeneralActionsIncomplete(map,obj,gi);
					}else {
						obj.setCurrentValue(val);
						//SendMessageToAllPlayersInGame(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Cambiado "+ChatColor.GOLD+val+"/"+obj.getCompleteValue());
					
						if(player != null) {
							Player target = Bukkit.getPlayerExact(player);
							if(target != null && gc.isPlayerinGame(target)) {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Cambiado gracias a "+target.getName()+" "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+ChatColor.GREEN+" Cambio por "+target.getName()+" "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");

								if(obj.getPlayerInteractions().containsKey(target)) {
									obj.getPlayerInteractions().put(target,obj.getPlayerInteractions().get(target)+value);
								}else {
									obj.getPlayerInteractions().put(target,value);
								}
							}else {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Cambiado "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" cambio "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");

							}
						}else {
							//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Cambiado "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");
							sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" cambio "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");

						}
					}
					//VALOR START: 0  VALOR COMPLETE: 10
				}else if(vals < valc) {
					if(val >= valc) {
						if(obj.getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
							obj.setObjetiveStatusType(ObjetiveStatusType.COMPLETE);
						}else {
							obj.setObjetiveStatusType(ObjetiveStatusType.CONCLUDED);
						}
						obj.setCurrentValue(obj.getCompleteValue()); 
						ObjetiveGeneralActionsComplete(map,obj,gi);
						if(player != null) {
							Player target = Bukkit.getPlayerExact(player);
							if(target != null && gc.isPlayerinGame(target)) {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Completado gracias a "+target.getName()+" "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+ChatColor.GREEN+" Completado por "+target.getName()+" "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");

								if(obj.getPlayerInteractions().containsKey(target)) {
									obj.getPlayerInteractions().put(target,obj.getPlayerInteractions().get(target)+value);
								}else {
									obj.getPlayerInteractions().put(target,value);
								}
							}else {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");

							}
						}else {
							//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");
							sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" Completado "+ChatColor.DARK_PURPLE+"["+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.DARK_PURPLE+"]");

						}
						
					}else if(val <= vali) {
						if(obj.getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
							obj.setObjetiveStatusType(ObjetiveStatusType.INCOMPLETE);
						}else {
							obj.setObjetiveStatusType(ObjetiveStatusType.CONCLUDED);
						}
						obj.setCurrentValue(obj.getIncompleteValue());
						ObjetiveGeneralActionsIncomplete(map,obj,gi);
					}else {
						obj.setCurrentValue(val);
						if(player != null) {
							Player target = Bukkit.getPlayerExact(player);
							if(target != null && gc.isPlayerinGame(target)) {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Cambiado gracias a "+target.getName()+" "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+ChatColor.GREEN+" Cambio por "+target.getName()+" "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");
								
								if(obj.getPlayerInteractions().containsKey(target)) {
									obj.getPlayerInteractions().put(target,obj.getPlayerInteractions().get(target)+value);
								}else {
									obj.getPlayerInteractions().put(target,value);
								}
							}else {
								//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Cambiado "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");
								sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" cambio "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");

							}
						}else {
							//SendMessageToAllPlayersInMap(map, ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getNombre()+ChatColor.GREEN+" a Cambiado "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");
							sendTittleToAllPlayersInMap(map, "", ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+obj.getObjetiveName()+" cambio "+ChatColor.AQUA+"("+ChatColor.GREEN+val+ChatColor.GOLD+"/"+ChatColor.GREEN+obj.getCompleteValue()+ChatColor.AQUA+")");

						}
					}
				}
				
			   		isCompleteAllPrimaryObjetiveForReward(map);
					isCompleteAllSecondaryObjetiveForReward(map);
			}else {
				sendMessageToUserAndConsole(null,ChatColor.GOLD+map+" "+ChatColor.GREEN+"El Objetivo "+ChatColor.RED+name+ChatColor.GREEN+" fue Activado pero no genera Cambios.");

			}
			
		}
	
		
	
		return ;	
	}
	

	public void ObjetiveGeneralActionsComplete(String map,ObjetivesMG oj,GameInfo gi) {
		
		List<String> objetivesmg = oj.getObjetiveCompleteMessage();
   		List<String> objetivesaction = oj.getObjetiveCompleteActions();
   		List<String> objetivesactionpl = oj.getObjetiveCompletePlayerActions();
		
   		 
   		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			List<Player> players = ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player p : players) {
		   			if(!objetivesmg.isEmpty()) {
		   				p.sendMessage(ChatColor.GREEN+"Objetivo "+ChatColor.GOLD+oj.getObjetiveName()+ChatColor.GREEN+" fue Completado.");
		   				for(String text : objetivesmg) {
		   	   				p.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
		   		}}
		   		ExecuteMultipleCommandsInConsole(p,objetivesactionpl);
			}
			ExecuteMultipleCommandsInConsole(null,objetivesaction);
   		}	
		
   		sendMessageToAllPlayersInMap(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+oj.getObjetiveName()+ChatColor.GREEN+" a sido Completado "+ChatColor.GOLD+oj.getCompleteValue()+"/"+oj.getCompleteValue());
	}
	 
	public void ObjetiveGeneralActionsIncomplete(String map,ObjetivesMG oj,GameInfo gi)  {
		List<String> objetivesmg = oj.getObjetiveIncompleteMessage();
   		List<String> objetivesaction = oj.getObjetiveIncompleteActions();
   		List<String> objetivesactionpl = oj.getObjetiveIncompletePlayerActions();
		
   		
   		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			List<Player> players = ConvertStringToPlayer(ga.getAlivePlayers());
				for(Player p : players) {
		   			if(!objetivesmg.isEmpty()) {
		   				p.sendMessage(ChatColor.RED+"Objetivo "+ChatColor.GOLD+oj.getObjetiveName()+ChatColor.RED+" no fue Completado.");
		   				for(String text : objetivesmg) {
		   	   				p.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
		   		}}
		   		ExecuteMultipleCommandsInConsole(p,objetivesactionpl);
			}
			ExecuteMultipleCommandsInConsole(null,objetivesaction);
   		}
   		sendMessageToAllPlayersInMap(map, ChatColor.RED+"El progreso del Ojetivo "+ChatColor.GOLD+oj.getObjetiveName()+ChatColor.RED+" no fue Completado "+ChatColor.GOLD+oj.getIncompleteValue()+"/"+oj.getIncompleteValue());
	}
	

	  

	//TODO TYPE
	public void ObjetiveChangeType(String map, String name, ObjetiveStatusType ob,String player) {
		
		if(!isMapinGame(map)) {
			sendMessageToUserAndConsole(null,"El Mapa "+map+" no esta en Juego no puedes editar el tipo de Objetivos.");
			return;
		}
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);
 		GameObjetivesMG gomg = gi.getGameObjetivesMg();

		List<ObjetivesMG> l = gomg.getObjetives();
		
		
		if(!l.stream().filter(o -> o.getObjetiveName().equals(name)).findFirst().isPresent()) {
			sendMessageToUserAndConsole(null,ChatColor.RED+"El Objetivo "+ChatColor.GOLD+name+ChatColor.RED+" no Existe.");
			return;
		}else{
			ObjetivesMG mo = (ObjetivesMG) l.stream().filter(o -> o.getObjetiveName().equals(name)).findFirst().get();
			GameConditions gc = new GameConditions(plugin);
			switch(ob) {
			case WAITING:
				mo.setObjetiveStatusType(ObjetiveStatusType.WAITING);
				sendMessageToAllPlayersInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" cambio a Modo Esperando.");
				
				if(player != null) {
					Player target = Bukkit.getPlayerExact(player);
					if(target != null && gc.isPlayerinGame(target)) {
						sendMessageToAllPlayersOpInMap(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+mo.getObjetiveName()+ChatColor.GREEN+" cambio a Esperando gracias a "+target.getName());

					}
				}
				
				break;
			case COMPLETE:
				mo.setObjetiveStatusType(ObjetiveStatusType.COMPLETE);
				sendMessageToAllPlayersInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" fue Completado.");
				mo.setCurrentValue(mo.getCompleteValue());
				ObjetiveGeneralActionsComplete(map,mo,gi);
				isCompleteAllPrimaryObjetiveForReward(map);
				isCompleteAllSecondaryObjetiveForReward(map);
			
				if(player != null) {
					Player target = Bukkit.getPlayerExact(player);
					if(target != null && gc.isPlayerinGame(target)) {
						sendMessageToAllPlayersInMap(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+mo.getObjetiveName()+ChatColor.GREEN+" fue Completado gracias a "+target.getName()+" "+ChatColor.GOLD+mo.getCompleteValue()+"/"+mo.getCompleteValue());

						mo.getPlayerInteractions().put(target,mo.getPlayerInteractions().get(target)+1);
						
					}
				}
				
				break;
			case INCOMPLETE:
				mo.setObjetiveStatusType(ObjetiveStatusType.INCOMPLETE);
				sendMessageToAllPlayersInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" cambio a Modo Incompleto.");
				mo.setCurrentValue(mo.getIncompleteValue());
				ObjetiveGeneralActionsIncomplete(map,mo,gi);
				
				if(player != null) {
					Player target = Bukkit.getPlayerExact(player);
					if(target != null && gc.isPlayerinGame(target)) {
						sendMessageToAllPlayersInMap(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+mo.getObjetiveName()+ChatColor.GREEN+" cambio a Incompleto gracias a "+target.getName());

						mo.getPlayerInteractions().put(target,mo.getIncompleteValue());
						
					}
				}
				
				break;
			case DANGER:
				mo.setObjetiveStatusType(ObjetiveStatusType.DANGER);
				sendMessageToAllPlayersOpInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" cambio a Modo Peligro.");
				break;
			case UNKNOW:
				mo.setObjetiveStatusType(ObjetiveStatusType.UNKNOW);
				sendMessageToAllPlayersOpInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" cambio a Modo Desconocido.");
				
				if(player != null) {
					Player target = Bukkit.getPlayerExact(player);
					if(target != null && gc.isPlayerinGame(target)) {
						sendMessageToAllPlayersOpInMap(map, ChatColor.GREEN+"El progreso del Ojetivo "+ChatColor.GOLD+mo.getObjetiveName()+ChatColor.GREEN+" cambio a Desconocido gracias a "+target.getName());

					}
				}
				break;
			case WARNING:
				mo.setObjetiveStatusType(ObjetiveStatusType.WARNING);
				sendMessageToAllPlayersOpInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" cambio a Modo Advertencia.");
				break;
			case RESET:
				mo.setObjetiveStatusType(ObjetiveStatusType.WAITING);
				sendMessageToAllPlayersOpInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" se Reseteo.");
				mo.setCurrentValue(mo.getStartValue());
				if(mo.getPriority() == 1) {
					gomg.setObjetivesPrimaryComplete(false);
				}else if(mo.getPriority() >= 2){
					gomg.setObjetivesSecondaryComplete(false);
				}
				
				break;
			case CONCLUDED:
				mo.setObjetiveStatusType(ObjetiveStatusType.CONCLUDED);
				sendMessageToAllPlayersOpInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" a Concluido.");
				mo.setCurrentValue(mo.getCompleteValue());
				ObjetiveGeneralActionsComplete(map,mo,gi);
				break;
			case HIDE:
				mo.setObjetiveStatusType(ObjetiveStatusType.HIDE);
				sendMessageToAllPlayersInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" esta Oculto.");
				break;
			case CANCELLED:
				mo.setObjetiveStatusType(ObjetiveStatusType.CANCELLED);
				sendMessageToAllPlayersOpInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" esta Cancelado.");
				break;
			default:
				sendMessageToAllPlayersOpInMap(map, ChatColor.GOLD+"El Objetivo "+ChatColor.GREEN+name+ChatColor.GOLD+" no tuvo Cambios.");
			}
		}
		 
		
		
	}
   
	public void saveMapFrequencysmg(GameInfo gi) {
		
		FileConfiguration mst = plugin.getMapFrequency();
		
		int revive = 0;
		int deads = 0; 
		
		if(gi instanceof GameAdventure) {
			
			GameAdventure ga = (GameAdventure) gi;
			String mapname = gi.getMapName();
			
			Map<Player,PlayerInfo> pi = plugin.getPlayerInfoPoo();
			
			List<Map.Entry<Player, PlayerInfo>> list = new ArrayList<>(pi.entrySet());
			for (Map.Entry<Player, PlayerInfo> e : list) {
				if(e.getValue().getMapName().equals(mapname)) {
					GamePoints  pli = e.getValue().getGamePoints();
					revive += pli.getHelpRevive();
					deads += pli.getDeads();
				}
			}
			
			
			if(mst.contains("MapFrequency."+mapname)) {
				
				int timeplayed = mst.getInt("MapFrequency."+mapname+".Times-Played");
				int participating = mst.getInt("MapFrequency."+mapname+".Participating-Players");
				int winningplayed = mst.getInt("MapFrequency."+mapname+".Winning-Players");
				int reviveplayer = mst.getInt("MapFrequency."+mapname+".Revive-Players");
				int deadplayer = mst.getInt("MapFrequency."+mapname+".Dead-Players");
				
				mst.set("MapFrequency."+mapname+".Times-Played",timeplayed+1);
				mst.set("MapFrequency."+mapname+".Participating-Players",participating+gi.getParticipants().size());
				mst.set("MapFrequency."+mapname+".Winning-Players",winningplayed+ga.getArrivePlayers().size());
				mst.set("MapFrequency."+mapname+".Revive-Players",reviveplayer+revive);
				mst.set("MapFrequency."+mapname+".Dead-Players",deadplayer+deads);
			}else {
				mst.set("MapFrequency."+mapname+".Times-Played",1);
				mst.set("MapFrequency."+mapname+".Participating-Players",gi.getParticipants().size());
				mst.set("MapFrequency."+mapname+".Winning-Players",ga.getArrivePlayers().size());
				mst.set("MapFrequency."+mapname+".Revive-Players",revive);
				mst.set("MapFrequency."+mapname+".Dead-Players",deads);
				
			
			}
			plugin.getMapFrequency().save();
			plugin.getMapFrequency().reload();
		}
		
	
		
	}
	
	//TODO COMPLEJIDAD
	public String calcularComplejidad(int participantes,int ganadores,int revividos,int muertes) {
	    
    	double ganadoress = (double)ganadores / (double)participantes * 100 ;
    	double perdedores = 100 - ((double)ganadores / (double)participantes * 100) ;
    	double muertos = (double)muertes / (double)participantes * 100;
    	double revividoss = (double)revividos / (double)participantes * 100;
    	double exito = (double)ganadores / ((double)ganadores + (double)muertes + (double)revividos) * 100;
    	
    	    return "Ganadores: "+(int)ganadoress+"% Perdedores: "+(int)perdedores+"% Muertos: "+(int)muertos+"% Revividos: "+(int)revividoss+"% Exito: "+(int)exito+"%"; // Convertir a porcentaje
    }
    
   	//mg objetive-primary complete 1
    //mg objetive-secondary complete 1
	//mg objetive-primary incomplete 1
    //mg objetive-secondary incomplete 1
	//X ! >⨉  ?  ✔

	public int getAmountOfObjetivesComplete(List<ObjetivesMG> l) {
		int actual = 0;
   		List<String> complete = new ArrayList<>();
   		
   		if(!l.isEmpty()) {
   			for(int i = 0;i<l.size();i++) {
   				if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.COMPLETE) {
   					complete.add(l.get(i).getObjetiveName());
   		  }}}
   		
   		actual = complete.size();
   		return actual ;
	}
	
	//TODO CONVERT STRING TO PLAYER IF IS ONLY ONE 
	public Player ConvertStringToPlayerAlone(String name) {return Bukkit.getPlayerExact(name);}
	
	//TODO STRING TO PLAYER
	public List<Player> ConvertStringToPlayer(List<String> list){
		List<Player> l = new ArrayList<>();
		if(!list.isEmpty()) {
			for(int i = 0;i < list.size();i++) {
	   			Player user = Bukkit.getServer().getPlayerExact(list.get(i));
	   			l.add(user);
	   		}
		}
		
		return l ;
	}
	 
	public void ExecuteMultipleCommandsInConsole(Player player,List<String> l) {
		
		if(!l.isEmpty()) {
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			for(int i = 0 ; i < l.size(); i++) {
				String texto = l.get(i);
				if(texto.contains("%player%")) {
					if(player != null) {
						Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",player.getName())));
					}
				}else{
					Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto));
				}
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
			System.out.println("LOG DIALOGOS: No existe "+id);
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
	
	//TODO OBTENER KIT DE CARTEL
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
	
	//TODO VER SI USA UN COMANDO DE LUCKPERMS Y SI TIENE PERMISO
	public boolean hasPlayerPermissionByLuckPerms(Player player ,String text) {
		
		if(!text.contains("permission set")) {
			return false;
		}else {
				String[] split = text.split(" ");
				String perm = split[5];
				if(player.hasPermission(perm)) {
					 Bukkit.getConsoleSender().sendMessage(Utils.colorText("&cEl Jugador &a%player% &cya tiene el Permiso &6%perm% &c(Omitiendo).".replace("%player%", player.getName()).replace("%perm%", perm))); 
					return false;
				}else{
					return true;
				}
		}
			
		
	}
	
	public GameInteractions convertStringToGameInteractions(String text) {
		GameInteractions gi = null;
		Map<String,GameInteractions> map = new HashMap<>();
		map.put("CANPLACE", GameInteractions.CANPLACE);
		map.put("CANBREAK", GameInteractions.CANBREAK);
		map.put("CANMODIFY", GameInteractions.CANMODIFY);
		if(!map.containsKey(text.toUpperCase())) {
			sendMessageToConsole("No existe "+text+" usa CANPLACE o CANBREAK colocando CANPLACE por default.");
			gi = GameInteractions.CANPLACE;
		}else {
			gi = map.get(text.toUpperCase());
		}
		
		return gi;
	} 
	
	public Location convertStringLocationToLocations(String location) {
		String[] split = location.split("/");
		String m = split[0];
		World world = Bukkit.getWorld(m);
		if(world == null) {
			world = Bukkit.getServer().getWorlds().get(0);
			sendMessageToConsole("El Mundo "+m+" no existe , se procede a colocar el Primer mundo del servidor como reemplazo junto con las coordenadas Otorgadas.");
		}
		double x = Double.valueOf(split[1]);
		double y = Double.valueOf(split[2]);
		double z = Double.valueOf(split[3]);
		return new Location(world,x,y,z);
	}

	
	public void checkGenerator(GameInfo gi) {
		
		
		List<Location> locations = gi.getGenerators();
		if(locations.isEmpty()) {
			sendMessageToConsole(ChatColor.YELLOW+"No hay Generadores en el Mapa.");
			return;
		}
		String comments = ChatColor.AQUA+"GENERADORES DE MAPA "+ ChatColor.GOLD+gi.getMapName()+" ";
		
		for(Location loc : locations) {
			Block a = loc.getBlock();
			Block b = a.getRelative(0,-1, 0);
			
			if(a.getType() == Material.GREEN_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";
				
			}else if(a.getType() == Material.BLUE_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.ORANGE_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.LIGHT_GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.WHITE_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.BLACK_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.RED_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.PURPLE_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.LIME_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.MAGENTA_CONCRETE && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.NETHERITE_BLOCK && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.DIAMOND_BLOCK && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.EMERALD_BLOCK && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.IRON_BLOCK && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else if(a.getType() == Material.GOLD_BLOCK && b.getType() == Material.BEDROCK) {
				comments = comments+ChatColor.GREEN+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}else {
				comments = comments+ChatColor.RED+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+ChatColor.YELLOW+"-";

			}
		}
		
			comments = comments+ChatColor.RED+" las coordenadas en Rojo no tienen los Bloques Correctos.";
			sendMessageToConsole(comments);

		
		
	}
	
	//TODO NEXO
	//PLAYER VERSION
	public boolean isInsideOfLocations(Location player , Location point1 , Location point2) {
		
		if(!(point1.getWorld().getName().equals(point2.getWorld().getName()))) return false;
		
			double minX = Math.min(point1.getX(),point2.getX());
			double minY = Math.min(point1.getY(),point2.getY());
			double minZ = Math.min(point1.getZ(),point2.getZ());
			
			double maxX = Math.max(point1.getX(), point2.getX())+1;
			double maxY = Math.max(point1.getY(), point2.getY());//quite un +1 
			double maxZ = Math.max(point1.getZ(), point2.getZ())+1;

			return (maxX >= player.getX() && player.getX() >= minX) &&
				   (maxY >= player.getY() && player.getY() >= minY) &&
				   (maxZ >= player.getZ() && player.getZ() >= minZ);
		
	}
	
	public boolean isBlockInside(Material m , Location point1 , Location point2) {
		
		if(point1.distance(point2) > 50) return false;
		if(!(point1.getWorld().getName().equals(point2.getWorld().getName()))) return false;
		
			String world = point1.getWorld().getName();
			double minX = Math.min(point1.getX(),point2.getX());
			double minY = Math.min(point1.getY(),point2.getY());
			double minZ = Math.min(point1.getZ(),point2.getZ());
			
			double maxX = Math.max(point1.getX(), point2.getX())+1;
			double maxY = Math.max(point1.getY(), point2.getY());//quite un +1 
			double maxZ = Math.max(point1.getZ(), point2.getZ())+1;

			
			for(int x = (int) minX; x <= maxX;x++) {
				for(int y = (int) minY; y <= maxY;y++) {
					for(int z = (int) minZ; z <= maxZ;z++) {
						Location l = new Location(Bukkit.getWorld(world),x,y,z);
						if(m == l.getBlock().getType()) return true;
					}
				}
			}
			return false;
		
	}
	
	public boolean isBlock(Material m , Location point1) {
		return point1.getBlock().getType() == m ;
	
	}
	
	public boolean isEntityAbove(Location loc) {
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> ner = getNearbyEntities(loc, 5);
		for(Entity e : ner) {
		
			Block b = e.getLocation().getBlock();
			Block r = b.getRelative(0,-1,0);
			if(r.getLocation().equals(loc)) {
				if(e.getType() == EntityType.PLAYER) continue;
				   entities.add(e);
				   return true;
			}
		}
		return false;
	}
	
	public int getAmountOfEntityAboveBlock(Location loc) {
	  
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> ner = getNearbyEntities(loc, 5);
		for(Entity e : ner) {
			Block b = e.getLocation().getBlock();
			Block r = b.getRelative(0,-1,0);
			if(r.getLocation().equals(loc)) {
				if(e.getType() == EntityType.PLAYER) continue;
				 entities.add(e);
			}
		}
	
		
		return entities.size();
	}
	
	
	public int getEntitysAmountInZone(Location loc1 , Location loc2) {
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : loc1.getWorld().getEntities()) {
			if(isInsideOfLocations(e.getLocation(), loc1, loc2)) {
				if(e.getType() == EntityType.PLAYER) continue;
				 entities.add(e);
			}
		}
		return entities.size();
	}
	
	
	public int getSpecifictEntitysAmountInZone(EntityType type ,Location loc1 , Location loc2) {
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : loc1.getWorld().getEntities()) {
			if(isInsideOfLocations(e.getLocation(), loc1, loc2)) {
				if(e.getType() != type) continue; 
				 entities.add(e);
			}
		}
		return entities.size();
	}
	
	public List<Entity> getNearbyEntities(Location l , int size){
			
			List<Entity> entities = new ArrayList<Entity>();
			for(Entity e : l.getWorld().getEntities()) {
			
				if(l.distance(e.getLocation()) <= size) {
					if(e.getType() == EntityType.PLAYER) continue;
					entities.add(e);
				}
			}
			return entities;
	}
	
	public void mgfill(Material m , Location point1 , Location point2,boolean blockbreak) {
		
		
		
			String world = point1.getWorld().getName();
			double minX = Math.min(point1.getX(),point2.getX());
			double minY = Math.min(point1.getY(),point2.getY());
			double minZ = Math.min(point1.getZ(),point2.getZ());
			  
			double maxX = Math.max(point1.getX(), point2.getX());
			double maxY = Math.max(point1.getY(), point2.getY());
			double maxZ = Math.max(point1.getZ(), point2.getZ());

			
			
			for(int x = (int) minX; x <= maxX;x++) {
				for(int y = (int) minY; y <= maxY;y++) {
					for(int z = (int) minZ; z <= maxZ;z++) {
						Location l = new Location(Bukkit.getWorld(world),x,y,z);
						Block b = l.getBlock();
						b.setType(m);
						if(blockbreak) {
							b.getDrops().clear();
							b.breakNaturally();
						}
					}
				}
			}
		
		
	}
	
	
	//TODO GET VERSION PLAYER AT MG
	public void getPlayerVersion(Player player,String targ) {
		
		
		if(Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
			//
			
			Player target = Bukkit.getPlayerExact(targ);
			@SuppressWarnings("rawtypes")
			ViaAPI api = Via.getAPI(); // Get the API
	
			if(target != null) {
				@SuppressWarnings("unchecked")
				int version  = api.getPlayerVersion(target); // Get the protocol version; 
				
					if(player != null) {
						if(player == target) {
							player.sendMessage(ChatColor.GOLD+"La version de tu minecraft es: "+ChatColor.GREEN+ConvertProtocolVersion(version));
							return;
						}else {
							player.sendMessage(ChatColor.GOLD+"La version de minecraft de "+ChatColor.GREEN+target.getName()+ChatColor.GOLD+" es: "+ChatColor.GREEN+ConvertProtocolVersion(version));
							return;
						}
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"La version de minecraft de "+ChatColor.GREEN+target.getName()+ChatColor.GOLD+" es: "+ChatColor.GREEN+ConvertProtocolVersion(version));
					}
				}else {
					if(player != null) {
						player.sendMessage(ChatColor.RED+" El jugador no esta en Linea o el nombre es Incorrecto.");
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" El jugador no esta en Linea o el nombre es Incorrecto.");
					}
					
				}
				
			}else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" ViaVersion no encontrado para MG");
			}
	}

	
	//TODO MG VERSION (
	public String ConvertProtocolVersion(int protocol) {
		String version = "";
		
		switch(protocol) {
			case 47:
				version = "1.8 - 1.8.8";
			break;
			case 107:
				version = "1.9";
			break;
			case 110:
				version = "1.9.3 - 1.9.4";
			break;
			case  210:
				version = "1.10";
			break;
			case  315:
				version = "1.11";
			break;
			case  316:
				version = "1.11.1 - 1.11.2";
			break;
			case  335:
				version = "1.12";
			break;
			case  338:
				version = "1.12.1";
			break;
			case  340:
				version = "1.12.1";
			break;
			case  393:
				version = "1.13";
			break;
			case  477:
				version = "1.14";
			break;
			case  480:
				version = "1.14.1";
			break;
			case  485:
				version = "1.14.2";
			break;
			case  490:
				version = "1.14.3";
			break;
			case  498:
				version = "1.14.4";
			break;
			case  573:
				version = "1.15";
			break;
			case  575:
				version = "1.15.1";
			break;
			case  578:
				version = "1.15.2";
			break;
			case  735:
				version = "1.16";
			break;
			case  736:
				version = "1.16.1";
			break;
			case  751:
				version = "1.16.2";
			break;
			case  753:
				version = "1.16.3";
			break;
			case  754:
				version = "1.16.4";
			break;
			case  755:
				version = "1.17";
			break;
			case  756:
				version = "1.17.1";
			break;
			case  757:
				version = "1.18";
			break;
			case  758:
				version = "1.18.2";
			break;
			case  759:
				version = "1.19";
			break;
			case  760:
				version = "1.19.1";
			break;
			case  762:
				version = "1.19.4";
			break;
			case  763:
				version = "1.20 - 1.20.1";
			break;
			case  764:
				version = "1.20.2";
			break;
			case  765:
				version = "1.20.3 - 1.20.4";
			break;
			case  766:
				version = "1.20.5";
			break;
			default:
			version = "Esta usando una version Snapshot.";	
		}
		return version;
	}
	
	
	public boolean hasPlayerTempCooldown(Player player , int time) {
		if(plugin.getTempCooldown().containsKey(player)){
			long timeleft = plugin.getTempCooldown().get(player) / 1000 + time - System.currentTimeMillis() / 1000;
			if(timeleft > 0) {
				player.sendMessage(ChatColor.RED+"Cooldown: "+ChatColor.YELLOW+"Debes esperar "+ChatColor.GREEN+timeleft+ChatColor.YELLOW+" para volver a usarlo.");
				return true;
			}
			plugin.getTempCooldown().remove(player);
		}
		
		return false;
				
 	}
	
	public void setPlayerTempCooldown(Player player) {
		plugin.getTempCooldown().put(player,  System.currentTimeMillis());
		return;
	}
	
	
	@SuppressWarnings("deprecation")
	public void turret(LivingEntity ent) {
		
		Location loc = ent.getLocation();
		Location loc2 = ent.getLocation();
		Block b = ent.getLocation().getBlock();
		Block under = b.getRelative(0,-1,0);
		Block under2 = b.getRelative(0,-2,0);
		Block under3 = b.getRelative(0,-3,0);
		
		
		if(under.getType() == Material.LODESTONE && under2.getType() == Material.BEACON && under3.getType() == Material.WHITE_CONCRETE) {
		
			Arrow aw = (Arrow) loc.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
			Arrow aw2 = (Arrow) loc2.getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.ARROW);
			aw.setCritical(true);
			
			aw.setKnockbackStrength(1);
			aw.setFireTicks(1200);
			aw.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
			aw.setCustomName("Torreta");
			aw2.setCritical(true);
			aw2.setKnockbackStrength(1);
			aw2.setFireTicks(1200);
			aw2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
			aw2.setCustomName("Torreta");
			aw.setShooter(ent);
			aw2.setShooter(ent);
			if(ent instanceof Player) {
				Player player = (Player) ent;
					player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 20.0F, 1F);
			}else{
				RayTraceResult rt = ent.getWorld().rayTraceEntities(ent.getEyeLocation().add(ent.getLocation().getDirection()),ent.getLocation().getDirection() , 100.0D);
          		if(rt != null && rt.getHitEntity() != null) {
          			if(rt.getHitEntity().getType() == EntityType.PLAYER) {
          				ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,/*duration*/ 30*20,/*amplifier:*/20, false ,false,true));
        				
          			}
          		}
				
			}
			
		}
	
	}
	

	public void blockPotion(LivingEntity e) {
		Block b = e.getLocation().getBlock();
		Block under = b.getRelative(0,-1,0);
		Block under2 = b.getRelative(0,-2,0);
		
		
		if(under.getType() == Material.GREEN_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.POISON,/*duration*/ 15*20,/*amplifier:*/20, false ,false,true));
			
		}else if(under.getType() == Material.RED_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE,/*duration*/ 15*20,/*amplifier:*/25, false ,false,true));
			
		}else if(under.getType() == Material.LIME_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 15*20,/*amplifier:*/25, false ,false,true));
			
		}else if(under.getType() == Material.LIGHT_BLUE_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,/*duration*/ 15*20,/*amplifier:*/15, false ,false,true));

		}else if(under.getType() == Material.BLUE_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,/*duration*/ 15*20,/*amplifier:*/15, false ,false,true));

		}else if(under.getType() == Material.WHITE_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,/*duration*/ 20*20,/*amplifier:*/3, false ,false,true));

		}else if(under.getType() == Material.PINK_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH,/*duration*/ 20*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.YELLOW_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,/*duration*/ 20*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.GRAY_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,/*duration*/ 25*20,/*amplifier:*/25, false ,false,true));
			
		}else if(under.getType() == Material.LIGHT_GRAY_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,/*duration*/ 25*20,/*amplifier:*/10, false ,false,true));
			
		}else if(under.getType() == Material.ORANGE_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,/*duration*/ 25*20,/*amplifier:*/10, false ,false,true));
			
		}else if(under.getType() == Material.BLACK_CONCRETE && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,/*duration*/ 25*20,/*amplifier:*/10, false ,false,true));

		}else if(under.getType() == Material.RED_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,/*duration*/ 25*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.WHITE_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,/*duration*/ 35*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.YELLOW_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,/*duration*/ 35*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.GRAY_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,/*duration*/ 35*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.PINK_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,/*duration*/ 10*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.BLUE_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,/*duration*/ 15*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.GREEN_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,/*duration*/ 15*20,/*amplifier:*/25, false ,false,true));

		}else if(under.getType() == Material.ORANGE_TERRACOTTA && under2.getType() == Material.BEACON) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,/*duration*/ 15*20,/*amplifier:*/25, false ,false,true));

		}
		
		return;
	}
	

	public Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }
	
	public void createLineOfParticle(Location Start, Location End,Particle part) {
		 Vector vector = getDirectionBetweenLocations(Start, End);
         for (double i = 1; i <= Start.distance(End); i += 0.5) {
             vector.multiply(i);
             Start.add(vector);
             Start.getWorld().spawnParticle(part, Start,
     				/* NUMERO DE PARTICULAS */100, 0, 0, 0, /* velocidad */0, null, true);
            // Start.getWorld().spigot().playEffect(Start, Effect.FLAME, 0, 0, 0, 0, 0, 1, 0, 100);
             Start.subtract(vector);
             vector.normalize();
         }
	}
	
	public void showCuboid(Location aLoc, Location bLoc, double step,Particle part) {
		//Step is the distance between particles
	    World world = aLoc.getWorld();

	    double[] xArr = {Math.min(aLoc.getX(), bLoc.getX()), Math.max(aLoc.getX(), bLoc.getX())};
	    double[] yArr = {Math.min(aLoc.getY(), bLoc.getY()), Math.max(aLoc.getY(), bLoc.getY())};
	    double[] zArr = {Math.min(aLoc.getZ(), bLoc.getZ()), Math.max(aLoc.getZ(), bLoc.getZ())};

	    for (double x = xArr[0]; x < xArr[1]; x += step) for (double y : yArr) for (double z : zArr) {
	        world.spawnParticle(part, x, y, z, 1, 0, 0, 0, 0.1);
	    }
	    for (double y = yArr[0]; y < yArr[1]; y += step) for (double x : xArr) for (double z : zArr) {
	        world.spawnParticle(part, x, y, z, 1, 0, 0, 0, 0.1);
	    }
	    for (double z = zArr[0]; z < zArr[1]; z += step) for (double y : yArr) for (double x : xArr) {
	        world.spawnParticle(part, x, y, z, 1, 0, 0, 0, 0.1);
	    }
	}
	
	
}
