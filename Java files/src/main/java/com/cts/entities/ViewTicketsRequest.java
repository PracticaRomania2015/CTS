package com.cts.entities;

public class ViewTicketsRequest {

	private User user;
	private boolean isViewMyTicketsRequest;
	private int requestedPageNumber;
	private String textToSearch;
	private String searchType;

	public ViewTicketsRequest() {

		user = new User();
		isViewMyTicketsRequest = true;
		requestedPageNumber = 0;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isViewMyTicketsRequest() {
		return isViewMyTicketsRequest;
	}

	public void setViewMyTicketsRequest(boolean isViewMyTicketsRequest) {
		this.isViewMyTicketsRequest = isViewMyTicketsRequest;
	}

	public int getRequestedPageNumber() {
		return requestedPageNumber;
	}

	public void setRequestedPageNumber(int requestedPageNumber) {
		this.requestedPageNumber = requestedPageNumber;
	}

	public String getTextToSearch() {
		return textToSearch;
	}

	public void setTextToSearch(String textToSearch) {
		this.textToSearch = textToSearch;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
}
