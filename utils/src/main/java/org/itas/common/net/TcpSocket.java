package org.itas.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.itas.common.collection.ByteBuffer;
import org.itas.common.collection.DataReader;
import org.itas.common.collection.DataWriter;

public class TcpSocket {

	private DataReader reader;
	private DataWriter writer;
	private InputStream in;
	private OutputStream out;
	private Socket socket;
	private boolean active;
	
	public DataWriter getWriter() {
		return writer;
	}

	public synchronized void close() {
		active = false;
		if (socket == null)
			return;
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		} finally {
			reader = null;
			writer = null;
			in = null;
			out = null;
			socket = null;
		}
	}

	public String toString() {
		if (socket != null)
			return "remoteIP=" + socket.getInetAddress().getHostAddress()
					+ ";remotePort=" + socket.getPort() + ";localPort="
					+ socket.getLocalPort();
		else
			return "";
	}

	public TcpSocket(String s, int i) throws IOException {
		this(new Socket(s, i));
	}

	public TcpSocket(Socket socket1) throws IOException {
		active = true;
		socket = socket1;
		in = socket1.getInputStream();
		out = socket1.getOutputStream();
		reader = new DataReader(in);
		writer = new DataWriter(out);
	}

	public TcpSocket(String s, int i, String s1, int j) throws IOException {
		this(new Socket(s, i), s1, j);
	}

	public TcpSocket(String s, int i, String s1, String s2, String s3, int j)
			throws IOException {
		this(new Socket(s, i), s1, s2, s3, j);
	}

	public TcpSocket(Socket socket1, String s, int i) throws IOException {
		active = true;
		in = socket1.getInputStream();
		out = socket1.getOutputStream();
		reader = new DataReader(in);
		writer = new DataWriter(out);
		byte abyte0[] = { 5, 1, 0 };
		out.write(abyte0);
		int j = reader.readShort();
		if (j != 5)
			throw new IOException("proxy not support!");
		abyte0 = (new byte[] { 5, 1, 0, 3 });
		out.write(abyte0);
		abyte0 = s.getBytes();
		out.write(abyte0.length);
		out.write(abyte0);
		out.write(i >> 8);
		out.write(i & 0xff);
		j = reader.readInt();
		if (j != 0x1000005) {
			throw new IOException("proxy failed");
		} else {
			reader.readBytes(4);
			int k = in.read();
			int l = in.read();
			socket = socket1;
			return;
		}
	}

	public TcpSocket(Socket socket1, String s, String s1, String s2, int i)
			throws IOException {
		active = true;
		in = socket1.getInputStream();
		out = socket1.getOutputStream();
		reader = new DataReader(in);
		writer = new DataWriter(out);
		byte abyte0[] = { 5, 2, 0, 2 };
		out.write(abyte0);
		int j = reader.readShort();
		if (j != 517)
			throw new IOException("proxy not support!");
		out.write(1);
		abyte0 = s.getBytes();
		out.write(abyte0.length);
		out.write(abyte0);
		abyte0 = s1.getBytes();
		out.write(abyte0.length);
		out.write(abyte0);
		j = reader.readShort();
		if (j != 1)
			throw new IOException("invalid username or password");
		abyte0 = (new byte[] { 5, 1, 0, 3 });
		out.write(abyte0);
		abyte0 = s2.getBytes();
		out.write(abyte0.length);
		out.write(abyte0);
		out.write(i >> 8 & 0xff);
		out.write(i & 0xff);
		j = reader.readInt();
		if (j != 0x1000005) {
			throw new IOException("proxy failed");
		} else {
			reader.readBytes(4);
			int k = in.read();
			int l = in.read();
			socket = socket1;
			return;
		}
	}

	public byte[] peek() {
		if (!active)
			return null;
		if (in == null)
			return null;
		try {
			int i = in.available();
			byte abyte0[] = new byte[i];
			for (int j = in.read(abyte0, 0, abyte0.length); j < abyte0.length; j += in
					.read(abyte0, j, abyte0.length - j))
				;
			return abyte0;
		} catch (IOException _ex) {
			active = false;
		}
		return null;
	}

	public int peek(byte abyte0[]) {
		if (!active)
			return 0;
		if (in == null)
			return 0;
		try {
			int i = Math.min(abyte0.length, in.available());
			for (int j = 0; j < i; j++)
				abyte0[j] = (byte) in.read();

			return i;
		} catch (Exception _ex) {
			active = false;
		}
		return 0;
	}

	public DataReader getReader() {
		return reader;
	}

	public int available() {
		if (in == null || !active)
			return 0;
		try {
			return in.available();
		} catch (IOException _ex) {
			active = false;
		}
		return 0;
	}

	public byte[] getLocalIp() {
		if (socket != null)
			return socket.getLocalAddress().getAddress();
		else
			return null;
	}

	public String getHost() {
		if (socket != null)
			return socket.getInetAddress().getHostAddress();
		else
			return null;
	}

	public String getLocalHost() {
		if (socket != null)
			return socket.getLocalAddress().getHostAddress();
		else
			return null;
	}

	public byte[] getHostIp() {
		if (socket != null)
			return socket.getInetAddress().getAddress();
		else
			return null;
	}

	public boolean active() {
		return active;
	}

	public boolean hasData() {
		if (in == null || !active)
			return false;
		try {
			return in.available() > 0;
		} catch (IOException _ex) {
			active = false;
		}
		return false;
	}

	public boolean send(byte abyte0[], int i, int j) {
		if (!active)
			return false;
		try {
			out.write(abyte0, i, j);
			out.flush();
			return true;
		} catch (IOException _ex) {
			active = false;
		}
		return false;
	}

	public boolean send(ByteBuffer bytebuffer) {
		byte abyte0[] = bytebuffer.getBytes();
		int i = bytebuffer.position();
		int j = bytebuffer.available();
		return send(abyte0, i, j);
	}

	public int getPort() {
		if (socket != null)
			return socket.getPort();
		else
			return 0;
	}

	public int getLocalPort() {
		if (socket != null)
			return socket.getLocalPort();
		else
			return 0;
	}

}
