package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class represents a Web listener, which, when context has been
 * initialized stores the current time in milliseconds (server start time) as
 * context attribute
 * 
 * @author Marko Mesarić
 *
 */
@WebListener
public class TimeListener implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		event.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

}
