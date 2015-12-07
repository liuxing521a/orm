package org.itas.common.net;

import org.itas.common.collection.ArrayList;
import org.itas.common.collection.ByteBuffer;
import org.itas.common.collection.List;


public abstract class NetConnection {

	public static boolean NET_DEBUG = false;
	public static final int MAX_STORED_BYTES = 0x10000;
	public static final int MAX_SEND_SIZE = 8092;
	private List listeners;
	private Object attachment;
	private int maxBufferSize;
	private MessageDecoder messageDecoder;
	private MessageEncoder messageEncdoer;
	private ByteBuffer readBuffer;
	private ByteBuffer sendBuffer;
	private long createdTime;
	private long lastReceiveTime;
	private int pingTime;
	private int timeout;
	private int receivedMessageCount;
	private int receivedDataLength;
	private int sendedMessageCount;
	private int sendedDataLength;
	
	public NetConnection() {
		listeners = new ArrayList();
		maxBufferSize = 0x7fffffff;
		readBuffer = new ByteBuffer(1024);
		sendBuffer = new ByteBuffer(16384);
		pingTime = 0;
		timeout = 0;
	}
	
	public void addListener(NetConnectionListener netconnectionlistener) {
		listeners.add(netconnectionlistener);
	}

	public void setAttachment(Object obj) {
		attachment = obj;
	}

	public Object getAttachment() {
		return attachment;
	}

	public NetConnectionListener[] getAllListeners() {
		NetConnectionListener anetconnectionlistener[] = new NetConnectionListener[listeners.size()];
		listeners.toArray(anetconnectionlistener);
		return anetconnectionlistener;
	}

	public int getReceivedDataLength() {
		return receivedDataLength;
	}

	public MessageDecoder getMessageDecoder() {
		return messageDecoder;
	}

	public void setMessageDecoder(MessageDecoder messagedecoder) {
		messageDecoder = messagedecoder;
	}

	public void sendMessage(Message message) {
		synchronized (sendBuffer) {
			messageEncdoer.encode(message, sendBuffer);
		}
		if (sendBuffer.length() > 4000)
			flush();
		sendedMessageCount++;
	}

	public void sendAndFlushMessage(Message message) {
		synchronized (sendBuffer) {
			messageEncdoer.encode(message, sendBuffer);
		}
		flush();
		sendedMessageCount++;
	}

	public void update(long l) {
		if (lastReceiveTime == 0L)
			lastReceiveTime = l;
		if (pingTime > 0 && l - lastReceiveTime > (long) pingTime) {
			sendPingMessage();
			lastReceiveTime = l;
		}
		if (timeout > 0 && l - lastReceiveTime > (long) timeout) {
			if (NET_DEBUG)
				System.out.println("timeOutClose:" + timeout + ";" + toString());
			close();
		}
	}

	public int getSendedDataLength() {
		return sendedDataLength;
	}

	public void dispatchMessage(Message message) {
		receivedMessageCount++;
		for (int i = 0; i < listeners.size(); i++) {
			NetConnectionListener netconnectionlistener = (NetConnectionListener) listeners.get(i);
			netconnectionlistener.messageArrived(this, message);
		}

	}

	public void removeListener(NetConnectionListener netconnectionlistener) {
		listeners.remove(netconnectionlistener);
	}

	public void flush() {
		synchronized (sendBuffer) {
			int i = sendBuffer.length();
			if (i > 0) {
				i = Math.min(i, 8092);
				i = sendDataImpl(sendBuffer.getRawBytes(), 0, i);
				sendBuffer.setReadPos(i);
				sendBuffer.pack();
				sendedDataLength += i;
			}
			if (sendBuffer.length() >= 0x10000)
				sendBuffer.clear();
		}
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int i) {
		timeout = i;
	}


	public int getSendedMessageCount() {
		return sendedMessageCount;
	}

	protected void onDataRead(byte abyte0[], int i, int j, long l) {
		lastReceiveTime = l;
		receivedDataLength += j;
		if (messageDecoder != null) {
			readBuffer.writeBytes(abyte0, i, j);
			for (Message message = messageDecoder.decode(readBuffer);message != null; 
				   message = messageDecoder.decode(readBuffer)) {
				message.setSource(this);
				dispatchMessage(message);
			}
			readBuffer.pack();
		}
		if (readBuffer.available() > maxBufferSize) {
			if (NET_DEBUG)
				System.out.println("buffer data overflow:" + maxBufferSize + ";" + toString());
			synchronized (sendBuffer) {
				readBuffer.clear();
				close();
			}
		}
	}


	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long l) {
		createdTime = l;
	}

	public int getPingTime() {
		return pingTime;
	}

	public void setPingTime(int i) {
		pingTime = i;
	}

	public int getMaxBufferSize() {
		return maxBufferSize;
	}

	public void setMaxBufferSize(int i) {
		maxBufferSize = i;
	}

	protected void sendPingMessage() {
		sendAndFlushMessage(null);
	}

	public void removeAllListeners() {
		listeners.clear();
	}

	public MessageEncoder getMessageEncoder() {
		return messageEncdoer;
	}

	public void setMessageEncoder(MessageEncoder messageencoder) {
		messageEncdoer = messageencoder;
	}

	public int getReceivedMessageCount() {
		return receivedMessageCount;
	}
	
	public abstract String getHost();
	
	public abstract int getPort();
	
	public abstract int getLocalPort();
	
	protected abstract int sendDataImpl(byte abyte0[], int i, int j);
	
	public abstract void close();

	public abstract byte[] getHostIP();

	public abstract boolean isActive();
}
