package me.nao.scoreboard.mg;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Strings;

import me.nao.enums.mg.ObjetiveStatusType;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GamePointHunt;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.GameObjetivesMG;
import me.nao.generalinfo.mg.GamePoints;
import me.nao.generalinfo.mg.ObjetivesMG;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.revive.mg.RevivePlayer;
import me.nao.utils.mg.Utils;


@SuppressWarnings("deprecation")
public class MgScore {

	private Minegame plugin;
	private GameConditions gmc;
	
	public MgScore(Minegame plugin) {
		this.plugin = plugin;
		this.gmc = new GameConditions(plugin);
	}
	
	//main scoreboard usa la scoreboard del juego (se guarda informcion)
	public void CreateLife(Player player) {
	 	ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		
		
		
		if(board.getObjective("mgvida") == null) {
			Objective ob = board.registerNewObjective("mgvida", Criteria.HEALTH,Utils.colorTextChatColor("&c&l❤"), RenderType.INTEGER);
			ob.setDisplaySlot(DisplaySlot.BELOW_NAME);
			
			player.setScoreboard(board);
			return;
		}
		
		
		Objective ob = board.getObjective("mgvida");
			//board.getObjective("mgvida");
		ob.setDisplaySlot(DisplaySlot.BELOW_NAME);
		ob.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+"❤");
		
