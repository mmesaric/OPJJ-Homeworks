package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {
	
	@Test
	public void forPositiveNumbers() {
		long input1=5;
		long expected1=120;
		
		long input2= 3;
		long expected2=6;
		
		Assert.assertEquals(expected1, Factorial.calculateFactorial(input1));
		Assert.assertEquals(expected2, Factorial.calculateFactorial(input2));

	}
	
	@Test
	public void forZero() {
		long input=0;
		long expected=1;
		
		Assert.assertEquals(expected, Factorial.calculateFactorial(input));

	}
	
	@Test
	public void forNegativeNumbers() {
		long input1=-2;
		long expected1=-1;
		
		long input2=-5;
		long expected2=-1;
		
		Assert.assertEquals(expected1, Factorial.calculateFactorial(input1));
		Assert.assertEquals(expected2, Factorial.calculateFactorial(input2));


	}
}
