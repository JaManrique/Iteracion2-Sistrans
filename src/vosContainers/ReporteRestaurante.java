package vosContainers;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteRestaurante
{
	@JsonProperty(value="nombre_restaurante")
	private String nombre_restaurante;
	
	@JsonProperty(value="reportes_producto")
	private List<ReporteProducto> reportes_producto;

	public ReporteRestaurante(@JsonProperty(value="nombre_restaurante") String Nnombre_restaurante, @JsonProperty(value="reportes_producto") List<ReporteProducto> Rreportes_producto)
	{
		super();
		nombre_restaurante = Nnombre_restaurante;
		reportes_producto = Rreportes_producto;
	}
	
	public String getNombre_restaurante() {
		return nombre_restaurante;
	}

	public void setNombre_restaurante(String nombre_restaurante) {
		this.nombre_restaurante = nombre_restaurante;
	}

	public List<ReporteProducto> getReportes_producto() {
		return reportes_producto;
	}

	public void setReportes_producto(List<ReporteProducto> reportes_producto) {
		this.reportes_producto = reportes_producto;
	}
}
