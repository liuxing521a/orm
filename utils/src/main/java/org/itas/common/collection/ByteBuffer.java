package org.itas.common.collection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ByteBuffer
  implements Cloneable
{
  private int readPos;
  private int writePos;
  private byte[] data;

  public void position(int paramInt)
  {
    this.readPos = (this.writePos = paramInt);
  }

  public void readFrom(InputStream paramInputStream)
    throws IOException
  {
    readFrom(paramInputStream, capacity() - length());
  }

  public void skipBytes(int paramInt)
  {
    this.readPos += paramInt;
  }

  public void readFrom(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    ensureCapacity(this.writePos + paramInt);
    int i = 0;
    while (i < paramInt)
      i += paramInputStream.read(this.data, this.writePos + i, paramInt - i);
    this.writePos += paramInt;
  }

  public int capacity()
  {
    return this.data.length;
  }

  private void ensureCapacity(int paramInt)
  {
    if (paramInt <= this.data.length)
      return;
    byte[] arrayOfByte = new byte[paramInt * 3 / 2];
    System.arraycopy(this.data, 0, arrayOfByte, 0, this.writePos);
    this.data = arrayOfByte;
  }

  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    int i = available();
    for (int j = 0; j < i; ++j)
      paramOutputStream.write(this.data[(this.readPos++)]);
  }

  public void pack()
  {
    if (this.readPos == 0)
      return;
    int i = available();
    for (int j = 0; j < i; ++j)
      this.data[j] = this.data[(this.readPos++)];
    this.readPos = 0;
    this.writePos = i;
  }

  public void writeByte(int paramInt)
  {
    writeNumber(paramInt, 1);
  }

  public int readByte()
  {
    return this.data[(this.readPos++)];
  }

  public int readUnsignedByte()
  {
    return this.data[(this.readPos++)] & 0xFF;
  }

  protected void read(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    System.arraycopy(this.data, paramInt3, paramArrayOfByte, paramInt1, paramInt2);
  }

  public int getReadPos()
  {
    return this.readPos;
  }

  public void setReadPos(int paramInt)
  {
    this.readPos = paramInt;
  }

  protected void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    ensureCapacity(paramInt3 + paramInt2);
    System.arraycopy(paramArrayOfByte, paramInt1, this.data, paramInt3, paramInt2);
  }

  public void writeChar(char paramChar)
  {
    writeNumber(paramChar, 2);
  }

  public char readChar()
  {
    return (char)(int)(readNumber(2) & 0xFFFF);
  }

  private void writeNumber(long paramLong, int paramInt)
  {
    ensureCapacity(this.writePos + paramInt);
    for (int i = 0; i < paramInt; ++i)
    {
      this.data[(this.writePos++)] = (byte)(int)paramLong;
      paramLong >>= 8;
    }
  }

  private long readNumber(int paramInt)
  {
    long l = 0L;
    for (int i = 0; i < paramInt; ++i)
      l |= (this.data[(this.readPos++)] & 0xFF) << (i << 3);
    return l;
  }

  public byte[] getBytes()
  {
    byte[] arrayOfByte = new byte[length()];
    System.arraycopy(this.data, 0, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  public Object clone()
  {
    ByteBuffer localByteBuffer = new ByteBuffer(this.writePos);
    System.arraycopy(this.data, 0, localByteBuffer.data, 0, this.writePos);
    localByteBuffer.writePos = this.writePos;
    localByteBuffer.readPos = this.readPos;
    return localByteBuffer;
  }

  public void writeAnsiString(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      writeShort(0);
    }
    else
    {
      if (paramString.length() > 32767)
        throw new IllegalArgumentException("string over flow");
      byte[] arrayOfByte = paramString.getBytes();
      writeShort(arrayOfByte.length);
      writeBytes(arrayOfByte);
    }
  }

  public String readAnsiString()
  {
    int i = readUnsignedShort();
    if (i == 0)
      return "";
    byte[] arrayOfByte = readBytes(i);
    return new String(arrayOfByte);
  }

  public int length()
  {
    return this.writePos;
  }

  public void writeBoolean(boolean paramBoolean)
  {
    writeByte((paramBoolean) ? 1 : 0);
  }

  public boolean readBoolean()
  {
    return readByte() != 0;
  }

  public float readFloat()
  {
    int i = readInt();
    return Float.intBitsToFloat(i);
  }

  public void reset()
  {
    this.readPos = 0;
  }

  public void writeLong(long paramLong)
  {
    writeNumber(paramLong, 8);
  }

  public ByteBuffer()
  {
    this(1024);
  }

  public ByteBuffer(int paramInt)
  {
    this.data = new byte[paramInt];
  }

  public ByteBuffer(byte[] paramArrayOfByte)
  {
    this(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public ByteBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.data = paramArrayOfByte;
    this.readPos = paramInt1;
    this.writePos = (paramInt1 + paramInt2);
  }

  public void writeShortAnsiString(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      writeByte(0);
    }
    else
    {
      byte[] arrayOfByte = paramString.getBytes();
      if (arrayOfByte.length > 255)
        throw new IllegalArgumentException("short string over flow");
      writeByte(arrayOfByte.length);
      writeBytes(arrayOfByte);
    }
  }

  public long readLong()
  {
    return readNumber(8);
  }

  public void writeShort(int paramInt)
  {
    writeNumber(paramInt, 2);
  }

  public int readShort()
  {
    return (short)(int)(readNumber(2) & 0xFFFF);
  }

  public void writeByteBuffer(ByteBuffer paramByteBuffer)
  {
    writeByteBuffer(paramByteBuffer, paramByteBuffer.available());
  }

  public void writeByteBuffer(ByteBuffer paramByteBuffer, int paramInt)
  {
    ensureCapacity(length() + paramInt);
    for (int i = 0; i < paramInt; ++i)
      this.data[(this.writePos++)] = paramByteBuffer.data[(paramByteBuffer.readPos++)];
  }

  public void writeBytes(byte[] paramArrayOfByte)
  {
    writeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    ensureCapacity(this.writePos + paramInt2);
    for (int i = 0; i < paramInt2; ++i)
      this.data[(this.writePos++)] = paramArrayOfByte[(paramInt1++)];
  }

  public byte[] readBytes(int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    for (int i = 0; i < paramInt; ++i)
      arrayOfByte[i] = this.data[(this.readPos++)];
    return arrayOfByte;
  }

  public int readUnsignedShort()
  {
    return (int)(readNumber(2) & 0xFFFF);
  }

  public String readShortAnsiString()
  {
    int i = readUnsignedByte();
    if (i == 0)
      return "";
    byte[] arrayOfByte = readBytes(i);
    return new String(arrayOfByte);
  }

  public int available()
  {
    return this.writePos - this.readPos;
  }

  public String toString()
  {
    return new String(this.data, 0, this.writePos);
  }

  public int getWritePos()
  {
    return this.writePos;
  }

  public void setWritePos(int paramInt)
  {
    this.writePos = paramInt;
  }

  public byte[] getRawBytes()
  {
    return this.data;
  }

  public void writeUTF(String paramString)
  {
    if (paramString == null)
      paramString = "";
    int i = paramString.length();
    int j = 0;
    int l;
    for (int k = 0; k < i; ++k)
    {
      l = paramString.charAt(k);
      if (l < 127)
        ++j;
      else if (l > 2047)
        j += 3;
      else
        j += 2;
    }
    if (j > 65535)
      throw new IllegalArgumentException("the string is too long:" + i);
    ensureCapacity(this.writePos + j + 2);
    writeShort(j);
    for (int k = 0; k < i; ++k)
    {
      l = paramString.charAt(k);
      if (l < 127)
      {
        this.data[(this.writePos++)] = (byte)l;
      }
      else if (l > 2047)
      {
        this.data[(this.writePos++)] = (byte)(0xE0 | l >> 12 & 0xF);
        this.data[(this.writePos++)] = (byte)(0x80 | l >> 6 & 0x3F);
        this.data[(this.writePos++)] = (byte)(0x80 | l & 0x3F);
      }
      else
      {
        this.data[(this.writePos++)] = (byte)(0xC0 | l >> 6 & 0x1F);
        this.data[(this.writePos++)] = (byte)(0x80 | l & 0x3F);
      }
    }
  }

  public String readUTF()
  {
    int i = readUnsignedShort();
    if (i == 0)
      return "";
    char[] arrayOfChar = new char[i];
    int j = 0;
    int k = 0;
    int l = 0;
    int i1 = 0;
    int i2 = this.readPos + i;
    while (this.readPos < i2)
    {
      k = this.data[(this.readPos++)] & 0xFF;
      if (k < 127)
      {
        arrayOfChar[(j++)] = (char)k;
      }
      else if (k >> 5 == 7)
      {
        l = this.data[(this.readPos++)];
        i1 = this.data[(this.readPos++)];
        arrayOfChar[(j++)] = (char)((k & 0xF) << 12 | (l & 0x3F) << 6 | i1 & 0x3F);
      }
      else
      {
        l = this.data[(this.readPos++)];
        arrayOfChar[(j++)] = (char)((k & 0x1F) << 6 | l & 0x3F);
      }
    }
    return new String(arrayOfChar, 0, j);
  }

  public void clear()
  {
    this.writePos = (this.readPos = 0);
  }

  public void writeInt(int paramInt)
  {
    writeNumber(paramInt, 4);
  }

  public int readInt()
  {
    return (int)(readNumber(4) & 0xFFFFFFFF);
  }

  public int position()
  {
    return this.readPos;
  }
}

/* Location:           E:\��Ϸ����\���\server\server.zip
 * Qualified Name:     daff.util.ByteBuffer
 * JD-Core Version:    0.5.4
 */