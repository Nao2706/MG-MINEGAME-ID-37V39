package me.nao.general.info;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

import me.nao.enums.GameStatus;
import me.nao.main.game.Minegame;

public class GameTime {

	
	private String map;
	
	private int gamehour;
	private int gameminute;
	private int gamesecond;
	
	private int timerhour;
	private int timerminute;
	private int timersecond;
	
	private int cronomethour;
	private int cronometminute;
	private int cronometsecond;
	
	private int addedhour;
	private int addedminute;
	private int addedsecond;
	
	private int showaddedtime; 
	
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
		this.gamehour = gamehour;
		this.gameminute = gameminute;
		this.gamesecond = gamesecond;
		this.timerhour = gamehour;
		this.timerminute = gameminute;
		this.timersecond = gamesecond;
		this.cronomethour = 0;
		this.cronometminute = 0;
		this.cronometsecond = 0;
		
		this.bossbarhora = this.timerhour * 3600;
		this.bossbarminute = this.timerminute * 60;
		this.bossbarsecond = this.timersecond;
		this.bossbartotal = this.bossbarhora + this.bossbarminute + this.bossbarsecond;
		this.bossbarpro = 1.0;
		this.bossbartime = 1.0 / this.bossbartotal;
	}
	
	public void timeRun() {
		GameInfo gi = plugin.getGameInfoPoo().get(this.map);
		
		if(gi.getGameStatus() != GameStatus.PAUSE) {
			 if(this.timersecond != 0){
				 this.timersecond--; 
			 }
			 
			 if(this.timerminute != 0 && this.timersecond == 0) {
				 this.timersecond = 60;
					this.timerminute --;
			 }
			 
			 if(this.timerhour != 0 && this.timerminute == 0) {
				 this.timerminute = 60;
					this.timerhour --;
			 }
		}
		
		
		 
		 if(this.cronometsecond != 60 ){
			 this.cronometsecond++; 
		 }
		 
		 if(this.cronometminute != 60 && this.cronometsecond == 60) {
			 this.cronometsecond = 0;
			 this.cronometminute ++;
		 }
		 
		 if(this.cronomethour != 60 && this.cronometminute == 60) {
			 this.cronometminute = 0;
			 this.cronomethour ++;
		 }
		
		 updateBossBarTittle();
	}
	

	public void addTimeToTimer(int hour , int minute , int seconds) {
			
		
		this.timersecond = this.timersecond + seconds;
		this.timerminute = this.timerminute + minute;
		this.timerhour = this.timerhour + hour;
		
		this.addedhour = hour;
		this.addedminute = minute;
		this.addedsecond = seconds;
		this.showaddedtime = 3;
		
		if(this.timersecond > 60) {
			this.timersecond = this.timersecond - 60;
			this.timerminute ++;
			
			if(this.timerminute > 60) {
				this.timerminute = 0;
				this.cronomethour ++;
			}
		}
		
		
	}
	
	public void setTimeToTimer(int hour , int minute , int seconds) {
		this.timersecond = seconds;
		this.timerminute = minute;
		this.timerhour = hour;
		if(this.timersecond > 60) {
			this.timersecond = 60;
		}
		if(this.timerminute > 60) {
			this.timerminute = 60;
		}
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
        boss.setProgress(this.bossbarpro);
  	   
	  if(this.timerhour <= 0 && this.timerminute >= 10) {
		  boss.setColor(BarColor.GREEN);
	  }

	  if(this.timerhour <= 0 && this.timerminute <= 9) {
		  boss.setColor(BarColor.YELLOW);						  
	  }
	  
      if(this.timerhour <= 0 && this.timerminute <= 1 ) {
		  boss.setColor(BarColor.RED);
	  } 			
      
      this.bossbarpro = this.bossbarpro - this.bossbartime;
	  boss.setTitle(getGameTimer());

      
      
	}

	public int getGamehour() {
		return gamehour;
	}

	public int getGameminute() {
		return gameminute;
	}

	public int getGamesecond() {
		return gamesecond;
	}

	public int getTimerhour() {
		return timerhour;
	}

	public int getTimerminute() {
		return timerminute;
	}

	public int getTimersecond() {
		return timersecond;
	}

	public int getCronomethour() {
		return cronomethour;
	}

	public int getCronometminute() {
		return cronometminute;
	}

	public int getCronometsecond() {
		return cronometsecond;
	}
	
	
	public String getGameTimer() {
		String text = "";
		
		if(this.showaddedtime != 0) {
			this.showaddedtime -- ;
			text = ""+ChatColor.DARK_RED+ChatColor.BOLD+"GT Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;

			if(this.addedhour != 0){
				text = text +ChatColor.GREEN+ChatColor.BOLD+"+"+this.addedhour+"h";
			}else if(this.addedminute != 0){
				text = text +ChatColor.GREEN+ChatColor.BOLD+" +"+this.addedminute+"m";
			}else if(this.addedsecond != 0){
				text = text +ChatColor.GREEN+ChatColor.BOLD+" + "+this.addedsecond+"s";
			}

			return text;
		}
		
		return ""+ChatColor.DARK_RED+ChatColor.BOLD+"GT Tiempo Remanente:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getTimerhour()+"h "+getTimerminute()+"m "+getTimersecond()+"s " ;
	}
	
	public String getGameCronomet() {
		return ""+ChatColor.AQUA+ChatColor.BOLD+"Cronometro:"+ChatColor.DARK_GREEN+ChatColor.BOLD+" "+getCronomethour()+"h "+getCronometminute()+"m "+getCronometsecond()+"s " ;
	}
	
}
