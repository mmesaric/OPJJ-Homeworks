package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This class offers the implementation of a simple list model used for storing
 * and displaying currently generated prime numbers.
 * 
 * @author Marko Mesarić
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * Constant which defines first prime number
	 */
	private static final int STARTING_PRIME = 1;

	/**
	 * Used for storing the current prime number
	 */
	private int currentPrime;
	/**
	 * List for storing generated prime numbers
	 */
	private List<Integer> elements = new ArrayList<>();
	/**
	 * List for storing all registered observers
	 */
	private List<ListDataListener> observers = new ArrayList<>();

	/**
	 * Constructor which creates the lists used for storing numbers and observers
	 * and defines the first prime number.
	 */
	public PrimListModel() {
		this.currentPrime = STARTING_PRIME;
		elements.add(currentPrime++);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return elements.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getElementAt(int index) {
		if (index < 0 && index > getSize() - 1) {
			throw new IllegalArgumentException("Non-existant index was given.");
		}

		return elements.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Given listener can't be null.");
		observers.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Given listener can't be null.");
		observers.remove(l);
	}

	/**
	 * Method which, when called upon, generates the next prime number.
	 */
	public void next() {

		int size = getSize();
		for (int i = currentPrime;; i++) {
			boolean primeCheck = true;

			for (int j = 2; j <= Math.sqrt(i); j++) {

				if (i % j == 0) {
					primeCheck = false;
					break;
				}
			}
			if (primeCheck) {
				currentPrime = i;
				elements.add(currentPrime++);
				ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, size, size);
				notifyObservers(listDataEvent);
				break;
			}
		}
	}

	/**
	 * Method used for notifying all active listeners after value change has been
	 * made.
	 * 
	 * @param listDataEvent
	 *            which triggered the value change
	 */
	private void notifyObservers(ListDataEvent listDataEvent) {

		for (ListDataListener listener : observers) {
			listener.intervalAdded(listDataEvent);
		}
	}
}
