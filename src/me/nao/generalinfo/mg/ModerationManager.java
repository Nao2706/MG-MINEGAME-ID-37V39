package me.nao.generalinfo.mg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.enums.mg.GameModerationActionType;
import me.nao.main.mg.Minegame;





@SuppressWarnings("deprecation")
public class ModerationManager {


	//cuando se penalice se coloca el cooldown con valor 1225 etc ejemplo
	//para chequear se estable un objeto con valor 0 por que solo testeara si tiene cooldown
	
	private Minegame plugin;
               // <====================================================
	
	
	public ModerationManager(Minegame plugin) {
		this.plugin = plugin;
		
	}
	
	
	
	
	public String getCooldown(long cooldown ,String player) {
		
		FileConfiguration data = plugin.getPlayersHistoryYaml();

		//String pathtime = "Players."+player.getUniqueId()+".Cooldown-Recompensa";  
		String pathtime = "Players."+player+".Server-Time";  
		
		
		if(data.contains(pathtime)){
		    String timecooldownString = data.getString(pathtime);
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
		FileConfiguration report = plugin.getPlayersHistoryYaml();
		GameConditions gc = new GameConditions(plugin);
		
		if(!report.contains("Players."+player.getName())) {
			//System.out.println("LOG SI TIENE SANCION: Limpio :)");
			return false;
		}
		
		
		GameModerationActionType statusconfig = GameModerationActionType.valueOf(report.getString("Players."+player.getName()+".Status").toUpperCase());
		if(statusconfig == GameModerationActionType.BAN) {
			player.sendMessage(ChatColor.RED+"Estas Baneado Permanentemente de los MiniJuegos");
			gc.sendMessageToConsole(ChatColor.GOLD+player.getName()+ChatColor.RED+" Esta Baneado Permanentemente de los MiniJuegos.");
			return true;
		}else if(statusconfig == GameModerationActionType.TEMPBAN) {
			int time = report.getInt("Players."+player.getName()+".TempBanTime");
			
			String cooldown = getCooldown(time,player.getName());
			if(cooldown.equals("-1")) {
				report.set("Players."+player+".Status",GameModerationActionType.NINGUNO.toString());
				plugin.getPlayersHistoryYaml().save();
				plugin.getPlayersHistoryYaml().reload();
				sendMessageGeneral(player,ChatColor.GREEN+player.getName()+" Tu sancion se Completo ya puedes Jugar.");
				return false;
			}else {
				player.sendMessage(ChatColor.RED+"Estas Baneado Temporalmente de los MiniJuegos");
				player.sendMessage(ChatColor.YELLOW+"Tiempo Remanente: "+ChatColor.RED+cooldown);
				return true;
		}}
		
		
		return false;
	}
	
	
	public void HasSancionPlayerConsoleOrOp(Player player,String target) {
		FileConfiguration report = plugin.getPlayersHistoryYaml();
		
		if(!report.contains("Players."+target)) {
			System.out.println("Limpio2  :)");
			return;
		}
		
		GameModerationActionType statusconfig = GameModerationActionType.valueOf(report.getString("Players."+target+".Status").toUpperCase());
		if(statusconfig == GameModerationActionType.BAN) {
			sendMessageGeneral(player,ChatColor.GOLD+target+ChatColor.RED+" Esta Baneado Permanentemente de los MiniJuegos");
			return ;
		}else if(statusconfig == GameModerationActionType.TEMPBAN) {
			int time = report.getInt("Players."+target+".TempBanTime");
			
			String cooldown = getCooldown(time,target);
			if(cooldown.equals("-1")) {
				report.set("Players."+target+".Status",GameModerationActionType.NINGUNO.toString());
				plugin.getPlayersHistoryYaml().save();
				plugin.getPlayersHistoryYaml().reload();
				sendMessageGeneral(player,ChatColor.GREEN+target+" No tiene ninguna sancion Activa.");
				return ;
			}else {
				sendMessageGeneral(player,ChatColor.GOLD+target+ChatColor.RED+" Esta Baneado Temporalmente de los Juegos");
				sendMessageGeneral(player,ChatColor.YELLOW+"Tiempo Remanente: "+ChatColor.RED+cooldown);
				return ;
		    }
		}else if(statusconfig == GameModerationActionType.NINGUNO) {
			sendMessageGeneral(player,ChatColor.GREEN+target+" No tiene ninguna sancion.");
		}
		
		
		return ;
	}
	
	
	
	public void SetSancionPlayer(Player player,GameModeration r , int seconds) {
		FileConfiguration report = plugin.getPlayersHistoryYaml();
		GameModerationActionType gr = r.getReportype();
		String target = r.getTarget();
		
		if(report.contains("Players."+target)) {
			GameModerationActionType statusconfig = GameModerationActionType.valueOf(report.getString("Players."+target+".Status").toUpperCase());
			if(gr == GameModerationActionType.BAN) {
				
				if(gr == statusconfig) {
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" ya fue Baneado Permanentemente de los MiniJuegos.");

				}else {
					sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Baneo Permanentemente de los MiniJuegos.");
					sendToTarget(target,"");
					sendToTarget(target,""+ChatColor.DARK_RED+ChatColor.BOLD+"Recibiste un Baneo Permanente en los MiniJuegos.");
					sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
					sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
					sendToTarget(target,"");
					SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

				}
				
				return;
			}else if(gr == GameModerationActionType.TEMPBAN) {
				if(statusconfig == GameModerationActionType.TEMPBAN) {
						int time = report.getInt("Players."+target+".TempBanTime");
						String coold = getCooldown(time,target);
						if(coold.equals("-1")) {
							report.set("Players."+player+".Status",GameModerationActionType.NINGUNO.toString());
							plugin.getPlayersHistoryYaml().save();
							plugin.getPlayersHistoryYaml().reload();
							
						}else {
							sendMessageGeneral(player,ChatColor.RED+"El Jugador "+ChatColor.YELLOW+target+ChatColor.RED+" tiene una sancion en progreso.");
							sendMessageGeneral(player,ChatColor.AQUA+"El Jugador "+ChatColor.GREEN+target+ChatColor.AQUA+" ya tiene un TempBan de "+ChatColor.RED+coold);
							//setear que no tiene
						}
				}else {
					sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue sancionado por "+ChatColor.GREEN+r.getTimeReport());
					sendToTarget(target,"");
					sendToTarget(target,""+ChatColor.RED+ChatColor.BOLD+"Recibiste un Baneo Temporal en los Juegos.");
					sendToTarget(target,""+ChatColor.GREEN+ChatColor.BOLD+"Tiempo : "+ChatColor.RED+ChatColor.BOLD+ShowInMomentCooldown(seconds, String.valueOf(System.currentTimeMillis())));
					sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
					sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
					sendToTarget(target,"");
					SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
				}
				return;
			}else if(gr == GameModerationActionType.PARDON) {
				if(statusconfig == GameModerationActionType.NINGUNO && gr == GameModerationActionType.PARDON) {
					
					sendMessageGeneral(player,ChatColor.GREEN+"El Jugador "+ChatColor.YELLOW+target+ChatColor.GREEN+" no tiene Ninguna Sancion.");
					
				}else {
					sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue perdonado.");
					SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
				}
				return;
			}else if(gr == GameModerationActionType.WARN) {
				sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Advertido en los Juegos.");
				sendToTarget(target,"");
				sendToTarget(target,"Recibiste un Advertencia en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				sendToTarget(target,"");
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
			}else if(gr == GameModerationActionType.KICK) {
				sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Kickeado de los Juegos.");
				sendToTarget(target,"");
				sendToTarget(target,"Recibiste un Kickeo en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				sendToTarget(target,"");
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}
			
			
			return;
			
		}else {
			if(gr == GameModerationActionType.WARN) {
				sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Advertido en los MiniJuegos.");
				sendToTarget(target,"");
				sendToTarget(target,"Recibiste un Advertencia en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				sendToTarget(target,"");
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());
			}else if(gr == GameModerationActionType.KICK) {
				sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Kickeado de los MiniJuegos.");
				sendToTarget(target,"");
				sendToTarget(target,"Recibiste un Kickeo en los Juegos. ");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				sendToTarget(target,"");
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}else if(gr == GameModerationActionType.BAN) {
				sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue Baneo Permanentemente de los MiniJuegos.");
				sendToTarget(target,"");
				sendToTarget(target,""+ChatColor.DARK_RED+ChatColor.BOLD+"Recibiste un Baneo Permanente en los Juegos.");
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				sendToTarget(target,"");
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}else if(gr == GameModerationActionType.TEMPBAN) {
				sendPlaySoundToTarget(target, Sound.BLOCK_NOTE_BLOCK_PLING);
				sendMessageGeneral(player,ChatColor.YELLOW+"El Jugador "+ChatColor.GREEN+target+ChatColor.YELLOW+" fue sancionado por "+ChatColor.GREEN+r.getTimeReport());
				sendToTarget(target,"");
				sendToTarget(target,""+ChatColor.RED+ChatColor.BOLD+"Recibiste un Baneo Temporal en los MiniJuegos.");
				sendToTarget(target,""+ChatColor.GREEN+ChatColor.BOLD+"Tiempo : "+ChatColor.RED+ChatColor.BOLD+ShowInMomentCooldown(seconds, String.valueOf(System.currentTimeMillis())));
				sendToTarget(target,"Razon: "+ChatColor.GREEN+r.getCausa());
				sendToTarget(target,ChatColor.GREEN+"Moderador: "+ChatColor.AQUA+r.getModerador());
				sendToTarget(target,"");
				SetTimeCooldownMg(seconds,r.getReportype(),r.getTarget(),r.DataReport());

			}else if(gr == GameModerationActionType.PARDON) {
				
				sendMessageGeneral(player,ChatColor.YELLOW+"No puedes usar Pardon con "+ChatColor.GREEN+target+ChatColor.YELLOW+" porque no esta sancionado.");

			}
			return;
		}
		
	}
	
	public void SetTimeCooldownMg(int time ,GameModerationActionType type,String player,String text) {
		
		FileConfiguration report = plugin.getPlayersHistoryYaml();
		
		if(type == GameModerationActionType.TEMPBAN) {
			long millis = System.currentTimeMillis();  
			report.set("Players."+player+".Status",type.toString());
			report.set("Players."+player+".TempBanTime",time);
			report.set("Players."+player+".Server-Time", millis);
			List<String> list = report.getStringList("Players."+player+".History");
			report.set("Players."+player+".History", list);
			list.add(text);
		}else if(type == GameModerationActionType.BAN) {
			long millis = System.currentTimeMillis();  
			report.set("Players."+player+".Status",type.toString());
			report.set("Players."+player+".TempBanTime",time);
			report.set("Players."+player+".Server-Time", millis);
			List<String> list = report.getStringList("Players."+player+".History");
			report.set("Players."+player+".History", list);
			list.add(text);
		}else if(type == GameModerationActionType.PARDON) {
			long millis = 0;  
			int t = 0;
			report.set("Players."+player+".Status","NINGUNO");
			report.set("Players."+player+".TempBanTime",t);
			report.set("Players."+player+".Server-Time", millis);
			List<String> list = report.getStringList("Players."+player+".History");
			report.set("Players."+player+".History", list);
			list.add(text);
		}else {
			long millis = 0;
			int t = 0;
			report.set("Players."+player+".Status","NINGUNO");
			report.set("Players."+player+".TempBanTime",t);
			report.set("Players."+player+".Server-Time",millis);
			List<String> list = report.getStringList("Players."+player+".History");
			report.set("Players."+player+".History", list);
			list.add(text);
		}
		
		
		plugin.getPlayersHistoryYaml().save();
		plugin.getPlayersHistoryYaml().reload();
		
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
	
	//Sound.BLOCK_NOTE_BLOCK_PLING
	public void sendPlaySoundToTarget(String player, Sound sound) {
		Player target = Bukkit.getServer().getPlayerExact(player);
		
			if(target != null) {
				
				target.playSound(target.getLocation(), sound , 50.0F, 1F);
			}
	
	}
	
	
	public void sendModerationLogs(Player player, String target,int pag) {
		
		FileConfiguration report = plugin.getPlayersHistoryYaml();
		if(!report.contains("Players."+target)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun Dato.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun Dato.");
			
			return;
		}
		//Jugador=NAO2706-Sancion=WARN-Fecha=19/06/2024 22:09:37 PM-Tiempo=SIN TIEMPO-Moderador=CONSOLA-Razon=(HACKS)
    
		List<String> list = report.getStringList("Players."+target+".History");
		
		pagssendtoPlayer(player,list,pag,5,target);
		
	}
 /// PRIMERO PEDIMOS UNA LISTA , LUEGO LA PAGINA A VISUALIZAR , POR ULTIMO CUANTOS DATOS CONFORMAN UNA PAGINA
	 public void pagssendtoPlayer(Player player,List<String> l , int pag,int datosperpags,String target) {
	    	
	    	if(!l.isEmpty()) {
	    		int inicio = (pag -1) * datosperpags;
	    		int fin = inicio + datosperpags;
	    		
	    		int sizelista = l.size();
	    		int numerodepags = (int) Math.ceil((double) sizelista /datosperpags);
	    		
	    		if(pag > numerodepags) {
	    			if(player != null) {
		    			player.sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+ChatColor.GREEN+" Paginas en Total: "+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));

	    			}
	    			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay mas datos para mostrar en la pag: "+ChatColor.GOLD+pag+ChatColor.GREEN+" Paginas en Total: "+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    			return;
	    		}
	    		if(player != null) {
	    			player.sendMessage(ChatColor.GOLD+"Historial de: "+ChatColor.GREEN+target);
		    		player.sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    		}
	    		
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Historial de: "+ChatColor.GREEN+target);
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    	
	    		for(int i = inicio;i < fin && i < l.size();i++) {
	    			if(player != null) {
	    				player.sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
	    						.replaceAll("Sancion:",ChatColor.GOLD+"Sancion:"+ChatColor.GREEN)
		    					.replaceAll("Fecha:",ChatColor.GOLD+"Fecha:"+ChatColor.GREEN).replaceAll("Tiempo:",ChatColor.GOLD+"Tiempo:"+ChatColor.GREEN)
		    					.replaceAll("Mod:",ChatColor.GOLD+"Mod:"+ChatColor.GREEN).replaceAll("Razon:",ChatColor.GOLD+"Razon:"+ChatColor.GREEN));
	    			}
	    			
	    			
	    			Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
	    					.replaceAll("Sancion:",ChatColor.GOLD+"Sancion:"+ChatColor.GREEN)
	    					.replaceAll("Fecha:",ChatColor.GOLD+"Fecha:"+ChatColor.GREEN).replaceAll("Tiempo:",ChatColor.GOLD+"Tiempo:"+ChatColor.GREEN)
	    					.replaceAll("Mod:",ChatColor.GOLD+"Mod:"+ChatColor.GREEN).replaceAll("Razon:",ChatColor.GOLD+"Razon:"+ChatColor.GREEN));
	    					
	    		}
	    		
	    	}else {
	    		if(player != null) {
		    		player.sendMessage(ChatColor.RED+"No hay datos para mostrar.");

	    		}
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay datos para mostrar.");
	    	}
	    	return;
	    }
	
	

	 public boolean isATimeMgFormat(String val) {
			
	 		Pattern p = Pattern.compile("([0-9])");
			Matcher m = p.matcher(val);
			
			if(m.find() && val.length() == 2 || val.length() == 3) {
				if(val.endsWith("d") || val.endsWith("D") ||val.endsWith("h") || val.endsWith("H") || val.endsWith("m") || val.endsWith("M") || val.endsWith("s") || val.endsWith("S")) {
					
					return true;
				}
			}
		
			return false;
		}
		
	 	public int ReturnHourAndMinuteToSecons(String val) {
			
			int total = 0;
			
				if(val.endsWith("d") || val.endsWith("D")) {
					int tex = Integer.valueOf(val.replace("d","").replace("D",""));
					total = tex * 86400;
				}
				if(val.endsWith("h") || val.endsWith("H")) {
					int tex = Integer.valueOf(val.replace("h","").replace("H",""));
					total = tex * 3600;
				}if(val.endsWith("m") || val.endsWith("M")) {
					int tex = Integer.valueOf(val.replace("m","").replace("M",""));
					total = tex * 60;
				}if(val.endsWith("s") || val.endsWith("S")) {
					int tex = Integer.valueOf(val.replace("s","").replace("S",""));
					total = tex;
				}
			
			
			
			return total;
		}
		
	//mg ban NAO POR NOOB
		public void IdentifierModerationReports(Player player,String[] report) {
			
			try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
					// StringBuilder sb = new StringBuilder();
					 LocalDateTime ld = LocalDateTime.now();
					
					 
					 String type = report[0];
					 String target = report[1];
					 
					 if(type == null || target == null) {
							if(player != null) {
								player.sendMessage(ChatColor.YELLOW+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
							}
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .");
						 return;
					 }
					 
					 //mg pardon NAO
					 if(type.startsWith("pardon")) {
						
							 String comments = "";
				        	 for(int i = 2 ;i < report.length; i++) {
				        		 comments = comments+report[i]+" "; 
								 
							 }
				        	 
				        	 comments = comments.trim()+".";
				        	 
				        	 if(player != null) {
									GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"Sin Tiempo",player.getName(),comments.replace("-", " ").replace(",", " "));
									SetSancionPlayer(player, gr, 0);
								}else {
									 GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"Sin Tiempo","Consola",comments.replace("-", " ").replace(",", " "));
										SetSancionPlayer(player, gr, 0);
								}
						
						 
					 }else if(type.startsWith("ban")|| type.startsWith("kick") || type.startsWith("warn")) {
							
								
								//mg warn nao test
								//mg ban nao
								
								 String comments = "";
					        	 for(int i = 2 ;i < report.length; i++) {
					        		 comments = comments+report[i]+" "; 
									 
								 }
					        	
							     comments = comments.trim()+".";
								 
								//String timeg = time;
								GameConditions gc = new GameConditions(plugin);
								
								if(player != null) {
									GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"Sin Tiempo",player.getName(),comments.replace("-", " ").replace(",", " "));
									SetSancionPlayer(player, gr, 0);
								}else {
									GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),"Sin Tiempo","Consola",comments.replace("-", " ").replace(",", " "));
									SetSancionPlayer(player, gr, 0);
								}
								
								Player target1 = Bukkit.getServer().getPlayerExact(target);
								if(!type.startsWith("warn")) {
									if(target1 != null && gc.isPlayerinGame(target1)) {
										gc.mgLeaveOfTheGame(target1);

									}	
								}
								
								return;
							
					 }else if(type.startsWith("tempban")) {
						       //0    1    2 3  4   5  6
						 //mg tempban nao 1h 1h 1h com
						 	String comments = "";
			        	
								
							 	int pa = 0;
								for(String s : report) {
									if(!isATimeMgFormat(s))continue;
									pa++;
									
								}
								
							
								if(pa == 0) {
									if(player != null) {
										player.sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 1>");
										player.sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 2>");
										player.sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 3>");

										
									}
									Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 1>");
									Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 2>");
									Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s .<Formato 3>");

									
									
									return;
								}else if(pa == 1) {
									String time = report[2];
								
									 for(int i = 3 ;i < report.length; i++) {
						        		 comments = comments+report[i]+" "; 
									 }
									
									 if(comments.isEmpty()) {
										 comments = "Sin Especificar.";
									 }else {
										 comments = comments.trim()+".";
									 }
									 
									int total = (ReturnHourAndMinuteToSecons(time));
									if(player != null) {
										GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),player.getName(),comments);
										
										SetSancionPlayer(player, gr, total);
									}else {
										GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),"Consola",comments);
									
										SetSancionPlayer(player, gr, total);
									}
									return;
								}else if(pa == 2) {
									String time = report[2];
									String time2 = report[3];
									
									 for(int i = 4 ;i < report.length; i++) {
						        		 comments = comments+report[i]+" "; 
									 }
									
									 if(comments.isEmpty()) {
										 comments = "Sin Especificar.";
									 }else {
										 comments = comments.trim()+".";
									 }
									 
									int total = (ReturnHourAndMinuteToSecons(time)+ReturnHourAndMinuteToSecons(time2));
									if(player != null) {
										
										GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),player.getName(),comments);
										SetSancionPlayer(player, gr, total);
									}else{
										GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),"Consola",comments);
										SetSancionPlayer(player, gr, total);
									}
									return;
								}else if(pa == 3) {
									
									String time = report[2];
									String time2 = report[3];
									String time3 = report[4];
									
									 for(int i = 5 ;i < report.length; i++) {
						        		 comments = comments+report[i]+" "; 
									 }
									 
									 if(comments.isEmpty()) {
										 comments = "Sin especificar.";
									 }else {
										 comments = comments.trim()+".";
									 }
									 
								
									int total = (ReturnHourAndMinuteToSecons(time)+ReturnHourAndMinuteToSecons(time2)+ReturnHourAndMinuteToSecons(time3));

									if(player != null) {
										GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),player.getName(),comments);
										SetSancionPlayer(player, gr, total);
									}else{
										GameModeration gr = new GameModeration(target,GameModerationActionType.valueOf(type.toUpperCase()),ld.format(formatter).toString(),ShowInMomentCooldown(total, String.valueOf(System.currentTimeMillis())),"Consola",comments);
										SetSancionPlayer(player, gr, total);
									}
								}
								
								
						
					 }
					
					//TEMPBAN
					 // 2
				     //mg tempban NAO 1 ES 3 PERO PUEDE SER 4
			}catch(ArrayIndexOutOfBoundsException e) {
				if(player != null) {
					player.sendMessage(ChatColor.YELLOW+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s ...");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Usa /mg ban,kick,warn <player> <escribe una razon> Para especificar tiempo porfavor usa los siguientes 1D 1H 1M 1S 1d 1h 1m 1s ...");
				if(player != null) {
					player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <escribe una razon>...");
					player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <escribe una razon>...");
					player.sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <escribe una razon>...");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <escribe una razon>...");
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <escribe una razon>...");
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"Usa /mg tempban <player> <1DHMS> <1DHMS> <1DHMS> <escribe una razon>...");
			 return;
			}
		
			
		}
	

	
}
