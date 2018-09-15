package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which, when called, checks if any colors have been sent as parameter
 * "color". If no parameters are sent, white color is sent. Color is saved under
 * session attributes after performing necessary checks.
 * 
 * @author Marko Mesarić
 *
 */
public class ColorPickerServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String color = (String) req.getParameter("color");

		if (color == null) {
			color = "white";
		}

		req.getSession().setAttribute("pickedBgCol", color);

		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
