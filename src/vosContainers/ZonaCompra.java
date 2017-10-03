package vosContainers;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.CheckOut;

public class ZonaCompra
{
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="capacidad")
	private Integer capacidad;
	
	private List<CheckOut> checksO;
	
	public ZonaCompra(@JsonProperty(value="nombre")String pNom,@JsonProperty(value="capacidad")Integer ctrn) {
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

	public List<CheckOut> getChecksO() {
		return checksO;
	}

	public void setChecksO(List<CheckOut> checksO) {
		this.checksO = checksO;
	}
	
}
