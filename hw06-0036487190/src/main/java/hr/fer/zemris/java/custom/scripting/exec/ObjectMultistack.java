package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents the implementation of an object multistack. For each
 * key, user is able to store multiple values in a stack-like manner. Keys are
 * instances of class String. Values associated with those keys are instances of
 * class ValueWrapper. Internal map is used for mapping each key to first list
 * node (represented by MultistackEntry object which stores a reference to
 * ValueWrapper object and a reference to the next entry).
 * 
 * @author Marko Mesarić
 *
 */
public class ObjectMultistack {

	/**
	 * This class represents the implementation of an entry that acts as a node of a
	 * single-linked list. With help of this structure, stack-like behavior is
	 * possible by forming chains of entries.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private static class MultistackEntry {

		/**
		 * attribute used for storing reference to value wrapper object
		 */
		private ValueWrapper value;
		/**
		 * attribute used for storing reference to the next entry
		 */
		private MultistackEntry next;

		/**
		 * Custom constructor which sets the value to given value.
		 * 
		 * @param value
		 *            value to be set
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
			next = null;
		}

		/**
		 * Getter for value property
		 * 
		 * @return reference to value
		 */
		public ValueWrapper getValue() {
			return value;
		}

		/**
		 * reference to next entry in list
		 * 
		 * @return next entry reference
		 */
		public MultistackEntry getNext() {
			return next;
		}

		/**
		 * Setter for next entry reference
		 * 
		 * @param next
		 *            next entry reference
		 */
		public void setNext(MultistackEntry next) {
			this.next = next;
		}
	}

	/**
	 * Internal map used for storing strings and a reference to the head of list
	 * acting as stack
	 */
	private Map<String, MultistackEntry> map;

	/**
	 * Custom constructor used for initializing map.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Method used for pushing given value to stack based on key value.
	 * 
	 * @throws NullPointerException
	 *             in case if given key or value is null
	 * @param name
	 * @param valueWrapper
	 */
	public void push(String name, ValueWrapper valueWrapper) {

		Objects.requireNonNull(name, "Key can't be null");
		Objects.requireNonNull(valueWrapper, "Null value can't be stored.");

		MultistackEntry entry = new MultistackEntry(valueWrapper);

		if (!map.containsKey(name) || map.get(name) == null) {
			map.put(name, entry);
			return;
		}
		
		MultistackEntry currentNode = map.get(name);
		
		while (currentNode.getNext() != null) {
			currentNode = currentNode.getNext();
		}
		currentNode.setNext(entry);
	}

	/**
	 * Method used for removing elements from top of stack based on key value.
	 * 
	 * @param name
	 *            map key
	 * @return valueWrapper popped wrapper object.
	 */
	public ValueWrapper pop(String name) {
		checkBeforeRetrieving(name);

		MultistackEntry entry = getEntry(name);
		ValueWrapper value;

		if (entry.getNext() != null) {
			value = entry.getNext().getValue();
			entry.setNext(null);
		} else {
			value = entry.getValue();
//			entry = null;
			map.put(name, null);
		}

		return value;
	}

	/**
	 * Method used for retrieving elements from top of stack based on key value
	 * without removing them.
	 * 
	 * @param name
	 *            map key
	 * @return valueWrapper element from the top of the stack.
	 */
	public ValueWrapper peek(String name) {
		checkBeforeRetrieving(name);

		MultistackEntry entry = getEntry(name);
		ValueWrapper value;
		if (entry.getNext() != null) {
			value = entry.getNext().getValue();
		} else {
			value = entry.getValue();
		}

		return value;
	}

	/**
	 * Auxiliary method used for checking the validity of passed key String
	 * 
	 * @throws EmptyStackException
	 *             in case of an empty stack
	 * @param name
	 *            key value to be checked
	 */
	public void checkBeforeRetrieving(String name) {
		Objects.requireNonNull(name, "Key can't be null");

		if (map.get(name) == null) {
			throw new EmptyStackException("Can't pop from empty stack.");
		}
	}

	/**
	 * Auxiliary method used for retrieving entry based on given key value
	 * 
	 * @param name
	 *            key value
	 * @return found entry
	 */
	public MultistackEntry getEntry(String name) {
		Objects.requireNonNull(name, "Key can't be null");

		MultistackEntry entry = map.get(name);
		MultistackEntry temp = map.get(name);

		while (temp.getNext() != null) {
			entry = temp;
			temp = entry.getNext();
		}
		return entry;

	}

	/**
	 * Method which checks if stack stored under given key is empty
	 * 
	 * @param name
	 *            key value
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		Objects.requireNonNull(name, "Key can't be null");
		return map.get(name) == null;
	}
}
