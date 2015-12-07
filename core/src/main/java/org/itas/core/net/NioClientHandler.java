package org.itas.core.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.itas.common.Logger;

public class NioClientHandler extends SimpleChannelInboundHandler<Message> {
	
	/** 行为监听器*/
	private final List<SessionListener> listeners;
	
	public NioClientHandler() throws Exception {
		this.listeners = new ArrayList<>(4);
	}

	public void addListener(SessionListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(SessionListener listener) {
		this.listeners.remove(listener);
	}
	
	
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
    	for (SessionListener listener : listeners) {
    		listener.onSessionConnected(ctx.channel());
		}
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	for (SessionListener listener : listeners) {
    		listener.onSessionDestroyed(ctx.channel());
		}
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message message) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	if (cause instanceof IOException) {
    		ctx.close();
    		return;
		} 

    	Logger.error("netHandler exceptionCaught:", cause);
    }
}
