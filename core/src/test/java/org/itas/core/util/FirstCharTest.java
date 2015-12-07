package org.itas.core.util;

import junit.framework.Assert;

import org.itas.core.util.FirstChar;
import org.junit.Test;

public class FirstCharTest implements FirstChar {

	@Test
	public void testFirstKeyUPCase() {
		String result = upCase("hello");
		Assert.assertEquals("Hello", result);

		result = upCase("Hello");
		Assert.assertEquals("Hello", result);
	}

	@Test
	public void testFirstKeyLowerCase() {
		String result = lowerCase("hello");
		Assert.assertEquals("hello", result);

		result = lowerCase("Hello");
		Assert.assertEquals("hello", result);
	}

}
