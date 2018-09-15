package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This method offers the implementation of a single Document model. It keeps
 * the reference to text area and document path, along with flag used for
 * checking if document's been modified.
 * 
 * @author Marko Mesarić
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * reference to text area
	 */
	private JTextArea textArea;
	/**
	 * document path
	 */
	private Path path;
	/**
	 * modification flag
	 */
	private boolean modified;

	/**
	 * List of listeners
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Default constructor which adds the document listener to this text area.
	 * @param text text to be added in j text area.
	 * @param path file path
	 */
	public DefaultSingleDocumentModel(String text, Path path) {
		super();

		this.textArea = new JTextArea();
		textArea.setText(text);
		this.path = path;
		this.listeners = new ArrayList<>();

		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!isModified()) {
					setModified(true);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!isModified()) {
					setModified(true);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (!isModified()) {
					setModified(true);
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "Path can't be null.");
		this.path = path;
		listeners.forEach((listener) -> listener.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyObservers();
	}

	/**
	 * Auxiliary method used for notifying user of document modification. 
	 */
	private void notifyObservers() {
		listeners.forEach((listener) -> listener.documentModifyStatusUpdated(this));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Listener can't be null.");
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Listener can't be null.");
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
}
