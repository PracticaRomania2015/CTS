package com.cts.entities;

public class UserForUpdate extends User {

	private String oldPassword;

	public UserForUpdate() {
		oldPassword = "";
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
