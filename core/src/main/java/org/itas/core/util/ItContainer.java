package org.itas.core.util;

import static org.itas.core.bytecode.Type.enumByteType;
import static org.itas.core.bytecode.Type.enumIntType;
import static org.itas.core.bytecode.Type.enumStringType;
import static org.itas.core.bytecode.Type.resourceType;
import static org.itas.core.bytecode.Type.simpleType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.itas.common.Pair;
import org.itas.common.Utils.Objects;
import org.itas.core.EnumByte;
import org.itas.core.EnumInt;
import org.itas.core.EnumString;
import org.itas.core.HashId;

/**
 * 对象基类的提供处理集合的方法
 * 
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年1月27日下午3:04:09
 */
public class ItContainer {

	public static char parseChar(String text) {
		if (text == null || text.length() == 0) {
			return ' ';
		}

		return text.charAt(0);
	}

	public static String parseString(String text) {
		if (text == null || text.length() == 0) {
			return null;
		}

		return text;
	}

	public static String[] parseArray(String content) {
		if (Objects.isEmpty(content)) {
			return new String[] {};
		}

		final int begin = content.indexOf('[');
		final int end = content.indexOf(']');
		final char[] chs = content.toCharArray();
		final char[] sizeStr = Arrays.copyOfRange(chs, begin + 1, end);

		final int size = Integer.parseInt(new String(sizeStr));
		final String[] arrays = new String[size];

		int index = 0;
		final StringBuffer dataBuf = new StringBuffer();
		for (int i = end + 1; i < chs.length; i++) {
			if (chs[i] == '|') {
				arrays[index++] = dataBuf.toString();
				dataBuf.setLength(0);
				continue;
			}

			dataBuf.append(chs[i]);
		}

		return arrays;
	}

	public static String[][] parseDoubleArray(String content) {
		if (Objects.isEmpty(content)) {
			return new String[][] {};
		}

		final int begin = content.indexOf('[');
		final int middle = content.indexOf(',');
		final int end = content.indexOf(']');

		final char[] chs = content.toCharArray();
		final char[] rowStr = Arrays.copyOfRange(chs, begin + 1, middle);
		final char[] colStr = Arrays.copyOfRange(chs, middle + 1, end);

		final int row = Integer.parseInt(new String(rowStr));
		final int col = Integer.parseInt(new String(colStr));
		final String[][] arrays = new String[row][col];

		int rowIndex = 0;
		int colIndex = 0;
		final StringBuffer dataBuf = new StringBuffer();
		for (int i = end + 1; i < chs.length; i++) {
			if (chs[i] == '|') {
				if (colIndex >= col) {
					colIndex = 0;
					rowIndex++;
				}

				arrays[rowIndex][colIndex++] = dataBuf.toString();
				dataBuf.setLength(0);
				continue;
			}

			dataBuf.append(chs[i]);
		}

		return arrays;
	}

	@SuppressWarnings("unchecked")
	public static Pair<String, String>[] parsePair(String content) {
		if (Objects.isEmpty(content)) {
			return new Pair[] {};
		}

		final int begin = content.indexOf('[');
		final int end = content.indexOf(']');
		final char[] chs = content.toCharArray();
		final char[] sizeStr = Arrays.copyOfRange(chs, begin + 1, end);

		final int size = Integer.parseInt(new String(sizeStr));
		final Pair<String, String>[] arrays = new Pair[size];

		final StringBuffer k = new StringBuffer();
		final StringBuffer v = new StringBuffer();

		int index = 0;
		StringBuffer c = k;
		for (int i = end + 1; i < chs.length; i++) {
			if (chs[i] == ',') {
				c = v;
				continue;
			}

			if (chs[i] == '|') {
				arrays[index++] = new Pair<>(k.toString(), v.toString());
				k.setLength(0);
				v.setLength(0);
				c = k;
				continue;
			}

			c.append(chs[i]);
		}

		return arrays;
	}

