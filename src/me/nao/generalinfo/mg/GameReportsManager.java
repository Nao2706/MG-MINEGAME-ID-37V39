package me.nao.generalinfo.mg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import me.nao.main.mg.Minegame;

@SuppressWarnings("deprecation")
public class GameReportsManager {

	
	private Minegame plugin;
	
	public GameReportsManager(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	
	public void setReporttoTarget(Player user ,String target,String motive,String map, String comment) {
		
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
		 LocalDateTime ld = LocalDateTime.now();
		
		 if(hasPlayerTempCooldownReport(user,60)) return;
		 
			if(hasReportsPlayer(target)) {
				GameReport gr = plugin.getGameReports().get(target);
				List<String> l = gr.getReports();
				l.add("Fecha: "+ld.format(formatter).toString()+" Reporta: "+user.getName()+" Motivo: "+motive+" Mapa: "+map+" Comentarios: "+comment);
				
				saveData(plugin.getGameReports().get(target));
				
			}else {
				List<String> l = new ArrayList<>();
				l.add("Fecha: "+ld.format(formatter).toString()+" Reporta: "+user.getName()+" Motivo: "+motive+" Mapa: "+map+" Comentarios: "+comment);
				
				plugin.getGameReports().put(target,new GameReport(target,ld,l));
				
				saveData(plugin.getGameReports().get(target));
			} 
		
		user.sendMessage(ChatColor.RED+"Reportaste"+ChatColor.GRAY+" a "+ChatColor.GOLD+target+ChatColor.GRAY+" por "+ChatColor.GOLD+motive+ChatColor.GOLD+" Comentarios:"+ChatColor.DARK_PURPLE+comment);
		user.sendMessage("");
		user.sendMessage(ChatColor.YELLOW+"Notificacion: "+ChatColor.GRAY+"Reportar sin razon Justificada o siendo falso es Sancionable.");
		user.playSound(user,Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
	
		
		setPlayerTempCooldowns(user);
//		GameConditions gc = new GameConditions(plugin);
//		Player t = gc.ConvertStringToPlayerAlone(target);
//		
//		if(t != null) {
//			
//		}
		
		return;
		
	}
	
	
	public void checkReportsDay(Player player,int pag) {
		
		if(plugin.getGameReports().isEmpty()) {
			
			if(player != null) {
				player.sendMessage(ChatColor.RED+"Game-Reports: "+ChatColor.YELLOW+"No hay Reportes el Dia de Hoy.");

			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Game-Reports: "+ChatColor.YELLOW+"No hay Reportes el Dia de Hoy.");
			return;
			
		}
		
		sendPagesToPlayerMap(player, plugin.getGameReports(), pag, 10);
		
	}
	
	
	public void checkReportOfPlayerDay(Player player, String target,int pag) {
		
		
		if(!hasReportsPlayer(target)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.RED+"Game-Reports: "+ChatColor.YELLOW+"Ese Jugador no tiene Ningun Reporte el dia de Hoy.");

			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Game-Reports: "+ChatColor.YELLOW+"Ese Jugador no tiene Ningun Reporte el dia de Hoy.");
			
			hasMoreReports(player,target);
			hasModLogs(player,target);
			//sendReportLogs(player,target,pag);
			return;
		}
		
		GameReport gr = plugin.getGameReports().get(target);
		
		pagssendtoPlayer(player,gr.getReports(),pag,10,target,"De Hoy");
		
		//sendReportLogs(player,target,pag);
	}
	
	
	public boolean hasReportsPlayer(String target) {
		return plugin.getGameReports().containsKey(target);
	}
	
	
	public boolean hasPlayerTempCooldownReport(Player player , int time) {
		if(plugin.getTempCooldownReport().containsKey(player)){
			long timeleft = plugin.getTempCooldownReport().get(player) / 1000 + time - System.currentTimeMillis() / 1000;
			if(timeleft > 0) {
				player.sendMessage(ChatColor.RED+"Report-Cooldown: "+ChatColor.YELLOW+"Debes esperar "+ChatColor.GREEN+timeleft+ChatColor.YELLOW+" para volver a Reportar.");
				return true;
			}
			plugin.getTempCooldownReport().remove(player);
		}
		
		return false;
				
 	}
	
	public void setPlayerTempCooldowns(Player player) {
		plugin.getTempCooldownReport().put(player,  System.currentTimeMillis());
		return;
	}
	
	
	public void saveData(GameReport info) {
		
		FileConfiguration reports = plugin.getReportsYamls();
		
		String target = info.getTarget();
		
		if(reports.contains("Reports."+target)) {
			List<String> list = reports.getStringList("Reports."+target+".Game-Reports");
			reports.set("Reports."+target+".Game-Reports", list);
			list.addAll(info.getReports());
		}else {
			List<String> list = reports.getStringList("Reports."+target+".Game-Reports");
			reports.set("Reports."+target+".Game-Reports", list);
			list.addAll(info.getReports());
		}
		
		plugin.getReportsYamls().save();
		plugin.getReportsYamls().reload();
		
	}
	
	
	public void hasMoreReports(Player player, String target) {
		FileConfiguration report = plugin.getReportsYamls();
		if(report.contains("Reports."+target)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.GOLD+"Pero si tiene Registros Anteriores, Usa para ver: "+ChatColor.GREEN+"/mg reportlogs details <target>");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Pero si tiene Registros Anteriores, Usa para ver: "+ChatColor.GREEN+"/mg reportlogs details <target>");
			
			return;
		}
	}
	
	
	public void hasModLogs(Player player, String target) {
		FileConfiguration mod = plugin.getPlayersHistoryYaml();
		if(mod.contains("Players."+target)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.GOLD+"Tambien tiene Registros de Moderacion, Usa para ver: "+ChatColor.AQUA+"/mg modlogs <target>");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Tambien tiene Registros de Moderacion, Usa para ver: "+ChatColor.AQUA+"/mg modlogs <target>");
			
			return;
		}
	}
	
