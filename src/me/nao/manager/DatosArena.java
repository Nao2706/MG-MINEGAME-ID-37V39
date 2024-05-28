package me.nao.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatosArena {
	
	
		HashMap <String,List<String>> arenajoin ;
	    HashMap <String,List<String>> arrive ;
	    HashMap <String,List<String>> alive ;
	    HashMap <String,List<String>> deaths ;
	    HashMap <String,List<String>> spectators ;
	
	    
		List<String> join1 ;
		List<String> alive1 ;
		List<String> arrive1 ;
		List<String> deads1 ;
		List<String> spectator1 ;
	
	
	public DatosArena() {
		arenajoin = new HashMap <String,List<String>>();
		arrive = new HashMap <String,List<String>>();
		alive = new HashMap <String,List<String>>();
		deaths = new HashMap <String,List<String>>();
		spectators = new HashMap <String,List<String>>();
		
		alive1 = new ArrayList<>();
		arrive1 = new ArrayList<>();
		join1 = new ArrayList<>();
		deads1 = new ArrayList<>();
		spectator1 = new ArrayList<>();
		
		
	}
	
	
	public void PlayerAddArenaData(String arena ,String player) {
		join1.add(player);
		alive1.add(player);
		arenajoin.put(arena, join1);
		alive.put(arena,alive1);
	}
	
	
	
	
	
	
	
	

}
