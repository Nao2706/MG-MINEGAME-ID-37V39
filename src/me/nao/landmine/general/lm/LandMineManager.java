package me.nao.landmine.general.lm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nao.landmine.enums.lm.DetectionType;
import me.nao.landmine.enums.lm.LandMineType;
import me.nao.landmine.enums.lm.Position;
import me.nao.landmine.main.lm.LandMine;
import me.nao.landmine.utils.lm.Utils;

public class LandMineManager {

	
	private LandMine plugin;
	
	public LandMineManager(LandMine plugin) {
		this.plugin = plugin;
	}
	
	
	public void executeLandMine(Location l , LandMineType type) {
		
		
		
		
		if(type == LandMineType.WATER) {
		
			for(int x = -2; x < 3;x++) {
				for(int y = -2; y < 3;y++) {
					for(int z = -2; z < 3;z++) {
    			 
    			    Block a =  l.getBlock().getRelative(x,y,z);
    	     
    			    //setea bloques en esos puntos
        			if(y == 2||x== 2||z== 2||y == -2||x== -2||z== -2) {
        				a.setType(Material.GLASS);
        			}
        	            
        			else if(y == 1||x== 1||z== 1||y == -1||x== -1||z== -1) {
        				a.setType(Material.WATER);
        			}
        	
        			else if(y == -1||x== 0||z== 0||y == 0||x== 0||z== 0) {
        				a.setType(Material.AIR);
    			    }
    			};};};
	
		}else if(type == LandMineType.LAVA) {
			
				 for(int x = -2; x < 3;x++) {
	    			 for(int y = -1; y < 1;y++) {
	    				 for(int z = -2; z < 3;z++) {
	    				 
	    				 
	    				 Block a =  l.getBlock().getRelative(x,y,z);
	    		 
	            
	    				 if(x== 2||z== 2||y == -1||x== -2||z== -2) {
	         				a.setType(Material.LAVA);
		    				 //HAY DOS PARA QUE EL ULTIMO ELIMINE EL BLOQUE ORIGEN
		    				
	         			  }else if(y == -1||x== 0||z== 0||y == 0||x== 0||z== 0) {
	    					 a.setType(Material.LAVA);
	     				
	    				  }
	    		
	    		 }}}
			
			
			
		}else if(type == LandMineType.LAVA) {
			
			
       			for(int x = -2; x < 3;x++) {
					for(int y = -25; y < 1;y++) {
						for(int z = -2; z < 3;z++) {
 
 
							Block a =  l.getBlock().getRelative(x,y,z);

 
// el eje Y determina la altura y se llena de lbloque 
							if(x== 2||z== 2||y == 1||x== -2||z== -2) {
								  a.setType(Material.AIR);
		
						      }else if(x== 1||z== 1||y == 1||x== -1||z== -1) {
								a.setType(Material.AIR);
	
							  }else if(x== 0||z== 0||y == 1||x== -0||z== -0) {
								a.setType(Material.AIR);

							  }

					}}}		

			
			
		}else if(type == LandMineType.PORTAL) {
			
			for(int x = -2; x < 3;x++) {
				for(int y = 2; y < 3;y++) {
					for(int z = -2; z < 3;z++) {


						Block a =  l.getBlock().getRelative(x,y,z);


//el eje Y determina la altura y se llena de lbloque 
						if(x== 2||z== 2||y == 1||x== -2||z== -2) {
							a.setType(Material.END_PORTAL);
	
						  }else if(x== 1||z== 1||y == 1||x== -1||z== -1) {
							a.setType(Material.END_PORTAL);
						  }else if(x== 0||z== 0||y == 1||x== -0||z== -0) {

							a.setType(Material.END_PORTAL);
						 }


					}}}		
			
			
			
			
		}else if(type == LandMineType.INMOVIL) {
			 for(int x = -2; x < 3;x++) {
    			 for(int y = -1; y < 1;y++) {
    				 for(int z = -2; z < 3;z++) {
    				 
    				 
    				 Block a =  l.getBlock().getRelative(x,y,z);
    		 
            
    				 if(x== 2||z== 2||y == -1||x== -2||z== -2) {
         				a.setType(Material.AIR);
         				
         			  }else if(y == -1||x== 0||z== 0||y == 0||x== 0||z== 0) {
         				  
    	    				a.setType(Material.AIR);
    				 }
    				 
    				 
    				 }}}
         	
			
			
		}else if(type == LandMineType.EXPLOSION) {
			
			//boolean 1 fuego 2 romper bloques
			l.getWorld().createExplosion(l, 5, false, true);
			//sendMessageToAll(player.getName()+" &bpiso una Mina Explosiva.");
		}
		
		for(Player players : Bukkit.getOnlinePlayers()){
			
			if(players.getLocation().distance(l) < 6) {
				players.sendMessage(Utils.formatChatColor(players.getName()+" &cFue afectado por una Mina Remota"));
			}
			
			
		}
		
		plugin.getLandMinesData().remove(locationToString(l));

}
	
	
	public void executeLandMine(Player player , Location l , LandMineType type) {
		
		
		
		
				if(type == LandMineType.WATER) {
				
	    			for(int x = -2; x < 3;x++) {
	    				for(int y = -2; y < 3;y++) {
	    					for(int z = -2; z < 3;z++) {
	        			 
	        			    Block a =  l.getBlock().getRelative(x,y,z);
	        	     
	        			    //setea bloques en esos puntos
		        			if(y == 2||x== 2||z== 2||y == -2||x== -2||z== -2) {
		        				a.setType(Material.GLASS);
		        			}
		        	            
		        			else if(y == 1||x== 1||z== 1||y == -1||x== -1||z== -1) {
		        				a.setType(Material.WATER);
		        			}
		        	
		        			else if(y == -1||x== 0||z== 0||y == 0||x== 0||z== 0) {
		        				a.setType(Material.AIR);
		    			    }
	        			};};};
			
				}else if(type == LandMineType.LAVA) {
					
						 for(int x = -2; x < 3;x++) {
			    			 for(int y = -1; y < 1;y++) {
			    				 for(int z = -2; z < 3;z++) {
			    				 
			    				 
			    				 Block a =  l.getBlock().getRelative(x,y,z);
			    		 
			            
			    				 if(x== 2||z== 2||y == -1||x== -2||z== -2) {
			         				a.setType(Material.LAVA);
				    				 //HAY DOS PARA QUE EL ULTIMO ELIMINE EL BLOQUE ORIGEN
				    				
			         			  }else if(y == -1||x== 0||z== 0||y == 0||x== 0||z== 0) {
			    					 a.setType(Material.LAVA);
			     				
			    				  }
			    		
			    		 }}}
					
					
					
				}else if(type == LandMineType.LAVA) {
					
					
		       			for(int x = -2; x < 3;x++) {
							for(int y = -25; y < 1;y++) {
								for(int z = -2; z < 3;z++) {
		 
		 
									Block a =  l.getBlock().getRelative(x,y,z);
		
		 
	// el eje Y determina la altura y se llena de lbloque 
									if(x== 2||z== 2||y == 1||x== -2||z== -2) {
										  a.setType(Material.AIR);
				
								      }else if(x== 1||z== 1||y == 1||x== -1||z== -1) {
										a.setType(Material.AIR);
			
									  }else if(x== 0||z== 0||y == 1||x== -0||z== -0) {
										a.setType(Material.AIR);
		
									  }

							}}}		
		
					
					
				}else if(type == LandMineType.PORTAL) {
					
					for(int x = -2; x < 3;x++) {
						for(int y = 2; y < 3;y++) {
							for(int z = -2; z < 3;z++) {
	 
	 
								Block a =  l.getBlock().getRelative(x,y,z);
	
	 
// el eje Y determina la altura y se llena de lbloque 
								if(x== 2||z== 2||y == 1||x== -2||z== -2) {
									a.setType(Material.END_PORTAL);
			
								  }else if(x== 1||z== 1||y == 1||x== -1||z== -1) {
									a.setType(Material.END_PORTAL);
								  }else if(x== 0||z== 0||y == 1||x== -0||z== -0) {
	
									a.setType(Material.END_PORTAL);
								 }

	 
							}}}		
					
					
					
					
				}else if(type == LandMineType.INMOVIL) {
					 for(int x = -2; x < 3;x++) {
		    			 for(int y = -1; y < 1;y++) {
		    				 for(int z = -2; z < 3;z++) {
		    				 
		    				 
		    				 Block a =  l.getBlock().getRelative(x,y,z);
		    		 
		            
		    				 if(x== 2||z== 2||y == -1||x== -2||z== -2) {
		         				a.setType(Material.AIR);
		         				
		         			  }else if(y == -1||x== 0||z== 0||y == 0||x== 0||z== 0) {
		         				  
		    	    				a.setType(Material.AIR);
		    				 }
		    				 
		    				 
		    				 }}}
		         	
					
					
				}else if(type == LandMineType.EXPLOSION) {
					
					//boolean 1 fuego 2 romper bloques
					l.getWorld().createExplosion(l, 5, false, true);
					//sendMessageToAll(player.getName()+" &bpiso una Mina Explosiva.");
				}
				
				sendResultOfLandMine(l,player);
				plugin.getLandMinesData().remove(locationToString(l));
		
	}
	
	
	
	
	
