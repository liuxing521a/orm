package org.itas.common.net;

/**
 *	��Ϣ���빤��������������룩
 * @author Administrator
 */
public interface MessageCodecFactory {

	/**
	 * ����
	 * @return
	 */
    public abstract MessageEncoder createEncoder();

    /**
     * ����
     * @return
     */
    public abstract MessageDecoder createDecoder();
}
