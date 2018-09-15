package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This interface defines methods for data persistence
 * 
 * @author Marko Mesarić
 *
 */
public interface DAO {

	/**
	 * Finds and returns the blog entry with given id.
	 * 
	 * @param id
	 *            entry key
	 * @return entry or null if entry wasn't found
	 * @throws DAOException
	 *             in case of exception
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Finds and returns the Blog user with given nick
	 * 
	 * @param nick
	 *            nick of blog user
	 * @return found user
	 * @throws DAOException
	 *             in case of exception
	 */
	BlogUser getUserByNick(String nick) throws DAOException;

	/**
	 * Method which returns a list of all blog registered blog users
	 * 
	 * @return list of all blog users
	 * @throws DAOException
	 *             in case of exception
	 */
	List<BlogUser> getRegisteredAuthors() throws DAOException;

	/**
	 * Method used for creating a new blog user from given object
	 * 
	 * @param blogUser
	 *            to be created
	 */
	void createNewUser(BlogUser blogUser);

	/**
	 * Method which finds blog entry stored under given id
	 * 
	 * @param entryID
	 *            of searched entry
	 * @return blog entry with given id
	 */
	BlogEntry getEntryByID(Long entryID);

	/**
	 * Method used for creating and storing given entry
	 * 
	 * @param entry
	 *            blog entry to be persisted
	 */
	void createNewEntry(BlogEntry entry);

	/**
	 * Method which updates the given blog entry
	 * 
	 * @param entry
	 *            entry to be updated
	 */
	void updateEntry(BlogEntry entry);

	/**
	 * Method which persists the given comment
	 * 
	 * @param comment
	 *            comment to be persisted
	 */
	void addComment(BlogComment comment);

}