package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDEnumIntProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDEnumIntProvider.PROVIDER;
		field = clazz.getDeclaredField("effect");
	}
	
	@Override
	public void setStatementTest() throws Exception {
		String expected =  "\n\t\t"
				+ "{" + "\n\t\t\t" 
				+ "int value_ = 0;"	+ "\n\t\t\t"
				+ "if (getEffect() != null) {"  + "\n\t\t\t\t" 
				+ "value_ = getEffect().key();"  + "\n\t\t\t" 
				+ "}" + "\n\t\t\t" 
				+ "state.setInt(1, value_);"  + "\n\t\t" 
				+ "}";
			
			String actual = provider.setStatement(1, field);
			Assert.assertEquals(expected, actual);
	}

	@Override
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t"
				+ "setEffect(parse(org.itas.core.bytecode.Model.Effect.class, result.getInt(\"effect\")));";
		
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
