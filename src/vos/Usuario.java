package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	@JsonProperty(value="usuario")
	private String usuario;
	
	@JsonProperty(value="contrase�a")
	private String contrase�a;
	
	@JsonProperty(value="rol")
	private String rol;
	
	@JsonProperty(value="correo")
	private String correo;
	
	public Usuario(@JsonProperty(value="usuario")String pNom,@JsonProperty(value="contrase�a")String ctrn,
			@JsonProperty(value="rol")String pVenta, @JsonProperty(value="correo")String prod) {
		super();
		usuario=pNom;
		contrase�a=ctrn;
		rol=pVenta;
		correo=prod;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
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
