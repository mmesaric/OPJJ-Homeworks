package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This servlet is responsible for preparing the data for 3 different pages.
 * /servleti/author/{NICK} (used for viewing all blog entries by user with given
 * nick), /servleti/author/new or /servleti/author/{NICK}/edit/{id}, used for
 * adding new blog entries and editing the existing and
 * /servleti/author/{NICK}/{id} used for reading the blog entry content and
 * commenting
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet(urlPatterns = "/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * Id regex used for id validation
	 */
	private final String idRegex = "^[0-9]+";

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo().substring(1);
		String[] splitPath = path.split("/");

		if (splitPath.length == 1) {
			String nick = splitPath[0];

			BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
			if (user == null) {
				req.setAttribute("message", "User with given nick doesn't exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			req.setAttribute("nick", nick);
			req.setAttribute("blogEntries", user.getBlogEntries().isEmpty() ? null : user.getBlogEntries());
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntries.jsp").forward(req, resp);

		} else if (splitPath.length == 2) {

			String nick = splitPath[0];
			req.setAttribute("nick", nick);

			BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
			if (user == null) {
				req.setAttribute("message", "User with given nick doesn't exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}

			if (splitPath[1].equals("new")) {

				String loggedInNick = (String) req.getSession().getAttribute("current.user.nick");

				if (loggedInNick == null) {
					req.setAttribute("message", "You need to login in order to add new blog entries.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				if (!loggedInNick.equals(nick)) {
					req.setAttribute("message", "You are currently logged in as " + loggedInNick + ". Sign in as "
							+ nick + " to add new blog entry.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				req.getRequestDispatcher("/WEB-INF/pages/EditAddEntry.jsp").forward(req, resp);

			} else if (splitPath[1].matches(idRegex)) {

				Long eid = Long.parseLong(splitPath[1]);

				BlogEntry entry = DAOProvider.getDAO().getEntryByID(eid);

				if (entry == null) {
					req.setAttribute("message", "Blog entry with given id doesn't exist.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				req.setAttribute("comments", entry.getComments().isEmpty() ? null : entry.getComments());
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/BlogEntryContent.jsp").forward(req, resp);

			} else {
				req.setAttribute("message", "Invalid url.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		} else if (splitPath.length == 3) {

			String nick = splitPath[0];
			req.setAttribute("nick", nick);

			BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
			if (user == null) {
				req.setAttribute("message", "User with given nick doesn't exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}

			if (splitPath[1].toLowerCase().equals("edit")) {
				String blogID = splitPath[2];

				Long blogIDLong;
				try {
					blogIDLong = Long.parseLong(blogID);
				} catch (NumberFormatException e) {
					req.setAttribute("message", "Given blog entry ID must be a Long" + nick);
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				boolean contains = false;
				for (BlogEntry entry : user.getBlogEntries()) {
					if (entry.getId().equals(blogIDLong)) {
						contains = true;
						break;
					}
				}

				if (!contains) {
					req.setAttribute("message",
							"Given blog entry ID doesn't exist or that entry doesn't belong to author " + nick);
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;

				}

				String loggedInNick = (String) req.getSession().getAttribute("current.user.nick");

				if (loggedInNick == null) {
					req.setAttribute("message", "You need to login in order to add new blog entries.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				if (!loggedInNick.equals(nick)) {
					req.setAttribute("message", "You are currently logged in as " + loggedInNick + ". Sign in as "
							+ nick + " to edit given blog entry.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(blogIDLong);
				req.setAttribute("title", entry.getTitle());
				req.setAttribute("text", entry.getText());
				req.setAttribute("entryID", blogIDLong);
				req.getRequestDispatcher("/WEB-INF/pages/EditAddEntry.jsp").forward(req, resp);

			} else {
				req.setAttribute("message", "Invalid url.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		} else {
			req.setAttribute("message", "Invalid url.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String title = req.getParameter("title");
		String text = req.getParameter("text");

		String path = req.getPathInfo().substring(1);
		String[] splitPath = path.split("/");

		if (splitPath.length == 1) {

		} else if (splitPath.length == 2) {

			String nick = splitPath[0];
			req.setAttribute("nick", nick);

			BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
			if (user == null) {
				req.setAttribute("message", "User with given nick doesn't exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}

			if (splitPath[1].equals("new")) {

				String loggedInNick = (String) req.getSession().getAttribute("current.user.nick");

				if (loggedInNick == null) {
					req.setAttribute("message", "You need to login in order to add new blog entries.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				if (!loggedInNick.equals(nick)) {
					req.setAttribute("message", "You are currently logged in as " + loggedInNick + ". Sign in as "
							+ nick + " to add new blog entry.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				BlogEntry blogEntry = new BlogEntry();
				blogEntry.setCreatedAt(new Date());
				blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
				blogEntry.setTitle(title);
				blogEntry.setText(text);
				blogEntry.setCreator(DAOProvider.getDAO().getUserByNick(loggedInNick));

				DAOProvider.getDAO().createNewEntry(blogEntry);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
				return;
			} else if (splitPath[1].matches(idRegex)) {

				Long eid = Long.parseLong(splitPath[1]);

				BlogEntry entry = DAOProvider.getDAO().getEntryByID(eid);

				if (entry == null) {
					req.setAttribute("message", "Blog entry with given id doesn't exist.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				String email = req.getParameter("email");

				BlogComment comment = new BlogComment();
				comment.setBlogEntry(entry);
				comment.setMessage(text);
				comment.setUsersEMail(email);
				comment.setPostedOn(new Date());

				DAOProvider.getDAO().addComment(comment);
				entry.getComments().add(comment);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick + "/" + eid);

			} else {
				req.setAttribute("message", "Invalid url.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}

		} else if (splitPath.length == 3) {

			String nick = splitPath[0];
			req.setAttribute("nick", nick);

			BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
			if (user == null) {
				req.setAttribute("message", "User with given nick doesn't exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}

			if (splitPath[1].toLowerCase().equals("edit")) {
				String blogID = splitPath[2];

				Long blogIDLong;
				try {
					blogIDLong = Long.parseLong(blogID);
				} catch (NumberFormatException e) {
					req.setAttribute("message", "Given blog entry ID must be a Long" + nick);
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				boolean contains = false;
				for (BlogEntry entry : user.getBlogEntries()) {
					if (entry.getId().equals(blogIDLong)) {
						contains = true;
						break;
					}
				}

				if (!contains) {
					req.setAttribute("message",
							"Given blog entry ID doesn't exist or that entry doesn't belong to author " + nick);
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;

				}

				String loggedInNick = (String) req.getSession().getAttribute("current.user.nick");

				if (loggedInNick == null) {
					req.setAttribute("message", "You need to login in order to add new blog entries.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				if (!loggedInNick.equals(nick)) {
					req.setAttribute("message", "You are currently logged in as " + loggedInNick + ". Sign in as "
							+ nick + " to edit given blog entry.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}

				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(blogIDLong);

				blogEntry.setLastModifiedAt(new Date());
				blogEntry.setTitle(title);
				blogEntry.setText(text);

				// DAOProvider.getDAO().updateEntry(blogEntry);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
				return;

			} else {
				req.setAttribute("message", "Invalid url.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		} else {
			req.setAttribute("message", "Invalid url.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
	}
}
