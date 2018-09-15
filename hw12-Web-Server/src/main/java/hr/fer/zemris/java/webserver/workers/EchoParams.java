package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents the implementation of worker which goes through all
 * parameters in context and creates a table containing those parameters.
 * 
 * @author Marko Mesarić
 *
 */
public class EchoParams implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		String html = "<html><table border=\"1\">\n";

		context.setMimeType("text/html");

		for (String name : context.getParameterNames()) {
			html += "<tr><td>" + context.getParameter(name) + "</td></tr>\n";
		}
		html += "</table></html>\n";
		try {
			context.write(html);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
