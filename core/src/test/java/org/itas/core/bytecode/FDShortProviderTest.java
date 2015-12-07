package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FDShortProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDShortProvider.PROVIDER;
		field = clazz.getDeclaredField("gemCount");
	}
	
	@Test
	public void setStatementTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"state.setShort(1, getGemCount());";
		
		String actual  = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"setGemCount(result.getShort(\"gemCount\"));";
		
		String actual  = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
