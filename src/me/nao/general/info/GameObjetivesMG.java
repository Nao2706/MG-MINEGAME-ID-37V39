package me.nao.general.info;

import java.util.List;

public class GameObjetivesMG {

	/**
	 * Lista de Objetivos : Contiene Objetivos que cada mapa contenga.o
	 * 
	 * 
	 * 
	 */

	private List<ObjetivesMG> objetives;
	
	public GameObjetivesMG(List<ObjetivesMG> objetives) {
		this.objetives = objetives;
	}

	/**
	 * Lista de Objetivos
	 * 
	 * @return Lista de Objetivos del Mapa
	 * 
	 */
	public List<ObjetivesMG> getObjetives() {
		return objetives;
	}

}
