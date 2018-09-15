package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test
	public void initialPutOneAndPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertEquals(2000, multistack.peek("year").getValue());
	}
	
	@Test
	public void initialPutTwoAndPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(200));
		
		assertEquals(200, multistack.peek("year").getValue());
		assertEquals(200, multistack.peek("year").getValue());
	}
	
	@Test
	public void initialPutTwoNamesAndPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year2", new ValueWrapper(200));
		
		assertEquals(2000, multistack.peek("year").getValue());
		assertEquals(200, multistack.peek("year2").getValue());
	}
	
	@Test
	public void putAndPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertEquals(2000, multistack.pop("year").getValue());
		assertTrue(multistack.isEmpty("year"));
	}
	
	@Test
	public void putTwoAndPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(200));
		
		assertEquals(200, multistack.pop("year").getValue());
		assertEquals(2000, multistack.pop("year").getValue());
		assertTrue(multistack.isEmpty("year"));
	}
	
	@Test
	public void putTwoNamesAndPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year2", new ValueWrapper(200));
		
		assertEquals(2000, multistack.pop("year").getValue());
		assertEquals(200, multistack.pop("year2").getValue());
		assertTrue(multistack.isEmpty("year"));
		assertTrue(multistack.isEmpty("year2"));
	}
	
	@Test
	public void putMultipleAndPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(200));
		multistack.push("year", new ValueWrapper(20));
		multistack.push("year", new ValueWrapper(2));
		
		assertEquals(2, multistack.pop("year").getValue());
		assertEquals(20, multistack.pop("year").getValue());
		assertEquals(200, multistack.pop("year").getValue());
		assertEquals(2000, multistack.peek("year").getValue());
		assertFalse(multistack.isEmpty("year"));
	}
	
	@Test
	public void putMultipleToDifferentNames() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(200));
		multistack.push("year", new ValueWrapper(20));
		multistack.push("year", new ValueWrapper(2));
		
		multistack.push("year2", year);
		multistack.push("year2", new ValueWrapper(200));
		multistack.push("year2", new ValueWrapper("String"));
		multistack.push("year2", new ValueWrapper(2));
		
		assertEquals(2, multistack.pop("year").getValue());
		assertEquals(20, multistack.pop("year").getValue());
		assertEquals(200, multistack.pop("year").getValue());
		assertEquals(2000, multistack.peek("year").getValue());
		assertFalse(multistack.isEmpty("year"));
		
		assertEquals(2, multistack.pop("year2").getValue());
		assertEquals("String", multistack.pop("year2").getValue());
		assertEquals(200, multistack.pop("year2").getValue());
		assertEquals(2000, multistack.pop("year2").getValue());
		assertTrue(multistack.isEmpty("year2"));

	}
	
	@Test (expected = EmptyStackException.class)
	public void testFromEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year2", new ValueWrapper(200));
		
		assertEquals(2000, multistack.peek("year").getValue());
		assertEquals(200, multistack.peek("year1").getValue());

	}
	
	@Test 
	public void testIsEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertFalse(multistack.isEmpty("year"));
	}
}
