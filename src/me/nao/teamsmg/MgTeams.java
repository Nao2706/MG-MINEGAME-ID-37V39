package me.nao.teamsmg;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import me.nao.main.game.Main;

public class MgTeams {
	
	private Main plugin;
	
	public MgTeams(Main plugin) {
		this.plugin = plugin;
	}
	
	
	 //TODO TEAMS MISIONS
	public void JoinTeamLifeMG(Player player) {
		Team t = plugin.LifePlayersMG();
		if(!t.hasEntry(player.getName())) {
			t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.GREEN+ChatColor.BOLD+"VIVO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
			t.setColor(ChatColor.GREEN);
			t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
			t.setCanSeeFriendlyInvisibles(true);
			t.addEntry(player.getName());
			return;
		}	
		
	}
	
	public void LeaveTeamLifeMG(Player player) {

		Team t = plugin.LifePlayersMG();
		if(t.removeEntry(player.getName())) ;
			
		return;
		
	}
	
	
	public void JoinTeamDeadMG(Player player) {
	
		Team t = plugin.DeadPlayersMG();
		if(!t.hasEntry(player.getName())) {
			t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
			t.setColor(ChatColor.RED);
			t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
			t.setCanSeeFriendlyInvisibles(true);
			t.addEntry(player.getName());
			return;
		}	
	
	}
	
	public void LeaveTeamDeadMG(Player player) {
		
		Team t = plugin.DeadPlayersMG();
		if(t.removeEntry(player.getName())) ;
		return;
		
	}
	
	
	
	public void JoinTeamSpectatorMG(Player player) {
	
		Team t = plugin.SpectatorPlayersMG();
		if(!t.hasEntry(player.getName())) {
			t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.WHITE+ChatColor.BOLD+"ESPECTADOR"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
			t.setColor(ChatColor.WHITE);
			t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
			t.setCanSeeFriendlyInvisibles(true);
			t.addEntry(player.getName());
			return;
		
		}	
		
	}
	
	public void LeaveTeamSpectatorMG(Player player) {
		
		Team t = plugin.SpectatorPlayersMG();
		if(t.removeEntry(player.getName()));
		return;
		
	}
	
	public void RemoveAllPlayer(Player player) {
		Team lt = plugin.LifePlayersMG();
		Team dt = plugin.DeadPlayersMG();
		Team st = plugin.SpectatorPlayersMG();
		if(lt.removeEntry(player.getName()));
		if(dt.removeEntry(player.getName()));
		if(st.removeEntry(player.getName()));
		return;
	}
	
	public void JoinTeamRedMg(Player player) {
		Team t = plugin.RedNexo();
		if(!t.hasEntry(player.getName())) {
			t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"ROJO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
			t.setColor(ChatColor.RED);
			t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
			t.setCanSeeFriendlyInvisibles(true);
			t.addEntry(player.getName());
			return;
		
		}	
		
	}
	
	public void JoinTeamBlueMg(Player player) {
		Team t = plugin.BlueNexo();
		if(!t.hasEntry(player.getName())) {
			
			t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.BLUE+ChatColor.BOLD+"AZUL"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
			t.setColor(ChatColor.BLUE);
			t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
			t.setCanSeeFriendlyInvisibles(true);
			t.addEntry(player.getName());
			return;
		
		}	
		
	}
	
	
	
	
	

}
