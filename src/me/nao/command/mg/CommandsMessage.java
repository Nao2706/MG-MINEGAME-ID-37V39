package me.nao.command.mg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.nao.enums.GameStatus;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.main.mg.Minegame;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandsMessage {
	
	private Minegame plugin;
	
	public CommandsMessage(Minegame plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public void FormatsMessage(Player player) {
		
		player.sendMessage(ChatColor.RED+"Advertencia:"+ChatColor.YELLOW+" Respeta cada formato.");
		TextComponent m1 = new TextComponent();
		TextComponent m2 = new TextComponent();
		TextComponent m3 = new TextComponent();
		TextComponent m4 = new TextComponent();
		
		m1.setText(ChatColor.GREEN + " formato 1");
		m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con dias. (Asegurate de colocar los dias correctamente)").color(ChatColor.GOLD).create()));
	    m1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"Lunes Miercoles Viernes"));

		m2.setText(ChatColor.GREEN + " formato 2");
		m2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con dias mas horas. (Asegurate de colocar los dias y las horas correctamente)").color(ChatColor.GOLD).create()));
	    m2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"Domingo Sabado Viernes 08:35-17:11"));
	    
		m3.setText(ChatColor.GREEN + " formato 3");
		m3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con fecha. (Asegurate de colocar las fecha correctamente)").color(ChatColor.GOLD).create()));
	    m3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-"));
		
		
	    m4.setText(ChatColor.GREEN + " formato 4");
		m4.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Formato con fecha. (Asegurate de colocar las fechas correctamente)").color(ChatColor.GOLD).create()));
	    m4.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM"));
		
	    player.spigot().sendMessage(m1);
		player.spigot().sendMessage(m2);
		player.spigot().sendMessage(m3);
		player.spigot().sendMessage(m4);
	
		player.sendMessage("- Los dias de la semana siempre llevaran la primera letra en mayuscula.");
		player.sendMessage("- Si cambias las horas del formato 2 asegurate que la primera hora sea inferior a la segunda hora.");
		player.sendMessage("- Si usas el formato 3 no borres el signo ( - ) es similar al formato 4 pero solo usa una fecha");
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

		
		if(player.hasPermission("mg.announce.join") && !gc.HasMaintenance() && !gc.isBlockedTheMap(map)) {
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
		
		
		
		GameConditions gc = new GameConditions(plugin);
		
		if(!gc.ExistMap(map)) {
			if(player != null) {
				player.sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe o esta mal escrito.");
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" no existe o esta mal escrito.");
			return;
		}
		
		TextComponent m1 = new TextComponent();
		m1.setText(""+ChatColor.DARK_PURPLE+ChatColor.MAGIC+"[]"+ChatColor.AQUA+"        [Click para Jugar]        "+ChatColor.DARK_PURPLE+ChatColor.MAGIC+"[]");
		m1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("(Clickeame para Jugar.)").color(ChatColor.GOLD).create()));
	    m1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mg join "+map));
		
		if(gc.isMapinGame(map)) {
			GameInfo gi = plugin.getGameInfoPoo().get(map);
			if(gi.getGameStatus() == GameStatus.ESPERANDO) {
				if(!gc.HasMaintenance() && !gc.isBlockedTheMap(map)) {
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
			}else {
				if(player != null) {
					player.sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" ya esta Empezando o Jugando nose puede Invitar.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El mapa "+ChatColor.GOLD+map+ChatColor.RED+" ya esta Empezando o Jugando nose puede Invitar.");
			}
		}else {
			
			if(!gc.HasMaintenance() && !gc.isBlockedTheMap(map)) {
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
	



}
