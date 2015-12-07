package org.itas.common.net;

/**
 * ���紫������� 
 * @author Administrator
 */
public interface NetConnectionListener { 

	/**
	 * ��Ϣ����
	 * @param netconnection
	 * @param message
	 */
    public abstract void messageArrived(NetConnection netconnection, Message message);
    
}
