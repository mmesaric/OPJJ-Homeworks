package hr.fer.zemris.java.custom.collections;

import org.junit.Test;

import org.junit.Assert;

public class ArrayIndexedCollectionTest {

	@Test
	public void fillOtherCollection() {

		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		arrayIndexedCollection.add("string3");
		arrayIndexedCollection.add("string4");

		ArrayIndexedCollection arrayIndexedCollection2 = new ArrayIndexedCollection(arrayIndexedCollection);

		int real = arrayIndexedCollection2.size();
		int expected = 4;

		Assert.assertEquals(expected, real);
	}

	@Test
	public void fillOtherCollectionWithCapacity() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(4);

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		arrayIndexedCollection.add("string3");
		arrayIndexedCollection.add("string4");
		arrayIndexedCollection.add("string5");

		ArrayIndexedCollection arrayIndexedCollection2 = new ArrayIndexedCollection(arrayIndexedCollection);

		int real = arrayIndexedCollection2.size();
		int expected = 5;

		Assert.assertEquals(expected, real);

	}

	@Test
	public void doubleCollectionCapacity() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(2);

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		arrayIndexedCollection.add("string3");
		arrayIndexedCollection.add("string4");
		arrayIndexedCollection.add("string5");

		int real = arrayIndexedCollection.size();
		int expected = 5;

		arrayIndexedCollection.add("string5");
		arrayIndexedCollection.add("string6");
		arrayIndexedCollection.add("string7");
		arrayIndexedCollection.add(Double.valueOf(12));
		arrayIndexedCollection.add("string8");

		int real1 = arrayIndexedCollection.size();
		int expected1 = 10;

		Assert.assertEquals(expected, real);
		Assert.assertEquals(expected1, real1);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getInvalidIndex() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");

		arrayIndexedCollection.get(2);
	}

	@Test
	public void getValidIndex() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");

		Assert.assertEquals("string1", arrayIndexedCollection.get(0));
		Assert.assertEquals("string2", arrayIndexedCollection.get(1));
	}

	@Test
	public void clearCollection() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		arrayIndexedCollection.add(Integer.valueOf(29));
		arrayIndexedCollection.clear();

		Assert.assertEquals(0, arrayIndexedCollection.size());

	}

	@Test
	public void indexOfTest() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		arrayIndexedCollection.add(Integer.valueOf(29));

		Assert.assertEquals(1, arrayIndexedCollection.indexOf("string2"));
		Assert.assertEquals(0, arrayIndexedCollection.indexOf("string1"));
		Assert.assertEquals(2, arrayIndexedCollection.indexOf(Integer.valueOf(29)));
		Assert.assertEquals(-1, arrayIndexedCollection.indexOf("ne postoji"));

	}

	@Test
	public void insert() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(2);

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		arrayIndexedCollection.add("string3");
		arrayIndexedCollection.insert(Integer.valueOf(29), 1);
		arrayIndexedCollection.insert("sir", 2);

		Assert.assertEquals(1, arrayIndexedCollection.indexOf(Integer.valueOf(29)));
		Assert.assertEquals(3, arrayIndexedCollection.indexOf("string2"));
		Assert.assertEquals(2, arrayIndexedCollection.indexOf("sir"));

	}

	@Test
	public void remove() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(2);

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		arrayIndexedCollection.add("string3");
		
		arrayIndexedCollection.remove(0);
		
		Assert.assertEquals(2, arrayIndexedCollection.size());
	}
	
	@Test 
	public void toArrayTest() {
		
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(2);

		arrayIndexedCollection.add("string1");
		arrayIndexedCollection.add("string2");
		
		Object[] expected = {"string1", "string2"};

		
		Assert.assertArrayEquals(expected, arrayIndexedCollection.toArray());

	}

}
