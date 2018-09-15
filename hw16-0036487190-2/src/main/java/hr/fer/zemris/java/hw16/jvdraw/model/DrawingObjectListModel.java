package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;

/**
 * This class represents the implementation of Drawing object list model which
 * contains information about geometric objects currently stored in drawing
 * model.
 * 
 * @author Marko Mesarić
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model object
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructor which sets the drawing model to given value and performs the
	 * necessary wiring of listeners. In case of change on collection of geometric
	 * objects, drawing model notifies this object list model which in turn notifies
	 * the JList component so it can render necessary entries in list.
	 * 
	 * @param drawingModel {@link #drawingModel}
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		super();
		this.drawingModel = drawingModel;

		drawingModel.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(source, index0, index1);
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(source, index0, index1);
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(source, index0, index1);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}
}
