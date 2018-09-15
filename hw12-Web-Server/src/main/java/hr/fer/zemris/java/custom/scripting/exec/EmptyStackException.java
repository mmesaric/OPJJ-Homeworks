package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Custom exception thrown when pop() or peek() methods are called over an empty
 * stack.
 * 
 * @author Marko Mesarić
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyStackException(String message) {
		super(message);
	}
}
