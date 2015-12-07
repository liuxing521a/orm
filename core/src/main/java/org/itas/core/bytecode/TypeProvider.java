package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * 可操作类型支持
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年3月2日上午11:42:12
 */
public interface TypeProvider extends Provider {

	/**
	 * 是否是java类型
	 * @param clazz
	 * @return
	 */
	boolean isType(Class<?> clazz);
	
	/**
	 * 是否是ctClass指定类型
	 * @param clazz
	 * @return
	 */
	boolean isType(CtClass clazz) throws Exception ;

	/**
	 * 数据库列字段类型
	 * @param field
	 * @return
	 */
	String sqlType(CtField field) throws Exception;
	
}
