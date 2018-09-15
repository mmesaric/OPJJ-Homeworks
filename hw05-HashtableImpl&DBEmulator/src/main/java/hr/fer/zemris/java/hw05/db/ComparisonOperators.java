package hr.fer.zemris.java.hw05.db;

/**
 * Class used for implementing concrete strategies for each comparison operator
 * the program is required to support. LIKE operator is handled by static nested
 * class implements IComparisonOperator and overrides the method used for
 * comparing strings and other operators are handled by lambda expressions.
 * 
 * @author Marko Mesarić
 *
 */
public class ComparisonOperators {

	/**
	 * Static class which implements Interface IComparisonOperator and overrides
	 * it's only method "satisfied". The method is responsible for checking if given
	 * String satisfies given pattern based on "LIKE" operator. Given pattern can
	 * contain a wildcard *, and only one appearance of that character is allowed.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	static class LikeComparisonImpl implements IComparisonOperator {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean satisfied(String toBeChecked, String pattern) {

			if (pattern.contains("*")) {

				int count = 0;
				char[] array = pattern.toCharArray();

				for (int i = 0; i < array.length; i++) {
					if (array[i] == '*') {
						count++;
					}
				}
				if (count > 1) {
					throw new IllegalArgumentException("Pattern contained more than 1 wildcard.");
				}

				pattern = pattern.replace("*", "(.*)");

				return toBeChecked.matches(pattern);

			}

			return toBeChecked.compareTo(pattern) == 0;
		}

	}

	/**
	 * true is value1 is less than value2, false otherwise
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;
	/**
	 * true is value1 is less or equal to value2, false otherwise
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
	/**
	 * true is value1 is greater than value2, false otherwise
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
	/**
	 * true is value1 is greater or equal than value2, false otherwise
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
	/**
	 * true is value1 is equal to value2, false otherwise
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;
	/**
	 * true is value1 is not equal to value2, false otherwise
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;
	/**
	 * LIKE operator is handled by static nested class described on top.
	 */
	public static final IComparisonOperator LIKE = new LikeComparisonImpl();
}
