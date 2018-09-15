package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * This servlet is called with a single parameter, id of the band which got the
 * up-vote. Increases the number of total votes of poll option stored with that
 * ID and redirects the user to voting results page.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String optionID = req.getParameter("id");

		if (optionID == null) {
			req.setAttribute("message", "Poll option with given ID doesn't exist.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		long longId;
		try {
			longId = Long.parseLong(optionID);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid value for poll id.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		DAOProvider.getDao().updateVotesCount(longId);

		long pollID = DAOProvider.getDao().getPollIDByOptionID(longId);

		if (pollID == 0) {
			req.setAttribute("message", "No polls exist for given poll option");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);

	}
}
