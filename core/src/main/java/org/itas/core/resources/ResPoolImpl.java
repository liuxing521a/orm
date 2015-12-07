package org.itas.core.resources;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.core.Builder;
import org.itas.core.DoubleException;
import org.itas.core.Pool.ResPool;
import org.itas.core.Resource;

@SuppressWarnings("unchecked")
final class ResPoolImpl implements ResPool {

	private final Map<String, Resource> resMap;
	private final Map<Class<?>, XmlBean> beanMap;
  
	private ResPoolImpl(Map<String, Resource> resMap, Map<Class<?>, XmlBean> beanMap) {
		this.resMap = Collections.unmodifiableMap(resMap);
		this.beanMap = Collections.unmodifiableMap(beanMap);
	}
  
	@Override 
	public <T extends Resource> T get(String Id) {
		if (Id == null || Id.length() == 0) {
			return null;
		}
			
		return (T)resMap.get(Id);
	}
	
	@Override
	public <T extends Resource> List<T> get(Class<T> clazz) {
		XmlBean bean = beanMap.get(clazz);
		if (bean == null) {
			return null;
		}
		
		return bean.getXmlList();
	}

	Collection<XmlBean> getXmlBeans() {
		return beanMap.values();
	}

	public void loading(String file) throws Exception {
		
	}


	public void loadingAll() throws Exception {
		Collection<XmlBean> beans = getXmlBeans();
		for (XmlBean bean : beans) {
			bean.reLoad();
		}
	}
	
	public static ResPoolImplBuilder newBuilder() {
		return new ResPoolImplBuilder();
	}
	
	public static class ResPoolImplBuilder implements Builder {

		private Map<String, Resource> resMap;
		private Map<Class<?>, XmlBean> beanMap;
		
		private ResPoolImplBuilder() {
		}
		
		public ResPoolImplBuilder addXmlBean(XmlBean bean) {
			if (resMap == null) {
				resMap = new HashMap<>();
				beanMap = new HashMap<>();
			}
			
			List<Resource> resList = bean.getXmlList();
			
			Resource old;
			for (Resource res : resList) {
				old = resMap.put(res.getId(), res);
				checkNonull(old, res);
			}
			
			beanMap.put(bean.getClazz(), bean);
			return this;
		}
		
		private void checkNonull(Resource old, Resource res) {
			if (old != null) {
				throw new DoubleException(String.format("class[%s] and class[%s] has same Id[%s]", 
					old.getClass().getName(), res.getClass().getName(), old.getId()));
			}
		}
		
		@Override
		public ResPoolImpl builder() {
			return new ResPoolImpl(resMap, beanMap);
		}
		
	}

}