	public static String toString(Collection<?> c) {
		if (Objects.isEmpty(c)) {
			return "";
		}

		final StringBuffer dataBuf = new StringBuffer(32 * c.size());
		dataBuf.append('[').append(c.size()).append(']');
		for (Object o : c) {
			dataBuf.append(toValue(o.getClass(), o)).append('|');
		}

		return dataBuf.toString();
	}

	public static String toString(Map<?, ?> m) {
		if (Objects.isEmpty(m)) {
			return "";
		}

		final StringBuilder dataBuf = new StringBuilder(64 * m.size());
		dataBuf.append('[').append(m.size()).append(']');
		for (Entry<?, ?> data : m.entrySet()) {
			dataBuf.append(toValue(data.getKey().getClass(), data.getKey())).append(',');
			dataBuf.append(toValue(data.getValue().getClass(), data.getValue())).append('|');
		}

		return dataBuf.toString();
	}

	public static String toString(Object[] array) {
		if (Objects.isNull(array)) {
			return "";
		}

		final StringBuffer dataBuf = new StringBuffer(32 * array.length);
		dataBuf.append('[').append(array.length).append(']');
		for (Object o : array) {
			dataBuf.append(toValue(o.getClass(), o)).append('|');
		}

		return dataBuf.toString();
	}

	public static String toString(Object[][] array) {
		if (Objects.isNull(array)) {
			return "";
		}

		final StringBuffer dataBuf = new StringBuffer(32 * array.length);
		dataBuf.append('[').append(array.length).append(',');
		dataBuf.append(array[0].length).append(']');

		for (Object[] os : array) {
			for (Object o : os) {
				dataBuf.append(toValue(o.getClass(), o)).append('|');
			}
		}

		return dataBuf.toString();
	}

	String textEncode(String text) {
		text = noNull(text, "");

		StringBuffer str = new StringBuffer();
		for (char ch : text.toCharArray()) {
			switch (ch) {
			case '|':
				str.append("&lwx;");
				break;
			case ',':
				str.append("&lyx;");
				break;
			default:
				str.append(ch);
				break;
			}
		}

		return str.toString();
	}

	String textDecode(String content) {
		StringBuilder textBuf = new StringBuilder();

		StringBuilder signBuf = null;
		for (char ch : content.toCharArray()) {
			/* begin 判断是转义符开始,创建转义缓冲 */
			if (ch == '&') {
				signBuf = new StringBuilder('&');
				continue;
			}/* end */

			/* begin 转义缓冲为空，加入字符缓冲 */
			if (Objects.isNull(signBuf)) {
				textBuf.append(ch);
				continue;
			}/* end */

			signBuf.append(ch);

			/* begin 超过转义串长度， 非转义，添加字符缓冲 */
			if (signBuf.length() > 5) {
				textBuf.append(signBuf.toString());
				signBuf = null;
				continue;
			} /* end */

			/* begin 小于转义串长度， 继续添加转义缓冲 */
			if (signBuf.length() < 5) {
				continue;
			}/* end */

			/* begin 竖线[|]转义，替换 */
			if ("&lwx;".equals(signBuf.toString())) {
				textBuf.append('|');
				signBuf = null;
				continue;
			} /* end */

			/* begin 逗号[,]转义，替换 */
			if ("&lyx;".equals(signBuf.toString())) {
				textBuf.append(',');
				signBuf = null;
				continue;
			} /* end */
		}

		if (Objects.nonNull(signBuf)) {
			textBuf.append(signBuf.toString());
		}

		return textBuf.toString();
	}

	private static Object toValue(Class<?> clazz, Object o) {
		if (simpleType.isType(clazz)) {
			return ((HashId) o).getId();
		} else if (resourceType.isType(clazz)) {
			return ((HashId) o).getId();
		} else if (enumByteType.isType(clazz)) {
			return ((EnumByte) o).key();
		} else if (enumIntType.isType(clazz)) {
			return ((EnumInt) o).key();
		} else if (enumStringType.isType(clazz)) {
			return ((EnumString) o).key();
		} else {
			return o;
		}
	}

	private String noNull(String str, String defaultStr) {
		return Objects.isNull(str) ? str : defaultStr;
	}

	private ItContainer() {
	}
}
