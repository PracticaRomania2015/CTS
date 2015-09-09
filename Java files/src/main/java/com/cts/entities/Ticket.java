package com.cts.entities;

import java.util.ArrayList;

public class Ticket {

	private int ticketId;
	private String subject;
	private Category category;
	private State state;
	private User assignedToUser;
	private ArrayList<TicketComment> comments;
	private Priority priority;

	public Ticket() {

		ticketId = 0;
		subject = "";
		category = new Category();
		state = new State();
		assignedToUser = new User();
		comments = new ArrayList<TicketComment>();
		priority = new Priority();
	}

	public Priority getPriority() {

		return priority;
	}

	public void setPriority(Priority priority) {

		this.priority = priority;
	}

	public User getAssignedToUser() {

		return assignedToUser;
	}

	public void setAssignedToUser(User assignedToUser) {

		this.assignedToUser = assignedToUser;
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

	public String getSubject() {

		return subject;
	}
}
