package hr.fer.zemris.java.hw05.db;

/**
 * Implementation of strategy used for obtaining a requested field value from given student
 * record.
 * 
 * @author Marko Mesarić
 *
 */
public class FieldValueGetters {

	/**
	 * Strategy used for retrieving student's first name
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();
	/**
	 * Strategy used for retrieving student's last name
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();
	/**
	 * Strategy used for retrieving student's final grade
	 */
	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();

}
