package org.itas.core.bytecode;

import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.bytecode.AbstractFieldProvider.javassistType;
import org.junit.Before;

public class FDMapProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDMapProvider.PROVIDER;
	}
	
	@Override
	public void typeTest() throws NotFoundException, Exception {
		final CtField[] fields = clazz.getDeclaredFields();
		for (CtField field : fields) {
			if (field == this.field || javassistType.map_.subtypeOf(field.getType())) {
				Assert.assertEquals(true, provider.isType(field.getType()));
			} else {
				Assert.assertEquals(false, provider.isType(field.getType()));
			}
		}
	}
	
	@Override
	public void setStatementTest() throws Exception {
		javaBaseStatementTest();
		resourceStatementTest();
		enumStatementTest();
		simpleStatmentTest();
	}

	@Override
	public void getResultSetTest() throws Exception {
		javaBaseResultTest();
		resourceResultTest();
		enumResultTest();
		simpleResultTest();
	}
	
	public void javaBaseStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroGroups");
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getHeroGroups()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void javaBaseResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroGroups");
		String expected =	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroGroups\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.HashMap(8);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(java.lang.Integer.valueOf((String)pair.getKey()), new org.itas.core.Simple((String)pair.getValue()));"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setHeroGroups(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void resourceStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroResMap");
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getHeroResMap()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void resourceResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroResMap");

		String expected = "\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroResMap\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.HashMap(16);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(org.itas.core.Pool.getResource((String)pair.getKey()), java.lang.Integer.valueOf((String)pair.getValue()));"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setHeroResMap(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexCoinMap");
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getSexCoinMap()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexCoinMap");
		
		String expected = 	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"sexCoinMap\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.HashMap(8);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(parse(org.itas.core.bytecode.Model.SexType.class, java.lang.Byte.valueOf((String)pair.getKey())), java.lang.Float.valueOf((String)pair.getValue()));"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setSexCoinMap(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void simpleStatmentTest() throws Exception {
		CtField field = clazz.getDeclaredField("cardGroupNames");
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getCardGroupNames()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void simpleResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("cardGroupNames");
		
		String expected = 	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"cardGroupNames\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.LinkedHashMap(8);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(new org.itas.core.Simple((String)pair.getKey()), (String)pair.getValue());"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setCardGroupNames(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}

	
}
