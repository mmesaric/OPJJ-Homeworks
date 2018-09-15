package hr.fer.zemris.java.hw05.db;

/**
 * Class which represents the model of a single Conditional Expression. It
 * consists of an attribute name, a String literal, and an operator used for
 * comparing.
 * 
 * @author Marko Mesarić
 *
 */
public class ConditionalExpression {

	/**
	 * Attribute used for storing the name of the attribute
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * Attribute used for storing the String literal
	 */
	private String literal;
	/**
	 * Attribute used for storing operator
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor which sets the values to given values.
	 * 
	 * @param fieldValueGetter
	 *            attribute name to be set
	 * @param literal
	 *            String literal value
	 * @param comparisonOperator
	 *            Comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal,
			IComparisonOperator comparisonOperator) {
		this.fieldValueGetter = fieldValueGetter;
		this.literal = literal;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for attribute name.
	 * 
	 * @return field value
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Getter for String literal value
	 * 
	 * @return String literal value
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Getter for comparison operator
	 * 
	 * @return comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
