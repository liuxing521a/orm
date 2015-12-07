package org.itas.core.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

class NioServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private NioServerHandler handler;

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //pipeline.addLast("ping", new IdleStateHandler(30, 15, 10, TimeUnit.SECONDS));
        pipeline.addLast("decoder", new MessageDecoder());
        pipeline.addLast("encoder", new MessageEncoder());

        pipeline.addLast("handler", handler);
    }
}
