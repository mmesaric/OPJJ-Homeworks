package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class SimpleHashtableTest {

	@Test
	public void testPut() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Assert.assertEquals(Integer.valueOf(5), examMarks.get("Ivana"));
		Assert.assertEquals(4, examMarks.size());
	}

	@Test
	public void testGet() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);

		Assert.assertEquals(Integer.valueOf(2), examMarks.get("Ivana"));
		Assert.assertEquals(Integer.valueOf(2), examMarks.get("Ante"));
		Assert.assertEquals(2, examMarks.size());
	}

	@Test
	public void testPutOverwriteValue() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Assert.assertEquals(Integer.valueOf(5), examMarks.get("Ivana"));
		Assert.assertEquals(Integer.valueOf(2), examMarks.get("Ante"));
		Assert.assertEquals(4, examMarks.size());
	}

	@Test
	public void testGetValue() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 4);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Anita", 5);
		examMarks.put("Anita", 4);

		Assert.assertEquals(Integer.valueOf(5), examMarks.get("Ivana"));
		Assert.assertEquals(Integer.valueOf(2), examMarks.get("Ante"));
		Assert.assertEquals(Integer.valueOf(2), examMarks.get("Jasna"));
		Assert.assertEquals(Integer.valueOf(4), examMarks.get("Kristina"));
		Assert.assertEquals(Integer.valueOf(4), examMarks.get("Anita"));
		Assert.assertEquals(5, examMarks.size());
	}

	@Test
	public void emptyMapTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		Assert.assertEquals(0, examMarks.size());

	}

	@Test
	public void nonEmptyMapTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		Assert.assertEquals(false, examMarks.isEmpty());

	}

	@Test(expected = NullPointerException.class)
	public void putNull() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put(null, 5);
	}

	@Test
	public void containsKeyTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Jasna", 5);
		examMarks.put("Ivana", 2);

		Assert.assertEquals(true, examMarks.containsKey("Jasna"));
		Assert.assertEquals(true, examMarks.containsKey("Ivana"));
		Assert.assertEquals(false, examMarks.containsKey("Štefica"));
	}

	@Test
	public void containsValueTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Jasna", 5);
		examMarks.put("Ivana", 2);

		Assert.assertEquals(true, examMarks.containsValue(5));
		Assert.assertEquals(true, examMarks.containsValue(2));
		Assert.assertEquals(false, examMarks.containsValue("Štefica"));
	}

	@Test
	public void removeFromStart() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Jasna", 5);
		examMarks.put("Ivana", 2);
		examMarks.remove("Jasna");

		Assert.assertEquals(true, examMarks.containsKey("Ivana"));
		Assert.assertEquals(false, examMarks.containsKey("Jasna"));
		Assert.assertEquals(false, examMarks.containsValue(5));
		Assert.assertEquals(true, examMarks.containsValue(2));
		Assert.assertEquals(1, examMarks.size());
	}

	@Test
	public void removeFromEnd() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Jasna", 5);
		examMarks.put("Ivana", 2);
		examMarks.remove("Ivana");

		Assert.assertEquals(false, examMarks.containsKey("Ivana"));
		Assert.assertEquals(true, examMarks.containsKey("Jasna"));
		Assert.assertEquals(true, examMarks.containsValue(5));
		Assert.assertEquals(false, examMarks.containsValue(2));
		Assert.assertEquals(1, examMarks.size());
	}

	@Test
	public void clearMap() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		examMarks.clear();
		Assert.assertEquals(false, examMarks.containsKey("Ivana"));
		Assert.assertEquals(false, examMarks.containsKey("Ante"));
		Assert.assertEquals(false, examMarks.containsValue(5));
		Assert.assertEquals(false, examMarks.containsKey("Jasna"));
		Assert.assertEquals(false, examMarks.containsKey("Kristina"));

		Assert.assertEquals(0, examMarks.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void testNextFromEmptyTable() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.iterator().next();
	}

	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}

		Assert.assertEquals(false, examMarks.containsKey("Ivana"));
		Assert.assertEquals(false, examMarks.containsKey("Ante"));
		Assert.assertEquals(false, examMarks.containsValue(5));
		Assert.assertEquals(false, examMarks.containsKey("Jasna"));
		Assert.assertEquals(false, examMarks.containsKey("Kristina"));

		Assert.assertEquals(0, examMarks.size());

	}

	@Test(expected = ConcurrentModificationException.class)
	public void testConcurrentModification() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testConcurrentModificationTwoRemoves() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}

	}
}
