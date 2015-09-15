package com.cts.entities;

public class Role {

	private int roleId;
	private String roleName;

	public Role() {

		roleId = 0;
		roleName = "";
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
