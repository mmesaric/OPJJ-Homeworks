package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * This class offers the implementation of a calculator model. Models the
 * "background" behavior of calculator, after certain actions were taken in GUI
 * display.
 * 
 * @author Marko Mesarić
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Currently stored value
	 */
	private String currentValue;
	/**
	 * used for storing active operands
	 */
	private String activeOperand;
	/**
	 * used for storing pending operations
	 */
	private DoubleBinaryOperator pendingOperation;

	/**
	 * List of registered listeners
	 */
	private List<CalcValueListener> observers;

	/**
	 * Initializes the object and creates an empty list used for storing observers.
	 */
	public CalcModelImpl() {
		this.observers = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		observers.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		observers.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		if (currentValue == null) {
			return 0;
		}
		return Double.parseDouble(currentValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		if (Double.isFinite(value) && !Double.isNaN(value)) {
			int intValue = (int) value;
			if (value == Math.floor(intValue)) {
				currentValue = Integer.toString(intValue);
				nofityObservers();
				return;
			}
			currentValue = Double.toString(value);
			nofityObservers();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		currentValue = null;
		nofityObservers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		currentValue = null;
		activeOperand = null;
		pendingOperation = null;
		nofityObservers();
	}

	/**
	 * Method used for notifying all registered observers of value change.
	 */
	private void nofityObservers() {
		for (CalcValueListener listener : observers) {
			listener.valueChanged(this);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() {
		if (currentValue != null && !currentValue.isEmpty()) {
			if (!currentValue.contains(".")) {

				int a = (int) getValue();
				if (currentValue.startsWith("-")) {
					currentValue = currentValue.substring(1, currentValue.length());
					nofityObservers();
					return;
				} else {
					currentValue = "-" + Integer.toString(a);
					nofityObservers();
					return;
				}
			}
			double value = getValue();
			value = -1 * value;
			setValue(value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() {

		if (currentValue != null && !currentValue.contains(".")) {
			currentValue += ".";
			nofityObservers();
		}

		else if (currentValue == null || currentValue.isEmpty()) {
			currentValue = "0.";
			nofityObservers();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) {

		if (currentValue == null) {
			currentValue = Integer.toString(digit);
			nofityObservers();
			return;
		}

		if (!currentValue.startsWith("-")) {
			double test = getValue() * 10 + (double) digit;
			if (Double.isInfinite(test)) {
				return;
			}
		}

		if (currentValue.startsWith("-")) {
			double test = getValue() * 10 + (double) digit;
			if (Double.isInfinite(test)) {
				return;
			}
		}

		if (currentValue.equals("0") && digit == 0) {
			return;
		}

		if (currentValue.equals("0") && digit != 0) {
			currentValue = Integer.toString(digit);
			nofityObservers();
			return;
		}

		if (currentValue.equals("0.")) {
			currentValue += Integer.toString(digit);
			nofityObservers();
			return;
		}

		currentValue += digit;
		nofityObservers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		if (activeOperand == null || activeOperand.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() {
		if (activeOperand == null || activeOperand.isEmpty()) {
			throw new IllegalStateException("Active operand is not set.");
		}
		return Double.parseDouble(activeOperand);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {

		if (Double.isFinite(activeOperand) && !Double.isNaN(activeOperand)) {
			this.activeOperand = Double.toString(activeOperand);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = null;
		pendingOperation = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		Objects.requireNonNull(op, "Double binary operator can't be null.");

		if (pendingOperation != null) {
			setActiveOperand(pendingOperation.applyAsDouble(getActiveOperand(), getValue()));
			this.pendingOperation = op;
			clear();
			return;
		}
		if (currentValue==null) return;
		activeOperand = new String(currentValue);
		pendingOperation = op;
		clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (currentValue == null) {
			return "0";
		}
		return currentValue;
	}
}
