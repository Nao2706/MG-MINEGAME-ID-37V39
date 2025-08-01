package me.nao.topusers.mg;


import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nao.cosmetics.mg.RankPlayer;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.ObjetivesMG;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;

@SuppressWarnings("deprecation")
public class PHMiniGame extends PlaceholderExpansion{

	
	
	
	 // We get an instance of the plugin later.
    private Minegame plugin;
 
    public PHMiniGame(Minegame plugin) {
    	this.plugin = plugin;
    }
 
    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }
    /**
     * Since this expansion requires api access to the plugin "SomePlugin" 
     * we must check if said plugin is on the server or not.
     *
     * @return true or false depending on if the required plugin is installed.
     */
    @Override
    public boolean canRegister(){
        return true;
    }
 
    /**
     * The name of the person who created this expansion should go here.
     * 
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "NAO2706";
    }
 
    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest 
     * method to obtain a value if a placeholder starts with our 
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "mg";
    }
 
    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }
 
    /**
     * This is the method called when a placeholder with our identifier 
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.Player Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
 
    // %mipluginvidas_vidas%
    // %mg_top%
 
    @Override
    public String onPlaceholderRequest(Player player, String identifier){
 
        if(player == null){
            return "";
            
            
        }// %mg_top_1%
        else if(identifier.equals("top_1") || identifier.equals("top_2") || identifier.equals("top_3") || identifier.equals("top_4") || identifier.equals("top_5") ||
        identifier.equals("top_6") || identifier.equals("top_7") || identifier.equals("top_8") || identifier.equals("top_9") || identifier.equals("top_10")){
        	PointsManager p = new PointsManager(plugin);
        	String[] spl = identifier.split("_");
        	int n = Integer.valueOf(spl[1]);
        	n = n -1;
        	return p.positionArmorStand(n);
        	
        }else if(identifier.equals("dific_1") || identifier.equals("dific_2") || identifier.equals("dific_3") || identifier.equals("dific_4") || identifier.equals("dific_5")
        		|| identifier.equals("dific_6")){
        	
        	GameConditions c = new GameConditions(plugin);
        	int value = Integer.valueOf(identifier.replace("dific_",""));
        	
        	return c.DifficultyMap("%dific"+value+"%");
        	
        }else if(identifier.equals("getmap")){
        	return getMapNamePlaceHolder(player);
        	
        }else if(identifier.equals("isingame")){
        	return isPlayerinGamePlaceHolder(player);
        	
        }else if(identifier.equals("author")){
        	return getAuthor();
        	
        }else if(identifier.equals("version")){
        	return getVersion();
        	
        }else if(identifier.startsWith("isinside_")){
        	String text = identifier.replace("isinside_","");
        	String[] split = text.split("_");
        	String[] split2 = split[0].split(",");
        	String[] split3 = split[1].split(",");
        	
        	GameConditions c = new GameConditions(plugin);
        	
        	Location loc1 = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
        	Location loc2 = new Location(Bukkit.getWorld(split3[0]),Double.valueOf(split3[1]),Double.valueOf(split3[2]),Double.valueOf(split3[3]));

        	
        	return 	String.valueOf(c.isInsideOfLocations(player.getLocation(), loc1, loc2));
 
        }else if(identifier.startsWith("isblockinside_")){
        	
        	String text = identifier.replace("isblockinside_","");
        	String[] split = text.split("_");
        	String[] split1 = text.split(":");
        	String[] split2 = split[0].split(",");
        	String[] split3 = split[1].split(",");
        	
        	GameConditions c = new GameConditions(plugin);
        	
        	Location loc1 = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
        	Location loc2 = new Location(Bukkit.getWorld(split3[0]),Double.valueOf(split3[1]),Double.valueOf(split3[2]),Double.valueOf(split3[3].replaceAll("\\D",""))); // \\D remplaza todo lo que nosea numero 

        	
        	return 	String.valueOf(c.isBlockInside(Material.valueOf(split1[1].toUpperCase()), loc1, loc2));
 
        }else if(identifier.startsWith("isblock_")){
        	
        	String text = identifier.replace("isblock_","");
        	String[] split = text.split("_");
        	String[] split1 = text.split(",");
        	String[] split2 = split[0].split(",");
   
        	//mg isblock_world,23,45,67:AIR
        	//mg isblock_world,23,45,67:SEA_LANTERN
        	
        	Location loc1 = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
        	
        	return 	String.valueOf(split1[4].toUpperCase().equals(loc1.getBlock().getType().toString()));
 
        }else if(identifier.startsWith("entitysamountaboveblock_")){
        	
        	String text = identifier.replace("entitysamountaboveblock_","");
        	String[] split = text.split("_");
        	String[] split2 = split[0].split(",");
   
        	//mg isblock_world,23,45,67:AIR
        	GameConditions c = new GameConditions(plugin);
        	Location loc1 = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
        	return String.valueOf(c.getAmountOfEntityAboveBlock(loc1));
 
        }else if(identifier.startsWith("entityaboveblock")){
        	
        	String text = identifier.replace("entityaboveblock_","");
        	String[] split = text.split("_");
        	String[] split2 = split[0].split(",");
   
        	//mg isblock_world,23,45,67:AIR
        	GameConditions c = new GameConditions(plugin);
        	Location loc1 = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
        	return 	String.valueOf(c.isEntityAbove(loc1));
 
        }else if(identifier.startsWith("amountmobsinzonetype")){
        	
        	String text = identifier.replace("amountmobsinzonetype_","");
        	String[] split = text.split("_");
        	String[] split1 = text.split(":");
        	String[] split2 = split[0].split(",");
        	String[] split3 = split[1].split(",");
        	
        	GameConditions c = new GameConditions(plugin);
        	
        	Location loc1 = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
        	Location loc2 = new Location(Bukkit.getWorld(split3[0]),Double.valueOf(split3[1]),Double.valueOf(split3[2]),Double.valueOf(split3[3].replaceAll("\\D",""))); // \\D remplaza todo lo que nosea numero 

        	
        	return 	String.valueOf(c.getSpecifictEntitysAmountInZone(EntityType.valueOf(split1[1].toUpperCase()), loc1, loc2));
 
        }else if(identifier.startsWith("amountmobsinzone")){
        	
        	String text = identifier.replace("amountmobsinzone_","");
        	String[] split = text.split("_");
        	String[] split2 = split[0].split(",");
        	String[] split3 = split[1].split(",");
        	
        	GameConditions c = new GameConditions(plugin);
        	
        	Location loc1 = new Location(Bukkit.getWorld(split2[0]),Double.valueOf(split2[1]),Double.valueOf(split2[2]),Double.valueOf(split2[3]));
        	Location loc2 = new Location(Bukkit.getWorld(split3[0]),Double.valueOf(split3[1]),Double.valueOf(split3[2]),Double.valueOf(split3[3]));

        	
        	return 	String.valueOf(c.getEntitysAmountInZone(loc1, loc2));
 
        }else if(identifier.startsWith("objetivecurrentvalue")){
        	//mg_objetive_Tutorial,Puerta1
        	String text = identifier.replace("objetivecurrentvalue_","");
        	String[] split = text.split(",");
        	
      
        	
        	return 	getObjetiveCurrentValue(split[0], split[1]);
 
        }else if(identifier.startsWith("objetivecompletevalue")){
        	//mg_objetive_Tutorial,Puerta1
        	String text = identifier.replace("objetivecompletevalue_","");
        	String[] split = text.split(",");
        	
      
        	
        	return 	getObjetiveCurrentCompleteValue(split[0], split[1]);
 
        }else if(identifier.startsWith("level")){
        	//mg_objetive_Tutorial,Puerta1
        	return 	getPlayerlvlmg(player);
 
        }else if(identifier.startsWith("prestige")){
        	//mg_objetive_Tutorial,Puerta1
        	return 	getPlayerprestigemg(player);
 
        }else if(identifier.startsWith("numberprestige")){
        	//mg_objetive_Tutorial,Puerta1
        	return 	getPlayerprestigelvlmg(player);
 
        }
        
        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%) 
        // was provided
        return null;
    }
	
	
	
	public String getMapNamePlaceHolder(Player player) {
		String status = "";
		GameConditions gc = new GameConditions(plugin);
		if(gc.isPlayerinGame(player)) {
			PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
			status = pi.getMapName();
		}else {
			status = "Ninguno";
		}
		return status;
	}
	
	public String isPlayerinGamePlaceHolder(Player player) {
		GameConditions gc = new GameConditions(plugin);
		
		return String.valueOf(gc.isPlayerinGame(player));
	}
	
	
	public String getObjetiveCurrentValue(String map,String objetive) {
		
		GameConditions gc = new GameConditions(plugin);
		if(!gc.existMap(map)) {
			return "El mapa no existe.";
		}
		
		if(!gc.isMapinGame(map)) {
			return "El mapa no esta en Juego";
		}
		GameInfo gi = plugin.getGameInfoPoo().get(map);	
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		if(!l.stream().filter(o -> o.getObjetiveName().equals(objetive)).findFirst().isPresent()) {
			return "Sin datos";
		}
		ObjetivesMG obj = l.stream().filter(o -> o.getObjetiveName().equals(objetive)).findFirst().get();
		
		return String.valueOf(obj.getCurrentValue());
	}
	
	public String getObjetiveCurrentCompleteValue(String map,String objetive) {
		
		GameConditions gc = new GameConditions(plugin);
		if(!gc.existMap(map)) {
			return "El mapa no existe.";
		}
		
		if(!gc.isMapinGame(map)) {
			return "El mapa no esta en Juego";
		}
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);	
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		if(!l.stream().filter(o -> o.getObjetiveName().equals(objetive)).findFirst().isPresent()) {
			return "Sin datos.";
		}
		ObjetivesMG obj = l.stream().filter(o -> o.getObjetiveName().equals(objetive)).findFirst().get();
		
		return String.valueOf(obj.getCompleteValue());
	}
	
	public String getPlayerlvlmg(Player player) {
		FileConfiguration lvl = plugin.getPoints();
		String text = "";
	
		text = lvl.getString("Players."+player.getName()+".Level","0");
		
		
		return text;
	}
	
	
	public String getPlayerprestigemg(Player player) {
		FileConfiguration lvl = plugin.getPoints();
		RankPlayer rp = new RankPlayer(plugin);
		String text = "";
		text = rp.getRankPrestigePlaceHolder(lvl.getInt("Players."+player.getName()+".Prestige",0));
		
		return text;
	}
	
	public String getPlayerprestigelvlmg(Player player) {
		FileConfiguration lvl = plugin.getPoints();
		String text = "";
		text = lvl.getString("Players."+player.getName()+".Prestige","0");
		
		return text;
	}
	
	
	
	
}
