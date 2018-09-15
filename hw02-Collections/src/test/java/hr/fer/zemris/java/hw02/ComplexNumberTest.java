package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberTest {

	@Test
	public void testParse() {

		ComplexNumber expected = new ComplexNumber(-542, -423);

		ComplexNumber got = ComplexNumber.parse("-542-423i");

		Assert.assertEquals(expected.getReal(), got.getReal(), 0);
		Assert.assertEquals(expected.getImaginary(), got.getImaginary(), 0);
		Assert.assertEquals(expected.getAngle(), got.getAngle(), 0);

		ComplexNumber expected2 = new ComplexNumber(0, 1);
		ComplexNumber got2 = ComplexNumber.parse("i");

		Assert.assertEquals(expected2.getReal(), got2.getReal(), 0);
		Assert.assertEquals(expected2.getImaginary(), got2.getImaginary(), 0);

	}

	@Test
	public void parse2() {
		ComplexNumber expected = new ComplexNumber(3.51, 0);

		ComplexNumber got = ComplexNumber.parse("3.51");

		Assert.assertEquals(expected.getReal(), got.getReal(), 0);
		Assert.assertEquals(expected.getImaginary(), got.getImaginary(), 0);

		ComplexNumber expected2 = new ComplexNumber(-3.17, 0);

		ComplexNumber got2 = ComplexNumber.parse("-3.17");

		Assert.assertEquals(expected2.getReal(), got2.getReal(), 0);
		Assert.assertEquals(expected2.getImaginary(), got2.getImaginary(), 0);

	}

	@Test
	public void parse3() {
		ComplexNumber expected = new ComplexNumber(0, -2.71);

		ComplexNumber got = ComplexNumber.parse("-2.71i");

		Assert.assertEquals(expected.getReal(), got.getReal(), 0);
		Assert.assertEquals(expected.getImaginary(), got.getImaginary(), 0);

		ComplexNumber expected2 = new ComplexNumber(0, 2.71);

		ComplexNumber got2 = ComplexNumber.parse("2.71i");

		Assert.assertEquals(expected2.getReal(), got2.getReal(), 0);
		Assert.assertEquals(expected2.getImaginary(), got2.getImaginary(), 0);

	}

	@Test
	public void parse4() {
		ComplexNumber expected = new ComplexNumber(0, 1);

		ComplexNumber got = ComplexNumber.parse("i");

		Assert.assertEquals(expected.getReal(), got.getReal(), 0);
		Assert.assertEquals(expected.getImaginary(), got.getImaginary(), 0);

		ComplexNumber expected2 = new ComplexNumber(1, 0);

		ComplexNumber got2 = ComplexNumber.parse("1");

		Assert.assertEquals(expected2.getReal(), got2.getReal(), 0);
		Assert.assertEquals(expected2.getImaginary(), got2.getImaginary(), 0);
	}

	@Test
	public void parseDecimal() {
		ComplexNumber expected = new ComplexNumber(110.1, 2.125);

		ComplexNumber got = ComplexNumber.parse("110.1+2.125i");

		Assert.assertEquals(expected.getReal(), got.getReal(), 0);
		Assert.assertEquals(expected.getImaginary(), got.getImaginary(), 0);
	}

	@Test
	public void fromRealAndImaginary() {
		ComplexNumber num = ComplexNumber.fromReal(5.4);

		Assert.assertEquals(5.4, num.getReal(), 0);
		Assert.assertEquals(0, num.getImaginary(), 0);

		ComplexNumber num2 = ComplexNumber.fromImaginary(5.4);

		Assert.assertEquals(0, num2.getReal(), 0);
		Assert.assertEquals(5.4, num2.getImaginary(), 0);

	}

	@Test
	public void fromMagnitudeAndAngle() {

		ComplexNumber c3 = ComplexNumber.fromMagnitudeAndAngle(21, 0.92);

		Assert.assertEquals(12.72, c3.getReal(), 10E-2);
		Assert.assertEquals(16.71, c3.getImaginary(), 10E-2);
		Assert.assertEquals(21, c3.getMagnitude(), 10E-2);

	}

	@Test
	public void getAngle() {
		ComplexNumber c3 = new ComplexNumber(12.72, 16.71);

		Assert.assertEquals(0.92, c3.getAngle(), 10E-3);

	}

	@Test
	public void addSub() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		ComplexNumber c2 = new ComplexNumber(3, 4);

		ComplexNumber c3 = c1.add(c2);

		Assert.assertEquals(6, c3.getReal(), 10E-2);
		Assert.assertEquals(8, c3.getImaginary(), 10E-2);

		ComplexNumber c4 = c3.sub(c2);

		Assert.assertEquals(3, c4.getReal(), 10E-2);
		Assert.assertEquals(4, c4.getImaginary(), 10E-2);
	}

	@Test
	public void mulDiv() {
		ComplexNumber c1 = new ComplexNumber(3, 2);
		ComplexNumber c2 = new ComplexNumber(1, 4);

		ComplexNumber c3 = c1.mul(c2);

		Assert.assertEquals(-5, c3.getReal(), 10E-2);
		Assert.assertEquals(14, c3.getImaginary(), 10E-2);

		ComplexNumber c4 = c1.div(c2);

		Assert.assertEquals((double) 11 / 17, c4.getReal(), 10E-2);
		Assert.assertEquals((double) -10 / 17, c4.getImaginary(), 10E-2);

	}

	@Test
	public void power() {
		ComplexNumber c1 = new ComplexNumber(3, 5);
		ComplexNumber c2 = c1.power(5);

		Assert.assertEquals(2868, c2.getReal(), 10E-2);
		Assert.assertEquals(-6100, c2.getImaginary(), 10E-2);

	}

	@Test
	public void secondRoot() {
		ComplexNumber c1 = new ComplexNumber(3, 5);

		ComplexNumber[] array = c1.root(2);

		Assert.assertEquals(2.1013034, array[0].getReal(), 10E-6);
		Assert.assertEquals(1.1897378, array[0].getImaginary(), 10E-6);
		Assert.assertEquals(-2.1013034, array[1].getReal(), 10E-6);
		Assert.assertEquals(-1.1897378, array[1].getImaginary(), 10E-6);

	}

}
