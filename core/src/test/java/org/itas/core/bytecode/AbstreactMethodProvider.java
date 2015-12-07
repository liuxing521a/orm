package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstreactMethodProvider {

	protected CtClass ctClazz;
	
	protected MethodProvider provider;
	
	@Before
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Model.class.getName());
	}
	
	@Test
	public abstract void methodTest() throws Exception;
}
