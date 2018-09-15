package hr.fer.zemris.java.hw06.observer2;

/**
 * This class encapsulates Integer storage. It stores the reference to according
 * integer storage object, value before change and value after change.
 * 
 * @author Marko Mesarić
 *
 */
public class IntegerStorageChange {

	/**
	 * Reference to integer storage object
	 */
	private final IntegerStorage integerStorage;
	/**
	 * stored value before change
	 */
	private final int valueBeforeChange;
	/**
	 * new value after change
	 */
	private final int valueAfterChange;

	/**
	 * Default constructor used for setting initial values of the wrapper
	 * 
	 * @param integerStorage
	 *            reference to integer storage object
	 * @param valueBeforeChange
	 *            value before change
	 * @param valueAfterChange
	 *            value after change
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int valueBeforeChange, int valueAfterChange) {

		this.integerStorage = integerStorage;
		this.valueBeforeChange = valueBeforeChange;
		this.valueAfterChange = valueAfterChange;
	}

	/**
	 * Getter for integer storage object reference
	 * 
	 * @return reference to integer storage object
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	/**
	 * Getter for value before change
	 * 
	 * @return value before change
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}

	/**
	 * Getter for value after change
	 * 
	 * @return value after change
	 */
	public int getValueAfterChange() {
		return valueAfterChange;
	}
}
