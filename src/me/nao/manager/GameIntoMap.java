package me.nao.manager;

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
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nao.cosmetics.fireworks.Fireworks;
import me.nao.enums.GameStatus;
import me.nao.enums.GameType;
import me.nao.enums.Items;
import me.nao.enums.StopMotivo;
//import me.nao.gamemode.DestroyNexo;
import me.nao.general.info.GameInfo;
import me.nao.general.info.GameNexo;
import me.nao.general.info.GameObjetivesMG;
import me.nao.general.info.GamePoints;
import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameConditions;
import me.nao.general.info.PlayerInfo;
import me.nao.main.game.Minegame;
import me.nao.shop.MinigameShop1;
import me.nao.teamsmg.MgTeams;
import me.top.users.PointsManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

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
					cm.SetHeartsInGame(player, mapa);
					cm.RevivePlayerToGame(target, mapa);
				
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
		
		
		
		if(partida != GameStatus.JUGANDO) {
			cm.sendMessageToUserAndConsole(player,ChatColor.RED+"Solo puedes usar el Comando de Revivir en una Partida en Progreso");
			return;
		}
		
		if(gm instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gm;
			List<String> vivo = ga.getAlivePlayers();
			List<String> deaths = ga.getDeadPlayers();
			
			
			if(!deaths.contains(name)) {
				
				cm.sendMessageToUserAndConsole(player,ChatColor.RED+"Ese jugador salio de la partida o ya fue Revivido.");
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
			
		
			
				
					
					cm.RevivePlayerToGame(target, mapa);
					
					cm.setKitMg(target);
					
					MgTeams te = new MgTeams(plugin);
					te.JoinTeamLifeMG(target);
					
					target.teleport(l);
					target.setGameMode(GameMode.ADVENTURE);
					HealPlayer(target);
					target.sendTitle(ChatColor.GREEN+"Fuiste Revivido",ChatColor.GREEN+"por: "+ChatColor.YELLOW+player.getName(),20,60,20);
					target.sendMessage(ChatColor.WHITE+"Fuiste Revivido por: "+ChatColor.GREEN+player.getName());
					cm.sendMessageToUserAndConsole(player,ChatColor.GREEN+"Reviviste a: "+ChatColor.GOLD+target.getName());
					cm.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Revivio a "+ChatColor.WHITE+target.getName());
				
			
		
		}
		
	
			
		 
		
	}
	
	public void GamePlayerFallMap(Player player) {
		
		
		Block block = player.getLocation().getBlock();
		Block b = block.getRelative(0, -1, 0);
		
		if(b.getType() == Material.BARRIER && player.getGameMode() == GameMode.ADVENTURE) {
			
			PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
			String mapa = pl.getMapName();
			GameInfo gi = plugin.getGameInfoPoo().get(mapa);
			GameConditions gmc = new GameConditions(plugin);
			
			if(gmc.hasPlayerACheckPoint(player)) {
				return;
			}else if(gmc.hasAntiVoid(player) && gi.getGameType() != GameType.NEXO){
				
				gmc.TptoSpawnMapSimple(player);
				player.sendTitle(ChatColor.GREEN+"Anti Void Activado",ChatColor.GREEN+"No te Caigas",20,60,20);
				player.sendMessage(ChatColor.GREEN+"-Que suerte el Mapa tiene Anti Void pero Siempre Regresaras al Inicio del Mapa Ojo con el Tiempo.");
				player.sendMessage(ChatColor.GREEN+"-Mmmm no estoy seguro si el Daño por Caida esta Anulado asi que ten Cuidado.");
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

				}if(gi.getGameType() == GameType.ADVENTURE) {
					player.sendMessage(ChatColor.RED+"\nUsa la hotbar para ver a otros jugadores. "+ChatColor.YELLOW+"\n!!!Solo podras ver a los que estan en tu partida");
					player.sendMessage(ChatColor.GREEN+"Puedes ser revivido por tus compañeros.(Siempre que hayan cofres de Revivir)");
					
				 	MgTeams t = new MgTeams(plugin);;
					t.JoinTeamDeadMG(player);
					
			
					GameConditions cm = new GameConditions(plugin);
					cm.DeadPlayerToGame(player, mapa);
				
					 
					
					
					player.setGameMode(GameMode.SPECTATOR);
					gmc.DeathTptoSpawn(player, mapa);
					
				}else if(gi.getGameType() == GameType.NEXO) {
					 if(gi instanceof GameNexo) {
//							GameNexo gn = (GameNexo) gi;
//							DestroyNexo dn = new DestroyNexo(plugin);
//							if(gn.getBlueTeamMg().contains(player.getName())) {
//								player.teleport(dn.TpSpawnBlue(mapa));
//							}else if(gn.getRedTeamMg().contains(player.getName())){
//								player.teleport(dn.TpSpawnRed(mapa));
//							}
//							
						}
				}
			}
			
		}
		
	}
	
	public void GamePlayerWin(Player player) {
		GameConditions gmc = new GameConditions(plugin);
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		
		if(gm instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gm;
			StopMotivo motivo = gm.getMotivo();
			List<String> dead = ga.getDeadPlayers();
			List<String> arrivo = ga.getArrivePlayers();
			
			//FINALES POR COMANDO STOP PARA MODOS ADVENTURE Y RESISTENCE
			if(motivo == StopMotivo.WIN && gm.getGameType() == GameType.ADVENTURE && gm.getGameStatus() == GameStatus.JUGANDO) {
				if(!dead.contains(player.getName()) && !arrivo.contains(player.getName())) {
					
					gmc.PlayerArriveToTheWin(player, mapa);
					player.setGameMode(GameMode.SPECTATOR);
					Fireworks f = new Fireworks(player);
					f.spawnFireballGreenLarge();
					player.sendMessage(ChatColor.GREEN+"Has Sobrevivido Felicidades.");
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Sobrevivio y Gano.");
					isTheGameRanked(player,mapa);
					
				
				
				 return;
				}
			}else if(motivo == StopMotivo.WIN && gm.getGameType() == GameType.RESISTENCE && gm.getGameStatus() == GameStatus.JUGANDO) {
				
				gmc.EndTptoSpawn(player, mapa);
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
					gmc.PlayerArriveToTheWin(player, mapa);
					player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
					player.setGameMode(GameMode.SPECTATOR);
					Fireworks f = new Fireworks(player);
					f.spawnFireballGreenLarge();
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
			       
					gmc.EndTptoSpawn(player, mapa);
				}
				isTheGameRanked(player,mapa);
			}else {
				if(gm.getGameType() == GameType.ADVENTURE) {
				
					gmc.TptoSpawnMapSimple(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que sino Completas los Objetivos Primarios no podras Ganar.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
					GamePlayerLost(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que no Completaste los Objetivos Primarios para poder Ganar.");
				}
				
			}
			return;
		}else if(!gomg.isNecessaryObjetivePrimary() && gomg.isNecessaryObjetiveSedondary()) {
			if(gmc.isAllSecondaryObjetivesComplete(player, mapa)) {
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.PlayerArriveToTheWin(player, mapa);
					player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
					player.setGameMode(GameMode.SPECTATOR);
					Fireworks f = new Fireworks(player);
					f.spawnFireballGreenLarge();
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
			      
					gmc.EndTptoSpawn(player, mapa);
				}
				isTheGameRanked(player,mapa);
			}else {
				
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.TptoSpawnMapSimple(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que sino Completas los Objetivos Secundarios no podras Ganar.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
					GamePlayerLost(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que no Completaste los Objetivos Secundarios para poder Ganar.");
				}
				
				
			}
			return;
		}else if(gomg.isNecessaryObjetivePrimary() && gomg.isNecessaryObjetiveSedondary()) {
			if(gmc.isAllPrimaryObjetivesComplete(player, mapa) && gmc.isAllSecondaryObjetivesComplete(player, mapa)) {
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.PlayerArriveToTheWin(player, mapa);
					player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
					player.setGameMode(GameMode.SPECTATOR);
					Fireworks f = new Fireworks(player);
					f.spawnFireballGreenLarge();
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
				}if(gm.getGameType() == GameType.RESISTENCE) {

					gmc.EndTptoSpawn(player, mapa);
				}
				isTheGameRanked(player,mapa);
			}else {
				if(gm.getGameType() == GameType.ADVENTURE) {
					gmc.TptoSpawnMapSimple(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que sino Completas los Objetivos Primarios y Secundarios no podras Ganar.");
				}if(gm.getGameType() == GameType.RESISTENCE) {
					GamePlayerLost(player);
					player.sendMessage(ChatColor.RED+"Ups me temo que no Completaste los Objetivos Primarios y Secundarios para poder Ganar.");
				}
				
			}
			return;
		}else {
			if(gm.getGameType() == GameType.ADVENTURE) {
				gmc.PlayerArriveToTheWin(player, mapa);
				player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta.");
				player.setGameMode(GameMode.SPECTATOR);
				Fireworks f = new Fireworks(player);
				f.spawnFireballGreenLarge();
				gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
			}if(gm.getGameType() == GameType.RESISTENCE) {
		      
				gmc.EndTptoSpawn(player, mapa);
			}
			isTheGameRanked(player,mapa);
			return;
		}
	}
	
	
	public void GamePlayerLost(Player player) {
		GameConditions gmc = new GameConditions(plugin);
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		StopMotivo motivo = gm.getMotivo();
		
		gmc.DeadPlayerToGame(player, mapa);
		
		
		MgTeams t = new MgTeams(plugin);
		t.JoinTeamDeadMG(player);
		PlayerDropAllItems(player);
		player.setGameMode(GameMode.SPECTATOR);
		player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0, 0),
				/* NUMERO DE PARTICULAS */50, 0.5, 1, 0.5, /* velocidad */0, null, true);
		
		if(motivo == StopMotivo.LOSE) {
			 return;
									}
		if(motivo == StopMotivo.ERROR) {
			 return;
		}
		if(motivo == StopMotivo.FORCE) {
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
				/* NUMERO DE PARTICULAS */50, 0, 0, 0, /* velocidad */0, null, true);
		player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0),
				/* NUMERO DE PARTICULAS */50, 0, 0, 0, /* velocidad */0, null, true);
	
		if(gi instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gi;
			gmc.DeadPlayerToGame(player, mapa);
			
		
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
			puntos = puntos+1;
			
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
		int puntos5 = gp.getDamage();
		player.sendMessage(""+ChatColor.YELLOW+ChatColor.BOLD+" [PUNTUACION ACTUAL]");
		player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+" Eliminaciones : "+ChatColor.YELLOW+ChatColor.BOLD+puntos);
		player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+" Muertes : "+ChatColor.YELLOW+ChatColor.BOLD+puntos2);
		player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+" Revivido : "+ChatColor.YELLOW+ChatColor.BOLD+puntos3);
		player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+" Ayudas a Revivir : "+ChatColor.YELLOW+ChatColor.BOLD+puntos4);
		player.sendMessage(""+ChatColor.GREEN+ChatColor.BOLD+" Daño : "+ChatColor.YELLOW+ChatColor.BOLD+TransformPosOrNeg(puntos5));
		
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
	
	public void isTheGameRanked(Player player ,String mapa) {
		GameConditions gm = new GameConditions(plugin);
		//int puntos = plugin.getEspecificPlayerPoints().get(player);
		//int puntos = pl.getGamePoints().getKills();
		
		if(gm.isMapRanked(mapa)) {
			PointsManager pm = new PointsManager(plugin);
			player.sendMessage(ChatColor.GREEN+"Se han agregado Puntos al Ranking de Juegos");
			pm.addGamePoints(player);	
		}else {
			player.sendMessage(ChatColor.RED+"Este Mapa no añade Puntos al Ranking de Juegos.");
		}
		return;
	}
	
	//TODO DROP
	public void PlayerDropAllItems(Player player) {
		GameConditions gm = new GameConditions(plugin);
		
		Location l = null;
		
		if(gm.isPlayerKnocked(player)) {
			 l = plugin.getKnockedPlayer().get(player).getArmorStand().getLocation();
		}else {
			 l = player.getLocation();
		}
		
		if(player.getInventory().getContents().length >= 1) {
			for (ItemStack itemStack : player.getInventory().getContents()) {
				if(itemStack == null) continue;
				
					if(gm.isPlayerinGame(player) && gm.CanJoinWithYourInventory(plugin.getPlayerInfoPoo().get(player).getMapName())) {
						Item it = (Item) l.getWorld().spawnEntity(l,EntityType.DROPPED_ITEM);
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
				}else{
					player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+p.getName());
				}
			
				gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+p.getName());
				
			//SI TE MATA UN PROYECTIL	
			}else if(e instanceof Projectile) {
				Projectile shoot = (Projectile) e;
				Entity damager = (Entity) shoot.getShooter();
				
				//CHEQUEO POR SI ES DE UN JUGADOR
				if(damager instanceof Player) {
					Player killer = (Player) damager;
				
					if(damager.getName().equals(player.getName())) {
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+"Autodispararse (Suicidio)", 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Autodispararse a ti mismo/a. (Suicidio)");
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"Autodispararse (Suicidio)");
					}else{
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+killer.getName(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+killer.getName());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+killer.getName());
					}
					
					
				//SINO PROVIENE DE UN JUGADOR SE EVALUA SI TIENE NOMBRE (ES CUSTOM) SINO ES DE UN MOV	
				}else if(EntityHasShooter(damager)) {
					if(EntityHasName(damager)) {
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+damager.getCustomName(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+damager.getCustomName());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+damager.getCustomName());

						
					}else {
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+damager.getType(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+damager.getType());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+damager.getType());

					}
				}else {
					if(ProjectileHasName(shoot)) {
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+shoot.getCustomName(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por :"+ChatColor.YELLOW+shoot.getCustomName());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+shoot.getCustomName());

						
					}else {
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+shoot.getType(), 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+shoot.getType());
						gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+shoot.getType());

					}
				}
				
			}else {
				if(EntityHasName(e)) {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+e.getCustomName(), 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por: "+ChatColor.YELLOW+e.getCustomName());
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+e.getType());

				}else {
					player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+e.getType(), 40, 80, 40);
					player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+e.getType());
					gmc.sendMessageToUsersOfSameMapLessPlayer(player, ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+e.getType());

				}
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
							
							
						}
						return;
					}
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
	
	

}
