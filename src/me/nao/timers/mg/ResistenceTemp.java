package me.nao.timers.mg;




import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.StopMotive;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.GameTime;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.manager.mg.GameIntoMap;
import me.nao.mobs.mg.MobsActions;
import me.nao.scoreboard.mg.MgScore;


@SuppressWarnings("deprecation")
public class ResistenceTemp {

	
	  
	
	   private Minegame plugin;
	   int taskID;
	  
	
	  
	 public ResistenceTemp(Minegame plugin) {
		 
		  this.plugin = plugin;
		  
		  
	 }
	
	
	public void Inicio(String name) {
		  
		GameConditions gc = new GameConditions(plugin);
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		MobsActions ma = new MobsActions(plugin);
		
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			
			
			GameInfo ms = plugin.getGameInfoPoo().get(name);
			GameAdventure ga = (GameAdventure) ms;
			 MgScore sco = new MgScore(plugin);
			
			 int startm = ms.getCountDownStart();
			 int end = 10;
		
//			String timer[] = ms.getTimeMg().split(",");
//			int hora = Integer.valueOf(timer[0]);
//			int minuto = Integer.valueOf(timer[1]);
//			int segundo = Integer.valueOf(timer[2]);
		
//		    int  segundo2 = 0;
//			int  minuto2 = 0 ;
//			int  hora2 = 0 ;
			int anuncios = 0;																						//APARENTEMENTE ESTOS DOS SON BarFlag. unas flags
		
			GameTime gt = ms.getGameTime();
		   
//		    //Transformar minutos - hora a segundos
//		 	double minuto3 = minuto * 60;
//		 	double hora3 = hora * 3600;
//		 	double segundo3 = segundo;
//		 	//todos los seg
//		 	double total = hora3 + minuto3 + segundo3;
//		 	//bossbar al maximo
//		    double pro = 1.0;
//		 	//calculo para hacer uba reduccion
//		 	double time = 1.0 / total;
		 	
		    BossBar boss = ms.getBossbar();
		    
		    
		@Override
		public void run() {
		
				
			List<Player> joins = gc.ConvertStringToPlayer(ms.getParticipants());
			List<Player> alive = gc.ConvertStringToPlayer(ga.getAlivePlayers());
			//List<Player> dead = gc.ConvertStringToPlayer(ga.getDeadPlayers());
			List<Player> spect = gc.ConvertStringToPlayer(ga.getSpectators());
			GameStatus part = ms.getGameStatus();
			StopMotive motivo = ms.getStopMotive();

			//SI TODOS SE SALEN MIENTRAS COMIENZA
			if(joins.isEmpty() && part != GameStatus.TERMINANDO) {
				boss.setProgress(1.0);
		  		boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"FIN");
		  		boss.setVisible(false);
		  		ms.setGameStatus(GameStatus.TERMINANDO);
   			    Bukkit.getConsoleSender().sendMessage("No hay jugadores terminando en "+end+"s");
   		
		     }
			//TODO EMPEZANDO
			
		
			
			if(part == GameStatus.COMENZANDO) {
				
				//GameModeConditions gm = new GameModeConditions(plugin);
				if(startm == 0) {
					gc.startGameActions(name);
				}
				for(Player players : joins) {
					//String mision = plugin.getPlayerInfoPoo().get(target).getMisionName();
					
					
						if(startm <= 5) {
			       	  		if(startm != 0) {
			       	  			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
			       	  			players.sendTitle(""+ChatColor.RED+ChatColor.BOLD+String.valueOf(startm),""+ChatColor.YELLOW+ChatColor.BOLD+"La partida empieza en ", 0, 20, 0);
			       	  		}
			    			
				    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
			    		}else {
			    			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
			    			players.sendTitle("",ChatColor.YELLOW+"La partida empieza en "+ChatColor.RED+startm, 0, 20, 0);

			    			//target.sendMessage(ChatColor.YELLOW+"La partida empieza en "+ChatColor.RED+startm);
			    		}
						
						
						if(startm == 0) {
				    		 // Bukkit.getScheduler().cancelTask(taskID);	
				    		 // DeleteAllArmor(target);
				    		
				    		  if(part == GameStatus.COMENZANDO) {
				    		  
				    			ms.setGameStatus(GameStatus.JUGANDO);
				    			
							    gc.TptoSpawnMap(players, name);
				    		
				    		  	}
						  
					     }
				}
				
			      startm --;
			}//TODO EN PROGRESO
			else if(part == GameStatus.JUGANDO || part == GameStatus.PAUSE|| part == GameStatus.FREEZE) {

			    	

				if(gt.getTimersecond() <= 0 && gt.getTimerminute() <= 0 && gt.getTimerhour() <= 0) {

					  	 //String result = alive.isEmpty() ? alive.toString().replace("[","").replace("]","") :"";
					  	 
					     GameIntoMap cig = new GameIntoMap(plugin);
						 for(Player players : alive) {
							 players.sendMessage(ChatColor.GREEN+"Todos los jugadores con vida han llegado a la Meta. "+ChatColor.GOLD+"\nSobrevivieron: "+ChatColor.GREEN+ga.getAlivePlayers().toString().replace("[","").replace("]","")+".\n");
							 cig.ObjetivesInGame(players, name);

						 }
						
						 boss.setProgress(1.0);
				  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"FIN...");
				  		 ms.setGameStatus(GameStatus.TERMINANDO);
				  		
					}else if(motivo == StopMotive.WIN || motivo == StopMotive.LOSE || motivo == StopMotive.ERROR || motivo == StopMotive.FORCE) {

						 boss.setProgress(1.0);
				  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"FIN..");
				  		 ms.setGameStatus(GameStatus.TERMINANDO);
				  		
				  		
					}else if(alive.isEmpty()) {
						 for(Player players : joins) {
							 players.sendMessage(ChatColor.RED+"Todos los Jugadores fueron eliminados F ...\n");
						 }

						 boss.setProgress(1.0);
				  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"( FIN. )");
						 Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Todos perdieron Resistencia "+ChatColor.GREEN+ConvertPlayerToString(joins)+ChatColor.RED+" mapa: "+ChatColor.GREEN+name+"\n");
						 ms.setGameStatus(GameStatus.TERMINANDO);
						
