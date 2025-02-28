package me.nao.database.mg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionMySQL {
	
	private Connection connection;
	private String host;
	private int puerto;
	private String database;
	private String usuario;
	private String password;
	
	
	
	public ConexionMySQL(String host, int puerto, String database, String usuario, String password) {
		this.host = host;
		this.puerto = puerto;
		this.database = database;
		this.usuario = usuario;
		this.password = password;
		
		try {
			  Logger logger = Logger.getLogger(ConexionMySQL.class.getName());
			synchronized(this) {
				if(connection != null && !connection.isClosed()) {
					
				      logger.log(Level.SEVERE,"Error al conectar con la Base de Datos");
					return;
				}
				//com.mysql.cj.jdbc.Driver //old driver:com.mysql.jdbc.Driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				this.connection = DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.puerto+"/"+this.database,this.usuario,this.password);
				logger.log(Level.INFO,"Plugin Conectado a la Base de Datos con exito.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	public Connection getConnection() {
		return connection;
	}



	
	
	
	

}
