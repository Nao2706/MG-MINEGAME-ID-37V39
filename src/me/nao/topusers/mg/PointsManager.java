package me.nao.topusers.mg;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@SuppressWarnings("deprecation")
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
			int maxlvl = 100;
			if(currentlvl == maxlvl) {
				player.sendMessage(ChatColor.DARK_PURPLE+"Has alcanzado el Nivel Maximo ahora puedes Acceder al Prestigio.");
				return;
			}
			if(currentlvl == maxlvl && prestige == 10) {
				player.sendMessage(ChatColor.DARK_PURPLE+"Has alcanzado el Nivel y Prestigio Maximo.");
				return;
			}
			
			long savexp = points.getLong("Players."+player.getName()+".Xp");
			long refer = points.getLong("Players."+player.getName()+".Reference-Xp");
			int streak = points.getInt("Players."+player.getName()+".Streaks");
			
			
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
			
			
			SystemOfLevels manager = new SystemOfLevels();
			//CALCULAR XP DE NIVEL LA BASE Y EL LIMITE
			manager.rangeOfLvl(currentlvl);
			//long baseoflvlxp = manager.getTotalxplvlA();
			//long limitoflvlxp = manager.getTotalxplvlB();
			
			//SI PERDIO ESTO ESTARA EN TRUE SINO SERA FALSE
			boolean playerlose = false;
			if(gi instanceof GameAdventure) {
				GameAdventure ga = (GameAdventure) gi;
				if(!ga.getArrivePlayers().contains(player.getName())) {
					playerlose = true;
				}
			}
		
			//RECHA
			int calcstreaks = streak * 100;
			//DISPLAY DE XP : LO QUE CONSIGUIO DE XP EN UNA PARTIDA SE POSITIVO O NEGATIVO
			long displayxp = (savexp+calcstreaks+val);
			//XP REAL: LO QUE EL JUGADOR RALMENTE TIENE DE XP SUMANDO LOS NIVELES BASE MAS LA XP GUARDADA + RACHA + XP QUE GANO EN PARTIDA
			long currentplayertotalxpwhithoutchanges = (manager.getTotalPlayerXpLvl()+savexp+calcstreaks+val);
			//System.out.println("current "+savexp+" "+calcstreaks+" "+val+"= "+currentplayertotalxpwhithoutchanges);
			//long copyeofcurrentplayertotal = displayxp;
			
			//COPIA DE LA XP TOTAL PARA PODER REALIZAR MODIFICACIONES Y POSTERIOR PRINTEAR DIFERENCIAS.
			long currentplayertotalxpwithchanges = currentplayertotalxpwhithoutchanges;
			
			//SE MODIFICA EL VALOR COPIADO PARA VER CUAL SERA EL VERDADERO PUNTAJE EN CASO DE PERDER 
			// SI EL JUGADOR TIENE DE BASE 1001 + 100 XP = 1101 Y SI PIERDE -500 SERIA 601 Y SI RECIBE -1% SERIA 594
			if(playerlose) { 
				currentplayertotalxpwithchanges = negativePointIncrease(currentplayertotalxpwithchanges,gi.getPointsLosePorcent());
				displayxp = negativePointIncrease(displayxp,gi.getPointsLosePorcent());
			}			
			//System.out.println("current22 "+displayxp);
			//System.out.println("current2 "+currentplayertotalxpwithchanges);
			//SE LO USA PARA MOSTRAR EN MENSAJES DATOS
			
			//PRINTEAR DATOS
			GamePoints gp = pl.getGamePoints();
			//player.sendMessage("Has Ganado "+val+" de Xp para el modo Ranked.");
			player.sendMessage("");
			player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+ChatColor.UNDERLINE+"|TU INFORME DE XP|"+Utils.colorTextChatColor("&6 "+player.getName()));
			player.sendMessage("");
			player.sendMessage(Utils.colorTextChatColor("&6El Mapa: &a"+pl.getMapName()+" &6da la Siguiente XP por Cada&f:"));
			player.sendMessage(Utils.colorTextChatColor("&7Kill: &a"+gi.getPointsPerKills()+" &7Muerte: &a"+gi.getPointsPerDeads()+" &7Revivir: &a"+gi.getPointsPerRevive()+" &7Ayudara Revivir: &a"+gi.getPointsPerHelpRevive()));
			player.sendMessage(Utils.colorTextChatColor("&aEl Bonus de este Mapa es: &6X"+gi.getPointsBonus()));
			player.sendMessage(Utils.colorTextChatColor("&7Kills: &a"+gp.getKills()+" &7Muertes: &a"+gp.getDeads()+" &7Revivido: &a"+gp.getRevive()+" &7Ayudas a Revivir: &a"+gp.getHelpRevive()));

			//CAMBIAR ESTA COSA POR MINIMESSAGE Y SUS COMPONENTES
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.GRAY+"Xp de tu Nivel: "+net.md_5.bungee.api.ChatColor.GREEN+manager.getTotalPlayerXpLvl(),"La Experiencia que tu nivel Tiene", net.md_5.bungee.api.ChatColor.GOLD));
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.GRAY+"Xp Guardada: "+net.md_5.bungee.api.ChatColor.GREEN+savexp,"La Experiencia que tenias.", net.md_5.bungee.api.ChatColor.GOLD));
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.GREEN+"Xp Ganada: "+net.md_5.bungee.api.ChatColor.GREEN+val,"La Experiencia Conseguiste.", net.md_5.bungee.api.ChatColor.GOLD));
			player.spigot().sendMessage(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_GRAY+"- "+net.md_5.bungee.api.ChatColor.DARK_PURPLE+"Racha: "+net.md_5.bungee.api.ChatColor.GOLD+streak+net.md_5.bungee.api.ChatColor.RED+" x "+net.md_5.bungee.api.ChatColor.GOLD+net.md_5.bungee.api.ChatColor.BOLD+"100 "+net.md_5.bungee.api.ChatColor.RED+"= "+net.md_5.bungee.api.ChatColor.DARK_PURPLE+calcstreaks,"La Racha que tienes.", net.md_5.bungee.api.ChatColor.GOLD));
			ComponentBuilder cb = new ComponentBuilder();
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GOLD+"Resultado: ","El Calculo de la Experiencia.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GRAY+String.valueOf(manager.getTotalPlayerXpLvl()),"Experiencia del nivel.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" + "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GRAY+String.valueOf(savexp),"Experiencia Guardada.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" + "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.GREEN+String.valueOf(val),"Experiencia Ganada.", net.md_5.bungee.api.ChatColor.GREEN));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" + "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.RED+String.valueOf(calcstreaks),Utils.colorText("La Racha se multiplica por 100.\nLuego se suma."), net.md_5.bungee.api.ChatColor.DARK_PURPLE));
			cb.append(Utils.sendTextComponent(net.md_5.bungee.api.ChatColor.RED+" = "));
			cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.DARK_PURPLE+String.valueOf(currentplayertotalxpwithchanges),"Total de Puntos. ", net.md_5.bungee.api.ChatColor.GOLD));
			if(playerlose) {
				cb.append(Utils.sendTextComponentShow(net.md_5.bungee.api.ChatColor.RED+" Castigo por Perder: -"+String.valueOf(gi.getPointsLosePorcent())+"%"+net.md_5.bungee.api.ChatColor.GREEN+" \nXP Original:"+net.md_5.bungee.api.ChatColor.GOLD+currentplayertotalxpwhithoutchanges,"Si tu XP es Positiva es Descuento.\nSi tu XP es Negativa empeorara.", net.md_5.bungee.api.ChatColor.RED));
			}
			
			player.spigot().sendMessage(cb.create());
			player.sendMessage("");
			
			//SE VUELVE A CALCULAR EL NIVEL EN BASE A LA EXPERIENCIA TOTAL 
			SystemOfLevels manager2 = new SystemOfLevels();
			manager2.calcXp(currentplayertotalxpwithchanges);
			
				//SUBE DE NIVEL
			//SE COMPRUEBA QUE EL TOTAL DE XP CON CAMBIOS SEA MAYOR AL LIMITE DEL NIVEL QUE TUVO PARA VER SI SUBE DE NIVEL
			if(manager2.getPlayerlvl() > currentlvl) {
				//LA XP DEL JUEGO SE RESTA - POR LA REFERENCIA BASE DEL NUEVO NIVEL EJEMPLO XP 1002 - BASE -1001 = 1 XP RESTANTE
			
				//SI POR ALGUNA RAZON FUERA NEGATIVO SERIA 0 (NO DEBERIA ESTAR ESTO AQUI PUESTO QUE SOLO CALCULA SUBIDA)
				
				player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,player.getLocation().add(0.5, 1, 0.5), 100);
				player.sendMessage("     "+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.GREEN+ChatColor.BOLD+ChatColor.UNDERLINE+" SUBES DE NIVEL "+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]");
			  	player.sendMessage("");
				player.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"   DE "+ChatColor.YELLOW+ChatColor.BOLD+"LVL."+currentlvl+ChatColor.GOLD+ChatColor.BOLD+" >>> A >>> "+ChatColor.GREEN+ChatColor.BOLD+"LVL."+manager2.getPlayerlvl());
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+manager2.getRemaingxp()+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+manager2.getReferenceB()+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"            ["+showProgressBar(manager2.getRemaingxp(), manager2.getReferenceB(),20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
				player.sendMessage("");
				
				points.set("Players."+player.getName()+".Level",manager2.getPlayerlvl());
				points.set("Players."+player.getName()+".Xp",manager2.getRemaingxp());
				points.set("Players."+player.getName()+".Reference-Xp",manager2.getReferenceB());
				
				saveAll();
				player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
				player.sendMessage("");
				//BAJA DE NIVEL
				//COMPRUEBA QUE EL TOTAL DE XP SEA MENOR A LA BASE DEL NIVEL ACTUAL LO QUE SIGNIFICA QUE BAJARIA DE NIVEL , SI ES IGUAL A0 NO SE EJECUTARA POR QUE NO
				//PUEDES BAJARLE MAS A 0
				return;
			}else if(manager2.getPlayerlvl() < currentlvl && currentlvl != 0) {
				//System.out.println(currentplayertotalxpwithchanges+" "+manager2.getReferenceA()+" ="+(currentplayertotalxpwithchanges-manager2.getReferenceA()));
				//COMO SE RECALCULA DE NUEVO LA XP SE RESTA DEL TOTAL DE XP LA REFERENCIA BASE
				
				player.sendMessage("     "+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.RED+ChatColor.BOLD+ChatColor.UNDERLINE+" BAJAS DE NIVEL "+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.MAGIC+"[]");
			  	player.sendMessage("");
				player.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"   DE "+ChatColor.YELLOW+ChatColor.BOLD+"LVL."+currentlvl+ChatColor.GOLD+ChatColor.BOLD+" >>> A >>> "+ChatColor.RED+ChatColor.BOLD+"LVL."+manager2.getPlayerlvl());
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+manager2.getRemaingxp()+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+manager2.getReferenceB()+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"            ["+showProgressBar(manager2.getRemaingxp(), manager2.getReferenceB(),20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
				player.sendMessage("");
				
				points.set("Players."+player.getName()+".Level",manager2.getPlayerlvl());
				points.set("Players."+player.getName()+".Xp",manager2.getRemaingxp());
				points.set("Players."+player.getName()+".Reference-Xp",manager2.getReferenceB());
				
				saveAll();
				player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
				player.sendMessage("");
				return;
				//SE MANTIENE EN EL NIVEL
			}else{
				//System.out.println(" "+manager.getReferenceB()+" - "+displayxp +" "+(manager.getReferenceB()-displayxp));
				long result = displayxp < 0 ? 0 : displayxp;
				long leftpoints = manager2.getReferenceB() - displayxp;
				String text = displayxp > 0 ? ChatColor.GREEN+" + Subiste." : ChatColor.RED+" - Bajaste.";
				if(displayxp == savexp) {
					player.sendMessage(ChatColor.DARK_GRAY+" Te Mantienes en el Mismo Puntaje de Xp "+ChatColor.YELLOW+savexp+ChatColor.DARK_GRAY+" con el que Entraste.");
					text = ChatColor.YELLOW+" Se Mantiene";
				}else if(displayxp > savexp) {
				
					player.sendMessage(ChatColor.DARK_GRAY+" Te faltan "+ChatColor.GREEN+leftpoints+ChatColor.DARK_GRAY+" Puntos de XP para Subir de Nivel.");
				}else if(displayxp < savexp) {
					
					player.sendMessage(ChatColor.DARK_GRAY+" Bajaste "+ChatColor.RED+leftpoints+ChatColor.DARK_GRAY+" Puntos de XP para Subir de Nivel.");
				}
				
				
				
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+result+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+refer+ChatColor.DARK_GRAY+ChatColor.BOLD+"]"+text);
				player.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"            ["+showProgressBar(result, refer,20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
				player.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
				player.sendMessage("");
				
				points.set("Players."+player.getName()+".Xp",result);
				saveAll();
				player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
				player.sendMessage("");
				return;
			}
			
	
			

			
		}
		
		
		public void calcReferenceExpAddOrRemove(Player player ,String target,long val,boolean isset) {
			
			FileConfiguration points = plugin.getPoints();
			
			int currentlvl = points.getInt("Players."+target+".Level");
			int prestige = points.getInt("Players."+target+".Prestige");
			
			//360 sera el tope de niveles , ultimo nivel 359 : pide 1223099 de xp para 360
			int maxlvl = 100;
			if(currentlvl == maxlvl) {
				if(player != null) {
					player.sendMessage(ChatColor.DARK_PURPLE+"Ese Jugador a alcanzado el Nivel Maximo ahora puedes Acceder al Prestigio.");

				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"Ese Jugador a alcanzado el Nivel Maximo ahora puedes Acceder al Prestigio.");
				return;
			}
			if(currentlvl == maxlvl && prestige == 10) {
				if(player != null) {
					player.sendMessage(ChatColor.DARK_PURPLE+"Ese Jugador a alcanzado el Nivel y Prestigio Maximo.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"Ese Jugador a alcanzado el Nivel y Prestigio Maximo.");

				return;
			}
			
			long savexp = points.getLong("Players."+target+".Xp");
			long refer = points.getLong("Players."+target+".Reference-Xp");
			
			
			
			SystemOfLevels manager = new SystemOfLevels();
			//CALCULAR XP DE NIVEL LA BASE Y EL LIMITE
			manager.rangeOfLvl(currentlvl);
			
			//RECHA
			
			//DISPLAY DE XP : LO QUE CONSIGUIO DE XP EN UNA PARTIDA SE POSITIVO O NEGATIVO
			long displayxp = (savexp+val);
			//XP REAL: LO QUE EL JUGADOR RALMENTE TIENE DE XP SUMANDO LOS NIVELES BASE MAS LA XP GUARDADA + RACHA + XP QUE GANO EN PARTIDA
			long currentplayertotalxpwhithoutchanges = (manager.getTotalPlayerXpLvl()+savexp+val);
			if(isset) {
				displayxp = (val);
				//XP REAL: LO QUE EL JUGADOR RALMENTE TIENE DE XP SUMANDO LOS NIVELES BASE MAS LA XP GUARDADA + RACHA + XP QUE GANO EN PARTIDA
				currentplayertotalxpwhithoutchanges = (manager.getTotalPlayerXpLvl()+val);
			}else {
				displayxp = (savexp+val);
				//XP REAL: LO QUE EL JUGADOR RALMENTE TIENE DE XP SUMANDO LOS NIVELES BASE MAS LA XP GUARDADA + RACHA + XP QUE GANO EN PARTIDA
				currentplayertotalxpwhithoutchanges = (manager.getTotalPlayerXpLvl()+savexp+val);
			}
			
			//System.out.println("current "+savexp+" "+calcstreaks+" "+val+"= "+currentplayertotalxpwhithoutchanges);
			//long copyeofcurrentplayertotal = displayxp;
			
			//COPIA DE LA XP TOTAL PARA PODER REALIZAR MODIFICACIONES Y POSTERIOR PRINTEAR DIFERENCIAS.
			long currentplayertotalxpwithchanges = currentplayertotalxpwhithoutchanges;
			

			//SE VUELVE A CALCULAR EL NIVEL EN BASE A LA EXPERIENCIA TOTAL 
			SystemOfLevels manager2 = new SystemOfLevels();
			manager2.calcXp(currentplayertotalxpwithchanges);
			Player playeronline = Bukkit.getPlayerExact(target);
			long updateval = val < 0 ? 0 : val;
				//SUBE DE NIVEL
			//SE COMPRUEBA QUE EL TOTAL DE XP CON CAMBIOS SEA MAYOR AL LIMITE DEL NIVEL QUE TUVO PARA VER SI SUBE DE NIVEL
			if(manager2.getPlayerlvl() > currentlvl) {
				//LA XP DEL JUEGO SE RESTA - POR LA REFERENCIA BASE DEL NUEVO NIVEL EJEMPLO XP 1002 - BASE -1001 = 1 XP RESTANTE
			
				//SI POR ALGUNA RAZON FUERA NEGATIVO SERIA 0 (NO DEBERIA ESTAR ESTO AQUI PUESTO QUE SOLO CALCULA SUBIDA)
				if(playeronline != null) {
					playeronline.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,player.getLocation().add(0.5, 1, 0.5), 100);
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
					if(isset) {
						playeronline.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"XP SETEADA A "+ChatColor.RED+ChatColor.BOLD+updateval);
					}
					playeronline.sendMessage("     "+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.GREEN+ChatColor.BOLD+ChatColor.UNDERLINE+" SUBES DE NIVEL "+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"[]");
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"   DE "+ChatColor.YELLOW+ChatColor.BOLD+"LVL."+currentlvl+ChatColor.GOLD+ChatColor.BOLD+" >>> A >>> "+ChatColor.GREEN+ChatColor.BOLD+"LVL."+manager2.getPlayerlvl());
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+manager2.getRemaingxp()+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+manager2.getReferenceB()+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"            ["+showProgressBar(manager2.getRemaingxp(), manager2.getReferenceB(),20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
					playeronline.sendMessage("");
					

				}
							
				points.set("Players."+target+".Level",manager2.getPlayerlvl());
				points.set("Players."+target+".Xp",manager2.getRemaingxp());
				points.set("Players."+target+".Reference-Xp",manager2.getReferenceB());
				
				saveAll();
				
				//BAJA DE NIVEL
				//COMPRUEBA QUE EL TOTAL DE XP SEA MENOR A LA BASE DEL NIVEL ACTUAL LO QUE SIGNIFICA QUE BAJARIA DE NIVEL , SI ES IGUAL A0 NO SE EJECUTARA POR QUE NO
				//PUEDES BAJARLE MAS A 0
			}else if(manager2.getPlayerlvl() < currentlvl && currentlvl != 0) {
				//System.out.println(currentplayertotalxpwithchanges+" "+manager2.getReferenceA()+" ="+(currentplayertotalxpwithchanges-manager2.getReferenceA()));
				//COMO SE RECALCULA DE NUEVO LA XP SE RESTA DEL TOTAL DE XP LA REFERENCIA BASE
				if(playeronline != null) {
					
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
					if(isset) {
						playeronline.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"XP SETEADA A "+ChatColor.RED+ChatColor.BOLD+updateval);
					}
					playeronline.sendMessage("     "+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.MAGIC+"[]"+ChatColor.RED+ChatColor.BOLD+ChatColor.UNDERLINE+" BAJAS DE NIVEL "+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.MAGIC+"[]");
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"   DE "+ChatColor.YELLOW+ChatColor.BOLD+"LVL."+currentlvl+ChatColor.GOLD+ChatColor.BOLD+" >>> A >>> "+ChatColor.RED+ChatColor.BOLD+"LVL."+manager2.getPlayerlvl());
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+manager2.getRemaingxp()+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+manager2.getReferenceB()+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"            ["+showProgressBar(manager2.getRemaingxp(), manager2.getReferenceB(),20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
					playeronline.sendMessage("");
				}
				points.set("Players."+target+".Level",manager2.getPlayerlvl());
				points.set("Players."+target+".Xp",manager2.getRemaingxp());
				points.set("Players."+target+".Reference-Xp",manager2.getReferenceB());
				
				saveAll();
				
				//SE MANTIENE EN EL NIVEL
			}else{
				//System.out.println(" "+manager.getReferenceB()+" - "+displayxp +" "+(manager.getReferenceB()-displayxp));
				long result = displayxp < 0 ? 0 : displayxp;
				long leftpoints = manager2.getReferenceB() - displayxp;
				if(playeronline != null) {
				
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
					if(isset) {
						playeronline.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"XP SETEADA A "+ChatColor.RED+ChatColor.BOLD+updateval);
					}
					String text = displayxp > 0 ? ChatColor.GREEN+" + Subiste." : ChatColor.RED+" - Bajaste.";
					if(displayxp == savexp) {
						playeronline.sendMessage(ChatColor.DARK_GRAY+" Te Mantienes en el Mismo Puntaje de Xp "+ChatColor.YELLOW+savexp+ChatColor.DARK_GRAY+" con el que Entraste.");
						text = ChatColor.YELLOW+" Se Mantiene";
					}else if(displayxp > savexp) {
					
						playeronline.sendMessage(ChatColor.DARK_GRAY+" Te faltan "+ChatColor.GREEN+leftpoints+ChatColor.DARK_GRAY+" Puntos de XP para Subir de Nivel.");
					}else if(displayxp < savexp) {
						
						playeronline.sendMessage(ChatColor.DARK_GRAY+" Bajaste "+ChatColor.RED+leftpoints+ChatColor.DARK_GRAY+" Puntos de XP para Subir de Nivel.");
					}
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"             ["+ChatColor.YELLOW+result+ChatColor.GOLD+ChatColor.BOLD+"/"+ChatColor.GREEN+refer+ChatColor.DARK_GRAY+ChatColor.BOLD+"]"+text);
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"            ["+showProgressBar(result, refer,20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.DARK_GRAY+ChatColor.BOLD+"]");
					playeronline.sendMessage(""+ChatColor.DARK_GRAY+"Total de XP General: "+ChatColor.GREEN+ChatColor.BOLD+currentplayertotalxpwithchanges);
					playeronline.sendMessage("");
					playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
					playeronline.sendMessage("");
				}
				points.set("Players."+target+".Xp",result);
				saveAll();
				
			}
			
	
			
			
			
		}
		
		public void setPlayerLevelMg(Player player ,String target ,int lvl) {
			FileConfiguration points = plugin.getPoints();
			
			if(!points.contains("Players."+target)) {
				
				if(player != null) {
					player.sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				return;
			}
			
			
			SystemOfLevels manager = new SystemOfLevels();
			
			int updatelvl = lvl < 0 ? 0 : lvl;
			
			Player playeronline = Bukkit.getPlayerExact(target);
			if(playeronline != null ) {
				playeronline.sendMessage("");
				playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
				playeronline.sendMessage("");
				playeronline.sendMessage(""+ChatColor.GREEN+"Te Setearon al Nivel: "+ChatColor.GOLD+updatelvl); 
				playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
				playeronline.sendMessage("");
			}
			
			manager.rangeOfLvl(updatelvl);
			manager.calcXp(manager.getTotalPlayerXpLvl());
			points.set("Players."+target+".Level",manager.getPlayerlvl());
			points.set("Players."+target+".Xp",points.getLong("Players."+target+".Xp",0));
			points.set("Players."+target+".Reference-Xp",manager.getReferenceB());
			saveAll();
		}
		
		
		public void addOrRemovePlayerLevelMg(Player player ,String target ,int lvl) {
			FileConfiguration points = plugin.getPoints();
			
			if(!points.contains("Players."+target)) {
				
				if(player != null) {
					player.sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				return;
			}
			
			
			int currentlvl = points.getInt("Players."+target+".Level");
			
			int updatelvl = (currentlvl+lvl) < 0 ? 0 : (currentlvl+lvl);
			
			SystemOfLevels manager = new SystemOfLevels();
			manager.rangeOfLvl(updatelvl);
			manager.calcXp(manager.getTotalPlayerXpLvl());
			
			
			points.set("Players."+target+".Level",manager.getPlayerlvl());
			points.set("Players."+target+".Xp",points.getLong("Players."+target+".Xp",0));
			points.set("Players."+target+".Reference-Xp",manager.getReferenceB());
			saveAll();
		}
		
		public void setPlayerPrestigeMg(Player player ,String target ,int lvl) {
			FileConfiguration points = plugin.getPoints();
			
			if(!points.contains("Players."+target)) {
				
				if(player != null) {
					player.sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				return;
			}
			
			
			int updatelvl = lvl < 0 ? 0 : lvl;
			Player playeronline = Bukkit.getPlayerExact(target);
			if(playeronline != null ) {
				playeronline.sendMessage("");
				playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
				playeronline.sendMessage("");
				playeronline.sendMessage(""+ChatColor.GREEN+"Te Setearon el Nivel de Prestigio a: "+ChatColor.GOLD+updatelvl); 
				playeronline.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+"_____________________________________");
				playeronline.sendMessage("");
			}
			points.set("Players."+target+".Prestige",updatelvl);
			
			saveAll();
		}
		
		
		public void addOrRemovePlayerPrestigeMg(Player player ,String target ,int lvl) {
			FileConfiguration points = plugin.getPoints();
			
			if(!points.contains("Players."+target)) {
				
				if(player != null) {
					player.sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"El Jugador "+target+" no existe (no hay datos) o su nombre esta mal escrito.");
				return;
			}
			
			
			int currentprestige = points.getInt("Players."+target+".Prestige");
			
			int updatelvl = (currentprestige+lvl) < 0 ? 0 : (currentprestige+lvl);
			
		
			points.set("Players."+target+".Prestige",updatelvl);

			saveAll();
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
			
			
		if(points.contains("Players."+player.getName())) {
//			// PRIMERA PARTE
//			HashMap<String, Integer> scores = new HashMap<>();
//
//			for (String key : points.getConfigurationSection("Players").getKeys(false)) {
//
//				int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
//				// SE GUARDAN LOS DATOS EN EL HASH MAP
//				scores.put(key, puntaje);
//
//			}
//
//			// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
//			List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
//
//			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//				public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
//					return e2.getValue() - e1.getValue();
//				}
//			});

			HashMap<String, Long> scores = new HashMap<>();
			for (String key : points.getConfigurationSection("Players").getKeys(false)) {

				//lvl 100 312233
				long xp = points.getLong("Players." + key + ".Xp");
				int lvl = points.getInt("Players." + key + ".Level");
				int prestige = points.getInt("Players." + key + ".Prestige");
				
				
				SystemOfLevels manager = new SystemOfLevels();
				manager.rangeOfLvl(lvl);
				long xptotal = xp+manager.getTotalPlayerXpLvl()+(prestige*312232);
				// SE GUARDAN LOS DATOS EN EL HASH MAP
				scores.put(key, xptotal);

			}

			// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
			List<Map.Entry<String, Long>> list = new ArrayList<>(scores.entrySet());

			list.sort(Comparator.comparingLong((Map.Entry<String, Long> e)->e.getValue()).reversed());
			
			
			// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR

			int i = 0;
			for (Map.Entry<String, Long> e : list) {
				
				i++;
				if(i <= 10) {
						if(player.getName().equals(e.getKey())) {
							claimReward(player,i);
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
		
		
		
	
		
		
		
	public void claimReward(Player player,int pos) {
		 
		FileConfiguration cool = plugin.getCooldown();
		FileConfiguration config = plugin.getConfig();
		 List<String> rewards = config.getStringList("Top-Rewards.List");
			if(rewards.isEmpty()) {
				player.sendMessage(ChatColor.RED+"No hay Datos para dar Recompensas contacta a un Administrador.");
				return;
			}
			
			
			if(rewards.size() > pos) {
				// Random r = new Random();
					
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
//						for(int i = 0 ; i < (11 - pos) ;i++) {
//						}
							 //String commando = rand.get(r.nextInt(rand.size()));
							 int position = pos-1;
							 Bukkit.dispatchCommand(console, rewards.get(position).replaceAll("%player%",player.getName()));
							 console.sendMessage(""+ChatColor.RED+(pos)+ChatColor.GOLD+" Premio: "+ChatColor.GREEN+rewards.get(position).replaceAll("%player%",player.getName()));
							 player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100,1);
						
						 player.sendMessage(ChatColor.GOLD+"[Recompensa]: "+ChatColor.GREEN+"Has reclamado tu recompensa felicidades Diaria.");
						 //player.sendMessage("Como tu posicion en el Top es: "+ChatColor.GREEN+pos+"# \n"+ChatColor.GOLD+" Recibiras "+ChatColor.GREEN+(11 - pos)+ChatColor.GOLD+" Recompensas.");
						 player.sendMessage("Como tu Posicion en el Top es: "+ChatColor.GREEN+pos+"# \n"+ChatColor.GOLD+" Recibiras la recompensa de esa Posicion.");
						 
					}else {
						
						 player.sendMessage(ChatColor.GOLD+"[Recompensa]: "+ChatColor.RED+"Tu Proxima Recompensa sera en: "+ChatColor.GREEN+cooldown);
						 player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100,1);
					 }
			}else {
				player.sendMessage(Utils.colorTextChatColor("&cNo hay recompensas para tu posicion solo desde la Posicion&7: &61 &chasta &6"+(rewards.size()-1)));

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
		HashMap<String, Long> scores = new HashMap<>();

//		for (String key : points.getConfigurationSection("Players").getKeys(false)) {
//
//			int puntaje = Integer.valueOf(points.getString("Players." + key + ".Kills"));
//			// SE GUARDAN LOS DATOS EN EL HASH MAP
//			scores.put(key, puntaje);
//
//		}
		
		
		for (String key : points.getConfigurationSection("Players").getKeys(false)) {

			long xp = points.getLong("Players." + key + ".Xp");
			int lvl = points.getInt("Players." + key + ".Level");
			int prestige = points.getInt("Players." + key + ".Prestige");
			
			
			SystemOfLevels manager = new SystemOfLevels();
			manager.rangeOfLvl(lvl);
			long xptotal = xp+manager.getTotalPlayerXpLvl()+(prestige*312232);
			// SE GUARDAN LOS DATOS EN EL HASH MAP
			scores.put(key, xptotal);

		}
		
		// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
		List<Map.Entry<String, Long>> list = new ArrayList<>(scores.entrySet());

		//CON EL REVERSED VA DE MAYOR A MENOR SIN EL REVERSED DE MENOR A MAYOR
		list.sort(Comparator.comparingLong((Map.Entry<String, Long> e)->e.getValue()).reversed());
//		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
//				return e2.getValue() - e1.getValue();
//			}
//		});

		// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR
		
		  Entry<String, Long> p = list.get(posicion);
	
		
				String texto = message.getString("Top.message-top-global-text");
				f.append( texto.replaceAll("%player%",p.getKey())
						.replaceAll("%userxp%", p.getValue().toString())
						.replaceAll("%userlvl%",  points.getString("Players." + p.getKey() + ".Level"))
						.replaceAll("%place%", Integer.toString(posicion+1)));
			
		
		
		
		
		
		
		String top = f.toString();
		
		 return ChatColor.translateAlternateColorCodes('&',top);
	}catch(IndexOutOfBoundsException e) {
		return ChatColor.RED+"Vacio";
	}
		 
	}
	
	public String showProgressBar(long current, long max, int totalBars, char symbol, ChatColor completedColor,ChatColor notCompletedColor) {
		double percent = (double) current/max;
        int progressBars = (int) (totalBars * percent);
        if(current > max) {
        	System.out.println("ERROR:Es mayor "+current+" que "+max+" Percent:["+percent+"-"+current+"/"+max+"] "+"Progress:["+progressBars+"-"+totalBars+"*"+percent+"]");
        
        	
     }
        
        return Strings.repeat(""+ completedColor +ChatColor.BOLD + symbol, (progressBars > totalBars ? totalBars : progressBars)) + Strings.repeat("" + notCompletedColor +ChatColor.BOLD+ symbol,totalBars-progressBars);
   }
	
	
	
	public String Porcentage(long current , long max ) {
		double percent = current < 0 ? 0 : (double) current/max*100;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		return nf.format(percent)+"%";
	}
	
	public void saveAll() {
		plugin.getPoints().save();
		plugin.getPoints().reload();
	}
	
	
}
