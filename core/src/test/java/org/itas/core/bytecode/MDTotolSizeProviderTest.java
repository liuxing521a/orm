package org.itas.core.bytecode;

import javassist.NotFoundException;

import org.itas.core.util.ClassLoaders;

public class MDTotolSizeProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders {

	@Override
	public void setUP() throws NotFoundException {
//		ctClazz = ClassPool.getDefault().get(Item.class.getName());
//		provider =  new MDTotolSizeProvider();
	}
	
	@Override
	public void methodTest() throws Exception {
		for (int i = 0; i < 0xFFF; i++) {
			System.out.println("i=" + i + ", pos=" + (i & 0xFF) + ", " + ((i & 0xFF) == 0xFF));
		}
//		provider.startClass(ctClazz);
//		
//		final List<CtField> fields = getAllField(ctClazz);
//		for (CtField field : fields) {
//			provider.processField(field);
//		}
//		
//		provider.endClass();
//	
//		String expected = 
//			"protected String updateSQL() {" +
//				"return \"UPDATE `item` SET `name` = ?, `coinPrice` = ?, `goldPrice` = ?, `updateTime` = ?, `createTime` = ? WHERE `Id` = ?;\";" +
//			"}";
//		Assert.assertEquals(expected, provider.toString());
//		
//		for (CtMethod ctMethod : provider.toMethod()) {
//			ctClazz.addMethod(ctMethod);
//		}
	}
}
