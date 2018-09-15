package hr.fer.zemris.java.servlets;

import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet which, when called, generates a Pie chart containing statistics about
 * OS usage.
 * 
 * @author Marko Mesarić
 *
 */
public class ReportImageServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");
		ServletOutputStream os = resp.getOutputStream();

		DefaultPieDataset dataset = new DefaultPieDataset();

		dataset.setValue("Windows", Double.valueOf(71.8));
		dataset.setValue("Macintosh", Double.valueOf(12.1));
		dataset.setValue("iOS", Double.valueOf(6.3));
		dataset.setValue("Android", Double.valueOf(5.8));
		dataset.setValue("Linux", Double.valueOf(2.7));
		dataset.setValue("Other", Double.valueOf(1.3));

		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, true, true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setCircular(true);

		RenderedImage chartImage = chart.createBufferedImage(600, 600);
		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();
	}

}
