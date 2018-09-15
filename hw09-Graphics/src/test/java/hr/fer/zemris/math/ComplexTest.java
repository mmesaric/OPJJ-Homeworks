package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {

	@Test
	public void testAdd() {
		
		Complex c1 = new Complex(3,1);
		Complex c2 = new Complex(4,1);

		Complex result = c1.add(c2);
		
		Assert.assertEquals(result.getRe(), 7, 10E-3);
		Assert.assertEquals(result.getIm(), 2, 10E-3);
	}
	
	@Test
	public void testSub() {
		
		Complex c1 = new Complex(3,1);
		Complex c2 = new Complex(4,1);

		Complex result = c1.sub(c2);
		
		Assert.assertEquals(result.getRe(), -1, 10E-3);
		Assert.assertEquals(result.getIm(), 0, 10E-3);
	}
	
	@Test
	public void testMul() {
		
		Complex c1 = new Complex(3,1);
		Complex c2 = new Complex(4,1);

		Complex result = c1.multiply(c2);
		
		Assert.assertEquals(result.getRe(), 11, 10E-3);
		Assert.assertEquals(result.getIm(), 7, 10E-3);
	}
	
	@Test
	public void testDiv() {
		
		Complex c1 = new Complex(3,1);
		Complex c2 = new Complex(4,1);

		Complex result = c1.divide(c2);
		
		Assert.assertEquals(result.getRe(), 13./17., 10E-3);
		Assert.assertEquals(result.getIm(), 1./17., 10E-3);
	}
	
	@Test
	public void testMod() {
		
		Complex c1 = new Complex(3,4);
		
		Assert.assertEquals(c1.module(), 5, 10E-3);
	}
	
	@Test
	public void testNegate() {
		
		Complex c1 = new Complex(3,4);
		
		Assert.assertEquals(c1.negate().getRe(), -3, 10E-3);
		Assert.assertEquals(c1.negate().getIm(), -4, 10E-3);

	}
}
