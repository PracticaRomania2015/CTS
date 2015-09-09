package com.cts.entities;

public class Priority {

	private int priorityId;
	private String priorityName;

	public Priority() {

		priorityId = 0;
		priorityName = "";
	}

	public int getPriorityId() {

		return priorityId;
	}

	public void setPriorityId(int priorityId) {

		this.priorityId = priorityId;
	}

	public String getPriorityName() {

		return priorityName;
	}

	public void setPriorityName(String priorityName) {

		this.priorityName = priorityName;
	}

}
