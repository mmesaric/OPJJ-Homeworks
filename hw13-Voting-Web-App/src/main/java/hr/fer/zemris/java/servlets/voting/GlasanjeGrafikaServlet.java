package hr.fer.zemris.java.servlets.voting;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
 * bands and total number of votes given by users.
 * 
 * @author Marko Mesarić
 *
 */
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
		
		String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path votesFilePath = Paths.get(resultsFileName);
		Path bandsFilePath = Paths.get(bandsFileName); 
		
		resp.setContentType("image/png");
		ServletOutputStream os = resp.getOutputStream();
		
		DefaultPieDataset dataset = new DefaultPieDataset();
			
		if (!Files.exists(votesFilePath) ) {
			return;
		}
			 	
		else {
			List<String> votesLines = Files.readAllLines(votesFilePath);
			List<String> bandsLines = Files.readAllLines(bandsFilePath);
 
			for (String line : votesLines) {
				
				if (line == null || line.isEmpty()) continue;
				
				String[] splitLine =line.split("\t");
				
				if (splitLine.length!=2) continue;		
				int id = Integer.parseInt(splitLine[0]);
				int numberOfVotes = Integer.parseInt(splitLine[1]);
				String bandName = bandsLines.get(id-1).split("\t")[1];
				dataset.setValue(bandName, numberOfVotes);
			}
		}
		

		JFreeChart chart = ChartFactory.createPieChart("Voting results ", dataset, true, true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setCircular(true);
		plot.setStartAngle(270);
		plot.setForegroundAlpha(0.60f);
		plot.setInteriorGap(0.02);

		RenderedImage chartImage = chart.createBufferedImage(600, 600);
		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();

	}
}
