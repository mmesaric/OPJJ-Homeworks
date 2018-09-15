package hr.fer.zemris.java.gui.calc;

/**
 * Interface which models the the calculator state observer.
 * 
 * @author Marko Mesarić
 *
 */
public interface CalcValueListener {
	/**
	 * Method which is called after model's value has been changed.
	 * 
	 * @param model
	 *            reference to calculator model which has been altered.
	 */
	void valueChanged(CalcModel model);
}