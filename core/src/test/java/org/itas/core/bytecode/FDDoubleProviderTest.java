package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FDDoubleProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDDoubleProvider.PROVIDER;
		field = clazz.getDeclaredField("money");
	}
	
	@Test
	public void setStatementTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"state.setDouble(1, getMoney());";
		
		String actual  = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"setMoney(result.getDouble(\"money\"));";
		
		String actual  = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