	public void addConvertItemToLandMine(Player player, Material it,DetectionType d,LandMineType lt) {
		
		
		if(it == null) {
			sendMessage(player,"&cEl Material no existe o esta mal escrito.");
			return;
		}
		
		if(d == null) {
			sendMessage(player,"&cEl Tipo de Deteccion no existe o esta mal escrito.");
			return;
		}
		
		if(lt == null) {
			sendMessage(player,"&cEl Tipo de Mina no existe o esta mal escrito.");
			return;
		}
		
		
		ItemStack item = new ItemStack(it);
		ItemMeta meta = (ItemMeta) item.getItemMeta();	
		
		//meta.setDisplayName(Utils.formatChatColor("&a&lLANDMINE"));
		List<String> l = new ArrayList<>();
		
		l.add("");
		l.add(Utils.formatChatColor("&c&lDetectionType:"));
		l.add(Utils.formatChatColor("&e")+d.toString());
		l.add(Utils.formatChatColor("&6&lLandMineType:"));
		l.add(Utils.formatChatColor("&b")+lt.toString());
		l.add("");
		l.add(Utils.formatChatColor("&fLandMine"));
		
		meta.setLore(l);
		
		item.setItemMeta(meta);
		
		player.getInventory().addItem(item);
		
	}
	
