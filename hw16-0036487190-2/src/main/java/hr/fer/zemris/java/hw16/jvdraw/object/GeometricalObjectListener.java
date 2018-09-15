package hr.fer.zemris.java.hw16.jvdraw.object;

/**
 * This class offers the method called in case of any modification on geometric
 * objects properties.
 * 
 * @author Marko Mesarić
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Method triggered in case of change on geometric object's values.
	 * @param o modified object.
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}
