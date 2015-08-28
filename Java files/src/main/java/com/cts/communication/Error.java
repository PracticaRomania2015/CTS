package com.cts.communication;

import org.codehaus.jackson.map.ObjectMapper;

import com.cts.utils.ConfigReader;

public interface Error {

	public ConfigReader configReader = new ConfigReader();
	public ObjectMapper objectMapper = new ObjectMapper();

	public String getErrorJson(int errorCode);

	public void initDescription(int errorCode);

}
