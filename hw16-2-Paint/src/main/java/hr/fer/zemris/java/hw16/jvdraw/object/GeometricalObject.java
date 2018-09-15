package hr.fer.zemris.java.hw16.jvdraw.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;

/**
 * Abstract class which defines the methods which each geometric object should
 * override. Has a collection of listeners in case of objects properties being
 * modified.
 * 
 * @author Marko Mesarić
 *
 */
public abstract class GeometricalObject {

	/**
	 * Collection of listeners
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Used in visitor design pattern
	 * 
	 * @param v
	 *            object visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Method which adds the given geometric object listener to collection of
	 * listeners
	 * 
	 * @param l
	 *            listener to be added
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Listener can't be null");
		listeners.add(l);
	}

	/**
	 * Method which removes the given geometric object listener from collection of
	 * listeners
	 * 
	 * @param l
	 *            listener to be removed
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Listener can't be null");
		listeners.remove(l);
	}

	/**
	 * Method used for notifying all subscribed listeners
	 */
	public void notifyObservers() {
		for (GeometricalObjectListener listener : listeners) {
			listener.geometricalObjectChanged(this);
		}
	}

	/**
	 * Method which each specific geometric objects implements and returns the
	 * concrete geometric object editor.
	 * 
	 * @return geometric object editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
}
