package me.nao.landmine.yamlfile.lm;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;



import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
 
public class YamlFile extends YamlConfiguration {
 
    private final String fileName;
 
    private final Plugin plugin;
 
    private File file;
 
    private File folder;
 
    public YamlFile(Plugin plugin, String fileName, File folder) {
        this.folder = folder;
        this.plugin = plugin;
        this.fileName = fileName + (fileName.endsWith(".yml") ? "" : ".yml");
        createFile();
    }
 
    public YamlFile(Plugin plugin, String fileName) {
 
        this(plugin, fileName, plugin.getDataFolder());
    }
    
    
 
    private void createFile() {
        try {
            this.file = new File(this.folder, this.fileName);
            if (file.exists()) {
                load(this.file);
                save(this.file);
                return;
            }
            if (this.plugin.getResource(this.fileName) != null) {
                this.plugin.saveResource(this.fileName, false);
            } else {
                save(this.file);
            }
            load(this.file);
        } catch (InvalidConfigurationException | IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Creation of Configuration '" + this.fileName + "' failed.", e);
        }
    }
 
    public void delete() {
        if (this.file.delete()) {
        } else {
            this.plugin.getLogger().log(Level.SEVERE, "Error on delete the file '" + this.fileName + "'.");
        }
    }
    

 
    public void save() {
    	
    	//Bukkit.getConsoleSender().sendMessage(this.fileName+" tesssst");
    	 File folder = this.plugin.getDataFolder();
        if (folder == null) {
            folder = this.plugin.getDataFolder();
        }
        File file = new File(folder, this.fileName);
        
        /*
        try {
            save(file);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.", e);
        }
        */
        
       

        String[] nombre_archivos = folder.list();
        try {
        	if(file.exists()) {
        		 save(file);
        		// Bukkit.getConsoleSender().sendMessage(file.getName()+"ggif1");
        	}
        	else{
        		//Bukkit.getConsoleSender().sendMessage(file.getName()+"gg5else");
            for(int i =0; i < nombre_archivos.length;i++) {
              	 File file2 = new File(folder.getAbsolutePath(), nombre_archivos[i]);
              	 if(file2.isDirectory()) {
              		 String[] sub_archivos = file2.list();
              		 for(int j = 0 ;j<sub_archivos.length;j++) {
              			File file3 = new File(file2.getAbsolutePath(), sub_archivos[j]);
              			if(file3.exists()) {
              			//	Bukkit.getConsoleSender().sendMessage(file.getName()+"gg9");
              				if(this.fileName.equals(file3.getName())) {
              					save(file3.getAbsoluteFile());
              				//Bukkit.getConsoleSender().sendMessage("gg1");
              				}
              			}
              		
              		 }
              	  
              	 }
              }
            }
        } catch (IOException e ) {
            this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.", e);
        }
        
        
        
        
    }

    
    public void save2(String folder2 ,String name) {
    	 
    	
    	  File file = new File(new File(plugin.getDataFolder().getAbsolutePath() + "/" + folder2 + "/"), name+".yml");
          String[] nombre_archivos = folder.list();      
        try {
        	if(file.exists()) {
       		 save(file);
       	}
       	else{
          
           for(int i =0; i < nombre_archivos.length;i++) {
             	 File file2 = new File(folder.getAbsolutePath(), nombre_archivos[i]);
             	 if(file2.isDirectory()) {
             		 String[] sub_archivos = file2.list();
             		 for(int j = 0 ;j<sub_archivos.length;j++) {
             			File file3 = new File(file2.getAbsolutePath(), sub_archivos[j]);
             			if(file3.exists()) {
             				save(file3.getAbsoluteFile());
             				
             			}
             		
             		 }
             	  
             	 }
             }
           }        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.", e);
        }
    }
 
    public void reload() {
        File folder = this.plugin.getDataFolder();
        File file = new File(folder, this.fileName);
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
              				if(this.fileName.equals(file3.getName())) {
              					load(file3.getAbsoluteFile());
              					//Bukkit.getConsoleSender().sendMessage("gg2 reload");
              				}
              			}
              		
              		 }
              	  
              	 }
              }
            }
        } catch (IOException | InvalidConfigurationException e ) {
            this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.", e);
        }
        
        
    }
    
    
    
    
    

}
 