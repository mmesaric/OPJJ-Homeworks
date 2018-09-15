package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo program for testing graphic Fractal representation by using given
 * methods for details.
 * 
 * @author Marko Mesarić
 *
 */
public class Glavni1 {
	
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}
	
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.registerCommand('C', "color 00ced1")
		.setOrigin(0.05, 0.4)
//		.setOrigin(0.1, 0.0)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "CF+F--F+F")
		.setAxiom("F")
		.build();
		}


}
