package org.itas.core.bytecode;

import java.util.List;

import javassist.ClassPool;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.util.ClassLoaders;
import org.itas.core.util.Next;

public class MDDoUpdateProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders, Next {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDDoUpdateProvider();
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
				"protected void doUpdate(java.sql.PreparedStatement state) throws java.sql.SQLException {"
						+ "\n\t\tstate.setString(1, getName());" 
						+ "\n\t\tstate.setInt(2, getCoinPrice());" 
						+ "\n\t\tstate.setInt(3, getGoldPrice());" 
						+ "\n\t\tstate.setTimestamp(4, getUpdateTime());" 
						+ "\n\t\tstate.setTimestamp(5, getCreateTime());" 
						+ "\n\t\tstate.setString(6, getId());" 
						+ "\n}";
		
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
