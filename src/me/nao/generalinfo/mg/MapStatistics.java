package me.nao.generalinfo.mg;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MapStatistics {
	

	private int timesplayed;
    private int participatingplayers;
    private int winningplayers;
    private int reviveplayers;
    private int deadplayers;

    public MapStatistics(int timesplayed, int participatingplayers, int winningplayers, int reviveplayers, int deadplayers) {
        this.timesplayed = timesplayed;
        this.participatingplayers = participatingplayers;
        this.winningplayers = winningplayers;
        this.reviveplayers = reviveplayers;
        this.deadplayers = deadplayers;
    }

    public BigDecimal getPorcentWins() {
    	
    	 if (participatingplayers == 0) {
             return BigDecimal.ZERO;
         }
    	
        return BigDecimal.valueOf(winningplayers)
                .divide(BigDecimal.valueOf(participatingplayers), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal getPorcentOfDeads() {
    	
    	if (participatingplayers == 0) {
            return BigDecimal.ZERO;
        }
    	
        return BigDecimal.valueOf(deadplayers)
                .divide(BigDecimal.valueOf(participatingplayers), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal getPorcentRevives() {
    	
    	if (deadplayers == 0) {
            return BigDecimal.ZERO;
        }
    	
        return BigDecimal.valueOf(reviveplayers)
                .divide(BigDecimal.valueOf(deadplayers), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
    
    public BigDecimal getProbablyOfWin() {
    	
    	if (timesplayed == 0) {
            return BigDecimal.ZERO;
        }
    	
        return BigDecimal.valueOf(winningplayers)
                .divide(BigDecimal.valueOf(timesplayed), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal getProbablyOfLose() {
        return BigDecimal.valueOf(100).subtract(getProbablyOfWin());
    }
	
}
