package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.object.Circle;
import hr.fer.zemris.java.hw16.jvdraw.object.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;
import hr.fer.zemris.java.hw16.jvdraw.state.CircleState;
import hr.fer.zemris.java.hw16.jvdraw.state.FilledCircleState;
import hr.fer.zemris.java.hw16.jvdraw.state.LineState;
import hr.fer.zemris.java.hw16.jvdraw.state.PolygonState;
import hr.fer.zemris.java.hw16.jvdraw.state.Tool;

/**
 * This class represents the JVDraw application. Starts the execution and
 * renders the frame along with all other graphical components contained in this
 * frame. Application can be used to draw lines, circles and filled circles.
 * After being drawn on canvas, they are presented in list from where their
 * properties (color and coordinates) can be modified. Saving in form of ".jvd"
 * files is offered, same as opening and reading the same files.
 * 
 * @author Marko Mesarić
 *
 */
public class JVDraw extends JFrame {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Current state reference
	 */
	private Tool currentState;
	/**
	 * Reference to drawing model
	 */
	private DrawingModel drawingModel = new DrawingModelImpl();

	/**
	 * Action for opening new files
	 */
	private Action openFileAction;
	/**
	 * Action for saving modified files.
	 */
	private Action saveFileAction;
	/**
	 * Action for saving as modified files.
	 */
	private Action saveAsFileAction;
	/**
	 * Action for exiting application
	 */
	private Action exitAction;
	/**
	 * Action for export of drawn graphical objects
	 */
	private Action exportAction;

	/**
	 * Flag for tracking modifications on current document
	 */
	private boolean isModified = false;
	/**
	 * Path of currently opened file
	 */
	private Path filePath;

