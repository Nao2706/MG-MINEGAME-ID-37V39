package me.nao.gamemode;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;


import me.nao.general.info.GameInfo;
import me.nao.general.info.GameNexo;
import me.nao.general.info.PlayerInfo;
import me.nao.general.info.TeamsMg;
//import me.nao.general.info.GameConditions;
//import me.nao.general.info.GameNexo;
import me.nao.main.game.Main;
import me.nao.yamlfile.game.YamlFilePlus;

public class DestroyNexo {

	
	private Main plugin;
	public DestroyNexo(Main plugin) {
		this.plugin = plugin;
	}
	
	
	// red;1

	public void RespawnTeam(Player player) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		if(gi instanceof GameNexo) {
			
			GameNexo gn = (GameNexo) gi;
			
			for(List<TeamsMg> l : gn.getTeams()) {
				TeamsMg team = (TeamsMg) l;
				if(team.getMembers().contains(player.getName())) {
					if(team.getLifeNexo() != 0) {
						//player.teleport(team.getSpawn());
					}
					return;
				}
			}
				
			
		}else {
			System.out.println("ERROR - DE JUEGO EN NEXO");
		}
	}
	
	public void DamageNexo(Location loc , Player player) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		if(gi instanceof GameNexo) {
			
			GameNexo gn = (GameNexo) gi;
			
			for(List<TeamsMg> l : gn.getTeams()) {
				TeamsMg team = (TeamsMg) l;
			//SI EL LOCATION ES IGUAL AL DEL NEXO DE ALGUN EQUIPO PUES ESTE BAJARA
					if(team.getNexo() == l) {
						if(team.getLifeNexo() != 0) {
							team.setLifeNexo(team.getLifeNexo()-1);
						}
						return;
					}
			}
				
			
		}else {
			System.out.println("ERROR - DE JUEGO EN NEXO");
		}
	}
	
	public void TeamsEliminated(Player player) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		if(gi instanceof GameNexo) {
			
			GameNexo gn = (GameNexo) gi;
			
			for(List<TeamsMg> l : gn.getTeams()) {
				TeamsMg team = (TeamsMg) l;
				if(team.getMembers().contains(player.getName())) {
					if(team.getLifeNexo() == 0) {
						team.getMembers().remove(player.getName());
						// EL EQUIPO ES ELIMINADO CUANDO TODOS LOS MIEMBROS SON REMOVIDOS
						if(team.getMembers().isEmpty()) {
						   gn.getTeams().remove(l);	
						}
						
					}else{
						//RESPAWN 
						
					}
					return;
				}
			}
				
			
		}else {
			System.out.println("ERROR - DE JUEGO EN NEXO");
		}
	}
	  
	  public Location TpSpawnRed(String map) {
		  FileConfiguration ym = getGameConfig(map);
		   if(ym.contains("Spawn-Red")) {
			   String[] coords = ym.getString("Spawn-Red").split("/");
			    String world = coords[0];
			    double x = Double.valueOf(coords[1]);
			    double y = Double.valueOf(coords[2]);
			    double z = Double.valueOf(coords[3]);
			    float yaw = Float.valueOf(coords[4]);
			    float pitch = Float.valueOf(coords[5]);
			    
			    Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			    return l;
			    
		   }
		   return null;
	  }
	  
	  
	  public Location TpSpawnBlue(String map) {
		  FileConfiguration ym = getGameConfig(map);
		   if(ym.contains("Spawn-Blue")) {
			   String[] coords = ym.getString("Spawn-Blue").split("/");
			    String world = coords[0];
			    double x = Double.valueOf(coords[1]);
			    double y = Double.valueOf(coords[2]);
			    double z = Double.valueOf(coords[3]);
			    float yaw = Float.valueOf(coords[4]);
			    float pitch = Float.valueOf(coords[5]);
			    
			    Location l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			    return l;
			    
			    
		   }
		   return null;
	  }
	

   public Location getRedNexo(String map) {
	   		
				FileConfiguration ym = getGameConfig(map);
			   if(ym.contains("Nexo-Red")) {
				   String[] coords = ym.getString("Nexo-Red").split("/");
				    String world = coords[0];
				    double x = Double.valueOf(coords[1]);
				    double y = Double.valueOf(coords[2]);
				    double z = Double.valueOf(coords[3]);
				  
				    
				    Location l = new Location(Bukkit.getWorld(world), x, y, z);
				
				    return l;
				    
			   }
			   return null;
   }
   
   public Location getBlueNexo(String map) {
  		
		FileConfiguration ym = getGameConfig(map);
	   if(ym.contains("Nexo-Blue")) {
		   String[] coords = ym.getString("Nexo-Blue").split("/");
		    String world = coords[0];
		    double x = Double.valueOf(coords[1]);
		    double y = Double.valueOf(coords[2]);
		    double z = Double.valueOf(coords[3]);
		  
		    
		    Location l = new Location(Bukkit.getWorld(world), x, y, z);
		
		    return l;
		    
	   }
	   return null;
}
   
   
   public static List<List<String>> getTeams(List<String> l,int amountteams){
		  
	   	List<String> copy = new ArrayList<>();
	   	copy.addAll(l);
		Collections.shuffle(copy);
		List<List<String>> g = IntStream.range(0, copy.size())
				.boxed()
				.collect(Collectors.groupingBy(i -> i % amountteams))
				.values() 
				.stream()
				.map(il -> il.stream().map(copy::get).collect(Collectors.toList()))
				.collect(Collectors.toList());
		
	   return g;
  }
   