	public void addConvertItemToLandMine(Player player, ItemStack it,DetectionType d,LandMineType lt) {
		
		
		if(d == null) {
			sendMessage(player,"&cEl Tipo de Deteccion no existe o esta mal escrito.");
			return;
		}
		
		if(lt == null) {
			sendMessage(player,"&cEl Tipo de Mina no existe o esta mal escrito.");
			return;
		}
		
		
		ItemMeta meta = (ItemMeta) it.getItemMeta();	
		
		//meta.setDisplayName(Utils.formatChatColor("&a&lLANDMINE"));
		List<String> l = new ArrayList<>();
		
		l.add("");
		l.add(Utils.formatChatColor("&c&lDetectionType:"));
		l.add(Utils.formatChatColor("&e")+d.toString());
		l.add(Utils.formatChatColor("&6&lLandMineType:"));
		l.add(Utils.formatChatColor("&b")+lt.toString());
		l.add("");
		l.add(Utils.formatChatColor("&fLandMine"));
		
		meta.setLore(l);
		
		it.setItemMeta(meta);
		
		player.getInventory().addItem(it);
		
	}
	
	
	//COLOCAR IDENTIFICADOR DE MINA ?? SI EXPLOTA CONMIGO O CON OTRO JUGADOR?
	public void registerLandMineData(Player player ,ItemStack it,Location loc) {
		
		
		if(it.hasItemMeta()) {
			
			ItemMeta meta = (ItemMeta) it.getItemMeta();
			
			if(meta.hasLore()) {
				List<String> l = meta.getLore();
				String typedetection = ChatColor.stripColor(l.get(2));
				String typelandmine = ChatColor.stripColor(l.get(4));
				String title = ChatColor.stripColor(l.get(6));
		
				if(title.equals("LandMine")) {
					plugin.getLandMinesData().put(locationToString(loc),new ExplosiveMine(player.getName(),loc,DetectionType.valueOf(typedetection),LandMineType.valueOf(typelandmine),it.getType()));
					player.sendMessage(Utils.formatChatColor("&aMinaExplosiva Colocada"));
				}
			}
		}
		
		
	}
	
