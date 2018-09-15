package hr.fer.zemris.java.galerija.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents the implementation of a servlet used for retrieving a
 * thumbnail from database stored by file name given as parameter and writing
 * the thumbnail to output stream. If thumbnail for given file name doesn't
 * exist, original image is retrieved, resized and saved to thumbnail directory
 * and then rendered to output stream.
 * 
 * @author Marko Mesarić
 *
 */
@WebServlet("/servlets/getThumbnail")
public class ThumbnailServlet extends HttpServlet {

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

		String path = req.getServletContext().getRealPath("/WEB-INF/thumbnails");

		if (!Paths.get(path).toFile().exists()) {
			new File(path).mkdirs();
		}

		Path filePath = Paths.get(path + "/" + fileName);
		BufferedImage thumbnail;

		if (filePath.toFile().exists()) {
			thumbnail = ImageIO.read(filePath.toFile());
		} else {
			thumbnail = resizeImage(req, fileName);
			ImageIO.write(thumbnail, "jpg", filePath.toFile());
		}

		resp.setContentType("image/jpeg");
		ServletOutputStream os = resp.getOutputStream();

		ImageIO.write(thumbnail, "jpg", os);
		os.close();
	}

	/**
	 * Auxiliary method used for resizing the image given by passed file name.
	 * 
	 * @param req
	 *            HTTP servlet request
	 * @param fileName
	 *            file name of image to be resized
	 * @return resized image
	 * @throws IOException
	 *             in case of exception
	 */
	private BufferedImage resizeImage(HttpServletRequest req, String fileName) throws IOException {

		Path path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + fileName));

		File originalImage = new File(path.toString());
		BufferedImage image = ImageIO.read(originalImage);

		BufferedImage resizedImage = new BufferedImage(150, 150, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, 150, 150, null);
		g.dispose();

		return resizedImage;
	}
}
