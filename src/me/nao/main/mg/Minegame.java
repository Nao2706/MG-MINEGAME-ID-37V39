package me.nao.main.mg;

import java.io.File;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.nao.command.mg.Comandsmg;
import me.nao.command.mg.TabCompletemg;
import me.nao.database.mg.ConexionMySQL;
import me.nao.database.mg.SQLInfo;
import me.nao.events.mg.EventRandoms;
import me.nao.events.mg.SourceOfDamage;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.ItemMenu;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.revive.mg.RevivePlayer;
import me.nao.shop.mg.MinigameShop1;
import me.nao.topusers.mg.PHMiniGame;
import me.nao.yamlfile.mg.YamlFile;






@SuppressWarnings("deprecation")
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
    private YamlFile kits;
    private YamlFile items;
    private YamlFile itemsmenu;
    private YamlFile reports;
    private YamlFile mapfrequency;
    private YamlFile recordtime;

    private Map<String, Boolean> delete;
    private Map<Player, PlayerInfo> playerinfopoo;
    private Map<String, GameInfo> misioninfopoo;
    private Map<Player, RevivePlayer> playerrevive;
    private Map<Entity,String> guardiancredit;
    private Map<String,LocalDateTime> cooldownmap;
    
    //ITEMS ACTION
    private Map<String,List<Entity>> entitys;
    private Map<Player,Long> tempcooldown;

    
    
   //==================================== 
	private Map<String,YamlFile>getAllYmls ; // YML Manager
	private Map<String,YamlFile>getAllYmlsdialog ; 
	
	private HashMap <String,String> logsmg ; 
	private HashMap <String,String> cronomet ;
	private HashMap <String,String> aretime ;
	private HashMap<Player,Integer>pags;

	private List<String> air;
	private List<String> timeract;
	private HashMap<String,ItemMenu>itemmenu;
    
    //Esto fue un test
  
    ///COMAND FILL AREA
	HashMap <String,Boolean> cg ;
    
   // HashMap <String,YamlFile> t2 ;
    
     //creada para agregar a al nombre de la arena 1 vez para iniciar timer o un cronometro
 
	

	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public List<String> autor = pdffile.getAuthors();
	public String nombre = ""+ChatColor.GREEN+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+pdffile.getName()+ChatColor.GREEN+ChatColor.BOLD+"]";
	
	
	
	private Team green ;
	private Team red ;
	private Team white ;
	private Team infected ;
	private Team red1 ;
	private Team blue1 ;
	///================================================0

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
			  SQLInfo.createtableInventory(getMySQL());
			  SQLInfo.createTableItems(getMySQL());
			  SQLInfo.createTableKits(getMySQL());
			  
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
		
		this.reports = new YamlFile(this, "Reports");
		this.itemsmenu = new YamlFile(this, "Menuitems");
		this.messages = new YamlFile(this, "Messages");
		this.config = new YamlFile(this, "Config");
		this.points = new YamlFile(this, "Points");
		this.cooldown = new YamlFile(this, "Cooldowns");
		this.levels = new YamlFile(this, "Levels");
		this.commands = new YamlFile(this, "Commands");
		this.kits = new YamlFile(this, "Kits");
		this.items = new YamlFile(this, "Items");
		this.mapfrequency = new YamlFile(this, "Mapfrequency");
		this.recordtime = new YamlFile(this, "Record-time");

		
		
		Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+nombre+ChatColor.GRAY+"\nHa sido Activado "+ChatColor.RED+"\n[Version:"+ChatColor.DARK_GREEN+version+ChatColor.RED+"]");
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+" Hora de Crear Aventuras. ");
		Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		 
		 registrarcomando();
		 registerEvents();
	
		 InitializerMg();
		 TeamsMg();
		 DataBase();
		 GameConditions c = new GameConditions(this);
		 c.loadItemMenu();
	
	}
	
   public void onDisable() {
	   
	    GameConditions c = new GameConditions(this);
	    
	   // MgTeams t = new MgTeams(this);
	    for(Player players : Bukkit.getOnlinePlayers()) {
	    	if(c.isPlayerinGame(players)) {
	    		c.mgLeaveOfTheGame(players);
	    	}
	    }
	   
	   
	    Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+nombre+ChatColor.GRAY+"\nHa sido Desactivado "+ChatColor.RED+"\n[Version:"+ChatColor.DARK_GREEN+version+ChatColor.RED+"]");
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+" Hora de Descansar. ");
		Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}	
 
   public void registrarcomando() {
		
		this.getCommand("minegame").setExecutor(new Comandsmg(this));
		this.getCommand("minegame").setTabCompleter(new TabCompletemg(this));
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
	    guardiancredit = new HashMap<>();
	    playerrevive = new HashMap<>();
	    cooldownmap = new HashMap<>();
	    
	    itemmenu = new HashMap<>();
	    //items
	    entitys = new HashMap<>();
	    tempcooldown = new HashMap<>();
	
		getAllYmls = new HashMap<>();
		getAllYmlsdialog  = new HashMap<>();
	
		logsmg = new HashMap <String,String>();
		cronomet = new HashMap <String,String>();
		aretime = new HashMap <String,String>();
	
		air = new ArrayList<String>();
		timeract = new ArrayList<String>();
		//playerlookingmgmenu = new ArrayList<String>();
	
		delete = new HashMap<>();
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
   
   public Map<Player,RevivePlayer> getKnockedPlayer(){
	   return playerrevive;
   }
   
   public Map<String,LocalDateTime> getCooldownMap(){
	   return cooldownmap;
   }
   
   public Map<String,Boolean> getDeleteMaps(){
	   return delete;
   }
   
   ////////////ITEMS
   
   public Map<String ,List<Entity>> getEntitiesFromFlare(){
	   return entitys;
   }
   
   public Map<Player,Long> getTempCooldown(){
	   return tempcooldown;
   }
   
   public Map<Entity,String> getGuardianCredit(){
	   return guardiancredit;
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
   
   public YamlFile getKitsYaml() {
       return kits; 
   }
   
   public YamlFile getItemsYaml() {
       return items; 
   }
   
   public YamlFile getReportsYaml() {
       return reports; 
   }    
  
   public YamlFile getMapFrequency() {
       return mapfrequency; 
   }   
   
   public YamlFile getRecordTime() {
       return recordtime; 
   }   
   
    
   public List<String> getPlayerGround(){
	   return air;
   }
   
   public List<String> getTimerAction(){
	   return timeract;
   }
   
//   public List<String> getPlayersLookingMgMenus(){
//	   return playerlookingmgmenu;
//   }
//   
  
   public HashMap <String,String> getPlayerCronomets(){
	   return cronomet;
   }
   
   public HashMap <String,String> getArenaCronometer(){
	   return aretime; 
   }
   
   public HashMap <String,ItemMenu> getItemMenuMg(){
	   return itemmenu; 
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
