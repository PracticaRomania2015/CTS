package com.cts.entities;

import java.sql.Timestamp;

public class TicketComment {

	private int commentId;
	private int ticketId;
	private Timestamp dateTime;
	private String comment;
	private User user;
	private String filePath;

	public TicketComment() {

		commentId = 0;
		ticketId = 0;
		dateTime = new Timestamp(0);
		comment = "";
		user = new User();
		filePath = "";
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
