package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This interface offers methods of a single, multiple document listener.
 * 
 * @author Marko Mesarić
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Method called when tab has been switched
	 * 
	 * @param previousModel
	 *            previous active tab
	 * @param currentModel
	 *            current active tab
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method called when document (tab) has been added
	 * 
	 * @param model
	 *            document model which has been added
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Method called when document (tab) has been removed.
	 * 
	 * @param model
	 *            document model which has been removed
	 */
	void documentRemoved(SingleDocumentModel model);

}
