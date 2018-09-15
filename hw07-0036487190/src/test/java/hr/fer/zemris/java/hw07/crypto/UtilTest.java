package hr.fer.zemris.java.hw07.crypto;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

	@Test
	public void hexToByteTest1() {
		byte[] bytes = Util.hexToByte("01aE22");

		Assert.assertEquals(1, bytes[0]);
		Assert.assertEquals(-82, bytes[1]);
		Assert.assertEquals(34, bytes[2]);
	}

	@Test
	public void testEmptyHexToByte() {
		byte[] bytes = Util.hexToByte("");

		Assert.assertEquals(0, bytes.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidHexToByte() {
		byte[] bytes = Util.hexToByte("ž");

		Assert.assertEquals(0, bytes.length);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidHexToByte2() {
		byte[] bytes = Util.hexToByte("g");

		Assert.assertEquals(0, bytes.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidHexToByte3() {
		byte[] bytes = Util.hexToByte("01aE22e");

		Assert.assertEquals(1, bytes[0]);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidHexToByte4() {
		byte[] bytes = Util.hexToByte("G");

		Assert.assertEquals(0, bytes.length);
	}

	@Test
	public void byteToHex() {
		Assert.assertEquals("01ae22", Util.byteToHex(new byte[] { 1, -82, 34 }));
	}

	@Test
	public void testEmptyArray() {
		Assert.assertEquals("", Util.byteToHex(new byte[] {}));
	}
}
