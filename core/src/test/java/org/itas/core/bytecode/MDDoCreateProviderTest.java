package org.itas.core.bytecode;

import java.util.List;

import javassist.ClassPool;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.util.ClassLoaders;
import org.itas.core.util.Next;

public class MDDoCreateProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders, Next {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDDoCreateProvider();
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
				"CREATE TABLE IF NOT EXISTS `item`(" + next(1, 1)
						+ "`Id` VARCHAR(36) NOT NULL DEFAULT ''," + next(1, 1)
						+ "`name` VARCHAR(36) NOT NULL DEFAULT ''," + next(1, 1)
						+ "`coinPrice` INT(11) NOT NULL DEFAULT '0'," + next(1, 1)
						+ "`goldPrice` INT(11) NOT NULL DEFAULT '0'," + next(1, 1)
						+ "`updateTime` TIMESTAMP NOT NULL," + next(1, 1)
						+ "`createTime` TIMESTAMP NOT NULL," + next(1, 1)
						+ "PRIMARY KEY `Id` (`Id`)" + next(1, 1)
						+ ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
