package hr.fer.zemris.java.hw07.shell.commands.name;

/**
 * Interface which defines the properties connected with the given name builder.
 * It is responsible for storing string builder used for generating final name
 * after renaming the original file.
 * 
 * @author Marko Mesarić
 *
 */
public interface NameBuilderInfo {
	/**
	 * Getter for string builder object.
	 * 
	 * @return relevant string builder object
	 */
	StringBuilder getStringBuilder();

	/**
	 * Getter for String value which equals to the value stored under given subgroup
	 * index
	 * 
	 * @param index
	 *            group index
	 * @return String value of group
	 */
	String getGroup(int index);
}
