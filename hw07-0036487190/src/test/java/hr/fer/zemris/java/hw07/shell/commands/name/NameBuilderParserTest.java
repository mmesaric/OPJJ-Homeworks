package hr.fer.zemris.java.hw07.shell.commands.name;

import org.junit.Assert;
import org.junit.Test;

public class NameBuilderParserTest {

	@Test
	public void testOne() {
		
		NameBuilderParser parser = new NameBuilderParser("gradovi-${2}-${1,03}.jpg");

		Assert.assertEquals(5, parser.parseExpression("gradovi-${2}-${1,03}.jpg").size());
	}
	
	@Test
	public void tesTwo() {
		
		NameBuilderParser parser = new NameBuilderParser("gradovi-${2}-${1,03}.{$}jpg");

		Assert.assertEquals(5, parser.parseExpression("gradovi-${2}-${1,03}.{$}jpg").size());
	}
	
	@Test(expected= ParserException.class)
	public void testThree() {
		
		NameBuilderParser parser = new NameBuilderParser("gradovi-${2}-${1,03}.${}jpg");

		Assert.assertEquals(6, parser.parseExpression("gradovi-${2}-${1,03}.{$}jpg").size());
	}
	
	@Test
	public void test4() {
		
		NameBuilderParser parser = new NameBuilderParser("gradovi-${2}-${1,03}.${1}jpg");

		Assert.assertEquals(7, parser.parseExpression("gradovi-${2}-${1,03}.${1}jpg").size());
	}
}
