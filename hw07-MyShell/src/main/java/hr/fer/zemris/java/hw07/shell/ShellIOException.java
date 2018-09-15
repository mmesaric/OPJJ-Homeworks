package hr.fer.zemris.java.hw07.shell;

/**
 * Custom exception thrown in case of an unexpected behavior while working with
 * shell.
 * 
 * @author Marko Mesarić
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ShellIOException(String message) {
		super(message);
	}

}
