package me.nao.generalinfo.mg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import me.nao.main.mg.Minegame;

@SuppressWarnings("deprecation")
public class GameReportsManager {

	
	private Minegame plugin;
	
	public GameReportsManager(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	
	public void setReporttoTarget(Player user ,String target,String motive, String comment) {
		
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a",Locale.ENGLISH);
		 LocalDateTime ld = LocalDateTime.now();
		
		 if(hasPlayerTempCooldownReport(user,60)) return;
		 
			if(hasReportsPlayer(user)) {
				GameReport gr = plugin.getGameReports().get(target);
				List<String> l = gr.getReports();
				l.add("Fecha: "+ld.format(formatter).toString()+" Reporta: "+user.getName()+" Motivo: "+motive+" Comentarios: "+comment);
				
				saveData(plugin.getGameReports().get(target));
				
			}else {
				List<String> l = new ArrayList<>();
				l.add("Fecha: "+ld.format(formatter).toString()+" Reporta: "+user.getName()+" Motivo: "+motive+" Comentarios: "+comment);
				
				plugin.getGameReports().put(target,new GameReport(target,ld,l));
				
				saveData(plugin.getGameReports().get(target));
			} 
		
		user.sendMessage(ChatColor.RED+"Reportaste"+ChatColor.GRAY+" a "+ChatColor.GOLD+target+ChatColor.GRAY+" por "+ChatColor.GOLD+motive+ChatColor.GOLD+" Comentarios:"+ChatColor.DARK_PURPLE+comment);
		
		GameConditions gc = new GameConditions(plugin);
		Player t = gc.ConvertStringToPlayerAlone(target);
		
		if(t != null) {
			
		}
		
		return;
		
	}
	
	
	public boolean hasReportsPlayer(Player target) {
		return plugin.getTempCooldownReport().containsKey(target);
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
	
	
	public void sendReportLogs(Player player, String target,int pag) {
		
		FileConfiguration report = plugin.getReportsYamls();
		if(!report.contains("Reports."+target)) {
			
			if(player != null) {
				player.sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun Reporte.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"El Jugador "+target+" no existe o no tiene ningun Reporte.");
			
			return;
		}
		//Jugador=NAO2706-Sancion=WARN-Fecha=19/06/2024 22:09:37 PM-Tiempo=SIN TIEMPO-Moderador=CONSOLA-Razon=(HACKS)
    
		List<String> list = report.getStringList("Reports."+target+".Game-Reports");
		
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
	    			player.sendMessage(ChatColor.GOLD+"Reportes de: "+ChatColor.GREEN+target);
		    		player.sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    		}
	    		
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Reportes de: "+ChatColor.GREEN+target);
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"Paginas: "+ChatColor.RED+pag+ChatColor.GOLD+"/"+ChatColor.RED+((l.size()+datosperpags-1)/datosperpags));
	    	
	    		for(int i = inicio;i < fin && i < l.size();i++) {
	    			if(player != null) {
	    				player.sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
	    						.replaceAll("Fecha:",ChatColor.GOLD+"Fecha:"+ChatColor.GREEN)
		    					.replaceAll("Reporta:",ChatColor.GOLD+"Reporta:"+ChatColor.GREEN).replaceAll("Motivo:",ChatColor.GOLD+"Motivo:"+ChatColor.GREEN)
		    					.replaceAll("Comentarios:",ChatColor.GOLD+"Comentarios:"+ChatColor.GREEN));
	    			}
	    			
	    			
	    			Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+(i+1)+"). "+ChatColor.WHITE+l.get(i).replaceAll("-"," ")
    						.replaceAll("Fecha:",ChatColor.GOLD+"Fecha:"+ChatColor.GREEN)
	    					.replaceAll("Reporta:",ChatColor.GOLD+"Reporta:"+ChatColor.GREEN).replaceAll("Motivo:",ChatColor.GOLD+"Motivo:"+ChatColor.GREEN)
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
	
	
}
