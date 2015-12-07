package org.itas.common.net;

import org.itas.common.collection.ArrayList;
import org.itas.common.collection.List;


public abstract class NetServer
{

    public abstract void stop();

    public abstract int getConnectionCount();

    public synchronized void removeAllNetServerListeners()
    {
        listeners.clear();
    }

    public NetServer()
    {
        listeners = new ArrayList();
    }

    public void setMessageCodecFactory(MessageCodecFactory messagecodecfactory)
    {
        factory = messagecodecfactory;
    }

    public MessageCodecFactory getMessageCodecFactory()
    {
        return factory;
    }

    public abstract boolean start();

    public synchronized void removeNetServerListener(NetServerListener netserverlistener)
    {
        listeners.remove(netserverlistener);
    }

    public void addReadBytes(int i)
    {
        totalBytesRead += i;
    }

    public void addSendedBytes(int i)
    {
        totalBytesSended += i;
    }

    public synchronized void addNetServerListener(NetServerListener netserverlistener)
    {
        listeners.add(netserverlistener);
    }

    public void setMaxConnections(int i)
    {
        maxConnections = i;
    }

    public int getMaxConnections()
    {
        return maxConnections;
    }

    public void init(int i)
    {
        port = i;
    }

    public long getTotalBytesRead()
    {
        return totalBytesRead;
    }

    public long getTotalBytesSended()
    {
        return totalBytesSended;
    }

    protected void connectionOpened(NetConnection netconnection)
    {
        if(factory != null)
        {
            netconnection.setMessageDecoder(factory.createDecoder());
            netconnection.setMessageEncoder(factory.createEncoder());
        }
        int i = listeners.size();
        for(int j = 0; j < i; j++)
        {
            NetServerListener netserverlistener = (NetServerListener)listeners.get(j);
            netserverlistener.connectionOpened(netconnection);
        }

    }

    protected void connectionClosed(NetConnection netconnection)
    {
        int i = listeners.size();
        for(int j = 0; j < i; j++)
        {
            NetServerListener netserverlistener = (NetServerListener)listeners.get(j);
            netserverlistener.connectionClosed(netconnection);
        }

    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int i)
    {
        port = i;
    }

    private int port;
    private List listeners;
    private int maxConnections;
    private MessageCodecFactory factory;
    protected long totalBytesSended;
    protected long totalBytesRead;
}
