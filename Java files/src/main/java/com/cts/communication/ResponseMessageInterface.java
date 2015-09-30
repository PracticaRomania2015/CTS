package com.cts.communication;

import org.codehaus.jackson.map.ObjectMapper;

public interface ResponseMessageInterface {

	public ObjectMapper objectMapper = new ObjectMapper();

	public String getMessageJSON(ResponseValues responseValue);

}