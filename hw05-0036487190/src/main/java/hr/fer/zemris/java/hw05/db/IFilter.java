package hr.fer.zemris.java.hw05.db;

/**
 * Interface which defines a single method used for filtering given student
 * records.
 * 
 * @author Marko Mesarić
 *
 */
public interface IFilter {

	/**
	 * Method which, when implemented, examines the given student record and
	 * determines if it is accepted or not based on conditional expressions.
	 * 
	 * @param record
	 *            record to be examined
	 * @return true if accepts, false otherwise
	 */
	public boolean accepts(StudentRecord record);

}
