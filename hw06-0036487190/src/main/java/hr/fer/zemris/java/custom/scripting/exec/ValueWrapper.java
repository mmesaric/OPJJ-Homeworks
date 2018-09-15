package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class used as wrapper for stored Object. It offers simple mathematical
 * operations and comparison over stored object.
 * 
 * @author Marko Mesarić
 *
 */
public class ValueWrapper {

	/**
	 * stored object
	 */
	private Object value;

	/**
	 * constant representing symbol for addition
	 */
	private static final String ADD_OPERATOR = "+";
	/**
	 * constant representing symbol for subtraction
	 */
	private static final String SUB_OPERATOR = "-";
	/**
	 * constant representing symbol for multiplication
	 */
	private static final String MUL_OPERATOR = "*";
	/**
	 * constant representing symbol for division
	 */
	private static final String DIV_OPERATOR = "/";
	/**
	 * constant representing symbol for comparison
	 */
	private static final String CMP_OPERATOR = "CMP";
	/**
	 * Variable used for storing result of value comparisons
	 */
	private static int COMPARE_VALUE;

	/**
	 * Constructor which sets the wrapped object to given value.
	 * 
	 * @param value
	 *            given object reference to be stored
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Method which performs addition of this object's value and passed reference
	 * value without modifying this object's value.
	 * 
	 * @param incValue
	 *            reference to other object
	 */
	public void add(Object incValue) {

		value = determineObjectType(value);
		Object calculatedValue = determineObjectType(incValue);

		calculate(calculatedValue, ADD_OPERATOR);
	}

	/**
	 * Method which performs subtraction of this object's value and passed reference
	 * value without modifying this object's value.
	 * 
	 * @param incValue
	 *            reference to other object
	 */
	public void subtract(Object decValue) {

		value = determineObjectType(value);
		Object calculatedValue = determineObjectType(decValue);

		calculate(calculatedValue, SUB_OPERATOR);
	}

	/**
	 * Method which performs multiplication of this object's value and passed
	 * reference value without modifying this object's value.
	 * 
	 * @param incValue
	 *            reference to other object
	 */
	public void multiply(Object mulValue) {

		value = determineObjectType(value);
		Object calculatedValue = determineObjectType(mulValue);

		calculate(calculatedValue, MUL_OPERATOR);
	}

	/**
	 * Method which performs division of this object's value with passed reference
	 * value without modifying this object's value.
	 * 
	 * @param incValue
	 *            reference to other object
	 */
	public void divide(Object divValue) {

		value = determineObjectType(value);
		Object calculatedValue = determineObjectType(divValue);

		calculate(calculatedValue, DIV_OPERATOR);
	}

	/**
	 * Method used for comparing this object's value with passed object's value.
	 * 
	 * @param withValue
	 *            other object to be compared with this object's value
	 * @return 1 if this value is greater than the other, 0 if values are equal, -1
	 *         if this value is less than the other one
	 */
	public int numCompare(Object withValue) {
		value = determineObjectType(value);
		Object calculatedValue = determineObjectType(withValue);

		calculate(calculatedValue, CMP_OPERATOR);

		return COMPARE_VALUE;
	}

	/**
	 * Auxiliary method used for checking the instance of objects on which operation
	 * is to be performed. After checking, appropriate methods used for calculating
	 * are called.
	 * 
	 * @param calculatedValue
	 *            passed object
	 * @param operator
	 *            used for determining which operation is to be performed.
	 */
	public void calculate(Object calculatedValue, String operator) {

		if (value instanceof Integer && calculatedValue instanceof Double) {
			calculateIntegerDoubleOperations(calculatedValue, operator);
		}

		else if (value instanceof Double && calculatedValue instanceof Integer) {
			calculateDoubleIntegerOperations(calculatedValue, operator);
		}

		else if (value instanceof Double && calculatedValue instanceof Double) {
			calculateDoubleDoubleOperations(calculatedValue, operator);
		}

		else if (value instanceof Integer && calculatedValue instanceof Integer) {
			calculateIntegerIntegerOperations(calculatedValue, operator);
		}
	}

	/**
	 * Method used for casting the passed object to instance of Class appropriate
	 * for demanded mathematical operation and calculating result in case of this
	 * object being an instance of Integer and the other being an instance of
	 * Double.
	 * 
	 * @param calculatedValue
	 *            passed object
	 * @param operator
	 *            used for determining which operation is to be performed.
	 */
	public void calculateIntegerDoubleOperations(Object calculatedValue, String operator) {

		switch (operator) {
		case "+":
			value = (Integer) value + (Double) calculatedValue;
			break;
		case "-":
			value = (Integer) value - (Double) calculatedValue;
			break;
		case "*":
			value = (Integer) value * (Double) calculatedValue;
			break;
		case "/":
			if ((Double) calculatedValue == 0) {
				throw new IllegalArgumentException("Division by 0 is not allowed.");
			}
			value = (Integer) value * (Double) calculatedValue;
			break;
		case "CMP":
			Double thisValue = Double.valueOf((int) value);
			Double otherValue = (Double) calculatedValue;
			COMPARE_VALUE = thisValue.compareTo(otherValue);
		}
	}

