package net.itas.util;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.itas.util.GenericInfo;
import org.itas.util.Utils.ClassUtils;
import org.itas.util.Utils.GenericUtils;
import org.junit.Test;

public class TestClazzs {

	@Test
	public void testNewInstance() throws Exception {
		TestInt intInstance = ClassUtils.newInstance(TestInt.class, new Integer[]{10}, new Class[]{int.class});
		Assert.assertEquals(10, intInstance.id);
		
		TestLong longInstance = ClassUtils.newInstance(TestLong.class, new Long[]{100L}, new Class[]{long.class});
		Assert.assertEquals(100L, longInstance.id);
		
		TestString stringInstance = ClassUtils.newInstance(TestString.class, new String[]{"hello"}, new Class[]{String.class});
		Assert.assertEquals("hello", stringInstance.id);
	}
	
	@Test
	public void testGenerics() throws Exception {
		String generic1 = TestInt.class.getDeclaredField("aa").getGenericType().toString();
		String generic = "Ljava/util/List<Ljava/util/Map<Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/Integer;>;>;Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/util/Map<Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/Integer;>;>;Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/Integer;>;>;>;>;>;>;>;";
		
		GenericInfo info = GenericUtils.getGenericInfo(generic, true);
		GenericInfo info1 = GenericUtils.getGenericInfo(generic1, false);
		System.out.println(info);
		System.out.println(info1);
	}
	
	
}

class TestInt {
	
	TestInt(int Id) {
		this.id = Id;
	}
	
	int id;
	
	List<Map<Map<?, List<?>>, Map<String, List<Map<Map<String, List<Integer>>, Map<String, List<Integer>>>>>>> aa;;
}

class TestLong {
	
	TestLong(long Id) {
		this.id = Id;
	}
	
	long id;
}
class TestString {
	
	TestString(String Id) {
		this.id = Id;
	}
	
	String id;
}