	/**
	 * Default constructor which sets up the frame specification and adds
	 * appropriate window listener along with drawing model listeners for tracking
	 * file modifications.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(200, 50);
		setTitle("JVDraw");
		setSize(900, 500);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkModifiedDocuments();
			}
		});

		drawingModel.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				isModified = true;
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				isModified = true;
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				isModified = true;
			}
		});

		setupActions();
		createActions();
		initGUI();
	}

	/**
	 * Auxiliary method which performs the initial setup of all actions used in this
	 * application.
	 */
	private void setupActions() {

		openFileAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				if (!fileName.toString().endsWith(".jvd")) {
					JOptionPane.showMessageDialog(JVDraw.this, "Chosen file must have .jvd extension.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				while (drawingModel.getSize() > 0) {
					drawingModel.remove(drawingModel.getObject(0));
				}

				filePath = fileName.toPath();
				try {
					List<String> lines = Files.readAllLines(filePath);
					if (!parseJvdFile(lines)) {
						JOptionPane.showMessageDialog(JVDraw.this, "Invalid data in .jvd file.", "Error",
								JOptionPane.ERROR_MESSAGE);

					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Something went wrong when reading given file.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				isModified = false;
			}

			/**
			 * Auxiliary method used for parsing .jvd file when opening documents through
			 * application.
			 * 
			 * @param lines
			 *            list of strings contained in file
			 * @return true if successful, false otherwise.
			 */
			private boolean parseJvdFile(List<String> lines) {

				for (String line : lines) {

					if (line.isEmpty())
						continue;

					String[] splitLine = line.trim().split("\\s+");
					if (line.toUpperCase().startsWith("LINE")) {
						if (splitLine.length != 8) {
							return false;
						}
						try {
							int x0 = Integer.parseInt(splitLine[1]);
							int y0 = Integer.parseInt(splitLine[2]);
							int x1 = Integer.parseInt(splitLine[3]);
							int y1 = Integer.parseInt(splitLine[4]);
							int r = Integer.parseInt(splitLine[5]);
							int g = Integer.parseInt(splitLine[6]);
							int b = Integer.parseInt(splitLine[7]);

							Color fgColor = new Color(r, g, b);
							drawingModel.add(new Line(x0, x1, y0, y1, fgColor));

						} catch (IllegalArgumentException e) {
							return false;
						}
					} else if (line.toUpperCase().startsWith("CIRCLE")) {
						if (splitLine.length != 7) {
							return false;
						}
						try {
							int centerx = Integer.parseInt(splitLine[1]);
							int centery = Integer.parseInt(splitLine[2]);
							int radius = Integer.parseInt(splitLine[3]);
							int r = Integer.parseInt(splitLine[4]);
							int g = Integer.parseInt(splitLine[5]);
							int b = Integer.parseInt(splitLine[6]);

							Color fgColor = new Color(r, g, b);
							drawingModel.add(new Circle(centerx, centery, radius, fgColor));

						} catch (IllegalArgumentException e) {
							return false;
						}
					} else if (line.toUpperCase().startsWith("FCIRCLE")) {
						if (splitLine.length != 10) {
							return false;
						}
						try {
							int centerx = Integer.parseInt(splitLine[1]);
							int centery = Integer.parseInt(splitLine[2]);
							int radius = Integer.parseInt(splitLine[3]);
							int r = Integer.parseInt(splitLine[4]);
							int g = Integer.parseInt(splitLine[5]);
							int b = Integer.parseInt(splitLine[6]);
							int r2 = Integer.parseInt(splitLine[7]);
							int g2 = Integer.parseInt(splitLine[8]);
							int b2 = Integer.parseInt(splitLine[9]);

							Color fgColor = new Color(r, g, b);
							Color bgColor = new Color(r2, g2, b2);
							drawingModel.add(new FilledCircle(centerx, centery, radius, fgColor, bgColor));

						} catch (IllegalArgumentException e) {
							return false;
						}
					} else if (line.toUpperCase().startsWith("FPOLY")) {

						try {

							List<Integer> xs = new ArrayList<>();
							List<Integer> ys = new ArrayList<>();

							for (int j = 2; j < splitLine.length - 6; j += 2) {
								xs.add(Integer.parseInt(splitLine[j]));
								ys.add(Integer.parseInt(splitLine[j + 1]));
							}
							int r = Integer.parseInt(splitLine[splitLine.length - 6]);
							int g = Integer.parseInt(splitLine[splitLine.length - 5]);
							int b = Integer.parseInt(splitLine[splitLine.length - 4]);
							int r2 = Integer.parseInt(splitLine[splitLine.length - 3]);
							int g2 = Integer.parseInt(splitLine[splitLine.length - 2]);
							int b2 = Integer.parseInt(splitLine[splitLine.length - 1]);

							int[] xArray = new int[xs.size()];
							int[] yArray = new int[ys.size()];

							for (int k = 0; k < xs.size(); k++) {
								xArray[k] = xs.get(k);
								yArray[k] = ys.get(k);
							}
							
							Color fgColor = new Color(r, g, b);
							Color bgColor = new Color(r2, g2, b2);
							drawingModel.add(new ConvexPolygon(xArray, yArray, fgColor, bgColor));

						} catch (IllegalArgumentException e) {
							return false;
						}
					}
				}
				return true;
			}
		};

		saveFileAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (filePath == null) {
					saveAsFileAction.actionPerformed(e);
					return;
				}
				if (!isModified) {
					JOptionPane.showMessageDialog(JVDraw.this, "No changes to save.", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				List<String> lines = toJvdLines();

				try {
					Files.write(filePath, lines, Charset.forName("UTF-8"));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Something went wrong when saving the file.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				isModified = false;
				JOptionPane.showMessageDialog(JVDraw.this, "Successfully saved file.", "Information",
						JOptionPane.INFORMATION_MESSAGE);

			}
		};

		saveAsFileAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Nothing was saved.", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path openedFilePath = jfc.getSelectedFile().toPath();
				String path = openedFilePath.toString();

				if (!path.endsWith(".jvd")) {
					path += ".jvd";
				}
				openedFilePath = Paths.get(path);

				List<String> lines = toJvdLines();

				try {
					Files.write(openedFilePath, lines, Charset.forName("UTF-8"));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Something went wrong when saving the file.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				isModified = false;
				filePath = openedFilePath;
				JOptionPane.showMessageDialog(JVDraw.this, "Successfully saved file.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		};

		exitAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				checkModifiedDocuments();
			}
		};

		exportAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
				jfc.setFileFilter(filter);

				jfc.setDialogTitle("Select document name");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Nothing was chosen", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path openedFilePath = jfc.getSelectedFile().toPath();
				String path = openedFilePath.toString();

				String extension;
				if (path.toUpperCase().endsWith("JPG")) {
					extension = "jpg";
				} else if (path.toUpperCase().endsWith("PNG")) {
					extension = "png";
				} else if (path.toUpperCase().endsWith("GIF")) {
					extension = "gif";
				} else {
					JOptionPane.showMessageDialog(JVDraw.this, "Invalid extension for export", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
				for (int i = 0; i < drawingModel.getSize(); i++) {
					drawingModel.getObject(i).accept(bbcalc);
				}
				Rectangle box = bbcalc.getBoundingBox();

				BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g = image.createGraphics();
				RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHints(rh);

				AffineTransform transform = new AffineTransform();
				transform.translate(-box.x, -box.y);
				g.setTransform(transform);
				GeometricalObjectPainter geometricalObjectPainter = new GeometricalObjectPainter(g);
				for (int i = 0; i < drawingModel.getSize(); i++) {
					drawingModel.getObject(i).accept(geometricalObjectPainter);
				}
				g.dispose();
				File file = openedFilePath.toFile();
				try {
					ImageIO.write(image, extension, file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(JVDraw.this, "Successful export", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;

			}
		};

	}

	/**
	 * Method called before exiting application. Checks if there are any modified
	 * and unsaved documents and asks the user if he wants to save or discard those
	 * changes.
	 */
	private void checkModifiedDocuments() {

		if (isModified) {
			int response = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save changes made to file?");

			if (response == 0) {
				saveFileAction.actionPerformed(null);
			} else if (response == 1) {
			} else {
				return;
			}
		}
		dispose();

	}

	/**
	 * Auxiliary method used for generating a list of strings to be written when
	 * saving jvd file. Iterates through all currently rendered objects and adds
	 * lines to collection which this application knows how to read (through
	 * openFileAction) after saving.
	 * 
	 * @return list of lines to be written
	 */
	public List<String> toJvdLines() {
		List<String> lines = new ArrayList<>();

		for (int i = 0; i < drawingModel.getSize(); i++) {
			GeometricalObject object = drawingModel.getObject(i);

			if (object instanceof Line) {
				Line line = (Line) object;
				lines.add("LINE " + line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2() + " "
						+ line.getFgColor().getRed() + " " + line.getFgColor().getGreen() + " "
						+ line.getFgColor().getBlue());
			} else if (object instanceof Circle) {
				Circle circle = (Circle) object;
				lines.add("CIRCLE " + circle.getX() + " " + circle.getY() + " " + circle.getR() + " "
						+ circle.getFgColor().getRed() + " " + circle.getFgColor().getGreen() + " "
						+ circle.getFgColor().getBlue());

			} else if (object instanceof FilledCircle) {
				FilledCircle circle = (FilledCircle) object;
				lines.add("FCIRCLE " + circle.getX() + " " + circle.getY() + " " + circle.getR() + " "
						+ circle.getFgColor().getRed() + " " + circle.getFgColor().getGreen() + " "
						+ circle.getFgColor().getBlue() + " " + circle.getBgColor().getRed() + " "
						+ circle.getBgColor().getGreen() + " " + circle.getBgColor().getBlue());
			} else if (object instanceof ConvexPolygon) {
				ConvexPolygon polygon = (ConvexPolygon) object;
				StringBuilder sb = new StringBuilder();

				sb.append("FPOLY ").append(polygon.getXs().length).append(" ");
				for (int j = 0; j < polygon.getXs().length; j++) {
					sb.append(polygon.getXs()[j]).append(" ").append(polygon.getYs()[j]).append(" ");
				}
				sb.append(polygon.getFgColor().getRed() + " " + polygon.getFgColor().getGreen() + " "
						+ polygon.getFgColor().getBlue() + " " + polygon.getBgColor().getRed() + " "
						+ polygon.getBgColor().getGreen() + " " + polygon.getBgColor().getBlue());
				lines.add(sb.toString());
			}
		}

		return lines;
	}

	/**
	 * Method which sets the graphical components of the application including
	 * toolbar, canvas and list. Adds the necessary listeners in order for the
	 * application to be fully wired and functional.
	 */
	private void initGUI() {

		createMenuBar();

		this.setLayout(new BorderLayout());
		JColorArea fgColorArea = new JColorArea(Color.RED);
		JColorArea bgColorArea = new JColorArea(Color.BLUE);

		CurrentColorsJLabel bottomColorInfo = new CurrentColorsJLabel(fgColorArea, bgColorArea);
		setupInitialColorsText(bottomColorInfo, fgColorArea, bgColorArea);
		bottomColorInfo.setHorizontalAlignment(SwingConstants.CENTER);
		bottomColorInfo.setFont(new Font("Helvetica", Font.PLAIN, 15));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		toolBar.add(fgColorArea);
		toolBar.addSeparator();
		toolBar.add(bgColorArea);
		toolBar.addSeparator(new Dimension(5, 0));

		JDrawingCanvas canvas = new JDrawingCanvas(drawingModel, this);
		currentState = new LineState(fgColorArea, drawingModel, canvas);

		canvas.addMouseListener(new StateMouseAdapter());
		canvas.addMouseMotionListener(new StateMouseMotionAdapter());

		ButtonGroup toolsGroup = new ButtonGroup();

		JToggleButton lineButton = new JToggleButton("Line");
		lineButton.addActionListener((e) -> currentState = new LineState(fgColorArea, drawingModel, canvas));
		lineButton.setSelected(true);
		JToggleButton circleButton = new JToggleButton("Circle");
		circleButton.addActionListener((e) -> currentState = new CircleState(fgColorArea, drawingModel, canvas));
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		filledCircleButton.addActionListener(
				(e) -> currentState = new FilledCircleState(fgColorArea, bgColorArea, drawingModel, canvas));
		JToggleButton convexPolygon = new JToggleButton("Convex polygon");
		convexPolygon.addActionListener(
				(e) -> currentState = new PolygonState(fgColorArea, bgColorArea, drawingModel, canvas));

		toolsGroup.add(lineButton);
		toolsGroup.add(circleButton);
		toolsGroup.add(filledCircleButton);
		toolsGroup.add(convexPolygon);

		toolBar.add(lineButton);
		toolBar.add(circleButton);
		toolBar.add(filledCircleButton);
		toolBar.add(convexPolygon);

		JList<GeometricalObject> geometricalObjects = new JList<>(new DrawingObjectListModel(drawingModel));
		JScrollPane pane = new JScrollPane(geometricalObjects);

		geometricalObjects.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2)
					return;

				int selectedIndex = geometricalObjects.getSelectedIndex();
				if (selectedIndex == -1)
					return;

				GeometricalObject object = drawingModel.getObject(selectedIndex);
				GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
				if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit coordinates and colors",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					try {
						editor.checkEditing();
						editor.acceptEditing();
					} catch (RuntimeException ex) {
						JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
					}
				}
			}
		});

