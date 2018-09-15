package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector2DTest {

	@Test
	public void testRotateFirstQuadrant() {

		Vector2D vector2d = new Vector2D(3, 4);

		vector2d.rotate(50);

		Assert.assertEquals(-1.135814943, vector2d.getX(), 10E-6);
		Assert.assertEquals(4.869283, vector2d.getY(), 10E-6);
	}

	@Test
	public void testRotateFirstQuadrantTwoPI() {

		Vector2D vector2d = new Vector2D(3, 4);

		vector2d.rotate(360);

		Assert.assertEquals(3, vector2d.getX(), 10E-6);
		Assert.assertEquals(4, vector2d.getY(), 10E-6);
	}

	@Test
	public void testRotateSecondQuadrant() {

		Vector2D vector2d = new Vector2D(-3, 4);

		vector2d.rotate(50);

		Assert.assertEquals(-4.99254060153553, vector2d.getX(), 10E-6);
		Assert.assertEquals(0.2730171093892226, vector2d.getY(), 10E-6);
	}

	@Test
	public void testRotateSecondQuadrantTwoPi() {

		Vector2D vector2d = new Vector2D(-3, 4);

		vector2d.rotate(360);

		Assert.assertEquals(-3, vector2d.getX(), 10E-6);
		Assert.assertEquals(4, vector2d.getY(), 10E-6);
	}

	@Test
	public void testRotateThirdQuadrant() {

		Vector2D vector2d = new Vector2D(-3, -4);

		vector2d.rotate(60);

		Assert.assertEquals(1.9641016151377526, vector2d.getX(), 10E-6);
		Assert.assertEquals(-4.598076211353317, vector2d.getY(), 10E-6);
	}

	@Test
	public void testRotateThirdQuadrantTwoPi() {

		Vector2D vector2d = new Vector2D(-3, -4);

		vector2d.rotate(360);

		Assert.assertEquals(-3, vector2d.getX(), 10E-6);
		Assert.assertEquals(-4, vector2d.getY(), 10E-6);
	}

	@Test
	public void testRotateFourthQuadrant() {

		Vector2D vector2d = new Vector2D(3, -4);

		vector2d.rotate(60);

		Assert.assertEquals(4.964101615137754, vector2d.getX(), 10E-6);
		Assert.assertEquals(0.5980762113533182, vector2d.getY(), 10E-6);
	}

	@Test
	public void testRotateFourthQuadrantTwoPi() {

		Vector2D vector2d = new Vector2D(3, -4);

		vector2d.rotate(360);

		Assert.assertEquals(3, vector2d.getX(), 10E-6);
		Assert.assertEquals(-4, vector2d.getY(), 10E-6);
	}

	@Test
	public void testTranslate() {
		Vector2D vector2d = new Vector2D(3, 4);

		vector2d.translate(new Vector2D(2, 2));

		Assert.assertEquals(5, vector2d.getX(), 10E-6);
		Assert.assertEquals(6, vector2d.getY(), 10E-6);
	}

	@Test
	public void testTranslateNewObject() {
		Vector2D vector2d = new Vector2D(3, 4);

		Vector2D translatedVector = vector2d.translated(new Vector2D(2, 2));

		Assert.assertEquals(5, translatedVector.getX(), 10E-6);
		Assert.assertEquals(6, translatedVector.getY(), 10E-6);
	}

	@Test
	public void testScaling() {
		Vector2D vector2d = new Vector2D(3, 4);

		vector2d.scale(2);

		Assert.assertEquals(6, vector2d.getX(), 10E-6);
		Assert.assertEquals(8, vector2d.getY(), 10E-6);

	}

	@Test
	public void testScalingNewObject() {
		Vector2D vector2d = new Vector2D(3, 4);

		Vector2D scaledVector = vector2d.scaled(2);

		Assert.assertEquals(6, scaledVector.getX(), 10E-6);
		Assert.assertEquals(8, scaledVector.getY(), 10E-6);

	}

}
