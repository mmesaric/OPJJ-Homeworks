package hr.fer.zemris.java.custom.scripting.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class used for demonstration of "zbrajanje.smscr" script execution after
 * being compiled with SmartScriptEngine.
 * 
 * @author Marko Mesarić
 *
 */
public class ZbrajanjeDemo {
	
	/**
	 * Method which starts the execution of the program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		String documentBody = loader("smartscripts/zbrajanje.smscr");
//		String documentBody = loader("smartscripts/home.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> tempParams = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
//		tempParams.put("a", "4");
//		tempParams.put("b", "2");
//		tempParams.put("background", "00FF00");
//		tempParams.put("zbroj", "6");
//		tempParams.put("", "4");
		parameters.put("a", "4");
		parameters.put("b", "2");


		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies, tempParams, null)).execute();
	}

	/**
	 * Auxiliary method used for reading contents of the given file.
	 * 
	 * @param filename
	 *            file to be read
	 * @return read content as String
	 */
	private static String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = TreeWriter.class.getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

}
