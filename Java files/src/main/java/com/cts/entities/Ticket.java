package com.cts.entities;

import java.util.ArrayList;

public class Ticket {

	private int ticketId;
	private String subject;
	private Category category;
	private State state;
	private int assignedToUserId;
	private ArrayList<TicketComment> comments;
	private TicketComment newTicketComment;

	public Ticket() {

		ticketId = 0;
		subject = "";
		category = new Category();
		state = new State();
		assignedToUserId = 0;
		comments = new ArrayList<TicketComment>();
		newTicketComment = new TicketComment();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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

	public TicketComment getNewTicketComment() {

		return newTicketComment;
	}
}
