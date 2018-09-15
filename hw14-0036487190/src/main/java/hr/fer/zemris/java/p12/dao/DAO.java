package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

	/**
	 * Retrieves the list of Polls
	 * 
	 * @return
	 */
	List<Poll> getAllPolls();

	/**
	 * Retrieves the list of poll options for given pollID
	 * 
	 * @param id
	 *            given poll ID
	 * @return list of poll options stored under given poll ID
	 */
	List<PollOption> getPollOptionsByID(long pollID);

	/**
	 * Retrieves the poll with id equal to given poll ID parameter.
	 * 
	 * @param pollID
	 *            of searched Poll
	 * @return searched poll
	 */
	Poll getPollByID(long pollID);

	/**
	 * Method which increments votes count of poll option stored under given id
	 * 
	 * @param id
	 *            of poll option
	 */
	void updateVotesCount(long id);

	/**
	 * Method which retrieves poll ID based on given poll option ID.
	 * 
	 * @param pollOptionID
	 *            id of poll option
	 * @return poll ID
	 */
	long getPollIDByOptionID(long pollOptionID);
}
