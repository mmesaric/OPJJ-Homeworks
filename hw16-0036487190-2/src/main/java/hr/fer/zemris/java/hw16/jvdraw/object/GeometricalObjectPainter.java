package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

/**
 * This class represents a Geometric object visitor used for painting all
 * geometric objects upon visiting such objects.
 * 
 * @author Marko Mesarić
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics object
	 */
	private Graphics2D graphics;

	/**
	 * Sets up the graphics object
	 * @param graphics {@link #graphics}
	 */
	public GeometricalObjectPainter(Graphics2D graphics) {
		super();
		this.graphics = graphics;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		graphics.setColor(line.getFgColor());
		graphics.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		graphics.setColor(circle.getFgColor());
		graphics.drawOval(circle.getX() - circle.getR(), circle.getY() - circle.getR(), 2 * circle.getR(),
				2 * circle.getR());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {

		graphics.setColor(filledCircle.getBgColor());
		Ellipse2D.Double circle = new Ellipse2D.Double(filledCircle.getX() - filledCircle.getR(),
				filledCircle.getY() - filledCircle.getR(), filledCircle.getR() * 2, filledCircle.getR() * 2);
		graphics.fill(circle);

		graphics.setColor(filledCircle.getFgColor());
		graphics.drawOval(filledCircle.getX() - filledCircle.getR(), filledCircle.getY() - filledCircle.getR(),
				2 * filledCircle.getR(), 2 * filledCircle.getR());
	}

	@Override
	public void visit(ConvexPolygon polygon) {
		graphics.setColor(polygon.getBgColor());
		Polygon poly = new Polygon(polygon.getXs(), polygon.getYs(), polygon.getXs().length);
		graphics.drawPolygon(poly);
		
		graphics.setColor(polygon.getFgColor());
		graphics.fill(poly);
	}
}
