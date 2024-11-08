package me.nao.general.info;




import java.util.List;



public class GameNexo extends GameInfo{


	private List<List<TeamsMg>> teams;
	




	//O1 Y O2 son los objetivos si es que se completaron para evitar doble reclamo o recompensa
	public GameNexo() {
	
		this.teams = null;

	}
	

	public List<List<TeamsMg>> getTeams() {
		return teams;
	}

	

	




	
}
