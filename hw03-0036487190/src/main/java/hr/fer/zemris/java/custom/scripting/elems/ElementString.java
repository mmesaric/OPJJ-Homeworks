package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents a single string element.
 * 
 * @author Marko Mesarić
 *
 */
public class ElementString extends Element {

	/**
	 * value of string element
	 */
	private String value;

	/**
	 * sets the string value to given value.
	 * 
	 * @param value
	 *            to be set
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}

	/**
	 * Getter for field value.
	 * @return
	 */
	public String getValue() {
		return value;
	}
}
