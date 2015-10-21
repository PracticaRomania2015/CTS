package com.cts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

@SuppressWarnings("static-access")
public class ConfigReader {

	private static Logger logger = Logger.getLogger(ConfigReader.class.getName());
	private static ConfigReader instance = null;
	private static Properties prop = null;
	private static InputStream input = null;
	private static String filePath = null;
	private static String fileName = "config_en.properties";
	private static long initialModDate = 0;

	public Properties getProp() {
		return prop;
	}

	public InputStream getInput() {
		return input;
	}

	public String getFilePath() {
		return filePath;
	}

	protected ConfigReader() {
		prop = new Properties();
		if (ConfigReader.class.getClassLoader().getResource(fileName) != null) {
			filePath = ConfigReader.class.getClassLoader().getResource(fileName).getPath().toString();
		} else {
			filePath = new File(fileName).getAbsolutePath().toString();
		}

		try {
			input = new FileInputStream(filePath);
			prop.load(input);
		} catch (IOException e) {
			logger.error("File " + filePath + "not found.");
			e.printStackTrace();
		}
	}

	public static ConfigReader getInstance() {
		if (instance == null) {
			instance = new ConfigReader();
			Thread checker = new Thread() {
				public void run() {
					while (true) {
						try {
							logger.debug("Periodic configuration file check.");
							long dateMod = new File(filePath).lastModified();
							if (dateMod != initialModDate) {
								try {
									input = new FileInputStream(filePath);
									prop.load(input);
									logger.info("Config file was updated.");
								} catch (IOException e) {
									logger.error("File " + filePath + "not found.");
									e.printStackTrace();
								}
								initialModDate = dateMod;
							} else {
								logger.debug("Config file was not changed.");
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
		logger.info("Got value: [" + returnedValue + "] for the key: [" + key + "]");
		return returnedValue;
	}
}