package org.itas.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Utils {

	/** 
	 * 类工具
	 * @author liuzhen
	 */
	public static final class ClassUtils {

	    public static boolean isJavaBasicType(Class<?> clazz) {
	        if (clazz == null) {
	            return false;
	        }

	        return  clazz.isPrimitive() 
	        		|| Number.class.isAssignableFrom(clazz)
	                || Character.class.isAssignableFrom(clazz)
	                || Boolean.class.isAssignableFrom(clazz)
	                || CharSequence.class.isAssignableFrom(clazz)
	                || Enum.class.isAssignableFrom(clazz)
	                || Date.class.isAssignableFrom(clazz)
	                || Calendar.class.isAssignableFrom(clazz);
	    }

	    /**
	     * 判断指定的类是否为Collection（或者其子类或者其子接口）。
	     * @param clazz 需要判断的类
	     * @return true：是Collection false：非Collection
	     */
	    public static boolean isCollection(Class<?> clazz) {
	        if (clazz == null) {
	            return false;
	        }

	        return Collection.class.isAssignableFrom(clazz);
	    }

	    /**
	     * 判断指定的类是否为Map（或者其子类或者其子接口)。
	     * @param clazz 需要判断的类
	     * @return true：是Map false：非Map
	     */
	    public static boolean isMap(Class<?> clazz) {
	        if (clazz == null)  {
	            return false;
	        }

	        return Map.class.isAssignableFrom(clazz);
	    }

	    /**
	     * 判断指定的类是否为Java基本型别的数组。
	     * @param clazz 需要判断的类
	     * @return true：是Java基本型别的数组 false：非Java基本型别的数组
	     */
	    public static boolean isPrimitiveArray(Class<?> clazz) {
	        if (clazz == null) {
	        	return false;
	        } else if (clazz == byte[].class){
	        	return true;
	        } else if (clazz == short[].class) {
	        	return true;
	        } else if (clazz == int[].class) {
	        	return true;
	        } else if (clazz == long[].class) {
	        	return true;
	        } else if (clazz == char[].class) {
	        	return true;
	        } else if (clazz == float[].class) {
	        	return true;
	        } else if (clazz == double[].class) {
	        	return true;
	        } else if (clazz == boolean[].class) {
	        	return true;
	        } else {
	        	return false;
	        }
	    }

	    /**
	     * 判断指定的类是否为Java基本型别的数组。
	     * @param clazz 需要判断的类
	     * @return true：是Java基本型别的数组 false：非Java基本型别的数组
	     */
	    public static boolean isPrimitiveWrapperArray(Class<?> clazz)
	    {
	        if (clazz == null) {
	            return false;
	        } else if (clazz == Byte[].class) {
	            return true;
		    } else if (clazz == Short[].class) {
		    	return true;
			} else if (clazz == Integer[].class) {
	            return true;
			} else if (clazz == Long[].class) {
	            return true;
			} else if (clazz == Character[].class) {
	            return true;
			} else if (clazz == Float[].class) {
	            return true;
			} else if (clazz == Double[].class) {
	            return true;
			} else if (clazz == Boolean[].class) {
	            return true;
			} else {
	            return false;
	    	}
	    }

	    /**
	     * 获取包括该类本身但不包含java.lang.Object的所有超类。
	     * @param clazz Class
	     * @return 超类数组
	     */
	    public static List<Class<?>> getAllClass(Class<?> clazz) {
	        List<Class<?>> clazzList = new ArrayList<Class<?>>();
	        getAllSupperClass0(clazzList, clazz);
	        
	        return clazzList;
	    }

	    private static void getAllSupperClass0(List<Class<?>> clazzList,  Class<?> clazz) {
	        if (clazz.equals(Object.class)) {
	            return;
	        }
	        
	        clazzList.add(clazz);
	        getAllSupperClass0(clazzList, clazz.getSuperclass());
	    }
	    
	    /**
	     * 获取该类所有实现的接口数组。
	     * @param clazz Class
	     * @return 该类所有实现的接口数组
	     */
	    public static List<Class<?>> getAllInterface(Class<?> clazz) {
	        List<Class<?>> clazzList = new ArrayList<Class<?>>();
	        Class<?>[] interfaces = clazz.getInterfaces();

	        for (Class<?> interfaceClazz : interfaces) {
	            clazzList.add(interfaceClazz);
	            getAllSupperInterface0(clazzList, interfaceClazz);
	        }

	        return clazzList;
	    }

	    private static void getAllSupperInterface0(List<Class<?>> clazzList, Class<?> clazz) {
	        if (clazz.equals(Object.class)) {
	            return;
	        }

	        Class<?>[] interfaces = clazz.getInterfaces();
	        for (Class<?> interfaceClazz : interfaces) {
	            clazzList.add(interfaceClazz);
	            getAllSupperInterface0(clazzList, interfaceClazz);
	        }
	    }

	    /**
	     * 获取包括该类本身以及所有超类（不含java.lang.Object）和实现的接口中定义的属性。
	     * @param clazz Class
	     * @return 属性数组
	     */
	    public static List<Field> getAllField(Class<?> clazz) {
	        List<Field> fieldList = new ArrayList<Field>();
	        
	        List<Class<?>> supperClazzs = getAllClass(clazz);
	        for (Class<?> cls : supperClazzs) {
	        	Objects.addAll(fieldList, cls.getDeclaredFields());
			}
	        
	        List<Class<?>> supperInterfaces = getAllInterface(clazz);
	        for (Class<?> cls : supperInterfaces) {
	        	Objects.addAll(fieldList, cls.getDeclaredFields());
			}

	        return fieldList;
	    }
	    
	    /**
	     * child是否继承parent
	     * @param child 子类
	     * @param parent 父类
	     * @return child是parent子类 返回true 否则返回false
	     */
	    public static boolean isExtends(Class<?> child, Class<?> parent) {
	    	return parent.isAssignableFrom(child);
	    }
	    
	    /**
	     * 注册类
	     * @param className 目标注册类名
	     * @return 类class
	     */
	    public static Class<?> forName(String className) {
	    	try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(String.format("class not found exception:[%s]", className), e);
			}
	    }
	    
	    /**
	     * 获得泛型的类型
	     * @param cls 包含泛型的类
	     * @param fileName 泛型引用名
	     * @return 泛型类类型 class
	     */
	    public static Class<?> getGenericsType(Class<?> cls, String fileName) {
	    	try {
				Field field1 = cls.getDeclaredField(fileName);
				String type = field1.getGenericType().toString();
				
				int beginPos;
				int endPos;
				if ((beginPos = type.indexOf("<")) > 0 	&& (endPos = type.indexOf(">")) > 0) 
				{
					if (endPos < beginPos + 1) {
						return null;
					}
					
					type = type.substring(beginPos + 1, endPos);
					return forName(type);
				}
				
				return null;
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
	    }
	    
	    /**
	     * 创建对象
	     * @param className  类名
	     * @return 创建的新对象
	     */
	    public static <T> T newInstance(String className) {
	    	return newInstance(className, null, null);
	    }

	    /**
	     * 创建对象
	     * @param className  类名
	     * @return 创建的新对象
	     */
	    public static <T> T newInstance(Class<?> cls) {
	    	return newInstance(cls, null, null);
	    }
	    
	    /**
	     * 创建对象
	     * @param className  类名
	     * @param args 构造函数参数
	     * @param parameterTypes 构造函数参数类型
	     * @return 创建的新对象
	     */
	    public static <T> T newInstance(String className, Object[] args, Class<?>[] parameterTypes) {
	    	Class<?> cls = forName(className);
	    	return newInstance(cls, args, parameterTypes);
	    }
	    
	    /**
	     * 创建对象
	     * @param cls  类class
	     * @param args 构造函数参数
	     * @param parameterTypes 构造函数参数类型
	     * @return 创建的新对象
	     */
	    @SuppressWarnings("unchecked")
		public static <T> T newInstance(Class<?> cls, Object param, Class<?>[] paraTypes) {
			try {
				T t;
				if (Objects.isNull(param)) {
				    final Constructor<?> cons = cls.getDeclaredConstructor();
				    final boolean isAccess = cons.isAccessible();

				    cons.setAccessible( true );
				    t = (T)cons.newInstance();
				    cons.setAccessible(isAccess);
				} else {
					
					
					 
				    final Constructor<?> cons = cls.getDeclaredConstructor(paraTypes);
				    final boolean isAccess = cons.isAccessible();

				    cons.setAccessible(true);
				    if (param instanceof int[]) {
				    	t = (T)cons.newInstance((int[])param);
				    } else if (param instanceof long[]) {
				    	t = (T)cons.newInstance((long[])param);
				    } else {
				    	t = (T)cons.newInstance((Object[])param);
				    }
				    cons.setAccessible(isAccess);
				}

				return t;
			} catch (Exception e) {
				throw new ItasException(e);
			}
	    }
	    
	    public static List<Class<?>> loadClazz(Class<?> parentClass, String pack) throws IOException  {
			List<Class<?>> clazzList = new ArrayList<Class<?>>(32);
			
			File root = XResources.getResourceAsFile("/");
			String rootPath = root.getPath() + File.separator;

			root = new File(rootPath + pack.replace(".", File.separator));
			loadSonClass(clazzList, root, rootPath, parentClass);

			return clazzList;
		}

		private static <T> void loadSonClass(List<Class<?>> clazzList, File root, final String rootPath, Class<T> parentClass) {
			if (root.isDirectory()) {

				final File[] files = root.listFiles();
				assert files != null;
				for (final File file : files) {
					loadSonClass(clazzList, file, rootPath, parentClass);
				}

			} else {
				String className = null;
				try {
					if (root.getPath().endsWith(".class")) {
						className = root.getPath().replace(rootPath, "").replace(".class", "").replace(File.separator, ".");

						Class<?> clazz = XResources.classForName(className);
						if (parentClass.isAssignableFrom(clazz)) {
							clazzList.add(clazz);
						}
					}

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		
	    private ClassUtils() { }
	}
	
	public static final class Objects {
		
	    private static final String NULL = "null";
	    private static final String EMPTY_STR = "";

	    /** 定义toString时忽略的属性名称字符串集合 */
	    private static final String[] IGNORE_FIELD_NAMES =  {
	    	"log", "logger",  "serialVersionUID"
	    };

	    /** 定义toString时忽略的属性类名字符串集合 */
	    private static final String[] IGNORE_FIELD_CLASSES =  {
	        "org.apache.log4j.Logger",
	        "java.util.logging.Logger", 
	        "org.slf4j.Logger", 
	        "java.lang.Object", 
	        "java.lang.Class",
	        "java.lang.reflect.AccessibleObject", 
	        "java.lang.reflect.Field",
	        "java.lang.reflect.Method", 
	        "java.lang.reflect.Constructor"
	    };

	    // =====================================================
	    // isNull(); nonNull();
	    // isEmpty(); nonEmpty();
	    // =====================================================
	    /**
	     * 判断指定的对象是否为null。
	     * 
	     * @param obj 要判断的对象实例
	     * @return true：为null false：非null
	     */
	    public static boolean isNull(Object obj)
	    {
	        return obj == null;
	    }

	    /**
	     * 判断指定的对象是否不为null。
	     * 
	     * @param obj 要判断的对象实例
	     * @return true：非null false：为null
	     */
	    public static boolean nonNull(Object obj)
	    {
	        return !isNull(obj);
	    }

	    /**
	     * 判断指定的对象数组是否为空。
	     * 
	     * @param objs 要判断的对象数组实例
	     * @return true：对象数组为空 false：对象数组非空
	     */
	    public static boolean isEmpty(Object[] objs)
	    {
	        return isNull(objs) || objs.length == 0;
	    }

	    /**
	     * 判断指定的对象数组是否非空。
	     * 
	     * @param objs 要判断的对象数组实例
	     * @return true：对象数组非空 false：对象数组为空
	     */
	    public static boolean nonEmpty(Object[] objs)
	    {
	        return !isEmpty(objs);
	    }

	    /**
	     * 判断指定的集合类是否为空。<BR>
	     * 为空的含义是指：该集合为null，或者该集合不包含任何元素。
	     * 
	     * @param coll 要判断的集合实例
	     * @return true：集合为空 false：集合非空
	     */
	    public static boolean isEmpty(Collection<?> coll)
	    {
	        return isNull(coll) || coll.isEmpty();
	    }

	    /**
	     * 判断指定的集合类是否非空。 <BR>
	     * 非空的含义是指：该集合非null并且该集合包含元素。
	     * 
	     * @param coll 要判断的集合实例
	     * @return true：集合非空 false：集合为空
	     */
	    public static boolean nonEmpty(Collection<?> coll)
	    {
	        return !isEmpty(coll);
	    }

	    /**
	     * 判断指定的Map类是否为空。 <BR>
	     * 为空的含义是指：该Map为null，或者该Map不包含任何元素。
	     * 
	     * @param map 要判断的Map实例
	     * @return true：Map为空 false：Map非空
	     */
	    public static boolean isEmpty(Map<?, ?> map)
	    {
	        return isNull(map) || map.isEmpty();
	    }

	    /**
	     * 判断指定的Map类是否非空。<BR>
	     * 非空的含义是指：该Map非null并且该Map包含元素。
	     * 
	     * @param map 要判断的Map实例
	     * @return true：Map非空 false：Map为空
	     */
	    public static boolean nonEmpty(Map<?, ?> map)
	    {
	        return !isEmpty(map);
	    }

	    /**
	     * 判断指定的CharSequence类是否为空。 <BR>
	     * 为空的含义是指：该CharSequence为null，或者该CharSequence长度为0。<BR>
	     * 对String类型，虽然也属于CharSequence，<BR>
	     * 但因为通常字符串都有特殊的处理要求，所以使用方法isEmpty(String str);
	     * 
	     * @param charSeq 要判断的CharSequence实例
	     * @return true：CharSequence为空 false：CharSequence非空
	     */
	    public static boolean isEmpty(CharSequence charSeq)
	    {
	        return isNull(charSeq) || charSeq.length() == 0;
	    }

	    /**
	     * 判断指定的CharSequence类是否非空。 <BR>
	     * 非空的含义是指：该CharSequence非null并且该CharSequence包含元素。<BR>
	     * 对String类型，虽然也属于CharSequence， <BR>
	     * 但因为通常字符串都有特殊的处理要求，所以使用方法nonEmpty(String str);
	     * 
	     * @param charSeq 要判断的CharSequence实例
	     * @return true：CharSequence非空 false：CharSequence为空
	     */
	    public static boolean nonEmpty(CharSequence charSeq)
	    {
	        return !isEmpty(charSeq);
	    }

	    /**
	     * 判断指定的String类是否为空。 <BR>
	     * 为空的含义是指：该String为null，或者该String为空串""。
	     * 
	     * @param str 要判断的String实例
	     * @return true：String为空 false：String非空
	     */
	    public static boolean isEmpty(String str)
	    {
	        return isNull(str) || EMPTY_STR.equals(str);
	    }

	    /**
	     * 判断指定的String类是否非空。 <BR>
	     * 非空的含义是指：该String非null并且该String不为空串。
	     * 
	     * @param str 要判断的String实例
	     * @return true：String非空 false：String为空
	     */
	    public static boolean nonEmpty(String str)
	    {
	        return !isEmpty(str);
	    }

	    /**
	     * 判断指定的String类是否为空。<BR>
	     * 为空的含义是指：该String为null，或者该String为空串""。
	     * 
	     * @param str 要判断的String实例
	     * @return true：String为空 false：String非空
	     */
	    public static boolean isTrimEmpty(String str)
	    {
	        return isNull(str) || EMPTY_STR.equals(str.trim());
	    }

	    /**
	     * 判断指定的String类是否非空。 <BR>
	     * 非空的含义是指：该String非null并且该String不为空串。
	     * 
	     * @param str 要判断的String实例
	     * @return true：String非空 false：String为空
	     */
	    public static boolean isTrimNotEmpty(String str)
	    {
	        return !isTrimEmpty(str);
	    }

	    // =============================================================
	    // toString()
	    // 1. Java 基本型别，包括基本对象型别 直接调用对象本身的toString()
	    // 2. 简单的Bean类型 采用反射机制取得每个属性的值
	    // 3. Java集合类型 如：Collection，Map以及对象数组
	    // 4. 非Java基本型别的对象类型作为属性
	    // 5. enum处理：能正确调用object.toString()，归类为Java基本类型
	    // 6. 继承关系处理：先取得所有父类，再取得所有属性 递归toString
	    // 7. 内部类处理：内部类包含了一个对外部类的默认引用，其名称会包含"this$",忽略即可
	    // =============================================================

	    
	    public static <T> void addAll(List<T> list, T[] array) {
	        if (isNull(list) || isNull(array)) {
	            return;
	        }

	        for (T t : array) {
	            list.add(t);
	        }
	    }

	    /**
	     * 判断属性字段是否为toString时忽略的字段属性。
	     *
	     * @param field 需要判断是否忽略属性的字段。
	     * @return true：为忽略属性 false：非忽略属性
	     */
	    public static boolean isIgnoreField(Field field)
	    {
	        if (isNull(field))
	        {
	            return false;
	        }

	        // 根据名称判断总归比根据类名判断要快
	        return isIgnoreFieldByName(field) || isIgnoreFieldByClass(field);
	    }

	    /**
	     * 根据字段的Name判断是否该字段为已定义的忽略属性
	     *
	     * @param field 需要判断是否忽略属性的字段。
	     * @return true：为忽略属性 false：非忽略属性
	     */
	    private static boolean isIgnoreFieldByName(Field field)
	    {
	        // 如果是已定义的需要忽略的属性
	        for (String fieldName : IGNORE_FIELD_NAMES)
	        {
	            if (fieldName.equals(field.getName()))
	            {
	                return true;
	            }
	        }

	        // 如果是需要模糊匹配需要忽略的属性
	        // 说明：如果一个类定义了内部类，内部类保留一个对外部类的默认引用
	        // 这会导致递归toString时堆栈溢出，而这个默认引用又不需要toString，所以忽略
	        if (field.getName().indexOf("this$") != -1)
	        {
	            return true;
	        }

	        return false;
	    }

	    /**
	     * 根据字段的Class判断是否该字段为已定义的忽略属性
	     *
	     * @param field 需要判断是否忽略属性的字段。
	     * @return true：为忽略属性 false：非忽略属性
	     */
	    private static boolean isIgnoreFieldByClass(Field field)
	    {
	        Class<?> clazz = null;
	        for (String className : IGNORE_FIELD_CLASSES)
	        {
	        	clazz = ClassUtils.forName(className);
	            if (clazz != null && clazz.isAssignableFrom(field.getType()))
	            {
	                return true;
	            }
	        }

	        return false;
	    }
	    
	    /**
	     * 通用的toString方法。 <BR>
	     * 1. 以对象形式存在的基本型别数组，如：byte[],long[]需要特殊处理。<BR>
	     * @param obj 要转化为字符串的对象
	     * @return 转化后的字符串
	     */
	    public static String toString(Object obj) {
	        if (isNull(obj)) {
	            return NULL;
	        }

	        Class<?> clazz = obj.getClass();
	        if (clazz.equals(Object.class) || clazz.equals(Class.class)) {
	            return obj.toString();
	        }

	        // 如果是Java基本型别 可以直接调用toString得到正确的信息
	        if (ClassUtils.isJavaBasicType(clazz)) {
	            return obj.toString();
	        }

	        StringBuilder sb = new StringBuilder();

	        // 如果是数组类
	        if (clazz.isArray()) {
	            // Java基本型别的数组，可以利用Arrays.toString()完成转化，
	            // 这里转为对象数组再用deepToString()，只是为了添加附加信息和减少代码
	            if (ClassUtils.isPrimitiveArray(clazz)) {
	                Object[] objs = new Object[] {obj};
	                sb.append(clazz.getSimpleName()).append("=");
	                String objsStr = Arrays.deepToString(objs);
	                sb.append(objsStr.substring(1, objsStr.length() - 1));
	                return sb.toString();
	            }

	            // 基本类型的数组已经处理，这里只可能是对象数组。
	            // 将基本型别数组与对象数字分开处理的原因是：
	            // 基本型别数组可以利用Arrays.deepToString(Object[] objs)转为String
	            // 但对象数组中的对象没有声明toSring 方法时也需要deepToString，
	            // Arrays.deepToString方法就无能为力了
	            return toString((Object[])obj);
	        }

	        // 如果是集合类Collection
	        if (Collection.class.isAssignableFrom(clazz)) {
	            return toString((Collection<?>)obj);
	        }

	        // 如果是映射类Map
	        if (Map.class.isAssignableFrom(clazz)) {
	            return toString((Map<?, ?>) obj);
	        }

	        // 到这里，是Bean类型的对象，反射每个字段toString
	        sb.append(clazz.getSimpleName()).append("{");
	        List<Field> fields = ClassUtils.getAllField(clazz);

	        if (fields.size() > 0)
	        {
	            boolean isAppend = false;
	            for (Field field : fields) {
	                // 需要忽略的属性
	                if (isIgnoreField(field)) {
	                    continue;
	                }
	                
	                field.setAccessible(true);
	                
	                isAppend = true;
	                sb.append(field.getName()).append("=");
	                try {
	                    // 这里如果对每一个属性都调用ObjectUtil.toString()
	                    // 对内部类，会造成堆栈溢出，需要解决
	                    sb.append(toString(field.get(obj))).append(",");
	                } catch (Exception e) {
	                    sb.append("???,");
	                }
	            }
	            
	            if (isAppend) {
	                sb.deleteCharAt(sb.length() - 1);
	            }
	        }
	        sb.append("}");
	        return sb.toString();
	    }


	    /**
	     * 将对象数组转换为String。
	     * @param objs 对象数组
	     * @return 转换后的字符串
	     */
	    public static String toString(Object[] objs) {
	        if (isNull(objs)) {
	            return NULL;
	        }
	        
	        if (objs.length == 0) {
	            return "[]";
	        }

	        StringBuilder sb = new StringBuilder();
	        sb.append(objs.getClass().getSimpleName());
	        sb.append(String.format("(length=%s)={", objs.length));
	        
	        for (Object obj : objs) {
	            sb.append(toString(obj)).append(',');
	        }
	        sb.deleteCharAt(sb.length() - 1);
	        sb.append(']');

	        return sb.toString();
	    }

	    /**
	     * 将Collection转换为String
	     * @param coll Collection
	     * @return 转换后的字符串
	     */
	    public static String toString(Collection<?> coll) {
	        if (isNull(coll)) {
	            return NULL;
	        }
	        
	        if (coll.isEmpty()) {
	            return "{}";
	        }

	        StringBuilder sb = new StringBuilder();
	        sb.append(coll.getClass().getSimpleName());
	    	sb.append(String.format("(size=%s)={", coll.size()));
	        
	    	for (Object o : coll) {
	    		sb.append(toString(o)).append(',');
			}

	        sb.deleteCharAt(sb.length() - 1);
	        sb.append('}');

	        return sb.toString();
	    }

	    /**
	     * 将Map转换为String
	     * @param map Map
	     * @return 转换后的字符串
	     */
	    public static String toString(Map<?, ?> map) {
	        if (isNull(map)) {
	            return NULL;
	        }
	        
	        if (map.isEmpty()) {
	            return "{}";
	        }

	        StringBuilder sb = new StringBuilder();
	        sb.append(map.getClass().getSimpleName());
	        sb.append(String.format("(size=%s)={", map.size()));

	        for (Entry<?, ?> entry : map.entrySet()) {
	        	sb.append(toString(entry.getKey())).append('=');
	        	sb.append(toString(entry.getValue())).append(',');
			}
	        
	        sb.deleteCharAt(sb.length() - 1);
	        sb.append('}');

	        return sb.toString();
	    }


	    /**
	     * 通用的equals 方法
	     * @param a 对象a
	     * @param b 对象b
	     * @return true 两个对象值相同 false 两个对象值不相同
	     */
	    public static boolean equals(Object a, Object b) {
	    	 return a == b || (nonNull(a) && a.equals(b));
	    }
	    
	    public static int hashCode(Object... objects) {
	    	return Arrays.hashCode(objects);
	    }
	}
	
	/** 
	 * 文件相关操作
	 * @author liuzhen
	 */
	public static final class FileUtils {
	    
		public static InputStream getResourceAsInputStream(String url) throws IOException {
	    	return new FileInputStream(new File(url));
	    }
		
		public static Properties getResource(String url) throws IOException {
			Properties properties = new Properties();
			properties.load(getResourceAsInputStream(url));
			
			return properties;
		}
		
		public static File toFile(URL url) {
			if (url == null || !url.getProtocol().equals("file")) {
	            return null;
	        } else {
	            String filename = url.getFile().replace('/', File.separatorChar);
	            int pos =0;
	            while ((pos = filename.indexOf('%', pos)) >= 0) {
	                if (pos + 2 < filename.length()) {
	                    String hexStr = filename.substring(pos + 1, pos + 3);
	                    char ch = (char) Integer.parseInt(hexStr, 16);
	                    filename = filename.substring(0, pos) + ch + filename.substring(pos + 3);
	                }
	            }
	            
	            return new File(filename);
	        }
	    }
		
	    public static StringBuffer readByBufferReader( String fileName ) 
		throws IOException 
		{
	        BufferedReader read = null;
	        InputStreamReader isr = null;
	        try {
	        	final File file = new File(fileName);
	            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
	            read = new BufferedReader(isr);

	            StringBuffer buffer = new StringBuffer();
	            String line = null;
	            while ((line = read.readLine()) != null)
	            {
	            	buffer.append(line).append("\r\n");
	            }
	            
	            return buffer;
	        }  finally {
	        	if (Objects.nonNull(isr))
	        	{
	                isr.close();
	        	}
	        	if (Objects.nonNull(read))
	        	{
	                read.close();
	        	}
	        }
	    }

	    @Deprecated
	    static void readFileByRandomAccess(String fileName) {
	        RandomAccessFile randomFile = null;
	        try {
	            randomFile = new RandomAccessFile(fileName, "r");
	            long fileLength = randomFile.length();
	            int beginIndex = (fileLength > 4) ? 4 : 0;
	            randomFile.seek(beginIndex);
	            byte[] bytes = new byte[2<<4];
	            int byteread = 0;
	            while ((byteread = randomFile.read(bytes)) != -1) 
	            {
	                System.out.write(bytes, 0, byteread);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (randomFile != null) {
	                try {
	                    randomFile.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
	    
		public static StringBuffer readByDataInputStream(String file) throws Exception {
			ByteArrayInputStream bais = null;
			DataInputStream dis = null;
			try {
				bais = new ByteArrayInputStream(readByBufferReader(file).toString().getBytes());
				dis = new DataInputStream(bais);

				StringBuffer buffer = new StringBuffer(bais.available());
				while (dis.available() > 0) 
				{
					buffer.append((char) dis.read());
				}
				
				return buffer;
			} finally {
				if (Objects.nonNull(dis))
				{
					dis.close();
				}
				if (Objects.nonNull(bais)) 
				{
					bais.close();
				}
			}
		}
		
		public static StringBuffer readByBufferedInputStreamNoArray(String file) throws IOException 
		{
			FileInputStream fis = null;
			InputStream is = null;
			try {
				fis = new FileInputStream(new File(file));
				is = new BufferedInputStream(fis);
				
				StringBuffer buffer = new StringBuffer(is.available());
				while (is.available() > 0) 
				{
					buffer.append((char)is.read());
				}
				
				return buffer;
			} finally {
				if (Objects.nonNull(is)) 
				{
					is.close();
				}
				if (Objects.nonNull(fis))
				{
					fis.close();
				}
			}
		}

		public static StringBuffer readByBufferedInputStream(String file) throws Exception 
		{
			FileInputStream fis = null;
			BufferedInputStream input = null;
			try
			{
				fis = new FileInputStream(file);
				input = new BufferedInputStream(fis);
				byte[] bytes = new byte [1024];
				
				StringBuffer buffer = new StringBuffer(fis.available());
				int avaliable = 0;
				while ((avaliable = input.available()) > 0) 
				{
					if (avaliable < bytes.length) 
					{
						bytes = new byte[avaliable];
					} 
					input.read(bytes);
					buffer.append(new String(bytes, "UTF-8"));
				}
				
				return buffer;
			} finally  
			{
				
				if (Objects.nonNull(input))
				{
					input.close();
				}
				if (Objects.nonNull(fis))
				{
					fis.close();
				}
			}
		}
		
		public static StringBuffer readByChannel(String file) throws Exception 
		{
			FileInputStream input = null;
			try {
				input = new FileInputStream(file);
				FileChannel in = input.getChannel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				
				StringBuffer strBuffer = new StringBuffer((int)in.size());
				while (in.read(buffer) != -1) 
				{
					buffer.flip(); 
					strBuffer.append(buffer.getChar());
					buffer.clear(); 
				}
				
				return strBuffer;
			}  finally  
			{
				if (input != null)
				{
					input.close();
				}
			}
		}
		
		public static StringBuffer readByChannelMap(String file) throws Exception 
		{
			FileInputStream input = null;
			try {
				input = new FileInputStream(new File(file));
				FileChannel fc = input.getChannel();
				CharBuffer cb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asCharBuffer();
				
				StringBuffer buf = new StringBuffer((int)fc.size());
				while (cb.hasRemaining())
				{
					buf.append(cb.get());
				}
				
				return buf;
			} finally  
			{
				if (Objects.nonNull(input))
				{
					input.close();
				}
			}
		}
		
		public static void writeFile(File file, String content) throws IOException 
		{
			OutputStreamWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			try {
				fileWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}  finally  
			{
				if (Objects.nonNull(bufferedWriter))
				{
					bufferedWriter.close();
				}
				if (Objects.nonNull(fileWriter))
				{
					fileWriter.close();
				}
			}
		}
		
		public static void setFileDir(String dir) 
		{
			System.setProperty("game-file-dir", dir);
		}
		
		private FileUtils(){}
	}


	
	/**
	 * 生成32位随机序列号
	 * @author liuzhen
	 */
	public static final class SerialUtils {

	    private static final char[] CODES = {
	            'a', 'b', 'c', 'd', 'e', 'f', 'g',
	            'h', 'i', 'j', 'k', 'l', 'm', 'n',
	            'o', 'p', 'q',      'r', 's', 't',
	            'u', 'v', 'w',      'x', 'y', 'z',
	            'A', 'B', 'C', 'D', 'E', 'F', 'G',
	            'H', 'I', 'J', 'K', 'L', 'M', 'N',
	            'O', 'P', 'Q',      'R', 'S', 'T',
	            'U', 'V', 'W',      'X', 'Y', 'Z',
	            '1', '2', '3', '4', '5', '6', '7',
	            '8', '9', '0'

	    } ;

	    public static String autoGenerate() {
	        final int len = CODES.length;

	        StringBuilder code = new StringBuilder(32);
	        for (int i = 0; i < 32; i ++) {
	            code.append(CODES[ThreadLocalRandom.current().nextInt(len)]);
	        }

	        return code.toString();
	    }

	    private SerialUtils() { }
	}
	
	/** 
	 * 日期相关
	 * @author liuzhen
	 */
	public static final class DataUtils {
		
		static final ThreadLocal<SimpleDateFormat> YYMMDD_HH_MM_SS = new ThreadLocal<SimpleDateFormat>() {
			@Override protected SimpleDateFormat initialValue() {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
		};
		
		static final ThreadLocal<SimpleDateFormat> YYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
			@Override protected SimpleDateFormat initialValue() {
				return new SimpleDateFormat("yyyyMMddHHmmss");
			}
		};;
		
		public static String YYMMDD_HH_MM_SS(Date date) {
			return YYMMDD_HH_MM_SS.get().format(date);
		}

		public static String YYMMDDHHMMSS(Date date) {
			return YYMMDDHHMMSS.get().format(date);
		}
		
		private DataUtils(){}
	}
	
	/** 
	 * 时间相关
	 * @author liuzhen
	 */
	public static final class TimeUtil {

		private static volatile long _systemTime = System.currentTimeMillis();
		public static final long MILLIS_PER_SECOND = 1000L;
		public static final long MILLIS_PER_MINUTE = 60000L;
		public static final long MILLIS_PER_HOUR = 3600000L;
		public static final long MILLIS_PER_DAY = 86400000L;
		public static final int SECONDS_PER_MINUTE = 60;
		public static final int SECONDS_PER_HOUR = 3600;
		public static final int SECONDS_PER_DAY = 86400;
		public static final int SECONDS_PER_WEEK = 604800;
		private static final long systemTimeTick = Long.parseLong(System.getProperty("systime.time.tick", "200"));

		private static final ScheduledExecutorService systemTimeTickExecutor = Executors.newSingleThreadScheduledExecutor();

		static {
			systemTimeTickExecutor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					TimeUtil.accessTime(System.currentTimeMillis());
				}
			}, systemTimeTick, systemTimeTick, TimeUnit.MILLISECONDS);
		}


		private static void accessTime(final long millis) {
			_systemTime = millis;
		}

		/** 当前时间 200毫秒更新一次 */
		public static long systemTime() {
			return _systemTime;
		}

		public static int systemTimeSec() {
			return (int) (_systemTime / 1000L);
		}

		public static Timestamp timestampNow() {
			return new Timestamp(_systemTime);
		}

		public static Date dateNow() {
			return new Date(_systemTime);
		}

		public static Timestamp dateTimeNow() {
			return new Timestamp(systemTime());
		}

		private TimeUtil() {
		}
	}
	
	/**
	 * 安全相关
	 * @author liuzhen
	 */
	public static final class SecureUtils {

		public static String MD5(String...strs) throws Exception {
			String str = String.join("", strs);
			return MD5(str.getBytes());
		}

		public static String MD5(byte[] data) throws Exception {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();  
			
			md5.update(data);  
			byte[] digest = md5.digest();  
			
			StringBuilder buf = new StringBuilder(digest.length);
			for (byte bs : digest) {     
				buf.append(Character.forDigit((bs & 0xF0) >> 4, 16));
				buf.append(Character.forDigit((bs & 0xF), 16));
			}  

			return buf.toString();
		}

		public static String MD5Encrypt(String inStr) throws Exception {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(inStr.getBytes());
			
			return bytetoString(digest);
		}

		private static String bytetoString(byte[] digest) {
			StringBuffer buf = new StringBuffer();
			
			String tempStr;
			for (byte bs : digest) {
				tempStr = Integer.toHexString(bs & 0xff);
				if (tempStr.length() == 1) {
					buf.append('0');
				}
				
				buf.append(tempStr);
			}
			
			return buf.toString().toLowerCase();
		}
		
		private SecureUtils() { }
	}
	
	/** 
	 * 泛型相关
	 * @author liuzhen
	 */
	public static final class GenericUtils {
		
		public static List<Type> getGenerics(Field field) {
			ParameterizedType type = (ParameterizedType) field.getGenericType();
			Type[] types = type.getActualTypeArguments();
			
			List<Type> typeList = new ArrayList<>(types.length);
			for (Type t : types) {
				if (t instanceof ParameterizedType) {
					typeList.add(((ParameterizedType)t).getRawType());
				} else {
					typeList.add(t);
				}
			}
			
			return typeList;
		}
		
		public static List<GenericInfo> getGenerics(String genericStr, boolean isByteCode) {
			return getGenericInfo(genericStr, isByteCode).getChilds();
		}
		
		public static GenericInfo getGenericInfo(String genericStr, boolean isByteCode) {
			if (Objects.isEmpty(genericStr)) {
				return null;
			}
			
			if (isByteCode) {
				genericStr = genericStr.replaceAll(";>", ">");
			}

			GenericInfo root = new GenericInfo();
			GenericInfo current = root;
			char[] chs = genericStr.toCharArray();
			for (char ch : chs) {
				
				if (ch == '<') {
					GenericInfo tmp = new GenericInfo();
					tmp.setParent(current);
					current = tmp;
				} else if (ch == '>') {
					if (Objects.nonNull(current.getParent())) {
						current.getParent().addChild(current, isByteCode);
						current = current.getParent();
					}
				} else if (ch == ';' || ch == ',') {
					if (Objects.nonNull(current.getParent())) {
						current.getParent().addChild(current, isByteCode);
					}
					
					GenericInfo tmp = new GenericInfo();
					tmp.setParent(current.getParent());
					current = tmp;
				} else if (ch == '/') {
					current.addName('.');
				} else if (ch == ' ') {
					// do nothing
				}/* else if (ch == '*') {不支持
					current.addName("Ljava.lang.Object");
				} else if (ch == '?') {
					current.addName("java.lang.Object");
				}  */else {
					current.addName(ch);
				}
			}
			
			if (isByteCode) {
				root.deleteChildFirst();
			}
			return root;
		}
		
		
		private GenericUtils() { }
	}
	
	/** 
	 * 网络Ip相关
	 * @author liuzhen
	 */
	public static final class InetUtils {
		private static final Pattern IPV4_PATTERN = 
	        Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

	    private static final Pattern IPV6_STD_PATTERN = 
	        Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

	    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = 
	        Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

	    public static boolean isIPv4Address(final String input) {
	        return IPV4_PATTERN.matcher(input).matches();
	    }

	    public static boolean isIPv6StdAddress(final String input) {
	        return IPV6_STD_PATTERN.matcher(input).matches();
	    }

	    public static boolean isIPv6HexCompressedAddress(final String input) {
	        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
	    }

	    public static boolean isIPv6Address(final String input) {
	        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input); 
	    }
	}
	
	public interface Charsets {
		
		  public static final Charset US_ASCII = Charset.forName("US-ASCII");

		  public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

		  public static final Charset UTF_8 = Charset.forName("UTF-8");

		  public static final Charset UTF_16BE = Charset.forName("UTF-16BE");

		  public static final Charset UTF_16LE = Charset.forName("UTF-16LE");

		  public static final Charset UTF_16 = Charset.forName("UTF-16");

		}
	
	/**
	 * 操作系统相关
	 * @author liuzhen
	 */
	public static final class OS {
		
		/** 获取当前操作系统名称*/
		private static String OS = System.getProperty("os.name").toLowerCase();

		private static OS instance = new OS();

		private EPlatform platform;

		private OS() {
		}

		public static boolean isLinux() {
			return OS.indexOf("linux") >= 0;
		}

		public static boolean isMacOS() {
			return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
		}

		public static boolean isMacOSX() {
			return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
		}

		public static boolean isWindows() {
			return OS.indexOf("windows") >= 0;
		}

		public static boolean isOS2() {
			return OS.indexOf("os/2") >= 0;
		}

		public static boolean isSolaris() {
			return OS.indexOf("solaris") >= 0;
		}

		public static boolean isSunOS() {
			return OS.indexOf("sunos") >= 0;
		}

		public static boolean isMPEiX() {
			return OS.indexOf("mpe/ix") >= 0;
		}

		public static boolean isHPUX() {
			return OS.indexOf("hp-ux") >= 0;
		}

		public static boolean isAix() {
			return OS.indexOf("aix") >= 0;
		}

		public static boolean isOS390() {
			return OS.indexOf("os/390") >= 0;
		}

		public static boolean isFreeBSD() {
			return OS.indexOf("freebsd") >= 0;
		}

		public static boolean isIrix() {
			return OS.indexOf("irix") >= 0;
		}

		public static boolean isDigitalUnix() {
			return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
		}

		public static boolean isNetWare() {
			return OS.indexOf("netware") >= 0;
		}

		public static boolean isOSF1() {
			return OS.indexOf("osf1") >= 0;
		}

		public static boolean isOpenVMS() {
			return OS.indexOf("openvms") >= 0;
		}

		/**
		 * 获取操作系统名字
		 * 
		 * @return 操作系统名
		 */
		public static EPlatform getOSname() {
			if (isAix()) {
				instance.platform = EPlatform.AIX;
			} else if (isDigitalUnix()) {
				instance.platform = EPlatform.Digital_Unix;
			} else if (isFreeBSD()) {
				instance.platform = EPlatform.FreeBSD;
			} else if (isHPUX()) {
				instance.platform = EPlatform.HP_UX;
			} else if (isIrix()) {
				instance.platform = EPlatform.Irix;
			} else if (isLinux()) {
				instance.platform = EPlatform.Linux;
			} else if (isMacOS()) {
				instance.platform = EPlatform.Mac_OS;
			} else if (isMacOSX()) {
				instance.platform = EPlatform.Mac_OS_X;
			} else if (isMPEiX()) {
				instance.platform = EPlatform.MPEiX;
			} else if (isNetWare()) {
				instance.platform = EPlatform.NetWare_411;
			} else if (isOpenVMS()) {
				instance.platform = EPlatform.OpenVMS;
			} else if (isOS2()) {
				instance.platform = EPlatform.OS2;
			} else if (isOS390()) {
				instance.platform = EPlatform.OS390;
			} else if (isOSF1()) {
				instance.platform = EPlatform.OSF1;
			} else if (isSolaris()) {
				instance.platform = EPlatform.Solaris;
			} else if (isSunOS()) {
				instance.platform = EPlatform.SunOS;
			} else if (isWindows()) {
				instance.platform = EPlatform.Windows;
			} else {
				instance.platform = EPlatform.Others;
			}
			return instance.platform;
		}
	}
	
	/**
	 * 操作系统类型
	 * @author liuzhen
	 */
	public enum EPlatform {  
	    Any { 
	    	@Override 
	    	public String toString() {
    			return "any";
    		}
    	},  
	    Linux { 
	    	@Override 
	    	public String toString() {
    			return "Linux";
    		}
    	},
	    Mac_OS { 
	    	@Override 
	    	public String toString() {
    			return "Mac OS";
    		}
    	},  
	    Mac_OS_X { 
	    	@Override 
	    	public String toString() {
    			return "Mac OS X";
    		}
    	},  
	    Windows { 
	    	@Override 
	    	public String toString() {
    			return "Windows";
    		}
    	},  
	    OS2 { 
	    	@Override 
	    	public String toString() {
    			return "OS/2";
    		}
    	},  
	    Solaris { 
	    	@Override 
	    	public String toString() {
    			return "Solaris";
    		}
    	},  
	    SunOS { 
	    	@Override 
	    	public String toString() {
    			return "SunOS";
    		}
    	},  
	    MPEiX { 
	    	@Override 
	    	public String toString() {
    			return "MPE/iX";
    		}
    	},  
	    HP_UX { 
	    	@Override 
	    	public String toString() {
    			return "HP-UX";
    		}
    	},  
	    AIX { 
	    	@Override 
	    	public String toString() {
    			return "AIX";
    		}
    	},  
	    OS390 { 
	    	@Override 
	    	public String toString() {
    			return "OS/390";
    		}
    	},  
	    FreeBSD { 
	    	@Override 
	    	public String toString() {
    			return "FreeBSD";
    		}
    	},  
	    Irix { 
	    	@Override 
	    	public String toString() {
    			return "Irix";
    		}
    	},  
	    Digital_Unix { 
	    	@Override 
	    	public String toString() {
    			return "Digital Unix";
    		}
    	},  
	    NetWare_411 { 
	    	@Override 
	    	public String toString() {
    			return "NetWare";
    		}
    	},  
	    OSF1 { 
	    	@Override 
	    	public String toString() {
    			return "OSF1";
    		}
    	},  
	    OpenVMS { 
	    	@Override 
	    	public String toString() {
    			return "OpenVMS";
    		}
    	},  
	    Others { 
	    	@Override 
	    	public String toString() {
    			return "Others";
    		}
    	};  
	      
	    private EPlatform(){  
	    }  
	}  
	
	static public final class Base64 {
	   
		private static final char[] lookup = new char[64];
	    private static final byte[] reverseLookup = new byte[256];

	    static {
	        for (int i = 0; i < 26; i++) {
	            lookup[i] = (char) ('A' + i);
	        }

	        for (int i = 26, j = 0; i < 52; i++, j++) {
	            lookup[i] = (char) ('a' + j);
	        }

	        for (int i = 52, j = 0; i < 62; i++, j++) {
	            lookup[i] = (char) ('0' + j);
	        }

	        lookup[62] = '+';
	        lookup[63] = '/';

	        for (int i = 0; i < 256; i++) {
	            reverseLookup[i] = -1;
	        }

	        for (int i = 'Z'; i >= 'A'; i--) {
	            reverseLookup[i] = (byte) (i - 'A');
	        }

	        for (int i = 'z'; i >= 'a'; i--) {
	            reverseLookup[i] = (byte) (i - 'a' + 26);
	        }

	        for (int i = '9'; i >= '0'; i--) {
	            reverseLookup[i] = (byte) (i - '0' + 52);
	        }

	        reverseLookup['+'] = 62;
	        reverseLookup['/'] = 63;
	        reverseLookup['='] = 0;
	    }

	    public static String encode(byte[] bytes) {
	        // always sequence of 4 characters for each 3 bytes; padded with '='s as necessary:
	        StringBuilder buf = new StringBuilder(((bytes.length + 2) / 3) * 4);

	        // first, handle complete chunks (fast loop)
	        int i = 0;
	        for (int end = bytes.length - 2; i < end; ) {
	            int chunk = ((bytes[i++] & 0xFF) << 16)
	                    | ((bytes[i++] & 0xFF) << 8)
	                    | (bytes[i++] & 0xFF);
	            buf.append(lookup[chunk >> 18]);
	            buf.append(lookup[(chunk >> 12) & 0x3F]);
	            buf.append(lookup[(chunk >> 6) & 0x3F]);
	            buf.append(lookup[chunk & 0x3F]);
	        }

	        // then leftovers, if any
	        int len = bytes.length;
	        if (i < len) { // 1 or 2 extra bytes?
	            int chunk = ((bytes[i++] & 0xFF) << 16);
	            buf.append(lookup[chunk >> 18]);
	            if (i < len) { // 2 bytes
	                chunk |= ((bytes[i] & 0xFF) << 8);
	                buf.append(lookup[(chunk >> 12) & 0x3F]);
	                buf.append(lookup[(chunk >> 6) & 0x3F]);
	            } else { // 1 byte
	                buf.append(lookup[(chunk >> 12) & 0x3F]);
	                buf.append('=');
	            }
	            buf.append('=');
	        }
	        return buf.toString();
	    }

	    public static byte[] decode(String encoded) {
	        int padding = 0;

	        for (int i = encoded.length() - 1; encoded.charAt(i) == '='; i--) {
	            padding++;
	        }

	        int length = encoded.length() * 6 / 8 - padding;
	        byte[] bytes = new byte[length];

	        for (int i = 0, index = 0, n = encoded.length(); i < n; i += 4) {
	            int word = reverseLookup[encoded.charAt(i)] << 18;
	            word += reverseLookup[encoded.charAt(i + 1)] << 12;
	            word += reverseLookup[encoded.charAt(i + 2)] << 6;
	            word += reverseLookup[encoded.charAt(i + 3)];

	            for (int j = 0; j < 3 && index + j < length; j++) {
	                bytes[index + j] = (byte) (word >> (8 * (2 - j)));
	            }

	            index += 3;
	        }

	        return bytes;
	    }
	    
	    private Base64() {
	    }
	}
}
