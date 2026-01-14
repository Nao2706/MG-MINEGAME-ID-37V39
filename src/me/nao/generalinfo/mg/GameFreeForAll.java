package me.nao.generalinfo.mg;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.nao.cosmetics.mg.Fireworks;
import me.nao.enums.mg.GameStatus;
import me.nao.main.mg.Minegame;
import me.nao.utils.mg.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GameFreeForAll extends GameInfo{
	
	
	private Minegame plugin;
	private int limitpoints;
	private List<Location> locations;
	
	public GameFreeForAll(Minegame plugin) {
		this.plugin = plugin;
		this.limitpoints = 0;
		this.locations = new ArrayList<>();
	}
	
	
	public List<Location> getSpawns(){
		return locations;
	}
	

	public int getLimitpoints() {
		return limitpoints;
	}
	
	
	public void setSpawns(List<Location> spawns) {
		this.locations = spawns;
	}

	public void setLimitpoints(int limitpoints) {
		this.limitpoints = limitpoints;
	}
	
	
	public boolean hasAnyPlayerReachedPointLimit() {
		HashMap<String, Integer> scores = new HashMap<>();
		GameConditions gc = new GameConditions(plugin);
		
		List<Player> joins = gc.ConvertStringToPlayer(getParticipants());
		
		for(Player user : joins) {
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
			scores.put(user.getName(), pl.getGamePoints().getKills());	
		}
		
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});
		
		if(!list.isEmpty()) {
			
			for(Map.Entry<String, Integer> e : list) {
				if(e.getValue() >= getLimitpoints()) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	public String getWinnerTopPlayer() {
		String name = "";
		HashMap<String, Integer> scores = new HashMap<>();
		GameConditions gc = new GameConditions(plugin);
		
		List<Player> joins = gc.ConvertStringToPlayer(getParticipants());
		
		for(Player user : joins) {
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
			scores.put(user.getName(), pl.getGamePoints().getKills());	
		}
		
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});
		
		if(!list.isEmpty()) {
			
			for(Map.Entry<String, Integer> e : list) {
			
			   return e.getKey();
				
			}
		}
		
		return name;
	}
	
	
	public List<String> getPlayersSortedOfGame() {
		List<String> l = new ArrayList<>();
		HashMap<String, Integer> scores = new HashMap<>();
		GameConditions gc = new GameConditions(plugin);
		
		List<Player> joins = gc.ConvertStringToPlayer(getParticipants());
		
		for(Player user : joins) {
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
			scores.put(user.getName(), pl.getGamePoints().getKills());	
		}
		
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});
		
		if(!list.isEmpty()) {
			
			for(Map.Entry<String, Integer> e : list) {
				l.add(e.getKey());
			}
		}
		
		return l;
		
	}
	
	public void reachLimitPoint() {
		
		if(hasAnyPlayerReachedPointLimit()) {
			endGamesByReachLimitPoints();
			getWinnersPlayers().addAll(getPlayersSortedOfGame());
			setGameStatus(GameStatus.TERMINANDO);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void startGame(Player player) {
		List<Location> l  = getSpawns();
		Random r = new Random();
		player.teleport(l.get(r.nextInt(l.size())));
		player.sendTitle(Utils.colorTextChatColor("&a&l!!! A LUCHAR !!!"),Utils.colorTextChatColor("&eConsigue &b&l"+getLimitpoints()+" &ePuntos para Ganar."), 20, 20, 20);
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void respawnGame(Player player) {
		List<Location> l  = getSpawns();
		Random r = new Random();
		player.teleport(l.get(r.nextInt(l.size())));
		player.sendTitle(Utils.colorTextChatColor("&a&l!!! TEN CUIDDO !!!"),Utils.colorTextChatColor("&eCaerse del Mapa no es Bueno"), 20, 20, 20);
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void endGamesByReachLimitPoints() {
		GameConditions gc = new GameConditions(plugin);
	    Fireworks fw = new Fireworks();
	    
		List<Player> joins = gc.ConvertStringToPlayer(getPlayersSortedOfGame());
		
		for(Player p : joins) {
			if(p.getName().equals(getWinnerTopPlayer())) {
				p.setFlying(true);
				p.setInvisible(true);
				p.getInventory().clear();
				p.sendTitle(Utils.colorTextChatColor("&a&lFELICIDADES"+getWinnerTopPlayer()+" &b&lGANASTE"),Utils.colorTextChatColor("&eEres muy Bueno/a."), 20, 20, 20);

				continue;
			}
			p.sendTitle(Utils.colorTextChatColor("&a&l"+getWinnerTopPlayer()+" &b&lGANO"),Utils.colorTextChatColor("&epor Alcanzar el Limite de Puntos."), 20, 20, 20);
			p.setGameMode(GameMode.SPECTATOR);
			
		}
		fw.spawnMetodoAyi(gc.ConvertStringToPlayerAlone(getWinnerTopPlayer()));
		getWinnersPlayers().addAll(getPlayersSortedOfGame());
		setGameStatus(GameStatus.TERMINANDO);

	}
	
	@SuppressWarnings("deprecation")
	public void endGamesByTimeOut() {
		GameConditions gc = new GameConditions(plugin);
	    Fireworks fw = new Fireworks();
	    
		List<Player> joins = gc.ConvertStringToPlayer(getPlayersSortedOfGame());
		for(Player p : joins) {
			if(p.getName().equals(getWinnerTopPlayer())) {
				p.setFlying(true);
				p.setInvisible(true);
				p.getInventory().clear();
				p.sendTitle(Utils.colorTextChatColor("&a&lFELICIDADES"+getWinnerTopPlayer()+" &b&lGANASTE"),Utils.colorTextChatColor("&eTienes el Puntaje mas Alto."), 20, 20, 20);

				continue;
			}
			 p.sendTitle(Utils.colorTextChatColor("&a&l"+getWinnerTopPlayer()),Utils.colorTextChatColor("&eGana por ser el Top &c#&61"), 20, 20, 20);

			p.setGameMode(GameMode.SPECTATOR);
			
		}
		fw.spawnMetodoAyi(gc.ConvertStringToPlayerAlone(getWinnerTopPlayer()));
		getWinnersPlayers().addAll(getPlayersSortedOfGame());
		setGameStatus(GameStatus.TERMINANDO);

	}
	

}
