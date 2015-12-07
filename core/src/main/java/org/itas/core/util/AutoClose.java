package org.itas.core.util;

public interface AutoClose {

  default void close(AutoCloseable...autos) {
  	for (AutoCloseable auto : autos) {
		  try {
		  	if (auto != null) {
		  		auto.close();
		  	}
		  } catch (Exception e) {
		    // do nothing
		  }
  	}
  }
	
}
