package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * This class represents the implementation of a custom JComponent, JColorArea.
 * It acts as a 15x15 squared component painted with currently selected color
 * and on click opens a color chooser dialog used for changing currently
 * selected color
 * 
 * @author Marko Mesarić
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant for default width
	 */
	private static final int DEFAULT_WIDTH = 15;
	/**
	 * Constant for default height
	 */
	private static final int DEFAULT_HEIGHT = 15;

	/**
	 * currently selected color property
	 */
	private Color selectedColor;

	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Default constructor responsible for setting the given selected color. Also,
	 * adds a mouse listener to this component, thus making it clickable in order to
	 * choose different color from JColorChooser.
	 * 
	 * @param selectedColor
	 *            selected color
	 */
	public JColorArea(Color selectedColor) {
		Objects.requireNonNull(selectedColor, "Color can't be null.");
		this.selectedColor = selectedColor;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Color color = JColorChooser.showDialog(JColorArea.this, "Choose color", selectedColor);
				if (color != null) {
					setSelectedColor(color);
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(selectedColor);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * Setter for selected color.
	 * 
	 * @param selectedColor
	 *            {@link #selectedColor}
	 */
	public void setSelectedColor(Color selectedColor) {
		Objects.requireNonNull(selectedColor, "Color to be set cannot be null");
		Color oldColor = this.selectedColor;
		this.selectedColor = selectedColor;
		fire(oldColor);
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Color change listener cannot be null.");
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Color change listener cannot be null.");
		listeners.remove(l);
	}

	/**
	 * Method used for notifying subscribers
	 * 
	 * @param oldColor
	 *            old color
	 */
	public void fire(Color oldColor) {
		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, oldColor, selectedColor);
		}
	}
}
