package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		Assert.assertEquals(true, oper.satisfied("Ana", "Jasna"));
		Assert.assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		Assert.assertEquals(true, oper.satisfied("Ana", "Jasna"));
		Assert.assertEquals(true, oper.satisfied("Ana", "Ana"));
		Assert.assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		Assert.assertEquals(true, oper.satisfied("Jasna", "Ana"));
		Assert.assertEquals(false, oper.satisfied("Ana", "Ana"));
		Assert.assertEquals(false, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testGreaterOrEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		Assert.assertEquals(true, oper.satisfied("Jasna", "Ana"));
		Assert.assertEquals(true, oper.satisfied("Ana", "Ana"));
		Assert.assertEquals(false, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testEqual() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		Assert.assertEquals(false, oper.satisfied("Jasna", "Ana"));
		Assert.assertEquals(true, oper.satisfied("Ana", "Ana"));
		Assert.assertEquals(false, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		Assert.assertEquals(true, oper.satisfied("Jasna", "Ana"));
		Assert.assertEquals(false, oper.satisfied("Ana", "Ana"));
		Assert.assertEquals(true, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLike() {		
		IComparisonOperator oper = ComparisonOperators.LIKE;

		Assert.assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		Assert.assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		Assert.assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testLikeMultipleWildcards() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		Assert.assertEquals(false, oper.satisfied("Zagreb", "*Aba*"));

	}
}
