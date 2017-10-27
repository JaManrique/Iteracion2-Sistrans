package vosContainers;

import org.codehaus.jackson.annotate.JsonProperty;

public class TuplaEQProducto {

	@JsonProperty(value="prod1")
	private String prod1;

	@JsonProperty(value="prod2")
	private String prod2;
	
	@JsonProperty(value="adminPass")
	private String adminPass;
	
	@JsonProperty(value="adminUser")
	private String adminUser;
	
	public TuplaEQProducto(@JsonProperty(value="prod1")String pProd1,@JsonProperty(value="prod2")String pProd2,
			@JsonProperty(value="adminPass")String pAdminPass, @JsonProperty(value="adminUser")String pAdminUser) {
		super();
		prod1 = pProd1;
		prod2 = pProd2;
		adminUser = pAdminUser;
		adminPass = pAdminPass;
	}

	public String getProd1() {
		return prod1;
	}

	public void setProd1(String prod1) {
		this.prod1 = prod1;
	}

	public String getProd2() {
		return prod2;
	}

	public void setProd2(String prod2) {
		this.prod2 = prod2;
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
