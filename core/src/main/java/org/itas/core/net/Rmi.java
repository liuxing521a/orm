package org.itas.core.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Rmi extends Remote {

	boolean isConnected() throws RemoteException;
	
}
