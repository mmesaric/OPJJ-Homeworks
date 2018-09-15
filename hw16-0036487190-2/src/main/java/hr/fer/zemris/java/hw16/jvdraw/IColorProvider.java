package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface which defines method for working with color provider. Contains
 * methods for retrieving current color, registering and unregistering from
 * color change even notifications.
 * 
 * @author Marko Mesarić
 *
 */
public interface IColorProvider {

	/**
	 * Getter for current color
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Method used for adding subscribers to color change events
	 * 
	 * @param l
	 *            color change listener to be added
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Method used for removing subscribers from color change events
	 * 
	 * @param l
	 *            color change listener to be removed
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
