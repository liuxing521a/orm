// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataWriter.java

package org.itas.common.collection;

import java.io.*;

public final class DataWriter extends FilterOutputStream
{

    public void writeBoolean(boolean flag)
        throws IOException
    {
        write(flag ? 1 : 0);
    }

    public void writeByte(int i)
        throws IOException
    {
        write(i & 0xff);
    }

    public void closeStream()
        throws IOException
    {
        out.close();
    }

    public void writeShortString(String s)
        throws IOException
    {
        if(s == null || s.length() == 0)
        {
            writeByte(0);
        } else
        {
            byte abyte0[] = s.getBytes();
            if(abyte0.length > 255)
                throw new IOException("string length overflow");
            writeByte(abyte0.length);
            write(abyte0);
        }
    }

    public DataWriter(OutputStream outputstream)
    {
        super(outputstream);
        data = new byte[2];
        out = outputstream;
    }

    public void writeShort(int i)
        throws IOException
    {
        data[0] = (byte)(i & 0xff);
        data[1] = (byte)(i >> 8 & 0xff);
        write(data, 0, 2);
    }

    public void write(byte abyte0[])
        throws IOException
    {
        out.write(abyte0);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        out.write(abyte0, i, j);
    }

    public void writeChar(char c)
        throws IOException
    {
        writeShort(c);
    }

    public void writeInt(int i)
        throws IOException
    {
        writeShort(i & 0xffff);
        writeShort(i >> 16 & 0xffff);
    }

    public void writeString(String s)
        throws IOException
    {
        if(s == null || s.length() == 0)
        {
            writeShort(0);
        } else
        {
            byte abyte0[] = s.getBytes();
            if(abyte0.length > 65535)
                throw new IOException("string length overflow");
            writeShort(abyte0.length);
            write(abyte0);
        }
    }

    private byte data[];
    private OutputStream out;
}
