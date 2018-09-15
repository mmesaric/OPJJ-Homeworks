package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * This class defines methods which every drawing model listener should
 * implement. Triggered in case of changes on collection of geometrical objects
 * or changes of their properties.
 * 
 * @author Marko Mesarić
 *
 */
public interface DrawingModelListener {

	/**
	 * Trigger in case of objects being added to collection
	 * 
	 * @param source
	 *            source of event
	 * @param index0
	 *            beginning interval index
	 * @param index1
	 *            ending interval index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Trigger in case of objects being removed from collection
	 * 
	 * @param source
	 *            source of event
	 * @param index0
	 *            beginning interval index
	 * @param index1
	 *            ending interval index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Trigger in case of change of objects values
	 * 
	 * @param source
	 *            source of event
	 * @param index0
	 *            beginning interval index
	 * @param index1
	 *            ending interval index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
