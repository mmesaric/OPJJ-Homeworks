package hr.fer.zemris.java.hw06.observer2;

/**
 * This class represents the concrete observer implementation used for printing
 * the double value of current value but only first n times, after that, this
 * observer unregisters itself. It implements IntegerStorageObserver and
 * overrides valueChanged method called after changing value of Integer storage.
 * 
 * @author Marko Mesarić
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times value is doubled.
	 */
	private int n;

	/**
	 * Constructor used for setting the n value to given value
	 * 
	 * @param n
	 *            Number of times value is doubled
	 */
	public DoubleValue(int n) {
		this.n = n;
	}

	/**
	 * Method which is called on every state change. Used for printing the double
	 * value of current value but only first n times, after that, this observer
	 * unregisters itself.
	 * 
	 * @param istorage
	 *            reference to wrapper of integer storage object
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Double value: " + istorage.getValueAfterChange() * 2);

		if (--n == 0) {
			istorage.getIntegerStorage().removeObserver(this);
		}
	}

}
