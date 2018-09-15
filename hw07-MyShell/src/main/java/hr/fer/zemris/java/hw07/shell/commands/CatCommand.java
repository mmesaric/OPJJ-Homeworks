package hr.fer.zemris.java.hw07.shell.commands;

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
 * This class represents the implementation of the cat command in MyShell. It
 * takes one or two arguments. The first argument is path to some file and is
 * mandatory. The second argument is charset name which is used to interpret
 * chars from bytes. This command opens given file and writes its content to
 * console.
 * 
 * @author Marko Mesarić
 *
 */
public class CatCommand implements ShellCommand {

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

		String file = "";
		String charset = "";

		if (arguments.startsWith("\"")) {
			file = Util.getPath(arguments);

			for (int i = arguments.lastIndexOf("\"") + 1; i < arguments.length(); i++) {
				charset += arguments.charAt(i);
			}
			charset = charset.trim();

		} else {
			String[] splitArguments = arguments.split("\\s+");

			if (splitArguments.length == 1) {
				file = splitArguments[0];
			} else if (splitArguments.length == 2) {
				file = splitArguments[0];
				charset = splitArguments[1];
			} else {
				env.writeln("Invalid command.");
				return ShellStatus.CONTINUE;
			}
		}
		
		Path path = env.getCurrentDirectory().resolve(Paths.get(file));

		try {
			Util.readFromFile(path.toString(), charset, env);
		} catch (IOException e) {
			env.writeln("Error when trying to read file.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is called with one or two additional arguments. First argument is path to ");
		list.add("some file and is mandatory.  The second argument is charset name that is used to ");
		list.add("interpret chars from bytes. If not provided, a default platform charset is used. ");
		list.add("This command opens given file and writes its content to console.");

		return Collections.unmodifiableList(list);
	}

}
