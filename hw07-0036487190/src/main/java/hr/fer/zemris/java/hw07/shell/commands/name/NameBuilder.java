package hr.fer.zemris.java.hw07.shell.commands.name;

/**
 * Interface which defines the behavior of name builders and all methods which
 * specific implementations need to offer.
 * 
 * @author Marko Mesarić
 *
 */
public interface NameBuilder {
	/**
	 * Method which is called when concrete name builder is executed.
	 * 
	 * @param info
	 *            extra information used for name building process
	 */
	void execute(NameBuilderInfo info);
}
