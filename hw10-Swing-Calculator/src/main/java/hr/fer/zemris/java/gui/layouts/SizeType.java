package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;

/**
 * Interface which defines the method used for calculating different types of size.
 * @author Marko Mesarić
 *
 */
public interface SizeType {
	
	/**
	 * Calculate dimension based on type of size.
	 * @param component
	 * @return
	 */
	public Dimension getTypeOfSize(Component component);
}
