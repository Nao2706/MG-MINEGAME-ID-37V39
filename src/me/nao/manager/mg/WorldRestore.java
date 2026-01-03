package me.nao.manager.mg;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.nao.main.mg.Minegame;

public class WorldRestore {

	
	
	private Minegame plugin;
	
	public WorldRestore(Minegame plugin) {
		this.plugin = plugin;
	}

//    public void unloadMap(String mapName) {
//        World w = Bukkit.getServer().getWorld(mapName);
//        if (w != null && Bukkit.getServer().getWorlds().contains(w)) {
//            for (Entity en : w.getEntities()) {
//                if (en instanceof Player) {
//                    Player p = (Player) en;
//               
//                     p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
//                      p.sendMessage("Mapa en Restauracion Redirigiendo.");
//                }
//            }
//            Bukkit.getServer().unloadWorld(w, false);
//            return;
//        }
//  
//    }
//
//    private void loadMap(String mapName) {
//        World w;
//        if (Bukkit.getWorld(mapName) == null) {
//            w = Bukkit.getServer().createWorld(new WorldCreator(mapName));
//        } else {
//            w = Bukkit.getWorld(mapName);
//        }
//        w.setAutoSave(false);
//    }
//
//    public void rollback(String mapName) {
//        this.unloadMap(mapName);
//        this.loadMap(mapName);
//    }
//	
	public void rollback(String mapName) {
	    // Descargar el mundo actual
	    unloadMap(mapName);

	    // Copiar los archivos de la carpeta de backup a la carpeta del mundo original
	    File backupFolder = new File(plugin.getDataFolder(), "MapsBackUps/" + mapName);
	    File worldFolder = new File(Bukkit.getWorldContainer(), mapName);
	    try {
	        FileUtils.copyDirectory(backupFolder, worldFolder);
	    } catch (IOException e) {
	        Bukkit.getConsoleSender().sendMessage("Error al restaurar el mundo");
	        return;
	    }

	    // Cargar el mundo restaurado
	    loadMap(mapName);
	}

	private void unloadMap(String mapName) {
	    World w = Bukkit.getServer().getWorld(mapName);
	    if (w != null) {
          for (Entity en : w.getEntities()) {
          if (en instanceof Player) {
              Player p = (Player) en;
         
               p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                p.sendMessage("Mapa en Restauracion Redirigiendo.");
          }
      }
	        Bukkit.getServer().unloadWorld(w, false);
	    }
	}

	private void loadMap(String mapName) {
	    World w = Bukkit.getServer().createWorld(new WorldCreator(mapName));
	    w.setAutoSave(false);
	}
	
	
}
