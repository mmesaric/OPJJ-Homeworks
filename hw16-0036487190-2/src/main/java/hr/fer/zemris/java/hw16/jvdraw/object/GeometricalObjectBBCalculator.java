package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class represents a Geometric object visitor used for calculating the
 * bounding box of all geometric objects currently rendererd on canvas and
 * stored in drawing model collection.
 * 
 * @author Marko Mesarić
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Result bounding box
	 */
	private Rectangle result;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		if (result == null) {
			result = new Rectangle(new Point(line.getX1(), line.getY1()));
			result.add(new Point(line.getX2(), line.getY2()));
		} else {
			result.add(new Point(line.getX1(), line.getY1()));
			result.add(new Point(line.getX2(), line.getY2()));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		if (result == null) {
			result = new Rectangle(new Point(circle.getX() - circle.getR(), circle.getY() - circle.getR()));
			result.add(new Point(circle.getX() + circle.getR(), circle.getY() + circle.getR()));
		} else {
			result.add(new Point(circle.getX() - circle.getR(), circle.getY() - circle.getR()));
			result.add(new Point(circle.getX() + circle.getR(), circle.getY() + circle.getR()));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle circle) {
		if (result == null) {
			result = new Rectangle(new Point(circle.getX() - circle.getR(), circle.getY() - circle.getR()));
			result.add(new Point(circle.getX() + circle.getR(), circle.getY() + circle.getR()));
		} else {
			result.add(new Point(circle.getX() - circle.getR(), circle.getY() - circle.getR()));
			result.add(new Point(circle.getX() + circle.getR(), circle.getY() + circle.getR()));
		}
	}
	
	@Override
	public void visit(ConvexPolygon polygon) {
		if (result == null) {
			result = new Rectangle(new Point(polygon.getXs()[0], polygon.getYs()[0]));
			for (int i=1; i<polygon.getXs().length; i++) {
				result.add(new Point(polygon.getXs()[i], polygon.getYs()[i]));
			}			
		}
		else {
			for (int i=0; i<polygon.getXs().length; i++) {
				result.add(new Point(polygon.getXs()[i], polygon.getYs()[i]));
			}	
		}
		
	}

	/**
	 * Getter for bounding box.
	 * @return bounding box.
	 */
	public Rectangle getBoundingBox() {
		return result.getBounds();
	}

}
