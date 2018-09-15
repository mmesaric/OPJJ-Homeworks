package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class PrimListModelTest {
	
	@Test
	public void testInitial() {
		PrimListModel model = new PrimListModel();
		
		Assert.assertEquals(1, model.getSize());
		assertEquals(Integer.valueOf(2), model.getElementAt(0));
	}
	
	@Test
	public void testNext() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		
		Assert.assertEquals(2, model.getSize());
		assertEquals(Integer.valueOf(2), model.getElementAt(0));
		assertEquals(Integer.valueOf(3), model.getElementAt(1));

	}
	
	@Test
	public void testNext2() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		model.next();

		Assert.assertEquals(3, model.getSize());
		assertEquals(Integer.valueOf(2), model.getElementAt(0));
		assertEquals(Integer.valueOf(3), model.getElementAt(1));
		assertEquals(Integer.valueOf(5), model.getElementAt(2));
	}
	
	@Test
	public void testNext3() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		model.next();
		model.next();

		Assert.assertEquals(4, model.getSize());
		assertEquals(Integer.valueOf(2), model.getElementAt(0));
		assertEquals(Integer.valueOf(3), model.getElementAt(1));
		assertEquals(Integer.valueOf(5), model.getElementAt(2));
		assertEquals(Integer.valueOf(7), model.getElementAt(3));
	}
	
	@Test
	public void testNext4() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		model.next();
		model.next();
		model.next();

		Assert.assertEquals(5, model.getSize());
		assertEquals(Integer.valueOf(2), model.getElementAt(0));
		assertEquals(Integer.valueOf(3), model.getElementAt(1));
		assertEquals(Integer.valueOf(5), model.getElementAt(2));
		assertEquals(Integer.valueOf(7), model.getElementAt(3));
		assertEquals(Integer.valueOf(11), model.getElementAt(4));
	}
	
	@Test
	public void testNext5() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();

		Assert.assertEquals(6, model.getSize());
		assertEquals(Integer.valueOf(2), model.getElementAt(0));
		assertEquals(Integer.valueOf(3), model.getElementAt(1));
		assertEquals(Integer.valueOf(5), model.getElementAt(2));
		assertEquals(Integer.valueOf(7), model.getElementAt(3));
		assertEquals(Integer.valueOf(11), model.getElementAt(4));
		assertEquals(Integer.valueOf(13), model.getElementAt(5));

	}
}
