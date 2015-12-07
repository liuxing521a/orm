package org.itas.common.net;

import java.net.Socket;

/**
 * �׽���������
 * @author Administrator
 */
public interface SocketConsumer {

	/**
	 * ������ʱ
	 * @return ��ʱʱ��
	 */
    public abstract int getAcceptDelay();

    /**
     * �������Ƿ��ܽ�
     * @param socket
     * @return
     */
    public abstract boolean consumeSocket(Socket socket);

    /**
     * �Ƿ����������
     * @return
     */
    public abstract boolean acceptSocket();
}
