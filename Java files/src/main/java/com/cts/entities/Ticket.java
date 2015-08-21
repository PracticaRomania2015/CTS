package com.cts.entities;

import java.sql.Timestamp;

public class Ticket {

	private int ticketId;
	private String subject;
	private String category;
	private String description;
	private String filePath;
	private int userId;
	private Timestamp dateTime;

	public Ticket() {

		ticketId = 0;
		userId = 0;
		dateTime = new Timestamp(0);
		subject = "";
		category = "";
		description = "";
		filePath = "";
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getUserId() {

		return userId;
	}

	public Timestamp getDateTime() {

		return dateTime;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getSubject() {

		return subject;
	}

	public String getCategory() {

		return category;
	}

	public String getDescription() {

		return description;
	}

	public String getFilePath() {

		return filePath;
	}
}
