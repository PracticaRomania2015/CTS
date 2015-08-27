package com.cts.communication;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.cts.utils.ConfigReader;

public abstract class Error {

	protected ConfigReader configReader;
	protected String description;
	protected ObjectMapper objectMapper = new ObjectMapper();

	public String getErrorJson(int errorCode) {

		initDescription(errorCode);

		String errorMessageJson = "";

		try {
			errorMessageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return errorMessageJson;

	}

	protected void initDescription(int errorCode) {
	}

}
