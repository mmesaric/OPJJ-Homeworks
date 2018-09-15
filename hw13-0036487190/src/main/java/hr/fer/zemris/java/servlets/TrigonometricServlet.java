package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which is called with parameters a and b (if values aren't passed or
 * in case of invalid values, a is set to 0 and b to 360) and is responsible for
 * creating a list of trigonometric values for each number in given interval.
 * After values have been calculated, "trigonometric.jsp" prepares the data for
 * presentation
 * 
 * @author Marko Mesarić
 *
 */
public class TrigonometricServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int a;
		int b;

		String paramA = req.getParameter("a");
		String paramB = req.getParameter("b");

		paramA = paramA == null ? "0" : paramA;
		paramB = paramB == null ? "360" : paramB;

		double doubleA;
		double doubleB;
		try {
			doubleA = Double.parseDouble(paramA);
			doubleB = Double.parseDouble(paramB);

			if (doubleA == Math.floor(doubleA)) {
				a = (int) doubleA;
			} else {
				a = 0;
			}
			if (doubleB == Math.floor(doubleB)) {
				b = (int) doubleB;
			} else {
				b = 360;
			}
		} catch (NumberFormatException e) {
			a = 0;
			b = 360;
		}

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}

		if (b > (a + 720))
			b = a + 720;

		List<TrigonometricValues> tableValues = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			tableValues.add(new TrigonometricValues(i, Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}

		req.setAttribute("trigonometricValues", tableValues);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);

	}

	/**
	 * Class which models a single entry containing x, sin(x) and cos(x) values
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class TrigonometricValues {

		/**
		 * x value
		 */
		private int x;
		/**
		 * sinx value
		 */
		private double sinx;
		/**
		 * cosx value
		 */
		private double cosx;

		/**
		 * 
		 * @param x {@link #x}
		 * @param sinx {@link #sinx}
		 * @param cosx {@link #cosx}
		 */
		public TrigonometricValues(int x, double sinx, double cosx) {
			this.x = x;
			this.sinx = sinx;
			this.cosx = cosx;
		}

		/**
		 * Getter for x
		 * @return {@link #x}
		 */
		public int getX() {
			return x;
		}

		/**
		 * Getter for sin(x)
		 * @return {@link #sinx}
		 */
		public String getSinx() {
			return String.format("%.5f", sinx);
		}

		/**
		 * Getter for cos(x)
		 * @return {@link #cosx}
		 */
		public String getCosx() {
			return String.format("%.5f", cosx);
		}
	}

}
