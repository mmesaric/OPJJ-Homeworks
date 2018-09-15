package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 * 
 * @author Marko Mesarić
 *
 */
public class TextNode extends Node {

	/**
	 * Text of the given node
	 */
	private String text;

	/**
	 * Constructor which sets the text to given value.
	 * 
	 * @param text
	 *            given text value
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Getter for text attribute.
	 * 
	 * @return text value
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}

}
