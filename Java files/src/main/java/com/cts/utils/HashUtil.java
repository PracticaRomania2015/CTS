package com.cts.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

	private static final String HASHING_ALGORITHM = "MD5";

	private static MessageDigest messageDigest;

	static {
		try {
			messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
		}
	}

	public static String getHash(String toBeHashed) throws NullPointerException {
		messageDigest.update(toBeHashed.getBytes());

		byte byteData[] = messageDigest.digest();

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < byteData.length; i++) {
			stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return stringBuffer.toString();
	}

}
