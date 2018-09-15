package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * * This class represents the implementation of the mkdir command in MyShell.
 * It takes a single argument: directory name, and creates the appropriate
 * directory structure.
 * 
 * @author Marko Mesarić
 *
 */
public class MkdirCommand implements ShellCommand {

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

		String dirName = "";

		if (arguments.startsWith("\"")) {
			dirName = Util.getPath(arguments);

		} else {
			String[] splitArguments = arguments.split("\\s+");

			if (splitArguments.length != 1) {
				env.writeln("File path was not valid.");
				return ShellStatus.CONTINUE;
			}
			dirName = arguments.trim();
		}
		env.getCurrentDirectory().resolve(dirName).toFile().mkdirs();

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command takes a single argument: directory name, and ");
		list.add("creates the appropriate directory structure");

		return Collections.unmodifiableList(list);
	}

}
