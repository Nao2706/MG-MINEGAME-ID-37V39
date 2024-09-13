package me.nao.yamlfile.game;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.nao.main.game.Main;

public class YamlFilePlus extends YamlConfiguration {

	
	private Main plugin;
	
	public YamlFilePlus(Main plugin) {
		this.plugin = plugin;
	}
	

		  public YamlConfiguration getSpecificYamlFile(String folder ,String name ) {
			  
		    File file = new File(new File(plugin.getDataFolder().getAbsolutePath() + "/" + folder + "/"), name+".yml");
		    
		    if(file.exists()) {
		    	
			        YamlConfiguration yaml = new YamlConfiguration();
			      	try {
			      		if(file.getName().equals(name+".yml")) {
			      			yaml.load(file);
			      			//Bukkit.getConsoleSender().sendMessage("ggdefaultnao");
						       return yaml;
			      		}
				      } catch (IOException | InvalidConfigurationException e) {
				        e.printStackTrace();
				      }
			    }
		    return null;
		  }
		  
		  
		  public YamlConfiguration getSpecificYamlFileSave(String folder ,String name ) {
			  
			  File file = new File(new File(plugin.getDataFolder().getAbsolutePath() + "/" + folder + "/"), name+".yml");
			    
			    if(file.exists()) {
			    	
				        YamlConfiguration yaml = new YamlConfiguration();
				      	try {
				      		if(file.getName().equals(name+".yml")) {
				      			yaml.load(file);
				      			yaml.save(file);
				      			Bukkit.getConsoleSender().sendMessage("ggdefaultnao2ojalafunq");
							    return yaml;
				      		}
					      } catch (IOException | InvalidConfigurationException e) {
					        e.printStackTrace();
					      }
				    }
			    return null;
			  }
		  
		  
		  public void getSpecificYamlFileSaveUltra(File file) {
			  
			    
			    if(file.exists()) {
			    	
				      	try {
				      			save(file.getAbsoluteFile());
	              				Bukkit.getConsoleSender().sendMessage("gg134");

				      		
					      } catch (IOException  e) {
					        e.printStackTrace();
					      }
				    }
			  }
		  
		
		   public void saveSpecificPlayer(String name) {
		    	
		    	FileConfiguration config = plugin.getConfig();
				List<String> ac = config.getStringList("Maps-Created.List");


				if(!ac.isEmpty()) {
				
					if(ac.contains(name)) {
						config.set("Maps-Created.List",ac);
						ac.remove(name);
						plugin.getConfig().save();
						plugin.getConfig().reload();
					    File folder = this.plugin.getDataFolder();
				        File file = new File(folder, name);
				        String[] nombre_archivos = folder.list();
				       
				        	if(file.exists()) {
				        		try {
									save(file);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				       
				        	}
				        	else if(!file.exists()) {
				        	     for(int i = 0; i < nombre_archivos.length;i++) {
					              	 File file2 = new File(folder.getAbsolutePath(), nombre_archivos[i]);
					              	 //si hay una carpeta
					              	 if(file2.isDirectory()) {
					              		 //si hay enlistas las cosas que tiene dentro
					              		 String[] sub_archivos = file2.list();
					              		
					              		 for(int j = 0 ;j<sub_archivos.length;j++) {
					              			
					              			File file3 = new File(file2.getAbsolutePath(), sub_archivos[j]);
					              		
					              				
					              				if(file3.getName().equals(name+".yml")) {
					              					
					              				try {	
					              					save(file3.getAbsoluteFile());
					              				} catch (IOException e) {
						              		        e.printStackTrace();
						              		      }
					              					
					              				//da la ruta igual que el absolute path	file3.getAbsoluteFile();
					              				
					              					
					              					break;
					              				}
					              				
					              				
					              				
					              				   
					              			
					              		
					              		 }
					              	  
					              	 }
					              }
				        	}
				        	
					}
				
					
				}else{
			           
		            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" El archivo no existe o su nombre esta mal escrito ");
		            
		           }
		    	

		    }
		  
