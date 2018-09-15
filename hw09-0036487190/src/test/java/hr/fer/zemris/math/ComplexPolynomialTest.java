package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;


public class ComplexPolynomialTest {
	
	@Test
	public void testConstructor1() {
//		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(7,2), new Complex(2,0),new Complex(5,0),new Complex(1,0)});
		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(1,0), new Complex(5,0),new Complex(2,0),new Complex(7,2)});
		Complex z = new Complex(0,0);

		Complex polynomial = poly.apply(z);
		
		Assert.assertEquals(1, polynomial.getRe(), 10E-3);
		Assert.assertEquals(0, polynomial.getIm(), 10E-3);
		Assert.assertEquals(3, poly.order());	
	}

	@Test
	public void testConstructor2() {
//		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(7,2), new Complex(2,0),new Complex(5,0),new Complex(1,0)});
		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(1,0), new Complex(5,0),new Complex(2,0),new Complex(7,2)});
		Complex z = new Complex(1,1);

		Complex polynomial = poly.apply(z);
		
		Assert.assertEquals(-12, polynomial.getRe(), 10E-3);
		Assert.assertEquals(19, polynomial.getIm(), 10E-3);
		Assert.assertEquals(3, poly.order());	
	}

	@Test
	public void testConstructor3() {
//		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(7,2), new Complex(2,0),new Complex(5,0),new Complex(1,0)});
		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(1,0), new Complex(5,0),new Complex(2,0),new Complex(7,2)});
		Complex z = new Complex(1,0);

		Complex polynomial = poly.apply(z);
		
		Assert.assertEquals(15, polynomial.getRe(), 10E-3);
		Assert.assertEquals(2, polynomial.getIm(), 10E-3);
		Assert.assertEquals(3, poly.order());	
	}

	@Test
	public void testConstructor4() {
//		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(7,2), new Complex(2,0),new Complex(5,0),new Complex(1,0)});
		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(1,0), new Complex(5,0),new Complex(2,0),new Complex(7,2)});
		Complex z = new Complex(0,1);

		Complex polynomial = poly.apply(z);
		
		Assert.assertEquals(1, polynomial.getRe(), 10E-3);
		Assert.assertEquals(-2, polynomial.getIm(), 10E-3);
		Assert.assertEquals(3, poly.order());	
	}
	
	@Test
	public void testDerivative() {
//		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(7,2), new Complex(2,0),new Complex(5,0),new Complex(1,0)});
		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(1,0), new Complex(5,0),new Complex(2,0),new Complex(7,2)});

		ComplexPolynomial polynomial = poly.derive();
		
		Assert.assertEquals(3, poly.order());	
		Assert.assertEquals(2, polynomial.order());	
	}
	
	@Test
	public void testDerivative2() {
//		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(7,2), new Complex(2,0),new Complex(5,0),new Complex(1,0)});
		ComplexPolynomial poly = new ComplexPolynomial(new Complex[] {new Complex(-1,0), new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0)});

		ComplexPolynomial polynomial = poly.derive();
		
		Assert.assertEquals(4, poly.order());	
		Assert.assertEquals(3, polynomial.order());	
	}
}
