package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sun.javafx.iio.png.PNGIDATChunkInputStream;

public class ProductoIter5 {
	
	@JsonProperty(value="nombreRotonda")
	private String nombreRotonda;
	
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

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="disponibilidad")
	private Integer disponibilidad;
	
	@JsonProperty(value="idrestaurante")
	private Long restaurante;
	
	@JsonProperty(value="cantidadMaxima")
	private int cantidadMaxima;
	
	public ProductoIter5(@JsonProperty(value="nombreRotonda")String pNomR,@JsonProperty(value="nombre")String pNom, @JsonProperty(value="categoria")Integer pCat,
			@JsonProperty(value="precio")Integer pVenta, @JsonProperty(value="costo")Integer pProd, 
			@JsonProperty(value="tipo")String ptipo, @JsonProperty(value="tiempoPreparacion")Integer ptiempo, @JsonProperty(value="id")Long pid,
			@JsonProperty(value="descripcion")String pdesc, @JsonProperty(value="disponibilidad")Integer disp, @JsonProperty(value="idrestaurante")Long idrest,
			@JsonProperty(value="cantidadMaxima")Integer pcant) {
		super();
		nombreRotonda=pNomR;
		nombre=pNom;
		id=pid;
		categoria=pCat;
		precio=pVenta;
		costo=pProd;
		tipo=ptipo;
		tiempoPreparacion=ptiempo;
		descripcion=pdesc;
		disponibilidad=disp;
		restaurante=idrest;
		cantidadMaxima=pcant;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(Integer disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public Long getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Long restaurante) {
		this.restaurante = restaurante;
	}
	public int getCantidadMaxima() {
		return cantidadMaxima;
	}
	public void setCantidadMaxima(int cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
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
