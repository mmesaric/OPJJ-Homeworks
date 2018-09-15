package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents a single element of type integer
 *
 * @author Marko Mesarić
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * int value of element.
	 */
	private int value;

	/**
	 * 
	 * @param value
	 *            value of the element
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/**
	 * Getter for attribute value.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

}
