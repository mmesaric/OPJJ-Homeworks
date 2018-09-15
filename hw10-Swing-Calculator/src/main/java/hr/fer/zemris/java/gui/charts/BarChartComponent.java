package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Collections;

import javax.swing.JComponent;

/**
 * This class offers the implementation of a bar chart component. It extends the
 * JComponent class and overrides the {@link #paintComponents(Graphics)} method.
 * Component is drawn according to the assignment instructions.
 * 
 * @author Marko Mesarić
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant used for storing top insets
	 */
	private static final int TOP_INSET = 30;
	/**
	 * Constant used for storing right insets
	 */
	private static final int RIGHT_INSET = 20;

	/**
	 * Custom defined space between left side of the window and y axis description.
	 */
	private static final int LEFT_GAP = 25;
	/**
	 * Custom defined space between bottom side of the window and x axis
	 * description.
	 */
	private static final int BOTTOM_GAP = 25;

	/**
	 * reference to bar chart model which is to be displayed
	 */
	private BarChart barChart;

	/**
	 * path to file containing the bar chart properties
	 */
	private String path;

	/**
	 * Default constructor
	 * 
	 * 
	 * @param barChart
	 *            {@link #barChart}
	 * @param path
	 *            {@link #path}
	 */
	public BarChartComponent(BarChart barChart, String path) {
		super();
		Collections.sort(barChart.getValues());
		this.barChart = barChart;
		this.path = path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D graphics = initialGraphicsSetup(g);

		int heightWithoutInset = getSize().height - TOP_INSET;
		int widthWithoutInset = getSize().width - RIGHT_INSET;

		double stepCheck = (barChart.getMaxY() - barChart.getMinY()) / (barChart.getYDelta() * 1.0);
		int numberOfRows = Double.valueOf(Math.ceil(stepCheck)).intValue();
		int numberOfColumns = barChart.getValues().size();

		int startX = LEFT_GAP * 2;
		int startY = BOTTOM_GAP * 2;

		int chartWidth = (widthWithoutInset - (startX + RIGHT_INSET)) / numberOfColumns;
		int chartHeight = (heightWithoutInset - (startY + TOP_INSET)) / numberOfRows;

		drawHorizontalLines(graphics, startX, startY, numberOfRows, numberOfColumns, heightWithoutInset, chartWidth,
				chartHeight);
		drawVerticalLines(graphics, startX, startY, numberOfColumns, heightWithoutInset, chartWidth, numberOfRows,
				chartHeight);

		int x = startX + 1;
		for (int i = 0; i < numberOfColumns; i++) {

			int yValue = barChart.getValues().get(i).getY();

			if (yValue < barChart.getMinY()) {
				yValue = barChart.getMinY();
			} else if (yValue > barChart.getMaxY()) {
				yValue = barChart.getMaxY();
			}

			int height = (yValue - barChart.getMinY()) / barChart.getYDelta() * chartHeight;
			int y = heightWithoutInset - height - startY;
			graphics.setColor(new Color(255, 127, 80));
			graphics.fillRect(x, y, chartWidth, height);

			graphics.setColor(new Color(169, 169, 169));
			graphics.drawLine(x, y, x + chartWidth, y);
			graphics.drawLine(x + chartWidth - 1, y, x + chartWidth - 1, heightWithoutInset - startY);

			x += chartWidth;
		}

		drawAxisDescriptions(graphics);
	}

	/**
	 * Auxiliary method used for drawing horizontal grid lines.
	 * 
	 * @param graphics
	 *            graphics object
	 * @param startX
	 *            starting x coordinate
	 * @param startY
	 *            starting y coordinate
	 * @param numberOfRows
	 *            number of rows
	 * @param numberOfColumns
	 *            number of columns
	 * @param heightWithoutInset
	 *            available height
	 * @param chartWidth
	 *            column width
	 * @param chartHeight
	 *            row height
	 */
	private void drawHorizontalLines(Graphics2D graphics, int startX, int startY, int numberOfRows, int numberOfColumns,
			int heightWithoutInset, int chartWidth, int chartHeight) {

		int minValue = barChart.getMinY();
		int deltaY = barChart.getYDelta();

		int maxHorizontal = startX + (numberOfColumns * chartWidth);
		int y = 0;

		for (int i = heightWithoutInset - startY; y <= numberOfRows; i -= chartHeight) {

			graphics.setColor(new Color(169, 169, 169));
			graphics.drawLine(startX - 5, i, startX, i);

			graphics.setColor(new Color(169, 169, 169));
			graphics.drawLine(startX, i, maxHorizontal + 5, i);

			if (i == heightWithoutInset - startY) {

				int xAxisTriangleStart = maxHorizontal+10;
				int[] xArray = { xAxisTriangleStart-5, xAxisTriangleStart-5, xAxisTriangleStart+3};
				int[] yArray = { heightWithoutInset-startY-3, heightWithoutInset -startY+3, heightWithoutInset-startY};
				graphics.fillPolygon(xArray, yArray, 3);
			}

			String number = String.valueOf(minValue * deltaY);
			minValue++;
			graphics.setColor(Color.BLACK);
			graphics.setFont(new Font("Arial", Font.BOLD, 13));
			graphics.drawString(number, startX - 5 - startX / 3, i + 12);

			y++;
		}
	}

	/**
	 * Auxiliary method used for drawing vertical grid lines.
	 * 
	 * @param graphics
	 *            graphics object
	 * @param startX
	 *            starting x coordinate
	 * @param startY
	 *            starting y coordinate
	 * @param numberOfColumns
	 *            number of columns
	 * @param heightWithoutInset
	 *            available height
	 * @param chartWidth
	 *            column width
	 * @param numberOfRows
	 *            number of rows
	 * @param chartHeight
	 *            row height
	 * 
	 */
	private void drawVerticalLines(Graphics2D graphics, int startX, int startY, int numberOfColumns,
			int heightWithoutInset, int chartWidth, int numberOfRows, int chartHeight) {

		int maxVertical = heightWithoutInset - numberOfRows * chartHeight - startY;
		int x = 0;

		for (int i = startX; x <= numberOfColumns; i += chartWidth) {

			graphics.setColor(new Color(169, 169, 169));
			graphics.drawLine(i, heightWithoutInset - startY, i, heightWithoutInset - startY + 5);

			graphics.setColor(new Color(169, 169, 169));
			graphics.drawLine(i, maxVertical - 5, i, heightWithoutInset - startY);

			graphics.setFont(new Font("Arial", Font.BOLD, 13));
			graphics.setColor(Color.BLACK);

			if (i == startX) {

				graphics.setColor(new Color(169, 169, 169));
				int yAxisTriangleStart = maxVertical - 3;

				int[] xArray = { i - 3, i + 3, i };
				int[] yArray = { yAxisTriangleStart, yAxisTriangleStart, yAxisTriangleStart - 8 };
				graphics.fillPolygon(xArray, yArray, xArray.length);

				graphics.setFont(new Font("Arial", Font.BOLD, 13));
				graphics.setColor(Color.BLACK);
			}

			if (x < numberOfColumns) {
				String number = String.valueOf(barChart.getValues().get(x).getX());
				int lineX = i + (chartWidth / 2) - (number.length() / 2);
				int lineY = heightWithoutInset - 2 * (startY / 3);
				graphics.drawString(number, lineX, lineY);
			}

			x++;
		}
	}

	/**
	 * Method used for drawing axis descriptions
	 * 
	 * @param graphics
	 *            graphics object
	 */
	private void drawAxisDescriptions(Graphics2D graphics) {

		graphics.setColor(Color.black);
		graphics.setFont(new Font("Arial", Font.ITALIC, 15));
		FontMetrics fm = graphics.getFontMetrics();
		int xWidth = fm.stringWidth(barChart.getxDescription());
		int pathTextWidth = fm.stringWidth(path);
		int yWidth = fm.stringWidth(barChart.getyDescription());

		graphics.drawString(barChart.getxDescription(), getWidth() / 2 - (xWidth / 2), getSize().height - 10);
		graphics.drawString(path, getWidth() / 2 - (pathTextWidth / 2), TOP_INSET - 10);

		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		graphics.setTransform(at);

		int x = -((getSize().height - TOP_INSET) / 2 + (yWidth / 2));
		int y = LEFT_GAP;
		graphics.drawString(barChart.getyDescription(), x, y);

	}

	/**
	 * Method used for initial graphics object setup.
	 * 
	 * @param g
	 *            graphics object
	 * @return customized graphics2d object
	 */
	public Graphics2D initialGraphicsSetup(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getSize().width, getSize().height);

		return graphics;
	}
}
