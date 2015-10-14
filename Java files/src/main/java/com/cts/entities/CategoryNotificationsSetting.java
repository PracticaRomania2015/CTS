package com.cts.entities;

public class CategoryNotificationsSetting {

	private Category category;
	private boolean getEmailForNewTicket;
	private boolean getEmailForNewComment;

	public CategoryNotificationsSetting() {
		category = new Category();
		getEmailForNewTicket = false;
		getEmailForNewComment = false;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isGetEmailForNewTicket() {
		return getEmailForNewTicket;
	}

	public void setGetEmailForNewTicket(boolean getEmailForNewTicket) {
		this.getEmailForNewTicket = getEmailForNewTicket;
	}

	public boolean isGetEmailForNewComment() {
		return getEmailForNewComment;
	}

	public void setGetEmailForNewComment(boolean getEmailForNewComment) {
		this.getEmailForNewComment = getEmailForNewComment;
	}
}
