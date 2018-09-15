package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class consists of auxiliary methods used for hashing password.
 * 
 * @author Marko Mesarić
 *
 */
public class Utils {

	/**
	 * Auxiliary method used for transforming given byte array to hex string.
	 * 
	 * @param byteArray
	 *            byte array to be transformed.
	 * @return hex string representation of given byte array.
	 */
	public static String byteToHex(byte[] byteArray) {
		if (byteArray.length == 0) {
			return "";
		}

		int doubleLength = byteArray.length * 2;

		StringBuilder stringBuilder = new StringBuilder(doubleLength);
		for (byte singleByte : byteArray)
			stringBuilder.append(String.format("%02x", singleByte));

		return stringBuilder.toString();
	}

	/**
	 * Auxiliary method which hashes the given password.
	 * 
	 * @param password
	 *            password to be hashed
	 * @return hashed password
	 */
	public static String getHex(String password) {
		byte[] buff = null;
		try {
			buff = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // ignorable
		}

		digest.update(buff);

		return Utils.byteToHex(digest.digest());
	}
}
