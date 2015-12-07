package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDEnumByteProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDEnumByteProvider.PROVIDER;
		field = clazz.getDeclaredField("sexType");
	}
	
	@Override
	public void setStatementTest() throws Exception {
		String expected =  "\n\t\t"
				+ "{" + "\n\t\t\t" 
				+ "byte value_ = 0;"	+ "\n\t\t\t"
				+ "if (getSexType() != null) {"  + "\n\t\t\t\t" 
				+ "value_ = getSexType().key();"  + "\n\t\t\t" 
				+ "}" + "\n\t\t\t" 
				+ "state.setByte(1, value_);"  + "\n\t\t" 
				+ "}";
			
			String actual = provider.setStatement(1, field);
			Assert.assertEquals(expected, actual);
	}

	@Override
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t"
				+ "setSexType(parse(org.itas.core.bytecode.Model.SexType.class, result.getByte(\"sexType\")));";
		
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
