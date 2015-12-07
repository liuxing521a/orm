package org.itas.common.net;

import org.itas.common.collection.ArrayList;
import org.itas.common.collection.Utils;
import java.io.IOException;
import java.net.Socket;


public class SimpleNetServer extends NetServer implements SocketConsumer,
		Runnable {

	public void stop() {
		try {
			socketFactory.stop();
			socketFactory = null;
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

	public int getAcceptDelay() {
		return acceptDelay;
	}

	public void setAcceptDelay(int i) {
		acceptDelay = i;
	}

	public int getConnectionCount() {
		return connectionList.size();
	}

	public SimpleNetServer() {
		connectionList = new ArrayList();
		acceptDelay = 100;
	}

	public boolean consumeSocket(Socket socket) {
		if (!acceptSocket())
			return false;
		try {
			SimpleConnection simpleconnection = new SimpleConnection(
					new TcpSocket(socket), this);
			simpleconnection.setCreatedTime(System.currentTimeMillis());
			synchronized (connectionList) {
				connectionList.add(simpleconnection);
			}
			connectionOpened(simpleconnection);
			Thread thread = new Thread(simpleconnection, simpleconnection
					.toString());
			thread.start();
			return true;
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		return false;
	}

	public boolean acceptSocket() {
		return getMaxConnections() <= 0
				|| getMaxConnections() > getConnectionCount();
	}

	public boolean start() {
		if (socketFactory == null) {
			try {
				socketFactory = new SocketFactory(getPort(), this);
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
				return false;
			}
			socketFactory.start();
			new Thread(this, "simpleNetServerIOThread").start();
		}
		return true;
	}

	public void run() {
		while (socketFactory != null)
			synchronized (connectionList) {
				int i = connectionList.size();
				for (int k = 0; k < i; k++) {
					SimpleConnection simpleconnection = (SimpleConnection) connectionList
							.get(k);
					if (!simpleconnection.isActive()) {
						connectionClosed(simpleconnection);
						connectionList.remove(k--);
						i--;
					}
				}

				Utils.sleep(50);
			}
		int j = connectionList.size();
		for (int l = 0; l < j; l++) {
			NetConnection netconnection = (NetConnection) connectionList.get(l);
			connectionClosed(netconnection);
			netconnection.close();
		}

		connectionList.clear();
	}

	private SocketFactory socketFactory;
	private ArrayList connectionList;
	private int acceptDelay;
}
