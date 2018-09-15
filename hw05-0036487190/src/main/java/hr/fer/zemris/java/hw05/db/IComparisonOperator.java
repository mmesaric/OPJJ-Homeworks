package hr.fer.zemris.java.hw05.db;

/**
 * Strategy which offers a single method which, when overridden returns if the
 * comparison condition is satisfied
 * 
 * @author Marko Mesarić
 *
 */
public interface IComparisonOperator {

	/**
	 * Method when implemented returns true if the condition is satisfied, false
	 * otherwise
	 * 
	 * @param value1
	 *            first value to be compared
	 * @param value2
	 *            second value to be compared
	 * @return true if condition is satisfied, false otherwise
	 */
	public boolean satisfied(String value1, String value2);

}
