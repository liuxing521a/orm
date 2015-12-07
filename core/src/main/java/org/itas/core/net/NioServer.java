package org.itas.core.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.itas.core.net.Hosts.Address;

public class NioServer {

    
	/** 可能发生事件*/
    private ChannelFuture future;
    
    /** 绑定地址*/
    private SocketAddress socketAddress;
    

    public NioServer(Address address) throws Exception {
        this.socketAddress = new InetSocketAddress(address.getHost(), address.getPort());
    }

    public void initialize() throws InterruptedException  {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverboots = new ServerBootstrap();
			serverboots.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new NioServerInitializer())
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_RCVBUF, 1024)
			.childOption(ChannelOption.SO_SNDBUF, 1024)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childOption(ChannelOption.SO_REUSEADDR, true)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			future = serverboots.bind(socketAddress).sync();
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
    }
    
    public void destroy() {
    	future.channel().close();
	}

	@Override
	public String toString() {
		return socketAddress.toString();
	}
}
