package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.util.ClassLoaders;

public class MDTableNameProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDTableNameProvider();
	}
	
	@Override
	public void methodTest() throws Exception {
		provider.startClass(ctClazz);
		provider.endClass();
	
		String expected = 
			"protected String tableName() {" +
				"return \"item\";" +
			"}";
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