						 // Bukkit.getScheduler().cancelTask(taskID);
					}
					
				  for(Player players : joins) {
					  if(players != null){
					
						  ma.shootEntityToPlayer(players);
			    		  ma.removeEntitysInBarrier(players);
						  ma.removeTrapEntitys(players);
						 // getGeneratorsOfOres(players);
						 // getNearbyBlocks3(players);
						  ma. mobLocation(players);
						  ma.spawnOres(players);
						  ma.moblandmine(players);
						  
						  if(anuncios >= 0 && anuncios <= 5) {
							  sco.ShowObjetives(players,1);
						  }else if(anuncios >= 6 && anuncios <= 11) {
							  sco.ShowObjetives(players,2);
						  }else if(anuncios >= 12 && anuncios <= 17) {
							  sco.ShowObjetives(players,0);
						  }else if(anuncios >= 18 && anuncios <= 23) {
							  sco.ShowObjetives(players,3);
						  }
						  
						  String s1 = String.valueOf(gt.getTimersecond());
						  if(s1.endsWith("0")) {
							 // getNearbyBlocks(players);
							  ma.spawnMobs(players);
						  }
					 }}	
				  
				    gc.HasTimePath("Time-"+gt.getTimerhour()+"-"+gt.getTimerminute()+"-"+gt.getTimersecond(), name);
					gt.timerRunMg();
				  
					  if(anuncios == 25) {
							anuncios = 0;
						 }
						
						anuncios ++;
				  
				
				//colocar terminando
			}
			//TODO TERMINANDO
			else if(part == GameStatus.TERMINANDO) {
				 		
						if(end == 10) {
							 boss.setProgress(1.0);
					  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"| FIN |");
							 gc.topConsole(name);
							 gc.topGame(name);
							 gc.sendResultsOfGame(ga,gt.getGameCronometForResult(),gt.getGameTimerForResult());

						}
				
					   	if(end == 0) {
					   			System.out.println("ANTES MISION MISION RUN : "+ms.ShowGame());
			    	
								gc.mgEndTheGame(name);
								gc.endGameActions(name);
				   
				    		Bukkit.getScheduler().cancelTask(taskID);	
				    		System.out.println("SE DETUVO ;)");
					   	}
				
						for(Player players : joins) {
							if(end != 0) {
							if(end <= 5 && end >= 1) {
			 	       		  //  RemoveArmorStandsAndItemsInMap(target);
								players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
								players.sendTitle(""+ChatColor.GOLD+ChatColor.BOLD+String.valueOf(end),""+ChatColor.GOLD+ChatColor.BOLD+"La partida termina en ", 20, 20, 20);
						    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
					    		}else {
					    			//RemoveArmorStandsAndItemsInMap(target);
					    			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
					    			players.sendTitle("",ChatColor.GREEN+"La partida termina en "+ChatColor.DARK_PURPLE+end, 20, 20, 20);
	
					    			//target.sendMessage(ChatColor.GREEN+"La partida termina en "+ChatColor.DARK_PURPLE+end);
					    		}
							
								ma.removeEntitysAfterGame(players);
							}	
					 
						}
					
						for(Player players : spect) {
							if(end != 0) {
							if(end <= 5 && end >= 1) {
			 	       		  //  RemoveArmorStandsAndItemsInMap(target);
								players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
								players.sendTitle(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+String.valueOf(end),""+ChatColor.AQUA+ChatColor.BOLD+"La partida termina en ", 20, 20, 20);
						    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
					    		}else {
					    			//RemoveArmorStandsAndItemsInMap(target);
					    			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
					    			players.sendTitle("",ChatColor.GREEN+"La partida termina en "+ChatColor.DARK_PURPLE+end, 20, 20, 20);
	
					    			//target.sendMessage(ChatColor.GREEN+"La partida termina en "+ChatColor.DARK_PURPLE+end);
					    		}
							ma.removeEntitysAfterGame(players);
							
							}
							
						}
					
				
					
				
				end--;
			
				//colocar esperando 
			}
			
			

		

	
		  
			
		  
		 //  System.out.println("Cuenta atras : "+hora+"h "+minuto+"m "+segundo+"s " );
			
			
			}
		},0L,20);
		
	  }
	

	
	
	 public List<String> ConvertPlayerToString(List<Player> pl){
    	 List<String> l = new ArrayList<>();
    	 
    	 for(Player player : pl) {
    		 l.add(player.getName());
    	 }
    	return l;
    }
	

	
	//TODO NERBYBLOCK 2
	public void getGeneratorsOfOres(Player player) {
		
		if(player.getGameMode() != GameMode.ADVENTURE) return;
		
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String map = pl.getMapName();
		GameInfo gi = plugin.getGameInfoPoo().get(map);
		int rango = gi.getSpawnItemRange();
		
		if (r.getType() == Material.AIR) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						
							
							
							
							
							if(a.getType() == Material.NETHERITE_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_INGOT));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_BLOCK,RandomBetweenValue(1, 100)));
								
							}
							
							if(a.getType() == Material.DIAMOND_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
							     a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND));
							     a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND_BLOCK,RandomBetweenValue(1, 100)));
							}
							
							if(a.getType() == Material.EMERALD_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD_BLOCK,RandomBetweenValue(1, 100)));
							}
							
							if(a.getType() == Material.IRON_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_INGOT));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_BLOCK,RandomBetweenValue(1, 100)));
							}
							
							if(a.getType() == Material.GOLD_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_INGOT));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_BLOCK,RandomBetweenValue(1, 100)));
							}
								//player.sendMessage("Hay un bloque de diamante en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
							

					}
					;
				}
				;
			}
			;


		}
	}	
	
	
	public int RandomBetweenValue(int min ,int max) {
		
		try {
			Random r = new Random();
			int value = r.nextInt(max-min+1) + min;
			
			if(value == 3 || value == 2 || value == 1) {
				return value;
			}else if(value >= 6){
				return 1;
			}
		}catch(IllegalArgumentException e) {
			 Logger logger = Logger.getLogger(AdventureTemp.class.getName());
	       	 logger.log(Level.WARNING,"Coloca primero un valor menor y despues un mayor. "+max+"/"+min);
		}
		
		return -1;
		
	}
	
	//TODO SPAWN GENERATOR 3
	public void getNearbyBlocks3(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Item-Spawn-Range") ;
		
		if (r.getType() == (Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode() == GameMode.ADVENTURE) {
							
						
							if(a.getType() == Material.END_PORTAL_FRAME && b.getType() == Material.BEDROCK) {
								DetectChestAndGenerator(a,player);
							}
			
								//player.sendMessage("Hay un bloque de diamante en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
						}

						

					

					}
					;
				}
				;
			}
			;


		}
	}
	
	//REVISAR ESTO PENDIENTE DE CODIGO INNECESARIO
	public void DetectChestAndGenerator(Block b,Player player) {
		//FileConfiguration config = plugin.getConfig();
		
		FileConfiguration config = plugin.getConfig();
		int rango = config.getInt("Item-Spawn-Range") ;
	//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		
		for (int x = -rango; x < rango+1; x++) {
			for (int y = -rango; y < rango+1; y++) {
				for (int z = -rango; z < rango+1; z++) {
					
					Block a = r.getRelative(x, y, z);
					if(a.getType() == Material.CHEST) {
						
						  Chest chest = (Chest) a.getState();
						  if(chest.getCustomName() != null) {
							  if(chest.getCustomName().contains("GENERATOR")) {
								  if(player.getLocation().distance(a.getLocation()) <= rango) {
										player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									}
								  if(!chest.getInventory().isEmpty()) {
										for (ItemStack itemStack : chest.getInventory().getContents()) {
											if(itemStack == null) continue;
												b.getWorld().dropItem(b.getLocation().add(0.5,1,0.5), itemStack);
					   
							              }
								  }
							  
							  }
				          }
					
						//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
					}
				};};};
	
								
	
		
				
			//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

		//}
	}
	

    
	public List<Entity> getNearbyEntities(Location l , int size){
			
			List<Entity> entities = new ArrayList<Entity>();
			for(Entity e : l.getWorld().getEntities()) {
			
				if(l.distance(e.getLocation()) <= size) {
					if(e.getType() == EntityType.PLAYER) continue;
					entities.add(e);
				}
			}
			return entities;
			
			
		}

    
    
