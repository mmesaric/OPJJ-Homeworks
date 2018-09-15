package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of 'listd' command which ouputs all
 * paths currently stored in stack. In case of popping from empty stack, user is
 * notified of error.
 * 
 * @author Marko Mesarić
 *
 */
public class ListdCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!arguments.trim().isEmpty()) {
			env.writeln("Command 'listd' is called with no additional arguments.");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(Util.STACK_KEY);

		if (stack == null || stack.isEmpty()) {
			env.writeln("There are no paths on stack.");
			return ShellStatus.CONTINUE;
		}

		List<Object> stackAsList = Arrays.asList(stack.toArray());
		Collections.reverse(stackAsList);

		for (Object object : stackAsList) {
			String path = String.valueOf(object);

			env.writeln(path);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "listd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command when called, outputs all paths currently stored in stack");
		list.add("starting from the one pushed last.");

		return Collections.unmodifiableList(list);
	}

}
