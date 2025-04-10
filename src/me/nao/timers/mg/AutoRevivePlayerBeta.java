package me.nao.timers.mg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.enums.mg.GameStatus;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;


@SuppressWarnings("deprecation")
public class AutoRevivePlayerBeta {

	
	private int time;
	private Player player;
	private Location l;
	private Minegame plugin;
	int taskID;
	
	public AutoRevivePlayerBeta(int time, Location l,Player player, Minegame plugin) {
		this.time = time;
		this.l = l;
		this.player = player;
		this.plugin = plugin;
	}
	
	public void Respawn() {
		PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pi.getMapName());
		GameConditions gc = new GameConditions(plugin);
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		taskID = sh.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
   	  			player.sendTitle(""+ChatColor.GREEN+ChatColor.BOLD+"Respawneando",""+ChatColor.GREEN+ChatColor.BOLD+"en: "+ChatColor.RED+ChatColor.BOLD+time, 20, 20, 20);

				if(gi.getGameStatus() == GameStatus.JUGANDO) {
					if(time == 0) {
						player.teleport(l);
		   	  			player.sendTitle(""+ChatColor.GREEN+ChatColor.BOLD+"Respawneo",""+ChatColor.GOLD+ChatColor.BOLD+"Completado", 20, 20, 20);

					}
					
				}else if(!player.isOnline() || gi.getGameStatus() != GameStatus.JUGANDO || !gc.isPlayerinGame(player)) {
					Cancel();
				} 
				
				time--;
			}
		}, 0L, 20);
	
	}
	
	public void Cancel() {
		Bukkit.getScheduler().cancelTask(taskID);
	}
	
	
	
}
