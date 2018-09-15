package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Observer which defines the behavior and actions taken after
 * digit button in calculator has been clicked.
 * 
 * @author Marko Mesarić
 *
 */
public class OnDigitClickAction implements ActionListener {

	/**
	 * Calculator model
	 */
	private CalcModel calcModel;
	/**
	 * Which digit was clicked
	 */
	private int clickedDigit;

	/**
	 * Constructor which sets the values to given values.
	 * @param calcModel current calculator model
	 * @param clickedDigit clicked digit
	 */
	public OnDigitClickAction(CalcModel calcModel, int clickedDigit) {
		this.calcModel = calcModel;
		this.clickedDigit = clickedDigit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		calcModel.insertDigit(clickedDigit);
	}

}
