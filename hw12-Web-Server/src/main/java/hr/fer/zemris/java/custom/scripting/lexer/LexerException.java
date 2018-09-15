package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Custom exception thrown when there is a mistake in given user input when
 * generating tokens.
 * 
 * @author Marko Mesarić
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LexerException(String message) {
		super(message);
	}

}