		player.setScoreboard(board);
		
	
		return;
	}
	
	
	//new scoreboard no guarda nada por lo menos no encontre registros de la nueva scoreboard
	public void LoadScore(Player player){
		
	
		
		if(!gmc.isPlayerinGame(player)) return;
	
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective ob = scoreboard.registerNewObjective("Anuncio",Criteria.DUMMY,"");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		List<String> show = new ArrayList<>();
		
		Random r = new Random();
		
		int val = r.nextInt(3+1); 
		
			 ob.setDisplayName(Utils.colorTextChatColor("&e&lTIPS"));
			 
			 if(val == 0) {
				 show.add(Utils.colorTextChatColor("&b&l-------------"));
				 show.add(" ");
				 show.add(Utils.colorTextChatColor("&e&lCargando Informacion...."));
				 show.add(Utils.colorTextChatColor("&aPuede que haya"));
				 show.add(Utils.colorTextChatColor("&aPuede que no"));
				 show.add(Utils.colorTextChatColor("&a&lCrees Ganar???"));
				 show.add(Utils.colorTextChatColor("&cNo uses Hacks o Bugs"));
				 show.add(Utils.colorTextChatColor("&cReportalos si los ves."));
				 show.add(Utils.colorTextChatColor("&6/mg report &c<player>"));
				 show.add("  ");
				 show.add(Utils.colorTextChatColor("&b&l------------- "));
			 }if(val == 1) {
				 show.add(Utils.colorTextChatColor("&b&l-------------"));
				 show.add(" ");
				 show.add(Utils.colorTextChatColor("&e&lCargando Informacion...."));
				 show.add(Utils.colorTextChatColor("&aTe fijaste "));
				 show.add(Utils.colorTextChatColor("&aen la Dificultad"));
				 show.add(Utils.colorTextChatColor("&ano ?? &f&lF"));
				 show.add(Utils.colorTextChatColor("&cNo uses Hacks o Bugs"));
				 show.add(Utils.colorTextChatColor("&c&lReportalos si los ves."));
				 show.add(Utils.colorTextChatColor("&6/mg report &c<player>"));
				 show.add("  ");
				 show.add(Utils.colorTextChatColor("&b&l------------- "));
			 }if(val == 2) {
				 show.add(Utils.colorTextChatColor("&b&l-------------"));
				 show.add(" ");
				 show.add(Utils.colorTextChatColor("&e&lCargando Informacion...."));
				 show.add(Utils.colorTextChatColor("&5Es facil perderse"));
				 show.add(Utils.colorTextChatColor("&6Explora con cuidado"));
				 show.add(Utils.colorTextChatColor("&aY trata de no jugar solo"));
				 show.add(Utils.colorTextChatColor("&cNo uses Hacks o Bugs"));
				 show.add(Utils.colorTextChatColor("&c&lReportalos si los ves."));
				 show.add(Utils.colorTextChatColor("&6/mg report &c<player>"));
				 show.add("  ");
				 show.add(Utils.colorTextChatColor("&b&l------------- "));
			 }if(val == 3) {
				 show.add(Utils.colorTextChatColor("&b&l-------------"));
				 show.add(" ");
				 show.add(Utils.colorTextChatColor("&e&lCargando Informacion...."));
				 show.add(Utils.colorTextChatColor("&a&lLa clave es aprender"));
				 show.add(Utils.colorTextChatColor("&a&ldel &6&lmapa &a&ly sus &b&ltrucos"));
				 show.add(Utils.colorTextChatColor("&a&lo eran los hacks??"));
				 show.add(Utils.colorTextChatColor("&aEs broma no uses Hacks o Bugs"));
				 show.add(Utils.colorTextChatColor("&a&lReportalos si los ves."));
				 show.add(Utils.colorTextChatColor("&6/mg report &c<player>"));
				 show.add("  ");
				 show.add(Utils.colorTextChatColor("&b&l------------- "));
			 }
			 
			
			
	
		for(int i = 0; i< show.size();i++) {
			
			Score score = ob.getScore(show.get(i));
			score.setScore((show.size()-i));
		}
		player.setScoreboard(scoreboard);
		
	}
	
	
	public void showTopPlayersPh(Player player) {
	
		if(!gmc.isPlayerinGame(player)) return;
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
	
		Team green = scoreboard.registerNewTeam("green");
		Team aqua = scoreboard.registerNewTeam("aqua");
		Team yellow = scoreboard.registerNewTeam("yellow");
		Team red = scoreboard.registerNewTeam("red");
		green.setColor(ChatColor.GREEN);
		yellow.setColor(ChatColor.YELLOW);
		red.setColor(ChatColor.RED);
		aqua.setColor(ChatColor.AQUA);
	 
		PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
		GamePointHunt ph = (GamePointHunt) gi;
		
		
		if(!plugin.getEntitiesFromFlare().isEmpty()) {
			
			
			List<Entity> entlist = plugin.getEntitiesFromFlare().get(p.getMapName());
		
				for(Entity ent : entlist) {
					if(ent instanceof LivingEntity) {
						if(ent.isDead() || ent == null) continue;
						if(ent instanceof Player) {
							
							Player pla = (Player) ent;
							if(pla.getGameMode() == GameMode.ADVENTURE) {
								green.addEntry(pla.getName());
							}
						
							
						}else if(ent instanceof Monster) {
						  if(ent.getType() == EntityType.CREEPER) {
								aqua.addEntry(ent.getUniqueId().toString());
								
							}else if(ent.getType() == EntityType.PILLAGER || ent.getType() == EntityType.VINDICATOR || ent.getType() == EntityType.EVOKER) {
								yellow.addEntry(ent.getUniqueId().toString());
								
							}else{
								red.addEntry(ent.getUniqueId().toString());
								
							}
						}
					}
				}
			
		}
		
		Objective ob = scoreboard.registerNewObjective("Anuncio",Criteria.DUMMY,"");
		
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		List<String> show = new ArrayList<>();
		 ob.setDisplayName(Utils.colorTextChatColor("&a&l<<<&b&lTOP PUNTAJE&a&l>>>"));
		 //show.add(""+ChatColor.RED+ChatColor.BOLD+"-------------");
		 show.add(ChatColor.RED+" ");
		 	HashMap<String, Integer> scores = new HashMap<>();
			
			
			List<Player> joins = gmc.ConvertStringToPlayer(gi.getParticipants());

			
			for(Player user : joins) {
				PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
				scores.put(user.getName(), pl.getGamePoints().getPoints());	
			}
			
			List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

			
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
					return e2.getValue() - e1.getValue();
				}
			});
			
			if(!list.isEmpty()) {
				int i = 0;
				for(Map.Entry<String, Integer> e : list) {
					i++;
					
					if(i == 1) {
						if(e.getKey().equals(player.getName())) {
							if(e.getValue() != 0) {
								 show.add(Utils.colorTextChatColor("&c"+i+") &b&lMVP &a"+e.getKey()+"&e: &6"+e.getValue()));
							}else {
								 show.add(Utils.colorTextChatColor("&c"+i+") &a"+e.getKey()+"&e: &6"+e.getValue()));
							}
							
							
						}else {
							if(e.getValue() != 0) {
								show.add(Utils.colorTextChatColor("&c"+i+") &b&lMVP &6"+e.getKey()+"&e: &c"+e.getValue()));
							}else {
								show.add(Utils.colorTextChatColor("&c"+i+") &6"+e.getKey()+"&e: &c"+e.getValue()));
							}
						}
					}else {
						if(e.getKey().equals(player.getName())) {
							 show.add(Utils.colorTextChatColor("&c"+i+") &a"+e.getKey()+"&e: &6"+e.getValue()));
						}else {
							 show.add(Utils.colorTextChatColor("&c"+i+") &6"+e.getKey()+"&e: &c"+e.getValue()));
						}
					}
					
	
					
					
				}
			}
		 show.add(ChatColor.RED+"  ");
		 show.add(Utils.colorTextChatColor("&a&lOBTEN &c&l"+ph.getLimitpoints()+" &b&lPTOs"));
