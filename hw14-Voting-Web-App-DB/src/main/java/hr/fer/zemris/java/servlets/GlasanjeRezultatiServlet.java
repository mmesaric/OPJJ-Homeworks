package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet, when called, prepares the necessary data which
 * "glasanjeRez.jsp" has to display to user. Retrieves the list of poll options
 * stored under pollID passed as parameter. Processes the list and sorts by
 * votes in descending order.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Default serial version ID
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

		List<PollOption> results = DAOProvider.getDao().getPollOptionsByID(longId);

		results.sort(
				Comparator.comparing(PollOption::getVotesCount, (a, b) -> Long.valueOf(b).compareTo(a.longValue())));

		List<PollOption> winnerEntries = new ArrayList<>();
		long maxVotes = results.get(0).getVotesCount();
		for (PollOption entry : results) {
			if (entry.getVotesCount() >= maxVotes) {
				winnerEntries.add(entry);
			}
		}

		req.setAttribute("results", results);
		req.setAttribute("winnerEntries", winnerEntries);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp?pollID=" + pollID).forward(req, resp);
	}
}
