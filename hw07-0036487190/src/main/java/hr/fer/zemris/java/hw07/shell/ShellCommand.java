package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * Interface which specifies methods which all Shell commands should implement.
 * 
 * @author Marko Mesarić
 *
 */
public interface ShellCommand {

	/**
	 * Method which, when called upon, starts the execution of the passed command in
	 * the given environment and additional command arguments. Method returns the
	 * result depending on if execution was performed successfully or not.
	 * 
	 * @param env
	 *            current shell environment in which command is executed
	 * @param arguments
	 *            additional command arguments
	 * @return result of execution which determines if shell execution is to be
	 *         continued or terminated.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Getter for command name
	 * 
	 * @return name of the particular command
	 */
	String getCommandName();

	/**
	 * Getter for command description.
	 * 
	 * @return command description as a list of strings.
	 */
	List<String> getCommandDescription();

}
