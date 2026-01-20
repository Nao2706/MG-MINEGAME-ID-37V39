package me.nao.command.mg;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.nao.enums.mg.GameStatus;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.main.mg.Minegame;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandsMessage {
	
	private Minegame plugin;
	private GameConditions gc;
	
	
	public CommandsMessage(Minegame plugin) {
		this.plugin = plugin;
		this.gc = new GameConditions(plugin);
	}
	
	@SuppressWarnings("deprecation")
	public void formatsMessage(Player player) {
		
		
		if(player == null) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Advertencia:"+ChatColor.YELLOW+" Respeta cada formato.");
			
		    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " Formato 1 Lunes Miercoles Viernes : Formato con dias. (Asegurate de colocar los dias correctamente)");
		    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " Formato 2 1)Sabado Viernes 08:35-10:12 2)Sabado Viernes 08:35-10:12,12:35-15:11 :Formato con dias mas horas. (Asegurate de colocar los dias y las horas correctamente)");
		    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " Formato 3 10/01/2023 06:14:00 AM : Formato con fecha. (Asegurate de colocar las fecha correctamente)");
		    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " Formato 4 0/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM : Formato con fecha. (Asegurate de colocar las fechas correctamente)");
		
		    Bukkit.getConsoleSender().sendMessage("- Asegurate de escribir bien los dias (lunes martes miercolres etc).");
		    Bukkit.getConsoleSender().sendMessage("- Si cambias las horas del formato 2 asegurate que la primera hora sea inferior a la segunda hora.");
		    Bukkit.getConsoleSender().sendMessage("- Similar al formato 4 pero solo usa una fecha");
		    Bukkit.getConsoleSender().sendMessage("- Si usas el formato 4 que la primera fecha se inferior a la segunda y revisa bien lo del AM y PM");
			return;
		}
		
		player.sendMessage(ChatColor.RED+"Advertencia:"+ChatColor.YELLOW+" Respeta cada formato.");
		TextComponent m1 = new TextComponent();
		TextComponent m2 = new TextComponent();
		TextComponent m3 = new TextComponent();
		TextComponent m4 = new TextComponent();
		
		m1.setText(ChatColor.GREEN + " Formato 1");
		m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con dias. (Asegurate de colocar los dias correctamente)").color(ChatColor.GOLD).create()));
	    m1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"Lunes Miercoles Viernes"));

		m2.setText(ChatColor.GREEN + " Formato 2");
		m2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con dias mas horas. (Asegurate de colocar los dias y las horas correctamente)").color(ChatColor.GOLD).create()));
	    m2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"1)Sabado Viernes 08:35-10:12 2)Sabado Viernes 08:35-10:12,12:35-15:11"));
	    
		m3.setText(ChatColor.GREEN + " Formato 3");
		m3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con fecha. (Asegurate de colocar las fecha correctamente)").color(ChatColor.GOLD).create()));
	    m3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM"));
		
		
	    m4.setText(ChatColor.GREEN + " Formato 4");
		m4.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con fecha. (Asegurate de colocar las fechas correctamente)").color(ChatColor.GOLD).create()));
	    m4.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM"));
		
	    player.spigot().sendMessage(m1);
		player.spigot().sendMessage(m2);
		player.spigot().sendMessage(m3);
		player.spigot().sendMessage(m4);
	
		player.sendMessage("- Asegurate de escribir bien los dias (lunes martes miercolres etc).");
		player.sendMessage("- Si cambias las horas del formato 2 asegurate que la primera hora sea inferior a la segunda hora.");
		player.sendMessage("- Similar al formato 4 pero solo usa una fecha");
		player.sendMessage("- Si usas el formato 4 que la primera fecha se inferior a la segunda y revisa bien lo del AM y PM");
		
		
