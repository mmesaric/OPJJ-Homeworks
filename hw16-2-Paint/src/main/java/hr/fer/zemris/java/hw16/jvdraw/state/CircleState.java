package hr.fer.zemris.java.hw16.jvdraw.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.Circle;

/**
 * This class represents the Circle State. Defines behaviors for different mouse
 * actions and motions used while drawing circles on canvas.
 * 
 * @author Marko Mesarić
 *
 */
public class CircleState implements Tool {

	/**
	 * foreground color provider
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Canvas
	 */
	private JDrawingCanvas canvas;

	/**
	 * flag used for checking if initial click happened
	 */
	private boolean clickedOnce = false;
	/**
	 * starting point
	 */
	private Point start;
	/**
	 * ending point
	 */
	private Point end;

	/**
	 * radius
	 */
	private int radius;

	/**
	 * 
	 * @param fgColorProvider
	 *            {@link #fgColorProvider}
	 * @param drawingModel
	 *            {@link #drawingModel}
	 * @param canvas
	 *            {@link #canvas}
	 */
	public CircleState(IColorProvider fgColorProvider, DrawingModel drawingModel, JDrawingCanvas canvas) {
		super();
		this.fgColorProvider = fgColorProvider;
		this.drawingModel = drawingModel;
		this.canvas = canvas;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!clickedOnce) {
			start = e.getPoint();
			clickedOnce = true;
		} else {
			end = e.getPoint();

			radius = (int) end.distance(start);
			drawingModel
					.add(new Circle((int) start.getX(), (int) start.getY(), radius, fgColorProvider.getCurrentColor()));
			clickedOnce = false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (clickedOnce) {
			end = e.getPoint();
			radius = (int) end.distance(start);
			canvas.repaint();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		if (clickedOnce) {
			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.drawOval((int) start.getX() - radius, (int) start.getY() - radius, radius * 2, radius * 2);
		}

	}

}
