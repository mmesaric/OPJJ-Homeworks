package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet which is responsible for creating and returning an XLS table which
 * contains statistics about bands and total number of votes each band got.
 * 
 * @author Marko Mesarić
 *
 */
public class GlasanjeXlsServlet extends HttpServlet {

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

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=voting-results.xls");

		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Voting results");

		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Band name");
		rowhead.createCell(1).setCellValue("Number of votes");

		if (!Files.exists(votesFilePath)) {

			List<String> bandsLines = Files.readAllLines(bandsFilePath);
			int i = 0;
			for (String line : bandsLines) {
				if (line == null || line.isEmpty())
					continue;
				String[] splitLine = line.split("\t");
				String bandName = splitLine[1];

				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(bandName);
				row.createCell(1).setCellValue("0");
				i++;
			}

		}

		else {
			List<String> votesLines = Files.readAllLines(votesFilePath);
			List<String> bandsLines = Files.readAllLines(bandsFilePath);

			int i = 0;
			for (String line : votesLines) {

				if (line == null || line.isEmpty())
					continue;

				String[] splitLine = line.split("\t");

				if (splitLine.length != 2)
					continue;
				int id = Integer.parseInt(splitLine[0]);
				int numberOfVotes = Integer.parseInt(splitLine[1]);
				String bandName = bandsLines.get(id - 1).split("\t")[1];

				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(bandName);
				row.createCell(1).setCellValue(numberOfVotes);
				i++;
			}
		}

		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
