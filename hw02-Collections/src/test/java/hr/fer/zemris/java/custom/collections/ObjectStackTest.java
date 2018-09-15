package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class ObjectStackTest {

	@Test
	public void pushAndPop() {

		ObjectStack objectStack = new ObjectStack();

		objectStack.push("test1");
		objectStack.push("test2");
		objectStack.push("test3");

		Assert.assertEquals("test3", objectStack.pop());
		Assert.assertEquals("test2", objectStack.pop());
		Assert.assertEquals(1, objectStack.size());
		Assert.assertEquals("test1", objectStack.pop());
		Assert.assertEquals(0, objectStack.size());

		objectStack.push("test1");
		objectStack.push("test2");
		objectStack.push("test3");
		
		Assert.assertEquals("test3", objectStack.peek());
		Assert.assertEquals(3, objectStack.size());

	}

}
