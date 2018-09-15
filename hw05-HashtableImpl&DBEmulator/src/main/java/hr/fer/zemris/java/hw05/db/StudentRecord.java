package hr.fer.zemris.java.hw05.db;

/**
 * Class which represents the model of a single student record.
 * 
 * @author Marko Mesarić
 *
 */
public class StudentRecord {

	/**
	 * Unique identifier for student
	 */
	private final String jmbag;
	/**
	 * Student's last name
	 */
	private final String lastName;
	/**
	 * Student's first name
	 */
	private final String firstName;
	/**
	 * Student's final grade
	 */
	private final int finalGrade;

	/**
	 * Constructor which sets the values to given values.
	 * 
	 * @param jmbag
	 *            student's jmbag
	 * @param lastName
	 *            student's last name
	 * @param firstName
	 *            student's first name
	 * @param finalGrade
	 *            student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}


	/**
	 * Getter for student's last name
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for student's first name
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for student's final grade
	 * 
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Two students are equal if the jmbags are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		}

		return jmbag.equals(other.jmbag);
	}

}
