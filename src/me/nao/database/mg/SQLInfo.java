package me.nao.database.mg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nao.main.mg.Minegame;

@SuppressWarnings("deprecation")
public class SQLInfo {
 
	private  Minegame plugin;
	

	public SQLInfo(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	public String Host() {
		FileConfiguration config = plugin.getConfig();
		return config.getString("Data-Base.Host");
	}
	
	public int Puerto() throws NumberFormatException{
		FileConfiguration config = plugin.getConfig();
		
		return config.getInt("Data-Base.Puerto");
	}
	
	public String BaseDeDatos() {
		FileConfiguration config = plugin.getConfig();
		return config.getString("Data-Base.BaseDeDatos");
	}
	
	public String Usuario() {
		FileConfiguration config = plugin.getConfig();
		return config.getString("Data-Base.Usuario");
	}
	
	public String Clave() {
		FileConfiguration config = plugin.getConfig();
		return config.getString("Data-Base.Clave");
	}
	
	
	public static boolean isConnected(Connection connection) {
	    try {
	        return connection != null && !connection.isClosed();
	    } catch (SQLException e) {
	        return false;
	    }
	}
	
	
	//set info ejemplos
	public static void createtable(Connection connection) {
		try {
		
				
			PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Inventory ("+ 
				//PreparedStatement statement = connection.prepareStatement("CREATE TABLE Inventory ("+ 
						"UUID VARCHAR (40) NOT NULL," + 
						"Nombre VARCHAR (40)," + 
						"Value BOOLEAN ," +
						"PRIMARY KEY (UUID)"+
						");");
			
				statement.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//EN USO
	public static void createtableInventory(Connection connection) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
			
			PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Inventory ("+ 
				//PreparedStatement statement = connection.prepareStatement("CREATE TABLE Inventory ("+ 
						"UUID VARCHAR (40) NOT NULL," + 
						"Nombre VARCHAR (40)," + 
						"Inventario TEXT ," +
						"PRIMARY KEY (UUID)"+
						");");
			
			statement.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createTableItems(Connection connection) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	            "CREATE TABLE IF NOT EXISTS Items (" +
	            "ID VARCHAR(40) NOT NULL PRIMARY KEY," +
	            "ItemData TEXT" +
	            ");"
	        );
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void createTableKits(Connection connection) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	            "CREATE TABLE IF NOT EXISTS Kits (" +
	            "ID VARCHAR(40) NOT NULL PRIMARY KEY," +
	            "ItemData TEXT" +
	            ");"
	        );
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static boolean isPlayerinDB(Connection connection,UUID uuid) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return false;
		}
		
