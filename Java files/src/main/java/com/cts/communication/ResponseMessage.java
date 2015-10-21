package com.cts.communication;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Handle the response messages for controllers.
 */
@JsonSerialize
public class ResponseMessage implements ResponseMessageInterface {

	private static Logger logger = Logger.getLogger(ResponseMessage.class.getName());

	private String type;
	private String description;
	private Object data;

	public ResponseMessage(Object data) {
		this.data = data;
	}

	public ResponseMessage() {
		this(null);
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String getMessageJSON(ResponseValues responseValue) {

		this.type = responseValue.getType();
		this.description = responseValue.getDescription();

		try {
			String response = objectMapper.writeValueAsString(this);
			logger.debug("JSON response created successfully!");
			return response;
		} catch (IOException e) {
			logger.error("Could not create JSON response!");
			return "Could not create JSON";
		}
	}
}