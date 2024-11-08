package me.nao.timers;




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
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Item;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.RayTraceResult;

import me.nao.enums.GameStatus;
import me.nao.enums.StopMotivo;
import me.nao.general.info.GameAdventure;
import me.nao.general.info.GameConditions;
import me.nao.general.info.GameInfo;
import me.nao.main.game.Minegame;
import me.nao.manager.GameIntoMap;
//import net.md_5.bungee.api.ChatMessageType;
//import net.md_5.bungee.api.chat.TextComponent;
import me.nao.scoreboard.MgScore;



public class ResistenceTemp {

	
	  
	
	   private Minegame plugin;
	   int taskID;
	  
	
	  
	 public ResistenceTemp(Minegame plugin) {
		 
		  this.plugin = plugin;
		  
		  
	 }
	
	
	public void Inicio(String name) {
		  
		FileConfiguration config = plugin.getConfig();
		GameConditions gc = new GameConditions(plugin);
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			
			
			GameInfo ms = plugin.getGameInfoPoo().get(name);
			GameAdventure ga = (GameAdventure) ms;
			 MgScore sco = new MgScore(plugin);
			
			int startm = config.getInt("CountDownPreLobby");
			int end = 10;
		
			String timer[] = ms.getTimeMg().split(",");
			int hora = Integer.valueOf(timer[0]);
			int minuto = Integer.valueOf(timer[1]);
			int segundo = Integer.valueOf(timer[2]);
		
		    int  segundo2 = 0;
			int  minuto2 = 0 ;
			int  hora2 = 0 ;
			int anuncios = 0;																						//APARENTEMENTE ESTOS DOS SON BarFlag. unas flags
		
			
		   
		    //Transformar minutos - hora a segundos
		 	double minuto3 = minuto * 60;
		 	double hora3 = hora * 3600;
		 	double segundo3 = segundo;
		 	//todos los seg
		 	double total = hora3 + minuto3 + segundo3;
		 	//bossbar al maximo
		    double pro = 1.0;
		 	//calculo para hacer uba reduccion
		 	double time = 1.0 / total;
		 	
		    BossBar boss = ms.getBoss();
		    
		    
		@Override
		public void run() {
		
				
			List<Player> joins = gc.ConvertStringToPlayer(ms.getParticipants());
			List<Player> alive = gc.ConvertStringToPlayer(ga.getAlivePlayers());
			List<Player> dead = gc.ConvertStringToPlayer(ga.getDeadPlayers());
			List<Player> spect = gc.ConvertStringToPlayer(ga.getSpectators());
			GameStatus part = ms.getGameStatus();
			StopMotivo motivo = ms.getMotivo();

			//SI TODOS SE SALEN MIENTRAS COMIENZA
			if(joins.size() == 0 && part == GameStatus.COMENZANDO || part == GameStatus.JUGANDO || part == GameStatus.PAUSE) {
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
					gc.StartGameActions(name);
				}
				for(Player players : joins) {
					//String mision = plugin.getPlayerInfoPoo().get(target).getMisionName();
					
					
						if(startm <= 5) {
			       	  		if(startm != 0) {
			       	  			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
			       	  			players.sendTitle(""+ChatColor.RED+ChatColor.BOLD+String.valueOf(startm),""+ChatColor.YELLOW+ChatColor.BOLD+"La partida empieza en ", 20, 20, 20);
			       	  		}
			    			
				    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
			    		}else {
			    			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
			    			players.sendTitle("",ChatColor.YELLOW+"La partida empieza en "+ChatColor.RED+startm, 20, 20, 20);

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
			else if(part == GameStatus.JUGANDO) {
				
				boss.setVisible(true);
	            boss.setProgress(pro);
	      	    //System.out.println("Boss data"+pro);
			  if(hora <= 0 && minuto >= 10) {
				  boss.setColor(BarColor.GREEN);
			  }

			  if(hora <= 0 && minuto <= 9) {
				  boss.setColor(BarColor.YELLOW);						  
			  }
			  
	          if(hora <= 0 && minuto <= 1 ) {
				  boss.setColor(BarColor.RED);
			  } 			
	          pro = pro - time;

	      //  players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_RED+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " ));
		//        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""+ChatColor.GREEN+ChatColor.BOLD+"KILLS:"+ChatColor.DARK_RED+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " ));


				//HORA> MINUTO
				//MINUTO > SEGUND
				
				//CRONOMETRO
				 if (segundo2 != 60 ){
						 segundo2++; 
					}
				 if (minuto2 != 60 && segundo2 == 60) {
						segundo2 = 0;
						minuto2 ++;
					}
				 if (hora2 != 60 && minuto2 == 60) {
						minuto2 = 0;
						hora2 ++;
					}
							 //60:30:00
				 //String fs = String.format("%02d", numef);
				 String seg = String.format("%02d", segundo2);
				 String min = String.format("%02d", minuto2);
				 String hor = String.format("%02d", hora2);
				 
				 plugin.getArenaCronometer().put(name,hor+":"+min+":"+seg);
				
				 
				
				//TIMER
				 if (segundo != 0){
					 segundo--; 
				 }
				 if (minuto != 0 && segundo == 0) {
						segundo = 60;
						minuto --;
				}
				 if(hora != 0 && minuto == 0) {
						minuto = 60;
						hora --;
				}
				 	
				  boss.setTitle(""+ChatColor.DARK_RED+ChatColor.BOLD+"Resiste:"+ChatColor.AQUA+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " );
				
				 //boss = Bukkit.createBossBar("Hello",BarColor.GREEN, BarStyle.SOLID,  null ,null);
//					List<String> alive1 = plugin.getAlive().get(name);
//					List<String> arrive1 = plugin.getArrive().get(name);
					//List<String> ends = ym.getStringList("End.Commands");
				 
				  
				  if(segundo == 0 && minuto == 0 && hora == 0) {
					  GameIntoMap cig = new GameIntoMap(plugin);
						 for(Player players : alive) {
							 cig.ObjetivesInGame(players, name);
						 }
						
						 gc.TopConsole(name);
						 gc.Top(name);
						 Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Jugadores que ganaron Resistencia "+ChatColor.GREEN+ConvertPlayerToString(alive)+ChatColor.RED+" mapa: "+ChatColor.GREEN+name);
						 boss.setProgress(1.0);
				  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"FIN...");
				  		 ms.setGameStatus(GameStatus.TERMINANDO);
				  		 
					}else if(motivo == StopMotivo.WIN || motivo == StopMotivo.LOSE || motivo == StopMotivo.ERROR || motivo == StopMotivo.FORCE) {
						 gc.TopConsole(name);
						 gc.Top(name);
						 boss.setProgress(1.0);
				  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"FIN..");
				  		 ms.setGameStatus(GameStatus.TERMINANDO);
				  		
					}else if(dead.size() == joins.size()) {
						 for(Player players : joins) {
							 players.sendMessage(ChatColor.RED+"Todos los Jugadores fueron eliminados F ...");
						 }
						 gc.TopConsole(name);
						 gc.Top(name);
						 boss.setProgress(1.0);
				  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"( FIN. )");
						 Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Todos perdieron Resistencia "+ChatColor.GREEN+ConvertPlayerToString(joins)+ChatColor.RED+" mapa: "+ChatColor.GREEN+name+"\n");
						 ms.setGameStatus(GameStatus.TERMINANDO);
						 // Bukkit.getScheduler().cancelTask(taskID);
					}
					
				  for(Player players : joins) {
					  if(players != null){
					
			    		  ShootEntityToPlayer(players);
						  RemoveEntitysInBarrier(players);
						  removeTrapArrows(players);
						  getGeneratorsOfOres(players);
						  getNearbyBlocks3(players);
						  mobLocation(players);
						  
						  
						  if(anuncios >= 0 && anuncios <= 5) {
							  sco.ShowObjetives(players,1);
						  }else if(anuncios >= 6 && anuncios <= 11) {
							  sco.ShowObjetives(players,2);
						  }else if(anuncios >= 12 && anuncios <= 17) {
							  sco.ShowObjetives(players,0);
						  }else if(anuncios >= 18 && anuncios <= 23) {
							  sco.ShowObjetives(players,3);
						  }
						  
						  String s1 = String.valueOf(segundo);
						  if(s1.endsWith("0")) {
							  getNearbyBlocks(players);
						  }
					 }}	
				  
					  if(anuncios == 25) {
							anuncios = 0;
						 }
						
						anuncios ++;
				  
					gc.HasTimePath("Time-"+hora+"-"+minuto+"-"+segundo, name);	
				
				//colocar terminando
			}
			//TODO TERMINANDO
			else if(part == GameStatus.TERMINANDO) {
				 
				
					   	if(end == 0) {
						   	
				   			
					   		
					   		System.out.println("ANTES MISION MISION RUN : "+ms.ShowGame());
			    			
		//			   		
		//						//RemoveArmorStandsAndItemsInMap(target);
								gc.mgEndTheGame(name);
								gc.EndGameActions(name);
				    	
				    	
					    	 //ms.setEstadopartida(EstadoPartida.ESPERANDO);
				   		
			    		Bukkit.getScheduler().cancelTask(taskID);	
			    		System.out.println("SE DETUVO ;)");
				     }
				
					for(Player players : joins) {
						if(end <= 5) {
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
						
						RemoveEntitysAfterGame(players);
				 
					}
					
					for(Player players : spect) {
					
						if(end <= 5) {
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
						RemoveEntitysAfterGame(players);
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
	
	 public void RemoveEntitysAfterGame(Player player) {
	    	
	    	List<Entity> l = getNearbyEntites(player.getLocation(),150);
	    	
	    	if(!l.isEmpty()) {
	    		for(Entity e : l) {
	        		
	        		if(e.getType() != EntityType.PLAYER && e.getType() != EntityType.PAINTING) {
	        			if(e.getType() == EntityType.DROPPED_ITEM) {
	        				Item ite = (Item) e;
	        				if(ite.getOwner() != null) continue;
	        					e.remove();
	        			    }else {
	        			    	e.remove();
	        			    }
	        		}
	        	}
	    	}
	    
	    	
	    }

	//TODO NERBYBLOCK
	public void getNearbyBlocks(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Mob-Spawn-Range") ;
		
		if (r.getType() == (Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						if(a.getType() == Material.AIR) continue;
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode() == GameMode.ADVENTURE ) {
								
								
								
								if(a.getType() == Material.GREEN_CONCRETE && b.getType() == Material.BEDROCK) {
								
									zombi(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.BLUE_CONCRETE && b.getType() == Material.BEDROCK) {
									
									drowned(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.ORANGE_CONCRETE && b.getType() == Material.BEDROCK) {
									
									husk(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.LIGHT_GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
									
									villagerz(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.WHITE_CONCRETE && b.getType() == Material.BEDROCK) {
									
									skeleton(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.BLACK_CONCRETE && b.getType() == Material.BEDROCK) {
									skeletonDark(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
									vindicador(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.RED_CONCRETE && b.getType() == Material.BEDROCK) {
									Pillager(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.PURPLE_CONCRETE && b.getType() == Material.BEDROCK) {
									evoker(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.LIME_CONCRETE && b.getType() == Material.BEDROCK) {
									Creeper(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.MAGENTA_CONCRETE && b.getType() == Material.BEDROCK) {
									Bruja(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
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
	
	
	
	
	//TODO NERBYBLOCK 2
	public void getGeneratorsOfOres(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Item-Spawn-Range") ;
		
		if (r.getType() == Material.AIR) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode() == GameMode.ADVENTURE ) {
							
							
							
							
							if(a.getType() == Material.NETHERITE_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_INGOT));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_BLOCK,RandomBetweenValue(1, 100)));
								
							}
							
							if(a.getType() == Material.DIAMOND_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
							     a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND));
							     a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND_BLOCK,RandomBetweenValue(1, 100)));
							}
							
							if(a.getType() == Material.EMERALD_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD_BLOCK,RandomBetweenValue(1, 100)));
							}
							
							if(a.getType() == Material.IRON_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_INGOT));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_BLOCK,RandomBetweenValue(1, 100)));
							}
							
							if(a.getType() == Material.GOLD_BLOCK && b.getType() == Material.BEDROCK) {
								if(player.getLocation().distance(a.getLocation()) <= rango) {
									player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_INGOT));
								 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_BLOCK,RandomBetweenValue(1, 100)));
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
	
	
	public int RandomBetweenValue(int min ,int max) {
		
		try {
			Random r = new Random();
			int value = r.nextInt(max-min+1) + min;
			
			if(value == 3 || value == 2 || value == 1) {
				return value;
			}else if(value >= 6){
				return 0;
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
	
	//TODO Creeper
    public void Blaze(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.BLAZE);
		Blaze s = (Blaze) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	
	//TODO Creeper
    public void Creeper(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.CREEPER);
		Creeper s = (Creeper) entidad;
		s.setExplosionRadius(10);
		s.setMaxFuseTicks(3);
		s.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"SUICIDA");
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }

	//TODO evoker
    public void villagerz(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE_VILLAGER);
		ZombieVillager s = (ZombieVillager) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void drowned(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.DROWNED);
		Drowned s = (Drowned) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void husk(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.HUSK);
		Husk s = (Husk) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void evoker(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.EVOKER);
		Evoker s = (Evoker) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	
	//TODO pillager
    public void Pillager(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.PILLAGER);
		Pillager s = (Pillager) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		ItemStack b = new ItemStack(Material.CROSSBOW,1);
		ItemMeta meta = b.getItemMeta();
		meta.addEnchant(Enchantment.QUICK_CHARGE, 5, true);
		meta.addEnchant(Enchantment.MULTISHOT, 1, true);
		b.setItemMeta(meta);
		
		s.getEquipment().setItemInMainHand(b);
		
		
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
    
	  public void mobLocation(Player player) {
		  	GameConditions gc = new GameConditions(plugin);
	    	List<Entity> entities = getNearbyEntities(player.getLocation(), 50);
	    	for(int i = 0;i< entities.size();i++) {
	    		if(!(entities.get(i) instanceof LivingEntity))continue;
	    		
	    		LivingEntity lve = (LivingEntity) entities.get(i);
	    		Block block = lve.getLocation().getBlock();
	    		Block r = block.getRelative(0, 0, 0);
	    		
	    		if(lve.getType() == EntityType.PLAYER) continue;
	    		
	    		gc.blockPotion(lve);
	    		if(r.getType() == Material.OAK_PRESSURE_PLATE) {
	    			lve.setVelocity(lve.getLocation().getDirection().multiply(3).setY(2));
	    		}
	    		if(r.getType() == Material.STONE_PRESSURE_PLATE) {
	    			lve.setVelocity(lve.getLocation().getDirection().multiply(3).setY(1));
	    		}
	    		
	    	}
	    }
	
	//TODO vindicator
    public void vindicador(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.VINDICATOR);
		Vindicator s = (Vindicator) entidad;
		
		Random random = new Random();

		int n = random.nextInt(50);
		
		if(n == 0) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		}
		
		if(n == 5) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
		}
		
		if(n == 10) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
		}
		
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		s.setCanPickupItems(true);
		
		
    }
	
	//TODO SkeletomDARK
    public void skeletonDark(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.WITHER_SKELETON);
		WitherSkeleton s = (WitherSkeleton) entidad;
		s.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		s.setCanPickupItems(true);
		
		
    }
	
	//TODO Skeletom
    public void skeleton(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
		Skeleton s = (Skeleton) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
  //TODO Bruja
    public void Bruja(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.WITCH);
		Witch s = (Witch) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO ZOMBI
    public void zombi(World world , int x , int y , int z) {
    	
    	
		
		Random random = new Random();
	
		
		int n = random.nextInt(50);
		
		
	
		Location l2 = new Location(world, x, y+2, z); 			
	
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
		
		Zombie zombi = (Zombie) entidad;
		zombi.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		zombi.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(50);
		
		
		PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/4, true ,true,true );
		PotionEffect salto= new PotionEffect(PotionEffectType.JUMP,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );

		
	    zombi.addPotionEffect(rapido);
		zombi.addPotionEffect(salto);
		

		
	     	
		if(n == 0) {
			LivingEntity entidad10 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE_VILLAGER);
			ZombieVillager zv = (ZombieVillager) entidad10;
			zv.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
			
			
			LivingEntity entidad8 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.DROWNED);
			Drowned zombi8 = (Drowned) entidad8;
			
			
			zv.addPotionEffect(salto);
			zv.addPotionEffect(rapido);
			
			
		    zombi8.addPotionEffect(rapido);
		   
			zombi8.addPotionEffect(salto);
			zombi8.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			
			LivingEntity husk1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.HUSK);	
			Husk husk = (Husk) husk1;
			
			
			husk.addPotionEffect(rapido);
		
			husk.addPotionEffect(rapido);
		
			husk.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			
			

		}
		
