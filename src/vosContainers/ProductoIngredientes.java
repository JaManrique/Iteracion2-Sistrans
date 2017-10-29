package vosContainers;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoIngredientes{


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
	
	@JsonProperty(value="isIngrediente")
	private Boolean isIngrediente;
	
	@JsonProperty(value="isProducto")
	private Boolean isProducto;
	
	@JsonProperty(value="nombreIng")
	private String nombreIng;
	
	@JsonProperty(value="descEsp")
	private String descEsp;
	
	@JsonProperty(value="descIng")
	private String descING;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="esMax")
	private Boolean esMax;
	
	@JsonProperty(value="max")
	private Integer max;

	public ProductoIngredientes(@JsonProperty(value="nombre")String pNom, @JsonProperty(value="categoria")Integer pCat,
			@JsonProperty(value="precioVenta")Integer pVenta, @JsonProperty(value="costosProduccion")Integer pProd, 
			@JsonProperty(value="tipoComidaProd")String ptipoCom, @JsonProperty(value="tiempoPreparacion")Integer ptiempo, @JsonProperty(value="nombreIng")String pNomIng,@JsonProperty(value="descEsp")String pdescEsp,
			@JsonProperty(value="descIng")String pdescING,@JsonProperty(value="tipo")String ptipo, @JsonProperty(value="isIngrediente") Boolean pIsIng,@JsonProperty(value="isProducto") Boolean pIsProd,
			@JsonProperty(value="esMax") Boolean esMaxx, @JsonProperty(value="max") Integer maxx) {
		super();
		nombre=pNom;
		categoria=pCat;
		precioVenta=pVenta;
		costosProduccion=pProd;
		tipoComidaProd=ptipoCom;
		tiempoPreparacion=ptiempo;
		nombreIng = pNomIng;
		descEsp = pdescEsp;
		descING = pdescING;
		tipo = ptipo;
		isIngrediente = pIsIng;
		isProducto = pIsProd;
		esMax = esMaxx;
		max = maxx;
	}
	public Integer getCostosProduccion() {
		return costosProduccion;
	}
	public void setCostosProduccion(Integer costosProduccion) {
		this.costosProduccion = costosProduccion;
	}
	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}
	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}
	public Boolean getIsIngrediente() {
		return isIngrediente;
	}
	public void setIsIngrediente(Boolean isIngrediente) {
		this.isIngrediente = isIngrediente;
	}
	public Boolean getIsProducto() {
		return isProducto;
	}
	public void setIsProducto(Boolean isProducto) {
		this.isProducto = isProducto;
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
	
	public String getNombreIng() {
		return nombreIng;
	}

	public void setNombreIng(String nombreIng) {
		this.nombreIng = nombreIng;
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
	public Boolean getEsMax() {
		return esMax;
	}
	public void setEsMax(Boolean esMax) {
		this.esMax = esMax;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	

}
