package org.itas.core.bytecode;

import java.util.List;

import javassist.ClassPool;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.util.ClassLoaders;
import org.itas.core.util.Next;

public class MDDoAlterProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders, Next {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDDoAlterProvider();
	}
	
	@Override
	public void methodTest() throws Exception {
		provider.startClass(ctClazz);
		
		final List<CtField> fields = getAllField(ctClazz);
		for (CtField field : fields) {
			provider.processField(field);
		}
		
		provider.endClass();
	
		String expected = 
				"protected void doAlter(java.sql.Statement stmt, java.util.Set columns) throws java.sql.SQLException {" + next(1, 2)
			+ "java.util.Map alterMap = new java.util.HashMap();" + next(1, 2)
			+ "alterMap.put(\"name\", \"ALTER TABLE `item` ADD `name` VARCHAR(36) NOT NULL DEFAULT '';\");" + next(1, 2)
			+ "alterMap.put(\"coinPrice\", \"ALTER TABLE `item` ADD `coinPrice` INT(11) NOT NULL DEFAULT '0';\");" + next(1, 2)
			+ "alterMap.put(\"goldPrice\", \"ALTER TABLE `item` ADD `goldPrice` INT(11) NOT NULL DEFAULT '0';\");" + next(1, 2)
			+ "alterMap.put(\"Id\", \"ALTER TABLE `item` ADD `Id` VARCHAR(36) NOT NULL DEFAULT '';\");" + next(1, 2)
			+ "alterMap.put(\"updateTime\", \"ALTER TABLE `item` ADD `updateTime` TIMESTAMP NOT NULL;\");" + next(1, 2)
			+ "alterMap.put(\"createTime\", \"ALTER TABLE `item` ADD `createTime` TIMESTAMP NOT NULL;\");" + next(1, 2)
			+ "java.util.Iterator it = alterMap.entrySet().iterator();" + next(1, 2)
			+ "java.util.Map.Entry entry;" + next(1, 2)
			+ "while (it.hasNext()) {" + next(1, 3)
			+ "entry = (java.util.Map.Entry)it.next();" + next(1, 3)
			+ "if (!columns.contains((String)entry.getKey()))" + next(1, 3)
			+ "stmt.addBatch((String)entry.getValue());" + next(1, 2)
			+ "}" + next(1, 1)
			+ "}";
		
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
