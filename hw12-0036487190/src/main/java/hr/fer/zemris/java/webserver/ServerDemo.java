package hr.fer.zemris.java.webserver;

/**
 * Class used for starting the execution of SmartHTTPServer. <b>IMPORTANT</b> In
 * order for the server to be executed successfully, you must change the paths
 * in config/server.properties according to your own project path location.
 * 
 * @author Marko Mesarić
 *
 */
public class ServerDemo {

	/**
	 * Main method which starts the execution of the program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("server.properties");
		server.start();

	}

}
