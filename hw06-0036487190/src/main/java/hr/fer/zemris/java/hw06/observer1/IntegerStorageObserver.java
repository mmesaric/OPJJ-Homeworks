package hr.fer.zemris.java.hw06.observer1;

/**
 * This class is used as Observer interface in Observer design pattern solution.
 * It offers a single method "valueChanged" called each time the state of
 * subject is changed.
 * 
 * @author Marko Mesarić
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called each time storage's value is changed.
	 * 
	 * @param istorage
	 *            reference to object being changed.
	 */
	void valueChanged(IntegerStorage istorage);
}