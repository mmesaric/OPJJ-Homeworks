package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents a single function element.
 * 
 * @author Marko Mesarić
 *
 */
public class ElementFunction extends Element {

	/**
	 * function name.
	 */
	private String name;

	/**
	 * sets function name to given value.
	 * 
	 * @param name
	 *            of the given function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Getter for attribute name.
	 * 
	 * @return name of function
	 */
	public String getName() {
		return name;
	}

}
