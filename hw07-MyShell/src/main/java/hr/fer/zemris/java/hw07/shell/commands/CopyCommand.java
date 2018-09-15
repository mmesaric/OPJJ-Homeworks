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
 * This class represents the implementation of the copy command in MyShell. It
 * expects two arguments: source file name and destination file name (i.e. paths
 * and names). If destination file exists, used is asked if it is allowed to
 * overwrite it. If the second argument is directory, is it assumed that user
 * wants to copy the original file into that directory using the original file
 * name.
 * 
 * @author Marko Mesarić
 *
 */
public class CopyCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();

		if (!Util.isValidPath(arguments)) {
			env.writeln("File path was not valid.");
			return ShellStatus.CONTINUE;
		}

		String sourceFilename = "";
		String destinationFilename = "";

		if (arguments.startsWith("\"")) {
			sourceFilename = Util.getPath(arguments);

			char[] argumentsArray = arguments.toCharArray();
			int counter =0;
			for (int i=0; i<argumentsArray.length; i++) {
				if (argumentsArray[i] == '"') counter++; 
			}
			if (counter%2!=0) {
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

//		if (pathSrc.toString().equals(env.getCurrentDirectory().toString())
//				&& pathDst.toString().equals(env.getCurrentDirectory().toString())) {
//			env.writeln("Invalid source/destination to copy.");
//			return ShellStatus.CONTINUE;
//		}

		return copyFile(env, pathSrc.toString(), pathDst.toString());
	}

	/**
	 * Auxiliary method used for determining the copying of files based on rules
	 * which command has to meet. The method is called after both source and
	 * destination paths were determined. This method determines on which terms the
	 * replication of files is done and calls the appropriate methods used for
	 * copying accordingly.
	 * 
	 * @param env
	 *            environment in which shell is running.
	 * @param sourceFilename
	 *            source file name
	 * @param destinationFilename
	 *            destination file name
	 * @return status of the shell which determines if execution is continued or
	 *         terminated.
	 */
	public ShellStatus copyFile(Environment env, String sourceFilename, String destinationFilename) {

		destinationFilename = destinationFilename.trim();

		File destinationFile = new File(destinationFilename);
		File sourceFile = new File(sourceFilename);

		if (sourceFile.isDirectory()) {
			env.writeln("Source must be a file when copying.");
			return ShellStatus.CONTINUE;
		}

		if (destinationFile.isFile() && destinationFile.exists()) {

			env.writeln("Destination file exists. Do you want to overwrite it? YES / NO");
			env.write(env.getPromptSymbol() + " ");
			String input = env.readLine();

			if (input.toUpperCase().equals("NO")) {
				return ShellStatus.CONTINUE;
			} else if (input.toUpperCase().equals("YES")) {
				try {
					Util.copyFile(sourceFilename, destinationFilename);
				} catch (IOException e) {
					env.writeln("Error when trying to copy file.");
					return ShellStatus.CONTINUE;
				}
			} else {
				env.writeln("Wrong input.");
				return ShellStatus.CONTINUE;
			}
		} else if (destinationFile.isDirectory()) {

			if (!destinationFile.exists()) {
				env.writeln("Destination directory doesn't exist.");
				return ShellStatus.CONTINUE;
			}
			destinationFile = new File(destinationFile, sourceFile.getName());
			if (destinationFile.exists()) {
				env.writeln("Destination file exists. Do you want to overwrite it? YES / NO");
				env.write(env.getPromptSymbol() + " ");
				String input = env.readLine();

				if (input.toUpperCase().equals("NO")) {
					return ShellStatus.CONTINUE;
				} else if (input.toUpperCase().equals("YES")) {
					try {
						Util.copyFile(sourceFilename, destinationFile.toString());
					} catch (IOException e) {
						env.writeln("Error when trying to copy file.");
						return ShellStatus.CONTINUE;
					}
				} else {
					env.writeln("Wrong input.");
					return ShellStatus.CONTINUE;
				}
			}
			try {
				Util.copyFile(sourceFilename, new File(destinationFilename, sourceFile.getName()).toString());
			} catch (IOException e) {
				env.writeln("Error when trying to copy file.");
				return ShellStatus.CONTINUE;
			}
		} else {
			try {
				Util.copyFile(sourceFilename, destinationFilename);
			} catch (IOException e) {
				env.writeln("Error when trying to copy file.");
				return ShellStatus.CONTINUE;
			}
		}
		return ShellStatus.CONTINUE;
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
		return "copy";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command expects two arguments: source file name and destination file name (i.e. paths and ");
		list.add("names). Is destination file exists, user is asked is it allowed to overwrite it. Copy command ");
		list.add("works only with files (no directories). If the second argument is directory, command assumes that");
		list.add(" user wants to copy the original file into that directory using the original file name");

		return Collections.unmodifiableList(list);
	}

}
