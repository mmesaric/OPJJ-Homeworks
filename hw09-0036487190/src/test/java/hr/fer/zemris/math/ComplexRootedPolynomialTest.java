package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void constructorTest() {

		Complex[] roots = new Complex[] { new Complex(-2, 1), new Complex(-2, -1) };
		Complex z = new Complex(0, 0);

		Complex polynomial = new ComplexRootedPolynomial(roots).apply(z);

		Assert.assertEquals(5, polynomial.getRe(), 10E-3);
		Assert.assertEquals(0, polynomial.getIm(), 10E-3);
	}

	@Test
	public void constructorTest2() {

		Complex[] roots = new Complex[] { new Complex(-2, 1), new Complex(-2, -1) };
		Complex z = new Complex(0, 1);

		Complex polynomial = new ComplexRootedPolynomial(roots).apply(z);

		Assert.assertEquals(4, polynomial.getRe(), 10E-3);
		Assert.assertEquals(4, polynomial.getIm(), 10E-3);
	}

	@Test
	public void constructorTest3() {

		Complex[] roots = new Complex[] { new Complex(-2, 1), new Complex(-2, -1) };
		Complex z = new Complex(1, 1);

		Complex polynomial = new ComplexRootedPolynomial(roots).apply(z);

		Assert.assertEquals(9, polynomial.getRe(), 10E-3);
		Assert.assertEquals(6, polynomial.getIm(), 10E-3);
	}

	@Test
	public void constructorTest4() {

		Complex[] roots = new Complex[] { new Complex(-2, 1), new Complex(-2, -1) };
		Complex z = new Complex(1, 2);

		Complex polynomial = new ComplexRootedPolynomial(roots).apply(z);

		Assert.assertEquals(6, polynomial.getRe(), 10E-3);
		Assert.assertEquals(12, polynomial.getIm(), 10E-3);
	}

	@Test
	public void constructorTest5() {

		Complex[] roots = new Complex[] { new Complex(-2, 1), new Complex(-2, -1) };
		Complex z = new Complex(0, -2);

		Complex polynomial = new ComplexRootedPolynomial(roots).apply(z);

		Assert.assertEquals(1, polynomial.getRe(), 10E-3);
		Assert.assertEquals(-8, polynomial.getIm(), 10E-3);
	}

	@Test
	public void transformTest() {

		Complex[] roots = new Complex[] { new Complex(0, 1), new Complex(0, -1), new Complex(1, 0),
				new Complex(-1, 0) };

		ComplexPolynomial polynomial = new ComplexRootedPolynomial(roots).toComplexPolynom();

		Assert.assertEquals(4, polynomial.order());
	}
}