	/**
	 * Method used for casting the passed object to instance of Class appropriate
	 * for demanded mathematical operation and calculating result in case of this
	 * object being an instance of Double and the other being an instance of
	 * Integer.
	 * 
	 * @param calculatedValue
	 *            passed object
	 * @param operator
	 *            used for determining which operation is to be performed.
	 */
	public void calculateDoubleIntegerOperations(Object calculatedValue, String operator) {

		switch (operator) {
		case "+":
			value = (Double) value + (Integer) calculatedValue;
			break;
		case "-":
			value = (Double) value - (Integer) calculatedValue;
			break;
		case "*":
			value = (Double) value * (Integer) calculatedValue;
			break;
		case "/":
			if ((Integer) calculatedValue == 0) {
				throw new IllegalArgumentException("Division by 0 is not allowed.");
			}
			value = (Integer) value * (Integer) calculatedValue;
			break;
		case "CMP":
			Double thisValue = (Double) value;
			Double otherValue = Double.valueOf((int) calculatedValue);
			COMPARE_VALUE = thisValue.compareTo(otherValue);
		}
	}

	/**
	 * Method used for casting the passed object to instance of Class appropriate
	 * for demanded mathematical operation and calculating result in case of this
	 * object being an instance of Double and the other being an instance of Double.
	 * 
	 * @param calculatedValue
	 *            passed object
	 * @param operator
	 *            used for determining which operation is to be performed.
	 */
	public void calculateDoubleDoubleOperations(Object calculatedValue, String operator) {

		switch (operator) {
		case "+":
			value = (Double) value + (Double) calculatedValue;
			break;
		case "-":
			value = (Double) value - (Double) calculatedValue;
			break;
		case "*":
			value = (Double) value * (Double) calculatedValue;
			break;
		case "/":
			if ((Double) calculatedValue == 0) {
				throw new IllegalArgumentException("Division by 0 is not allowed.");
			}
			value = (Double) value * (Double) calculatedValue;
			break;
		case "CMP":
			Double thisValue = Double.valueOf((int) value);
			Double otherValue = Double.valueOf((int) calculatedValue);
			COMPARE_VALUE = thisValue.compareTo(otherValue);
		}
	}

	/**
	 * Method used for casting the passed object to instance of Class appropriate
	 * for demanded mathematical operation and calculating result in case of this
	 * object being an instance of Integer and the other being an instance of
	 * Integer.
	 * 
	 * @param calculatedValue
	 *            passed object
	 * @param operator
	 *            used for determining which operation is to be performed.
	 */
	public void calculateIntegerIntegerOperations(Object calculatedValue, String operator) {

		switch (operator) {
		case "+":
			value = (Integer) value + (Integer) calculatedValue;
			break;
		case "-":
			value = (Integer) value - (Integer) calculatedValue;
			break;
		case "*":
			value = (Integer) value * (Integer) calculatedValue;
			break;
		case "/":
			if ((Integer) calculatedValue == 0) {
				throw new IllegalArgumentException("Division by 0 is not allowed.");
			}
			value = (Integer) value * (Integer) calculatedValue;
			break;
		case "CMP":
			Integer thisValue = Integer.valueOf((int) value);
			Integer otherValue = Integer.valueOf((int) calculatedValue);
			COMPARE_VALUE = thisValue.compareTo(otherValue);
		}
	}

	/**
	 * Method used for casting the passed object to instance of Class appropriate
	 * for demanded mathematical operation. If incValue is null, value is set to
	 * Integer with value 0.
	 * 
	 * @throws RuntimeException
	 *             in case if passed value is not of Integer, Double, String or
	 *             null.
	 * @param incValue
	 *            value which type is to be determined
	 * @return correct object instance
	 */
	public Object determineObjectType(Object incValue) {

		if (value == null) {
			value = (Integer) 0;
		}

		if (incValue == null) {
			incValue = (Integer) 0;
			return incValue;
		}

		if (incValue instanceof Integer) {
			return incValue;
		}

		else if (incValue instanceof Double) {
			return incValue;
		}

		else if (incValue instanceof String) {
			String stringValue = String.valueOf(incValue);

			if (stringValue.contains(".") || stringValue.contains("E")) {
				try {
					Double decimalValue = Double.valueOf(stringValue);
					return decimalValue;
				} catch (NumberFormatException e) {
					throw new RuntimeException("Passed value and be only Integer, Double, String or null.");
				}
			}

			else {
				try {
					Integer intValue = Integer.parseInt(stringValue);
					return intValue;
				} catch (NumberFormatException e) {
					throw new RuntimeException("Passed value and be only Integer, Double, String or null.");
				}
			}
		} else
			throw new RuntimeException("Passed value and be only Integer, Double, String or null.");

	}

	/**
	 * Getter for object reference
	 * 
	 * @return reference to wrapped object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for object value
	 * 
	 * @param value
	 *            given value to be set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
