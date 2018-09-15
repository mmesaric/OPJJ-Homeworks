package hr.fer.zemris.java.hw16.jvdraw.editor;

import javax.swing.JPanel;

/**
 * This abstract class defines the methods which each specific geometrical
 * object editor has to offer
 * 
 * @author Marko Mesarić
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method called for checking the validity of user passed values through form
	 */
	public abstract void checkEditing();

	/**
	 * Method called after check editing is validated. Sets the geometric objects
	 * values to user given values.
	 */
	public abstract void acceptEditing();

}
