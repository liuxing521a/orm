//package org.itas.core.net;
//
//import java.rmi.Naming;
//import java.rmi.RemoteException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import org.itas.core.net.Hosts.Address;
//import org.itas.core.resource.HostRes;
//import org.itas.util.Logger;
//
//public abstract class RmiClient {
//
//	private static final RmiClient instance = new RmiClient(){};
//	
//	private final Map<String, Map<Class<? extends Rmi>, Rmi>> looksMap;
//
//	private RmiClient() {
//		this.looksMap = new HashMap<>();
//	}
//	
//	public static RmiClient getDefault() {
//		return instance;
//	}
//	
//	public void Initializer(HostRes host, List<Class<? extends Rmi>> clazzList) throws Exception {
//		for (Class<? extends Rmi> clazz : clazzList) {
//			addCache(host.getHost(), host.getPort(), clazz);
//		}
//	}
//	public void Initializer(Address address, List<Class<? extends Rmi>> clazzList) throws Exception {
//		for (Class<? extends Rmi> clazz : clazzList) {
//			addCache(address.getHost(), address.getPort(), clazz);
//		}
//	}
//	
//	public void addCache(String host, int port, Class<? extends Rmi> clazz) throws Exception {
//		getRmiCache(host, port).put(clazz, null);
//	}
//	
//	public Rmi lookUp(HostRes host, Class<? extends Rmi> clazz) throws Exception {
//		return lookUp(host.getHost(), host.getPort(), clazz);
//	}
//
//	public Rmi lookUp(Address host, Class<? extends Rmi> clazz) throws Exception {
//		return lookUp(host.getHost(), host.getPort(), clazz);
//	}
//	
//	private Rmi lookUp(String host, int port, Class<? extends Rmi> clazz) throws Exception {
//		Map<Class<? extends Rmi>, Rmi> remoteMap = looksMap.get(String.format("%s:%s", host, port));
//		if (Objects.isNull(remoteMap)) {
//			throw new IllegalArgumentException("errror binding host:" + host + ":" + port+ ", class:" + clazz.getSimpleName());
//		}
//
//		Rmi rmi;
//		try {
//			if (!remoteMap.containsKey(clazz)) {
//				throw new IllegalArgumentException("errror binding host:" + host + ":" + port+  ", class:" + clazz.getSimpleName());
//			}
//			
//			rmi = remoteMap.get(clazz);
//			if (Objects.isNull(rmi)) {
//				rmi = (Rmi)Naming.lookup("rmi://" + host + ":" + port+  "/" + clazz.getSimpleName());
//				remoteMap.replace(clazz, rmi);
//			} else {
//				rmi.isConnected();
//			}
//		} catch (RemoteException e) {
//			Logger.warn("remote connect is break: host=" + host + ", bindclass=" + clazz.getName());
//			rmi = (Rmi)Naming.lookup("rmi://" + host + ":" + port+  "/" + clazz.getSimpleName());
//			remoteMap.replace(clazz, rmi);
//		}
//		
//		return rmi;
//	}
//	
//	private Map<Class<? extends Rmi>, Rmi> getRmiCache(String host, int port) {
//		Map<Class<? extends Rmi>, Rmi> rmpMap = this.looksMap.get(String.format("%s:%s", host, port));
//		 
//		if (Objects.isNull(rmpMap)) {
//			rmpMap = new HashMap<Class<? extends Rmi>, Rmi>(4);
//			this.looksMap.put(String.format("%s:%s", host, port), rmpMap);
//		}
//		
//		return rmpMap;
//	}
//	
//}
