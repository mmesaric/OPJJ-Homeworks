package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw07.shell.Environment;

/**
 * Class which contains methods frequently used throughout whole project. Mostly
 * contains methods used for manipulating the file system and transformations
 * between hex String and byte array.
 * 
 * @author Marko Mesarić
 *
 */
public class Util {

	public static final String STACK_KEY = "cdstack";

	/**
	 * Auxiliary method used for transforming given hex string to byte array
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid hex
	 * @param keyText
	 *            hexadecimal string to be transformed
	 * @return calculated byte array of passed string
	 */
	public static byte[] hexToByte(String keyText) {
		if (keyText.isEmpty()) {
			return new byte[0];
		}
		int keyLength = keyText.length();

		// try {
		// Long.parseLong(keyText, 16);
		// } catch (NumberFormatException exc) {
		// throw new IllegalArgumentException("Invalid hex string.");
		// }

		if (keyLength % 2 != 0 || !keyText.matches("^[0-9a-fA-F]+$")) {
			throw new IllegalArgumentException("Passed string is odd-sized");
		}
		byte[] byteArray = new byte[keyLength / 2];

		for (int i = 0; i < keyLength; i += 2) {

			byteArray[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		return byteArray;

	}

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
	 * Auxiliary method used for reading file contents in given encoding.
	 * 
	 * @param file
	 *            which content is to be read
	 * @param encoding
	 *            encoding in which file is to be read
	 * @param environment
	 *            environment in which shell is executing.
	 * @throws IOException
	 *             in case of an error when reading from file.
	 */
	public static void readFromFile(String file, String encoding, Environment environment) throws IOException {

		if (encoding.equals("")) {
			encoding = Charset.defaultCharset().toString();
		}

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), encoding))) {

			String line = "";
			while ((line = br.readLine()) != null) {
				environment.writeln(line);
			}
		}
	}

	/**
	 * Auxiliary method used for retrieving the path String from given arguments in
	 * case of path being passed in "" for paths containing space and special
	 * characters.
	 * 
	 * @param arguments
	 *            extra arguments passed with command
	 * @return path of the file passed in arguments
	 */
	public static String getPath(String arguments) {
		char[] argumentsCharArray = arguments.toCharArray();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 1; i < argumentsCharArray.length; i++) {
			if (argumentsCharArray[i] == '"') {
				break;
			}
			stringBuilder.append(argumentsCharArray[i]);
		}

		try {
			Paths.get(stringBuilder.toString());
			return stringBuilder.toString();
		} catch (InvalidPathException | NullPointerException ex) {
			return "";
		}
	}

	/**
	 * Auxiliary method used for retrieving the additional arguments in case of
	 * arguments being passed in quotation marks for groups containing space and
	 * special characters.
	 * 
	 * @param arguments
	 *            extra arguments passed with command
	 * @return arguments without quotation marks
	 */
	public static String getQuotationsArgument(String arguments) {
		char[] argumentsCharArray = arguments.toCharArray();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 1; i < argumentsCharArray.length; i++) {
			if (argumentsCharArray[i] == '"') {
				break;
			}
			stringBuilder.append(argumentsCharArray[i]);
		}

		return stringBuilder.toString();
	}

	/**
	 * Auxiliary method used for determining if passed path is valid or not.
	 * 
	 * @param arguments
	 *            paths which are checked
	 * @return true of valid, false otherwise.
	 */
	public static boolean isValidPath(String arguments) {

		if (arguments.trim().startsWith("\"")) {
			return !getPath(arguments).equals("");
		}

		else {
			String[] splitArguments = arguments.trim().split("\\s+");

			if (splitArguments.length == 1 || splitArguments.length == 2) {
				try {
					Paths.get(splitArguments[0]);
					return true;
				} catch (InvalidPathException | NullPointerException ex) {
					return false;
				}
			}
			return false;
		}
	}

	/**
	 * Method used for copying contents of the given file to another path which is
	 * also passed.
	 * 
	 * @param filename
	 *            name of the file to be copied
	 * @param createdFilename
	 *            path name of the copied file
	 * @throws IOException
	 *             in case of an error when copying the file.
	 */
	public static void copyFile(String filename, String createdFilename) throws IOException {
		try (InputStream is = new FileInputStream(filename);
				FileOutputStream os = new FileOutputStream(createdFilename)) {

			byte[] buff = new byte[1024];

			while (true) {
				int r = is.read(buff);
				if (r > 1) {
					os.write(buff, 0, r);
				}
				if (r < 1) {
					break;
				}
			}
		}
	}

	/**
	 * Auxiliary method used for reading from given file and formatting output of
	 * hexdump command.
	 * 
	 * @param filename
	 *            name of file to be read
	 * @return output of hexdump as String
	 * @throws IOException
	 *             in case of failing to read the file.
	 */
	public static String readFileForHexDump(String filename) throws IOException {
		StringBuilder outputBuilder = new StringBuilder();
		int counter = 0;

		try (InputStream is = new FileInputStream(filename)) {

			byte[] buff = new byte[16];

			while (true) {
				int r = is.read(buff);

				if (r < 1) {
					break;
				}

				outputBuilder.append(String.format("%08d", counter));
				counter += 10;
				outputBuilder.append(": ");

				for (int i = 0; i < r; i++) {
					if (i == 8) {
						outputBuilder.append("|");
					}
					byte[] pom = new byte[] { buff[i] };
					String hex = byteToHex(pom);
					outputBuilder.append(hex.toUpperCase()).append(" ");

				}
				if (r == buff.length) {
					outputBuilder.append("| ");
				} else {
					for (int i = r; i < buff.length; i++) {

						if (i == 8) {
							outputBuilder.append("|");
						}
						outputBuilder.append("   ");
					}
					outputBuilder.append("| ");
				}

				for (int i = 0; i < r; i++) {
					if (buff[i] < 32 || buff[i] > 127) {
						buff[i] = '.';
					}
				}
				outputBuilder.append(new String(buff)).append("\n");
				buff = new byte[16];
			}
		}

		return outputBuilder.toString();
	}

}
