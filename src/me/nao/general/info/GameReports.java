package me.nao.general.info;

import me.nao.enums.GameReportType;

public class GameReports {

	
	private String target;
	private GameReportType reportype;
	private String timereal;
	private String time ;
	private String moderador;
	private String razones;
	
	
	public GameReports(String target, GameReportType reportype, String timereal, String timereport, String moderador,
			String razones) {
		this.target = target;
		this.reportype = reportype;
		this.timereal = timereal;
		this.time = timereport;
		this.moderador = moderador;
		this.razones = razones;
	}
	
	public String getTarget() {
		return target;
	}
	public GameReportType getReportype() {
		return reportype;
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
