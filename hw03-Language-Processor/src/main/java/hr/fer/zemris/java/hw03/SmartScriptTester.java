package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class for testing parser on given assignment example. First (and only)
 * Command line argument should be the name of the file in which string input is
 * located
 * 
 * @author Marko Mesarić
 *
 */
public class SmartScriptTester {

	public static void main(String[] args) {

		if (args.length == 1) {
			String docBody = loader(args[0]);
			SmartScriptParser parser = null;
			try {
				parser = new SmartScriptParser(docBody);
			} catch (SmartScriptParserException e) {
				System.out.println("Unable to parse document!");
				System.exit(-1);
			} catch (Exception e) {
				System.out.println("If this line ever executes, you have failed this class!");
				System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			String originalDocumentBody = createOriginalDocumentBody(document);
			System.out.println(originalDocumentBody); // should write something like
			// original
			// content of docBodys
		}

		else {
			System.err.println("Wrong number of arguments.");
			System.exit(1);
		}

	}

	private static String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = SmartScriptTester.class.getClassLoader().getResourceAsStream(filename)) {
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

	private static String createOriginalDocumentBody(Node node) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(node);
		for (int i = 0; i < node.numberOfChildren(); i++) {
			stringBuilder.append(createOriginalDocumentBody(node.getChild(i)));
		}
		if (node instanceof ForLoopNode) {
			stringBuilder.append("{$END$}");
		}

		return stringBuilder.toString();

	}

}
