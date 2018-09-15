package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;

/**
 * This class represents the FilledCircle editor which is a JPanel. Sets up this
 * component with needed GUI elements in order to create a form which is to be
 * shown to user in case of editing filled circles properties.
 * 
 * @author Marko Mesarić
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Filled circle object
	 */
	private FilledCircle filledCircle;

	/**
	 * Center x field
	 */
	private JTextField xField;
	/**
	 * Center y field
	 */
	private JTextField yField;
	/**
	 * radius
	 */
	private JTextField radius;
	/**
	 * Foreground color area
	 */
	private JColorArea fgColorArea;
	/**
	 * Background color area
	 */
	private JColorArea bgColorArea;

	/**
	 * Constructor which receives reference to filled circle object and sets up
	 * graphical components.
	 * 
	 * @param filledCircle {@link #filledCircle}
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;

		setupGUI();
	}

	/**
	 * Auxiliary method which sets up graphical components and adds them to this
	 * component in order to be rendered back to user.
	 */
	private void setupGUI() {
		this.setLayout(new GridLayout(5, 2));

		JLabel x1 = new JLabel("Center x");
		add(x1);
		xField = new JTextField(10);
		x1.setLabelFor(xField);
		xField.setText(String.valueOf(filledCircle.getX()));
		add(xField);

		JLabel y1 = new JLabel("Center y");
		add(y1);
		yField = new JTextField(10);
		y1.setLabelFor(yField);
		yField.setText(String.valueOf(filledCircle.getY()));
		add(yField);

		JLabel radiusLabel = new JLabel("Radius");
		add(radiusLabel);
		radius = new JTextField(10);
		radiusLabel.setLabelFor(radius);
		radius.setText(String.valueOf(filledCircle.getR()));
		add(radius);

		JLabel fgColorLabel = new JLabel("Foreground color");
		fgColorArea = new JColorArea(filledCircle.getFgColor());
		add(fgColorLabel);
		add(fgColorArea);

		JLabel bgColorLabel = new JLabel("Background color");
		bgColorArea = new JColorArea(filledCircle.getBgColor());
		add(bgColorLabel);
		add(bgColorArea);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(xField.getText());
			Integer.parseInt(yField.getText());
			Integer.parseInt(radius.getText());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid input for circle specification!");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		filledCircle.setX(Integer.parseInt(xField.getText()));
		filledCircle.setY(Integer.parseInt(yField.getText()));
		filledCircle.setR(Integer.parseInt(radius.getText()));
		filledCircle.setFgColor(fgColorArea.getCurrentColor());
		filledCircle.setBgColor(bgColorArea.getCurrentColor());
	}
}
