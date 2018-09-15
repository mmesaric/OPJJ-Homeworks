package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RequestContext {

	/**
	 * Constant which defines the default status code.
	 */
	private static final int DEFAULT_STATUS_CODE = 200;

	/**
	 * Reference to output stream
	 */
	private OutputStream outputStream;
	/**
	 * Context charset
	 */
	private Charset charset;

	/**
	 * Encoding for this context used when forming header
	 */
	public String encoding;
	/**
	 * Status code for this context used when forming header
	 */
	public int statusCode;
	/**
	 * Status text for this context used when forming header
	 */
	public String statusText;
	/**
	 * Mime type for this context used when forming header
	 */
	public String mimeType;

	/**
	 * Read only map used for storing parameters
	 */
	private final Map<String, String> parameters;
	/**
	 * Map used for storing temporary parameters
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * Map used for storing persistent parameters
	 */
	private Map<String, String> persistentParameters;
	/**
	 * List for storing cookies
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Flag which signals if header's been generated
	 */
	private boolean headerGenerated;

	/**
	 * Dispatcher used for dispatching requests
	 */
	private final IDispatcher dispatcher;

	/**
	 * Constructor which delegates the object construction to other constructor.
	 * 
	 * @param outputStream
	 *            {@link #outputStream}
	 * @param parameters
	 *            {@link #parameters}
	 * @param persistentParameters
	 *            {@link #persistentParameters}
	 * @param outputCookies
	 *            {@link #outputCookies}
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {

		this(outputStream, parameters, persistentParameters, outputCookies, new HashMap<>(), null);
	}

	/**
	 * Constructor which creates the object with given values
	 * 
	 * @param outputStream
	 *            {@link #outputStream}
	 * @param parameters
	 *            {@link #parameters}
	 * @param persistentParameters
	 *            {@link #persistentParameters}
	 * @param outputCookies
	 *            {@link #outputCookies}
	 * @param temporaryParameters
	 *            {@link #temporaryParameters}
	 * @param dispatcher
	 *            {@link #dispatcher}
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {

		Objects.requireNonNull(outputStream, "Output stream can't be null");

		this.outputStream = outputStream;
		this.temporaryParameters = temporaryParameters;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.encoding = "UTF-8";
		this.statusCode = DEFAULT_STATUS_CODE;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.headerGenerated = false;
		this.dispatcher = dispatcher;
	}

	/**
	 * Getter for parameter
	 * 
	 * @param name
	 *            name of parameter
	 * @return stored parameter for given name
	 * @throws NullPointerException
	 *             if passed name is null
	 */
	public String getParameter(String name) {
		Objects.requireNonNull(name, "Parameters map key can't be null");
		return parameters.get(name);
	}

	/**
	 * Getter for set of parameter names
	 * 
	 * @return parameter name set
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Getter for persistent parameter
	 * 
	 * @param name
	 *            name of the persistent parameter
	 * @return stored persistent parameter for given name
	 * @throws NullPointerException
	 *             if passed name is null
	 */
	public String getPersistentParameter(String name) {
		Objects.requireNonNull(name, "Persistent parameter map key can't be null");
		return persistentParameters.get(name);
	}

	/**
	 * Getter for set of persistent parameter names
	 * 
	 * @return stored persistent parameter name set
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Setter for persistent parameters map
	 * 
	 * @param name
	 *            name of parameter
	 * @param value
	 *            value of parameter
	 * @throws NullPointerException
	 *             if given name or value is null
	 */
	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name, "Persistent Parameters map key can't be null");
		Objects.requireNonNull(value, "Persistent Parameters map key can't be null");

		persistentParameters.put(name, value);
	}

	/**
	 * Method used for removing persistent parameter stored under given name
	 * 
	 * @param name
	 */
	public void removePersistentParameter(String name) {
		Objects.requireNonNull(name, "Persistent Parameters map key can't be null");

		persistentParameters.remove(name);
	}

	/**
	 * Getter for temporary parameter
	 * 
	 * @param name
	 *            name of temp parameter
	 * @return temporary parameter
	 * @throws NullPointerException
	 *             if given name was null
	 */
	public String getTemporaryParameter(String name) {
		Objects.requireNonNull(name, "Temporary Parameters map key can't be null");

		return temporaryParameters.get(name);
	}

	/**
	 * Getter for temporary parameter name set
	 * 
	 * @return temporary parameter name set
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Method used for adding new temporary parameters
	 * 
	 * @param name
	 *            name of temp parameter to be added
	 * @param value
	 *            value of temp parameter to be added
	 * @throws NullPointerException
	 *             if name of value was null
	 */
	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name, "Temporary Parameters map key can't be null");
		Objects.requireNonNull(value, "Temporary Parameters map key can't be null");

		temporaryParameters.put(name, value);
	}

	/**
	 * Method used for removing temp parameter stored under given name
	 * 
	 * @param name
	 *            name of parameter to be removed
	 */
	public void removeTemporaryParameter(String name) {
		Objects.requireNonNull(name, "Temporary Parameters map key can't be null");

		temporaryParameters.remove(name);
	}

	/**
	 * Method which writes given byte array to output stream
	 * 
	 * @param data
	 *            byte array to be written
	 * @return this context
	 * @throws IOException
	 *             in case of exception
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);
		return this;
	}

	/**
	 * Method which writes given String to output stream
	 * 
	 * @param text
	 *            string to be written
	 * @return this context
	 * @throws IOException
	 *             in case of exception
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		byte[] buff = text.getBytes(charset);
		outputStream.write(buff);
		return this;
	}

	/**
	 * Auxiliary method used for generating HTTP header on request.
	 */
	private void generateHeader() {

		charset = Charset.forName(encoding);

		String header = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Content-Type: " + getContentType()
				+ "\r\n" + appendCookies() + "\r\n";

		try {
			outputStream.write(header.getBytes(Charset.forName("ISO_8859_1")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		headerGenerated = true;
	}

	/**
	 * Auxiliary method used for appending cookie lines to HTTP reponse header.
	 * 
	 * @return cookies string
	 */
	private String appendCookies() {

		if (outputCookies.isEmpty())
			return "";

		StringBuilder cookieBlock = new StringBuilder();

		for (RCCookie cookie : outputCookies) {

			StringBuilder cookieLineBuilder = new StringBuilder();

			cookieLineBuilder.append("Set-Cookie: ");
			cookieLineBuilder.append(cookie.name + "=\"" + cookie.value + "\"; ");
			cookieLineBuilder.append(cookie.domain == null ? "" : "Domain=" + cookie.domain + "; ");
			cookieLineBuilder.append(cookie.path == null ? "" : "Path=" + cookie.path + "; ");
			cookieLineBuilder.append(cookie.maxAge == null ? "" : "Max-Age=" + cookie.maxAge + "; ");

			String line = cookieLineBuilder.toString().trim();

			if (line.endsWith(";")) {
				line = line.substring(0, line.length() - 1);
			}

			cookieBlock.append(line).append("\r\n");
		}
		return cookieBlock.toString();
	}

	/**
	 * Auxiliary method used for forming content type line contained in header
	 * 
	 * @return content type line
	 */
	private String getContentType() {
		String contentType = mimeType;
		if (mimeType.startsWith("text/")) {
			contentType += "; charset=" + encoding;
		}
		return contentType;
	}

	/**
	 * Setter for encoding
	 * 
	 * @param encoding
	 *            {@link #encoding}
	 * @throws RuntimeException
	 *             if header was already generated
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated)
			throw new RuntimeException("Encoding can't be modified after header's been created.");

		this.encoding = encoding;
	}

	/**
	 * Setter for status code
	 * 
	 * @param encoding
	 *            {@link #statusCode}
	 * @throws RuntimeException
	 *             if header was already generated
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated)
			throw new RuntimeException("Status code can't be modified after header's been created.");

		this.statusCode = statusCode;
	}

	/**
	 * Setter for status text
	 * 
	 * @param encoding
	 *            {@link #statusText}
	 * @throws RuntimeException
	 *             if header was already generated
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated)
			throw new RuntimeException("Status text can't be modified after header's been created.");

		this.statusText = statusText;
	}

	/**
	 * Setter for mime type
	 * 
	 * @param encoding
	 *            {@link #mimeType}
	 * @throws RuntimeException
	 *             if header was already generated
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated)
			throw new RuntimeException("Mime type can't be modified after header's been created.");

		this.mimeType = mimeType;
	}

	/**
	 * Getter for dispatcher property
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Class which represents the model of a single cookie and its properties
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class RCCookie {

		/**
		 * cookie name
		 */
		final String name;
		/**
		 * cookie value
		 */
		final String value;
		/**
		 * corresponding domain
		 */
		final String domain;
		/**
		 * corresponding path
		 */
		final String path;
		/**
		 * max age value
		 */
		final Integer maxAge;

		/**
		 * Constructor which creates the object with given values
		 * 
		 * @param name
		 *            {@link #name}
		 * @param value
		 *            {@link #value}
		 * @param maxAge
		 *            {@link #maxAge}
		 * @param domain
		 *            {@link #domain}
		 * @param path
		 *            {@link #path}
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}

	}

	/**
	 * Auxiliary method used for adding given cookies.
	 * 
	 * @param rcCookie
	 *            cookie to be added
	 * @throws NullPointerException
	 *             if passed cookie was null.
	 */
	public void addRCCookie(RCCookie rcCookie) {
		Objects.requireNonNull(rcCookie, "Given cookie can't be null.");
		if (headerGenerated)
			throw new RuntimeException("Encoding can't be modified after header's been created.");

		outputCookies.add(rcCookie);
	}
}
