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
 * This class represents the implementation of 'dropd' command which pops the
 * last pushed path from stack and discards it without modifying current working
 * directory. In case of popping from empty stack, user is notified of error.
 * 
 * @author Marko Mesarić
 *
 */
public class DropdCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!arguments.trim().isEmpty()) {
			env.writeln("Command 'dropd' is called with no additional arguments.");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(Util.STACK_KEY);

		if (stack == null || stack.isEmpty()) {
			env.writeln("There are no paths pushed on stack.");
			return ShellStatus.CONTINUE;
		}
		stack.pop();

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "dropd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is called without additional arguments. When called ");
		list.add("pops the last directory from stack without modifying current working directory.");

		return Collections.unmodifiableList(list);

	}

}
