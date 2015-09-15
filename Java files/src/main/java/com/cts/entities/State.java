package com.cts.entities;

public class State {

	private int stateId;
	private String stateName;

	public State() {

		stateId = 0;
		stateName = "";
	}

	public int getStateId() {

		return stateId;
	}

	public void setStateId(int stateId) {

		this.stateId = stateId;
	}

	public String getStateName() {

		return stateName;
	}

	public void setStateName(String stateName) {

		this.stateName = stateName;
	}
}
