package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * This interface defines a method which all color change listeners implement
 * triggered in case of color change event.
 * 
 * @author Marko Mesarić
 *
 */
public interface ColorChangeListener {
	/**
	 * Method which defines behavior in case of color change event.
	 * 
	 * @param source
	 *            source of color change
	 * @param oldColor
	 *            old color
	 * @param newColor
	 *            new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
