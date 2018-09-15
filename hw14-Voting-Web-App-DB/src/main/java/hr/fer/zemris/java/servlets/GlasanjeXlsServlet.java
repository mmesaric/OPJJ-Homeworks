package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet which is responsible for creating and returning an XLS table which
 * contains statistics about poll title and total number of votes each poll
 * option got.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/glasanje-xls")
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

		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Voting results");

		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Option title");
		rowhead.createCell(1).setCellValue("Number of votes");

		List<PollOption> results = DAOProvider.getDao().getPollOptionsByID(longId);

		results.sort(
				Comparator.comparing(PollOption::getVotesCount, (a, b) -> Long.valueOf(b).compareTo(a.longValue())));

		int i = 1;
		for (PollOption option : results) {

			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(option.getOptionTitle());
			row.createCell(1).setCellValue(option.getVotesCount());
			i++;
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=voting-results.xls");

		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
