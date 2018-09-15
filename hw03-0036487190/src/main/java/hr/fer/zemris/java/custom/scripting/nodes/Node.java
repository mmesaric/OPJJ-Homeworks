package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author Marko Mesarić
 *
 */
public class Node {

	/**
	 * collection which is used for storing relevant nodes.
	 */
	private ArrayIndexedCollection arrayIndexedCollection;

	/**
	 * adds given child to an internally managed collection of children
	 * 
	 * @throws NullPointerException
	 *             in case of trying to add null
	 * @param node
	 *            child to be added to collection
	 */
	public void addChild(Node node) {

		if (node == null) {
			throw new NullPointerException("Node can't be null");
		}

		if (arrayIndexedCollection == null) {
			arrayIndexedCollection = new ArrayIndexedCollection();
		}
		arrayIndexedCollection.add(node);
	}

	/**
	 * Returns the number of children, that is the number of elements in the
	 * collection
	 * 
	 * @return number of elements in the collection
	 */
	public int numberOfChildren() {
		if (arrayIndexedCollection != null)
			return arrayIndexedCollection.size();
		return 0;
	}

	/**
	 * Gets and returns the Child node at given index.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             in case of invalid index value.
	 * @param index
	 *            of the Node to be returned.
	 * @return found node
	 */
	public Node getChild(int index) {

		if (index < 0 || index > (arrayIndexedCollection.size() - 1)) {
			throw new IndexOutOfBoundsException(
					"Index has to be in [0," + (arrayIndexedCollection.size() - 1) + "] range. Was " + index);
		}

		return (Node) arrayIndexedCollection.get(index);
	}

	/**
	 * Getter for collection of nodes.
	 * 
	 * @return collection of stored nodes.
	 */
	public ArrayIndexedCollection getArrayIndexedCollection() {
		return arrayIndexedCollection;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}
	

}
