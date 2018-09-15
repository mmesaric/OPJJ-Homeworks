package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * This class represents the implementation of a simple parser for given
 * language. It relies on Lexer to generate and return tokens. It checks for
 * proper Token types on specific places.
 * 
 * @author Marko Mesarić
 *
 */
public class SmartScriptParser {

	/**
	 * Root node to who's collection all children are added.
	 */
	private DocumentNode documentNode;

	/**
	 * Constructor which receives input text, initializes Lexer with it and
	 * propagates created objects to parse method which begins the parsing process.
	 * Generates the root document Node.
	 * 
	 * @throws SmartScriptParserException
	 *             in case of wrong input.
	 * @param text
	 *            which is to be parsed.
	 */
	public SmartScriptParser(String text) {

		Lexer lexer;

		try {
			lexer = new Lexer(text);
		} catch (LexerException e) {
			throw new SmartScriptParserException("Wrong input.");
		}
		documentNode = new DocumentNode();

		parse(lexer);

	}

	/**
	 * Method which begins the parsing process and checks the last generated Lexer
	 * token and calls the helper methods appropriately. This solution uses the
	 * implementation of stack object which was created for last assignment. In case
	 * of finding for loop node adds the node parent node and pushes for loop node
	 * to stack. This is how object stack is implemented for creating tree like
	 * structure of nodes.
	 * 
	 * @param lexer
	 *            reference to created lexer object.
	 * @throws SmartScriptParserException
	 *             in case of wrong input.
	 */
	private void parse(Lexer lexer) {

		ObjectStack objectStack = new ObjectStack();
		objectStack.push(documentNode);

		while (lexer.nextToken() != null) {
			Token token = lexer.getToken();

			if (token.getType() == TokenType.EOF) {
				return;
			}

			if (token.getType() == TokenType.TEXT) {
				Node lastNodeInStack = (Node) objectStack.peek();
				lastNodeInStack.addChild(new TextNode(String.valueOf(token.getValue())));
			}

			if (token.getType() == TokenType.OPENING_TAG) {
				lexer.setState(LexerState.TAG);
				continue;
			}

			if (token.getType() == TokenType.CLOSING_TAG) {
				lexer.setState(LexerState.TEXT);
				continue;
			}

			if (token.getType() == TokenType.IDENTIFIER) {

				if (String.valueOf(token.getValue()).toUpperCase().equals("END")) {
					objectStack.pop();
					if (objectStack.isEmpty()) {
						throw new SmartScriptParserException("Invalid text input. Error when parsing.");
					}
				}

				if (String.valueOf(token.getValue()).toUpperCase().equals("FOR")) {

					ElementVariable variable = addForLoopNode(lexer);
					Element startExpression = parseForExpressions(lexer);
					Element endExpression = parseForExpressions(lexer);
					Element stepExpression = null;
					try {
						stepExpression = parseForExpressions(lexer);
					} catch (SmartScriptParserException e) {

					}

					if (variable == null || startExpression == null || endExpression == null) {
						throw new SmartScriptParserException("For loop invalid.");
					}

					if (lexer.getToken().getType() != TokenType.CLOSING_TAG) {
						if (lexer.nextToken() != null) {
							if (lexer.getToken().getType() != TokenType.CLOSING_TAG) {
								throw new SmartScriptParserException("Too many arguments");
							}
							lexer.setState(LexerState.TEXT);
						} else {
							throw new SmartScriptParserException("Too many arguments");
						}
					}
					ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
					
					Node lastNodeInStack = (Node) objectStack.peek();
					lastNodeInStack.addChild(forLoopNode);
					objectStack.push(forLoopNode);

				}

				if (String.valueOf(token.getValue()).equals("=")) {

//					ElementVariable elementVar = addForLoopNode(lexer);
					
					Element elementVar = findElementVar(lexer);

					ArrayIndexedCollection pomCollection = ifValidEmptyTag(lexer);
					Element[] elements = new Element[pomCollection.size() + 1];
					elements[0] = elementVar;

					for (int i = 0; i < pomCollection.size(); i++) {
						Element element = (Element) pomCollection.get(i);
						elements[i+1] = element;
					}

					EchoNode echoNode = new EchoNode(elements);
					Node lastNodeInStack = (Node) objectStack.peek();
					lastNodeInStack.addChild(echoNode);
//					objectStack.push(lastNodeInStack);
				}
			}
		}

	}

	private Element findElementVar(Lexer lexer) {
		
		if (lexer.nextToken().getValue() == null) {
			throw new SmartScriptParserException("Echo node syntax invalid.");
		}
		
		Token token = lexer.getToken();
		
		if (token.getType() == TokenType.IDENTIFIER) {
			if (!String.valueOf(token.getValue()).toUpperCase().equals("FOR")
					&& !String.valueOf(token.getValue()).equals("=")
					&& !String.valueOf(token.getValue()).equals("END")) {
				ElementVariable elementVar = new ElementVariable(String.valueOf(token.getValue()));

				return elementVar;
			}
		}
		else if (token.getType() == TokenType.STRING) {
			ElementString elementString = new ElementString(String.valueOf(token.getValue()));
			return elementString;
		}
		
		throw new SmartScriptParserException("Echo node syntax invalid.");
		
	}

