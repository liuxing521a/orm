//package org.itas.core.bytecode;
//
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtField;
//import javassist.NotFoundException;
//import junit.framework.Assert;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class TestMethodSQLProvider {
//
//	
//	private CtClass ctClass;
//	
//	@Before
//	public void setUP() throws NotFoundException, Exception {
//		ctClass = ClassPool.getDefault().get(Model.class.getName());
//	}
//	
//	@Test
//	public void testSQLCreate() throws Exception {
//		MethodDoCreateProvider sqlCreate = new MethodDoCreateProvider();
//		
//		CtField ctField = ctClass.getDeclaredField("name");
//		sqlCreate.begin(ctClass);
//		sqlCreate.append(ctField);
//		sqlCreate.end();
//		
//		String expected = 
//				"CREATE TABLE IF NOT EXISTS `model`("
//				+ "\n\t"
//				+ "`Id` VARCHAR(36) NOT NULL DEFAULT '',"
//				+ "\n\t"
//				+ "`name` VARCHAR(36) NOT NULL DEFAULT ''"
//				+ "\n"
//				+ ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
//		Assert.assertEquals(expected, sqlCreate.toString());
//		
//		sqlCreate = new MethodDoCreateProvider();
//		sqlCreate.begin(ctClass);
//		sqlCreate.append(ctClass.getDeclaredField("identy"));
//		sqlCreate.end();
//		expected = 
//				"CREATE TABLE IF NOT EXISTS `model`("
//				+ "\n\t"
//				+ "`Id` VARCHAR(36) NOT NULL DEFAULT '',"
//				+ "\n\t"
//				+ "`identy` VARCHAR(36) NOT NULL DEFAULT '',"
//				+ "\n\t"
//				+ "UNIQUE KEY `identy` (`identy`)"
//				+ "\n"
//				+ ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
//		Assert.assertEquals(expected, sqlCreate.toString());
//		
//		sqlCreate = new MethodDoCreateProvider();
//		ctField = ctClass.getDeclaredField("address");
//		sqlCreate.begin(ctClass);
//		sqlCreate.append(ctField);
//		sqlCreate.end();
//		expected = 
//				"CREATE TABLE IF NOT EXISTS `model`("
//				+ "\n\t"
//				+ "`Id` VARCHAR(36) NOT NULL DEFAULT '',"
//				+ "\n\t"
//				+ "`address` VARCHAR(36) NOT NULL DEFAULT '',"
//				+ "\n\t"
//				+ "KEY `address` (`address`)"
//				+ "\n"
//				+ ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
//		Assert.assertEquals(expected, sqlCreate.toString());
//		
//		sqlCreate.toMethod();
////		ctClass.addMethod(method);
//	}
//	
//	@Test
//	public void testSqlAlter() throws Exception {
//		MethodDoAlterProvider sqlAlter = new MethodDoAlterProvider();
//		CtField ctField = ctClass.getDeclaredField("name");
//		
//		sqlAlter.begin(ctClass);
//		sqlAlter.append(ctField);
//		sqlAlter.end();
//		
//		String expected = "ALTER TABLE `model` ADD `name` VARCHAR(36) NOT NULL DEFAULT '';\n";
//		String actual = sqlAlter.toString();
//		Assert.assertEquals(expected, actual);
//		
//		sqlAlter.toMethod();
////		ctClass.addMethod(method);
//	}
//	
//	@Test
//	public void testSQLSelect() throws Exception {
//		MethodSQLSelectProvider sqlSelect = new MethodSQLSelectProvider();
//		
//		CtField ctField = ctClass.getDeclaredField("name");
//			
//		sqlSelect.begin(ctClass);
//		sqlSelect.append(ctField);
//		sqlSelect.end();
//		
//		String expected = "SELECT `name` FROM `model` WHERE Id = ?;";
//		String actual = sqlSelect.toString();
//		Assert.assertEquals(expected, actual);
//		
//		sqlSelect.toMethod();
////		ctClass.addMethod(sqlSelect.toMethod());
//	}
//	
//	@Test
//	public void testSQLInsert() throws Exception {
//		MethodSQLInsertProvider insert = new MethodSQLInsertProvider();
//		
//		
//		insert.begin(ctClass);
//		insert.append(ctClass.getDeclaredField("name"));
//		insert.end();
//		
//		String expected = "INSERT INTO `model` (`name`) VALUES (?);";
//		String actual = insert.toString();
//		Assert.assertEquals(expected, actual);
//		
//		insert.toMethod();
////		ctClass.addMethod(method);
//	}
//	
//	@Test
//	public void testSQLUpdate() throws Exception {
//		MethodSQLUpdateProvider update = new MethodSQLUpdateProvider();
//		
//		
//		update.begin(ctClass);
//		update.append(ctClass.getDeclaredField("name"));
//		update.end();
//		
//		String expected = "UPDATE `model` SET `name` = ? WHERE Id = ?;";
//		String actual = update.toString();
//		Assert.assertEquals(expected, actual);
//		
//		update.toMethod();
////		ctClass.addMethod(method);
//	}
//	
//	@Test
//	public void testSQLDelete() throws Exception {
//		MethodSQLDeleteProvider delete = new MethodSQLDeleteProvider();
//		
//		
//		delete.begin(ctClass);
//		delete.end();
//		
//		String expected = "DELETE FROM `model` WHERE Id = ?;";
//		String actual = delete.toString();
//		Assert.assertEquals(expected, actual);
//		
//		delete.toMethod();
////		ctClass.addMethod(method);
//	}
//	
//}
