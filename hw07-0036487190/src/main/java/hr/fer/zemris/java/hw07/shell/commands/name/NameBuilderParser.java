package hr.fer.zemris.java.hw07.shell.commands.name;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of a parser used for parsing
 * expressions which generate new file names after renaming them.
 * 
 * @author Marko Mesarić
 *
 */
public class NameBuilderParser {

	/**
	 * Enumerator used for determing the state of parser
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private enum State {
		/**
		 * Used when parsing strings with concrete values
		 */
		STRING,
		/**
		 * Used when parsing group blocks
		 */
		GROUP;
	}

	/**
	 * Variable used for storing the reference to the name builder generated after
	 * parsing process.
	 */
	private NameBuilder nameBuilder;

	/**
	 * Default constructor
	 * 
	 * @param expression
	 *            Expression to be parsed
	 */
	public NameBuilderParser(String expression) {
		nameBuilder = new LastNameBuilder(parseExpression(expression));
	}

	/**
	 * Auxiliary method which performs the parsing of the given expression and
	 * returns the list of generated name builders.
	 * 
	 * @param expression
	 *            expression to be parsed
	 * @return list of generated name builders.
	 */
	public List<NameBuilder> parseExpression(String expression) {

		char[] expressionArray = expression.toCharArray();
		List<NameBuilder> nameBuilders = new ArrayList<>();
		StringBuilder name = new StringBuilder();
		State state = State.STRING;

		for (int i = 0; i < expressionArray.length; i++) {

			char currentChar = expressionArray[i];

			if (state == State.STRING) {
				if (currentChar == '$') {
					if (i + 1 == expressionArray.length) {
						name.append(currentChar);
						nameBuilders.add(new StringNameBuilder(name.toString()));
					} else {
						char nextChar = expressionArray[i + 1];

						if (nextChar == '{') {
							if (!name.toString().isEmpty()) {
								nameBuilders.add(new StringNameBuilder(name.toString()));
								name = new StringBuilder();
								i++;
							}
							state = State.GROUP;
						} else {
							name.append(currentChar);
							name.append(nextChar);
							i++;
						}
					}
				} else {
					name.append(currentChar);
					if (i + 1 == expressionArray.length) {
						nameBuilders.add(new StringNameBuilder(name.toString()));
					}
				}
			} else {

				if (currentChar == '}') {
					if (i + 1 == expressionArray.length) {
						parseSubstitution(name.toString(), nameBuilders);
					} else {

						parseSubstitution(name.toString(), nameBuilders);
						name = new StringBuilder();
						state = State.STRING;
					}
				} else if (Character.isDigit(currentChar) || Character.isWhitespace(currentChar)
						|| currentChar == ',') {
					name.append(currentChar);
					if (i + 1 == expressionArray.length) {
						parseSubstitution(name.toString(), nameBuilders);
					}
				} else {
					throw new ParserException("Syntax error in substitution block.");
				}
			}
		}
		return nameBuilders;
	}

	/**
	 * Auxiliary method used for parsing group blocks according to given rules of
	 * group blocks behavior.
	 * 
	 * @param substition
	 *            expression which is checked
	 * @param nameBuilders
	 *            reference to list of stored name builders
	 */
	private void parseSubstitution(String substition, List<NameBuilder> nameBuilders) {
		substition = substition.trim();

		if (substition.isEmpty()) {
			throw new ParserException("Substitution expression can't be empty!");
		}

		if (substition.contains(",")) {
			String[] splitSubstition = substition.split(",");

			if (splitSubstition.length != 2) {
				throw new ParserException("Syntax error in substitution block.");
			}

			int groupNumber = Integer.parseInt(splitSubstition[0].trim());
			int numberOfZeroes = Integer.parseInt(splitSubstition[1].trim());

			if (groupNumber < 0 || numberOfZeroes < 0) {
				throw new ParserException("Numbers in grouping must be greater than 0.");
			}

			nameBuilders.add(new GroupNameBuilder(groupNumber, splitSubstition[1].trim()));
		} else {
			int groupNumber = Integer.parseInt(substition);
			if (groupNumber < 0) {
				throw new ParserException("Numbers in grouping must be greater than 0.");
			}
			nameBuilders.add(new GroupNameBuilder(groupNumber, ""));

		}
	}

	/**
	 * Getter for name builder reference.
	 * 
	 * @return last generated name builder which stores the references to all
	 *         previously generated name builders
	 */
	public NameBuilder getNameBuilder() {
		return nameBuilder;
	}
}
