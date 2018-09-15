package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class demonstrates simple modifications of list of student records using
 * streams. Information about student's results this semester is read from log
 * file named "studenti.txt" located in project resources. Auxiliary methods are
 * used for reading and interpreting content of the log file. Each line in file
 * represents a single Student record containing information in following order:
 * jmbag (identifier), last name, first name, number of points scored on mid
 * term exam, number of points scored on final exam, number of points scored on
 * practice exams and finally, final grade this semester. Each modification is
 * printed on standard output.
 * 
 * @author Marko Mesarić
 *
 */
public class StudentDemo {

	/**
	 * Constant used for defining minimum points condition
	 */
	private static final int MIN_POINTS = 25;
	/**
	 * Constant used for defining maximum grade
	 */
	private static final int MAXIMUM_GRADE = 5;
	/**
	 * Constant used for defining minimum grade
	 */
	private static final int MINIMUM_GRADE = 1;

	/**
	 * Main method used for calling methods which perform list modifications using
	 * streams and printing the returned result on standard output.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {

		List<String> lines = readFromFile();

		List<StudentRecord> records = convert(lines);

		System.out.println("Number of students who's total points from all exams is greater than 25: "
				+ vratiBodovaViseOd25(records));

		System.out.println("Number of students who's final grade is 5: " + vratiBrojOdlikasa(records));
		System.out.println("\n");

		System.out.println("Students who's final grade is 5:");
		vratiListuOdlikasa(records).forEach((record) -> System.out.println(record.getJmbag() + " "
				+ record.getFirstName() + " " + record.getLastName() + " " + record.getFinalGrade()));
		System.out.println("\n");

		System.out.println("Students who's final grade is 5 sorted by total exam points descending");
		vratiSortiranuListuOdlikasa(records).forEach((record) -> System.out.println(record.getJmbag() + " "
				+ record.getFirstName() + " " + record.getLastName() + " " + record.getFinalGrade() + " "
				+ (record.getMidtermExamPoints() + record.getFinalExamPoints() + record.getPracticeExamPoints())));
		System.out.println("\n");

		System.out.println("JMBAG identifiers of all students who failed the class sorted in ascending order. ");
		vratiPopisNepolozenih(records).forEach((record) -> System.out.println(record.getJmbag() + " "
				+ record.getFirstName() + " " + record.getLastName() + " " + record.getFinalGrade()));
		System.out.println("\n");

		razvrstajStudentePoOcjenama(records).forEach((key, value) -> System.out
				.println("Number of students who got " + (int) key + " as final grade: " + value.size()));
		System.out.println("\n");

		vratiBrojStudenataPoOcjenama(records).forEach((key, value) -> System.out
				.println("Number of students who got " + (int) key + " as final grade: " + value));
		System.out.println("\n");

		razvrstajProlazPad(records).forEach(
				(key, value) -> System.out.println(key == true ? "Number of students who passed: " + value.size()
						: "Number of students who failed: " + value.size()));
		System.out.println("\n");

	}

	/**
	 * Method which performs stream modification by counting all student records
	 * with sum of all exam points greater than 25.
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students who's sum of all exam points is greater than 25.
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {

		return records.stream().filter(record -> (record.getMidtermExamPoints() + record.getFinalExamPoints()
				+ record.getPracticeExamPoints() > MIN_POINTS)).count();
	}

	/**
	 * Method which performs stream modification by counting all student records
	 * who's final grade is 5.
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students who's final grade is 5.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {

		return records.stream().filter(record -> record.getFinalGrade() == MAXIMUM_GRADE).count();
	}

	/**
	 * Method which performs stream modification by creating the list of all student
	 * records who's final grade is 5.
	 * 
	 * @param records
	 *            list of student records
	 * @return number list of student records who's final grade is 5.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {

		return records.stream().filter(record -> (record.getFinalGrade() == MAXIMUM_GRADE))
				.collect(Collectors.toList());
	}

	/**
	 * Method which performs stream modification by creating the list of all student
	 * records who's final grade is 5 and sorts that list in such a manner that
	 * student with most scored points is first in list (descending order based on
	 * total exam points).
	 * 
	 * @param records
	 *            list of student records
	 * @return number sorted descending list based on total exam points of student
	 *         records who's final grade is 5.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {

		return records.stream().filter(record -> (record.getFinalGrade() == MAXIMUM_GRADE))
				.sorted((record1, record2) -> Double
						.valueOf(record2.getMidtermExamPoints() + record2.getFinalExamPoints()
								+ record2.getPracticeExamPoints())
						.compareTo(Double.valueOf(record1.getMidtermExamPoints() + record1.getFinalExamPoints()
								+ record1.getPracticeExamPoints())))
				.collect(Collectors.toList());
	}

	/**
	 * Method which performs stream modification by creating the list of all student
	 * records who failed the class (final grade =1) and sorts that list in
	 * ascending order based on identifier (jmbag).
	 * 
	 * @param records
	 *            list of student records
	 * @return list of students who failed the class in ascending order based on
	 *         identifier(jmbag).
	 */
	private static List<StudentRecord> vratiPopisNepolozenih(List<StudentRecord> records) {

		return records.stream().filter(record -> (record.getFinalGrade() == MINIMUM_GRADE))
				.sorted((record1, record2) -> record1.getJmbag().compareTo(record2.getJmbag()))
				.collect(Collectors.toList());
	}

