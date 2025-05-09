package me.nao.generalinfo.mg;

public class SystemOfLevels {

	
	private long referenceA ,referenceB , totalxplvlA, totalxplvlB, xp, remaingxp, totalplayerxp ;
	private int playerlvl;
	public SystemOfLevels() {
		this.referenceA = 0;
		this.referenceB = 0;
		this.totalxplvlA = 0;
		this.totalxplvlB = 0;
		this.xp = 0;
		this.remaingxp = 0;
		this.totalplayerxp = 0;
		this.playerlvl = 0;
	}
	public long getReferenceA() {
		return referenceA;
	}
	public long getReferenceB() {
		return referenceB;
	}
	public long getTotalxplvlA() {
		return totalxplvlA;
	}
	public long getTotalxplvlB() {
		return totalxplvlB;
	}
	public long getRemaingxp() {
		return remaingxp;
	}
	public long getTotalPlayerXpLvl() {
		return totalplayerxp;
	}
	public long getXp() {
			return xp;
	}
	public int getPlayerlvl() {
		return playerlvl;
	}
	public void setReferenceA(long referenceA) {
		this.referenceA = referenceA;
	}
	public void setReferenceB(long referenceB) {
		this.referenceB = referenceB;
	}
	public void setTotalxplvlA(long totalxplvlA) {
		this.totalxplvlA = totalxplvlA;
	}
	public void setTotalxplvlB(long totalxplvlB) {
		this.totalxplvlB = totalxplvlB;
	}
	public void setRemaingxp(long remaingxp) {
		this.remaingxp = remaingxp;
	}
	public void setTotalPlayerXpLvl(long totalplayerxp) {
		this.totalplayerxp = totalplayerxp;
	}
	public void setPlayerlvl(int playerlvl) {
		this.playerlvl = playerlvl;
	}
	public void setXp(long xp) {
		this.xp = xp;
	}
	
	public void calcXp(long generaltotalxp) {
		long preresult = generaltotalxp < 0 ? 0 : generaltotalxp;
		int lvl = 0;
		long referencia = 1000;
	  	long referencianterior = 0;
	  	long puntajerestante = preresult;
	  	//CACLULA EL NIVEL EN BASE AL PUNTAJE 
	  	while(puntajerestante > referencia) {
	  		referencianterior = referencia;
	  		lvl++;
	  		referencia = (int) Math.round(1000 * Math.pow((1 + 2 / 100.0), lvl));
	  		puntajerestante -= referencianterior;
	  	}
	  	
	  	referencianterior = lvl == 0 ? 0 : referencianterior+1;
	  	//xptotalcalculado += puntajerestante;
	  	//System.out.println("LVL "+lvl+" RA:"+referencianterior+" RP:"+referencia+" total xp:"+generaltotalxp);
	  	setPlayerlvl(lvl);
	  	setReferenceA(referencianterior);
	  	setReferenceB(referencia);
	  	rangeOfLvl(lvl);
	  	setRemaingxp(generaltotalxp-getTotalxplvlA());
	  	
    }
	
	
    
    public void rangeOfLvl (int lvl) {
    	long refer = 1000;
		long referant = 0;
    	for(int i =0;i<lvl;i++) {
    		referant = lvl != 0 ? refer+1 : refer;
    		refer +=  (int) Math.round(1000 * Math.pow((1 + 2 / 100.0),i+1));
    	}
    	//System.out.println(referant+" "+refer); 
    	setTotalxplvlA(referant);
    	setTotalxplvlB(refer);
    	setTotalPlayerXpLvl(referant);
    
    	
    }
	
	
	
	
	
	
	
	
	
}
