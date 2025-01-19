package me.nao.scoreboard;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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

import me.nao.enums.ObjetiveStatusType;
import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.GameObjetivesMG;
import me.nao.general.info.GamePoints;
import me.nao.general.info.ObjetivesMG;
import me.nao.general.info.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.revive.RevivePlayer;



public class MgScore {

	private Minegame plugin;
	public MgScore(Minegame plugin) {
		this.plugin = plugin;
	}
	
	//main scoreboard usa la scoreboard del juego (se guarda informcion)
	public void CreateLife(Player player) {
	 	ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		
		
		
		if(board.getObjective("mgvida") == null) {
			Objective ob = board.registerNewObjective("mgvida", Criteria.HEALTH,""+ChatColor.RED+ChatColor.BOLD+"❤", RenderType.INTEGER);
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
		
		GameConditions gc = new GameConditions(plugin);
		
		if(!gc.isPlayerinGame(player)) return;
	
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective ob = scoreboard.registerNewObjective("Anuncio",Criteria.DUMMY,"");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		List<String> show = new ArrayList<>();
		
		Random r = new Random();
		
		int val = r.nextInt(3+1); 
		
			 ob.setDisplayName(""+ChatColor.YELLOW+ChatColor.BOLD+"NOTAS");
			 
			 if(val == 0) {
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"-------------");
				 show.add(ChatColor.RED+" ");
				 show.add(""+ChatColor.YELLOW+ChatColor.BOLD+"Cargando Informacion....");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Puede que haya");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Puede que no");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Crees Ganar???");
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"------------- ");
			 }if(val == 1) {
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"-------------");
				 show.add(ChatColor.RED+" ");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Cargando Informacion....");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Te fijaste ");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"en la Dificultad");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"no ?? F");
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"------------- ");
			 }if(val == 2) {
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"-------------");
				 show.add(ChatColor.RED+" ");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Cargando Informacion....");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Es facil perderse");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Explora con cuidado");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Y trata de no jugar solo");
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"------------- ");
			 }if(val == 3) {
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"-------------");
				 show.add(ChatColor.RED+" ");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"Cargando Informacion....");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"La clave es aprender");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"del mapa y sus trucos");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"o eran los hacks??");
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.AQUA+ChatColor.BOLD+"------------- ");
			 }
			 
			
			
	
		for(int i = 0; i< show.size();i++) {
			
			Score score = ob.getScore(show.get(i));
			score.setScore((show.size()-i));
		}
		player.setScoreboard(scoreboard);
		
	}
	
	
	public void ShowObjetives(Player player,int priority) {
		
		GameConditions gc = new GameConditions(plugin);
		
		if(!gc.isPlayerinGame(player)) return;
		
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
		GamePoints gp = (GamePoints) p.getGamePoints();
		GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
		GameObjetivesMG gomg = gi.getGameObjetivesMg();
		GameAdventure ga = (GameAdventure) gi;
		
		
		if(!plugin.getEntitiesFromFlare().isEmpty()) {
			
		
			List<Entity> entlist = plugin.getEntitiesFromFlare().get(p.getMapName());
		
				for(Entity ent : entlist) {
					if(ent instanceof LivingEntity) {
						if(ent.isDead() || ent == null) continue;
						if(ent instanceof Player) {
							
							Player pla = (Player) ent;
							if(pla.getGameMode() == GameMode.ADVENTURE) {
								green.addEntry(pla.getUniqueId().toString());
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
			for(Player target : gc.ConvertStringToPlayer(ga.getKnockedPlayers())) {
				
				//COLOCADO PARA QUE AL ITERAR SOBRE EL MAP DE JUGADORES NOQUEADOS NO ESTE SETANDO EL SCOREBOARD A OTRO JUGADOR DE OTRO MAPA (TESTEAR Y CAMBIAR SI ES NECESARIO)
				PlayerInfo pl = plugin.getPlayerInfoPoo().get(target);
				if(pl.getMapName().equals(p.getMapName())) {
				
					RevivePlayer rp = plugin.getKnockedPlayer().get(target);
					int timelife = rp.getRemainingTimeLife();
				
					if(timelife >= 21 && timelife <= 30) {
					
						green.addEntry(rp.getArmorStand().getUniqueId().toString());
					}else if(timelife >= 11 && timelife <= 20) {
					
						yellow.addEntry(rp.getArmorStand().getUniqueId().toString());
					}else if(timelife >= 1 && timelife <= 10) {
						
						red.addEntry(rp.getArmorStand().getUniqueId().toString());
					}
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
				 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"OBJETIVOS HOSTILES"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
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
		    	 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"OBJETIVOS PRIMARIOS"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
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
				 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"OBJETIVOS SECUNDARIOS"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
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
				 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"Stats "+player.getName()+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
			
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"=========");
				 show.add(ChatColor.RED+" ");
				
				 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Kills: "+ChatColor.RED+ChatColor.BOLD+gp.getKills());  
				 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Damage: "+ChatColor.RED+ChatColor.BOLD+gc.TransformPosOrNeg(gp.getDamage())); 
				 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Deads: "+ChatColor.RED+ChatColor.BOLD+gp.getDeads()); 
				 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Revive: "+ChatColor.RED+ChatColor.BOLD+gp.getRevive()); 
				 show.add(" "+ChatColor.GREEN+ChatColor.BOLD+"Help-Revive: "+ChatColor.RED+ChatColor.BOLD+gp.getHelpRevive()); 
				 
				 show.add(ChatColor.RED+"  ");
				 show.add(""+ChatColor.GREEN+ChatColor.BOLD+"========= ");
				
				 
			}
		
		
			for(int i = 0; i< show.size();i++) {
				
				Score score = ob.getScore(show.get(i));
				score.setScore((show.size()-i));
			}
			player.setScoreboard(scoreboard);
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
			con.add(ChatColor.GREEN+"Perdido??");
			con.add(ChatColor.GREEN+"Facilito 100%");
			con.add(ChatColor.GREEN+"Dificil? un 1%");
			con.add(ChatColor.GREEN+"Sigue tu instinto");
			con.add(ChatColor.GREEN+"Saludos de NAO2706");
			
			Random r = new Random();
			
			List<String> show = new ArrayList<>();
			 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"CONSEJOS"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
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
		player.setScoreboard(scoreboard);
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
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"- "+ChatColor.WHITE+ChatColor.MAGIC+l.get(i).getObjetiveName()+" "+ChatColor.GOLD+"("+ChatColor.WHITE+ChatColor.STRIKETHROUGH+l.get(i).getCurrentValue()+ChatColor.GOLD+"/"+ChatColor.WHITE+ChatColor.STRIKETHROUGH+l.get(i).getCompleteValue()+ChatColor.GOLD+")");
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
