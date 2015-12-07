package org.itas.core;

import java.lang.reflect.Field;

import org.itas.core.resources.AbstractXml;


public abstract class Config extends AbstractXml {
	
  @Override
  protected String confiMessage(Field field) {
  	return String.format("class:%s[field=%s]", getClass().getName(), field.getName());
  }
  
  @Override
  protected String formatMessage(Field field, String value) {
  	return String.format("class:%s[field=%s, type=%s, value=%s]", 
  			getClass().getName(), field.getName(), field.getType().getName(), value);
  }
  
}
