package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents an operator element.
 * 
 * @author Marko Mesarić
 *
 */
public class ElementOperator extends Element {

	/**
	 * Operator value.
	 */
	private String symbol;

	/**
	 * Constructor which sets the value of symbol to given symbol value.
	 * 
	 * @param symbol
	 *            to be set
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Getter for symbol value
	 * 
	 * @return symbol
	 */
	public String getSymbol() {
		return symbol;
	}

}
