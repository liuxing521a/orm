package org.itas.core;

import java.lang.reflect.Field;

import org.itas.core.resources.AbstractXml;

public abstract class Resource extends AbstractXml implements HashId {
	
  /** 资源唯一Id*/
  private String Id;
	
  protected Resource(String Id)  {
  	this.Id = Id;
  }
	
  @Override
  public String getId()  {
  	return Id;
  }
  
  @Override
  protected String confiMessage(Field field) {
  	return String.format("class:%s[Id=%s, field=%s]", 
  			getClass().getName(), Id, field.getName());
  }
  
  @Override
  protected String formatMessage(Field field, String value) {
  	return String.format("class:%s[Id=%s, field=%s, type=%s, value=%s]", 
  			getClass().getName(), Id, field.getName(), field.getType().getName(), value);
  }

  @Override
  public boolean equals(Object o)  {
    if (this == o) {
      return true;
    }
    
    if (o.getClass() == this.getClass()) {
    	return Id.equals(((Resource)o).Id);	
    }
		
    return false;
  }
	
  @Override
  public int hashCode() {
  	return 31 + Id.hashCode();
  }
	
}
