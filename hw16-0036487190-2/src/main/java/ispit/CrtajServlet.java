package ispit;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObjectPainter;

@WebServlet("/crtaj")
public class CrtajServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jvd = req.getParameter("jvd");
		
		
//		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
//		for (int i = 0; i < drawingModel.getSize(); i++) {
//			drawingModel.getObject(i).accept(bbcalc);
//		}
//		Rectangle box = bbcalc.getBoundingBox();
//
//		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
//		Graphics2D g = image.createGraphics();
//		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
//				RenderingHints.VALUE_ANTIALIAS_ON);
//		g.setRenderingHints(rh);

//		AffineTransform transform = new AffineTransform();
//		transform.translate(-box.x, -box.y);
//		g.setTransform(transform);
//		GeometricalObjectPainter geometricalObjectPainter = new GeometricalObjectPainter(g);
//		for (int i = 0; i < drawingModel.getSize(); i++) {
//			drawingModel.getObject(i).accept(geometricalObjectPainter);
//		}
//		g.dispose();
//		File file = openedFilePath.toFile();
//		try {
//			ImageIO.write(image, extension, file);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		JOptionPane.showMessageDialog(JVDraw.this, "Successful export", "Information",
//				JOptionPane.INFORMATION_MESSAGE);
//		return;


	}	
}
