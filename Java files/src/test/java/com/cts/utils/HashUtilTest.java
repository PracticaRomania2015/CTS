package com.cts.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HashUtilTest {

	@Test
	public void testGood() {
		assertEquals("e10adc3949ba59abbe56e057f20f883e", HashUtil.getHash("123456"));
	}

}
