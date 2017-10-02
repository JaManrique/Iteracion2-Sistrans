package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sun.javafx.iio.png.PNGIDATChunkInputStream;

public class Producto {
	
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
	
	public Producto(@JsonProperty(value="nombre")String pNom, @JsonProperty(value="categoria")Integer pCat,
			@JsonProperty(value="precioVenta")Integer pVenta, @JsonProperty(value="costosProduccion")Integer pProd, 
			@JsonProperty(value="tipoComidaProd")String ptipo, @JsonProperty(value="tiempoPreparacion")Integer ptiempo) {
		super();
		nombre=pNom;
		categoria=pCat;
		precioVenta=pVenta;
		costosProduccion=pProd;
		tipoComidaProd=ptipo;
		tiempoPreparacion=ptiempo;
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

	public Integer getCostosDeProduccion() {
		return costosProduccion;
	}

	public void setCostosDeProduccion(Integer costosDeProduccion) {
		this.costosProduccion = costosDeProduccion;
	}

	public String getTipoComidaProd() {
		return tipoComidaProd;
	}

	public void setTipoComidaProd(String tipoComidaProd) {
		this.tipoComidaProd = tipoComidaProd;
	}

	public Integer getTiempoDePreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoDePreparacion(Integer tiempoDePreparacion) {
		this.tiempoPreparacion = tiempoDePreparacion;
	}

}
