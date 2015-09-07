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

	public Properties getProp() {
		return prop;
	}

	public InputStream getInput() {
		return input;
	}

	public String getFilePath() {
		return filePath;
	}

	private static ConfigReader instance = null;
	private Properties prop = null;
	private InputStream input = null;
	private String filePath = null;
	
	private static Logger logger = Logger.getLogger(ConfigReader.class.getName());
	
	protected ConfigReader() {
		prop = new Properties();
		filePath = ConfigReader.class.getClassLoader().getResource("config_en.properties").getPath().toString();
		try {
			input = new FileInputStream(filePath);
			prop.load(input);
		} catch (IOException e) {
			logger.error("File " + filePath + "not found.");
			e.printStackTrace();
		}
	}
	
	public static ConfigReader getInstance() {
		if(instance == null) {
			instance = new ConfigReader();
	    }
	    return instance;
	}
	
	public String getValueForKey(String key) {
		String returnedValue = prop.getProperty(key);
		logger.info("Got value: [" + returnedValue + "] for the key: [" + key +"]");
		return returnedValue;
	}

}
