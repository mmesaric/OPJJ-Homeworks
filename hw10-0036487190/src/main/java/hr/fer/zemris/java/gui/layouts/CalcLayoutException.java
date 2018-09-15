package hr.fer.zemris.java.gui.layouts;

/**
 * Custom exception used in CalcLayout and thrown in case of exception when
 * modeling the layout.
 * 
 * @author Marko Mesarić
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CalcLayoutException(String message) {
		super(message);
	}

}
