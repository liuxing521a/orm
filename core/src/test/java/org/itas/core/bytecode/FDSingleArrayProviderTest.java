package org.itas.core.bytecode;

import java.util.Arrays;
import java.util.List;

import javassist.ClassPool;
import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDSingleArrayProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		clazz = ClassPool.getDefault().get(Hero.class.getName());
		provider = FDSingleArrayProvider.PROVIDER;
	}
	
	@Override
	public void typeTest() throws NotFoundException, Exception {
		final CtField[] fields = clazz.getDeclaredFields();
		List<String> list = Arrays.asList("pointArray", "heroArray", "heroResArray", "heroTypeArray", "itemArray");
		for (CtField field : fields) {
			if (list.contains(field.getName())) {
				Assert.assertEquals(true, provider.isType(field.getType()));
			} else {
				Assert.assertEquals(false, provider.isType(field.getType()));
			}
		}
	}
	
	@Override
	public void setStatementTest() throws Exception {
		javaBaseStatementTest();
		simpleStatementTest();
		resourceStatementTest();
		enumStatementTest();
		GameObjectNocacheStatementTest();
	}

	@Override
	public void getResultSetTest() throws Exception {
		javaBaseResultTest();
		simpleResultTest();
		resourceResultTest();
		enumResultTest();
//		GameObjectNocacheResultTest();
	}
	
	public void javaBaseResultTest() throws Exception {
		field = clazz.getDeclaredField("pointArray");
		typeTest();
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getPointArray()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void javaBaseStatementTest() throws Exception {
		field = clazz.getDeclaredField("pointArray");
		
		String expected =	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"pointArray\");"	+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
				+ "int[] valueList_ = new int[valueArray_.length];"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "if (valueArray_[i] != null && valueArray_[i].length > 3)"	+ "\n\t\t\t\t"
				+ "valueList_[i] = java.lang.Integer.valueOf(valueArray_[i]);"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setPointArray(valueList_);"	+ "\n\t\t"
				+ "}";
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleResultTest() throws Exception {
		field = clazz.getDeclaredField("heroArray");
		typeTest();
		
		String expected =	"\n\t\t"
				+ "state.setString(1, toString(getHeroArray()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleStatementTest() throws Exception {
		field = clazz.getDeclaredField("heroArray");
		
		String expected = "\n\t\t"
				+ "{" + "\n\t\t\t"
				+ "String value_ = result.getString(\"heroArray\");" + "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);" + "\n\t\t\t"
				+ "org.itas.core.Simple[] valueList_ = new org.itas.core.Simple[valueArray_.length];" + "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {" + "\n\t\t\t\t"
				+ "if (valueArray_[i] != null && valueArray_[i].length > 3)" + "\n\t\t\t\t"
				+ "valueList_[i] = new org.itas.core.Simple(valueArray_[i]);" + "\n\t\t\t"
				+ "}" + "\n\t\t\t"
				+ "setHeroArray(valueList_);" + "\n\t\t"
				+ "}";

		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void resourceStatementTest() throws Exception {
		field = clazz.getDeclaredField("heroResArray");
		typeTest();
		
		String expected = 
				"\n\t\t"
				+ "state.setString(1, toString(getHeroResArray()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}

	public void resourceResultTest() throws Exception {
		field = clazz.getDeclaredField("heroResArray");
		
		String expected = "\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroResArray\");"	+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
				+ "org.itas.core.bytecode.HeroRes[] valueList_ = new org.itas.core.bytecode.HeroRes[valueArray_.length];"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"		+ "\n\t\t\t\t"
				+ "if (valueArray_[i] != null && valueArray_[i].length > 3)"		+ "\n\t\t\t\t"
				+ "valueList_[i] = org.itas.core.Pool.getResource(valueArray_[i]);"		+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setHeroResArray(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeArray");
		typeTest();
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getHeroTypeArray()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeArray");
		
		String expected = "\n\t\t"
						+ "{"	+ "\n\t\t\t"
						+ "String value_ = result.getString(\"heroTypeArray\");"	+ "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
						+ "org.itas.core.bytecode.Model.HeroType[] valueList_ = new org.itas.core.bytecode.Model.HeroType[valueArray_.length];"	+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
						+ "if (valueArray_[i] != null && valueArray_[i].length > 3)"	+ "\n\t\t\t\t"
						+ "valueList_[i] = parse(org.itas.core.bytecode.Model.HeroType.class, valueArray_[i]);"	+ "\n\t\t\t"
						+ "}"	+ "\n\t\t\t"
						+ "setHeroTypeArray(valueList_);"	+ "\n\t\t"
						+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	public void GameObjectNocacheStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("itemArray");
		typeTest();
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getItemArray()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void GameObjectNocacheResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("itemArray");
		
		String expected = "\n\t\t"
						+ "{"	+ "\n\t\t\t"
						+ "String value_ = result.getString(\"itemArray\");" + "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
						+ "org.itas.core.bytecode.Item[] valueList_ = new org.itas.core.bytecode.Item[valueArray_.length];"	+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
						+ "if (valueArray_[i] != null && valueArray_[i].length > 3)"	+ "\n\t\t\t\t"
						+ "valueList_[i] = org.itas.core.Pool.getModule(org.itas.core.bytecode.Item.class).clone(valueArray_[i]);"	+ "\n\t\t\t"
						+ "}"	+ "\n\t\t\t"
						+ "setItemArray(valueList_);" + "\n\t\t"
						+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
}
