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
 * @version 1.0
 */
public class ConfigReader {

	private FileReader fileReader;
	private BufferedReader bufferedReader;

	private static Logger logger = Logger.getLogger(ConfigReader.class.getName());

	private Map<String, String> stuff = new HashMap<String, String>();

	public ConfigReader() {
		// going to change to relative path. again problems with it
		this("D:\\cts\\CTS\\Java files\\src\\main\\webapp\\resources\\errors.txt");
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
			for(String line; (line = bufferedReader.readLine())!=null;){
				
			}
		} catch (IOException e) {
			logger.info("Buffered Reader going ham, this probably will never be shown");
		}
	}

}
