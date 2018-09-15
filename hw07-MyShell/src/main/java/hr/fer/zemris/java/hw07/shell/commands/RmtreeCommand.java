package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
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
 * This class represents the implementation of 'rmtree' command. When called,
 * removes the given folder and all files and folders located inside the given
 * directory.
 * 
 * @author Marko Mesarić
 *
 */
public class RmtreeCommand implements ShellCommand {

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

		if (!dirPath.toFile().exists() || !dirPath.toFile().isDirectory()) {
			env.writeln("Given path must be an existing directory.");
			return ShellStatus.CONTINUE;
		}

		if (dirPath.toString().equals(env.getCurrentDirectory().toString())) {
			env.writeln(
					"Are you sure that you want to delete the current directory? Type anything to continue with removing, 'NO' otherwise.");
			env.write("> ");
			String input = env.readLine();

			if (input.toUpperCase().equals("NO")) {
				return ShellStatus.CONTINUE;
			}
		}

		try {
			Files.walk(dirPath).map((path) -> path.toFile()).sorted((file1, file2) -> file2.compareTo(file1))
					.forEach((file) -> file.delete());
		} catch (IOException e) {
			env.writeln("Error when deleting files.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "rmtree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command removes the directory tree which path is passed as argument.");

		return Collections.unmodifiableList(list);
	}

}