//   public void TpAllTeamsToSpawn(String map) {
//	   GameNexo l = (GameNexo) plugin.getGameInfoPoo().get(map);
//	  
//	   
//	      Location red = TpSpawnRed(map);
//		  Location blue = TpSpawnBlue(map);
//	   GameConditions gm = new GameConditions(plugin);
//	   List<Player> b1 = gm.ConvertStringToPlayer(l.getBlueTeamMg());
//	   for(Player pl  : b1) {
//		  
//		  pl.teleport(blue);
//	   }
//	   
//	   List<Player> r1 = gm.ConvertStringToPlayer(l.getRedTeamMg());
//	   for(Player pl  : r1) {
//	
//		   pl.teleport(red);
//	   }
//	   
//   }
   
//   public void RandomTeam(String map) {
//	   GameNexo l = (GameNexo) plugin.getGameInfoPoo().get(map);
//	   List<String> p = l.getParticipantes();
//	   List<List<String>> g = getTeams(p,2);
//	   
//	   List<String> t1 = l.getBlueTeamMg();
//	   t1.addAll(g.get(0));
//	   
//	   List<String> t2 = l.getRedTeamMg();
//	   t2.addAll(g.get(1));
//	   
//	   GameConditions gm = new GameConditions(plugin);
//	   List<Player> b1 = gm.ConvertStringToPlayer(t1);
//	   for(Player pl  : b1) {
//		   JoinTeamBlue(pl);
//		//   pl.teleport(blue);
//	   }
//	   
//	   List<Player> r1 = gm.ConvertStringToPlayer(t2);
//	   for(Player pl  : r1) {
//		   JoinTeamRed(pl);
//		   //pl.teleport(red);
//	   }
//	   
//   }
   
   
	public void JoinTeamRed(Player player) {
		Team t = plugin.RedNexo();
		if(!t.hasEntry(player.getName())) {
			player.sendMessage(ChatColor.GREEN+"Eres del Equipo "+ChatColor.RED+"Rojo");
			t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"RED"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
			t.setColor(ChatColor.RED);
			t.setAllowFriendlyFire(false);
			t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
			t.addEntry(player.getName());
		
		}	
		return;
	}
	
	public void LeaveTeamRed(Player player) {
		Team t = plugin.RedNexo();
		if(t.removeEntry(player.getName()));
		return;
		
	}
	
	
	public void JoinTeamBlue(Player player) {
		Team t = plugin.BlueNexo();
		if(!t.hasEntry(player.getName())) {
			player.sendMessage(ChatColor.GREEN+"Eres del Equipo "+ChatColor.BLUE+"Azul");
			t.setPrefix(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.BLUE+ChatColor.BOLD+"BLUE"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] ");
			t.setColor(ChatColor.BLUE);
			t.setAllowFriendlyFire(false);
			t.setOption(Option.NAME_TAG_VISIBILITY,OptionStatus.ALWAYS);
			t.addEntry(player.getName());
		
		}	
		return;
	}
	
	public void LeaveTeamBlue(Player player) {
		Team t = plugin.BlueNexo();
		if(t.removeEntry(player.getName()));
		return;
		
	}
	
	
