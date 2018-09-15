package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void testNullValues() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		Assert.assertEquals(Integer.valueOf(0), v1.getValue());
		Assert.assertEquals(null, v2.getValue());
	}

	@Test
	public void testDecimalScientificFormat() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());

		Assert.assertEquals(Double.valueOf(13), v3.getValue());
		Assert.assertEquals(Integer.valueOf(1), v4.getValue());
	}

	@Test
	public void testIntegerAddition() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());

		Assert.assertEquals(Integer.valueOf(13), v5.getValue());
		Assert.assertEquals(Integer.valueOf(1), v6.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testRuntimeExc() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());
	}

	@Test
	public void testCompareValues() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));

		Assert.assertEquals(1, v1.numCompare(v2.getValue()));
	}

	@Test
	public void testCompareEqualValues() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));

		Assert.assertEquals(0, v1.numCompare(v2.getValue()));
	}

	@Test
	public void testCompareGreaterValues() {
		ValueWrapper v1 = new ValueWrapper("11");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));

		Assert.assertEquals(-1, v1.numCompare(v2.getValue()));
	}

	@Test
	public void testCompareIntegerValuesEqual() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(12));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));

		Assert.assertEquals(0, v1.numCompare(v2.getValue()));
	}

	@Test
	public void testCompareIntegerDoubleValuesEqual() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(12));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));

		Assert.assertEquals(0, v1.numCompare(v2.getValue()));
	}

	@Test
	public void testCompareIntegerDoubleGreaterValuesEqual() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(12));

		Assert.assertEquals(1, v1.numCompare(v2.getValue()));
	}

	@Test
	public void subTwoDoubles() {

		ValueWrapper v1 = new ValueWrapper(Double.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(12));
		v1.subtract(v2.getValue());

		Assert.assertEquals(1.0, v1.getValue());
		Assert.assertEquals(Double.valueOf(12), v2.getValue());

	}

	@Test
	public void subIntegerDoubles() {

		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(12));
		v1.subtract(v2.getValue());

		Assert.assertEquals(1.0, v1.getValue());
	}

	@Test
	public void subIntegerInteger() {

		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));
		v1.subtract(v2.getValue());

		Assert.assertEquals(1, v1.getValue());
	}

	@Test
	public void subDoubleInteger() {

		ValueWrapper v1 = new ValueWrapper(Double.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));
		v1.subtract(v2.getValue());

		Assert.assertEquals(1.0, v1.getValue());
	}

	@Test
	public void mulTwoDoubles() {

		ValueWrapper v1 = new ValueWrapper(Double.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(12));
		v1.multiply(v2.getValue());

		Assert.assertEquals(156.0, v1.getValue());
	}

	@Test
	public void mulIntegerDoubles() {

		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(12));
		v1.multiply(v2.getValue());

		Assert.assertEquals(156.0, v1.getValue());
	}

	@Test
	public void mulIntegerInteger() {

		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));
		v1.multiply(v2.getValue());

		Assert.assertEquals(156, v1.getValue());
	}

	@Test
	public void mulDoubleInteger() {

		ValueWrapper v1 = new ValueWrapper(Double.valueOf(13));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));
		v1.multiply(v2.getValue());

		Assert.assertEquals(156.0, v1.getValue());
	}

}
