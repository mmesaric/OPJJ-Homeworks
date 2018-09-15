package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.object.Circle;

/**
 * This class represents the Circle editor which is a JPanel. Sets up this
 * component with needed GUI elements in order to create a form which is to be
 * shown to user in case of editing circles properties.
 * 
 * @author Marko Mesarić
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Circle object
	 */
	private Circle circle;

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
	 * color area object
	 */
	private JColorArea colorArea;

	/**
	 * Constructor which receives reference to circle object and sets up graphical
	 * components.
	 * 
	 * @param circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;

		setupGUI();
	}

	/**
	 * Auxiliary method which sets up graphical components and adds them to this
	 * component in order to be rendered back to user.
	 */
	private void setupGUI() {
		this.setLayout(new GridLayout(4, 2));

		JLabel x1 = new JLabel("Center x");
		add(x1);
		xField = new JTextField(10);
		x1.setLabelFor(xField);
		xField.setText(String.valueOf(circle.getX()));
		add(xField);

		JLabel y1 = new JLabel("Center y");
		add(y1);
		yField = new JTextField(10);
		y1.setLabelFor(yField);
		yField.setText(String.valueOf(circle.getY()));
		add(yField);

		JLabel radiusLabel = new JLabel("Radius");
		add(radiusLabel);
		radius = new JTextField(10);
		radiusLabel.setLabelFor(radius);
		radius.setText(String.valueOf(circle.getR()));
		add(radius);

		JLabel fgColorLabel = new JLabel("Foreground color");

		colorArea = new JColorArea(circle.getFgColor());
		add(fgColorLabel);
		add(colorArea);
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
		circle.setX(Integer.parseInt(xField.getText()));
		circle.setY(Integer.parseInt(yField.getText()));
		circle.setR(Integer.parseInt(radius.getText()));
		circle.setFgColor(colorArea.getCurrentColor());
	}

}
