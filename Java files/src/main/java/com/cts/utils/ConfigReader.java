/**
 * 
 */
package com.cts.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Mircea.Iordache
 * @version 1.618
 */
public class ConfigReader {

	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private static Map<String, String> stuff = new HashMap<String, String>();

	private static final char BACK_END_CONFIG_LINE_CHARACTER = '#';

	private static Logger logger = Logger.getLogger(ConfigReader.class.getName());

	public ConfigReader() {
		// !!!!!!!!!!!!HERE!!!!!!!!!!!!
		// going to change to relative path. again problems with it
		this("C:\\Users\\AS043881\\Documents\\GitHub\\CTS\\Java files\\src\\main\\webapp\\resources\\config.cfg");
		// !!!!!!!!!!!!!!!!!!!!!!!!!
	}

	public ConfigReader(String path) {
		try {
			fileReader = new FileReader(path);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			logger.info("File " + path + "not found.");
		}

		if (fileReader != null && bufferedReader != null) {
			populateMap();
		} else {
			return;
		}
	}

	private void populateMap() {
		try {
			for (String line; (line = bufferedReader.readLine()) != null;) {
				if (!line.isEmpty() && isBackEndConfigLine(line)) {

					String[] toBeInserted = splitToKeyAndValue(line.substring(1, line.length()));

					stuff.put(toBeInserted[0], toBeInserted[1]);

				} else {
					continue;
				}
			}
		} catch (IOException e) {
			logger.info("Buffered Reader going ham, this probably will never be shown");
		}
	}

	private String[] splitToKeyAndValue(String line) {
		return line.split(",", 2);
	}

	private boolean isBackEndConfigLine(String line) {
		if (line.charAt(0) == BACK_END_CONFIG_LINE_CHARACTER) {
			return true;
		} else {
			return false;
		}
	}

	public String getValueForKey(String key) {
		return stuff.get(key);
	}

}
