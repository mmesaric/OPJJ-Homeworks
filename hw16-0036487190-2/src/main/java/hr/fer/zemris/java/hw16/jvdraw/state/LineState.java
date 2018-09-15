package hr.fer.zemris.java.hw16.jvdraw.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;

/**
 * This class represents the Line State. Defines behaviors for different mouse
 * actions and motions used while drawing lines on canvas.
 * 
 * @author Marko Mesarić
 *
 */
public class LineState implements Tool {

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
	 * 
	 * @param fgColorProvider
	 *            {@link #fgColorProvider}
	 * @param drawingModel
	 *            {@link #drawingModel}
	 * @param canvas
	 *            {@link #canvas}
	 */
	public LineState(IColorProvider fgColorProvider, DrawingModel drawingModel, JDrawingCanvas canvas) {
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

			drawingModel.add(new Line((int) start.getX(), (int) end.getX(), (int) start.getY(), (int) end.getY(),
					fgColorProvider.getCurrentColor()));
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
			g2d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
		}
	}

}
