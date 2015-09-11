package com.cts.entities;

public class User {

	private int userId;
	private String firstName;
	private String lastName;
	private String title;
	private String email;
	private String password;
	private int isAdmin;

	public User() {

		userId = 0;
		email = "";
		password = "";
		firstName = "";
		lastName = "";
		title = "";
		isAdmin = 0;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getEmail() {

		return email;
	}

	public String getPassword() {

		return password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {

		return firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public String getTitle() {
		return title;
	}
}