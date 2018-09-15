package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of 'pushd' command. It pushes the
 * current working directory to stack and switches it with passed value. If
 * passed path doesn't represent an existing directory, user is notified of
 * error, and no modifications are done.
 * 
 * @author Marko Mesarić
 *
 */

public class PushdCommand implements ShellCommand {

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
		File newWorkingDirectory = env.getCurrentDirectory().resolve(dirName).toFile();

		if (!newWorkingDirectory.exists() || !newWorkingDirectory.isDirectory()) {
			env.writeln("Argument must be an existing directory.");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(Util.STACK_KEY);

		if (stack == null) {
			stack = new Stack<>();
		}

		stack.push(env.getCurrentDirectory());

		env.setSharedData(Util.STACK_KEY, stack);
		env.setCurrentDirectory(newWorkingDirectory.toPath());

		return ShellStatus.CONTINUE;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pushd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command pushes the current working directory to stack and switches it ");
		list.add("with passed value. If passed path doesn't represent an existing directory, ");
		list.add("user is notified of error, and no modifications are done.");

		return Collections.unmodifiableList(list);
	}

}
