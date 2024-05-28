package me.nao.general.info;

import java.util.List;



public class GameDialogs {

	private String map;
	private String name;
	private int tiempo;
	private boolean status;
	private List<String> l;
 
    
    //cuenta atras
	public GameDialogs(String map,String name ,int tiempo,boolean status,List<String> l) {
		this.map = map;
		this.name = name;
		this.tiempo = tiempo;
		this.status = status;
		this.l = l;
	}
	
	public String getMap() {
		return map;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTiempo() {
		return tiempo;
	}

	public boolean isStatus() {
		return status;
	}

	public List<String> getList() {
		return l;
	}




	

	
	
	

	
	
	
	
	
	
	
	
	
	
}
