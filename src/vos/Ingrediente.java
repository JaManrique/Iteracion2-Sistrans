package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="descEsp")
	private String descEsp;
	
	@JsonProperty(value="descIng")
	private String descING;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	public Ingrediente(@JsonProperty(value="nombre")String pNom,@JsonProperty(value="descEsp")String pdescEsp,
			@JsonProperty(value="descIng")String pdescING,@JsonProperty(value="tipo")String ptipo) {
		super();
		nombre=pNom;
		descEsp=pdescEsp;
		descING=pdescING;
		tipo=ptipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescEsp() {
		return descEsp;
	}

	public void setDescEsp(String descEsp) {
		this.descEsp = descEsp;
	}

	public String getDescING() {
		return descING;
	}

	public void setDescING(String descING) {
		this.descING = descING;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
