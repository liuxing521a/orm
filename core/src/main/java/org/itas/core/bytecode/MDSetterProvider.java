package org.itas.core.bytecode;

import java.util.Map;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.common.Utils.Objects;
import org.itas.core.util.FirstChar;

import com.google.common.collect.Maps;

/**
 *  select 预处理方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MDSetterProvider extends AbstractMethodProvider implements FirstChar {
	
  private Map<String, CtMethod> setMethodMaps;
  private Map<String, CtMethod> getMethodMaps;
  
  MDSetterProvider() {
  }
	
  @Override
  public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		this.getMethodMaps = Maps.newHashMap();
		this.setMethodMaps = Maps.newHashMap();
		
		final CtMethod[] ctMethods = clazz.getMethods();
		for (CtMethod ctMethod : ctMethods) {
		  if (ctMethod.getName().startsWith("set")) {
		  	setMethodMaps.put(compressSetName(ctMethod.getName()), ctMethod);
		  	continue;
		  }
		  
		  if (ctMethod.getName().startsWith("get")) {
				getMethodMaps.put(compressSetName(ctMethod.getName()), ctMethod);
				continue;
		  }
		}
  }
  

  @Override
  public void processField(CtField field) throws Exception {
  	if (!field.getName().equals("Id")) {
  	  CtMethod ctMethod = checkSetMethodNull(field);
  	  ctMethod.insertAfter("this.modify();");
  	}
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
  	return buffer.toString();
  }

  private String compressSetName(String name) {
    if (name == null || name.length() < 3) {
      throw new IllegalArgumentException("must get|set Name:" + name);
    }
    
    return lowerCase(name.substring(3, name.length()));
  }
  
  CtMethod checkgetMethodNull(CtField ctField) {
  	CtMethod ctMethod = getMethodMaps.get(ctField.getName());
  	if (Objects.isNull(ctMethod)) {
  		throw new UnDeFineException("class's field has not get method:[class:" + 
	      ctClass.getName() + ", Field:" + ctField.getName() + "]");
  	}
  
  	return ctMethod;
  }
  
  CtMethod checkSetMethodNull(CtField ctField) {
  	CtMethod ctMethod = setMethodMaps.get(ctField.getName());
  	if (Objects.isNull(ctMethod)) {
  		throw new UnDeFineException("class's field has not set method:[class:" + 
				ctClass.getName() + ", Field:" + ctField.getName() + "]");
  	}
	
  	return ctMethod;
  }
}