//		Lunes Miercoles Viernes
//		Domingo Sabado Viernes 08:35-17:11
//		10/01/2023 06:14:00 AM-
//		10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM
	}
	
	@SuppressWarnings("deprecation")
	public void DifficultMessage(Player player) {
		
		player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"[NIVELES DE DIFICULTAD]");
		TextComponent m1 = new TextComponent();
		TextComponent m2 = new TextComponent();
		TextComponent m3 = new TextComponent();
		TextComponent m4 = new TextComponent();
		TextComponent m5 = new TextComponent();
		TextComponent m6 = new TextComponent();
		
		m1.setText("- "+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.GREEN+ChatColor.BOLD+" FACIL"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("No tiene mucha dificultad , es algo simple de entender.").color(ChatColor.GREEN).create()));
	   // m1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"Lunes Miercoles Viernes"));

		m2.setText("- "+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.YELLOW+ChatColor.BOLD+" MEDIA"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		m2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Dificultad moderada , te encontraras con trampas en el camino , minas explosivas , trampas de flecha.").color(ChatColor.YELLOW).create()));
	   // m2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"Domingo Sabado Viernes 08:35-17:11"));
	    
		m3.setText("- "+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.RED+ChatColor.BOLD+" DIFICIL"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		m3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Pude hacerte enojar un poco , procura ir con cuidado.").color(ChatColor.RED).create()));
	    //m3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-"));
		
		
	    m4.setText("- "+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.DARK_RED+ChatColor.BOLD+" HARDCORE"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		m4.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Puede ser un nivel muy estresante , asegurate de ir con mas jugadores a menos que te consideres un experto.").color(ChatColor.DARK_RED).create()));
	   // m4.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM"));
		
	    m5.setText("- "+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.DARK_PURPLE+ChatColor.BOLD+" ELITE"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		m5.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Solo tu habilidad y experiencia te permitira sobrevivir a este tipo de niveles.").color(ChatColor.DARK_PURPLE).create()));
	   // m4.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM"));
		
	    m6.setText("- "+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.AQUA+ChatColor.BOLD+"DIFICULTAD:"+ChatColor.GOLD+ChatColor.BOLD+" LEYENDA"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]" );
		m6.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Este nivel es sumamente complejo asegurate de ir acompa�ado. ").color(ChatColor.DARK_RED).create()));
	   // m4.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM"));
		
	    player.spigot().sendMessage(m1);
		player.spigot().sendMessage(m2);
		player.spigot().sendMessage(m3);
		player.spigot().sendMessage(m4);
		player.spigot().sendMessage(m5);
		player.spigot().sendMessage(m6);
	
	
		
	}
	
	@SuppressWarnings("deprecation")
	public void joinannounce(Player player, String map) {
		GameConditions gc = new GameConditions(plugin);
		TextComponent m1 = new TextComponent();
		m1.setText(ChatColor.AQUA+"    [Click para Jugar]");
		m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("(Clickeame para Jugar.)").color(ChatColor.GOLD).create()));
	    m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg join "+map));

		
		if(player.hasPermission("mg.announce.join") && !gc.hasMaintenance() && !gc.isBlockedTheMap(map)) {
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(players.getName().equals(player.getName())) continue;
				if(gc.isPlayerinGame(players)) continue;
				players.sendMessage("");
				players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.GOLD+" Entro al mapa: "+ChatColor.RED+map);
				players.sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
			    players.spigot().sendMessage(m1);
			    players.sendMessage("");
			}
			
	 		
	 	}
	}
	
	@SuppressWarnings("deprecation")
	public void inviteToPlay(Player player, String map) {
		
		
		
		
		
		if(!gc.existMap(map)) {
			if(player != null) {
				player.sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe o esta mal escrito.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe o esta mal escrito.");
			return;
		}
		
		if(gc.isBlockedTheMap(map)) {
			gc.switchsendMessageForUserAndConsole(player,"&cEl Mapa &6"+map+" &cesta Deshabilitado.");
			return;
		}
		
		if(gc.hasMaintenance()) {
			gc.switchsendMessageForUserAndConsole(player,"&cAcceso denegado por Mantenimiento General.");

			return;
		}
		
		
		TextComponent m1 = new TextComponent();
		m1.setText(""+ChatColor.DARK_PURPLE+ChatColor.MAGIC+"[]"+ChatColor.AQUA+ChatColor.BOLD+"        [Click para Jugar]        "+ChatColor.DARK_PURPLE+ChatColor.MAGIC+"[]");
		m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("(Clickeame para Jugar.)").color(ChatColor.GOLD).create()));
	    m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg join "+map));
		
		if(gc.isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			if(gi.getGameStatus() == GameStatus.ESPERANDO || gi.getGameStatus() == GameStatus.COMENZANDO) {
				
				if(gi.getParticipants().size() < gi.getMaxPlayers()) {
					if(!gc.hasMaintenance() && !gc.isBlockedTheMap(map)) {
						for(Player players : Bukkit.getOnlinePlayers()) {
							if(gc.isPlayerinGame(players)) continue;
							players.sendMessage("");
							if(player != null) {
								players.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
								players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.GRAY+" esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
								players.sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
								players.spigot().sendMessage(m1);
							}else {
								players.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
								players.sendMessage(ChatColor.GREEN+"Notificacion: "+ChatColor.GRAY+"Se esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
								players.sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
								players.spigot().sendMessage(m1);
							}
							players.sendMessage("");
						  
						}
						
						
						Bukkit.getConsoleSender().sendMessage("");
						if(player != null) {
							Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+player.getName()+ChatColor.GRAY+" esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
							Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
							Bukkit.getConsoleSender().spigot().sendMessage(m1);
						}else {
							Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Notificacion: "+ChatColor.GRAY+"Se esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
							Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
							Bukkit.getConsoleSender().spigot().sendMessage(m1);
						}
						Bukkit.getConsoleSender().sendMessage("");
				 	}
				}
				

			}else {
				if(player != null) {
					player.sendMessage(ChatColor.RED+"El Mapa "+ChatColor.GOLD+map+ChatColor.RED+" ya esta Empezando o Jugando nose puede Invitar.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El ,apa "+ChatColor.GOLD+map+ChatColor.RED+" ya esta Empezando o Jugando nose puede Invitar.");
			}
		}else {
			//SINO ESTAS EN JUEGO
			if(!gc.hasMaintenance() && !gc.isBlockedTheMap(map)) {
				for(Player players : Bukkit.getOnlinePlayers()) {
					if(gc.isPlayerinGame(players)) continue;
					players.sendMessage("");
					if(player != null) {
						players.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
						players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.GRAY+" esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
						players.sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
						players.spigot().sendMessage(m1);
					}else {
						players.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
						players.sendMessage(ChatColor.GREEN+"Notificacion: "+ChatColor.GRAY+"Se esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
						players.sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
						players.spigot().sendMessage(m1);
					}
					players.sendMessage("");
				  
				}
				
				Bukkit.getConsoleSender().sendMessage("");
				if(player != null) {
					Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+player.getName()+ChatColor.GRAY+" esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
					Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
					Bukkit.getConsoleSender().spigot().sendMessage(m1);
				}else {
					Bukkit.getConsoleSender().sendMessage(""+ChatColor.RED+ChatColor.BOLD+"INVITACION DE JUEGO");
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Notificacion: "+ChatColor.GRAY+"Se esta Invitando a Jugar en el Mapa: "+ChatColor.RED+map);
					Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY+"Si deseas Unirte escribe: "+ChatColor.GREEN+"/mg join "+map+ChatColor.GRAY+" o Has Click en el mensaje de Abajo.");
					Bukkit.getConsoleSender().spigot().sendMessage(m1);
				}
				Bukkit.getConsoleSender().sendMessage("");
		 	}
		}

		
	
	}
	

	
	@SuppressWarnings("deprecation")
	public void infoPrestige(Player player) {
		
		FileConfiguration points = plugin.getPoints();
		
		if(!points.contains("Players."+player.getName())) {
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"!!! NOTIFICACION !!!");
			player.sendMessage(ChatColor.GRAY+"Este Comando es solo para Jugadores de Nivel 100");
			player.sendMessage(ChatColor.GRAY+"Tu nivel es: "+ChatColor.RED+0);
			player.sendMessage(ChatColor.GRAY+"Sube de Nivel Rapido para poder Usarlo.");
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 100,1);
			return;
		}
		
		
		int savelvl = points.getInt("Players."+player.getName()+".Level");
		int prestige = points.getInt("Players."+player.getName()+".Prestige");
		int maxlvl = 100;
		
		if(savelvl != maxlvl) {
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"!!! NOTIFICACION !!!");
			player.sendMessage(ChatColor.GRAY+"Este Comando es solo para Jugadores de Nivel 100");
			player.sendMessage(ChatColor.GRAY+"Tu nivel es: "+ChatColor.GREEN+savelvl);
			player.sendMessage(ChatColor.GRAY+"Sube de Nivel Rapido para poder Usarlo.");
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 100,1);
			return;
		}if(savelvl == maxlvl && prestige == 10) {
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"!!! MAXIMO PRESTIGIO ALCANZADO !!!");
			player.sendMessage(ChatColor.GRAY+"Ya has alcanzado el Prestigio Maximo.");
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 100,1);
			
			return;
		}
		
		
		
		
		TextComponent m1 = new TextComponent();
		m1.setText(""+ChatColor.GOLD+ChatColor.BOLD+">>>>>"+ChatColor.GREEN+ChatColor.BOLD+"        [SUBIR DE PRESTIGIO]        "+ChatColor.GOLD+ChatColor.BOLD+"<<<<<");
		m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("(Clickeame para Subir)").color(ChatColor.DARK_PURPLE).create()));
	    m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg upgradeprestige"));
	    player.sendMessage("");
		player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"!!! NOTIFICACION !!!");
		player.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.GRAY+" Felicidades has alcanzado el Nivel Maximo "+ChatColor.RED+"360.LVL");
		player.sendMessage(ChatColor.GRAY+"Tienes la Oportunidad de Subir de Prestigio, esto Reiniciara tus Niveles.");
		player.sendMessage(ChatColor.GRAY+"Pero puede que encuentres Cosas nuevas.");
		player.spigot().sendMessage(m1);
		player.sendMessage("");
		return;
	}


}
