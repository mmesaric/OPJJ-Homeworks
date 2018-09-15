package hr.fer.zemris.java.custom.scripting.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * This class represents the implementation of a Tree writer used for traversing
 * tree of parsed nodes and printing them using visitor design pattern.
 * 
 * @author Marko Mesarić
 *
 */
public class TreeWriter {

	/**
	 * This class represents the implementation of the Visitor object in Visitor
	 * design pattern. It implements the behavior when visiting all types of nodes
	 * in parse tree.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {

			ElementVariable variable = node.getVariable();
			Element startExpression = node.getStartExpression();
			Element endExpression = node.getEndExpression();
			Element stepExpression = node.getStepExpression();

			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("{$ FOR ").append(variable.asText()).append(" ").append(startExpression.asText())
					.append(" ").append(endExpression.asText());
			stringBuilder.append(stepExpression == null ? "" : " " + stepExpression.asText()).append(" ");
			stringBuilder.append("$}");

			System.out.print(stringBuilder.toString());

			ArrayIndexedCollection collection = node.getArrayIndexedCollection();

			for (int i = 0; i < collection.size(); i++) {
				Node childNode = (Node) collection.get(i);
				childNode.accept(this);
			}

			System.out.print("{$END$}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {

			Element[] elements = node.getElements();

			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("{$= ");

			for (int i = 0; i < elements.length; i++) {
				if (elements[i] instanceof ElementString) {
					stringBuilder.append("\"").append(elements[i].asText()).append("\" ");

				} else if (elements[i] instanceof ElementFunction) {
					stringBuilder.append("@").append(elements[i].asText());

				} else {
					stringBuilder.append(elements[i].asText()).append(" ");
				}
			}

			stringBuilder.append("$}");

			System.out.print(stringBuilder.toString());

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {

			ArrayIndexedCollection collection = node.getArrayIndexedCollection();

			for (int i = 0; i < collection.size(); i++) {
				Node childNode = (Node) collection.get(i);
				childNode.accept(this);
			}
		}

	}

	/**
	 * Method which starts the execution of the program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected 1 argument. File name");
			System.exit(1);
		}

		String docBody = loader(args[0]);
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);

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
