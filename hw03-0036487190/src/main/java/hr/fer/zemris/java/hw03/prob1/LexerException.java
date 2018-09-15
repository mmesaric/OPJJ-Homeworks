package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents the implementation of the custom exception which occurs
 * if the program determines an error when trying to parse a token.
 * 
 * @author Marko Mesarić
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LexerException() {

	}

	public LexerException(String string) {
		super(string);
	}
}
