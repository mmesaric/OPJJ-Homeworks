package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents the Home worker used for storing color to temporary
 * parameter map if such a parameter exists. If not, default, #7F7F7F color is
 * stored
 * 
 * @author Marko Mesarić
 *
 */
public class Home implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		String param = context.getPersistentParameter("bgcolor");

		context.setTemporaryParameter("background", param == null ? "7F7F7F" : param);

		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}
}