	public void loadGroups() {
		FileConfiguration data = plugin.getLandMineGeneralDataYml().getConfig();
		  plugin.getPermissionGroups().clear();
		if(!data.getBoolean("Users-Groups.Enabled")) return;
		
		  for (String key : data.getConfigurationSection("Users-Groups").getKeys(false)) {
			  if(key.equals("Enabled")) continue;
		
			  String perm = data.getString("Users-Groups."+key+".Permission");
			  int value = Integer.valueOf(data.getString("Users-Groups."+key+".LandMinesLimit"));
			  //System.out.println(key+": "+perm+" "+value);
			  plugin.getPermissionGroups().put(key+":"+perm, value);
		  }
		
	}
	
	
	public boolean hasReachLimitOfLandMines(Player player) {
		FileConfiguration data = plugin.getLandMineGeneralDataYml().getConfig();
		if(player.isOp()) return false;
		if(!data.getBoolean("Users-Groups.Enabled")) return false;
		int count = 0;
		int limit = 0;
		for(Map.Entry<String,Integer> info : plugin.getPermissionGroups().entrySet()) {
			
			String text = info.getKey();
			String[] perm = text.split(":");
			
			if(player.hasPermission(perm[1])) {
				limit = info.getValue();
				for(Map.Entry<String,ExplosiveMine> mine : plugin.getLandMinesData().entrySet()) {
					
					if(mine.getValue().getOwnerName().equals(player.getName())) {
						count++;
					}
				
					
				}
			}
			
		}
		
		return (count == limit);
		
	}
	
	public void showLandMinesLimit(Player player) {
		FileConfiguration data = plugin.getLandMineGeneralDataYml().getConfig();
		FileConfiguration datam = plugin.getLandMineGeneralDataYml().getMessages();
		if(player.isOp()) return;
		if(!data.getBoolean("Users-Groups.Enabled")) return;
		int count = 0;
		int limit = 0;
		String group = "";
		for(Map.Entry<String,Integer> info : plugin.getPermissionGroups().entrySet()) {
			String text = info.getKey();
			String[] perm = text.split(":");
			group = perm[0];
			if(player.hasPermission(perm[1])) {
				limit = info.getValue();
				for(Map.Entry<String,ExplosiveMine> mine : plugin.getLandMinesData().entrySet()) {
					
					if(mine.getValue().getOwnerName().equals(player.getName())) {
						count++;
					}
				}
			}
			
		}
		if(limit == 0) {
			sendMessage(player,datam.getString("Users-Groups.UserOutOfGroups"));
		}else if(count == limit) {
			sendMessage(player,landMineTextGroup(datam.getString("Users-Groups.UserShowPlacedLandMines"), player, group, count, limit));
		}else {
			sendMessage(player,landMineTextGroup(datam.getString("Users-Groups.UserShowLimitLandMines"), player, group, count, limit));
		}
		
		
		return;
	}
	
