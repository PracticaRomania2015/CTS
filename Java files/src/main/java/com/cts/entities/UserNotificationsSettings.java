package com.cts.entities;

import java.util.ArrayList;

public class UserNotificationsSettings {

	private User user;
	private boolean getEmailForTicketResponse;
	private ArrayList<CategoryNotificationsSetting> categoriesNotificationsSettings;

	public UserNotificationsSettings() {
		user = new User();
		getEmailForTicketResponse = false;
		categoriesNotificationsSettings = new ArrayList<CategoryNotificationsSetting>();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isGetEmailForTicketResponse() {
		return getEmailForTicketResponse;
	}

	public void setGetEmailForTicketResponse(boolean getEmailForTicketResponse) {
		this.getEmailForTicketResponse = getEmailForTicketResponse;
	}

	public ArrayList<CategoryNotificationsSetting> getCategoriesNotificationsSettings() {
		return categoriesNotificationsSettings;
	}

	public void setCategoriesNotificationsSettings(
			ArrayList<CategoryNotificationsSetting> categoriesNotificationsSettings) {
		this.categoriesNotificationsSettings = categoriesNotificationsSettings;
	}
}