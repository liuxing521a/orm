package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstreactFieldProvider {

	protected CtClass clazz;
	
	protected CtField field;

	protected FieldProvider provider;
	
	@Before
	public void setUP() throws NotFoundException {
		clazz = ClassPool.getDefault().get(Model.class.getName());
	}
	
	@Test
	public abstract void setStatementTest() throws Exception;
	
	@Test
	public abstract void getResultSetTest() throws Exception;
	
	@Test
	public void typeTest() throws NotFoundException, Exception {
		final CtField[] fields = clazz.getDeclaredFields();
		for (CtField field : fields) {
			if (field == this.field) {
				Assert.assertEquals(true, provider.isType(field.getType()));
			} else {
				Assert.assertEquals(false, provider.isType(field.getType()));
			}
		}
	}
}
