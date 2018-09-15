package hr.fer.zemris.java.hw05.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.ParserException;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Class used for demonstration of a simple query language used for getting
 * Student Records which satisfy given Conditional expressions
 * 
 * @author Marko Mesarić
 *
 */
public class Demo {

	/**
	 * Main class used for reading input from "database" (database.txt file in
	 * src/main/resources) and creating a simple Database based on StudentDatabase
	 * class. User is then asked to input simple queries, after which output is
	 * formatted and student records printed. If invalid query is given, used is
	 * notified and prompted to enter input again. Program ends when "exit" is
	 * entered.
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid input
	 * @throws ParserException
	 *             in case of invalid syntax of query.
	 * @param args
	 *            command line arguments passed when starting the program
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		String line = "";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);


		System.out.print(">");

		while ((line = scanner.nextLine()) != null) {

			if ("exit".equals(line)) {
				System.out.println("Goodbye!");
				System.exit(0);
			}

			line = line.trim();
			String[] splitLine = line.split("\\s+");
			String query = "";

			for (int i = 1; i < splitLine.length; i++) {
				query += splitLine[i] + " ";
			}

			query = query.trim();

			QueryParser parser = null;
			try {
				parser = new QueryParser(query);
			} catch (IllegalArgumentException | ParserException e) {
				System.out.println("Invalid query!");
				System.out.print("\n>");
				continue;
			}

			if (parser.getQuery().size() == 0) {
				System.out.println("Records selected: 0");
				System.out.print("\n>");
				continue;
			}

			if (parser.isDirectQuery()) {
				StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());

				System.out.println("Using index for record retrieval");

				if (r == null) {
					System.out.println("Records selected: 0");
					System.out.print("\n>");
					continue;
				}

				List<StudentRecord> studentRecords = new ArrayList<>();
				studentRecords.add(r);

				formatOutput(studentRecords);

			} else {

				List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));

				if (studentRecords.size() == 0) {
					System.out.println("Records selected: 0");
					System.out.print("\n>");
					continue;
				}

				formatOutput(studentRecords);
			}
		}
		scanner.close();
	}

	/**
	 * Class used for formatting console output based on the query result. Table is
	 * designed dynamically based on longest first and last name of all student
	 * records which satisfy the given conditional expressions.
	 * 
	 * @param studentRecords
	 *            result list of all student records to be printed on output.
	 */
	private static void formatOutput(List<StudentRecord> studentRecords) {

		int longestNameLength = longestFirstName(studentRecords);
		int longestLastNameLength = longestLastname(studentRecords);

		StringBuilder outputBuilder = new StringBuilder();

		outputBuilder.append("+");
		outputBuilder.append("============+");
		for (int i = 0; i < longestLastNameLength + 2; i++) {
			outputBuilder.append("=");
		}
		outputBuilder.append("+");
		for (int i = 0; i < longestNameLength + 2; i++) {
			outputBuilder.append("=");
		}
		outputBuilder.append("+");
		outputBuilder.append("===+\n");

		String header = outputBuilder.toString();

		for (StudentRecord r : studentRecords) {

			outputBuilder.append("| " + r.getJmbag() + " | ");
			outputBuilder.append(r.getLastName());
			for (int i = 0; i < longestLastNameLength - r.getLastName().toCharArray().length + 1; i++) {
				outputBuilder.append(" ");
			}
			outputBuilder.append("| ");
			outputBuilder.append(r.getFirstName());
			for (int i = 0; i < longestNameLength - r.getFirstName().toCharArray().length + 1; i++) {
				outputBuilder.append(" ");
			}
			outputBuilder.append("| ");
			outputBuilder.append(r.getFinalGrade() + " |");

			outputBuilder.append("\n");

		}
		outputBuilder.append(header);
		System.out.print(outputBuilder.toString());
		System.out.println("Records selected: " + studentRecords.size());
		System.out.print("\n>");

	}

	/**
	 * Auxiliary method used for calculating longest last name of all students in
	 * student records list.
	 * 
	 * @param studentRecords
	 *            list of all students who's last name is checked
	 * @return length of longest last name
	 */
	private static int longestLastname(List<StudentRecord> studentRecords) {
		int longestLastName = 0;

		for (StudentRecord record : studentRecords) {

			if (record.getLastName().toCharArray().length > longestLastName) {
				longestLastName = record.getLastName().toCharArray().length;
			}

		}
		return longestLastName;
	}

	/**
	 * Auxiliary method used for calculating longest first name of all students in
	 * student records list.
	 * 
	 * @param studentRecords
	 *            list of all students who's first name is checked
	 * @return length of longest first name
	 */
	private static int longestFirstName(List<StudentRecord> studentRecords) {

		int longestName = 0;

		for (StudentRecord record : studentRecords) {

			if (record.getFirstName().toCharArray().length > longestName) {
				longestName = record.getFirstName().toCharArray().length;
			}

		}
		return longestName;
	}

	/**
	 * Auxiliary method used for reading all data from "database.txt" file. If
	 * reading of file failed, user is notified and program ends the execution.
	 * 
	 * @return List of lines of file.
	 */
	public static List<String> readFromFile() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"),
					StandardCharsets.UTF_8);
			return lines;

		} catch (IOException e) {
			System.out.println("Cannot read file: database.txt");
			System.exit(1);
		}

		return null;
	}

}