		try {
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM Inventory WHERE (UUID=?)");
				statement.setString(1,uuid.toString());
				ResultSet resultado = statement.executeQuery();
				if(resultado.next()) {
					return true;
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	
	
	public static boolean isItemInDB(Connection connection, String id) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return false;
		}
		
	    try {
	        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Items WHERE ID = ?");
	        statement.setString(1, id);
	        ResultSet resultado = statement.executeQuery();
	        if (resultado.next()) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	public static boolean isKitInDB(Connection connection, String id) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return false;
		}
		
	    try {
	        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Kits WHERE ID = ?");
	        statement.setString(1, id);
	        ResultSet resultado = statement.executeQuery();
	        if (resultado.next()) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	
	//TODO SAVE
	public static void SavePlayerInventory(Connection connection , UUID uuid , String name,String inv,Player player) {
		
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {	
		
			if(!isPlayerinDB(connection, uuid)) {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO Inventory VALUE (?,?,?)");
				statement.setString(1, uuid.toString());
				statement.setString(2, name);
				statement.setString(3,inv);
			    statement.executeUpdate();
			    
			  player.sendMessage(ChatColor.GREEN+"Inventario Salvado con Exito ...");
			}else{
				setUpdateInventory(connection, uuid, inv,player);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void SavePlayerItem(Connection connection,Player player,String id,String item) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
	    try {
	        if (!isItemInDB(connection, id)) {
	            PreparedStatement statement = connection.prepareStatement("INSERT INTO Item (ID, ItemData) VALUES (?, ?)");
	            statement.setString(1, id.toString());
	            statement.setString(2, item);
	            statement.executeUpdate();
	            player.sendMessage(ChatColor.GREEN + "Item Salvado con Exito ...");
	        } else {
	           //setNewInventory(connection, uuid, inv, player);
	        	setUpdateItem(connection, player, id, item);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public static void SavePlayerKit(Connection connection,Player player,String id,String kit) {
		
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
	    try {
	        if (!isKitInDB(connection, id)) {
	            PreparedStatement statement = connection.prepareStatement("INSERT INTO Kits (ID, ItemData) VALUES (?, ?)");
	            statement.setString(1, id.toString());
	            statement.setString(2, kit);
	            statement.executeUpdate();
	            player.sendMessage(ChatColor.GREEN + "Kit Salvado con Exito ...");
	        } else {
	           //setNewInventory(connection, uuid, inv, player);
	        	setUpdateItem(connection, player, id, kit);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void GetPlayerInventory(Connection connection,UUID uuid,Player player) throws IllegalArgumentException, IOException ,ClassNotFoundException{
		
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
			//VanishManager v = new VanishManager(null);
		
				
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM Inventory WHERE (UUID=?)");
				statement.setString(1,uuid.toString());
				ResultSet resultado = statement.executeQuery();
				if(resultado.next()) {
				
					String inv = resultado.getString("Inventario");
			
					//Inventory invi = BukkitSerialization.fromBase64(inv);
					ItemStack[] items = BukkitSerialization.deserializarMultipleItems(inv);
				
					player.getInventory().setContents(items);
					
					//String name = resultado.getString("Nombre");
					//si deseas puedes mandar un mensaje al jugador
					
				}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	//TODO ITEM
	public static void getItemData(Connection connection,Player player, String id) throws IllegalArgumentException, IOException, ClassNotFoundException {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
	    try {
	        PreparedStatement statement = connection.prepareStatement("SELECT ItemData FROM Items WHERE ID = ?");
	        statement.setString(1, id);
	        ResultSet resultado = statement.executeQuery();
	        if (resultado.next()) {
	        	String itemData = resultado.getString("ItemData");
	        	
	        	  
	       		if (itemData != null) {
	       		    ItemStack item = BukkitSerialization.deserializarOneItem(itemData);
	       		  
	       		 if(player.getInventory().firstEmpty() == -1) {
	    			 player.sendMessage(ChatColor.RED+"Tu Inventario esta lleno el Item se Dropeara al Piso.");
	    			 player.getWorld().dropItem(player.getLocation(), item);
	       		 }else{
	       			 
	       			 if(player.getInventory().getItemInMainHand() == null) {
	       				player.getInventory().setItemInMainHand(item);
	       			 }else {
	       				player.getInventory().addItem(item);
	       			 }
	       			 
	       		
	       		 }
	       		    
	       		    player.sendMessage(ChatColor.GREEN + "Item cargado con éxito...");
	       		}else {
	       		    player.sendMessage(ChatColor.RED + "No se encontró el item en la base de datos.");
	       		}
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	 
	}
	
	public static void getKitData(Connection connection,Player player, String id) throws IllegalArgumentException, IOException, ClassNotFoundException {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
	    try {
	        PreparedStatement statement = connection.prepareStatement("SELECT ItemData FROM Kits WHERE ID = ?");
	        statement.setString(1, id);
	        ResultSet resultado = statement.executeQuery();
	        if (resultado.next()) {
	        	String itemData = resultado.getString("ItemData");
	        	
	        	  
	       		if (itemData != null) {
	    
	       		  
	       			ItemStack[] kit = BukkitSerialization.deserializarMultipleItems(itemData);
					
					player.getInventory().setContents(kit);
	       		    
	       		    player.sendMessage(ChatColor.GREEN + "Kit cargado con éxito...");
	       		} else {
	       		    player.sendMessage(ChatColor.RED + "No se encontró el Kit en la base de datos.");
	       		}
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	 
	}
	
	public static void setUpdateInventory(Connection connection,UUID uuid,String inv,Player player) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
				PreparedStatement statement = connection.prepareStatement("UPDATE Inventory SET Inventario=? WHERE (UUID=?)");
				statement.setString(1,inv);
				statement.setString(2, uuid.toString());
				statement.executeUpdate();
				player.sendMessage(ChatColor.GREEN+"Inventario Salvado con Exito .");
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	
	//TODO ITEM
	public static void setUpdateItem(Connection connection,Player player,String id , String item) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
				PreparedStatement statement = connection.prepareStatement("UPDATE Items SET ItemData=? WHERE (ID=?)");
				statement.setString(1,id);
				statement.setString(2, item);
				statement.executeUpdate();
				player.sendMessage(ChatColor.GREEN+"Item Actualizado con Exito.");
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	
	
	//TODO ITEM
	public static void setUpdateKit(Connection connection,Player player,String id , String kit) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
				PreparedStatement statement = connection.prepareStatement("UPDATE Kits SET ItemData=? WHERE (ID=?)");
				statement.setString(1,id);
				statement.setString(2, kit);
				statement.executeUpdate();
				player.sendMessage(ChatColor.GREEN+"Kit Actualizado con Exito.");
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	
	public static void DeleteUserInventory(Connection connection,UUID uuid,Player player) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
			
			if(isPlayerinDB(connection, uuid)) {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM Inventory WHERE (UUID=?)");
				statement.setString(1,uuid.toString());
				statement.executeUpdate();
			
			}else {
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//TODO ITEM
	public static void DeleteItem(Connection connection,Player player,String id) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
			
			if(isItemInDB(connection, id)) {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM Items WHERE (ID=?)");
				statement.setString(1,id);
				statement.executeUpdate();
			
				player.sendMessage(ChatColor.GREEN+"El Kit "+id+" fue Borrado.");
				
			}else {
				player.sendMessage(ChatColor.RED+"El Kit "+id+" no existe o fue Borrado.");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//TODO ITEM
	public static void DeleteKit(Connection connection,Player player,String id) {
		
		if(!isConnected(connection)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+ChatColor.YELLOW+" La configuracion de la Base de Datos esta en "+ChatColor.GOLD+"true"+ChatColor.YELLOW+" pero no se esta Conectada a la Base de Datos.");
			return;
		}
		
		try {
			
			if(isKitInDB(connection, id)) {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM Kits WHERE (ID=?)");
				statement.setString(1,id);
				statement.executeUpdate();
			
				player.sendMessage(ChatColor.GREEN+"El Item "+id+" fue Borrado.");
				
			}else {
				player.sendMessage(ChatColor.RED+"El Item "+id+" no existe o fue Borrado.");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	//TODO END
	public static boolean TableExist(Connection connection){
		
			//Name:"{\"text\":\"ESPADA NICHIRIN\"}"}}}}] 
		try {	
				PreparedStatement statement = connection.prepareStatement("SHOW TABLES LIKE \"Vanish2\"");
				
				statement.executeUpdate();
				return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
		
	
	public static void getAllTrue2(Connection connection) {
		try {
			//SELECT Value, Nombre FROM Tabla1 WHERE Value = true
			//SELECT COUNT(*) FROM Vanish WHERE Value = true da el total de jugadores con true
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vanish WHERE Value = true");
			
			ResultSet resultado = statement.executeQuery();
			if(resultado.next()) {
				//SELECT Nombre FROM Vanish WHERE Value = 'true'
				boolean value = resultado.getBoolean("Value");
				String id = resultado.getString("UUID");
				String name = resultado.getString("Nombre");
				
				//si deseas puedes mandar un mensaje al jugador
				Bukkit.getConsoleSender().sendMessage("\nID: "+id+" Nombre: "+name+" Value: "+value);
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void getAllTrue(Connection connection,Player player) {
		try {
			List <String> list = new ArrayList<String>();
			StringBuilder sb1 = new StringBuilder();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vanish WHERE Value = true");
			
			ResultSet resultado = statement.executeQuery();
			//int total = 0;
			if(player != null) {
				player.sendMessage(ChatColor.GREEN+"[Jugadores que estan usando Vanish en la Network]");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"[Jugadores que estan usando Vanish en la Network]");
			while(resultado.next()) {
				//SELECT Nombre FROM Vanish WHERE Value = 'true'
			//	boolean value = resultado.getBoolean("Value");
			//	String id = resultado.getString("UUID");
				String name = resultado.getString("Nombre");
				list.add(name);
				//si deseas puedes mandar un mensaje al jugador
			//	Bukkit.getConsoleSender().sendMessage("\nID: "+id+" Nombre: "+name+" Value: "+value);
			
			}
			if(list.isEmpty()) {
				if(player != null) {
					player.sendMessage(ChatColor.RED+"Nadie esta usando Vanish.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Nadie esta usando Vanish.");
			}else {
				for(int i = 0; i < list.size();i++) {
				 sb1.append(ChatColor.GOLD+list.get(i)+ChatColor.AQUA+",");
				}
				sb1.append(ChatColor.RED+" Total: "+ChatColor.GREEN+list.size());
				
				String p = sb1.toString();
				
				if(player != null) {
					player.sendMessage(p);
				}
				Bukkit.getConsoleSender().sendMessage(p);
			}
			
			//Bukkit.getConsoleSender().sendMessage("Total: "+total);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/*
	public static void getAllTrue2(Connection connection,Player player) {
		try {
			List <String> list = new ArrayList<String>();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vanish WHERE Value = true");
			
			ResultSet resultado = statement.executeQuery();
			int total = 0;
			if(player != null) {
				player.sendMessage(ChatColor.GREEN+"[Jugadores que estan usando Vanish en la Network]");
			}
			while(resultado.next()) {
				//SELECT Nombre FROM Vanish WHERE Value = 'true'
			   boolean value = resultado.getBoolean("Value");
				String id = resultado.getString("UUID");
				String name = resultado.getString("Nombre");
				
				
				
				
				
				
				//si deseas puedes mandar un mensaje al jugador
			//	Bukkit.getConsoleSender().sendMessage("\nID: "+id+" Nombre: "+name+" Value: "+value);
			 total++;
			}
			Bukkit.getConsoleSender().sendMessage("Total: "+total);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	*/
	public static void getAllJoin(Connection connection,Player player) {
		try {
			//VanishManager vanish = new VanishManager(plugin);
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vanish WHERE Value = true");
			
			ResultSet resultado = statement.executeQuery();
		//	int total = 0;
			
			while(resultado.next()) {
				//SELECT Nombre FROM Vanish WHERE Value = 'true'
				
				String name = resultado.getString("Nombre");
				Player target = Bukkit.getServer().getPlayerExact(name);
				
				if(target != null) {
					
					//player.hidePlayer(target);
				}
				
		
			}
		
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void getValueChange(Connection connection,UUID uuid,Player player) {
		try {
			//VanishManager v = new VanishManager(null);
		
				
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vanish WHERE (UUID=?)");
				statement.setString(1,uuid.toString());
				ResultSet resultado = statement.executeQuery();
				if(resultado.next()) {
				
					boolean value = resultado.getBoolean("Value");
					//String name = resultado.getString("Nombre");
					//si deseas puedes mandar un mensaje al jugador
					if(value == true) {
						setValue(connection,uuid, false);
					
					
					}else {
						setValue(connection,uuid, true);
						
						
					}
					
					
				}
			
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void setValue(Connection connection,UUID uuid,boolean value) {
	try {
			PreparedStatement statement = connection.prepareStatement("UPDATE Vanish SET Value=? WHERE (UUID=?)");
			statement.setBoolean(1,value);
			statement.setString(2, uuid.toString());
			statement.executeUpdate();
		
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void DeleteUser(Connection connection,UUID uuid) {
		try {
			
			if(!isPlayerinDB(connection, uuid)) {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM Vanish WHERE (UUID=?)");
				statement.setString(1,uuid.toString());
				statement.executeUpdate();
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	
	
	
	
}