		geometricalObjects.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int selectedIndex = geometricalObjects.getSelectedIndex();
				if (selectedIndex == -1)
					return;

				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					drawingModel.remove(drawingModel.getObject(selectedIndex));
				} else if (e.getKeyCode() == KeyEvent.VK_PLUS) {
					drawingModel.changeOrder(drawingModel.getObject(selectedIndex), 1);
				} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					drawingModel.changeOrder(drawingModel.getObject(selectedIndex), -1);
				}
			}
		});

		this.getContentPane().add(canvas, BorderLayout.CENTER);
		this.getContentPane().add(pane, BorderLayout.EAST);
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
		this.getContentPane().add(bottomColorInfo, BorderLayout.PAGE_END);
	}

	/**
	 * Method responsible for creating the menu bar and adding menus and menu items
	 * accordingly.
	 */
	private void createMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		menuBar.add(file);

		file.add(new JMenuItem(openFileAction));
		file.addSeparator();
		file.add(new JMenuItem(saveFileAction));
		file.add(new JMenuItem(saveAsFileAction));
		file.addSeparator();
		file.add(new JMenuItem(exportAction));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Auxiliary method used for defining names, descriptions, mnemonic and
	 * accelerator keys for defined actions.
	 */
	private void createActions() {

		openFileAction.putValue(Action.NAME, "Open");
		openFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("Control O"));
		openFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openFileAction.putValue(Action.SHORT_DESCRIPTION, "Open existing .jvd file");

		saveFileAction.putValue(Action.NAME, "Save");
		saveFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("Control S"));
		saveFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveFileAction.putValue(Action.SHORT_DESCRIPTION, "Save file");

		saveAsFileAction.putValue(Action.NAME, "Save as");
		saveAsFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("Control A"));
		saveAsFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsFileAction.putValue(Action.SHORT_DESCRIPTION, "Save as file");

		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("Control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Export image");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("Control W"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application");

	}

	/**
	 * Auxiliary method which performs the initial setup of bottomColorInfo JLabel
	 * text.
	 * 
	 * @param bottomColorInfo
	 *            jlabel object reference
	 * @param fgColorArea
	 *            foreground color area object
	 * @param bgColorArea
	 *            background color area object
	 */
	private void setupInitialColorsText(CurrentColorsJLabel bottomColorInfo, JColorArea fgColorArea,
			JColorArea bgColorArea) {
		Color bgColor = bgColorArea.getCurrentColor();
		Color fgColor = fgColorArea.getCurrentColor();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Foreground color: (")
				.append(fgColor.getRed() + ", " + fgColor.getGreen() + ", " + fgColor.getBlue() + "), ");
		stringBuilder.append("background color: (")
				.append(bgColor.getRed() + ", " + bgColor.getGreen() + ", " + bgColor.getBlue() + ").");

		bottomColorInfo.setText(stringBuilder.toString());
	}

	/**
	 * Getter for current state
	 * 
	 * @return {@link #currentState}
	 */
	public Tool getCurrentState() {
		return currentState;
	}

	/**
	 * Method which starts the execution of the program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JVDraw jvDraw = new JVDraw();
				jvDraw.setVisible(true);
			}
		});
	}

	/**
	 * Custom state mouse adapter which delegates the work to be done (in case of
	 * mouse action) to corresponding state based on current state object.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public class StateMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			currentState.mouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			currentState.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			currentState.mouseReleased(e);
		}
	}

	/**
	 * Custom state mouse adapter which delegates the work to be done (in case of
	 * mouse motion) to corresponding state based on current state object.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public class StateMouseMotionAdapter extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			currentState.mouseMoved(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			currentState.mouseDragged(e);
		}
	}
}
