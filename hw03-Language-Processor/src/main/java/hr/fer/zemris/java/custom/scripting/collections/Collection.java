package hr.fer.zemris.java.custom.scripting.collections;

/**
 * This class represents some general collection of objects. The methods which
 * are empty are overridden within child classes. This class does not have any
 * storage capabilities.
 * 
 * @author Marko Mesarić
 *
 */
public class Collection {

	/**
	 * Default constructor with protected modifier
	 */
	protected Collection() {

	}

	/**
	 * Method which checks if the collection is empty and returns the result
	 * accordingly to value which is retrieved by calling method size
	 * 
	 * @return boolean true if collection is empty, and false otherwise
	 */
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method which returns the size of the collection. Implemented here to always
	 * return 0 in order to be overridden in extending classes
	 * 
	 * @return always 0
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method which adds demanded value in collection. Here implemented with empty
	 * body.
	 * 
	 * @param value
	 *            to be added into collection
	 */
	public void add(Object value) {

	}

	/**
	 * Checks the collection for first appearance of passed value. Does nothing.
	 * 
	 * @param value
	 *            search value
	 * @return boolean always false
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * This method returns true if the collection contains given value as determined
	 * by equals method and removes one occurrence of that value Implemented to
	 * always return false.
	 * 
	 * @param value
	 *            given value to be removed
	 * @return always false;
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collection, fills it
	 * with collection content and returns the allocated array. Never returns null.
	 * Implemented here to always throw UnsupportedOperationException.
	 * 
	 * @return Created new array of objects contained in this collection.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method to iterate through objects of this collection and do some operations
	 * with them through given processor object. Implemented here as an empty method
	 * 
	 * @param processor
	 *            object who's method implements adding elements to collection
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method which adds all elements from other collection to this collection
	 * without changing the other collection and its elements.
	 * 
	 * @param other
	 *            collection with contains elements to be added
	 */
	public void addAll(Collection other) {

		class LocalProcessor extends Processor {
			public void process(Object obj) {
				add(obj);
			}

		}

		other.forEach(new LocalProcessor());
	}

	/**
	 * Method which removes all elements from collection. Implemented here as an
	 * empty method
	 */
	public void clear() {

	}
}
