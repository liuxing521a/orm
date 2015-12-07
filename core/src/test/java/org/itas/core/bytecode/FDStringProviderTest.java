package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FDStringProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDStringProvider.PROVIDER;
		field = clazz.getDeclaredField("name");
	}
	
	@Test
	public void setStatementTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"state.setString(1, getName());";
		
		String actual  = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t" +
				"setName(result.getString(\"name\"));";
		
		String actual  = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
