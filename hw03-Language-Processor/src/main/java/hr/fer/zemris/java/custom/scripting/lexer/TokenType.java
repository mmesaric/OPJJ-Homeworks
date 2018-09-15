package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Possible types of a single token which can appear in given processed language
 * 
 * @author Marko
 *
 */
public enum TokenType {

	FUNCTION_NAME, IDENTIFIER, OPENING_TAG, CLOSING_TAG, SYMBOL, TEXT, INT, DOUBLE, STRING, EOF

}
