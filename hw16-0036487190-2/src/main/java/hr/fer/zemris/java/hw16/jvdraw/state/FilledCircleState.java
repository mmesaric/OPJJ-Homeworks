package hr.fer.zemris.java.hw16.jvdraw.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;

/**
 * This class represents the Filled Circle State. Defines behaviors for
 * different mouse actions and motions used while drawing filled circles on
 * canvas.
 * 
 * @author Marko Mesarić
 *
 */
public class FilledCircleState implements Tool {

	/**
	 * background color provider
	 */
	private IColorProvider bgColorProvider;
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
	 * @param fgColorProvider
	 *            {@link #fgColorProvider}
	 * @param bgColorProvider
	 *            {@link #bgColorProvider}
	 * @param drawingModel
	 *            {@link #drawingModel}
	 * @param canvas
	 *            {@link #canvas}
	 */
	public FilledCircleState(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DrawingModel drawingModel,
			JDrawingCanvas canvas) {
		super();
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
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
			drawingModel.add(new FilledCircle((int) start.getX(), (int) start.getY(), radius,
					fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor()));
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
			g2d.setColor(bgColorProvider.getCurrentColor());
			Ellipse2D.Double circle = new Ellipse2D.Double(start.getX() - radius, start.getY() - radius, radius * 2,
					radius * 2);
			g2d.fill(circle);

			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.drawOval((int) start.getX() - radius, (int) start.getY() - radius, radius * 2, radius * 2);
		}
	}

}
