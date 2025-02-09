package me.nao.general.info;


import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

import me.nao.enums.GameStatus;
import me.nao.enums.GameType;
import me.nao.main.mg.Minegame;

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
	}
	
	public void timerRunMg() {
		GameInfo gi = plugin.getGameInfoPoo().get(this.map);
		
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
		 
		 
		  
		 return;
	}
	

	
	public void addTimeToTimer(int hour , int minute , int seconds) {
			
		System.out.println("VIEJO TIEMPO: "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s");

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
		
		this.bossbartime = 1.0 / totaltimertime;
		
		this.showaddedtime = 3;
		return;
	}
	
	public void setTimeToTimer(int hour , int minute , int seconds) {
		
		
		System.out.println("VIEJO TIEMPO: "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s");
		int totalasettedseconds = (hour * 3600) + (minute * 60) + seconds;
		
		int totaltimertime = totalasettedseconds;
		int horas = totaltimertime / 3600;
		int minutos = (totaltimertime % 3600) / 60;
		int segundos = totaltimertime % 60;

		
		setTimerhourmg(horas);
		setTimerminutemg(minutos);
		setTimersecondmg(segundos);
		System.out.println("NUEVO TIEMPO SET: "+horas+"h "+minutos+"m "+segundos+"s");
		
		this.bossbartime = 1.0 / totaltimertime;
		
	}
	
	public void removeTimeToTimer(int hour , int minute , int seconds) {
			
		System.out.println("VIEJO TIEMPO: "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s");
		
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

			
			this.bossbartime = 1.0 / totaltimertime;
			
			this.showremovetime = 3;
		}else {
			
			this.removehour = rhoras;
			this.removeminute = rminutos;
			this.removesecond = rsegundos;
			

			setTimerhourmg(0);
			setTimerminutemg(0);
			setTimersecondmg(0);
			
			System.out.println("NUEVO TIEMPO REMOVE: 0 0 0 ");

			this.bossbartime = 1.0 / 0.0;
			
			
			this.showremovetime = 3;
		}
		
	
		
	
		return;
	}
	
	public void freezesetTimeToTimer(int hour , int minute , int seconds) {
		
		GameInfo gi = plugin.getGameInfoPoo().get(this.map);
	
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
		BossBar boss = gi.getBossbar();
		
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
		
		boss.setVisible(true);
		
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

	public String getGameTimer(GameStatus status) {
		
		
	     GameInfo gi = plugin.getGameInfoPoo().get(this.map);
		 String text = "";
		
		
		
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
			}
			
			
			return text;
		}
		
		if(status == GameStatus.FREEZE) { 
			text = ""+ChatColor.AQUA+ChatColor.BOLD+"FREEZE: "+ChatColor.BLUE+ChatColor.BOLD+ChatColor.STRIKETHROUGH+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s" +ChatColor.AQUA+" "+showTimerFormat(this.freezehour)+":"+showTimerFormat(this.freezeminute)+":"+showTimerFormat(this.freezesecond);
		}
		
		if(status == GameStatus.PAUSE) { 
			
			if(gi.getGameType() == GameType.ADVENTURE) {
				text =  ""+ChatColor.GOLD+ChatColor.BOLD+"Tiempo Remanente Pausado:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
			}else if(gi.getGameType() == GameType.RESISTENCE){
				text = ""+ChatColor.GOLD+ChatColor.BOLD+"Resistencia Pausado:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
			}
		}
		
		//return ""+ChatColor.DARK_RED+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
		if(gi.getGameType() == GameType.ADVENTURE) {
			text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
		}else if(gi.getGameType() == GameType.RESISTENCE){
			text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"Resiste:"+ChatColor.AQUA+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
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
	
	private String showTimerFormat(int value) {
		 String val = String.format("%02d", value);
		 return val;
	}
	
	
	
}
