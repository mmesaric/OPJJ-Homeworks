package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.name.NameBuilder;
import hr.fer.zemris.java.hw07.shell.commands.name.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.commands.name.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.commands.name.ParserException;

/**
 * This class represents the implementation of 'massrename'. This command is
 * used for renaming/moving files from one folder to another based on regular
 * expression and naming properties which are passed as additional arguments
 * when command is called. This command is called with additional subcommand
 * name as argument. Known subcommands are 'filter' (lists file names of all
 * files that match the given regular expression), 'groups' (outputs the name
 * each file with all groups which match given expression), 'show' (outputs the
 * file name and the name of file after renaming) and finally, subcommand
 * 'execute' which renames and moves the files from given source folder.
 * 
 * @author Marko Mesarić
 *
 */
public class MassRenameCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		String sourceFilename = "";
		String destinationFilename = "";

		String[] splitArguments = arguments.split("\\s+", 3);

		if (!Util.isValidPath(splitArguments[0]) || !Util.isValidPath(splitArguments[0])) {
			env.writeln("Both paths passed as arguments to massrename must be valid!");
			return ShellStatus.CONTINUE;
		}
		sourceFilename = splitArguments[0];
		destinationFilename = splitArguments[1];

		Path pathSrc = env.getCurrentDirectory().resolve(Paths.get(sourceFilename));
		Path pathDst = env.getCurrentDirectory().resolve(Paths.get(destinationFilename));

		if (pathSrc.toFile().isFile() || pathDst.toFile().isFile()) {
			env.writeln("Both paths passed as arguments must be directories.");
			return ShellStatus.CONTINUE;
		}

		String restOfArgument = splitArguments[2];

		String commandName = restOfArgument.split("\\s+")[0];

		if (commandName.equals("filter")) {
			whenSubcommandFilter(env, restOfArgument, pathSrc);
		} else if (commandName.equals("groups")) {
			whenSubcommandGroups(env, restOfArgument, pathSrc);
		} else if (commandName.equals("show")) {
			whenSubcommandShow(env, restOfArgument, pathSrc);
		} else if (commandName.equals("execute")) {
			whenSubcommandExecute(env, restOfArgument, pathSrc, pathDst);
		} else {
			env.writeln("Unknown subcommand.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary method used for implementation of behavior in case when
	 * 'massrename' command is called with 'execute' subcommand. Used for performing
	 * the renaming and moving the files.
	 * 
	 * @param env
	 *            environment in which shell is running.
	 * @param restOfArgument
	 *            arguments to be parsed
	 * @param pathSrc
	 *            source folder path
	 * @param pathDst
	 *            destination folder path
	 */
	private void whenSubcommandExecute(Environment env, String restOfArgument, Path pathSrc, Path pathDst) {

		String[] showArguments = restOfArgument.split("\\s+", 2);

		if (showArguments.length != 2) {
			env.writeln("Wrong number of arguments in subcommand 'show'.");
			return;
		}

		String expressionAndGrouping = showArguments[1];
		String regExpression = "";
		String grouping = "";

		if (expressionAndGrouping.startsWith("\"")) {

			regExpression = Util.getQuotationsArgument(expressionAndGrouping);

			char[] argumentsArray = expressionAndGrouping.toCharArray();
			int counter = 0;
			for (int i = 0; i < argumentsArray.length; i++) {
				if (argumentsArray[i] == '"')
					counter++;
			}
			if (counter % 2 != 0) {
				env.writeln("Invalid arguments syntax");
				return;
			}
			for (int i = expressionAndGrouping.indexOf("\"", expressionAndGrouping.indexOf("\"") + 1)
					+ 1; i < argumentsArray.length; i++) {
				char currentChar = argumentsArray[i];

				if (currentChar == '\"') {
					grouping = appendStringPath(argumentsArray, i);
					break;
				}
				grouping += String.valueOf(currentChar);
			}

		} else {
			String[] expressions = expressionAndGrouping.split("\\s+", 2);

			regExpression = expressions[0];
			grouping = expressions[1];

			if (grouping.startsWith("\"") && grouping.endsWith("\"")) {
				grouping = grouping.substring(1, grouping.length() - 1);
			}
		}

		if (regExpression.isEmpty() || grouping.isEmpty()) {
			env.writeln("Invalid arguments to show subcommand");
			return;
		}
		regExpression = regExpression.trim();
		grouping = grouping.trim();
		NameBuilderParser parser = null;
		try {
			parser = new NameBuilderParser(grouping);
		} catch (ParserException exc) {
			env.writeln("Syntax error when parsing.");
			return;
		}
		Pattern pattern = Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		NameBuilder builder = parser.getNameBuilder();
		for (File file : pathSrc.toFile().listFiles()) {
			Matcher matcher = pattern.matcher(file.getName());

			if (!matcher.find()) {
				continue;
			}
			NameBuilderInfo info = new NameInfo(matcher);
			builder.execute(info);
			String newName = info.getStringBuilder().toString();
			env.writeln(pathSrc.toFile().getName()+File.separator+file.getName() + " => " +pathDst.toFile().getName()+File.separator+newName);
			try {
				Files.move(file.toPath(), new File(pathDst.toString(), newName).toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				env.writeln("Error when trying to move file.");
				return;
			}
			
		}

	}

	/**
	 * Auxiliary method used for implementation of behavior in case when
	 * 'massrename' command is called with 'show' subcommand. Used for output of
	 * selected file names and newly generated names based on given expression.
	 * 
	 * @param env
	 *            environment in which shell is running.
	 * @param restOfArgument
	 *            arguments to be parsed
	 * @param pathSrc
	 *            source folder path
	 */
	private void whenSubcommandShow(Environment env, String restOfArgument, Path pathSrc) {

		String[] showArguments = restOfArgument.split("\\s+", 2);

		if (showArguments.length != 2) {
			env.writeln("Wrong number of arguments in subcommand 'show'.");
			return;
		}

		String expressionAndGrouping = showArguments[1];
		String regExpression = "";
		String grouping = "";

		if (expressionAndGrouping.startsWith("\"")) {

			regExpression = Util.getQuotationsArgument(expressionAndGrouping);

			char[] argumentsArray = expressionAndGrouping.toCharArray();
			int counter = 0;
			for (int i = 0; i < argumentsArray.length; i++) {
				if (argumentsArray[i] == '"')
					counter++;
			}
			if (counter % 2 != 0) {
				env.writeln("Invalid arguments syntax");
				return;
			}
			for (int i = expressionAndGrouping.indexOf("\"", expressionAndGrouping.indexOf("\"") + 1)
					+ 1; i < argumentsArray.length; i++) {
				char currentChar = argumentsArray[i];

				if (currentChar == '\"') {
					grouping = appendStringPath(argumentsArray, i);
					break;
				}
				grouping += String.valueOf(currentChar);
			}

		} else {
			String[] expressions = expressionAndGrouping.split("\\s+", 2);

			regExpression = expressions[0];
			grouping = expressions[1];

			if (grouping.startsWith("\"") && grouping.endsWith("\"")) {
				grouping = grouping.substring(1, grouping.length() - 1);
			}
		}
		regExpression = regExpression.trim();
		grouping = grouping.trim();
		if (regExpression.isEmpty() || grouping.isEmpty()) {
			env.writeln("Invalid arguments to show subcommand");
			return;
		}
		NameBuilderParser parser = null;
		try {
			parser = new NameBuilderParser(grouping);
		} catch (ParserException exc) {
			env.writeln("Syntax error when parsing.");
			return;
		}
		Pattern pattern = Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		NameBuilder builder = parser.getNameBuilder();
		for (File file : pathSrc.toFile().listFiles()) {
			Matcher matcher = pattern.matcher(file.getName());

			if (!matcher.find()) {
				continue;
			}
			NameBuilderInfo info = new NameInfo(matcher);
			builder.execute(info);
			String newName = info.getStringBuilder().toString();
			env.writeln(file.getName() + " => " + newName);
		}

	}

	/**
	 * Auxiliary method used for implementation of behavior in case when
	 * 'massrename' command is called with 'groups' subcommand. Used for output of
	 * all groups of selected filenames based on passed regular expression.
	 * 
	 * @param env
	 *            environment in which shell is running.
	 * @param restOfArgument
	 *            arguments to be parsed
	 * @param pathSrc
	 *            source folder path
	 */
	private void whenSubcommandGroups(Environment env, String restOfArgument, Path pathSrc) {

		String mask = restOfArgument.split("\\s+", 2)[1];

		if (mask.startsWith("\"") && mask.endsWith("\"")) {
			mask = mask.substring(1, mask.length() - 1);
		}

		Pattern pattern = Pattern.compile(mask, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		for (File file : pathSrc.toFile().listFiles()) {
			Matcher matcher = pattern.matcher(file.getName());

			if (!matcher.find()) {
				continue;
			}
			NameBuilderInfo info = new NameInfo(matcher);
			env.write(file.getName() + " ");
			for (int i = 0; i <= matcher.groupCount(); i++) {
				env.write(i + ": " + info.getGroup(i) + " ");
			}
			env.writeln("");
		}
	}

	/**
	 * Auxiliary method used for implementation of behavior in case when
	 * 'massrename' command is called with 'filter' subcommand. Used for output of
	 * file names which match passed regular expression.
	 * 
	 * @param env
	 *            environment in which shell is running.
	 * @param restOfArgument
	 *            arguments to be parsed
	 * @param pathSrc
	 *            source folder path
	 */
	private void whenSubcommandFilter(Environment env, String restOfArgument, Path pathSrc) {
		String mask = restOfArgument.split("\\s+", 2)[1];

		if (mask.startsWith("\"") && mask.endsWith("\"")) {
			mask = mask.substring(1, mask.length() - 1);
		}

		Pattern pattern = Pattern.compile(mask, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		for (File file : pathSrc.toFile().listFiles()) {
			Matcher matcher = pattern.matcher(file.getName());

			if (!matcher.find()) {
				continue;
			}

			env.writeln(file.getName());
		}
	}

	/**
	 * Auxiliary method used for appending the rest of the string representation of
	 * the path.
	 * 
	 * @param arguments
	 *            command arguments as char array
	 * @param i
	 *            starting index of second path
	 * @return path as String.
	 */
	private String appendStringPath(char[] arguments, int i) {
		String destinationName = "";
		for (int j = i + 1; j < arguments.length; j++) {
			if (arguments[j] == '\"') {
				break;
			}
			destinationName += arguments[j];
		}
		return destinationName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "massrename";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is used for renaming/moving files from one folder to another based on regular ");
		list.add("expression and naming properties which are passed as additional arguments");
		list.add(" when command is called. This command is called with additional subcommand");
		list.add("name as argument. Known subcommands are 'filter' (lists file names of all");
		list.add("files that match the given regular expression), 'groups' (outputs the name");
		list.add("each file with all groups which match given expression), 'show' (outputs the");
		list.add("file name and the name of file after renaming) and finally, subcommand");
		list.add("'execute' which renames and moves the files from given source folder.");

		return Collections.unmodifiableList(list);
	}

	/**
	 * This class represents the implementation of NameBuilderInfo class which is
	 * used for generating new name with which original name is replaced
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private class NameInfo implements NameBuilderInfo {

		private StringBuilder name;
		private Matcher matcher;

		public NameInfo(Matcher matcher) {
			name = new StringBuilder();
			this.matcher = matcher;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public StringBuilder getStringBuilder() {
			return name;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getGroup(int index) {
			return matcher.group(index);
		}
	}
}
