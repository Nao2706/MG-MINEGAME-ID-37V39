package me.top.users.mg;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Strings;

import me.nao.cooldown.mg.Cooldown;
import me.nao.cosmetics.fireworks.Fireworks;
import me.nao.general.info.GamePoints;
import me.nao.general.info.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.utils.Utils;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PointsManager {

	private Minegame plugin;
	
	public PointsManager(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
		//AGREGAR PUNTOS SI EL JUGADOR LLEGA A LA META 
		public void addGamePoints(Player player) {
	
			FileConfiguration points = plugin.getPoints();
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			GamePoints gp = pl.getGamePoints();
			if(points.contains("Players."+player.getName()+".Kills")) {
				int point = points.getInt("Players."+player.getName()+".Kills");
				int point2 = points.getInt("Players."+player.getName()+".Deads");
				int point3 = points.getInt("Players."+player.getName()+".Revive");
				int point4 = points.getInt("Players."+player.getName()+".Help-Revive");
				
				
				points.set("Players."+player.getName()+".Kills",point+gp.getKills());
				points.set("Players."+player.getName()+".Deads",point2+gp.getDeads());
				points.set("Players."+player.getName()+".Revive",point3+gp.getRevive());
				points.set("Players."+player.getName()+".Help-Revive",point4+gp.getHelpRevive());
				saveAll();
				calcReferenceExp(player,calcExp(player, gp));
			}else {
				
				
				points.set("Players."+player.getName()+".Prestige",0);
				points.set("Players."+player.getName()+".Level",0);
				points.set("Players."+player.getName()+".Xp",0);
				points.set("Players."+player.getName()+".Reference-Xp",1000);
				points.set("Players."+player.getName()+".Streaks",0);
				points.set("Players."+player.getName()+".Wins",0);
				points.set("Players."+player.getName()+".Loses",0);
				points.set("Players."+player.getName()+".Kills",gp.getKills());
				points.set("Players."+player.getName()+".Deads",gp.getDeads());
				points.set("Players."+player.getName()+".Revive",gp.getRevive());
				points.set("Players."+player.getName()+".Help-Revive",gp.getHelpRevive());
				
				saveAll();
				
				calcReferenceExp(player,calcExp(player, gp));
			}
		
			
		}
		
		
		public int calcExp(Player player,GamePoints gp) {
			//k 200 hr 100 deads -200 revive 50 
			int kills = gp.getKills();
			int helpr = gp.getHelpRevive();
			int deads = gp.getDeads();
			int revive = gp.getRevive();
			int total ;
			//2
			kills = kills * 2;
			helpr = helpr * 15;
			deads = deads * 30;
			revive = revive * 10;
			
			total = kills+helpr+revive-deads;
		
			
			if(total <= 0) {
				player.sendMessage(ChatColor.RED+"XP Ganada: "+ChatColor.GOLD+0+ChatColor.RED+" tuviste muchas muertes.");
			}else {
				//player.sendMessage(ChatColor.RED+"XP Ganada: "+total);
				return total;
			}
			
			
			
			return 0;
		}
		
		public long xpPerLvl(int expstart , double increase , int level) {
	    	return (long) Math.round(expstart * Math.pow(increase, level));  
	    }
		
		public void calcReferenceExp(Player player ,int val) {
			FileConfiguration points = plugin.getPoints();
			
			int savelvl = points.getInt("Players."+player.getName()+".Level");
			int prestige = points.getInt("Players."+player.getName()+".Prestige");
			
			if(savelvl == 100) {
				player.sendMessage(ChatColor.DARK_PURPLE+"Has alcanzado el Nivel Maximo ahora puedes Acceder al Prestigio.");
				return;
			}
			if(savelvl == 100 && prestige == 100) {
				player.sendMessage(ChatColor.DARK_PURPLE+"Has alcanzado el Nivel y Prestigio Maximo.");
				return;
			}
			
			long refer = points.getInt("Players."+player.getName()+".Reference-Xp");
			long xp = points.getInt("Players."+player.getName()+".Xp");
			int streak = points.getInt("Players."+player.getName()+".Streaks");
		
			
			
			long totalxp = (xp + val + (streak * 100));
			
			//player.sendMessage("Has Ganado "+val+" de Xp para el modo Ranked.");
			player.sendMessage("");
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"| INFORME DE PROGRESO |");
			
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.GRAY+"Xp Guardada: "+net.md_5.bungee.api.ChatColor.GREEN+xp,"La Experiencia que tenias.", net.md_5.bungee.api.ChatColor.GOLD));
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.GREEN+"Xp Ganada: "+net.md_5.bungee.api.ChatColor.GREEN+val,"La Experiencia Conseguiste.", net.md_5.bungee.api.ChatColor.GOLD));
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.DARK_PURPLE+"Racha: "+net.md_5.bungee.api.ChatColor.GOLD+streak,"La Racha que Conseguiste.", net.md_5.bungee.api.ChatColor.GOLD));
			ComponentBuilder cb = new ComponentBuilder();
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GOLD+"Resultado: ","El Calculo de la Experiencia.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GRAY+String.valueOf(xp),"Experiencia Guardada.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" + "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GREEN+String.valueOf(val),"Experiencia Ganada.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" x "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.RED+String.valueOf(streak),"Las Rachas Multiplican el Puntaje.", net.md_5.bungee.api.ChatColor.GOLD));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" = "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_PURPLE+String.valueOf(totalxp),"Total de Puntos.", net.md_5.bungee.api.ChatColor.GOLD));

			player.spigot().sendMessage(cb.create());
			//player.sendMessage(ChatColor.GOLD+"Resultado: "+ChatColor.GRAY+xp+ChatColor.RED+" + "+ChatColor.GREEN+val+ChatColor.RED+" x "+ChatColor.GOLD+ChatColor.RED+streak+ChatColor.DARK_PURPLE+" = "+ChatColor.GREEN+(xp + val + (streak * 100)));
			//SI VA EN RACHA GANARA UN BONUS
			
			
			
			
			if(totalxp >= refer) {
//				// EL NUMERO 2 CAMBIA EL RANGO ENTRE LOS VALORES (EL RANGO FUE TESTEADO Y ES ACEPTABLE)
//				refer =  (int) Math.round(refer * (1 + 2 / 100.0));
				int lvl = 0;
				long referencia = 1000;
			  	long referencianterior = 0;
			  	long puntajerestante = totalxp;
			  	//CACLULA EL NIVEL EN BASE AL PUNTAJE 
			  	while(puntajerestante > referencia) {
			  		referencianterior = referencia;
			  		lvl++;
			  		referencia = (int) Math.round(referencia * (1 + 2 / 100.0));
			  		puntajerestante -= referencianterior;
			  	}
			  	
				//player.sendTitle(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.GREEN+ChatColor.BOLD+ChatColor.UNDERLINE+" SUBISTE DE NIVEL "+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]", ""+ChatColor.GOLD+ChatColor.BOLD+"DE NIVEL "+ChatColor.YELLOW+ChatColor.BOLD+savelvl+ChatColor.GOLD+ChatColor.BOLD+" AL NIVEL "+ChatColor.GREEN+ChatColor.BOLD+lvl, 40, 60, 20);
			  	player.sendMessage(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.GREEN+ChatColor.BOLD+ChatColor.UNDERLINE+" SUBISTE DE NIVEL "+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]");
				player.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"   DE "+ChatColor.YELLOW+ChatColor.BOLD+savelvl+".LVL"+ChatColor.GOLD+ChatColor.BOLD+" >>>>> "+ChatColor.GREEN+ChatColor.BOLD+lvl+".LVL");
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.YELLOW+puntajerestante+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+referencia+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+getProgressBar(puntajerestante, referencia,20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage("");
				player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,player.getLocation().add(0.5, 1, 0.5), 100);
				points.set("Players."+player.getName()+".Level",lvl);
				points.set("Players."+player.getName()+".Xp",puntajerestante);
				points.set("Players."+player.getName()+".Reference-Xp",referencia);
				
				saveAll();
				return;
			}else {
				points.set("Players."+player.getName()+".Xp",totalxp);
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+" "+ChatColor.YELLOW+totalxp+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+refer+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+getProgressBar(totalxp, refer,20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				saveAll();
			}
			
		}
		
		
		public void WinGamePoints(Player player) {
			FileConfiguration points = plugin.getPoints();
			if(points.contains("Players."+player.getName())) {
				int point = points.getInt("Players."+player.getName()+".Streaks");
				int point2 = points.getInt("Players."+player.getName()+".Wins");
				
				points.set("Players."+player.getName()+".Streaks",point+1);
				points.set("Players."+player.getName()+".Wins",point2+1);
				saveAll();
				
			}
		}
		
		public void LoseGamePoints(Player player) {
			FileConfiguration points = plugin.getPoints();
			if(points.contains("Players."+player.getName())) {
			
				int point2 = points.getInt("Players."+player.getName()+".Loses");
				
				points.set("Players."+player.getName()+".Streaks",0);
				points.set("Players."+player.getName()+".Loses",point2+1);
				saveAll();
				
			}
		}
	
		
		
		public void isInTop(Player player) {
			
			
			
			FileConfiguration points = plugin.getPoints();
			
			
			if(points.contains("Players."+player.getName()+".Kills")) {
			// PRIMERA PARTE
			HashMap<String, Integer> scores = new HashMap<>();

			for (String key : points.getConfigurationSection("Players").getKeys(false)) {

				int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
				// SE GUARDAN LOS DATOS EN EL HASH MAP
				scores.put(key, puntaje);

			}

			// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
			List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
					return e2.getValue() - e1.getValue();
				}
			});

			// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR

			int i = 0;
			for (Map.Entry<String, Integer> e : list) {
				
				i++;
				if(i <= 10) {
						if(player.getName().equals(e.getKey())) {
							ClaimReward(player,i);
							break;
						}
	
				}else{
					player.sendMessage(ChatColor.RED+"No estas en el Top 10 de Puntaje de Misiones.");
					break;
				}
			}
			
		}else {
			player.sendMessage(ChatColor.RED+"No tienes ni un Punto para participar en esto.");
			player.sendMessage(ChatColor.RED+"Consigue puntos en las Misiones.");
		}
			
			
		}
		
		
		
	
		
		
		
	public void ClaimReward(Player player,int pos) {
		FileConfiguration cool = plugin.getCooldown();
		FileConfiguration config = plugin.getConfig();
		 List<String> rand = config.getStringList("Random-Reward.List");
		 Random r = new Random();
		
		int tiempo = config.getInt("Time-Reward");// 3 horas 3600
		
		Cooldown c = new Cooldown (plugin, tiempo); 
		String pathtime = "Players."+player.getUniqueId()+".Cooldown-Recompensa";  
		String cooldown = c.getCooldown(player);
	
		if(cooldown.equals("-1")) {
			long millis = System.currentTimeMillis();   
			cool.set(pathtime, millis);
		    plugin.getCooldown().save();
		    
		    Fireworks f = new Fireworks(player);
		    for(int i = 0 ; i < 5; i++) {
		    	 f.spawnMetodoAyi();
		    }
		   
		    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		    console.sendMessage(ChatColor.WHITE+"El Jugador "+ChatColor.GREEN+player.getName()+ChatColor.WHITE+" en la Posicion "+ChatColor.GREEN+pos+"#"+ChatColor.WHITE+" del Top Reclamo su Recompensa Diaria.");
			for(int i = 0 ; i < (11 - pos) ;i++) {
		    	
				 String commando = rand.get(r.nextInt(rand.size()));
				 Bukkit.dispatchCommand(console, commando.replaceAll("%player%",player.getName()));
				 console.sendMessage(""+ChatColor.RED+(i+1)+ChatColor.GOLD+" Premio: "+ChatColor.GREEN+commando);
		    }
			
			 player.sendMessage(ChatColor.GOLD+"[Recompensa]: "+ChatColor.GREEN+"Has reclamado tu recompensa felicidades Diaria.");
			 player.sendMessage("Como tu posicion en el Top es: "+ChatColor.GREEN+pos+"# \n"+ChatColor.GOLD+" Recibiras "+ChatColor.GREEN+(11 - pos)+ChatColor.GOLD+" Recompensas.");
			 
			 
		}
		
		// se activa despues de reclamar
		else {
			
			player.sendMessage(ChatColor.GOLD+"[Recompensa]: "+ChatColor.RED+"Tu Proxima Recompensa sera en: "+ChatColor.GREEN+cooldown);
			
		 }
	}
	
	public String topArmorStand() {
		
		StringBuilder f = new StringBuilder();
		FileConfiguration message = plugin.getMessage();
    	FileConfiguration points = plugin.getPoints();
    	// PRIMERA PARTE
		HashMap<String, Integer> scores = new HashMap<>();

		for (String key : points.getConfigurationSection("Players").getKeys(false)) {

			int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
			// SE GUARDAN LOS DATOS EN EL HASH MAP
			scores.put(key, puntaje);

		}

		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});

		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR

		int i = 0;
		for (Map.Entry<String, Integer> e : list) {

			i++;
			if (i <= message.getInt("Top-Amount-Global")) {
				// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

				if (message.getBoolean("Top.message-top-global")) {
					String texto = message.getString("Top.message-top-global-text");
				
					
					
					f.append( texto.replaceAll("%player%",e.getKey())
							.replace("%pointuser%", e.getValue().toString())
							.replaceAll("%place%", Integer.toString(i))+"\n");
					
					
					
				}

			}
		}
		
		String top = f.toString();
		
		 return ChatColor.translateAlternateColorCodes('&',top);
	}
	
	
	public String positionArmorStand(int posicion) {
		
		try {
		
		StringBuilder f  = new StringBuilder();
		FileConfiguration message = plugin.getMessage();
    	FileConfiguration points = plugin.getPoints();
    	// PRIMERA PARTE
		HashMap<String, Integer> scores = new HashMap<>();

		for (String key : points.getConfigurationSection("Players").getKeys(false)) {

			int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
			// SE GUARDAN LOS DATOS EN EL HASH MAP
			scores.put(key, puntaje);

		}

		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
		List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});

		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
		
		  Entry<String, Integer> p = list.get(posicion);
	
		
				String texto = message.getString("Top.message-top-global-text");
				f.append( texto.replaceAll("%player%",p.getKey())
						.replace("%pointuser%", p.getValue().toString())
						.replaceAll("%place%", Integer.toString(posicion+1)));
			
		
		
		
		
		
		
		String top = f.toString();
		
		 return ChatColor.translateAlternateColorCodes('&',top);
	}catch(IndexOutOfBoundsException e) {
		return ChatColor.RED+"Vacio";
	}
		 
	}
	
	public String getProgressBar(long current, long max, int totalBars, char symbol, ChatColor completedColor,ChatColor notCompletedColor) {
		double percent = (double) current/max;
        int progressBars = (int) (totalBars * percent);
 
        return Strings.repeat(""+ completedColor +ChatColor.BOLD + symbol, progressBars) + Strings.repeat("" + notCompletedColor +ChatColor.BOLD+ symbol, totalBars - progressBars);
   }
	
	public String Porcentage(long current , long max ) {
		double percent = (double) current/max*100;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		return nf.format(percent)+"%";
	}
	
	public void saveAll() {
		plugin.getPoints().save();
		plugin.getPoints().reload();
	}
	
	
}
