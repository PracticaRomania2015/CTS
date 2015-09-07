/**
 * 
 */
package com.cts.utils;

import java.io.File;
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
	private static Properties prop = null;
	private static InputStream input = null;
	private static String filePath = ConfigReader.class.getClassLoader().getResource("config_en.properties").getPath().toString();
	private static long initialModDate = 0;
	
	private static Logger logger = Logger.getLogger(ConfigReader.class.getName());
	
	private void CheckForChanges() {
		return;
	}
	
	protected ConfigReader() {
		prop = new Properties();
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
			Thread checker = new Thread() {
				@SuppressWarnings("static-access")
				public void run() {
					while(true) {
						try {
							logger.info("Periodic configuration file check.");
							long dateMod = new File(filePath).lastModified();
							if ( dateMod != initialModDate ) {
								try {
									input = new FileInputStream(filePath);
									prop.load(input);
									logger.info("Config file was updated.");
								} catch (IOException e) {
									logger.error("File " + filePath + "not found.");
									e.printStackTrace();
								}
								initialModDate = dateMod;
							}
							else {
								logger.info("Config file was not changed.");
							}
							this.sleep(600000);
						} catch (InterruptedException e) {
							logger.error("The check and potential reconfiguration failed !");
							e.printStackTrace();
						}
					}
				}
			};
			checker.start();
	    }
	    return instance;
	}
	
	public String getValueForKey(String key) {
		String returnedValue = prop.getProperty(key);
		logger.info("Got value: [" + returnedValue + "] for the key: [" + key +"]");
		return returnedValue;
	}

}
