package hr.fer.zemris.java.gui.layouts;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;

public class CalcLayoutTest {
	
	@Test (expected = CalcLayoutException.class)
	public void testInvalidRange() {
		CalcLayout layout = new CalcLayout();
		
		layout.addLayoutComponent(new JLabel("a"), new RCPosition(0, -1));
		
		Assert.assertEquals(true, layout!=null);
	}

	@Test (expected = CalcLayoutException.class)
	public void testInvalidRange2() {
		CalcLayout layout = new CalcLayout();
		
		layout.addLayoutComponent(new JLabel("a"), new RCPosition(1, -4));
		
		Assert.assertEquals(true, layout!=null);
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testInvalidRange3() {
		CalcLayout layout = new CalcLayout();
		
		layout.addLayoutComponent(new JLabel("a"), new RCPosition(6, -4));
		
		Assert.assertEquals(true, layout!=null);
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testInvalidRange4() {
		CalcLayout layout = new CalcLayout();
		
		layout.addLayoutComponent(new JLabel("a"), new RCPosition(4, 8));
		
		Assert.assertEquals(true, layout!=null);
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testInvalidRange5() {
		CalcLayout layout = new CalcLayout();
		
		layout.addLayoutComponent(new JLabel("a"), new RCPosition(1, 2));
		
		Assert.assertEquals(true, layout!=null);
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testInvalidRange6() {
		CalcLayout layout = new CalcLayout();
		
		layout.addLayoutComponent(new JLabel("a"), new RCPosition(1, 5));
		
		Assert.assertEquals(true, layout!=null);
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testInvalidRange7() {
		CalcLayout layout = new CalcLayout();
		
		layout.addLayoutComponent(new JLabel("a"), new RCPosition(1, 6));
		layout.addLayoutComponent(new JLabel("b"), new RCPosition(1, 6));

		Assert.assertEquals(true, layout!=null);
	}
	
	@Test 
	public void prefSizeTest() {
		
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		Assert.assertEquals(152, dim.width);
		Assert.assertEquals(158, dim.height);
	}
	
	@Test 
	public void prefSizeTest2() {
		
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		Assert.assertEquals(152, dim.width);
		Assert.assertEquals(158, dim.height);
	}
}
