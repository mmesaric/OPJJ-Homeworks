package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class FieldValueGettersTest {
	
	@Test
	public void testFieldValueGetter() {
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);

		Assert.assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(record));
		Assert.assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(record));
		Assert.assertEquals("0000000001", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void testFieldValueGetter2() {
		StudentRecord record = new StudentRecord("0000000017", "Grđan", "Goran", 2);
		
		Assert.assertEquals("Goran", FieldValueGetters.FIRST_NAME.get(record));
		Assert.assertEquals("Grđan", FieldValueGetters.LAST_NAME.get(record));
		Assert.assertEquals("0000000017", FieldValueGetters.JMBAG.get(record));
	}

}
