package org.itas.common.net;

import org.itas.common.collection.Utils;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public final class SocketFactory {
	
	private ServerSocket server;
	private Thread runner;
	private SocketConsumer consumer;
	private boolean stopOnError;
    
    private class ListenThread extends Thread {

    	 public ListenThread() {
             super("socketFactoryListenThread");
         }
    	 
        public void run()  {
            while(runner == this) 
            {
                if(consumer.getAcceptDelay() > 0)
                    Utils.sleep(consumer.getAcceptDelay());
                try
                {
                    Socket socket = server.accept();
                    if(consumer == null || !consumer.consumeSocket(socket))
                        socket.close();
                    continue;
                }
                catch(IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
                if(stopOnError)
                    break;
            }
            try
            {
                server.close();
            }
            catch(IOException ioexception1)
            {
                ioexception1.printStackTrace();
            }
            runner = null;
        }

       
    }


    public void stop()
        throws IOException
    {
        if(runner != null)
        {
            runner.interrupt();
            runner = null;
        }
    }

    public SocketFactory(int i, SocketConsumer socketconsumer)
        throws IOException
    {
        this(new ServerSocket(i), socketconsumer);
    }

    public SocketFactory(ServerSocket serversocket, SocketConsumer socketconsumer)
    {
        stopOnError = true;
        server = serversocket;
        consumer = socketconsumer;
    }

    public void setStopOnError(boolean flag)
    {
        stopOnError = flag;
    }

    public boolean getStopOnError()
    {
        return stopOnError;
    }

    public void start()
    {
        if(runner == null)
        {
            runner = new ListenThread();
            runner.start();
        }
    }

    
}