		   public void reloadSpecificYml(String name) {
		    	
		    	//FileConfiguration config = plugin.getConfig();
	
					    File folder = this.plugin.getDataFolder();
				        File file = new File(folder, name);
				        String[] nombre_archivos = folder.list();
				       

				        try {
				        	if(file.exists()) {
				        		 load(file);
				        	}
				        	else{
				           
				            for(int i =0; i < nombre_archivos.length;i++) {
				              	 File file2 = new File(folder.getAbsolutePath(), nombre_archivos[i]);
				              	 if(file2.isDirectory()) {
				              		 String[] sub_archivos = file2.list();
				              		 for(int j = 0 ;j<sub_archivos.length;j++) {
				              			File file3 = new File(file2.getAbsolutePath(), sub_archivos[j]);
				              			if(file3.exists()) {
				              				if(name.equals(file3.getName())) {
				              					load(file3.getAbsoluteFile());
				              					Bukkit.getConsoleSender().sendMessage("gg2 reload");
				              				}
				              			}
				              		
				              		 }
				              	  
				              	 }
				              }
				            }
				        } catch (IOException | InvalidConfigurationException e ) {
				            this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" +name + "' failed.", e);
				        }
				        	
					
		   }		
					
			
		    	

		    	  
		

	
    public void deleteSpecificPlayer(Player player ,String name) {
    	
    	FileConfiguration config = plugin.getConfig();
		List<String> ac = config.getStringList("Maps-Created.List");


		if(!ac.isEmpty()) {
		
			if(ac.contains(name)) {
				
				
				config.set("Maps-Created.List",ac);
				ac.remove(name);
				plugin.getConfig().save();
				plugin.getConfig().reload();
				
				
				FileConfiguration menuitems = plugin.getMenuItems();
				
				menuitems.set(name,null);
				
				   plugin.getMenuItems().save();
				   plugin.getMenuItems().reload();
				
				
			    File folder = this.plugin.getDataFolder();
		        File file = new File(folder, name);
		        String[] nombre_archivos = folder.list();
		       
		        	if(file.exists()) {
		        		 file.delete();
		       
		        	}
		        	else if(!file.exists()) {
		        	     for(int i = 0; i < nombre_archivos.length;i++) {
			              	 File file2 = new File(folder.getAbsolutePath(), nombre_archivos[i]);
			              	 //si hay una carpeta
			              	 if(file2.isDirectory()) {
			              		 //si hay enlistas las cosas que tiene dentro
			              		 String[] sub_archivos = file2.list();
			              		
			              		 for(int j = 0 ;j<sub_archivos.length;j++) {
			              			
			              			File file3 = new File(file2.getAbsolutePath(), sub_archivos[j]);
			              		
			              				
			              				if(file3.getName().equals(name+".yml")) {
			              					file3.getAbsoluteFile().delete();
			              				//da la ruta igual que el absolute path	file3.getAbsoluteFile();
			              					player.sendMessage(ChatColor.GREEN+"Se a Eliminado el Mapa: "+name);
			              					
			              					break;
			              				}
			              				
			              				
			              				
			              				   
			              			
			              		
			              		 }
			              	  
			              	 }
			              }
		        	}
		        	
			}
		
			
		}else{
	           
            player.sendMessage(ChatColor.RED+" El archivo no existe o su nombre esta mal escrito ");
            
           }
    	

    }
    
    public void deleteSpecificConsole(String name) {
    	FileConfiguration config = plugin.getConfig();
		List<String> ac = config.getStringList("Maps-Created.List");


		if(!ac.isEmpty()) {
		
			if(ac.contains(name)) {
				config.set("Maps-Created.List",ac);
				ac.remove(name);
				plugin.getConfig().save();
				plugin.getConfig().reload();
			    File folder = this.plugin.getDataFolder();
		        File file = new File(folder, name);
		        String[] nombre_archivos = folder.list();
		       
		        	if(file.exists()) {
		        		 file.delete();
		       
		        	}
		        	else if(!file.exists()) {
		        	     for(int i = 0; i < nombre_archivos.length;i++) {
			              	 File file2 = new File(folder.getAbsolutePath(), nombre_archivos[i]);
			              	 //si hay una carpeta
			              	 if(file2.isDirectory()) {
			              		 //si hay enlistas las cosas que tiene dentro
			              		 String[] sub_archivos = file2.list();
			              		
			              		 for(int j = 0 ;j<sub_archivos.length;j++) {
			              			
			              			File file3 = new File(file2.getAbsolutePath(), sub_archivos[j]);
			              		
			              				
			              				if(file3.getName().equals(name+".yml")) {
			              					file3.getAbsoluteFile().delete();
			              				//da la ruta igual que el absolute path	file3.getAbsoluteFile();
			              					 Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Se a Eliminado el Mapa: "+name);
			              					
			              					break;
			              				}
			              				
			              				
			              				
			              				   
			              			
			              		
			              		 }
			              	  
			              	 }
			              }
		        	}
		        	
			}
		
			
		}else{
	           
	           Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" El archivo no existe o su nombre esta mal escrito ");
	            
	      }
    }
	
	
	
	
	
	
	
	
	
	
}
