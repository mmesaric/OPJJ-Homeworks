package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Instances of this class represent a single token which is the product of
 * lexical analysis. Each token consists of TokenType which represents the type
 * of the token and a value of the current token.
 * 
 * @author Marko Mesarić
 *
 */
public class Token {

	/**
	 * Type of generated token
	 */
	private TokenType type;
	/**
	 * Value of the current token
	 */
	private Object value;

	/**
	 * Constructor which sets the values of type and value to given values.
	 * 
	 * @param type
	 *            token type
	 * @param value
	 *            token value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * getter method for type property.
	 * 
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Getter method for value property.
	 * 
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}

}
