package hr.fer.zemris.java.fractals;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.math.Complex;

public class NewtonTest {
	
	@Test 
	public void testParser1() {	
		Complex number = Newton.parseComplex("0");
		
		Assert.assertEquals(0, number.getRe(), 10E-3);
		Assert.assertEquals(0, number.getIm(), 10E-3);
	}

	@Test 
	public void testParser2() {	
		Complex number = Newton.parseComplex("i0");
		
		Assert.assertEquals(0, number.getRe(), 10E-3);
		Assert.assertEquals(0, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser3() {	
		Complex number = Newton.parseComplex("1");
		
		Assert.assertEquals(1, number.getRe(), 10E-3);
		Assert.assertEquals(0, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser4() {	
		Complex number = Newton.parseComplex("i");
		
		Assert.assertEquals(0, number.getRe(), 10E-3);
		Assert.assertEquals(1, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser5() {	
		Complex number = Newton.parseComplex("i5");
		
		Assert.assertEquals(0, number.getRe(), 10E-3);
		Assert.assertEquals(5, number.getIm(), 10E-3);
	}

	@Test 
	public void testParser6() {	
		Complex number = Newton.parseComplex("-i4");
		
		Assert.assertEquals(0, number.getRe(), 10E-3);
		Assert.assertEquals(-4, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser7() {	
		Complex number = Newton.parseComplex("-4");
		
		Assert.assertEquals(-4, number.getRe(), 10E-3);
		Assert.assertEquals(0, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser8() {	
		Complex number = Newton.parseComplex("-1+i0");
		
		Assert.assertEquals(-1, number.getRe(), 10E-3);
		Assert.assertEquals(0, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser9() {	
		Complex number = Newton.parseComplex("-1-i0");
		
		Assert.assertEquals(-1, number.getRe(), 10E-3);
		Assert.assertEquals(0, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser10() {	
		Complex number = Newton.parseComplex("1+i10");
		
		Assert.assertEquals(1, number.getRe(), 10E-3);
		Assert.assertEquals(10, number.getIm(), 10E-3);
	}
	
	@Test 
	public void testParser11() {	
		Complex number = Newton.parseComplex("1-i10");
		
		Assert.assertEquals(1, number.getRe(), 10E-3);
		Assert.assertEquals(-10, number.getIm(), 10E-3);
	}
}
