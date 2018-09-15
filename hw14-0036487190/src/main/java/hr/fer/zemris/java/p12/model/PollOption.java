package hr.fer.zemris.java.p12.model;

/**
 * This class models a single Poll option
 * 
 * @author Marko Mesarić
 *
 */
public class PollOption {
	/**
	 * poll option id
	 */
	private long id;
	/**
	 * poll option title
	 */
	private String optionTitle;
	/**
	 * poll option link
	 */
	private String optionLink;
	/**
	 * ID of corresponding poll
	 */
	private long pollID;
	/**
	 * votes count for this poll option
	 */
	private long votesCount;

	/**
	 * Default constructor
	 */
	public PollOption() {
		super();
	}

	/**
	 * Getter for id
	 * 
	 * @return {@link #id}
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * @param id {@link #id}
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for option title 
	 * @return {@link #optionTitle}
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for option title
	 * @param id {@link #optionTitle}
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for option link 
	 * @return {@link #optionLink}
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for option link
	 * @param id {@link #optionLink}
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Getter for poll ID 
	 * @return {@link #pollID}
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for poll ID
	 * @param id {@link #pollID}
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}
	
	/**
	 * Getter for votes count 
	 * @return {@link #votesCount}
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for votes count
	 * @param id {@link #votesCount}
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
