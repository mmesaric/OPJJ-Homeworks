package hr.fer.zemris.java.hw07.shell.commands.name;

/**
 * This class represents the implementation of a specific name builder used for
 * generating and appending blocks of strings to the name builder.
 * 
 * @author Marko Mesarić
 *
 */
public class StringNameBuilder implements NameBuilder {

	/**
	 * String name
	 */
	private String name;

	/**
	 * Default constructor
	 * 
	 * @param name
	 *            name value to be set
	 */
	public StringNameBuilder(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(name);
	}

}
