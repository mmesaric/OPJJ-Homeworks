package hr.fer.zemris.java.custom.collections;

/**
 * Class which extends RuntimeException and models the custom Exception and it's
 * behavior which is thrown in case of popping elements from an empty stack.
 * 
 * @author Marko Mesarić
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/*
	 * Default constructor
	 */
	public EmptyStackException() {

	}

	/**
	 * Construct which propagates the message to the parent constructor.
	 * 
	 * @param message
	 *            detailed message about what cause the exception.
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructor which propagates cause to the parent constructor.
	 * 
	 * @param cause
	 *            reference to the case of exception which contains information
	 *            about how exception occurred in detail.
	 */
	public EmptyStackException(Throwable cause) {

	}

	/**
	 * Constructor which propagates the message along with cause to the parent
	 * constructor.
	 * 
	 * @param message
	 *            detailed message about what cause the exception.
	 * @param cause
	 *            reference to the case of exception which contains information
	 *            about how exception occurred in detail.
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

}
