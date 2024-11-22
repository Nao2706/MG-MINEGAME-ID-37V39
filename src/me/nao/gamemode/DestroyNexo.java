package me.nao.gamemode;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.GameNexo;
import me.nao.general.info.PlayerInfo;
import me.nao.general.info.TeamsMg;
import me.nao.main.game.Minegame;
import me.nao.yamlfile.game.YamlFilePlus;

public class DestroyNexo {

	
	private Minegame plugin;
	
	public DestroyNexo(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	// red;1

	
	
	public void joinIntoRandomTeam(String map) {
		
		GameConditions gc = new GameConditions(plugin);
		GameNexo gn = (GameNexo) plugin.getGameInfoPoo().get(map);
		
		List<Player> participants = gc.ConvertStringToPlayer(gn.getParticipants()); 
		List<String> playerwithoutteam = new ArrayList<>();
		
		
		for(Player player : participants) {
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			if(pl.getTeamName().equals("NINGUNO")) {
				playerwithoutteam.add(player.getName());
			}
		}
		
		if(!playerwithoutteam.isEmpty()) {
			List<List<String>> teamsr = getRandomTeams(playerwithoutteam, gn.getTeams().size());
			
			List<Map.Entry<String, TeamsMg>> list = new ArrayList<>(gn.getTeams().entrySet());
			
			int index = 0;
			for(Map.Entry<String, TeamsMg> e : list) {
				List<String> members = e.getValue().getMembers();
				members.addAll(teamsr.get(index));
				index++;
			}
			
			
			for(Player player : participants) {
				PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
				for(Map.Entry<String, TeamsMg> e : list) {
					List<String> members = e.getValue().getMembers();
					if(members.contains(player.getName())) {
						pl.setTeamName(e.getKey());
					}
				}
			}
			
		}
		
		
		
		
	}
	
	
	 public List<List<String>> getRandomTeams(List<String> l,int amountteams){
		  
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
	
	
	
	
	public void RespawnTeam(Player player) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		if(gi instanceof GameNexo) {
			
			
			GameNexo gn = (GameNexo) gi;
			TeamsMg team = gn.getTeams().get(pl.getTeamName());
			
			if(!team.isNexoDestroyed()) {
				Location l = team.getSpawn();
				player.teleport(l);
			}else {
				//COLOCAR EN MODO ESPCTADOR
				player.sendMessage(ChatColor.RED+"El Nexo de tu equipo fue destruido , debes esperar a que un Compañero te reviva.");
			}
				
			
		}else {
			System.out.println("ERROR - DE JUEGO EN NEXO");
		}
	}
	
	public void damageNexo(Location loc , Player player) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		GameConditions gc = new GameConditions(plugin);
		if(gi instanceof GameNexo) {
			
			GameNexo gn = (GameNexo) gi;
			List<Map.Entry<String, TeamsMg>> list = new ArrayList<>(gn.getTeams().entrySet());
			
			for(Map.Entry<String, TeamsMg> e : list) {
				TeamsMg t = e.getValue();
				if(t.getNexo().equals(loc)) {
					if(!t.isNexoDestroyed()) {
						t.setLifeNexo(t.getLifeNexo()-1);
						
						if(t.getLifeNexo() == 0) {
							gc.sendMessageToAllPlayersInMap(pl.getMapName(),ChatColor.RED+"El Nexo del Equipo "+ChatColor.GOLD+e.getKey()+ChatColor.RED+" fue Destruido por "+ChatColor.GREEN+player.getName());
						}
					}
					
					break;
				}
			}
			
		}else {
			System.out.println("ERROR - DE JUEGO EN NEXO");
		}
	}
	


   public void tpAllTeamsToSpawn(String map) {
	   
	   GameConditions gc = new GameConditions(plugin);
	   GameInfo gi = plugin.getGameInfoPoo().get(map);
	   GameNexo gn = (GameNexo) gi;
	   List<Player> participants = gc.ConvertStringToPlayer(gn.getParticipants()); 
	   
	   for(Player player : participants) {
		   PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		   TeamsMg team = gn.getTeams().get(pl.getTeamName());
		   player.teleport(team.getSpawn());
		   
	   }
	   
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
   

	

	public FileConfiguration getGameConfig(String name) {
		YamlFilePlus file = new YamlFilePlus(plugin);
		FileConfiguration config = file.getSpecificYamlFile("Maps",name);
		//u.saveSpecificl(name);
	    return config;
	}
	
	
	
	
	
	
	
}
