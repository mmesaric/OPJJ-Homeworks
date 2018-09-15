package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.LexerException;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Class which represents the implementation of a simple parser for our custom
 * query language. It iterates through generated tokens and creates the
 * Conditional expressions accordingly, which are later used for comparing
 * student records and are filtered.
 * 
 * @author Marko Mesarić
 *
 */
public class QueryParser {

	/**
	 * List for storing generated conditional expressions
	 */
	private List<ConditionalExpression> conditionalExpressions;
	/**
	 * Flag which determines if query is a direct one
	 */
	private boolean isDirect;
	/**
	 * Valued used for storing jmbag when direct query is used
	 */
	private String queriedJMBAG;

	/**
	 * Constructor which receives the query to be parsed
	 * 
	 * @throws NullPointerException
	 *             if given query is a null reference
	 * @throws IllegalArgumentException
	 *             if given query is empty
	 * @param query
	 *            which is to be parsed
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new NullPointerException("Query can't be null");
		}

		if (query.isEmpty()) {
			throw new IllegalArgumentException("Query can't be empty");
		}

		QueryLexer lexer;
		try {
			lexer = new QueryLexer(query);
		} catch (LexerException e) {
			throw new ParserException("Error when parsing query.");
		}

		conditionalExpressions = new ArrayList<>();
		parseQuery(lexer);
	}

	/**
	 * Method responsible for iteration over tokens and generation of conditional
	 * expressions
	 * 
	 * @throws ParserException
	 *             if invalid query syntax
	 * @param lexer
	 *            reference to Query lexer object
	 */
	private void parseQuery(QueryLexer lexer) {

		while (lexer.nextToken() != null) {

			Token token = lexer.getToken();

			if (token.getType() == TokenType.EOF) {
				return;
			}

			else if (token.getType() == TokenType.ATTRIBUTE_NAME) {

				if (token.getValue().equals("jmbag")) {
					isDirect = true;
				}

				parseNewExpression(lexer);

			}

			else if (token.getType() == TokenType.LOGICAL_OPERATOR) {
				Token nextToken = lexer.nextToken();

				if (nextToken == null) {
					throw new ParserException("There has to be some input after AND logical operator.");
				}

				else if (nextToken.getType() == TokenType.ATTRIBUTE_NAME) {
					parseNewExpression(lexer);

				} else {
					throw new ParserException("Attribute name is expected after AND logical operator.");
				}
			}

			else {
				throw new ParserException("Invalid syntax for query.");
			}
		}

	}

	/**
	 * Auxiliary method for generating concrete Conditional expression fields based
	 * on generated and retrieved tokens from lexical analysis.
	 * 
	 * @param lexer
	 *            reference to query lexer object
	 */
	private void parseNewExpression(QueryLexer lexer) {

		IFieldValueGetter fieldGetter = parserFieldGetter(lexer.getToken());
		IComparisonOperator comparisonOperator = parserOperator(lexer);

		if (!lexer.getToken().getValue().equals("=")) {
			isDirect = false;
		}

		String literal = parseLiteral(lexer);

		if (isDirect) {
			queriedJMBAG = literal;
		}

		if (fieldGetter == null || comparisonOperator == null || literal == null) {
			throw new ParserException("Invalid syntax for query.");
		}

		conditionalExpressions.add(new ConditionalExpression(fieldGetter, literal, comparisonOperator));

	}

	/**
	 * Auxiliary method used for parsing a String literal
	 * 
	 * @param lexer
	 *            reference to query lexer object
	 * @return generated String literal
	 */
	private String parseLiteral(QueryLexer lexer) {

		if (lexer.nextToken() != null && lexer.getToken().getType() == TokenType.STRING_LITERAL) {

			return String.valueOf(lexer.getToken().getValue());

		} else {
			throw new ParserException("Invalid syntax for query.");
		}
	}

	/**
	 * Auxiliary method used for generating according comparison operator based on
	 * token values
	 * 
	 * @param lexer
	 *            reference to query lexer object
	 * @return generated comparison operator
	 */
	private IComparisonOperator parserOperator(QueryLexer lexer) {

		if (lexer.nextToken() != null && lexer.getToken().getType() == TokenType.OPERATOR) {

			IComparisonOperator comparisonOperator = null;

			switch (String.valueOf(lexer.getToken().getValue())) {
			case ">":
				comparisonOperator = ComparisonOperators.GREATER;
				break;
			case ">=":
				comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
				break;
			case "<":
				comparisonOperator = ComparisonOperators.LESS;
				break;
			case "<=":
				comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
				break;
			case "=":
				comparisonOperator = ComparisonOperators.EQUALS;
				break;
			case "!=":
				comparisonOperator = ComparisonOperators.NOT_EQUALS;
				break;
			case "LIKE":
				comparisonOperator = ComparisonOperators.LIKE;
				break;
			}

			return comparisonOperator;
		}

		else {
			throw new ParserException("Invalid syntax for query.");
		}
	}

	/**
	 * Auxiliary method used for generating according field value based on token
	 * value.
	 * 
	 * @param lexer
	 *            reference to query lexer object
	 * @return generated field value getter
	 */
	private IFieldValueGetter parserFieldGetter(Token token) {

		if (token.getValue().equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		} else if (token.getValue().equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		} else if (token.getValue().equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		}
		return null;
	}

	/**
	 * Method which says if the given query is a direct one.
	 * 
	 * @return true if is direct, false otherwise
	 */
	public boolean isDirectQuery() {
		return isDirect;
	}

	/**
	 * Method which returns the queried jmbag if direct query was used.
	 * 
	 * @throws IllegalStateException
	 *             if method was called over a non direct query
	 * @return
	 */
	public String getQueriedJMBAG() {
		if (!isDirect)
			throw new IllegalStateException("Query wasn't a direct query.");
		return queriedJMBAG;
	}

	/**
	 * Getter for the list of generated conditional expressions.
	 * 
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> getQuery() {
		return conditionalExpressions;
	}

}
