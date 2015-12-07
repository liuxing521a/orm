package org.itas.core.bytecode;

import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.bytecode.AbstractFieldProvider.javassistType;
import org.junit.Before;

public class FDListProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDListProvider.PROVIDER;
	}
	
	@Override
	public void typeTest() throws NotFoundException, Exception {
		final CtField[] fields = clazz.getDeclaredFields();
		for (CtField field : fields) {
			if (field == this.field || javassistType.list_.subtypeOf(field.getType())) {
				Assert.assertEquals(true, provider.isType(field.getType()));
			} else {
				Assert.assertEquals(false, provider.isType(field.getType()));
			}
		}
	}
	
	@Override
	public void setStatementTest() throws Exception {
		simpleStatementTest();
		javaBaseStatementTest();
		resourceStatementTest();
		enumStatementTest();
		enumbyteStatementTest();
		enumIntStatementTest();
		enumStringStatementTest();
	}

	@Override
	public void getResultSetTest() throws Exception {
		javaBaseResultTest();
		simpleResultTest();
		resourceResultTest();
		enumResultTest();
		enumbyteResultTest();
		enumIntResultTest();
		enumStringResultTest();
	}
	
	public void javaBaseResultTest() throws Exception {
		field = clazz.getDeclaredField("points");
		typeTest();
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getPoints()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void javaBaseStatementTest() throws Exception {
		field = clazz.getDeclaredField("points");
		
		String expected = 
				"\n\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String value_ = result.getString(\"points\");"
				+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"
				+ "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.LinkedList();"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "valueList_.add(java.lang.Integer.valueOf(valueArray_[i]));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setPoints(valueList_);"
				+ "\n\t\t"
				+ "}";
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleResultTest() throws Exception {
		field = clazz.getDeclaredField("depotS");
		typeTest();
		
		String expected =	"\n\t\t"
				+ "state.setString(1, toString(getDepotS()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleStatementTest() throws Exception {
		field = clazz.getDeclaredField("depotS");
		
		String expected = "\n\t\t"
				+ "{" + "\n\t\t\t"
				+ "String value_ = result.getString(\"depotS\");" + "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);" + "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.ArrayList(8);" + "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {" + "\n\t\t\t\t"
				+ "valueList_.add(new org.itas.core.Simple(valueArray_[i]));" + "\n\t\t\t"
				+ "}" + "\n\t\t\t"
				+ "setDepotS(valueList_);" + "\n\t\t"
				+ "}";

		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void resourceStatementTest() throws Exception {
		field = clazz.getDeclaredField("heroResList");
		typeTest();
		
		String expected = 
				"\n\t\t"
				+ "state.setString(1, toString(getHeroResList()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}

	public void resourceResultTest() throws Exception {
		field = clazz.getDeclaredField("heroResList");
		
		String expected = 
				"\n\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroResList\");"
				+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"
				+ "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.ArrayList(16);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "valueList_.add(org.itas.core.Pool.getResource(valueArray_[i]));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setHeroResList(valueList_);"
				+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeList");
		typeTest();
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getHeroTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeList");
		
		String expected = 
				"\n\t\t"
						+ "{"
						+ "\n\t\t\t"
						+ "String value_ = result.getString(\"heroTypeList\");"
						+ "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"
						+ "\n\t\t\t"
						+ "java.util.List valueList_ = new java.util.ArrayList(8);"
						+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"
						+ "\n\t\t\t\t"
						+ "valueList_.add(parse(org.itas.core.bytecode.Model.HeroType.class, valueArray_[i]));"
						+ "\n\t\t\t"
						+ "}"
						+ "\n\t\t\t"
						+ "setHeroTypeList(valueList_);"
						+ "\n\t\t"
						+ "}";
		//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	public void enumbyteStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexTypeList");
		typeTest();
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getSexTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumbyteResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexTypeList");
		
		String expected = 
				"\n\t\t"
						+ "{"
						+ "\n\t\t\t"
						+ "String value_ = result.getString(\"sexTypeList\");"
						+ "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"
						+ "\n\t\t\t"
						+ "java.util.List valueList_ = new java.util.ArrayList(8);"
						+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"
						+ "\n\t\t\t\t"
						+ "valueList_.add(parse(org.itas.core.bytecode.Model.SexType.class, java.lang.Byte.valueOf(valueArray_[i])));"
						+ "\n\t\t\t"
						+ "}"
						+ "\n\t\t\t"
						+ "setSexTypeList(valueList_);"
						+ "\n\t\t"
						+ "}";
		//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumIntStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("effectTypeList");
		typeTest();
		
		String expected = 
				"\n\t\t"
				+ "state.setString(1, toString(getEffectTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumIntResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("effectTypeList");
		
		String expected = 
				"\n\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String value_ = result.getString(\"effectTypeList\");"
				+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"
				+ "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.ArrayList(8);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "valueList_.add(parse(org.itas.core.bytecode.Model.Effect.class, java.lang.Integer.valueOf(valueArray_[i])));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setEffectTypeList(valueList_);"
				+ "\n\t\t"
				+ "}";
				//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStringStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("skillTypeList");
		typeTest();
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getSkillTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStringResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("skillTypeList");
		
		String expected = 
				"\n\t\t"
						+ "{"
						+ "\n\t\t\t"
						+ "String value_ = result.getString(\"skillTypeList\");"
						+ "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"
						+ "\n\t\t\t"
						+ "java.util.List valueList_ = new java.util.ArrayList(8);"
						+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"
						+ "\n\t\t\t\t"
						+ "valueList_.add(parse(org.itas.core.bytecode.Model.SkillType.class, valueArray_[i]));"
						+ "\n\t\t\t"
						+ "}"
						+ "\n\t\t\t"
						+ "setSkillTypeList(valueList_);"
						+ "\n\t\t"
						+ "}";
		//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
}