//	public void EndNexo(String map) {
//		    GameNexo l = (GameNexo) plugin.getGameInfoPoo().get(map);
//	
//			GameConditions gc = new GameConditions(plugin);
//			
//			int bluep = l.getBlueLifeNexo();
//    		int redp = l.getRedLifeNexo();
//    		
//    		List<String> bluet = l.getBlueTeamMg();
//    		List<String> redt = l.getRedTeamMg();
//    		List<Player> part = gc.ConvertStringToPlayer(l.getParticipantes());
//    		
//    		if(bluet.isEmpty() && bluep <= 50) {
//    			List<Player> red = gc.ConvertStringToPlayer(redt);
//    			for(Player playerr : red) {
//    				gc.PlayerWinnerReward(playerr);
//    				
//    			}
//    			return;
//    		}else if(redt.isEmpty() && redp <= 50) {
//    			List<Player> blue = gc.ConvertStringToPlayer(bluet);
//    			
//    			for(Player playerb : blue) {
//    				gc.PlayerWinnerReward(playerb);	
//    			}
//    			return;
//    		}
//    		
//    		if(bluep == redp) {
//    			
//
//    			for(Player player : part) {
//    				player.sendTitle(""+ChatColor.WHITE+ChatColor.BOLD+"Empate",""+ChatColor.WHITE+ChatColor.BOLD+"Ningun Equipo Gana.", 20, 20, 20);
//    				player.sendMessage(""+ChatColor.WHITE+ChatColor.BOLD+"Empate ningun Equipo Gana.");
//
//    			}
//    			
//    			
//    		}else if(bluep > redp) {
//    			List<Player> blue = gc.ConvertStringToPlayer(bluet);
//    			
//    			for(Player playerb : blue) {
//    				gc.PlayerWinnerReward(playerb);	
//    			}
//    			
//    			List<Player> red = gc.ConvertStringToPlayer(redt);
//    			for(Player playerr : red) {
//    				gc.PlayerLoserReward(playerr);
//    			}
//    			
//    		}else {
//    			List<Player> blue = gc.ConvertStringToPlayer(bluet);
//    			
//    			for(Player playerb : blue) {
//    				gc.PlayerLoserReward(playerb);	
//    			}
//    			List<Player> red = gc.ConvertStringToPlayer(redt);
//    			for(Player playerr : red) {
//    				gc.PlayerWinnerReward(playerr);
//    				
//    			}
//    		}
//    		
//    		for(Player target : gc.ConvertStringToPlayer(l.getParticipantes())) {
//				gc.RestorePlayer(target);
//			}
//				plugin.getGameInfoPoo().remove(map);
//				System.out.println("DESPUES NEXO OBJECT BORRADO: "+plugin.getGameInfoPoo().toString());
//	}
	
	
	public FileConfiguration getGameConfig(String name) {
		YamlFilePlus file = new YamlFilePlus(plugin);
		FileConfiguration config = file.getSpecificYamlFile("Maps",name);
		//u.saveSpecificl(name);
	    return config;
	}
	
	
	
	
	
	
	
}
