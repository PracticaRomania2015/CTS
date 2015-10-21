package com.cts.entities;

public class Question {

	private int questionId;
	private String question;

	public Question() {
		questionId = 0;
		question = "";
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}