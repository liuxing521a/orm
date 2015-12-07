package org.itas.core.util;

/**
 * 第一个字符大小写改变
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年3月4日下午4:53:09
 */
public interface FirstChar {

  default String upCase(String str) {
	StringBuilder buffer = new StringBuilder(str.length());
		
	for (char ch : str.toCharArray()) {
	  if (buffer.length() == 0 && (ch >= 'a' && ch <= 'z')) {
        buffer.append((char)(ch - 32));
	  } else {
		buffer.append(ch);
	  }
	}

	return buffer.toString();
  }

  default String lowerCase(String str) {
	StringBuilder buffer = new StringBuilder(str.length());
			
	for (char ch : str.toCharArray()) {
	  if (buffer.length() == 0 && (ch >= 'A' && ch <= 'Z')) {
		buffer.append((char)(ch + 32));
	  } else {
		buffer.append(ch);
	  }
	}
		
	return buffer.toString();
  }
}
