package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;

import org.junit.Test;

public class TestByteCodes {

	@Test
	public void testByteCodeClass() throws Exception {
		CtClass ctClass = ClassPool.getDefault().get(Model.class.getName());
		ByteCodes.instance.testToClass(ctClass);
	}
}
