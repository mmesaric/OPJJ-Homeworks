package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents a simple Lexer implementation which can work in two
 * different states. It works in basic state until character '#' is read and
 * then it switches to extended state. Character escaping is possible in basic
 * state of lexer if input is according to given rules. Possible tokens are
 * words, numbers, symbols and eof. It consists of two separate classes, for
 * each of given states.
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
	interface Tokenizer {
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
	class BasicStateLexer implements Tokenizer {

		/**
		 * Generates and returns the next Token. Calls corresponding helper methods
		 * based on the character it reads in this method.
		 * 
		 * @return generated Token
		 */
		@Override
		public Token nextToken() {

			for (int i = currentIndex;; i++) {

				if (currentIndex == data.length) {
					token = new Token(TokenType.EOF, null);
					currentIndex++;
					return token;
				}

				else if (currentIndex > data.length) {
					throw new LexerException("There are no more tokens left after EOF value");
				}

				if (Character.isWhitespace(data[i])) {
					currentIndex++;
					continue;
				}

				if (i == data.length - 1 && data[i] == '\\') {
					throw new LexerException("Failed tokenization, wrong input. '\\' Can't appear at the end");
				}

				if (Character.isLetter(data[i]) || data[i] == '\\') {
					checkIfLetter(i);
					return token;
				}

				else if (Character.isDigit(data[i])) {
					checkIfNumber(i);
					return token;
				}

				else {
					if (data[i] == '#') {
						lexerState = LexerState.EXTENDED;
					}
					currentIndex++;
					token = new Token(TokenType.SYMBOL, data[i]);
					return token;
				}
			}
		}

		/**
		 * Helper method which is called after finding an occurrence of a Letter.
		 * Iterates through following chars in hope of creating a new Token with WORD
		 * type.
		 * 
		 * @throws LexerException
		 *             in case if after '\' appears anything else besides a digit or
		 *             another '\'
		 * @param index
		 *            index of the occurrence of the first letter
		 */
		public void checkIfLetter(int index) {
			StringBuilder wordBuilder = new StringBuilder();

			for (int j = index;; j++) {
				currentIndex = j;

				if (currentIndex == data.length) {
					break;
				}

				if (Character.isLetter(data[j])) {
					wordBuilder.append(data[j]);
				}

				else if (data[j] == '\\') {

					if (Character.isDigit(data[j + 1])) {
						wordBuilder.append(data[j + 1]);
						j++;
					}

					else if (data[j + 1] == '\\') {
						wordBuilder.append('\\');
						j++;
					} else {
						throw new LexerException(
								"Failed tokenization, wrong input. After '\\' can appear only '\\' or digit.");
					}
				} else {
					break;
				}
			}

			token = new Token(TokenType.WORD, wordBuilder.toString());
		}

		/**
		 * Helper method which is called after finding an occurrence of a Digit.
		 * Iterates through following chars in hope of creating a new Token with DIGIT
		 * type
		 * 
		 * @throws LexerException
		 *             in case if number is too big to be stored in type Long
		 * @param index
		 *            index of the occurrence of the first digit
		 */
		public void checkIfNumber(int index) {
			StringBuilder numberBuilder = new StringBuilder();

			for (int j = index;; j++) {

				currentIndex = j;

				if (currentIndex == data.length) {
					break;
				}

				if (!Character.isDigit(data[j])) {
					break;
				} else {
					numberBuilder.append(data[j]);
				}
			}

			try {
				Long number = Long.parseLong(numberBuilder.toString());
				token = new Token(TokenType.NUMBER, number);
			} catch (NumberFormatException e) {
				throw new LexerException("The number is too big to be stored in Long");
			}
		}

	}

	/**
	 * This class represents the solution to implementation of Lexer when working in
	 * Extended state. While in extended state, there is no escaping special signs,
	 * all arrays of digits or alphabet signs are considered a word and returned as
	 * such token.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	class ExtendedStateLexer implements Tokenizer {

		@Override
		public Token nextToken() {

			int i;
			for (i = currentIndex;; i++) {

				if (currentIndex == data.length) {
					token = new Token(TokenType.EOF, null);
					currentIndex++;
					return token;
				}

				if (currentIndex > data.length) {
					throw new LexerException("There are no more tokens left after EOF value");
				}

				if (Character.isWhitespace(data[i])) {
					currentIndex++;
					continue;
				}

				else {

					formWordOrSymbol(i);
					return token;
				}
			}
		}

		/**
		 * Helper method which is called when alphabet sign or digit appeared. It
		 * iterates through the rest of sequence and generates token based on it.
		 * 
		 * @param index
		 *            current data index
		 * @return generated Token
		 */
		public Token formWordOrSymbol(int index) {

			StringBuilder wordBuilder = new StringBuilder();

			for (int j = index;; j++) {
				currentIndex = j;

				if (currentIndex == data.length) {
					break;
				}

				if (data[j] == ' ') {
					break;
				}

				if (data[j] == '#') {

					if (wordBuilder.toString().equals("")) {
						currentIndex++;
						lexerState = LexerState.BASIC;
						token = new Token(TokenType.SYMBOL, data[j]);
						return token;
					}
					break;

				}

				wordBuilder.append(data[j]);
			}

			token = new Token(TokenType.WORD, wordBuilder.toString());
			return token;
		}

	}

	/**
	 * char array of input string
	 */
	private char[] data;
	/**
	 * last generated token
	 */
	private Token token;
	/**
	 * information about current lexer state is stored here, based on it's value,
	 * lexer switches states
	 */
	private LexerState lexerState;
	/**
	 * current index of data array
	 */
	private int currentIndex;

	/**
	 * Constructor which generates char array, state and index.
	 * 
	 * @throws IllegalArgumentException
	 *             if input text is null
	 * @param text
	 *            given text input
	 */
	public Lexer(String text) {

		if (text == null) {
			throw new IllegalArgumentException("Input text can't be null");
		}

		this.data = text.toCharArray();
		this.lexerState = LexerState.BASIC;
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
	 * Helper factory method which determines which instance of Tokenizer class
	 * should be active
	 * 
	 * @return
	 */
	public Tokenizer stateFactoryGenerator() {
		if (lexerState == LexerState.BASIC) {
			return new BasicStateLexer();
		}

		else {
			return new ExtendedStateLexer();
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
