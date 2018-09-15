package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents the implementation of the background color worker. Its
 * job is to, when asked to process request, check for any background color
 * parameters in context map and change the background color of index2.html page
 * if such color is found.
 * 
 * @author Marko Mesarić
 *
 */
public class BgColorWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		String value = context.getParameter("bgcolor");
		if (value == null) {
			context.getDispatcher().dispatchRequest("/private/colorchange.smscr");
			context.write("Failed switching color.");
		}

		boolean validHex = true;

		for (int i = 0; i < value.length(); i++) {
			char currentChar = value.charAt(i);
			validHex = ((currentChar >= '0' && currentChar <= '9') || (currentChar >= 'a' && currentChar <= 'f')
					|| (currentChar >= 'A' && currentChar <= 'F'));
		}

		if (!validHex) {
			context.getDispatcher().dispatchRequest("/private/colorchange.smscr");
			context.write("Failed switching color.");
		}

		context.setPersistentParameter("bgcolor", value);
		context.getDispatcher().dispatchRequest("/private/colorchange.smscr");
		context.write("Succesfully switched color.");
	}

}
