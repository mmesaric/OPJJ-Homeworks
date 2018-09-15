package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Custom Exception which is thrown when parsing given input in case of an error
 * or unknown scenario.
 * 
 * @author Marko Mesarić
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmartScriptParserException(String message) {
		super(message);
	}

}
