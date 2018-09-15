package hr.fer.zemris.java.hw07.shell.commands;

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
 * This class represents the implementation of 'cd' command. When called,
 * switches the current working directory of shell to the given new value.
 * 
 * @author Marko Mesarić
 *
 */
public class CdCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();

		if (!Util.isValidPath(arguments) || arguments.isEmpty()) {
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
		Path dirPath = env.getCurrentDirectory().resolve(Paths.get(dirName));

		if (!dirPath.toFile().isDirectory() || !dirPath.toFile().exists()) {
			env.writeln("Given path must be an existing directory.");
			return ShellStatus.CONTINUE;
		}

		env.setCurrentDirectory(dirPath.normalize());

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command takes a single argument: directory name, which should be");
		list.add("new working directory of shell.");

		return Collections.unmodifiableList(list);
	}

}
