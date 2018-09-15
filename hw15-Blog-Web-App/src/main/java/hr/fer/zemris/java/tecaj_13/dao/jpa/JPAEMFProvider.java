package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class offers the implementation of Entity manager factory provider
 * 
 * @author Marko Mesarić
 *
 */
public class JPAEMFProvider {

	/**
	 * entity manager factory object
	 */
	public static EntityManagerFactory emf;

	/**
	 * Getter for emf
	 * @return entity manager factory object
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter for emf
	 * @param emf {@link #emf}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}