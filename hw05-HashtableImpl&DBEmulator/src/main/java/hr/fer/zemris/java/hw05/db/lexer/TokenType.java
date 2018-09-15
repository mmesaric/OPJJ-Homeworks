package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Enumerator used for different token types.
 * @author Marko Mesarić
 *
 */
public enum TokenType {
	/**
	 * In case of values "firstName", "lastName" or "jmbag" which are database columns
	 */
	 ATTRIBUTE_NAME, 
	 /**
	  * Operator used for queries (>, >=, <, <=, =, !=, LIKE)
	  */
	 OPERATOR, 
	 /**
	  * String literal value in query
	  */
	 STRING_LITERAL, 
	 /**
	  * Logical operator AND used for concatenating conditional expressions
	  */
	 LOGICAL_OPERATOR, 
	 /**
	  * EOF token which comes as last token
	  */
	 EOF

}
