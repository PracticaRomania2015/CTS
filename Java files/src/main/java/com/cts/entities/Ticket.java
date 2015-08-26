package com.cts.entities;

import java.util.ArrayList;

public class Ticket {

	private int ticketId;
	private String subject;
	private int categoryId;
	private int assignedToUserId;
	private TicketComment newTicketComment;
	private ArrayList<TicketComment> comments;

	public Ticket() {

		ticketId = 0;
		subject = "";
		categoryId = 0;
		assignedToUserId = 0;
		newTicketComment = new TicketComment();
		comments = new ArrayList<TicketComment>();
	}

	public ArrayList<TicketComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<TicketComment> comments) {
		this.comments = comments;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getAssignedToUserId() {
		return assignedToUserId;
	}

	public void setAssignedToUserId(int assignedToUserId) {
		this.assignedToUserId = assignedToUserId;
	}

	public void setNewTicketComment(TicketComment newTicketComment) {
		this.newTicketComment = newTicketComment;
	}

	public String getSubject() {

		return subject;
	}

	public int getCategoryId() {

		return categoryId;
	}

	public TicketComment getNewTicketComment() {

		return newTicketComment;
	}
}
