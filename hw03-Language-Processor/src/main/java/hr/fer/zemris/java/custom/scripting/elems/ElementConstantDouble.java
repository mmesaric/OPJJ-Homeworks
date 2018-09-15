package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents a single element of type double
 * 
 * @author Marko Mesarić
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * double value of element
	 */
	private double value;

	/**
	 * 
	 * @param value
	 *            value of the element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}

	/**
	 * getter for parameter value.
	 * 
	 * @return value
	 */
	public double getValue() {
		return value;
	}

}
