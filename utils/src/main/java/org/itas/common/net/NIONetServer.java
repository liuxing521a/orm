// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NIONetServer.java

package org.itas.common.net;

import org.itas.common.collection.ArrayList;
import org.itas.common.collection.Utils;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

// Referenced classes of package org.itas.common.net:
//            NetServer, NIOConnection, NetConnection

public class NIONetServer extends NetServer
    implements Runnable
{

    public NIONetServer()
    {
        data = new byte[1024];
        buffer = ByteBuffer.wrap(data, 0, data.length);
        connectionList = new ArrayList(1000);
    }

    public int getConnectionCount()
    {
        return connectionList.size();
    }

    public void run()
    {
        running = true;
        int i = 0;
        while(running) 
        {
            long l = System.currentTimeMillis();
            int i1 = connectionList.size();
            for(int j1 = 0; j1 < i1; j1++)
            {
                NIOConnection nioconnection = (NIOConnection)connectionList.get(j1);
                if(nioconnection.isActive())
                {
                    nioconnection.update(l);
                } else
                {
                    connectionClosed(nioconnection);
                    connectionList.remove(j1--);
                    i1--;
                }
            }

            try
            {
                i = selector.selectNow();
            }
            catch(IOException ioexception)
            {
                ioexception.printStackTrace();
            }
            if(i > 0)
                processSelection(l);
            Utils.sleep(5);
        }
        int j = connectionList.size();
        for(int k = 0; k < j; k++)
        {
            NetConnection netconnection = (NetConnection)connectionList.get(k);
            connectionClosed(netconnection);
            netconnection.close();
        }

    }

    public boolean start()
    {
        boolean flag = bindServer();
        if(!flag)
        {
            return false;
        } else
        {
            (new Thread(this, "NIONetServerThread")).start();
            return true;
        }
    }

    public void stop()
    {
        running = false;
    }

    private boolean bindServer()
    {
        try
        {
            serverChannel = ServerSocketChannel.open();
            serverSocket = serverChannel.socket();
            serverSocket.setReceiveBufferSize(512);
            serverSocket.setPerformancePreferences(0, 2, 1);
            serverSocket.bind(new InetSocketAddress(getPort()));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverKey = serverChannel.register(selector, 16);
            return true;
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        return false;
    }

    private void processSelection(long l)
    {
        Iterator<SelectionKey> iterator;
        for (iterator = selector.selectedKeys().iterator(); iterator.hasNext(); ) {
        	SelectionKey selectionkey = iterator.next();
        	iterator.remove();
        	if(selectionkey == serverKey)
        	{
        		if(selectionkey.isAcceptable())
        			try
        		{
        				SocketChannel socketchannel = serverChannel.accept(); //  ���������ܵ��׽��ֵ����ӡ�
        				socketchannel.configureBlocking(false);	// ��Ϊ������ģʽ
        				socketchannel.socket().setTcpNoDelay(false);	// ���÷���ʱ
        				SelectionKey selectionkey1 = socketchannel.register(selector, 1);	// 
        				NIOConnection nioconnection2 = new NIOConnection(socketchannel, this);
        				nioconnection2.setCreatedTime(System.currentTimeMillis());
        				selectionkey1.attach(nioconnection2);
        				connectionList.add(nioconnection2);
        				connectionOpened(nioconnection2);
        		}
        		catch(IOException ioexception)
        		{
        			ioexception.printStackTrace();
        		}
        	} 
        	else if(!selectionkey.isValid())
        	{
        		NIOConnection nioconnection = (NIOConnection)selectionkey.attachment();
        		if(nioconnection != null)
        			nioconnection.onClosed();
        	} 
        	else  if(selectionkey.isReadable())
        	{
        		NIOConnection nioconnection1 = (NIOConnection)selectionkey.attachment();
        		try
        		{
        			SocketChannel socketchannel1 = (SocketChannel)selectionkey.channel();
        			buffer.clear();
        			int i = socketchannel1.read(buffer);
        			if(i > 0)
        			{
        				nioconnection1.onDataRead(data, 0, i, l);
        				totalBytesRead += i;
        			}
        		}
        		catch(IOException ioexception1)
        		{
        			nioconnection1.onClosed();
        			selectionkey.cancel();
        		}
        	}
		}
    }

    private static final int MAX_READ_BUFFER_SIZE = 1024;
    private ServerSocketChannel serverChannel;
    private ServerSocket serverSocket;
    private Selector selector;
    private SelectionKey serverKey;
    private boolean running;
    private byte data[];
    private ByteBuffer buffer;
    private ArrayList connectionList;
}
