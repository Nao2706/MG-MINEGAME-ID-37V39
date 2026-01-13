package me.nao.generalinfo.mg;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.nao.enums.mg.GameStatus;
import me.nao.enums.mg.GameType;
import me.nao.enums.mg.TimerStatus;
import me.nao.main.mg.Minegame;
import me.nao.utils.mg.Utils;

@SuppressWarnings("deprecation")
public class GameTime {

	
	private String map;
	
	private int gamehourmg,gameminutemg,gamesecondmg;	
	private int timerhourmg,timerminutemg,timersecondmg;
	private int cronomethourmg,cronometminutemg,cronometsecondmg;
	private int freezehour,freezeminute,freezesecond;
	
	
	private int addedhour , removehour;
	private int addedminute, removeminute;
	private int addedsecond , removesecond;
	
	private int showaddedtime, showremovetime; 
	
	private double bossbarhora;
	private double bossbarminute;
	private double bossbarsecond;
	private double bossbarpro;
	private double bossbartotal;
	private double bossbartime;
	
	private BossBar timerbosbar;
	private String title;
	private BarColor color;
	private boolean isExcutabletimer;
	private List<String> actions;
	private TimerStatus ts;
	
	private Minegame plugin;
	
	public GameTime(Minegame plugin ,String map , int gamehour, int gameminute, int gamesecond) {
		this.plugin = plugin;
		this.map = map;
		this.gamehourmg = gamehour;
		this.gameminutemg = gameminute;
		this.gamesecondmg = gamesecond;
		
		this.timerhourmg = gamehour;
		this.timerminutemg = gameminute;
		this.timersecondmg = gamesecond;
		
		this.cronomethourmg = 0;
		this.cronometminutemg = 0;
		this.cronometsecondmg = 0;
		
		this.addedhour = 0;
		this.addedminute = 0;
		this.addedsecond = 0;
		this.showaddedtime = 0;
		
		this.removehour = 0;
		this.removeminute = 0;
		this.removesecond = 0;
		this.showremovetime = 0;
		
		this.freezehour = 0;
		this.freezeminute = 0;
		this.freezesecond = 0;
		
		this.bossbarhora = this.timerhourmg * 3600;
		this.bossbarminute = this.timerminutemg * 60;
		this.bossbarsecond = this.timersecondmg;
		this.bossbartotal = this.bossbarhora + this.bossbarminute + this.bossbarsecond;
		this.bossbarpro = 1.0;
		this.bossbartime = 1.0 / this.bossbartotal;
		
		this.title = "";
		this.color = null;
		this.timerbosbar = Bukkit.createBossBar("",BarColor.GREEN, BarStyle.SOLID,  null ,null);
		this.actions = new ArrayList<>();
		this.isExcutabletimer = false;
		ts = TimerStatus.READY;
	}
	
