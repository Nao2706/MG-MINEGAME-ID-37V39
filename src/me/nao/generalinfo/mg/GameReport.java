package me.nao.generalinfo.mg;

import java.time.LocalDateTime;
import java.util.List;

public class GameReport {

	private String target;
	private LocalDateTime time;
	private List<String> reports;

	
	
	public GameReport(String target, LocalDateTime time, List<String> reports) {

		this.target = target;
		this.time = time;
		this.reports = reports;
	}



	public String getTarget() {
		return target;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public List<String> getReports() {
		return reports;
	}

	
	
}
