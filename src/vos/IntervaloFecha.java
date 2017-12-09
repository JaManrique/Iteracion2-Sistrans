package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IntervaloFecha {

	@JsonProperty(value="ini")
	private String ini;
	
	@JsonProperty(value="fin")
	private String fin;
	
	@JsonProperty(value="rol")
	private String rol;
	
	@JsonProperty(value="correo")
	private String correo;
	
	public IntervaloFecha(@JsonProperty(value="ini")String pNom,@JsonProperty(value="fin")String ctrn) {
		super();
		ini=pNom;
		fin=ctrn;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
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
