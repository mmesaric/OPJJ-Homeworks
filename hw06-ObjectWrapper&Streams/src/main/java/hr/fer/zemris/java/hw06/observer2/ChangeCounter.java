package hr.fer.zemris.java.hw06.observer2;

/**
 * This class represents the concrete observer implementation used for tracking
 * changes of value attribute. It implements IntegerStorageObserver and
 * overrides valueChanged method called after changing value of Integer storage.
 * 
 * @author Marko Mesarić
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * attribute used for tracking number of changes
	 */
	private int counter;

	/**
	 * Constructor which initializes counter to 0.
	 */
	public ChangeCounter() {
		counter = 0;
	}

	/**
	 * Method which is called on every state change. Tracks the number of changes
	 * and prints it to standard output.
	 * 
	 * @param istorage
	 *            reference to wrapper of integer storage object
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
