package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface which defines the methods of working with a single document model.
 * 
 * @author Marko Mesarić
 *
 */
public interface SingleDocumentModel {

	/**
	 * Getter for text component of a single document
	 * 
	 * @return
	 */
	JTextArea getTextComponent();

	/**
	 * Getter for file path
	 * 
	 * @return file path
	 */
	Path getFilePath();

	/**
	 * Setter for file Path
	 * 
	 * @param path
	 *            path to be set
	 */
	void setFilePath(Path path);

	/**
	 * Returns true if document was modified, false otherwise.
	 * 
	 * @return
	 */
	boolean isModified();

	/**
	 * Setter for modified flag
	 * 
	 * @param modified
	 *            given value for flag to be set
	 */
	void setModified(boolean modified);

	/**
	 * Method used for registering listeners
	 * 
	 * @param l
	 *            listener to be registered
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Method used for removing listeners
	 * 
	 * @param l
	 *            listener to be de-registered
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
