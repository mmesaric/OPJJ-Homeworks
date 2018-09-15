package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * A node representing a command which generates some textual output
 * dynamically. It inherits from node class.
 * 
 * @author Marko Mesarić
 *
 */
public class EchoNode extends Node {

	/**
	 * array of elements in echo node tag
	 */
	private Element[] elements;

	/**
	 * Constructor which sets the array to given array of elements.
	 * 
	 * @param elements
	 *            given elements array
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * getter for attribute elements.
	 * 
	 * @return
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("{$= ");

		for (int i = 0; i < elements.length; i++) {
			if (elements[i] instanceof ElementString) {
				stringBuilder.append("\"").append(elements[i].asText()).append("\" ");

			} else {
				stringBuilder.append(elements[i].asText()).append(" ");
			}
		}

		stringBuilder.append("$}");

		return stringBuilder.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
