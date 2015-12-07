package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.common.ItasException;

/**
 *  SQL操作方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MDCloneProvider extends AbstractMethodProvider {

	@Override
	public void startClass(CtClass clazz) throws Exception {
		checkCloneAble(clazz);
		super.startClass(clazz);
	}
	
	@Override
	public void processField(CtField field) throws Exception {
		
	}
	
	@Override
	public void endClass() throws Exception {
		buffer.append("protected org.itas.core.GameBase clone(java.lang.String oid) {");
		buffer.append("return new ").append(ctClass.getName()).append("(oid);");
		buffer.append("}");
	}
	
	@Override
	public CtMethod[] toMethod() throws CannotCompileException {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}

	private void checkCloneAble(CtClass clazz) throws Exception {
		if (Type.gameObjectAutoIdType.isType(clazz)) {
			return;
		} 

		if (Type.gameObjectType.isType(clazz)) {
			return;
		} 
		
		throw new ItasException("un supported clone:" + clazz.getName());
	}
	
}
