package hr.fer.zemris.java.hw06.demo4;

/**
 * Class which represents the model of a single student record consisting of
 * information about student and his performance on given semester. Student
 * information is read only, modification is not allowed thus class attributes
 * are final.
 * 
 * @author Marko Mesarič
 *
 */
public class StudentRecord {

	/**
	 * student's identifier
	 */
	private final String jmbag;
	/**
	 * student's last name
	 */
	private final String lastName;
	/**
	 * student's first name
	 */
	private final String firstName;
	/**
	 * points scored on mid term exam
	 */
	private final double midtermExamPoints;
	/**
	 * points scored on final exam.
	 */
	private final double finalExamPoints;
	/**
	 * points scored on practice exams
	 */
	private final double practiceExamPoints;
	/**
	 * student's final grade
	 */
	private final int finalGrade;

	/**
	 * Default constructor which sets the student's attributes values to given
	 * values.
	 * 
	 * @param jmbag
	 *            student's identifier
	 * @param lastName
	 *            student's first name
	 * @param firstName
	 *            student's first name
	 * @param midtermExamPoints
	 *            points scored on mid term exam
	 * @param finalExamPoints
	 *            points scored on final exam.
	 * @param practiceExamPoints
	 *            points scored on practice exams
	 * @param finalGrade
	 *            student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double midtermExamPoints,
			double finalExamPoints, double practiceExamPoints, int finalGrade) {
		super();

		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midtermExamPoints = midtermExamPoints;
		this.finalExamPoints = finalExamPoints;
		this.practiceExamPoints = practiceExamPoints;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for identifier
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for student's last name 
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for student's first name
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for student's score on mid term exam
	 * @return points scored on mid term exam
	 */
	public double getMidtermExamPoints() {
		return midtermExamPoints;
	}

	/**
	 * Getter for student's score on final exam
	 * @return points scored on final exam
	 */
	public double getFinalExamPoints() {
		return finalExamPoints;
	}

	/**
	 * Getter for student's score on practice exams
	 * @return points scored on practice exams
	 */
	public double getPracticeExamPoints() {
		return practiceExamPoints;
	}

	/**
	 * Getter for student's final grade
	 * @return student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

}
