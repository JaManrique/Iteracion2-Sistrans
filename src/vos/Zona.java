package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="capacidad")
	private Integer capacidad;
	
	public Zona(@JsonProperty(value="nombre")String pNom,@JsonProperty(value="capacidad")Integer ctrn) {
		super();
		nombre=pNom;
		capacidad=ctrn;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	
}
