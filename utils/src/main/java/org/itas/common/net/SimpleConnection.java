// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleConnection.java

package org.itas.common.net;

import org.itas.common.collection.ByteBuffer;
import org.itas.common.collection.Utils;

// Referenced classes of package org.itas.common.net:
//            NetConnection, TcpSocket, NetServer

public class SimpleConnection extends NetConnection
    implements Runnable
{

    public boolean isActive()
    {
        return socket.active();
    }

    protected int sendDataImpl(byte abyte0[], int i, int j)
    {
        if(sendBuffer != null)
            synchronized(sendBuffer)
            {
                sendBuffer.writeBytes(abyte0, i, j);
                netServer.addSendedBytes(j);
            }
        else
            socket.send(abyte0, i, j);
        return j;
    }

    public void close()
    {
        socket.close();
    }

    public String toString()
    {
        return "SimpleConnection(" + socket.toString() + ")";
    }

    public SimpleConnection(TcpSocket tcpsocket)
    {
        sendBuffer = null;
        socket = tcpsocket;
    }

    public SimpleConnection(TcpSocket tcpsocket, NetServer netserver)
    {
        sendBuffer = null;
        socket = tcpsocket;
        netServer = netserver;
        sendBuffer = new ByteBuffer(10240);
    }

    public String getHost()
    {
        if(socket != null)
            return socket.getHost();
        else
            return null;
    }

    public TcpSocket getSocket()
    {
        return socket;
    }

    public boolean hasData()
    {
        return socket.hasData();
    }

    public void run()
    {
        byte abyte0[] = new byte[1024];
        for(; isActive(); Utils.sleep(5))
        {
            long l = System.currentTimeMillis();
            update(l);
            if(socket.hasData())
            {
                int i = socket.peek(abyte0);
                onDataRead(abyte0, 0, i, l);
                if(netServer != null)
                    netServer.addReadBytes(i);
            }
            int j;
            synchronized(sendBuffer)
            {
                j = sendBuffer.available();
                if(j > 0)
                {
                    if(j > abyte0.length)
                        abyte0 = new byte[j];
                    System.arraycopy(sendBuffer.getRawBytes(), sendBuffer.position(), abyte0, 0, j);
                    sendBuffer.clear();
                }
            }
            if(j > 0)
                socket.send(abyte0, 0, j);
        }

    }

    public byte[] getHostIP()
    {
        if(socket != null)
            return socket.getHostIp();
        else
            return null;
    }

    public int getPort()
    {
        if(socket != null)
            return socket.getPort();
        else
            return -1;
    }

    public int getLocalPort()
    {
        if(socket != null)
            return socket.getLocalPort();
        else
            return -1;
    }

    private TcpSocket socket;
    private ByteBuffer sendBuffer;
    private NetServer netServer;
}
