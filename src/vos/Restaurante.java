package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante {

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="tipoComidaRest")
	private String tipoComidaRest;
	
	@JsonProperty(value="paginaWeb")
	private String paginaWeb;
	
	public Restaurante(@JsonProperty(value="nombre")String pNom, @JsonProperty(value="descripcion")String pDesc,
			@JsonProperty(value="tipoComidaRest")String ptipo, @JsonProperty(value="paginaWeb")String ppag) 
	{
		super();
		nombre=pNom;
		descripcion=pDesc;
		tipoComidaRest=ptipo;
		paginaWeb= ppag;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoComidaRest() {
		return tipoComidaRest;
	}

	public void setTipoComidaRest(String tipoComidaRest) {
		this.tipoComidaRest = tipoComidaRest;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	
}
