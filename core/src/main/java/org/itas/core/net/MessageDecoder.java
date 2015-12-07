package org.itas.core.net;

import static org.itas.core.net.Message.key;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Objects;

import org.itas.common.Logger;
import org.itas.core.User;

//@Sharable
public class MessageDecoder extends ByteToMessageDecoder {

	
    public MessageDecoder() {
    	super();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf ioBuf, List<Object> out) throws Exception {
    	if (ioBuf.readableBytes() < 4) {
    		return;
    	}
    	
		short length = ioBuf.order(ByteOrder.LITTLE_ENDIAN).getShort(ioBuf.readerIndex());
		if (length < 4) {
			Logger.warn( "message too small[4]: lenght={}, totalBufferSize={}, userId={}", new Object[]{length,  ioBuf.readableBytes(), getUserInfo(ctx.channel())});
			ctx.channel().close();
			return;
		}

		if (length > 10240) {// 不超过10K
			ctx.channel().close();
			Logger.warn( "message too large[{}]: lenght={}, totalBufferSize={}, userId={}", new Object[]{10240, length,  ioBuf.readableBytes(), getUserInfo(ctx.channel())});
			return;
		}
		
		if (length > ioBuf.readableBytes()) {
			return;
		}

		ByteBuf buf = ioBuf.readSlice(length);
		buf.skipBytes(2);
		
		buf = buf.copy().order(ByteOrder.LITTLE_ENDIAN);
		out.add(Message.allocate(buf.readShort(), buf, ctx.channel()));
	}

    private String getUserInfo(Channel channel) {
    	User user = channel.attr(key).get();
    	if (Objects.isNull(user)) {
    		return "userid=0, nickname=null";
    	}
    	
    	return String.format("userid=%s, nickname=%s", user.getId(), user.getName());
    }

}
