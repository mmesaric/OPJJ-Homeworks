package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents a single variable element.
 * 
 * @author Marko Mesarić
 *
 */
public class ElementVariable extends Element {

	/**
	 * name of the variable.
	 */
	private String name;

	/**
	 * Constructor which sets the name of the variable to given value.
	 * 
	 * @param name
	 *            given name of the variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Getter for field name.
	 * 
	 * @return name of the variable
	 */
	public String getName() {
		return name;
	}

}
