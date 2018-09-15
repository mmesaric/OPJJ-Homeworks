package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This class represents the implementation of the help command in MyShell. It
 * takes a single argument, path to directory and writes a directory listing
 * (non recursive). Output consists of 4 columns. First column indicates if
 * current object is directory (d), readable (r), writable (w) and executable
 * (x). Second column contains object size in bytes that is right aligned and
 * occupies 10 characters (0 if syze is too big to be stored in 10 characters).
 * Follows file creation date/time and finally file name.
 * 
 * @author Marko Mesarić
 *
 */
public class LsCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Util.isValidPath(arguments)) {
			env.writeln("File path was not valid.");
			return ShellStatus.CONTINUE;
		}
		String filePath = "";

		if (arguments.startsWith("\"")) {
			filePath = Util.getPath(arguments);
		} else {
			String[] splitArguments = arguments.trim().split("\\s+");
			if (splitArguments.length != 1) {
				env.writeln("File path was not valid.");
				return ShellStatus.CONTINUE;
			}
			filePath = arguments.trim();
		}

		File file = env.getCurrentDirectory().resolve(Paths.get(filePath)).toFile();
		try {
			listFiles(file, env);
		} catch (NullPointerException exc) {
			env.writeln("Directory doesn't exist or you don't have rights to access it.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary method used for retrieving the list of files contained in passed
	 * directory and formatting the output string as a result of the command after
	 * calling appropriate auxiliary methods.
	 * 
	 * @param file
	 *            reference to directory file
	 * @param env
	 *            environment in which shell is running.
	 */
	private void listFiles(File file, Environment env) {

		File[] files = file.listFiles();
		if (files == null) {
			throw new NullPointerException();
		}

		for (File singleFile : files) {
			StringBuilder stringBuilder = new StringBuilder();

			checkFirstColumn(stringBuilder, singleFile);
			stringBuilder.append(" ");
			stringBuilder.append(String.format("%10s", singleFile.length()));
			stringBuilder.append(" ");
			try {
				formatDateTime(stringBuilder, singleFile);
			} catch (IOException e) {
				throw new NullPointerException();
			}
			stringBuilder.append(" ");
			stringBuilder.append(singleFile.getName());

			env.writeln(stringBuilder.toString());
		}
	}

	/**
	 * Auxiliary method used for determining and formatting the time and date of the
	 * current file as shown in homework 7 guidelines by Marko čupić.
	 * 
	 * @param stringBuilder
	 *            reference to stringbuilder object used for storing output string
	 * @param singleFile
	 *            file of which properties are checked.
	 * @throws IOException
	 *             in case of exception when accessing file.
	 */
	private void formatDateTime(StringBuilder stringBuilder, File singleFile) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = singleFile.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		stringBuilder.append(formattedDateTime);

	}

	/**
	 * Auxiliary method used for appending the first column of the ls command output
	 * passed on current file properties.
	 * 
	 * @param stringBuilder
	 *            reference to stringbuilder object used for storing output string
	 * @param singleFile
	 *            file of which properties are checked.
	 */
	private void checkFirstColumn(StringBuilder stringBuilder, File singleFile) {
		if (singleFile.isDirectory()) {
			stringBuilder.append("d");
		} else {
			stringBuilder.append("-");
		}

		if (singleFile.canRead()) {
			stringBuilder.append("r");
		} else {
			stringBuilder.append("-");
		}

		if (singleFile.canWrite()) {
			stringBuilder.append("w");
		} else {
			stringBuilder.append("-");
		}

		if (singleFile.canExecute()) {
			stringBuilder.append("x");
		} else {
			stringBuilder.append("-");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command takes a single argument – directory – and writes a directory ");
		list.add("listing (non recursive). The output consists of 4 columns. First column indicates");
		list.add(" if current object is directory (d), readable (r), writable (w) and executable (x). ");
		list.add("Second column contains object size in bytes that is right aligned and ");
		list.add("occupies 10 characters. Follows file creation date/time and finally file name");

		return Collections.unmodifiableList(list);
	}

}
