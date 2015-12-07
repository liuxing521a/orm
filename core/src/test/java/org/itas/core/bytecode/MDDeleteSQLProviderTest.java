package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

public class MDDeleteSQLProviderTest extends AbstreactMethodProvider {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDDeleteSQLProvider();
	}
	
	@Override
	public void methodTest() throws Exception {
		provider.startClass(ctClazz);
		provider.endClass();
	
		String expected = 
			"protected String deleteSQL() {" +
				"return \"DELETE FROM `item` WHERE Id = ?;\";" +
			"}";
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
