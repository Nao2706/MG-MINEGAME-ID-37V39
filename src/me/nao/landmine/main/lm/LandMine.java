package me.nao.landmine.main.lm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nao.landmine.commands.lm.LandMineCommands;
import me.nao.landmine.commands.lm.LandMineTabComplete;
import me.nao.landmine.events.lm.LandMineEvents;
import me.nao.landmine.general.lm.ExplosiveMine;
import me.nao.landmine.general.lm.LandMineData;
import me.nao.landmine.general.lm.LandMineManager;
import me.nao.landmine.utils.lm.Utils;
import me.nao.landmine.yamlfile.lm.YamlFile;


public class LandMine extends JavaPlugin{

	
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public List<String> autor = pdffile.getAuthors();
	public String nombre = ""+ChatColor.GREEN+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+pdffile.getName()+ChatColor.GREEN+ChatColor.BOLD+"]";
	
	private LandMineManager lm;
	
	public ConcurrentHashMap<String,ExplosiveMine> data;
	public Map<String,Integer> permissionsgroup;
	
	
    private YamlFile config;
    private YamlFile messages;
    private YamlFile landminesdata;
	
    private LandMineData dat;
	
	public void onEnable() {
		
		
		
		this.config = new YamlFile(this, "Config");
		this.messages = new YamlFile(this, "Messages");
		this.landminesdata = new YamlFile(this, "LandMineData");
		this.dat = new LandMineData(this.config,this.messages);
		
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("&a##########################################"));
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("          "+nombre+"\n       &7Ha sido Activado \n       &c[&aVersion&c:&b"+version+"&c]"));
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("     &9Hora de Crear Explosiones. "));
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("&a##########################################"));
		
		 registerEvents();
		 registerCommands();
		 loadData();
		 
		 lm = new LandMineManager(this);
		 lm.loadGroups();
	}
	
	
	
	public void onDisable() {
		
		
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("&a##########################################"));
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("          "+nombre+"\n       &7Ha sido Desactivado \n       &c[&aVersion&c:&b"+version+"&c]"));
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("     &9Hora de Descansar "));
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor("&a##########################################"));
		  
	}
	
	
	public void registerCommands() {
		this.getCommand("landmine").setExecutor(new LandMineCommands(this));
		this.getCommand("landmine").setTabCompleter(new LandMineTabComplete());
	}
	
	public void registerEvents() {
	    PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new LandMineEvents(this),this);
		  
	}
	
	public void loadData() {
		data = new ConcurrentHashMap<>();
		permissionsgroup = new HashMap<>();
	}
	
	
	
	public ConcurrentHashMap<String,ExplosiveMine> getLandMinesData(){
		return data;
	}
	
	
	public YamlFile getConfig() {
		return config;
	}
	
	public YamlFile getMessages() {
		return messages;
	}
	
	public YamlFile getLandMineData() {
		return landminesdata;
	}
	
	public LandMineData getLandMineGeneralDataYml() {
		return dat;
	}
	
	public Map<String,Integer> getPermissionGroups(){
		return permissionsgroup;
	}
	
	
	
	
	
}
