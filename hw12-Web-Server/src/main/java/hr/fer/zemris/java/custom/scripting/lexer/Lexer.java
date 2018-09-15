package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class represents a simple Lexer implementation which can work with text
 * blocks or tag blocks. Character escaping is possible in both states of lexer
 * if input is according to given rules. Possible tokens are numbers,
 * identifiers, for loops, function names, opening and closing blocks, strings
 * and eof. It consists of two separate classes, for each of given states.
 * 
 * @author Marko Mesarić
 *
 */
public class Lexer {

	/**
	 * Interface which specifies a single method to implement for generating and
	 * returning next Token
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public interface Tokenizer {
		Token nextToken();
	}

	/**
	 * Class which gives the solution for generating tokens when parsing blocks of
	 * text. Iterates through data array char by char and appends them accordingly.
	 * Can generate text or EOF tokens. Implements and overrides next token method
	 * responsible for generating next tokens and retrieving last generated tokens.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	class TextTokenizer implements Tokenizer {

		/**
		 * Generates and returns the next Token.
		 * 
		 * @return generated Token
		 */
		@Override
		public Token nextToken() {

			StringBuilder textBuilder = new StringBuilder();

			for (;; currentIndex++) {

				if (currentIndex == data.length) {
					if (textBuilder.toString().isEmpty()) {
						token = new Token(TokenType.EOF, null);
						currentIndex++;
					} else {
						token = new Token(TokenType.TEXT, textBuilder.toString());
					}
					return token;
				}

				if (currentIndex > data.length) {
					throw new LexerException("There are no more tokens after EOF token");
				}

				if (data[currentIndex] == '\\') {
					checkWhenEscapeSign(textBuilder);
					continue;
				}

				else if (data[currentIndex] == '{') {
					if (!textBuilder.toString().isEmpty()) {
						token = new Token(TokenType.TEXT, textBuilder.toString());
						return token;
					}
					Token toBeReturned = generateOpeningTag(data[currentIndex], textBuilder);
					if (toBeReturned!=null) {
						return token;
					}
//					return generateOpeningTag(data[currentIndex], textBuilder);

				}

				else {
					textBuilder.append(data[currentIndex]);
				}

			}
		}

		/**
		 * Helper method for generating tokens consisting of Opening tag.
		 * 
		 * @param currentElement
		 *            current element of data array
		 * @param textBuilder
		 *            reference to stringbuilder which appends chars of text.
		 * @return generated token
		 * @throws LexerException
		 *             in case of wrong string input.
		 */
		public Token generateOpeningTag(char currentElement, StringBuilder textBuilder) {

			if (data.length == currentIndex + 1) {
				throw new LexerException("'\\' Escape sequence is not complete.");
			}

			if (data[currentIndex + 1] == '$') {
				textBuilder.append(currentElement).append(data[currentIndex + 1]);
				currentIndex += 2;
				token = new Token(TokenType.OPENING_TAG, textBuilder.toString());
				return token;
			} else {
//				throw new LexerException("Input opening tag is not valid. " + currentIndex);
				textBuilder.append(data[currentIndex]);
				return null;
			}

		}

