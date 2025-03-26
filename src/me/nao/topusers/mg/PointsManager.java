package me.nao.topusers.mg;

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
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Strings;

import me.nao.cooldown.mg.Cooldown;
import me.nao.cosmetics.mg.Fireworks;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.GamePoints;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.generalinfo.mg.SystemOfLevels;
import me.nao.main.mg.Minegame;
import me.nao.utils.mg.Utils;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PointsManager {

	private Minegame plugin;
	
	public PointsManager(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
		//AGREGAR PUNTOS SI EL JUGADOR LLEGA A LA META 
		public void setGamePoints(Player player) {
	
			FileConfiguration points = plugin.getPoints();
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
			GamePoints gp = pl.getGamePoints();
			if(points.contains("Players."+player.getName())) {
				int point = points.getInt("Players."+player.getName()+".Kills");
				int point2 = points.getInt("Players."+player.getName()+".Deads");
				int point3 = points.getInt("Players."+player.getName()+".Revive");
				int point4 = points.getInt("Players."+player.getName()+".Help-Revive");
				
				
				points.set("Players."+player.getName()+".Kills",point+gp.getKills());
				points.set("Players."+player.getName()+".Deads",point2+gp.getDeads());
				points.set("Players."+player.getName()+".Revive",point3+gp.getRevive());
				points.set("Players."+player.getName()+".Help-Revive",point4+gp.getHelpRevive());
				saveAll();
				calcReferenceExp(player,calcExp(player, gp, gi));
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
				
				calcReferenceExp(player,calcExp(player, gp,gi));
			}
		
			
		}
		
		
		public long calcExp(Player player,GamePoints gp,GameInfo gi) {
			//k 200 hr 100 deads -200 revive 50 
			int kills = gp.getKills();
			int helpr = gp.getHelpRevive();
			int deads = gp.getDeads();
			int revive = gp.getRevive();
			int total = 0;
			//2
			kills = kills * gi.getPointsPerKills();
			helpr = helpr * gi.getPointsPerHelpRevive();
			deads = deads * gi.getPointsPerDeads();
			revive = revive * gi.getPointsPerRevive();
			
			total = (kills+helpr+revive)-deads;
		
			total = (gi.getPointsBonus() == 0) ? total : total * gi.getPointsBonus();
			
			
			return total;
		}
		
		
		public long currentXpLvl(int level) {
	    	//xpstart    increase
	    	//1000,  (1 + 2 / 100.0),
	  
	    	
	    	return level < 0 ? 0 :(long) Math.round(1000 * Math.pow((1 + 2 / 100.0), level));  
	    }

	 	
	 public long negativePointIncrease(long xp, int porcent) {
		    	
		    	long result = xp;
		    	
		    	if(result < 0 && porcent != 0) {
		    		// al colocar X -1 un valor positivo pasa a negativo lo mismo ocurre si el valor es negativo es una inversion
		    		result = (long) ((porcent((result * -1), porcent, true)) * - 1) ;
		    	}else if(result > 0 && porcent != 0) {
		    		result  = (long) porcent(result, porcent, false) ;
		    	}
		    	return result;
	 }
		    
		    
		    
		public double porcent(double valor , double porcent , boolean aumentar) {
				if(aumentar) {
					return valor + (valor * porcent /100 );
				}else {
					return valor - (valor * porcent /100 );
				}
		}
		
		

		
		public void calcReferenceExp(Player player ,long val) {
			
			FileConfiguration points = plugin.getPoints();
			
			int currentlvl = points.getInt("Players."+player.getName()+".Level");
			int prestige = points.getInt("Players."+player.getName()+".Prestige");
			
			//360 sera el tope de niveles , ultimo nivel 359 : pide 1223099 de xp para 360
			
			if(currentlvl == 360) {
				player.sendMessage(ChatColor.DARK_PURPLE+"Has alcanzado el Nivel Maximo ahora puedes Acceder al Prestigio.");
				return;
			}
			if(currentlvl == 360 && prestige == 10) {
				player.sendMessage(ChatColor.DARK_PURPLE+"Has alcanzado el Nivel y Prestigio Maximo.");
				return;
			}
			
			long savexp = points.getInt("Players."+player.getName()+".Xp");
			long refer = points.getInt("Players."+player.getName()+".Reference-Xp");
			int streak = points.getInt("Players."+player.getName()+".Streaks");
			
			
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
			
			
			SystemOfLevels manager = new SystemOfLevels();
			
			manager.rangeOfLvl(currentlvl);
			long baseoflvlxp = manager.getTotalxplvlA();
			long limitoflvlxp = manager.getTotalxplvlB();
			
			boolean playerlose = false;
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				if(!ga.getArrivePlayers().contains(player.getName())) {
					playerlose = true;
				}
			}
		
			int calcstreaks = streak * 100;
			long displayxp = (savexp+calcstreaks+val);
			long currentplayertotalxpwhithoutchanges = (manager.getTotalplayerxp()+savexp+calcstreaks+val);
			long copyeofcurrentplayertotal = displayxp;
			long currentplayertotalxpwithchanges = currentplayertotalxpwhithoutchanges;
			
			if(playerlose) {
				currentplayertotalxpwithchanges = (int) negativePointIncrease(currentplayertotalxpwithchanges,gi.getPointsLosePorcent());
				displayxp = (int) negativePointIncrease(displayxp,gi.getPointsLosePorcent());
			}			
			
			//SE LO USA PARA MOSTRAR EN MENSAJES DATOS
			GamePoints gp = pl.getGamePoints();
			//player.sendMessage("Has Ganado "+val+" de Xp para el modo Ranked.");
			player.sendMessage("");
			player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+ChatColor.UNDERLINE+"| INFORME DE XP|");
			player.sendMessage("");
			player.sendMessage(Utils.colorTextChatColor("&6El Mapa: &a"+pl.getMapName()+" &6da la Siguiente XP por Cada&f:"));
			player.sendMessage(Utils.colorTextChatColor("&7Kill: &a"+gi.getPointsPerKills()+" &7Muerte: &a"+gi.getPointsPerDeads()+" &7Revivir: &a"+gi.getPointsPerRevive()+" &7Ayudara Revivir: &a"+gi.getPointsPerHelpRevive()));
			player.sendMessage(Utils.colorTextChatColor("&aEl Bonus de este Mapa es: &6"+gi.getPointsBonus()));
			player.sendMessage(Utils.colorTextChatColor("&aTus Datos: &6"+player.getName()));
			player.sendMessage(Utils.colorTextChatColor("&7Kills: &a"+gp.getKills()+" &7Muertes: &a"+gp.getDeads()+" &7Revivido: &a"+gp.getRevive()+" &7Ayudas a Revivir: &a"+gp.getHelpRevive()));

			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.GRAY+"Xp Guardada: "+net.md_5.bungee.api.ChatColor.GREEN+savexp,"La Experiencia que tenias.", net.md_5.bungee.api.ChatColor.GOLD));
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.GREEN+"Xp Ganada: "+net.md_5.bungee.api.ChatColor.GREEN+val,"La Experiencia Conseguiste.", net.md_5.bungee.api.ChatColor.GOLD));
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.DARK_PURPLE+"Racha: "+net.md_5.bungee.api.ChatColor.GOLD+streak+net.md_5.bungee.api.ChatColor.RED+" x "+net.md_5.bungee.api.ChatColor.GOLD+net.md_5.bungee.api.ChatColor.BOLD+"100 "+net.md_5.bungee.api.ChatColor.RED+"= "+net.md_5.bungee.api.ChatColor.DARK_PURPLE+calcstreaks,"La Racha que tienes.", net.md_5.bungee.api.ChatColor.GOLD));
			ComponentBuilder cb = new ComponentBuilder();
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GOLD+"Resultado: ","El Calculo de la Experiencia.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GRAY+String.valueOf(savexp),"Experiencia Guardada.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" + "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GREEN+String.valueOf(val),"Experiencia Ganada.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" + "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.RED+String.valueOf(calcstreaks),Utils.colorText("La Racha se multiplica por 100.\nLuego se suma."), net.md_5.bungee.api.ChatColor.DARK_PURPLE));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" = "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_PURPLE+String.valueOf(displayxp),"Total de Puntos. ", net.md_5.bungee.api.ChatColor.GOLD));
			if(playerlose) {
				cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.RED+" Castigo por Perder: -"+String.valueOf(gi.getPointsLosePorcent())+"%"+net.md_5.bungee.api.ChatColor.GREEN+" \nOriginal XP:"+copyeofcurrentplayertotal,"Si tu XP es positiva es Descuento.\nSi tu XP es negativa enpeorara.", net.md_5.bungee.api.ChatColor.RED));
			}
			
			player.spigot().sendMessage(cb.create());
			player.sendMessage("");
			
			
			SystemOfLevels manager2 = new SystemOfLevels();
			manager2.calcXp(currentplayertotalxpwithchanges);
			
				//SUBE DE NIVEL
			if(currentplayertotalxpwithchanges > limitoflvlxp) {
				
				manager2.setRemaingxp(displayxp-limitoflvlxp);
				player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,player.getLocation().add(0.5, 1, 0.5), 100);
				player.sendMessage(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.GREEN+ChatColor.BOLD+ChatColor.UNDERLINE+" SUBISTE DE NIVEL "+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]");
			  	player.sendMessage("");
				player.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"   DE "+ChatColor.YELLOW+ChatColor.BOLD+"LVL."+currentlvl+ChatColor.GOLD+ChatColor.BOLD+" >>> A >>> "+ChatColor.GREEN+ChatColor.BOLD+"LVL."+manager2.getPlayerlvl());
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+manager2.getRemaingxp()+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+manager2.getReferenceB()+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"           ["+getProgressBar(manager2.getRemaingxp(), manager2.getReferenceB(),20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
				player.sendMessage("");
				
				points.set("Players."+player.getName()+".Level",manager2.getPlayerlvl());
				points.set("Players."+player.getName()+".Xp",manager2.getRemaingxp());
				points.set("Players."+player.getName()+".Reference-Xp",manager2.getReferenceB());
				
				saveAll();
				
				//BAJA DE NIVEL
			}else if(currentplayertotalxpwithchanges < baseoflvlxp) {
				manager2.setRemaingxp(currentplayertotalxpwithchanges-manager2.getReferenceA());
				player.sendMessage(""+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.RED+ChatColor.BOLD+ChatColor.UNDERLINE+" BAJASTE DE NIVEL "+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.MAGIC+"[]");
			  	player.sendMessage("");
				player.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"   DE "+ChatColor.YELLOW+ChatColor.BOLD+"LVL."+currentlvl+ChatColor.GOLD+ChatColor.BOLD+" >>> A >>> "+ChatColor.RED+ChatColor.BOLD+"LVL."+manager2.getPlayerlvl());
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+manager2.getRemaingxp()+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+manager2.getReferenceB()+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"           ["+getProgressBar(manager2.getRemaingxp(), manager2.getReferenceB(),20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
				player.sendMessage("");
				
				points.set("Players."+player.getName()+".Level",manager2.getPlayerlvl());
				points.set("Players."+player.getName()+".Xp",manager2.getRemaingxp());
				points.set("Players."+player.getName()+".Reference-Xp",manager2.getReferenceB());
				
				saveAll();
				
				//SE MANTIENE EN EL NIVEL
			}else{
				manager2.setRemaingxp(manager.getReferenceB()-displayxp);
				String text = manager2.getRemaingxp() > 0 ? ChatColor.GREEN+" + Subiste." : ChatColor.RED+" - Bajaste.";
				if(manager2.getRemaingxp() > 0) {
					player.sendMessage(ChatColor.DARK_GRAY+" Te faltan "+ChatColor.RED+manager2.getRemaingxp()+ChatColor.DARK_GRAY+" Puntos de XP para Subir de Nivel.");
				}else {
					player.sendMessage(ChatColor.DARK_GRAY+" Bajaste "+ChatColor.RED+manager2.getRemaingxp()+ChatColor.DARK_GRAY+" Puntos de XP para Subir de Nivel.");
				}
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+displayxp+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+refer+ChatColor.DARK_GRAY+ChatColor.BOLD+"]"+text);
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"           ["+getProgressBar(displayxp, refer,20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
				player.sendMessage("");
				
				points.set("Players."+player.getName()+".Xp",displayxp);
				saveAll();
				
			}
			
	
			
			player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
			player.sendMessage("");
			
		}
		
		//no tiene muchas condiciones por que esas van en otro lado CommandsMessage
		public void prestigeMg(Player player) {
			
			FileConfiguration points = plugin.getPoints();
			
			int savelvl = points.getInt("Players."+player.getName()+".Level");
			int prestige = points.getInt("Players."+player.getName()+".Prestige");
			
			//360 sera el tope de niveles , ultimo nivel 359 : pide 1223099 de xp para 360
			
			if(savelvl == 360) {
				player.sendMessage(ChatColor.DARK_PURPLE+"Has alcanzado el Nivel Maximo ahora puedes Acceder al Prestigio.");
				return;
			}
			
				points.set("Players."+player.getName()+".Level",0);
				points.set("Players."+player.getName()+".Xp",0);
				points.set("Players."+player.getName()+".Reference-Xp",1000);
				points.set("Players."+player.getName()+".Prestige",(prestige+1));
				saveAll();
				player.sendMessage(ChatColor.DARK_PURPLE+"Felicidades eres Prestigio: "+(prestige+1));
				player.sendTitle(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"["+ChatColor.GREEN+ChatColor.BOLD+"FELICIDADES"+ChatColor.DARK_PURPLE+ChatColor.BOLD+"]", ""+ChatColor.GREEN+ChatColor.BOLD+"SUBISTE DE PRESTIGIO "+ChatColor.GOLD+ChatColor.BOLD+(prestige+1), 20, 40, 20);
				player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100,1);
				Fireworks fw = new Fireworks(player);
				fw.spawnFireballGreenLarge();
				fw.spawnFireballAquaLarge();
				fw.spawnMetodoAyi();
				return;
			
			
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
