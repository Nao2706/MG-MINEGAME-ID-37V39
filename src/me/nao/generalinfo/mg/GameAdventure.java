package me.nao.generalinfo.mg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import me.nao.revive.mg.RevivePlayer;



public class GameAdventure extends GameInfo{

	
	private List<String> alives;
	private List<String> deads;
	private Map<Player,RevivePlayer> rp;
	
	//O1 Y O2 son los objetivos si es que se completaron para evitar doble reclamo o recompensa
	public GameAdventure() {
		this.alives = new ArrayList<>();
		this.deads = new ArrayList<>();
		this.rp = new HashMap<>();
	}

	public List<String> getAlivePlayers() {
		return alives;
	}
	
	public List<String> getDeadPlayers() {
		return deads;
	}


	public Map<Player,RevivePlayer> getKnockedPlayers(){
		return rp;
	}
	
	
	public void addKnockedPlayer(Player player, RevivePlayer rp) {
		getKnockedPlayers().put(player, rp);
	}
	
	public boolean isPlayerKnocked(Player player) {
		return getKnockedPlayers().containsKey(player);
	}
	
	public void removeKnockedPlayer(Player player) {
		getKnockedPlayers().remove(player);
	}
	
}
