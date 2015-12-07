package org.itas.common.net;

import org.itas.common.collection.ByteBuffer;

/**
 *	���� 
 * @author Administrator
 */
public interface MessageDecoder {

	/**
	 * ����
	 * @param message
	 * @param bytebuffer
	 */
    public abstract Message decode(ByteBuffer bytebuffer);
    
}
