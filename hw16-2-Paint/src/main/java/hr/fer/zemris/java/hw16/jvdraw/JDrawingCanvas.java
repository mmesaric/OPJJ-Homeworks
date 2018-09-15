package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObjectPainter;

/**
 * This class represents the drawing canvas used for drawing geometric objects
 * (lines, circles and filled circles). It is subscribed to drawing model and
 * whenever there is a modification on list stored in that model, repaint method
 * is called and objects are rendered again.
 * 
 * @author Marko Mesarić
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * JVDraw frame
	 */
	private JVDraw jvDraw;

	/**
	 * Constructor which sets up initial member values and registers itself as
	 * listener to drawing model
	 * 
	 * @param drawingModel {@link #drawingModel}
	 * @param jvDraw {@link #jvDraw}
	 */
	public JDrawingCanvas(DrawingModel drawingModel, JVDraw jvDraw) {
		this.drawingModel = drawingModel;
		this.jvDraw = jvDraw;

		drawingModel.addDrawingModelListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHints(rh);

		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);

		for (int i = 0; i < drawingModel.getSize(); i++) {
			drawingModel.getObject(i).accept(painter);
		}
		jvDraw.getCurrentState().paint(g2d);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