	public void deleteLandMineData(Player player , Location loc) {
		
		if(!isaLandMine(loc)) return;
		ExplosiveMine em = plugin.getLandMinesData().get(locationToString(loc));
		
		Material m = em.getLandMineMaterial();
		
		ItemStack item = new ItemStack(m);
		ItemMeta meta = (ItemMeta) item.getItemMeta();	
		
		//meta.setDisplayName(Utils.formatChatColor("&a&lLANDMINE"));
		List<String> l = new ArrayList<>();
		
		l.add("");
		l.add(Utils.formatChatColor("&c&lDetectionType:"));
		l.add(Utils.formatChatColor("&e")+em.getDetectionType().toString());
		l.add(Utils.formatChatColor("&6&lLandMineType:"));
		l.add(Utils.formatChatColor("&b")+em.getLandMineType().toString());
		l.add("");
		l.add(Utils.formatChatColor("&fLandMine"));
		
		meta.setLore(l);
		
		item.setItemMeta(meta);
		loc.getBlock().setType(Material.AIR);
		loc.getWorld().dropItem(loc.add(0.5,1,0.5), item);
		
		sendMessage(player, "&aMina Removida");
		//player.getInventory().addItem(item);

	}
	
	
	public boolean isLandMineItem(ItemStack it) {
		if(it.hasItemMeta()) {
			if(it.getItemMeta().hasLore()) {
				List<String> l = it.getItemMeta().getLore();
				
				if(l.size() == 7) {
					String text = ChatColor.stripColor(l.get(6));
					if(text.equals("LandMine")) {
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
	
	public boolean isaLandMine(Location loc) {
		return plugin.getLandMinesData().containsKey(locationToString(loc));
	}
	
	public void removeLandMine(Location loc) {
		System.out.println("REMOVIDO: "+locationToString(loc));
		plugin.getLandMinesData().remove(locationToString(loc));
		return;
	}
	
	public ExplosiveMine getLandMineInfo(Location loc) {
		return plugin.getLandMinesData().get(locationToString(loc));
	}
	
	public void saveLandMineData() {
		FileConfiguration storage = plugin.getLandMineData();
		ConcurrentHashMap<String, ExplosiveMine> data = plugin.getLandMinesData();
		List<String> l = storage.getStringList("LandMinesData");
		storage.set("LandMinesData", l);
		for(Map.Entry<String, ExplosiveMine> map : data.entrySet()) {
			l.add(map.getValue().toData());
		}
		
		plugin.getLandMineData().save();
		plugin.getLandMineData().reload();
		
	}
	
	
	public void loadLandMineData() {
		FileConfiguration storage = plugin.getLandMineData();
		ConcurrentHashMap<String, ExplosiveMine> data = plugin.getLandMinesData();
		
		List<String> l = storage.getStringList("LandMinesData");
		for(String t : l) {
			data.put(locationToStringSplit(t),stringToExplosiveMine(t,stringToLocation(t)));
		}

		
	}
	
	
	public String locationToString(Location loc) {
		return loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ();
	}
	
	public String locationToStringSplit(String text) {
		String[] split = text.split(";");
		
		return split[0];
	}

	
	public Location stringToLocation(String text) {
		String[] split = text.split(",");
	
		return new Location(Bukkit.getWorld(split[0]),Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3]));
	}
	
	public ExplosiveMine stringToExplosiveMine(String text,Location loc) {
		String[] split = text.split(";");
		String[] split2 = split[1].split(",");
		
		return new ExplosiveMine(split2[0],loc,DetectionType.valueOf(split2[2]),LandMineType.valueOf(split2[3]),Material.valueOf(split2[1]));
	}
	
	public void deleteLandMineData() {
		FileConfiguration storage = plugin.getLandMineData();
	
		storage.set("LandMinesData", null);
		
		plugin.getLandMineData().save();
		plugin.getLandMineData().reload();
		
	}
	
	
	public void checkLandMine(Player player , Location loc) {
		if(isaLandMine(loc)) {
			player.sendMessage("Es una Mina");
		}else {
			player.sendMessage("No es una Mina");
		}
		
	}
	
	public boolean isWorldAllowedForLandMines(String text) {
		FileConfiguration data = plugin.getLandMineGeneralDataYml().getConfig();
		List<String> worlds = data.getStringList("Allowed-Worlds-For-LandMines.List");
	    return worlds.isEmpty() || worlds.contains(text);
	
	}
	
	public void printResult(Player player) {
		ConcurrentHashMap<String, ExplosiveMine> data = plugin.getLandMinesData();
		Location loc = player.getLocation();
		Location under = loc.getBlock().getRelative(0, -1, 0).getLocation();
		for(Map.Entry<String,ExplosiveMine> l : data.entrySet()) {
			System.out.println(l.getKey());
		}
		System.out.println(locationToString(under));
	}
	
	public void sendResultOfLandMine(Location loc,Player player) {
		
		FileConfiguration data = plugin.getLandMineGeneralDataYml().getMessages();
		ExplosiveMine mineinfo = plugin.getLandMinesData().get(locationToString(loc));
		
		if(!data.getBoolean("EnabledLandMineMessage")) return;
		
		List<String> worldmessage = data.getStringList("Allowed-Worlds-For-LandMinesMessages.List");
		List<String> landminetype = data.getStringList("HideLandMineTypeMessage.List");
		
			if(landminetype.contains(mineinfo.getLandMineType().toString())) return;
		
			if(worldmessage.isEmpty() && data.getBoolean("ConsoleSendMessage")) {
				
				if(mineinfo.getDetectionType() == DetectionType.MOVE) {
					
					
					if(mineinfo.getLandMinePosition() == Position.UP) {
						sendBroadCastMessage(landMineText(data.getString("LandMine-ActivationMessage.AboveTheirHead-Message"),player,mineinfo));
	
					}else if(mineinfo.getLandMinePosition() == Position.MIDDLE) {
						sendBroadCastMessage(landMineText(data.getString("LandMine-ActivationMessage.Through-Message"),player,mineinfo));
	
					}else if(mineinfo.getLandMinePosition() == Position.DOWN) {
						sendBroadCastMessage(landMineText(data.getString("LandMine-ActivationMessage.Stepp-Message"),player,mineinfo));
	
					}
	
				}else if(mineinfo.getDetectionType() == DetectionType.INTERACT) {
					sendBroadCastMessage(landMineText(data.getString("LandMine-ActivationMessage.Interact-Message"),player,mineinfo));
	
	
				}else if(mineinfo.getDetectionType() == DetectionType.BLOCKBREAK) {
					sendBroadCastMessage(landMineText(data.getString("LandMine-ActivationMessage.BlockBreak-Message"),player,mineinfo));
	
				}
				return;
			}
	
		
			if(mineinfo.getDetectionType() == DetectionType.MOVE) {
				if(mineinfo.getLandMinePosition() == Position.UP) {
					sendMessageToAll(landMineText(data.getString("LandMine-ActivationMessage.AboveTheirHead-Message"),player,mineinfo),worldmessage,data.getBoolean("ConsoleSendMessage"));

				}else if(mineinfo.getLandMinePosition() == Position.MIDDLE) {
					sendMessageToAll(landMineText(data.getString("LandMine-ActivationMessage.Through-Message"),player,mineinfo),worldmessage,data.getBoolean("ConsoleSendMessage"));

				}else if(mineinfo.getLandMinePosition() == Position.DOWN) {
					sendMessageToAll(landMineText(data.getString("LandMine-ActivationMessage.Stepp-Message"),player,mineinfo),worldmessage,data.getBoolean("ConsoleSendMessage"));

				}
			}else if(mineinfo.getDetectionType() == DetectionType.INTERACT) {
				sendMessageToAll(landMineText(data.getString("LandMine-ActivationMessage.Interact-Message"),player,mineinfo),worldmessage,data.getBoolean("ConsoleSendMessage"));

			}else if(mineinfo.getDetectionType() == DetectionType.BLOCKBREAK) {
				sendMessageToAll(landMineText(data.getString("LandMine-ActivationMessage.BlockBreak-Message"),player,mineinfo),worldmessage,data.getBoolean("ConsoleSendMessage"));

			}
		
	}
	
	public String landMineText(String text,Player player, ExplosiveMine mine) {
		return text.replaceAll("%player%",player.getName()).replaceAll("%landminetype%",mine.getLandMineType().getValue()).replaceAll("%landmineowner%",mine.getOwnerName());
	}
	
	public String landMineTextGroup(String text,Player player,String group , int current, int limit) {
		return text.replaceAll("%player%",player.getName()).replaceAll("%group%", group).replaceAll("%currentlandmines%", String.valueOf(current)).replaceAll("%limitlandmines%", String.valueOf(limit));
	}
	
	
	public void switchsendMessage(Player player , String texto) {
		if(player != null) {
			player.sendMessage(Utils.formatChatColor(texto));
			return;
		}
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor(texto));
		
	}
	
	public void sendMessage(Player player , String texto) {
		player.sendMessage(Utils.formatChatColor(texto));
	}
	
	public void sendMessageToAll(String text,List<String> worlds,boolean console) {
		
		if(console) {
			Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor(text));
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!worlds.contains(p.getWorld().getName())) continue;
			p.sendMessage(Utils.formatChatColor(text));
		}
		
	}
	
	
	
	
	public void sendBroadCastMessage(String text) {
		Bukkit.broadcastMessage(Utils.formatChatColor(text));
	}
	
