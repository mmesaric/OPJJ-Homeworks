package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editor.ConvexPolygonEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;

public class ConvexPolygon extends GeometricalObject {

	private int[] xs;
	private int[] ys;

	private Color fgColor;
	private Color bgColor;

	public ConvexPolygon(int[] xs, int[] ys, Color fgColor, Color bgColor) {
		super();
		this.xs = xs;
		this.ys = ys;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}

	public int[] getXs() {
		return xs;
	}

	public void setXs(int[] xs) {
		this.xs = xs;
		notifyObservers();
	}

	public int[] getYs() {
		return ys;
	}

	public void setYs(int[] ys) {
		this.ys = ys;
		notifyObservers();
	}

	public Color getFgColor() {
		return fgColor;
	}

	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		notifyObservers();
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		notifyObservers();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new ConvexPolygonEditor();
	}

}
