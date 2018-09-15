package hr.fer.zemris.java.hw07.shell.commands.name;

/**
 * Custom exception thrown in case of syntax error when parsing the expression
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
