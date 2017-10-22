package vosContainers;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductosServidos {
	
	@JsonProperty(value="filtro")
	private String filtro;
	@JsonProperty(value="columnaFiltro")
	private String columnaFiltro;
	@JsonProperty(value="compFiltro")
	private String compFiltro;
	@JsonProperty(value="agruparPor")
	private String agruparPor;
	@JsonProperty(value="orden")
	private String orden;
	
	
	public ProductosServidos(@JsonProperty(value="filtro")String filtro, @JsonProperty(value="columnaFiltro")String columnaFiltro, 
			@JsonProperty(value="compFiltro")String compFiltro, @JsonProperty(value="agruparPor")String agruparPor, @JsonProperty(value="orden")String orden) {
		super();
		this.filtro = filtro;
		this.columnaFiltro = columnaFiltro;
		this.compFiltro = compFiltro;
		this.agruparPor = agruparPor;
		this.orden = orden;
	}


	public String getFiltro() {
		return filtro;
	}


	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


	public String getColumnaFiltro() {
		return columnaFiltro;
	}


	public void setColumnaFiltro(String columnaFiltro) {
		this.columnaFiltro = columnaFiltro;
	}


	public String getCompFiltro() {
		return compFiltro;
	}


	public void setCompFiltro(String compFiltro) {
		this.compFiltro = compFiltro;
	}


	public String getAgruparPor() {
		return agruparPor;
	}


	public void setAgruparPor(String agruparPor) {
		this.agruparPor = agruparPor;
	}


	public String getOrden() {
		return orden;
	}


	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	
}
