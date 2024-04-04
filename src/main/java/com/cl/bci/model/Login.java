package com.cl.bci.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Login implements Serializable {

	private static final long serialVersionUID = 179711444408257378L;

	@NotNull
	private String password;

	@NotNull
	private String email;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
