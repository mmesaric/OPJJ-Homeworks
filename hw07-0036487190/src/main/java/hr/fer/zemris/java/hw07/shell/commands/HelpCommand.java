package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of the help command in MyShell. If
 * started with no arguments it lists the names of all shell-supported commands.
 * If started with a single argument prints the name and the description of
 * selected command.
 * 
 * @author Marko Mesarić
 *
 */
public class HelpCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			for (String commandName : env.commands().keySet()) {
				env.writeln(commandName);
			}
			return ShellStatus.CONTINUE;
		}

		ShellCommand command = env.commands().get(arguments.trim());

		if (command == null) {
			env.writeln("Command with the given name doesn't exist.");
			return ShellStatus.CONTINUE;
		}

		env.writeln("Command name: " + command.getCommandName());

		for (String line : command.getCommandDescription()) {
			env.writeln(line);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("If started with no arguments, this command lists names of all supported ");
		list.add("commands. If started with single argument, prints name and ");
		list.add("the description of selected command");

		return Collections.unmodifiableList(list);

	}

}
