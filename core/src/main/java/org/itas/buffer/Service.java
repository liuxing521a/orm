package org.itas.buffer;

import java.nio.ByteBuffer;


/**
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-13
 */
public abstract class Service<T> {
	
	
	/**
	 * <p>事件头版半部分</p>
	 * @return 事件头
	 */
	public abstract byte PREFIX();

    /**
     * <p>事件分配器</p>
     * @param user 用户
     * @param suffix
     * @param buffer
     * @throws Exception
     */
	public abstract void dispatch(T user, byte suffix, ByteBuffer buffer) throws Exception;
	
}
