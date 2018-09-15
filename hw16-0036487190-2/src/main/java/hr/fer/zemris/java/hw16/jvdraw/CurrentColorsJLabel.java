package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.util.Objects;

import javax.swing.JLabel;

/**
 * This class represents the custom JLabel which holds information of current
 * foreground and background color. Through color change listeners, this label
 * is updated accordingly with information about currently picked colors.
 * 
 * @author Marko Mesarić
 *
 */
public class CurrentColorsJLabel extends JLabel implements ColorChangeListener {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Foreground color provider
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Background color provider
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Default constructor used for setting class attributes to given values and
	 * subscribing for color change events
	 * 
	 * @param fgColorProvider
	 *            {@link #fgColorProvider}
	 * @param bgColorProvider
	 *            {@link #bgColorProvider}
	 */
	public CurrentColorsJLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		super();
		Objects.requireNonNull(fgColorProvider, "Foreground color provider cannot be null.");
		Objects.requireNonNull(bgColorProvider, "Background color provider cannot be null.");
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {

		StringBuilder stringBuilder = new StringBuilder();
		if (source.equals(fgColorProvider)) {
			Color bgColor = bgColorProvider.getCurrentColor();
			stringBuilder.append("Foreground color: (")
					.append(newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue() + "), ");
			stringBuilder.append("background color: (")
					.append(bgColor.getRed() + ", " + bgColor.getGreen() + ", " + bgColor.getBlue() + ").");
		} else if (source.equals(bgColorProvider)) {
			Color fgColor = fgColorProvider.getCurrentColor();
			stringBuilder.append("Foreground color: (")
					.append(fgColor.getRed() + ", " + fgColor.getGreen() + ", " + fgColor.getBlue() + "), ");
			stringBuilder.append("background color: (")
					.append(newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue() + ").");
		}

		this.setText(stringBuilder.toString());
	}
}