		/**
		 * Helper method for generating tokens which include escape sign (\)
		 * 
		 * @param textBuilder
		 *            reference to strinbuilder object
		 * @throws LexerException
		 *             in case of wrong input.
		 */
		public void checkWhenEscapeSign(StringBuilder textBuilder) {

			if (data.length == currentIndex + 1) {
				throw new LexerException("'\\' Escape sequence is not complete.");
			}

			if (data[currentIndex + 1] == '\\') {
				textBuilder.append(currentIndex);
			} else if (data[currentIndex + 1] == '{') {
				textBuilder.append(data[currentIndex]);
				textBuilder.append(data[currentIndex + 1]);
			} else {
				throw new LexerException("After '\\' can come only '\' or '{' for text tokens");
			}
			currentIndex++;
		}

	}

	/**
	 * Class which gives the solution for generating tokens when parsing blocks of
	 * tags. Iterates through data array char by char and appends them accordingly.
	 * Can generate number, identifier, symbol, string, function name or EOF tokens.
	 * Implements and overrides next token method responsible for generating next
	 * tokens and retrieving last generated tokens.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	class TagTokenizer implements Tokenizer {

		@Override
		public Token nextToken() {

			StringBuilder tagBuilder = new StringBuilder();

			for (;; currentIndex++) {

				if (currentIndex == data.length) {
					return checkIfEnd(tagBuilder);
				}

				if (currentIndex > data.length) {
					throw new LexerException("There are no more tokens after EOF token");
				}

				char currentElement = data[currentIndex];

				if (Character.isWhitespace(currentElement)) {
					continue;
				}

				if (currentElement == '+' || currentElement == '*' || currentElement == '/' || currentElement == '^'
						|| currentElement == '-') {
					if (generateSymbol(currentElement) != null) {
						return token;
					}
				}

				if (Character.isAlphabetic(currentElement)) {
					tagBuilder.append(currentElement);
					return generateIdentifier(tagBuilder);
				}

				if (currentElement == '-') {
					if (data.length != currentIndex + 1) {
						if (Character.isDigit(data[currentIndex + 1])) {
							tagBuilder.append(currentElement).append(data[currentIndex + 1]);
							currentIndex++;
							return generateNumber(tagBuilder);
						}
					}
				}

				if (Character.isDigit(currentElement)) {
					tagBuilder.append(currentElement);
					return generateNumber(tagBuilder);
				}

				switch (currentElement) {
				// case '{':
				// return generateOpeningTag(currentElement, tagBuilder);
				case '$':
					return generateClosingTag(currentElement, tagBuilder);
				case '@':
					if (data.length == currentIndex + 1) {
						throw new LexerException("Function name is not valid.");
					}
					if (Character.isAlphabetic(data[currentIndex + 1])) {
						currentIndex++;
						tagBuilder.append(currentElement).append(data[currentIndex]);
						return generateFunctionName(tagBuilder);
					} else {
						throw new LexerException("Function name must start with a letter.");
					}
				case '"':
					return generateString(tagBuilder);
				case '=':
					currentIndex++;
					token = new Token(TokenType.IDENTIFIER, currentElement);
					return token;
				default:
					throw new LexerException("Wrong input.");
				}

				//

			}

		}

		/**
		 * Helper method which is called in the end of iteration through data array.
		 * Determines if token is type EOF or TEXT
		 * 
		 * @param tagBuilder
		 *            reference to StringBuilder object
		 * @return generated Token
		 */
		public Token checkIfEnd(StringBuilder tagBuilder) {
			if (tagBuilder.toString().isEmpty()) {
				token = new Token(TokenType.EOF, null);
				currentIndex++;
			} else {
				token = new Token(TokenType.TEXT, tagBuilder.toString());
			}
			return token;
		}

		/**
		 * Method responsible for generating number. Called upon first occurrence of a
		 * digit. In case of decimal point, calls the method of generating double Token
		 * accordingly.
		 * 
		 * @param tagBuilder
		 *            reference to stringbuilder object responsible for generating
		 *            number sequence.
		 * @return generated Token
		 * @throws LexerException
		 *             in case of invalid input.
		 */
		public Token generateNumber(StringBuilder tagBuilder) {

			currentIndex++;

			for (;; currentIndex++) {

				if (data.length == currentIndex) {
					token = new Token(TokenType.INT, tagBuilder.toString());
					return token;
				}

				char currentElement = data[currentIndex];

				if (currentElement == ' ' || currentElement =='\n' || currentElement == '\t' || currentElement== '\r') {
					currentIndex++;
					token = new Token(TokenType.INT, tagBuilder.toString());
					return token;
				}

				if (Character.isDigit(currentElement)) {
					tagBuilder.append(currentElement);
				}

				else if (currentElement == '.') {
					tagBuilder.append(currentElement);
					return appendDecimalPart(tagBuilder);
				}

				else if (currentElement == '$') {
					token = new Token(TokenType.INT, tagBuilder.toString());
					return token;
				}

				else {
					throw new LexerException("Number must start with digit, and can contain only digits."
							+ "Error on index: " + currentIndex);
				}
			}
		}

		/**
		 * Helper method which is called when generating number sequence and finding a
		 * decimal point. Iterates through the rest of sequence and appends the digits
		 * accordingly.
		 * 
		 * @param tagBuilder
		 *            reference to stringbuilder object responsible for generating
		 *            number sequence.
		 * 
		 * @return generated Token
		 * @throws LexerException
		 *             in case of invalid input.
		 */
		public Token appendDecimalPart(StringBuilder tagBuilder) {

			currentIndex++;

			for (;; currentIndex++) {

				if (data.length == currentIndex) {
					token = new Token(TokenType.DOUBLE, tagBuilder.toString());
					return token;
				}

				char currentElement = data[currentIndex];

				if (currentElement == ' ') {
					currentIndex++;
					token = new Token(TokenType.DOUBLE, tagBuilder.toString());
					return token;
				}

				if (Character.isDigit(currentElement)) {
					tagBuilder.append(currentElement);
				}

				else if (currentElement == '$') {
					token = new Token(TokenType.DOUBLE, tagBuilder.toString());
					return token;
				} else {
					throw new LexerException("Number must start with digit, and can contain only digits.");
				}
			}

		}

		/**
		 * Helper method which is called when generating identifier sequence and
		 * alphabetic char. Iterates through the rest of sequence and appends the
		 * characters accordingly.
		 * 
		 * @param tagBuilder
		 *            reference to stringbuilder object responsible for generating
		 *            identifier sequence.
		 * 
		 * @return generated Token
		 * @throws LexerException
		 *             in case of invalid input.
		 */
		public Token generateIdentifier(StringBuilder tagBuilder) {

			currentIndex++;

			for (;; currentIndex++) {

				if (data.length == currentIndex) {
					token = new Token(TokenType.IDENTIFIER, tagBuilder.toString());
					return token;
				}

				char currentElement = data[currentIndex];

				if (currentElement == ' ') {
					currentIndex++;
					token = new Token(TokenType.IDENTIFIER, tagBuilder.toString());
					return token;
				}

				if (Character.isAlphabetic(currentElement) || Character.isDigit(currentElement)
						|| currentElement == '_') {
					tagBuilder.append(currentElement);
				} else if (currentElement == '$') {
					token = new Token(TokenType.IDENTIFIER, tagBuilder.toString());
					return token;
				}

				else {
					throw new LexerException(
							"Name of identifier must start with letter, and can contain only digits, letters and '_'");
				}
			}
		}

		/**
		 * Helper method which is called when generating symbols(operators).
		 * 
		 * @param currentElement
		 *            current element in the data array
		 * @return generated Token
		 * @throws LexerException
		 *             in case of invalid input.
		 */
		public Token generateSymbol(char currentElement) {

			if (currentElement == '-') {

				if (data.length == currentIndex + 1) {
					throw new LexerException("'\\' Escape sequence is not complete.");
				}

				if (!Character.isDigit(data[currentIndex + 1])) {
					token = new Token(TokenType.SYMBOL, data[currentIndex]);
					currentIndex++;
					return token;
				}
				return null;
			} else {
				currentIndex++;
				token = new Token(TokenType.SYMBOL, currentElement);
				return token;
			}
		}

		/**
		 * Helper method for generating tokens consisting of closing tag.
		 * 
		 * @param currentElement
		 *            current element of data array
		 * @param textBuilder
		 *            reference to stringbuilder which appends chars of text.
		 * @return generated token
		 * @throws LexerException
		 *             in case of wrong string input.
		 */
		public Token generateClosingTag(char currentElement, StringBuilder tagBuilder) {

			if (data.length == currentIndex + 1) {
				throw new LexerException("'\\' Escape sequence is not complete.");
			}

			if (data[currentIndex + 1] == '}') {
				tagBuilder.append(currentElement).append(data[currentIndex + 1]);
				currentIndex += 2;
				token = new Token(TokenType.CLOSING_TAG, tagBuilder.toString());
				return token;
			} else {
				throw new LexerException("Input closing tag is not valid.");
			}
		}

		/**
		 * Helper method which is called when generating function name sequence. Called
		 * upon first occurence of '@' char. Iterates through the rest of sequence and
		 * appends the characters accordingly.
		 * 
		 * @param tagBuilder
		 *            reference to stringbuilder object responsible for generating
		 *            method name sequence.
		 * 
		 * @return generated Token
		 * @throws LexerException
		 *             in case of invalid input.
		 */
		public Token generateFunctionName(StringBuilder tagBuilder) {

			currentIndex++;

			for (;; currentIndex++) {

				if (data.length == currentIndex) {
					token = new Token(TokenType.FUNCTION_NAME, tagBuilder.toString().substring(1));
					return token;
				}

				char currentElement = data[currentIndex];

				if (currentElement == ' ' || currentElement =='\n' || currentElement == '\t' || currentElement== '\r') {
					currentIndex++;
					token = new Token(TokenType.FUNCTION_NAME, tagBuilder.toString().substring(1));
					return token;
				}

				if (Character.isAlphabetic(currentElement) || Character.isDigit(currentElement)
						|| currentElement == '_') {
					tagBuilder.append(currentElement);
				} else if (currentElement == '$') {
					token = new Token(TokenType.FUNCTION_NAME, tagBuilder.toString().substring(1));
					return token;
				} else {
					throw new LexerException("Exception on index: " + currentIndex
							+ "Name of function must start with letter, and can contain only digits, letters and '_'");
				}
			}

		}

		/**
		 * Helper method which is called when generating string sequence. Called upon
		 * finding the first occurrence of '"'. Iterates through the rest of sequence
		 * and appends the characters accordingly.
		 * 
		 * @param tagBuilder
		 *            reference to stringbuilder object responsible for generating
		 *            string sequence.
		 * 
		 * @return generated Token
		 * @throws LexerException
		 *             in case of invalid input.
		 */
		public Token generateString(StringBuilder tagBuilder) {

			currentIndex++;

			for (;; currentIndex++) {

				if (data.length == currentIndex) {
					throw new LexerException("Wrong string input");
				}

				char currentElement = data[currentIndex];

				if (currentElement == '"') {
					currentIndex++;
					token = new Token(TokenType.STRING, tagBuilder.toString());
					return token;
				}
				
//				if (Character.isWhitespace(currentElement)) {
//					tagBuilder.append(currentElement);
//					continue;
//				}

				if (currentElement == '\\') {
					checkWhenEscape(currentElement, tagBuilder);
				}

				else {
					tagBuilder.append(currentElement);
				}

			}
		}

		/**
		 * Helper method for generating tokens which include escape sign (\)
		 * 
		 * @param textBuilder
		 *            reference to stringbuilder object
		 * @throws LexerException
		 *             in case of wrong input.
		 */
		public void checkWhenEscape(char currentElement, StringBuilder tagBuilder) {
			if (data.length == currentIndex + 1) {
				throw new LexerException("Wrong string input");
			}

			char nextChar = data[currentIndex + 1];
			
			if (nextChar == 'n') {
				tagBuilder.append("\n");
			}
			
			else if (nextChar == 'r') {
				tagBuilder.append("\r");
			}

			else if (nextChar == 't') {
				tagBuilder.append("\t");
			}
			
			else if (nextChar == '\\' ) {
				tagBuilder.append(nextChar);
			} else if (nextChar == '"') {
				tagBuilder.append(nextChar);
			} else {
				throw new LexerException("In strings, after '\\' can appear only '\\' or '\"' " + " Index: " +currentIndex);
			}
			currentIndex++;
		}
	}

	/**
	 * char array of data which lexer should handle.
	 */
	private char[] data;

	/**
	 * Last generated token
	 */
	private Token token;

	/**
	 * Attribute which determines the current work mode of Lexer.
	 */
	private LexerState lexerState;

	/**
	 * current index of the data array.
	 */
	private int currentIndex;

	/**
	 * Constructor which parses input string to char array and sets the default
	 * state and size
	 * 
	 * @throws IllegalArgumentException
	 *             in case when input is null
	 * @param text
	 *            given input
	 */
	public Lexer(String text) {

		if (text == null) {
			throw new IllegalArgumentException("Input text can't be null");
		}

		this.data = text.toCharArray();
		this.lexerState = LexerState.TEXT;
		this.currentIndex = 0;
	}

	/**
	 * Generates and returns the next Token.
	 * 
	 * @return generated Token
	 */
	public Token nextToken() {

		Tokenizer tokenizer = stateFactoryGenerator();

		return tokenizer.nextToken();

	}

	/**
	 * Factory method which returns the proper instantiated object of class which
	 * implements Tokenizer interface.
	 * 
	 * @return Tokenizer object
	 */
	public Tokenizer stateFactoryGenerator() {
		if (lexerState == LexerState.TEXT) {
			return new TextTokenizer();
		}

		else {
			return new TagTokenizer();
		}
	}

	/**
	 * Returns last generated token without creating the next one
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		if (token == null) {
			throw new NullPointerException("There are no generated tokens yet.");
		}
		return token;
	}

	/**
	 * Sets the lexer state to given state value.
	 * 
	 * @throws IllegalArgumentException
	 *             in case null is sent
	 * @param state
	 *            can be BASIC or EXTENDED
	 */
	public void setState(LexerState state) {

		if (state == null) {
			throw new IllegalArgumentException("State can't be null.");
		}

		this.lexerState = state;
	}
}
