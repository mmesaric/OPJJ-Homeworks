package hr.fer.zemris.java.hw06.observer1;

/**
 * This class represents the concrete observer implementation used for printing
 * the squared value of current value. It implements IntegerStorageObserver and
 * overrides valueChanged method called after changing value of Integer storage.
 * 
 * @author Marko Mesarić
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Method which is called on every state change. Calculates and prints the
	 * squared value of current value stored.
	 * 
	 * @param istorage
	 *            reference to integer storage object
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {

		System.out.println("Provided new value: " + istorage.getValue() + ", square is: "
				+ (int) Math.pow(istorage.getValue(), 2));

	}

}
