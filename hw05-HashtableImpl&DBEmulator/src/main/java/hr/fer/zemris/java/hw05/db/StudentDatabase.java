package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Class used for representation of a simple database holding student records,
 * information about their jmbag, first name, last name and final grade.
 * 
 * @author Marko Mesarić
 *
 */
public class StudentDatabase {

	/**
	 * List for storing student records
	 */
	private List<StudentRecord> studentRecords;
	/**
	 * Map used for storing student records based on jmbag as key. Used in direct
	 * queries for fast retrieval
	 */
	private SimpleHashtable<String, StudentRecord> studentTable;

	/**
	 * Standard array length when splitting a single line
	 */
	private static final int LENGTH_WHEN_ONE_SURNAME = 4;
	/**
	 * Array length in case of two surnames for a single student
	 */
	private static final int LENGTH_WHEN_TWO_SURNAMES = 5;

	/**
	 * Constructor which parses the input of database and creates a list of student
	 * records along with the map
	 * 
	 * @param databaseContent
	 *            list of lines of file representing the database.
	 */
	public StudentDatabase(List<String> databaseContent) {

		studentRecords = new ArrayList<>();
		studentTable = new SimpleHashtable<>();

		if (databaseContent == null) {
			throw new NullPointerException("Content can't be null");
		}

		for (String record : databaseContent) {
			String[] splitString = record.split("\\s+");

			String jmbag;
			String firstName;
			String lastName;
			int finalGrade;

			jmbag = splitString[0];
			finalGrade = Integer.parseInt(splitString[splitString.length - 1]);
			firstName = splitString[splitString.length - 2];

			if (splitString.length == LENGTH_WHEN_ONE_SURNAME) {
				lastName = splitString[1];
			}

			else if (splitString.length == LENGTH_WHEN_TWO_SURNAMES) {
				lastName = splitString[1] + " " + splitString[2];
			}

			else {
				throw new IllegalArgumentException("Input .txt database is invalid");
			}

			StudentRecord studentRecord = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			studentRecords.add(studentRecord);
			studentTable.put(jmbag, studentRecord);

		}

	}

	/**
	 * Method used fast retrieval of student record in constant time in case of
	 * direct query
	 * 
	 * @param jmbag
	 *            jmbag of searched student's record
	 * @return
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentTable.get(jmbag);
	}

	/**
	 * Method which filters List of student records based on given Filter parameter
	 * 
	 * @param filter
	 *            based on which records are filtered
	 * @return filtered list of student records
	 */
	public List<StudentRecord> filter(IFilter filter) {

		List<StudentRecord> filteredList = new ArrayList<>();

		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				filteredList.add(record);
			}
		}
		return filteredList;
	}
}
