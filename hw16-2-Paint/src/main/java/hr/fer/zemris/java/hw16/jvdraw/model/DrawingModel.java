package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;

/**
 * This interface offers the methods which each Drawing model should implement.
 * Offers the methods for working with collection of geometric objects and
 * listeners.
 * 
 * @author Marko Mesarić
 *
 */
public interface DrawingModel {

	/**
	 * Getter for current collection size
	 * 
	 * @return number of geometric objects currently stored
	 */
	public int getSize();

	/**
	 * Gets the geometric object stored at given index
	 * 
	 * @param index
	 *            index of geometric object
	 * @return geometric object stored at given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Method used for adding new Geometric objects to collection
	 * 
	 * @param object
	 *            to be added
	 */
	public void add(GeometricalObject object);

	/**
	 * Method used for adding subscribers to drawing model events
	 * 
	 * @param l
	 *            drawing model listener to be added
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Method used for removing subscribers from drawing model events
	 * 
	 * @param l
	 *            drawing model listener to be removed
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Method used for removing given Geometric objects from collection
	 * 
	 * @param object
	 *            to be removed
	 */
	void remove(GeometricalObject object);

	/**
	 * Method used for changing the order of elements currently stored in the list
	 * 
	 * @param object
	 *            to be shifted
	 * @param offset
	 *            +1 or -1.
	 */
	void changeOrder(GeometricalObject object, int offset);

}
