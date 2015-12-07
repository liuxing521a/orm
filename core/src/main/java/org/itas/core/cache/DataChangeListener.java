package org.itas.core.cache;

import java.util.EventListener;

public interface DataChangeListener extends EventListener {

	abstract void onExpired(Object o);
	
	abstract void onRemove(Object o);
	
	abstract void onAdded(Object o);
	
}
