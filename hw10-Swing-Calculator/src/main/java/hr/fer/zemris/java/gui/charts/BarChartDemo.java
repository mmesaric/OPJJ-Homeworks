package hr.fer.zemris.java.gui.charts;

import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class used as demonstration for custom bar chart implementation created in
 * this homework assignment.
 * 
 * @author Marko Mesarić
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * Constant which defines the number of lines to be read from file.
	 */
	private static final int NUMBER_OF_LINES = 6;

	/**
	 * stores the created bar chart object
	 */
	private static BarChart barChart;

	/**
	 * Constructor which sets the frame properties.
	 * 
	 * @param path
	 *            path to file containing the bar chart properties
	 */
	public BarChartDemo(String path) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart demo");
		setLocation(0, 0);
		setSize(800, 500);
		initGUI(path);
	}

	/**
	 * Method for GUI initialization.
	 * 
	 * @param path
	 *            path to file containing the bar chart properties
	 */
	private void initGUI(String path) {
		Container p = getContentPane();
		p.add(new BarChartComponent(barChart, path));
	}

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method which starts the execution of a bar chart demo and parses the data
	 * from given text file. Path to file which content should be read is passed as
	 * first argument.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Pass only one argumet; path to file with bar chart specification.");
			System.exit(1);
		}

		List<String> lines = null;
		try {
			lines = readFromFile(Paths.get(args[0]), NUMBER_OF_LINES);
		} catch (IOException e) {
			System.err.println("File was not found or could not be read.");
			System.exit(1);
		}

		if (lines.size() < NUMBER_OF_LINES) {
			System.err.println("Invalid format for bart chart specification.");
			System.exit(1);
		}

		String xDescription = lines.get(0);
		String yDescription = lines.get(1);
		String xyValues = lines.get(2);
		String minY = lines.get(3);
		String maxY = lines.get(4);
		String deltaY = lines.get(5);
		List<XYValue> valuesList = new ArrayList<>();

		String[] values = xyValues.split("\\s+");
		for (String value : values) {

			String[] singleValue = value.split(",");
			if (singleValue.length != 2) {
				System.err.println("Invalid format for bart chart specification.");
				System.exit(1);
			}
			try {
				int x = Integer.parseInt(singleValue[0]);
				int y = Integer.parseInt(singleValue[1]);
				valuesList.add(new XYValue(x, y));
			} catch (NumberFormatException exc) {
				System.err.println("Invalid format for bart chart specification.");
				System.exit(1);
			}
		}

		try {
			int minYInt = Integer.parseInt(minY);
			int maxYInt = Integer.parseInt(maxY);
			int yDeltaInt = Integer.parseInt(deltaY);

			barChart = new BarChart(valuesList, xDescription, yDescription, minYInt, maxYInt, yDeltaInt);
		} catch (NumberFormatException exc) {
			System.err.println("Invalid format for bart chart specification.");
			System.exit(1);
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BarChartDemo demo = new BarChartDemo(args[0]);
				demo.setVisible(true);
			}
		});
	}

	/**
	 * Method used for reading first {@link #NUMBER_OF_LINES} lines from file.
	 * 
	 * @param path
	 *            path to file to be read
	 * @param numLines
	 *            number of lines to be read
	 * @return content of the file as list of strings
	 * @throws IOException
	 *             in case of error when reading the file.
	 */
	public static List<String> readFromFile(Path path, int numLines) throws IOException {
		try (Stream<String> lines = Files.lines(path)) {
			return lines.limit(numLines).collect(Collectors.toList());
		}
	}

}
