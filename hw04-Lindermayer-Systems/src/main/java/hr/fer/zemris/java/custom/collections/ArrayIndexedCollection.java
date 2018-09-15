package hr.fer.zemris.java.custom.collections;

/**
 * This class is an implementation of resizable array-backed collection of
 * objects which extends class Collection. Each instance of this class manages
 * three private variables. Size represents the current size of the collection,
 * that is, the number of elements actually stored. Capacity represents the
 * current collection capacity, the number of object references which can be
 * stored in array called elements. DEFAULT_CAPACITY is the constant, default
 * capacity with which the elements array is allocated. Storing duplicate values
 * is allowed, storing null values is not allowed.
 * 
 * @author Marko Mesarić
 *
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	private int capacity;
	private Object[] elements;
	public static int DEFAULT_CAPACITY = 16;
	public static int MINIMUM_CAPACITY = 1;
	public static int MINIMUM_INDEX = 0;

	/**
	 * Default constructor which delegates DEFAULT_CAPACITY accordingly.
	 */
	public ArrayIndexedCollection() {

		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor which allocates elements array to given capacity and sets the
	 * collection capacity to that value. In case of an illegal capacity (less than
	 * 1), throws IllegalArgumentException with according message.
	 * 
	 * @param initialCapacity
	 *            given capacity for collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {

		if (initialCapacity < MINIMUM_CAPACITY)
			throw new IllegalArgumentException("Capacity can't be less than 1. Was " + initialCapacity);

		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
	}

	/**
	 * Constructor which fills delegates reference to some other Collection object
	 * along with default capacity integer.
	 * 
	 * @param other
	 *            reference to another Collection object
	 */
	public ArrayIndexedCollection(Collection other) {

		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Constructor which sets this collection capacity to the size of other
	 * collection or given, initial capacity, whichever is greater. Allocates the
	 * elements array to that size and fills this collection with elements from
	 * other given collection. In case of reference being null, throws
	 * NullPointerException with according message.
	 * 
	 * @param other
	 *            reference to another Collection object
	 * @param initialCapacity
	 *            value with which the array is reallocated
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {

		if (other == null) {
			throw new NullPointerException("Collection is null");
		}

		if (initialCapacity < other.size()) {
			this.capacity = other.size();
		} else {
			this.capacity = initialCapacity;
		}

		this.elements = new Object[capacity];

		addAll(other);
	}

	/**
	 * Method which adds given object reference to first empty space in elements
	 * array. In case of it being full, reallocates the array by doubling it's size.
	 * The method refuses to add null to collection and throws NullPointerException
	 * in that case.
	 * 
	 * @param value
	 *            value to be added in collection
	 */
	@Override
	public void add(Object value) {

		if (value == null) {
			throw new NullPointerException("Null can't be added to collection");
		}

		if (size == capacity) {

			reallocateElementsLength();

		}

		elements[size] = value;
		size++;
	}

	/**
	 * Method which reallocates the elements array by doubling its size by creating
	 * a new array and returning the reference to it.
	 */
	private void reallocateElementsLength() {
		this.capacity = capacity * 2;

		Object[] reallocated = new Object[capacity];

		for (int i = 0; i < size; i++) {
			reallocated[i] = get(i);
		}

		elements = reallocated;
	}

	/**
	 * Returns the object that is stored in backing array at position index. Valid
	 * indexes are 0 to size-1. In case of an invalid index, the method throws
	 * IndexOutOfBoundsException.
	 * 
	 * @param index
	 * @return
	 */
	public Object get(int index) {

		if (index < MINIMUM_INDEX || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Index has to be in [0," + (size - 1) + "] range. Was " + index);
		}

		return elements[index];
	}

	/**
	 * Removes all elements from the collection while not changing the allocated
	 * elements array capacity. Writes null references into the backing array.
	 */
	@Override
	public void clear() {

		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Inserts the given value at the given position in array without overwriting
	 * the current element. Elements at position and at greated positions are
	 * shifted one place towards the end of the array. Legal positions are 0 to
	 * size. If position is invalid, IndexOutOfBoundsException is thrown
	 * 
	 * @param value
	 *            to be added in collection
	 * @param position
	 *            to which the value is stored
	 */
	public void insert(Object value, int position) {
		if (position < MINIMUM_INDEX || position > size) {
			throw new IndexOutOfBoundsException("Position has to be in [0," + size + "] range. Was " + position);
		}

		if (size == capacity) {
			reallocateElementsLength();
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		size++;
		elements[position] = value;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. In case of search for null
	 * reference, -1 is returned, since collection can't store null references.
	 * 
	 * @param value
	 *            which is searched for in collection
	 * @return index of given value, or -1 if no such value in collection
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Method which removes element at specified index from this collection. Element
	 * that was previously at location index+1 is on location index after this
	 * operation and so forth.. Valid indexes are 0 to size-1. In case of invalid
	 * index, throws an IndexOutOfBoundsException.
	 * 
	 * @param index
	 *            of value to be removed
	 */
	public void remove(int index) {
		if (index < MINIMUM_INDEX || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Index has to be in [0," + (size - 1) + "] range. Was " + index);
		}

		elements[index] = null;
		size--;

		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size] = null;

	}

	/**
	 * Searches the collection for given value and returns the result accordingly.
	 * 
	 * @param value
	 *            which is searched for
	 * @return true if element is found, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}

		if (indexOf(value) != (-1)) {
			return true;
		}
		return false;
	}

	/**
	 * Method which returns the current size of collection.
	 * 
	 * @return size of collection
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Allocates new array with size equals to the size of this collection, fills it
	 * with collection content and returns the allocated array. Never returns null.
	 * 
	 * @return Created new array of objects contained in this collection.
	 */
	@Override
	public Object[] toArray() {
		Object[] pomElements = new Object[size];

		for (int i = 0; i < size; i++) {
			pomElements[i] = get(i);
		}

		return pomElements;
	}

	/**
	 * Method which iterates through all elements of this collection and calls the
	 * method process upon them.
	 * 
	 * @param processor
	 *            reference to processor object
	 */
	@Override
	public void forEach(Processor processor) {

		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
}
