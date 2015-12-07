package org.itas.core.net;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Hosts {

	private static final String PATTERN = "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})[:]*([0-9]{2,4})";
	
	private Map<String, Address> Hosts;
	
	private List<Address> HostList;
	
	public Hosts() {
	}
	
	public void initHost(List<String> hosts) {
		Map<String, Address> HostMap = new HashMap<>(hosts.size());
		List<Address> HostList = new ArrayList<>(hosts.size());
		
		for (String address : hosts) {
			Address Host = new Address(address);
			HostList.add(Host);
			HostMap.put(Host.getHost(), Host);
		}
		
		this.Hosts = Collections.unmodifiableMap(HostMap);
		this.HostList = Collections.unmodifiableList(HostList);
	}
	
	public List<Address> getHosts() {
		return this.HostList;
	}
	
	public Address getHost(String host) {
		return this.Hosts.get(host);
	}
	
	public Address getRandomHost() {
		int value = ThreadLocalRandom.current().nextInt(0, HostList.size());
		return this.HostList.get(value);
	}
	
	public static Address findHost(String address) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(address);
		matcher.find();
		
		return new Address(matcher.group(1), Integer.parseInt(matcher.group(2)));
	}

	public static class Address implements Externalizable {

		/** 端口*/
		private int port;
		/** 主机名*/
		private String host;
		
		public Address() {
		}
		
		public Address(String address) {
			String[] strs = address.split(":");
			this.port = Integer.parseInt(strs[1]);
			this.host = strs[0];
		}
		
		public Address(String host, int port) {
			this.port = port;
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}
		
		@Override
		public String toString() {
			return String.format("%s:%s", host, port);
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			
			if (o instanceof Address) {
				Address other = (Address)o;
				return port == other.port && host.equals(other.host);
			}

			return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(port, host);
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeInt(port);
			out.writeUTF(host);
		}

		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			this.port = in.readInt();
			this.host = in.readUTF();
		}
	}
	
}
