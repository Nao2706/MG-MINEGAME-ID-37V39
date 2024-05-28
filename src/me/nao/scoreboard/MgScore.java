package me.nao.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;



public class MgScore {

	
	
	public void createLife(Player player) {
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
	
	
	public void clearScore(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		return;
	}
	
	
	
}
