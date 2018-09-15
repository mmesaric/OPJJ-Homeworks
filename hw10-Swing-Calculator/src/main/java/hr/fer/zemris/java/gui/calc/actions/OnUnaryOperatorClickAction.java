package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.UnaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Observer which defines the behavior and actions taken after one of the unary
 * operators has been clicked. Performs the necessary actions for updating the
 * result on calculator screen.
 * 
 * @author Marko Mesarić
 *
 */
public class OnUnaryOperatorClickAction implements ActionListener {

	/**
	 * Calculator model
	 */
	private CalcModel calcModel;
	/**
	 * Variable used for storing the reference to regular unary operation
	 */
	private UnaryOperator<Double> regularUnaryOperator;
	/**
	 * Variable used for storing the reference to inverse unary operation
	 */
	private UnaryOperator<Double> inverseUnaryOperator;
	/**
	 * used for checking if check box is selected
	 */
	private JCheckBox checkBox;

	/**
	 * Default constructor which initializes the object and sets the given values.
	 * 
	 * @param calcModel
	 *            calculator model
	 * @param regularUnaryOperator
	 *            reference to regular unary operator
	 * @param inverseUnaryOperator
	 *            reference to inverse unary operator
	 * @param checkBox
	 *            check box
	 */
	public OnUnaryOperatorClickAction(CalcModel calcModel, UnaryOperator<Double> regularUnaryOperator,
			UnaryOperator<Double> inverseUnaryOperator, JCheckBox checkBox) {

		this.calcModel = calcModel;
		this.regularUnaryOperator = regularUnaryOperator;
		this.inverseUnaryOperator = inverseUnaryOperator;
		this.checkBox = checkBox;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (checkBox.isSelected()) {
			calcModel.setValue(inverseUnaryOperator.apply(calcModel.getValue()));
			return;
		}
		calcModel.setValue(regularUnaryOperator.apply(calcModel.getValue()));
	}
}
