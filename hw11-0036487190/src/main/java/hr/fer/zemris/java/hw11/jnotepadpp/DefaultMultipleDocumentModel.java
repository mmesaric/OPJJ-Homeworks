package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This class offers the implementation of a single MultipleDocumentModel
 * interface. It holds the information of all currently active tabs, currently
 * displayed document and all active listeners.
 * 
 * @author Marko Mesarić
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Red image icon
	 */
	private static ImageIcon RED_ICON;
	/**
	 * Green image icon
	 */
	private static ImageIcon GREEN_ICON;

	/**
	 * List of active,open documents (tabs)
	 */
	private List<SingleDocumentModel> models;
	/**
	 * Currently active model
	 */
	private SingleDocumentModel currentModel;

	/**
	 * List of listeners
	 */
	private List<MultipleDocumentListener> listeners;

	/**
	 * Constructor which initializes the lists and adds change listener to this
	 * component
	 */
	public DefaultMultipleDocumentModel() {

		this.models = new ArrayList<>();
		this.listeners = new ArrayList<>();

		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = getSelectedIndex();
				if (index == -1) {
					return;
				} else {
					SingleDocumentModel previousModel = currentModel;
					currentModel = getDocument(getSelectedIndex());
					notifyCurrentDocumentChanged(previousModel, currentModel);
				}
			}
		});

		try {
			readIcons();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Auxiliary method used for reading icon content.
	 * 
	 * @throws IOException
	 *             in case of error while reading contents
	 */
	private void readIcons() throws IOException {
		InputStream is3 = this.getClass().getResourceAsStream("icons/redDisk.png");
		byte[] bytes = is3.readAllBytes();
		RED_ICON = new ImageIcon(bytes);

		is3 = this.getClass().getResourceAsStream("icons/blueDisk.png");
		bytes = is3.readAllBytes();
		is3.close();
		GREEN_ICON = new ImageIcon(bytes);

	}

	/**
	 * Auxiliary method used for notifying all current listeners of change.
	 * 
	 * @param previousModel
	 *            previous current model
	 * @param currentModel
	 *            new, current model
	 */
	private void notifyCurrentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		listeners.forEach(listener -> listener.currentDocumentChanged(previousModel, currentModel));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = new DefaultSingleDocumentModel("", null);
		models.add(model);
		addTab(LocalizationProvider.getInstance().getString("create_document"),
				new JPanel().add(new JScrollPane(model.getTextComponent())));
		this.setSelectedIndex(models.size() - 1);

		setIconAt(getSelectedIndex(), GREEN_ICON);
		addIconListener();
		return model;
	}

	/**
	 * Auxiliary method used for adding single document listener on active
	 * documents. Adds the functionality of switching tab icons on change.
	 */
	private void addIconListener() {
		currentModel.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIconAt(getSelectedIndex(), RED_ICON);
				} else {
					setIconAt(getSelectedIndex(), GREEN_ICON);
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toString());
				notifyCurrentDocumentChanged(null, model);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path can't be null");

		File fileName = path.toFile();

		if (!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"File " + fileName.getAbsolutePath() + " doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		byte[] buff;
		try {
			buff = Files.readAllBytes(path);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"Error when trying to read file " + fileName.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String text = new String(buff, StandardCharsets.UTF_8);

		SingleDocumentModel model = new DefaultSingleDocumentModel(text, path);

		if (!models.contains(model)) {
			models.add(model);
			addTab(fileName.getName().toString(), null, new JPanel().add(new JScrollPane(model.getTextComponent())),
					fileName.toString());
			this.setSelectedIndex(models.size() - 1);

		} else {
			this.setSelectedIndex(models.indexOf(model));
		}
		setIconAt(getSelectedIndex(), GREEN_ICON);
		addIconListener();
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, data);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"Error while trying to save file." + newPath.toAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<SingleDocumentModel> documentsCopy = new ArrayList<>(models);
		documentsCopy.remove(currentModel);

		for (SingleDocumentModel document : documentsCopy) {
			Path path = document.getFilePath();
			if (path != null) {

				if (newPath.equals(path)) {
					JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
							"Can't save file since demanded file with demanded file is being edited."
									+ newPath.toAbsolutePath(),
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		currentModel.setFilePath(newPath);
		JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "File was saved.", "Information",
				JOptionPane.INFORMATION_MESSAGE);
		currentModel.setModified(false);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		models.remove(getSelectedIndex());
		remove(getSelectedIndex());
		listeners.forEach(listener -> listener.documentRemoved(currentModel));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Listener can't be null");
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Listener can't be null");
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return models.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index > models.size() - 1) {
			throw new IllegalArgumentException("Invalid index. Valid index range is: [0," + models.size());
		}
		return models.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return models.iterator();
	}

}
