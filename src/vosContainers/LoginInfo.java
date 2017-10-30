package vosContainers;

import org.codehaus.jackson.annotate.JsonProperty;

public class LoginInfo {

	@JsonProperty(value="usr")
	private String usr;

	@JsonProperty(value="pass")
	private String pass;

	@JsonProperty(value="todo")
	private Boolean todo;

	public LoginInfo(String usr, String pass, Boolean todo) {
		super();
		this.usr = usr;
		this.pass = pass;
		this.todo = todo;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Boolean getTodo() {
		return todo;
	}

	public void setTodo(Boolean todo) {
		this.todo = todo;
	}	
}
