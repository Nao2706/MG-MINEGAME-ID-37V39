package me.nao.main.game;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.nao.command.game.Comandos;
import me.nao.command.game.TabComplete1;
import me.nao.database.ConexionMySQL;
import me.nao.database.SQLInfo;
import me.nao.event.game.SourceOfDamage;
import me.nao.events.EventRandoms;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.general.info.PlayerInfo;
import me.nao.shop.MinigameShop1;
import me.nao.yamlfile.game.YamlFile;
import me.top.users.PHMiniGame;







public class Minegame extends JavaPlugin{
	
	private ConexionMySQL conexion;
	public String carpeta = "/Maps/";
	public String carpeta2 = "/Maps-Dialogues/";

    
    private YamlFile general;
    private YamlFile dialogs;
  
    ///
    //TODO MINI DB
  
    private YamlFile config;
    private YamlFile messages;
    private YamlFile points;
    private YamlFile cooldown;
    private YamlFile levels;
    private YamlFile commands;    
    private YamlFile inventorys;
    private YamlFile itemsmenu;
    private YamlFile reports;
   

    
    private Map<Player, PlayerInfo> playerinfopoo;
    private Map<String, GameInfo> misioninfopoo;
    private Map<Player,Entity> credit;
    
   //==================================== 
	public Map<String,YamlFile>getAllYmls ; // YML Manager
	public Map<String,YamlFile>getAllYmlsdialog ; 
	
	HashMap <String,String> logsmg ; 
	HashMap <Player,Location> checkpoint ; 
    HashMap <String,String> cronomet ;
    HashMap <String,String> aretime ;
    HashMap<Player,Integer>pags;

    List<String> air;
    List<String> timeract;
   
    
    //Esto fue un test
  
    ///COMAND FILL AREA
	HashMap <String,Boolean> cg ;
    
   // HashMap <String,YamlFile> t2 ;
    
     //creada para agregar a al nombre de la arena 1 vez para iniciar timer o un cronometro
 
	

	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String nombre = ""+ChatColor.AQUA+ChatColor.BOLD+"["+ChatColor.GREEN+ChatColor.BOLD+pdffile.getName()+ChatColor.AQUA+ChatColor.BOLD+"]";
	
	
	
	private Team green ;
	private Team red ;
	private Team white ;
	
	private Team infected ;
	
	private Team red1 ;
	private Team blue1 ;
	///================================================0

	
	

	
	//====================================================================================================================

	// metodo que se ejecuta  al acabar una partida

	 //para comprobar si un jugador esta en una arena
	
	
	 
	
	//====================================================================================================================
	
	//====================================================================================================================	

	 //TODO YML
	 