	/**
	 * Helper method which generates the variable element of some token types.
	 * 
	 * @param lexer
	 *            reference to lexer object
	 * @return generated Element Variable
	 * @throws SmartScriptParserException
	 *             in case of invalid input.
	 */
	private ElementVariable addForLoopNode(Lexer lexer) {

		if (lexer.nextToken().getValue() == null) {
			throw new SmartScriptParserException("For loop invalid.");
		}

		Token token = lexer.getToken();

		if (token.getType() == TokenType.IDENTIFIER) {
			if (!String.valueOf(token.getValue()).toUpperCase().equals("FOR")
					&& !String.valueOf(token.getValue()).equals("=")
					&& !String.valueOf(token.getValue()).equals("END")) {
				ElementVariable elementVar = new ElementVariable(String.valueOf(token.getValue()));

				return elementVar;
			}

			else {
				throw new SmartScriptParserException("First argument in for loop must be variable");
			}
		} else {
			throw new SmartScriptParserException("First argument in for loop must be variable");
		}
	}

	/**
	 * Helper method for parsing expressions in for loop tag.
	 * 
	 * @param lexer
	 *            reference to lexer object
	 * @return generated element
	 * @throws SmartScriptParserException
	 *             in case of wrong input.
	 */
	private Element parseForExpressions(Lexer lexer) {

		if (lexer.nextToken().getValue() == null) {
			throw new SmartScriptParserException("For loop invalid.");
		}

		Token token = lexer.getToken();
		return parseForElements(token);
	}

	/**
	 * Helper method with checks for all possible elements which can occur after
	 * identifier element in for loop tag
	 * 
	 * @param token
	 *            given token to be checked.
	 * @return generated element
	 * @throws SmartScriptParserException
	 *             in case of wrong input.
	 */
	private Element parseForElements(Token token) {

		Element result = null;
		if (token.getType() == TokenType.IDENTIFIER) {

			if (!String.valueOf(token.getValue()).toUpperCase().equals("FOR")
					&& !String.valueOf(token.getValue()).equals("=")
					&& !String.valueOf(token.getValue()).equals("END")) {
				result = new ElementVariable(String.valueOf(token.getValue()));
				return result;
			}
		}

		else if (token.getType() == TokenType.STRING) {
			result = new ElementString(String.valueOf(token.getValue()));
			return result;
		}

		else if (token.getType() == TokenType.DOUBLE) {
			result = new ElementConstantDouble(Double.parseDouble(String.valueOf(token.getValue())));
			return result;
		} else if (token.getType() == TokenType.INT) {
			result = new ElementConstantInteger(Integer.parseInt(String.valueOf(token.getValue())));
			return result;
		}

		throw new SmartScriptParserException("First argument in for loop must be variable");

	}

	/**
	 * Helper method responsible for creating the collection of tokens found in echo
	 * tag.
	 * 
	 * @param lexer
	 *            reference to lexer object
	 * @return generated collection of elements
	 * @throws SmartScriptParserException
	 *             in case of wrong input
	 */
	private ArrayIndexedCollection ifValidEmptyTag(Lexer lexer) {

		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();

		while (lexer.nextToken().getType() != TokenType.CLOSING_TAG) {

			Token token = lexer.getToken();

			if (token.getType() == TokenType.EOF) {
				throw new SmartScriptParserException("Error when parsing empty tag");
			}

			// parseEmptyTagElements(token);

			arrayIndexedCollection.add(parseEmptyTagElements(token));

		}

		lexer.setState(LexerState.TEXT);
		return arrayIndexedCollection;

	}

	/**
	 * Helper method with checks for all possible elements which can occur after
	 * identifier element in empty tag
	 * 
	 * @param token
	 *            given token to be checked.
	 * @return generated element
	 * @throws SmartScriptParserException
	 *             in case of wrong input.
	 */
	private Element parseEmptyTagElements(Token token) {
		Element result = null;
		if (token.getType() == TokenType.IDENTIFIER) {

			if (!String.valueOf(token.getValue()).toUpperCase().equals("FOR")
					&& !String.valueOf(token.getValue()).equals("=")
					&& !String.valueOf(token.getValue()).equals("END")) {
				result = new ElementVariable(String.valueOf(token.getValue()));
				return result;
			}
		}

		else if (token.getType() == TokenType.STRING) {
			result = new ElementString(String.valueOf(token.getValue()));
			return result;
		}

		else if (token.getType() == TokenType.DOUBLE) {
			result = new ElementConstantDouble(Double.parseDouble(String.valueOf(token.getValue())));
			return result;
		} else if (token.getType() == TokenType.INT) {
			result = new ElementConstantInteger(Integer.parseInt(String.valueOf(token.getValue())));
			return result;
		} else if (token.getType() == TokenType.FUNCTION_NAME) {
			result = new ElementFunction(String.valueOf(token.getValue()));
			return result;
		} else if (token.getType() == TokenType.SYMBOL) {
			result = new ElementOperator(String.valueOf(token.getValue()));
			return result;
		}

		throw new SmartScriptParserException("First argument in for loop must be variable");
	}

	/**
	 * getter for root node
	 * 
	 * @return document node first pushed to stack
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

}
