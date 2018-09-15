package hr.fer.zemris.java.hw07.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class represents the implementation of a program which allows the user
 * to encrypt or decrypt given file using the AES crypto-algorithm and the
 * 128-bit encryption key or calculate and check the SHA-256 file digest.
 * Program is called with 2 arguments in case of determining digest ("checksha"
 * as first argument and the other argument is the name of the file which should
 * be located in root project directory.). In case of encryption or decryption,
 * first argument should be "encrypt" or "decrypt", second argument is the name
 * of the file, and third, the name of the encrypted/decrypted file.
 * 
 * @author Marko Mesarić
 *
 */
public class Crypto {

	/**
	 * Constant which defines the algorithm used for determining digest.
	 */
	private static final String ALGORITHM = "SHA-256";

	/**
	 * Main method used for determining which operation is to be performed.
	 * 
	 * @param args
	 *            array of command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length == 2) {

			if (!args[0].equals("checksha") || !args[1].endsWith(".bin")) {
				System.err.println("Unknown command as first argument.");
				System.exit(1);
			}

			processDigest(args[1]);
		}

		else if (args.length == 3) {

			if (!args[0].equals("encrypt") && !args[0].equals("decrypt")) {
				System.err.println("Unknown command as first argument.");
				System.exit(1);
			}

			try {
				processEncryptDecrypt(args[0], args[1], args[2]);
			} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
					| NoSuchPaddingException e) {
				System.out.println("Error when crypting/decrypting");
				System.exit(1);
			}
		}

		else {
			System.err.println("Program can only be run with 2 or 3 arguments.");
			System.exit(1);
		}
	}

	/**
	 * Method used for performing encryption / decryption based on passed
	 * parameters.
	 * 
	 * @param command
	 *            encrypt / decrypt string
	 * @param filename
	 *            name of the file to be processed
	 * @param createdFilename
	 *            name of the created file
	 * @throws InvalidKeyException
	 *             in case of invalid key
	 * @throws InvalidAlgorithmParameterException
	 *             in case of invalid algorithm parameters
	 * @throws NoSuchAlgorithmException
	 *             in case of non-existent algorithm
	 * @throws NoSuchPaddingException
	 *             in case of error when determining paddig.
	 */
	private static void processEncryptDecrypt(String command, String filename, String createdFilename)
			throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchPaddingException {

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");

		Scanner scanner = new Scanner(System.in);
		String keyText = scanner.nextLine();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");

		String ivText = scanner.nextLine();
		scanner.close();

		boolean encrypt = command.equals("encrypt");

		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		encryptDecrypt(cipher, filename, createdFilename);

		if (encrypt) {
			System.out
					.println("Encryption completed. Generated file " + createdFilename + " based on file " + filename);
		} else {
			System.out
					.println("Decryption completed. Generated file " + createdFilename + " based on file " + filename);
		}

	}

	/**
	 * Method used for determining digest of the given file
	 * 
	 * @param command
	 *            command name
	 * @param file
	 *            of which digest is determined.
	 */
	private static void processDigest(String file) {

		System.out.println("Please provide expected sha-256 digest for " + file);
		System.out.print("> ");

		Scanner sc = new Scanner(System.in);
		String expectedDigest = sc.nextLine();
		sc.close();
		String calculatedDigest = calculateDigest(file);

		if (expectedDigest.equals(calculatedDigest)) {
			System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + file
					+ " does not match expected digest. Digest was: " + calculatedDigest);
		}
	}

	/**
	 * Auxiliary method used for encryption / decryption of the passed file based on
	 * cipher configuration and given algorithm.
	 * 
	 * @param cipher
	 *            configuration for encryption/decryption
	 * @param filename
	 *            name of the file to be encrypted / decrypted
	 * @param createdFilename
	 *            name of the created file
	 */
	private static void encryptDecrypt(Cipher cipher, String filename, String createdFilename) {
		try (InputStream is = new FileInputStream(filename);
				FileOutputStream os = new FileOutputStream(createdFilename)) {

			byte[] buff = new byte[1024];

			while (true) {
				int r = is.read(buff);
				if (r > 1) {
					os.write(cipher.update(buff, 0, r));
				}
				if (r < 1) {
					break;
				}
			}
			os.write(cipher.doFinal());

		} catch (IOException ex) {
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Auxiliary method used for determining the digest of the passed file.
	 * 
	 * @param filename
	 *            of which digest is determined
	 * @return String representation of digest in hex.
	 */
	private static String calculateDigest(String filename) {

		try (InputStream is = new FileInputStream(filename)) {
			byte[] buff = new byte[1024];

			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);

			while (true) {
				int r = is.read(buff);
				if (r > 1) {
					digest.update(buff, 0, r);
				}
				if (r < 1)
					break;
			}
			return Util.byteToHex(digest.digest());

		} catch (IOException ex) {
			return null;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

}
