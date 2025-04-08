package me.nao.manager.mg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nao.cosmetics.mg.Fireworks;
import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.GameType;
import me.nao.enums.mg.Items;
import me.nao.enums.mg.ReviveStatus;
import me.nao.enums.mg.StopMotive;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.GameNexo;
import me.nao.generalinfo.mg.GameObjetivesMG;
import me.nao.generalinfo.mg.GamePoints;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.revive.mg.RevivePlayer;
import me.nao.shop.mg.MinigameShop1;
import me.nao.teams.mg.MgTeams;
import me.nao.topusers.mg.PointsManager;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public class GameIntoMap {
	
	private Minegame plugin;

	
	public GameIntoMap(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	
	public void GameRevivePlayer(Player player,String name) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		GameObjetivesMG gomg = gm.getGameObjetivesMg();
		if(gm instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gm;
			List<String> deaths = ga.getDeadPlayers();
			
		 	player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 20.0F, 1F);
			if(!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 35)) {
				player.sendMessage(ChatColor.RED+"Necesitas 35 Diamantes para Revivir a ese Jugador");
				return;
			}
			
			if(!deaths.contains(name)) {
				
				player.sendMessage(ChatColor.RED+"Ese jugador salio de la partida o ya fue Revivido.");
			    return;
			}
			
			 String world = player.getWorld().getName();
			 int x = player.getLocation().getBlockX();
			 int y = player.getLocation().getBlockY();
			 int z = player.getLocation().getBlockZ();
			 
			 Location l = new Location(Bukkit.getWorld(world), x, y, z);
			
		
				   Player target = Bukkit.getServer().getPlayerExact(name);
				
				
					GameConditions cm = new GameConditions(plugin);
					cm.setHeartsInGame(player, mapa);
					cm.revivePlayerToGame(target, mapa);
				
					cm.setKitMg(target);
					if(gomg.hasMapObjetives()) {
						if(target.getInventory().getItemInMainHand() != null) {
							if(target.getInventory().getItemInMainHand().getType() == Material.AIR) {
								target.getInventory().setItemInMainHand(Items.OBJETIVOSP.getValue());
								
							}else {
								ItemStack item = target.getInventory().getItemInMainHand();
								target.getWorld().dropItem(target.getLocation(),item);
								target.getInventory().setItemInMainHand(Items.OBJETIVOSP.getValue());
							}
							
						}
					}
					MgTeams t = new MgTeams(plugin);
					t.JoinTeamLifeMG(target);
					
					target.teleport(l);
					target.setGameMode(GameMode.ADVENTURE);
					HealPlayer(target);
					target.sendTitle(ChatColor.GREEN+"Fuiste Revivido",ChatColor.GREEN+"por: "+ChatColor.YELLOW+player.getName(),20,60,20);
					target.sendMessage(ChatColor.WHITE+"Fuiste Revivido por: "+ChatColor.GREEN+player.getName());
					player.sendMessage(ChatColor.GREEN+"Reviviste a: "+ChatColor.GOLD+target.getName());
	
					cm.sendMessageToUsersOfSameMapLessTwoPlayers(player, target,ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Revivio a "+ChatColor.WHITE+target.getName());

					
					 //BORRAR ICONO DE MUERTE
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,35));
					pl.getGamePoints().setHelpRevive(pl.getGamePoints().getHelpRevive()+1);
					
					PlayerInfo targetrevive = plugin.getPlayerInfoPoo().get(target);
					targetrevive.getGamePoints().setRevive(targetrevive.getGamePoints().getRevive()+1);
					
					
					if(!deaths.isEmpty()) {
						MinigameShop1 ms = new MinigameShop1(plugin);
						ms.GameReviveInv(player);
						
					 }else {
						 player.closeInventory();
					 }
		}
		
				
		
	}
	
	
	public void GameRevivePlayerByCommand(Player player,String name) {
		
		Player target = Bukkit.getServer().getPlayerExact(name);
	
		GameConditions cm = new GameConditions(plugin);
		
		if(target == null) {
			cm.sendMessageToUserAndConsole(player, ChatColor.RED+"Ese Jugador esta desconectado.");
			return;
		}
		
		if(!plugin.getPlayerInfoPoo().containsKey(target)) {
			cm.sendMessageToUserAndConsole(player, ChatColor.RED+"Ese Jugador no esta en Partida.");
			return;
		}
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(target);
		String mapa = pl.getMapName();
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		GameStatus partida = gm.getGameStatus();
		
		
		
		if(partida == GameStatus.COMENZANDO || partida == GameStatus.TERMINANDO) {
			cm.sendMessageToUserAndConsole(player,ChatColor.RED+"Solo puedes usar el Comando de Revivir en una Partida en Progreso");
			return;
		}
		
		if(gm instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gm;
			List<String> vivo = ga.getAlivePlayers();
			List<String> deaths = ga.getDeadPlayers();
			List<String> knocked = ga.getKnockedPlayers();
			
			if(knocked.contains(name)) {
				
				RevivePlayer rp = plugin.getKnockedPlayer().get(target);
				rp.setReviveStatus(ReviveStatus.REVIVED);
				
				if(player != null) {
					target.sendTitle(ChatColor.GREEN+"Fuiste Revivido",ChatColor.GREEN+"por: "+ChatColor.YELLOW+player.getName(),20,60,20);
					target.sendMessage(ChatColor.WHITE+"Fuiste Revivido por: "+ChatColor.GREEN+player.getName());
					cm.sendMessageToUserAndConsole(player,ChatColor.GREEN+"Reviviste a: "+ChatColor.GOLD+target.getName());
					cm.sendMessageToUsersOfSameMapLessTwoPlayers(player,target,ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Revivio a "+ChatColor.WHITE+target.getName());
				}else {
					target.sendTitle(ChatColor.GREEN+"Fuiste Revivido",ChatColor.GREEN+"por: "+ChatColor.YELLOW+"Consola",20,60,20);
					target.sendMessage(ChatColor.WHITE+"Fuiste Revivido por: "+ChatColor.GREEN+"Consola");
					cm.sendMessageToUserAndConsole(null,ChatColor.GREEN+"Reviviste a: "+ChatColor.GOLD+target.getName());
					cm.sendMessageToUsersOfSameMapLessTwoPlayers(null,target,ChatColor.GOLD+"Consola"+ChatColor.GREEN+" Revivio a "+ChatColor.WHITE+target.getName());

				}
				
				return;
			}
			
			
			if(!deaths.contains(name)) {
				cm.sendMessageToUserAndConsole(player,ChatColor.RED+"Ese Jugador salio de la partida o ya fue Revivido.");
			    return;
			}
			
			Random r = new Random();
			
			String t = vivo.get(r.nextInt(vivo.size()));
			
			 Player targetgo = Bukkit.getServer().getPlayerExact(t);
			 String world = targetgo.getWorld().getName();
			 int x = targetgo.getLocation().getBlockX();
			 int y = targetgo.getLocation().getBlockY();
			 int z = targetgo.getLocation().getBlockZ();
			 
			 Location l = new Location(Bukkit.getWorld(world), x, y, z);
			
					
					cm.revivePlayerToGame(target, mapa);
					cm.setKitMg(target);
					
					MgTeams te = new MgTeams(plugin);
					te.JoinTeamLifeMG(target);
					
					target.teleport(l);
					target.setGameMode(GameMode.ADVENTURE);
					HealPlayer(target);
					
					if(player != null) {
						target.sendTitle(ChatColor.GREEN+"Fuiste Revivido",ChatColor.GREEN+"por: "+ChatColor.YELLOW+player.getName(),20,60,20);
						target.sendMessage(ChatColor.WHITE+"Fuiste Revivido por: "+ChatColor.GREEN+player.getName());
						cm.sendMessageToUserAndConsole(player,ChatColor.GREEN+"Reviviste a: "+ChatColor.GOLD+target.getName());
						cm.sendMessageToUsersOfSameMapLessTwoPlayers(player,target,ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Revivio a "+ChatColor.WHITE+target.getName());
					}else {
						target.sendTitle(ChatColor.GREEN+"Fuiste Revivido",ChatColor.GREEN+"por: "+ChatColor.YELLOW+"Consola",20,60,20);
						target.sendMessage(ChatColor.WHITE+"Fuiste Revivido por: "+ChatColor.GREEN+"Consola");
						cm.sendMessageToUserAndConsole(null,ChatColor.GREEN+"Reviviste a: "+ChatColor.GOLD+target.getName());
						cm.sendMessageToUsersOfSameMapLessTwoPlayers(null,target,ChatColor.GOLD+"Consola"+ChatColor.GREEN+" Revivio a "+ChatColor.WHITE+target.getName());

					}
					
					
				
			
		
		}
		
	
			
		 
		
	}
	
	public void GamePlayerFallMap(Player player,DamageCause d) {
		
	
		Block block = player.getLocation().getBlock();
		Block b = block.getRelative(0, -1, 0);
		
		if(b.getType() == Material.BARRIER && player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.ADVENTURE && d != null && d == DamageCause.VOID) {
			
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			String mapa = pl.getMapName();
			GameInfo gi = plugin.getGameInfoPoo().get(mapa);
			GameConditions gmc = new GameConditions(plugin);
			
			if(gmc.hasPlayerACheckPoint(player)) {
				return;
			}else if(gmc.hasAntiVoid(player) && gi.getGameType() != GameType.NEXO){
				
				gmc.TptoSpawnMapSimple(player);
				player.sendTitle(ChatColor.GREEN+"Anti Void Activado",ChatColor.GREEN+"No te Caigas",20,60,20);
				player.sendMessage(ChatColor.GREEN+"- Que suerte el Mapa tiene Anti Void pero Siempre Regresaras al Inicio del Mapa Ojo con el Tiempo.");
				player.sendMessage(ChatColor.GREEN+"- Mmmm no estoy seguro si el Daño por Caida esta Anulado asi que ten Cuidado.");
				return;
				
			}else {
				pl.getGamePoints().setDeads(pl.getGamePoints().getDeads()+1);
				player.getInventory().clear(); 
				player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"TE CAISTE DEL MAPA", 40, 80, 40);
				
				if(plugin.CreditKill().containsKey(player)) {
					Entity mob = plugin.CreditKill().get(player);
					if(!mob.isDead()) {
						if(EntityHasName(mob)) {
							player.sendMessage(ChatColor.RED+"Moriste por que "+ChatColor.YELLOW+"Te Caiste fuera del Mapa mientras escapabas de "+mob.getCustomName());
							gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Caerse Fuera del Mapa mientras Trataba de Escapar de "+mob.getCustomName());

						}else {
							player.sendMessage(ChatColor.RED+"Moriste por que "+ChatColor.YELLOW+"Caerte Fuera del Mapa  mientras escapabas de un "+mob.getType());
							gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Caerse Fuera del Mapa mientras Trataba de Escapar de un "+mob.getType());

						}
					}
					
				}else {
					player.sendMessage(ChatColor.RED+"Moriste por que "+ChatColor.YELLOW+"Te caiste fuera del Mapa");
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Caerse Fuera del Mapa.");

				}
					player.sendMessage(ChatColor.RED+"\nUsa la hotbar para ver a otros jugadores. "+ChatColor.YELLOW+"\n!!!Solo podras ver a los que estan en tu partida");
					player.sendMessage(ChatColor.GREEN+"Puedes ser revivido por tus compañeros.(Siempre que hayan cofres de Revivir)");
					
				 	MgTeams t = new MgTeams(plugin);;
					t.JoinTeamDeadMG(player);
					gmc.deadPlayerToGame(player, mapa);
					gmc.DeathTptoSpawn(player, mapa);
					player.setGameMode(GameMode.SPECTATOR);
					
					
				
			}
			
		}
		return;
	}
	
	public void GamePlayerWin(Player player) {
		GameConditions gmc = new GameConditions(plugin);
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		
		if(gm instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gm;
			StopMotive motivo = gm.getStopMotive();
			List<String> spect = ga.getSpectators();
			List<String> dead = ga.getDeadPlayers();
			List<String> arrivo = ga.getArrivePlayers();
			
			//FINALES POR COMANDO STOP PARA MODOS ADVENTURE Y RESISTENCE
			if(motivo == StopMotive.WIN && gm.getGameType() == GameType.ADVENTURE) {
				
				if(gm.getGameStatus() == GameStatus.JUGANDO || gm.getGameStatus() == GameStatus.PAUSE || gm.getGameStatus() == GameStatus.FREEZE) {
					if(!dead.contains(player.getName()) && !arrivo.contains(player.getName()) && !spect.contains(player.getName())) {
						
						gmc.playerArriveToTheWin(player, mapa);
						player.setGameMode(GameMode.SPECTATOR);
						Fireworks f = new Fireworks(player);
						f.spawnFireballGreenLarge();
						player.sendMessage(ChatColor.GREEN+"Has Sobrevivido Felicidades.");
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Sobrevivio y Gano.");
						
					 return;
					}
				}
				
				
			}else if(motivo == StopMotive.WIN && gm.getGameType() == GameType.RESISTENCE) {
				
				if(gm.getGameStatus() == GameStatus.JUGANDO || gm.getGameStatus() == GameStatus.PAUSE || gm.getGameStatus() == GameStatus.FREEZE) {
					gmc.EndTptoSpawn(player, mapa);
				}
				
				return;
				
				//VICTORIAS SIN USAR COMANDOS
			}else {
				
				if(gm.getGameType() == GameType.ADVENTURE) {
					
					//ESTO SOLO AFECTA AL MODO ADVENTURE 
					Block block = player.getLocation().getBlock();
					Block b = block.getRelative(0, -1, 0);
					Block b2 = block.getRelative(0, -2, 0);
					if(player.getGameMode() == GameMode.ADVENTURE && b.getType() == Material.LIME_STAINED_GLASS && b2.getType() == Material.BEACON) {
						
						ObjetivesInGame(player,mapa);
						return;
					}
				}
			
				
			}
		}
		
		
		
	}
	
	
	public void ObjetivesInGame(Player player,String mapa) {
		GameConditions gmc = new GameConditions(plugin);
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		GameObjetivesMG gomg = gm.getGameObjetivesMg();

		if(gomg.isNecessaryObjetivePrimary() && !gomg.isNecessaryObjetiveSedondary()) {
			if(gmc.isAllPrimaryObjetivesComplete(player, mapa)) {
				
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.playerArriveToTheWin(player, mapa);
					player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
					player.setGameMode(GameMode.SPECTATOR);
					Fireworks f = new Fireworks(player);
					f.spawnFireballGreenLarge();
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
			       
					gmc.EndTptoSpawn(player, mapa);
				}
				
			}else {
				if(gm.getGameType() == GameType.ADVENTURE) {
				
					gmc.TptoSpawnMapSimple(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que sino Completas los Objetivos Primarios no podras Ganar.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
					GamePlayerLost(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que no Completaste los Objetivos Primarios para poder Ganar.");
				}
				
			}
			isTheRankedGames(player,gm.isRankedMap());
			return;
		}else if(!gomg.isNecessaryObjetivePrimary() && gomg.isNecessaryObjetiveSedondary()) {
			if(gmc.isAllSecondaryObjetivesComplete(player, mapa)) {
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.playerArriveToTheWin(player, mapa);
					player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
					player.setGameMode(GameMode.SPECTATOR);
					Fireworks f = new Fireworks(player);
					f.spawnFireballGreenLarge();
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
			      
					gmc.EndTptoSpawn(player, mapa);
				}
				
			}else {
				
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.TptoSpawnMapSimple(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que sino Completas los Objetivos Secundarios no podras Ganar.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
					GamePlayerLost(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que no Completaste los Objetivos Secundarios para poder Ganar.");
				}
				
				
			}
			isTheRankedGames(player,gm.isRankedMap());
			return;
		}else if(gomg.isNecessaryObjetivePrimary() && gomg.isNecessaryObjetiveSedondary()) {
			if(gmc.isAllPrimaryObjetivesComplete(player, mapa) && gmc.isAllSecondaryObjetivesComplete(player, mapa)) {
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.playerArriveToTheWin(player, mapa);
					player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
					player.setGameMode(GameMode.SPECTATOR);
					Fireworks f = new Fireworks(player);
					f.spawnFireballGreenLarge();
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
				}if(gm.getGameType() == GameType.RESISTENCE) {

					gmc.EndTptoSpawn(player, mapa);
				}
				
			}else {
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.TptoSpawnMapSimple(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que sino Completas los Objetivos Primarios y Secundarios no podras Ganar.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
					GamePlayerLost(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que no Completaste los Objetivos Primarios y Secundarios para poder Ganar.");
				}
				
			}
			isTheRankedGames(player,gm.isRankedMap());
			return;
		}else {
			if(gm.getGameType() == GameType.ADVENTURE) {
				gmc.playerArriveToTheWin(player, mapa);
				player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
				player.setGameMode(GameMode.SPECTATOR);
				Fireworks f = new Fireworks(player);
				f.spawnFireballGreenLarge();
				gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
			}if(gm.getGameType() == GameType.RESISTENCE) {
		      
				gmc.EndTptoSpawn(player, mapa);
			}
			isTheRankedGames(player,gm.isRankedMap());
			return;
		}
	}
	
	
	public void GamePlayerLost(Player player) {
		GameConditions gmc = new GameConditions(plugin);
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		StopMotive motivo = gm.getStopMotive();
		
		gmc.deadPlayerToGame(player, mapa);
		
		
		MgTeams t = new MgTeams(plugin);
		t.JoinTeamDeadMG(player);
		PlayerDropAllItems(player);
		player.setGameMode(GameMode.SPECTATOR);
		player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0, 0),
				/* NUMERO DE PARTICULAS */50, 0.5, 1, 0.5, /* velocidad */0, null, true);
		
		if(motivo == StopMotive.LOSE) {
			 return;
									}
		if(motivo == StopMotive.ERROR) {
			 return;
		}
		if(motivo == StopMotive.FORCE) {
			 return;
		}else {
			//   player.setDisplayName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getDisplayName());
				 player.sendMessage(ChatColor.RED+"Se te acabo el tiempo has perdido.");
				 return;
		
		}
		
	}
	
	public void GamePlayerDeadInMap(Player player) {
		GameConditions gmc = new GameConditions(plugin);
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo gi = plugin.getGameInfoPoo().get(mapa);
		PlayerDropAllItems(player);
		player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0, 0),
				/* NUMERO DE PARTICULAS */100, 1, 2, 1, /* velocidad */0, null, true);
		
	
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			gmc.deadPlayerToGame(player, mapa);
			
		
			MgTeams t = new MgTeams(plugin);
			t.JoinTeamDeadMG(player);
			
			Fireworks f = new Fireworks(player);
			f.spawnFireballRedLarge();
			
			pl.getGamePoints().setDeads(pl.getGamePoints().getDeads()+1);
			player.setGameMode(GameMode.SPECTATOR);
			
			if(!ga.getAlivePlayers().isEmpty()) {
				player.sendMessage(ChatColor.RED+"|Usa la hotbar para Espectear a otros Jugadores. "+ChatColor.YELLOW+"\n|Solo podras ver a los que estan en tu Partida");
				player.sendMessage(ChatColor.WHITE+"|Puedes ser "+ChatColor.GREEN+"Revivido "+ChatColor.WHITE+"por tus compañeros"+ChatColor.GOLD+"(Siempre que hayan cofres de Revivir)");
			}
		
			
		}else if(gi instanceof GameNexo) {
//			GameNexo gn = (GameNexo) gi;
//			DestroyNexo dn = new DestroyNexo(plugin);
//			if(gn.getBlueTeamMg().contains(player.getName())) {
//				player.teleport(dn.TpSpawnBlue(mapa));
//			}else if(gn.getRedTeamMg().contains(player.getName())){
//				player.teleport(dn.TpSpawnRed(mapa));
//			}
			
			
			
		}
		
		
		
	}
	
	//TODO BORDE DE BIEN
	public void GamePlayerAddPoints(Player player) {
		
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		
			int puntos = pl.getGamePoints().getKills();
			puntos = puntos +1;
			pl.getGamePoints().setKills(puntos);
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""+ChatColor.GREEN+ChatColor.BOLD+"KILLS: "+ChatColor.RED+puntos));

		
	}
	
	public void getPointsOfPlayerGame(Player player) {
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GamePoints gp = pl.getGamePoints();
		int puntos = gp.getKills();
		int puntos2 = gp.getDeads();
		int puntos3 = gp.getRevive();
		int puntos4 = gp.getHelpRevive();
		long puntos5 = gp.getDamage();
		player.sendMessage(""+ChatColor.YELLOW+" [PUNTOS DE LA PARTIDA]");
		player.sendMessage(""+ChatColor.GREEN+" Eliminaciones : "+ChatColor.YELLOW+puntos);
		player.sendMessage(""+ChatColor.GREEN+" Muertes : "+ChatColor.YELLOW+puntos2);
		player.sendMessage(""+ChatColor.GREEN+" Revivido : "+ChatColor.YELLOW+puntos3);
		player.sendMessage(""+ChatColor.GREEN+" Ayudas a Revivir : "+ChatColor.YELLOW+puntos4);
		player.sendMessage(""+ChatColor.GREEN+" Daño : "+ChatColor.YELLOW+puntos5);
		
	}
	
	public int TransformPosOrNeg(int i) {
		return i =  (~(i -1));
	}
	
	public List<Entity> getNearbyEntites(Location l , int size){
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : l.getWorld().getEntities()) {
			if(l.distance(e.getLocation()) <= size) {
				entities.add(e);
			}
		}
		return entities;
		
		
	}
	
	public void isTheRankedGames(Player player ,boolean isranked) {
		
		if(isranked) {
			PointsManager pm = new PointsManager(plugin);
			player.sendMessage(ChatColor.GREEN+"Se han agregado Puntos al Ranking de Juegos");
			pm.setGamePoints(player);	
		}else {
			player.sendMessage(ChatColor.RED+"Este Mapa no añade Puntos al Ranking de Juegos.");
		}
		return;
	}
	
	//TODO DROP
	public void PlayerDropAllItems(Player player) {
		GameConditions gm = new GameConditions(plugin);
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		
		
		
		Location l = null;
		
		if(gm.isPlayerKnocked(player)) {
			 l = plugin.getKnockedPlayer().get(player).getArmorStand().getLocation();
		}else {
			 l = player.getLocation();
		}
		
		if(player.getInventory().getContents().length >= 1) {
			for (ItemStack itemStack : player.getInventory().getContents()) {
				if(itemStack == null) continue;
				
					if(gm.isPlayerinGame(player) && pl.isInventoryAllowedForTheMap()) {
						Item it = (Item) l.getWorld().spawnEntity(l,EntityType.ITEM);
						it.setItemStack(itemStack);
						it.setOwner(player.getUniqueId());
					}else {
						l.getWorld().dropItemNaturally(l, itemStack);
					}	
					
                    player.getInventory().removeItem(itemStack);
              }
				player.getInventory().clear(); 
		}

		return;
	}
	
	
	public void GameMobDamagerCauses(Player player ,Entity e) {
			
			GameConditions gmc = new GameConditions(plugin);
			
			
			if(gmc.hasPlayerACheckPoint(player)) return;
			
			player.sendMessage("");
			//SI TE MATA UN JUGADOR
			if(e instanceof Player) {
				Player p = (Player) e;
				player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto..",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+p.getName(), 40, 80, 40);
				if(p.getName().equals(player.getName())) {
					player.sendMessage(ChatColor.RED+"Moriste por Suicidarte");
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Suicidarse.");
				}else{
					
					if(hasEntityCustomItemStack(p)) {
						player.sendMessage(Component.text(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+p.getName()+ChatColor.RED+" usando ").append(Component.text(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName())).hoverEvent(p.getInventory().getItemInMainHand()));
						
						gmc.sendMessageTextComponentToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+p.getName()+ChatColor.RED+" usando ",p.getInventory().getItemInMainHand());
					}else {
						player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+p.getName());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+p.getName());
					}
				}
				
			//SI TE MATA UN PROYECTIL	
			}else if(e instanceof Projectile) {
				Projectile shoot = (Projectile) e;
				
				if(ProjectileHasName(shoot)) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+shoot.getCustomName(), 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por :"+ChatColor.YELLOW+shoot.getCustomName());
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+shoot.getCustomName());

				}else if(EntityHasShooter(shoot)) {
					Entity damager = (Entity) shoot.getShooter();
					//CHEQUEO POR SI ES DE UN JUGADOR
					if(damager instanceof Player) {
						Player killer = (Player) damager;
					
						if(killer.getName().equals(player.getName())) {
							
							if(hasEntityCustomItemStack(killer)) {
								
								String text = killer.getInventory().getItemInMainHand().getItemMeta() == null ? killer.getInventory().getItemInMainHand().getType().toString() : killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto ",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+"Autodispararse (Suicidio)", 40, 80, 40);
								player.sendMessage(Component.text(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto Disparado "+ChatColor.YELLOW+"por: "+ChatColor.YELLOW+"Autodispararse (Suicidio)"+ChatColor.RED+" usando ").append(Component.text(text)).hoverEvent(killer.getInventory().getItemInMainHand()));
								
								gmc.sendMessageTextComponentToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Autodispararse (Suicidio)"+ChatColor.RED+" usando ",killer.getInventory().getItemInMainHand());
							}else {
								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+"Autodispararse (Suicidio)", 40, 80, 40);
								player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+killer.getName());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Autodispararse (Suicidio)");
							}
							
							
//							player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Autodispararse a ti mismo/a. (Suicidio)");
//							gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Autodispararse (Suicidio)");
						}else{
							if(hasEntityCustomItemStack(killer)) {
								String text = killer.getInventory().getItemInMainHand().getItemMeta() == null ? killer.getInventory().getItemInMainHand().getType().toString() : killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName();

								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto Disparado ",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+killer.getName(), 40, 80, 40);
								player.sendMessage(Component.text(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto Disparado "+ChatColor.YELLOW+"por: "+killer.getName()+ChatColor.RED+" usando ").append(Component.text(text)).hoverEvent(killer.getInventory().getItemInMainHand()));
								
								gmc.sendMessageTextComponentToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio Disparado por "+ChatColor.YELLOW+killer.getName()+ChatColor.RED+" usando ",killer.getInventory().getItemInMainHand());
							}else {
								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto Disparado ",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+killer.getName(), 40, 80, 40);
								player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+killer.getName());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio Disparado por "+ChatColor.YELLOW+killer.getName());
							}
							
//							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+killer.getName(), 40, 80, 40);
//							player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+killer.getName());
//							gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+killer.getName());
						}
						
						
					//SINO PROVIENE DE UN JUGADOR SE EVALUA SI TIENE NOMBRE (ES CUSTOM) SINO ES DE UN MOV	
					}else if(damager instanceof LivingEntity) {
						LivingEntity liv = (LivingEntity) damager;
						
						
						if(EntityHasName(liv)) {
							if(hasEntityCustomItemStack(liv)) {
								String text1 = liv.getEquipment().getItemInMainHand().getItemMeta() == null ? liv.getEquipment().getItemInMainHand().getType().toString() : liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName();

								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"Disparado por: "+ChatColor.YELLOW+damager.getCustomName(), 40, 80, 40);
								//player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+damager.getCustomName()+" usando "+liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
								player.sendMessage(Component.text(ChatColor.RED+"Moriste Disparado por: "+ChatColor.YELLOW+liv.getCustomName()+ChatColor.RED+" usando ").append(Component.text(text1)).hoverEvent(liv.getEquipment().getItemInMainHand()));
							
								gmc.sendMessageTextComponentToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio Disparado por "+ChatColor.YELLOW+liv.getCustomName()+ChatColor.RED+" usando ",liv.getEquipment().getItemInMainHand());
								//gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+damager.getCustomName()+" usando "+liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
								//gmc.sendMessageTextComponentToAllUsersOfSameMap(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+liv.getCustomName()+ChatColor.RED+" usando ", liv.getEquipment().getItemInMainHand());
								//gmc.SendMessageTextComponentToAllUsersOfSameMap(player, null)
							}else {
								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto Disparado ",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+damager.getCustomName(), 40, 80, 40);
								player.sendMessage(ChatColor.RED+"Moriste Disparado por: "+ChatColor.YELLOW+damager.getCustomName());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio Disparado por "+ChatColor.YELLOW+damager.getCustomName());
							}
						}else {
							if(hasEntityCustomItemStack(liv)) {
								String text1 = liv.getEquipment().getItemInMainHand().getItemMeta() == null ? liv.getEquipment().getItemInMainHand().getType().toString() : liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName();

								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto Disparado ",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+liv.getType(), 40, 80, 40);
								//player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+damager.getCustomName()+" usando "+liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
								player.sendMessage(Component.text(ChatColor.RED+"Moriste Disparado por: "+ChatColor.YELLOW+liv.getType()+ChatColor.RED+" usando ").append(Component.text(text1)).hoverEvent(liv.getEquipment().getItemInMainHand()));
							
								gmc.sendMessageTextComponentToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio Disparado por "+ChatColor.YELLOW+liv.getType()+ChatColor.RED+" usando ",liv.getEquipment().getItemInMainHand());
						
							}else {
								player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto Disparado ",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+liv.getType(), 40, 80, 40);
								player.sendMessage(ChatColor.RED+"Moriste Disparado por: "+ChatColor.YELLOW+liv.getType());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio Disparado por "+ChatColor.YELLOW+liv.getType());
							}
						}
					}
				}
				
				
			}else if(e instanceof TNTPrimed) {
				TNTPrimed tnt = (TNTPrimed) e;
				if(tnt.getSource() != null) {
					Entity entnt = (Entity) tnt.getSource();
					if(entnt instanceof Player && EntityHasName(tnt)) {
						Player target = (Player) entnt;
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+tnt.getCustomName()+" de "+target.getName(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+tnt.getCustomName()+" de "+target.getName());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por. "+ChatColor.YELLOW+tnt.getCustomName()+" de "+target.getName());

					}else if(EntityHasName(entnt) && EntityHasName(tnt)){
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+tnt.getCustomName()+" de "+entnt.getCustomName(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+tnt.getCustomName()+" de "+entnt.getCustomName());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por. "+ChatColor.YELLOW+tnt.getCustomName()+" de "+entnt.getCustomName());

					}
				}
			}else{
				if(e instanceof LivingEntity) {
					LivingEntity liv = (LivingEntity) e;

					if(EntityHasName(liv)) {
						if(hasEntityCustomItemStack(liv)) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+liv.getCustomName(), 40, 80, 40);
							//player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+liv.getCustomName()+" usando "+liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
							player.sendMessage(Component.text(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+liv.getCustomName()+ChatColor.RED+" usando ").append(Component.text(liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName())).hoverEvent(liv.getEquipment().getItemInMainHand()));
							gmc.sendMessageTextComponentToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+liv.getCustomName()+ChatColor.RED+" usando ",liv.getEquipment().getItemInMainHand());

							//gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+liv.getCustomName()+" usando "+liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName()); 
							//gmc.sendMessageTextComponentToAllUsersOfSameMap(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+liv.getCustomName()+ChatColor.RED+" usando ", liv.getEquipment().getItemInMainHand());
							//gmc.SendMessageTextComponentToAllUsersOfSameMap(player, null)
						}else {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+e.getCustomName(), 40, 80, 40);
							player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+liv.getCustomName()+".");
							gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+liv.getCustomName()+".");
						}
						
						

					}else {
						if(hasEntityCustomItemStack(liv)) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+e.getType(), 40, 80, 40);
							//player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+e.getType()+" usando "+liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
							player.sendMessage(Component.text(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+liv.getType()+ChatColor.RED+" usando ").append(Component.text(liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName())).hoverEvent(liv.getEquipment().getItemInMainHand()));

							gmc.sendMessageTextComponentToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+liv.getCustomName()+ChatColor.RED+" usando ",liv.getEquipment().getItemInMainHand());

							//gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+e.getType()+" usando "+liv.getEquipment().getItemInMainHand().getItemMeta().getDisplayName()); 
							//gmc.sendMessageTextComponentToAllUsersOfSameMap(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+liv.getType()+ChatColor.RED+" usando ", liv.getEquipment().getItemInMainHand());
							//gmc.SendMessageTextComponentToAllUsersOfSameMap(player, null)
						}else {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+e.getType(), 40, 80, 40);
							player.sendMessage(ChatColor.RED+"Moriste por, "+ChatColor.YELLOW+e.getType()+".");
							gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+e.getType()+".");
						}
					}
					
					
				}else if(EntityHasName(e)) {
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+e.getCustomName(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+e.getCustomName()+".");
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+e.getCustomName()+".");

				}else {
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+e.getType(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por, "+ChatColor.YELLOW+e.getType()+".");
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+e.getType()+".");

				}
			}
			if(e instanceof Zombie) {
				spawnPlayerZombi(player);
			}
			player.sendMessage("");
			HealPlayer(player);
			GamePlayerDeadInMap(player);
	}
	
	public void GameDamageCauses(Player player, EntityDamageEvent.DamageCause c) {
		
			GameConditions gmc = new GameConditions(plugin);
			if(gmc.hasPlayerACheckPoint(player)) return;
			
				
				player.sendMessage("");
				if(plugin.CreditKill().containsKey(player)) {
					Entity mob = plugin.CreditKill().get(player);
					if(!mob.isDead()) {
						if(c == EntityDamageEvent.DamageCause.FALL) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"CAIDA", 40, 80, 40);
							if(EntityHasName(mob)) {
								player.sendMessage(ChatColor.RED+"Moriste por una "+ChatColor.YELLOW+"Caida mientras Tratabas de Escapar de "+mob.getCustomName());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por una "+ChatColor.YELLOW+"CAIDA mientras Tratabas de Escapar de "+mob.getCustomName());

							}else {
								player.sendMessage(ChatColor.RED+"Moriste por una "+ChatColor.YELLOW+"Caida mientras Tratabas de Escapar de "+mob.getType());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por una "+ChatColor.YELLOW+"CAIDA mientras Tratabas de Escapar de "+mob.getType());

							}
							     
						}
						
						if(c == EntityDamageEvent.DamageCause.FIRE) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"FUEGO", 40, 80, 40);
							
							if(EntityHasName(mob)) {
								player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Quemado mientras Tratabas de Escapar de "+mob.getCustomName());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"FUEGO mientras Tratabas de Escapar de "+mob.getCustomName());
							
							}else {
								player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Quemado mientras Tratabas de Escapar de "+mob.getType());
								gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"FUEGO mientras Tratabas de Escapar de "+mob.getType());
							
							}
							
							
						}
						
						if(c == EntityDamageEvent.DamageCause.LAVA) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"LAVA", 40, 80, 40);
							
							if(EntityHasName(mob)) {
								  player.sendMessage(ChatColor.RED+"Moriste por la "+ChatColor.YELLOW+"Lava mientras Tratabas de Escapar de "+mob.getCustomName());
								  gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Herobrine mientras Trataba de Escapar de "+mob.getCustomName());
										   
							}else {
							     player.sendMessage(ChatColor.RED+"Moriste por la "+ChatColor.YELLOW+"Lava mientras Tratabas de Escapar de "+mob.getType());
								 gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Herobrine mientras Trataba de Escapar de "+mob.getType());
									     	
							}
							
							
						}
						
						if(c == EntityDamageEvent.DamageCause.SUICIDE) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"SUICIDIO", 40, 80, 40);
							if(EntityHasName(mob)) {
								  player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Suicidio mientras Tratabas de Escapar de "+mob.getCustomName());
								  gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Suicidarse mientras Trataba de Escapar de "+mob.getCustomName());
										   
							}else {
							     player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Suicidio mientras Tratabas de Escapar de "+mob.getType());
								 gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Suicidarse mientras Trataba de Escapar de "+mob.getType());
									     	
							}
							   
						}
						
						if(c == EntityDamageEvent.DamageCause.CUSTOM) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"Herobrine", 40, 80, 40);
							
							if(EntityHasName(mob)) {
								  player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Herobrine mientras Tratabas de Escapar de "+mob.getCustomName());
								  gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Herobrine mientras Trataba de Escapar de "+mob.getCustomName());
										   
							}else {
							     player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Herobrine mientras Tratabas de Escapar de "+mob.getType());
								 gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Herobrine mientras Trataba de Escapar de "+mob.getType());
									     	
							}
							
							
						}if(c == EntityDamageEvent.DamageCause.FIRE_TICK) {
							player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"Arder en LLamas.", 40, 80, 40);
							
							if(EntityHasName(mob)) {
								  player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"en Llamas mientras Tratabas de Escapar de "+mob.getCustomName());
								  gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"en Llamas mientras Trataba de Escapar de "+mob.getCustomName());
										   
							}else {
							     player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"estar en Llamas mientras Tratabas de Escapar de "+mob.getType());
								 gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"estar en Llamas mientras Trataba de Escapar de "+mob.getType());
									     	
							}
						}
					}
					
					//PARA EVITAR DOBLE MENSAJE DUPLICADO POR LA MISMA CAUSA 
					player.sendMessage("");
					HealPlayer(player);
					GamePlayerDeadInMap(player);	
					return;
				}
				
				if(c == EntityDamageEvent.DamageCause.FALL) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"CAIDA", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por una "+ChatColor.YELLOW+"Caida");
					
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por una "+ChatColor.YELLOW+"CAIDA");
					     
				}
				
				if(c == EntityDamageEvent.DamageCause.FIRE) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"FUEGO", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Quemado");

				
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"FUEGO");
				
				}
				
				if(c == EntityDamageEvent.DamageCause.LAVA) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"LAVA", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por la "+ChatColor.YELLOW+"Lava");
				
				   gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por la "+ChatColor.YELLOW+"LAVA");
					     	
				}
				
				if(c == EntityDamageEvent.DamageCause.HOT_FLOOR) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"PISO CALIENTE", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"un Piso Caliente");
					
				    gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por un "+ChatColor.YELLOW+"PISO CALIENTE");
					    
				}
				
				if(c == EntityDamageEvent.DamageCause.DROWNING) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"AHOGADO", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Ahogado");
					
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio "+ChatColor.YELLOW+"AHOGADO");
					  
				}
				
				if(c == EntityDamageEvent.DamageCause.CONTACT) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"CONTACTO", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por un "+ChatColor.YELLOW+"Cactus");
					
				    gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por un "+ChatColor.YELLOW+"CACTUS");
					
				}
				
				if(c == EntityDamageEvent.DamageCause.FIRE_TICK) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"FUEGO", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Quemado hasta no poder mas");
			       
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio "+ChatColor.YELLOW+"QUEMADO");
					
				}
				
				if(c == EntityDamageEvent.DamageCause.POISON) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"ENVENENAMIENTO", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Envenenado");
					
				    gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"ENVENENAMIENTO");
					  
					
				}
				
				if(c == EntityDamageEvent.DamageCause.SUFFOCATION) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"SOFOCACION", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Sofocarte");
		
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"SOFOCACION");
					   
				}
				
				if(c == EntityDamageEvent.DamageCause.SUICIDE) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"SUICIDIO", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Suicidarte");
					
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"SUICIDIO");
					   
				}
				
				if(c == EntityDamageEvent.DamageCause.CUSTOM) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"HEROBRINE", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Herobrine");
					
				   gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"HEROBRINE");
					  
				}
				
				if(c == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"EXPLOTO UN BLOQUE", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"UNA EXPLOSION DE UN BLOQUE");
				
					 gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"UNA EXPLOSION DE UN BLOQUE");
					  
				}
				
				if(c == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"TE ESTRELLASTE", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"ESTRELLARTE CONTRA UNA PARED");
					
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"ESTRELLARSE CONTRA UNA PARED");
					 
					
				}
				
				if(c == EntityDamageEvent.DamageCause.KILL) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"TE HICIERON /KILL", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"QUE TE HICIERON /KILL");
					
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"QUE LE HICIERON /KILL");
					 
					
				}
				
				if(c == EntityDamageEvent.DamageCause.STARVATION) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"HAMBRE", 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste de "+ChatColor.YELLOW+"HAMBRE");
					
					gmc.sendMessageToUsersOfSameMapLessPlayer(player,ChatColor.GOLD+player.getName()+ChatColor.RED+" murio de "+ChatColor.YELLOW+"HAMBRE");
					 
					
				}
				player.sendMessage("");
				HealPlayer(player);
				GamePlayerDeadInMap(player);		
		//colocar abajo condicion reformada de checkpoint
		
	}
	
	public void HealPlayer(Player player) {
		PotionEffect vid = new PotionEffect(PotionEffectType.REGENERATION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		PotionEffect comida = new PotionEffect(PotionEffectType.SATURATION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		PotionEffect abso = new PotionEffect(PotionEffectType.ABSORPTION,/*duration*/ 10 * 20,/*amplifier:*/10, true ,true,true );
		player.addPotionEffect(vid);
		player.addPotionEffect(comida);
		player.addPotionEffect(abso);

		return;
	}
	
	public boolean EntityHasName(Entity e) {
		
		if(e.getCustomName() != null) {
			return true;
		}
		return false;
	}
	
	public boolean EntityHasShooter(Entity e) {
		
		if(e != null) {
			return true;
		}
		return false;
	}	
	
	public boolean ProjectileHasName(Projectile e) {
		
		if(e.getCustomName() != null) {
			return true;
		}
		return false;
	}
	
	public void spawnPlayerZombi(Player player) {
		
		
		PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );   
		PotionEffect salto= new PotionEffect(PotionEffectType.JUMP_BOOST,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );
		ItemStack[] inv = player.getInventory().getArmorContents();
		ItemStack item = new ItemStack(Material.PLAYER_HEAD,/*CANTIDAD*/1);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(player);
		item.setItemMeta(meta);
		

		Zombie zombi = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		zombi.setCanPickupItems(true);
		zombi.setCustomName(ChatColor.RED+player.getName());
		zombi.setCustomNameVisible(true);
		zombi.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(150);
		zombi.addPotionEffect(rapido);
		zombi.addPotionEffect(salto);
		zombi.getEquipment().setArmorContents(inv);
		zombi.getEquipment().setItemInMainHand(player.getInventory().getItemInMainHand());
		zombi.getEquipment().setHelmet(item);
		 	
	        //acciones
	
		//plugin.getDeadmob().put(player, player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ());
		
//		ArmorStand a = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0,-1,0), EntityType.ARMOR_STAND);
//		a.setCustomName(ChatColor.YELLOW+player.getName()+ChatColor.RED+" Murio aqui.");
//		a.setCustomNameVisible(true);
//		a.setCollidable(false);
//		a.setInvisible(true);
//		a.setInvulnerable(true);
//		a.setGravity(false);
	}
	
	
	public boolean hasEntityCustomItemStack(LivingEntity e) {
		
		if(e instanceof Player) {
			Player p = (Player) e;
			if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().hasItemMeta()) {
				if(p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
					return true;
				}
			}
		}else {
			if(e.getEquipment().getItemInMainHand() != null && e.getEquipment().getItemInMainHand().hasItemMeta()) {
				if(e.getEquipment().getItemInMainHand().getItemMeta().hasDisplayName()) {
					return true;
				}
			}
		}
		
		return false;
	}
	

 }
