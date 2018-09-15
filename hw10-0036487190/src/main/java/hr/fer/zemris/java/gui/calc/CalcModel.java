package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Interface used for modeling the behavior of a custom calculator created in
 * this homework assignment.
 * 
 * @author Marko Mesarić
 *
 */
public interface CalcModel {

	/**
	 * Adds the given listener to the list of active listeners
	 * 
	 * @param l
	 *            listener to be added
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Removes the given listener from the list of active listeners
	 * 
	 * @param l
	 *            listener to be removed
	 */
	void removeCalcValueListener(CalcValueListener l);

	/**
	 * Getter for current value
	 * 
	 * @return current value
	 */
	double getValue();

	/**
	 * Setter for current value
	 * 
	 * @param value
	 *            value to be set
	 */
	void setValue(double value);

	/**
	 * Method which clears the current value
	 */
	void clear();

	/**
	 * Method which clears the current value, active operand and scheduled
	 * operation.
	 */
	void clearAll();

	/**
	 * Method which swaps the sign of current value
	 */
	void swapSign();

	/**
	 * Method used for inserting decimal point in current value
	 */
	void insertDecimalPoint();

	/**
	 * Method which inserts the given digit to current value
	 * 
	 * @param digit
	 *            digit to be inserted
	 */
	void insertDigit(int digit);

	/**
	 * Method which checks if active operand is set. Returns true if set, false
	 * otherwise.
	 * 
	 * @return true if set, false otherwise.
	 */
	boolean isActiveOperandSet();

	/**
	 * Getter for active operand
	 * 
	 * @return active operand
	 */
	double getActiveOperand();

	/**
	 * Setter for active operand
	 * 
	 * @param activeOperand
	 *            to be set
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Method which clears the active operand
	 */
	void clearActiveOperand();

	/**
	 * Getter for pending binary operation
	 * 
	 * @return pending binary operation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Setter for pending binary operation
	 * 
	 * @param op
	 *            pending binary operation to be set
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);

	/**
	 * {@inheritDoc}
	 */
	String toString();

}
