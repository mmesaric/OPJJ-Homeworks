package hr.fer.zemris.java.p12.model;

/**
 * This class models a single poll entry
 * @author Marko Mesarić
 *
 */
public class Poll {

	/**
	 * poll ID
	 */
	private long id;
	/**
	 * Poll title
	 */
	private String title;
	/**
	 * Poll message
	 */
	private String message;

	/**
	 * Default constructor
	 */
	public Poll() {
	}

	/**
	 * Getter for poll id
	 * @return {@link #id}
	 */
	public long getId() {
		return id;
	} 

	/**
	 * Setter for poll id
	 * @param id {@link #id}
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for poll title
	 * @return {@link #title}
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for poll title 
	 * @param title {@link #title}
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for poll message
	 * @return {@link #message}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for poll message
	 * @param message {@link #message}
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
