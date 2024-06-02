package me.nao.general.info;

public class ObjetivesMG {
	
	
	private String nombre ,description;
	private int priority ,valuestart, valueinitial, valuecomplete,valueincomplete;
	private ObjetiveType status;
	
	public ObjetivesMG(String nombre,  int priority, int valuestart, int valueinitial, int valuecomplete,int valueincomplete,String description, ObjetiveType status) {
		this.nombre = nombre;
		this.valuestart = valuestart;
		this.valueinitial = valueinitial;
		this.valuecomplete = valuecomplete;
		this.valueincomplete = valueincomplete;
		this.description = description;
		this.status = status;
		this.priority = priority;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPriority() {
		return priority;
	}
	
	public int getStartValue() {
		return valuestart;
	}
	
	public int getValue() {
		return valueinitial;
	}
	
	public int getCompleteValue() {
		return valuecomplete;
	}
	
	public int getIncompleteValue() {
		return valueincomplete;
	}
	
	public String getDescription() {
		return description;
	}

	public ObjetiveType getObjetiveType() {
		return status;
	}

	public void setValue(int valueinitial) {
		this.valueinitial = valueinitial;
	}

	public void setObjetiveType(ObjetiveType status) {
		this.status = status;
	}
	
	
	
	
	

}
