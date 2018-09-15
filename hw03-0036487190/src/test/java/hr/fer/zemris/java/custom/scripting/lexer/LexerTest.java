package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Assert;
import org.junit.Test;

public class LexerTest {

	@Test
	public void tokenizeText() {

		Lexer lexer = new Lexer("This is sample text.\r\n");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
	}

	@Test
	public void tokenizeTextWithTag() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$ ovo je tag navodno i nebi trebao biti u tekstu$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
	}

	@Test
	public void tokenizeTextWithNoTag() {

		Lexer lexer = new Lexer("This is sample text.\r\n\\{$ ovo je i dalje tekst$}");

		Assert.assertEquals("This is sample text.\r\n\\{$ ovo je i dalje tekst$}", lexer.nextToken().getValue());
	}

	@Test
	public void tokenizeEmptyTag() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test(expected = LexerException.class)
	public void throwsLexerException() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
		lexer.nextToken();
	}

	@Test
	public void tokenizeEmptyTagWithSpace() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$   $}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);
		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());

	}

	public void textAndTagSwitch() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$   $}This is sample text again\n");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());


		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("$}", lexer.nextToken().getValue());

		lexer.setState(LexerState.TEXT);

		Assert.assertEquals("This is sample text again\n", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());

	}

	@Test
	public void tagWithString() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$\"teststring\"$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("teststring", lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test(expected = LexerException.class)
	public void testStringException() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$\"teststring$}");

		lexer.nextToken();

		lexer.nextToken();
		lexer.setState(LexerState.TAG);

		lexer.nextToken();

	}

	@Test
	public void testStringWithNumbers() {
		Lexer lexer = new Lexer("This is sample text.\r\n{$\"tests201_tring\"$}This is sample text{$\"string02\"$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);
		Assert.assertEquals("tests201_tring", lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());

		lexer.setState(LexerState.TEXT);

		Assert.assertEquals("This is sample text", lexer.nextToken().getValue());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("string02", lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());

		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	public void testStringWithNumbersAndEscapeSigns() {
		Lexer lexer = new Lexer(
				"This is sample text.\r\n{$\"tests201_\\\\tring\"$}This is sample text{$\"strin\\\"g02\"$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("tests201_\\tring", lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());

		lexer.setState(LexerState.TEXT);

		Assert.assertEquals("This is sample text", lexer.nextToken().getValue());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("strin\"g02", lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());
		lexer.setState(LexerState.TEXT);


		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	public void testStringWithNumbersAndEscapeSignsWithSymbols() {
		Lexer lexer = new Lexer(
				"This is sample text.\r\n{$\"tests201_\\\\tring\"$}This is sample text{$\"strin\\\"g02\"  +- / ^$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());


		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("tests201_\\tring", lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());

		lexer.setState(LexerState.TEXT);

		Assert.assertEquals("This is sample text", lexer.nextToken().getValue());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("strin\"g02", lexer.nextToken().getValue());

		Assert.assertEquals('+', lexer.nextToken().getValue());
		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals('/', lexer.nextToken().getValue());
		Assert.assertEquals('^', lexer.nextToken().getValue());
		Assert.assertEquals("$}", lexer.nextToken().getValue());
		lexer.setState(LexerState.TEXT);


		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	public void testIdentifiersValidity() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$var1 $}tekst2");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("var1", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());

		Assert.assertEquals("$}", lexer.nextToken().getValue());
		lexer.setState(LexerState.TEXT);
		
		Assert.assertEquals("tekst2", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());

	}

	@Test
	public void testIdentifiersValidityWithUnderscores() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$ var_1_4_longVarName$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals("var_1_4_longVarName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());

		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	public void testFunctionNamesAndVars() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$ @sin var1 @cos2  var_1_4_longVarName @method3$}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		
		lexer.setState(LexerState.TAG);


		Assert.assertEquals("sin", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.FUNCTION_NAME, lexer.getToken().getType());

		Assert.assertEquals("var1", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());

		Assert.assertEquals("cos2", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.FUNCTION_NAME, lexer.getToken().getType());

		Assert.assertEquals("var_1_4_longVarName", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());

		Assert.assertEquals("method3", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.FUNCTION_NAME, lexer.getToken().getType());

		Assert.assertEquals("$}", lexer.nextToken().getValue());
		lexer.setState(LexerState.TEXT);

		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	public void testEndTag() {

		Lexer lexer = new Lexer("This is sample text.\r\n{$END$}This is sample text again\n");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		
		Assert.assertEquals("END", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());
		
		Assert.assertEquals("$}", lexer.nextToken().getValue());

		lexer.setState(LexerState.TEXT);

		Assert.assertEquals("This is sample text again\n", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());
	}
	
	@Test
	public void testInt() {
		
		Lexer lexer = new Lexer("This is sample text.\r\n{$ @sin var1 @cos2   124 $}");

		Assert.assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());


		Assert.assertEquals("{$", lexer.nextToken().getValue());
		
		lexer.setState(LexerState.TAG);


		Assert.assertEquals("sin", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.FUNCTION_NAME, lexer.getToken().getType());

		Assert.assertEquals("var1", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());

		Assert.assertEquals("cos2", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.FUNCTION_NAME, lexer.getToken().getType());
		
		Assert.assertEquals("124", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.INT, lexer.getToken().getType());

		Assert.assertEquals("$}", lexer.nextToken().getValue());
		lexer.setState(LexerState.TEXT);

		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());

	}
	
	@Test
	public void testNegativeIntAndSymbol() {
		
		Lexer lexer = new Lexer("{$- -1245512 - var1 FOR -$}");
		

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		lexer.setState(LexerState.TAG);

		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.SYMBOL, lexer.getToken().getType());
		Assert.assertEquals("-1245512", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.INT, lexer.getToken().getType());
		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.SYMBOL, lexer.getToken().getType());
		Assert.assertEquals("var1", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());
		Assert.assertEquals("FOR", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());
		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.SYMBOL, lexer.getToken().getType());
		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());	
	}
	
	@Test 
	public void testNumbers () {
		
		Lexer lexer = new Lexer("{$- -1245512 --42.38321  -2.42 FOR - 0.1$}");

		Assert.assertEquals("{$", lexer.nextToken().getValue());
		
		lexer.setState(LexerState.TAG);

		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.SYMBOL, lexer.getToken().getType());
		Assert.assertEquals("-1245512", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.INT, lexer.getToken().getType());
		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.SYMBOL, lexer.getToken().getType());
		Assert.assertEquals("-42.38321", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.DOUBLE, lexer.getToken().getType());
		Assert.assertEquals("-2.42", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.DOUBLE, lexer.getToken().getType());
		Assert.assertEquals("FOR", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.IDENTIFIER, lexer.getToken().getType());
		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.SYMBOL, lexer.getToken().getType());
		Assert.assertEquals("0.1", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.DOUBLE, lexer.getToken().getType());
		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertNotNull(lexer.nextToken());
		Assert.assertEquals(TokenType.EOF, lexer.getToken().getType());	
	}
	
	@Test
	public void testSwitchingStates() {
		
		Lexer lexer = new Lexer("prvo ide tekst{$- -$}");
		
		Assert.assertEquals("prvo ide tekst", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.getToken().getType());
		Assert.assertEquals("{$", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.OPENING_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		
		Assert.assertEquals('-', lexer.nextToken().getValue());
		Assert.assertEquals('-', lexer.nextToken().getValue());

		Assert.assertEquals("$}", lexer.nextToken().getValue());
		Assert.assertEquals(TokenType.CLOSING_TAG, lexer.getToken().getType());
		lexer.setState(LexerState.TEXT);

	}
}
