package me.nao.command.game;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandsMessage {
	
	
	
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
		m6.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Este nivel es sumamente complejo asegurate de ir acompañado. ").color(ChatColor.DARK_RED).create()));
	   // m4.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"10/01/2023 06:14:00 AM-13/01/2023 12:14:00 PM"));
		
	    player.spigot().sendMessage(m1);
		player.spigot().sendMessage(m2);
		player.spigot().sendMessage(m3);
		player.spigot().sendMessage(m4);
		player.spigot().sendMessage(m5);
		player.spigot().sendMessage(m6);
	
	
		
	}
	
	
	
	
	

}
