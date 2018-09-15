package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo program for testing graphic Fractal representation with default values
 * since we're not using any methods for LSystem configuration.
 * 
 * @author Marko Mesarić
 *
 */
public class Glavni3 {

	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
