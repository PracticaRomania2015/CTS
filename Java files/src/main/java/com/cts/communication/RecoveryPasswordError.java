package com.cts.communication;

public class RecoveryPasswordError extends Error {

	private String incorrectSpecifiedEmailForRecoverError;
	private String unknownError;

	public RecoveryPasswordError() {

		initAll();
	}

	private void initAll() {

		incorrectSpecifiedEmailForRecoverError = configReader.getValueForKey("incorrectSpecifiedEmailForRecoverError");
		unknownError = configReader.getValueForKey("unknownError");
	}

	@Override
	protected void initDescription(int errorCode) {

		switch (errorCode) {
		case 1: {
			description = incorrectSpecifiedEmailForRecoverError;
			break;
		}
		default: {
			description = unknownError;
			break;
		}
		}
	}
}
