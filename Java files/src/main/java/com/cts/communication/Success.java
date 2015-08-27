package com.cts.communication;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.cts.utils.ConfigReader;

public class Success {

	private ConfigReader configReader;
	private ObjectMapper objectMapper = new ObjectMapper();

	private String description;
	private String success;

	public Success() {
		initAll();

		description = success;
	}

	private void initAll() {
		configReader = new ConfigReader();

		success = configReader.getValueForKey("success");
	}

	public String getDescription() {
		return description;
	}

	public String getSuccessJson(int successCode) {

		String successMessageJson = "";

		try {
			successMessageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return successMessageJson;
	}
}
