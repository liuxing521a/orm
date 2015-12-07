package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDSimpleProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDSimpleProvider.PROVIDER;
		field = clazz.getDeclaredField("heroS");
	}
	
	@Override
	public void setStatementTest() throws Exception {
		String expected = "\n\t\t"
				+ "{" + "\n\t\t\t"
				+ "String value_ = \"\";" + "\n\t\t\t" 
				+ "if (getHeroS() != null) {"  + "\n\t\t\t\t" 
				+ "value_ = getHeroS().getId();"  + "\n\t\t\t" 
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
				+ "String value_ = result.getString(\"heroS\");" + "\n\t\t\t"
				+ "if (value_ != null && value_.length() > 0) {"  + "\n\t\t\t\t"
				+ "setHeroS(new org.itas.core.Simple(value_));" + "\n\t\t\t" 
				+ "}" + "\n\t\t" 
				+ "}";
		
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
