package com.cts.communication;

import org.codehaus.jackson.map.ObjectMapper;

public interface Error {

	public ObjectMapper objectMapper = new ObjectMapper();

	public String getErrorJson(int errorCode);

	public void initDescription(int errorCode);

}
