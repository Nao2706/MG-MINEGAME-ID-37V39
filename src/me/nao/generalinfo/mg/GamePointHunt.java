package me.nao.generalinfo.mg;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import me.nao.cosmetics.mg.Fireworks;
import me.nao.enums.mg.GameStatus;
import me.nao.main.mg.Minegame;
import me.nao.scoreboard.mg.MgScore;
import me.nao.utils.mg.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GamePointHunt extends GameInfo{
	
	
	private Minegame plugin;
	private int limitpoints;
	private List<Location> locations;
	private GameConditions gc;
	private Random r;
	private MgScore sco;
	private List<EntityPoints> l;
	private boolean hascustompoint;
	
	public GamePointHunt(Minegame plugin) {
		this.plugin = plugin;
		this.limitpoints = 0;
		this.locations = new ArrayList<>();
		this.gc = new GameConditions(plugin);;
		this.r = new Random();
		this.sco = new MgScore(plugin);
		this.l = new ArrayList<>();
		this.hascustompoint = false;
	}
	
	
	public List<Location> getSpawns(){
		return locations;
	}
	

	public int getLimitpoints() {
		return limitpoints;
	}
	
	
	public List<EntityPoints> getEntityPoints(){
		return l;
	}

	
	public boolean hasCustomPoints() {
		return hascustompoint;
	}
	
	public void setSpawns(List<Location> spawns) {
		this.locations = spawns;
	}

	public void setLimitpoints(int limitpoints) {
		this.limitpoints = limitpoints;
	}
	
	public void setEntityPoints( List<EntityPoints> l) {
		this.l = l;
	}
	
	public void sethasCustomPoints(boolean custom) {
		this.hascustompoint = custom;
	}
	
	public boolean hasAnyPlayerReachedPointLimit() {
		HashMap<String, Integer> scores = new HashMap<>();
		
		List<Player> joins = gc.ConvertStringToPlayer(getParticipants());
		
		for(Player user : joins) {
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
			scores.put(user.getName(), pl.getGamePoints().getPoints());	
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
		
		List<Player> joins = gc.ConvertStringToPlayer(getParticipants());
		
		for(Player user : joins) {
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
			scores.put(user.getName(), pl.getGamePoints().getPoints());	
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
	
		List<Player> joins = gc.ConvertStringToPlayer(getParticipants());
		
		for(Player user : joins) {
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(user);
			scores.put(user.getName(), pl.getGamePoints().getPoints());	
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
			gc.reloadSignData();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void startGame(Player player) {
		List<Location> l  = getSpawns();
		
		showPointsMobs(player);
    	sco.showTopPlayersPh(player);
		player.setInvulnerable(false);
		player.teleport(l.get(r.nextInt(l.size())));
		player.sendTitle(Utils.colorTextChatColor("&a&l!!! A LUCHAR !!!"),Utils.colorTextChatColor("&eConsigue &b&l"+getLimitpoints()+" &ePuntos para Ganar."), 20, 20, 20);
		
		
	}
	
	//@SuppressWarnings("deprecation")
	public void respawnGame(Player player) {
		List<Location> l  = getSpawns();
	
		player.teleport(l.get(r.nextInt(l.size())));
		//player.sendTitle(Utils.colorTextChatColor("&a&l!!! TEN CUIDDO !!!"),Utils.colorTextChatColor("&eCaerse del Mapa no es Bueno"), 20, 20, 20);
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	public void showPointsMobs(Player player) {
		
		ItemStack book =  new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		
	
		
		if(hasCustomPoints()) {
			meta.setTitle(Utils.colorTextChatColor("&b&lGUIA DE CAZA"));
			//player.sendMessage(Utils.colorTextChatColor("&a&lMobs a Cazar:"));
			for(EntityPoints ep : getEntityPoints()) {
				
				if(ep.getName() != null) {
					
					if(ep.getPoint() < 0) {
						//player.sendMessage(Utils.colorTextChatColor("&e&lEvitar &6&lTipo: &c"+ep.getType()+" &6&lNombre: &a"+ep.getName()+" &6&lPuntos: &c&l"+ep.getPoint()));
						meta.addPage(Utils.colorTextChatColor("&6&lEvitar\n&6&lTipo: &c"+ep.getType()+"\n&6&lNombre: &a"+ep.getName()+"\n&6&lPuntos: &c&l"+ep.getPoint()));
					}else {
						//player.sendMessage(Utils.colorTextChatColor("&c&lEliminar &6&lTipo: &c"+ep.getType()+" &6&lNombre: &a"+ep.getName()+" &6&lPuntos: &b&l+"+ep.getPoint()));
						meta.addPage(Utils.colorTextChatColor("&c&lEliminar\n&6&lTipo: &b"+ep.getType()+"\n&6&lNombre: &a"+ep.getName()+"\n&6&lPuntos: &a&l"+ep.getPoint()));

					}
					
					
				}else {
					if(ep.getPoint() < 0) {
						//player.sendMessage(Utils.colorTextChatColor("&e&lEvitar &6&lTipo: &c"+ep.getType()+" &6&lPuntos: &c&l"+ep.getPoint()));
						meta.addPage(Utils.colorTextChatColor("&6&lEvitar\n&6&lTipo: &c"+ep.getType()+"\n&6&lPuntos: &c&l"+ep.getPoint()));

					}else {
						//player.sendMessage(Utils.colorTextChatColor("&c&lEliminar &6&lTipo: &c"+ep.getType()+" &6&lPuntos: &b&l+"+ep.getPoint()));
						meta.addPage(Utils.colorTextChatColor("&c&lEliminar\n&6&lTipo: &b"+ep.getType()+"\n&6&lPuntos: &a&l"+ep.getPoint()));

					}
				}
			}
			book.setItemMeta(meta);
			player.getInventory().addItem(book);
		}
	
		
		return;
	}
	
	
	@SuppressWarnings("deprecation")
	public void endGamesByReachLimitPoints() {
	    Fireworks fw = new Fireworks();
	    
		List<Player> joins = gc.ConvertStringToPlayer(getPlayersSortedOfGame());
		
		for(Player p : joins) {
			if(p.getName().equals(getWinnerTopPlayer())) {
				p.setAllowFlight(true);
				p.setFlying(true);
				p.setInvulnerable(true);
				p.setHealth(20);
				p.getInventory().clear();
				p.sendTitle(Utils.colorTextChatColor("&a&lFELICIDADES"+getWinnerTopPlayer()+" &b&lGANASTE"),Utils.colorTextChatColor("&eEres muy Bueno/a."), 20, 20, 20);

	
			}else {
				p.sendTitle(Utils.colorTextChatColor("&a&l"+getWinnerTopPlayer()+" &b&lGANO"),Utils.colorTextChatColor("&epor Alcanzar el Limite de Puntos."), 20, 20, 20);
				p.setGameMode(GameMode.SPECTATOR);
			}
		
			
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
				p.setAllowFlight(true);
				p.setFlying(true);
				p.setInvulnerable(true);
				p.setHealth(20);
				p.getInventory().clear();
				//p.sendTitle(Utils.colorTextChatColor("&a&lFELICIDADES"+getWinnerTopPlayer()+" &b&lGANASTE"),Utils.colorTextChatColor("&eTienes el Puntaje mas Alto."), 20, 20, 20);

			
			}else {
				 p.sendTitle(Utils.colorTextChatColor("&a&l"+getWinnerTopPlayer()),Utils.colorTextChatColor("&eGana por ser el Top &c#&61"), 20, 20, 20);
				 p.setGameMode(GameMode.SPECTATOR);
			}

			
		}
		fw.spawnMetodoAyi(gc.ConvertStringToPlayerAlone(getWinnerTopPlayer()));
		getWinnersPlayers().addAll(getPlayersSortedOfGame());
		setGameStatus(GameStatus.TERMINANDO);

	}
	

}
