package vosContainers;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistroCliente {

	@JsonProperty(value="usuario")
	private String usuario;

	@JsonProperty(value="contraseña")
	private String contraseña;

	@JsonProperty(value="rol")
	private String rol;

	@JsonProperty(value="correo")
	private String correo;
	
	@JsonProperty(value="adminPass")
	private String adminPass;
	
	@JsonProperty(value="adminUser")
	private String adminUser;
	

	public RegistroCliente(@JsonProperty(value="usuario")String pNom,@JsonProperty(value="contraseña")String ctrn,
			@JsonProperty(value="rol")String pVenta, @JsonProperty(value="correo")String prod, @JsonProperty(value="adminUser") String padminUser, @JsonProperty(value="adminPass") String padminPass) {
		super();
		usuario=pNom;
		contraseña=ctrn;
		rol=pVenta;
		correo=prod;
		adminUser = padminUser;
		adminPass = padminPass;
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

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}


}
