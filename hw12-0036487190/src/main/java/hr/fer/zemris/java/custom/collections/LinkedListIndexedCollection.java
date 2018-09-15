package hr.fer.zemris.java.custom.collections;

/**
 * This class is an implementation of linked list-backed collection of objects
 * which extends class Collection. Each instance of this class manages three
 * private variables. First is an integer which contains the size of this
 * collection, number of elements stored that is. Second and third variable are
 * references to the first and last node in this collection. Storing duplicate
 * elements is allowed, but storing null references is not allowed.
 * 
 * @author Marko Mesarić
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * This private static class implements the model of a single list node in this
	 * collection. It contains the reference to the previous node, reference to the
	 * next node and the reference to the value which current node contains.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;

		/**
		 * Constructor which initializes the values of the node.
		 * 
		 * @param previous
		 *            reference to the previous node in list
		 * @param next
		 *            reference to the next node in list
		 * @param value
		 *            reference to the value to be stored in node
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
	}

	private int size;
	private ListNode first;
	private ListNode last;
	public static int MINIMUM_INDEX = 0;

	/**
	 * Default constructor which creates an empty collection while setting the first
	 * and last reference to null reference.
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
	}

	/**
	 * Constructor which receives the reference to another collection and stores all
	 * of it's elements in this collection
	 * 
	 * @param other
	 *            reference to another Collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}

	/**
	 * Method which adds the the node with given value to this collection at the end
	 * in constant time (0(1)). Refuses to add null, and in that case, throws
	 * NullPointerException. If the collection is empty, adds the value at the
	 * start.
	 * 
	 * @param value
	 *            value to be added in collection
	 */
	public void add(Object value) {

		if (value == null) {
			throw new NullPointerException("Null can't be added to collection");
		}

		ListNode listNode = new ListNode(null, null, value);

		if (first == null) {
			first = listNode;
			last = listNode;
			size++;
			return;
		}

		last.next = listNode;
		listNode.previous = last;
		last = listNode;
		size++;
	}

	/**
	 * Returns the object that is stored in linked list at position index. Valid
	 * indexes are 0 to size-1. If index is invalid, IndexOutOfBoundsException is
	 * thrown. This method never has complexity greater than n/2 +1.
	 * 
	 * @param index
	 *            of the element which is to be returned
	 * @return reference to searched object
	 */
	public Object get(int index) {

		if (index < MINIMUM_INDEX || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Index has to be in [0," + (size - 1) + "] range. Was " + index);
		}

		if (first == null) {
			throw new NullPointerException("There are no elements in List.");
		}

		ListNode searchedNode = findNode(index);

		return searchedNode.value;

	}

	/**
	 * Help method which grabs the node at given index.
	 * 
	 * @param index
	 *            of node to be returned
	 * @return searched node
	 */
	private ListNode findNode(int index) {

		if (index < size / 2) {

			ListNode current = first;
			int i = 0;

			while (current != null) {

				if (i == index) {
					return current;
				}
				current = current.next;
				i++;
			}

		} else {

			ListNode current = last;
			int i = size - 1;

			while (current != null) {

				if (i == index) {
					return current;
				}
				current = current.previous;
				i--;
			}
		}
		return null;
	}

	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Method inserts the given value at the demanded position without overwriting
	 * current object stored there. Elements starting from this position are shifted
	 * one spot to the right. The legal positions are 0 to size. If position is
	 * invalid, IndexOutOfBoundsException is thrown.
	 * 
	 * @param value
	 *            which is to be inserted
	 * @param position
	 *            at which the value is to be inserted
	 */
	public void insert(Object value, int position) {

		if (position < MINIMUM_INDEX || position > size) {
			throw new IndexOutOfBoundsException("Position has to be in [0," + size + "] range. Was " + position);
		}

		ListNode newListNode = new ListNode(null, null, value);

		if (position == size) {
			newListNode.previous = last;
			last.previous.next = newListNode;
			last = newListNode;
			size++;
			return;
		}

		if (first == null) {
			add(value);
			return;
		}

		ListNode currentListNode = findNode(position);

		if (position == MINIMUM_INDEX) {
			newListNode.previous = null;
			newListNode.next = currentListNode;

			first = newListNode;
			currentListNode.previous = newListNode;
			size++;
			return;
		}

		newListNode.previous = currentListNode.previous;
		newListNode.next = currentListNode;

		currentListNode.previous.next = newListNode;
		currentListNode.previous = newListNode;
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found.
	 * 
	 * @param value
	 *            which is searched for
	 * @return index of the given value
	 */
	public int indexOf(Object value) {
		if (first == null) {
			return -1;
		}

		for (int i = 0; i < size; i++) {

			ListNode currentListNode = findNode(i);
			if (value.equals(currentListNode.value)) {
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

		if (index + 1 == size) {
			last = last.previous;
			last.next = null;
			size--;
			return;
		}

		ListNode currentListNode = findNode(index);

		if (index == 0) {
			first = first.next;
			first.previous = null;
			size--;
			return;
		}

		currentListNode.previous.next = currentListNode.next;
		currentListNode.next.previous = currentListNode.previous;
		size--;

	}

	/**
	 * Method which returns the current size of collection.
	 * 
	 * @return size of collection
	 */
	public int size() {
		return size;
	}

	/**
	 * Searches the collection for given value and returns the result accordingly.
	 * 
	 * @param value
	 *            which is searched for
	 * @return true if element is found, false otherwise
	 */
	public boolean contains(Object value) {
		if (value == null) {
			return true;
		}

		if (indexOf(value) != (-1)) {
			return true;
		}
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collection, fills it
	 * with collection content and returns the allocated array. Never returns null.
	 * 
	 * @return Created new array of objects contained in this collection.
	 */
	public Object[] toArray() {
		Object[] elements = new Object[size];

		for (int i = 0; i < size; i++) {
			elements[i] = get(i);
		}

		return elements;
	}

	/**
	 * Method which iterates through all elements of this collection and calls the
	 * method process upon them.
	 * 
	 * @param processor
	 *            reference to processor object
	 */
	public void forEach(Processor processor) {

		ListNode current = first;

		while (current != null) {
			processor.process(current.value);
			current = current.next;

		}

	}

}
