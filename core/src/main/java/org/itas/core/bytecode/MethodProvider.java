package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public interface MethodProvider extends Provider {

	/**
	 * 开始处理类
	 * @param clazz
	 * @throws Exception
	 */
	void startClass(CtClass ctClazz) throws Exception;
	
	/**
	 * 开始处理属性
	 * @param field
	 * @throws Exception
	 */
	void processField(CtField field) throws Exception;
	
	/**
	 * 结束处理类
	 * @throws Exception
	 */
	void endClass() throws Exception;
	
	/**
	 * 转成ctMethod
	 * @return
	 * @throws CannotCompileException
	 */
	CtMethod[] toMethod() throws Exception;
	
}
