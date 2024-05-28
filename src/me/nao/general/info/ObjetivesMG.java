package me.nao.general.info;

public class ObjetivesMG {
	
	
	private String nombre;
	private int priority;
	private int valueinitial;
	private int valuereferencial;
	private String description;
	private ObjetiveType status;
	
	public ObjetivesMG(String nombre,  int priority, int valueinitial, int valuereferencial,String description, ObjetiveType status) {
		this.nombre = nombre;
		this.valueinitial = valueinitial;
		this.valuereferencial = valuereferencial;
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
	
	public int getValueinitial() {
		return valueinitial;
	}

	public int getValuereferencial() {
		return valuereferencial;
	}
	
	public String getDescription() {
		return description;
	}

	public ObjetiveType isStatus() {
		return status;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setValueinitial(int valueinitial) {
		this.valueinitial = valueinitial;
	}

	public void setValuereferencial(int valuereferencial) {
		this.valuereferencial = valuereferencial;
	}

	public void setStatus(ObjetiveType status) {
		this.status = status;
	}
	
	
	
	
	

}
