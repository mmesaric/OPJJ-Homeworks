package hr.fer.zemris.java.hw05.db.lexer;

import org.junit.Assert;
import org.junit.Test;

public class QueryLexerTest {

	@Test
	public void testJmbagQuery() {
		QueryLexer lexer = new QueryLexer(" jmbag=\"0000000003\"");

		Assert.assertEquals("jmbag", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("=", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("0000000003", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals(null, lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}
	
	@Test
	public void testJmbagLessQuery() {
		QueryLexer lexer = new QueryLexer(" jmbag < \"0000000005\"");

		Assert.assertEquals("jmbag", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("<", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("0000000005", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals(null, lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	public void testLastNameQuery() {
		QueryLexer lexer = new QueryLexer("  firstName>\"A\" and lastName LIKE \"B*ć\"");

		Assert.assertEquals("firstName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals(">", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("A", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals("and", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("lastName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("LIKE", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("B*ć", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals(null, lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	public void testLastNameQuery2() {
		QueryLexer lexer = new QueryLexer(
				" firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"\r\n");

		Assert.assertEquals("firstName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals(">", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("A", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals("and", lexer.nextToken().getValue());
		Assert.assertEquals("firstName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("<", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("C", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals("and", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("lastName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("LIKE", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("B*ć", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals("and", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("jmbag", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals(">", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("0000000002", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals(null, lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}
	
	@Test
	public void testLastGEQ() {
		QueryLexer lexer = new QueryLexer("  firstName>=\"A\" and lastName LIKE \"B*ć\"");

		Assert.assertEquals("firstName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals(">=", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("A", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals("and", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("lastName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("LIKE", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("B*ć", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals(null, lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}
	
	@Test
	public void testLastLEQ() {
		QueryLexer lexer = new QueryLexer("  firstName<=\"A\" and lastName LIKE \"B*ć\"");

		Assert.assertEquals("firstName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("<=", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("A", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals("and", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("lastName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("LIKE", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("B*ć", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals(null, lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}
	
	@Test
	public void testLastNE() {
		QueryLexer lexer = new QueryLexer("  firstName!=\"A\" and lastName LIKE \"B*ć\"");

		Assert.assertEquals("firstName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("!=", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("A", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals("and", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("lastName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.ATTRIBUTE_NAME, lexer.getToken().getType());
		Assert.assertEquals("LIKE", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		Assert.assertEquals("B*ć", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.STRING_LITERAL, lexer.getToken().getType());
		Assert.assertEquals(null, lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

}
