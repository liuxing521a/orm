package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDLongProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDLongProvider.PROVIDER;
		field = clazz.getDeclaredField("hp");
	}
	
	public void setStatementTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"state.setLong(1, getHp());";
		
		String actual  = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
		
	}
	
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"setHp(result.getLong(\"hp\"));";
		
		String actual  = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
