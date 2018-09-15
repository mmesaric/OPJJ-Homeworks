package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * This interface offers methods for defining a behavior of a single environment
 * in which MyShell is run by passing this abstraction to each defined command.
 * Each command communicates with user only through this interface. Commands
 * once registered cannot be altered after initializing environment.
 * 
 * @author Marko Mesarić
 *
 */
public interface Environment {

	/**
	 * This method is used for reading a single line of user input.
	 * 
	 * @return read Line
	 * @throws ShellIOException
	 *             in case of Input Stream error.
	 */
	String readLine() throws ShellIOException;

	/**
	 * This method is used for writing given text to Output Stream.
	 * 
	 * @param text
	 *            text to be written to output.
	 * @throws ShellIOException
	 *             in case of Output Stream error.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * This method is used for writing given text to Output Stream and adds the new
	 * line character after given text.
	 * 
	 * @param text
	 *            text to be written to output.
	 * @throws ShellIOException
	 *             in case of Output Stream error.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Getter for map of commands.
	 * 
	 * @return collection of stored commands for a given environment
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Getter for multiline symbol
	 * 
	 * @return character representing the multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Setter for multiline symbol.
	 * 
	 * @param symbol
	 *            symbol to be set
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Getter for prompt symbol
	 * 
	 * @return character representing the prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Setter for prompt symbol
	 * 
	 * @param symbol
	 *            symbol to be set
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Getter for morelines symbol
	 * 
	 * @return character representing the morelines symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter for more lines symbol
	 * 
	 * @param symbol
	 *            value for more lines symbol
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Getter for current directory path
	 * 
	 * @return current directory path
	 */
	Path getCurrentDirectory();

	/**
	 * Setter for current directory path
	 * 
	 * @param path
	 *            path to be set
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Getter for shared data object
	 * 
	 * @param key
	 *            key under which shared data object is stored
	 * @return value for given key
	 */
	Object getSharedData(String key);

	/**
	 * Setter for shared data object
	 * 
	 * @param key
	 *            under which object is stored
	 * @param value
	 *            value to be stored
	 */
	void setSharedData(String key, Object value);

}
