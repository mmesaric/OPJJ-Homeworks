package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;


public class DictionaryTest {

	@Test
	public void createDictionaryDefaultCapacity() {
		
		Dictionary dictionary = new Dictionary();
		
		Assert.assertEquals( 0, dictionary.size());
	}
	
	@Test
	public void createDictionaryWithGivenCapacity() {
		
		Dictionary dictionary = new Dictionary(16);
		
		Assert.assertEquals( 0, dictionary.size());
	}
	
	@Test
	public void putEntry() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("key1", "value1");
		dictionary.put("key2", "value2");
		
		Assert.assertEquals( "key1:value1\nkey2:value2\n", dictionary.toString());		
	}
	
	@Test
	public void dictionarySize() {
		
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("key1", "value1");
		dictionary.put("key2", "value2");
		
		Assert.assertEquals( 2, dictionary.size());		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {
		
		Dictionary dictionary = new Dictionary();
		
		dictionary.put(null, "value1");
	}
	
	@Test
	public void putExisting() {
		
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("key1", "value1");
		dictionary.put("key1", "value2");

		Assert.assertEquals( "key1:value2\n", dictionary.toString());
		Assert.assertEquals( 1, dictionary.size());
		
		dictionary.put("key1", (Integer)20);
		
		Assert.assertEquals( "key1:20\n", dictionary.toString());
		Assert.assertEquals( 1, dictionary.size());
	}
	
	@Test
	public void testGetMethod() {
		
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("key1", "value1");
		dictionary.put("key2", "value2");
		
		Assert.assertEquals("value1" , dictionary.get("key1"));
		Assert.assertEquals("value2" , dictionary.get("key2"));
		Assert.assertEquals( 2, dictionary.size());
	}
	
	@Test
	public void testIsEmptyWhenNoEntries() {
		
		Dictionary dictionary = new Dictionary();
		
		Assert.assertEquals(true, dictionary.isEmpty());
	}
	
	@Test
	public void testIsEmptyWithEntries() {
		
		Dictionary dictionary = new Dictionary();
		dictionary.put("key1", "value1");
		
		Assert.assertEquals(false, dictionary.isEmpty());
	}
	
}
