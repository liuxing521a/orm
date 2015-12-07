package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDResourceProviderTest extends AbstreactFieldProvider {
	
	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDResourceProvider.PROVIDER;
		field = clazz.getDeclaredField("heroRes");
	}
	
	@Override
	public void setStatementTest() throws Exception {
		String expected = "\n\t\t"
				+ "{" + "\n\t\t\t"
				+ "String value_ = \"\";" + "\n\t\t\t"  
				+ "if (getHeroRes() != null) {" + "\n\t\t\t\t" 
				+ "value_ = getHeroRes().getId();"  + "\n\t\t\t"
				+ "}" + "\n\t\t\t" 
				+ "state.setString(1, value_);" + "\n\t\t"
				+ "}";

		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}

	@Override
	public void getResultSetTest() throws Exception {
		String expected = "\n\t\t"
				+ "{" + "\n\t\t\t"
				+ "String value_ = result.getString(\"heroRes\");" + "\n\t\t\t" 
				+ "if (value_ != null && value_.length() > 0) {"  + "\n\t\t\t\t" 
				+ "setHeroRes(org.itas.core.Pool.getResource(value_));" + "\n\t\t\t" 
				+ "}" + "\n\t\t"
				+ "}";
		
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
