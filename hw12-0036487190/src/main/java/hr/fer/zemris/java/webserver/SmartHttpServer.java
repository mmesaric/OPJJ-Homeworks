package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This class represents the implementation of a Smart HTTP Server. It reads
 * properties necessary for setting up the server configuration, creates the
 * socket and binds it to given address and port read from properties. It
 * proceeds to listen to client requests on 127.0.0.1:5721 and
 * www.localhost.com:5721. This server implementation offers the caching
 * functionality and stores the necessary session informations is sessions map.
 * 
 * @author Marko Mesarić
 *
 */
public class SmartHttpServer {

	/**
	 * Server address
	 */
	private String address;
	/**
	 * Server domain name
	 */
	private String domainName;
	/**
	 * Server port
	 */
	private int port;
	/**
	 * Number of worker threads
	 */
	private int workerThreads;
	/**
	 * Time until session times out
	 */
	private int sessionTimeout;
	/**
	 * Map for storing all supported mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Server thread for creating and submitting the jobs
	 */
	private ServerThread serverThread;
	/**
	 * thread pool for processing requests
	 */
	private ExecutorService threadPool;
	/**
	 * Path to document root
	 */
	private Path documentRoot;
	/**
	 * Map for storing workers
	 */
	private Map<String, IWebWorker> workersMap;

	/**
	 * Map for storing sessions
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * random used for generating SID
	 */
	private Random sessionRandom = new Random();

	/**
	 * Constant which defines sid length
	 */
	private static final int SID_LENGTH = 20;
	/**
	 * Constant which defines map key under which session is stored.
	 */
	private static final String SID_STRING = "sid";

