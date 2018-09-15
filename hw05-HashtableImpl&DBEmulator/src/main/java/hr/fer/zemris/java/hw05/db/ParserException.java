package hr.fer.zemris.java.hw05.db;

/**
 * Custom exception thrown when there is a syntax error in given query
 * 
 * @author Marko Mesarić
 *
 */
public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParserException(String message) {
		super(message);
	}

}
