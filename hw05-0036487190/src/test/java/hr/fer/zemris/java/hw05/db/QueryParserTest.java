package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class QueryParserTest {
	
	@Test
	public void testJmbagQuery() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		
		Assert.assertEquals(true, qp1.isDirectQuery());
		Assert.assertEquals("0123456789", qp1.getQueriedJMBAG());
		Assert.assertEquals(1, qp1.getQuery().size());
	}
	
	@Test (expected = IllegalStateException.class)
	public void testJmbagAndLastName() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
		Assert.assertEquals(false, qp1.isDirectQuery());
		Assert.assertEquals("0123456789", qp1.getQueriedJMBAG());
		Assert.assertEquals(2, qp1.getQuery().size());	
	}
	
	@Test (expected = IllegalStateException.class)
	public void testThreeExpressions() {
		QueryParser qp1 = new QueryParser("firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		
		Assert.assertEquals(false, qp1.isDirectQuery());
		Assert.assertEquals("0000000002", qp1.getQueriedJMBAG());
		Assert.assertEquals(4, qp1.getQuery().size());	
	}
	
}
