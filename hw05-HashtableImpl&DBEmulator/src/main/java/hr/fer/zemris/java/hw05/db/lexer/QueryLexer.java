package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Class which implements the solution of lexical analysis in given query
 * language. It iterates through characters of given String query and generates
 * tokens accordingly. In the end, EOF token is returned. If generation of next
 * token is called, exception is thrown since no more tokens can be generated.
 * 
 * @author Marko Mesarić
 *
 */
public class QueryLexer {

	/**
	 * char array used for storing query String
	 */
	private char[] data;
	/**
	 * Variable used for storing last generated token
	 */
	private Token token;
	/**
	 * Variable used for storing current index of char array value.
	 */
	private int currentIndex;

	/**
	 * Constant used for storing length of one char operator.
	 */
	private static final int LENGTH_WHEN_ONE_CHAR_OPERATOR = 1;
	/**
	 * Constant used for storing length of two char operator.
	 */
	private static final int LENGTH_WHEN_TWO_CHAR_OPERATOR = 2;

	/**
	 * Constructor for Query Lexer.
	 * 
	 * @throws NullPointerException
	 *             in case of passed null reference.
	 * @param query
	 *            given query on which lexical analysis is performed
	 */
	public QueryLexer(String query) {

		if (query == null) {
			throw new NullPointerException("Input text can't be null");
		}

		this.data = query.toCharArray();
		this.currentIndex = 0;
	}

	/**
	 * Method used for generating next token according to current character and
	 * rules of lexical analysis of our query language
	 * 
	 * @throws LexerException
	 *             in case of lexically invalid query.
	 * @return generated Token
	 */
	public Token nextToken() {

		StringBuilder queryBuilder = new StringBuilder();

		for (;; currentIndex++) {

			if (currentIndex == data.length) {

				if (queryBuilder.toString().isEmpty()) {
					token = new Token(TokenType.EOF, null);
					currentIndex++;
				} else {
					token = whenEndOfInput(queryBuilder);
				}

				return token;
			}

			if (currentIndex > data.length) {
				throw new LexerException("There are no more tokens after EOF token.");
			}

			char currentChar = data[currentIndex];

			if (Character.isWhitespace(currentChar)) {
				continue;
			}

			else if (Character.isAlphabetic(currentChar)) {
				queryBuilder.append(currentChar);
				return parseName(queryBuilder);
			}

			else if (currentChar == '\"') {
				currentIndex++;
				return parseStringLiteral(queryBuilder);
			}

			else if (currentChar == '>' || currentChar == '<' || currentChar == '=' || currentChar =='!') {
				queryBuilder.append(currentChar);

				if (++currentIndex < data.length) {

					char nextChar = data[currentIndex];

					if ((currentChar == '>' && nextChar == '=') || (currentChar == '=' && nextChar == '=')
							|| (currentChar == '<' && nextChar == '=') || (currentChar == '!' && nextChar == '=')) {
						queryBuilder.append(nextChar);
						token = new Token(TokenType.OPERATOR, queryBuilder.toString());
						currentIndex++;
					}

					else {
						token = new Token(TokenType.OPERATOR, queryBuilder.toString());
					}
					
				} else {
					token = new Token(TokenType.OPERATOR, queryBuilder.toString());
				}
				return token;

			}

		}

	}

	/**
	 * Auxiliary method used for generating Tokens starting with alphabetic
	 * character in given query String.
	 * 
	 * @param queryBuilder
	 *            reference to queryBuilder used for building a query by appending
	 *            characters
	 * @return generated Token
	 */
	private Token parseName(StringBuilder queryBuilder) {
		currentIndex++;

		for (;; currentIndex++) {

			if (data.length == currentIndex) {
				return whenEndOfInput(queryBuilder);
			}

			char currentElement = data[currentIndex];

			if (currentElement == ' ') {
				currentIndex++;
				return whenEndOfInput(queryBuilder);
			}

			if (Character.isAlphabetic(currentElement)) {
				queryBuilder.append(currentElement);
			}

			else {
				return whenEndOfInput(queryBuilder);
			}
		}

	}

