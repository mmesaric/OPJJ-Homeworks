package hr.fer.zemris.java.custom.collections;

/**
 * This class represents the implementation of a simple dictionary or map. By
 * the given key, it stores the given value. Implementation is handled by
 * storing objects of class TableEntry in ArrayIndexedCollection, which acts as
 * dynamic collection based on array. This class offers basic methods used for
 * storing objects and retrieving objects from dictionary combined with some
 * methods used for checking if dictionary is empty or retrieving it's size.
 * 
 * @author Marko Mesarić
 *
 */
public class Dictionary {

	/**
	 * This nested class represents the single entry in dictionary. It consists of
	 * key and value. Keys are unique and storing two different values with the same
	 * key value is impossible.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private class TableEntry {
		/**
		 * Unique Key by which value is stored
		 */
		private Object key;
		/**
		 * Value which is stored
		 */
		private Object value;

		/**
		 * Constructor for setting up a basic Table Entry
		 * 
		 * @throws IllegalArgumentException
		 *             in case key being a null reference
		 * @param key
		 *            key of the object
		 * @param value
		 *            value of the object
		 */
		public TableEntry(Object key, Object value) {
			if (key == null) {
				throw new IllegalArgumentException("Map key can't be null.");
			}
			this.key = key;
			this.value = value;
		}

		/**
		 * Overridden equals method. Two Table Entry objects are the same if their keys
		 * are equal.
		 * 
		 * @param obj
		 *            object to be compared with this object
		 * @return true of objects are equal, false otherwise
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}

			if (!(obj instanceof TableEntry)) {
				return false;
			}

			TableEntry tableEntry = (TableEntry) obj;
			if (tableEntry.key == this.key) {
				return true;
			}
			return false;
		}

		/**
		 * Overridden toString method used for formatting dictionary
		 * 
		 * @return String contents of dictionary in formatted String representation
		 */
		@Override
		public String toString() {
			return String.valueOf(key) + ":" + String.valueOf(value);
		}
	}

	/**
	 * Constant which defines the minimal value for capacity
	 */
	private static final int MIN_CAPACITY = 1;

	/**
	 * Collection used for storing Table Entries
	 */
	private ArrayIndexedCollection arrayIndexedCollection;

	/**
	 * Default constructor which initializes dictionary storage.
	 */
	public Dictionary() {
		arrayIndexedCollection = new ArrayIndexedCollection();
	}

	/**
	 * Default constructor which initializes dictionary storage to given value.
	 * 
	 * @param initialCapacity
	 *            initial capacity value of the collection to be set
	 */
	public Dictionary(int initialCapacity) {
		if (initialCapacity < MIN_CAPACITY) {
			throw new IllegalArgumentException("Capacity can't be less than 1. Was " + initialCapacity);
		}
		arrayIndexedCollection = new ArrayIndexedCollection(initialCapacity);
	}

	/**
	 * Method which checks if dictionary is empty
	 * 
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return arrayIndexedCollection.isEmpty();
	}

	/**
	 * Method which returns the number of elements stored currently in dictionary
	 * 
	 * @return size of dictionary
	 */
	public int size() {
		return arrayIndexedCollection.size();
	}

	/**
	 * Method which clears all elements from dictionary.
	 */
	public void clear() {
		arrayIndexedCollection.clear();
	}

	/**
	 * Method used for storing given key:value pair to dictionary. If dictionary
	 * already contains the given key, current value is overwritten with given value
	 * 
	 * @throws IllegalArgumentException
	 *             in case of key being a null reference
	 * @param key
	 *            unique key by which value is stored
	 * @param value
	 *            value stored in dictionary by given key
	 */
	public void put(Object key, Object value) {
		if (key == null) {
			throw new IllegalArgumentException("Map key can't be null.");
		}

		TableEntry tableEntry = new TableEntry(key, value);

		if (arrayIndexedCollection.contains(tableEntry)) {
			TableEntry currentObject = (TableEntry) arrayIndexedCollection
					.get(arrayIndexedCollection.indexOf(tableEntry));
			currentObject.value = value;
		}

		else {
			arrayIndexedCollection.add(tableEntry);
		}

	}

	/**
	 * Method which returns the value stored in dictionary by given key. If there is
	 * no such key in dictionary, null is returned
	 * 
	 * @throws IllegalArgumentException
	 *             in case of key being a null reference
	 * @param key
	 *            by which value is stored in dictionary
	 * @return searched value
	 */
	public Object get(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("Map key can't be null.");
		}

		TableEntry tableEntry = new TableEntry(key, null);

		if (arrayIndexedCollection.indexOf(tableEntry) == -1) {
			return null;
		}

		TableEntry currentObject = (TableEntry) arrayIndexedCollection.get(arrayIndexedCollection.indexOf(tableEntry));

		return currentObject.value;
	}

	/**
	 * Overridden toString method which iterates through all Table Entries and
	 * formats the output of all dictionary elements
	 */
	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		class LocalProcessor extends Processor {
			public void process(Object o) {
				stringBuilder.append(String.valueOf(o)).append("\n");
			}
		}
		;

		arrayIndexedCollection.forEach(new LocalProcessor());

		return stringBuilder.toString();
	}

}
