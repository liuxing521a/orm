package org.itas.core;

import java.sql.Statement;
import java.util.Collections;
import java.util.Map;

import org.itas.common.ItasException;
import org.itas.common.Logger;
import org.itas.common.Utils.Objects;
import org.itas.common.Utils.TimeUtil;
import org.itas.core.Pool.DataPool;
import org.itas.core.Syner.DataBaseSyner;
import org.itas.core.Syner.GameBaseSyner;

import com.google.common.collect.Maps;

@SuppressWarnings("unchecked")
final class DataPoolImpl implements DataPool, DataBaseSyner {

	private final Map<String, CacheBean> prefixCached;
	private final Map<Class<?>, CacheBean> classCached;

	private DataPoolImpl(
			Map<String, CacheBean> prefixCached, 
			Map<Class<?>, CacheBean> classCached) {
		this.prefixCached = Collections.unmodifiableMap(prefixCached);
		this.classCached = Collections.unmodifiableMap(classCached);
	}

	@Override
	public void put(GameBase gameBase) {
		getCacheBean(gameBase.getId()).cache(gameBase);
	}

	@Override
	public <T extends GameBase> T get(String Id) {
		final CacheBean cached = getCacheBean(Id);
		
		GameBase gameObject = cached.get(Id);
		if (Objects.nonNull(gameObject)) {
			gameObject.setUpdateTime(TimeUtil.systemTime());
			return (T)gameObject;
		} 
		
		if (cached.module() instanceof GameObject) {
			gameObject = ((GameObject)cached.module()).autoInstance(Id);
			return (T)gameObject;
		}
			
		return null;
	}

	@Override
	public <T extends GameBase> T get(Class<T> clazz, String Id) {
		final CacheBean cached = getCacheBean(Id);
		
		GameBase gameObject = cached.get(Id);
		if (Objects.nonNull(gameObject)) {
			gameObject.setUpdateTime(TimeUtil.systemTime());
			return (T)gameObject;
		} 
		
		if (cached.module() instanceof GameObject) {
			gameObject = ((GameObject)cached.module()).autoInstance(Id);
			return (T)gameObject;
		}
			
		return null;
	}
	
	@Override
	public GameBaseSyner getSyner(Class<?> clazz) {
		return getCacheBean(clazz);
	}

	@Override
	public boolean isCached(String Id) {
		return getCacheBean(Id).contains(Id);
	}

	@Override
	public <T extends GameBase> boolean isCached(Class<T> clazz, String Id) {
		return getCacheBean(clazz).contains(Id);
	}

	@Override
	public <T extends GameBase> T remove(String Id) {
		return (T) getCacheBean(Id).remove(Id);
	}

	@Override
	public <T extends GameBase> T remove(Class<T> clazz, String Id) {
		return (T) getCacheBean(clazz).remove(Id);
	}

	@Override
	public <T extends GameBase> T newInstance(Class<T> clazz, String Id) {
		return (T) newInstance(getCacheBean(clazz), Id);
	}

	@Override
	public <T extends GameBase> T newInstance(String Id) {
		return (T) newInstance(getCacheBean(Id), Id);
	}
	
	@Override
	public void synDatabase() {
		classCached.forEach((clazz, bean)->{
			try {
				bean.doInsert();
			} catch (Exception e) {
				Logger.error("", e);
			}
			
			try {
				bean.doUpdate();
			} catch (Exception e) {
				Logger.error("", e);
			}
			
			try {
				bean.doDelete();
			} catch (Exception e) {
				Logger.error("", e);
			}
		});
	}
	
	@Override
	public void createTable(Statement statement) throws Exception {
		for (CacheBean bean : classCached.values()) {
			bean.doCreate(statement);
		}
	}
	
	@Override
	public void alterTable(Statement statement) throws Exception {
		for (CacheBean bean : classCached.values()) {
			bean.doAlter(statement);
		}
	}

	private CacheBean getCacheBean(String Id) {
		final String prifex = getPrifex(Id);
		
		final CacheBean cached = prefixCached.get(prifex);
		if (Objects.isNull(cached)) {
			throw new ItasException(
				String.format("prifex[%s] module not found Id:%s", prifex, Id));
		}
		
		return cached;
	}
	
	private CacheBean getCacheBean(Class<?> clazz) {
		final CacheBean cached = classCached.get(clazz);
		
		if (Objects.isNull(cached)) {
			throw new NullPointerException("class cache:" + clazz.getClass());
		}

		return cached;
	}
	
	private String getPrifex(String Id) {
		if (Id == null || Id.length() < 3) {
			return null;
		}

		String prifex = Id.substring(0, 3);
		if (prifex.charAt(2) != '_') {
			throw new IllegalArgumentException("PRIFEX must endwith [_]");
		}

		return prifex;
	}
	
	private GameBase newInstance(CacheBean cached, String Id) {
		synchronized (Id.intern()) {
			GameBase gameObject = cached.get(Id);
			if (Objects.nonNull(gameObject)) {
				return gameObject;
			}

			gameObject = cached.newInstance(Id);
			gameObject.initialized();
			cached.cache(gameObject);
			
			return gameObject;
		}
	}
	
	public static DataPoolImplBuilder newBuilder() {
		return new DataPoolImplBuilder();
	}

	public static class DataPoolImplBuilder implements Builder {

		private Map<String, CacheBean> prefixCached;
		private Map<Class<?>, CacheBean> classCached;
		
		private DataPoolImplBuilder() {
		}
		
		public DataPoolImplBuilder setPrefixCached(Map<String, CacheBean> prefixCached) {
			this.prefixCached = prefixCached;
			return this;
		}

		public DataPoolImplBuilder setClassCached(Map<Class<?>, CacheBean> classCached) {
			this.classCached = classCached;
			return this;
		}

		public DataPoolImplBuilder addBean(CacheBean bean) {
			if (prefixCached == null) {
				prefixCached = Maps.newHashMap();
				classCached = Maps.newHashMap();
			}
			
			prefixCached.put(bean.prefix(), bean);
			classCached.put(bean.clazz(), bean);
			
			return this;
		}

		@Override
		public DataPoolImpl builder() {
			return new DataPoolImpl(prefixCached, classCached);
		}
		
	}
}
