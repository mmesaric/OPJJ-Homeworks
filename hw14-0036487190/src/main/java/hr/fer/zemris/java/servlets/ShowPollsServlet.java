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

/**
 * Servlet used for displaying the list of currently active polls.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/index.html")
public class ShowPollsServlet extends HttpServlet {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Poll> polls = DAOProvider.getDao().getAllPolls();

		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/activePolls.jsp").forward(req, resp);
	}
}
