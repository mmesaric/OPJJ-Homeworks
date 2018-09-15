package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This parameterized Class represents the implementation of a simple Hashtable.
 * It allows the storage of key:value pair. It offers two public constructors.
 * The first one is an empty constructor which sets the capacity of hashtable to
 * default value. The other one sets the capacity to the given number if it is
 * power of number 2. If not, first greater power of 2 is set as Hashtable
 * capacity. Class implements the Iterable interface used for iterating through
 * table elements with foreach loop. Implementation offers standard methods used
 * for data manipulation. Keys can't be null, values can.
 * 
 * @author Marko Mesarić
 *
 * @param <K>
 *            hashtable key type
 * @param <V>
 *            hashtable value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Static nested class which represents the single table entry in a Hashtable.
	 * Key is the unique identifier and value is the stored value for given key.
	 * 
	 * @author Marko Mesarić
	 *
	 * @param <K>
	 *            hashtable key type
	 * @param <V>
	 *            hashtable value type
	 */
	static class TableEntry<K, V> {

		/**
		 * key of the current entry
		 */
		private K key;
		/**
		 * value of the current entry
		 */
		private V value;
		/**
		 * reference to the next table entry object
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructor for table entry element
		 * 
		 * @throws NullPointerException
		 *             in case of key being a null reference
		 * @param key
		 *            given key value
		 * @param value
		 *            given value
		 * @param next
		 *            reference to next element
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {

			if (key == null) {
				throw new NullPointerException("Key can't be null");
			}

			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for value
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for value
		 * 
		 * @param value
		 *            to be set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter for entry key
		 * 
		 * @return entry key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Overridden toString method for returning entry in "key:value" format
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}

	}

	/**
	 * Constant for default table size.
	 */
	private static final int DEFAULT_SIZE = 16;

	/**
	 * Array of table entry references
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Element containing the number of stored entries
	 */
	private int size;
	/**
	 * Variable used for checking if table is modified while iterating through it
	 */
	private int modificationCount;

	/**
	 * Default constructor which sets the table size to default size.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.size = 0;
		this.modificationCount = 0;
		this.table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_SIZE];

	}

	/**
	 * Constructor which sets the table size to given value if power of 2, or the
	 * first greater power of 2 compared to passed capacity
	 * 
	 * @param initialCapacity
	 *            custom table size
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity can't be less than 1.");
		}

		if (initialCapacity % 2 == 0) {
			this.table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];
		}

		else {
			int power = 1;
			while (power < initialCapacity) {
				power *= 2;
			}
			this.table = (TableEntry<K, V>[]) new TableEntry[power];
		}
		this.modificationCount = 0;
		this.size = 0;
	}

	/**
	 * Method used for storing values in map. If the element with given key already
	 * exists, it's value is overwritten. New Entry is added to the end of the list.
	 * 
	 * @throws NullPointerException
	 *             in case of key being a null reference
	 * @param key
	 *            given key of the entry
	 * @param value
	 *            value to be stored
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key can't be null");
		}

		TableEntry<K, V> newEntry = new TableEntry<>(key, value, null);

		int slotNumber = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> head = table[slotNumber];

		if (head == null) {
			table[slotNumber] = newEntry;
		}

		else {

			if (head.next == null) {
				if (head.getKey().equals(key)) {
					head.setValue(value);
					return;
				}
			}

			while (head.next != null) {
				if (head.getKey().equals(key)) {
					head.setValue(value);
					return;
				}
				head = head.next;
			}

			head.next = newEntry;
		}
		size++;
		modificationCount++;
		checkIfOverfill();
	}

	/**
	 * Method which is called to check if the number of entries is greater or equal
	 * to 75% of slot number. In case when that's true, a new table with the
	 * capacity equal to double of current capacity is created and filled with
	 * currently stored elements
	 */
	@SuppressWarnings("unchecked")
	private void checkIfOverfill() {

		if ((float) size >= (float) 0.75 * table.length) {

			TableEntry<K, V>[] doubleCapacityTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
			copyElementsFromTable(doubleCapacityTable);

			table = doubleCapacityTable;

		}

	}

	/**
	 * Auxiliary method used for iterating through elements from old table when
	 * placing them in newly created table.
	 * 
	 * @param doubleCapacityTable
	 *            reference to the new table array
	 */
	private void copyElementsFromTable(TableEntry<K, V>[] doubleCapacityTable) {

		for (int i = 0; i < table.length; i++) {

			TableEntry<K, V> head = table[i];

			if (head == null) {
				continue;
			}

			while (head.next != null) {
				putToNewTable(new TableEntry<K, V>(head.getKey(), head.getValue(), null), doubleCapacityTable);
				head = head.next;
			}

			if (head != null) {
				putToNewTable(new TableEntry<K, V>(head.getKey(), head.getValue(), null), doubleCapacityTable);
			}
		}

	}

	/**
	 * Auxiliary method for placing the entries from old table to the newly created
	 * table with double capacity.
	 * 
	 * @param entry
	 *            entry which is to be placed in new table array
	 * @param doubleCapacityTable
	 *            reference to the new table
	 */
	private void putToNewTable(TableEntry<K, V> entry, TableEntry<K, V>[] doubleCapacityTable) {

		int slotNumber = Math.abs(entry.getKey().hashCode()) % doubleCapacityTable.length;

		TableEntry<K, V> head = doubleCapacityTable[slotNumber];

		if (head == null) {
			doubleCapacityTable[slotNumber] = entry;
		}

		else {

			while (head.next != null) {
				head = head.next;
			}

			head.next = entry;
		}
	}

	/**
	 * Method used for retrieving the values based on given key. If the object
	 * doesn't exist, null is returned.
	 * 
	 * @param key
	 *            given key value under which value is stored
	 * @return value of the searched object, null if not found
	 */
	public V get(Object key) {

		if (key == null) {
			return null;
		}

		TableEntry<K, V> entry = findRelevantEntry(key);

		return entry == null ? null : entry.getValue();
	}

	/**
	 * Method which checks if table contains entry with given key element
	 * 
	 * @param key
	 *            which is searched for
	 * @return true if table contains, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		return findRelevantEntry(key) != null;
	}

	/**
	 * Auxiliary method used for finding the table entry based on given key value.
	 * Used by several methods in this class
	 * 
	 * @param otherKey
	 *            key of the searched entry
	 * @return table entry if found, null otherwise
	 */
	private TableEntry<K, V> findRelevantEntry(Object otherKey) {
		int slotNumber = Math.abs(otherKey.hashCode()) % table.length;

		TableEntry<K, V> head = table[slotNumber];

		if (head == null) {
			return null;
		}

		if (head.getKey().equals(otherKey)) {
			return head;
		}

		while (head.next != null) {
			if (head.getKey().equals(otherKey)) {
				break;
			}
			head = head.next;
		}
		if (head != null) {
			if (head.getKey().equals(otherKey)) {
				return head;
			}
		}
		return null;
	}

	/**
	 * Getter for size of table
	 * 
	 * @return number of elements stored
	 */
	public int size() {
		return size;
	}

	/**
	 * Method which clears whole table and sets the size to zero.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * Method which checks if table contains given value
	 * 
	 * @param otherValue
	 *            value which is searched for
	 * @return true if contains, false otherwise
	 */
	public boolean containsValue(Object otherValue) {

		for (int i = 0; i < table.length; i++) {

			TableEntry<K, V> head = table[i];

			if (head == null) {
				continue;
			}

			if (head.getValue().equals(otherValue)) {
				return true;
			}

			while (head.next != null) {

				if (head.getValue() == null) {
					if (otherValue == null) {
						return true;
					}
				}

				else if (head.getValue().equals(otherValue)) {
					return true;
				}
				head = head.next;
			}

			if (head != null) {
				if (head.getValue() == null) {
					if (otherValue == null) {
						return true;
					}
				}

				else if (head.getValue().equals(otherValue)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method which removes the entry from table with given key value if it exists.
	 * If null is passed, nothing happens (since entry key can't be null). Elements
	 * following the index of the removed element are shifted to the left by one
	 * index value
	 * 
	 * @param key
	 *            key of the entry to be removed
	 */
	public void remove(Object key) {

		if (key == null) {
			return;
		}

		int slotNumber = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> head = table[slotNumber];

		if (head == null) {
			return;
		}

		if (head.getKey().equals(key)) {
			table[slotNumber] = head.next;
			head = null;
			size--;
			modificationCount++;
			return;
		}

		while (head.next != null) {
			if (head.next.getKey().equals(key)) {

				TableEntry<K, V> previousEntry = head;
				previousEntry.next = previousEntry.next.next;
				head.next = null;
				size--;
				modificationCount++;
				break;
			}
			head = head.next;
		}
	}

	/**
	 * Method for checking is table is empty.
	 * 
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Overridden toString method used for formatting output of the Table (i.e
	 * [key1:value1, key2:value2, ...])
	 */
	@Override
	public String toString() {
		StringBuilder entries = new StringBuilder();
		entries.append("[");

		for (int i = 0; i < table.length; i++) {

			TableEntry<K, V> head = table[i];

			if (head == null) {
				continue;
			}

			while (head.next != null) {
				entries.append(head);
				entries.append(", ");
				head = head.next;
			}

			if (head != null) {
				entries.append(head);
				entries.append(", ");
			}
		}
		String mapString = entries.toString();
		if (!mapString.isEmpty()) {
			mapString = mapString.substring(0, mapString.length() - 2);
		}
		mapString = mapString + "]";
		return mapString;

	}

	/**
	 * Inner class used for implementation of Iterator which is used for iteration
	 * over entries in Hashtable.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * index of the current slot if table
		 */
		private int currentIndex;
		/**
		 * Variable used for checking if table is modified when iterating through it
		 */
		private int iteratorModificationsNumber;
		/**
		 * Reference to current table entry
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Reference to next table entry if one exists
		 */
		private TableEntry<K, V> entry;

		/**
		 * Default constructor which iterates through table slots until first element is
		 * found.
		 */
		public IteratorImpl() {
			for (int i = 0; i < table.length; i++) {

				TableEntry<K, V> head = table[i];

				if (head == null) {
					continue;
				}

				entry = head;
				currentEntry = head;
				currentIndex = i;
				iteratorModificationsNumber = modificationCount;
				break;
			}
		}

		/**
		 * Method used for checking if there are any elements left in table
		 * 
		 * @throws ConcurrentModificationException
		 *             if table is modified while being iterated
		 * @return true if there are more elements, false otherwise
		 */
		@Override
		public boolean hasNext() {

			if (iteratorModificationsNumber != modificationCount) {
				throw new ConcurrentModificationException(
						"Can't remove elements from table while iterating though table");
			}

			return entry != null;

		}

		/**
		 * Method used for retrieving the next entry if such exists.
		 * 
		 * @throws ConcurrentModificationException
		 *             if table is modified while being iterated
		 * @throws NoSuchElementException
		 *             if there are no more elements
		 * @return next table entry which is found
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {

			if (iteratorModificationsNumber != modificationCount) {
				throw new ConcurrentModificationException(
						"Can't remove elements from table while iterating though table");
			}

			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in map.");
			}

			currentEntry = entry;

			if (currentEntry.next != null) {
				entry = currentEntry.next;
			} else {
				if (currentIndex + 1 < table.length) {
					for (int i = currentIndex + 1; i < table.length; i++) {

						if (table[i] == null) {
							continue;
						}

						entry = table[i];
						currentIndex = i;
						break;
					}
				} else {
					entry = null;
				}
			}

			return currentEntry;
		}

		/**
		 * Method used for removing the last retrieved element. * @throws
		 * 
		 * @throws ConcurrentModificationException
		 *             if table is modified while being iterated
		 */
		@Override
		public void remove() {
			if (iteratorModificationsNumber != modificationCount) {
				throw new ConcurrentModificationException(
						"Can't remove elements from table while iterating though table");
			}

			iteratorModificationsNumber++;

			SimpleHashtable.this.remove(currentEntry.getKey());
		}
	}

	/**
	 * Method which returns a newly created, our custom, Iterator object used for
	 * iteration over table elements
	 * 
	 * @return new object of custom iterator implementation
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

}
