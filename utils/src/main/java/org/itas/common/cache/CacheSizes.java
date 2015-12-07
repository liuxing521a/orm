package org.itas.common.cache;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *	<p>占用内存计算工具类</p>
 *	用来估算常用对象占用内存大小（以字节为单位）
 *  @author liuzhen<liuxing521a@gmail.com>
 */
public class CacheSizes {

	public static int sizeOfObject() {
		return 4;
	}

	public static int sizeOfString(String str) {
		if (Objects.isNull(str)) {
			return 0;
		}

		return 4 + str.getBytes().length;
	}

	public static int sizeOfByte() {
		return 1;
	}

	public static int sizeOfBoolean() {
		return 1;
	}

	public static int sizeOfChar() {
		return 2;
	}

	public static int sizeOfShort() {
		return 2;
	}

	public static int sizeOfInt() {
		return 4;
	}

	public static int sizeOfFloat() {
		return 4;
	}

	public static int sizeOfLong() {
		return 8;
	}

	public static int sizeOfDouble() {
		return 8;
	}

	public static int sizeOfDate() {
		return 12;
	}

	public static int sizeOfMap(Map<?, ?> map) throws CalculateSizeException {
		if (Objects.isNull(map)) {
			return 0;
		}

		AtomicInteger size = new AtomicInteger(36);
		for (Entry<?, ?> entry : map.entrySet()) {
			size.addAndGet(sizeOf(entry.getKey()));
			size.addAndGet(sizeOf(entry.getValue()));
		}

		return size.get();
	}

	public static int sizeOfCollection(Collection<?> list) throws CalculateSizeException {
		if (Objects.isNull(list)) {
			return 0;
		}

		AtomicInteger size = new AtomicInteger(36);
		for (Object o : list) {
			size.addAndGet(sizeOf(o));
		}

		return size.get();
	}

	public static int sizeOf(Object o) throws CalculateSizeException {
		if (Objects.isNull(o)) {
			return 0;
		}
		
		if (o instanceof Cacheable) {
			return ((Cacheable) o).getCachedSize();
		}
		
		if (o instanceof Byte) {
			return sizeOfObject() + sizeOfByte();
		}
		
		if (o instanceof Boolean) {
			return sizeOfObject() + sizeOfBoolean();
		}
		
		if (o instanceof Character) {
			return sizeOfObject() + sizeOfChar();
		}
		
		if (o instanceof Short) {
			return sizeOfObject() + sizeOfShort();
		}
		
		if (o instanceof Integer) {
			return sizeOfObject() + sizeOfInt();
		}

		if (o instanceof Float) {
			return sizeOfObject() + sizeOfFloat();
		}
		
		if (o instanceof Long) {
			return sizeOfObject() + sizeOfLong();
		}

		if (o instanceof Double) {
			return sizeOfObject() + sizeOfDouble();
		}
		
		if (o instanceof String) {
			return sizeOfString((String) o);
		} 
		
		if (o instanceof Collection) {
			return sizeOfCollection((Collection<?>) o);
		}
		
		if (o instanceof Map) {
			return sizeOfMap((Map<?, ?>) o);
		} 

		if (o instanceof long[]) {
			long[] array = (long[]) o;
			return sizeOfObject() + array.length * sizeOfLong();
		} 

		if (o instanceof byte[]) {
			byte[] array = (byte[]) o;
			return sizeOfObject() + array.length;
		}
		
		int size = 1;
		try (NullOutputStream out = new NullOutputStream();
			ObjectOutputStream outObj = new ObjectOutputStream(out)){
			outObj.writeObject(o);
			size = out.size();
		} catch (IOException ioe) {
			throw new CalculateSizeException(o);
		}
		
		return size;
	}

	private static class NullOutputStream extends OutputStream {

		int size = 0;

		@Override
		public void write(int b) throws IOException {
			size++;
		}

		@Override
		public void write(byte[] b) throws IOException {
			size += b.length;
		}

		@Override
		public void write(byte[] b, int off, int len) {
			size += len;
		}

		public int size() {
			return size;
		}
	}
}
