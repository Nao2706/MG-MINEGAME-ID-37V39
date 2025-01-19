package me.nao.cooldown.mg;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.main.mg.Minegame;





public class Cooldown {


	private Minegame plugin;
	long cooldown;                  // <====================================================
	
	
	public Cooldown(Minegame plugin,long cooldown) {
		this.plugin = plugin;
		this.cooldown = cooldown;
	}
	
	
	public String getCooldown(Player player) {
		
		FileConfiguration Cd = plugin.getCooldown();
		String pathtime = "Players."+player.getUniqueId()+".Cooldown-Recompensa";  
		
		if(Cd.contains(pathtime)){
		    String timecooldownString = Cd.getString(pathtime);
		    long timecooldown = Long.valueOf(timecooldownString);
		    long millis = System.currentTimeMillis();
		      //long cooldown = 100; //En Segundos es el tiempo de espera debes realizar un calculo no olvides   
		        long cooldownmil = cooldown*1000;
		        
		        long espera = millis - timecooldown;
		        long esperaDiv = espera/1000;
		        long esperatotalseg = cooldown - esperaDiv;
		        long esperatotalmin = esperatotalseg/60;
		        long esperatotalhour = esperatotalmin/60;
		        if(((timecooldown + cooldownmil) > millis) && (timecooldown != 0)){                 
		           if(esperatotalseg > 59){
		               esperatotalseg = esperatotalseg - 60*esperatotalmin;
		           }
		           String time = "";
		           if(esperatotalseg != 0){
		              time = esperatotalseg+"s";
		           }
		           if(esperatotalmin > 59){
		               esperatotalmin = esperatotalmin - 60*esperatotalhour;
		           }    
		           if(esperatotalmin > 0){
		               time = esperatotalmin+"min"+" "+time;
		           }
		           
		           if(esperatotalhour > 0){
		               time = esperatotalhour+ "h"+" " + time;
		           }
		           
		           return time;
		           //Aun no se termina el cooldown
		           //player.sendMessage("Puedes reclamar otra recompensa diaria dentro de "+time);
		        }else{
		         return "-1";
		        }
		}else{
		    //Usa el comando por primera vez, ya que no existe el path en la config
			
		  return "-1";
		}
		
		
		
		
		
	}
	
	
	
	
}
