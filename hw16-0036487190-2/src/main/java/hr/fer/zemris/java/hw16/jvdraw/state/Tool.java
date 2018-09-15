package hr.fer.zemris.java.hw16.jvdraw.state;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This interface models State design pattern and defines methods for different
 * states.
 * 
 * @author Marko Mesarić
 *
 */
public interface Tool {

	/**
	 * Triggered when mouse is pressed
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Triggered when mouse is released
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Triggered when mouse is clicked
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Triggered when mouse is moved
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Triggered when mouse is dragged
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method used for painting when needed.
	 * 
	 * @param g2d
	 *            graphics2d object
	 */
	public void paint(Graphics2D g2d);
}