	/**
	 * Constructor which reads server configuration from properties file and creates
	 * the initialized server object
	 * 
	 * @param configFileName
	 *            name of server properties file located in config folder
	 */
	public SmartHttpServer(String configFileName) {

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("./config/server.properties"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Couldn't find given server properties file.");
		} catch (IOException e) {
			throw new RuntimeException("Error when reading server properties file.");
		}

		address = properties.getProperty("server.address");
		domainName = properties.getProperty("server.domainName");
		port = Integer.parseInt(properties.getProperty("server.port"));
		workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));

		documentRoot = Paths.get(properties.getProperty("server.documentRoot"));

		mimeTypes = loadMimeTypes(properties.getProperty("server.mimeConfig"));
		workersMap = loadWorkers(properties.getProperty("server.workers"));

		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});

		exec.scheduleAtFixedRate(new SessionsCleanup(), 5, 5, TimeUnit.MINUTES);
	}

	/**
	 * Background thread which checks every 5 minutes for invalid sessions and
	 * removes them from sessions map. Used for preventing excessive memory
	 * expenditure.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private class SessionsCleanup implements Runnable {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			synchronized (sessions) {
				Iterator<Map.Entry<String, SmartHttpServer.SessionMapEntry>> iter = sessions.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, SmartHttpServer.SessionMapEntry> entry = iter.next();
					if (entry.getValue().validUntil < (System.currentTimeMillis() / 1000)) {
						iter.remove();
					}
				}
			}
		}
	}

	/**
	 * Auxiliary method used for reading worker.properties and creating map of
	 * workers based on read configuration
	 * 
	 * @param workersFileName
	 *            name of workers properties file
	 * @return workers map
	 */
	private Map<String, IWebWorker> loadWorkers(String workersFileName) {

		Map<String, IWebWorker> map = new HashMap<>();

		try {
			List<String> lines = Files.readAllLines(Paths.get(workersFileName));

			for (String line : lines) {
				if (line != null && !line.startsWith("#") && !line.isEmpty()) {
					String[] split = line.split("=");
					if (split.length != 2)
						throw new RuntimeException("Invalid workers properties.");

					String path = split[0].trim();
					String fqcn = split[1].trim();

					if (map.containsKey(path))
						throw new RuntimeException("Worker properties file can't contain duplicate paths.");

					Class<?> referenceToClass = Class.forName(fqcn);
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					map.put(path, iww);
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("Error when reading workers properties file.");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Error when trying to find the class given in workers properties.");
		}

		return map;
	}

	/**
	 * Auxiliary method used for reading mime.properties and creating map of mime
	 * types based on read configuration
	 * 
	 * @param workersFileName
	 *            name of mime properties file
	 * @return mime types map
	 */
	private Map<String, String> loadMimeTypes(String mimeTypesFileName) {

		Map<String, String> map = new HashMap<>();

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(mimeTypesFileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Couldn't find given mime types properties file.");
		} catch (IOException e) {
			throw new RuntimeException("Error when reading mime types properties file.");
		}

		for (Object key : properties.keySet()) {
			map.put(String.valueOf(key), String.valueOf(properties.get(key)));
		}

		return map;
	}

	/**
	 * Method which creates the server thread and begins the execution of the server
	 * thread if not already initialized.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.run();
		} else if (!serverThread.isAlive()) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
	}

	/**
	 * Method which stops the execution of the server thread if running.
	 */
	protected synchronized void stop() {
		if (serverThread != null) {
			serverThread.interrupt();
			threadPool.shutdown();
		}
	}

	/**
	 * Server thread used for creating socket and binding it to read address and
	 * port. Listens for client HTTP requests, creates the jobs and submits them to
	 * thread pool for execution.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	protected class ServerThread extends Thread {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {

			try (ServerSocket socket = new ServerSocket()) {
				socket.bind(new InetSocketAddress(address, port));
				// socket.setSoTimeout(sessionTimeout * 1000);
				while (true) {
					Socket client = socket.accept();
					ClientWorker clientWorker = new ClientWorker(client);
					threadPool.submit(clientWorker);
				}
			} catch (IOException e) {
				// throw new RuntimeException("Error when creating socket.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Class which models a single session.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * session ID
		 */
		String sid;
		@SuppressWarnings("unused")
		/**
		 * host
		 */
		String host;
		/**
		 * session validity time
		 */
		long validUntil;
		/**
		 * map for storing cookies in each session
		 */
		Map<String, String> map;

		/**
		 * Default constructor
		 * 
		 * @param sid
		 *            {@link #sid}
		 * @param host
		 *            {@link #host}
		 * @param validUntil
		 *            {@link #validUntil}
		 * @param map
		 *            {@link #map}
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * This class offers an implementation of a single job which processes the
	 * client's request, parses and resolves all necessary paths and arguments.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * binded socket
		 */
		private Socket csocket;
		/**
		 * input stream
		 */
		private PushbackInputStream istream;
		/**
		 * output stream
		 */
		private OutputStream ostream;
		/**
		 * http protocol version
		 */
		private String version;
		/**
		 * HTTP method used (get/post/..)
		 */
		private String method;
		/**
		 * Host address
		 */
		private String host;
		/**
		 * map for storing parameters parsed from request
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * map for storing temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * map for storing persistent parameters for caching functionality
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		/**
		 * list of output cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * current session ID
		 */
		private String SID;
		/**
		 * Request context
		 */
		private RequestContext context;

		/**
		 * Default constructor
		 * 
		 * @param csocket
		 *            {@link #csocket}
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			try {

				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> request = readRequest();
				if (request == null || request.size() < 1) {
					return;
				}
				String[] firstLine = request.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(405, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1")) {
					sendError(505, "HTTP Version Not Supported");
					return;
				}

				host = domainName;
				for (String line : request) {
					if (line.startsWith("Host:")) {
						host = line.split(":")[1].trim();
						if (host.contains(":")) {
							host = host.substring(0, host.indexOf(':'));
						}
					}
				}

				String path = firstLine[1];
				String paramString = "";

				if (path.contains("?")) {
					char[] charPath = path.toCharArray();
					int questionMarkIndex = 0;
					for (int i = 0; i < charPath.length; i++) {
						if (charPath[i] == '?') {
							questionMarkIndex = i;
							break;
						}
					}
					paramString = path.substring(questionMarkIndex + 1);
					path = path.substring(0, questionMarkIndex);
				}

				checkSession(request, path);

				if (!paramString.isEmpty()) {
					parseParameters(paramString);
				}

				internalDispatchRequest(path, true);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Auxiliary method used for checking active sessions and modifying them if
		 * needed. Iterates through request header lines and searches for SID match. If
		 * such SID exists, time validity of that session is checked and updated. If no
		 * such SID exists, new session is "created" and added to sessions map and is
		 * further acting as stored session. Modifications over sessions map are
		 * dangerous in multi threaded environment, thus synchronized block is used.
		 * 
		 * @param request
		 *            request header lines
		 * @param path
		 *            path passed in request header
		 */
		private void checkSession(List<String> request, String path) {

			String sidCandidate = "";
			for (String line : request) {
				if (!line.startsWith("Cookie:"))
					continue;

				String cookies = line.split(":")[1].trim();
				String[] cookieLine = cookies.split(";");

				for (String singleCookie : cookieLine) {
					String cookieKey = singleCookie.split("=")[0].trim();
					String cookieValue = singleCookie.split("=")[1].trim();

					if (cookieKey == null || cookieValue == null || cookieKey.isEmpty() || cookieValue.isEmpty())
						continue;
					if (cookieKey.equals(SID_STRING))
						sidCandidate = cookieValue.substring(1, cookieValue.length() - 1);
				}

			}
			synchronized (this) {
				if (sidCandidate.isEmpty() || !sessions.containsKey(sidCandidate)) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < SID_LENGTH; i++) {
						char c = (char) (sessionRandom.nextInt(26) + 65);
						sb.append(c);
					}

					sidCandidate = sb.toString();
					SID = sidCandidate;
					addSessionEntry(sidCandidate);
				} else {
					SessionMapEntry entry = sessions.get(sidCandidate);

					if ((System.currentTimeMillis() / 1000) >= entry.validUntil) {
						sessions.remove(entry.sid);
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < SID_LENGTH; i++) {
							char c = (char) (sessionRandom.nextInt(26) + 65);
							sb.append(c);
						}

						sidCandidate = sb.toString();
						SID = sidCandidate;
						addSessionEntry(sidCandidate);
					} else {
						SID = sidCandidate;
						entry.validUntil = (System.currentTimeMillis() / 1000) + sessionTimeout;
					}
				}
				permParams = sessions.get(SID).map;
			}
		}

		/**
		 * Auxiliary method used for creating and storing new session under given SID.
		 * 
		 * @param sidCandidate
		 *            given SID
		 */
		private synchronized void addSessionEntry(String sidCandidate) {

			long valid = System.currentTimeMillis() / 1000 + sessionTimeout;
			Map<String, String> map = new ConcurrentHashMap<>();
			map.put(SID_STRING, sidCandidate);

			outputCookies.forEach((cookie) -> map.put(cookie.name, cookie.value));

			sessions.put(sidCandidate, new SessionMapEntry(sidCandidate, host, valid, map));
			outputCookies.add(new RCCookie(SID_STRING, sidCandidate, null, host, "/"));
		}

		/**
		 * Auxiliary method used for parsing parameters from path from request header
		 * 
		 * @param paramString
		 *            path passed in request header
		 */
		private void parseParameters(String paramString) {

			String[] parameters = paramString.split("&");

			for (String param : parameters) {
				String[] keyValue = param.split("=");
				params.put(keyValue[0], keyValue[1]);
			}

		}

		/**
		 * Method which reads the client HTTP request. Implemented by Marko Čupić as
		 * demonstration on class and used here. Returns the list of request header
		 * lines.
		 * 
		 * @return list of request lines
		 * @throws IOException
		 *             in case of error when reading request
		 */
		private List<String> readRequest() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			byte[] request = bos.toByteArray();
			if (request == null) {
				sendError(400, "Bad request");
				return null;
			}
			String requestHeader = new String(request, StandardCharsets.US_ASCII);

			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Method used for sending error in case of an unexpected behavior in request
		 * processing.
		 * 
		 * @param statusCode
		 *            status code
		 * @param statusText
		 *            status text
		 * @throws IOException
		 *             in case of error when sending error message
		 */
		private void sendError(int statusCode, String statusText) throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Method used for internal dispatch requesting. Based on given url path and
		 * flag 'directCall', processes and responds the user request accordingly.
		 * 
		 * @param urlPath
		 *            from request header
		 * @param directCall
		 *            flag indicating if direct call
		 * @throws IOException
		 *             in case of error
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws IOException {

			if (context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
			}

			if (urlPath.startsWith("/private/") || urlPath.equals("/private")) {
				if (directCall) {
					sendError(404, "Not found");
					return;
				}
			}

			if (urlPath.startsWith("/ext/")) {
				try {
					String fqcn = urlPath.substring(urlPath.lastIndexOf("/") + 1);
					Class<?> referenceToClass = Class.forName("hr.fer.zemris.java.webserver.workers." + fqcn);
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					iww.processRequest(context);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					throw new RuntimeException("Error when trying to find the class given in workers properties.");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			if (workersMap.containsKey(urlPath)) {
				try {
					workersMap.get(urlPath).processRequest(context);
					return;
				} catch (Exception e) {
				}
			}

			Path requestedPath = documentRoot.resolve(Paths.get(urlPath.substring(1))).toAbsolutePath();

			if (requestedPath.toString().endsWith(".smscr")) {
				try {
					byte[] buff = Files.readAllBytes(requestedPath);
					String fileContent = new String(buff, "UTF-8");

					if (context == null) {
						context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
					}

					new SmartScriptEngine(new SmartScriptParser(fileContent).getDocumentNode(), context).execute();

				} catch (NoSuchFileException e) {
					sendError(404, "Not found");
				}
				return;
			}

			if (!requestedPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}

			if (!(Files.exists(requestedPath) && !Files.isDirectory(requestedPath)
					&& Files.isReadable(requestedPath))) {
				sendError(404, "Not found");
				return;
			}

			String extension = requestedPath.toString().substring(requestedPath.toString().lastIndexOf('.') + 1);

			String mime = mimeTypes.get(extension);
			if (mime == null) {
				mime = "application/octet-stream";
			}
			if (context == null)
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);

			context.setMimeType(mime);
			context.setStatusCode(200);

			try (InputStream is = Files.newInputStream(requestedPath)) {

				byte[] buff = is.readAllBytes();
				context.write(buff);
			}
		}
	}
}