	public void sendReportLogs(Player player, String target,int pag) {
		
		FileConfiguration report = plugin.getReportsYamls();
		if(!report.contains("Reports."+target)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun Registro Reporte.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun Registro Reporte.");
			
			return;
		}
		//Jugador=NAO2706-Sancion=WARN-Fecha=19/06/2024 22:09:37 PM-Tiempo=SIN TIEMPO-Moderador=CONSOLA-Razon=(HACKS)
    
		List<String> list = report.getStringList("Reports."+target+".Game-Reports");
		
		pagssendtoPlayer(player,list,pag,10,target,"Generales");
		
	}
 /// PRIMERO PEDIMOS UNA LISTA , LUEGO LA PAGINA A VISUALIZAR , POR ULTIMO CUANTOS DATOS CONFORMAN UNA PAGINA
	 public void pagssendtoPlayer(Player player,List<String> l , int pag,int datosperpags,String target,String message) {
	    	
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
	    			player.sendMessage(ChatColor.GOLD+"Reportes "+message+" de: "+ChatColor.GREEN+target);
		    		player.sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    		}
	    		
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Reportes "+message+" de: "+ChatColor.GREEN+target);
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    	
	    		for(int i = inicio;i < fin && i < l.size();i++) {
	    			if(player != null) {
	    				player.sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
	    						.replaceAll("Fecha:",ChatColor.GOLD+"Fecha:"+ChatColor.GREEN)
		    					.replaceAll("Reporta:",ChatColor.GOLD+"Reporta:"+ChatColor.GREEN).replaceAll("Mapa:",ChatColor.GOLD+"Mapa:"+ChatColor.GREEN).replaceAll("Motivo:",ChatColor.GOLD+"Motivo:"+ChatColor.GREEN)
		    					.replaceAll("Comentarios:",ChatColor.GOLD+"Comentarios:"+ChatColor.GREEN));
	    			}
	    			
	    			
	    			Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
    						.replaceAll("Fecha:",ChatColor.GOLD+"Fecha:"+ChatColor.GREEN)
	    					.replaceAll("Reporta:",ChatColor.GOLD+"Reporta:"+ChatColor.GREEN).replaceAll("Mapa:",ChatColor.GOLD+"Mapa:"+ChatColor.GREEN).replaceAll("Motivo:",ChatColor.GOLD+"Motivo:"+ChatColor.GREEN)
	    					.replaceAll("Comentarios:",ChatColor.GOLD+"Comentarios:"+ChatColor.GREEN));
	    					
	    		}
	    		
	    	}else {
	    		if(player != null) {
		    		player.sendMessage(ChatColor.RED+"No hay datos para mostrar.");

	    		}
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay datos para mostrar.");
	    	}
	    	return;
	    }
	 
	 
	 public void sendPagesToPlayerMap(Player player, LinkedHashMap<String, GameReport> reportMap, int page, int itemsPerPage) {
		 
		 	
		    if (!reportMap.isEmpty()) {
		        int startIndex = (page - 1) * itemsPerPage;
		        int endIndex = startIndex + itemsPerPage;
		        int mapSize = reportMap.size();
		        int numberOfPages = (int) Math.ceil((double) mapSize / itemsPerPage);

		        if (page > numberOfPages) {
		            // Mensaje de error: no hay más datos
		            return;
		        }

		        // Mensajes de encabezado
		        if (player != null) {
		            player.sendMessage(ChatColor.GOLD + "Reportes del Dia de Hoy");
		            player.sendMessage(ChatColor.GOLD + "Páginas: " + ChatColor.RED + page + ChatColor.GOLD + "/" + ChatColor.RED + numberOfPages);
		        }
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Reportes del Dia de Hoy");
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Páginas: " + ChatColor.RED + page + ChatColor.GOLD + "/" + ChatColor.RED + numberOfPages);

		        // Convertir el map a una lista de entradas para poder paginar
		        List<Map.Entry<String, GameReport>> entryList = new ArrayList<>(reportMap.entrySet());
		        
		        for (int i = startIndex; i < endIndex && i < mapSize; i++) {
		            Map.Entry<String, GameReport> entry = entryList.get(i);
		            String playerName = entry.getKey();
		            GameReport reports = entry.getValue();
		            int reportCount = reports.getReports().size(); // Asumiendo que getReports() devuelve la lista de reports

		            String message = ""+ChatColor.RED + (i + 1) + "). " + ChatColor.RED + playerName + ChatColor.RED +" - Reportes: " +ChatColor.GOLD + reportCount;
		            if (player != null) {
		                player.sendMessage(message);
		            }
		            Bukkit.getConsoleSender().sendMessage(message);
		        }
		    } else {
		        // Mensaje de error: no hay datos
		    }
		}
	 
	
	
}
