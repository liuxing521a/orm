// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataReader.java

package org.itas.common.collection;

import java.io.*;

public final class DataReader extends FilterInputStream
{

    public boolean readBoolean()
        throws IOException
    {
        return readByte() == 1;
    }

    public byte readByte()
        throws IOException
    {
        int i = super.in.read(data, 0, 1);
        if(i <= 0)
            throw new IOException("end of inputstream");
        else
            return data[0];
    }

    public void closeStream()
        throws IOException
    {
        super.in.close();
    }

    public String readShortString()
        throws IOException
    {
        int i = readByte() & 0xff;
        if(i == 0)
        {
            return null;
        } else
        {
            byte abyte0[] = readBytes(i);
            return new String(abyte0);
        }
    }

    public DataReader(InputStream inputstream)
    {
        super(inputstream);
        data = new byte[4];
    }

    public short readShort()
        throws IOException
    {
        int i = read(data, 0, 2);
        if(i < 2)
            throw new IOException("end of inputstream");
        else
            return (short)(data[0] & 0xff | (data[1] & 0xff) << 8);
    }

    public byte[] readBytes(int i)
        throws IOException
    {
        byte abyte0[] = new byte[i];
        i = super.in.read(abyte0, 0, abyte0.length);
        if(i < 0)
            throw new IOException("end of inputstream");
        for(; i < abyte0.length; i += super.in.read(abyte0, i, abyte0.length - i));
        return abyte0;
    }

    public char readChar()
        throws IOException
    {
        return (char)readShort();
    }

    public int readInt()
        throws IOException
    {
        int i = readShort() & 0xffff;
        int j = readShort() & 0xffff;
        return i | j << 16;
    }

    public String readString()
        throws IOException
    {
        int i = readShort() & 0xffff;
        if(i == 0)
        {
            return null;
        } else
        {
            byte abyte0[] = readBytes(i);
            return new String(abyte0);
        }
    }

    private byte data[];
}
