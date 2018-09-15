package hr.fer.zemris.java.custom.scripting.collections;

/**
 * This class represents the implementation of the stack-like collection.
 * Provides the user with methods which are natural for stack operations and
 * hides the element storage implementation details from the user. It manages
 * its own private instance of ArrayIndexedCollection and uses it for actual
 * element storage.
 * 
 * @author Marko Mesarić
 *
 */
public class ObjectStack {

	private ArrayIndexedCollection arrayIndexedCollection;

	/**
	 * Default constructor which initializes collection used for storing elements in
	 * stack-like manner
	 */
	public ObjectStack() {
		arrayIndexedCollection = new ArrayIndexedCollection();
	}

	/**
	 * Checks if the stack is empty or not.
	 * 
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return arrayIndexedCollection.isEmpty();
	}

	/**
	 * Returns the number of elements stored in stack.
	 * 
	 * @return number of elements in stack
	 */
	public int size() {
		return arrayIndexedCollection.size();
	}

	/**
	 * Adds the given value to the stack or throws NullPointerException if null
	 * reference is trying to be added.
	 * 
	 * @param value
	 *            to be added in stack
	 */
	public void push(Object value) {

		if (value == null) {
			throw new NullPointerException("Null can't be added to collection");
		}

		arrayIndexedCollection.add(value);
	}

	/**
	 * Removes and returns the last value stored in stack.
	 * 
	 * @return the last stored element
	 */
	public Object pop() {

		Object lastObjectInStack = peek();
		arrayIndexedCollection.remove(arrayIndexedCollection.size() - 1);

		return lastObjectInStack;
	}

	/**
	 * This method returns the last value in stack without removing it
	 * 
	 * @return last value in stack
	 */
	public Object peek() {
		if (arrayIndexedCollection.size() == 0) {
			throw new EmptyStackException("There aren't any objects on the stack.");
		}

		return arrayIndexedCollection.get(arrayIndexedCollection.size() - 1);

	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		arrayIndexedCollection.clear();
	}

}
