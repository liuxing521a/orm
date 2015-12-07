package org.itas.core.bytecode;

import java.util.List;

import org.itas.core.util.ClassLoaders;

import javassist.ClassPool;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

public class MDInsertSQLProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDInsertSQLProvider();
	}
	
	@Override
	public void methodTest() throws Exception {
		provider.startClass(ctClazz);
		
		List<CtField> fields = getAllField(ctClazz);
		for (CtField ctField : fields) {
			provider.processField(ctField);
		}
		
		provider.endClass();
	
		String expected = 
			"protected String insertSQL() {" +
				"return \"INSERT INTO `item` (`name`, `coinPrice`, `goldPrice`, `Id`, `updateTime`, `createTime`) VALUES (?, ?, ?, ?, ?, ?);\";" +
			"}";
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
