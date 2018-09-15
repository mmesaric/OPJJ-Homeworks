package hr.fer.zemris.java.hw16.jvdraw.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.ConvexPolygon;

public class PolygonState implements Tool {

	private IColorProvider bgColorProvider;

	private IColorProvider fgColorProvider;

	private DrawingModel drawingModel;

	private JDrawingCanvas canvas;
	
	private JFrame thisFrame;

	private Point end;

	private List<Integer> xs = new ArrayList<>();
	private List<Integer> ys = new ArrayList<>();

	public PolygonState(IColorProvider bgColorProvider, IColorProvider fgColorProvider, DrawingModel drawingModel,
			JDrawingCanvas canvas) {
		super();
		this.bgColorProvider = bgColorProvider;
		this.fgColorProvider = fgColorProvider;
		this.drawingModel = drawingModel;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (SwingUtilities.isRightMouseButton(e)) {
			return;
		}
		Point end = e.getPoint();

		if (xs.size() < 3) {

			if (xs.size() == 2) {

				boolean check = false;

				for (int i = 0; i < xs.size(); i++) {
					int distance = (int) end.distance(new Point(xs.get(xs.size() - 1), ys.get(ys.size() - 1)));
					if (distance <= 3) {

						check = true;

						xs.add((int) end.getX());
						ys.add((int) end.getY());

						int[] xArray = new int[xs.size()];
						int[] yArray = new int[ys.size()];

						for (int j = 0; j < xs.size(); j++) {
							xArray[j] = xs.get(j);
							yArray[j] = ys.get(j);
						}

						drawingModel.add(new ConvexPolygon(xArray, yArray, fgColorProvider.getCurrentColor(),
								bgColorProvider.getCurrentColor()));
						xs = new ArrayList<>();
						ys = new ArrayList<>();
						break;
					}
				}

				if (!check) {
					xs.add((int) end.getX());
					ys.add((int) end.getY());
				}
			}

			else {
				xs.add((int) end.getX());
				ys.add((int) end.getY());
			}
		} else {
			
			xs.add((int) end.getX());
			ys.add((int) end.getY());
			boolean sign = false;
			boolean convex = true;
			int n = xs.size();

			for (int i = 0; i < xs.size(); i++) {
				
		        double dx1 = xs.get((i + 2) % n) - xs.get((i + 1) % n);
		        double dy1 = ys.get((i + 2) % n) - ys.get((i + 1) % n);
		        double dx2 = xs.get(i) - xs.get((i + 1) % n);
		        double dy2 = ys.get(i) - ys.get((i + 1) % n);
		        double crossProd = dx1 * dy2 - dy1 * dx2;
		        
		        if (i == 0)
		            sign = crossProd > 0;
		        else if (sign != (crossProd > 0)) {
		            convex = false;
		            break;
		        }     
			}
			
			if (!convex) {
				xs.remove(xs.size()-1);
				ys.remove(ys.size()-1);
				
				JOptionPane.showMessageDialog(canvas, "Adding this point makes polygon non convex!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				xs.remove(xs.size()-1);
				ys.remove(ys.size()-1);

				boolean check = false;	
				for (int i = 0; i < xs.size(); i++) {
					int distance = (int) end.distance(new Point(xs.get(xs.size() - 1), ys.get(ys.size() - 1)));
					if (distance <= 3) {

						xs.add((int) end.getX());
						ys.add((int) end.getY());
						check = true;

						int[] xArray = new int[xs.size()];
						int[] yArray = new int[ys.size()];

						for (int j = 0; j < xs.size(); j++) {
							xArray[j] = xs.get(j);
							yArray[j] = ys.get(j);
						}

						drawingModel.add(new ConvexPolygon(xArray, yArray, fgColorProvider.getCurrentColor(),
								bgColorProvider.getCurrentColor()));
						xs = new ArrayList<>();
						ys = new ArrayList<>();
						break;
					}
				}
				if (!check) {
					xs.add((int) end.getX());
					ys.add((int) end.getY());
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (xs.size() > 0) {
			end = e.getPoint();
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void paint(Graphics2D g2d) {

		if (xs.size() > 0) {
			g2d.setColor(fgColorProvider.getCurrentColor());

			List<Integer> pomXs = new ArrayList<>(xs);
			List<Integer> pomYs = new ArrayList<>(ys);

			pomXs.add((int) end.getX());
			pomYs.add((int) end.getY());

			int[] xArray = new int[pomXs.size()];
			int[] yArray = new int[pomYs.size()];

			for (int j = 0; j < pomXs.size(); j++) {
				xArray[j] = pomXs.get(j);
				yArray[j] = pomYs.get(j);
			}

			Polygon polygon = new Polygon(xArray, yArray, xArray.length);

			g2d.setColor(bgColorProvider.getCurrentColor());

			g2d.drawPolygon(polygon);
			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.fill(polygon);
		}
	}
}
