package me.nao.timers.mg;




import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.RayTraceResult;

import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.StopMotive;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameNexo;
import me.nao.main.mg.Minegame;


@SuppressWarnings("deprecation")
public class NexoTemp {

	
	  
	
	   private Minegame plugin;
	   int taskID;
	  
	
	  
	 public NexoTemp(Minegame plugin) {
		 
		  this.plugin = plugin;
		  
		  
	 }
	
	
	public void Inicio(String name) {
		  
		FileConfiguration config = plugin.getConfig();
		GameConditions gc = new GameConditions(plugin);
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		//DestroyNexo dn = new DestroyNexo(plugin);
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			
			
			GameNexo ms = (GameNexo) plugin.getGameInfoPoo().get(name);
			
			int startm = config.getInt("CountDownPreLobby");
			int end = 10;
		
			String timer[] = ms.getTimeMg().split(",");
			int hora = Integer.valueOf(timer[0]);
			int minuto = Integer.valueOf(timer[1]);
			int segundo = Integer.valueOf(timer[2]);
		
		    int  segundo2 = 0;
			int  minuto2 = 0 ;
			int  hora2 = 0 ;
																						//APARENTEMENTE ESTOS DOS SON BarFlag. unas flags
		
			
		   
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
		 	
		    BossBar boss = ms.getBossbar();
		    
		    
		@Override
		public void run() {
			
			List<String> joins = ms.getParticipants();
		
			GameStatus part = ms.getGameStatus();
			StopMotive motivo = ms.getStopMotive();

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
		  		
				for(String players : joins) {
					Player target = Bukkit.getServer().getPlayerExact(players);
					//String mision = plugin.getPlayerInfoPoo().get(target).getMisionName();
					
					
						if(startm <= 5) {
			       	  		if(startm != 0) {
			       	  		target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
			       	  	    target.sendTitle(""+ChatColor.AQUA+ChatColor.BOLD+String.valueOf(startm),""+ChatColor.GREEN+ChatColor.BOLD+"La partida empieza en ", 20, 20, 20);
			       	  		}
			    			
				    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
			    		}else {
			    			target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
			    			target.sendMessage(ChatColor.YELLOW+"La partida empieza en "+ChatColor.RED+startm);
			    		}
						
						
						if(startm == 0) {
				    		 // Bukkit.getScheduler().cancelTask(taskID);	
				    		 // DeleteAllArmor(target);
				    		
				    		  if(part == GameStatus.COMENZANDO) {
				    		  
				    			ms.setGameStatus(GameStatus.JUGANDO);
				    			
				    		
				    			//dn.TpAllTeamsToSpawn(name);
				    			
				    			gc.startGameActions(name);
				    		}
						  
					     }
				}
				
			      startm --;
			}//TODO EN PROGRESO
			else if(part == GameStatus.JUGANDO) {
				
				
				
				boss.setVisible(true);
	            boss.setProgress(pro);
	      	   
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

			   
	          //reduccion de tiempo en boss bar
			    
			//	if(pro <= 0) {
			//		pro = 1.0;
			//	}
			  
			 
			
			

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
				 if (hora != 0 && minuto == 0) {
						minuto = 60;
						hora --;
					}
				 	
				  boss.setTitle(""+ChatColor.DARK_RED+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " );
				
				 //boss = Bukkit.createBossBar("Hello",BarColor.GREEN, BarStyle.SOLID,  null ,null);
//					List<String> alive1 = plugin.getAlive().get(name);
//					List<String> arrive1 = plugin.getArrive().get(name);
					//List<String> ends = ym.getStringList("End.Commands");
					
				  	
				  
					for(String players : joins) {
						Player target = Bukkit.getServer().getPlayerExact(players);
						
				    	if(segundo == 0 && minuto == 0 && hora == 0) {
				    		
//				    		int bluep = ms.getBlueLifeNexo();
//				    		int redp = ms.getRedLifeNexo();
//				    		
//				    		if(bluep == redp) {
//								  target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Tiempo Agotado",""+ChatColor.YELLOW+ChatColor.BOLD+"Empate entre ambos Equipos", 20, 20, 20);
//	
//				    		}else if(bluep > redp) {
//								  target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Tiempo Agotado",""+ChatColor.RED+ChatColor.BOLD+"Equipo Azul Gana", 20, 20, 20);
//
//				    		}else {
//								  target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Tiempo Agotado",""+ChatColor.RED+ChatColor.BOLD+"Equipo Rojo Gana", 20, 20, 20);
//
//				    		}
//				  
				    		
				    		 boss.setProgress(1.0);
					  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"FIN...");
				    		 //Bukkit.getScheduler().cancelTask(taskID);	
					  		 //gc.Top(target,name);
					  		 

					    		
					    			//RemoveArmorStandsAndItemsInMap(target);
									//Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Jugadores que ganaron "+ChatColor.GREEN+arrive+ChatColor.RED+" mapa: "+ChatColor.GREEN+name);

									ms.setGameStatus(GameStatus.TERMINANDO);
							
  								
									
									System.out.println("TIME");
					    			
					    		
						  
					     }else if(motivo == StopMotive.WIN || motivo == StopMotive.LOSE || motivo == StopMotive.ERROR || motivo == StopMotive.FORCE) {
//				    			
				    			
				    			
				    			 boss.setProgress(1.0);
						  		 boss.setTitle(""+ChatColor.WHITE+ChatColor.BOLD+"FIN..");
						  		//RemoveArmorStandsAndItemsInMap(target);
						  		 if(motivo == StopMotive.WIN || motivo == StopMotive.LOSE ) {
						  	//		 gc.Top(target,name);
						  		 }
						  		
						  		ms.setGameStatus(GameStatus.TERMINANDO);
						  		System.out.println("STOP MOMENT");
					     }
//				    	}else if(ms.getBlueTeamMg().isEmpty()) {
//							  target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"No hay Jugadores en el Equipo "+ChatColor.BLUE+ChatColor.BOLD+"Azul",""+ChatColor.WHITE+ChatColor.BOLD+"Finalizando Partida", 20, 20, 20);
//							  ms.setEstadopartida(EstadoPartida.TERMINANDO);
//
//				    	}
//				    	else if(ms.getRedTeamMg().isEmpty()) {
//							  target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"No hay Jugadores en el Equipo "+ChatColor.RED+ChatColor.BOLD+"Rojo",""+ChatColor.WHITE+ChatColor.BOLD+"Finalizando Partida", 20, 20, 20);
//							  ms.setEstadopartida(EstadoPartida.TERMINANDO);
//
//				    	}
					  
				     
				      ShootEntityToPlayer(target);
					  RemoveEntitysInBarrier(target);
					  removeTrapArrows(target);
					  getNearbyBlocks2(target);
					  getNearbyBlocks3(target);
					  JumpMob(target);
					  
					  String s1 = String.valueOf(segundo);
					  if(s1.endsWith("0")) {
						
					  }
						
					
					
					}
				
					 gc.HasTimePath("Time-"+hora+"-"+minuto+"-"+segundo, name);
				//colocar terminando
			}
			//TODO TERMINANDO
			else if(part == GameStatus.TERMINANDO) {
				 

			 	
					for(String players : joins) {
						Player target = Bukkit.getServer().getPlayerExact(players);
						if(end <= 5) {
		 	       		  //  RemoveArmorStandsAndItemsInMap(target);
		 	       		    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
		 	       		    target.sendTitle(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+String.valueOf(end),""+ChatColor.AQUA+ChatColor.BOLD+"La partida termina en ", 20, 20, 20);
					    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
				    		}else {
				    			//RemoveArmorStandsAndItemsInMap(target);
				    			target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
				    			target.sendMessage(ChatColor.RED+"La partida termina en "+ChatColor.DARK_PURPLE+end);
				    		}
						RemoveEntitysAfterGame(target);
					}
					
				   	if(end == 0) {
				   	
				   			
					   		
					   		System.out.println("ANTES NEXO RUN : "+ms.ShowGame());
			    			
//					   		
//								//RemoveArmorStandsAndItemsInMap(target);
					   			//dn.EndNexo(name);
								gc.endGameActions(name);
				  
					    	 //ms.setEstadopartida(EstadoPartida.ESPERANDO);
				   		
			    		Bukkit.getScheduler().cancelTask(taskID);	
			    		System.out.println("SE DETUVO NEXO ;)");
				     }
					
				
				end--;
			
				//colocar esperando 
			}
			
			

		

	
		  
			
		  
		 //  System.out.println("Cuenta atras : "+hora+"h "+minuto+"m "+segundo+"s " );
			
			
			}
		},0L,20);
		
	  }
	

	
	//TODO NERBYBLOCK 2
	public void getNearbyBlocks2(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Item-Spawn-Range") ;
		
		if (r.getType().equals(Material.AIR)) {
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
									}
									 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
			
								}
								
								if(a.getType() == Material.DIAMOND_BLOCK && b.getType() == Material.BEDROCK) {
									if(player.getLocation().distance(a.getLocation()) <= rango) {
										player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									}
								     a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND));
									 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.EMERALD_BLOCK && b.getType() == Material.BEDROCK) {
									if(player.getLocation().distance(a.getLocation()) <= rango) {
										player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									}
									 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD));
									 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.IRON_BLOCK && b.getType() == Material.BEDROCK) {
									if(player.getLocation().distance(a.getLocation()) <= rango) {
										player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									}
									 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.GOLD_BLOCK && b.getType() == Material.BEDROCK) {
									if(player.getLocation().distance(a.getLocation()) <= rango) {
										player.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									}
									 a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING,a.getLocation().add(0.5, 1.5, 0.5), 1);
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
	
	//TODO SPAWN GENERATOR 3
	public void getNearbyBlocks3(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Item-Spawn-Range") ;
		
		if (r.getType().equals(Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode() == GameMode.ADVENTURE ) {
							
						
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
	

    public void JumpMob(Player player) {
    	List<Entity> entities = getNearbyEntites(player.getLocation(), 50);
    	for(int i = 0;i< entities.size();i++) {
    		Block block = entities.get(i).getLocation().getBlock();
    		Block r = block.getRelative(0, 0, 0);
    		
    		if(entities.get(i).getType() == EntityType.PLAYER) continue;
    		
    		if(r.getType() == Material.OAK_PRESSURE_PLATE) {
    			 entities.get(i).setVelocity(entities.get(i).getLocation().getDirection().multiply(3).setY(2));
    		}
    		if(r.getType() == Material.STONE_PRESSURE_PLATE) {
   			   entities.get(i).setVelocity(entities.get(i).getLocation().getDirection().multiply(3).setY(1));
    		}
    		
    	}
    }
	
 
 
	@SuppressWarnings("removal")
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
    
    public void RemoveEntitysInBarrier(Player player) {
    	
    	List<Entity> l = getNearbyEntites(player.getLocation(),150);
    	
    	if(!l.isEmpty()) {
    		for(Entity e : l) {
    	    	
        		Block b = e.getLocation().getBlock();
        		Block block = b.getRelative(0,-1,0);
        		
        		if(e.getType() != EntityType.PLAYER && block.getType() == Material.BARRIER) {
        			e.remove();
        		}
        	}
    	}
    
    	
    }
    
   public void RemoveEntitysAfterGame(Player player) {
    	
    	List<Entity> l = getNearbyEntites(player.getLocation(),150);
    	
    	if(!l.isEmpty()) {
    		for(Entity e : l) {
        		
        		if(e.getType() != EntityType.PLAYER && e.getType() != EntityType.PAINTING) {
        			e.remove();
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
    		
    		if(entities.get(i).getType() == EntityType.SNOW_GOLEM || entities.get(i).getType() == EntityType.IRON_GOLEM) {
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
