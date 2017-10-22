package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	@JsonProperty(value="usuario")
	private String usuario;
	
	@JsonProperty(value="contraseña")
	private String contraseña;
	
	@JsonProperty(value="rol")
	private String rol;
	
	@JsonProperty(value="correo")
	private String correo;
	
	public Usuario(@JsonProperty(value="usuario")String pNom,@JsonProperty(value="contraseña")String ctrn,
			@JsonProperty(value="rol")String pVenta, @JsonProperty(value="correo")String prod) {
		super();
		usuario=pNom;
		contraseña=ctrn;
		rol=pVenta;
		correo=prod;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
}
