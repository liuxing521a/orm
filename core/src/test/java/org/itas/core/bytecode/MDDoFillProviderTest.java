package org.itas.core.bytecode;

import java.util.List;

import javassist.ClassPool;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.util.ClassLoaders;
import org.itas.core.util.Next;

public class MDDoFillProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders, Next {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDDoFillProvider();
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
				"protected void doFill(java.sql.ResultSet result) throws java.sql.SQLException {"
			+ "\n\t\tsetName(result.getString(\"name\"));"
			+ "\n\t\tsetCoinPrice(result.getInt(\"coinPrice\"));"
			+ "\n\t\tsetGoldPrice(result.getInt(\"goldPrice\"));"
			+ "\n\t\tsetUpdateTime(result.getTimestamp(\"updateTime\"));"
			+ "\n\t\tsetCreateTime(result.getTimestamp(\"createTime\"));" + next(1, 0)
			+ "}";
		
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
