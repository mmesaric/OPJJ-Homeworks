package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropdCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListdCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassRenameCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopdCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeCommand;

/**
 * This class represents a simple Shell program. When started, program greets
 * the user and prompts input command by outputting prompt symbol. The command
 * can span across multiple lines. However, each line that is not the last line
 * of command must end with a special symbol that is used to inform the shell
 * that more lines as expected. For each line that is a part of multi-line
 * command (except for the first one), shell writes current multiline symbol at
 * the begging followed by a whitespace. To get a list of supported commands,
 * call command 'help'.
 * 
 * @author Marko Mesarić
 *
 */
public class MyShell {

	/**
	 * Variable used for setting up environment input stream
	 */
	public static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
	/**
	 * Variable used for setting up environment output stream
	 */
	public static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

	/**
	 * Main method used for starting the execution of the shell. Notifies the user
	 * that shell program started and that it's waiting for command input. In case
	 * of input or output stream being unavailable, shell execution is terminated.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		MyEnvironment environment = createEnvironment();

		ShellStatus status = ShellStatus.CONTINUE;
		try {
			environment.writeln("Welcome to MyShell v1.0");
		} catch (ShellIOException exc) {
			System.err.println("Can't write to shell ouput.");
			status = ShellStatus.TERMINATE;
		}

		do {
			environment.write(environment.getPromptSymbol() + " ");
			String line = environment.readLine();

			if (line.endsWith(environment.getMorelinesSymbol().toString())) {
				line = line.substring(0, line.lastIndexOf(environment.getMorelinesSymbol())).trim();
				String nextLine = "";
				do {
					environment.write(environment.getMultilineSymbol() + " ");
					nextLine = environment.readLine();
					if (nextLine.endsWith(environment.getMorelinesSymbol().toString())) {
						line += " "
								+ nextLine.substring(0, nextLine.lastIndexOf(environment.getMorelinesSymbol())).trim();
					} else {
						line += " " + nextLine;
					}
				} while (nextLine.endsWith(environment.getMorelinesSymbol().toString()));

			}

			String[] splitLine = line.split("\\s+", 2);

			String commandName = splitLine[0];
			String arguments = "";
			if (splitLine.length == 2) {
				arguments = splitLine[1];
			}

			ShellCommand command = environment.commands().get(commandName);
			try {
				status = command.executeCommand(environment, arguments);
			} catch (NullPointerException exc) {
				environment.writeln(
						"Invalid command or commmand format. To get a list of supported commands, call command 'help'.");
			} catch (ShellIOException exc) {
				System.err.println("Failed to read/write from input/output stream.");
				status = ShellStatus.TERMINATE;
			}
		} while (status != ShellStatus.TERMINATE);

		try {
			READER.close();
			WRITER.close();
		} catch (IOException e) {
			// ignorable
		}

	}

	/**
	 * Auxiliary method which sets up the initial configuration of the environment
	 * and initializes it.
	 * 
	 * @return newly created environment
	 */
	private static MyEnvironment createEnvironment() {

		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		commands.put("exit", new ExitCommand());
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("tree", new TreeCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("help", new HelpCommand());
		commands.put("ls", new LsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("pwd", new PwdCommand());
		commands.put("cd", new CdCommand());
		commands.put("dropd", new DropdCommand());
		commands.put("rmtree", new RmtreeCommand());
		commands.put("popd", new PopdCommand());
		commands.put("pushd", new PushdCommand());
		commands.put("listd", new ListdCommand());
		commands.put("cptree", new CptreeCommand());
		commands.put("massrename", new MassRenameCommand());

		Character promptSymbol = '>';
		Character multilineSymbol = '|';
		Character morelinesSymbol = '\\';

		return new MyEnvironment(commands, multilineSymbol, promptSymbol, morelinesSymbol, READER, WRITER);
	}

}
