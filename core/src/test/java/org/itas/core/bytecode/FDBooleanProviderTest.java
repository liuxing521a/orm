package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FDBooleanProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDBooleanProvider.PROVIDER;
		field = clazz.getDeclaredField("isMarry");
	}
	
	@Test
	public void setStatementTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"state.setBoolean(1, isMarry());";
		
		String actual  = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"setMarry(result.getBoolean(\"isMarry\"));";
		
		String actual  = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