	 //REESTRUCTURAR
	 public void ChargedYml(String name, Player player) {
		
		 List<String> ac = config.getStringList("Maps-Created.List");
			if(ac.contains(name)) {
				this.general = new YamlFile(this,name, new File(this.getDataFolder().getAbsolutePath()+carpeta));
				getAllYmls.put(name,general);
				if(player != null) {
					 
					//player.sendMessage(nombre+ChatColor.GREEN+" La arena "+name+" a sido cargada");
				}
				Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GREEN+" El Mapa "+name+" a sido cargada");
			}
			else {
		    	Bukkit.getConsoleSender().sendMessage("El Mapa "+name+" no existe.");
		    	if(player != null) {
		    	player.sendMessage(nombre+ChatColor.GREEN+" El Mapa "+name+" no existe");
		    	}
		       }
	 }
	 
	 //TODO [NEW YML] REESTRUCTURAR
	public void NewYml(String name, Player player) {
		
	    
		List<String> ac = config.getStringList("Maps-Created.List");
	
		 
		 
		if(!ac.contains(name)) {
			this.general = new YamlFile(this,name, new File(this.getDataFolder().getAbsolutePath()+carpeta));
			this.config.set("Maps-Created.List",ac);
			ac.add(name);
			this.config.save();
			
			
			   getAllYmls.put(name,this.general);
			   //statusArena.put(name,EstadoPartida.ESPERANDO);
			if(player != null) {
				player.sendMessage(nombre+ChatColor.GREEN+" El Mapa "+name+" a sido creada");
			}
		
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Nuevo yml creado: "+name);
		}else {
	    	Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Mapa "+name+" ya existe.");
	    	if(player != null) {
	    	player.sendMessage(nombre+ChatColor.GREEN+" El Mapa "+name+" ya existe");
	    	}
	       }
		
		
	}

	
	 //REESTRUCTURAR
	 public void ChargedYmlDialog(String name, Player player) {
		
		 List<String> ac = config.getStringList("Maps-Dialogs.List");
			if(ac.contains(name)) {
				this.dialogs = new YamlFile(this,name, new File(this.getDataFolder().getAbsolutePath()+carpeta2));
				getAllYmlsdialog.put(name,dialogs);
				if(player != null) {
					 
					//player.sendMessage(nombre+ChatColor.GREEN+" La arena "+name+" a sido cargada");
				}
				Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GREEN+" El Dialogo de Mapa "+name+" a sido cargada");
			}
			else {
		    	Bukkit.getConsoleSender().sendMessage("El Dialogo de Mapa "+name+" no existe.");
		    	if(player != null) {
		    	player.sendMessage(nombre+ChatColor.GREEN+" El Dialogo de Mapa "+name+" no existe");
		    	}
		       }
	 }
	 
	 //TODO [NEW YML] REESTRUCTURAR
	public void NewYmlDialog(String name, Player player) {
		
	    
		List<String> ac = config.getStringList("Maps-Dialogs.List");
	
		 
		 
		if(!ac.contains(name)) {
			this.dialogs = new YamlFile(this,name, new File(this.getDataFolder().getAbsolutePath()+carpeta2));
			this.config.set("Maps-Dialogs.List",ac);
			ac.add(name);
			this.config.save();
			
			
			getAllYmlsdialog.put(name,this.dialogs);
			   //statusArena.put(name,EstadoPartida.ESPERANDO);
			if(player != null) {
				player.sendMessage(nombre+ChatColor.GREEN+" El Dialogo de Mapa "+name+" a sido creada");
			}
		
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Nuevo yml creado: "+name);
		}else {
	    	Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Dialogo de Mapa "+name+" ya existe.");
	    	if(player != null) {
	    	player.sendMessage(nombre+ChatColor.GREEN+" El Dialogo de Mapa "+name+" ya existe");
	    	}
	       }
		
		
	}
	
	public boolean DataBase() {
		if(this.config.getBoolean("Data-Base.Enabled")) {
			  SQLInfo sql = new SQLInfo(this);
			  this.conexion = new ConexionMySQL(sql.Host(),sql.Puerto(),sql.BaseDeDatos(),sql.Usuario(),sql.Clave());
			
			  Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Se esta usando Base de Datos");
		}else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"No se esta usando Base de Datos");
		}
		return false;
	}
	
