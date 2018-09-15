package hr.fer.zemris.java.hw07.shell.commands;

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
 * This class represents the implementation of 'popd' command which pops the
 * last pushed path from stack and sets it as current working directory of the
 * shell. In case of popping from empty stack, user is notified of error.
 * 
 * @author Marko Mesarić
 *
 */
public class PopdCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!arguments.trim().isEmpty()) {
			env.writeln("Command 'popd' is called with no additional arguments.");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(Util.STACK_KEY);

		if (stack == null || stack.isEmpty()) {
			env.writeln("There are no paths pushed on stack.");
			return ShellStatus.CONTINUE;
		}
		Path topPath = stack.pop();

		if (topPath.toFile().exists()) {
			env.setCurrentDirectory(topPath);
		} else {
			env.writeln("Directory doesn't exist anymore.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "popd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is called without additional arguments. When called ");
		list.add("pops the last directory from stack and sets it as current working directory.");

		return Collections.unmodifiableList(list);
	}

}
