package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This servlet offers the implementation of user registration. Only registered
 * users can create blog entries and edit their own entries.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * User name regular expression
	 */
	private final String nameRegex = "^[a-zA-Z\\s]+";
	/**
	 * User nick regular expression
	 */
	private final String nickRegex = "^[a-zA-Z0-9_]*$";

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");

		if (!validParameters(req, firstName, lastName, email, nick, password)) {
			req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
			return;
		}

		String ep = Utils.getHex(password);

		BlogUser user = new BlogUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);
		user.setPasswordHash(ep);

		DAOProvider.getDAO().createNewUser(user);

		// req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
		resp.sendRedirect(req.getContextPath() + "/servleti/main");

	}

	/**
	 * Auxiliary method used for validating the given parameters.
	 * 
	 * @param req
	 *            http request
	 * @param firstName
	 *            given first name
	 * @param lastName
	 *            given last name
	 * @param email
	 *            given email
	 * @param nick
	 *            given nick
	 * @param password
	 *            given password
	 * @return true if valid, false otherwise
	 */
	private boolean validParameters(HttpServletRequest req, String firstName, String lastName, String email,
			String nick, String password) {

		boolean valid = true;

		if (!firstName.matches(nameRegex)) {
			req.setAttribute("fnMessage", "First name can contain only alphabets.");
			valid = false;
		}
		if (!lastName.matches(nameRegex)) {
			req.setAttribute("lnMessage", "Last name can contain only alphabets.");
			valid = false;
		}
		if (!nick.matches(nickRegex)) {
			req.setAttribute("nickMessage", "Nick can contain only digits and alphabets");
			valid = false;
		}
		BlogUser blogUser = DAOProvider.getDAO().getUserByNick(nick);
		if (blogUser != null) {
			req.setAttribute("nickMessage", "User with given nick already exists.");
			valid = false;
		}

		return valid;
	}
}
