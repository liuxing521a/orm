package org.itas.core.bytecode;

import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FDEnumProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDEnumProvider.PROVIDER;
		field = clazz.getDeclaredField("heroType");
	}
	
	@Test
	public void typeTest() throws NotFoundException, Exception {
		final CtField[] fields = clazz.getDeclaredFields();
		for (CtField field : fields) {
			if (field == this.field || Type.enumType.isType(field.getType())) {
				Assert.assertEquals(true, provider.isType(field.getType()));
			} else {
				Assert.assertEquals(false, provider.isType(field.getType()));
			}
		}
	}
	
	@Override
	public void setStatementTest() throws Exception {
		String expected =  "\n\t\t"
				+ "{" + "\n\t\t\t" 
				+ "String value_ = \"\";"	+ "\n\t\t\t"
				+ "if (getHeroType() != null) {"  + "\n\t\t\t\t" 
				+ "value_ = getHeroType().name();"  + "\n\t\t\t" 
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
				+ "setHeroType(parse(org.itas.core.bytecode.Model.HeroType.class, result.getString(\"heroType\")));";
		
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
