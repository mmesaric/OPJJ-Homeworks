package hr.fer.zemris.java.webserver;

/**
 * Interface which models the Dispatcher behavior
 * 
 * @author Marko Mesarić
 *
 */
public interface IDispatcher {
	/**
	 * Method used for dispatching request with given URL
	 * 
	 * @param urlPath
	 *            URL of job to be executed
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
