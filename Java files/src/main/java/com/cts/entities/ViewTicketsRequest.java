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
	private Category selectedCategory;
	private Priority selectedPriority;
	private State selectedState;

	public ViewTicketsRequest() {
		user = new User();
		typeOfRequest = 0;
		requestedPageNumber = 0;
		ticketsPerPage = 0;
		textToSearch = "";
		searchType = "";
		sortType = "";
		isSearchASC = true;
		selectedCategory = new Category();
		selectedPriority = new Priority();
		selectedState = new State();
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

	public boolean getIsSearchASC() {
		return isSearchASC;
	}

	public void setIsSearchASC(boolean isSearchASC) {
		this.isSearchASC = isSearchASC;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public Priority getSelectedPriority() {
		return selectedPriority;
	}

	public void setSelectedPriority(Priority selectedPriority) {
		this.selectedPriority = selectedPriority;
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(State selectedState) {
		this.selectedState = selectedState;
	}
}