package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet responsible for processing data passed from login form and preparing the
 * list of all blog users.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> authors = DAOProvider.getDAO().getRegisteredAuthors();
		if (authors != null) {
			req.setAttribute("authors", authors);
		}
		req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String nick = req.getParameter("nick");
		String password = req.getParameter("password");

		if (nick == null || password == null) {
			req.setAttribute("message", "You have to provide nick and password.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		String ep = Utils.getHex(password);

		List<BlogUser> authors = DAOProvider.getDAO().getRegisteredAuthors();
		if (authors != null) {
			req.setAttribute("authors", authors);
		}

		BlogUser blogUser = DAOProvider.getDAO().getUserByNick(nick);
		if (blogUser == null) {

			req.setAttribute("nickMessage", "User with given nick doesn't exist.");
			req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
			return;
		}

		if (!ep.equals(blogUser.getPasswordHash())) {
			req.setAttribute("passMessage", "Given password does not match the password of user: " + nick);
			req.setAttribute("nickInput", nick);
			req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("current.user.id", blogUser.getId());
		req.getSession().setAttribute("current.user.fn", blogUser.getFirstName());
		req.getSession().setAttribute("current.user.ln", blogUser.getLastName());
		req.getSession().setAttribute("current.user.nick", blogUser.getNick());

		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}
