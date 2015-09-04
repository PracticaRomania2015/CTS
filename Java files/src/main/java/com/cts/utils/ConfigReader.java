/**
 * 
 */
package com.cts.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Mircea.Iordache
 * @version 1.618
 */
public class ConfigReader {

	Properties prop = null;
	InputStream input = null;
	String filePath = null;
	
	private static Logger logger = Logger.getLogger(ConfigReader.class.getName());
	
	public ConfigReader() {
		prop = new Properties();
		filePath = "config_en.properties";
		try {
			input = new FileInputStream(filePath);
			prop.load(input);
		} catch (IOException e) {
			logger.error("File " + filePath + "not found.");
			e.printStackTrace();
		}
	}
	
	public String getValueForKey(String key) {
		String returnedValue = prop.getProperty(key);
		logger.info("Got value: [" + returnedValue + "] for the key: [" + key +"]");
		return prop.getProperty(returnedValue);
	}

}