	public void showPlayersLandMines(Player player, int pag) {
		ConcurrentHashMap<String,ExplosiveMine> data = plugin.getLandMinesData();
		Map<String,Integer> countByUser = new HashMap<>();
		
		switchsendMessage(player,"&aHay un Total de: &c"+data.size()+" &aMinas");
		switchsendMessage(player,"&aJugadores:");
		for(ExplosiveMine mine : data.values()) {
			String owner = mine.getOwnerName();
			countByUser.put(owner,countByUser.getOrDefault(owner, 0) + 1);
		}
		
	}
		
		public void showPlayerLandMines(Player player, String name, int pag) {
			ConcurrentHashMap<String,ExplosiveMine> data = plugin.getLandMinesData();
			
			
		  switchsendMessage(player,name+" &atiene un Total de: &c"+data.size()+" &aMinas");
			
		  List<String> l = data.values().stream().filter(mine -> mine.getOwnerName().equals(name)).map(ExplosiveMine::toData).collect(Collectors.toList());
		
//		for(Map.Entry<String,Integer> entry : countByUser.entrySet()) {
//			
//		}
		
		
			pagsSystem(player, l, pag, 10);	
	}
	
	
	public void showWorldsLandMines(Player player, int pag) {
		ConcurrentHashMap<String,ExplosiveMine> data = plugin.getLandMinesData();
		Map<String,Integer> countByworld = new HashMap<>();
		
		switchsendMessage(player,"&aHay un Total de: &c"+data.size()+" &aMinas");
		switchsendMessage(player,"&aMundos:");
		for(ExplosiveMine mine : data.values()) {
			String world = mine.getLocationLandMine().getWorld().getName();
			countByworld.put(world,countByworld.getOrDefault(world, 0) + 1);
		}
		
//		for(Map.Entry<String,Integer> entry : countByUser.entrySet()) {
//			
//		}
		
		
		pagsSystem(player, countByworld, pag, 10);	
	}
	
