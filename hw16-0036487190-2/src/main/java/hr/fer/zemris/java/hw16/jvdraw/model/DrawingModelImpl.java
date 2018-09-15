package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObjectListener;

/**
 * This class represents the basic implementation of a drawing model interface.
 * 
 * @author Marko Mesarić
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * Collection of stored geometric objects
	 */
	private List<GeometricalObject> geometricalObjects = new ArrayList<>();
	/**
	 * COllection of subscribed listeners
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return geometricalObjects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index > geometricalObjects.size() - 1)
			throw new IllegalArgumentException("Invalid index.");

		return geometricalObjects.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(GeometricalObject object) {
		geometricalObjects.add(object);

		object.addGeometricalObjectListener(new GeometricalObjectListener() {

			@Override
			public void geometricalObjectChanged(GeometricalObject o) {
				for (DrawingModelListener listener : listeners) {
					listener.objectsChanged(DrawingModelImpl.this, geometricalObjects.indexOf(o),
							geometricalObjects.indexOf(o));
				}
			}
		});
		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, geometricalObjects.indexOf(object), geometricalObjects.indexOf(object));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Listener can't be null");
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Listener can't be null");
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(GeometricalObject object) {
		int index0 = geometricalObjects.indexOf(object);
		geometricalObjects.remove(object);

		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, index0, index0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = geometricalObjects.indexOf(object);

		if (offset == -1) {
			if (index == 0)
				return;

			GeometricalObject temp = geometricalObjects.get(index - 1);

			geometricalObjects.set(index - 1, object);
			geometricalObjects.set(index, temp);

		} else if (offset == 1) {

			if (index == getSize() - 1)
				return;

			GeometricalObject temp = geometricalObjects.get(index + 1);

			geometricalObjects.set(index + 1, object);
			geometricalObjects.set(index, temp);
		}

		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, 0, getSize());
		}

	}
}
