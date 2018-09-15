package hr.fer.zemris.java.webserver;

/**
 * Interface which defines the method implemented by all web workers for
 * processing requests in given context.
 * 
 * @author Marko Mesarić
 *
 */
public interface IWebWorker {

	/**
	 * Method used for implementation of each worker's job processing
	 * 
	 * @param context
	 *            context in which request if processed
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
