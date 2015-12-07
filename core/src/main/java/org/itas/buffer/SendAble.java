package org.itas.buffer;

import java.nio.ByteBuffer;


/**
 * 处理发送数据接口
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月2日下午3:28:43
 */
public interface SendAble extends NetAble {

	/**
	 * <p>处理发送数据</p>
	 * @return 数据处理后的缓冲
	 */
	abstract ByteBuffer toBuffer();
	
}
