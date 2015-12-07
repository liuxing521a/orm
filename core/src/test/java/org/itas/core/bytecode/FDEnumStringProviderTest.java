package org.itas.core.bytecode;

import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDEnumStringProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDEnumStringProvider.PROVIDER;
		field = clazz.getDeclaredField("skillType");
	}
	
	@Override
	public void setStatementTest() throws Exception {
		String expected =  "\n\t\t"
				+ "{" + "\n\t\t\t" 
				+ "String value_ = \"\";"	+ "\n\t\t\t"
				+ "if (getSkillType() != null) {"  + "\n\t\t\t\t" 
				+ "value_ = getSkillType().key();"  + "\n\t\t\t" 
				+ "}" + "\n\t\t\t" 
				+ "state.setString(1, value_);"  + "\n\t\t" 
				+ "}";
			
			String actual = provider.setStatement(1, field);
			Assert.assertEquals(expected, actual);
	}

	@Override
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t"
				+ "setSkillType(parse(org.itas.core.bytecode.Model.SkillType.class, result.getString(\"skillType\")));";
		
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
