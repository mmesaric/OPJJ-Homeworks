package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJMenu;

/**
 * This class offers the implementation of our JNotepad++. It offers the
 * functionality off working with multiple tabs, a set of often used commands
 * and document manipulation. It also implements the localization solution and
 * user is offered to choose from 3 different languages (German, English and
 * Croatian).
 * 
 * @author Marko Mesarić
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Multiple document model
	 */
	private DefaultMultipleDocumentModel multipleDocumentModel;

	/**
	 * Form localization provider used for localization
	 */
	private FormLocalizationProvider flp;

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Used for storing currently copied text
	 */
	private String copiedText = "";
	/**
	 * boolean flag used for determining if program should stop execution
	 */
	private volatile static boolean continueWork = true;

	/**
	 * Jlabel for length in status bar
	 */
	private JLabel length;
	/**
	 * Jlabel for line in status bar
	 */
	private JLabel ln;
	/**
	 * Jlabel for column in status bar
	 */
	private JLabel col;
	/**
	 * Jlabel for selection in status bar
	 */
	private JLabel sel;

	/**
	 * Action for creating new empty documents
	 */
	private Action newBlankDocumentAction;

	/**
	 * Action for opening existing documents
	 */
	private Action openExistingDocumentAction;

	/**
	 * Action for saving documents
	 */
	private Action saveDocumentAction;

	/**
	 * Action for saving as documents.
	 */
	private Action saveAsDocumentAction;

	/**
	 * Action for closing the document
	 */
	private Action closeDocumentAction;

	/**
	 * Action for copying selected text
	 */
	private Action copyAction;

	/**
	 * Action for cutting selected text
	 */
	private Action cutAction;

	/**
	 * Action for pasting copied text.
	 */
	private Action pasteAction;

	/**
	 * Action for displaying document statistics
	 */
	private Action statisticsAction;

	/**
	 * Action for switching selected text to upper case
	 */
	private Action toUpperCaseAction;

	/**
	 * Action for switching selected text to lower case
	 */
	private Action toLowerCaseAction;

	/**
	 * Action for inverting the case of selected text
	 */
	private Action invertCaseAction;

	/**
	 * Action for exiting the application
	 */
	private Action exitAction;

	/**
	 * Action for unique action
	 */
	private Action uniqueAction;

	/**
	 * Action for sorting selected text in ascending order
	 */
	private Action sortAscendingAction;
	/**
	 * Action for sorting selected text in descending order
	 */
	private Action sortDescendingAction;
	/**
	 * Action for changing language to german
	 */
	private Action deAction;

	/**
	 * Action for changing language to croatian.
	 */
	private Action hrAction;

	/**
	 * Action for changing language to english.
	 */
	private Action enAction;

	/**
	 * Constructor for this class. Creates the object, sets the window properties
	 * and adds necessary listener to windows.
	 */
	public JNotepadPP() {

		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

		setupActions();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(200, 50);
		setTitle("JNotepad++");
		setSize(900, 500);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				checkModifiedDocuments();
			}

		});
		length = new LocalizableJLabel("length", flp);
		ln = new LocalizableJLabel("line", flp);
		col = new LocalizableJLabel("column", flp);
		sel = new LocalizableJLabel("selection", flp);

		initGUI();
	}

	/**
	 * Auxiliary method which goes through all necessary actions and "creates" their
	 * expected behavior
	 * 
	 */
	private void setupActions() {

		/**
		 * Creates the new blank document action as new Localizable action and offers
		 * the implementation of creating new document functionality.
		 */
		newBlankDocumentAction = new LocalizableAction("create_document", "create_document_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				multipleDocumentModel.createNewDocument();
			}
		};

		/**
		 * Creates the open existing document action as new Localizable action and
		 * offers the implementation of opening existing document functionality.
		 */
		openExistingDocumentAction = new LocalizableAction("open_file", "open_file_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				multipleDocumentModel.loadDocument(filePath);
			}
		};

		/**
		 * Creates the save document action as new Localizable action and offers the
		 * implementation of saving document functionality.
		 */
		saveDocumentAction = new LocalizableAction("save_file", "save_file_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				SingleDocumentModel currentDocument = multipleDocumentModel.getCurrentDocument();
				if (currentDocument == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				Path openedFilePath = multipleDocumentModel.getCurrentDocument().getFilePath();

				if (openedFilePath == null) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(JNotepadPP.this, "Nothing was saved.", "Warning",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					openedFilePath = jfc.getSelectedFile().toPath();
				} else {
					openedFilePath = multipleDocumentModel.getCurrentDocument().getFilePath();
				}

				multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), openedFilePath);
			}
		};

		/**
		 * Creates the save as document action as new Localizable action and offers the
		 * implementation of saving as document functionality. Checks if current
		 * document under given path exists and asks the user if he wants to overwrite.
		 */
		saveAsDocumentAction = new LocalizableAction("save_as_file", "save_as_file_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SingleDocumentModel currentDocument = multipleDocumentModel.getCurrentDocument();
				if (currentDocument == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Nothing was saved.", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path openedFilePath = jfc.getSelectedFile().toPath();

				if (openedFilePath.toFile().exists()) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to overwrite this file?",
							"Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == 0) {
						multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), openedFilePath);
						return;
					} else {
						return;
					}
				}

				multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), openedFilePath);
			}
		};

		/**
		 * Creates the close document action as new Localizable action and offers the
		 * implementation of closing current document functionality.
		 */
		closeDocumentAction = new LocalizableAction("close_document", "close_document_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel currentDocument = multipleDocumentModel.getCurrentDocument();

				if (currentDocument == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (currentDocument.isModified()) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Warning",
							JOptionPane.YES_NO_OPTION);
					if (dialogResult == 0) {
						saveDocumentAction.actionPerformed(null);
						multipleDocumentModel.closeDocument(currentDocument);
					} else {
						multipleDocumentModel.closeDocument(currentDocument);
					}
					return;
				}

				multipleDocumentModel.closeDocument(currentDocument);
			}
		};

		/**
		 * Creates the copy selected text action as new Localizable action and offers
		 * the implementation of copying selected text from current document
		 * functionality.
		 */
		copyAction = new LocalizableAction("copy", "copy_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SingleDocumentModel currentModel = multipleDocumentModel.getCurrentDocument();

				JTextArea editor = currentModel.getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					copiedText = doc.getText(offset, len);
					// doc.remove(offset, len);

				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

			}
		};

		/**
		 * Creates the cut selected text action as new Localizable action and offers the
		 * implementation of cutting selected text from current document functionality.
		 */
		cutAction = new LocalizableAction("cut", "cut_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SingleDocumentModel currentModel = multipleDocumentModel.getCurrentDocument();

				JTextArea editor = currentModel.getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					copiedText = doc.getText(offset, len);
					doc.remove(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		/**
		 * Creates the paste copied text action as new Localizable action and offers the
		 * implementation of pasting copied text to current document functionality.
		 */
		pasteAction = new LocalizableAction("paste", "paste_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SingleDocumentModel currentModel = multipleDocumentModel.getCurrentDocument();
				if (currentModel == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				JTextArea editor = currentModel.getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					doc.remove(offset, len);
					doc.insertString(offset, copiedText, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

			}
		};

		/**
		 * Creates the documents statistics text action as new Localizable action and
		 * offers the implementation of showing additional statistics of selected text
		 * from current document functionality.
		 */
		statisticsAction = new LocalizableAction("statistics", "statistics_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SingleDocumentModel currentModel = multipleDocumentModel.getCurrentDocument();
				if (currentModel == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				String text = currentModel.getTextComponent().getText();

				int numberOfCharacters = text.length();
				int numberOfNonBlankCharacters = text.replaceAll("\\s+", "").length();
				int numberOfLines = (int) (text.chars().filter(currentChar -> currentChar == '\n').count() + 1);

				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString("part1") + numberOfCharacters + " " + flp.getString("part2")  + numberOfNonBlankCharacters
								+" "+ flp.getString("part3") + numberOfLines+" " + flp.getString("part4"),
						flp.getString("info"), JOptionPane.WARNING_MESSAGE);

			}
		};

		/**
		 * Creates the to upper case action as new Localizable action and offers the
		 * implementation of switching selected text to upper case functionality.
		 */
		toUpperCaseAction = new LocalizableAction("to_upper_case", "to_upper_case_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					String toTransform = doc.getText(offset, len);
					doc.remove(offset, len);
					doc.insertString(offset, toTransform.toUpperCase(), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		/**
		 * Creates the to lower case action as new Localizable action and offers the
		 * implementation of switching selected text to lower case functionality.
		 */
		toLowerCaseAction = new LocalizableAction("to_lower_case", "to_lower_case_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					String toTransform = doc.getText(offset, len);
					doc.remove(offset, len);
					doc.insertString(offset, toTransform.toLowerCase(), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		/**
		 * Creates the invert case action as new Localizable action and offers the
		 * implementation of inverting characters of a selected text functionality.
		 */
		invertCaseAction = new LocalizableAction("invert_case", "invert_case_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				int offset = 0;

				if (len != 0) {
					offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
				} else {
					len = doc.getLength();
				}

				try {
					String text = doc.getText(offset, len);
					text = changeCase(text);
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}

			private String changeCase(String text) {
				char[] znakovi = text.toCharArray();
				for (int i = 0; i < znakovi.length; i++) {
					char c = znakovi[i];
					if (Character.isLowerCase(c)) {
						znakovi[i] = Character.toUpperCase(c);
					} else if (Character.isUpperCase(c)) {
						znakovi[i] = Character.toLowerCase(c);
					}
				}
				return new String(znakovi);
			}
		};

		/**
		 * Creates the exit action as new Localizable action and offers the
		 * implementation of exiting the program functionality.
		 */
		exitAction = new LocalizableAction("exit", "exit_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				checkModifiedDocuments();
			}
		};

		/**
		 * Creates the sort ascending action as new Localizable action and offers the
		 * implementation of sorting selected lines in ascending manner functionality.
		 */
		sortAscendingAction = new LocalizableAction("sort_asc", "sort_asc_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Locale loc = Locale.forLanguageTag(flp.getString("lang"));
				Collator col = Collator.getInstance(loc);

				SingleDocumentModel currentModel = multipleDocumentModel.getCurrentDocument();
				if (currentModel == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				sortAuxiliary(true, col);
			}
		};

		/**
		 * Creates the sort descending action as new Localizable action and offers the
		 * implementation of sorting selected lines in descending manner functionality.
		 */
		sortDescendingAction = new LocalizableAction("sort_desc", "sort_desc_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Locale loc = Locale.forLanguageTag(flp.getString("lang"));
				Collator col = Collator.getInstance(loc);


				SingleDocumentModel currentModel = multipleDocumentModel.getCurrentDocument();
				if (currentModel == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				sortAuxiliary(false, col);
			}
		};

		/**
		 * Creates the unique action as new Localizable action and offers the
		 * implementation of deleting line duplications from selected area of text
		 * functionality.
		 */
		uniqueAction = new LocalizableAction("unique", "unique_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SingleDocumentModel currentModel = multipleDocumentModel.getCurrentDocument();
				if (currentModel == null || multipleDocumentModel.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("message"), flp.getString("title"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				int offset = len == 0 ? doc.getLength()
						: Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
					offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));

					String text = doc.getText(offset, len - offset);
					String[] splitText = text.split("\\r?\\n");
					List<String> lines = new ArrayList<>();
					for (int i = 0; i < splitText.length; i++) {
						lines.add(splitText[i]);
					}
					Set<String> sort = new LinkedHashSet<>(lines);
					int numberOfLines = (int) (text.chars().filter(currentChar -> currentChar == '\n').count() + 1);
					doc.remove(offset, len - offset);

					for (String input : sort) {
						numberOfLines--;
						if (numberOfLines > 0) {
							doc.insertString(offset, input + "\n", null);
						} else {
							doc.insertString(offset, input, null);
						}
						offset += input.length() + 1;
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		/**
		 * Creates the "to German language" action as new Localizable action and offers the
		 * implementation of switching all GUI components to German language functionality.
		 */
		deAction = new LocalizableAction("german_item", "german_item_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};

		/**
		 * Creates the "to Croatian language" action as new Localizable action and offers the
		 * implementation of switching all GUI components to Croatian language functionality.
		 */
		hrAction = new LocalizableAction("croatian_item", "croatian_item_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};

		/**
		 * Creates the "to English language" action as new Localizable action and offers the
		 * implementation of switching all GUI components to English language functionality.
		 */
		enAction = new LocalizableAction("english_item", "english_item_desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};
	}

	/**
	 * Auxiliary method used when sorting the selected text in text area component.
	 * 
	 * @param ascending
	 *            flag for determining if it should be sorted in ascending or
	 *            descending manner.
	 * @param col Collator object
	 */
	private void sortAuxiliary(boolean ascending, Collator col) {

		JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();

		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		if (len == 0)
			return;
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

		try {
			len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
			offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));

			String text = doc.getText(offset, len - offset);
			String[] splitText = text.split("\\r?\\n");
			List<String> lines = new ArrayList<>();
			for (int i = 0; i < splitText.length; i++) {
				lines.add(splitText[i]);
			}
			List<String> sorted;
			if (ascending) {
				sorted = lines.stream().sorted((o1, o2) -> col.compare(o1, o2)).collect(Collectors.toList());
			} else {
				sorted = lines.stream().sorted((o1, o2) -> col.compare(o2, o1)).collect(Collectors.toList());
			}

			int numberOfLines = (int) (text.chars().filter(currentChar -> currentChar == '\n').count() + 1);
			doc.remove(offset, len - offset);

			for (String input : sorted) {
				numberOfLines--;
				if (numberOfLines > 0) {
					doc.insertString(offset, input + "\n", null);
				} else {
					doc.insertString(offset, input, null);
				}
				offset += input.length() + 1;
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method which is called when window is trying to close. Checks for any
	 * unmodified documents and asks the user if he wants to save or discard
	 * changes, along with an option to abort the mission of closing the program.
	 */
	protected void checkModifiedDocuments() {
		Iterator<SingleDocumentModel> documents = multipleDocumentModel.iterator();
		List<SingleDocumentModel> copyOfDocuments = new ArrayList<>();

		while (documents.hasNext()) {
			copyOfDocuments.add(documents.next());
		}

		for (int i = copyOfDocuments.size() - 1; i >= 0; i--) {
			SingleDocumentModel model = copyOfDocuments.get(i);
			if (model.isModified()) {

				int response = JOptionPane.showConfirmDialog(JNotepadPP.this,
						"Do you want to save changes made to file?");

				if (response == 0) {
					if (model.getFilePath() == null) {
						saveAsDocumentAction.actionPerformed(null);

					} else {
						saveDocumentAction.actionPerformed(null);
					}
				} else if (response == 1) {
					model.setModified(false);
					multipleDocumentModel.closeDocument(model);
				} else {
					return;
				}
			} else {
				multipleDocumentModel.closeDocument(model);
			}
		}
		continueWork = false;
		dispose();
	}

	/**
	 * Method which initializes the GUI, creates toolbar, menubar and statusbar and
	 * displays all components accordingly. Also, adds the caret listener used for
	 * disabling and enabling the button clicks on toolbar.
	 */
	private void initGUI() {

		JPanel panel = new JPanel(new BorderLayout());

		multipleDocumentModel = new DefaultMultipleDocumentModel();

		multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if (multipleDocumentModel.getNumberOfDocuments() == 0) {
					setTitle("JNotepad++");
					return;
				}
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (multipleDocumentModel.getNumberOfDocuments() == 0) {
					setTitle("JNotepad++");
					return;
				}

				if (currentModel.getFilePath() == null) {
					setTitle("New file - JNotepad++");
				} else {
					setTitle(currentModel.getFilePath().toString() + " - JNotepad++");
				}
				JTextArea editor = currentModel.getTextComponent();
				editor.setFocusable(true);

				currentModel.getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {

						JTextArea editor = currentModel.getTextComponent();

						int len = editor.getCaret().getDot() - editor.getCaret().getMark();
						cutAction.setEnabled(len != 0);
						copyAction.setEnabled(len != 0);
						toUpperCaseAction.setEnabled(len != 0);
						toLowerCaseAction.setEnabled(len != 0);
						invertCaseAction.setEnabled(len != 0);
						sortAscendingAction.setEnabled(len != 0);
						sortDescendingAction.setEnabled(len != 0);

						calculateStatusBarFields(currentModel, editor);

					}

					private void calculateStatusBarFields(SingleDocumentModel currentModel, JTextArea editor) {

						int ln = 1;
						int col = 1;

						int caretPosition = editor.getCaretPosition();
						int length = editor.getText().length();
						JNotepadPP.this.length.setText(flp.getString("length") + length);
						try {
							ln = editor.getLineOfOffset(caretPosition);
							JNotepadPP.this.ln.setText(flp.getString("line") + (ln + 1));
							col = caretPosition - editor.getLineStartOffset(ln);
							JNotepadPP.this.col.setText(flp.getString("column") + col);
							int sel = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
							JNotepadPP.this.sel.setText(flp.getString("selection") + sel);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
						;
					}
				});

				CaretListener oldCaretListener = currentModel.getTextComponent().getCaretListeners()[0];
				oldCaretListener.caretUpdate(null);
			}
		});

		panel.add(multipleDocumentModel, BorderLayout.CENTER);

		createMenusBars(panel);
		createActions();
		createToolbar(panel);
		createStatusBar(panel);

		this.getContentPane().add(panel, BorderLayout.CENTER);
	}

	/**
	 * Auxiliary method used for creating the status bar and adding it to frame's
	 * content pane.
	 * 
	 * @param panel
	 */
	private void createStatusBar(JPanel panel) {

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panel.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 22));
		statusPanel.setLayout(new GridLayout(1, 5));

		statusPanel.add(length);
		statusPanel.add(ln);
		statusPanel.add(col);
		statusPanel.add(sel);
		statusPanel.add(new ClockPane());

		// panel.add(statusPanel, BorderLayout.PAGE_END);
		getContentPane().add(statusPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Auxiliary method used for creating and adding developed components to given
	 * panel.
	 * 
	 * @param panel
	 *            to which components are added.
	 */
	private void createToolbar(JPanel panel) {

		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(newBlankDocumentAction));
		toolBar.add(new JButton(openExistingDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.add(new JButton(statisticsAction));

		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));

		toolBar.add(new JButton(exitAction));

		panel.add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Auxiliary method used for creating and adding developed menus to given panel.
	 * 
	 * @param panel
	 *            to which components are added.
	 */

	private void createMenusBars(JPanel panel) {

		JMenuBar menuBar = new JMenuBar();

		// JMenu fileMenu = new JMenu("File");
		JMenu fileMenu = new LocalizableJMenu("file", flp);

		fileMenu.add(new JMenuItem(newBlankDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(openExistingDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(exitAction));

		menuBar.add(fileMenu);

		JMenu infoMenu = new LocalizableJMenu("info", flp);

		infoMenu.add(new JMenuItem(statisticsAction));

		menuBar.add(infoMenu);

		JMenu editMenu = new LocalizableJMenu("edit", flp);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.add(new JMenuItem(cutAction));

		menuBar.add(editMenu);

		JMenu toolsMenu = new LocalizableJMenu("tools", flp);

		JMenu changeCaseMenu = new LocalizableJMenu("change_case", flp);

		changeCaseMenu.add(new JMenuItem(toUpperCaseAction));
		changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
		changeCaseMenu.add(new JMenuItem(invertCaseAction));

		toolsMenu.add(changeCaseMenu);

		toolsMenu.add(new JMenuItem(uniqueAction));

		menuBar.add(toolsMenu);

		JMenu sortMenu = new LocalizableJMenu("sort", flp);

		sortMenu.add(new JMenuItem(sortAscendingAction));
		sortMenu.add(new JMenuItem(sortDescendingAction));

		toolsMenu.add(sortMenu);

		JMenu languagesMenu = new LocalizableJMenu("languages", flp);

		languagesMenu.add(new JMenuItem(hrAction));
		languagesMenu.add(new JMenuItem(enAction));
		languagesMenu.add(new JMenuItem(deAction));

		menuBar.add(languagesMenu);
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
	}

	/**
	 * Auxiliary method used for "creating" actions by defining their accelerator
	 * and mnemonic key.
	 */
	private void createActions() {

		newBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openExistingDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExistingDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cutAction.setEnabled(false);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.setEnabled(false);

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		toUpperCaseAction.setEnabled(false);

		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		toLowerCaseAction.setEnabled(false);

		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		invertCaseAction.setEnabled(false);

		sortAscendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		sortDescendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		hrAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
		hrAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);

		enAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		enAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		deAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		deAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

	}

	/**
	 * This class offers the implementation off a clock displayed in lower right
	 * corner of JNotepadPP
	 * 
	 * @author Marko Mesarić
	 *
	 */
	static class ClockPane extends JPanel {

		/**
		 * Default serial version UID
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Jlabel used for storing current time.
		 */
		private JLabel clock;

		/**
		 * Default constructor which sets the preferences of this jpanel
		 */
		public ClockPane() {
			setLayout(new BorderLayout());
			clock = new JLabel();
			clock.setHorizontalAlignment(JLabel.RIGHT);
			clock.setFont(new Font("Arial", Font.BOLD, 11));
			setLabelDate();
			add(clock);

			final int HALF_A_SECOND = 500;

			Timer timer = new Timer(HALF_A_SECOND, (ActionListener) new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (continueWork) {
						setLabelDate();
					} else {
						((Timer) e.getSource()).stop();
					}
				}
			});
			timer.setRepeats(true);
			timer.start();
		}

		/**
		 * Method which updates the text of clock label.
		 */
		public void setLabelDate() {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			clock.setText(formatter.format(new Date()));
		}
	}

	/**
	 * Method which begins the execution of the program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}