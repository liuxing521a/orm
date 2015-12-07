package org.itas.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BubferBuilder {
	
	/**
	 * java.nio.ByteBuffer
	 */
	private ByteBuffer buffer; // Where we construct the FlatBuffer.
	
	/**
	 * byte order
	 */
	private ByteOrder order;
	
	public BubferBuilder(int size) {
		this(size, ByteOrder.LITTLE_ENDIAN);
	}

	public BubferBuilder(int size, ByteOrder order) {
		this.order = order;
		this.buffer = newByteBuffer(size <= 0 ? 1 : size);
	}
	
	public ByteBuffer toBuffer() {
		return buffer;
	}
	
	public void addBytes(byte[] bs) {
		prep(bs.length);
		buffer.put(bs);
	}
	
	public void addByte(byte value) {
		prep(1);
		buffer.put(value);
	}

	public void addShort(short value) {
		prep(2);
		buffer.putShort(value);
	}

	public void addInt(int value) {
		prep(4);
		buffer.putInt(value);
	}

	public void addLong(long value) {
		prep(8);
		buffer.putLong(value);
	}

	public void addFloat(float value) {
		prep(4);
		buffer.putFloat(value);
	}

	public void addDouble(double value) {
		prep(8);
		buffer.putDouble(value);
	}
	
	public void replaceByte(int index, byte value) {
		buffer.put(index, value);
	}

	public void replaceShort(int index, short value) {
		buffer.putShort(index, value);
	}

	public void replaceInt(int index, int value) {
		buffer.putInt(index, value);
	}

	public void replaceLong(int index, long value) {
		buffer.putLong(index, value);
	}

	public void replaceFloat(int index, float value) {
		buffer.putFloat(index, value);
	}

	public void replaceDouble(int index, double value) {
		buffer.putDouble(index, value);
	}
	
	public int position() {
		return buffer.position();
	}
	
	private void prep(int size) {
		while (buffer.position() + size < buffer.capacity()) {
			buffer = growByteBuffer(buffer);
		}
	}

	private ByteBuffer newByteBuffer(int capacity) {
		ByteBuffer newbb = ByteBuffer.allocate(capacity);
		newbb.order(order);
		
		return newbb;
	}

	private ByteBuffer growByteBuffer(ByteBuffer bb) {
		int old_buf_size = bb.capacity();
		int new_buf_size = old_buf_size << 1;

		if (new_buf_size > Short.MAX_VALUE)  {
			throw new AssertionError("BubferBuilder: cannot grow buffer beyond 2 gigabytes.");
		}
		
		bb.position(0);
		ByteBuffer nbb = newByteBuffer(new_buf_size);
		nbb.position(new_buf_size - old_buf_size);
		nbb.put(bb);
		return nbb;
	}
}
