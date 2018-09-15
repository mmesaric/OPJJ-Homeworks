package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents the implementation of sum worker. Based on given
 * parameters, values for a and b that is, performs the addition of those
 * arguments and prints the content in html form as a table.
 * 
 * @author Marko Mesarić
 *
 */
public class SumWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		int a;
		int b;

		String paramA = context.getParameter("a");
		String paramB = context.getParameter("b");

		paramA = paramA == null ? "1" : paramA;
		paramB = paramB == null ? "2" : paramB;

		double doubleA;
		double doubleB;
		try {
			doubleA = Double.parseDouble(paramA);
			doubleB = Double.parseDouble(paramB);

			if (doubleA == Math.floor(doubleA)) {
				a = (int) doubleA;
			} else {
				a = 1;
			}
			if (doubleB == Math.floor(doubleB)) {
				b = (int) doubleB;
			} else {
				b = 2;
			}
		} catch (NumberFormatException e) {
			a = 1;
			b = 2;
		}

		String sum = String.valueOf(a + b);

		context.setTemporaryParameter("a", String.valueOf(a));
		context.setTemporaryParameter("b", String.valueOf(b));
		context.setTemporaryParameter("zbroj", sum);

		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

}
