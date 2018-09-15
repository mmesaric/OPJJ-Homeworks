package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton design pattern used as DAO provider
 * 
 * @author Marko Mesarić
 *
 */
public class DAOProvider {

	/**
	 * Singleton dao object
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Getter for DAO object
	 * 
	 * @return singleton object
	 */
	public static DAO getDAO() {
		return dao;
	}

}