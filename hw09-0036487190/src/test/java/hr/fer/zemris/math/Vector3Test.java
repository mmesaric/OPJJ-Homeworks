package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector3Test {

	@Test
	public void testConstruct() {
		Vector3 i = new Vector3(1,0,0);
		
		Assert.assertEquals(i.getX(), 1, 10e-3);
		Assert.assertEquals(i.getY(), 0, 10e-3);
		Assert.assertEquals(i.getZ(), 0, 10e-3);
	}
	
	@Test
	public void testConstruct2() {
		Vector3 i = new Vector3(0,1,0);
		
		Assert.assertEquals(i.getX(), 0, 10e-3);
		Assert.assertEquals(i.getY(), 1, 10e-3);
		Assert.assertEquals(i.getZ(), 0, 10e-3);
	}
	
	@Test
	public void testCross() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		
		Assert.assertEquals(k.getX(), 0, 10e-3);
		Assert.assertEquals(k.getY(), 0, 10e-3);
		Assert.assertEquals(k.getZ(), 1, 10e-3);
	}
	
	@Test
	public void testCross2() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		
		Assert.assertEquals(l.getX(), 0, 10e-3);
		Assert.assertEquals(l.getY(), 5, 10e-3);
		Assert.assertEquals(l.getZ(), 5, 10e-3);
	}
	
	@Test
	public void testCross3() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		
		Assert.assertEquals(l.norm(), 7.0710678118654755, 10e-3);
	}

	@Test
	public void testCross4() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		Vector3 m = l.normalized();
		
		Assert.assertEquals(m.getX(), 0, 10e-3);
		Assert.assertEquals(m.getY(), 0.707107, 10e-3);
		Assert.assertEquals(m.getZ(), 0.707107, 10e-3);
	}
	
	@Test
	public void testCross5() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		
		Assert.assertEquals(l.dot(j), 5, 10e-3);
	}
}
