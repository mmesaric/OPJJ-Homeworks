package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of the charsets command in MyShell.
 * It takes no arguments and lists names of supported charsets for current Java
 * platform. A single charset name is written per line.
 * 
 * @author Marko Mesarić
 *
 */
public class CharsetsCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			env.writeln("Command 'Charsets' must have no additional arguments.");
			return ShellStatus.CONTINUE;
		}
		for (String charset : Charset.availableCharsets().keySet()) {
			env.writeln(charset);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is called without additional arguments. ");
		list.add("Lists names of supported charsets for this Java platform.");

		return Collections.unmodifiableList(list);
	}

}