	public void timerRunMg() {
		GameInfo gi = plugin.getGameInfoPoo().get(this.map);
		
		
		
		if(isExcutableTimerMg()) {
			
			  updateBossBarTittle();
			  
			  if(this.timersecondmg == 0 && this.timerminutemg == 0 && this.timerhourmg == 0) {
				  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				  
				  if(!getTimerActions().isEmpty());
				  for(String t : getTimerActions()) {
					  
						if(t.contains("%player%")) {
							if(gi instanceof GameAdventure) {
								GameAdventure ga = (GameAdventure) gi;
								for(String player : ga.getAlivePlayers()){
									Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', t.replaceAll("%player%",player)));
								}
							}
							
						}else{
							Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', t));
						}
					  
					  
				  }
				  getCustomTimerBossBar().setVisible(false);
				  setExecutableTimerByCommand(false);
				  setTimerStatus(TimerStatus.FINISH);
			  }
			  
			  
			  if(this.timersecondmg != 0){
				    this.timersecondmg--; 
				
			   }if(this.timerminutemg != 0 && this.timersecondmg == 0) {
				    this.timerminutemg--;
				    this.timersecondmg = 60;
					
			   }if(this.timerhourmg != 0 && this.timerminutemg == 0) {
				    this.timerhourmg--;
				    this.timerminutemg = 60;
					
			   }
			
		}else {
			if(gi.getGameStatus() == GameStatus.TERMINANDO)return;
			if(this.cronometsecondmg != 60 ){
			   this.cronometsecondmg++;
				
			}if(this.cronometminutemg != 60 && this.cronometsecondmg == 60) {
				 
				 this.cronometminutemg++;
				 this.cronometsecondmg = 0;
				
			}if(this.cronomethourmg != 60 && this.cronometminutemg == 60) {
				
				 this.cronomethourmg++;
				 this.cronometminutemg = 0;
			 }
			
			
			
			updateBossBarTittle();
			if(gi.getGameStatus() == GameStatus.PAUSE)return;
			
			
			if(gi.getGameStatus() == GameStatus.FREEZE) { 
				  if(this.freezesecond <= 0 && this.freezeminute <= 0 &&  this.freezehour <= 0) {
					  gi.setGameStatus(GameStatus.JUGANDO);
				  }
				
				  if(this.freezesecond != 0){
					    this.freezesecond--; 
					
				   }if(this.freezeminute != 0 && this.freezesecond == 0) {
					    this.freezeminute--;
					    this.freezesecond = 60;
						
				   }if(this.freezehour != 0 && this.freezeminute == 0) {
					    this.freezehour--;
					    this.freezeminute = 60;
						
				   }
				return;
			}

			
			  if(this.timersecondmg != 0){
				    this.timersecondmg--; 
				
			   }if(this.timerminutemg != 0 && this.timersecondmg == 0) {
				    this.timerminutemg--;
				    this.timersecondmg = 60;
					
			   }if(this.timerhourmg != 0 && this.timerminutemg == 0) {
				    this.timerhourmg--;
				    this.timerminutemg = 60;
					
			   }
		}
		

		 
		 
		  
		 return;
	}
	

	
	public void addTimeToTimer(String map ,int hour , int minute , int seconds) {
			
		System.out.println("VIEJO TIEMPO: "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s");
		//OCULTE ESTO
		//GameInfo gi = plugin.getGameInfoPoo().get(map);
		//BossBar boss = gi.getBossbar();
//		 boss.setProgress(1.0);
		int totaltimerinseconds = (this.timerhourmg * 3600) + (this.timerminutemg * 60) + this.timersecondmg;
		
		int totaladdedseconds = (hour * 3600) + (minute * 60) + seconds;
		
		int totaltimertime = totaltimerinseconds + totaladdedseconds;
		int horas = totaltimertime / 3600;
		int minutos = (totaltimertime % 3600) / 60;
		int segundos = totaltimertime % 60;
		
		int addedseconds = totaladdedseconds;
		
		int ahoras = addedseconds / 3600;
		int aminutos = (addedseconds % 3600) / 60;
		int asegundos = addedseconds % 60;
		
		
		
		this.addedhour = ahoras;
		this.addedminute = aminutos;
		this.addedsecond = asegundos;
		
		setTimerhourmg(horas);
		setTimerminutemg(minutos);
		setTimersecondmg(segundos);
		
		System.out.println("NUEVO TIEMPO ADD: "+horas+"h "+minutos+"m "+segundos+"s");
		this.bossbartotal = totaltimertime;
		this.bossbarpro = 1.0;
		this.bossbartime = 1.0 / this.bossbartotal;
		//OCULTE ESTO 2
//		this.bossbartime = 1.0 / totaltimertime;
//		boss.setProgress(this.bossbarpro);
		
		this.showaddedtime = 5;
		return;
	}
	
	public void setTimeToTimer(String map ,int hour , int minute , int seconds) {
		
		
		System.out.println("VIEJO TIEMPO: "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s");
//		GameInfo gi = plugin.getGameInfoPoo().get(map);
//		BossBar boss = gi.getBossbar();
		 //boss.setProgress(1.0);
		int totalasettedseconds = (hour * 3600) + (minute * 60) + seconds;
		
		int totaltimertime = totalasettedseconds;
		int horas = totaltimertime / 3600;
		int minutos = (totaltimertime % 3600) / 60;
		int segundos = totaltimertime % 60;

		
		setTimerhourmg(horas);
		setTimerminutemg(minutos);
		setTimersecondmg(segundos);
		System.out.println("NUEVO TIEMPO SET: "+horas+"h "+minutos+"m "+segundos+"s");
		this.bossbartotal = totaltimertime;
		this.bossbarpro = 1.0;
		this.bossbartime = 1.0 / this.bossbartotal;
//		this.bossbartime = 1.0 / totaltimertime;
//		boss.setProgress(this.bossbarpro);
		
	}
	
	public void removeTimeToTimer(String map ,int hour , int minute , int seconds) {
			
		System.out.println("VIEJO TIEMPO: "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s");
//		GameInfo gi = plugin.getGameInfoPoo().get(map);
//		BossBar boss = gi.getBossbar();
		 //boss.setProgress(1.0);
		int totaltimerinseconds = (this.timerhourmg * 3600) + (this.timerminutemg * 60) + this.timersecondmg;
		
		int totalremovedseconds = (hour * 3600) + (minute * 60) + seconds;
		
		int totaltimertime = totaltimerinseconds - totalremovedseconds;
		int horas = totaltimertime / 3600;
		int minutos = (totaltimertime % 3600) / 60;
		int segundos = totaltimertime % 60;
		
		int removedseconds = totalremovedseconds;
		
		int rhoras = removedseconds / 3600;
		int rminutos = (removedseconds % 3600) / 60;
		int rsegundos = removedseconds % 60;
		
	
		
		if(totaltimertime > 0) {
			
			this.removehour =  rhoras;
			this.removeminute = rminutos;
			this.removesecond = rsegundos;
			
			
			setTimerhourmg(horas);
			setTimerminutemg(minutos);
			setTimersecondmg(segundos);
			
			System.out.println("NUEVO TIEMPO REMOVE: "+horas+"h "+minutos+"m "+segundos+"s");

			this.bossbartotal = totaltimertime;
			this.bossbarpro = 1.0;
			this.bossbartime = 1.0 / this.bossbartotal;
//			this.bossbartime = 1.0 / totaltimertime;
//			boss.setProgress(this.bossbarpro);
			
			
			this.showremovetime = 5;
		}else {
			
			this.removehour = rhoras;
			this.removeminute = rminutos;
			this.removesecond = rsegundos;
			

			setTimerhourmg(0);
			setTimerminutemg(0);
			setTimersecondmg(0);
			
			System.out.println("NUEVO TIEMPO REMOVE: 0 0 0 ");
			this.bossbartotal = 0;
			this.bossbarpro = 1.0;
			this.bossbartime = 1.0 / this.bossbartotal;
//			this.bossbartime = 1.0 / 0.0;
//		    boss.setProgress(this.bossbarpro);
			
			this.showremovetime = 5;
		}
		
	
		
	
		return;
	}
	
	public void freezesetTimeToTimer(String map ,int hour , int minute , int seconds) {
		
		GameInfo gi = plugin.getGameInfoPoo().get(map);
	
		int totalasettedseconds = (hour * 3600) + (minute * 60) + seconds;
		
		int totaltimertime = totalasettedseconds;
		int horas = totaltimertime / 3600;
		int minutos = (totaltimertime % 3600) / 60;
		int segundos = totaltimertime % 60;

		this.freezehour = horas;
		this.freezeminute = minutos;
		this.freezesecond = segundos;
		gi.setGameStatus(GameStatus.FREEZE);
		System.out.println("NUEVO TIEMPO FREEZE: "+horas+"h "+minutos+"m "+segundos+"s");
		
	
		
	}
	
		
	public void updateBossBarTittle() {
		
		GameInfo gi = plugin.getGameInfoPoo().get(this.map);
		
		
//		  //Transformar minutos - hora a segundos
//	 	double hora3 = this.timerhour * 3600;
//
//	 	double minuto3 = this.timerminute * 60;
//	 	double segundo3 = this.timersecond;
//	 	//todos los seg
//	 	double total = hora3 + minuto3 + segundo3;
//	 	//bossbar al maximo
//	    double pro = 1.0;
//	 	//calculo para hacer uba reduccion
//	 	double time = 1.0 / total;
 			
      
     
      if(isExcutableTimerMg()) {
    	  
	    		BossBar boss = getCustomTimerBossBar();
	    		if(this.bossbarpro > 0) {
	     			 boss.setProgress(this.bossbarpro);
	     		}else {
	     			 boss.setProgress(0.0);
	     		}
    	  
    		
    	  	    if(this.timerhourmg <= 0 && this.timerminutemg >= 10) {
    	   		  boss.setColor(color);
    	   	    }

    	   	    if(this.timerhourmg <= 0 && this.timerminutemg <= 9) {
    	   		  boss.setColor(BarColor.YELLOW);						  
    	   	    }
    	   	  
    	        if(this.timerhourmg <= 0 && this.timerminutemg <= 1 ) {
    	   		  boss.setColor(BarColor.RED);
    	   	    }
    		
    	        if(getTimerStatus() == TimerStatus.STOPPING) {
       	    		getCustomTimerBossBar().setVisible(false);
           			setExecutableTimerByCommand(false);
           			setTimerStatus(TimerStatus.CANCELED);
           		
    	        }
    	        
    	        boss.setTitle(getGameTimer(gi.getGameStatus()));
    	        
    	

    	
    	  
      }else {
    	  
		  		BossBar boss = gi.getBossbar();
		  		
		  		
		  		if(this.bossbarpro > 0) {
		  			 boss.setProgress(this.bossbarpro);
		  		}else {
		  			 boss.setProgress(0.0);
		  		}
		  		
		         
		    	   
		  	   if(this.timerhourmg <= 0 && this.timerminutemg >= 10) {
		  		  boss.setColor(BarColor.GREEN);
		  	    }
		
		  	    if(this.timerhourmg <= 0 && this.timerminutemg <= 9) {
		  		  boss.setColor(BarColor.YELLOW);						  
		  	    }
		  	  
		        if(this.timerhourmg <= 0 && this.timerminutemg <= 1 ) {
		  		  boss.setColor(BarColor.RED);
		  	     }
		    	  
		    	  boss.setTitle(getGameTimer(gi.getGameStatus()));
      }
     
	  
	  
	  if(gi.getGameStatus() == GameStatus.JUGANDO) { 
    	  this.bossbarpro = this.bossbarpro - this.bossbartime;
      }
      
      
	}

	public int getGamehour() {
		return this.gamehourmg;
	}

	public int getGameminute() {
		return this.gameminutemg;
	}

	public int getGamesecond() {
		return this.gamesecondmg;
	}

	public int getTimerhour() {
		return this.timerhourmg;
	}

	public int getTimerminute() {
		return this.timerminutemg;
	}

	public int getTimersecond() {
		return this.timersecondmg;
	}

	public int getCronomethour() {
		return this.cronomethourmg;
	}

	public int getCronometminute() {
		return this.cronometminutemg;
	}

	public int getCronometsecond() {
		return this.cronometsecondmg;
	}
	
	public List<String> getTimerActions(){
		return actions;
	}
	
	public boolean isExcutableTimerMg() {
		return isExcutabletimer;
	}
	
	public BossBar getCustomTimerBossBar() {
		return timerbosbar;
	}
	
	public String getCustomTitle() {
		return title;
	}
	
	public TimerStatus getTimerStatus() {
		return ts;
	}
	
	public BarColor getBossBarColor() {
		return color;
	}
	
	public void setGamehour(int gamehourmg) {
		this.gamehourmg = gamehourmg;
	}

	public void setGameminute(int gameminutemg) {
		this.gameminutemg = gameminutemg;
	}

	public void setGamesecond(int gamesecondmg) {
		this.gamesecondmg = gamesecondmg;
	}

	public void setTimerhourmg(int timerhourmg) {
		this.timerhourmg = timerhourmg;
	}

	public void setTimerminutemg(int timerminutemg) {
		this.timerminutemg = timerminutemg;
	}

	public void setTimersecondmg(int timersecondmg) {
		this.timersecondmg = timersecondmg;
	}

	public void setCronomethour(int cronomethourmg) {
		this.cronomethourmg = cronomethourmg;
	}

	public void setCronometminute(int cronometminute) {
		this.cronometminutemg = cronometminute;
	}

	public void setCronometsecond(int cronometsecond) {
		this.cronometsecondmg = cronometsecond;
	}

	public void setAddedhour(int addedhour) {
		this.addedhour = addedhour;
	}

	public void setAddedminute(int addedminute) {
		this.addedminute = addedminute;
	}

	public void setAddedsecond(int addedsecond) {
		this.addedsecond = addedsecond;
	}
	
	public void setExcutableTimerActions(List<String> actions) {
		this.actions = actions;
	}
	
	public void setExecutableTimerByCommand(boolean isExcutabletimer) {
		this.isExcutabletimer = isExcutabletimer;
	}
	
	public void setCustomTitle(String title) {
		this.title = title;
	}
	
	public void setBossBarTimer (BossBar bosstimer) {
		this.timerbosbar = bosstimer;
	}
	
	public void setTimerStatus(TimerStatus t) {
		this.ts = t;
	}
	
	public void setBossBarColor(BarColor bc) {
		this.color = bc;
	}

	public String getGameTimer(GameStatus status) {
		
		
	     GameInfo gi = plugin.getGameInfoPoo().get(this.map);
		 String text = "";
		
		 //DE MOMENTO EL TIMER CUSTOM NO SE PUEDE FREZEAR
		if(isExcutableTimerMg()) {
			
			 if(getTimerStatus() == TimerStatus.STOPPING) {
				   text = Utils.colorTextChatColor("&c&lCancelado "+getCustomTitle().replaceAll("%timer%",getGameTimerForPlayer())
				  			.replaceAll("%timersecond%",String.valueOf(getTimersecond())
						    .replaceAll("%timerminute%",String.valueOf(getTimerminute())
						    .replaceAll("%timerhour%",String.valueOf(getTimerhour())
						    		
						    		)))); 
				 
					setTimerStatus(TimerStatus.CANCELED);
       			
   	        }else{
 			   text = Utils.colorTextChatColor(getCustomTitle().replaceAll("%timer%",getGameTimerForPlayer())
			  			.replaceAll("%timersecond%",String.valueOf(getTimersecond())
					    .replaceAll("%timerminute%",String.valueOf(getTimerminute())
					    .replaceAll("%timerhour%",String.valueOf(getTimerhour())
					    		
					    		))));
   	        }
			

			   
			   return text;
		}
		
		if(this.showaddedtime != 0) {
			this.showaddedtime -- ;
			
			
			 if(gi.getGameType() == GameType.ADVENTURE) {
				
				if(status == GameStatus.PAUSE) { 
					text = ""+ChatColor.GOLD+ChatColor.BOLD+"Tiempo Remanente Pausado:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s "+ChatColor.GREEN+"+"+showTimerFormat(this.addedhour)+":"+showTimerFormat(this.addedminute)+":"+showTimerFormat(this.addedsecond);

				}else {
					text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " +ChatColor.GREEN+"+"+showTimerFormat(this.addedhour)+":"+showTimerFormat(this.addedminute)+":"+showTimerFormat(this.addedsecond);

				}
				
			}else if(gi.getGameType() == GameType.RESISTENCE){
				if(status == GameStatus.PAUSE) { 
					text = ""+ChatColor.GOLD+ChatColor.BOLD+"Resistencia Pausado:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s "+ChatColor.GREEN+"+"+showTimerFormat(this.addedhour)+":"+showTimerFormat(this.addedminute)+":"+showTimerFormat(this.addedsecond);

				}else {
					text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Resiste:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " +ChatColor.GREEN+"+"+showTimerFormat(this.addedhour)+":"+showTimerFormat(this.addedminute)+":"+showTimerFormat(this.addedsecond);

				}
			}else if(gi.getGameType() == GameType.FREEFORALL){
				if(status == GameStatus.PAUSE) { 
					text = ""+ChatColor.AQUA+ChatColor.BOLD+"Todos Contra Todos Pausado:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s "+ChatColor.GREEN+"+"+showTimerFormat(this.addedhour)+":"+showTimerFormat(this.addedminute)+":"+showTimerFormat(this.addedsecond);

				}else {
					text = ""+ChatColor.AQUA+ChatColor.BOLD+"Todos Contra Todos:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " +ChatColor.GREEN+"+"+showTimerFormat(this.addedhour)+":"+showTimerFormat(this.addedminute)+":"+showTimerFormat(this.addedsecond);

				}
			}
			
			
			
			return text;
			
		}if(this.showremovetime != 0) {
			this.showremovetime -- ;
			if(gi.getGameType() == GameType.ADVENTURE) {
				if(status == GameStatus.PAUSE) { 
					text = ""+ChatColor.GOLD+ChatColor.BOLD+"Tiempo Remanente Pausado:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s "+ChatColor.RED+"-"+showTimerFormat(this.removehour)+":"+showTimerFormat(this.removeminute)+":"+showTimerFormat(this.removesecond);

				}else {
					text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " +ChatColor.RED+"-"+showTimerFormat(this.removehour)+":"+showTimerFormat(this.removeminute)+":"+showTimerFormat(this.removesecond);

				}
			}else if(gi.getGameType() == GameType.RESISTENCE){
				if(status == GameStatus.PAUSE) { 
					text = ""+ChatColor.GOLD+ChatColor.BOLD+"Resistencia Pausado:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s "+ChatColor.RED+"-"+showTimerFormat(this.removehour)+":"+showTimerFormat(this.removeminute)+":"+showTimerFormat(this.removesecond);

				}else {
					text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Resiste:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " +ChatColor.RED+"-"+showTimerFormat(this.removehour)+":"+showTimerFormat(this.removeminute)+":"+showTimerFormat(this.removesecond);

				}
			}else if(gi.getGameType() == GameType.FREEFORALL){
				if(status == GameStatus.PAUSE) { 
					text = ""+ChatColor.AQUA+ChatColor.BOLD+"Todos Contra Todos Pausado:"+ChatColor.DARK_PURPLE+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s "+ChatColor.RED+"-"+showTimerFormat(this.removehour)+":"+showTimerFormat(this.removeminute)+":"+showTimerFormat(this.removesecond);

				}else {
					text = ""+ChatColor.AQUA+ChatColor.BOLD+"Todos Contra Todos:"+ChatColor.DARK_PURPLE+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " +ChatColor.RED+"-"+showTimerFormat(this.removehour)+":"+showTimerFormat(this.removeminute)+":"+showTimerFormat(this.removesecond);

				}
			}
			
			
			return text;
		}
		
		if(status == GameStatus.FREEZE) { 
			return text = ""+ChatColor.AQUA+ChatColor.BOLD+"FREEZE: "+ChatColor.BLUE+ChatColor.BOLD+ChatColor.STRIKETHROUGH+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s" +ChatColor.AQUA+" "+showTimerFormat(this.freezehour)+":"+showTimerFormat(this.freezeminute)+":"+showTimerFormat(this.freezesecond);
		}
		
		if(status == GameStatus.PAUSE) { 
			
			if(gi.getGameType() == GameType.ADVENTURE) {
				return text =  ""+ChatColor.GOLD+ChatColor.BOLD+"Tiempo Remanente Pausado:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
				
			}else if(gi.getGameType() == GameType.RESISTENCE){
				return text = ""+ChatColor.GOLD+ChatColor.BOLD+"Resistencia Pausado:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
				
			}else if(gi.getGameType() == GameType.FREEFORALL){
				return text = ""+ChatColor.AQUA+ChatColor.BOLD+"Todos Contra Todos Pausado:"+ChatColor.DARK_PURPLE+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
			}
		}
		
		//return ""+ChatColor.DARK_RED+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
		if(gi.getGameType() == GameType.ADVENTURE) {
			return text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
			
		}else if(gi.getGameType() == GameType.RESISTENCE){
			return text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Resiste:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
			
		}else if(gi.getGameType() == GameType.FREEFORALL){
			return text = ""+ChatColor.AQUA+ChatColor.BOLD+"Todos Contra Todos:"+ChatColor.DARK_PURPLE+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
			
		}
		return text;
	}
	
	public String getGameCronomet() {
		return ""+ChatColor.AQUA+ChatColor.BOLD+"Cronometro:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getCronomethour()+"h "+getCronometminute()+"m "+getCronometsecond()+"s " ;
	}
	
	public String getGameTimerForResult() {
		return getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s ";
	}
	
	public String getGameCronometForResult() {
		return getCronomethour()+"h "+getCronometminute()+"m "+getCronometsecond()+"s ";
	}
	
	public String getGameTimerDefaultForResult() {
		return getGamehour()+"h "+getGameminute()+"m "+getGamesecond()+"s ";

	}
	
	public String getGameCronometForPlayer() {
		
		 String seg = String.format("%02d", getCronometsecond());
		 String min = String.format("%02d", getCronometminute());
		 String hor = String.format("%02d", getCronomethour());
		return hor+":"+min+":"+seg;
	}
	
	public String getGameTimerForPlayer() {
		 String seg = String.format("%02d", getTimersecond());
		 String min = String.format("%02d", getTimerminute());
		 String hor = String.format("%02d", getTimerhour());
		return hor+":"+min+":"+seg;
	}
	
	public long getTotalSecondsofCronomet() {
		
		long total = 0;
		total = (getCronomethour() * 3600) + (getCronometminute() * 60) + getCronometsecond();
		return total;
	}
	
	private String showTimerFormat(int value) {
		 String val = String.format("%02d", value);
		 return val;
	}
	
	public String getSecondsConvertToCronomet(long seconds) {
		long totaltimertime = seconds;
		long horas = totaltimertime / 3600;
		long minutos = (totaltimertime % 3600) / 60;
		long segundos = totaltimertime % 60;
		 String seg = String.format("%02d", segundos);
		 String min = String.format("%02d", minutos);
		 String hor = String.format("%02d", horas);
		return ""+hor+":"+min+":"+seg;
	}
	

	public void startCustomTimer() {
		
			
		if(getTimerStatus() == TimerStatus.IN_PROGRESS) return;
		  getCustomTimerBossBar().setTitle(Utils.colorTextChatColor(getCustomTitle().replaceAll("%timer%",getGameTimerForPlayer())
		  			.replaceAll("%timersecond%",String.valueOf(getTimersecond())
				    .replaceAll("%timerminute%",String.valueOf(getTimerminute())
				    .replaceAll("%timerhour%",String.valueOf(getTimerhour())
				    		)))));
		  if(this.timersecondmg == 0 && this.timerminutemg == 0 && this.timerhourmg == 0) {
			  
			    getCustomTimerBossBar().setProgress(1.0);
			    setTimeToTimer(this.map,getGamehour(),getGameminute(),getGamesecond());
		
		   }
			
		  if(getTimerStatus() == TimerStatus.CANCELED) {
			    getCustomTimerBossBar().setProgress(1.0);
			    setTimeToTimer(this.map,getGamehour(),getGameminute(),getGamesecond());
		  }
		  

		  getCustomTimerBossBar().setColor(color);
		  setExecutableTimerByCommand(true);
		  setTimerStatus(TimerStatus.IN_PROGRESS);
		  getCustomTimerBossBar().setVisible(true);
		  
		  
		  
		  if(getCustomTimerBossBar().getPlayers().isEmpty()) {
			  GameConditions gc = new GameConditions(plugin);
			  
			  GameInfo gi = plugin.getGameInfoPoo().get(this.map);
			  
			  List<String> l = gi.getParticipants();
			  for(Player player : gc.ConvertStringToPlayer(l)) {
				  gc.showBossBarsTimers(player, gi);
			  }
		  }

	
		
	}
	
	
}