	 public void pagsSystem(Player player,List<String> l , int pag,int datosperpags) {
	    	
	    	if(!l.isEmpty() || l.size() != 0) {
	    		int inicio = (pag -1) * datosperpags;
	    		int fin = inicio + datosperpags;
	    		
	    		int tamañolista = l.size();
	    		int numerodepags = (int) Math.ceil((double) tamañolista /datosperpags);
	    		
	    		if(pag > numerodepags) {
	    			if(player != null) {
		    			player.sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+ChatColor.GREEN+" Paginas en Total: "+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));

	    			}
	    			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+ChatColor.GREEN+" Paginas en Total: "+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    			return;
	    		}
	    		if(player != null) {
	    			
		    		player.sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    		}
	    		
	    	
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    	
	    		for(int i = inicio;i < fin && i < l.size();i++) {
	    			if(player != null) {
	    				player.sendMessage(""+ChatColor.GREEN+(i+1)+"). "+l.get(i));
	    			}
	    			Bukkit.getConsoleSender().sendMessage(""+ChatColor.GREEN+(i+1)+"). "+ChatColor.WHITE+l.get(i));
	    					
	    		}
	    		
	    	}else {
	    		if(player != null) {
		    		player.sendMessage(ChatColor.RED+"No hay datos para mostrar.");

	    		}
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay datos para mostrar.");
	    	}
	    	return;
	    }
	 
	 
	 public void pagsSystem(Player player, Map<String, Integer> map, int pag, int datosperpags) {
		    if (!map.isEmpty()) {
		        int inicio = (pag - 1) * datosperpags;
		        int fin = inicio + datosperpags;
		        int tamañomapa = map.size();
		        int numerodepags = (int) Math.ceil((double) tamañomapa / datosperpags);

		        if (pag > numerodepags) {
		            if (player != null) {
		                player.sendMessage(ChatColor.RED + "No hay mas datos para mostrar en la pag: " + ChatColor.GOLD + pag + ChatColor.GREEN + " Paginas en Total: " + ChatColor.RED + numerodepags);
		            }
		            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No hay mas datos para mostrar en la pag: " + ChatColor.GOLD + pag + ChatColor.GREEN + " Paginas en Total: " + ChatColor.RED + numerodepags);
		            return;
		        }

		        if (player != null) {
		            player.sendMessage(ChatColor.GOLD + "Paginas: " + ChatColor.RED + pag + ChatColor.GOLD + "/" + ChatColor.RED + numerodepags);
		        }
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Paginas: " + ChatColor.RED + pag + ChatColor.GOLD + "/" + ChatColor.RED + numerodepags);

		        int i = 0;
		        for (Map.Entry<String, Integer> entry : map.entrySet()) {
		            if (i >= inicio && i < fin) {
		                if (player != null) {
		                    player.sendMessage("" + ChatColor.GREEN + (i + 1) + "). " + entry.getKey() + ": " + entry.getValue());
		                }
		                Bukkit.getConsoleSender().sendMessage("" + ChatColor.GREEN + (i + 1) + "). " + ChatColor.WHITE + entry.getKey() + ": " + entry.getValue());
		            }
		            i++;
		            if (i >= fin) {
		                break;
		            }
		        }
		    } else {
		        if (player != null) {
		            player.sendMessage(ChatColor.RED + "No hay datos para mostrar.");
		        }
		        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No hay datos para mostrar.");
		    }
		    return;
		}
	
}
