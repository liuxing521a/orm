package org.itas.core.resources;

import static org.itas.core.bytecode.Type.booleanType;
import static org.itas.core.bytecode.Type.byteType;
import static org.itas.core.bytecode.Type.charType;
import static org.itas.core.bytecode.Type.doubleType;
import static org.itas.core.bytecode.Type.enumByteType;
import static org.itas.core.bytecode.Type.enumIntType;
import static org.itas.core.bytecode.Type.enumStringType;
import static org.itas.core.bytecode.Type.enumType;
import static org.itas.core.bytecode.Type.floatType;
import static org.itas.core.bytecode.Type.intType;
import static org.itas.core.bytecode.Type.longType;
import static org.itas.core.bytecode.Type.resourceType;
import static org.itas.core.bytecode.Type.shortType;
import static org.itas.core.bytecode.Type.stringType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itas.core.Builder;
import org.itas.core.Pool;
import org.itas.core.Resource;
import org.itas.core.annotation.CanNull;
import org.itas.core.annotation.Clazz;
import org.itas.core.util.Enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@SuppressWarnings({ "rawtypes", "unchecked" })
final class Attribute {

	private final Map<String, String> values;
	
	private Attribute(Map<String, String> values) {
		this.values = Collections.unmodifiableMap(values);
	}
	
	public boolean getBoolean(Field field) {
		final String value = getValue(field.getName());
		return getBoolean(field, value, field.isAnnotationPresent(CanNull.class));
	}

