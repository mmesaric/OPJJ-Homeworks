package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface which defines the methods of working with a multiple document
 * model.
 * 
 * @author Marko Mesarić
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Method used for adding new, empty document
	 * 
	 * @return created, new, single document model
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Getter for current single document
	 * 
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * This method opens the file from given path, adds it to multiple document
	 * model and sets it as current active document
	 * 
	 * @param path
	 *            from which file is to be loaded
	 * @return created single document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the document under given path
	 * 
	 * @param model
	 *            which is to be saved
	 * @param newPath
	 *            path under which document is saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Method used for closing the given document model
	 * 
	 * @param model
	 *            document to be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Method used for adding multiple document listener
	 * 
	 * @param l
	 *            listener to be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Method used for removing multiple document listener
	 * 
	 * @param l
	 *            listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Getter for number of documents in list (number of tabs)
	 * 
	 * @return
	 */
	int getNumberOfDocuments();

	/**
	 * Getter for document stored under given index
	 * 
	 * @param index
	 *            index of searched document
	 * @return single document model
	 */
	SingleDocumentModel getDocument(int index);

}
