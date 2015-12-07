package org.itas.core.bytecode;

import java.sql.Timestamp;

import javassist.ClassPool;
import javassist.CtClass;

import org.itas.common.ItasException;
import org.itas.core.GameObject;
import org.itas.core.GameObjectAotuID;
import org.itas.core.util.FirstChar;

/**
 * 属性[field]字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:48
 */
abstract class AbstractFieldProvider implements FirstChar {
	
	protected static String next(int line, int table) {
		final StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < line; i++) {
			buffer.append('\n');
		}

		for (int i = 0; i < table; i++) {
			buffer.append('\t');
		}
		
		return buffer.toString();
	}
	
	static class javaType {
		
		public final static Class<?> boolean_ = boolean.class;
		public final static Class<?> booleanWrap = java.lang.Boolean.class;
		
		public final static Class<?> char_ = char.class;
		public final static Class<?> charWrap = java.lang.Character.class;
		
		public final static Class<?> byte_ = byte.class;
		public final static Class<?> byteWrap = java.lang.Byte.class;
		
		public final static Class<?> short_ = short.class;
		public final static Class<?> shortWrap = java.lang.Short.class;
		
		public final static Class<?> int_ = int.class;
		public final static Class<?> intWrap = java.lang.Integer.class;
		
		public final static Class<?> long_ = long.class;
		public final static Class<?> longWrap = java.lang.Long.class;
		
		public final static Class<?> float_ = float.class;
		public final static Class<?> floatWrap = java.lang.Float.class;
		
		public final static Class<?> double_ = double.class;
		public final static Class<?> doubleWrap = java.lang.Double.class;
		
		public final static Class<?> string_ = java.lang.String.class;
		
		public final static Class<?> simple_ = org.itas.core.Simple.class;
		
		public final static Class<?> resource_ = org.itas.core.Resource.class;
		
		public final static Class<?> enum_ = java.lang.Enum.class;

		public final static Class<?> enumByte_ = org.itas.core.EnumByte.class;
		
		public final static Class<?> enumInt_ = org.itas.core.EnumInt.class;
		
		public final static Class<?> enumString_ = org.itas.core.EnumString.class;
		
		public final static Class<?> list_ = java.util.List.class;
		
		public final static Class<?> set_ = java.util.Set.class;;
		
		public final static Class<?> concurrentMap_ = java.util.concurrent.ConcurrentMap.class;
		
		public final static Class<?> sortedMap_ = java.util.SortedMap.class;
		
		public final static Class<?> map_ = java.util.Map.class;
		
		public final static Class<?> timeStamp = Timestamp.class;

		public final static Class<?> gameObject = GameObject.class;

		public final static Class<?> gameBaseAotuID = GameObjectAotuID.class;

	}
	
	static class javassistType {
		
		public final static CtClass boolean_ = CtClass.booleanType;
		public final static CtClass booleanWrap;

		public final static CtClass char_ = CtClass.charType;
		public final static CtClass charWrap;

		public final static CtClass byte_ = CtClass.byteType;
		public final static CtClass byteWrap;

		public final static CtClass short_ = CtClass.shortType;
		public final static CtClass shortWrap;

		public final static CtClass int_ = CtClass.intType;
		public final static CtClass intWrap;

		public final static CtClass long_ = CtClass.longType;
		public final static CtClass longWrap;

		public final static CtClass float_ = CtClass.floatType;
		public final static CtClass floatWrap;

		public final static CtClass double_ = CtClass.doubleType;
		public final static CtClass doubleWrap;

		public final static CtClass string_;

		public final static CtClass simple_;

		public final static CtClass resource_;

		public final static CtClass enum_;

		public final static CtClass enumByte_;

		public final static CtClass enumInt_;

		public final static CtClass enumString_;

		public final static CtClass list_;

		public final static CtClass set_;

		public final static CtClass map_;
		
		public final static CtClass timeStamp;
		
		public final static CtClass gameObject;

		public final static CtClass gameBaseAotuID;

    static {
    	final ClassPool pool = ClassPool.getDefault();
    	try {
    		booleanWrap = pool.get(javaType.booleanWrap.getName());
    		charWrap = pool.get(javaType.charWrap.getName());
    		byteWrap = pool.get(javaType.byteWrap.getName());
    		shortWrap = pool.get(javaType.shortWrap.getName());
    		intWrap = pool.get(javaType.intWrap.getName());
    		longWrap = pool.get(javaType.longWrap.getName());
    		floatWrap = pool.get(javaType.floatWrap.getName());
				doubleWrap = pool.get(javaType.doubleWrap.getName());
				string_ = pool.get(javaType.string_.getName());
				simple_ = pool.get(javaType.simple_.getName());
				resource_ = pool.get(javaType.resource_.getName());
				enum_ = pool.get(javaType.enum_.getName());
				enumByte_ = pool.get(javaType.enumByte_.getName());
				enumInt_ = pool.get(javaType.enumInt_.getName());
				enumString_ = pool.get(javaType.enumString_.getName());
				list_ = pool.get(javaType.list_.getName());
				set_ = pool.get(javaType.set_.getName());
				map_ = pool.get(javaType.map_.getName());
				timeStamp = pool.get(javaType.timeStamp.getName());
				gameObject = pool.get(javaType.gameObject.getName());
				gameBaseAotuID = pool.get(javaType.gameBaseAotuID.getName());
			} catch (Exception e) {
					throw new ItasException(e);
			}
    }
	}
	
}
