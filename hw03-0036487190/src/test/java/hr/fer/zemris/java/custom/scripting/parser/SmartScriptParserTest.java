package hr.fer.zemris.java.custom.scripting.parser;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

public class SmartScriptParserTest {

	@Test
	public void testBasicFor() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1 10 1 $}");
		
		DocumentNode documentNode = parser.getDocumentNode();
		
		Assert.assertEquals(1, documentNode.numberOfChildren() );
	}
	
	@Test
	public void testTwoTags() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1 10 1 $} {$= i$}");
		
		DocumentNode documentNode = parser.getDocumentNode();
		
		Assert.assertEquals(1, documentNode.numberOfChildren() );
	}
	
	@Test
	public void testTwoTagsWithEnd() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1 10 1 $}{$END$}{$= i$}");
		
		DocumentNode documentNode = parser.getDocumentNode();
		
		Assert.assertEquals(2, documentNode.numberOfChildren() );
	}
	
	@Test
	public void testTwoTagsWithEndAndTextInsideFor() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1 10 1 $}this is text{$END$}{$= i$}");
		
		DocumentNode documentNode = parser.getDocumentNode();
		
		Assert.assertEquals(2, documentNode.numberOfChildren() );
	}
	
	@Test
	public void testTwoTagsWithEndAndTextOutsideFor() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1 10 1 $}{$END$}this is text{$= i$}");
		
		DocumentNode documentNode = parser.getDocumentNode();
		
		Assert.assertEquals(3, documentNode.numberOfChildren() );
	}
	
	@Test
	public void testForLoopChildrenSize() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1 10 1 $}this is text{$= i$}{$END$}");
		
		DocumentNode documentNode = parser.getDocumentNode();
		
		Assert.assertEquals(2, documentNode.getChild(0).numberOfChildren() );
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testForLoopValidityWithNoVar() {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser("{$ FOR -1 -1 10 1 $}this is text{$= i$}{$END$}");
				
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testForLoopValidityTooManyArguments() {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser("{$ FOR var_1 -1 10 1 1$}this is text{$= i$}{$END$}");			
	
	}
	

	@Test (expected = SmartScriptParserException.class)
	public void testTooForLoopTooFewArguments() {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser("{$ FOR var_1 1$}this is text{$= i$}{$END$}");			
	
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testForLoopOnlyOneArgument() {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser("{$ FOR var_1 $}this is text{$= i$}{$END$}");			

	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testForLoopWithFunction() {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser("{$ FOR var_1 @name1 $}this is text{$= i$}{$END$}");			

	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testEchoTagWithNoVariable() {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser("{$= $}");			
		
	}
}
