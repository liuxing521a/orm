package org.itas.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.rmi.Remote;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RmiRef {
	
	Class<? extends Remote> value();
	
}
