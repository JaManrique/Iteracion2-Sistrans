package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sun.javafx.iio.png.PNGIDATChunkInputStream;

public class Producto {
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="categoria")
	private Integer categoria;
	
	@JsonProperty(value="precio")
	private Integer precio;
	
	@JsonProperty(value="costo")
	private Integer costo;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="tiempoPreparacion")
	private Integer tiempoPreparacion;
	
	public Producto(@JsonProperty(value="nombre")String pNom, @JsonProperty(value="categoria")Integer pCat,
			@JsonProperty(value="precio")Integer pVenta, @JsonProperty(value="costo")Integer pProd, 
			@JsonProperty(value="tipo")String ptipo, @JsonProperty(value="tiempoPreparacion")Integer ptiempo) {
		super();
		nombre=pNom;
		categoria=pCat;
		precio=pVenta;
		costo=pProd;
		tipo=ptipo;
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
		return precio;
	}

	public void setPrecioVenta(Integer precioVenta) {
		this.precio = precioVenta;
	}

	public Integer getCostosDeProduccion() {
		return costo;
	}

	public void setCostosDeProduccion(Integer costosDeProduccion) {
		this.costo = costosDeProduccion;
	}

	public String getTipoComidaProd() {
		return tipo;
	}

	public void setTipoComidaProd(String tipoComidaProd) {
		this.tipo = tipoComidaProd;
	}

	public Integer getTiempoDePreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoDePreparacion(Integer tiempoDePreparacion) {
		this.tiempoPreparacion = tiempoDePreparacion;
	}

}
