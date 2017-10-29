package vosContainers;

import java.util.List;

import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoMenu {

	@JsonProperty(value="usuario")
	private String usuario;
	
	@JsonProperty(value="pass")
	private String pass;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="esMenu")
	private Boolean esMenu;
	
	@JsonProperty(value="alternativos")
	private List<String> alternativos;
	
	@JsonProperty(value="restaurante")
	private String restaurante;
	
	public PedidoMenu(@JsonProperty(value="usuario")String pNom, @JsonProperty(value="pass")String pPass,
			@JsonProperty(value="nombre")String pNombre, @JsonProperty(value="esMenu")Boolean pesmenu, 
			@JsonProperty(value="alternativos")List<String> pAlternativos, @JsonProperty(value="restaurante") String restaurante) {
		super();
		usuario=pNom;
		pass=pPass;
		nombre=pNombre;
		esMenu=pesmenu;
		alternativos = pAlternativos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEsMenu() {
		return esMenu;
	}

	public void setEsMenu(Boolean esMenu) {
		this.esMenu = esMenu;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public List<String> getAlternativos() {
		return alternativos;
	}

	public void setAlternativos(List<String> alternativos) {
		this.alternativos = alternativos;
	}

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}	
	
}
