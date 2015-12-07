package org.itas.core.bytecode;

import java.util.Arrays;
import java.util.List;

import javassist.ClassPool;
import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;

public class FDDoubleArrayProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		clazz = ClassPool.getDefault().get(Hero.class.getName());
		provider = FDDoubleArrayProvider.PROVIDER;
	}
	
	@Override
	public void typeTest() throws NotFoundException, Exception {
		final CtField[] fields = clazz.getDeclaredFields();
		List<String> list = Arrays.asList("pointDArray", "heroDArray", "heroResDArray", "heroTypeDArray", "itemDArray");
		
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
//		GameObjectNocacheStatementTest();
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
		field = clazz.getDeclaredField("pointDArray");
		typeTest();
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getPointDArray()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void javaBaseStatementTest() throws Exception {
		field = clazz.getDeclaredField("pointDArray");
		
		String expected =	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"pointDArray\");"	+ "\n\t\t\t"
				+ "String[][] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
				+ "int[][] valueList_ = new int[valueArray_.length][valueArray_[0].length];"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "for (int j = 0; j < valueArray_[i].length; j ++) {"	+ "\n\t\t\t\t\t"
				+ "if (valueArray_[i][j] != null && valueArray_[i][j].length > 3)"	+ "\n\t\t\t\t\t"
				+ "valueList_[i][j] = java.lang.Integer.valueOf(valueArray_[i][j]);"	+ "\n\t\t\t\t"
				+ "}" + "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setPointDArray(valueList_);"	+ "\n\t\t"
				+ "}";
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleResultTest() throws Exception {
		field = clazz.getDeclaredField("heroDArray");
		typeTest();
		
		String expected =	"\n\t\t"
				+ "state.setString(1, toString(getHeroDArray()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleStatementTest() throws Exception {
		field = clazz.getDeclaredField("heroDArray");
		
		String expected = "\n\t\t"
				+ "{" + "\n\t\t\t"
				+ "String value_ = result.getString(\"heroDArray\");" + "\n\t\t\t"
				+ "String[][] valueArray_ = parseArray(value_);" + "\n\t\t\t"
				+ "org.itas.core.Simple[][] valueList_ = new org.itas.core.Simple[valueArray_.length][valueArray_[0].length];" + "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {" + "\n\t\t\t\t"
				+ "for (int j = 0; j < valueArray_[i].length; j ++) {" + "\n\t\t\t\t\t"
				+ "if (valueArray_[i][j] != null && valueArray_[i][j].length > 3)" + "\n\t\t\t\t\t"
				+ "valueList_[i][j] = new org.itas.core.Simple(valueArray_[i][j]);" + "\n\t\t\t\t"
				+ "}" + "\n\t\t\t"
				+ "}" + "\n\t\t\t"
				+ "setHeroDArray(valueList_);" + "\n\t\t"
				+ "}";

		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void resourceStatementTest() throws Exception {
		field = clazz.getDeclaredField("heroResDArray");
		typeTest();
		
		String expected = 
				"\n\t\t"
				+ "state.setString(1, toString(getHeroResDArray()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}

	public void resourceResultTest() throws Exception {
		field = clazz.getDeclaredField("heroResDArray");
		
		String expected = "\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroResDArray\");"	+ "\n\t\t\t"
				+ "String[][] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
				+ "org.itas.core.bytecode.HeroRes[][] valueList_ = new org.itas.core.bytecode.HeroRes[valueArray_.length][valueArray_[0].length];"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"		+ "\n\t\t\t\t"
				+ "for (int j = 0; j < valueArray_[i].length; j ++) {"		+ "\n\t\t\t\t\t"
				+ "if (valueArray_[i][j] != null && valueArray_[i][j].length > 3)"		+ "\n\t\t\t\t\t"
				+ "valueList_[i][j] = org.itas.core.Pool.getResource(valueArray_[i][j]);"		+ "\n\t\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setHeroResDArray(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeDArray");
		typeTest();
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getHeroTypeDArray()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeDArray");
		
		String expected = "\n\t\t"
						+ "{"	+ "\n\t\t\t"
						+ "String value_ = result.getString(\"heroTypeDArray\");"	+ "\n\t\t\t"
						+ "String[][] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
						+ "org.itas.core.bytecode.Model.HeroType[][] valueList_ = new org.itas.core.bytecode.Model.HeroType[valueArray_.length][valueArray_[0].length];"	+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
						+ "for (int j = 0; j < valueArray_[i].length; j ++) {"	+ "\n\t\t\t\t\t"
						+ "if (valueArray_[i][j] != null && valueArray_[i][j].length > 3)"	+ "\n\t\t\t\t\t"
						+ "valueList_[i][j] = parse(org.itas.core.bytecode.Model.HeroType.class, valueArray_[i][j]);"	+ "\n\t\t\t\t"
						+ "}"	+ "\n\t\t\t"
						+ "}"	+ "\n\t\t\t"
						+ "setHeroTypeDArray(valueList_);"	+ "\n\t\t"
						+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	public void GameObjectNocacheStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("itemDArray");
		typeTest();
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getItemDArray()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void GameObjectNocacheResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("itemDArray");

		String expected = "\n\t\t"
						+ "{"	+ "\n\t\t\t"
						+ "String value_ = result.getString(\"itemDArray\");" + "\n\t\t\t"
						+ "String[][] valueArray_ = parseArray(value_);"	+ "\n\t\t\t"
						+ "org.itas.core.bytecode.Item[][] valueList_ = new org.itas.core.bytecode.Item[valueArray_.length][valueArray_[0].length];"	+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
						+ "for (int j = 0; j < valueArray_[i].length; j ++) {"	+ "\n\t\t\t\t\t"
						+ "if (valueArray_[i][j] != null && valueArray_[i][j].length > 3)"	+ "\n\t\t\t\t\t"
						+ "valueList_[i][j] = org.itas.core.Pool.getModule(org.itas.core.bytecode.Item.class).clone(valueArray_[i][j]);"	+ "\n\t\t\t\t"
						+ "}"	+ "\n\t\t\t"
						+ "}"	+ "\n\t\t\t"
						+ "setItemDArray(valueList_);" + "\n\t\t"
						+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
}