//    public void RemoveArmorStandsAndItemsInMap(Player players) {
//	    FileConfiguration config = plugin.getConfig();
//		int rango = config.getInt("Remove-Armor-Stands");
//		 
//		if(plugin.PlayerisArena(players)) {
//			for(Entity e1 : getNearbyEntites(players.getLocation(),rango)) {
//				
//				if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
//		    		String namea = ChatColor.stripColor(e1.getCustomName());
//		    		if(namea.contains("VIDA") ||namea.contains("NEXO") || namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
//		    			e1.remove();
//		    		}
//						//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
//				}
//			
//				
//				if(e1.getType() == EntityType.DROPPED_ITEM) {
//					e1.remove();
//				}
//				
//				
//				if(e1.getType() != EntityType.PLAYER && e1.getType() != EntityType.ARMOR_STAND && e1.getType() != EntityType.FIREWORK) {
//					e1.remove();
//				}
//				
//			}
//
//		}
//		
//		List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getDeadmob().entrySet());
//			for (Map.Entry<Player,String> datos : list) {
//				if(!list.isEmpty()) {
//					String[] coords = datos.getValue().split("/");
//			    String world = coords[0];
//			    Double x = Double.valueOf(coords[1]);
//			    Double y = Double.valueOf(coords[2]);
//			    Double z = Double.valueOf(coords[3]);
//			    
//			    Location l = new Location(Bukkit.getWorld(world),x,y,z);
//			    
//			    
//			    for(Entity e2 : getNearbyEntites(l,rango)) {
//			    	
//			    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null) {
//			    		String namea = ChatColor.stripColor(e2.getCustomName());
//			    		if(namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
//			    			e2.remove();
//			    		}
//						
//						//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
//					}
//			    	
//			    	if(e2.getType() == EntityType.DROPPED_ITEM) {
//						e2.remove();
//					}
//					
//			    	if(e2.getType() != EntityType.PLAYER && e2.getType() != EntityType.ARMOR_STAND && e2.getType() != EntityType.FIREWORK) {
//						e2.remove();
//					}
//			    	
//				}
//			    plugin.getDeadmob().remove(datos.getKey());
//				}
//				
//		    
//			}
//    }
//    

	public List<Entity> getNearbyEntites(Location l , int size){
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : l.getWorld().getEntities()) {
			
			if(l.distance(e.getLocation()) <= size) {
				if(e.getType() == EntityType.PLAYER) continue;
				entities.add(e);
			}
		}
		return entities;
		
		
	}
	
	  
	
}
