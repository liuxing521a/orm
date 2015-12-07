//package org.itas.core.net;
//
//import java.rmi.Naming;
//import java.rmi.Remote;
//import java.rmi.registry.LocateRegistry;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.itas.core.annotation.RmiRef;
//import org.itas.core.net.Hosts.Address;
//import org.itas.core.resource.HostRes;
//import org.itas.util.Utils.ClassUtils;
//
//
//public abstract class RmiServer {
//	
//	private static final RmiServer intance = new RmiServer(){};
//	
//	private final Map<Class<?>, Rmi> refMap = new HashMap<>(4);
//	
//	private RmiServer() {
//	}
//	
//	public static RmiServer getDefault() {
//		return intance;
//	}
//	
//	public void Initializer(HostRes hostRes, String pack) throws Exception {
//		Initializer(hostRes.getHost(), hostRes.getPort(), pack);
//	}
//	
//	public void Initializer(Address server, String pack) throws Exception {
//		Initializer(server.getHost(), server.getPort(), pack);
//	}
//	
//	private void Initializer(String host, int port, String pack) throws Exception {
//		LocateRegistry.createRegistry(port);
//		List<Class<?>> classList = ClassUtils.loadClazz(Rmi.class, pack);
//		for (Class<?> clazz : classList) {
//			if (clazz.isInterface() || !Remote.class.isAssignableFrom(clazz)) {
//				continue;
//			}
//			
//			RmiRef ref = clazz.getAnnotation(RmiRef.class);
//			Rmi remote = (Rmi) clazz.newInstance();
//			
//			Naming.rebind("rmi://" + host + ":" + port + "/" + ref.value().getSimpleName(), remote);
//			refMap.put(ref.value(), remote);
//		}
//	}
//	
//	public void destory(HostRes hostRes) throws Exception {
//		destory(hostRes.getHost(), hostRes.getPort());
//	}
//
//	public void destory(Address server) throws Exception {
//		destory(server.getHost(), server.getPort());
//	}
//
//	private void destory(String host, int port) throws Exception {
//		for (Entry<Class<?>, Rmi> entry : refMap.entrySet()) {
//			Naming.unbind("rmi://" + host + ":" + port + "/" + entry.getKey().getSimpleName());
//		}
//	}
//	
//}
