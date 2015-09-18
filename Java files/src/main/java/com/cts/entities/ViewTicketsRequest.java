package com.cts.entities;

public class ViewTicketsRequest {

	private User user;
	private int typeOfRequest;
	private int requestedPageNumber;
	private int ticketsPerPage;
	private String textToSearch;
	private String searchType;
	private String sortType;
	private boolean isSearchASC;

	public ViewTicketsRequest() {

		user = new User();
		typeOfRequest = 0;
		requestedPageNumber = 0;
		ticketsPerPage = 0;
		textToSearch = "";
		searchType = "";
		sortType = "";
		isSearchASC = true;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public int getTypeOfRequest() {

		return typeOfRequest;
	}

	public void setTypeOfRequest(int typeOfRequest) {

		this.typeOfRequest = typeOfRequest;
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

	public int getTicketsPerPage() {

		return ticketsPerPage;
	}

	public void setTicketsPerPage(int ticketsPerPage) {

		this.ticketsPerPage = ticketsPerPage;
	}

	public String getSortType() {

		return sortType;
	}

	public void setSortType(String sortType) {

		this.sortType = sortType;
	}

	public boolean isSearchASC() {

		return isSearchASC;
	}

	public void setSearchASC(boolean isSearchASC) {

		this.isSearchASC = isSearchASC;
	}
}