package vosContainers;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.CheckOut;
import vos.Producto;

public class ZonaCompra
{
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="capacidad")
	private Integer capacidad;
	
	private List<Producto> prods;
	
	
	
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

	public List<Producto> getChecksO() {
		return prods;
	}

	public void setChecksO(List<Producto> checksO) {
		this.prods = checksO;
	}
	
}
