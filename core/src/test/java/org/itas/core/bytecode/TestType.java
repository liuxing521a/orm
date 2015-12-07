//package org.itas.core.bytecode;
//
//import java.sql.Timestamp;
//
//import javassist.ClassPool;
//import javassist.CtClass;
//import junit.framework.Assert;
//
//import org.itas.core.Simple;
//import org.junit.Before;
//import org.junit.Test;
//
//public class TestType {
//	
//	private ClassPool pool;
//	
//	@Before
//	public void setUP() {
//		pool = ClassPool.getDefault();
//	}
//	
//	@Test
//	public void testBooleanType() throws Exception {
//		Class<?> clazz = boolean.class;
//		CtClass ctClass = pool.get(Boolean.class.getName());
//		Assert.assertEquals(true, Type.booleanType.is(clazz));
//		Assert.assertEquals(true, Type.booleanType.is(ctClass));
//	}
//	
//	@Test
//	public void testByteType() throws Exception {
//		Class<?> clazz = byte.class;
//		CtClass ctClass = pool.get(Byte.class.getName());
//		Assert.assertEquals(true, Type.byteType.is(clazz));
//		Assert.assertEquals(true, Type.byteType.is(ctClass));
//	}
//	
//	@Test
//	public void testString() throws Exception {
//		Class<?> clazz = String.class;
//		CtClass ctClass = pool.get(String.class.getName());
//		Assert.assertEquals(true, Type.stringType.is(clazz));
//		Assert.assertEquals(true, Type.stringType.is(ctClass));
//	}
//	
//	@Test
//	public void testEnum() throws Exception {
//		Class<?> clazz = TestEnum.class;
//		CtClass ctClass = pool.get(TestEnum.class.getName());
//		Assert.assertEquals(true, Type.enumByteType.is(clazz));
//		Assert.assertEquals(true, Type.enumByteType.is(ctClass));
//	}
//
//	@Test
//	public void testSimple() throws Exception {
//		Class<?> clazz = Simple.class;
//		CtClass ctClass = pool.get(Simple.class.getName());
//		Assert.assertEquals(true, Type.simpleType.is(clazz));
//		Assert.assertEquals(true, Type.simpleType.is(ctClass));
//	}
//	
//	@Test
//	public void testResource() throws Exception {
//		Class<?> clazz = Res.class;
//		CtClass ctClass = pool.get(Res.class.getName());
//		
//		Assert.assertEquals(true, Type.resourceType.is(clazz));
//		Assert.assertEquals(true, Type.resourceType.is(ctClass));
//	}
//	
//	@Test
//	public void testGameObject() throws Exception {
//		Class<?> clazz = TestModel.class;
//		CtClass ctClass = pool.get(TestModel.class.getName());
//		
//		Assert.assertEquals(true, Type.gameObjectType.is(clazz));
//		Assert.assertEquals(true, Type.gameObjectType.is(ctClass));
//		
//		Assert.assertEquals(false, Type.gameObjectAutoIdType.is(clazz));
//		Assert.assertEquals(false, Type.gameObjectAutoIdType.is(ctClass));
//	}
//	
//	@Test
//	public void testTimeStamp() throws Exception {
//		Class<?> clazz = Timestamp.class;
//		CtClass ctClass = pool.get(Timestamp.class.getName());
//		
//		Assert.assertEquals(true, Type.timeStampType.is(clazz));
//		Assert.assertEquals(true, Type.timeStampType.is(ctClass));
//	}
//	
//}
