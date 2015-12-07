//package org.itas.core.bytecode;
//
//import javassist.ClassPool;
//import javassist.NotFoundException;
//import junit.framework.Assert;
//
//import org.junit.Before;
//
//public class FDGameObjectNoCacheProviderTest extends AbstreactFieldProvider {
//	
//	@Before
//	public void setUP() throws NotFoundException {
//		clazz = ClassPool.getDefault().get(Hero.class.getName());
//		provider = FDGameObjectNoCacheProvider.PROVIDER;
//		field = clazz.getDeclaredField("item");
//	}
//	
//	@Override
//	public void setStatementTest() throws Exception {
//		String expected = "\n\t\t" + 
//				"{" + "\n\t\t\t" +
//				"String value_ = \"\";" + "\n\t\t\t" +
//				"if (getItem() != null) {" + "\n\t\t\t\t" +
//				"value_ = getItem().getId();" +  "\n\t\t\t" +
//				"}" + "\n\t\t\t" +
//				"state.setString(1, value_);" + "\n\t\t" +
//				"}";
//			
//		String actual = provider.setStatement(1, field);
//		Assert.assertEquals(expected, actual);
//	}
//
//	@Override
//	public void getResultSetTest() throws Exception {
//		String expected = "\n\t\t" +
//				"{" + "\n\t\t\t" + 
//				"String value_ = result.getString(\"item\");" + "\n\t\t\t" + 
//				"if (value_ != null && value_.length() > 3) {"+ "\n\t\t\t\t" +
//				"setItem(org.itas.core.Pool.getModule(org.itas.core.bytecode.Item.class).clone(value_));" + "\n\t\t\t" +
//				"}" + "\n\t\t" +
//				"}";
//		String actual = provider.getResultSet(field);
//		Assert.assertEquals(expected, actual);
//	}
//	
//}
