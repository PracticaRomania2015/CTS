package com.cts.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FirstCallControllerTest {

	private static FirstCallController firstCallController;

	@BeforeClass
	public static void initialize() {

		firstCallController = new FirstCallController();
	}

	@Test
	public void testFirstThingCalled() {

		assertEquals("index", firstCallController.firstThingCalled());
	}
}