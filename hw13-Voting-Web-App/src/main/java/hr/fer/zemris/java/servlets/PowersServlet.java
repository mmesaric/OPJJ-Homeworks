package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet which is called with parameters a,b and n (all mandatory) and is
 * responsible for creating and returning an XLS table which contains n number
 * of sheets with numbers in interval [a,b] and their corresponding powers.
 * 
 * @author Marko Mesarić
 *
 */
public class PowersServlet extends HttpServlet {

	/**
	 * Default servial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");

		if (a == null || b == null || n == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		int aInt;
		int bInt;
		int nInt;

		try {
			aInt = Integer.parseInt(a);
			bInt = Integer.parseInt(b);
			nInt = Integer.parseInt(n);
		} catch (NumberFormatException ex) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if ((aInt < -100 || aInt > 100) || (bInt < -100 || bInt > 100) || (nInt < 1 || nInt > 5)) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (aInt > bInt) {
			int temp = aInt;
			aInt = bInt;
			bInt = temp;
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=powers-table.xls");

		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= nInt; i++) {

			HSSFSheet sheet = hwb.createSheet("Powers ^(" + i + ")");

			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Number");
			rowhead.createCell(1).setCellValue("Power ^(" + i + ")");

			int k = 1;
			for (int j = aInt; j <= bInt; j++) {
				HSSFRow row = sheet.createRow(k);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
				k++;
			}
		}

		hwb.write(resp.getOutputStream());
		hwb.close();
	}

}
