package org.itas.core;

@FunctionalInterface
public interface CallBack<T> {

	public void called(T back);
	
}
