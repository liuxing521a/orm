package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

public class MDCloneProviderTest extends AbstreactMethodProvider {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDCloneProvider();
	}
	
	@Override
	public void methodTest() throws Exception {
		provider.startClass(ctClazz);
		provider.endClass();
		
		
		String expected = 
				"protected org.itas.core.GameBase clone(java.lang.String oid) {" + 
						"return new org.itas.core.bytecode.Item(oid);" + 
				"}";
		
		Assert.assertEquals(expected, provider.toString());
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
