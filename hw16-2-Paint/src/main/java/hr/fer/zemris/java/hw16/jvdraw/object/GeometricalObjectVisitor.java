package hr.fer.zemris.java.hw16.jvdraw.object;

/**
 * This class models the Geometric object listeners. Defines methods for
 * visiting different types of specific geometric object implementations.
 * 
 * @author Marko Mesarić
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Called upon visiting line object
	 * @param line visited line object
	 */
	public abstract void visit(Line line);

	/**
	 * Called upon visiting circle object
	 * @param circle visited circle object
	 */
	public abstract void visit(Circle circle);

	/**
	 * Called upon visiting filled circle object
	 * @param filledCircle visited filled circle object
	 */
	public abstract void visit(FilledCircle filledCircle);
	
	public abstract void visit(ConvexPolygon polygon);

}
