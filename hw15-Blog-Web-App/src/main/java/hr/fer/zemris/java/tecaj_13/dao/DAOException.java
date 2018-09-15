package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Custom exception thrown in case of error when handling data persistence
 * 
 * @author Marko Mesarić
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}
}