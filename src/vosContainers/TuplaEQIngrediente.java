package vosContainers;

import org.codehaus.jackson.annotate.JsonProperty;

public class TuplaEQIngrediente {

	@JsonProperty(value="ing1")
	private String ing1;

	@JsonProperty(value="ing2")
	private String ing2;
	
	@JsonProperty(value="adminPass")
	private String adminPass;
	
	@JsonProperty(value="adminUser")
	private String adminUser;
	
	public TuplaEQIngrediente(@JsonProperty(value="ing1")String pIng1,@JsonProperty(value="ing2")String pIng2,
			@JsonProperty(value="adminPass")String pAdminPass, @JsonProperty(value="adminUser")String pAdminUser) {
		super();
		ing1 = pIng1;
		ing2 = pIng2;
		adminUser = pAdminUser;
		adminPass = pAdminPass;
	}

	public String getIng1() {
		return ing1;
	}

	public void setIng1(String ing1) {
		this.ing1 = ing1;
	}

	public String getIng2() {
		return ing2;
	}

	public void setIng2(String ing2) {
		this.ing2 = ing2;
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
