package hr.fer.zemris.java.galerija.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.galerija.model.PictureDB;

/**
 * Servlet context listener which calls the corresponding method for
 * initializing the photo database.
 * 
 * @author Marko Mesarić
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		PictureDB.initializeDB(sce);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
