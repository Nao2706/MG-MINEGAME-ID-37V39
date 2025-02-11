package me.nao.cooldown.mg;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.enums.GameReportType;
import me.nao.general.info.GameReports;
import me.nao.main.mg.Minegame;






public class ReportsManager {


	//cuando se penalice se coloca el cooldown con valor 1225 etc ejemplo
	//para chequear se estable un objeto con valor 0 por que solo testeara si tiene cooldown
	
	private Minegame plugin;
               // <====================================================
	
	
	public ReportsManager(Minegame plugin) {
		this.plugin = plugin;
		
	}
	
	
	
	
	public String getCooldown(long cooldown ,String player) {
		
		FileConfiguration report = plugin.getReportsYaml();

		//String pathtime = "Players."+player.getUniqueId()+".Cooldown-Recompensa";  
		String pathtime = "Players."+player+".Server-Time";  
		
		
		if(report.contains(pathtime)){
		    String timecooldownString = report.getString(pathtime);
		    long timecooldown = Long.valueOf(timecooldownString);
		    long millis = System.currentTimeMillis();
		      //long cooldown = 100; //En Segundos es el tiempo de espera debes realizar un calculo no olvides   
		        long cooldownmil = cooldown*1000;
		        
		        long espera = millis - timecooldown;
		        long esperaDiv = espera/1000;
		        long esperatotalseg = cooldown - esperaDiv;
		        long esperatotalmin = esperatotalseg/60;
		        long esperatotalhour = esperatotalmin/60;
		        long esperatotaldays = esperatotalhour/24;
		        
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
		           
		           if(esperatotalhour > 24){
		        	   esperatotalhour = esperatotalhour - 24*esperatotaldays;
		           }
		           
		           if(esperatotalhour > 0){
		               time = esperatotalhour+ "h"+" " + time;
		               
		           } 
		           
		           if(esperatotaldays > 0){
		               time = esperatotaldays+ "day/s"+" " + time;
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
	
	
	public  String ShowInMomentCooldown(long cooldown ,String milis) {
		//Mostrara en el momento el tiempo que sera sancionado 
		   String timecooldownString = milis;
		    long timecooldown = Long.valueOf(timecooldownString);
		    long millis = System.currentTimeMillis();
		      //long cooldown = 100; //En Segundos es el tiempo de espera debes realizar un calculo no olvides   
		        long cooldownmil = cooldown*1000;
		        
		        long espera = millis - timecooldown;
		        long esperaDiv = espera/1000;
		        long esperatotalseg = cooldown - esperaDiv;
		        long esperatotalmin = esperatotalseg/60;
		        long esperatotalhour = esperatotalmin/60;
		        long esperatotaldays = esperatotalhour/24;
		        
		        if(((timecooldown + cooldownmil) > millis) && (timecooldown != 0)){                 
		           if(esperatotalseg > 59){
		               esperatotalseg = esperatotalseg - 60*esperatotalmin  ;
		           }
		           String time = "";
		           //antes tenia el !=
		           
		           // denttro de los condicionales el formato es 1d 2h 3m 3s pero algunos pueden desaparecer cuando esten en 0 si se desea cambiar solamente hay que colocar el ==
		           if(esperatotalseg != 0){
		              time = esperatotalseg+"s";
		           }
		           if(esperatotalmin > 59){
		               esperatotalmin = esperatotalmin - 60*esperatotalhour;
		           }    
		           if(esperatotalmin > 0){
		               time = esperatotalmin+"min"+" "+time;
		           } 
		           
		           if(esperatotalhour > 24){
		        	   esperatotalhour = esperatotalhour - 24*esperatotaldays;
		           }
		           
		           if(esperatotalhour > 0){
		               time = esperatotalhour+ "h"+" " + time;
		               
		           } 
		           
		           if(esperatotaldays > 0){
		               time = esperatotaldays+ "day/s"+" " + time;
		           }
		           
		           return time;
		           //Aun no se termina el cooldown
		           //player.sendMessage("Puedes reclamar otra recompensa diaria dentro de "+time);
		        }else{
		         return "-1";
		        }
	}
	
	
	
	public boolean HasSancionPlayer(Player player) {
		FileConfiguration report = plugin.getReportsYaml();
		
		if(!report.contains("Players."+player.getName())) {
			System.out.println("LOG SI TIENE SANCION: Limpio :)");
			return false;
		}
		
		
		GameReportType statusconfig = GameReportType.valueOf(report.getString("Players."+player.getName()+".Status").toUpperCase());
		if(statusconfig == GameReportType.BAN) {
			player.sendMessage(ChatColor.RED+"Estas Baneado Permanentemente de los Juegos de Aventura");
			return true;
		}else if(statusconfig == GameReportType.TEMPBAN) {
			int time = report.getInt("Players."+player.getName()+".TempBanTime");
			
			String cooldown = getCooldown(time,player.getName());
			if(cooldown.equals("-1")) {
				report.set("Players."+player+".Status",GameReportType.NINGUNO.toString());
				plugin.getReportsYaml().save();
				plugin.getReportsYaml().reload();
				sendMessageGeneral(player,ChatColor.GREEN+player.getName()+" Tu sancion se Completo ya puedes Jugar.");
				return false;
			}else {
				player.sendMessage(ChatColor.RED+"Estas Baneado Temporalmente de los Juegos");
				player.sendMessage(ChatColor.YELLOW+"Tiempo Remanente: "+ChatColor.RED+cooldown);
				return true;
		}}
		
		
		return false;
	}
	
	
	public void HasSancionPlayerConsoleOrOp(Player player,String target) {
		FileConfiguration report = plugin.getReportsYaml();
		
		if(!report.contains("Players."+target)) {
			System.out.println("Limpio2  :)");
			return;
		}
		
		GameReportType statusconfig = GameReportType.valueOf(report.getString("Players."+target+".Status").toUpperCase());
		if(statusconfig == GameReportType.BAN) {
			sendMessageGeneral(player,ChatColor.RED+target+" Esta Baneado Permanentemente de los Juegos");
			return ;
		}else if(statusconfig == GameReportType.TEMPBAN) {
			int time = report.getInt("Players."+target+".TempBanTime");
			
			String cooldown = getCooldown(time,target);
			if(cooldown.equals("-1")) {
				report.set("Players."+target+".Status",GameReportType.NINGUNO.toString());
				plugin.getReportsYaml().save();
				plugin.getReportsYaml().reload();
				sendMessageGeneral(player,ChatColor.GREEN+target+" No tiene ninguna sancion Activa.");
				return ;
			}else {
				sendMessageGeneral(player,ChatColor.RED+target+" Esta Baneado Temporalmente de los Juegos");
				sendMessageGeneral(player,ChatColor.YELLOW+"Tiempo Remanente: "+ChatColor.RED+cooldown);
				return ;
		}}else if(statusconfig == GameReportType.NINGUNO) {
			sendMessageGeneral(player,ChatColor.GREEN+target+" No tiene ninguna sancion.");
		}
		
		
		return ;
	}
	
	
	
	public void SetSancionPlayer(Player player,GameReports r , int seconds) {
		FileConfiguration report = plugin.getReportsYaml();
		GameReportType gr = r.getReportype();
		String target = r.getTarget();
		
		if(report.contains("Players."+target)) {
			GameReportType statusconfig = GameReportType.valueOf(report.getString("Players."+target+".Status").toUpperCase());
			if(gr == GameReportType.BAN) {
				
				if(gr == statusconfig) {
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" ya fue Baneado Permanentemente de los Juegos.");

				}else {
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Baneo Permanentemente de los Juegos.");
					sendToTarget(target,""+ChatColor.DARK_RED+ChatColor.BOLD+"Recibiste un Baneo Permanente en los Juegos.");
					sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
					sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
					SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

				}
				
				return;
			}else if(gr == GameReportType.TEMPBAN) {
				if(statusconfig == GameReportType.TEMPBAN) {
						int time = report.getInt("Players."+target+".TempBanTime");
						String coold = getCooldown(time,target);
						if(coold.equals("-1")) {
							report.set("Players."+player+".Status",GameReportType.NINGUNO.toString());
							plugin.getReportsYaml().save();
							plugin.getReportsYaml().reload();
							
						}else {
							sendMessageGeneral(player,ChatColor.RED+"El Jugador "+ChatColor.YELLOW+target+ChatColor.RED+" tiene una sancion en progreso.");
							sendMessageGeneral(player,ChatColor.AQUA+"El Jugador "+ChatColor.GREEN+target+ChatColor.AQUA+" ya tiene un TempBan de "+ChatColor.RED+coold);
							//setear que no tiene
						}
				}else {
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue sancionado por "+ChatColor.GREEN+r.getTimeReport());
					sendToTarget(target,""+ChatColor.RED+ChatColor.BOLD+"Recibiste un Baneo Temporal en los Juegos.");
					sendToTarget(target,""+ChatColor.GREEN+ChatColor.BOLD+"Tiempo : "+ChatColor.RED+ChatColor.BOLD+ShowInMomentCooldown(seconds, String.valueOf(System.currentTimeMillis())));
					sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
					sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
					SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
				}
				return;
			}else if(gr == GameReportType.PARDON) {
				if(statusconfig == GameReportType.NINGUNO && gr == GameReportType.PARDON) {
					
					sendMessageGeneral(player,ChatColor.GREEN+"El Jugador "+ChatColor.YELLOW+target+ChatColor.GREEN+" no tiene Ninguna Sancion.");
					
				}else {
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue perdonado.");
					SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
				}
				return;
			}else if(gr == GameReportType.WARN) {
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Advertido en los Juegos.");
				sendToTarget(target,"Recibiste un Advertencia en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
			}else if(gr == GameReportType.KICK) {
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Kickeado de los Juegos.");
				sendToTarget(target,"Recibiste un Kickeo en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}
			
			
			return;
			
		}else {
			if(gr == GameReportType.WARN) {
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Advertido en los Juegos de Aventura.");
				sendToTarget(target,"Recibiste un Advertencia en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
			}else if(gr == GameReportType.KICK) {
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Kickeado de los Juegos de Aventura.");
				sendToTarget(target,"Recibiste un Kickeo en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}else if(gr == GameReportType.BAN) {
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Baneo Permanentemente de los Juegos de Aventura.");
				sendToTarget(target,""+ChatColor.DARK_RED+ChatColor.BOLD+"Recibiste un Baneo Permanente en los Juegos.");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}else if(gr == GameReportType.TEMPBAN) {
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue sancionado por "+ChatColor.GREEN+r.getTimeReport());
				sendToTarget(target,""+ChatColor.RED+ChatColor.BOLD+"Recibiste un Baneo Temporal en los Juegos.");
				sendToTarget(target,""+ChatColor.GREEN+ChatColor.BOLD+"Tiempo : "+ChatColor.RED+ChatColor.BOLD+ShowInMomentCooldown(seconds, String.valueOf(System.currentTimeMillis())));
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}else if(gr == GameReportType.PARDON) {
				sendMessageGeneral(player,ChatColor.YELLOW+"No puedes usar Pardon con "+ChatColor.GREEN+target+ChatColor.YELLOW+" porque no esta sancionado.");

			}
			return;
		}
		
	}
	
	public void SetTimeCooldownMg(int time ,GameReportType type,String player,String text) {
		
		FileConfiguration report = plugin.getReportsYaml();
		
		if(type == GameReportType.TEMPBAN) {
			long millis = System.currentTimeMillis();  
			report.set("Players."+player+".Status",type.toString());
			report.set("Players."+player+".TempBanTime",time);
			report.set("Players."+player+".Server-Time", millis);
			List<String> list = report.getStringList("Players."+player+".Reports");
			report.set("Players."+player+".Reports", list);
			list.add(text);
		}else if(type == GameReportType.BAN) {
			long millis = System.currentTimeMillis();  
			report.set("Players."+player+".Status",type.toString());
			report.set("Players."+player+".TempBanTime",time);
			report.set("Players."+player+".Server-Time", millis);
			List<String> list = report.getStringList("Players."+player+".Reports");
			report.set("Players."+player+".Reports", list);
			list.add(text);
		}else if(type == GameReportType.PARDON) {
			long millis = 0;  
			int t = 0;
			report.set("Players."+player+".Status","NINGUNO");
			report.set("Players."+player+".TempBanTime",t);
			report.set("Players."+player+".Server-Time", millis);
			List<String> list = report.getStringList("Players."+player+".Reports");
			report.set("Players."+player+".Reports", list);
			list.add(text);
		}else {
			long millis = 0;
			int t = 0;
			report.set("Players."+player+".Status","NINGUNO");
			report.set("Players."+player+".TempBanTime",t);
			report.set("Players."+player+".Server-Time",millis);
			List<String> list = report.getStringList("Players."+player+".Reports");
			report.set("Players."+player+".Reports", list);
			list.add(text);
		}
		
		
		plugin.getReportsYaml().save();
		plugin.getReportsYaml().reload();
		
	}
	
	public void sendMessageGeneral(Player player, String text) {
		
		if(player != null) {
			player.sendMessage(text);
		}
		Bukkit.getConsoleSender().sendMessage(text);
	}
	
	
	public void sendToTarget(String player, String text) {
		Player target = Bukkit.getServer().getPlayerExact(player);
		
			if(target != null) {
				target.sendMessage(text);
			}
		Bukkit.getConsoleSender().sendMessage(text);
	}
	
	
	public void sendReportLogs(Player player, String target,int pag) {
		
		FileConfiguration report = plugin.getReportsYaml();
		if(!report.contains("Players."+target)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun reporte.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun reporte.");
			
			return;
		}
		//Jugador=NAO2706-Sancion=WARN-Fecha=19/06/2024 22:09:37 PM-Tiempo=SIN TIEMPO-Moderador=CONSOLA-Razon=(HACKS)
    
		List<String> list = report.getStringList("Players."+target+".Reports");
		
		pagssendtoPlayer(player,list,pag,5);
		
	}
	
	 public void pagssendtoPlayer(Player player,List<String> l , int pag,int datsperpags) {
	    	
	    	if(!l.isEmpty()) {
	    		int inicio = (pag -1) * datsperpags;
	    		int fin = inicio + datsperpags;
	    		
	    		int tamañolista = l.size();
	    		int numerodepags = (int) Math.ceil((double) tamañolista /datsperpags);
	    		
	    		if(pag > numerodepags) {
	    			if(player != null) {
		    			player.sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+" Paginas en Total: "+((l.size()+datsperpags-1)/datsperpags));

	    			}
	    			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+" Paginas en Total: "+((l.size()+datsperpags-1)/datsperpags));
	    			return;
	    		}
	    		player.sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datsperpags-1)/datsperpags));
	    		for(int i = inicio;i < fin && i < l.size();i++) {
	    			if(player != null) {
	    				player.sendMessage(""+ChatColor.RED+(i+1)+").  "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
		    					.replaceAll("Jugador",ChatColor.GREEN+"Jugador").replaceAll("Sancion",ChatColor.GOLD+"Sancion")
		    					.replaceAll("Fecha",ChatColor.GREEN+"Fecha").replaceAll("Tiempo",ChatColor.GREEN+"Tiempo")
		    					.replaceAll("Moderador",ChatColor.GREEN+"Moderador").replaceAll("Razon",ChatColor.GREEN+"Razon"));
	    			}
	    			
	    			
	    			Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+(i+1)+").  "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
	    					.replaceAll("Jugador",ChatColor.GREEN+"Jugador").replaceAll("Sancion",ChatColor.GOLD+"Sancion")
	    					.replaceAll("Fecha",ChatColor.GREEN+"Fecha").replaceAll("Tiempo",ChatColor.GREEN+"Tiempo")
	    					.replaceAll("Moderador",ChatColor.GREEN+"Moderador").replaceAll("Razon",ChatColor.GREEN+"Razon"));
	    					
	    		}
	    		
	    	}else {
	    		if(player != null) {
		    		player.sendMessage(ChatColor.RED+"No hay datos para mostrar.");

	    		}
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay datos para mostrar.");
	    	}
	    	return;
	    }
	
	


	

	
}
