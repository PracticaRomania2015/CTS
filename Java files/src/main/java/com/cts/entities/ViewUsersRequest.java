package com.cts.entities;

public class ViewUsersRequest {

	private int requestedPageNumber;
	private int usersPerPage;
	private String textToSearch;
	private String searchType;
	private String sortType;
	private boolean isSearchASC;

	public ViewUsersRequest() {

		requestedPageNumber = 0;
		usersPerPage = 0;
		textToSearch = "";
		searchType = "";
		sortType = "";
		isSearchASC = true;
	}

	public int getRequestedPageNumber() {

		return requestedPageNumber;
	}

	public void setRequestedPageNumber(int requestedPageNumber) {

		this.requestedPageNumber = requestedPageNumber;
	}

	public int getUsersPerPage() {

		return usersPerPage;
	}

	public void setUsersPerPage(int usersPerPage) {

		this.usersPerPage = usersPerPage;
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