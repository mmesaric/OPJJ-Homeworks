package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of 'pwd' command. When called,
 * outputs the current working directory of the shell environment.
 * 
 * @author Marko Mesarić
 *
 */
public class PwdCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!arguments.trim().isEmpty()) {
			env.writeln("Command 'pwd' is called with no additional arguments.");
			return ShellStatus.CONTINUE;
		}

		env.writeln("Current working directory is: " + env.getCurrentDirectory());

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pwd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command pushes outputs the current working directory of shell.");

		return Collections.unmodifiableList(list);
	}

}
