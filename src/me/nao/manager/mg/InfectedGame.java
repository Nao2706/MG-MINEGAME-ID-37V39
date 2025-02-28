package me.nao.manager.mg;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import me.nao.generalinfo.mg.GameConditions;
import me.nao.main.mg.Minegame;
import me.nao.yamlfile.mg.YamlFilePlus;

public class InfectedGame {

	private Minegame plugin;
	
	public InfectedGame(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	
	
	
	
	
	 //TODO INFECTADO
	   public void setArenaSpawnSurvivor(String name, Player player) {
		   GameConditions gc = new GameConditions(plugin);
	 		if(gc.ExistMap(name)) {
				plugin.ChargedYml(name, player);
				FileConfiguration ym = plugin.getCacheSpecificYML(name);	
				if(ym.getInt("Game-Mode") != 3) {
					player.sendMessage(ChatColor.RED+"Para setear el Spawn de Supervivientes debes tener el Modo 3.");

					return;
				}
				//	ym.set("Spawn",player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ()+"/"+player.getLocation().getYaw()+"/"+player.getLocation().getPitch());
					
					List<String> startc = ym.getStringList("Spawn-Survivor");
					if(!startc.contains(player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ()+"/"+player.getLocation().getYaw()+"/"+player.getLocation().getPitch())) {
						ym.set("Spawn-Survivor", startc);
						startc.add(player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ()+"/"+player.getLocation().getYaw()+"/"+player.getLocation().getPitch());
					
						player.sendMessage(ChatColor.GREEN+"Se a seteado un Spawn-Survivor correctamente en la arena: "+ChatColor.GOLD+name);
						plugin.getCacheSpecificYML(name).save();
						plugin.getCacheSpecificYML(name).reload();
					
					}else {
						player.sendMessage(ChatColor.RED+"Esa Coordenada ya existe.");

					}
					
					
					
			}else {
				player.sendMessage(ChatColor.YELLOW+"La arena "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
			}
			
		
	    
		}
	   
	   //TODO INFECTADO
	   public void setArenaSpawnInfectado(String name, Player player) {
			
		   GameConditions gc = new GameConditions(plugin);
	 		if(gc.ExistMap(name)) {
				//plugin.ChargedYml(name, player);
				//FileConfiguration ym = plugin.getCacheSpecificYML(name);	
				plugin.ChargedYml(name, player);
				FileConfiguration ym = plugin.getCacheSpecificYML(name);	
				//	ym.set("Spawn",player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ()+"/"+player.getLocation().getYaw()+"/"+player.getLocation().getPitch());
					if(ym.getInt("Game-Mode") != 3) {
						player.sendMessage(ChatColor.RED+"Para setear el Spawn de Infectados debes tener el Modo 3.");

						return;
					}
					List<String> startc = ym.getStringList("Spawn-Infected");
					if(!startc.contains(player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ()+"/"+player.getLocation().getYaw()+"/"+player.getLocation().getPitch())) {
						ym.set("Spawn-Infected", startc);
						startc.add(player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ()+"/"+player.getLocation().getYaw()+"/"+player.getLocation().getPitch());
					
						player.sendMessage(ChatColor.GREEN+"Se a seteado un Spawn-Infected correctamente en la arena: "+ChatColor.GOLD+name);
						plugin.getCacheSpecificYML(name).save();
						plugin.getCacheSpecificYML(name).reload();
					
					
					}else {
						player.sendMessage(ChatColor.RED+"Esa Coordenada ya existe.");

					}
					
					
					
			}else {
				player.sendMessage(ChatColor.YELLOW+"La arena "+ChatColor.GREEN+name+ChatColor.YELLOW+" no existe");
			}
			
		
	    
		}
	   
	   
	   public void TptoSpawnSurvivor(Player player ,String arenaName){
		   FileConfiguration ym = getConfigUnit(arenaName);
		   if(ym.contains("Spawn-Survivor")) {
			   
				List<String> startc = ym.getStringList("Spawn-Survivor");
				Random r = new Random();
				String rand = startc.get(r.nextInt(startc.size()));
			   
			    String[] coords = rand.split("/");
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
	   
	   
	   public void TptoSpawnInfected(Player player ,String arenaName){
			FileConfiguration ym = getConfigUnit(arenaName);
			
		   if(ym.contains("Spawn-Survivor")) {
				List<String> startc = ym.getStringList("Spawn-Survivor");
				Random r = new Random();
				String rand = startc.get(r.nextInt(startc.size()));
			   
			    String[] coords = rand.split("/");
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
	   
	   
	   
//		@SuppressWarnings("unused")
//		public void addPlayerArenaListInfec(String arena,String player) {
//			
//			if(plugin.getArenaJoinPlayer().containsKey(arena)) {
//				List<String> join1 = plugin.getArenaJoinPlayer().get(arena);
//				List<String> alive1 = plugin.getAlive().get(arena);
//				if(!join1.contains(player)) {
//					join1.add(player);
//					alive1.add(player);
//				}
//			}else {
//				List<String> join1 = new ArrayList<>();
//				List<String> alive1 = new ArrayList<>();
//				List<String> spect = new ArrayList<>();
//				List<String> infectd = new ArrayList<>();
//				
//				join1.add(player);
//				alive1.add(player);
//				//plugin.getInfecteds().put(arena, infectd);
//				plugin.getArenaJoinPlayer().put(arena, join1);
//				plugin.getAlive().put(arena, alive1);
//				plugin.getSpectators().put(arena, spect);
//			}
//			
			
		//	List<String> arrive1 = new ArrayList<>();
		//		List<String> deads1 = new ArrayList<>();

			//List<String> spectator1 = new ArrayList<>();
//		}
//		
//		public void removePlayerArenaListInfec(String arena ,String player) {
//			List<String> join1 = plugin.getArenaJoinPlayer().get(arena);
//			List<String> alive1 = plugin.getAlive().get(arena);
//			//List<String> infectd = plugin.getInfecteds().get(arena);
//			List<String> spect = plugin.getSpectators().get(arena);
//			if(join1.remove(player));
//			if(alive1.remove(player));
//		//	if(infectd.remove(player));
//			if(spect.remove(player));
//			
//			
//		}
//		
//		public void deathPlayer(String arena,String player) {
//			List<String> alive1 = plugin.getAlive().get(arena);
//			//List<String> infected = plugin.getInfecteds().get(arena);
//			//if(!infected.contains(player)) {
//			//	infected.add(player);
//			//}
//			if(alive1.remove(player));
//		}
	   
	   
		public void JoinTeamInfected(Player player) {
			Team t = plugin.InfectedPlayers();
			if(!t.hasEntry(player.getName())) {
				t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"INFECTADO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
				t.setColor(ChatColor.RED);
				t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
				t.setAllowFriendlyFire(false);
				t.addEntry(player.getName());
			
			}	
			return;
		}
		
		public void LeaveTeamInfected(Player player) {
			Team t = plugin.InfectedPlayers();
			if(t.removeEntry(player.getName())) ;
			return;
			
		}
	   
	   
	   public FileConfiguration getConfigUnit(String name) {
			FileConfiguration ym = new YamlFilePlus(plugin).getSpecificYamlFile("Arenas",name);
		    return ym;
		}
	   
	   public void saveConfigUnit(File ym) {
		   FileConfiguration ym1 = new YamlFilePlus(plugin);
			try {
				ym1.save(ym);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	   
	   
	   
	   
	   
	   
}


