package org.itas.core.util;

import java.lang.reflect.Constructor;

import org.itas.common.ItasException;

@SuppressWarnings("unchecked")
public interface Constructors {

  /**
   * 创建对象
   * @param cls
   * @param paramValues
   * @return
   */
  default <T> T newInstance(Class<?> cls, Object[] paramValues) {
		try {
		  Class<?>[] paramTypes = new Class<?>[paramValues.length];
		  for (int i = 0; i < paramValues.length; i++) {
			paramTypes[i] = paramValues[i].getClass();
		  }
		  
		  final Constructor<?> cons = cls.getDeclaredConstructor(paramTypes);
		  cons.setAccessible(true);
		  return (T)cons.newInstance(paramValues);
		} catch (Exception e) {
			throw new ItasException(e);
		}
  }
}