	/**
	 * Auxiliary method used for generating Tokens of String literal type in given
	 * query String.
	 * 
	 * @param queryBuilder
	 *            reference to queryBuilder used for building a query by appending
	 *            characters
	 * @throws LexerException
	 *             in case of invalid input of String literal
	 * @return generated Token
	 */
	private Token parseStringLiteral(StringBuilder queryBuilder) {

		for (;; currentIndex++) {

			if (data.length == currentIndex) {
				throw new LexerException("Invalid String literal");
			}

			char currentElement = data[currentIndex];

			if (currentElement == '"') {
				currentIndex++;
				token = new Token(TokenType.STRING_LITERAL, queryBuilder.toString());
				return token;
			}

			if (Character.isAlphabetic(currentElement) || Character.isDigit(currentElement) || currentElement == '*'
					|| Character.isWhitespace(currentElement)) {
				queryBuilder.append(currentElement);
			}

			else {
				throw new LexerException("Invalid string literal");
			}
		}

	}

	/**
	 * Auxiliary method used for generating Token in case of reaching end of line or
	 * end of analyzed given input .
	 * 
	 * @param queryBuilder
	 *            reference to queryBuilder used for building a query by appending
	 *            characters
	 * @throws LexerException
	 *             in case of invalid input of String literal
	 * @return generated Token
	 */
	private Token whenEndOfInput(StringBuilder queryBuilder) {

		String tokenString = queryBuilder.toString();

		if (tokenString.toUpperCase().equals("AND")) {
			token = new Token(TokenType.LOGICAL_OPERATOR, queryBuilder.toString());
		} else if (tokenString.equals("firstName")) {
			token = new Token(TokenType.ATTRIBUTE_NAME, queryBuilder.toString());
		} else if (tokenString.equals("lastName")) {
			token = new Token(TokenType.ATTRIBUTE_NAME, queryBuilder.toString());
		} else if (tokenString.equals("jmbag")) {
			token = new Token(TokenType.ATTRIBUTE_NAME, queryBuilder.toString());
		} else if (tokenString.startsWith("\"") && tokenString.endsWith("\"")) {
			token = new Token(TokenType.STRING_LITERAL, queryBuilder.toString());
		} else if (tokenString.equals("LIKE")) {
			token = new Token(TokenType.OPERATOR, queryBuilder.toString());
		} else if (tokenString.length() == LENGTH_WHEN_ONE_CHAR_OPERATOR
				|| tokenString.length() == LENGTH_WHEN_TWO_CHAR_OPERATOR) {

			token = parseOperatorAtEnd(queryBuilder);

		} else {
			throw new LexerException("Invalid input to lexer");
		}
		return token;

	}

	/**
	 * Auxiliary method used for generating Tokens based on operators in given query
	 * String.
	 * 
	 * @param queryBuilder
	 *            reference to queryBuilder used for building a query by appending
	 *            characters
	 * @throws LexerException
	 *             in case of invalid input of String literal
	 * @return generated Token
	 */
	private Token parseOperatorAtEnd(StringBuilder queryBuilder) {
		String tokenString = queryBuilder.toString();

		if (tokenString.length() == LENGTH_WHEN_ONE_CHAR_OPERATOR) {

			char operator = tokenString.charAt(0);

			if (operator == '>' || operator == '<' || operator == '=') {
				token = new Token(TokenType.OPERATOR, queryBuilder.toString());
			}

			else {
				throw new LexerException("Invalid input to lexer");
			}

		}

		else if (tokenString.length() == LENGTH_WHEN_TWO_CHAR_OPERATOR) {

			if (tokenString.equals(">=") || tokenString.equals("<=") || tokenString.equals("!=")) {
				token = new Token(TokenType.OPERATOR, queryBuilder.toString());
			}

			else {
				throw new LexerException("Invalid input to lexer");
			}

		}

		return token;

	}

	/**
	 * Method which returns last generated Token.
	 * 
	 * @throws NullPointerException
	 *             in case if token is a null reference
	 * @return last generated token
	 */
	public Token getToken() {
		if (token == null) {
			throw new NullPointerException("There are no generated tokens yet.");
		}

		return token;
	}

}
