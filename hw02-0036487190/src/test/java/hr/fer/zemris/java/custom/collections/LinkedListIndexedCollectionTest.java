package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class LinkedListIndexedCollectionTest {

	@Test
	public void generateListFromArrayCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco");

		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);

		Assert.assertEquals(3, col2.size());

	}

	@Test
	public void generateListFromListCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add("New York");
		col.add("San Francisco");

		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		LinkedListIndexedCollection col3 = new LinkedListIndexedCollection(col2);

		Assert.assertEquals(2, col3.size());

	}

	@Test
	public void addToList() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("test1");
		col.add("test2");

		Assert.assertEquals(2, col.size());

	}

	@Test
	public void getFromList() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("test1");
		col.add("test2");
		col.add("test3");
		col.add("test4");

		Assert.assertEquals("test1", col.get(0));
		Assert.assertEquals("test3", col.get(2));
		Assert.assertEquals("test4", col.get(3));


	}
	
	public void clear() {
		
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("test1");
		col.add("test2");
	
		Assert.assertEquals(0, col.size());

	}
	
	@Test
	public void insert() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("test1");
		col.add("test2");
		col.insert("testInsert", 1);
		
		
		Assert.assertEquals("testInsert", col.get(1));
		Assert.assertEquals("test1", col.get(0));
		Assert.assertEquals("test2", col.get(2));


		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		
		col1.add("test1");
		col1.add("test2");
		col1.insert("testInsert", 0);

		Assert.assertEquals("testInsert", col1.get(0));
		Assert.assertEquals("test1", col1.get(1));
		Assert.assertEquals("test2", col1.get(2));
		
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		
		col2.add("test1");
		col2.add("test2");
		col2.insert("testInsert", 2);

		Assert.assertEquals("testInsert", col2.get(2));
		Assert.assertEquals("test1", col2.get(0));
		Assert.assertEquals("test2", col2.get(1));


	}
	
	@Test
	public void indexOf() {
		
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("test1");
		col.add("test2");
		col.insert("testInsert", 1);
		
		
		Assert.assertEquals(0, col.indexOf("test1"));
		Assert.assertEquals(2, col.indexOf("test2"));
		Assert.assertEquals(1, col.indexOf("testInsert"));
		Assert.assertEquals(-1, col.indexOf("sir"));

	}
	
	@Test
	public void remove() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("test1");
		col.add("test2");
		col.add("test3");
		
		col.remove(0);
		
		Assert.assertEquals("test2", col.get(0));
		Assert.assertEquals("test3", col.get(1));
		
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();

		col2.add("test1");
		col2.add("test2");
		col2.add("test3");
		
		col2.remove(2);
		
		Assert.assertEquals(-1, col2.indexOf("test3"));
		Assert.assertEquals(2, col2.size());
		
		LinkedListIndexedCollection col3 = new LinkedListIndexedCollection();

		col3.add("test1");
		col3.add("test2");
		col3.add("test3");
		
		col3.remove(1);
		
		Assert.assertEquals(-1, col3.indexOf("test2"));
		Assert.assertEquals(2, col3.size());
		Assert.assertEquals("test1", col3.get(0));
		Assert.assertEquals("test3", col3.get(1));

	}
	
	@Test 
	public void toArray() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("string1");
		col.add("string2");
		
		Object[] expected = {"string1", "string2"};

		
		Assert.assertArrayEquals(expected, col.toArray());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
