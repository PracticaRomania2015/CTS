package com.cts.communication;

import org.codehaus.jackson.map.ObjectMapper;

public interface ResponseMessage {

	public ObjectMapper objectMapper = new ObjectMapper();

	public String getMessageJSON(ResponseValues responseValue);

	public void initDescription(ResponseValues responseValue);

}