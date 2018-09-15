package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;

/**
 * This class represents the Line editor which is a JPanel. Sets up this
 * component with needed GUI elements in order to create a form which is to be
 * shown to user in case of editing lines properties.
 * 
 * @author Marko Mesarić
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Line object
	 */
	private Line line;

	/**
	 * x1 coordinate field
	 */
	private JTextField x1Field;
	/**
	 * x2 coordinate field
	 */
	private JTextField x2Field;
	/**
	 * y1 coordinate field
	 */
	private JTextField y1Field;
	/**
	 * y2 coordinate field
	 */
	private JTextField y2Field;
	/**
	 * foreground color area
	 */
	private JColorArea colorArea;

	/**
	 * Constructor which receives reference to line object and sets up graphical
	 * components.
	 * 
	 * @param line {@link #line}
	 */
	public LineEditor(Line line) {
		super();
		this.line = line;

		setupGUI();
	}

	/**
	 * Auxiliary method which sets up graphical components and adds them to this
	 * component in order to be rendered back to user.
	 */
	private void setupGUI() {
		this.setLayout(new GridLayout(5, 2));

		JLabel x1 = new JLabel("x1");
		add(x1);
		x1Field = new JTextField(10);
		x1.setLabelFor(x1Field);
		x1Field.setText(String.valueOf(line.getX1()));
		add(x1Field);

		JLabel x2 = new JLabel("x2");
		add(x2);
		x2Field = new JTextField(10);
		x2.setLabelFor(x2Field);
		x2Field.setText(String.valueOf(line.getX2()));
		add(x2Field);

		JLabel y1 = new JLabel("y1");
		add(y1);
		y1Field = new JTextField(10);
		y1.setLabelFor(y1Field);
		y1Field.setText(String.valueOf(line.getY1()));
		add(y1Field);

		JLabel y2 = new JLabel("y2");
		add(y2);
		y2Field = new JTextField(10);
		y2.setLabelFor(y2Field);
		y2Field.setText(String.valueOf(line.getY2()));
		add(y2Field);

		JLabel fgColorLabel = new JLabel("Foreground color");

		colorArea = new JColorArea(line.getFgColor());
		add(fgColorLabel);
		add(colorArea);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(x1Field.getText());
			Integer.parseInt(x2Field.getText());
			Integer.parseInt(y1Field.getText());
			Integer.parseInt(y2Field.getText());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid input for coordinates!");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		line.setX1(Integer.parseInt(x1Field.getText()));
		line.setX2(Integer.parseInt(x2Field.getText()));
		line.setY1(Integer.parseInt(y1Field.getText()));
		line.setY2(Integer.parseInt(y2Field.getText()));
		line.setFgColor(colorArea.getCurrentColor());
	}

}