	private boolean getBoolean(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if ("".endsWith(value) || "0".equals(value)) {
			return false;
		}

		if ("1".equals(value)) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	public byte getByte(Field field) {
		final String value = getValue(field.getName());
		return getByte(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private byte getByte(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if ("".endsWith(value)) {
			return 0;
		}

		return Byte.parseByte(value);
	}

	public char getChar(Field field) {
		final String value = getValue(field.getName());
		return getChar(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private char getChar(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if (value.length() == 0) {
			return ' ';
		}

		return value.charAt(0);
	}
	
	public short getShort(Field field) {
		final String value = getValue(field.getName());
		return getShort(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private short getShort(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if (value.length() == 0) {
			return 0;
		}

		return Short.parseShort(value);
	}

	public int getInt(Field field) {
		final String value = getValue(field.getName());
		return getInt(field, value, field.isAnnotationPresent(CanNull.class));
	}

	private int getInt(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if (value.length() == 0) {
			return 0;
		}

		return Integer.parseInt(value);
	}

	public long getLong(Field field) {
		final String value = getValue(field.getName());
		return getLong(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private long getLong(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if (value.length() == 0) {
			return 0L;
		}

		return Long.parseLong(value);
	}

	public float getFloat(Field field) {
		final String value = getValue(field.getName());
		return getFloat(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private float getFloat(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if (value.length() == 0) {
			return 0.0F;
		}

		return Float.parseFloat(value);
	}

	public double getDouble(Field field) {
		final String value = getValue(field.getName());
		return getDouble(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private double getDouble(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if (value.length() == 0) {
			return 0.0D;
		}
		
		return Double.parseDouble(value);
	}

	public String getString(Field field) {
		final String value = getValue(field.getName());
		return getString(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private String getString(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		return value;
	}

	public Resource getResource(Field field) {
		final String value = getValue(field.getName());
		return getResource(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private Resource getResource(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		
		if (value.length() == 0) {
			return null;
		}
		
		return Pool.getResource(value);
	}

	public Timestamp getTimestamp(Field field) {
		final String value = getValue(field.getName());
		return getTimestamp(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private Timestamp getTimestamp(Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		return Timestamp.valueOf(value);
	}

	public List<Object> getList(Field field) throws Exception {
		final String value = getValue(field.getName());
		return getList(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private List<Object> getList(Field field, String value, boolean nullAble) throws Exception {
		checkNull(field, value, nullAble);

		final Clazz clazz = field.getAnnotation(Clazz.class);

		List<Object> list;
		if (clazz != null) {
			list = (List<Object>) clazz.value().newInstance();
		} else {
			list = Lists.newArrayList();
		}

		fill(list, field, value);
		return Collections.unmodifiableList(list);
	}

	public Set<Object> getSet(Field field) throws Exception {
		final String value = getValue(field.getName());
		return getSet(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private Set<Object> getSet(Field field, String value, boolean nullAble) throws Exception {
		checkNull(field, value, nullAble);

		final Clazz clazz = field.getAnnotation(Clazz.class);

		Set<Object> set;
		if (clazz != null) {
			set = (Set<Object>) clazz.value().newInstance();
		} else {
			set = Sets.newHashSet();
		}

		fill(set, field, value);
		return Collections.unmodifiableSet(set);
	}
	
	public Map<Object, Object> getMap(Field field) throws Exception {
		final String value = getValue(field.getName());
		return getMap(field, value, field.isAnnotationPresent(CanNull.class));
	}
	
	private Map<Object, Object> getMap(Field field, String value, boolean nullAble) throws Exception {
		checkNull(field, value, nullAble);

		final Clazz clazz = field.getAnnotation(Clazz.class);

		Map<Object, Object> map;
		if (clazz != null) {
			map = (Map<Object, Object>) clazz.value().newInstance();
		} else {
			map = Maps.newHashMap();
		}

		fill(map, field, value);
		return Collections.unmodifiableMap(map);
	}
	
	public <E extends Enum<E>> E getEnumByte(Field field) {
		final String value = getValue(field.getName());
		return getEnumByte((Class<E>)field.getType(), field, value, true);
	}

	private <E extends Enum<E>> E getEnumByte(Class<E> clazz, Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		return Enums.parseFrom(clazz, getByte(field, value, true));
	}
	
	public <E extends Enum<E>> E getEnumInt(Field field) {
		final String value = getValue(field.getName());
		return getEnumInt((Class<E>)field.getType(), field, value, true);
	}

	private <E extends Enum<E>> E getEnumInt(Class<E> clazz, Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		return Enums.parseFrom(clazz, getInt(field, value, true));
	}
	
	public <E extends Enum<E>> E getEnumString(Field field) {
		final String value = getValue(field.getName());
		return getEnumString((Class<E>)field.getType(), field, value, true);
	}

	private <E extends Enum<E>> E getEnumString(Class<E> clazz, Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		return Enums.parseFrom(clazz, getString(field, value, true));
	}
	
	public <E extends Enum<E>> E getEnum(Field field) {
		final String value = getValue(field.getName());
		return getEnum((Class<E>)field.getType(), field, value, true);
	}

	private <E extends Enum<E>> E getEnum(Class<E> clazz, Field field, String value, boolean nullAble) {
		checkNull(field, value, nullAble);
		return Enums.parseFrom(clazz, getString(field, value, true));
	}

	private void fill(Collection<Object> c, Field field, String content) {
		final char[] chs = content.toCharArray();
		final StringBuffer v = new StringBuffer();

		Class<?> genericClazz = getGenericClassArray(field)[0];
		for (char ch : chs) {
			if (ch == '|') {
				c.add(toValue(field, genericClazz, v.toString()));
				v.setLength(0);
				continue;
			}

			v.append(ch);
		}

		if (v.length() > 0) {
			c.add(toValue(field, genericClazz, v.toString()));
		}
	}

	private void fill(Map<Object, Object> map, Field field, String text) {
		final char[] chs = text.toCharArray();
		final StringBuffer k = new StringBuffer();
		final StringBuffer v = new StringBuffer();
		
		final Class<?>[] genericClazz = getGenericClassArray(field);
		
		StringBuffer c = k;
		for (char ch : chs) {
			if (ch == ',') {
				c = v;
				continue;
			}
			
			if (ch == '|') {
				map.put(toValue(field, genericClazz[0], k.toString()),
						toValue(field, genericClazz[1], v.toString()));
				k.setLength(0);
				v.setLength(0);
				c = k;
				continue;
			}
			
			c.append(ch);
		}
		
		if (k.length() > 0) {
			map.put(toValue(field, genericClazz[0], k.toString()),
					toValue(field, genericClazz[1], v.toString()));
		}
	}
	
	private Object toValue(Field field, Class<?> clazz, String value) {
		if (booleanType.isType(clazz)) {
			return getBoolean(field, value, true);
		} else if (byteType.isType(clazz)) {
			return getByte(field, value, true);
		} else if (charType.isType(clazz)) {
			return getChar(field, value, true);
		} else if (shortType.isType(clazz)) {
			return getShort(field, value, true);
		} else if (intType.isType(clazz)) {
			return getInt(field, value, true);
		} else if (longType.isType(clazz)) {
			return getLong(field, value, true);
		} else if (floatType.isType(clazz)) {
			return getFloat(field, value, true);
		} else if (doubleType.isType(clazz)) {
			return getDouble(field, value, true);
		} else if (stringType.isType(clazz)) {
			return getString(field, value, true);
		} else if (enumByteType.isType(clazz)) {
			return getEnumByte((Class<Enum>)clazz, field, value, true);
		} else if (enumIntType.isType(clazz)) {
			return getEnumInt((Class<Enum>)clazz, field, value, true);
		} else if (enumStringType.isType(clazz)) {
			return getEnumString((Class<Enum>)clazz, field, value, true);
		} else if (enumType.isType(clazz)) {
			return getEnum((Class<Enum>)clazz, field, value, true);
		} else if (resourceType.isType(clazz)) {
			return getResource(field, value, true);
		} else {
			throw new RuntimeException("reflect unSupported type:[" + field.getType() + "]");
		}
	}

	private Class<?>[] getGenericClassArray(Field field) {
		final ParameterizedType type = (ParameterizedType) field.getGenericType();
		final Type[] types = type.getActualTypeArguments();
		final Class<?>[] classArray = new Class<?>[types.length];

		int index = 0;
		for (Type t : types) {
			classArray[index++] = (Class<?>)
				((t instanceof ParameterizedType) ? (((ParameterizedType) t).getRawType()) :  t);
		}

		return classArray;
	}
	
	public String getValue(String name) {
		return values.get(name);
	}

	private void checkNull(Field field, String value, boolean nullAble) {
  	if (!nullAble && value == null) {
  		throw new FieldNotConfigException("field:" + field.getName());
  	}
  }
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		if (values != null) {
			values.forEach((k, v)->{
				buffer.append(" ");
				buffer.append(k).append("=\"").append(v).append("\"");
			});
		}
		
		return buffer.toString();
	}
	
	public static AttributeBuilder newBuilder() {
		return new AttributeBuilder();
	}

	public static class AttributeBuilder implements Builder {

		private Map<String, String> values;
		
		private AttributeBuilder() {
		}
		
		public AttributeBuilder addAttribute(String name, String value) {
			if (values == null) {
				values = new LinkedHashMap<>();
			}
			
			values.put(name, value);
			return this;
		}
		
		public AttributeBuilder setValues(Map<String, String> values) {
			this.values = values;
			return this;
		}

		@Override
		public Attribute builder() {
			return new Attribute(values);
		}
		
	}
	
	

}
