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
 * This class represents the implementation of the hexdump command. This command
 * expects a single argument: file name, and produces hex-output. For bytes
 * who's value is less that 32 or greater than 127 character '.' is printed
 * instead.
 * 
 * @author Marko Mesarić
 *
 */
public class HexdumpCommand implements ShellCommand {

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

		File file = new File(filePath);
		if (!file.isFile()) {
			env.writeln("Given path must be a file.");
			return ShellStatus.CONTINUE;
		}
		try {
			Path path = env.getCurrentDirectory().resolve(Paths.get(filePath));
			env.writeln(Util.readFileForHexDump(path.toString()));
		} catch (IOException e) {
			env.writeln("Error when outputting hexdump.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command expects a single argument: file name, and produces hex-output.");

		return Collections.unmodifiableList(list);
	}

}
