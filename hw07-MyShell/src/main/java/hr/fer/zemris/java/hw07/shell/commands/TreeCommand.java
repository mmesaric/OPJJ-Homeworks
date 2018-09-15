package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of the tree command in MyShell. It
 * expects a single argument: directory name and prints a tree (each directory
 * level shifts output two characters to the right)
 * 
 * @author Marko Mesarić
 *
 */
public class TreeCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!Util.isValidPath(arguments)) {
			env.writeln("File path was not valid.");
			return ShellStatus.CONTINUE;
		}
		String filePath = "";

		if (arguments.startsWith("\"")) {
			filePath = Util.getPath(arguments);
		} else {
			String[] splitArguments = arguments.trim().split("\\s+");
			if (splitArguments.length != 1) {
				env.writeln("File path was not valid.");
				return ShellStatus.CONTINUE;
			}
			filePath = arguments.trim();
		}

		Path path = env.getCurrentDirectory().resolve(Paths.get(filePath));

		try {
			Files.walkFileTree(path, new TreeOuput(env));
		} catch (IOException e1) {
			env.writeln("Error when trying to read file.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("this command expects a single argument: directory name and prints a tree ");
		list.add("(each directory level shifts output two characters to the right)");

		return Collections.unmodifiableList(list);
	}

	/**
	 * Private static class used for traversing given file tree by implementing file
	 * visitor and overriding it's methods.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private static class TreeOuput implements FileVisitor<Path> {

		/**
		 * Variable used for storing running instance of environment
		 */
		private Environment env;
		/**
		 * Variable used for storing current number of spaces used as indentation
		 */
		private int indent;

		/**
		 * Default constructor
		 * 
		 * @param env
		 *            currently running environment
		 */
		public TreeOuput(Environment env) {
			this.env = env;
			indent = 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path path, IOException attributes) throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attributes) throws IOException {
			for (int i = 0; i < indent; i++) {
				env.write(" ");
			}
			env.writeln(path.getFileName().toString());
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
			for (int i = 0; i < indent; i++) {
				env.write(" ");
			}
			env.writeln(path.getFileName().toString());
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult visitFileFailed(Path path, IOException exception) throws IOException {
			return FileVisitResult.TERMINATE;
		}
	}
}