		if(n == 1) {
		    LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			
				zombi1.addPotionEffect(rapido);
				zombi1.addPotionEffect(salto);
				
				
				
		}else if(n == 2) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(""+ChatColor.DARK_RED+ChatColor.BOLD+"Tank");
			
			
			zombi1.addPotionEffect(rapido);
			
			zombi1.addPotionEffect(salto);
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
			
		}else if(n == 3) {
		
			
				LivingEntity entidad4 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
				Zombie zombi4 = (Zombie) entidad4;

			
			    zombi4.addPotionEffect(rapido);
			    zombi4.addPotionEffect(salto);
				zombi4.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			
				
		}else if(n == 4) {
			
			
  		    
				LivingEntity entidad6 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
				Zombie zombi6 = (Zombie) entidad6;
				
				zombi6.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			    zombi6.addPotionEffect(rapido);
				zombi6.addPotionEffect(salto);
				zombi6.setBaby();
			
				LivingEntity entidad7 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
				Skeleton zombi7 = (Skeleton) entidad7;
				
			    zombi7.addPotionEffect(rapido);
				zombi7.addPotionEffect(salto);
				entidad6.addPassenger(entidad7);
				zombi7.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			
				
				
			
		}else if(n == 5) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Goliath");
			
			
			zombi1.addPotionEffect(rapido);
			
			zombi1.addPotionEffect(salto);
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
			zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
			zombi1.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
			zombi1.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
			zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
			
		}else if(n == 6) {
			
			for(int i = 0 ; i< 5;i++) {
				 LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					Zombie zombi1 = (Zombie) entidad1;
				
					zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					
	  				zombi1.addPotionEffect(rapido);
	  				zombi1.addPotionEffect(salto);
			}
		   
				
				
				
		}
		else if(n == 7) {
			 LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
				Zombie zombi1 = (Zombie) entidad1;
			
				zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
				
  				zombi1.addPotionEffect(rapido);
  				zombi1.addPotionEffect(salto);
			for(int i = 0 ; i< 10;i++) {
				 LivingEntity entidad2 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					Zombie zombi2 = (Zombie) entidad2;
				
					zombi2.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					
	  				zombi2.addPotionEffect(rapido);
	  				zombi2.addPotionEffect(salto);
			}
		   
				
				
				
		}else if(n == 8) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Armado");
			
			
			zombi1.addPotionEffect(rapido);
			
			zombi1.addPotionEffect(salto);
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
			zombi1.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
			zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
			
		}else if(n == 9) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Artillero");
			
			
			zombi1.addPotionEffect(rapido);
			
			zombi1.addPotionEffect(salto);
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
			zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			zombi1.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			zombi1.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
			zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.CROSSBOW));
			
		}else if(n == 10) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Super Suicida");
			
			
			zombi1.addPotionEffect(rapido);
			
			zombi1.addPotionEffect(salto);
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
			zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.TNT));
			
			
			LivingEntity ce = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.CREEPER);
			Creeper s = (Creeper) ce;
			s.setExplosionRadius(10);
			s.setMaxFuseTicks(1);
			s.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Zombi Super Suicida");
			s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.addPassenger(s);
		}else if(n == 10) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(""+ChatColor.WHITE+ChatColor.BOLD+"Zombi Super Suicida");
			zombi1.addPotionEffect(rapido);
			zombi1.addPotionEffect(salto);
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			zombi1.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
			zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.TNT));
			
			
			LivingEntity ce = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.CREEPER);
			Creeper s = (Creeper) ce;
			s.setExplosionRadius(10);
			s.setMaxFuseTicks(1);
			s.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Zombi Super Suicida");
			s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.addPassenger(s);
		}else if(n == 11) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
			
			
			zombi1.addPotionEffect(rapido);
			zombi1.addPotionEffect(salto);
		
			zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			zombi1.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			zombi1.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			zombi1.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));
			
			
			LivingEntity ce = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.BLAZE);
			Blaze s = (Blaze) ce;
			s.setCustomName(""+ChatColor.YELLOW+ChatColor.BOLD+"LANZALLAMAS");
			s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
			zombi1.addPassenger(s);
		}else if(n == 12) {
			
			for(int i = 0 ; i< 15;i++) {
				 LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					Zombie zombi1 = (Zombie) entidad1;
				
					zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					zombi1.setBaby();
	  				zombi1.addPotionEffect(rapido);
	  				zombi1.addPotionEffect(salto);
			}
		   
				
				
				
		}else if(n == 13) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(ChatColor.RED+"Speed");
			
			
			zombi1.addPotionEffect(rapido);
			zombi1.addPotionEffect(salto);
	
		}else if(n == 14) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(ChatColor.RED+"Summon");
			
			
			zombi1.addPotionEffect(rapido);
			zombi1.addPotionEffect(salto);
		
	
		}else if(n == 15) {
			LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
			Zombie zombi1 = (Zombie) entidad1;
			zombi1.setCustomName(ChatColor.RED+"Screamer");
			
			
			zombi1.addPotionEffect(rapido);
			zombi1.addPotionEffect(salto);
		
		
			
		}
		
		/*
		 else if(n == 7) {
			
			
  		    
			 WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
				CustomZombi giant = new CustomZombi(player.getLocation());
				
				world.addEntity(giant);
			
		}	
		*/
		
		
			

		return;
}
    
    
    public void RemoveEntitysInBarrier(Player player) {
    	
    	List<Entity> l = getNearbyEntites(player.getLocation(),150);
    	
    	if(!l.isEmpty()) {
    		for(Entity e : l) {
    	    	
        		Block b = e.getLocation().getBlock();
        		Block block = b.getRelative(0,-1,0);
        		
        		if(e.getType() != EntityType.PLAYER && block.getType() == Material.BARRIER) {
        			if(e instanceof Mob) {
        				((Mob) e).setHealth(0);
        			}else {
        				e.remove();
        			}
        		}
        	}
    	}
    
    	
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
    
    public void removeTrapArrows(Player player) {
    	List<Entity> entities = getNearbyEntites(player.getLocation(), 50);
    	for(int i = 0;i< entities.size();i++) {
    		if(entities.get(i).getType() == EntityType.ARROW) {
    			Arrow f = (Arrow) entities.get(i);
    			if(f.isOnGround() && f.getCustomName() != null && f.getCustomName().equals("Flecha Trampa")) {
    				
    				f.remove();
    			}
    			Block block = f.getLocation().getBlock();
    			Block r = block.getRelative(0, 0, 0);
    			int rango = 2;
    			for (int x = -rango; x < rango+1; x++) {
					for (int y = -rango; y < rango+1; y++) {
						for (int z = -rango; z < rango+1; z++) {
	
							Block a = r.getRelative(x, y, z);
	
							// setea bloques en esos puntos
							
	
								if(a.getType() == Material.BARRIER) {
									f.remove();
								}
								
						
						}}}
    			
    			
    		}
    		
    		if(entities.get(i).getType() == EntityType.SNOWMAN || entities.get(i).getType() == EntityType.IRON_GOLEM) {
    			   Block block = entities.get(i).getLocation().getBlock();
    			   Block r = block.getRelative(0, -1, 0);
    			   if(r.getType() == Material.BARRIER) {
    				   entities.get(i).remove();
					}
    		}
    	}
    	
    	
    }
    
//    public void DeleteAllArmor(Player player){
//    	if(plugin.PlayerisArena(player));
//    	for(Entity e : player.getWorld().getEntities()) {
//    		String name = ChatColor.stripColor(e.getCustomName());
//    		if(name != null) {
//    			if(name.contains("RECIPIENTE DE ALMAS") || name.contains("FALTAN") || name.contains("aqui.")) {
//	    			e.remove();
//	    		}
//    		}
//    	}
//    		
//    }s
    
public void ShootEntityToPlayer(Player player) {
    	
    	List<Entity> l = getNearbyEntites(player.getLocation(),150);
    	
    	if(!l.isEmpty()) {
    		for(Entity e : l) {
    			if(e instanceof Zombie) {
    				Zombie z = (Zombie) e;
    				
    				if(z.getTarget() != null) {
    					if(z.getTarget() instanceof Player) {
    						Player pla = (Player) z.getTarget();
    						if(!pla.getName().equals(player.getName())) {
    							return;
    						}
    					}
    				}
    				
    				
    				RayTraceResult ra = z.getWorld().rayTraceEntities(z.getEyeLocation().add(z.getLocation().getDirection()),z.getLocation().getDirection() , 100.0D);
              		if(ra != null && ra.getHitEntity() != null) {
              			
              			if(z.getCustomName() != null && ChatColor.stripColor(z.getCustomName()).equals("Zombi Artillero")) {
              				if(ra.getHitEntity().getType() == EntityType.PLAYER) {
                  				
              					Location loc = z.getLocation();
            					Location loc2 = z.getLocation();
              					
              					Entity h1 = z.getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
        						Entity h2 = z.getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.ARROW);
        						h1.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
        						h2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
        						Arrow aw = (Arrow) h1;
        						Arrow aw2 = (Arrow) h2;
        						aw.setCritical(true);
        						aw.setKnockbackStrength(2);
        						aw.setFireTicks(1200);
        						aw.setShooter(z);
        						aw2.setCritical(true);
        						aw2.setKnockbackStrength(2);
        						aw2.setFireTicks(1200);
        						aw2.setShooter(z);
                  			}
              			}
              			
              		}
              		
    			}
    			
    			
    		}
    	}
    
    	
    }
    
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
