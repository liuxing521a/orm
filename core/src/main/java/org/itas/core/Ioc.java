package org.itas.core;

import com.google.inject.Guice;
import com.google.inject.Injector;

public interface Ioc {

  static final Injector Ioc = Guice.createInjector(new ServerModule());
  
  public static <T> T getInstance(Class<T> clazz) {
  	return Ioc.getInstance(clazz);
  }
}
