package vosContainers;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteProducto {
	
	@JsonProperty(value="producto")
	private String producto;
	
	@JsonProperty(value="ganancia")
	private Double ganancia;
	
	@JsonProperty(value="cantidad")
	private Integer cantidad;
	
	@JsonProperty(value="cant_registrados")
	private Integer cant_registrados;
	
	@JsonProperty(value="cant_no_registrados")
	private Integer cant_no_registrados;

	public ReporteProducto( @JsonProperty(value="ganancia") String producto, @JsonProperty(value="ganancia") Double ganancia, 
			@JsonProperty(value="cantidad") Integer cantidad, @JsonProperty(value="cant_registrados") Integer cant_registrados,
			@JsonProperty(value="cant_no_registrados") Integer cant_no_registrados) {
		super();
		this.producto = producto;
		this.ganancia = ganancia;
		this.cantidad = cantidad;
		this.cant_registrados = cant_registrados;
		this.cant_no_registrados = cant_no_registrados;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}



	public Double getGanancia() {
		return ganancia;
	}



	public void setGanancia(Double ganancia) {
		this.ganancia = ganancia;
	}



	public Integer getCantidad() {
		return cantidad;
	}



	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}



	public Integer getCant_registrados() {
		return cant_registrados;
	}



	public void setCant_registrados(Integer cant_registrados) {
		this.cant_registrados = cant_registrados;
	}



	public Integer getCant_no_registrados() {
		return cant_no_registrados;
	}



	public void setCant_no_registrados(Integer cant_no_registrados) {
		this.cant_no_registrados = cant_no_registrados;
	}
}
