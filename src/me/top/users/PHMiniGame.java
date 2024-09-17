package me.top.users;


import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nao.general.info.GameConditions;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Minegame;
import me.nao.manager.ClassArena;


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
        	return p.PositionArmorStand(n);
        	
        }else if(identifier.equals("dific_1")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific1%");
        }else if(identifier.equals("dific_2")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific2%");
        }else if(identifier.equals("dific_3")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific3%");
        }else if(identifier.equals("dific_3")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific3%");
        }else if(identifier.equals("dific_4")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific4%");
        }else if(identifier.equals("dific5")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific5%");
        }else if(identifier.equals("dific6")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific6%");
        }else if(identifier.equals("getmap")){
        	return getMapNamePlaceHolder(player);
        }else if(identifier.equals("isingame")){
        	return isPlayerinGamePlaceHolder(player);
        }else if(identifier.equals("author")){
        	return getAuthor();
        }else if(identifier.equals("version")){
        	return getVersion();
        }
        
        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%) 
        // was provided
        return null;
    }
	
	
	
	public String getMapNamePlaceHolder(Player player) {
		GameConditions gc = new GameConditions(plugin);
		String status = "";
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