//====================================================================================================================	
	//TODO [ON ENABLE]
	
	public void onEnable() {
		//getServer().getPluginManager().registerEvents(new Codigo(this),this);
		
		
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PHMiniGame(this).register();
		}else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" PlaceholderAPI no encontrado para MG");
		}
		
		this.reports = new YamlFile(this, "reports");
		this.itemsmenu = new YamlFile(this, "menuitems");
		this.messages = new YamlFile(this, "messages");
		this.config = new YamlFile(this, "config");
		this.points = new YamlFile(this, "points");
		this.cooldown = new YamlFile(this, "cooldowns");
		this.levels = new YamlFile(this, "levels");
		this.commands = new YamlFile(this, "commands");
		this.inventorys = new YamlFile(this, "inventorys");
		

		
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"__________________________________");
		Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+nombre+ChatColor.GOLD+" Ha sido Activado "+ChatColor.RED+"[Version:"+ChatColor.DARK_GREEN+version+"]");
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+" Hora de Crear Aventuras. ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"__________________________________");
		registrarcomando();
		registerEvents();
	
		InitializerMg();
		TeamsMg();
		DataBase();
		
	
	}
	
   public void onDisable() {
	   
	    GameConditions c = new GameConditions(this);
	    
	   // MgTeams t = new MgTeams(this);
	    for(Player players : Bukkit.getOnlinePlayers()) {
	    	if(c.isPlayerinGame(players)) {
	    		c.LeaveOfTheGame(players);
	    	}
	    }
	   
	   
	    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"__________________________________");
		Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+nombre+ChatColor.GOLD+" Ha sido Desactivado "+ChatColor.RED+"[Version:"+ChatColor.DARK_GREEN+version+"]");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" Hora de Descansar. ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"__________________________________");
		
	}	
 
   public void registrarcomando() {
		
		this.getCommand("minegame").setExecutor(new Comandos(this));
		this.getCommand("minegame").setTabCompleter(new TabComplete1(this));
		//this.getCommand("mg").setTabCompleter(new TabComplete2(this));
		
		
		
	}
   
   public void registerEvents() {
		
	   PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventRandoms(this),this );
		pm.registerEvents(new SourceOfDamage(this),this );
		pm.registerEvents(new MinigameShop1(this),this );
		
	}

   public void InitializerMg() {
	   
	   playerinfopoo = new HashMap<>();
	   misioninfopoo = new HashMap<>();
	   credit = new HashMap<>();
	    
	
		checkpoint = new HashMap<Player,Location>();
		getAllYmls = new HashMap<>();
		getAllYmlsdialog  = new HashMap<>();
	
		logsmg = new HashMap <String,String>();
		cronomet = new HashMap <String,String>();
		aretime = new HashMap <String,String>();
	
		air = new ArrayList<String>();
		timeract = new ArrayList<String>();
		
	
		
		pags = new HashMap <Player,Integer>();
		
		//es test estos dos
		
		cg = new HashMap <String,Boolean>();
   }
   
   public void TeamsMg() {
	   	ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		
	   green = board.getTeam("lifemg");
	   red = board.getTeam("deadmg");
	   white = board.getTeam("spectatormg");
	   infected = board.getTeam("infectado");
	   
	   red1 = board.getTeam("redmg");
	   blue1 = board.getTeam("bluemg");
		
	   if(infected == null) {
		   infected = board.registerNewTeam("infectado");
		}
	   
		if(green == null) {
			green = board.registerNewTeam("lifemg");
		}
		
		if(red == null) {
			red = board.registerNewTeam("deadmg");
		}
		
		if(white == null) {
			white = board.registerNewTeam("spectatormg");
		}
		
		
		if(red1 == null) {
			red1 = board.registerNewTeam("redmg");
		}
		
		if(blue1 == null) {
			blue1 = board.registerNewTeam("bluemg");
		}
		
   }
   
   //POO CACHE
   
   public Connection getMySQL() {
		 return this.conexion.getConnection();
   }
   
   public Map<Player,PlayerInfo> getPlayerInfoPoo(){
	   return playerinfopoo;
   }
   
   public Map<String,GameInfo> getGameInfoPoo(){
	   return misioninfopoo;
   }
   
   public Map<Player,Entity> CreditKill(){
	   return credit;
   }
   
   
   //TODO TEAMS
   
   public Team LifePlayersMG() {
	  return green;
   }
   
   public Team DeadPlayersMG() {
		  return red;
   }
   
   public Team SpectatorPlayersMG() {
		  return white;
   }
   
   public Team InfectedPlayers() {
		  return infected;
   }
   
   public Team RedNexo() {
		  return red1;
   }

   public Team BlueNexo() {
		  return blue1;
   }
   
 
 //TODO  GETTERS
 

   
   public YamlFile getCacheSpecificYML(String name) {
	   if(getAllYmls.containsKey(name)) {
		   return getAllYmls.get(name);
	   }
	return null;
	 
   }
   
   public YamlFile getCacheSpecificDialogsYML(String name) {
	   if(getAllYmlsdialog.containsKey(name)) {
		   return getAllYmlsdialog.get(name);
	   }
	return null;
	 
   }
   
   
   public YamlFile getConfig() {
       return config; 
   }
   
   public YamlFile getMenuItems() {
       return itemsmenu; 
   }
   
   public YamlFile getMessage() {
       return messages; 
   }
   public YamlFile getPoints() {
       return points; 
   }
   public YamlFile getCooldown() {
       return cooldown; 
   }
   //sin uso aparente el lvls
   public YamlFile getLevels() {
       return levels; 
   }
   public YamlFile getCommandsMg() {
       return commands; 
   }
   
   public YamlFile getInventorysYaml() {
       return inventorys; 
   }
   
   public YamlFile getReportsYaml() {
       return reports; 
   }    
  
 
    
   public List<String> getPlayerGround(){
	   return air;
   }
   
   public List<String> getTimerAction(){
	   return timeract;
   }
   

   
  
   public HashMap <Player,Location> getCheckPoint(){
	   return checkpoint;
   }
   
   public HashMap <String,String> getPlayerCronomets(){
	   return cronomet;
   }
   
   public HashMap <String,String> getArenaCronometer(){
	   return aretime; 
   }
   
   public HashMap <String,String> getFirstLogMg(){
	   return logsmg; 
   }
   
  

   
   
   
   public HashMap <Player,Integer> getPags(){
	   return pags;
   }
   //================================================
   
   //AFK
 
   
   
   
//==============NEXO
  
   
//====================
   
 
   public HashMap <String,Boolean> getCommandFillArea(){
	   return cg;
   }
   
  
   
	
}
