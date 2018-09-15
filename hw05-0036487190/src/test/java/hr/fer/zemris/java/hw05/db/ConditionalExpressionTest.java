package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;


public class ConditionalExpressionTest {

	@Test
	public void testIfSatisfies1() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		
		Assert.assertEquals(false, recordSatisfies);

	}

	@Test
	public void testIfSatisfies2() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", 2);

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		
		Assert.assertEquals(true, recordSatisfies);

	}

	@Test
	public void testIfSatisfies3() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Akš*",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		
		Assert.assertEquals(true, recordSatisfies);

	}
	
	@Test
	public void testIfSatisfies4() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Akšamović",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		
		Assert.assertEquals(true, recordSatisfies);

	}


}
