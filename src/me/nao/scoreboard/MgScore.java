package me.nao.scoreboard;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.google.common.base.Strings;

import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.ObjetiveType;
import me.nao.general.info.ObjetivesMG;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Main;



public class MgScore {

	private Main plugin;
	public MgScore(Main plugin) {
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
	public void ShowProgressObjetive(Player player){
		
		GameConditions gc = new GameConditions(plugin);
		
		if(!gc.isPlayerinGame(player)) {
			return;
		}
		
		PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
		
		List<ObjetivesMG> l1 = gi.getGameObjetivesMg().getObjetives();
		
		if(l1.isEmpty()) {
			return;
		}
		
		
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
		
	
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective ob = scoreboard.registerNewObjective("Anuncio",Criteria.DUMMY,"");
	
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
		
		List<String> primobje = StatusIntoScore(pr);
		List<String> second = StatusIntoScore(se);
		List<String> hostilobje = StatusIntoScore(host);
		Random rand = new Random();
		int val = rand.nextInt(2+1);
		
		List<String> show = new ArrayList<>();
		
		
		
		 if(val == 0) {
			 ob.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+"OBJETIVOS");
			 show.add(ChatColor.RED+"|-----------|");
			 show.add(ChatColor.RED+" ");
			 show.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"Hostiles"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
			 show.addAll(hostilobje);
			 show.add(ChatColor.RED+"  ");
			 show.add(ChatColor.RED+"|-----------| ");
			 show.add(ChatColor.YELLOW+"Revisa el Libro !!!");
	    }else if(val == 1) {
	    	 ob.setDisplayName(""+ChatColor.GOLD+ChatColor.BOLD+"OBJETIVOS");
	    	 show.add(ChatColor.AQUA+"|-----------|");
	    	 show.add(ChatColor.RED+" ");
			 show.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"Primarios"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
			 show.addAll(primobje);
			 show.add(ChatColor.RED+"  ");
			 show.add(ChatColor.AQUA+"|-----------| ");
			 show.add(ChatColor.RED+"Revisa el Libro !!!");
		}else if(val == 2) {
			 ob.setDisplayName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"OBJETIVOS");
			 show.add(ChatColor.YELLOW+"|-----------|");
			 show.add(ChatColor.RED+" ");
			 show.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"Secundarios"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]");
			 show.addAll(second);
			 show.add(ChatColor.RED+"  ");
			 show.add(ChatColor.YELLOW+"|-----------| ");
			 show.add(ChatColor.GREEN+"Revisa el Libro !!!");
		}
		
		
	
	
		
		
	
	
		for(int i = 0; i< show.size();i++) {
			
			Score score = ob.getScore(show.get(i));
			score.setScore((show.size()-i));
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
				if(l.get(i).getObjetiveType() == ObjetiveType.COMPLETE) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+""+ChatColor.GREEN+ChatColor.BOLD+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}else if(l.get(i).getObjetiveType() == ObjetiveType.INCOMPLETE) {
					l2.add(""+ChatColor.RED+ChatColor.BOLD+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}else if(l.get(i).getObjetiveType() == ObjetiveType.WAITING) {
					l2.add(""+ChatColor.WHITE+ChatColor.BOLD+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}else if(l.get(i).getObjetiveType() == ObjetiveType.UNKNOW) {
					l2.add(""+ChatColor.WHITE+ChatColor.MAGIC+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}else if(l.get(i).getObjetiveType() == ObjetiveType.WARNING) {
					l2.add(""+ChatColor.YELLOW+ChatColor.BOLD+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}else if(l.get(i).getObjetiveType() == ObjetiveType.DANGER) {
					l2.add(""+ChatColor.DARK_RED+ChatColor.BOLD+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}else if(l.get(i).getObjetiveType() == ObjetiveType.CONCLUDED) {
					l2.add(""+ChatColor.DARK_GRAY+ChatColor.BOLD+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}else if(l.get(i).getObjetiveType() == ObjetiveType.CANCELLED) {
					l2.add(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+l.get(i).getNombre()+" "+Porcentage(l.get(i).getValue(),l.get(i).getCompleteValue()));
				}
				
			}
		}else {
			l2.add(""+ChatColor.RED+ChatColor.BOLD+"Vacio");
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
