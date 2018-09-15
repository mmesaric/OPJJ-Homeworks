package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet, which, when called retrieves the list of poll options for current
 * poll. Users can vote for poll option by clicking the link. Forwards the list
 * and poll information to glasanjeIndex.jsp
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("pollID");

		if (id == null) {
			req.setAttribute("message",
					"Can't take part in a poll who's ID doesn't exist. Pick the poll from list of valid polls listed in index.html");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		long longId;
		try {
			longId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid value for poll id.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		List<PollOption> entries = DAOProvider.getDao().getPollOptionsByID(longId);

		Poll poll = DAOProvider.getDao().getPollByID(longId);

		if (poll == null) {
			req.setAttribute("message", "No poll with given ID");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;

		}

		req.setAttribute("title", poll.getTitle());
		req.setAttribute("message", poll.getMessage());
		req.setAttribute("pollOptions", entries);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
