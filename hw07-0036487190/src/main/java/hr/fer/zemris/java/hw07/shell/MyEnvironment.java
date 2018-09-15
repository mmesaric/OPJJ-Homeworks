package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;

/**
 * This class represents the implementation of a single environment in which
 * MyShell is running. Each environment is characterized by characters (symbols)
 * used for prompting input, symbol notifying multi line command, and symbol
 * which makes multi line comments possible, more lines symbol.
 * 
 * @author Marko Mesarić
 *
 */
public class MyEnvironment implements Environment {

	/**
	 * Collection used for storing registered commands
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * Character which acts as prompt symbol in case of multi line command
	 */
	private Character multiLineSymbol;
	/**
	 * Character used for prompting user input.
	 */
	private Character promptSymbol;
	/**
	 * Character used at the end of user input in case of command stretching through
	 * multiple lines.
	 */
	private Character moreLinesSymbol;

	private Path currentDirectory;

	/**
	 * Reader used for reading given user input from stream
	 */
	private BufferedReader inputStreamReader;
	/**
	 * Writer used for writing shell output to stream.
	 */
	private BufferedWriter outputStreamWriter;

	private Map<String, Object> sharedData;

	/**
	 * Default constructor
	 * 
	 * @param commands
	 *            collection of commands to be registered
	 * @param multiLineSymbol
	 *            multi line symbol
	 * @param promptSymbol
	 *            prompt symbol
	 * @param moreLinesSymbol
	 *            more lines symbol
	 * @param inputStreamReader
	 *            reference to input stream
	 * @param outputStreamWriter
	 *            reference to output stream
	 */
	public MyEnvironment(SortedMap<String, ShellCommand> commands, Character multiLineSymbol, Character promptSymbol,
			Character moreLinesSymbol, BufferedReader inputStreamReader, BufferedWriter outputStreamWriter) {
		this.commands = commands;
		this.multiLineSymbol = multiLineSymbol;
		this.promptSymbol = promptSymbol;
		this.moreLinesSymbol = moreLinesSymbol;
		this.inputStreamReader = inputStreamReader;
		this.outputStreamWriter = outputStreamWriter;
		this.currentDirectory = Paths.get(".").toAbsolutePath().normalize();
		this.sharedData = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			return inputStreamReader.readLine();
		} catch (IOException e) {
			throw new ShellIOException("");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(String text) throws ShellIOException {

		try {
			outputStreamWriter.write(text);
			outputStreamWriter.flush();
		} catch (IOException e) {
			throw new ShellIOException("");
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			outputStreamWriter.write(text + "\n");
			outputStreamWriter.flush();
		} catch (IOException e) {
			throw new ShellIOException("");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiLineSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLinesSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		Objects.requireNonNull(path, "Path can't be null");
		if (path.toFile().isDirectory()) {
			this.currentDirectory = path;
			return;
		}
		throw new IllegalArgumentException("Passed path must be an existing directory.");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSharedData(String key, Object value) {
		Objects.requireNonNull(key, "Key of shared data can't be null.");
		Objects.requireNonNull(value, "Value of shared data can't be null.");

		sharedData.put(key, value);
	}
}
