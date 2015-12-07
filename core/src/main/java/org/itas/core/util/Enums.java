package org.itas.core.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.itas.core.EnumByte;
import org.itas.core.EnumInt;
import org.itas.core.EnumString;

import com.google.common.collect.Maps;

/**
 * 支持枚举处理
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年3月10日上午11:01:53
 */
public interface Enums {

	default <E extends Enum<E>> E parse(Class<E> clazz, Object key) {
		return EnumParse.parse(clazz, key);
	}

	public static <E extends Enum<E>> E parseFrom(Class<E> clazz, Object key) {
  	return EnumParse.parse(clazz, key);
  }

	class EnumParse {
		
		private static final Map<Class<?>, EnumModul> enums;
		
		static {
			enums = Maps.newHashMap();
		}
		
		static <E extends Enum<E>> E parse(Class<E> clazz, Object key) {
			EnumModul enumModul = enums.get(clazz);
			if (enumModul == null) {
				enumModul = EnumModul.loadEnums(clazz);
				enums.putIfAbsent(enumModul.getEnumClass(), enumModul);
			}
			
			return enumModul.getEnum(key);
		}
		
		private EnumParse() {
			throw new RuntimeException("not supported new instance...");
		}
	}
	
	class EnumModul {
		
		private final Class<? extends Enum<?>> enumClass;
		
		private final Map<Object, Enum<?>> enums;
		
		private EnumModul(Class<? extends Enum<?>> enumClass, Map<Object, Enum<?>> enums) {
			this.enumClass = enumClass;
			this.enums = Collections.unmodifiableMap(enums);
		}
		
		public Class<? extends Enum<?>> getEnumClass() {
			return enumClass;
		}
		
		@SuppressWarnings("unchecked")
		public <E extends Enum<E>> E getEnum(Object key) {
			final Enum<?> e = enums.get(key);
			if (e == null) {
				return null;
			}
			
			return ((E) e);
		}
		
		static <E extends Enum<E>> EnumModul loadEnums(Class<E> clazz) {
			final EnumSet<E> enumSet = EnumSet.allOf(clazz);
			
			final Map<Object, Enum<?>> enumMap = new HashMap<>(enumSet.size());
			for (E e : enumSet) {
				if (e instanceof EnumByte) {
					enumMap.put(((EnumByte)e).key(), e);
				} else if (e instanceof EnumInt) {
					enumMap.put(((EnumByte)e).key(), e);
				} else if (e instanceof EnumString) {
					enumMap.put(((EnumByte)e).key(), e);
				} else{
					enumMap.put(e.name(), e);
				}
			}
			
			return new EnumModul(clazz, enumMap);
		}
		
	}
}

