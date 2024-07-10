package me.nao.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Main;
import me.nao.manager.EstadoPartida;

public class RevivePlayer {

	
	private int time;
	private Player player;
	private Location l;
	private Main plugin;
	int taskID;
	
	public RevivePlayer(int time, Location l,Player player, Main plugin) {
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

				if(gi.getEstopartida() == EstadoPartida.JUGANDO) {
					if(time == 0) {
						player.teleport(l);
		   	  			player.sendTitle(""+ChatColor.GREEN+ChatColor.BOLD+"Respawneo",""+ChatColor.GOLD+ChatColor.BOLD+"Completado", 20, 20, 20);

					}
					
				}else if(!player.isOnline() || gi.getEstopartida() != EstadoPartida.JUGANDO || !gc.isPlayerinGame(player)) {
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
