package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Custom exception thrown in case of invalid lexer input.
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LexerException(String message) {
		super(message);
	}

}
