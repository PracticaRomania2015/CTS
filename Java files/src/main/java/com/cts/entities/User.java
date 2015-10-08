package com.cts.entities;

public class User {

	private int userId;
	private String firstName;
	private String lastName;
	private String title;
	private String email;
	private String password;
	private Role role;
	private Question question_1;
	private String questionAnswer_1;
	private Question question_2;
	private String questionAnswer_2;

	public User() {
		userId = 0;
		email = "";
		password = "";
		firstName = "";
		lastName = "";
		title = "";
		role = new Role();
		question_1 = new Question();
		questionAnswer_1 = "";
		question_2 = new Question();
		questionAnswer_2 = "";
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public Question getQuestion_1() {
		return question_1;
	}

	public void setQuestion_1(Question question_1) {
		this.question_1 = question_1;
	}

	public Question getQuestion_2() {
		return question_2;
	}

	public void setQuestion_2(Question question_2) {
		this.question_2 = question_2;
	}

	public String getQuestionAnswer_1() {
		return questionAnswer_1;
	}

	public void setQuestionAnswer_1(String questionAnswer_1) {
		this.questionAnswer_1 = questionAnswer_1;
	}

	public String getQuestionAnswer_2() {
		return questionAnswer_2;
	}

	public void setQuestionAnswer_2(String questionAnswer_2) {
		this.questionAnswer_2 = questionAnswer_2;
	}
}