	/**
	 * Method which performs stream modification by creating the map with a key
	 * equal to grade and value equal to list of students who scored that grade from
	 * given list of student records.
	 * 
	 * @param records
	 *            list of student records
	 * @return map of grades stored as key mapped to a list of students who scored
	 *         that particular grade as value.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {

		return records.stream().collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * Method which performs stream modification by creating the map with a key
	 * equal to grade and value equal to number of students who scored that grade
	 * from given list of student records.
	 * 
	 * @param records
	 *            list of student records
	 * @return map of grades mapped to number of students who scored that particular
	 *         grade as value.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {

		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getFinalGrade, value -> 1, (key, value) -> key + 1));
	}

	/**
	 * Method which performs stream modification by creating the map with a key
	 * equal to true or false (true if final grade of read record is greater than 1,
	 * false otherwise) and value equal to list of students who failed the class
	 * (grade 1, key = false), or a list of students who passed the class(grade>1,
	 * key = true)
	 * 
	 * @param records
	 *            list of student records
	 * @return map of list of students who passed the exam mapped to key true, and
	 *         the list of students who failed the exam mapped to false.
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {

		return records.stream().collect(Collectors.partitioningBy(record -> record.getFinalGrade() > 1));
	}

	/**
	 * Auxiliary method used for iterating through list of strings generated from
	 * log file. Each line consists of information about student in following order:
	 * jmbag (identifier), last name, first name, number of points scored on mid
	 * term exam, number of points scored on final exam, number of points scored on
	 * practice exams and finally, final grade this semester.
	 * 
	 * @param lines
	 *            List of strings read from log file
	 * @return list of student records parsed from lines of log file
	 */
	private static List<StudentRecord> convert(List<String> lines) {

		List<StudentRecord> studentRecords = new ArrayList<>();

		for (String studentLine : lines) {

			if (studentLine.isEmpty()) {
				continue;
			}

			String[] studentArray = studentLine.split("\\s+");
			try {
				studentRecords.add(new StudentRecord(studentArray[0], studentArray[1], studentArray[2],
						Double.parseDouble(studentArray[3]), Double.parseDouble(studentArray[4]),
						Double.parseDouble(studentArray[5]), Integer.parseInt(studentArray[6])));

			} catch (NumberFormatException e) {
				System.err.println("Error when reading exam points or final grade from file");
			}
		}

		return studentRecords;
	}

	/**
	 * Auxiliary method used for generating and returning contents of the
	 * "studenti.txt" file located in project resources as a list of Strings. File
	 * is a log of students and their performance on exams in the given semester. If
	 * file wasn't read successfully, user is notified and program stops the
	 * execution.
	 * 
	 * @return contents of file as a list of strings.
	 */
	private static List<String> readFromFile() {
		try {
			return Files.readAllLines(Paths.get("src/main/resources/studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Can't read from file.");
			System.exit(0);
		}
		return null;
	}

}
