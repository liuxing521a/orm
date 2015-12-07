package org.itas.common.net;

import org.itas.common.collection.ByteBuffer;

/**
 * ����
 * @author Administrator
 */
public interface MessageEncoder {
	
	/**
	 * ����
	 * @param message
	 * @param bytebuffer
	 */
    public abstract void encode(Message message, ByteBuffer bytebuffer);
    
}
