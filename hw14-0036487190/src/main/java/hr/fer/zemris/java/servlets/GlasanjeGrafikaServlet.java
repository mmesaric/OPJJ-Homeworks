package hr.fer.zemris.java.servlets;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet which, when called, generates a Pie chart containing statistics about
 * current poll entries and total number of votes given by users.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * Default version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pollID = req.getParameter("pollID");

		if (pollID == null) {
			req.setAttribute("message", "You have to pass the Poll ID when asking for poll results");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		long longId;
		try {
			longId = Long.parseLong(pollID);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid value for poll id.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		DefaultPieDataset dataset = new DefaultPieDataset();

		List<PollOption> results = DAOProvider.getDao().getPollOptionsByID(longId);

		for (PollOption option : results) {
			dataset.setValue(option.getOptionTitle(), option.getVotesCount());
		}

		JFreeChart chart = ChartFactory.createPieChart("Rezultati glasanja", dataset, true, true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setCircular(true);
		plot.setStartAngle(270);
		plot.setForegroundAlpha(0.60f);
		plot.setInteriorGap(0.02);

		resp.setContentType("image/png");
		ServletOutputStream os = resp.getOutputStream();

		RenderedImage chartImage = chart.createBufferedImage(600, 600);
		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();

	}
}
