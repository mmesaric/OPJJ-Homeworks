package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of command 'cptree'. It's task is to
 * copy the entire directory tree to source path. Both directories are passed
 * when executing the command. In case of non existing directory, user is
 * notified of error.
 * 
 * @author Marko Mesarić
 *
 */
public class CptreeCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Util.isValidPath(arguments)) {
			env.writeln("File path was not valid.");
			return ShellStatus.CONTINUE;
		}

		String sourceFilename = "";
		String destinationFilename = "";

		if (arguments.startsWith("\"")) {
			sourceFilename = Util.getPath(arguments);

			char[] argumentsArray = arguments.toCharArray();
			int counter = 0;
			for (int i = 0; i < argumentsArray.length; i++) {
				if (argumentsArray[i] == '"')
					counter++;
			}
			if (counter % 2 != 0) {
				env.writeln("Invalid arguments syntax");
				return ShellStatus.CONTINUE;
			}
			for (int i = arguments.indexOf("\"", arguments.indexOf("\"") + 1) + 1; i < argumentsArray.length; i++) {
				char currentChar = argumentsArray[i];

				if (currentChar == '\"') {
					destinationFilename = appendStringPath(argumentsArray, i);
					break;
				}
				destinationFilename += String.valueOf(currentChar);
			}

		} else {
			String[] splitArguments = arguments.split("\\s+", 2);

			if (splitArguments.length == 1) {
				env.writeln("Invalid command.");
				return ShellStatus.CONTINUE;
			}

			if (splitArguments.length == 2) {
				sourceFilename = splitArguments[0];

				if (splitArguments[1].startsWith("\"")) {
					destinationFilename = Util.getPath(splitArguments[1]);
				} else {
					destinationFilename = splitArguments[1];
				}

			} else {
				env.writeln("Invalid command.");
				return ShellStatus.CONTINUE;
			}
		}
		destinationFilename = destinationFilename.trim();

		Path pathSrc = env.getCurrentDirectory().resolve(Paths.get(sourceFilename));
		Path pathDst = env.getCurrentDirectory().resolve(Paths.get(destinationFilename));

		if (pathSrc.toFile().isFile() || pathDst.toFile().isFile()) {
			env.writeln("Command 'cptree' works with directores not files!");
			return ShellStatus.CONTINUE;
		}

		if (pathDst.toFile().exists() && pathDst.toFile().isDirectory()) {
			pathDst = new File(pathDst.toString(), pathSrc.toFile().getName()).toPath();
			copyExistingFolders(pathSrc, pathDst, env);
		} else if (!pathDst.toFile().exists() && pathDst.getParent() != null && pathDst.getParent().toFile().exists()) {
			pathDst.toFile().mkdir();
			copyExistingFolders(new File(pathSrc.toString(), File.separator).toPath(), pathDst, env);
		} else {
			env.writeln("Command 'cptree' was called with invalid arguments.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary method used for copying the directory tree after parsing necessary
	 * paths.
	 * 
	 * @param pathSrc
	 *            source directory path
	 * @param pathDst
	 *            destination directory path
	 * @param env
	 *            environment in which shell is executed
	 */
	private void copyExistingFolders(Path pathSrc, Path pathDst, Environment env) {

		if (pathSrc.toFile().isDirectory()) {

			if (!pathDst.toFile().exists()) {
				pathDst.toFile().mkdir();
			}

			File[] files = pathSrc.toFile().listFiles();
			if (files == null) {
				env.writeln("Can't access folder: " + pathSrc.toString());
				return;
			}

			for (File file : files) {
				copyExistingFolders(file.toPath(), Paths.get(new File(pathDst.toString(), file.getName()).toString()),
						env);
			}
		} else {
			try {
				Util.copyFile(pathSrc.toString(), pathDst.toString());
			} catch (IOException e) {
				env.writeln("Couldnt copy file: " + pathSrc.toString());
			}
		}
	}

	/**
	 * Auxiliary method used for appending the rest of the string representation of
	 * the path.
	 * 
	 * @param arguments
	 *            command arguments as char array
	 * @param i
	 *            starting index of second path
	 * @return path as String.
	 */
	private String appendStringPath(char[] arguments, int i) {
		String destinationName = "";
		for (int j = i + 1; j < arguments.length; j++) {
			if (arguments[j] == '\"') {
				break;
			}
			destinationName += arguments[j];
		}
		return destinationName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cptree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is called with two additional arguments. Source directory path");
		list.add("and destination directory path. Copies whole directory tree structure from source");
		list.add("to destination folder.");

		return Collections.unmodifiableList(list);

	}

}
