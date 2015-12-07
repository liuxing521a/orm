package org.itas.common.net;

/**
 * ��������� 
 * @author Administrator
 */
public interface NetServerListener {

	/**
	 * ���Ӵ򿪼���
	 * @param netconnection
	 */
    public abstract void connectionOpened(NetConnection netconnection);

    /**
     * ���ӹرռ���
     * @param netconnection
     */
    public abstract void connectionClosed(NetConnection netconnection);
}
