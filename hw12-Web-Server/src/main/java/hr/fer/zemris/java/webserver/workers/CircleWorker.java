package hr.fer.zemris.java.webserver.workers;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents the implementation of a Worker which draws a circle of
 * 200x200 dimensions.
 * 
 * @author Marko Mesarić
 *
 */
public class CircleWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		context.setMimeType("image/png");

		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double r = bim.getHeight() / 2;

		Shape theCircle = new Ellipse2D.Double(bim.getMinX(), bim.getMinY(), 2.0 * r - 1, 2.0 * r - 1);
		g2d.draw(theCircle);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
