package me.nao.landmine.general.lm;

import org.bukkit.configuration.file.FileConfiguration;

public class LandMineData {

	
	private FileConfiguration config;
	private FileConfiguration messages;
	
	public LandMineData(FileConfiguration config, FileConfiguration messages) {
		this.config = config;
		this.messages = messages;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public FileConfiguration getMessages() {
		return messages;
	}

	public void setConfig(FileConfiguration config) {
		this.config = config;
	}

	public void setMessages(FileConfiguration messages) {
		this.messages = messages;
	}
	
	
	
	
	
	
	
}
