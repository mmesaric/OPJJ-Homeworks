package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Class used as model for a single token used in lexical Analysis of given
 * query language
 * 
 * @author Marko Mesarić
 *
 */
public class Token {

	/**
	 * Type of token
	 */
	private TokenType type;
	/**
	 * Value of token
	 */
	private Object value;

	/**
	 * Constructor for token which sets the values to given ones.
	 * 
	 * @param type
	 *            token type to be set
	 * @param value
	 *            token value to be set
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for token type
	 * 
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Getter for token value.
	 * 
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}

}
