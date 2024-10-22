package me.nao.database;

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

import me.nao.main.game.Minegame;


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
	
	
	
	
	
	//set info 
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
	
	
	public static void createtableInventory(Connection connection) {
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
	
	
	public static boolean isPlayerinDB(Connection connection,UUID uuid) {
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
	
	
	//TODO SAVE
	public static void SavePlayerInventory(Connection connection , UUID uuid , String name,String inv,Player player) {
		
		try {	
		
			if(!isPlayerinDB(connection, uuid)) {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO Inventory VALUE (?,?,?)");
				statement.setString(1, uuid.toString());
				statement.setString(2, name);
				statement.setString(3,inv);
			    statement.executeUpdate();
			    
			  player.sendMessage(ChatColor.GREEN+"Inventario Salvado con Exito ...");
			}else{
				setNewInventory(connection, uuid, inv,player);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void GetPlayerInventory(Connection connection,UUID uuid,Player player) throws IllegalArgumentException, IOException ,ClassNotFoundException{
		try {
			//VanishManager v = new VanishManager(null);
		
				
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM Inventory WHERE (UUID=?)");
				statement.setString(1,uuid.toString());
				ResultSet resultado = statement.executeQuery();
				if(resultado.next()) {
				
					String inv = resultado.getString("Inventario");
			
					//Inventory invi = BukkitSerialization.fromBase64(inv);
					ItemStack[] items = BukkitSerialization.deserializar(inv);
				
					player.getInventory().setContents(items);
					
					//String name = resultado.getString("Nombre");
					//si deseas puedes mandar un mensaje al jugador
					
				}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setNewInventory(Connection connection,UUID uuid,String inv,Player player) {
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
	
	
	public static void DeleteUserInventory(Connection connection,UUID uuid,Player player) {
		try {
			
			if(!isPlayerinDB(connection, uuid)) {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM Inventory WHERE (UUID=?)");
				statement.setString(1,uuid.toString());
				statement.executeUpdate();
			
			}else {
				
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
