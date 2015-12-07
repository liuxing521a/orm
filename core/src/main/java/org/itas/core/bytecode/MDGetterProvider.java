package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.core.util.FirstChar;

/**
 *  select 预处理方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MDGetterProvider extends AbstractMethodProvider implements FirstChar {
	
  MDGetterProvider() {
  	throw new UnsupportException("getter");
  }
	
  @Override
  public void startClass(CtClass clazz) throws Exception {
  }

  @Override
  public void processField(CtField field) throws Exception {

  }
	
  @Override
  public void endClass() throws Exception {
  	
  }
	
  @Override
  public CtMethod[] toMethod() throws Exception {
  	return null;
  }
	
  @Override
  public String toString() {
  	return null;
  }
}
