package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class RecoveryPasswordError implements Error {

	private String incorrectSpecifiedEmailForRecoverError;
	private String unknownError;
	private String description;

	private ConfigReader configReader;

	public RecoveryPasswordError() {

		initAll();
	}

	private void initAll() {
		configReader = new ConfigReader();

		incorrectSpecifiedEmailForRecoverError = configReader.getValueForKey("incorrectSpecifiedEmailForRecoverError");
		unknownError = configReader.getValueForKey("unknownError");
	}

	@Override
	public void initDescription(int errorCode) {

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

	@Override
	public String getErrorJson(int errorCode) {
		initDescription(errorCode);

		String errorMessageJson = "";

		try {
			errorMessageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return errorMessageJson;
	}

	public String getDescription() {
		return description;
	}
}
