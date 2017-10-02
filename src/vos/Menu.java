package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="categoria")
	private Integer categoria;
	
	@JsonProperty(value="precioVenta")
	private Integer precioVenta;
	
	@JsonProperty(value="costosProduccion")
	private Integer costosProduccion;
	
	@JsonProperty(value="tipoComidaProd")
	private String tipoComidaProd;
	
	@JsonProperty(value="tiempoPreparacion")
	private Integer tiempoPreparacion;
	
	@JsonProperty(value="restaurante_nombre")
	private String restaurante_nombre;
	
	public Menu(@JsonProperty(value="nombre")String pNom, @JsonProperty(value="categoria")Integer pCat,
			@JsonProperty(value="precioVenta")Integer pVenta, @JsonProperty(value="costosProduccion")Integer pProd, 
			@JsonProperty(value="tipoComidaProd")String ptipo, @JsonProperty(value="tiempoPreparacion")Integer ptiempo,
			@JsonProperty(value="restaurante_nombre")String a) {
		super();
		nombre=pNom;
		categoria=pCat;
		precioVenta=pVenta;
		costosProduccion=pProd;
		tipoComidaProd=ptipo;
		tiempoPreparacion=ptiempo;
		restaurante_nombre=a;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Integer getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Integer getCostosProduccion() {
		return costosProduccion;
	}

	public void setCostosProduccion(Integer costosProduccion) {
		this.costosProduccion = costosProduccion;
	}

	public String getTipoComidaProd() {
		return tipoComidaProd;
	}

	public void setTipoComidaProd(String tipoComidaProd) {
		this.tipoComidaProd = tipoComidaProd;
	}

	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public String getRestaurante_nombre() {
		return restaurante_nombre;
	}

	public void setRestaurante_nombre(String restaurante_nombre) {
		this.restaurante_nombre = restaurante_nombre;
	}
	
}
