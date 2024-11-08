package me.nao.general.info;

import java.util.ArrayList;
import java.util.List;




public class GameAdventure extends GameInfo{

	
	private List<String> alives;
	private List<String> deads;
	private List<String> arrives;
	private List<String> knockeds;
	
	
	//O1 Y O2 son los objetivos si es que se completaron para evitar doble reclamo o recompensa
	public GameAdventure() {
		this.alives = new ArrayList<>();
		this.deads = new ArrayList<>();
		this.arrives = new ArrayList<>();
		this.knockeds = new ArrayList<>();
	}

	public List<String> getAlivePlayers() {
		return alives;
	}
	
	public List<String> getDeadPlayers() {
		return deads;
	}
	
	public List<String> getArrivePlayers() {
		return arrives;
	}
	
	public List<String> getKnockedPlayers() {
		return knockeds;
	}

	
	
}
