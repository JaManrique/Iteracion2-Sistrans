package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante_Producto {
	@JsonProperty(value="restaurante_nombre")
	private String restaurante_nombre;
	
	@JsonProperty(value="producto_nombre")
	private String producto_nombre;
	
	public Restaurante_Producto(@JsonProperty(value="restaurante_nombre")String pNom,@JsonProperty(value="producto_nombre")String ctrn) {
		super();
		restaurante_nombre=pNom;
		producto_nombre=ctrn;
	}

	public String getRestaurante_nombre() {
		return restaurante_nombre;
	}

	public void setRestaurante_nombre(String restaurante_nombre) {
		this.restaurante_nombre = restaurante_nombre;
	}

	public String getProducto_nombre() {
		return producto_nombre;
	}

	public void setProducto_nombre(String producto_nombre) {
		this.producto_nombre = producto_nombre;
	}
	
}