//		 show.add(ChatColor.RED+"  ");
//		 show.add(""+ChatColor.RED+ChatColor.BOLD+"------------- ");
		 
			for(int i = 0; i< show.size();i++) {
				
				Score score = ob.getScore(show.get(i));
				score.setScore((show.size()-i));
			}
			

			player.setScoreboard(scoreboard);
			
			return;
		
	}
	
	
	public void ShowObjetives(String map,int priority) {
		
		GameConditions gc = new GameConditions(plugin);
		
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Team green = scoreboard.registerNewTeam("green");
		Team aqua = scoreboard.registerNewTeam("aqua");
		Team yellow = scoreboard.registerNewTeam("yellow");
		Team red = scoreboard.registerNewTeam("red");
		green.setColor(ChatColor.GREEN);
		yellow.setColor(ChatColor.YELLOW);
		red.setColor(ChatColor.RED);
		aqua.setColor(ChatColor.AQUA);
		
		
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		GameObjetivesMG gomg = gi.getGameObjetivesMg();
		GameAdventure ga = (GameAdventure) gi;
		
		
		List<Player> join = gmc.ConvertStringToPlayer(ga.getParticipants());
		List<Player> spec = gmc.ConvertStringToPlayer(ga.getSpectators());

		
		if(!plugin.getEntitiesFromFlare().isEmpty()) {
			
		
			List<Entity> entlist = plugin.getEntitiesFromFlare().get(map);
		
				for(Entity ent : entlist) {
					if(ent instanceof LivingEntity) {
						if(ent.isDead() || ent == null) continue;
						if(ent instanceof Player) {
							
							Player pla = (Player) ent;
							if(pla.getGameMode() == GameMode.ADVENTURE) {
								green.addEntry(pla.getName());
							}
						
							
						}else if(ent instanceof Monster) {
						  if(ent.getType() == EntityType.CREEPER) {
								aqua.addEntry(ent.getUniqueId().toString());
								
							}else if(ent.getType() == EntityType.PILLAGER || ent.getType() == EntityType.VINDICATOR || ent.getType() == EntityType.EVOKER) {
								yellow.addEntry(ent.getUniqueId().toString());
								
							}else{
								red.addEntry(ent.getUniqueId().toString());
								
							}
						}
					}
				}
			
		}
		
		
		if(!ga.getKnockedPlayers().isEmpty()) {
			//System.out.println("NO ESTA VACIO");
			for(Player target : ga.getKnockedPlayers().keySet()) {
				  
				//COLOCADO PARA QUE AL ITERAR SOBRE EL MAP DE JUGADORES NOQUEADOS NO ESTE SETANDO EL SCOREBOARD A OTRO JUGADOR DE OTRO MAPA (TESTEAR Y CAMBIAR SI ES NECESARIO)
				//if(pl.getMapName().equals(p.getMapName())) {}
				
					RevivePlayer rp = ga.getKnockedPlayers().get(target);
					int timelife = rp.getRemainingTimeLife();
				
					if(timelife >= 51 && timelife <= 60) {
					
						green.addEntry(rp.getArmorStand().getUniqueId().toString());
					}else if(timelife >= 41 && timelife <= 50) {
					
						yellow.addEntry(rp.getArmorStand().getUniqueId().toString());
					}else if(timelife >= 1 && timelife <= 40) {
						
						red.addEntry(rp.getArmorStand().getUniqueId().toString());
					}
				
				
			}
		}
		
		
		
		if(gomg.hasMapObjetives()) {
			
			
			List<ObjetivesMG> l1 = gi.getGameObjetivesMg().getObjetives();
			
			if(l1.isEmpty()) return;
			
			List<ObjetivesMG> pr = new ArrayList<>();
			List<ObjetivesMG> se = new ArrayList<>();
			List<ObjetivesMG> host = new ArrayList<>();
			
			for(ObjetivesMG ob : l1) {	
				if(ob.getPriority() <= 0) {
					host.add(ob);
				}else if(ob.getPriority() == 1){
					pr.add(ob);
				}else if(ob.getPriority() >= 2){
					se.add(ob);
				}
			}
			
		
			
			Objective ob = scoreboard.registerNewObjective("Anuncio",Criteria.DUMMY,"");
		
			ob.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			List<String> primobje = StatusIntoScore(pr);
			List<String> second = StatusIntoScore(se);
			List<String> hostilobje = StatusIntoScore(host);
//			Random rand = new Random();
//			int val = rand.nextInt(2+1);
			
			List<String> show = new ArrayList<>();
			
			 if(priority == 0) {
				 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"OBJ.HOSTILES"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
				 show.add(""+ChatColor.RED+ChatColor.BOLD+"-------------");
				 show.add(ChatColor.RED+" ");
				 if(!hostilobje.isEmpty()){
					 show.addAll(hostilobje);
				 }else{
					 show.add(ChatColor.YELLOW+"Cargando..."); 
				 }
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.RED+ChatColor.BOLD+"------------- ");
				 if(!host.isEmpty()) {
					 show.add("   "+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(gc.getAmountOfObjetivesComplete(host), host.size(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]");
					 show.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(gc.getAmountOfObjetivesComplete(host), host.size()));
					
				 }else {
					 show.add(ChatColor.RED+"Sin Objetivos Hostiles");
				 }
				
				 show.add(ChatColor.YELLOW+"Revisa el Libro !!!");
		    }else if(priority == 1) {
		    	 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"OBJ.PRIMARIOS"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
		    	 if(!pr.isEmpty()) {
		    		 if(gomg.isNecessaryObjetivePrimary()) {
			    		 show.add(ChatColor.GOLD+"Obligatorio:" +ChatColor.RED+" Si");
			    	 }else {
			    		 show.add(ChatColor.GOLD+"Obligatorio:" +ChatColor.RED+" No");
			    	 }
		    	 }
		    	 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"-------------");
		    	 show.add(ChatColor.RED+" ");
				 if(!primobje.isEmpty()){
					 show.addAll(primobje);
				 }else{
					 show.add(ChatColor.YELLOW+"Cargando..."); 
				 }
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"------------- ");
				 if(!pr.isEmpty()) {
					 show.add("   "+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(gc.getAmountOfObjetivesComplete(pr), pr.size(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]");
					 show.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(gc.getAmountOfObjetivesComplete(pr), pr.size()));
				 }else {
					 show.add(ChatColor.RED+"Sin Objetivos Primarios");
				 }
				 show.add(ChatColor.RED+"Revisa el Libro !!!");
			}else if(priority == 2) {
				 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"OBJ.SECUNDARIOS"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
				 if(!se.isEmpty()) {
		    		 if(gomg.isNecessaryObjetiveSedondary()) {
			    		 show.add(ChatColor.GOLD+"Obligatorio:" +ChatColor.RED+" Si");
			    	 }else {
			    		 show.add(ChatColor.GOLD+"Obligatorio:" +ChatColor.RED+" No");
			    	 }
		    	 }
				 show.add(""+ChatColor.YELLOW+ChatColor.BOLD+"-------------");
				 show.add(ChatColor.RED+" ");
				 if(!second.isEmpty()){
					 show.addAll(second);
				 }else{
					 show.add(ChatColor.YELLOW+"Cargando..."); 
				 }
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.YELLOW+ChatColor.BOLD+"------------- ");
				 if(!se.isEmpty()) {
					 show.add("   "+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(gc.getAmountOfObjetivesComplete(se), se.size(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]");
					 show.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(gc.getAmountOfObjetivesComplete(se), se.size()));
				 }else {
					 show.add(ChatColor.RED+"Sin Objetivos Secundarios");
				 }
				
				 show.add(ChatColor.GREEN+"Revisa el Libro !!!");
				 
			}else if(priority == 3) {
				for(Player play : join) {
					PlayerInfo p = plugin.getPlayerInfoPoo().get(play);
					GamePoints gp = (GamePoints) p.getGamePoints();
					ScoreboardManager manager1 = Bukkit.getScoreboardManager();
					Scoreboard scoreboard1 = manager1.getNewScoreboard();
					Objective ob1 = scoreboard1.registerNewObjective("Anuncio",Criteria.DUMMY,"");
					
					 ob1.setDisplaySlot(DisplaySlot.SIDEBAR);
					 ob1.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"Stats "+play.getName()+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
						
					 show.add(""+ChatColor.GREEN+ChatColor.BOLD+ChatColor.STRIKETHROUGH+"=========");
					 show.add(ChatColor.RED+" ");
					
					 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Kills: "+ChatColor.RED+ChatColor.BOLD+gp.getKills());  
					 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Damage: "+ChatColor.RED+ChatColor.BOLD+gp.getDamage()); 
					 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Deads: "+ChatColor.RED+ChatColor.BOLD+gp.getDeads()); 
					 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Revive: "+ChatColor.RED+ChatColor.BOLD+gp.getRevive()); 
					 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Help-Revive: "+ChatColor.RED+ChatColor.BOLD+gp.getHelpRevive()); 
					 
					 show.add(ChatColor.RED+"  ");
					 show.add(""+ChatColor.GREEN+ChatColor.BOLD+ChatColor.STRIKETHROUGH+"========="+ChatColor.WHITE);
					 
					 
						for(int i = 0; i< show.size();i++) {
							
							Score score = ob1.getScore(show.get(i));
							score.setScore((show.size()-i));
						}
						
						
					play.setScoreboard(scoreboard1);
				}
				

				
				 return;
			}
		
		
			for(int i = 0; i< show.size();i++) {
				
				Score score = ob.getScore(show.get(i));
				score.setScore((show.size()-i));
			}
			
			for(Player play : join) {
				play.setScoreboard(scoreboard);
			}
			//en priority 3 no veran nada
			for(Player play : spec) {
				play.setScoreboard(scoreboard);
			}
			
			return;
		}else{
			
			Objective ob = scoreboard.registerNewObjective("Anuncio",Criteria.DUMMY,"");
		
			ob.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			List<String> con = new ArrayList<>();
			con.add(ChatColor.GREEN+"Cuidado por donde vas");
			con.add(ChatColor.GREEN+"No abuses de la Tienda");
			con.add(ChatColor.GREEN+"Ojo con el Tiempo");
			con.add(ChatColor.GREEN+"Juega acompañado");
			con.add(ChatColor.GREEN+"No te mueras tan facil");
			con.add(ChatColor.GREEN+"Muy dificil?? no creo");
			con.add(ChatColor.GREEN+"Probaste preguntar??");
			con.add(ChatColor.GREEN+"No te van a revivir :(");
			con.add(ChatColor.GREEN+"Falta un medico");
			con.add(ChatColor.GREEN+"Un Enigma");
			con.add(ChatColor.GREEN+"Concentrate para ganar");
			con.add(ChatColor.GREEN+"Compra cosas buenas");
			con.add(ChatColor.GREEN+"Perdido???");
			con.add(ChatColor.GREEN+"Facilito un 100%");
			con.add(ChatColor.GREEN+"Dificil? un 1%");
			con.add(ChatColor.GREEN+"Sigue tu instinto");
			con.add(ChatColor.GREEN+"Saludos de NAO2706");
			con.add(ChatColor.GREEN+"Y que intento es este?");
			con.add(ChatColor.GREEN+"Eres Pro o te crees Pro");
			con.add(ChatColor.GREEN+"Mejor Espectea");
			con.add(ChatColor.GREEN+"Reporta los Bugs");
			con.add(ChatColor.GREEN+"No uses Hacks");
			con.add(ChatColor.GREEN+"Deberias Analizar el Mapa");
			con.add(ChatColor.GREEN+"Puede que nosea por Alli.");
			con.add(ChatColor.GREEN+"Eres Tryhard o Noob???");
			con.add(ChatColor.GREEN+"Viste toda la Tienda de Items?");
			con.add(ChatColor.GREEN+"Explora con cuidado.");
			
			
			Random r = new Random();
			
			List<String> show = new ArrayList<>();
			 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"TIPS"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
			 show.add(""+ChatColor.RED+ChatColor.BOLD+"-------------");
			 show.add(ChatColor.RED+" ");
			 show.add(con.get(r.nextInt(con.size())));
			 show.add(ChatColor.RED+"  ");
			 show.add(""+ChatColor.RED+ChatColor.BOLD+"------------- ");
			 
			 for(int i = 0; i< show.size();i++) {
					
					Score score = ob.getScore(show.get(i));
					score.setScore((show.size()-i));
				}
			 
		}
		for(Player play : join) {
			play.setScoreboard(scoreboard);
		}
		//en priority 3 no veran nada
		for(Player play : spec) {
			play.setScoreboard(scoreboard);
		}
		
	}
	
	
	public void ClearScore(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		return;
	}
	
	public List<String> StatusIntoScore(List<ObjetivesMG> l) {
		List<String> l2 = new ArrayList<>();
		
		if(!l.isEmpty()) {
		
			for(int i=0;i<l.size();i++) {
				if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.COMPLETE) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.GREEN+ChatColor.BOLD+""+ChatColor.GREEN+ChatColor.BOLD+ChatColor.STRIKETHROUGH+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.GREEN+l.get(i).getCurrentValue()+"/"+ChatColor.GREEN+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.INCOMPLETE) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.RED+ChatColor.BOLD+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.RED+l.get(i).getCurrentValue()+ChatColor.GOLD+"/"+ChatColor.RED+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.WHITE+ChatColor.BOLD+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.AQUA+l.get(i).getCurrentValue()+ChatColor.GOLD+"/"+ChatColor.AQUA+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.UNKNOW) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.WHITE+ChatColor.MAGIC+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+ChatColor.MAGIC+"("+ChatColor.WHITE+ChatColor.MAGIC+l.get(i).getCurrentValue()+ChatColor.GOLD+ChatColor.MAGIC+"/"+ChatColor.WHITE+ChatColor.MAGIC+l.get(i).getCompleteValue()+ChatColor.GOLD+ChatColor.MAGIC+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.WARNING) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.YELLOW+ChatColor.BOLD+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.YELLOW+l.get(i).getCurrentValue()+ChatColor.GOLD+"/"+ChatColor.YELLOW+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.DANGER) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.DARK_RED+ChatColor.BOLD+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.RED+l.get(i).getCurrentValue()+ChatColor.GOLD+"/"+ChatColor.RED+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.CONCLUDED) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.DARK_GRAY+ChatColor.BOLD+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.RED+l.get(i).getCurrentValue()+ChatColor.GOLD+"/"+ChatColor.RED+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.CANCELLED) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.RED+l.get(i).getCurrentValue()+ChatColor.GOLD+"/"+ChatColor.RED+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
				}
				
			}
		}else {
			l2.add(""+ChatColor.WHITE+ChatColor.BOLD+"Vacio");
		}
	
		
		return l2;
	}
	
	
	public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,ChatColor notCompletedColor) {
        float percent = (float) current/max;
        int progressBars = (int) (totalBars * percent);
 
        return Strings.repeat(""+ completedColor +ChatColor.BOLD + symbol, progressBars) + Strings.repeat("" + notCompletedColor +ChatColor.BOLD+ symbol, totalBars - progressBars);
   }
	
	
	public String Porcentage(int current , int max ) {
		float percent = (float) current/max*100;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		return nf.format(percent)+"%";
	}
	
}
