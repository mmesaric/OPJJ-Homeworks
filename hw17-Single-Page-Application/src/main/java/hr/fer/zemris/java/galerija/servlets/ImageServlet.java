package hr.fer.zemris.java.galerija.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents the implementation of a servlet used for retrieving an
 * image from database stored by file name given as parameter and writing the
 * image to servlet output stream.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servlets/getImage")
public class ImageServlet extends HttpServlet {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getParameter("fileName");
		if (fileName == null) {
			resp.sendError(404);
			return;
		}

		String path = req.getServletContext().getRealPath("/WEB-INF/slike/" + fileName);
		BufferedImage image = ImageIO.read(new File(path));

		resp.setContentType("image/jpeg");
		ServletOutputStream os = resp.getOutputStream();

		ImageIO.write(image, "jpg", os);
		os.close();

	}
}
