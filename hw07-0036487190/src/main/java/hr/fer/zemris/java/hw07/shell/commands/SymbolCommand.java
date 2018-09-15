package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of the symbol command in MyShell. It
 * is used for outputting current characters representing the demanded symbol
 * for Prompt/morelines/multiline in case if called with 1 argument. If called
 * with 2 arguments, first Prompt/morelines/multiline and the second some other
 * character, it sets the environment's symbols accordingly.
 * 
 * @author Marko Mesarić
 *
 */
public class SymbolCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] splitArguments = arguments.trim().split("\\s+");

		if (splitArguments.length == 1) {
			String firstArgument = splitArguments[0];

			switch (firstArgument) {
			case "PROMPT":
				env.writeln("Symbol for prompt is: '" + env.getPromptSymbol() + "'");
				break;
			case "MORELINES":
				env.writeln("Symbol for more lines is: '" + env.getMorelinesSymbol() + "'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for multi line is: '" + env.getMultilineSymbol() + "'");
				break;
			default:
				env.writeln("Wrong type of argument for command symbol.");
			}
		} else if (splitArguments.length == 2) {

			String firstArgument = splitArguments[0];
			String secondArgument = splitArguments[1];
			if (secondArgument.length() != 1) {
				env.writeln("Second argument must be a character");
			}

			switch (firstArgument) {
			case "PROMPT":
				env.writeln(
						"Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + secondArgument + "'");
				env.setPromptSymbol(secondArgument.charAt(0));
				break;
			case "MORELINES":
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + secondArgument
						+ "'");
				env.setMorelinesSymbol(secondArgument.charAt(0));
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + secondArgument
						+ "'");
				env.setMultilineSymbol(secondArgument.charAt(0));
				break;
			default:
				env.writeln("Wrong type of argument for command symbol.");
			}

		} else {
			env.writeln("Command symbol can have only 1 or 2 arguments.");
		}

		return ShellStatus.CONTINUE;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command prints current prompt symbol.");

		return Collections.unmodifiableList(list);

	}

}
