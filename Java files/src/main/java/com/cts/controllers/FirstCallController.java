package com.cts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the index page.
 */
@Controller
public class FirstCallController{

	/**
	 * Index controller
	 * 
	 * @return index page of the application
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String firstThingCalled() {

		return "index";
	}
}
