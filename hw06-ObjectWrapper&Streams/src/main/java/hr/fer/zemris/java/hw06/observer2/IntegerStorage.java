package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class acts as subject in Observer design pattern solution. It is used
 * for storing a single int value. It holds the list of registered observers who
 * "listen" and wait for modifications of value property. This is is possible by
 * notification to all active and registered observers in case of value
 * modification. Observers can be unregistered accordingly. All active observers
 * are stored in list internally.
 * 
 * @author Marko Mesarić
 *
 */
public class IntegerStorage {

	/**
	 * current int value
	 */
	private int value;
	/**
	 * list of currently registered observers
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructor used for setting the int value to given value and creating a new
	 * Array list used for storing observers
	 * 
	 * @param initialValue
	 *            value to be set
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}

	/**
	 * Method which registers passed observer by adding him to the list
	 * 
	 * @throws NullPointerException
	 *             in case of observer being a null reference
	 * @param observer
	 *            observer to be added
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Method which removes passed observer from list
	 * 
	 * @throws NullPointerException
	 *             in case of observer being a null reference
	 * @param observer
	 *            observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);

		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Method which removes all registered observers.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for value
	 * 
	 * @return int value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for attribute value. All registered observers are notified of state
	 * change. Object of class IntegerStorageChange is used as a wrapper for integer
	 * storage value before and after change.
	 * 
	 * @param value
	 *            new value to be set
	 */
	public void setValue(int value) {

		if (this.value != value) {
			
			int valueBefore = value;
			this.value = value;

			if (observers != null) {

				IntegerStorageChange integerStorageChange = new IntegerStorageChange(this, valueBefore ,value);

				for (IntegerStorageObserver observer : new ArrayList<>(observers)) {
					observer.valueChanged(integerStorageChange);
				}
			}
		}
	}

}