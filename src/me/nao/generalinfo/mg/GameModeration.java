package me.nao.generalinfo.mg;

import me.nao.enums.mg.GameModerationActionType;

public class GameModeration {

	
	private String target;
	private GameModerationActionType action;
	private String timereal;
	private String time ;
	private String moderador;
	private String razones;
	
	
	public GameModeration(String target, GameModerationActionType action, String timereal, String timereport, String moderador,String razones) {
		this.target = target;
		this.action = action;
		this.timereal = timereal;
		this.time = timereport;
		this.moderador = moderador;
		this.razones = razones;
	}
	
	public String getTarget() {
		return target;
	}
	public GameModerationActionType getReportype() {
		return action;
	}
	public String getTimereal() {
		return timereal;
	}
	public String getTimeReport() {
		return time;
	}
	public String getModerador() {
		return moderador;
	}
	public String getCausa() {
		return razones;
	}

	
	public String DataReport() {
		return "Sancion:"+getReportype().toString()+"-Fecha:"+getTimereal()+"-Tiempo:"+getTimeReport()+"-Mod:"+getModerador()+"-Razon:["+getCausa()+"]";
	}
	
}
