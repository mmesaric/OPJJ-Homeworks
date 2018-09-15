package hr.fer.zemris.java.hw05.db;

/**
 * Definition of a strategy which is responsible for obtaining a requested field
 * value from given student record
 * 
 * @author Marko Mesarić
 *
 */
public interface IFieldValueGetter {

	/**
	 * Method responsible for obtaining a requested field from given record
	 * 
	 * @param record
	 *            given student record
	 * @return value to be returned according to requested field value
	 */
	public String get(StudentRecord record);

}
