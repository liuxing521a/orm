package org.itas.core.util;

import javassist.CtClass;

import org.itas.common.ItasException;
import org.itas.core.annotation.SQLEntity;

public interface DataBase {

  default String tableName(CtClass clazz) throws ClassNotFoundException {
		Object sqlEntity = clazz.getAnnotation(SQLEntity.class);
		if (sqlEntity == null) {
		  throw new ItasException(clazz.getName() + " module must has annotation[SQLEntity|UnPersistence]");
		}
	
		return ((SQLEntity)sqlEntity).value();
  }
	
}
