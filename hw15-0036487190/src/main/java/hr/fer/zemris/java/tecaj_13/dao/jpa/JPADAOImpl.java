package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This class offers the implementation of methods used for data persistence
 * 
 * @author Marko Mesarić
 *
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getUserByNick(String nick) throws DAOException {
		try {
			BlogUser blogUser = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.ByNick", BlogUser.class)
					.setParameter("nick", nick).getSingleResult();

			return blogUser;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getEntryByID(Long entryID) {
		try {
			BlogEntry blogEntry = JPAEMProvider.getEntityManager()
					.createNamedQuery("BlogEntry.ByEntryID", BlogEntry.class).setParameter("entryID", entryID)
					.getSingleResult();

			return blogEntry;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogUser> getRegisteredAuthors() throws DAOException {
		try {
			List<BlogUser> authors = JPAEMProvider.getEntityManager()
					.createNamedQuery("BlogUser.AllAuthors", BlogUser.class).getResultList();
			return authors;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewUser(BlogUser blogUser) {
		JPAEMProvider.getEntityManager().persist(blogUser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewEntry(BlogEntry entry) {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntry(BlogEntry entry) {
		JPAEMProvider.getEntityManager().merge(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
	}

}