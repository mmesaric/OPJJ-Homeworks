package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This interface offers methods of a single document listener.
 * 
 * @author Marko Mesarić
 *
 */
public interface SingleDocumentListener {

	/**
	 * Method which is called after modify status of document has been updated
	 * 
	 * @param model
	 *            which modify status has been changed
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Method which is called after model's path has been updated
	 * 
	 * @param model
	 *            model which path has been updated.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
